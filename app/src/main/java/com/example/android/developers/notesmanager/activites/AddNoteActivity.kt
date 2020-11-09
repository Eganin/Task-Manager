package com.example.android.developers.notesmanager.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import com.example.android.developers.notesmanager.DBHelper.NotesDatabase
import com.example.android.developers.notesmanager.R
import com.example.android.developers.notesmanager.common.Note
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {

    private var database: NotesDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        database = NotesDatabase.getInstance(context=this@AddNoteActivity)!!

        button.setOnClickListener {
            val title = edit_text_title.text.toString()
            val description = edit_text_desciption.text.toString()
            val dayOfWeek = spinner.selectedItemPosition
            val idRadioButton = radio_group_priority.checkedRadioButtonId
            val radioButton: RadioButton = findViewById(idRadioButton)
            val priority = radioButton.text.toString()

            if (isFilled(title = title, description = description)) {
                val note = Note(
                    title = title,
                    description = description,
                    dayOfWeek = dayOfWeek,
                    priority = priority
                )

                database?.notesDao()?.insertNote(note = note)
                startActivity(Intent(this@AddNoteActivity, MainActivity::class.java))
            } else {
                Toast.makeText(
                    this@AddNoteActivity,
                    R.string.warninf_filling_text,
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }


    private fun isFilled(title: String, description: String) =
        title.isNotEmpty() && description.isNotEmpty()
}