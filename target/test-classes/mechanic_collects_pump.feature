Feature: Mechanic attempts to collect a pump from a cistern.
  As a Mechanic, I want to be able to collect generated pumps (one at a time) from cisterns while standing on them, so that I can later place them down on the map.

  Scenario: A Mechanic with enough AP and an empty inventory collects a pump from a cistern that has a generated pump.
    Given that a cistern has a generated pump
    And I am a Mechanic
    And I am standing on the cistern
    And my inventory is empty
    And I have enough AP
    When I attempt to collect the pump
    Then the pump should be collected

  Scenario: A Mechanic without enough AP and an empty inventory attempts to collect a pump from a cistern that has a generated pump.
    Given that a cistern has a generated pump
    And I am a Mechanic
    And I am standing on the cistern
    And my inventory is empty
    And I don't have enough AP
    When I attempt to collect the pump
    Then the pump shouldn't be collected

  Scenario: A Mechanic with a non-empty inventory attempts to collect a pump from a cistern that has a generated pump.
    Given that a cistern has a generated pump
    And I am a Mechanic
    And I am standing on the cistern
    And my inventory is not empty
    When I attempt to collect the pump
    Then the pump shouldn't be collected

  Scenario: A Mechanic with enough AP and an empty inventory attempts to collect a pump from a cistern that doesn't have a generated pump.
    Given that a cistern has no generated pumps
    And I am a Mechanic
    And I am standing on the cistern
    And my inventory is empty
    And I have enough AP
    When I attempt to collect the pump
    Then there is no pump to collect