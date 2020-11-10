package com.example.android.developers.notesmanager.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.android.developers.notesmanager.R
import kotlinx.android.synthetic.main.activity_sorted.*

class SortedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorted)
        sendData.setOnClickListener {
            val currentIntent = Intent(this , MainActivity::class.java)
            currentIntent.putExtra(SAVE_SETTINGS_SORTED,spinner_sorted.selectedItemPosition)
            startActivity(currentIntent)
        }
    }

    companion object{
        const val SAVE_SETTINGS_SORTED = "SAVE_SETTINGS_SORTED"
    }
}