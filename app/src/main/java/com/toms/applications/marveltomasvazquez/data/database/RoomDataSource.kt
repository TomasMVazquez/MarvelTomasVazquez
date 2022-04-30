package com.toms.applications.marveltomasvazquez.data.database

import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(db: CharacterDatabase) : LocalDataSource {

    private val characterDao = db.characterDatabaseDao

    override fun saveCharacter(items: List<MyCharacter>) {
        characterDao.insert(*items.map { it.asDatabaseModel() }.toTypedArray())
    }

    override fun getCharacters(): Flow<List<MyCharacter>> {
        return characterDao.getCharacters().map { characters ->
            characters.map { it.asDomainModel() }
        }
    }

    override fun getMyFavoritesCharacters(): Flow<List<MyCharacter>> {
        return characterDao.getCharacters().map { characters ->
            characters.filter { it.isFavorite }.map { it.asDomainModel() }
        }
    }

    override fun searchCharacters(value: String): Flow<List<MyCharacter>> {
        return characterDao.searchCharacter(value).map { characters ->
            characters.map { it.asDomainModel() }
        }
    }

    override fun deleteCharacter(id: Long) {
        characterDao.deleteCharacter(id)
    }

    override fun isEmpty(): Boolean = characterDao.charactersCount() <= 0

    override fun getNumberSaved(): Int = characterDao.charactersCount()

    override fun addFavorite(favorite: MyCharacter) {
        characterDao.changeCharacterFavorite(true, favorite.id)
    }

    override fun removeFavorite(favorite: MyCharacter) {
        characterDao.changeCharacterFavorite(false, favorite.id)
    }
}