package oop2023.a01c.sol2;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GUILogicImplConsoleLoggerIntegrationTest {

    private static final int SIZE = 10;
    private static final Position FIRST_EDGE = new Position(2,2);
    private static final int FIRST_VALUE = 1;
    private static final Position SECOND_EDGE = new Position(4,5);
    private static final int SECOND_VALUE = 2;
    private static final Position SOME_POSITION = new Position(1, 7);

    private GUI gui;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void init() {
        this.outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outputStream));
        this.gui = new GUI(SIZE, new LogicImpl(SIZE), new ConsoleLogger());
    }

    @Test
    @DisplayName("Can select two cells to identify a rectangle and see the expected logs")
    void testCanSelectTwoCellsToIdentifyARectangleAndSeeTheExpectedLogs() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE));
        assertEquals(String.valueOf(FIRST_VALUE), getCellText(FIRST_EDGE));
        assertEquals(String.valueOf(SECOND_VALUE), getCellText(SECOND_EDGE));
        assertEquals("""
                Game started
                Clicked cell at [%d, %d]
                Marked edge with number %d
                Clicked cell at [%d, %d]
                Marked edge with number %d"""
                        .formatted(FIRST_EDGE.x(), FIRST_EDGE.y(), FIRST_VALUE,
                                SECOND_EDGE.x(), SECOND_EDGE.y(), SECOND_VALUE),
                this.output());
    }

    @Test
    @DisplayName("Can fill and extend the selected rectangle and see the expected logs")
    void testCanFillAndExtendTheSelectedRectangleAndSeeTheExpectedLogs() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE));
        this.outputStream.reset();
        this.clickCells(List.of(SOME_POSITION, SOME_POSITION));
        this.checkRectangleBetween(new Position(FIRST_EDGE.x() - 1, FIRST_EDGE.y() - 1),
                new Position(SECOND_EDGE.x() + 1, SECOND_EDGE.y() + 1));
        assertEquals("""
                Clicked cell at [%d, %d]
                Rectangle updated
                Clicked cell at [%d, %d]
                Rectangle updated"""
                        .formatted(SOME_POSITION.x(), SOME_POSITION.y(), SOME_POSITION.x(), SOME_POSITION.y()),
                this.output());
    }

    @Test
    @DisplayName("When the grid is filled, the GUI closes and the expected logs are printed")
    void testWhenTheGridIsFilledTheGUIClosesAndTheExpectedLogsArePrinted() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE));
        this.clickNTimes(SOME_POSITION, 5);
        this.outputStream.reset();
        this.click(SOME_POSITION);
        this.checkGridFilled();
        assertFalse(this.gui.isDisplayable());
        assertEquals("""
                Clicked cell at [%d, %d]
                Game ended"""
                        .formatted(SOME_POSITION.x(), SOME_POSITION.y()),
                this.output());
    }

    private String output() {
        return this.outputStream.toString().trim().replace("\r\n", "\n");
    }

    private void click(final Position position) {
        this.gui.getGrid().get(position).doClick();
    }

    private void clickCells(final List<Position> positions) {
        positions.forEach(this::click);
    }

    private void clickNTimes(final Position position, final int n) {
        IntStream.range(0, n).forEach(i -> this.click(position));
    }

    private String getCellText(final Position position) {
        return this.gui.getGrid().get(position).getText();
    }

    private void checkRectangleBetween(final Position firstPosition, final Position secondPosition) {
        for (int x = firstPosition.x(); x <= secondPosition.x(); x++) {
            for (int y = firstPosition.y(); y <= secondPosition.x(); y++) {
                final Position position = new Position(x, y);
                if (!position.equals(FIRST_EDGE) && !position.equals(SECOND_EDGE)) {
                    assertEquals("0", this.getCellText(new Position(x, y)));
                }
            }
        }
    }

    private void checkGridFilled() {
        this.checkRectangleBetween(new Position(0, 0), new Position(SIZE - 1, SIZE - 1));
    }
}
