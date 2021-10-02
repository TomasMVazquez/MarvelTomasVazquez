package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SeriesDatabase(
    val available: Int,
    val collectionURI: String,
    val items: List<SerieItemDatabase>,
    val returned: Int
): Parcelable
