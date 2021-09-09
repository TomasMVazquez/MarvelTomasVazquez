package com.toms.applications.marveltomasvazquez.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem

@Dao
interface CharacterDatabaseDao {
//TODO ADD FLOW
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: CharacterDatabaseItem)

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharacters(): LiveData<List<CharacterDatabaseItem>>

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharactersList(): List<CharacterDatabaseItem>

    @Query("SELECT * FROM marvel_characters_table WHERE name LIKE '%' || :value || '%'")
    fun searchCharacter(value: String): LiveData<List<CharacterDatabaseItem>>

    @Query("DELETE FROM marvel_characters_table WHERE id = :id")
    fun deleteCharacter(id: Long)
}