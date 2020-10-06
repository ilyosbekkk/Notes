package com.example.notes.async;

import android.os.AsyncTask;
import android.util.Log;

import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;

public class InsertAsyncTask extends AsyncTask<Note, Void , Void> {
    private static final String TAG = "InsertAsyncTask";
    private NoteDAO mNoteDao;
    public InsertAsyncTask(NoteDAO dao){
        mNoteDao = dao;
    }
    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: " + Thread.currentThread().getName());
        mNoteDao.insertNotes(notes);
        return null;
    }


}

















//AsyncTask is good for Single Task on a background thread

//PluralSight Threading Mitch

