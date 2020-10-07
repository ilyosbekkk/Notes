package com.example.notes.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.notes.async.DeleteAsyncTask;
import com.example.notes.async.InsertAsyncTask;
import com.example.notes.async.UpdateAsyncTask;
import com.example.notes.models.Note;

import java.util.List;

public class NoteRepository {

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);

    }

    //region insert
    public void insertNoteTask(Note note) {
        InsertAsyncTask insertAsyncTask = new InsertAsyncTask(mNoteDatabase.getNoteDao(), note);
        insertAsyncTask.start();

    }
    //endregion

    //region update
    public void updateNote(Note note) {
        UpdateAsyncTask updateAsyncTask = new UpdateAsyncTask(mNoteDatabase.getNoteDao(), note);
        updateAsyncTask.start();
    }
    //endregion

    //region retrieve
    public LiveData<List<Note>> retrieveNotesTask() {
        return mNoteDatabase.getNoteDao().getNote();
    }
    //endregion

    //region delete
    public void deleteNote(Note note) {
        DeleteAsyncTask deleteAsyncTask = new DeleteAsyncTask(mNoteDatabase.getNoteDao(), note);
        deleteAsyncTask.start();

    }
    //endregion

}
