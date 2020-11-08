package com.example.android.developers.notesmanager.DBHelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.android.developers.notesmanager.contacts.NoteContract

class NotesDBHelper(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(NoteContract().NotesEntry().CREATE_COMMAND)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(NoteContract().NotesEntry().DROP_COMMAND)
        onCreate(db=db)
    }
}