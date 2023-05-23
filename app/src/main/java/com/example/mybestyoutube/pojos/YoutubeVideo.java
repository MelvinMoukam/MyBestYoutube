package com.example.mybestyoutube.pojos;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class YoutubeVideo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private  long id;
    private String Titre;
    private String Description;
    private String category;
    private String url;

    private int favori;

    // constructeurs

    public YoutubeVideo() {
    }


    // getters et setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return Titre;
    }

    public void setTitre(String title) {
        Titre = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category ) {
        this.category = category ;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFavori() {
        return favori;
    }

    public void setFavori(int favori) {
        this.favori = favori;
    }

    public boolean isExpanded() {
        return false;
    }

    public void setExpanded(boolean b) {
    }
}
