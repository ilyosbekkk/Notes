package com.example.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;


public class DeleteAsyncTask extends Thread {
    private NoteDAO mNoteDao;
    private Note mNote;

    public DeleteAsyncTask(NoteDAO noteDAO, Note note) {
        mNoteDao = noteDAO;
        mNote = note;
    }

    @Override
    public void run() {
        mNoteDao.delete(mNote);
    }
}

