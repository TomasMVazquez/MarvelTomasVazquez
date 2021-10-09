package com.toms.applications.marveltomasvazquez.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.data.database.model.asDomainModel
import com.toms.applications.marveltomasvazquez.repository.LocalDataSource

class FakeLocalRepository(
    private var _characters: MutableLiveData<List<CharacterDatabaseItem>> = MutableLiveData<List<CharacterDatabaseItem>>()
): LocalDataSource {

    private val characters: LiveData<List<CharacterDatabaseItem>> get() = _characters

    override suspend fun saveCharacters(vararg items: CharacterDatabaseItem) {
        items.map {
            _characters.value?.toMutableList()?.add(it)
        }
    }

    override suspend fun getCharacters(): LiveData<List<Character>> {
        return characters.map { it.asDomainModel() }
    }

    override suspend fun searchCharacters(value: String): LiveData<List<Character>> {
        return characters.map { characters ->
            characters.filter { character ->
                character.name.contains(value)
            }
        }.map { it.asDomainModel() }
    }

    override suspend fun getCharactersList(): List<Character> {
        return characters.value?.asDomainModel() ?: emptyList()
    }

    override suspend fun deleteCharacter(id: Long) {
        val characterToDelete = characters.value?.find { it.id == id.toInt() } ?: return
        _characters.value?.toMutableList()?.remove(characterToDelete)
    }
}