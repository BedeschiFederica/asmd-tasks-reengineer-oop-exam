Feature: Selecting two cells to identify a rectangle
  As a user that wants to play the game
  I want to be able to select two cells
  so that I can identify a rectangle and start playing the game

  Background: Start with a grid
    Given I have a square grid of size 10

  Scenario Outline: Selecting the first cell
    Given I have an empty grid
    When I click the cell at [<x>, <y>]
    Then the cell at [<x>, <y>] is marked with "1"
    Examples:
      | x | y |
      | 0 | 0 |
      | 2 | 3 |
      | 9 | 5 |

  Scenario Outline: Selecting the second cell
    Given I have a grid with the cell at [<x1>, <y1>] marked with "1"
    When I click the cell at [<x2>, <y2>]
    Then the cell at [<x2>, <y2>] is marked with "2"
    Examples:
      | x1 | y1 | x2 | y2 |
      | 0  | 0  | 7  | 3  |
      | 7  | 3  | 0  | 0  |
      | 2  | 2  | 4  | 5  |

  Scenario Outline: Failing to select as second cell the same cell as the first one
    Given I have a grid with the cell at [<x>, <y>] marked with "1"
    When I click the cell at [<x>, <y>]
    Then the cell at [<x>, <y>] is marked with "1"
    Examples:
      | x | y |
      | 0 | 0 |
      | 2 | 3 |
      | 9 | 5 |
