Feature: Extending the rectangle until the grid is filled with zeros
  As a player of the game
  I want to be able to extend the rectangle
  so that I can complete the game

  Background: Start with a grid
    Given I have a square grid of size 10

  Scenario Outline: Expanding the rectangle one time without filling the grid
    Given I have a grid with the cell at [<x1>, <y1>] marked with "1"
    And I have a grid with the cell at [<x2>, <y2>] marked with "2"
    And the identified rectangle is filled with <n1> "0"
    When I click any cell
    Then the rectangle is extended by one cell in all directions, resulting in <n2> cells marked with "0"
    Examples:
      | x1 | y1 | x2 | y2 | n1  | n2 |
      | 0  | 0  | 7  | 3  | 30  | 43 |
      | 7  | 3  | 0  | 0  | 30  | 43 |
      | 2  | 2  | 4  | 5  | 10  | 28 |

  Scenario Outline: Expanding the rectangle two times without filling the grid
    Given I have a grid with the cell at [<x1>, <y1>] marked with "1"
    And I have a grid with the cell at [<x2>, <y2>] marked with "2"
    And the identified rectangle is filled with <n1> "0"
    When I click any cell
    And I click any cell again
    Then the rectangle is extended by two cells in all directions, resulting in <n2> cells marked with "0"
    Examples:
      | x1 | y1 | x2 | y2 | n1  | n2 |
      | 0  | 0  | 7  | 3  | 30  | 58 |
      | 7  | 3  | 0  | 0  | 30  | 58 |
      | 2  | 2  | 4  | 5  | 10  | 54 |

  Scenario Outline: Expanding the rectangle and filling the grid
    Given I have a grid with the cell at [<x1>, <y1>] marked with "1"
    And I have a grid with the cell at [<x2>, <y2>] marked with "2"
    And the identified rectangle is filled with <n> "0"
    When I click any cell
    Then the grid is filled
    And the GUI closes
    Examples:
      | x1 | y1 | x2 | y2 | n  |
      | 0  | 0  | 8  | 8  | 79 |
      | 9  | 9  | 1  | 1  | 79 |
      | 1  | 1  | 8  | 8  | 62 |
