package oop2023.a01c.sol2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConsoleLoggerTest {

    private Logger logger;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void init() {
        this.logger = new ConsoleLogger();
        this.outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outputStream));
    }

    @Test
    @DisplayName("gameStarted prints to standard output the expected message")
    public void testGameStartedPrintsExpectedMessage() {
        this.logger.gameStarted();
        assertEquals("Game started", this.output());
    }

    @Test
    @DisplayName("cellClicked prints to standard output the expected message containing the cell position")
    public void testCellClickedPrintsExpectedMessage() {
        this.logger.cellClicked(new Position(2, 3));
        assertEquals("Clicked cell at [2, 3]", this.output());
    }

    @Test
    @DisplayName("edgeMarked prints to standard output the expected message containing the edge number")
    public void testEdgeMarkedPrintsExpectedMessage() {
        this.logger.edgeMarked(1);
        assertEquals("Marked edge with number 1", this.output());
    }

    @Test
    @DisplayName("rectangleUpdated prints the expected message")
    public void testRectangleUpdatedPrintsExpectedMessage() {
        this.logger.rectangleUpdated();
        assertEquals("Rectangle updated", this.output());
    }

    @Test
    @DisplayName("gameEnded prints the expected message")
    public void testGameEndedPrintsExpectedMessage() {
        this.logger.gameEnded();
        assertEquals("Game ended", this.output());
    }

    private String output() {
        return this.outputStream.toString().trim();
    }
}
