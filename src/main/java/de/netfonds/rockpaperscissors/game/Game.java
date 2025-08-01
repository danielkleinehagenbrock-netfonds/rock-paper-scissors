package de.netfonds.rockpaperscissors.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class for Rock Paper Scissors game variants.
 *
 * <p>Provides a generic game engine that can handle different hand shapes and rule sets.
 * Uses the Strategy pattern for player behavior and implements the core game loop.
 *
 * <p>The class is parameterized by the hand shape type {@code S}, allowing type-safe
 * implementations of various game variants while maintaining a common interface.
 *
 * @param <S> the type of hand shapes used in this game variant
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 */
public class Game<S extends HandShape> {

    private static final Logger logger = LogManager.getLogger(Game.class);

    /**
     * Strategy for Player A's move selection.
     * Immutable after construction.
     */
    private final GameStrategy<S> playerAGameStrategy;

    /**
     * Strategy for Player B's move selection.
     * Immutable after construction.
     */
    private final GameStrategy<S> playerBGameStrategy;

    /**
     * Rule set mapping each hand shape to its corresponding game rule.
     * Converted to unmodifiable map for efficient lookup during gameplay.
     */
    private final Map<S, GameRule<S>> ruleSet;

    /**
     * Creates a new {@link Game} instance.
     * Constructor is private. {@link Game} is created via {@link Builder}.
     *
     * @param playerAGameStrategy Strategy for Player A's move selection.
     * @param playerBGameStrategy Strategy for Player B's move selection.
     * @param ruleSet             Rule set mapping each hand shape to its corresponding game rule.
     */
    protected Game(GameStrategy<S> playerAGameStrategy,
                   GameStrategy<S> playerBGameStrategy,
                   Set<GameRule<S>> ruleSet) {
        this.playerAGameStrategy = playerAGameStrategy;
        this.playerBGameStrategy = playerBGameStrategy;
        this.ruleSet = ruleSet
                .stream()
                .collect(Collectors.toUnmodifiableMap(
                        GameRule::getHandShape,
                        Function.identity()));
    }

    /**
     * Plays a single round of the game.
     *
     * <p>Retrieves hand shapes from both players, applies game rules to determine
     * the winner, and logs the result at debug level.
     *
     * @return the result of this game round
     * @throws GameRuleNotFoundException if no rule is found for Player A's hand shape
     */
    private GameRoundResult playRound() {
        final S handShapePlayerA = playerAGameStrategy.getNextHandShape();
        final S handShapePlayerB = playerBGameStrategy.getNextHandShape();

        return Optional.ofNullable(ruleSet.get(handShapePlayerA))
                .map(rule -> rule.getRuleResult(handShapePlayerB))
                .map(result -> switch (result) {
                    case WIN -> {
                        logger.debug("Player A wins. ({} > {})", handShapePlayerA, handShapePlayerB);
                        yield GameRoundResult.PLAYER_A_WINS;
                    }
                    case LOSE -> {
                        logger.debug("Player B wins. ({} < {})", handShapePlayerA, handShapePlayerB);
                        yield GameRoundResult.PLAYER_B_WINS;
                    }
                    case DRAW -> {
                        logger.debug("Round is a draw. ({})", handShapePlayerA);
                        yield GameRoundResult.DRAW;
                    }
                })
                .orElseThrow(() -> new GameRuleNotFoundException(handShapePlayerA));
    }

    /**
     * Plays a complete game with the specified number of rounds.
     *
     * <p>Executes the given number of rounds, tracking wins/losses/draws.
     *
     * @param numberOfRounds the number of rounds to play
     * @return aggregated results of all rounds
     * @throws IllegalArgumentException if a round result is not recognized
     */
    public GameResult playGame(int numberOfRounds) {
        int numberOfPlayerAWins = 0;
        int numberOfPlayerBWins = 0;
        int numberOfDraws = 0;
        for (int i = 0; i < numberOfRounds; i++) {
            logger.debug("Round {}:\t", i + 1);
            final GameRoundResult result = playRound();
            switch (result) {
                case PLAYER_A_WINS -> numberOfPlayerAWins++;
                case PLAYER_B_WINS -> numberOfPlayerBWins++;
                case DRAW -> numberOfDraws++;
                default -> throw new IllegalArgumentException("Unsupported GameRoundResult: " + result.name());
            }
        }
        final GameResult result = new GameResult(
                numberOfPlayerAWins,
                numberOfPlayerBWins,
                numberOfDraws
        );
        logger.info("Player A won {} rounds. ({} %)", result.numberOfPlayerAWins(), result.playerAWinRate());
        logger.info("Player B won {} rounds. ({} %)", result.numberOfPlayerBWins(), result.playerBWinRate());
        logger.info("{} rounds were drawn. ({} %)", result.numberOfDraws(), result.drawRate());
        return result;
    }

