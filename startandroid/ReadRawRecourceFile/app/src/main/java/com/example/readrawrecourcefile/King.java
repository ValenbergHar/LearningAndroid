package com.example.readrawrecourcefile;

import java.util.List;

public class King {
    private int id;
    private String kingName;
    private String kingDateLife;
    private String kingDateReign;
    private String kingShortHist;
    private String kingLongHist;
    private List<String> kingPhotos;


    public King(int id, String kingName, String kingDateLife, String kingDateReign, String kingShortHist, String kingLongHist, List<String> kingPhotos) {
        this.id = id;
        this.kingName = kingName;
        this.kingDateLife = kingDateLife;
        this.kingDateReign = kingDateReign;
        this.kingShortHist = kingShortHist;
        this.kingLongHist = kingLongHist;
        this.kingPhotos = kingPhotos;
    }

    @Override
    public String toString() {
        return "King{" +
                "id=" + id +
                ", kingName='" + kingName + '\'' +
                ", kingDateLife='" + kingDateLife + '\'' +
                ", kingDateReign='" + kingDateReign + '\'' +
                ", kingShortHist='" + kingShortHist + '\'' +
                ", kingLongHist='" + kingLongHist + '\'' +
                ", kingPhotos=" + kingPhotos +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getKingDateReign() {
        return kingDateReign;
    }

    public void setKingDateReign(String kingDateReign) {
        this.kingDateReign = kingDateReign;
    }

    public String getKingShortHist() {
        return kingShortHist;
    }

    public void setKingShortHist(String kingShortHist) {
        this.kingShortHist = kingShortHist;
    }

    public String getKingLongHist() {
        return kingLongHist;
    }

    public void setKingLongHist(String kingLongHist) {
        this.kingLongHist = kingLongHist;
    }

    public List<String> getKingPhotos() {
        return kingPhotos;
    }

    public void setKingPhotos(List<String> kingPhotos) {
        this.kingPhotos = kingPhotos;
    }
}
