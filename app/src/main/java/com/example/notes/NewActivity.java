package com.example.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.notes.persistence.NoteRepository;
import com.example.notes.util.Utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class NewActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener,
        View.OnClickListener, TextWatcher {

    //region All Variables
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
    Note mFinalNote;
    private boolean mIsNewNote;
    private GestureDetector mGestureDetector;
    private int mMode;
    private NoteRepository mNoteRepository;
    //endregion
    //endregion
    //region All Overrides
    //region onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        mLinedEditText = findViewById(R.id.note_text);
        mEditText = findViewById(R.id.note_edit_title);
        mViewTitle = findViewById(R.id.note_text_title);
        mCheck = findViewById(R.id.check_arrow);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowConatiner = findViewById(R.id.back_arrow_container);

        mNoteRepository = new NoteRepository(this);

        setListener();

        if (getIncomingIntent()) {
            setNewNoteProperties();
            enableEditMode();
        } else {
            setNoteProperties();
            disableContentInteraction();
        }
    }

    //endregion
    //region  onSaveInstanceState|onRestoreInstanceState


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if (mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    //endregion
    //region textwatcher
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mViewTitle.setText(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
    //endregion
    //endregion
    //region SaveChanges: NewNote&OldNote
    private void saveChanges() {
        if (mIsNewNote) {
            savenewNote();
        } else {
           saveOldNote();
        }
    }
    private void saveOldNote(){
        mNoteRepository.updateNote(mFinalNote);
    }
    private void savenewNote() {
        mNoteRepository.insertNoteTask(mFinalNote);
    }

    //endregion
    //region Set New/Old Note Properties
    @SuppressLint("SetTextI18n")
    private void setNewNoteProperties() {
        mViewTitle.setText("Note Title");
        mEditText.setText("Note Title");

        mInitialNote = new Note();
        mFinalNote = new Note();

        mInitialNote.setTitle("Note Title");
        mFinalNote.setTitle("Note Title");


    }


    @SuppressLint("SetTextI18n")
    private void setNoteProperties() {
        mViewTitle.setText(mFinalNote.getTitle());
        mEditText.setText(mFinalNote.getTitle());
        mLinedEditText.setText(mFinalNote.getContent());
    }

    //endregion
    //region Enable/Disable Content Interaction
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
    //region Enable/Disable Edit Mode
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
        mMode = EDIT_MODE_DISABLED;

        disableContentInteraction();
        String temp = Objects.requireNonNull(mLinedEditText.getText()).toString();
        temp = temp.replace("\n", "");
        temp = temp.replace("\t", "");

        if (temp.length() > 0) {
            mFinalNote.setTitle(mEditText.getText().toString());
            mFinalNote.setContent(mLinedEditText.getText().toString());
            String currentDate = Utility.getCurrentTimeStamp();
            mFinalNote.setTimestamp(currentDate);

            if (!mFinalNote.getContent().equals(mInitialNote.getContent()) || !mFinalNote.getTitle().equals(mInitialNote.getTitle())) {
                saveChanges();
            }
        }


    }

    //endregion
    //region Hide Soft Keyboard
    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);

        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //endregion
    //region  Get Incoming Intent
    private boolean getIncomingIntent() {
        if (getIntent().hasExtra("notes")) {
            mInitialNote = getIntent().getParcelableExtra("notes");

            mFinalNote  = new Note();

            assert mInitialNote != null;
            mFinalNote.setContent(mInitialNote.getContent());
            mFinalNote.setTitle(mInitialNote.getTitle());
            mFinalNote.setTimestamp(mInitialNote.getTimestamp());
            mFinalNote.setId(mInitialNote.getId());

            mIsNewNote = false;
            mMode = EDIT_MODE_DISABLED;
            return false;
        } else {
            mMode = EDIT_MODE_ENABLED;
            mIsNewNote = true;
            return true;
        }
    }

    //endregion
    //region Listener(s)
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
        mEditText.addTextChangedListener(this);
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

