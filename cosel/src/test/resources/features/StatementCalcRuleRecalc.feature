@StaticDataMaintenance
Feature: Statement Calculation Rules

  Background:
    Given have login with credential login.credential.test1

  @COL-2002 @TomChamberlain @LeahTarbuck @v15.3.0
  Scenario: Recalculate statement using a new drools calculation rule
    When feed FX rates feed.USD.fx.rate,feed.GBP.fx.rate,feed.EUR.fx.rate by xml
    And feed log should be feed.fx.rate.result
    And have collateral static data col.static.data.d1
    And feed agreements feed.etd.agreement by xml
    And feed log should be feed.etd.agreement.result
    And I navigate to the rule set up page
    And I create a new calculation rule calc.rule.1
    And search agreement etd.agrmt.search
    And edit agreement etd.agrmt.search.result in agreement search page
    And edit ETD agreement to add.drools.rule in document tab
    And view statement
    And recalculate agreement statement
    Then Agreement Statement - etd agreement top level statement summary should be pending.ed.1000
    And fx rates feed.USD.fx.rate,feed.GBP.fx.rate,feed.EUR.fx.rate should be applied correctly in agreement for each pending.ed.1000
    
  @COL-1318 @BenjaminCrispin @v15.3.0
  Scenario: Statement calc script has access to statement udf
    When have collateral static data col.static.data.statement.udf
    And I navigate to the rule set up page
    And I create a new calculation rule calc.rule.udfa
    And have collateral static data col.static.data.mcat1
    And feed agreements feed.etd.agreement by xml
    And feed log should be feed.etd.agreement.result
    And search agreement etd.agrmt.search
    And view completed agreement statement etd.agrmt.search.result
    And show/hide zero balances
    And Agreement Statement - etd agreement statement - expand category model.category.USD
    And select etd model model.category.USD
    Then Agreement Statement - etd agreement model level statement summary should be etd.statement.udfa.500
