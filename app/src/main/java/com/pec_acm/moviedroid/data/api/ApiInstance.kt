package com.pec_acm.moviedroid.data.api

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.pec_acm.moviedroid.App.Companion.appContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiInstance {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = "2c42abff161f2feeec8a668ca30982d7"
    private const val cacheSize = (10*1024*1024).toLong()
    private val cache = Cache(appContext!!.cacheDir, cacheSize)
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60,TimeUnit.SECONDS)
        .writeTimeout(60,TimeUnit.SECONDS)
        .cache(cache)
        .addInterceptor{
            var request = it.request()
            request = if (hasNetwork() ==true)
                request.newBuilder().header("Cache-Control","public, max-age="+5).build()
            else
                request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale="+60*60*24*7).build()
            it.proceed(request)
        }
        .build()

    val api : TMDBApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(TMDBApi::class.java)
    }
    private fun hasNetwork(): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = appContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}
