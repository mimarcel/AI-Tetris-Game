

public class Model_Tetris extends Model_Abstract
{
    protected int numberOfPlayers;
    protected Model_Player[] players;
    protected Model_Player_Factory playersFactory;
    protected Model_Tetromino_Factory tetrominosFactory;

    /**
     * Create tetris model.
     * players is an array containing the types of the players (human or robot)
     */
    public Model_Tetris(int[] players, int numberOfLinesTetrisField, int numberOfColumnsTetrisField) {
        this.tetrominosFactory = Model_Tetromino_Factory.getInstance();
        this.createPlayers(players, numberOfLinesTetrisField, numberOfColumnsTetrisField);
    }

    /**
     * Create the players.
     */
    protected void createPlayers(int[] players, int numberOfLines, int numberOfColumns) {
        this.playersFactory = Model_Player_Factory.getInstance();
        this.numberOfPlayers = players.length;
        this.players = new Model_Player[this.numberOfPlayers];
        for (int i = 0; i < this.numberOfPlayers; i++) {
            this.players[i] = playersFactory.createPlayer(players[i], numberOfLines, numberOfColumns);
        }
    }

    /**
     * Get players.
     * @return Model_Player[]
     */
    public Model_Player[] getPlayers() {
        return this.players;
    }

    public String toString() {
        String s = "";
        s += "Players:\n";
        for (Model_Player p : this.getPlayers()) {
            s += "» " + p.toString().replace("\n", "\n\t") + "\n";
        }
        return s;
    }

    /**
     * Get number of tetrominos types.
     */
    public int getNumberOfTetrominos() {
        return this.tetrominosFactory.getNumberOfTetrominoes();
    }

    /**
     * Get factory.
     */
    public Model_Tetromino_Factory getTetrominosFactory() {
        return tetrominosFactory;
    }

    /**
     * Get number of players.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
