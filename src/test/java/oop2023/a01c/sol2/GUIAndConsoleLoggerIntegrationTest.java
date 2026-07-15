package oop2023.a01c.sol2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GUIAndConsoleLoggerIntegrationTest {

    private static final int SIZE = 10;
    private static final Position FIRST_EDGE = new Position(2,2);
    private static final int FIRST_VALUE = 1;
    private static final Position SECOND_EDGE = new Position(4,5);
    private static final int SECOND_VALUE = 2;
    private static final Position SOME_POSITION = new Position(1, 7);

    private GUI gui;
    private Logic logic;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void init() {
        this.outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outputStream));
        this.logic = mock(LogicImpl.class);
        this.gui = new GUI(SIZE, this.logic, new ConsoleLogger());
    }

    @Test
    @DisplayName("After the GUI is created, the expected message is printed to standard output")
    void testAfterTheGUIIsCreatedTheExpectedMessageIsPrinted() {
        assertEquals("Game started", this.output());
    }

    @Test
    @DisplayName("After clicking a cell, the expected message containing the cell position is printed to standard output")
    void testAfterClickingACellTheExpectedMessageIsPrinted() {
        this.outputStream.reset();
        this.click(FIRST_EDGE);
        assertEquals("Clicked cell at [" + FIRST_EDGE.x() + ", " + FIRST_EDGE.y() + "]", this.firstOutputLine());
    }

    @Test
    @DisplayName("After clicking a cell, the expected messages are printed to standard output according to the logic")
    void testTheExpectedMessagesArePrintedAccordingToTheLogic() {
        when(this.logic.hit(FIRST_EDGE)).thenReturn(Optional.of(FIRST_VALUE));
        when(this.logic.hit(SECOND_EDGE)).thenReturn(Optional.of(SECOND_VALUE));
        when(this.logic.hit(SOME_POSITION)).thenReturn(Optional.empty());
        this.outputStream.reset();
        this.click(FIRST_EDGE);
        assertEquals("Marked edge with number " + FIRST_VALUE, this.lastOutputLine());
        this.outputStream.reset();
        this.click(SECOND_EDGE);
        assertEquals("Marked edge with number " + SECOND_VALUE, this.lastOutputLine());
        this.outputStream.reset();
        this.click(SOME_POSITION);
        assertEquals("Rectangle updated", this.lastOutputLine());
    }

    @Test
    @DisplayName("When the game is over, the expected message is printed to standard output")
    void testWhenTheGameIsOverTheExpectedMessageIsPrinted() {
        when(this.logic.isOver()).thenReturn(true);
        this.outputStream.reset();
        this.click(FIRST_EDGE);
        assertEquals("Game ended", this.lastOutputLine());
    }

    private String output() {
        return this.outputStream.toString().trim();
    }

    private String firstOutputLine() {
        return this.output().split("\\R")[0];
    }

    private String lastOutputLine() {
        final String[] lines = this.output().split("\\R");
        return lines[lines.length - 1];
    }

    private void click(final Position position) {
        this.gui.getGrid().get(position).doClick();
    }
}
