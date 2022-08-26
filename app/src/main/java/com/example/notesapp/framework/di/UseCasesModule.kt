package com.example.notesapp.framework.di

import com.example.core.repository.NoteRepository
import com.example.core.use_case.*
import com.example.notesapp.framework.UseCases
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun getUseCases(repository: NoteRepository) = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository),
        GetWordCount()
    )

}