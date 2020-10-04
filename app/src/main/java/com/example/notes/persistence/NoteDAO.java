
package com.example.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.models.Note;

import java.util.List;

@Dao
public interface NoteDAO {
    @Insert
    long[] insertNotes(Note... notes);  // Note... == Note[] elipsis

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNote();

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);
}
