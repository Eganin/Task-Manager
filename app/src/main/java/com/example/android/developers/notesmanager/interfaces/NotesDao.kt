package com.example.android.developers.notesmanager.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.android.developers.notesmanager.common.Note

// объект доступа к данным
@Dao
interface NotesDao {
    // выполняется при определенном запросе
    @Query("SELECT * FROM notes ORDER BY dayOfWeek ASC")
    fun getAllNotes(): List<Note>

    // выполняется при вставке данных
    @Insert
    fun insertNote(note: Note)

    // выполняется при удалении
    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM notes")
    fun deleteAllNotes()

}