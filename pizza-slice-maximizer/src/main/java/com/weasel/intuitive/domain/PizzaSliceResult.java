package com.weasel.intuitive.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PizzaSliceResult {
    private Integer numTypesOfPizza;
    private List<Integer> pizzaIndices;

    @Override
    public String toString() {
        return "PizzaSliceResult{\n" +
                "numTypesOfPizza:" + numTypesOfPizza + ",\n" +
                "pizzaIndices:" + pizzaIndices + "\n" +
                '}';
    }

    public String toBasicString() {
        StringBuilder ret = new StringBuilder(numTypesOfPizza + "\n");
        for (Integer pizzaIndex : pizzaIndices) {
            ret.append(pizzaIndex).append(" ");
        }
        return ret.toString();
    }
}
