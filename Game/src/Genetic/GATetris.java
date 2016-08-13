package Genetic;

import Genetic.Genetic_GATetris.GATetris_ThreadLogger;
import Genetic.Genetic_GATetris.GaTetris_ThreadGameManager;
import Tetris.Controller.Controller_PlayerAbstract;
import Tetris.Controller.Controller_PlayerRobotGenetic;
import Tetris.Controller.Controller_Tetris;
import Tetris.Model.Model_Exception;
import Tetris.Model.Model_Player;
import Tetris.StartGame;
import Tetris.View.View_Tetris;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class GATetris extends GAForGames
{
    public boolean LOG_OPERATORS_OF_EVOLUTION = true;
    public boolean LOG_EACH_GAME = false; // Do not turn on if NUMBER_OF_GAME_MANAGER_THREADS != 1 . Replace 'if (false) this.log' with 'this.log' in GATetris if needed to work.
    public boolean LOG_EACH_MOVE = false; // Do not turn on if NUMBER_OF_GAME_MANAGER_THREADS != 1 . Replace 'if (false) this.log' with 'this.log' in Controller_PlayerRobotGenetic if needed to work.
    public boolean LOG_EACH_MOVE_IN_ADVANCE = false; // Do not turn on if NUMBER_OF_GAME_MANAGER_THREADS != 1 . Replace 'if (false) this.log' with 'this.log' in Controller_PlayerRobotGenetic if needed to work.
    public long TIME_TO_LOG_PROGRESS = 30000; // Logs progress from time to time.
    public int NUMBER_OF_GAME_MANAGER_THREADS = Math.max(1, Math.min(1, NUMBER_OF_PLAYERS));
    public long TIME_TO_SLEEP_FOR_GAME_MANAGER_AFTER_ONE_MOVE = 0;
    public long TIME_FOR_MAIN_THREAD_TO_WAIT_FOR_GAME_MANAGERS = 500;

    public long MAX_NUMBER_OF_GENERATIONS = 1000;
    protected double MIN_PERCENTAGE_OF_MAXIMUM_OF_MOVES_TO_ASK_PLAYERS_TO_PLAY_FOR_NEXT_GENERATION = Double.MAX_VALUE;//1.5;
    public double WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION = 0.5;
    public double MUTATION_TYPE_UNIFORM_PROBABILITY_TO_CHANGE_WEIGHT = 0.5;
    public double CROSSOVER_TYPE_INTERMEDIATE_ALPHA = 0.3;

    public static final int MUTATION_TYPE_UNIFORM = 1;
    public static final int MUTATION_TYPE_NONUNIFORM = 2;
    public static final int MUTATION_TYPE_DEFAULT = MUTATION_TYPE_UNIFORM;
    protected double MUTATION_TYPE_UNIFORM_PROBABILITY_TO_PICK = 0;//0.1;
    protected double MUTATION_TYPE_NONUNIFORM_PROBABILITY_TO_PICK = 1 - MUTATION_TYPE_UNIFORM_PROBABILITY_TO_PICK;
    protected long FORCE_UNIFORM_MUTATION_FOR_FIRST_GENERATIONS = 0;//5;
    protected int mutationToUse = MUTATION_TYPE_DEFAULT;

    public String mutationTypeToString(int type) {
        switch (type) {
            case MUTATION_TYPE_UNIFORM:
                return "Uniform mutation";
            case MUTATION_TYPE_NONUNIFORM:
                return "Nonuniform mutation";
        }
        return null;
    }

    public static final int CROSSOVER_TYPE_DISCREET = 1;
    public static final int CROSSOVER_TYPE_INTERMEDIATE_SINGULAR = 2;
    public static final int CROSSOVER_TYPE_INTERMEDIATE_COMPLETE = 3;
    public static final int CROSSOVER_TYPE_DEFAULT = CROSSOVER_TYPE_INTERMEDIATE_COMPLETE;
    protected double CROSSOVER_TYPE_DISCREET_PROBABILITY_TO_PICK = 0.3;
    protected double CROSSOVER_TYPE_INTERMEDIATE_SINGULAR_PROBABILITY_TO_PICK = 0.5;
    protected double CROSSOVER_TYPE_INTERMEDIATE_COMPLETE_PROBABILITY_TO_PICK = 0.2;
    protected int crossoverToUse = CROSSOVER_TYPE_DEFAULT;

    public String crossoverTypeToString(int type) {
        switch (type) {
            case CROSSOVER_TYPE_DISCREET:
                return "Discreet crossover";
            case CROSSOVER_TYPE_INTERMEDIATE_SINGULAR:
                return "Intermediate singular crossover";
            case CROSSOVER_TYPE_INTERMEDIATE_COMPLETE:
                return "Intermediate complete crossover";
        }
        return null;
    }

    public Controller_Tetris controllerTetris;
    protected long numberOfTetrominoes;
    protected int[] playerTypes, playerTypesLength2, playerTypesLength3;
    public int currentPlayerOnMove = -1;
    public int numberOfPlayersWhichDidTheirMove = 0;
    public int numberOfMovesForCurrentGame = -1;
    protected long remainingMaxMoves = -1;
    protected Chromosome[] lastPlayersWhichPlayedAGame;
    protected GaTetris_ThreadGameManager[] gameManagers;
    protected Object gameManageLocker = new Object();
    public Controller_PlayerRobotGenetic lastPlayerWhichMoved = null;
    protected static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    protected long maxNumberOfMoves = 0, maxScore = 0, maxNumberOfMovesFromPreviousGeneration = 0;
    protected Controller_PlayerRobotGenetic bestPlayerFromLastGeneration;
    protected Controller_PlayerRobotGenetic bestPlayerMaxScore;
    protected Controller_PlayerRobotGenetic bestPlayerMaxMoves;


    public String getParamsOfTrainingAsString() {
        return "MAX_NUMBER_OF_GENERATIONS = " + MAX_NUMBER_OF_GENERATIONS + "\n"
                + "NUMBER_OF_PLAYERS = " + NUMBER_OF_PLAYERS + "\n"
                + "Population : " + this.population.getPopulationInfo() + "\n"
                + "NUMBER_OF_PLAYER_WEIGHTS = " + this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS + "\n"
                + "WEIGHT_MIN = " + this.getWeightsMin() + "\n"
                + "WEIGHT_MAX = " + this.getWeightsMax() + "\n"
                + "NUMBER_OF_GAME_MANAGER_THREADS = " + NUMBER_OF_GAME_MANAGER_THREADS + "\n"
                + "MIN_PERCENTAGE_OF_MAXIMUM_OF_MOVES_TO_ASK_PLAYERS_TO_PLAY_FOR_NEXT_GENERATION = " + MIN_PERCENTAGE_OF_MAXIMUM_OF_MOVES_TO_ASK_PLAYERS_TO_PLAY_FOR_NEXT_GENERATION + "\n\n"

                + "Controller_PlayerRobotGenetic.EVALUATION_CURRENT_GRID_IMPORTANCE = " + Controller_PlayerRobotGenetic.EVALUATION_CURRENT_GRID_IMPORTANCE + "\n"
                + "Controller_PlayerRobotGenetic.EVALUATION_NEXT_TETROMINO_IMPORTANCE = " + Controller_PlayerRobotGenetic.EVALUATION_NEXT_TETROMINO_IMPORTANCE + "\n"
                + "Controller_PlayerRobotGenetic.EVALUATION_ALL_TETROMINOES_IMPORTANCE = " + Controller_PlayerRobotGenetic.EVALUATION_ALL_TETROMINOES_IMPORTANCE + "\n"
                + "Controller_PlayerRobotGenetic.ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE = " + Controller_PlayerRobotGenetic.DEFAULT_ACCURACY_HOW_MANY_POSITIONS_TO_CHECK_IN_ADVANCE_PERCENTAGE + "\n\n"

                + "MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION = " + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION + "\n"
                + "MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION = " + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION + "\n"
                + "MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER = " + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER + "\n"
                + "MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = " + MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER + "\n"
                + "CROSSOVER_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = " + CROSSOVER_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER + "\n"
                + "CROSSOVER_MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = " + CROSSOVER_MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER + "\n\n"

                + "MUTATION_TYPE_UNIFORM_PROBABILITY_TO_PICK = " + MUTATION_TYPE_UNIFORM_PROBABILITY_TO_PICK + "\n"
                + "MUTATION_TYPE_NONUNIFORM_PROBABILITY_TO_PICK = " + MUTATION_TYPE_NONUNIFORM_PROBABILITY_TO_PICK + "\n"
                + "FORCE_UNIFORM_MUTATION_FOR_FIRST_GENERATIONS = " + FORCE_UNIFORM_MUTATION_FOR_FIRST_GENERATIONS + "\n"
                + "MUTATION_TYPE_UNIFORM_PROBABILITY_TO_CHANGE_WEIGHT = " + MUTATION_TYPE_UNIFORM_PROBABILITY_TO_CHANGE_WEIGHT + "\n"
                + "WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION = " + WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION + "\n\n"

                + "CROSSOVER_TYPE_DISCREET_PROBABILITY_TO_PICK = " + CROSSOVER_TYPE_DISCREET_PROBABILITY_TO_PICK + "\n"
                + "CROSSOVER_TYPE_INTERMEDIATE_SINGULAR_PROBABILITY_TO_PICK = " + CROSSOVER_TYPE_INTERMEDIATE_SINGULAR_PROBABILITY_TO_PICK + "\n"
                + "CROSSOVER_TYPE_INTERMEDIATE_COMPLETE_PROBABILITY_TO_PICK = " + CROSSOVER_TYPE_INTERMEDIATE_COMPLETE_PROBABILITY_TO_PICK + "\n"
                + "CROSSOVER_TYPE_INTERMEDIATE_ALPHA = " + CROSSOVER_TYPE_INTERMEDIATE_ALPHA + "\n\n"

                + "LOG_OPERATORS_OF_EVOLUTION = " + LOG_OPERATORS_OF_EVOLUTION + "\n"
                + "LOG_EACH_GAME = " + LOG_EACH_GAME + "\n"
                + "LOG_EACH_MOVE = " + LOG_EACH_MOVE + "\n"
                + "LOG_EACH_MOVE_IN_ADVANCE = " + LOG_EACH_MOVE_IN_ADVANCE + "\n"
                + "TIME_TO_LOG_PROGRESS = " + TIME_TO_LOG_PROGRESS + "\n"
                ;
    }

    private String getWeightsMin() {
        String s = "";
        for (double d : this.getPopulation().WEIGHT_MIN) {
            s += d + " ";
        }
        return s;
    }

    private String getWeightsMax() {
        String s = "";
        for (double d : this.getPopulation().WEIGHT_MAX) {
            s += d + " ";
        }
        return s;
    }

    @Override
    public PopulationTetris getPopulation() {
        return (PopulationTetris) super.getPopulation();
    }


    public int getCurrentPlayerOnMove() {
        return currentPlayerOnMove;
    }

    @Override
    public Population solve() {
        if (!LOG_EACH_GAME) {
            // If games details are not logged, then log progress from time to time using a thread.
            new GATetris_ThreadLogger(this).start();
        }
        return super.solve();
    }

    @Override
    public void init() {
        super.init();
//        this.population = new PopulationTetrisInitFromString(NUMBER_OF_PLAYERS,
//                "" +
//                        "    0 |            Robot Genetic 208 | 5886 moves | 2342 score | 0.0 (-2.228129052401171 -4.292373840296895 -1.9106506809859622 2.8582900586370403 2.879513591426213 2.7958314687157095 )\n" +
//                        "    1 |            Robot Genetic 203 | 8240 moves | 3283 score | 0.31156552730484033 (-1.8349893986256205 -2.9134111501045554 -3.5102632075959974 2.7563399733125573 2.512439292036853 2.4696963002611083 )\n" +
//                        "    2 |            Robot Genetic 210 | 15180 moves | 6057 score | 1.2300458424111242 (-1.8349893986256205 -4.292373840296895 -1.9106506809859622 3.010511240930829 2.879513591426213 2.7958314687157095 )\n" +
//                        "    3 |            Robot Genetic 206 | 17608 moves | 7030 score | 1.5521264554059568 (-4.743015459469993 -1.735664505086103 -1.9644831407589889 2.54719084248849 2.344347955119699 2.443820673130009 )\n" +
//                        "    4 |            Robot Genetic 205 | 25969 moves | 10373 score | 2.6589710546702885 (-2.189572098020321 -2.296385209167301 -3.6006643048076867 3.4027397039279172 3.293122069189791 2.443820673130009 )\n" +
//                        "    5 |            Robot Genetic 204 | 27886 moves | 11141 score | 2.91320026521071 (-3.0169317299412883 -2.296385209167301 -2.7725576291350413 3.616461627597947 2.946609618715849 2.443820673130009 )\n" +
//                        "    6 |            Robot Genetic 209 | 35505 moves | 14188 score | 3.9220188938008764 (-2.556953593512489 -2.296385209167301 -3.6006643048076867 3.4027397039279172 2.8996423996131035 2.443820673130009 )\n" +
//                        "    7 |            Robot Genetic 201 | 39277 moves | 15694 score | 4.4207170834700955 (-2.556953593512489 -3.541815039342452 -3.6006643048076867 2.2524207274962063 2.9466096187158484 2.443820673130009 )\n" +
//                        "    8 |            Robot Genetic 202 | 41711 moves | 16670 score | 4.743771071160728 (-3.2495301912926067 -2.296385209167301 -2.7725576291350413 3.616461627597947 2.946609618715849 2.443820673130009 )\n" +
//                        "    9 |            Robot Genetic 207 | 81402 moves | 32546 score | 10.0 (-3.0169317299412883 -2.296385209167301 -3.6006643048076867 3.616461627597947 2.946609618715849 2.443820673130009 )");
//        this.population = new PopulationTetrisInitZero(NUMBER_OF_PLAYERS);
        this.population = new PopulationTetrisInitFromString(NUMBER_OF_PLAYERS,
                "-0.7463702983468092 -0.6204272855664469 -4.772396962933903 4.3449783489821945 2.839652628013164 2.5851323029525437\n"
        );
        this.getPopulation().init();
        this.log(this.getParamsOfTrainingAsString());

        this.numberOfTetrominoes = 0;

        this.playerTypes = new int[NUMBER_OF_PLAYERS];
        for (int i = 0; i < playerTypes.length; i++) {
            this.playerTypes[i] = Model_Player.PLAYER_TYPE_ROBOT_GENETIC;
        }
        this.playerTypesLength2 = new int[2];
        for (int i = 0; i < playerTypesLength2.length; i++) {
            this.playerTypesLength2[i] = Model_Player.PLAYER_TYPE_ROBOT_GENETIC;
        }
        this.playerTypesLength3 = new int[3];
        for (int i = 0; i < playerTypesLength3.length; i++) {
            this.playerTypesLength3[i] = Model_Player.PLAYER_TYPE_ROBOT_GENETIC;
        }

        this.createGameManagers();
    }

    protected void createGameManagers() {
        if (NUMBER_OF_GAME_MANAGER_THREADS <= 1) {
            return;
        }
        this.gameManagers = new GaTetris_ThreadGameManager[NUMBER_OF_GAME_MANAGER_THREADS];
        for (int i = 0; i < this.gameManagers.length; i++) {
            this.gameManagers[i] = new GaTetris_ThreadGameManager(this);
            this.gameManagers[i].start();
        }
    }

    public Object getGameManageLocker() {
        return gameManageLocker;
    }

    public String logIntro() {
        try {
            return String.format(
                    "[%s Generation #%d%s%s%s]",
                    dateFormat.format(Calendar.getInstance().getTime()),
                    this.numberOfGenerations + 1,
                    (this.mutationPhase != -1
                            ? " - Mutation " + this.getPopulation().get(this.mutationPhase).getCp().getModel().getName()
                            : (this.crossoverAndMutationPhase != -1
                            ? " - Crossover&Mutation " + this.getPopulation().get(this.crossoverAndMutationPhase).getCp().getModel().getName()
                            : (
                            this.crossoverPhase != -1
                                    ? " - Crossover " + this.getPopulation().get(this.crossoverPhase).getCp().getModel().getName()
                                    : ""
                    )
                    )
                    ),
                    (this.numberOfMovesForCurrentGame != -1
                            ? " - Move #" + this.numberOfMovesForCurrentGame
                            : ""
                    ),
                    (this.controllerTetris != null && this.controllerTetris.isGameStarted() && lastPlayerWhichMoved != null
                            ? " - " + lastPlayerWhichMoved.getPlayerName() + " - Score " + lastPlayerWhichMoved.getScore()
                            : ""
                    )
            );
        } catch (Model_Exception e) {
            this.handleEasyError(e);
            return "";
        }
    }

    @Override
    public boolean log(String message) {
        if (!LOG_EACH_GAME && this.controllerTetris != null && this.controllerTetris.isGameStarted()) {
            return false;
        }
        if (!LOG_OPERATORS_OF_EVOLUTION && (
                this.mutationPhase != -1
                        || this.crossoverAndMutationPhase != -1
                        || this.crossoverPhase != -1
        )) {
            return false;
        }
        super.log(message);
        return true;
    }

    public void forceLog(String message) {
        super.log(message);
    }

    @Override
    public Boolean shouldStop() {
        return this.numberOfGenerations >= MAX_NUMBER_OF_GENERATIONS;
    }

    @Override
    public void beforeGeneration() {
        System.out.print("Generation " + (this.numberOfGenerations + 1));
        super.beforeGeneration();
        this.maxNumberOfMovesFromPreviousGeneration = this.maxNumberOfMoves;
        this.logProgressStats();
    }

    @Override
    public void afterGeneration() {
        this.bestPlayerFromLastGeneration = ((ChromosomeTetris) this.getPopulation().get(NUMBER_OF_PLAYERS - 1)).getCp();
        System.out.println(" " + bestPlayerFromLastGeneration.getWeightsAsString());
        super.afterGeneration();
    }

    protected void logProgressStats() {
        this.log(String.format(
                "\n%s\n" +
                        "%d chromosomes replaced through mutation so far\n" +
                        "%d chromosomes replaced through crossover&mutation so far\n" +
                        "%d chromosomes replaced through crossover so far\n" +
                        "Best player of previous generation: %d moves | %d score | %s\n" +
                        "Max Number of Moves so far        : %d moves | %s\n" +
                        "Max Score so far                  : %d score | %s\n",
                this.logIntro(),
                this.mutationsAllGenerations, this.crossoversAndMutationsAllGenerations, this.crossoversAllGenerations,
                (this.bestPlayerFromLastGeneration != null
                        ? this.bestPlayerFromLastGeneration.getNumberOfMoves()
                        : 0),
                (this.bestPlayerFromLastGeneration != null
                        ? this.bestPlayerFromLastGeneration.getScore()
                        : 0),
                (this.bestPlayerFromLastGeneration != null
                        ? this.bestPlayerFromLastGeneration.getModel().getName() + " | " + this.bestPlayerFromLastGeneration.getWeightsAsString()
                        : "-"),
                this.maxNumberOfMoves,
                (this.bestPlayerMaxMoves != null
                        ? this.bestPlayerMaxMoves.getModel().getName() + " | " + this.bestPlayerMaxMoves.getWeightsAsString()
                        : "-"),
                this.maxScore,
                (this.bestPlayerMaxScore != null
                        ? this.bestPlayerMaxScore.getModel().getName() + " | " + this.bestPlayerMaxScore.getWeightsAsString()
                        : "-")
        )
        );
    }

    @Override
    protected void mainGame() {
        super.mainGame();

        // Sort population.
        this.population.sortAscending();
        this.logGameStats();
    }

    @Override
    public void generateNewGame(Chromosome[] players) {
        synchronized (this.getGameManageLocker()) {
            try {
                // Init game.
                View_Tetris view = new View_Tetris(new StartGame());
                view.GAME_STATE = View_Tetris.GAME_STATE_IN_GENETIC_TRAINING;
                this.controllerTetris = new Controller_Tetris(view);

                // Set params for game in training.
                this.controllerTetris.STOP_GAME_ON_FIRST_PLAYER_GAME_OVER = false;
                this.controllerTetris.setLogger(this);
                this.controllerTetris.setLogEachGame(LOG_EACH_MOVE);
                this.controllerTetris.setLogEachGameInAdvanceDetails(LOG_EACH_MOVE_IN_ADVANCE);

                // Set static params for game in training.
                Controller_PlayerAbstract.PROBABILITY_TO_ADD_BAD_LINES = 0;
                Controller_PlayerRobotGenetic.counter = this.numberOfGenerations * NUMBER_OF_PLAYERS;

                // Start game.
                if (players.length == 2) {
                    this.controllerTetris.startGame(playerTypesLength2);
                } else if (players.length == 3) {
                    this.controllerTetris.startGame(playerTypesLength3);
                } else {
                    this.controllerTetris.startGame(playerTypes);
                }

                // Associate player with chromosome and chromosome with player.
                for (int i = 0; i < this.controllerTetris.getNumberOfPlayers(); i++) {
                    Controller_PlayerRobotGenetic cp = (Controller_PlayerRobotGenetic) this.controllerTetris.getPlayerControllers()[i];
                    cp.MIN_TIME_TO_MOVE = 0;
                    ChromosomeTetris ct = ((ChromosomeTetris) players[i]);
                    if (ct.getCp() != null) {
                        cp.getModel().setName(ct.getCp().getPlayerName());
                    } else {
                        cp.getModel().setName(cp.getPlayerName());
                        if (cp.getModel().getName().equals("Robot Genetic")) {
                            cp.getModel().setName("Robot Genetic 1");
                        }
                    }
                    cp.getModel().setWeights(ct.getWeights());
                    ct.setCp(cp);
                }

                // Set the name of players.
                if (this.mutationPhase != -1) {
                    this.controllerTetris.getPlayerControllers()[0].getModel().setName(this.getPopulation().get(this.mutationPhase - NUMBER_OF_PLAYERS + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION).getCp().getModel().getName());
                    this.controllerTetris.getPlayerControllers()[1].getModel().setName("Mutant " + this.getPopulation().get(this.mutationPhase).getCp().getModel().getName());
                } else if (this.crossoverAndMutationPhase != -1) {
                    this.controllerTetris.getPlayerControllers()[0].getModel().setName(this.getPopulation().get(this.crossoverAndMutationPhase).getCp().getModel().getName());
                    this.controllerTetris.getPlayerControllers()[1].getModel().setName("Mutant Chromosome Child 1");
                    this.controllerTetris.getPlayerControllers()[2].getModel().setName("Mutant Chromosome Child 2");
                } else if (this.crossoverPhase != -1) {
                    this.controllerTetris.getPlayerControllers()[0].getModel().setName(this.getPopulation().get(this.crossoverPhase).getCp().getModel().getName());
                    this.controllerTetris.getPlayerControllers()[1].getModel().setName("Chromosome Child 1");
                    this.controllerTetris.getPlayerControllers()[2].getModel().setName("Chromosome Child 2");
                }
            } catch (Exception ex) {
                this.handleFatalError(ex);
            }
        }
    }

    @Override
    protected void playersPlayTheGameAndEvaluate(Chromosome[] players) {
        this.log("\n");

        if (NUMBER_OF_GAME_MANAGER_THREADS > 1) {
            this.playersPlayTheGameAndEvaluateUsingThreads(players);
        } else {
            // Play the game until it's over.
            this.numberOfMovesForCurrentGame = 0;
            this.currentPlayerOnMove = -1;
            this.remainingMaxMoves = -1;
            this.controllerTetris.setGameStarted(true);
            do {
                this.numberOfMovesForCurrentGame++;
                this.numberOfPlayersWhichDidTheirMove = 0;

                for (int i = 0; i < this.controllerTetris.getNumberOfPlayers(); i++) {
                    try {
                        this.currentPlayerOnMove = i;
                        Controller_PlayerRobotGenetic cp = (Controller_PlayerRobotGenetic) this.controllerTetris.getPlayerControllers()[i];
                        this.lastPlayerWhichMoved = cp;

                        // Player's action.
                        while (!cp.isGameOver() && cp.getNumberOfMoves() < this.numberOfMovesForCurrentGame) {
                            if (!cp.isGameOver() && cp.isReady()) {
                                if (false) {
                                    this.log(this.logIntro());
                                }
                                cp.findNextMove();
                                cp.makeNextMove();
                            }

                            // Check what happens.
                            if (!cp.isGameOver()) {
                                cp.getGridController().checkCurrentTetromino();

                                if (!cp.isGameOver()) {
                                    // Update grid.
                                    int clearedLines = cp.getGridController().updateCurrentGrid();

                                    // Update score.
                                    cp.updateScore(clearedLines);
                                    //cp.playerUpdatedGrid(clearedLines);

                                    if (false) {
                                        this.log(String.format("%sScore: %d\n\n", cp.toString(), cp.getScore()));
                                    }
                                } else {
                                    if (false) {
                                        this.log(String.format("%sGame Over %s.\n\n", cp.toString(), cp.getPlayerName()));
                                    }
                                }
                            }
                        }
                        this.numberOfPlayersWhichDidTheirMove++;
                    } catch (Exception ex) {
                        this.handleEasyError(ex);
                    }
                }

                this.checkIfShouldForceGameOver();

            } while (!controllerTetris.isGameOver());
            this.controllerTetris.setGameStarted(false);
            this.currentPlayerOnMove = -1;
            this.numberOfMovesForCurrentGame = -1;
            this.numberOfPlayersWhichDidTheirMove = 0;
        }

        this.log("\n" + this.logIntro() + "\nGame over!\n");

        this.evaluatePlayers(players);
    }

    private void playersPlayTheGameAndEvaluateUsingThreads(Chromosome[] players) {
        synchronized (this.getGameManageLocker()) {
            this.numberOfMovesForCurrentGame = 0;
            this.remainingMaxMoves = -1;
            this.controllerTetris.setGameStarted(true);
        }
        do {
            synchronized (this.getGameManageLocker()) {
                this.numberOfMovesForCurrentGame++;
                this.currentPlayerOnMove = -1;
                this.numberOfPlayersWhichDidTheirMove = 0;
            }

            while (this.numberOfPlayersWhichDidTheirMove < this.controllerTetris.getNumberOfPlayers()) {
                try {
                    Thread.sleep(TIME_FOR_MAIN_THREAD_TO_WAIT_FOR_GAME_MANAGERS);
                } catch (InterruptedException e) {
                    this.handleEasyError(e);
                }
            }

            synchronized (this.getGameManageLocker()) {
                this.checkIfShouldForceGameOver();
            }

        } while (!controllerTetris.isGameOver());
        synchronized (this.getGameManageLocker()) {
            this.controllerTetris.setGameStarted(false);
            this.currentPlayerOnMove = -1;
            this.numberOfPlayersWhichDidTheirMove = 0;
            this.numberOfMovesForCurrentGame = -1;
        }
    }

    private void checkIfShouldForceGameOver() {
        if (MIN_PERCENTAGE_OF_MAXIMUM_OF_MOVES_TO_ASK_PLAYERS_TO_PLAY_FOR_NEXT_GENERATION == Double.MAX_VALUE) {
            return;
        }
        // Set game over if there are were enough players finishing.
        if (this.remainingMaxMoves == -1 && this.enoughPlayersDidPlayTheGame() && !this.controllerTetris.isGameOver()) {
            this.remainingMaxMoves = this.getRemainingMoves();
            this.lastPlayerWhichMoved = null;
            this.forceLog(this.logIntro() + "\nSet max remaining moves: " + this.remainingMaxMoves + ".\n");
        }
        if (this.remainingMaxMoves == -1
                && this.controllerTetris.getPlayersGameOver() == this.controllerTetris.getNumberOfPlayers() - 1) {
            this.remainingMaxMoves = this.getRemainingMoves();
            this.lastPlayerWhichMoved = null;
            this.forceLog(this.logIntro() + "\nSet max remaining moves: " + this.remainingMaxMoves + ".\n");
        }

        if (this.remainingMaxMoves >= 0) {
            if (this.remainingMaxMoves > 0) {
                this.remainingMaxMoves--;
            } else {
                this.lastPlayerWhichMoved = null;
                this.forceLog(this.logIntro() + "\nForce game over.\n");
                this.controllerTetris.setGameOver(true);
            }
        }
    }

    protected long getRemainingMoves() {
//        if (this.mutationPhase != -1 || this.crossoverPhase != -1 || this.crossoverAndMutationPhase != -1) {
//            return 10;
//        }
        return (long)
                Math.max(10,
                        (this.maxNumberOfMovesFromPreviousGeneration
                                * MIN_PERCENTAGE_OF_MAXIMUM_OF_MOVES_TO_ASK_PLAYERS_TO_PLAY_FOR_NEXT_GENERATION)
                                - this.numberOfMovesForCurrentGame);
    }

    protected boolean enoughPlayersDidPlayTheGame() {
        if (this.mutationPhase != -1) {
            return false;
        } else if (this.crossoverPhase != -1 || this.crossoverAndMutationPhase != -1) {
            return this.controllerTetris.getPlayerControllers()[0].isGameOver();
        } else {
            return this.controllerTetris.getPlayersGameOver() >=
                    MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION
                            + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION
                            + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER;
        }
    }

    protected void evaluatePlayers(Chromosome[] players) {
        // Set chromosomes' evaluation.
        for (int i = 0; i < this.controllerTetris.getNumberOfPlayers(); i++) {
            Controller_PlayerRobotGenetic cp = (Controller_PlayerRobotGenetic) this.controllerTetris.getPlayerControllers()[i];
            ChromosomeTetris ct = (ChromosomeTetris) (players[i]);
            int nom = cp.getNumberOfMoves();
            int s = cp.getScore();

            if (s > this.maxScore) {
                this.maxScore = s;
                this.bestPlayerMaxScore = cp;
            }

            if (nom > this.maxNumberOfMoves) {
                this.maxNumberOfMoves = nom;
                this.bestPlayerMaxMoves = cp;
            }
            ct.gamePlayed(cp.getNumberOfMoves(), cp.getScore());
            double evaluationScoreOfThisGameAllGames = ct.getAverageScore() * 3 / 4 + cp.getScore() * 1.0 / 4;
            double evaluationMovesOfThisGameAllGames = ct.getAverageMoves() * 3 / 4 + cp.getNumberOfMoves() * 1.0 / 4;
            ct.setEvaluation(
                    0.9 * evaluationScoreOfThisGameAllGames + 0.1 * evaluationMovesOfThisGameAllGames
            );
        }

        this.lastPlayersWhichPlayedAGame = players;
        if (LOG_OPERATORS_OF_EVOLUTION && (
                this.mutationPhase != -1
                        || this.crossoverAndMutationPhase != -1
                        || this.crossoverPhase != -1
        )) {
            Arrays.sort(players);
            this.logGameStats();
        }
    }

    protected void logGameStats() {
        Chromosome[] players = this.lastPlayersWhichPlayedAGame;
        // Print stats.
        for (int i = 0; i < this.controllerTetris.getNumberOfPlayers(); i++) {
            ChromosomeTetris ct = ((ChromosomeTetris) players[i]);
            Controller_PlayerRobotGenetic cp = ct.getCp();
            int nom = cp.getNumberOfMoves();
            int s = cp.getScore();
            try {
                this.log(String.format(
                        "%5d | %28s | %d moves | %d score | %d games | %f avg moves | %f avg score | %s\n",
                        i, cp.getPlayerName(), nom, s, ct.getNumberOfGamesPlayed(), ct.getAverageMoves(), ct.getAverageScore(), players[i]
                )
                );
            } catch (Model_Exception e) {
                this.handleEasyError(e);
            }
        }
    }

    @Override
    protected Chromosome mutationForChromosome(Chromosome chromosome) {
        Chromosome mutantChromosome = this.mutationForChromosome(chromosome, this.getMutationToUse());
        this.log(
                String.format(
                        "\n%s\n%s (%s) => (%s)\n",
                        this.logIntro(),
                        this.mutationTypeToString(this.mutationToUse),
                        ((ChromosomeTetris) chromosome).getWeightsAsString(),
                        ((ChromosomeTetris) mutantChromosome).getWeightsAsString()
                )
        );
        return mutantChromosome;
    }

    @Override
    protected void chromosomeReplacesChromosome(Chromosome chromosome, Chromosome replacement) {
        Controller_PlayerRobotGenetic c = ((ChromosomeTetris) chromosome).getCp();
        Controller_PlayerRobotGenetic r = ((ChromosomeTetris) replacement).getCp();
        this.log(r.getModel().getName() + " replaced " + c.getModel().getName() + ".\n");
        r.getModel().setName(c.getModel().getName());
        super.chromosomeReplacesChromosome(chromosome, replacement);
    }

    protected int getMutationToUse() {
        if (this.numberOfGenerations < FORCE_UNIFORM_MUTATION_FOR_FIRST_GENERATIONS || random.nextDouble() < MUTATION_TYPE_UNIFORM_PROBABILITY_TO_PICK) {
            this.mutationToUse = MUTATION_TYPE_UNIFORM;
        } else {
            this.mutationToUse = MUTATION_TYPE_NONUNIFORM;
        }
        return this.mutationToUse;
    }

    /**
     * Apply mutation of given type to the chromosome.
     */
    protected Chromosome mutationForChromosome(Chromosome chromosome, int type) {
        ChromosomeTetris chromosomeTetris = (ChromosomeTetris) chromosome;
        ChromosomeTetris mutantChromosome = new ChromosomeTetris(chromosomeTetris);

        switch (type) {
            // Each weight will be changed to a new value, with a probability MUTATION_TYPE_UNIFORM_PROBABILITY_TO_CHANGE_WEIGHT.
            case MUTATION_TYPE_UNIFORM:
                int noOfGenesChanged = 0;
                for (int i = 0; i < this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS; i++) {
                    if (this.numberOfGenerations <= FORCE_UNIFORM_MUTATION_FOR_FIRST_GENERATIONS
                            || random.nextDouble() < MUTATION_TYPE_UNIFORM_PROBABILITY_TO_CHANGE_WEIGHT) {
                        double mutantValue = this.getPopulation().WEIGHT_MIN[i]
                                + random.nextDouble() * (this.getPopulation().WEIGHT_MAX[i] - this.getPopulation().WEIGHT_MIN[i]);
                        mutantChromosome.setWeight(i, mutantValue);
                        noOfGenesChanged++;
                    }
                }
                if (noOfGenesChanged == 0) {
                    // Change random weight.
                    int r = random.nextInt(this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS);
                    double mutantValue = this.getPopulation().WEIGHT_MIN[r]
                            + random.nextDouble() * (this.getPopulation().WEIGHT_MAX[r] - this.getPopulation().WEIGHT_MIN[r]);
                    mutantChromosome.setWeight(r, mutantValue);
                }
                break;
            // One of the weights will be changed with a value between
            // -WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION and +WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION.
            case MUTATION_TYPE_NONUNIFORM:
                // Randomly get one of the weights.
                int randomWeightPos = random.nextInt(this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS);

                // Get weight value.
                double mutantValue = chromosomeTetris.getWeight(randomWeightPos);

                // Find new value.
                mutantValue += random.nextDouble() * WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION * 2 - WEIGHT_MAX_TO_CHANGE_ON_NONUNIFORM_MUTATION;
                if (mutantValue < this.getPopulation().WEIGHT_MIN[randomWeightPos]) {
                    mutantValue = this.getPopulation().WEIGHT_MIN[randomWeightPos];
                }
                if (mutantValue > this.getPopulation().WEIGHT_MAX[randomWeightPos]) {
                    mutantValue = this.getPopulation().WEIGHT_MAX[randomWeightPos];
                }

                // Apply mutation.
                mutantChromosome.setWeight(randomWeightPos, mutantValue);
                break;
        }

        return mutantChromosome;
    }

    @Override
    protected Chromosome[] crossoverForChromosome(Chromosome parent1, Chromosome parent2) {
        Chromosome[] children = crossoverForChromosome(parent1, parent2, this.getCrossoverToUse());
        this.log(String.format(
                "\n%s\n%s (%s) and (%s) => (%s) and (%s)\n",
                this.logIntro(),
                this.crossoverTypeToString(this.crossoverToUse),
                ((ChromosomeTetris) parent1).getWeightsAsString(),
                ((ChromosomeTetris) parent2).getWeightsAsString(),
                ((ChromosomeTetris) children[0]).getWeightsAsString(),
                ((ChromosomeTetris) children[1]).getWeightsAsString()
        )
        );
        return children;
    }

    protected int getCrossoverToUse() {
        double r = random.nextDouble();
        if (r <= CROSSOVER_TYPE_DISCREET_PROBABILITY_TO_PICK) {
            this.crossoverToUse = CROSSOVER_TYPE_DISCREET;
        } else if (r <= CROSSOVER_TYPE_DISCREET_PROBABILITY_TO_PICK + CROSSOVER_TYPE_INTERMEDIATE_SINGULAR_PROBABILITY_TO_PICK) {
            this.crossoverToUse = CROSSOVER_TYPE_INTERMEDIATE_SINGULAR;
        } else {
            this.crossoverToUse = CROSSOVER_TYPE_INTERMEDIATE_COMPLETE;
        }
        return this.crossoverToUse;
    }

    /**
     * Apply crossover of given type for the two parents.
     */
    protected Chromosome[] crossoverForChromosome(Chromosome parent1, Chromosome parent2, int type) {
        ChromosomeTetris parent1Tetris = (ChromosomeTetris) parent1;
        ChromosomeTetris parent2Tetris = (ChromosomeTetris) parent2;
        ChromosomeTetris child1 = new ChromosomeTetris(parent1Tetris);
        ChromosomeTetris child2 = new ChromosomeTetris(parent1Tetris);

        switch (type) {
            // For each weight get the parent1's weight or the parent2's weight.
            case CROSSOVER_TYPE_DISCREET:
                for (int i = 0; i < this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS; i++) {
                    if (random.nextDouble() < 0.5) {
                        child1.setWeight(i, parent1Tetris.getWeight(i));
                        child2.setWeight(i, parent2Tetris.getWeight(i));
                    } else {
                        child1.setWeight(i, parent2Tetris.getWeight(i));
                        child2.setWeight(i, parent1Tetris.getWeight(i));
                    }
                }
                break;
            // For one random weight apply intermediate crossover.
            case CROSSOVER_TYPE_INTERMEDIATE_SINGULAR:
                // Randomly get one of the weights.
                int randomWeightPos = random.nextInt(this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS);

                child1.setWeight(randomWeightPos,
                        CROSSOVER_TYPE_INTERMEDIATE_ALPHA * parent1Tetris.getWeight(randomWeightPos)
                                + (1 - CROSSOVER_TYPE_INTERMEDIATE_ALPHA) * parent2Tetris.getWeight(randomWeightPos)
                );
                child2.setWeight(randomWeightPos,
                        CROSSOVER_TYPE_INTERMEDIATE_ALPHA * parent2Tetris.getWeight(randomWeightPos)
                                + (1 - CROSSOVER_TYPE_INTERMEDIATE_ALPHA) * parent1Tetris.getWeight(randomWeightPos)
                );
                break;
            // For each weight apply intermediate crossover.
            case CROSSOVER_TYPE_INTERMEDIATE_COMPLETE:
                for (int i = 0; i < this.getPopulation().NUMBER_OF_PLAYER_WEIGHTS; i++) {
                    child1.setWeight(i,
                            CROSSOVER_TYPE_INTERMEDIATE_ALPHA * parent1Tetris.getWeight(i)
                                    + (1 - CROSSOVER_TYPE_INTERMEDIATE_ALPHA) * parent2Tetris.getWeight(i)
                    );
                    child2.setWeight(i,
                            CROSSOVER_TYPE_INTERMEDIATE_ALPHA * parent2Tetris.getWeight(i)
                                    + (1 - CROSSOVER_TYPE_INTERMEDIATE_ALPHA) * parent1Tetris.getWeight(i)
                    );
                }
                break;
        }

        return new Chromosome[]{child1, child2};
    }

    public GaTetris_ThreadGameManager[] getGameManagers() {
        return gameManagers;
    }
}
