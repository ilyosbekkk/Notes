package com.example.notes.async;


import com.example.notes.models.Note;
import com.example.notes.persistence.NoteDAO;

public class UpdateAsyncTask extends Thread {
    private NoteDAO mNoteDao;
    private Note mNote;

    public UpdateAsyncTask(NoteDAO noteDAO, Note note) {
        mNoteDao = noteDAO;
        mNote = note;
    }

    @Override
    public void run() {
        mNoteDao.update(mNote);
    }
}


