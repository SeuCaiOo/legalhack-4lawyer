package br.com.app4lawyer.data.remote

import br.com.app4lawyer.data.remote.network.AppDTO
import br.com.app4lawyer.data.remote.network.AppResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface Service {

    @GET("/.../{id}")
    fun get(
        @Header("token") token: String,
        @Path("id") id: Int
    ): Deferred<AppResponse>

    @POST("/.../{id}")
    fun post(
        @Path("id") id: String,
        @Body codigo: AppDTO
    ): Deferred<AppResponse>


    @PATCH("/...")
    fun patch(
        @Body senha: AppDTO
    ): Deferred<AppResponse>


    @Multipart
    @PATCH("/.../{id}")
    fun patchMultipart(
        @Path("id") id: Int,
        @Part doc: MultipartBody.Part,
        @Part("docName") requestBody: RequestBody
    ): Deferred<AppResponse>


    object MvvmApi {
        val retrofitServiceAPI: Service by lazy { retrofitMoshiAPI.create(Service::class.java) }
    }


}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    this.level = HttpLoggingInterceptor.Level.BODY
}

private val client: OkHttpClient = OkHttpClient.Builder().apply {
    this.addInterceptor(interceptor)
}.build()


private val retrofitMoshiAPI = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(client)
    .baseUrl(BaseUrl.API.url)
    .build()
