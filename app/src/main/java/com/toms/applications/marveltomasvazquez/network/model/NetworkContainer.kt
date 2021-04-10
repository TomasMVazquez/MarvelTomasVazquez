package com.toms.applications.marveltomasvazquez.network.model

data class NetworkContainer(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: Data,
    val etag: String,
    val status: String
)