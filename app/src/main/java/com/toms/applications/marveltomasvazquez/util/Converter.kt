package com.toms.applications.marveltomasvazquez.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.toms.applications.marveltomasvazquez.data.database.model.*

/**
 * Allows to save classes into Local database
 */
class Converter {

    @TypeConverter
    fun thumbnailToJson(value: ThumbnailDatabase): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToThumbnail(value: String): ThumbnailDatabase = Gson().fromJson(value, ThumbnailDatabase::class.java)

    @TypeConverter
    fun comicsToJson(value: ComicsDatabase): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToComics(value: String): ComicsDatabase = Gson().fromJson(value, ComicsDatabase::class.java)

    @TypeConverter
    fun eventsToJson(value: EventsDatabase?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToEvents(value: String): EventsDatabase = Gson().fromJson(value, EventsDatabase::class.java)

    @TypeConverter
    fun seriesToJson(value: SeriesDatabase?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToSeries(value: String): SeriesDatabase = Gson().fromJson(value, SeriesDatabase::class.java)

    @TypeConverter
    fun storiesToJson(value: StoriesDatabase?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStories(value: String): StoriesDatabase = Gson().fromJson(value, StoriesDatabase::class.java)

    @TypeConverter
    fun urlToJson(value: UrlsDatabase?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToUrl(value: String): UrlsDatabase = Gson().fromJson(value, UrlsDatabase::class.java)
}