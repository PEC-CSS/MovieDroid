import tensorflow as tf

# Convert the model
converter = tf.lite.TFLiteConverter.from_saved_model('/Users/uttammittal02/Desktop/coderecs/content/serving_model') # path to the SavedModel directory
print("Folder loaded successfully...")
tflite_model = converter.convert()

print("Model ready to write...")
# Save the model.
with open('model.tflite', 'wb') as f:
  f.write(tflite_model)
