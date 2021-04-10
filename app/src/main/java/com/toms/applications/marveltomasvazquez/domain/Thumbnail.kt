package com.toms.applications.marveltomasvazquez.domain

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.database.model.ThumbnailDatabase
import kotlinx.parcelize.Parcelize

@Parcelize
data class Thumbnail(
    val extension: String,
    val path: String
): Parcelable

fun Thumbnail.asDatabaseModel(): ThumbnailDatabase {
    return ThumbnailDatabase(
        extension = extension,
        path = path
    )
}