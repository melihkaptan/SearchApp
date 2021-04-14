package com.melhkptn.searchapp.di.module

import com.melhkptn.searchapp.data.repository.SearchRepositoryImpl
import com.melhkptn.searchapp.data.base.DataSource
import com.melhkptn.searchapp.data.source.remote.SearchAPI
import com.melhkptn.searchapp.data.source.remote.SearchMoviesRemoteDataSource
import com.melhkptn.searchapp.di.repository.SearchRepository
import com.melhkptn.searchapp.domain.model.response.SearchResponse
import com.melhkptn.searchapp.domain.model.request.SearchRequest
import com.melhkptn.searchapp.domain.usecase.base.BaseUseCase
import com.melhkptn.searchapp.domain.usecase.SearchDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataModule {


    @Provides
    @Singleton
    fun provideSearchMoviesRemoteDataSource(apiService: SearchAPI): DataSource.RequestRemoteDataSource<SearchRequest, SearchResponse> =
        SearchMoviesRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun provideSearchRepository(
        searchMoviesRemoteDataSource: SearchMoviesRemoteDataSource
    ): SearchRepository =
        SearchRepositoryImpl(
            searchMoviesRemoteDataSource
        )

    @Provides
    @Singleton
    fun provideSearchMoviesUseCase(
        searchRepository: SearchRepository
    ): BaseUseCase.RequestInteractor<SearchDataUseCase.Params, SearchResponse> =
        SearchDataUseCase(
            searchRepository
        )

}