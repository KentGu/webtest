@ETDFamilyAgreement
Feature: ETDFamilyAgreement

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have default organisation global preferences

  @MarcSales @COL-1793 @COL-5183 @FamilyAgreement @v15.4.0 @wip
  Scenario: Create, search and copy an ETD family agreement with configurable rules
    When add ETD agreements family.configurableRule.etd.agreement
    And approve the ETD agreement
    And search agreement etd.familyAgrmt.search
    And edit agreement etd.familyAgrmt.search.result in agreement search page
    And copy agreement 1 times
    And search agreement etd.familyAgrmt.search
    And edit agreement etd.familyAgrmt.search.draft.result in agreement search page
    Then party Counterparty tab should be populated like agreement family.configurableRule.etd.agreement

  @MarcSales @COL-1793 @COL-5183 @FamilyAgreement @v15.4.0 @wip
  Scenario: Linkage set copy of ETD family agreement with configurable rules
    When I create the linkage set linkageSet.familyAgreement
    When add ETD agreements family.configurableRule.etd.agreement.linkageset1
    And approve the ETD agreement
    And add ETD agreements family.configurableRule.etd.agreement.linkageset2
    And approve the ETD agreement
    And search agreement linkageset2.search
    And edit agreement linkageset2.search.result in agreement search page
    And linkage set copy agreement summary2
    And search agreement linkageset1.search
    And edit agreement linkageset1.search.result in agreement search page
    Then agreement summary – party/counterparty should be summary2