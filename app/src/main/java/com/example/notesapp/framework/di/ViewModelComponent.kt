package com.example.notesapp.framework.di

import com.example.notesapp.framework.ListViewModel
import com.example.notesapp.framework.NoteViewModel
import dagger.Component

@Component(modules = [AppModule::class, RepositoryModule::class, UseCasesModule::class])
interface ViewModelComponent {
    fun inject(noteViewModel: NoteViewModel)
    fun inject(listViewModel: ListViewModel)
}