package de.netfonds.rockpaperscissors.game;

import java.util.Set;

public class GameRule<S extends HandShape> {
    private final S handShape;
    private final Set<S> defeatingHandShapes;

    private GameRule(final S handShape, final Set<S> defeatingHandShapes) {
        this.handShape = handShape;
        this.defeatingHandShapes = defeatingHandShapes;
    }

    @SafeVarargs
    public static <E extends HandShape> GameRule<E> of(final E handShape, final E... defeatingHandShapes) {
        return new GameRule<>(handShape, Set.of(defeatingHandShapes));
    }

    public S getHandShape() {
        return handShape;
    }

    public GameRuleResult getRuleResult(final S otherHandShape) {
        if (handShape.equals(otherHandShape)) {
            return GameRuleResult.DRAW;
        }
        if (defeatingHandShapes.contains(otherHandShape)) {
            return GameRuleResult.WIN;
        }
        return GameRuleResult.LOSE;
    }
}
