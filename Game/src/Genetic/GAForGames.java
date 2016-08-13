package Genetic;

public abstract class GAForGames extends GALog
{
    public static int NUMBER_OF_PLAYERS = 1;//10;
    public static int MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION = 1;//(int) Math.floor(0.1 * NUMBER_OF_PLAYERS);
    public static int MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION = 0;//(int) Math.floor(0.2 * NUMBER_OF_PLAYERS);
    public static int MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER = 0;//(int) Math.floor(0.2 * NUMBER_OF_PLAYERS);

    public double MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = 0;//0.1;
    public double CROSSOVER_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = 0;//0.1;
    public double CROSSOVER_MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER = 0;// 0.1;

    protected int mutationsThisGeneration, mutationsAllGenerations = 0;
    protected int crossoversThisGeneration, crossoversAllGenerations = 0;
    protected int crossoversAndMutationsThisGeneration, crossoversAndMutationsAllGenerations = 0;
    protected int mutationPhase = -1;
    protected int crossoverAndMutationPhase = -1;
    protected int crossoverPhase = -1;
    protected int mainGamePhase = -1;

    @Override
    public void beforeGeneration() {
        this.log("\n######################################################\nGeneration "
                + (this.numberOfGenerations + 1) + " START"
                + "\n######################################################\n");
    }

    @Override
    public void newGeneration() {
        // All players play the game.
        this.mainGame();

        // Mutation.
        this.mutation();

        // Crossover and mutation.
        this.crossoverAndMutation();

        // Crossover.
        this.crossover();
    }

    @Override
    public void afterGeneration() {
        this.log("\n######################################################\nGeneration "
                + (this.numberOfGenerations) + " STOP"
                + "\n######################################################\n");
    }

    /**
     * Generate new game.
     */
    protected abstract void generateNewGame(Chromosome[] players);

    /**
     * Each player will play alone for a determined amount of time/moves etc.
     */
    protected abstract void playersPlayTheGameAndEvaluate(Chromosome[] players);

    protected void mainGame() {
        this.mainGamePhase = 0;

        // Generate new game for all players.
        this.log("\n--Game for all players in current generation--\n");
        this.generateNewGame(this.population.getAllChromosomes());

        // All players play the game.
        this.playersPlayTheGameAndEvaluate(this.population.getAllChromosomes());

        this.mainGamePhase = -1;
    }

    /**
     * The worst MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION players will not survive for the next generation.
     * The best MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION players will be copied and will
     * suffer mutation. The mutant players will be selected for next generation.
     */
    protected void mutation() {
        this.log("\n--Mutation phase--\n");
        this.mutationsThisGeneration = 0;

        for (int i = 0; i < MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION; i++) {
            this.mutationPhase = NUMBER_OF_PLAYERS - MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION + i;

            // Get chromosomes.
            Chromosome chromosomeToEliminate = this.population.get(i);
            Chromosome chromosomeToMutate = this.population.get(
                    NUMBER_OF_PLAYERS - MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION + i
            );

            // Apply mutation.
            Chromosome mutantChromosome = this.mutationForChromosome(chromosomeToMutate);
            Chromosome[] chromosomes = new Chromosome[]{chromosomeToMutate, mutantChromosome};
            this.generateNewGame(chromosomes);
            this.playersPlayTheGameAndEvaluate(chromosomes);

            // Set.
            if (mutantChromosome.isBetterThan(chromosomeToEliminate)
                    || random.nextDouble() < MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER) {
                this.chromosomeReplacesChromosome(chromosomeToEliminate, mutantChromosome);
                this.mutationsThisGeneration++;
                this.population.set(i, mutantChromosome);
                //this.log("Mutant player replaced initial player.\n");
            } else {
                this.log("Mutant player did not replace initial player.\n");
            }
        }

        this.mutationPhase = -1;
        this.log("\n" + this.mutationsThisGeneration + " chromosomes replaced through mutation.\n");
        this.mutationsAllGenerations += this.mutationsThisGeneration;
    }

    protected void chromosomeReplacesChromosome(Chromosome chromosome, Chromosome mutantChromosome) {

    }

