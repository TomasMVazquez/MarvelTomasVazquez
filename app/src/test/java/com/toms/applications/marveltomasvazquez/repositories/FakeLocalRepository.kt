package com.toms.applications.marveltomasvazquez.repositories

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import kotlinx.coroutines.flow.*

class FakeLocalRepository(): LocalDataSource {

    private var characters: List<CharacterDatabaseItem> = emptyList()

    override suspend fun saveCharacter(items: List<MyCharacter>): Either<EitherState, ErrorStates> {
        characters = items.map { it.asDatabaseModel() }
        return eitherSuccess(EitherState.SUCCESS)
    }

    override suspend fun getCharacters(): Either<List<MyCharacter>, ErrorStates> {
        return if (characters.isEmpty())
            eitherFailure(ErrorStates.EMPTY)
        else
            eitherSuccess(characters.map { it.asDomainModel() })
    }

    override suspend fun getMyFavoritesCharacters(): Either<List<MyCharacter>, ErrorStates> {
        val favorites = characters.filter { it.isFavorite }.map { it.asDomainModel() }
        return if (favorites.isEmpty()) eitherFailure(ErrorStates.EMPTY)
        else eitherSuccess(favorites)
    }

    override suspend fun searchCharacters(value: String): Either<List<MyCharacter>, ErrorStates> {
        return eitherSuccess(characters.filter { it.name.startsWith(value) }.map { it.asDomainModel() })
    }

    override fun isEmpty(): Boolean = characters.isEmpty()

    override fun getNumberSaved(): Int = characters.size

    override suspend fun addFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates> {
        val myFavorite = favorite.asDatabaseModel().copy(isFavorite = true)
        characters.map { if (it.marvelId == myFavorite.marvelId) it.isFavorite = true }
        return eitherSuccess(EitherState.SUCCESS)
    }

    override suspend fun removeFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates> {
        val myFavorite = favorite.asDatabaseModel().copy(isFavorite = true)
        characters.map { if (it.marvelId == myFavorite.marvelId) it.isFavorite = false }
        return eitherSuccess(EitherState.SUCCESS)
    }


}