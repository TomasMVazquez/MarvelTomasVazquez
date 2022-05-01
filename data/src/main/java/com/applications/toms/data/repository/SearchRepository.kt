package com.applications.toms.data.repository

import com.applications.toms.data.*
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to search fragment)
 */
class SearchRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCharacters(nameStartsWith: String): Either<List<MyCharacter>, ErrorStates> =
        remoteDataSource.getCharactersByNameSearch(nameStartsWith)
            .onSuccess { eitherSuccess(it) }
            .onFailure { eitherFailure(it) }

}
