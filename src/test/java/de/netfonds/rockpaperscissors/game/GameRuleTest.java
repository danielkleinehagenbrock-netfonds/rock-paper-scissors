package de.netfonds.rockpaperscissors.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameRuleTest {

    @Test
    public void testWiningGameRule() {
        // given
        final var rule = createGameRule();

        // when
        final GameRuleResult result = rule.getRuleResult(TestHandShape.B);
        final GameRuleResult otherResult = rule.getRuleResult(TestHandShape.C);

        // then
        assertAll(
                () -> assertNotNull(result, "GameRuleResult for A<>B is null."),
                () -> assertNotNull(otherResult, "GameRuleResult for A<>C is null."),
                () -> assertEquals(GameRuleResult.WIN, result),
                () -> assertEquals(GameRuleResult.WIN, otherResult)
        );
    }

    @Test
    public void testLosingGameRule() {
        // given
        final var rule = createGameRule();

        // when
        final GameRuleResult result = rule.getRuleResult(TestHandShape.D);

        // then
        assertAll(
                () -> assertNotNull(result, "GameRuleResult for A<>D is null."),
                () -> assertEquals(GameRuleResult.LOSE, result)
        );
    }

    @Test
    public void testDrawingGameRule() {
        // given
        final var rule = createGameRule();

        // when
        final GameRuleResult result = rule.getRuleResult(TestHandShape.A);

        // then
        assertAll(
                () -> assertNotNull(result, "GameRuleResult for A<>A is null."),
                () -> assertEquals(GameRuleResult.DRAW, result)
        );
    }

    private GameRule<TestHandShape> createGameRule() {
        return GameRule.of(TestHandShape.A, TestHandShape.B, TestHandShape.C);
    }

    private enum TestHandShape implements HandShape {
        A, B, C, D
    }
}