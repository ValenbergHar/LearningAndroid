package com.example.customlistadapter;

public class Person {
    private String name;
    private int age;
    private int pictureNumber;

    public Person(String name, int age, int pictureNumber) {
        this.name = name;
        this.age = age;
        this.pictureNumber = pictureNumber;
    }

    // compareTo for sorting

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPictureNumber() {
        return pictureNumber;
    }

    public void setPictureNumber(int pictureNumber) {
        this.pictureNumber = pictureNumber;
    }
}
