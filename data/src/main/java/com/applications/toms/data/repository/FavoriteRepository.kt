package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to favorite & detail fragment)
 */
class FavoriteRepository(
    private val localDataSource: LocalDataSource
) {

    suspend fun getCharacters(): Either<List<MyCharacter>, ErrorStates> =
        localDataSource.getMyFavoritesCharacters()

    suspend fun searchCharacters(value: String): Either<List<MyCharacter>, ErrorStates> =
        localDataSource.searchCharacters(value)

    suspend fun addToFavorites(character: MyCharacter): Either<EitherState, ErrorStates> =
        localDataSource.addFavorite(character)

    suspend fun removeFromFavorites(character: MyCharacter): Either<EitherState, ErrorStates> =
        localDataSource.removeFavorite(character)

}

