package com.example.notesapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentListBinding
import com.example.notesapp.framework.ListViewModel

class ListFragment : Fragment(), ListAction {

    private lateinit var binding: FragmentListBinding
    private val notesListAdapter = NotesListAdapter(arrayListOf(), this)
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[ListViewModel::class.java]
        handleView()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

    private fun handleView(){
        binding.apply {
            notesListView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = notesListAdapter
            }
            addNote.setOnClickListener{
                goToNoteDetails()
            }
        }
    }

    private fun observeViewModel(){
        viewModel.notes.observe(viewLifecycleOwner){ res ->
            binding.apply {
                progressBar.visibility = View.GONE
                notesListView.visibility = View.VISIBLE
                notesListAdapter.updateNotes(res.sortedBy { it.updateTime })
            }

        }
    }

    private fun goToNoteDetails(id: Long = 0L){
        val action = ListFragmentDirections.actionGoToNote(noteId = id)
        view?.findNavController()?.navigate(action)
    }

    override fun onClick(id: Long) {
        goToNoteDetails(id)
    }
}
