package com.weasel.intuitive.utils;

import com.weasel.intuitive.model.Individual;
import com.weasel.intuitive.model.Population;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.weasel.intuitive.utils.AlgorithmParameters.initialMutationDenominator;
import static com.weasel.intuitive.utils.AlgorithmParameters.initialSelectionChance;
import static com.weasel.intuitive.utils.AlgorithmParameters.tournamentSize;

@Slf4j
public class AlgorithmUtils {

    private Integer mutationDenominator = initialMutationDenominator;

    public Integer generateRandomBoundedInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public List<Integer> generateListOfRandomInts(int size, int min, int max) {
        List<Integer> list = new ArrayList<>();
        for (int i = min; i < max; i++)
            list.add(i);
        Collections.shuffle(list);
        return list.subList(0, size);
    }

    public String chromosomeToString(byte[] chromosome) {
        StringBuilder ret = new StringBuilder();
        for (byte gene : chromosome)
            ret.append(gene);
        return ret + "\n";
    }

    public byte[] mutateChromosome(byte[] chromosome) {
        for (int i = 0; i < chromosome.length; i++) {
            int chance = generateRandomBoundedInt(0, this.mutationDenominator);
            if (chance <= 1) {
                mutateGene(chromosome, i);
            }
        }
        return chromosome;
    }

    private void mutateGene(byte[] chromosome, int i) {
        if (chromosome[i] == 0) chromosome[i] = 1;
        else if (chromosome[i] == 1) chromosome[i] = 0;
    }

    public void updateMutationRate(HallOfFameQueue<Integer> hallOfFame) {
        if (hallOfFame.isQueueFull()) {
            if (hallOfFame.numberOfUniqueElements() == 1) {
                this.mutationDenominator -= 500;
                log.info("Increasing mutation rate! \n " +
                        "Mutation denominator is now: {}", this.mutationDenominator);
                hallOfFame.emptyQueue();
            }
            if (hallOfFame.numberOfUniqueElements() >= (hallOfFame.getSize() / 20)) {
                this.mutationDenominator += 500;
                log.info("Decreasing mutation rate! \n" +
                        "Mutation denominator is now: {}", this.mutationDenominator);
                hallOfFame.emptyQueue();
            }
            if (this.mutationDenominator <= 0) {
                this.mutationDenominator = initialMutationDenominator;
            }
        }
    }

    public Population initializePopulationWithRandomSelections(Integer chromosomeSize, Integer populationSize) {
        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            byte[] chromosome = initializeChromosomeRandomly(chromosomeSize);
            individuals.add(new Individual(chromosome, 0));
        }
        return new Population(individuals);
    }

    private byte[] initializeChromosomeRandomly(Integer chromosomeSize) {
        byte[] chromosome = new byte[chromosomeSize];
        for (int c = 0; c < chromosomeSize; c++) {
            Integer chance = generateRandomBoundedInt(0, 100);
            if (chance > initialSelectionChance)
                chromosome[c] = 1;
            else
                chromosome[c] = 0;
        }
        return chromosome;
    }

    public Population initializePopulationRandomlyWithZeroSelections(Integer chromosomeSize, Integer populationSize) {
        List<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            byte[] chromosome = initializeChromosomeWithZeros(chromosomeSize);
            individuals.add(new Individual(chromosome, 0));
        }
        return new Population(individuals);
    }

    private byte[] initializeChromosomeWithZeros(Integer chromosomeSize) {
        byte[] chromosome = new byte[chromosomeSize];
        for (int c = 0; c < chromosomeSize; c++) {
            chromosome[c] = 0;
        }
        return chromosome;
    }

    public Individual setIndividualToZero(Individual individual) {
        Arrays.fill(individual.getChromosome(), (byte) 0);
        return individual;
    }

    public Population reproduceNextGeneration(List<Individual> currentPopulation, Integer populationSize, Individual bestIndividual) {
        List<Individual> nextGeneration = new ArrayList<>();
        nextGeneration.add(bestIndividual);
        while (nextGeneration.size() != populationSize) {
            Pair<Individual, Individual> selectedIndividuals = selectIndividualsForReproduction(currentPopulation);
            Individual offspring = reproduceThroughTwoPointCrossover(
                    selectedIndividuals.getKey().getChromosome(),
                    selectedIndividuals.getValue().getChromosome()
            );
            nextGeneration.add(offspring);
        }
        return new Population(nextGeneration);
    }

    private Individual reproduceThroughTwoPointCrossover(byte[] chromosome1, byte[] chromosome2) {
        byte[] offspring = new byte[chromosome1.length];
        int cPoint1 = generateRandomBoundedInt(0, chromosome1.length - 1);
        int cPoint2 = generateRandomBoundedInt(0, chromosome2.length - 1);

        validateCrossoverPoints(chromosome2, cPoint1, cPoint2);

        if (cPoint1 >= 0)
            System.arraycopy(chromosome1, 0, offspring, 0, cPoint1);
        if (cPoint2 - cPoint1 >= 0)
            System.arraycopy(chromosome2, cPoint1, offspring, cPoint1, cPoint2 - cPoint1);
        if (chromosome1.length - cPoint2 >= 0)
            System.arraycopy(chromosome1, cPoint2, offspring, cPoint2, chromosome1.length - cPoint2);
        return new Individual(mutateChromosome(offspring), 0);
    }

    private void validateCrossoverPoints(byte[] chromosome, int cPoint1, int cPoint2) {
        while (cPoint1 == cPoint2) {
            cPoint2 = generateRandomBoundedInt(cPoint1, chromosome.length);
        }
        if (cPoint1 > cPoint2) {
            int temp = cPoint2;
            cPoint2 = cPoint1;
            cPoint1 = temp;
        }
    }

    private Pair<Individual, Individual> selectIndividualsForReproduction(List<Individual> currentPopulation) {
        ArrayList<Individual> tournamentList = new ArrayList<>();
        List<Integer> tournamentIndices = generateListOfRandomInts(tournamentSize, 0, currentPopulation.size() - 1);
        for (int i = 0; i < tournamentSize; i++) {
            Integer tournamentIndex = tournamentIndices.get(i);
            tournamentList.add(currentPopulation.get(tournamentIndex));
        }
        Collections.sort(tournamentList);
        Collections.reverse(tournamentList);
        return new Pair<>(tournamentList.get(0), tournamentList.get(1));
    }

}
