package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventItemDatabase(
    val name: String,
    val resourceURI: String
): Parcelable
