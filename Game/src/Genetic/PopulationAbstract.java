package Genetic;

import java.util.Arrays;

public abstract class PopulationAbstract implements Population
{
    protected Chromosome[] chromosomes;
    protected int n;

    public PopulationAbstract(int numberOfChromosomes) {
        this.n = 0;
        this.chromosomes = new Chromosome[numberOfChromosomes];
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public Chromosome get(int i) {
        return this.chromosomes[i];
    }

    @Override
    public void set(int i, Chromosome chromosome) {
        this.chromosomes[i] = chromosome;
    }

    @Override
    public Chromosome[] getAllChromosomes() {
        return this.chromosomes;
    }

    @Override
    public void add(Chromosome chromosome) {
        this.chromosomes[n++] = chromosome;
    }

    @Override
    public Chromosome getBestChromosome() {
        Chromosome bestChromosome = null;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isBetterThan(bestChromosome)) {
                bestChromosome = this.get(i);
            }
        }
        return bestChromosome;
    }

    @Override
    public void sortAscending() {
        Arrays.sort(this.chromosomes);
    }

    @Override
    public String toString() {
        String s = "";
        s += "Chromosomes:\n";
        for (int i = 0; i < this.size(); i++) {
            s += String.format("%4s", i + 1) + ": " + this.chromosomes[i] + "\n";
        }
        return s;
    }
}
