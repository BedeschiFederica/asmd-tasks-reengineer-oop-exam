package oop2023.a01c.sol2;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GUIAndLogicImplIntegrationTest {

    private static final int SIZE = 10;
    private static final Position FIRST_EDGE = new Position(2,2);
    private static final int FIRST_VALUE = 1;
    private static final Position SECOND_EDGE = new Position(4,5);
    private static final int SECOND_VALUE = 2;
    private static final Position SOME_POSITION = new Position(1, 7);

    private GUI gui;

    @BeforeEach
    void init() {
        this.gui = new GUI(SIZE, new LogicImpl(SIZE), spy(ConsoleLogger.class));
    }

    @Test
    @DisplayName("The first click on a cell changes the cell's text to 1")
    void testTheFirstClickOnACellChangesTheCellTextTo1() {
        this.clickCells(List.of(FIRST_EDGE));
        assertEquals(String.valueOf(FIRST_VALUE), getCellText(FIRST_EDGE));
    }

    @Test
    @DisplayName("The second click on a cell changes the cell's text to 2")
    void testTheSecondClickOnACellChangesTheCellTextTo2() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE));
        assertEquals(String.valueOf(SECOND_VALUE), getCellText(SECOND_EDGE));
    }

    @Test
    @DisplayName("The third click on a cell changes the rectangle cells' text to 0")
    void testTheThirdClickOnACellChangesTheRectangleCellsTextTo0() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE, SOME_POSITION));
        this.checkRectangleBetween(FIRST_EDGE, SECOND_EDGE);
    }

    @Test
    @DisplayName("The fourth click on a cell extends the rectangle")
    void testTheFourthClickOnACellExtendsTheRectangle() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE, SOME_POSITION, SOME_POSITION));
        this.checkRectangleBetween(new Position(FIRST_EDGE.x() - 1, FIRST_EDGE.y() - 1),
                new Position(SECOND_EDGE.x() + 1, SECOND_EDGE.y() + 1));
    }

    @Test
    @DisplayName("When the grid is filled, the GUI closes")
    void testWhenTheGridIsFilledTheGUICloses() {
        this.clickCells(List.of(FIRST_EDGE, SECOND_EDGE));
        this.clickNTimes(SOME_POSITION, 6);
        this.checkGridFilled();
        assertFalse(this.gui.isDisplayable());
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
