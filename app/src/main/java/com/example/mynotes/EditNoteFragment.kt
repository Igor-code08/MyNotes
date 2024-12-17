package com.example.mynotes

import android.os.BaseBundle
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar

private fun BaseBundle.putParcelable(argNote: String, note: Note) {

}

class EditNoteFragment : Fragment() {

    private var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Используем getParcelable вместо getSerializable
            note = it.getParcelable(ARG_NOTE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            // Закрываем текущий фрагмент
            parentFragmentManager.popBackStack()
        }

        btnEditNote.setOnClickListener {
            val updatedText = etEditNote.text.toString()
            if (updatedText.isNotEmpty()) {
                note?.text = updatedText
                (parentFragment as? NotesListFragment)?.notesAdapter?.updateNote(note!!)
                parentFragmentManager.popBackStack()
            }
        }

        // Устанавливаем текст заметки в поле редактирования
        etEditNote.setText(note?.text ?: "")
    }

    companion object {
        private const val ARG_NOTE = "note"

        fun newInstance(note: Note) =
                EditNoteFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_NOTE, note) // Передаем объект как Parcelable
                    }
                }
    }
}