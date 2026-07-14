package oop2023.a01c.sol2;

public class ConsoleLogger implements Logger {

    @Override
    public void gameStarted() {
        System.out.println("Game started");
    }

    @Override
    public void cellClicked(final Position position) {
        System.out.println("Clicked cell at [" + position.x() + ", " + position.y() + "]");
    }

    @Override
    public void edgeMarked(final int edgeNumber) {
        System.out.println("Marked edge with number " + edgeNumber);
    }

    @Override
    public void rectangleExpanded() {
        System.out.println("Rectangle expanded");
    }

    @Override
    public void gameEnded() {
        System.out.println("Game ended");
    }
}
