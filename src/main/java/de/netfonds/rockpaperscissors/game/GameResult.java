package de.netfonds.rockpaperscissors.game;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Immutable record representing the final results of a complete game.
 *
 * <p>Aggregates the outcomes of all rounds played, providing counts for
 * wins, losses, and draws. As a record, it automatically provides standard
 * methods like {@code equals()}, {@code hashCode()}, and {@code toString()}.
 *
 * @param numberOfPlayerAWins number of rounds won by Player A
 * @param numberOfPlayerBWins number of rounds won by Player B
 * @param numberOfDraws       number of rounds that ended in a draw
 * @author Daniel Kleinehagenbrock
 * @version 1.0
 * @since 1.0
 */
public record GameResult(
        int numberOfPlayerAWins,
        int numberOfPlayerBWins,
        int numberOfDraws
) {
    /**
     * Returns the aggregated number of rounds resulting in this GameResult.
     *
     * @return The number of rounds played.
     */
    public int numberOfRounds() {
        return numberOfPlayerAWins + numberOfPlayerBWins + numberOfDraws;
    }

    /**
     * Returns the rate Player A won in the rounds resulting in this GameResult
     *
     * @return The rate Player A won as a percentage value.
     */
    public BigDecimal playerAWinRate() {
        return getRate(numberOfPlayerAWins);
    }

    /**
     * Returns the rate Player B won in the rounds resulting in this GameResult
     *
     * @return The rate Player B won as a percentage value.
     */
    public BigDecimal playerBWinRate() {
        return getRate(numberOfPlayerBWins);
    }

    /**
     * Returns the rate of rounds ended as drawn.
     *
     * @return The rate of rounds ended as drawn as a percentage value.
     */
    public BigDecimal drawRate() {
        return getRate(numberOfDraws);
    }

    /**
     * Calculates a percentage rate base on the played rounds.
     *
     * @param number The number the calculated percentage is based on.
     * @return Percentage of played rounds.
     */
    private BigDecimal getRate(int number) {
        final BigDecimal numberOfRounds = BigDecimal.valueOf(numberOfRounds());
        System.out.println(numberOfRounds);
        return number > 0 ?
                BigDecimal.valueOf(number)
                        .movePointRight(2)
                        .divide(numberOfRounds, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
    }
}
