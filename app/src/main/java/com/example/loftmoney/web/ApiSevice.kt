package com.example.loftmoney.web

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://verdant-violet.glitch.me/"

interface ApiService {

    @GET("items")
    fun getItems(@Query("type") type: String): Observable<ResponseItem>

    @POST("items/add")
    @FormUrlEncoded
    fun postItem(@Field("price") price: Int?,
                @Field("name") name: String?,
                @Field("type") type: String?): Completable


    companion object {

        private val interceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val createApiService: ApiService = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}