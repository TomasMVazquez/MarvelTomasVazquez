package com.applications.toms.data.source

import com.applications.toms.domain.CharactersContainer

interface RemoteDataSource {

    suspend fun getCharacters(limit: Int,offset: Int): CharactersContainer

    suspend fun getCharactersByNameSearch(nameStartsWith:String): CharactersContainer
}