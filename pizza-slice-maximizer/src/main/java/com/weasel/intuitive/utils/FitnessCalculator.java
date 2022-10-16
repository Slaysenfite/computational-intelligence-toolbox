package com.weasel.intuitive.utils;

import com.weasel.intuitive.domain.Pizza;
import com.weasel.intuitive.model.Individual;

import java.util.List;

public class FitnessCalculator {
    public static final Integer INVALID_FITNESS = -1;

    public Integer calculateFitness(List<Pizza> pizzaChoice, Individual individual, Integer capacity) {
        Integer fitness = 0;
        for (int i = 0; i < pizzaChoice.size(); i++) {
            if (individual.getChromosome()[i] == 1) {
                fitness += pizzaChoice.get(i).getSlices();
            }
        }
        return checkValidityOfFitness(fitness, capacity);
    }

    private Integer checkValidityOfFitness(Integer provisionalFitness, Integer capacity) {
        if (provisionalFitness > capacity) {
            return INVALID_FITNESS;
        } else return provisionalFitness;
    }

}
