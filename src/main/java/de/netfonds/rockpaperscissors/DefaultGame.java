package de.netfonds.rockpaperscissors;

import de.netfonds.rockpaperscissors.game.DefaultHandShape;
import de.netfonds.rockpaperscissors.game.Game;
import de.netfonds.rockpaperscissors.game.GameRule;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DefaultGame extends Game<DefaultHandShape> {

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private static final DefaultHandShape[] SHAPES = DefaultHandShape.values();

    public DefaultGame() {
        super(
                () -> DefaultHandShape.PAPER,
                () -> SHAPES[RANDOM.nextInt(SHAPES.length)],
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.SCISSORS),
                        GameRule.of(DefaultHandShape.PAPER, DefaultHandShape.ROCK),
                        GameRule.of(DefaultHandShape.SCISSORS, DefaultHandShape.PAPER)
                ));
    }


}


