package com.toms.applications.marveltomasvazquez.network.model

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)

