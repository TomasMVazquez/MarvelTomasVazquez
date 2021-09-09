package com.applications.toms.data.repository

import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.CharactersContainer

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to home fragment)
 */
class CharactersRepository(private val remoteDataSource: RemoteDataSource) {

    //TODO Change to FLOW
    suspend fun fetchCharacters(offset: Int): CharactersContainer = remoteDataSource.getCharacters(
        NETWORK_LIMIT_CHARACTERS, offset)

    // If user reach the total items returned then we need to fetch more
    suspend fun getCharacters(lastVisible: Int, size: Int): CharactersContainer {
        return if (lastVisible >= size - THRESHOLD_SIZE)
            fetchCharacters(size)
        else
            fetchCharacters(0)
    }

    companion object{
        const val NETWORK_LIMIT_CHARACTERS = 100 // API limit of 100
        const val THRESHOLD_SIZE = 25
    }
}