    /**
     * The next worst MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION players will be selected
     * and suffer crossover with other better players.
     * After this, the children created through crossover will suffer mutation
     * and the best of them will be selected to go through next generation.
     */
    protected void crossoverAndMutation() {
        this.log("\n--Crossover & Mutation phase--\n");
        this.crossoversAndMutationsThisGeneration = 0;

        for (int i = MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION;
             i < MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION;
             i++) {
            this.crossoverAndMutationPhase = i;

            // First chromosome for crossover.
            Chromosome parent1 = this.population.get(i);
            // 2nd chromosome for crossover.
            Chromosome parent2 = this.population.get(this.randomPositionForCrossover(i));
            // Crossover.
            Chromosome[] children = this.crossoverForChromosome(parent1, parent2);

            // Apply mutation.
            children[0] = this.mutationForChromosome(children[0]);
            children[1] = this.mutationForChromosome(children[1]);
            Chromosome[] chromosomes = new Chromosome[]{parent1, children[0], children[1]};
            this.generateNewGame(chromosomes);
            this.playersPlayTheGameAndEvaluate(chromosomes);

            // Set new chromosome.
            Chromosome mutantChromosome = children[0].isBetterThan(children[1]) ? children[0] : children[1];
            if (mutantChromosome.isBetterThan(parent1)
                    || random.nextDouble() < CROSSOVER_MUTATION_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER) {
                this.chromosomeReplacesChromosome(parent1, mutantChromosome);
                this.population.set(i, mutantChromosome);
                this.crossoversAndMutationsThisGeneration++;
                //this.log("Mutant child player replaced initial player.\n");
            } else {
                this.log("Mutant children players did not replace initial player.\n");
            }
        }

        this.crossoverAndMutationPhase = -1;
        this.log("\n" + this.crossoversAndMutationsThisGeneration + " chromosomes replaced through crossover&mutation.\n");
        this.crossoversAndMutationsAllGenerations += this.crossoversAndMutationsThisGeneration;
    }

    /**
     * The next worst MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER players will be selected
     * and suffer crossover with other better players, after which
     * the best child from the ones created through crossover will be selected for the next generation.
     */
    protected void crossover() {
        this.log("\n--Crossover phase--\n");
        this.crossoversThisGeneration = 0;

        for (int i = MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION
                + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION;
             i < MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_MUTATION
                     + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER_AND_MUTATION
                     + MAX_NUMBER_OF_PLAYERS_TO_SELECT_FOR_CROSSOVER;
             i++) {
            this.crossoverPhase = i;

            // First chromosome for crossover.
            Chromosome parent1 = this.population.get(i);
            // 2nd chromosome for crossover.
            Chromosome parent2 = this.population.get(this.randomPositionForCrossover(i));
            // Crossover.
            Chromosome[] children = this.crossoverForChromosome(parent1, parent2);
            Chromosome[] chromosomes = new Chromosome[]{parent1, children[0], children[1]};
            this.generateNewGame(chromosomes);
            this.playersPlayTheGameAndEvaluate(chromosomes);

            // Set new chromosome.
            Chromosome crossoverChromosome = children[0].isBetterThan(children[1]) ? children[0] : children[1];
            if (crossoverChromosome.isBetterThan(parent1)
                    || random.nextDouble() < CROSSOVER_PROBABILITY_TO_ALLOW_CHILD_TO_GO_TO_NEXT_GENERATION_EVEN_IF_WEAKER) {
                this.chromosomeReplacesChromosome(parent1, crossoverChromosome);
                this.population.set(i, crossoverChromosome);
                this.crossoversThisGeneration++;
                //this.log("Child player replaced parent player.\n");
            } else {
                this.log("Children players did not replace initial player.\n");
            }
        }

        this.crossoverPhase = -1;
        this.log("\n" + this.crossoversThisGeneration + " chromosomes replaced through crossover.\n");
        this.crossoversAllGenerations += this.crossoversThisGeneration++;
    }

    /**
     * Select for crossover one of the chromosomes between start position and NUMBER_OF_PLAYERS - 1 position.
     * The best chromosomes (last positions) should have a higher chance to be picked.
     */
    public int randomPositionForCrossover(int start) {
        int pos = 0;
        int end = NUMBER_OF_PLAYERS - start;
        // Select a number between 1 and end.
        // The i-th number has a chance i/[(end)*(end+1)/2] to be selected.
        int max = end * (end + 1) / 2;
        int randomInt = random.nextInt(max) + 1;

        // Find the number pos such that (pos-1)*pos/2 < randomInt <= pos*(pos+1)/2.
        int s = 1;
        int d = end;
        while (s <= d) {
            pos = (s + d) / 2;
            if (randomInt <= (pos - 1) * pos / 2) {
                d = pos - 1;
            } else if (randomInt > pos * (pos + 1) / 2) {
                s = pos + 1;
            } else {
                break;
            }
        }

        // Return final position.
        return start + pos - 1;
    }

    /**
     * Copy chromosome, apply mutation and return mutant chromosome.
     */
    protected abstract Chromosome mutationForChromosome(Chromosome chromosome);

    /**
     * Create two children chromosomes from the crossover of two parents.
     */
    protected abstract Chromosome[] crossoverForChromosome(Chromosome parent1, Chromosome parent2);
}
