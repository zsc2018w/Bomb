package com.bomb.plus.study;

import android.content.Context;
import android.util.AttributeSet;

import pl.droidsonroids.gif.GifImageView;

public class TGifImageView extends GifImageView {
    public String getName() {
        return mIndex + "_" + mTag;
    }

     private String mName;
     public String mTag;
     public int mIndex;

    public TGifImageView(Context context) {
        super(context);
    }

    public TGifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TGifImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


}
