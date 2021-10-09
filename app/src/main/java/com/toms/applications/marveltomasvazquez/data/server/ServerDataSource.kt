package com.toms.applications.marveltomasvazquez.data.server

import com.applications.toms.data.Either
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.util.md5Hash
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class ServerDataSource: RemoteDataSource {

    private val ts = System.currentTimeMillis()
    private val hash = (ts.toString() + PRIVATE_KEY + PUBLIC_KEY).md5Hash()
    /**
     * Get data from Api Network
     */
    //TODO Add fiter of order
    override suspend fun getCharacters(limit: Int, offset: Int): Flow<Either<List<MyCharacter>,String>>
    = flow {
        try {
            val getCharacters =
                MarvelApiService.retrofitService.getCharactersAsync(
                    "name",limit,offset,ts.toString(), PUBLIC_KEY,hash).await()
            if (getCharacters.isSuccessful)
                getCharacters.body()?.let { emit(eitherSuccess(it.asDomainModel())) }
            else
                emit(eitherFailure(getCharacters.code().toString()))
        }catch (e: Exception){
            e.message?.let { emit(eitherFailure(it)) }
        }
    }

    /**
     * Get data filtering by name
     */
    override suspend fun getCharactersByNameSearch(nameStartsWith: String): Flow<Either<List<MyCharacter>,String>>
    = flow {
        try {
            val getCharacters =
                MarvelApiService.retrofitService.getCharactersByNameSearchAsync(
                    nameStartsWith,"name",100,ts.toString(), PUBLIC_KEY,hash).await()
            if (getCharacters.isSuccessful)
                getCharacters.body()?.let { emit(eitherSuccess(it.asDomainModel())) }
            else
                emit(eitherFailure(getCharacters.code().toString()))
        }catch (e: Exception){
            e.message?.let { emit(eitherFailure(it)) }
        }
    }

}