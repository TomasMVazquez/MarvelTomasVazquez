package com.applications.toms.data.repository

import com.applications.toms.data.*
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.CharactersContainer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to search fragment)
 */
class SearchRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(nameStartsWith: String): Flow<Either<CharactersContainer, String>> = flow {
        remoteDataSource.getCharactersByNameSearch(nameStartsWith).collect { result ->
            result.onSuccess { emit(eitherSuccess(it)) }
            result.onFailure { emit(eitherFailure(it)) }
        }
    }

}