package com.bomb.plus.study;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;

import pl.droidsonroids.gif.GifDrawable;

public class LoadImageListener implements RequestListener<Bitmap> {

    private String mTag = "";
    private WeakReference<TGifImageView> weakImage;

    public LoadImageListener setWeakImage(TGifImageView imageView) {
        weakImage = new WeakReference<>(imageView);
        mTag=imageView.getName();
        return this;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {

        TGifImageView imageView = weakImage.get();
        Log.d("gif_", "----" + model.toString());
        if (imageView != null && model instanceof String && !((String) model).isEmpty()) {

            Glide.with(imageView).asFile().load(model).into(new CacheImageTarget() {
                @Override
                public void onResourceReady(@NotNull File resource, @org.jetbrains.annotations.Nullable Transition<? super File> transition) {
                    super.onResourceReady(resource, transition);

                    try {
                        //resource.
                        if(weakImage.get()==null){
                            Log.d("gif_", "----w  null" );
                          return;
                        }
                         String name= imageView.getName();
                        if(mTag.equals(name)&&fileIsGif(resource)){
                            GifDrawable drawable = new GifDrawable(resource);
                            drawable.setLoopCount(2);
                            imageView.setImageDrawable(drawable);
                            drawable.start();
                            Log.d("gif_", "--------------------    drawable  start---->  "+ name);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("gif_", "----" + e);
                    }
                }
            });
        }

        return false;
    }



    private Boolean fileIsGif(File srcFilePath) {
        FileInputStream imgFile = null;
        byte[] b = new byte[10];
        int l = -1;
        try {
            imgFile = new FileInputStream(srcFilePath);
            l = imgFile.read(b);
            imgFile.close();
        } catch (Exception e) {
            return false;
        }
        if (l == 10) {
            byte b0 = b[0];
            byte b1 = b[1];
            byte b2 = b[2];
            byte b3 = b[3];
            byte b6 = b[6];
            byte b7 = b[7];
            byte b8 = b[8];
            byte b9 = b[9];

            if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F') {
                Log.d("gif_", "----gif");
                return true;
            } else if (b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G') {
                Log.d("gif_", "----png");
                return false;
            } else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
                Log.d("gif_", "----jpg");
                return false;
            } else {
                Log.d("gif_", "----gg");
                return false;
            }
        } else {
            return false;
        }


    }
}
