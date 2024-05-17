Feature: Saboteur standing on a pipe attempts to make it slippery
  As a Saboteur, I want to be able to make pipes slippery, so that other players will not be able to stay on them.

  Scenario: A Saboteur with enough AP standing on a non-slippery pipe makes it slippery.
    Given that a pipe is not slippery
    And I am a Saboteur
    And I am standing on the pipe
    And I have enough AP
    When I attempt to make the pipe slippery
    Then the pipe should get slippery

  Scenario: A Saboteur standing on a slippery pipe attempts to make it slippery again.
    Given that a pipe is slippery
    And I am a Saboteur
    And I am standing on the pipe
    And I have enough AP
    When I attempt to make the pipe slippery
    Then nothing should happen, as the pipe is already slippery

  Scenario: A Saboteur without enough AP standing on a pipe attempts to make it slippery.
    Given that I am standing on a pipe
    And I am a Saboteur
    And I don't have enough AP
    When I attempt to make the pipe slippery
    Then the pipe shouldn't get slippery