package com.weasel.intuitive.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class Individual implements Comparable<Individual> {

    private byte[] chromosome;
    private Integer fitness;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individual)) return false;
        Individual that = (Individual) o;
        return getFitness().equals(that.getFitness());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFitness());
    }

    @Override
    public int compareTo(Individual c) {
        if (this.fitness > c.getFitness()) return 1;
        if (this.fitness.equals(c.getFitness())) return 0;
        if (this.fitness < c.getFitness()) return -1;
        else return -2;
    }

}
