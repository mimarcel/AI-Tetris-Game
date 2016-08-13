package Genetic;

public abstract class ChromosomeAbstract implements Chromosome
{
    @Override
    public boolean isBetterThan(Chromosome chromosome) {
        return this.compareTo(chromosome) == 1;
    }
}
