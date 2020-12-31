package com.android.uraall.carsdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import Data.DatabaseHandler;
import Model.Car;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Log.d("CarsCount:", String.valueOf(databaseHandler.getCarsCount()));

        /*databaseHandler.addCar(new Car("Toyota", "30 000 $"));
        databaseHandler.addCar(new Car("Opel", "25 000 $"));
        databaseHandler.addCar(new Car("Mercedes", "50 000 $"));
        databaseHandler.addCar(new Car("KIA", "28 000 $"));
        databaseHandler.addCar(new Car("Mazda", "30 000 $"));
        databaseHandler.addCar(new Car("Honda", "25 000 $"));
        databaseHandler.addCar(new Car("Skoda", "50 000 $"));
        databaseHandler.addCar(new Car("Hundai", "28 000 $"));*/

        List<Car> carList = databaseHandler.getAllCars();



        /*Car deletingCar = databaseHandler.getCar(7);
        databaseHandler.deleteCar(deletingCar);*/

        for (Car car : carList) {
            Log.d("CarInfo:", "ID " + car.getId() + ", name " + car.getName() +
                    ", price " + car.getPrice());
        }

        /*Car car = databaseHandler.getCar(1);

        car.setName("Tesla");
        car.setPrice("50 000 $");

        int updatedCarId = databaseHandler.updateCar(car);

        Log.d("CarInfo:", "ID " + car.getId() + ", name " + car.getName() +
                ", price " + car.getPrice() + ", updatedCarId " + updatedCarId);*/
    }
}
