package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ComicsDatabase(
    val available: Int,
    val collectionURI: String,
    val items: List<ComicItemDatabase>,
    val returned: Int
): Parcelable
