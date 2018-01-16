@StaticDataMaintenance
Feature: Maintain Statement Calculation Rules

  Background:
    Given have login with credential login.credential.test1
    And I navigate to the rule set up page

  @COL-1516 @TomChamberlain @LeahTarbuck @v15.3.0
  Scenario: Creation of a new calculation rule
    When I create a new calculation rule new.calc.rule
    And I search calculation rules filtering by name new.calc.rule
    Then calculation rule new.calc.rule is displayed in list of rules

  @COL-1516 @TomChamberlain @LeahTarbuck @v15.3.0
  Scenario: Edit an existing calculation rule
    When I create a new calculation rule existing.calc.rule
    And I search calculation rules filtering by name existing.calc.rule
    And I edit the calculation rule and change it to be updated.calc.rule
    And I search calculation rules filtering by name updated.calc.rule
    Then calculation rule updated.calc.rule is displayed in list of rules

  @COL-1516 @TomChamberlain @LeahTarbuck @v15.3.0
  Scenario: Delete an existing calculation rule
    When I create a new calculation rule deleted.calc.rule
    And I search calculation rules filtering by name deleted.calc.rule
    And I delete this calculation rule in the rules table
    And I search calculation rules filtering by name deleted.calc.rule
    Then calculation rule deleted.calc.rule is not displayed in list of rules
    
  @COL-1334 @BenjaminCrispin @15.4.0
  Scenario: Creation of a new tier calculation rule with udf
    When have collateral static data col.static.data.statement.udfa
    And have collateral static data col.static.data.statement.udfb
    And I navigate to the rule set up page
    And I create a new calculation rule new.calc.rule
    And I search calculation rules filtering by name new.calc.rule
    Then calculation rule new.calc.rule is displayed in list of rules
