package com.bomb.plus.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class NormalItemDecoration extends RecyclerView.ItemDecoration  {
    public void setmSpace(int mSpace) {
        this.mSpace = mSpace;
    }

    private int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = mSpace;
        }

    }

    public NormalItemDecoration() {
    }


}
