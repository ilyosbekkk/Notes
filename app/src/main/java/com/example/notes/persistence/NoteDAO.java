
package com.example.notes.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notes.models.Note;

import java.util.List;

@Dao // Data Access Object
public interface NoteDAO {

    //region insert
    @Insert
    long[] insertNotes(Note... notes);  // Note... == Note[] elipsis
    //endregion

    //region getNotes
    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNote();
    ///endregion

    //region delete
    @Delete
    int delete(Note... notes);
    //endregion

    //region update
    @Update
    int update(Note... notes);
    //endregion
}


