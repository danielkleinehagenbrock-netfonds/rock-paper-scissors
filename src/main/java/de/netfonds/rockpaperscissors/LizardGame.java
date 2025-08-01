package de.netfonds.rockpaperscissors;

import de.netfonds.rockpaperscissors.game.GameRule;
import de.netfonds.rockpaperscissors.game.Game;
import de.netfonds.rockpaperscissors.game.HandShape;

import java.util.Random;
import java.util.Set;

public class LizardGame extends Game<LizardGame.LizardHandShape> {

    private static final Random RANDOM = new Random();
    private static final LizardHandShape[] shapes = LizardHandShape.values();

    public LizardGame() {
        super(
                () -> LizardHandShape.PAPER,
                () -> shapes[RANDOM.nextInt(shapes.length)],
                Set.of(
                        GameRule.of(LizardHandShape.ROCK, LizardHandShape.SCISSORS, LizardHandShape.LIZARD),
                        GameRule.of(LizardHandShape.PAPER, LizardHandShape.ROCK, LizardHandShape.SPOCK),
                        GameRule.of(LizardHandShape.SCISSORS, LizardHandShape.ROCK, LizardHandShape.LIZARD),
                        GameRule.of(LizardHandShape.SPOCK, LizardHandShape.ROCK, LizardHandShape.PAPER),
                        GameRule.of(LizardHandShape.LIZARD, LizardHandShape.PAPER, LizardHandShape.SPOCK)
                )
        );
    }


     enum LizardHandShape implements HandShape {
        ROCK,
        PAPER,
        SCISSORS,
        LIZARD,
        SPOCK
    }
}


