package com.project.andredantas.palettewithpicassodemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Andre Dantas on 4/10/17.
 */

public class SongsAdapter  extends RecyclerView.Adapter<SongsAdapter.SongHolder>{
    private List<Song> songs;
    private Context context;

    public SongsAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongHolder(LayoutInflater.from(context).inflate(R.layout.song_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SongHolder holder, int position) {
        Song song = songs.get(position);
        holder.albumName.setText(song.getAlbum());
        holder.songName.setText(song.getName());
        Picasso.with(context).load(song.getUrlImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.item_adapter_imageview)
        ImageView imageView;
        @Bind(R.id.item_adapter_song_name)
        TextView songName;
        @Bind(R.id.item_adapter_album_name)
        TextView albumName;

        public SongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.item_adapter_card_view)
        public void songItemClick(){
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", songs.get(getAdapterPosition()));
            context.startActivity(new Intent(context, SongActivity.class).putExtras(bundle));
        }
    }
}
