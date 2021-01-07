package com.example.customlistadapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyFriends {
    List<Person> myFriendList;

    public MyFriends(List<Person> myFriendList) {
        this.myFriendList = myFriendList;
    }

    public MyFriends(){
        String[] startingNames = {"Zianon", "Kastus", "Vitaut"};
        this.myFriendList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i <startingNames.length ; i++) {
            Person p = new Person(startingNames[i], random.nextInt(50)+10,random.nextInt(30));
            myFriendList.add(p);
        }
    }

    public List<Person> getMyFriendList() {
        return myFriendList;
    }

    public void setMyFriendList(List<Person> myFriendList) {
        this.myFriendList = myFriendList;
    }
}
