

public class Model_PlayerRobotGenetic extends Model_PlayerRobot
{
    protected double[] weights;
    private Model_PlayerRobotGenetic_EvaluationFunction evaluationFunction;
    /**
     * Create genetic robot player.
     */
    public Model_PlayerRobotGenetic(String name, int m, int n) {
        super(name, m, n);
    }

    public int getType() {
        return Model_Player.PLAYER_TYPE_ROBOT_GENETIC;
    }

    /**
     * Set weights.
     */
    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    /**
     * Get weight.
     */
    public double getWeight(int i) {
        return this.weights[i];
    }

    public double[] getWeights() {
        return weights;
    }

    public Model_PlayerRobotGenetic_EvaluationFunction getEvaluationFunction() {
        return evaluationFunction;
    }

    public void setEvaluationFunction(Model_PlayerRobotGenetic_EvaluationFunction evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
    }
}
