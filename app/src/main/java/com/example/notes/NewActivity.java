package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notes.models.Note;

public class NewActivity extends AppCompatActivity implements View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnClickListener {

    //region  constants
    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;
    private static final String TAG = "NewActivity";
    //endregion
    //region UI componenets
    private LinedEditText mLinedEditText;
    private EditText mEditText;
    private TextView mViewTitle;
    private RelativeLayout mCheckContainer, mBackArrowConatiner;
    private ImageButton mCheck, mBackArrow;
    //endregion
    //region vars
    Note mInitialNote;
    private boolean mIsNewNote;
    private GestureDetector mGestureDetector;
    private int mMode;


    //endregion
    //region override(s)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mLinedEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowConatiner = findViewById(R.id.back_arrow_container);
        mCheck = findViewById(R.id.check_arrow);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);

        if (getIncomingIntent()) {
            //edit mode
            enableEditMode();
            setNewNoteProperties();
        } else {
            //view mode
            setNoteProperties();
            disableContentInteraction();

        }


        setListener();

    }

    @Override
    public void onBackPressed() {
        if (mMode == EDIT_MODE_ENABLED) {
            onClick(mCheck);
        } else
            super.onBackPressed();
    }

    //endregion
    //region enable/disable contentInteraction
    private void disableContentInteraction() {
        mLinedEditText.setKeyListener(null);
        mLinedEditText.setFocusable(false);
        mLinedEditText.setFocusableInTouchMode(false);
        mLinedEditText.setCursorVisible(false);
        mLinedEditText.clearFocus();
    }

    private void enableContentInteraction() {
        mLinedEditText.setKeyListener(new EditText(this).getKeyListener());
        mLinedEditText.setFocusable(true);
        mLinedEditText.setFocusableInTouchMode(true);
        mLinedEditText.setCursorVisible(true);
        mLinedEditText.requestFocus();
    }

    //endregion
    //region enable/disable edit mode
    private void enableEditMode() {
        mBackArrowConatiner.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewTitle.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);

        enableContentInteraction();
        mMode = EDIT_MODE_ENABLED;
    }

    private void disableEditMode() {
        mBackArrowConatiner.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewTitle.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);


        disableContentInteraction();
        mMode = EDIT_MODE_DISABLED;
    }

    //endregion
    //region hide soft keyboard
    private  void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager)this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null) {
            view = new View(this);

        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //endregion
    //region  getIncoming Intent
    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("notes")) {
            mInitialNote = getIntent().getParcelableExtra("notes");
            assert mInitialNote != null;
            Log.d(TAG, "getIncomingIntent: " + mInitialNote.toString());
            mIsNewNote = false;
            mMode = EDIT_MODE_DISABLED;
            return false;
        } else   {
            mMode = EDIT_MODE_ENABLED;
            mIsNewNote = true;
            return true;
        }
    }

    //endregion
    //region set New/Not New Note Properties
    @SuppressLint("SetTextI18n")
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
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setListener() {
        mLinedEditText.setOnTouchListener(this);
        mViewTitle.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
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
        Log.d(TAG, "onSingleTapUp: ");

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
        Log.d(TAG, "onSingleTapConfirmed: ");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "Double Pressed...");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_arrow:
                hideSoftKeyboard();
                disableEditMode();
                break;
            case R.id.note_text_title:
                enableEditMode();
                mEditText.requestFocus();
                mEditText.setSelection(mEditText.length());
                break;
            case R.id.toolbar_back_arrow:
                finish();
        }
    }
    //endregion


}

