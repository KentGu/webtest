@Agreement @regression
Feature: Agreement

  Background:
    Given have login with credential login.credential.test1
#    And have collateral preferences collateral.preference.default
#    And have default stp configuration

  @ArchanaShetty @v.15.2.0 @T001 @F001 @wip
  Scenario: Verify that agreement is editable when all privileges are available
    When switch user to privilege.user
    And navigate to privileges page
    And edit privileges all.privileges.available
    And Go to agreement agr.no.prv.3001 statement page by URL
    And quick link - agreement review
    Then check fields in OTC agreement agr.all.prv.3001 in partycounterpaty tab
    Then check fields in OTC agreement agr.all.prv.3001 in documentation tab
    Then check fields in OTC agreement agr.all.prv.3001 in callSchedule tab
    Then check fields in OTC agreement agr.all.prv.3001 in products tab
    Then check fields in OTC agreement agr.all.prv.3001 in collateral tab
    Then check fields in OTC agreement agr.all.prv.3001 in ruleTrigger tab
    Then check fields in OTC agreement agr.all.prv.3001 in additionalFields tab
    Then check fields in OTC agreement agr.all.prv.3001 in settlement tab
    Then check fields in OTC agreement agr.all.prv.3001 in reportingControl tab
    Then check fields in OTC agreement agr.all.prv.3001 in messaging tab

  @ArchanaShetty @v.15.2.0 @T002 @F001 @wip
 Scenario:verify that contacts in documentation is not editable when opsContact privilege is unavailable
   When switch user to privilege.user2
   And navigate to privileges page
   And edit privileges all.privileges.available2
   And edit privileges documentation.privilege.unavailable
   And Go to agreement agr.no.prv.3001 statement page by URL
   And quick link - agreement review
   Then check fields in OTC agreement agr.no.prv.3001 in documentation tab

  @ArchanaShetty @v.15.2.0 @T001 @F001 @wip
  Scenario: Verify that agreement is editable when all privileges are available
    When switch user to privilege.user
    And navigate to privileges page
    And edit privileges all.privileges.available
    And Go to agreement agr.no.prv.3001 statement page by URL
    And quick link - agreement review
    Then check fields in OTC agreement agr.all.prv.3001 in partycounterpaty tab
    Then check fields in OTC agreement agr.all.prv.3001 in documentation tab
    Then check fields in OTC agreement agr.all.prv.3001 in callSchedule tab
    Then check fields in OTC agreement agr.all.prv.3001 in products tab
    Then check fields in OTC agreement agr.all.prv.3001 in collateral tab
    Then check fields in OTC agreement agr.all.prv.3001 in ruleTrigger tab
    Then check fields in OTC agreement agr.all.prv.3001 in additionalFields tab
    Then check fields in OTC agreement agr.all.prv.3001 in settlement tab
    Then check fields in OTC agreement agr.all.prv.3001 in reportingControl tab
    Then check fields in OTC agreement agr.all.prv.3001 in messaging tab

  @ArchanaShetty @v.15.2.0 @T002 @F001 @wip
  Scenario:verify that contacts in documentation is not editable when opsContact privilege is unavailable
    When switch user to privilege.user2
    And navigate to privileges page
    And edit privileges all.privileges.available2
    And edit privileges documentation.privilege.unavailable
    And Go to agreement agr.no.prv.3001 statement page by URL
    And quick link - agreement review
    Then check fields in OTC agreement agr.no.prv.3001 in documentation tab

  @MuktaKulkarni @v.15.2.0 @T003 @F001 @wip
  Scenario: verify that contacts in reportingcontrol is editable when opsReportingControl privilege is only available
    When switch user to privilege.user
    And navigate to privileges page
    And edit privileges all.privileges.available3
    And edit privileges collateral.agreement.legal.edit.unavailable
    And edit privileges collateral.agreement.ops.field.assets.assetnotes.edit.unavailable
    And edit privileges collateral.agreement.ops.field.assets.settlementdate.edit.unavailable
    And edit privileges collateral.agreement.ops.field.contacts.edit.unavailable
    And edit privileges collateral.agreement.ops.field.linkageset.edit.unavailable
    And edit privileges collateral.agreement.ops.tab.messaging.edit.unavailable
    And Go to agreement agr.no.prv.3002 statement page by URL
    And quick link - agreement review
    Then check fields in OTC agreement agr.partyCounterpartyTab.disable in partyCounterparty tab
    Then check fields in OTC agreement agr.documentTab.disable in documentation tab
    Then check fields in OTC agreement agr.callScheduleTab.disable in callSchedule tab
    Then check fields in OTC agreement agr.productsTab.disable in products tab
    Then check fields in OTC agreement agr.collateralTab.disable in collateral tab
    Then check fields in OTC agreement agr.fixedTriggerTab.disable in fixedTrigger tab
    Then check fields in OTC agreement agr.ruleTriggerTab.disable in ruleTrigger tab
    Then check fields in OTC agreement agr.additionalFieldTab.disable in additionalFields tab
    Then check fields in OTC agreement agr.settlementTab.disable in settlement tab
    Then check fields in OTC agreement agr.reportingTab.enable in reportingControl tab
    Then check fields in OTC agreement agr.messagingTab.disable in messaging tab