package Tetris.Model;

public class Model_PlayerRobotRandom extends Model_PlayerRobot
{
    /**
     * Create random robot player.
     */
    public Model_PlayerRobotRandom(String name, int m, int n) {
        super(name, m, n);
    }

    @Override
    public int getType() {
        return Model_Player.PLAYER_TYPE_ROBOT_RANDOM;
    }
}
