package com.example.android.developers.notesmanager.contacts

import android.provider.BaseColumns

class NoteContract {
    inner class NotesEntry : BaseColumns {
        val _ID = BaseColumns._ID
        val TABLE_NAME = "notes"
        val COLUMN_TITLE = "title"
        val COLUMN_DESCRIPTION = "descriptions"
        val COLUMN_DAY_FO_WEEK = "day_of_week"
        val COLUMN_PRIORITY = "priority"

        val TYPE_TEXT = "TEXT"
        val TYPE_INTEGER = "INTEGER"

        val CREATE_COMMAND = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                "$_ID $TYPE_INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_TITLE $TYPE_TEXT," +
                "$COLUMN_DESCRIPTION $TYPE_TEXT," +
                "$COLUMN_DAY_FO_WEEK $TYPE_INTEGER," +
                "$COLUMN_PRIORITY $TYPE_INTEGER )"

        val DROP_COMMAND = "DROP TABLE IF EXISTS $TABLE_NAME"

        val DB_NAME = "notes.db"
        val DB_VERSION = 2
    }
}