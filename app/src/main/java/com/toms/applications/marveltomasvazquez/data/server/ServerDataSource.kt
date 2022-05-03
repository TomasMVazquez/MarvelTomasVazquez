package com.toms.applications.marveltomasvazquez.data.server

import com.applications.toms.data.Either
import com.applications.toms.data.eitherFailure
import com.applications.toms.data.eitherSuccess
import com.applications.toms.data.source.RemoteDataSource
import com.applications.toms.domain.ErrorStates
import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.asDomainModel
import com.toms.applications.marveltomasvazquez.util.md5Hash
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ServerDataSource : RemoteDataSource {

    private val ts = System.currentTimeMillis()
    private val hash = (ts.toString() + PRIVATE_KEY + PUBLIC_KEY).md5Hash()

    /**
     * Get data from Api Network
     */
    override suspend fun getCharacters(
        limit: Int,
        offset: Int
    ): Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            try {
                val getCharacters =
                    MarvelApiService.retrofitService.getCharactersAsync(
                        "name", limit, offset, ts.toString(), PUBLIC_KEY, hash
                    ).await()
                if (getCharacters.isSuccessful)
                    eitherSuccess(getCharacters.body()?.asDomainModel() ?: emptyList())
                else
                    eitherFailure(ErrorStates.SERVER)
            } catch (t: Throwable) {
                eitherFailure(ErrorStates.THROWABLE)
            }
        }


    /**
     * Get data filtering by name
     */
    override suspend fun getCharactersByNameSearch(nameStartsWith: String):
            Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            try {
                val getCharacters =
                    MarvelApiService.retrofitService.getCharactersByNameSearchAsync(
                        nameStartsWith, "name", 100, ts.toString(), PUBLIC_KEY, hash
                    ).await()
                if (getCharacters.isSuccessful)
                    eitherSuccess(getCharacters.body()?.asDomainModel() ?: emptyList())
                else
                    eitherFailure(ErrorStates.SERVER)
            } catch (t: Throwable) {
                eitherFailure(ErrorStates.THROWABLE)
            }
        }

    /**
     * Get detail
     */
    override suspend fun getCharacterDetail(characterId: String): Either<List<MyCharacter>, ErrorStates> =
        withContext(Dispatchers.IO) {
            try {
                val getCharacters =
                    MarvelApiService.retrofitService.getCharacterDetailAsync(
                        characterId,
                        ts.toString(),
                        PUBLIC_KEY,
                        hash
                    ).await()
                if (getCharacters.isSuccessful)
                    eitherSuccess(getCharacters.body()?.asDomainModel() ?: emptyList())
                else
                    eitherFailure(ErrorStates.SERVER)
            } catch (t: Throwable) {
                eitherFailure(ErrorStates.THROWABLE)
            }
        }

}