package com.example.carsdb;

import Data.DataBaseHandler;
import Model.Car;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataBaseHandler dataBaseHandler = new DataBaseHandler(this);

       dataBaseHandler.addCar(new Car("MAZ", "2000"));
        dataBaseHandler.addCar(new Car("ZAZ", "200"));
        dataBaseHandler.addCar(new Car("GAZ", "28000"));
        dataBaseHandler.addCar(new Car("GAZ", "2800"));
        dataBaseHandler.addCar(new Car("GdgdgdAZ", "28555500"));

        List<Car> carList = dataBaseHandler.getAllCars();

        /*Car deletingcar = dataBaseHandler.getCar(2);
        dataBaseHandler.deleteCar(deletingcar);*/
        for (Car car : carList) {
            Log.d("Car Info ", "ID " + car.getId() + ", name " + car.getName() + ", price " + car.getPrice());
        }





       /*Car car = dataBaseHandler.getCar(2);

        // for update
        car.setName("Zazik");
        car.setPrice("510");
        dataBaseHandler.updateCar(car);
        Log.d("Car Info ", "ID " + car.getId() + ", name " + car.getName() + ", price " + car.getPrice());

        */

    }
}
