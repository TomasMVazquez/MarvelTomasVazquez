package com.toms.applications.marveltomasvazquez.repository

import com.toms.applications.marveltomasvazquez.network.model.NetworkContainer

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to home fragment)
 */
class CharactersRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(offset: Int): NetworkContainer = remoteDataSource.getCharacters(NETWORK_LIMIT_CHARACTERS, offset)

    // If user reach the total items returned then we need to fetch more
    suspend fun checkRequireGetMoreCharacters(lastVisible: Int, size: Int): NetworkContainer? {
        if (lastVisible >= size - THRESHOLD_SIZE){
            return getCharacters(size)
        }
        return null
    }

    companion object{
        const val NETWORK_LIMIT_CHARACTERS = 100 // API limit of 100
        const val THRESHOLD_SIZE = 25
    }
}