    /**
     * GameRuleNotFoundException is a runtime exception which can be thrown to track a game does not have
     * a rule for a given {@link HandShape}.
     */
    public static class GameRuleNotFoundException extends RuntimeException {

        /**
         * Creates a new {@link GameRuleNotFoundException} instance.
         *
         * @param handShape The {@link HandShape} a {@link GameRule} is missing.
         */
        public GameRuleNotFoundException(HandShape handShape) {
            super("No game rule found for hand shape: " + handShape);
        }
    }

    /**
     * Builder class to create a {@link Game} instance.
     *
     * @param <S> the type of hand shapes used in this game variant
     */
    public static class Builder<S extends HandShape> {
        private GameStrategy<S> playerAStrategy;
        private GameStrategy<S> playerBStrategy;
        private Set<GameRule<S>> rules = Set.of();

        /**
         * Sets the strategy for Player A's move selection.
         *
         * @param strategy strategy for Player A's move selection.
         * @return This {@link Builder} instance.
         */
        public Builder<S> withPlayerA(GameStrategy<S> strategy) {
            this.playerAStrategy = strategy;
            return this;
        }

        /**
         * Sets the strategy for Player B's move selection.
         *
         * @param strategy strategy for Player B's move selection.
         * @return This {@link Builder} instance.
         */
        public Builder<S> withPlayerB(GameStrategy<S> strategy) {
            this.playerBStrategy = strategy;
            return this;
        }

        /**
         * Sets the {@link GameRule}s for this game variant.
         *
         * @param rules the {@link GameRule}s for this game variant.
         * @return This {@link Builder} instance.
         */
        @SafeVarargs
        public final Builder<S> withRules(GameRule<S>... rules) {
            this.rules = Set.of(rules);
            return this;
        }

        /**
         * Builds the game variants instance.
         *
         * @return the game variants instance.
         */
        public Game<S> build() {
            Objects.requireNonNull(playerAStrategy, "Player A strategy required");
            Objects.requireNonNull(playerBStrategy, "Player B strategy required");
            if (rules.isEmpty()) {
                throw new IllegalArgumentException("At least one game rule required");
            }

            return new Game<>(playerAStrategy, playerBStrategy, rules);
        }

        /**
         * Creates a classic rock, paper, scissors game variant.
         *
         * @return a classic rock, paper, scissors game variant.
         */
        public static Game<ClassicHandShape> newClassicHandShapeGame() {
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            final ClassicHandShape[] shapes = ClassicHandShape.values();

            final var builder = new Game.Builder<ClassicHandShape>();
            return builder
                    .withPlayerA(() -> ClassicHandShape.PAPER)
                    .withPlayerB(() -> shapes[random.nextInt(shapes.length)])
                    .withRules(
                            GameRule.of(ClassicHandShape.ROCK, ClassicHandShape.SCISSORS),
                            GameRule.of(ClassicHandShape.PAPER, ClassicHandShape.ROCK),
                            GameRule.of(ClassicHandShape.SCISSORS, ClassicHandShape.PAPER)
                    )
                    .build();

        }

        /**
         * Creates a fancy rock, paper, scissors game variant.
         *
         * @return a fancy rock, paper, scissors game variant.
         */
        public static Game<LizardHandShape> newLizardHandShapeGame() {
            final ThreadLocalRandom random = ThreadLocalRandom.current();
            final LizardHandShape[] shapes = LizardHandShape.values();

            final var builder = new Game.Builder<LizardHandShape>();
            return builder
                    .withPlayerA(() -> LizardHandShape.PAPER)
                    .withPlayerB(() -> shapes[random.nextInt(shapes.length)])
                    .withRules(
                            GameRule.of(LizardHandShape.ROCK, LizardHandShape.SCISSORS, LizardHandShape.LIZARD),
                            GameRule.of(LizardHandShape.PAPER, LizardHandShape.ROCK, LizardHandShape.SPOCK),
                            GameRule.of(LizardHandShape.SCISSORS, LizardHandShape.PAPER, LizardHandShape.LIZARD),
                            GameRule.of(LizardHandShape.LIZARD, LizardHandShape.PAPER, LizardHandShape.SPOCK),
                            GameRule.of(LizardHandShape.SPOCK, LizardHandShape.ROCK, LizardHandShape.PAPER)
                    )
                    .build();

        }
    }
}

