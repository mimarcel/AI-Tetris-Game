package Genetic;

public class PopulationTetrisInitFromString extends PopulationTetris
{
    private String populationData;

    public PopulationTetrisInitFromString(int numberOfChromosomes, String populationData) {
        super(numberOfChromosomes);
        this.populationData = populationData;
    }

    @Override
    public void init() {
        String[] lines = populationData.split("\n");
        for (int i=0; i < lines.length; i++) {
            String line = lines[i];
            line = line.replaceAll(".*\\(","").replaceAll(" \\).*","");
            String[] weights = line.split(" ");
            ChromosomeTetris chromosome = new ChromosomeTetris(NUMBER_OF_PLAYER_WEIGHTS);
            for (int j=0; j < weights.length; j++) {
                chromosome.setWeight(j, Double.parseDouble(weights[j]));
            }
            this.set(i, chromosome);
        }
        this.n = this.chromosomes.length;
    }

    @Override
    public String getPopulationInfo() {
        return "PopulationTetrisInitFromString:\n" + this.populationData;
    }
}
