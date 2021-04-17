package com.example.hateoas.repositories;

import com.example.hateoas.models.Car;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Repository
public class CarRepository {

    static Long currentId = 4L;

    static final List<Car> cars = new LinkedList<>(Arrays.asList(
            new Car(1L, "Audi", "A4", 2010, "coupe", 15000.00),
            new Car(2L, "Audi", "A5", 2015, "coupe", 55000.50),
            new Car(3L, "Audi", "A6", 2017, "sedan", 75000.50)
    ));

    public List<Car> find() {
        return cars;
    }

    public Car find(Long id) {
        return cars.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    public Car save(Car car){
        car.setId(currentId);
        cars.add(car);
        return car;
    }

    public Car update(Car car, Long id) {
        Car carToUpdate = find(id);

        if (carToUpdate ==null) {
            return null;
        }

        carToUpdate.setMark(car.getMark());
        carToUpdate.setModel(car.getModel());
        carToUpdate.setPrice(car.getPrice());
        carToUpdate.setType(car.getType());
        carToUpdate.setYear(car.getYear());

        return carToUpdate;

    }

    public void delete(Long id) {
        Car car = find(id);
        cars.remove(car);

    }


}
