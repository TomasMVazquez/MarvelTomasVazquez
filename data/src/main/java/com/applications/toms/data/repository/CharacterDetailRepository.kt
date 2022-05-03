package com.applications.toms.data.repository

import com.applications.toms.data.Either
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

class CharacterDetailRepository(
    private val remoteDataSource: RemoteDataSource
) {

    suspend fun getCharacterDetail(characterId: String): Either<List<MyCharacter>, ErrorStates> =
        remoteDataSource.getCharacterDetail(characterId)

}
