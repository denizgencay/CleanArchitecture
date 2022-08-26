package com.example.notesapp.presentation

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.data.Note
import com.example.notesapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(var notes: ArrayList<Note>?, val actions: ListAction): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>){
        notes?.clear()
        notes?.addAll(newNotes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val layout = view.findViewById<CardView>(R.id.noteLayout)
        private val noteTitle = view.findViewById<TextView>(R.id.title)
        private val noteContent = view.findViewById<TextView>(R.id.content)
        private val noteDate = view.findViewById<TextView>(R.id.date)
        private val noteWords = view.findViewById<TextView>(R.id.wordCount)

        fun bind(note: Note){
            noteTitle.text = note.title
            noteContent.text = note.content
            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"
            noteWords.text = "Words: ${note.wordCount}"
            layout.setOnClickListener { actions.onClick(note.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(notes?.get(position) ?: Note("","",0L,0L))
    }

    override fun getItemCount(): Int {
        return notes?.size ?: 0
    }


}