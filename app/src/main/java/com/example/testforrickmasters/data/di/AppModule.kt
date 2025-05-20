package com.example.testforrickmasters.data.di

import com.example.testforrickmasters.data.api.StatisticsApi
import com.example.testforrickmasters.data.api.StatisticsApiImpl
import com.example.testforrickmasters.data.api.UserApi
import com.example.testforrickmasters.data.api.UserApiImpl
import com.example.testforrickmasters.data.repository.StatisticsRepositoryImpl
import com.example.testforrickmasters.data.repository.UserRepositoryImpl
import com.example.testforrickmasters.domain.repsitory.StatisticsRepository
import com.example.testforrickmasters.domain.repsitory.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Provides
    @Singleton
    fun provideStatisticsApi(client: HttpClient): StatisticsApi {
        return StatisticsApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideUsersApi(client: HttpClient): UserApi {
        return UserApiImpl(client)
    }

    @Provides
    @Singleton
    fun provideStatisticsRepository(api: StatisticsApi): StatisticsRepository {
        return StatisticsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideUsersRepository(api: UserApi): UserRepository {
        return UserRepositoryImpl(api)
    }
}