package com.applications.toms.data.repository

import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.MyCharacter
import kotlinx.coroutines.flow.Flow

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to favorite & detail fragment)
 */
class FavoriteRepository(private val localDataSource: LocalDataSource) {

    fun getCharacters(): Flow<List<MyCharacter>> = localDataSource.getMyFavoritesCharacters()
    
    fun searchCharacters(value: String): Flow<List<MyCharacter>> = localDataSource.searchCharacters(value)

    fun addToFavorites(character: MyCharacter){
        localDataSource.addFavorite(character)
    }

    fun removeFromFavorites(character: MyCharacter){
        localDataSource.removeFavorite(character)
    }

}

