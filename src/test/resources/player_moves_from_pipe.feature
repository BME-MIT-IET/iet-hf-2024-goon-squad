Feature: Player attempts to move from a pipe to one of its neighbouring components.
  As a Player, I want to be able to move from pipes to one of their neighbouring components.

  Scenario: A non-stuck Player standing on a pipe with enough AP moves to a valid neighbouring component.
    Given that I intend to move to an existing neighbour
    And I am standing on the pipe
    And I am not stuck
    And I have enough AP
    When I attempt to move to the selected component
    Then the movement should be successful

  Scenario: A stuck Player standing on a pipe attempts to move to a valid neighbouring component.
    Given that I intend to move to an existing neighbour
    And I am standing on the pipe
    And I am stuck
    When I attempt to move to the selected component
    Then the movement should be unsuccessful

  Scenario: A non-stuck Player standing on a pipe without enough AP attempts to move to a valid neighbouring component.
    Given that I intend to move to an existing neighbour
    And I am standing on the pipe
    And I am not stuck
    And I don't have enough AP
    When I attempt to move to the selected component
    Then the movement should be unsuccessful

  Scenario: A Player standing on a pipe attempts to move to a non-existent neighbouring component.
    Given that I intend to move to a non-existing neighbour
    And I am standing on the pipe
    When I attempt to move to the selected component
    Then the movement should be unsuccessful