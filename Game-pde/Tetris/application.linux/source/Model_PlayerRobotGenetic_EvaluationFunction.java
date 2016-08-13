

public abstract class Model_PlayerRobotGenetic_EvaluationFunction extends Model_Abstract
{
    public abstract double evaluate(
            double[] weights, short[][] grid,
            int maxHeightBefore, int maxHeightAfter,
            int noOfHolesBefore, int noOfHolesAfter,
            int noOfBlockadesBefore, int noOfBlockadesAfter,
            int freeSpaceBefore, int freeSpaceAfter,
            int noOfNeighboursBefore, int noOfNeighboursAfter,
            int noOfClearedLines
    );
}
