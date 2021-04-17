package com.example.hateoas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.MessageFormat;

@Getter
@Setter
@NoArgsConstructor
public class Car {
    Long id;
    String mark;
    String model;
    Integer year;
    String type;
    Double price;

    public Car(Long id ,String mark, String model, Integer year, String type, Double price) {
        this(mark, model, year, type, price);
        this.id = id;
    }

    public Car(String mark, String model, Integer year, String type, Double price) {
        this.mark = mark;
        this.model = model;
        this.year = year;
        this.type = type;
        this.price = price;
    }

    public String toString() {
        return MessageFormat.format("{0} - {1}, {2}", mark, model, year, type, price);
    }
}
