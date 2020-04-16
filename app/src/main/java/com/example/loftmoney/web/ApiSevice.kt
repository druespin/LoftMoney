package com.example.loftmoney.web

import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val BASE_URL = "https://loftschool.com/android-api/basic/v1/"

interface ApiService {

    @GET("items")
    fun getItems(@Query("type") type: String,
                 @Query("auth-token") authToken: String): Observable<List<DataArray>>


    @POST("items/add")
    @FormUrlEncoded
    fun postItem(@Field("price") price: Int,
                @Field("name") name: String,
                @Field("type") type: String,
                @Field("auth-token") authToken: String): Completable

    @POST("items/remove")
    @FormUrlEncoded
    fun removeItem(@Field("id") itemId: Int): Completable


    @GET("auth")
    fun getTokenForUser(@Query("social_user_id") userId: String): Observable<AuthResponse>


    @GET("logout")
    fun logout(): Observable<Status>

    @GET("balance")
    fun getBalance(): Observable<Balance>


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