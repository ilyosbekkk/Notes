package com.example.notes.util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator  extends RecyclerView.ItemDecoration{

    //region vars
    private final int verticalSpceHeight;

    public VerticalSpacingItemDecorator(int verticalSpceHeight) {
        this.verticalSpceHeight = verticalSpceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
          outRect.bottom = verticalSpceHeight;
    }


    //endregion
}
