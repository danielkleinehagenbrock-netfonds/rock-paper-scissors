package de.netfonds.rockpaperscissors;

import de.netfonds.rockpaperscissors.game.Game;
import de.netfonds.rockpaperscissors.game.GameRule;
import de.netfonds.rockpaperscissors.game.LizardHandShape;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class LizardGame extends Game<LizardHandShape> {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final LizardHandShape[] SHAPES = LizardHandShape.values();

    public LizardGame() {
        super(
                () -> LizardHandShape.PAPER,
                () -> SHAPES[RANDOM.nextInt(SHAPES.length)],
                Set.of(
                        GameRule.of(LizardHandShape.ROCK, LizardHandShape.SCISSORS, LizardHandShape.LIZARD),
                        GameRule.of(LizardHandShape.PAPER, LizardHandShape.ROCK, LizardHandShape.SPOCK),
                        GameRule.of(LizardHandShape.SCISSORS, LizardHandShape.ROCK, LizardHandShape.LIZARD),
                        GameRule.of(LizardHandShape.SPOCK, LizardHandShape.ROCK, LizardHandShape.PAPER),
                        GameRule.of(LizardHandShape.LIZARD, LizardHandShape.PAPER, LizardHandShape.SPOCK)
                )
        );
    }


}


