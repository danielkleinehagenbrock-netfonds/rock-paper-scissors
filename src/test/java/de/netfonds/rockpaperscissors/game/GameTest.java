package de.netfonds.rockpaperscissors.game;


import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void testIncompleteRuleSet() {
        // given
        final var game = new TestGame(
                () -> DefaultHandShape.ROCK,
                () -> DefaultHandShape.PAPER,
                Set.of()
        );

        // when
        Throwable throwable = null;
        try {
            game.playGame(1);
        } catch (Throwable t) {
            throwable = t;
        }

        // then
        final Throwable finalThrowable = throwable;
        assertAll(
                () -> assertNotNull(finalThrowable),
                () -> assertInstanceOf(IllegalStateException.class, finalThrowable)
        );
    }

    @Test
    public void testPlayerAWins() {
        // given
        final var game = new TestGame(
                () -> DefaultHandShape.ROCK,
                () -> DefaultHandShape.PAPER,
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.PAPER)
                )
        );

        // when
        final GameResult result = game.playGame(1);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.numberOfPlayerAWins()),
                () -> assertEquals(0, result.numberOfPlayerBWins()),
                () -> assertEquals(0, result.numberOfDraws())
        );

    }

    @Test
    public void testPlayerBWins() {
        // given
        final var game = new TestGame(
                () -> DefaultHandShape.ROCK,
                () -> DefaultHandShape.PAPER,
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.ROCK)
                )
        );

        // when
        final GameResult result = game.playGame(1);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(0, result.numberOfPlayerAWins()),
                () -> assertEquals(1, result.numberOfPlayerBWins()),
                () -> assertEquals(0, result.numberOfDraws())
        );

    }

    @Test
    public void testDrawn() {
        // given
        final var game = new TestGame(
                () -> DefaultHandShape.ROCK,
                () -> DefaultHandShape.ROCK,
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.PAPER)
                )
        );

        // when
        final GameResult result = game.playGame(1);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(0, result.numberOfPlayerAWins()),
                () -> assertEquals(0, result.numberOfPlayerBWins()),
                () -> assertEquals(1, result.numberOfDraws())
        );

    }

    @Test
    public void testCountSumsUpToNumberOfRounds() {
        // given
        final int numberOfRounds = 10;
        final Random random = new Random();
        final DefaultHandShape[] shapes = DefaultHandShape.values();
        final var game = new TestGame(
                () -> shapes[random.nextInt(shapes.length)],
                () -> shapes[random.nextInt(shapes.length)],
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.SCISSORS),
                        GameRule.of(DefaultHandShape.PAPER, DefaultHandShape.ROCK),
                        GameRule.of(DefaultHandShape.SCISSORS, DefaultHandShape.PAPER)
                )
        );

        // when
        final GameResult result = game.playGame(numberOfRounds);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(numberOfRounds, result.numberOfPlayerAWins()
                        + result.numberOfPlayerBWins()
                        + result.numberOfDraws())
        );

    }


    private static class TestGame extends Game<DefaultHandShape> {

        public TestGame(GameStrategy<DefaultHandShape> playerAGameStrategy, GameStrategy<DefaultHandShape> playerBGameStrategy, Set<GameRule<DefaultHandShape>> gameRules) {
            super(playerAGameStrategy, playerBGameStrategy, gameRules);
        }
    }

}