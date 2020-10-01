package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notes.models.Note;

public class NewActivity extends AppCompatActivity
        implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    //region UI componenets
    private LinedEditText mLinedEditText;
    private EditText mEditText;
    private TextView mViewTitle;
    //endregion
    //region vars
    Note mInitialNote;
    private boolean mIsNewNote;
    private static final String TAG = "NewActivity";
    private GestureDetector mGestureDetector;


    //endregion
    //region override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mLinedEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);

        if (getIncomingIntent()) {
            //edit mode
            setNewNoteProperties();
        } else {
            //view mode
            setNoteProperties();
        }
        setListener();

    }

    //endregion
    //region  getIncoming Intent
    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("notes")) {
            mInitialNote = getIntent().getParcelableExtra("notes");
            assert mInitialNote != null;
            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());
            mIsNewNote = false;
            return false;
        } else {
            mIsNewNote = true;
            return true;
        }
    }

    //endregion
    //region set New/Not New Note Properties
    private void setNewNoteProperties() {
        mViewTitle.setText("Note Title");
        mEditText.setText("Note Title");

    }


    private void setNoteProperties() {
        mViewTitle.setText(mInitialNote.getTitle());
        mEditText.setText(mInitialNote.getTitle());
        mLinedEditText.setText(mInitialNote.getContent());
    }

    //endregion
    //region listener(s)
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    private void setListener() {
        mLinedEditText.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this, this);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "Double Pressed...");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }
    //endregion

}

