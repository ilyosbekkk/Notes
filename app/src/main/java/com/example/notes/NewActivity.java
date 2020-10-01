package com.example.notes;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.models.Note;

public class NewActivity extends AppCompatActivity {

    //region UI componenets
    private LinedEditText mLinedEditText;
    private EditText mEditText;
    private TextView mViewTitle;
    //endregion
    private static final String TAG = "NewActivity";
    //region vars
    private boolean mIsNewNote;


    //endregion
    //region override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mLinedEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_title);

        if (getIncomingIntent()) {
            //edit mode
        } else {
            //view mode
        }

    }
    //endregion

    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("notes")) {
            Note incomingNote = getIntent().getParcelableExtra("note");
            assert incomingNote != null;
            Log.d(TAG, "getIncomingIntent: " + incomingNote.toString());
            mIsNewNote = false;
            return false;
        } else {
            mIsNewNote = true;
            return true;
        }
    }
    //region view state

    //endregion

    //region  edit state
    //endregion
}

