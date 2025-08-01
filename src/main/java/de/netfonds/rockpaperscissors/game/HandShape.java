package de.netfonds.rockpaperscissors.game;

/**
 * Marker interface for all hand shapes in Rock Paper Scissors variants.
 *
 * <p>Typical implementation as an enumeration:
 * <pre>{@code
 * public enum ClassicHandShape implements HandShape {
 *     ROCK, PAPER, SCISSORS
 * }
 * }</pre>
 *
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @see ClassicHandShape
 * @see LizardHandShape
 * @since 1.0
 */
public sealed interface HandShape permits ClassicHandShape, LizardHandShape {

}

