package com.example.listroom;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
@Dao
public interface PersonDao {
    @Query("select * from person")
    List<Person> getAllPersons();

    @Insert
    void insertAll(Person... persons);

    @Delete
    void delete(Person note);


}
