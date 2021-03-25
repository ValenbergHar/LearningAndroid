package com.example.kingsgdl.kings;

import java.io.Serializable;
import java.util.List;

public class King implements Serializable {
    private String id;
    private String kingName;
    private String kingDateLife;
    private String kingOccupation;
    private String link;
    private String kingPhoto;



    public King(String id, String kingName, String kingDateLife, String kingOccupation,String link, String kingPhoto ) {
        this.id = id;
        this.kingName = kingName;
        this.kingDateLife = kingDateLife;
        this.kingOccupation = kingOccupation;
        this.link = link;
        this.kingPhoto = kingPhoto;
    }

    public String getKingPhoto() {
        return kingPhoto;
    }

    public void setKingPhoto(String kingPhoto) {
        this.kingPhoto = kingPhoto;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKingName() {
        return kingName;
    }

    public void setKingName(String kingName) {
        this.kingName = kingName;
    }

    public String getKingDateLife() {
        return kingDateLife;
    }

    public void setKingDateLife(String kingDateLife) {
        this.kingDateLife = kingDateLife;
    }

    public String getKingOccupation() {
        return kingOccupation;
    }

    public void setKingOccupation(String kingOccupation) {
        this.kingOccupation = kingOccupation;
    }
}
