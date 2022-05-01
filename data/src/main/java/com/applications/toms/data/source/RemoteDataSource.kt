package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter

interface RemoteDataSource {

    suspend fun getCharacters(limit: Int, offset: Int): Either<List<MyCharacter>, ErrorStates>

    suspend fun getCharactersByNameSearch(nameStartsWith: String): Either<List<MyCharacter>, ErrorStates>
}