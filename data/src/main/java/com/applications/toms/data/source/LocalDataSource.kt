package com.applications.toms.data.source

import com.applications.toms.domain.MyCharacter
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun saveCharacter(items: List<MyCharacter>)
    fun getCharacters(): Flow<List<MyCharacter>>
    fun getMyFavoritesCharacters(): Flow<List<MyCharacter>>
    fun searchCharacters(value: String): Flow<List<MyCharacter>>
    fun deleteCharacter(id: Long)
    fun isEmpty(): Boolean
    fun getNumberSaved(): Int
    fun addFavorite(favorite: MyCharacter)
    fun removeFavorite(favorite: MyCharacter)

}