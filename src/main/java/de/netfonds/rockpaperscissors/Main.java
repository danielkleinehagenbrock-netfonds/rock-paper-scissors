package de.netfonds.rockpaperscissors;


import de.netfonds.rockpaperscissors.game.Game;

/**
 * Main entry point for the Rock Paper Scissors game application.
 *
 * <p>This application simulates a Rock Paper Scissors game between two players:
 * <ul>
 *   <li>Player A: Always plays PAPER</li>
 *   <li>Player B: Plays randomly depending on the game type:
 *      <ul>
 *          <li>one of: ROCK, PAPER, or SCISSORS</li>
 *          <li>one of: ROCK, PAPER, SCISSORS, SPOCK, or LIZARD</li>
 *      </ul>
 *   </li>
 * </ul>
 *
 * <p>The game runs for exactly 100 rounds and outputs the final statistics
 * for both players including wins, losses, and draws.
 *
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 * @see Game
 * @see Game.Builder#newClassicHandShapeGame()
 * @see Game.Builder#newLizardHandShapeGame()
 */
public class Main {

    /**
     * Number of rounds to be played in the game.
     * As per requirement, exactly 100 rounds are played.
     */
    private static final int ROUNDS_TO_PLAY = 100;

    /**
     * Main method that starts the Rock Paper Scissors game.
     *
     * <p>Creates a new {@link Game} instance and plays the specified
     * number of rounds.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Game.Builder
                .newClassicHandShapeGame()
                .playGame(ROUNDS_TO_PLAY);

    }
}