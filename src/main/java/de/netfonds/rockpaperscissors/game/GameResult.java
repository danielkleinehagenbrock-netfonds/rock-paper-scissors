package de.netfonds.rockpaperscissors.game;

public record GameResult(
        int numberOfPlayerAWins,
        int numberOfPlayerBWins,
        int numberOfDraws
) {
}
