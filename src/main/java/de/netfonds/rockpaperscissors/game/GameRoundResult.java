package de.netfonds.rockpaperscissors.game;

/**
 * Enumeration representing possible outcomes of a single game round.
 *
 * <p>Each round has exactly one outcome from the game engine's perspective.
 * Package-private as it's an internal implementation detail.
 *
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 */
enum GameRoundResult {
    /**
     * Player A wins this round
     */
    PLAYER_A_WINS,
    /**
     * Player B wins this round
     */
    PLAYER_B_WINS,
    /**
     * This round is a draw
     */
    DRAW
}
