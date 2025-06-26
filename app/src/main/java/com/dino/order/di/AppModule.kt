package com.dino.order.di

import android.app.Application
import android.content.Context
import com.dino.order.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dino.order.DinoOrderApplication
import com.dino.order.authfeature.data.remote.AuthAPIService
import com.dino.order.authfeature.data.remote.AuthAPIServiceImpl
import com.dino.order.authfeature.data.repository.AuthRepositoryImpl
import com.dino.order.authfeature.domain.repository.AuthRepository
import com.dino.order.corefeature.data.remote.util.HttpAPIUtil
import com.dino.order.corefeature.data.spref.DataStoreUtil
import com.dino.order.corefeature.data.spref.SPrefManager
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideDiyanWebApplication(application: Application): DinoOrderApplication {
        return application as DinoOrderApplication
    }


    /**
     * Provides the application context.
     *
     * @param context The application context.
     * @return The application context.
     */
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context) = context


    @Provides
    @Singleton
    fun provideGson() = Gson()

    /**
     * Provides the SPrefManager instance.
     *
     * @param context The application context.
     * @return The SPrefManager instance.
     */
    @Provides
    @Singleton
    fun provideSPrefManager(
        @ApplicationContext context: Context,
        gson: Gson
    ) = SPrefManager(gson, context)

    /**
     * Provides the HttpClient instance with necessary configurations.
     *
     * @return The configured HttpClient instance.
     */
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideHttpClient() =
        HttpClient(CIO) {
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
            install(Logging){
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 20_000
                connectTimeoutMillis = 20_000
                socketTimeoutMillis = 20_000
            }
            if (BuildConfig.DEBUG) install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        explicitNulls = false
                    }
                )
            }
        }

    /**
     * Provides the HttpAPIUtil instance.
     *
     * @param sPrefManager The SPrefManager instance.
     * @param context The application context.
     * @return The HttpAPIUtil instance.
     */
    @Singleton
    @Provides
    fun provideHttpAPIUtil(
        sPrefManager: SPrefManager,
        context: Context
    ) = HttpAPIUtil(
        sPrefManager,
        context
    )

    /**
     * Provides the AuthAPIService instance.
     *
     * @param httpClient The HttpClient instance.
     * @param sPrefManager The SPrefManager instance.
     * @param httpAPIUtil The HttpAPIUtil instance.
     * @return The AuthAPIService instance.
     */
    @Singleton
    @Provides
    fun provideAuthAPIService(
        httpClient: HttpClient,
        sPrefManager: SPrefManager,
        httpAPIUtil: HttpAPIUtil
    ): AuthAPIService = AuthAPIServiceImpl(
        httpClient = httpClient,
        sPrefManager = sPrefManager,
        httpAPIUtil = httpAPIUtil
    )

    /**
     * Provides the AuthRepository instance.
     *
     * @param authAPIService The AuthAPIService instance.
     * @return The AuthRepository instance.
     */
    @Singleton
    @Provides
    fun provideAuthRepository(
        authAPIService: AuthAPIService
    ): AuthRepository = AuthRepositoryImpl(authAPIService)

    /**
     * Provides the DataStoreUtil instance.
     *
     * @param app The application context.
     * @return The DataStoreUtil instance.
     */
    @Singleton
    @Provides
    fun provideDataStoreUtil(
        @ApplicationContext app: Context
    ) = DataStoreUtil(app)
}
