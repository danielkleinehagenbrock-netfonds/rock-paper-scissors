package de.netfonds.rockpaperscissors.game;

@FunctionalInterface
public
interface GameStrategy<S extends HandShape> {
    S getNextHandShape();
}
