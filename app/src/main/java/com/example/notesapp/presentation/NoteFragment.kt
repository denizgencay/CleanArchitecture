package com.example.notesapp.presentation

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.core.data.Note
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNoteBinding
import com.example.notesapp.framework.NoteViewModel


class NoteFragment : Fragment() {

    private var noteId = 0L
    private lateinit var binding: FragmentNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note("","",0L,0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[NoteViewModel::class.java]
        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L){
            viewModel.getNote(noteId)
        }
        handleView()
        observeViewModel()
    }

    private fun handleView(){
        binding.apply {
            checkButton.setOnClickListener {
                if (titleView.text.isNotEmpty() || contentView.text.isNotEmpty()){
                    val time = System.currentTimeMillis()
                    currentNote.title = titleView.text.toString()
                    currentNote.content = contentView.text.toString()
                    currentNote.updateTime = time
                    if (currentNote.id == 0L){
                        currentNote.creationTime = time
                    }
                    viewModel.saveNote(currentNote)
                }else{
                    view?.findNavController()?.popBackStack()
                }
            }
        }
    }

    private fun observeViewModel(){
        viewModel.saved.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(context, "Done!", Toast.LENGTH_LONG).show()
                view?.findNavController()?.popBackStack()
            }else{
                Toast.makeText(context, "Something went wrong, please try again!", Toast.LENGTH_LONG).show()
            }
        }
        viewModel.currentNote.observe(viewLifecycleOwner){ note ->
            note?.let {
                currentNote = it
                binding.apply {
                    titleView.setText(it.title, TextView.BufferType.EDITABLE)
                    contentView.setText(it.content, TextView.BufferType.EDITABLE)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteNote ->{
                if (context != null && noteId != 0L){
                    AlertDialog.Builder(context!!)
                        .setTitle(R.string.delete_note)
                        .setMessage(getString(R.string.are_you_sure))
                        .setPositiveButton(getString(R.string.yes),DialogInterface.OnClickListener(positiveButtonClick))
                        .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener(negativeButtonClick))
                        .create()
                        .show()

                }
            }
        }
        return true
    }

    private val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        viewModel.deleteNote(currentNote)
    }

    private val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(context,getString(R.string.no), Toast.LENGTH_LONG).show()
    }

}










