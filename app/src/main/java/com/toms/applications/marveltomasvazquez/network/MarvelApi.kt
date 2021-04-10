package com.toms.applications.marveltomasvazquez.network

import com.toms.applications.marveltomasvazquez.network.model.NetworkContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("orderBy")orderBy: String,
        @Query("limit")limit: Int,
        @Query("offset")offset: Int,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String,
        @Query("hash")hash: String
    ): NetworkContainer

    @GET("/v1/public/characters")
    suspend fun getCharactersByNameSearch(
        @Query("nameStartsWith")nameStartsWith: String,
        @Query("orderBy")orderBy: String,
        @Query("limit")limit: Int,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String,
        @Query("hash")hash: String
    ): NetworkContainer


}