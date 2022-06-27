
from typing import Dict, Text
from typing import List

import numpy as np
import tensorflow as tf

from tensorflow_metadata.proto.v0 import schema_pb2
import tensorflow_recommenders as tfrs
from tensorflow_transform.tf_metadata import schema_utils
from tfx import v1 as tfx
from tfx_bsl.public import tfxio

_FEATURE_KEYS = ['userId', 'movieId']
_LABEL_KEY = 'rating'

_FEATURE_SPEC = {
    **{
        feature: tf.io.FixedLenFeature(shape=[1], dtype=tf.int64)
        for feature in _FEATURE_KEYS
    }, _LABEL_KEY: tf.io.FixedLenFeature(shape=[1], dtype=tf.int64)
}


class RankingModel(tf.keras.Model):

  def __init__(self):
    super().__init__()
    embedding_dimension = 32

    unique_user_ids = np.array(range(943)).astype(str)
    unique_movie_ids = np.array(range(1682)).astype(str)

    # Compute embeddings for users.
    self.user_embeddings = tf.keras.Sequential([
        tf.keras.layers.Input(shape=(1,), name='userId', dtype=tf.int64),
        tf.keras.layers.Lambda(lambda x: tf.as_string(x)),
        tf.keras.layers.StringLookup(
            vocabulary=unique_user_ids, mask_token=None),
        tf.keras.layers.Embedding(
            len(unique_user_ids) + 1, embedding_dimension)
    ])

    # Compute embeddings for movies.
    self.movie_embeddings = tf.keras.Sequential([
        tf.keras.layers.Input(shape=(1,), name='movieId', dtype=tf.int64),
        tf.keras.layers.Lambda(lambda x: tf.as_string(x)),
        tf.keras.layers.StringLookup(
            vocabulary=unique_movie_ids, mask_token=None),
        tf.keras.layers.Embedding(
            len(unique_movie_ids) + 1, embedding_dimension)
    ])

    # Compute predictions.
    self.ratings = tf.keras.Sequential([
        tf.keras.layers.Dense(256, activation='relu'),
        tf.keras.layers.Dense(64, activation='relu'),
        tf.keras.layers.Dense(1)
    ])

  def call(self, inputs):

    user_id, movie_id = inputs

    user_embedding = self.user_embeddings(user_id)
    movie_embedding = self.movie_embeddings(movie_id)

    return self.ratings(tf.concat([user_embedding, movie_embedding], axis=2))


class MovielensModel(tfrs.models.Model):

  def __init__(self):
    super().__init__()
    self.ranking_model: tf.keras.Model = RankingModel()
    self.task: tf.keras.layers.Layer = tfrs.tasks.Ranking(
        loss=tf.keras.losses.MeanSquaredError(),
        metrics=[tf.keras.metrics.RootMeanSquaredError()])

  def call(self, features: Dict[str, tf.Tensor]) -> tf.Tensor:
    return self.ranking_model((features['userId'], features['movieId']))

  def compute_loss(self,
                   features: Dict[Text, tf.Tensor],
                   training=False) -> tf.Tensor:

    labels = features[1]
    rating_predictions = self(features[0])

    # The task computes the loss and the metrics.
    return self.task(labels=labels, predictions=rating_predictions)


def _input_fn(file_pattern: List[str],
              data_accessor: tfx.components.DataAccessor,
              schema: schema_pb2.Schema,
              batch_size: int = 256) -> tf.data.Dataset:
  return data_accessor.tf_dataset_factory(
      file_pattern,
      tfxio.TensorFlowDatasetOptions(
          batch_size=batch_size, label_key=_LABEL_KEY),
      schema=schema).repeat()


def _build_keras_model() -> tf.keras.Model:
  return MovielensModel()


# TFX Trainer will call this function.
def run_fn(fn_args: tfx.components.FnArgs):
  """Train the model based on given args.

  Args:
    fn_args: Holds args used to train the model as name/value pairs.
  """
  schema = schema_utils.schema_from_feature_spec(_FEATURE_SPEC)

  train_dataset = _input_fn(
      fn_args.train_files, fn_args.data_accessor, schema, batch_size=8192)
  eval_dataset = _input_fn(
      fn_args.eval_files, fn_args.data_accessor, schema, batch_size=4096)

  model = _build_keras_model()

  model.compile(optimizer=tf.keras.optimizers.Adagrad(learning_rate=0.1))

  model.fit(
      train_dataset,
      steps_per_epoch=fn_args.train_steps,
      epochs = 3,
      validation_data=eval_dataset,
      validation_steps=fn_args.eval_steps)

  model.save(fn_args.serving_model_dir)
