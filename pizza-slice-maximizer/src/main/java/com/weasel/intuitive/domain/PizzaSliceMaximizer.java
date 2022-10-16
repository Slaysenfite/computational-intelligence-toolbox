package com.weasel.intuitive.domain;

import com.weasel.intuitive.model.Individual;
import com.weasel.intuitive.model.Population;
import com.weasel.intuitive.utils.AlgorithmUtils;
import com.weasel.intuitive.utils.FileUtils;
import com.weasel.intuitive.utils.FitnessCalculator;
import com.weasel.intuitive.utils.HallOfFameQueue;
import com.weasel.intuitive.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static com.weasel.intuitive.utils.FitnessCalculator.INVALID_FITNESS;

@Slf4j
public class PizzaSliceMaximizer {
    private static final String FILENAME = "d_quite_big.in";
    private static final Integer NUM_GENERATIONS_FOR_STAGNATION = 500;

    private AlgorithmUtils algorithmUtils;
    private PizzaProperties pizzaProperties;
    private FitnessCalculator fitnessCalculator;
    private ResultUtils resultUtils;
    private FileUtils fileUtils;

    public PizzaSliceMaximizer() {
        try {
            algorithmUtils = new AlgorithmUtils();
            fitnessCalculator = new FitnessCalculator();
            fileUtils = new FileUtils();
            resultUtils = new ResultUtils();
            pizzaProperties = fileUtils.readDataset(FILENAME);
        } catch (URISyntaxException | IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public PizzaSliceResult maximizePizzaSlices(Integer numGenerations, Integer populationSize) {
        Integer generationCounter = 0;
        HallOfFameQueue<Integer> hallOfFame = new HallOfFameQueue<>(NUM_GENERATIONS_FOR_STAGNATION);

        final Integer capacity = pizzaProperties.getMaxSlices();
        final List<Pizza> pizzaChoice = pizzaProperties.getPizzaChoice();

        Population population = algorithmUtils.initializePopulationRandomlyWithZeroSelections(
                pizzaProperties.getPizzaTypes(),
                populationSize
        );

        Individual generationBest = new Individual(null, 0);

        while (!generationBest.getFitness().equals(pizzaProperties.getMaxSlices())) {
            calculatePopulationFitness(capacity, pizzaChoice, population);
            generationBest = resultUtils.findGenerationBest(population);

            log.info("Best fitness for generation {}: {}", generationCounter, generationBest.getFitness());
            hallOfFame.add(generationBest.getFitness());
            algorithmUtils.updateMutationRate(hallOfFame);

            population = algorithmUtils.reproduceNextGeneration(
                    population.getIndividuals(),
                    populationSize,
                    generationBest);
            generationCounter++;
        }
        Individual bestIndividual = resultUtils.findGenerationBest(population);
        return resultUtils.pizzaSliceResultBuilder(bestIndividual);
    }

    private void calculatePopulationFitness(Integer capacity, List<Pizza> pizzaChoice, Population population) {
        for (Individual individual : population.getIndividuals()) {
            individual.setFitness(
                    fitnessCalculator.calculateFitness(pizzaChoice, individual, capacity)
            );
            resetInvalidIndividuals(individual);
        }
    }

    private void resetInvalidIndividuals(Individual individual) {
        if (individual.getFitness().equals(INVALID_FITNESS)) {
            algorithmUtils.setIndividualToZero(individual);
        }
    }
}
