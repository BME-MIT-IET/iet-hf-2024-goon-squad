Feature: Player passes their turn.
  As a Player, I want to be able to pass my turn, so that my turn ends and the next player can start theirs.

  Scenario: A Player passes their turn.
    Given that I am the active player
    When I pass my turn
    Then my turn should be over