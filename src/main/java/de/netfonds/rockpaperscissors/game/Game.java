package de.netfonds.rockpaperscissors.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
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
        logger.info("Player A won {} rounds.", result.numberOfPlayerAWins());
        logger.info("Player B won {} rounds.", result.numberOfPlayerBWins());
        logger.info("{} rounds were drawn.", result.numberOfDraws());
        return result;
    }

    public static class GameRuleNotFoundException extends RuntimeException {
        public GameRuleNotFoundException(HandShape handShape) {
            super("No game rule found for hand shape: " + handShape);
        }
    }

}

