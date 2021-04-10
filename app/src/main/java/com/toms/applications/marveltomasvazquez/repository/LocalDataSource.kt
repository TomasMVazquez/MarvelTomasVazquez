package com.toms.applications.marveltomasvazquez.repository

import androidx.lifecycle.LiveData
import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.domain.Character

interface LocalDataSource {

    suspend fun saveCharacters(vararg items: CharacterDatabaseItem)
    suspend fun getCharacters(): LiveData<List<Character>>
    suspend fun searchCharacters(value: String): LiveData<List<Character>>
    suspend fun getCharactersList(): List<Character>
    suspend fun deleteCharacter(id: Long)

}