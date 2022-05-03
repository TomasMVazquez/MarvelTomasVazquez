package com.toms.applications.marveltomasvazquez.repositories

import com.applications.toms.data.Either
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.applications.toms.testshared.listOfMocks

class FakeRemoteRepository: RemoteDataSource {

    override suspend fun getCharacters(
        limit: Int,
        offset: Int
    ): Either<List<MyCharacter>, ErrorStates> =
        eitherSuccess(listOfMocks)

    override suspend fun getCharactersByNameSearch(nameStartsWith: String): Either<List<MyCharacter>, ErrorStates>  =
        eitherSuccess(listOfMocks)

    override suspend fun getCharacterDetail(characterId: String): Either<List<MyCharacter>, ErrorStates> =
        eitherSuccess(listOfMocks.filter { it.id.toString() == characterId })
}