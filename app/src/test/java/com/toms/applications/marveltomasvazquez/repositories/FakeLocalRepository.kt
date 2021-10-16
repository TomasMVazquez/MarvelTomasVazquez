package com.toms.applications.marveltomasvazquez.repositories

import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDatabaseModel
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import kotlinx.coroutines.flow.*

class FakeLocalRepository(): LocalDataSource {

    private var characters: List<CharacterDatabaseItem> = emptyList()

    override fun saveCharacter(items: List<MyCharacter>) {
        characters = items.map { it.asDatabaseModel() }
    }

    override fun getCharacters(): Flow<List<MyCharacter>> = flow {
        characters.map { it.asDomainModel() }
    }

    override fun getMyFavoritesCharacters(): Flow<List<MyCharacter>>  = flow {
        characters.filter { it.isFavorite }.map { it.asDomainModel() }
    }

    override fun searchCharacters(value: String): Flow<List<MyCharacter>> {
        TODO("Not yet implemented")
    }

    override fun deleteCharacter(id: Long) {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean = characters.count() <= 0

    override fun getNumberSaved(): Int {
        TODO("Not yet implemented")
    }

    override fun addFavorite(favorite: MyCharacter) {
        val myFavorite = favorite.asDatabaseModel().copy(isFavorite = true)
        characters.map { if (it.marvelId == myFavorite.marvelId) it.isFavorite = true }
    }

    override fun removeFavorite(favorite: MyCharacter) {
        val myFavorite = favorite.asDatabaseModel().copy(isFavorite = true)
        characters.map { if (it.marvelId == myFavorite.marvelId) it.isFavorite = false }
    }

}