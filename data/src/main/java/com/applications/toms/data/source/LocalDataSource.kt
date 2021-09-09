package com.applications.toms.data.source

import com.applications.toms.domain.Result

interface LocalDataSource {

    fun saveCharacter(items: Result)
    fun getCharacters(): List<Result>
    fun searchCharacters(value: String): List<Result>
    fun getCharactersList(): List<Result>
    fun deleteCharacter(id: Long)

}