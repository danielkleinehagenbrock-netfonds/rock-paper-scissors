package de.netfonds.rockpaperscissors;

import de.netfonds.rockpaperscissors.game.GameRule;
import de.netfonds.rockpaperscissors.game.Game;
import de.netfonds.rockpaperscissors.game.HandShape;

import java.util.Random;
import java.util.Set;

public class DefaultGame extends Game<DefaultGame.DefaultHandShape> {

    private static final Random RANDOM = new Random();
    private static final DefaultHandShape[] shapes = DefaultHandShape.values();

    public DefaultGame() {
        super(
                () -> DefaultHandShape.PAPER,
                () -> shapes[RANDOM.nextInt(shapes.length)],
                Set.of(
                        GameRule.of(DefaultHandShape.ROCK, DefaultHandShape.SCISSORS),
                        GameRule.of(DefaultHandShape.PAPER, DefaultHandShape.ROCK),
                        GameRule.of(DefaultHandShape.SCISSORS, DefaultHandShape.ROCK)
                ));
    }

     enum DefaultHandShape implements HandShape {
        ROCK,
        PAPER,
        SCISSORS
    }
}


