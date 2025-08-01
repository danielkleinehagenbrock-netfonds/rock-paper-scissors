package de.netfonds.rockpaperscissors.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GameRuleTest {

    @Test
    public void testWiningGameRule() {
        // given
        final var rule = createGameRule();

        // when
        final GameRuleResult result = rule.getRuleResult((LizardHandShape.SCISSORS));
        final GameRuleResult otherResult = rule.getRuleResult((LizardHandShape.LIZARD));

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
        final GameRuleResult result = rule.getRuleResult((LizardHandShape.SPOCK));

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
        final GameRuleResult result = rule.getRuleResult((LizardHandShape.ROCK));

        // then
        assertAll(
                () -> assertNotNull(result, "GameRuleResult for A<>A is null."),
                () -> assertEquals(GameRuleResult.DRAW, result)
        );
    }

    private GameRule<LizardHandShape> createGameRule() {
        return GameRule.of(LizardHandShape.ROCK, LizardHandShape.SCISSORS, LizardHandShape.LIZARD);
    }

}