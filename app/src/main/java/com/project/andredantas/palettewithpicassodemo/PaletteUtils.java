package com.project.andredantas.palettewithpicassodemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.Display;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Andre Dantas on 8/17/17.
 */

public class PaletteUtils {
    Bitmap bitmap;
    int defaultColor = 0x000000;
    Context context;

    public PaletteUtils(String albumCover, Context context) {
        this.context = context;
        URL url;
        try {
            url = new URL(albumCover);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPlayerNotificationBackground() {
        Palette p = Palette.from(bitmap).generate();
        int mutedColorDark = p.getDarkMutedColor(defaultColor);
        if (mutedColorDark == 0) {
            mutedColorDark = context.getResources().getColor(R.color.notification_player_background);
        }
        return mutedColorDark;
    }

    public int getPlayerNotificationText() {
        Palette p = Palette.from(bitmap).generate();
        int mutedColorLight = p.getLightMutedColor(defaultColor);
        if (mutedColorLight == 0 && p.getDarkMutedColor(defaultColor) == 0) {
            mutedColorLight = context.getResources().getColor(R.color.notification_player_song_name);
        } else if (mutedColorLight == 0 && p.getDarkMutedColor(defaultColor) != 0) {
            mutedColorLight = context.getResources().getColor(R.color.notification_player_background);
        }
        return mutedColorLight;
    }


    public Bitmap changeBitmapColor(Bitmap sourceBitmap, int color) {


        Paint paint = new Paint();
        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN); //ContextCompat.getColor(context, color)
        paint.setColorFilter(filter);

        Canvas canvas = new Canvas(sourceBitmap);
        canvas.drawBitmap(sourceBitmap, 0, 0, paint);


        return sourceBitmap;

    }

}
