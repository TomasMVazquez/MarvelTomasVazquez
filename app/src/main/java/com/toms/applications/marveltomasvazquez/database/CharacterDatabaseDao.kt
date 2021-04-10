package com.toms.applications.marveltomasvazquez.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.toms.applications.marveltomasvazquez.database.model.CharacterDatabaseItem

@Dao
interface CharacterDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg items: CharacterDatabaseItem)

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharacters(): LiveData<List<CharacterDatabaseItem>>

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharactersList(): List<CharacterDatabaseItem>

    @Query("SELECT * FROM marvel_characters_table WHERE name LIKE '%' || :value || '%'")
    fun searchCharacter(value: String): LiveData<List<CharacterDatabaseItem>>

    @Query("DELETE FROM marvel_characters_table WHERE id = :id")
    suspend fun deleteCharacter(id: Long)
}