package oop2023.a01c.sol2;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.*;

import javax.swing.*;
import java.util.*;

public class FeaturesSteps {
    private GUI gui;

    private void clickCell(final int x, final int y) {
        this.gui.getGrid().get(new Position(x, y)).doClick();
    }

    private String getTextOfCell(final int x, final int y) {
        return this.gui.getGrid().get(new Position(x, y)).getText();
    }

    @Given("I have a square grid of size {int}")
    public void iHaveASquareGridOfSize(final int size) {
        this.gui = new GUI(size);
    }

    @Given("I have an empty grid")
    public void iHaveAnEmptyGrid() {
        assertTrue(this.gui.getGrid().values().stream().allMatch(b -> b.getText().isEmpty()));
    }

    @When("I click the cell at [{int}, {int}]")
    public void iClickTheCellAt(final int x, final int y) {
        this.clickCell(x, y);
    }

    @Then("the cell at [{int}, {int}] is marked with {string}")
    public void theCellAtIsMarkedWith(final int x, final int y, final String mark) {
        assertEquals(mark, this.getTextOfCell(x, y));
    }

    @Given("I have a grid with the cell at [{int}, {int}] marked with {string}")
    public void iHaveAGridWithTheCellAtMarkedWith(final int x, final int y, final String mark) {
        this.clickCell(x, y);
        this.theCellAtIsMarkedWith(x, y, mark);
    }
}
