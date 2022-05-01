package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

interface LocalDataSource {

    suspend fun saveCharacter(items: List<MyCharacter>): Either<EitherState, ErrorStates>
    suspend fun getCharacters(): Either<List<MyCharacter>, ErrorStates>
    suspend fun getMyFavoritesCharacters(): Either<List<MyCharacter>, ErrorStates>
    suspend fun searchCharacters(value: String): Either<List<MyCharacter>, ErrorStates>
    fun isEmpty(): Boolean
    fun getNumberSaved(): Int
    suspend fun addFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates>
    suspend fun removeFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates>

}