package oop2023.a01c.sol2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

public class LogicImplTest {

    private static final int SIZE = 10;
    private static final Position FIRST_EDGE = new Position(2,2);
    private static final Position SECOND_EDGE = new Position(4,5);
    private static final Position SOME_POSITION = new Position(1, 7);

    private Logic logic;

    @BeforeEach
    public void init() {
        this.logic = new LogicImpl(SIZE);
    }

    @Test
    @DisplayName("First hit returns 1")
    public void testFirstHit() {
        assertEquals(Optional.of(1), this.logic.hit(FIRST_EDGE));
    }

    @Test
    @DisplayName("Hit on existing edge returns its index")
    public void testHitExistingEdgeReturnsItsIndex() {
        this.logic.hit(FIRST_EDGE);
        assertEquals(Optional.of(1), this.logic.hit(FIRST_EDGE));
    }

    @Test
    @DisplayName("Second hit returns 2")
    public void testSecondHit() {
        this.logic.hit(FIRST_EDGE);
        assertEquals(Optional.of(2), this.logic.hit(SECOND_EDGE));
    }

    @Test
    @DisplayName("Hits after the second one return Optional.empty")
    public void testHitsAfterTheSecondOneReturnOptionalEmpty() {
        this.performHits(List.of(FIRST_EDGE, SECOND_EDGE));
        assertEquals(Optional.empty(), this.logic.hit(SOME_POSITION));
        this.performHits(List.of(SOME_POSITION, SOME_POSITION));
        assertEquals(Optional.empty(), this.logic.hit(SOME_POSITION));
    }

    @Test
    @DisplayName("Third hit creates '0' marks inside the rectangle identified by the edges")
    public void testThirdHitCreates0MarksInsideRectangle() {
        this.performHits(List.of(FIRST_EDGE, SECOND_EDGE, SOME_POSITION));
        this.checkRectangleBetween(FIRST_EDGE, SECOND_EDGE);
    }

    @Test
    @DisplayName("Fourth hit extends the rectangle")
    public void testFourthHitExtendsRectangle() {
        this.performHits(List.of(FIRST_EDGE, SECOND_EDGE, SOME_POSITION, SOME_POSITION));
        this.checkRectangleBetween(new Position(FIRST_EDGE.x() - 1, FIRST_EDGE.y() - 1),
                new Position(SECOND_EDGE.x() + 1, SECOND_EDGE.y() + 1));
    }

    @Test
    @DisplayName("isOver returns false when the grid is not full")
    public void testIsOverReturnsFalseWhenTheGridIsNotFull() {
        this.performHits(List.of(FIRST_EDGE, SECOND_EDGE, SOME_POSITION, SOME_POSITION));
        assertFalse(this.logic.isOver());
    }

    @Test
    @DisplayName("isOver returns true when the grid is full")
    public void testIsOverReturnsTrueWhenTheGridIsFull() {
        this.performHits(List.of(new Position(1, 1), new Position(SIZE - 1, SIZE - 1), SOME_POSITION, SOME_POSITION));
        assertTrue(this.logic.isOver());
    }

    private void performHits(final List<Position> positions) {
        positions.forEach(p -> this.logic.hit(p));
    }

    private void checkRectangleBetween(final Position firstPosition, final Position secondPosition) {
        for (int x = firstPosition.x(); x <= secondPosition.x(); x++) {
            for (int y = firstPosition.y(); y <= secondPosition.x(); y++) {
                final Position position = new Position(x, y);
                if (!position.equals(FIRST_EDGE) && !position.equals(SECOND_EDGE)) {
                    assertEquals(Optional.of(0), this.logic.getMark(new Position(x, y)));
                }
            }
        }
    }
}
