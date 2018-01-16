@StaticDataMaintenance
Feature: Tier Calculation Rule

  Background:
    Given have login with credential login.credential.test1
    And have collateral static data tier1,tier2,tier3
    And I navigate to the rule set up page
    And I create a new calculation rule rule.1,rule.2,rule.3,rule.4

    @COL-5184 @LeahTarbuck @v15.4.0 @wip
    Scenario: Family Agreement setup
     When have collateral static data col.model.cat
     And feed FX rates feed.USD.fx.rate,feed.GBP.fx.rate,feed.EUR.fx.rate by xml
     And feed log should be feed.fx.rate.result
     And feed agreements feed.etd.agreement by xml
     And feed log should be feed.etd.agrmt.result
     And navigate to agreement search page
     And search agreement etd.agrmt.search
     And set a linked agreement on tier etd.agrmt.search.result feed.etd.family.agreement
     And feed agreements feed.etd.family.agreement by xml
     And feed log should be feed.etd.family.agrmt.result
     And search agreement family.agrmt.search
     And edit agreement family.agrmt.search.result in agreement search page
#     And select configurable rule
