package com.applications.toms.data.source

import com.applications.toms.domain.Result
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun saveCharacter(items: Result)
    fun getCharacters(): Flow<List<Result>>
    fun searchCharacters(value: String): Flow<List<Result>>
    fun deleteCharacter(id: Long)

}