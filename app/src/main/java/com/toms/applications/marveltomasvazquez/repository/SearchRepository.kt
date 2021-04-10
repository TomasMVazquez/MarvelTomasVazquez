package com.toms.applications.marveltomasvazquez.repository

/**
 * Extended from Interface to be able to replace it if necessary
 * (This repository belongs to search fragment)
 */
class SearchRepository(private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacters(nameStartsWith: String) = remoteDataSource.getCharactersByNameSearch(nameStartsWith)

}