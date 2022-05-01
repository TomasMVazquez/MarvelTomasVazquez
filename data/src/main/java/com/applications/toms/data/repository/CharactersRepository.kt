package com.applications.toms.data.repository

import com.applications.toms.data.*
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to home fragment)
 */
class CharactersRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    // If user reach the total items returned then we need to fetch more
    suspend fun getCharacters(offset: Int): Either<List<MyCharacter>, ErrorStates> =
        if (localDataSource.isEmpty() || offset > (localDataSource.getNumberSaved() - THRESHOLD_SIZE)) {
            fetchCharacters(offset)
                .onSuccess {
                    localDataSource.saveCharacter(it)
                        .onSuccess {
                            localDataSource.getCharacters()
                                .onSuccess { success -> eitherSuccess(success) }
                                .onFailure { fail -> eitherFailure(fail) }
                        }
                        .onFailure { fail -> eitherFailure(fail) }
                }
                .onFailure { eitherFailure(it) }
        } else {
            eitherFailure(ErrorStates.EMPTY)
        }


    suspend fun fetchCharacters(offset: Int): Either<List<MyCharacter>, ErrorStates> =
        remoteDataSource.getCharacters(NETWORK_LIMIT_CHARACTERS, offset)
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }

    companion object {
        const val NETWORK_LIMIT_CHARACTERS = 100 // API limit of 100
        const val THRESHOLD_SIZE = 25
    }
}

