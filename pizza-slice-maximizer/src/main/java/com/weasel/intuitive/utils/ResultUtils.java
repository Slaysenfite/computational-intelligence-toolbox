package com.weasel.intuitive.utils;

import com.weasel.intuitive.domain.PizzaSliceResult;
import com.weasel.intuitive.model.Individual;
import com.weasel.intuitive.model.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResultUtils {
    public Individual findGenerationBest(Population population) {
        Collections.sort(population.getIndividuals());
        return population.getIndividuals().get(population.getIndividuals().size() - 1);
    }

    public PizzaSliceResult pizzaSliceResultBuilder(Individual bestIndividual) {
        Integer counter = 0;
        List<Integer> pizzaSelectionIndices = new ArrayList<>();
        for (int i = 0; i < bestIndividual.getChromosome().length; i++) {
            if (bestIndividual.getChromosome()[i] == 1) {
                counter++;
                pizzaSelectionIndices.add(i);
            }
        }
        return new PizzaSliceResult(counter, pizzaSelectionIndices);
    }
}
