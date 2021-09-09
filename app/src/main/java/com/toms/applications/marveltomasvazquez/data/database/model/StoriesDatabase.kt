package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoriesDatabase(
    val available: Int,
    val collectionURI: String,
    val items: List<StoryItemDatabase>,
    val returned: Int
): Parcelable
