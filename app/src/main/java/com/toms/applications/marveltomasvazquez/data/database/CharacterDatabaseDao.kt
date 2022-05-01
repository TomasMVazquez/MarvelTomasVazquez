package com.toms.applications.marveltomasvazquez.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem

@Dao
interface CharacterDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg items: CharacterDatabaseItem): List<Long>

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharacters(): List<CharacterDatabaseItem>

    @Query("SELECT * FROM marvel_characters_table WHERE name LIKE '%' || :value || '%'")
    fun searchCharacter(value: String): List<CharacterDatabaseItem>

    @Query("SELECT COUNT(id) FROM  marvel_characters_table")
    fun charactersCount(): Int

    @Query("UPDATE marvel_characters_table SET isFavorite = :favorite WHERE marvelId = :marvelId")
    fun changeCharacterFavorite(favorite: Boolean, marvelId: Int): Int

}