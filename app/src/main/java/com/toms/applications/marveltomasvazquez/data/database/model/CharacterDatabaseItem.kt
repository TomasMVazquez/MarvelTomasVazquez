package com.toms.applications.marveltomasvazquez.data.database.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "marvel_characters_table")
@Parcelize
data class CharacterDatabaseItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val marvelId: Int,
    val name: String,
    val thumbnail: String,
    val description: String,
    val comics: Int,
    val series: Int,
    val stories: Int,
    val events: Int,
    var isFavorite: Boolean = false
): Parcelable