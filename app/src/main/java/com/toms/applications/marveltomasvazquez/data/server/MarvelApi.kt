package com.toms.applications.marveltomasvazquez.data.server

import com.toms.applications.marveltomasvazquez.data.server.model.CharactersContainer
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface MarvelApi {

    @GET("/v1/public/characters")
    fun getCharactersAsync(
        @Query("orderBy")orderBy: String,
        @Query("limit")limit: Int,
        @Query("offset")offset: Int,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String,
        @Query("hash")hash: String
    ): Deferred<Response<CharactersContainer>>

    @GET("/v1/public/characters")
    fun getCharactersByNameSearchAsync(
        @Query("nameStartsWith")nameStartsWith: String,
        @Query("orderBy")orderBy: String,
        @Query("limit")limit: Int,
        @Query("ts")ts: String,
        @Query("apikey")apiKey: String,
        @Query("hash")hash: String
    ): Deferred<Response<CharactersContainer>>

}