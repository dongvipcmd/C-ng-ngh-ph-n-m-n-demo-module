package com.example.demoCNPM.service.imp;

import com.example.demoCNPM.entity.Car;
import com.example.demoCNPM.repository.CarRepository;
import com.example.demoCNPM.service.interfaces.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getByClientId(Long clientId) {
        return carRepository.findByClientId(clientId);
    }

    @Override
    public Car findById(Long carid) {
        return carRepository.findById(carid)
                .orElseThrow(()->new RuntimeException("car not found"));
    }
}