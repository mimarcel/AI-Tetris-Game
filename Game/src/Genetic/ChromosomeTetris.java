package Genetic;

import Tetris.Controller.Controller_PlayerRobotGenetic;
import Tetris.Model.Model_Exception;

public class ChromosomeTetris extends ChromosomeDouble
{
    protected double[] weights;
    protected Controller_PlayerRobotGenetic cp;
    protected int numberOfGamesPlayed = 0;
    protected double averageScore = 0;
    protected double averageMoves = 0;

    public Controller_PlayerRobotGenetic getCp() {
        return cp;
    }

    public void setCp(Controller_PlayerRobotGenetic cp) {
        this.cp = cp;
    }

    /**
     * Create chromosome.
     */
    public ChromosomeTetris(int numberOfWeights) {
        this.weights = new double[numberOfWeights];
        for (int i = 0; i < numberOfWeights; i++) {
            this.weights[i] = 0.0;
        }
    }

    /**
     * Copy chromosome.
     */
    public ChromosomeTetris(ChromosomeTetris chromosome) {
        this.weights = new double[chromosome.weights.length];
        for (int i = 0; i < chromosome.weights.length; i++) {
            this.weights[i] = chromosome.weights[i];
        }
        this.averageScore = chromosome.averageScore;
        this.averageMoves = chromosome.averageMoves;
        this.numberOfGamesPlayed = chromosome.numberOfGamesPlayed;
    }

    public double[] getWeights() {
        return weights;
    }

    /**
     * Get weight.
     */
    public double getWeight(int i) {
        return this.weights[i];
    }

    /**
     * Set weight.
     */
    public void setWeight(int i, double weight) {
        this.weights[i] = weight;
    }

    @Override
    public String toString() {
        String s = "";
        s += /*String.format("%7.3f",*/ this.f/*)*/ + " (";
        for (int i = 0; i < this.weights.length; i++) {
            s += /*String.format("%7.3f",*/ this.weights[i]/*)*/ + " ";
        }
        s += ")";
        return s;
    }

    public String getWeightsAsString() {
        String s = "";
        for (int i = 0; i < this.getWeights().length; i++) {
            s += this.getWeight(i) + " ";
        }
        return s;
    }

    public void gamePlayed(long moves, long score) {
        this.averageMoves = (this.averageMoves*this.numberOfGamesPlayed + moves)/(this.numberOfGamesPlayed+1);
        this.averageScore = (this.averageScore*this.numberOfGamesPlayed + score)/(this.numberOfGamesPlayed+1);
        this.numberOfGamesPlayed++;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public double getAverageMoves() {
        return averageMoves;
    }
}
