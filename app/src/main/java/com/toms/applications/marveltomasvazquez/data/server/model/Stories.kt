package com.toms.applications.marveltomasvazquez.data.server.model

data class Stories(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemXXX>,
    val returned: Int
)