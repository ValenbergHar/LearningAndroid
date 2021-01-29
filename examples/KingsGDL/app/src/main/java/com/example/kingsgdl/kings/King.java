package com.example.kingsgdl.kings;

import java.util.Comparator;

public class King {
    private int id;
    private String name;
    private int dateOfElection;
    private String imageUrl;

    public King(int id, String name, int dateOfElection, String imageUrl) {
        this.id = id;
        this.name = name;
        this.dateOfElection = dateOfElection;
        this.imageUrl = imageUrl;
    }

    public static Comparator<King> kingNameAZComparator = new Comparator<King>() {
        @Override
        public int compare(King k1, King k2) {
            return k1.getName().compareTo(k2.getName());
        }
    };

    public static Comparator<King> kingNameZAComparator = new Comparator<King>() {
        @Override
        public int compare(King k1, King k2) {
            return k2.getName().compareTo(k1.getName());
        }
    };

    public static Comparator<King> kingDateAscComparator = new Comparator<King>() {
        @Override
        public int compare(King k1, King k2) {
            return k2.getDateOfElection()-k1.getDateOfElection();
        }
    };

    public static Comparator<King> kingDateDescComparator = new Comparator<King>() {
        @Override
        public int compare(King k1, King k2) {
            return k1.getDateOfElection()-k2.getDateOfElection();
        }
    };



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDateOfElection() {
        return dateOfElection;
    }
    public void setDateOfElection(int dateOfElection) {
        this.dateOfElection = dateOfElection;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "King{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfElection=" + dateOfElection +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
