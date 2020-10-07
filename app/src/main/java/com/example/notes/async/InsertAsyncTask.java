package com.example.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;

public class InsertAsyncTask extends Thread{

    NoteDAO mNoteDao;
    Note mNote;
    public InsertAsyncTask(NoteDAO noteDAO, Note note){
        mNoteDao = noteDAO;
        mNote = note;

    }
    @Override
    public void run() {
        mNoteDao.insertNotes(mNote);
    }
}

















//AsyncTask is good for Single Task on a background thread

//PluralSight Threading Mitch

