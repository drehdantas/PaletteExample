package com.project.andredantas.palettewithpicassodemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SongActivity extends AppCompatActivity {
    private Song song;
    private PlayerNotification playerNotification = null;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.album_image)
    ImageView albumImage;
    @Bind(R.id.vibrant_color)
    RelativeLayout vibrantColorView;
    @Bind(R.id.vibrant_color_dark)
    RelativeLayout vibrantColorDarkView;
    @Bind(R.id.vibrant_color_light)
    RelativeLayout vibrantColorLightView;
    @Bind(R.id.muted_color)
    RelativeLayout mutedColorView;
    @Bind(R.id.muted_color_dark)
    RelativeLayout mutedColorDarkView;
    @Bind(R.id.muted_color_light)
    RelativeLayout mutedColorLightView;

    @Bind(R.id.vibrant_color_text_vibrant_color_dark)
    TextView vibrantColorTextVibrantColorDark;
    @Bind(R.id.vibrant_color_text_vibrant_color_light)
    TextView vibrantColorTextVibrantColorLight;
    @Bind(R.id.vibrant_color_text_muted_color)
    TextView vibrantColorTextMutedColor;
    @Bind(R.id.vibrant_color_text_muted_color_dark)
    TextView vibrantColorTextMutedColorDark;
    @Bind(R.id.vibrant_color_text_muted_color_light)
    TextView vibrantColorTextMutedColorLight;

    @Bind(R.id.vibrant_color_dark_text_vibrant_color)
    TextView vibrantColorDarkTextVibrantColor;
    @Bind(R.id.vibrant_color_dark_text_vibrant_color_light)
    TextView vibrantColorDarkTextVibrantColorLight;
    @Bind(R.id.vibrant_color_dark_text_muted_color)
    TextView vibrantColorDarkTextMutedColor;
    @Bind(R.id.vibrant_color_dark_text_muted_color_dark)
    TextView vibrantColorDarkTextMutedColorDark;
    @Bind(R.id.vibrant_color_dark_text_muted_color_light)
    TextView vibrantColorDarkTextMutedColorLight;

    @Bind(R.id.vibrant_color_light_text_vibrant_color)
    TextView vibrantColorLightTextVibrantColor;
    @Bind(R.id.vibrant_color_light_text_vibrant_color_dark)
    TextView vibrantColorLightTextVibrantColorDark;
    @Bind(R.id.vibrant_color_light_text_muted_color)
    TextView vibrantColorLightTextMutedColor;
    @Bind(R.id.vibrant_color_light_text_muted_color_dark)
    TextView vibrantColorLightTextMutedColorDark;
    @Bind(R.id.vibrant_color_light_text_muted_color_light)
    TextView vibrantColorLightTextMutedColorLight;

    @Bind(R.id.muted_color_text_vibrant_color)
    TextView mutedColorTextVibrantColor;
    @Bind(R.id.muted_color_text_vibrant_color_dark)
    TextView mutedColorTextVibrantColorDark;
    @Bind(R.id.muted_color_text_vibrant_color_light)
    TextView mutedColorTextVibrantColorLight;
    @Bind(R.id.muted_color_text_muted_color_dark)
    TextView mutedColorTextMutedColorDark;
    @Bind(R.id.muted_color_text_muted_color_light)
    TextView mutedColorTextMutedColorLight;

    @Bind(R.id.muted_color_dark_text_vibrant_color)
    TextView mutedColorDarkTextVibrantColor;
    @Bind(R.id.muted_color_dark_text_vibrant_color_dark)
    TextView mutedColorDarkTextVibrantColorDark;
    @Bind(R.id.muted_color_dark_text_vibrant_color_light)
    TextView mutedColorDarkTextVibrantColorLight;
    @Bind(R.id.muted_color_dark_text_muted_color)
    TextView mutedColorDarkTextMutedColor;
    @Bind(R.id.muted_color_dark_text_muted_color_light)
    TextView mutedColorDarkTextMutedColorLight;

    @Bind(R.id.muted_color_light_text_vibrant_color)
    TextView mutedColorLightTextVibrantColor;
    @Bind(R.id.muted_color_light_text_vibrant_color_dark)
    TextView mutedColorLightTextVibrantColorDark;
    @Bind(R.id.muted_color_light_text_vibrant_color_light)
    TextView mutedColorLightTextVibrantColorLight;
    @Bind(R.id.muted_color_light_text_muted_color)
    TextView mutedColorLightTextMutedColor;
    @Bind(R.id.muted_color_light_text_muted_color_dark)
    TextView mutedColorLightTextMutedColorDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        ButterKnife.bind(this);
        getReceveidIntent();

        toolbar.setTitle(song.getName());
        Picasso.with(this)
                .load(song.getUrlImage())
                .into(albumImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        URL url;
                        try {
                            url = new URL(song.getUrlImage());
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            Bitmap bm = BitmapFactory.decodeStream(input);
                            extractProminentColors(bm);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        playerNotification = new PlayerNotification(this);
        playerNotification.buildNotification(SongActivity.class);
        playerNotification.updateNotificationInfo(song);
        playerNotification.updatePlayPause(false);
    }

    private void extractProminentColors(Bitmap bitmap) {
        int defaultColor = 0x000000;

        Palette p = Palette.from(bitmap).generate();
        Palette.Swatch swatch = p.getVibrantSwatch();
//        if (swatch != null){
//            // Gets an appropriate title text color
//            int titleTextColor = swatch.getTitleTextColor();
//            // Gets an appropriate body text color
//            int bodyTextColor = swatch.getBodyTextColor();
//        }

        int vibrantColor = p.getVibrantColor(defaultColor);
        vibrantColorView.setBackgroundColor(vibrantColor);

        int vibrantColorDark = p.getDarkVibrantColor(defaultColor);
        vibrantColorDarkView.setBackgroundColor(vibrantColorDark);

        int vibrantColorLight = p.getLightVibrantColor(defaultColor);
        vibrantColorLightView.setBackgroundColor(vibrantColorLight);

        int mutedColor = p.getMutedColor(defaultColor);
        mutedColorView.setBackgroundColor(mutedColor);

        int mutedColorDark = p.getDarkMutedColor(defaultColor);
        mutedColorDarkView.setBackgroundColor(mutedColorDark);

        int mutedColorLight = p.getLightMutedColor(defaultColor);
        mutedColorLightView.setBackgroundColor(mutedColorLight);

        //texts in vibrant color
        vibrantColorTextVibrantColorDark.setTextColor(vibrantColor);
        vibrantColorTextVibrantColorLight.setTextColor(vibrantColorLight);
        vibrantColorTextMutedColor.setTextColor(mutedColor);
        vibrantColorTextMutedColorDark.setTextColor(mutedColorDark);
        vibrantColorTextMutedColorLight.setTextColor(mutedColorLight);

        //texts in vibrant color dark
        vibrantColorDarkTextVibrantColor.setTextColor(vibrantColor);
        vibrantColorDarkTextVibrantColorLight.setTextColor(vibrantColorLight);
        vibrantColorDarkTextMutedColor.setTextColor(mutedColor);
        vibrantColorDarkTextMutedColorDark.setTextColor(mutedColorDark);
        vibrantColorDarkTextMutedColorLight.setTextColor(mutedColorLight);

        //texts in vibrant color light
        vibrantColorLightTextVibrantColor.setTextColor(vibrantColor);
        vibrantColorLightTextVibrantColorDark.setTextColor(vibrantColorDark);
        vibrantColorLightTextMutedColor.setTextColor(mutedColor);
        vibrantColorLightTextMutedColorDark.setTextColor(mutedColorDark);
        vibrantColorLightTextMutedColorLight.setTextColor(mutedColorLight);

        //texts in muted color
        mutedColorTextVibrantColor.setTextColor(vibrantColor);
        mutedColorTextVibrantColorDark.setTextColor(vibrantColorDark);
        mutedColorTextVibrantColorLight.setTextColor(vibrantColorLight);
        mutedColorTextMutedColorDark.setTextColor(mutedColorDark);
        mutedColorTextMutedColorLight.setTextColor(mutedColorLight);

        //texts in muted color dark
        mutedColorDarkTextVibrantColor.setTextColor(vibrantColor);
        mutedColorDarkTextVibrantColorDark.setTextColor(vibrantColorDark);
        mutedColorDarkTextVibrantColorLight.setTextColor(vibrantColorLight);
        mutedColorDarkTextMutedColor.setTextColor(mutedColor);
        mutedColorDarkTextMutedColorLight.setTextColor(mutedColorLight);

        //texts in muted color light
        mutedColorLightTextVibrantColor.setTextColor(vibrantColor);
        mutedColorLightTextVibrantColorDark.setTextColor(vibrantColorDark);
        mutedColorLightTextVibrantColorLight.setTextColor(vibrantColorLight);
        mutedColorLightTextMutedColor.setTextColor(mutedColor);
        mutedColorLightTextMutedColorDark.setTextColor(mutedColorDark);

    }

    public void getReceveidIntent() {
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            song = (Song) bundle.getSerializable("song");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        playerNotification.dismiss();
    }
}
