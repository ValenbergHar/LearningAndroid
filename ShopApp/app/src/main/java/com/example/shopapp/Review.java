package com.example.shopapp;

public class Review {
    private int groceryItem;
    private String text;
    private String userName;
    private String date;

    public Review(int groceryItem, String text, String userName, String date) {
        this.groceryItem = groceryItem;
        this.text = text;
        this.userName = userName;
        this.date = date;
    }

    public int getGroceryItem() {
        return groceryItem;
    }

    public void setGroceryItem(int groceryItem) {
        this.groceryItem = groceryItem;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "Review{" +
                "groceryItem=" + groceryItem +
                ", text='" + text + '\'' +
                ", userName='" + userName + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
