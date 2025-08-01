package de.netfonds.rockpaperscissors.game;

/**
 * Functional interface for defining player strategies in Rock Paper Scissors games.
 *
 * <p>This interface represents the Strategy pattern for player behavior, allowing
 * different implementations for various playing styles (fixed, random, adaptive, etc.).
 *
 * <p>As a functional interface, it can be implemented using lambda expressions:
 * <pre>{@code
 * GameStrategy<ClassicHandShape> alwaysRock = () -> DefaultHandShape.ROCK;
 * GameStrategy<ClassicHandShape> random = () -> shapes[random.nextInt(shapes.length)];
 * }</pre>
 *
 * @param <S> the type of hand shapes this strategy returns
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 */
@FunctionalInterface
public
interface GameStrategy<S extends HandShape> {

    /**
     * Returns the next hand shape to be played.
     *
     * <p>Called once per round to determine the player's move. Implementations
     * may use any logic including randomness, patterns, or fixed choices.
     *
     * @return the hand shape to play (must not be null)
     */
    S getNextHandShape();
}
