package de.netfonds.rockpaperscissors.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Game<S extends HandShape> {

    private static final Logger logger = LogManager.getLogger(Game.class);

    private final GameStrategy<S> playerAGameStrategy;

    private final GameStrategy<S> playerBGameStrategy;

    private final Map<S, GameRule<S>> ruleSet;

    public Game(GameStrategy<S> playerAGameStrategy,
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

    private GameRoundResult playRound() {
        final S handShapePlayerA = playerAGameStrategy.getNextHandShape();
        final S handShapePlayerB = playerBGameStrategy.getNextHandShape();

        if (ruleSet.containsKey(handShapePlayerA)) {
            final GameRule<S> rule =  ruleSet.get(handShapePlayerA);
            final GameRuleResult result = rule.getRuleResult(handShapePlayerB);

            switch (result) {
                case WIN -> {
                    logger.debug("Player A wins. ({} > {})", handShapePlayerA, handShapePlayerB);
                    return GameRoundResult.PLAYER_A_WINS;
                }
                case LOSE -> {
                    logger.debug("Player B wins. ({} < {})", handShapePlayerA, handShapePlayerB);
                    return GameRoundResult.PLAYER_B_WINS;
                }
                case DRAW -> {
                    logger.debug("Round is a draw. ({})", handShapePlayerA);
                    return GameRoundResult.DRAW;
                }
                default -> throw new IllegalArgumentException("Unsupported RuleResult: " + result.name());
            }
        }
        throw new IllegalStateException("No rule found for " + handShapePlayerA);
    }

    public GameResult playGame(int numberOfRounds) {
        final AtomicInteger numberOfPlayerAWins = new AtomicInteger();
        final AtomicInteger numberOfPlayerBWins = new AtomicInteger();
        final AtomicInteger numberOfDraws = new AtomicInteger();
        for (int i = 0; i < numberOfRounds; i++) {
            logger.debug("Round {}:\t", i + 1);
            final GameRoundResult result = playRound();
            switch (result) {
                case PLAYER_A_WINS -> numberOfPlayerAWins.incrementAndGet();
                case PLAYER_B_WINS -> numberOfPlayerBWins.incrementAndGet();
                case DRAW -> numberOfDraws.incrementAndGet();
                default -> throw new IllegalArgumentException("Unsupported GameRoundResult: " + result.name());
            }
        }
        final GameResult result = new GameResult(
                numberOfPlayerAWins.get(),
                numberOfPlayerBWins.get(),
                numberOfDraws.get()
        );
        logger.info("Player A won {} rounds.", result.numberOfPlayerAWins());
        logger.info("Player B won {} rounds.", result.numberOfPlayerBWins());
        logger.info("{} rounds were drawn.", result.numberOfDraws());
        return result;
    }

}

