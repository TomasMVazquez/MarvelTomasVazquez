package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsDatabase(
    val available: Int,
    val collectionURI: String,
    val items: List<EventItemDatabase>,
    val returned: Int
): Parcelable
