package com.toms.applications.marveltomasvazquez.repository

import com.toms.applications.marveltomasvazquez.network.model.NetworkContainer

interface RemoteDataSource {

    suspend fun getCharacters(limit: Int,offset: Int): NetworkContainer

    suspend fun getCharactersByNameSearch(nameStartsWith:String): NetworkContainer
}