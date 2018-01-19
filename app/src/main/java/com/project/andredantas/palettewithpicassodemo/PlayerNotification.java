package com.project.andredantas.palettewithpicassodemo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by Andre Dantas on 8/3/17.
 */

public class PlayerNotification  {
    Context context;
    Notification notification;
    NotificationManager notifyManager;

    public static final int NOTIFICATION_PLAYER_ID = 628117233;

    public PlayerNotification(Context context){

        this.context = context;
        this.notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    RemoteViews bigView;
    RemoteViews smallView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ServiceCast")
    public Notification buildNotification(Class clazz){
        Intent notificationIntent = new Intent(context, clazz);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        smallView = new RemoteViews(context.getPackageName(), R.layout.player_notification_control);
        bigView = new RemoteViews(context.getPackageName(), R.layout.player_notification_control_big);

        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_notify_player)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notification.contentView = smallView;

        if(currentVersionSupportBigNotification()){
            notification.bigContentView = bigView;
        }

        return notification;
    }

    public static boolean currentVersionSupportBigNotification() {
        int sdkVersion = Build.VERSION.SDK_INT;
        return sdkVersion >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public void updateNotificationInfo(Song song){
        PaletteUtils paletteUtils = new PaletteUtils(song.getUrlImage(), context);

        smallView.setTextViewText(R.id.player_notification_control_song, song.getName());
        smallView.setTextViewText(R.id.player_notification_control_artist, song.getAlbum());

        smallView.setTextColor(R.id.player_notification_control_song, paletteUtils.getPlayerNotificationText());
        smallView.setTextColor(R.id.player_notification_control_artist, paletteUtils.getPlayerNotificationText());
        smallView.setInt(R.id.player_notification_small_background, "setBackgroundColor", paletteUtils.getPlayerNotificationBackground());
        setColorsInButtons(smallView, paletteUtils);

        if (currentVersionSupportBigNotification()) {
            bigView.setTextViewText(R.id.player_notification_control_song, song.getName());
            bigView.setTextViewText(R.id.player_notification_control_album, song.getAlbum());

            bigView.setTextColor(R.id.player_notification_control_song, paletteUtils.getPlayerNotificationText());
            bigView.setTextColor(R.id.player_notification_control_artist, paletteUtils.getPlayerNotificationText());
            bigView.setTextColor(R.id.player_notification_control_album, paletteUtils.getPlayerNotificationText());
            bigView.setInt(R.id.player_notification_big_background, "setBackgroundColor", paletteUtils.getPlayerNotificationBackground());
            setColorsInButtons(bigView, paletteUtils);
        }

        Picasso.with(context).load(song.getUrlImage()).placeholder(R.drawable.img_placeholder).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                smallView.setImageViewBitmap(R.id.player_notification_image, bitmap);

                if (currentVersionSupportBigNotification()) {
                    bigView.setImageViewBitmap(R.id.player_notification_image, bitmap);
                }

                showNotification();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                smallView.setImageViewResource(R.id.player_notification_image, R.drawable.img_placeholder);

                if (currentVersionSupportBigNotification()) {
                    bigView.setImageViewResource(R.id.player_notification_image, R.drawable.img_placeholder);
                }

                showNotification();
            }
        });

        showNotification();
    }

    public void updatePlayPause(boolean isPlaying){
//        smallView.setImageViewResource(R.id.player_notification_control_play, isPlaying ? R.drawable.ic_np_pause : R.drawable.ic_np_play);
//
//        if(currentVersionSupportBigNotification()){
//            bigView.setImageViewResource(R.id.player_notification_control_play,  isPlaying ? R.drawable.ic_np_pause : R.drawable.ic_np_play);
//        }

        showNotification();
    }

    public PlayerNotification showNotification(){
        try {
            notifyManager.notify(NOTIFICATION_PLAYER_ID, notification);
        }catch (Exception e){
            e.printStackTrace();
        }

        return this;
    }

    public PlayerNotification dismiss(){
        notifyManager.cancel(NOTIFICATION_PLAYER_ID);
        return this;
    }

    public Notification getNotification(){
        return notification;
    }

    public void setColorsInButtons(RemoteViews view, PaletteUtils paletteUtils){
        Bitmap sourcePlay = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_np_play).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap resultPlay = paletteUtils.changeBitmapColor(sourcePlay, paletteUtils.getPlayerNotificationText());

        Bitmap sourceNext = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_np_next).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap resultNext = paletteUtils.changeBitmapColor(sourceNext, paletteUtils.getPlayerNotificationText());

        Bitmap sourcePrevious = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_np_previous).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap resultPrevious = paletteUtils.changeBitmapColor(sourcePrevious, paletteUtils.getPlayerNotificationText());

        Bitmap sourceClose = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_search_close).copy(Bitmap.Config.ARGB_8888, true);
        Bitmap resultClose = paletteUtils.changeBitmapColor(sourceClose, paletteUtils.getPlayerNotificationText());

        view.setImageViewBitmap(R.id.player_notification_control_play, resultPlay);
        view.setImageViewBitmap(R.id.player_notification_control_next, resultNext);
        view.setImageViewBitmap(R.id.player_notification_control_previous, resultPrevious);
        view.setImageViewBitmap(R.id.player_notification_control_close, resultClose);
    }

}