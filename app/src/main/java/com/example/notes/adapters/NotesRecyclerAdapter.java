package com.example.notes.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.models.Note;

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
        holder.timestamp.setText(mNotes.get(position).getTimestamp());
        holder.title.setText(mNotes.get(position).getTitle());

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
