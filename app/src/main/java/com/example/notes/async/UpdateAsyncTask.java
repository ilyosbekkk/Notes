package com.example.notes.async;

import android.os.AsyncTask;

import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;

public class UpdateAsyncTask extends AsyncTask<Note, Void, Void> {


    NoteDAO mNoteDao;

    public UpdateAsyncTask(NoteDAO noteDao) {
        mNoteDao = noteDao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        mNoteDao.update(notes);
        return null;

    }
}
