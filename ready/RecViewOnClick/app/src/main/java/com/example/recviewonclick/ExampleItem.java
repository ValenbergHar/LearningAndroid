package com.example.recviewonclick;

public class ExampleItem {
private int imageResource;
private String txt1;
private String txt2;

    public ExampleItem(int imageResource, String txt1, String txt2) {
        this.imageResource = imageResource;
        this.txt1 = txt1;
        this.txt2 = txt2;
    }

    public void changeText(String text){
        txt1=text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getTxt1() {
        return txt1;
    }

    public void setTxt1(String txt1) {
        this.txt1 = txt1;
    }

    public String getTxt2() {
        return txt2;
    }

    public void setTxt2(String txt2) {
        this.txt2 = txt2;
    }

    @Override
    public String toString() {
        return "ExampleItem{" +
                "imageResource=" + imageResource +
                ", txt1='" + txt1 + '\'' +
                ", txt2='" + txt2 + '\'' +
                '}';
    }
}
