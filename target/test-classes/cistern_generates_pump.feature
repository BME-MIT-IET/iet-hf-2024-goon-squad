Feature: Cistern attempts to generate a pump.
  As a Cistern, I want to be able to generate pumps (one at a time), so that they can be pe picked up and placed down by Mechanics.

  Scenario: A Cistern with no pump on it successfully generates a pump.
    Given that I am a Cistern
    And I have no generated pumps
    When it is time to generate a pump
    Then the pump should get generated

  Scenario: A cistern with a pump on it already attempts to generate another one.
    Given that I am a Cistern
    And I already have a generated pump
    When it is time to generate a pump
    Then the pump shouldn't get generated