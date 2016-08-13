package Genetic;

public interface Chromosome extends Comparable
{
    /**
     * Get evaluated value.
     */
    public Object getEvaluation();

    /**
     * Set evaluated value.
     */
    public void setEvaluation(Object evaluation);

    /**
     * Compare with other chromosome.
     */
    public boolean isBetterThan(Chromosome chromosome);
}
