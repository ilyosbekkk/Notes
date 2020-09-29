package com.example.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {

    private Rect mRect;
    private Paint mPaint;

    //AppCompatEditText compatible with older versions of Android
    //region  init&constructor
    public LinedEditText(@NonNull Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.GREEN);
    }
    //endregion

    @Override
    protected void onDraw(Canvas canvas) {
        int height = ((View) this.getParent()).getHeight();
        int lineHeight = getLineHeight();
        int numberOfLines = height / lineHeight;

        Rect rect = mRect;
        Paint paint = mPaint;
        int baseLine = getLineBounds(0, rect);
        for (int i = 0; i < numberOfLines; i++) {
            canvas.drawLine(rect.left, baseLine + 1, rect.right, baseLine + 1, paint);
            baseLine += lineHeight;
        }
        super.onDraw(canvas);
    }
}
