package oop2023.a01c.sol2;

public interface Logger {

    void gameStarted();

    void cellClicked(Position position);

    void edgeMarked(int edgeNumber);

    void rectangleUpdated();

    void gameEnded();
}
