package com.toms.applications.marveltomasvazquez.network.model

import com.toms.applications.marveltomasvazquez.database.model.ThumbnailDatabase
import com.toms.applications.marveltomasvazquez.domain.Thumbnail as ThumbnailDomain

data class Thumbnail(
    val extension: String,
    val path: String
)

fun Thumbnail.asDatabaseModel(): ThumbnailDatabase{
    return  ThumbnailDatabase(
        extension = extension,
        path = path
    )
}

fun Thumbnail.asDomainModel(): ThumbnailDomain{
    return  ThumbnailDomain(
        extension = extension,
        path = path
    )
}