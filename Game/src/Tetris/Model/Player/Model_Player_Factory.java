package Tetris.Model.Player;

import Tetris.Model.*;
import Tetris.Model.PlayerRobotGenetic.Model_PlayerRobotGenetic_EvaluationFunctionEvolved;

public class Model_Player_Factory extends Model_Abstract
{
    protected static Model_Player_Factory instance;

    /**
     * Constructor is restricted.
     */
    protected Model_Player_Factory() {

    }

    /**
     * Get singleton player factory object.
     * @return Model_Player_Factory
     */
    public static Model_Player_Factory getInstance() {
        if (Model_Player_Factory.instance == null) {
            Model_Player_Factory.instance = new Model_Player_Factory();
        }
        return Model_Player_Factory.instance;
    }

    /**
     * Create player of the given type.
     */
    public Model_Player createPlayer(int type, int m, int n) {
        switch (type) {
            case Model_Player.PLAYER_TYPE_HUMAN:
                return new Model_PlayerHuman(null, m, n);
            case Model_Player.PLAYER_TYPE_ROBOT_RANDOM:
                return new Model_PlayerRobotRandom(null, m, n);
            case Model_Player.PLAYER_TYPE_ROBOT_GENETIC:
                Model_PlayerRobotGenetic r = new Model_PlayerRobotGenetic(null, m, n);
                //r.setWeights(new double[]{-2, -1, -0.5, 0.5, 1, 1});
                //r.setWeights(new double[]{0.0,-3.313235994096597,0.0,4.642230234681632,1.5168725437827648,0.0});
                r.setWeights(new double[] {-0.7779275967758407, -1.4013782932130776, -0.7700676686917193, 0.6230435945392889, 0.9774635412697881, 2.8671072959961608});
                r.setEvaluationFunction(new Model_PlayerRobotGenetic_EvaluationFunctionEvolved());
                return r;
            default:
                return null;
        }
    }
}
