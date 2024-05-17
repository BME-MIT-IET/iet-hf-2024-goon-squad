Feature: Player standing on a pipe attempts to puncture it.
  As a Player, I want to be able to puncture pipes while standing on them, so that they become non-functional.

  Scenario: A Player with enough AP punctures a working pipe while standing on it.
    Given that a pipe is working
    And I am standing on the pipe
    And I have enough AP
    When I puncture the pipe
    Then it should be punctured

  Scenario: A Player without enough AP attempts to puncture a working pipe while standing on it.
    Given that a pipe is working
    And I am standing on the pipe
    And I don't have enough AP
    When I attempt to puncture the pipe
    Then the pipe shouldn't get punctured

  Scenario: A Player without enough AP attempts to puncture a broken pipe while standing on it.
    Given that a pipe is punctured
    And I am standing on the pipe
    And I have enough AP
    When I attempt to puncture the pipe
    Then the pipe shouldn't be punctured again