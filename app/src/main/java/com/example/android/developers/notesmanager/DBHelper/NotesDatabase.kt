package com.example.android.developers.notesmanager.DBHelper

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.android.developers.notesmanager.common.Note
import com.example.android.developers.notesmanager.interfaces.NotesDao

// класс отвечающий за работу с БД
@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    companion object {
        var database: NotesDatabase? = null
        private val DB_NAME: String = "notes2.db"

        fun getInstance(context: Context): NotesDatabase? {
            synchronized(NotesDatabase::class) {
                if (database == null) {
                    database =
                        Room.databaseBuilder(context.applicationContext, NotesDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                }
                return database
            }
        }

        fun destroyDataBase(){
            database=null
        }
    }
}