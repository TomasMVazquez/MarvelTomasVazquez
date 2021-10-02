package com.toms.applications.marveltomasvazquez.data.database

import androidx.room.*
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg items: CharacterDatabaseItem)

    @Query("SELECT * FROM marvel_characters_table ORDER BY name")
    fun getCharacters(): Flow<List<CharacterDatabaseItem>>

    @Query("SELECT * FROM marvel_characters_table WHERE name LIKE '%' || :value || '%'")
    fun searchCharacter(value: String): Flow<List<CharacterDatabaseItem>>

    @Query("DELETE FROM marvel_characters_table WHERE id = :id")
    fun deleteCharacter(id: Long)
}