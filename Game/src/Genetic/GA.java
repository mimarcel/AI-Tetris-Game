package Genetic;

import java.util.Random;

public abstract class GA
{
    protected static Random random = new Random();

    protected Population population;
    protected long numberOfGenerations;

    /**
     * Return the last generation of chromosomes found.
     */
    public Population solve() {
        // Init first generation.
        this.numberOfGenerations = 0;
        this.init();

        // Find solution.
        while (!shouldStop()) {
            this.beforeGeneration();
            this.newGeneration();
            this.numberOfGenerations++;
            this.afterGeneration();
        }

        // At the end of the algorithm.
        this.done();

        // Return solution.
        return this.population;
    }

    /**
     * Get population.
     */
    public Population getPopulation() {
        return this.population;
    }

    /**
     * Init this.population and do any other action needed before the algorithm starts.
     */
    public abstract void init();

    /**
     * Things to do after the result is found.
     */
    public abstract void done();

    /**
     * Stop the life of the chromosomes! (i.e. the algorithm will stop at the next generation).
     */
    public abstract Boolean shouldStop();

    /**
     * Things to do at the start of each new generation.
     */
    public abstract void beforeGeneration();

    /**
     * Generate new generation of chromosomes.
     */
    public abstract void newGeneration();

    /**
     * Things to do at the end of each generation.
     */
    public abstract void afterGeneration();

}
