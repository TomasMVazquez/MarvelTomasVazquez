package com.toms.applications.marveltomasvazquez.repository

import androidx.lifecycle.LiveData
import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.domain.Character

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to favorite & detail fragment)
 */
class FavoriteRepository(private val localDataSource: LocalDataSource) {

    suspend fun getCharacters(): LiveData<List<Character>> = localDataSource.getCharacters()
    
    suspend fun searchCharacters(value: String): LiveData<List<Character>> = localDataSource.searchCharacters(value)

    suspend fun getCharactersList(): List<Character> = localDataSource.getCharactersList()

    suspend fun saveCharacter(character: CharacterDatabaseItem){
        localDataSource.saveCharacters(character)
    }

    suspend fun deleteCharacter(id: Long){
        localDataSource.deleteCharacter(id)
    }

}

