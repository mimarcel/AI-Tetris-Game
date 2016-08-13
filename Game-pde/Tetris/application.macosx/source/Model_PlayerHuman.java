
public class Model_PlayerHuman extends Model_PlayerAbstract
{
    /**
     * Create player.
     */
    public Model_PlayerHuman(String name, int m, int n) {
        super(name, m, n);
    }

    public int getType() {
        return Model_Player.PLAYER_TYPE_HUMAN;
    }
}
