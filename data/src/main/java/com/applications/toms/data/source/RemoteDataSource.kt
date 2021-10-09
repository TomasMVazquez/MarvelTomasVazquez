package com.applications.toms.data.source

import com.applications.toms.data.Either
import com.applications.toms.domain.MyCharacter
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getCharacters(limit: Int,offset: Int): Flow<Either<List<MyCharacter>,String>>

    suspend fun getCharactersByNameSearch(nameStartsWith:String): Flow<Either<List<MyCharacter>,String>>
}