Feature: A pump experiences a breakdown.
  As a Pump, I want to sometimes break down and become non-functional, so that I provide an extra challenge for the players.

  Scenario: A functioning pump breaks down.
    Given that I am a pump
    And I am functional
    When I experience a breakdown
    Then I should become non-functional

  Scenario: A non-functioning pump breaks down.
    Given that I am a pump
    And I am non-functional
    When I experience a breakdown
    Then nothing happens, as I am already broken