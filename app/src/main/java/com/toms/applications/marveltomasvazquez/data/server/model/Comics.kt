package com.toms.applications.marveltomasvazquez.data.server.model

data class Comics(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)