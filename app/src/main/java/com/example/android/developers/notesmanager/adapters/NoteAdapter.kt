package com.example.android.developers.notesmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.developers.notesmanager.R
import com.example.android.developers.notesmanager.common.Note
import com.example.android.developers.notesmanager.contacts.NoteContract

class NoteAdapter(val notes: List<Note>) : RecyclerView.Adapter<NoteAdapter.NotesViewHolder>() {

    // интерфейс у которого еужно реализрвать методы удаления по кдержанию и свайпу в activity
    interface OnNoteClickListener {
        fun onNoteClick(position: Int)
        fun onLongClick(position : Int)
    }

    var onNoteClickListener: OnNoteClickListener? = null


    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewTitle: TextView = itemView.findViewById(R.id.text_view_title)
        private val textViewDescription: TextView =
            itemView.findViewById(R.id.text_view_description)
        private val textViewDayOfWeek: TextView = itemView.findViewById(R.id.text_view_day_of_week)

        fun bind(note: Note, holder: NotesViewHolder) {
            textViewTitle.text = note.title
            textViewDescription.text = note.description
            textViewDayOfWeek.text = Note.getDayAsString(note.dayOfWeek)
            val colorId: Int
            when (note.priority) {
                "1" -> colorId = holder.itemView.resources.getColor(android.R.color.holo_red_light)
                "2" -> colorId =
                    holder.itemView.resources.getColor(android.R.color.holo_orange_light)
                else -> colorId =
                    holder.itemView.resources.getColor(android.R.color.holo_green_light)
            }

            textViewTitle.setBackgroundColor(colorId)

            // устанавливаем listeners
            holder.itemView.setOnClickListener {

                if (onNoteClickListener != null) {
                    onNoteClickListener?.onNoteClick(adapterPosition)
                }
            }

            // listener - реагирует на долгое зажатин на элемент
            holder.itemView.setOnLongClickListener{
                if(onNoteClickListener != null){
                    onNoteClickListener?.onLongClick(adapterPosition)
                }
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.note_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(note = notes[position], holder = holder)
    }

    override fun getItemCount(): Int {
        return notes.size
    }
}


