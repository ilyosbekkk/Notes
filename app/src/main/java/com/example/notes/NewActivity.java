package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.notes.models.Note;

public class NewActivity extends AppCompatActivity {

    private static final String TAG = "NewActivity";


    //region override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        if (getIntent().hasExtra("notes")) {
            Note note = getIntent().getParcelableExtra("notes");
            assert note != null;
            Log.d(TAG, "onCreate: "  + note.toString());

        }


    }
    //endregion

    //region view state

    //endregion

    //region  edit state
    //endregion


}