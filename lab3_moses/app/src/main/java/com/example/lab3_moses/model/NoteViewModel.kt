package com.example.lab3_moses.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class NoteViewModel: ViewModel() {
    var noteList = mutableStateListOf<Note>()

    fun addNote(newItem: Note){
        noteList.add(newItem)
    }

    fun deleteNote(item: Note){
        noteList.remove(item)
    }
}