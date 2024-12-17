package com.example.mynotes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(
    private val notesList: MutableList<Note>,
    private val onItemClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        val tvNoteId = itemView.findViewById<TextView>(R.id.tvNoteId)
        val tvNoteText = itemView.findViewById<TextView>(R.id.tvNoteText)
        val cbCompleted = itemView.findViewById<CheckBox>(R.id.cbCompleted)
        val tvTimestamp = itemView.findViewById<TextView>(R.id.tvTimestamp)

        fun bind(note: Note) {
            tvNoteId.text = note.id.toString()
            tvNoteText.text = note.text
            cbCompleted.isChecked = note.isCompleted
            tvTimestamp.text = note.timestamp

            itemView.setOnClickListener { onItemClick(note) }
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                note.isCompleted = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int = notesList.size

    fun updateNote(updatedNote: Note) {
        val index = notesList.indexOfFirst { it.id == updatedNote.id }
        if (index != -1) {
            notesList[index] = updatedNote
            notifyItemChanged(index)
        }
    }
}