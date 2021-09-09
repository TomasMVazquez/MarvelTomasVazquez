package com.applications.toms.data.repository

import com.applications.toms.data.source.LocalDataSource
import com.applications.toms.domain.Result

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to favorite & detail fragment)
 */
class FavoriteRepository(private val localDataSource: LocalDataSource) {

    fun getCharacters(): List<Result> = localDataSource.getCharacters()
    
    fun searchCharacters(value: String): List<Result> = localDataSource.searchCharacters(value)

    fun getCharactersList(): List<Result> = localDataSource.getCharactersList()

    fun saveCharacter(character: Result){
        localDataSource.saveCharacter(character)
    }

    fun deleteCharacter(id: Long){
        localDataSource.deleteCharacter(id)
    }

}

