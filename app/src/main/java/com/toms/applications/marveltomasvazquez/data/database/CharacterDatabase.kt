package com.toms.applications.marveltomasvazquez.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.toms.applications.marveltomasvazquez.data.database.model.CharacterDatabaseItem

@Database(entities = [CharacterDatabaseItem::class], version = 1, exportSchema = false)
abstract class CharacterDatabase: RoomDatabase()     {
    abstract val characterDatabaseDao: CharacterDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: CharacterDatabase? = null

        fun getInstance(context: Context): CharacterDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            CharacterDatabase::class.java,
                            "marvel_characters_table"
                    )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}