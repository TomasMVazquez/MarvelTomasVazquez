package com.applications.toms.data.repository

import com.applications.toms.data.*
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.MyCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to home fragment)
 */
class CharactersRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource) {

    // If user reach the total items returned then we need to fetch more
    suspend fun getCharacters(offset: Int): Flow<Either<List<MyCharacter>, String>> = flow {
        if (localDataSource.isEmpty() || offset > (localDataSource.getNumberSaved() - THRESHOLD_SIZE)){
            fetchCharacters(offset).collect { result ->
               result.onSuccess { localDataSource.saveCharacter(it) }
                result.onFailure { emit(eitherFailure(it)) }
            }
        }
        localDataSource.getCharacters().collect {
            emit(eitherSuccess(it))
        }
    }

    suspend fun fetchCharacters(offset: Int): Flow<Either<List<MyCharacter>, String>> = flow {
        remoteDataSource.getCharacters(NETWORK_LIMIT_CHARACTERS, offset).collect { result ->
            result.onSuccess { emit(eitherSuccess(it)) }
            result.onFailure { emit(eitherFailure(it)) }
        }
    }

    companion object{
        const val NETWORK_LIMIT_CHARACTERS = 100 // API limit of 100
        const val THRESHOLD_SIZE = 25
    }
}

