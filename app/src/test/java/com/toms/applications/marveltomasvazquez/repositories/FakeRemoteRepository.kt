package com.toms.applications.marveltomasvazquez.repositories

import com.toms.applications.marveltomasvazquez.network.model.Data
import com.toms.applications.marveltomasvazquez.network.model.NetworkContainer
import com.toms.applications.marveltomasvazquez.repository.RemoteDataSource

class FakeRemoteRepository: RemoteDataSource {

    override suspend fun getCharacters(limit: Int, offset: Int): NetworkContainer =
        NetworkContainer(
            "",
            "",
            0,"",
            Data(0,0,0, emptyList(),0),
            "",
            ""
        )

    override suspend fun getCharactersByNameSearch(nameStartsWith: String): NetworkContainer =
        NetworkContainer(
            "",
            "",
            0,"",
            Data(0,0,0, emptyList(),0),
            "",
            ""
        )
}