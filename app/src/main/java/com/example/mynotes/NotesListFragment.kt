package com.example.mynotes

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Date
import java.util.Locale


class NotesListFragment : Fragment() {

    lateinit var notesAdapter: NotesAdapter
    private val notesList = mutableListOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_main)

        val etNoteInput = view.findViewById<EditText>(R.id.etNoteInput)
        val btnAddNote = view.findViewById<Button>(R.id.btnAddNote)

        val rvNotesList = view.findViewById<RecyclerView>(R.id.rvNotesList) as androidx.recyclerview.widget.RecyclerView


        // Настраиваем адаптер
        notesAdapter = NotesAdapter(notesList) { note ->
            // При нажатии на элемент списка открываем экран редактирования
            openEditNoteFragment(note)
        }

        rvNotesList.layoutManager = LinearLayoutManager(requireContext())
        rvNotesList.adapter = notesAdapter

        // Добавляем новую заметку при нажатии кнопки
        btnAddNote.setOnClickListener {
            val noteText = etNoteInput.text.toString()
            if (noteText.isNotEmpty()) {
                val newNote = Note(
                    id = notesList.size + 1,
                    text = noteText,
                    isCompleted = false,
                    timestamp = getCurrentTimestamp()
                )
                notesList.add(newNote)
                notesAdapter.notifyItemInserted(notesList.size - 1)
                etNoteInput.text.clear()
            }
        }
    }

    private fun openEditNoteFragment(note: Note) {
        val fragment = EditNoteFragment.newInstance(note)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}