package com.toms.applications.marveltomasvazquez.data.database

import androidx.lifecycle.map
import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.Result
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomDataSource(db: CharacterDatabase): LocalDataSource {

    private val characterDao = db.characterDatabaseDao

    override fun saveCharacter(items: Result) {
        characterDao.insert(items.asDatabaseModel())
    }

    override fun getCharacters(): Flow<List<Result>> {
        return characterDao.getCharacters().map { characters ->
            characters.map { it.asDomainModel() } }
    }

    override fun searchCharacters(value: String): Flow<List<Result>> {
        return characterDao.searchCharacter(value).map { characters ->
            characters.map { it.asDomainModel() }
        }
    }

    override fun deleteCharacter(id: Long) {
        characterDao.deleteCharacter(id)
    }
}