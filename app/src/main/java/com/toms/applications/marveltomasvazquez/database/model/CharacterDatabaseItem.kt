package com.toms.applications.marveltomasvazquez.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.toms.applications.marveltomasvazquez.domain.Character
import com.toms.applications.marveltomasvazquez.util.Converter

@Entity(tableName = "marvel_characters_table")
@TypeConverters(Converter::class)
data class CharacterDatabaseItem (
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDatabase,
    val comics: ComicsDatabase,
    val events: EventsDatabase,
    val series: SeriesDatabase,
    val stories: StoriesDatabase
)

fun CharacterDatabaseItem.asDomainModel(): Character{
    return Character(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.asDomainModel(),
        comics = comics.asDomainModel(),
        events = events.asDomainModel(),
        series = series.asDomainModel(),
        stories = stories.asDomainModel()
    )
}

fun List<CharacterDatabaseItem>.asDomainModel(): List<Character>{
    return map{
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