package com.example.android.developers.notesmanager.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.developers.notesmanager.DBHelper.NotesDatabase
import com.example.android.developers.notesmanager.R
import com.example.android.developers.notesmanager.adapters.NoteAdapter
import com.example.android.developers.notesmanager.common.Note
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val notes = arrayListOf<Note>()
    private var database: NotesDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        database = NotesDatabase.getInstance(context=this@MainActivity)!!
        getData()
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
                return false;
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
        val note = notes[position]
        /*
        Идем в абстрактный класс - NotesDatabase
        получаем instance интерфейса
        и в нем с помощью метода с анотацией
        удаляем данные по SQL запросу
         */
        database?.notesDao()?.deleteNote(note)
        //adapter.notifyDataSetChanged()
        getData()
    }

    private fun hideActionBar() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun getData() {
        // получаем все замеки из БД
        /*
        Идем в абстрактный класс - NotesDatabase
        получаем instance интерфейса
        и в нем с помощью метода с анотацией
        получаеи данные по SQL запросу
         */
        val notesFromDB = database?.notesDao()?.getAllNotes()
        notes.clear()
        notes.addAll(notesFromDB ?: listOf())
    }


}