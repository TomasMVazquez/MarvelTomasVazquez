package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ThumbnailDatabase(
    val extension: String,
    val path: String
): Parcelable
