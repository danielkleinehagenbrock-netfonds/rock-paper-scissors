package de.netfonds.rockpaperscissors.game;

/**
 * Enumeration representing possible outcomes when applying a game rule.
 *
 * <p>When a {@link GameRule} compares two hand shapes, it produces one of
 * these results from the perspective of the rule's hand shape.
 *
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @see GameRule
 * @since 1.0
 */
public enum GameRuleResult {
    /**
     * The rule's hand shape wins
     */
    WIN,
    /**
     * The rule's hand shape loses
     */
    LOSE,
    /**
     * Both hand shapes are the same (draw)
     */
    DRAW
}
