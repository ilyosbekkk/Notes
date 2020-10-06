package com.example.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;

public class DeleteAsyncTask extends AsyncTask<Note, Void , Void> {
    private static final String TAG = "InsertAsyncTask";
    private NoteDAO mNoteDao;
    public DeleteAsyncTask(NoteDAO dao){
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
        mNoteDao.delete(notes);
        return null;
    }


}


