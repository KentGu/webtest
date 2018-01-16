@demo
Feature: F00001
  Setup normal agreement
  This feature is a demo feature to show how BDD works

  In order to validate normal agreement statement
  As a colline user
  I want agreement to be setup with specified organisation

  Background:
    Given have login with credential login.credential.test1
    And have default organisation global preferences
    And have default collateral preferences
    And have default system preferences
    And have default letter configurations

#  available tags
#  @manual @deprecated @todo @wip @ignore
#  @D12345 @F12345 @V14.1.0
  @kenny
  Scenario: Setup agreement with newly created counterparty
  """
  This is sample documentation section
  ===============
  This section describes how to use documentation and show the difference against with comments
  """
    Given have organisation org
    And have OTC agreement agr
#    When view agreement sea
#    Then the statement should be sta

  @kenny @manual
  Scenario: Setup agreement with newly created principal
    Given have organisation org
    And have OTC agreement agr
    When view agreement sea
    Then agreement statement should be sta

  @kenny @wip
  Scenario Outline: Setup agreement with specified organisations
    Given have organisations <orgs>
    And have <line> agreement <agr>
    When view agreement <sea>
    Then agreement statement should be <sta>
    Examples:
      | orgs  | line | agr | sea | sta |
      | p1,c1 | OTC  | a1  | s1  | t1  |
      | p2,c2 | OTC  | a2  | s2  | t2  |

  @kelly @todo
  Scenario: Setup agreement with ?