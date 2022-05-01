package com.toms.applications.marveltomasvazquez.data.database

import com.applications.toms.data.Either
import com.applications.toms.data.EitherState
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: CharacterDatabase) : LocalDataSource {

    private val characterDao = db.characterDatabaseDao

    override suspend fun saveCharacter(items: List<MyCharacter>): Either<EitherState, ErrorStates> =
        withContext(Dispatchers.IO) {
            val response = characterDao.insert(*items.map { it.asDatabaseModel() }.toTypedArray())
            if (response.any { it < 0 }) eitherFailure(ErrorStates.NOT_SAVED)
            else eitherSuccess(EitherState.SUCCESS)
        }

    override suspend fun getCharacters(): Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            val listCharacters = characterDao.getCharacters().map { it.asDomainModel() }
            if (listCharacters.isNullOrEmpty())
                eitherFailure(ErrorStates.EMPTY)
            else
                eitherSuccess(listCharacters)
        }


    override suspend fun getMyFavoritesCharacters(): Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            val listCharacters =
                characterDao.getCharacters().filter { it.isFavorite }.map { it.asDomainModel() }
            if (listCharacters.isNullOrEmpty())
                eitherFailure(ErrorStates.EMPTY)
            else
                eitherSuccess(listCharacters)
        }

    override suspend fun searchCharacters(value: String): Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            val searchResult = characterDao.searchCharacter(value).map { it.asDomainModel() }
            if (searchResult.isNullOrEmpty())
                eitherFailure(ErrorStates.EMPTY)
            else
                eitherSuccess(searchResult)
        }

    override fun isEmpty(): Boolean = characterDao.charactersCount() <= 0

    override fun getNumberSaved(): Int = characterDao.charactersCount()

    override suspend fun addFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates> =
        withContext(Dispatchers.IO) {
            val response = characterDao.changeCharacterFavorite(true, favorite.id)
            if (response > 0) eitherSuccess(EitherState.SUCCESS)
            else eitherFailure(ErrorStates.NOT_SAVED)
        }

    override suspend fun removeFavorite(favorite: MyCharacter): Either<EitherState, ErrorStates> =
        withContext(Dispatchers.IO) {
            val response = characterDao.changeCharacterFavorite(false, favorite.id)
            if (response > 0) eitherSuccess(EitherState.SUCCESS)
            else eitherFailure(ErrorStates.NOT_SAVED)
        }
}