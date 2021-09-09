package com.toms.applications.marveltomasvazquez.data.server

import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.CharactersContainer
import com.toms.applications.marveltomasvazquez.util.md5Hash

class ServerDataSource: RemoteDataSource {

    private val ts = System.currentTimeMillis()
    private val hash = (ts.toString() + PRIVATE_KEY + PUBLIC_KEY).md5Hash()
    /**
     * Get data from Api Network
     */
    //TODO Add fiter of order
    override suspend fun getCharacters(limit: Int,offset: Int): CharactersContainer =
        MarvelApiService.retrofitService.getCharacters("name",limit,offset,ts.toString(),
            PUBLIC_KEY,hash)

    /**
     * Get data filtering by name
     */
    override suspend fun getCharactersByNameSearch(nameStartsWith: String): CharactersContainer =
        MarvelApiService.retrofitService.getCharactersByNameSearch(nameStartsWith,"name",100,ts.toString(),
            PUBLIC_KEY,hash)

}