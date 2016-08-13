package Genetic;

public abstract class PopulationTetris extends PopulationAbstract
{
    public int NUMBER_OF_PLAYER_WEIGHTS = 6;
    public double[] WEIGHT_MIN = new double[]{-5, -5, -5, 0, 0, 0};
    public double[] WEIGHT_MAX = new double[]{0, 0, 0, 5, 5, 5};

    public PopulationTetris(int numberOfChromosomes) {
        super(numberOfChromosomes);
    }

    @Override
    public ChromosomeTetris get(int i) {
        return (ChromosomeTetris)super.get(i);
    }
}
