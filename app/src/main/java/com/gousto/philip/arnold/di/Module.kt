package com.gousto.philip.arnold.di

import com.google.gson.GsonBuilder
import com.gousto.philip.arnold.ActivityViewModel
import com.gousto.philip.arnold.network.Network
import com.gousto.philip.arnold.ui.detail.DetailRepository
import com.gousto.philip.arnold.ui.detail.DetailViewModel
import com.gousto.philip.arnold.ui.main.MainRepository
import com.gousto.philip.arnold.ui.main.MainViewModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_BASE_URL = "https://api.gousto.co.uk/products/v2.0/"

val appModules = module {
    // The Retrofit service using our custom HTTP client instance as a singleton
    single {
        createWebService<Network>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = API_BASE_URL
        )
    }
    // Tells Koin how to create an instance of MainRepository
    factory { MainRepository(get()) }
    factory { DetailRepository() }

    // Specific viewModel pattern to tell Koin how to build MainViewModel
    viewModel { ActivityViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method(), original.body()).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}
