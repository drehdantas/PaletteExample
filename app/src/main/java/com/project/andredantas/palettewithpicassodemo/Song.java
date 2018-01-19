package com.project.andredantas.palettewithpicassodemo;

import java.io.Serializable;

/**
 * Created by Andre Dantas on 4/10/17.
 */

public class Song implements Serializable {
    private long id;
    private String urlImage;
    private String name;
    private String album;

    public Song(){

    }

    public Song(long id, String urlImage, String name, String album) {
        this.id = id;
        this.urlImage = urlImage;
        this.name = name;
        this.album = album;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
