package com.example.android.developers.notesmanager.DBHelper

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import com.example.android.developers.notesmanager.activites.MainActivity
import com.example.android.developers.notesmanager.common.Note


// на AndroidViewModel не действуют методы из цикла activity
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val database = NotesDatabase.getInstance(getApplication())

    var notes = if (MainActivity.sortedSettingPosition == 0) {
        database?.notesDao()?.getAllNotes()
    } else if (MainActivity.sortedSettingPosition == 1) {
        database?.notesDao()?.getAllNotesDESC()
    } else if (MainActivity.sortedSettingPosition == 2) {
        database?.notesDao()?.getAllNotesORDERPriority()
    } else {
        database?.notesDao()?.getAllNotesORDERPriorityDESC()
    }

    fun insertNote(note: Note) {
        InsertTask().execute(note)
    }

    fun deleteNote(note: Note) {
        DeleteTask().execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllTask().execute()
    }

    // async task  -для вставки данных из LiveData в Room-DB
    inner class InsertTask : AsyncTask<Note, Void, Void>() {
        override fun doInBackground(vararg params: Note): Void? {
            if (params.isNotEmpty()) {
                database?.notesDao()?.insertNote(params[0])
            }

            return null
        }
    }

    // async task  -для удаления данных из  Room-DB
    inner class DeleteTask : AsyncTask<Note, Void, Void>() {
        override fun doInBackground(vararg params: Note): Void? {
            if (params.isNotEmpty()) {
                database?.notesDao()?.deleteNote(params[0])
            }

            return null
        }
    }

    // async task  -для удаления всех данных из  Room-DB
    inner class DeleteAllTask : AsyncTask<Note, Void, Void>() {
        override fun doInBackground(vararg params: Note): Void? {
            database?.notesDao()?.deleteAllNotes()

            return null
        }
    }


}