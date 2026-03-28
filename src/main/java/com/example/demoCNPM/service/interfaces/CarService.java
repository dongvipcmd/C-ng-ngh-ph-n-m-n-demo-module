package com.example.demoCNPM.service.interfaces;

import com.example.demoCNPM.entity.Car;

import java.util.List;

public interface CarService {

    List<Car> getAll();

    Car save(Car car);

    List<Car> getByClientId(Long clientId);

    Car findById(Long carid);

}
