package com.toms.applications.marveltomasvazquez.data.database

import androidx.lifecycle.map
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.Result
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.*

class RoomDataSource(db: CharacterDatabase): LocalDataSource {

    private val characterDao = db.characterDatabaseDao

    override fun saveCharacter(items: Result) {
        characterDao.insert(items.asDatabaseModel())
    }

    override fun getCharacters(): List<Result> {
        return characterDao.getCharacters().map {
            it.map { characterDatabaseItem ->
                characterDatabaseItem.asDomainModel()
            }
        }.value ?: emptyList()
    }

    override fun searchCharacters(value: String): List<Result> {
        return characterDao.searchCharacter(value).map{ list ->
            list.map { it.asDomainModel() }
        }.value ?: emptyList()
    }

    override fun getCharactersList(): List<Result> {
        return characterDao.getCharactersList().map { it.asDomainModel() }
    }

    override fun deleteCharacter(id: Long) {
        characterDao.deleteCharacter(id)
    }
}