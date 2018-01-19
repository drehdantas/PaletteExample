package com.project.andredantas.palettewithpicassodemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initSongs();
        toolbar.setTitle(getString(R.string.app_name));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new SongsAdapter(songs, this));
    }

    public void initSongs(){
        Song song1 = new Song(1, "http://www.alternativenation.net/wp-content/uploads/2016/07/517HpPUIsxL.jpg", "Make It Wit Chu", "Era Vulgaris");
        Song song2 = new Song(2, "http://www.metalsucks.net/wp-content/uploads/2013/02/Ghost-BC-Opus.jpg", "Ritual", "Opus Eponymous");
        Song song3 = new Song(3, "http://exclaim.ca/images/kadavar.jpg", "Last Living Dinosaur", "Berlin");
        Song song4 = new Song(4, "https://f4.bcbits.com/img/a2495590154_16.jpg", "Spacebomb", "Black Space Riders");
        Song song5 = new Song(5, "http://4.bp.blogspot.com/-EUADGV_ujl4/T89VEyknxPI/AAAAAAAAA-4/x0pSJ-jRQYs/s1600/Black+Sabbath+-+Paranoid.jpg", "Paranoid", "Black Sabbath");
        Song song6 = new Song(6, "http://www.galeon.com/allmusic/caratulas/m/megadeth_-_rust_in_peace-front.jpg", "Tornado of Souls", "Megadeth");
        Song song7 = new Song(7, "https://upload.wikimedia.org/wikipedia/en/8/8b/Hellbilly_Deluxe.jpg", "Dragula", "Rob Zombie");
        Song song8 = new Song(8, "https://img.discogs.com/Ef4BWMEdU4bFhvtadXhCk-II780=/fit-in/600x600/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-2477142-1450268620-1201.jpeg.jpg", "Infest", "Papa Roach");
        Song song9 = new Song(9, "https://images-na.ssl-images-amazon.com/images/I/31G3XM1ZkZL._SS500.jpg", "Change", "Deftones");
        Song song10 = new Song(10, "https://upload.wikimedia.org/wikipedia/en/5/54/Ratm_renegades.png", "Renegades Of Funk", "Rage Against The Machine");
        Song song11 = new Song(11, "http://i3.mirror.co.uk/incoming/article9457686.ece/ALTERNATES/s615b/Ed-Sheeran.jpg", "Just a square blue", "yep. Just blue");

        songs = new ArrayList<>();
        songs.add(song1);
        songs.add(song2);
        songs.add(song3);
        songs.add(song4);
        songs.add(song5);
        songs.add(song6);
        songs.add(song7);
        songs.add(song8);
        songs.add(song9);
        songs.add(song10);
        songs.add(song11);
    }
}