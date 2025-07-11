package com.dino.message.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.dino.message.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.dino.message.DinoOrderApplication
import com.dino.message.authfeature.data.remote.AuthAPIService
import com.dino.message.authfeature.data.remote.AuthAPIServiceImpl
import com.dino.message.authfeature.data.repository.AuthRepositoryImpl
import com.dino.message.authfeature.domain.repository.AuthRepository
import com.dino.message.chatfeature.data.db.MessageDao
import com.dino.message.chatfeature.data.remote.ChatApiService
import com.dino.message.chatfeature.data.remote.ChatApiServiceImpl
import com.dino.message.chatfeature.data.repository.ChatRepositoryImpl
import com.dino.message.chatfeature.domain.repository.ChatRepository
import com.dino.message.corefeature.data.db.MessageDatabase
import com.dino.message.corefeature.data.remote.util.HttpAPIUtil
import com.dino.message.corefeature.data.spref.DataStoreUtil
import com.dino.message.corefeature.data.spref.SPrefManager
import com.dino.message.mainpage.data.remote.MainApiService
import com.dino.message.mainpage.data.remote.MainApiServiceImpl
import com.dino.message.mainpage.data.repository.MainRepositoryImpl
import com.dino.message.mainpage.domain.repository.MainRepository
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


    @Singleton
    @Provides
    fun provideMainAPIService(
        httpClient: HttpClient,
        sPrefManager: SPrefManager,
        httpAPIUtil: HttpAPIUtil
    ): MainApiService = MainApiServiceImpl(
        httpClient = httpClient,
        sPrefManager = sPrefManager,
        httpAPIUtil = httpAPIUtil
    )

    @Singleton
    @Provides
    fun provideMainRepository(
        mainApiService: MainApiService
    ): MainRepository = MainRepositoryImpl(mainApiService)


    @Singleton
    @Provides
    fun provideChatAPIService(
        httpClient: HttpClient,
        sPrefManager: SPrefManager,
        httpAPIUtil: HttpAPIUtil
    ): ChatApiService = ChatApiServiceImpl(
        httpClient = httpClient,
        sPrefManager = sPrefManager,
        httpAPIUtil = httpAPIUtil
    )

    @Singleton
    @Provides
    fun provideChatRepository(
        chatApiService: ChatApiService,
        messageDao: MessageDao
    ): ChatRepository = ChatRepositoryImpl(chatApiService, messageDao)


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

    @Provides
    @Singleton
    fun provideChatDatabase(@ApplicationContext context: Context): MessageDatabase {
        return Room.databaseBuilder(
            context,
            MessageDatabase::class.java,
            "chat_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMessageDao(database: MessageDatabase): MessageDao {
        return database.messageDao()
    }
}
