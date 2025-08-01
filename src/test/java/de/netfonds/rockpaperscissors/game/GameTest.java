package de.netfonds.rockpaperscissors.game;


import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameTest {

    @Test
    public void testIncompleteRuleSet() {
        // given
        final var game = new TestGame(
                () -> ClassicHandShape.ROCK,
                () -> ClassicHandShape.PAPER,
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
                () -> assertInstanceOf(Game.GameRuleNotFoundException.class, finalThrowable)
        );
    }

    @Test
    public void testPlayerAWins() {
        // given
        final var game = new TestGame(
                () -> ClassicHandShape.ROCK,
                () -> ClassicHandShape.PAPER,
                Set.of(
                        GameRule.of(ClassicHandShape.ROCK, ClassicHandShape.PAPER)
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
                () -> ClassicHandShape.ROCK,
                () -> ClassicHandShape.PAPER,
                Set.of(
                        GameRule.of(ClassicHandShape.ROCK, ClassicHandShape.ROCK)
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
                () -> ClassicHandShape.ROCK,
                () -> ClassicHandShape.ROCK,
                Set.of(
                        GameRule.of(ClassicHandShape.ROCK, ClassicHandShape.PAPER)
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
        final ClassicHandShape[] shapes = ClassicHandShape.values();
        final var game = new TestGame(
                () -> shapes[random.nextInt(shapes.length)],
                () -> shapes[random.nextInt(shapes.length)],
                Set.of(
                        GameRule.of(ClassicHandShape.ROCK, ClassicHandShape.SCISSORS),
                        GameRule.of(ClassicHandShape.PAPER, ClassicHandShape.ROCK),
                        GameRule.of(ClassicHandShape.SCISSORS, ClassicHandShape.PAPER)
                )
        );

        // when
        final GameResult result = game.playGame(numberOfRounds);

        // then
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(numberOfRounds, result.numberOfRounds()),
                () -> assertEquals(numberOfRounds, result.numberOfPlayerAWins()
                        + result.numberOfPlayerBWins()
                        + result.numberOfDraws())
        );

    }


    private static class TestGame extends Game<ClassicHandShape> {

        public TestGame(GameStrategy<ClassicHandShape> playerAGameStrategy, GameStrategy<ClassicHandShape> playerBGameStrategy, Set<GameRule<ClassicHandShape>> gameRules) {
            super(playerAGameStrategy, playerBGameStrategy, gameRules);
        }
    }

}