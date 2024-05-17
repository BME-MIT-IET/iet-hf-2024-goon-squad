Feature: Mechanic standing on a punctured pipe attempts to fix it.
  As a Mechanic, I want to be able to fix punctured pipes while standing on them, so that they become functional again.

  Scenario: A Mechanic with enough AP fixes a punctured pipe while standing on it.
    Given that a pipe has been punctured
    And I am a Mechanic
    And I am standing on the pipe
    And I have enough AP
    When I fix the pipe
    Then it should be functional again

  Scenario: A Mechanic without enough AP attempts to fix a punctured pipe while standing on it.
    Given that a pipe has been punctured
    And I am a Mechanic
    And I am standing on the pipe
    And I don't have enough AP
    When I try to fix the pipe
    Then the pipe shouldn't be fixed