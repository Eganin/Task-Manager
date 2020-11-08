package com.example.android.developers.notesmanager.activites

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.developers.notesmanager.DBHelper.NotesDBHelper
import com.example.android.developers.notesmanager.R
import com.example.android.developers.notesmanager.adapters.NoteAdapter
import com.example.android.developers.notesmanager.common.Note
import com.example.android.developers.notesmanager.contacts.NoteContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: NotesDBHelper
    private val notes = arrayListOf<Note>()
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDB()
        database = dbHelper.readableDatabase
        readDB()

        initRecyclerView()
        hideActionBar()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)

            startActivity(intent)
        }
    }

    private fun initRecyclerView() {
        val adapter = NoteAdapter(notes = notes)
        val layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager = layoutManager
        // используем интерфейс из NoteAdapter
        adapter.onNoteClickListener = object : NoteAdapter.OnNoteClickListener {
            override fun onNoteClick(position: Int) {
                Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_LONG).show()

            }

            // при долгом нажатии
            override fun onLongClick(position: Int) {
                removeTask(position = position, adapter = adapter)
            }
        }

        //ананимный класс для реагирования на свайп элемента
        val itemTouchHelper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // реагирует на свайп
                removeTask(position = viewHolder.adapterPosition, adapter = adapter)
            }
        })

        // прикрепляем к recycler view
        itemTouchHelper.attachToRecyclerView(main_recycler_view)
    }

    private fun removeTask(position: Int, adapter: NoteAdapter) {
        val id = notes.get(position).id
        val where = "${NoteContract().NotesEntry()._ID} = ?"
        val whereArgs = arrayOf(id.toString())
        database.delete(NoteContract().NotesEntry().TABLE_NAME,where,whereArgs)
        readDB()
        adapter.notifyDataSetChanged()
    }

    private fun initDB() {
        dbHelper = NotesDBHelper(
            context = this@MainActivity,
            name = NoteContract().NotesEntry().DB_NAME,
            factory = null,
            version = NoteContract().NotesEntry().DB_VERSION
        )
    }


    private fun readDB() {
        notes.clear()
        val selection = NoteContract().NotesEntry().COLUMN_PRIORITY+" < ?"
        val selectionArgs = arrayOf("2")
        val cursor = database.query(
            NoteContract().NotesEntry().TABLE_NAME,
            null, null, null, null, null,
            NoteContract().NotesEntry().COLUMN_DAY_FO_WEEK
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(NoteContract().NotesEntry()._ID))
            val title =
                cursor.getString(cursor.getColumnIndex(NoteContract().NotesEntry().COLUMN_TITLE))
            val desciption =
                cursor.getString(cursor.getColumnIndex(NoteContract().NotesEntry().COLUMN_DESCRIPTION))
            val dayOfWeek =
                cursor.getInt(cursor.getColumnIndex(NoteContract().NotesEntry().COLUMN_DAY_FO_WEEK))
            val priority =
                cursor.getString(cursor.getColumnIndex(NoteContract().NotesEntry().COLUMN_PRIORITY))

            val note = Note(id,title, desciption, dayOfWeek, priority)
            notes.add(note)
        }

        cursor.close()
    }

    private fun hideActionBar(){
        val actionBar = supportActionBar
        actionBar?.hide()
    }



}