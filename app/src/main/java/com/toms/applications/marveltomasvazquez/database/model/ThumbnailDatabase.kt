package com.toms.applications.marveltomasvazquez.database.model

import android.os.Parcelable
import com.toms.applications.marveltomasvazquez.domain.Thumbnail
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailDatabase(
    val extension: String,
    val path: String
): Parcelable


fun ThumbnailDatabase.asDomainModel(): Thumbnail {
    return Thumbnail(
        extension = extension,
        path = path
    )
}