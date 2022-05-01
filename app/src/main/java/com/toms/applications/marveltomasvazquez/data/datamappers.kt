package com.toms.applications.marveltomasvazquez.data

import com.applications.toms.domain.MyCharacter
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import com.toms.applications.marveltomasvazquez.data.server.model.CharactersContainer

fun CharactersContainer.asDomainModel(): List<MyCharacter> {
    return data.results.map {
        MyCharacter(
            id = it.id,
            name = it.name,
            thumbnail = "${it.thumbnail.path}.${it.thumbnail.extension}",
            description = it.description,
            comics = it.comics.available,
            series = it.series.available,
            stories = it.stories.available,
            events = it.events.available,
        )
    }
}

fun MyCharacter.asDatabaseModel(): CharacterDatabaseItem {
    return CharacterDatabaseItem(
        id = 0,
        marvelId = id,
        name = name,
        thumbnail = thumbnail,
        description = description,
        comics = comics,
        series = series,
        stories = stories,
        events = events,
    )
}

fun CharacterDatabaseItem.asDomainModel(): MyCharacter {
    return MyCharacter(
        id = marvelId,
        name = name,
        thumbnail = thumbnail,
        description = description,
        comics = comics,
        series = series,
        stories = stories,
        events = events,
    )
}