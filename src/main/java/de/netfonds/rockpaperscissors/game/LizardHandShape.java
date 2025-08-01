package de.netfonds.rockpaperscissors.game;

/**
 * Enumeration of hand shapes for the extended Lizard Spock variant.
 *
 * <p>Five hand shapes providing more complex rule interactions and
 * reduced probability of draw outcomes compared to the classic three-shape game.
 */
public enum LizardHandShape implements HandShape {
    ROCK,
    PAPER,
    SCISSORS,
    LIZARD,
    SPOCK
}
