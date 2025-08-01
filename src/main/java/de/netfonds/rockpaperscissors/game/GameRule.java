package de.netfonds.rockpaperscissors.game;

import java.util.Set;

/**
 * Represents a rule defining which hand shapes a particular shape can defeat.
 *
 * <p>Each rule associates one hand shape with a set of hand shapes it can defeat.
 * This design enables flexible rule definitions for different game variants.
 *
 * <p>The class is immutable and thread-safe. Rules are typically created using
 * the static factory method {@link #of(HandShape, HandShape...)}.
 *
 * @param <S> the type of hand shapes used in this rule
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 */
public class GameRule<S extends HandShape> {
    /**
     * The hand shape this rule applies to.
     */
    private final S handShape;

    /**
     * Unmodifiable set of hand shapes that this shape can defeat.
     */
    private final Set<S> defeatingHandShapes;


    /**
     * Private constructor - use {@link #of(HandShape, HandShape...)} instead.
     *
     * @param handShape           the hand shape this rule applies to
     * @param defeatingHandShapes set of shapes this shape defeats
     */
    private GameRule(final S handShape, final Set<S> defeatingHandShapes) {
        this.handShape = handShape;
        this.defeatingHandShapes = defeatingHandShapes;
    }

    /**
     * Creates a new game rule with specified defeat relationships.
     *
     * <p>Factory method that creates an immutable rule specifying which hand shapes
     * the given shape can defeat.
     *
     * <p><strong>Example:</strong>
     * <pre>{@code
     * GameRule<HandShape> rule = GameRule.of(ROCK, SCISSORS, LIZARD);
     * }</pre>
     *
     * @param <E>                 the type of hand shapes
     * @param handShape           the hand shape this rule applies to
     * @param defeatingHandShapes shapes that this shape can defeat
     * @return a new immutable game rule
     */
    @SafeVarargs
    public static <E extends HandShape> GameRule<E> of(final E handShape, final E... defeatingHandShapes) {
        return new GameRule<>(handShape, Set.of(defeatingHandShapes));
    }

    /**
     * Returns the hand shape this rule applies to.
     *
     * @return the hand shape for this rule
     */
    public S getHandShape() {
        return handShape;
    }

    /**
     * Determines the result when this hand shape meets another.
     *
     * <p>Compares the other hand shape against this rule's defeat set:
     * <ul>
     *   <li>{@link GameRuleResult#WIN} if other shape is in defeat set</li>
     *   <li>{@link GameRuleResult#DRAW} if shapes are equal</li>
     *   <li>{@link GameRuleResult#LOSE} otherwise</li>
     * </ul>
     *
     * @param otherHandShape the opposing hand shape
     * @return the comparison result
     */
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
