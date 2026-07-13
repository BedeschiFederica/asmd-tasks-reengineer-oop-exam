Feature: Filling the selected rectangle with zeros
  As a player of the game
  I want to be able to fill the selected rectangle with zeros
  so that I can extend it

  Background: Start with a grid
    Given I have a square grid of size 10

  Scenario Outline: Filling the rectangle with 0
    Given I have a grid with the cell at [<x1>, <y1>] marked with "1"
    And I have a grid with the cell at [<x2>, <y2>] marked with "2"
    When I click any cell
    Then the rectangle is filled with <n> "0"
    Examples:
      | x1 | y1 | x2 | y2 | n  |
      | 0  | 0  | 7  | 3  | 30 |
      | 7  | 3  | 0  | 0  | 30 |
      | 2  | 2  | 4  | 5  | 10 |
