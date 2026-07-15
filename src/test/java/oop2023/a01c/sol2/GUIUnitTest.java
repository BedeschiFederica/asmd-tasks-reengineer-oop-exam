package oop2023.a01c.sol2;

import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GUIUnitTest {

    private static final int SIZE = 10;
    private static final Position FIRST_EDGE = new Position(2,2);
    private static final int FIRST_VALUE = 1;
    private static final Position SECOND_EDGE = new Position(4,5);
    private static final int SECOND_VALUE = 2;
    private static final Position SOME_POSITION = new Position(1, 7);
    private static final int OTHER_VALUE = 0;

    private GUI gui;
    private Logic logic;
    private Logger logger;

    @BeforeEach
    void init() {
        this.logic = spy(mock(LogicImpl.class));
        this.logger = spy(ConsoleLogger.class);
        this.gui = new GUI(SIZE, this.logic, this.logger);
    }

    @Test
    @DisplayName("GUI is initially visible")
    void testGUIIsInitiallyVisible() {
        assertTrue(this.gui.isDisplayable());
    }

    @Test
    @DisplayName("GUI initially contains an empty grid")
    void testGUIContainsInitiallyAnEmptyGrid() {
        this.gui.getGrid().values().forEach(b -> assertTrue(b.getText().isBlank()));
    }

    @Test
    @DisplayName("After the GUI is created, the logger's gameStarted method is called")
    void testLoggerGameStartedMethodIsCalled() {
        verify(this.logger, times(1)).gameStarted();
    }

    @Test
    @DisplayName("After clicking a cell, the logger's cellClicked method is called")
    void testLoggerCellClickedMethodIsCalled() {
        this.click(FIRST_EDGE);
        verify(this.logger, times(1)).cellClicked(FIRST_EDGE);
    }

    @Test
    @DisplayName("After clicking a cell, the logger's methods are called according to the logic")
    void testLoggerMethodsAreCalledAccordingToTheLogic() {
        when(this.logic.hit(FIRST_EDGE)).thenReturn(Optional.of(FIRST_VALUE));
        when(this.logic.hit(SECOND_EDGE)).thenReturn(Optional.of(SECOND_VALUE));
        when(this.logic.hit(SOME_POSITION)).thenReturn(Optional.empty());
        this.click(FIRST_EDGE);
        verify(this.logger, times(1)).edgeMarked(FIRST_VALUE);
        this.click(SECOND_EDGE);
        verify(this.logger, times(1)).edgeMarked(SECOND_VALUE);
        this.click(SOME_POSITION);
        verify(this.logger, times(1)).rectangleUpdated();
    }

    @Test
    @DisplayName("When the game is over, the logger's gameEnded method is called")
    void testLoggerGameEndedMethodIsCalled() {
        when(this.logic.isOver()).thenReturn(true);
        this.click(FIRST_EDGE);
        verify(this.logger, times(1)).gameEnded();
    }

    @Test
    @DisplayName("Clicking a cell changes its text according to the logic")
    void testClickingACellChangesItsTextAccordingToTheLogic() {
        when(this.logic.getMark(FIRST_EDGE)).thenReturn(Optional.of(FIRST_VALUE));
        this.click(FIRST_EDGE);
        verify(this.logic, times(1)).hit(FIRST_EDGE);
        verify(this.logic, times(1)).getMark(FIRST_EDGE);
        assertEquals(String.valueOf(FIRST_VALUE), getCellText(FIRST_EDGE));
    }

    @Test
    @DisplayName("Clicking a cell changes other cell's text according to the logic")
    void testClickingACellChangesOtherCellsTextAccordingToTheLogic() {
        when(this.logic.getMark(SOME_POSITION)).thenReturn(Optional.of(OTHER_VALUE));
        this.click(FIRST_EDGE);
        verify(this.logic, times(1)).getMark(SOME_POSITION);
        assertEquals(String.valueOf(OTHER_VALUE), getCellText(SOME_POSITION));
    }

    @Test
    @DisplayName("When the game is over, the GUI closes")
    void testWhenGameIsOverTheGUICloses() {
        when(this.logic.isOver()).thenReturn(true);
        this.click(SOME_POSITION);
        verify(this.logic, times(1)).isOver();
        assertFalse(this.gui.isDisplayable());
    }

    private void click(final Position position) {
        this.gui.getGrid().get(position).doClick();
    }

    private String getCellText(final Position position) {
        return this.gui.getGrid().get(position).getText();
    }
}
