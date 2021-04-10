package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.domain.Character

data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun List<Result>.asDatabaseModel(): Array<CharacterDatabaseItem>{
    return map {
        CharacterDatabaseItem(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail.asDatabaseModel(),
            comics = it.comics.asDatabaseModel(),
            events = it.events.asDatabaseModel(),
            series = it.series.asDatabaseModel(),
            stories = it.stories.asDatabaseModel()
        )
    }.toTypedArray()
}

fun List<Result>.asDomainModel(): List<Character>{
    return map {
        Character(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail.asDomainModel(),
            comics = it.comics.asDomainModel(),
            events = it.events.asDomainModel(),
            series = it.series.asDomainModel(),
            stories = it.stories.asDomainModel()
        )
    }
}