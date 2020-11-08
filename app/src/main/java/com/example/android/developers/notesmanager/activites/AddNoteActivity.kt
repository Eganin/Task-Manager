package com.example.android.developers.notesmanager.activites

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.android.developers.notesmanager.DBHelper.NotesDBHelper
import com.example.android.developers.notesmanager.R
import com.example.android.developers.notesmanager.common.Note
import com.example.android.developers.notesmanager.contacts.NoteContract
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    private lateinit var dbHelper: NotesDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        initDB()

        button.setOnClickListener {
            val title = edit_text_title.text.toString()
            val description = edit_text_desciption.text.toString()
            val dayOfWeek = spinner.selectedItemPosition+1
            val idRadioButton = radio_group_priority.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(idRadioButton)
            val priority = radioButton.text.toString()
            val note = Note(
                id=0,
                title = title,
                description = description,
                dayOfWeek = dayOfWeek,
                priority = priority
            )

            if (isFilled(title, description)) {
                val database = dbHelper.writableDatabase
                fillingDB(database = database, note = note)

                startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
            } else {
                Toast.makeText(this@AddNoteActivity, getString(R.string.warninf_filling_text), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initDB() {
        dbHelper = NotesDBHelper(
            context = this@AddNoteActivity,
            name = NoteContract().NotesEntry().DB_NAME,
            factory = null,
            version = NoteContract().NotesEntry().DB_VERSION
        )
    }

    private fun fillingDB(database: SQLiteDatabase, note: Note) {
        // вставка значений в БД
        val contentValues = ContentValues()
        contentValues.put(NoteContract().NotesEntry().COLUMN_TITLE, note.title)
        contentValues.put(NoteContract().NotesEntry().COLUMN_DESCRIPTION, note.description)
        contentValues.put(NoteContract().NotesEntry().COLUMN_DAY_FO_WEEK, note.dayOfWeek)
        contentValues.put(NoteContract().NotesEntry().COLUMN_PRIORITY, note.priority)
        database.insert(NoteContract().NotesEntry().TABLE_NAME, null, contentValues)

    }

    private fun isFilled(title: String, description: String) =
        title.isNotEmpty() && description.isNotEmpty()
}