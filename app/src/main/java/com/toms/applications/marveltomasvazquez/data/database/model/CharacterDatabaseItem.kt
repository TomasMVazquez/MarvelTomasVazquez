package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.toms.applications.marveltomasvazquez.util.Converter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "marvel_characters_table")
@TypeConverters(Converter::class)
@Parcelize
data class CharacterDatabaseItem (
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDatabase,
    val comics: ComicsDatabase,
    val events: EventsDatabase,
    val series: SeriesDatabase,
    val stories: StoriesDatabase,
    val modified: String,
    val resourceURI: String,
    val urls: UrlsDatabase
): Parcelable {

    fun getUrlThumbnail(): String = "${thumbnail.path}.${thumbnail.extension}"

}