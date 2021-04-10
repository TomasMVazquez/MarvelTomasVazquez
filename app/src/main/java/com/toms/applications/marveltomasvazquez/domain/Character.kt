package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: Comics,
    val events: Events,
    val series: Series,
    val stories: Stories
): Parcelable {

    fun getUrlThumbnail(): String = "${thumbnail.path}.${thumbnail.extension}"

}

fun Character.asDatabaseModel(): CharacterDatabaseItem{
    return CharacterDatabaseItem(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnail.asDatabaseModel(),
        comics = comics.asDatabaseModel(),
        events = events.asDatabaseModel(),
        series = series.asDatabaseModel(),
        stories = stories.asDatabaseModel()
    )
}
