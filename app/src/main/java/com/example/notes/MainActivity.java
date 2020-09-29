package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.notes.adapters.NotesRecyclerAdapter;
import com.example.notes.models.Note;
import com.example.notes.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.ViewHolder.OnNoteClickListener {
    private static final String TAG = "Message";
    //region UI components
    private RecyclerView mRecyclerView;
    //endregion
    //region  vars
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;

    //endregion
    //region override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);


        initRecyclerView();
        insertFakeNotes();
        setSupportActionBar(toolbar);
        setTitle("Notes");

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("notes", mNotes.get(position));
        startActivity(intent);
    }
    //endregion
    //region utility methods
    private void insertFakeNotes() {
        for (int i = 0; i < 100; i++) {
            Note note = new Note();
            note.setContent("content #" + (i + 1));
            note.setTitle("title #" + (i + 1));
            note.setTimestamp("Jan 2019");
            mNotes.add(note);
        }
        mNoteRecyclerAdapter.notifyDataSetChanged(); //very important!!!
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(verticalSpacingItemDecorator);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }



    //endregion
}