package com.toms.applications.marveltomasvazquez.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.database.model.asDomainModel
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.repository.LocalDataSource

/**
 * Extended from Interface to be able to replace it if necessary
 */
class RoomDataSource(database: CharacterDatabase): LocalDataSource {

    private val characterDatabaseDao = database.characterDatabaseDao

    override suspend fun saveCharacters(vararg items: CharacterDatabaseItem) {
        characterDatabaseDao.insert(*items)
    }

    override suspend fun getCharacters() = characterDatabaseDao.getCharacters().map { it.asDomainModel() }

    override suspend fun searchCharacters(value: String): LiveData<List<Character>> = characterDatabaseDao.searchCharacter(value).map{ it.asDomainModel() }

    override suspend fun getCharactersList(): List<Character>  = characterDatabaseDao.getCharactersList().map { it.asDomainModel() }

    override suspend fun deleteCharacter(id: Long) {
        characterDatabaseDao.deleteCharacter(id)
    }

}