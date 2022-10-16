package com.weasel.intuitive.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PizzaProperties {

    private Integer maxSlices;
    private Integer pizzaTypes;
    private List<Integer> pizzaValues;

    public List<Pizza> getPizzaChoice() {
        List<Pizza> pizzas = new ArrayList<>();
        for (Integer pizzaValue : pizzaValues) {
            pizzas.add(new Pizza(pizzaValue, pizzaValue));
        }
        return pizzas;
    }

}
