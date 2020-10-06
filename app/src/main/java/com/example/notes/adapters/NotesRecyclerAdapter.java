package com.example.notes.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.util.Utility;

import java.util.ArrayList;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder> {

    //region  init & constructor
    private ArrayList<Note> mNotes = new ArrayList<>();
    private ViewHolder.OnNoteClickListener mOnNoteListener;

    public NotesRecyclerAdapter(ArrayList<Note> notes, ViewHolder.OnNoteClickListener onNoteClickListener) {
        this.mNotes = notes;
        this.mOnNoteListener = onNoteClickListener;
    }

    //endregion
    //region overrides
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_note_list_item, parent, false);
        return new ViewHolder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            String m = mNotes.get(position).getTimestamp().substring(0, 2);
            String month = Utility.getMonthFromNumber(m);
            String year = mNotes.get(position).getTimestamp().substring(3);
            String timestamp = month + " " + year;

            holder.timestamp.setText(timestamp);
            holder.title.setText(mNotes.get(position).getTitle());


        } catch (NullPointerException e) {
            Log.d("NullPointerException: ", "onBindViewHolder: " + e.toString());

        }

    }

    @Override
    public int getItemCount() {

        return mNotes.size();
    }

    //endregion
    //region view_holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, timestamp;
        OnNoteClickListener onNoteClickListener;

        public ViewHolder(@NonNull View itemView, OnNoteClickListener onNoteClickListener) {
            super(itemView);

            title = itemView.findViewById(R.id.note_title);
            timestamp = itemView.findViewById(R.id.note_timestamp);
            this.onNoteClickListener = onNoteClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteClickListener.onNoteClick(getAdapterPosition());

        }

        public interface OnNoteClickListener {
            void onNoteClick(int position);
        }
    }
    //endregion
}
