package com.applications.toms.domain

data class MyCharacter (
    val id: Int,
    val name: String,
    val thumbnail: String,
    val description: String,
    val comics: Int,
    val series: Int,
    val stories: Int,
    val events: Int
)