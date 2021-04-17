package com.example.hateoas.controllers;

import com.example.hateoas.models.Car;
import com.example.hateoas.repositories.CarRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/car")
public class CarController {

    final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @GetMapping
    CollectionModel<EntityModel<Car>> index() {

        List<EntityModel<Car>> cars = carRepository.find()
                .stream()
                .map(m -> EntityModel.of(m, linkTo(methodOn(CarController.class).show(m.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(cars,
                linkTo(methodOn(CarController.class).index()).withSelfRel());
    }

    @GetMapping("{id}")
    ResponseEntity<?> show(@PathVariable Long id) {

        Car car = carRepository.find(id);

        if (car != null) {
            EntityModel<Car> carModel = EntityModel.of(car,
                    linkTo(methodOn(CarController.class).show(id)).withSelfRel(),
                    linkTo(methodOn(CarController.class).index()).withRel("cars"));

            return ResponseEntity.ok().body(carModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    ResponseEntity<?> update(@RequestBody Car movie, @PathVariable Long id) {
        Car updatedCar = carRepository.update(movie, id);

        if (updatedCar != null) {
            EntityModel<Car> movieModel = EntityModel.of(updatedCar,
                    linkTo(methodOn(CarController.class).show(id)).withSelfRel());

            return ResponseEntity.ok().body(movieModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    ResponseEntity<?> save(@RequestBody Car car) {
        Car savedCar = carRepository.save(car);
        if (savedCar != null) {
            EntityModel<Car> carModel = EntityModel.of(savedCar,
                    linkTo(methodOn(CarController.class).show(savedCar.getId())).withSelfRel());

            return ResponseEntity.created(carModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(carModel);
        } else {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping("{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        carRepository.delete(id);
        return ResponseEntity.noContent().build();
    }

}
