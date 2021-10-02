package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UrlsDatabase(
    val items: List<UrlDatabase>
): Parcelable

@Parcelize
data class UrlDatabase(
    val type: String,
    val url: String
): Parcelable
