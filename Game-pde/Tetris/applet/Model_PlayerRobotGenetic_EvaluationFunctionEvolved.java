
public class Model_PlayerRobotGenetic_EvaluationFunctionEvolved extends Model_PlayerRobotGenetic_EvaluationFunction
{

    public double evaluate(
            double[] weights, short[][] grid,
            int maxHeightBefore, int maxHeightAfter,
            int noOfHolesBefore, int noOfHolesAfter,
            int noOfBlockadesBefore, int noOfBlockadesAfter,
            int freeSpaceBefore, int freeSpaceAfter,
            int noOfNeighboursBefore, int noOfNeighboursAfter,
            int noOfClearedLines) {
        return weights[0] * maxHeightAfter / grid.length * this.evaluationLinear(maxHeightAfter - maxHeightBefore, -4, 4)
                + weights[1] * this.evaluationLinear(noOfHolesAfter - noOfHolesBefore, -4, 3)
                + weights[2] * this.evaluationLinear(noOfBlockadesAfter - noOfBlockadesBefore, -10, 4)
                + weights[3] * this.evaluationLinear(freeSpaceAfter - freeSpaceBefore, -10, 10)
                + (
                noOfClearedLines > 0
                        ? 0
                        : weights[4] * this.evaluationLinear(noOfNeighboursAfter - noOfNeighboursBefore, 0, 30)
        )
                + weights[5] * this.evaluationLinear(noOfClearedLines, 0, 2);
    }

    public double evaluationLinear(int value, int min, int max) {
        return ((double) (value - min)) / (max - min);
    }
}
