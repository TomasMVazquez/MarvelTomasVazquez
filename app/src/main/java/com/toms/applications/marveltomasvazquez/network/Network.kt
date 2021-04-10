package com.toms.applications.marveltomasvazquez.network

import com.toms.applications.marveltomasvazquez.network.model.NetworkContainer
import com.toms.applications.marveltomasvazquez.repository.RemoteDataSource
import com.toms.applications.marveltomasvazquez.util.md5Hash

class Network: RemoteDataSource {

    /**
     * Get data from Api Network
     */
    override suspend fun getCharacters(limit: Int,offset: Int): NetworkContainer {
        val ts = System.currentTimeMillis()
        val hash = (ts.toString() + PRIVATE_KEY + PUBLIC_KEY).md5Hash()
        return MarvelApiService.retrofitService.getCharacters("name",limit,offset,ts.toString(),PUBLIC_KEY,hash)
    }

    /**
     * Get data filtering by name
     */
    override suspend fun getCharactersByNameSearch(nameStartsWith: String): NetworkContainer {
        val ts = System.currentTimeMillis()
        val hash = (ts.toString() + PRIVATE_KEY + PUBLIC_KEY).md5Hash()
        return MarvelApiService.retrofitService.getCharactersByNameSearch(nameStartsWith,"name",100,ts.toString(),PUBLIC_KEY,hash)
    }

}