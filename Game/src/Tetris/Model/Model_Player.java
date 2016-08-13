package Tetris.Model;

public interface Model_Player extends Model
{
    public static final int PLAYER_TYPE_HUMAN = 0;
    public static final int PLAYER_TYPE_ROBOT_RANDOM = 1;
    public static final int PLAYER_TYPE_ROBOT_GENETIC = 2;

    /**
     * Get player name.
     */
    public String getName();

    /**
     * Set player name.
     */
    public void setName(String name);

    /**
     * Player type.
     */
    public int getType();
}
