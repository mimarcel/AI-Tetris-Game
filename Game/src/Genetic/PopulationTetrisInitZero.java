package Genetic;

public class PopulationTetrisInitZero extends PopulationTetris
{

    public PopulationTetrisInitZero(int numberOfChromosomes) {
        super(numberOfChromosomes);
    }

    @Override
    public void init() {
        for (int i = 0; i < this.chromosomes.length; i++) {
            ChromosomeTetris chromosome = new ChromosomeTetris(NUMBER_OF_PLAYER_WEIGHTS);
            this.set(i, chromosome);
        }
        this.n = this.chromosomes.length;
    }

    @Override
    public String getPopulationInfo() {
        return "PopulationTetrisInitZero";
    }
}
