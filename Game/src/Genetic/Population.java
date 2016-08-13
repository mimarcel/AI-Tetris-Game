package Genetic;

public interface Population
{
    /**
     * Get number of chromosomes.
     */
    public int size();

    /**
     * Get the i-th chromosome.
     */
    public Chromosome get(int i);

    /**
     * Set the i-th chromosome.
     */
    public void set(int i, Chromosome chromosome);

    /**
     * Get all.
     */
    public Chromosome[] getAllChromosomes();

    /**
     * Add new chromosome.
     */
    public void add(Chromosome chromosome);

    /**
     * Init population with random values for chromosomes.
     */
    public void init();

    /**
     * Get the best chromosome from population.
     */
    public Chromosome getBestChromosome();

    /**
     * Sort chromosomes from worst chromosome to best.
     */
    public abstract void sortAscending();

    public abstract String getPopulationInfo();
}
