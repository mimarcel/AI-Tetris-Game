package Genetic;

public class StartTraining
{
    public static void main(String[] args) {
        GATetris training = new GATetris();
        Population lastGeneration = training.solve();
        System.out.println("Last Generation " + lastGeneration);
    }
}
