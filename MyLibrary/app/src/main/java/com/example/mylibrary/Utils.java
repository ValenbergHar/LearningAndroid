package com.example.mylibrary;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;

    public static ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public static ArrayList<Book> getAlreadyReadBooks() {
        return alreadyReadBooks;
    }

    public static ArrayList<Book> getWantToReadBooks() {
        return wantToReadBooks;
    }

    public static ArrayList<Book> getCurrentlyReadBooks() {
        return currentlyReadBooks;
    }

    public static ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    private static ArrayList<Book> currentlyReadBooks;
    private static ArrayList<Book> favoriteBooks;


    private Utils(){
        if(allBooks==null){
            allBooks=new ArrayList<>();
            initData();
        }
        if(alreadyReadBooks==null){
            alreadyReadBooks=new ArrayList<>();
        }
        if(wantToReadBooks==null){
            wantToReadBooks=new ArrayList<>();
        }
        if(currentlyReadBooks==null){
            currentlyReadBooks=new ArrayList<>();
        }
        if(favoriteBooks==null){
            favoriteBooks=new ArrayList<>();
        }

    }

    private void initData() {

        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
        allBooks.add(new Book(1, "IQ48",  "Haruki Murakami", 1350, "https://s1.livelib.ru/boocover/1000712784/o/be77/Haruki_Murakami__1Q84._Tysyacha_Nevestsot_Vosemdesyat_Chetyre._Kniga_3._Oktyabrd.jpeg",
                "fdbsdfbh", "gsfngfn"));
    }

    public static Utils getInstance(){
        if(instance!=null){
            return instance;
        }else {
            instance = new Utils();
            return instance;
        }
    }

    public Book getBookById(int id) {
        for (Book b:allBooks){
            if(b.getId() ==id){
                return b;
            }
        }
        return null;
    }


}
