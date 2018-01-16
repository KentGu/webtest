@ApprovalManager @regression
Feature: ApprovalManager

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1

  @KentGu @AM-Agreements @T6741 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager-AA-01-14 Verify that [Approve Ticked] button works correctly
    When navigate to approvals manager page
    And approvals manager - search agreement approval.search.otc.agreement
    And approvals manager - tick agreements approval.search.result.otc.agreement
    And approvals manager - approve ticked agreements
    Then approvals manager - the search result approval.search.result.otc.agreement is not existed
    When search agreement search.otc.agreement
    And view completed agreement statement search.result.otc.agreement
    Then agreement statement should be otc.agreement.statement.agreement.status.approve

  @KentGu @AM-Agreements @T6762 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager-AA-01-15 Verify that [Approve All] button works correctly
    When navigate to approvals manager page
    And approvals manager - search agreement approval.search.otc.agreement
    And approvals manager - approve all agreements approval.search.result.otc.agreement.cancel
    And search agreement search.otc.agreement
    And view completed agreement statement search.result.otc.agreement
    Then agreement statement should be otc.agreement.statement.pending
    When navigate to approvals manager page
    And approvals manager - search agreement approval.search.otc.agreement
    And approvals manager - approve all agreements approval.search.result.otc.agreement.ok
    And search agreement search.otc.agreement
    And view completed agreement statement search.result.otc.agreement
    Then agreement statement should be otc.agreement.statement.approved

  @KentGu @AM-Organisations @T6707 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager O-01-13 Verify that ApproveTicked Organisation works correctly
    When navigate to approvals manager page
    And approvals manager - search organisation approval.search.organisation
    And approvals manager - tick organisation approval.search.result.organisation
    And approvals manager - approve ticked organisations
    Then approvals manager - the search result approval.search.result.organisation is not existed
    When navigate to view organisation
    And search organisations search.organisation
    Then organisation should be check.organisation.approve

  @KentGu @AM-Organisations @T6643 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager O-01-07 Verify that Approve All Organisation works correctly
    When navigate to approvals manager page
    And approvals manager - search organisation approval.search.organisation
    And approvals manager - approve all organisations approval.search.result.organisation.cancel
    And navigate to view organisation
    And search organisations search.organisation
    Then organisation should be check.organisation.amended
    When navigate to approvals manager page
    And approvals manager - search organisation approval.search.organisation
    And approvals manager - approve all organisations approval.search.result.organisation.ok
    And navigate to view organisation
    And search organisations search.organisation
    Then organisation should be check.organisation.approve

  @KentGu @AM-SecuritiesData @T14269 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager SD-01-15 Verify that Approval Ticked for single securities data
    When navigate to approvals manager page
    And approvals manager - search securities data approval.search.security
    And approvals manager - tick securities data approval.search.result.security
    And approvals manager - approve ticked security data
    Then approvals manager - the search result approval.search.result.security is not existed
    When navigate to security search page
    And search security search.security
    Then security search result should be check.security.approved

  @KentGu @AM-SecuritiesData @T14270 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario: Approval Manager SD-01-16 Verify that Approval All for several securities data
    When navigate to approvals manager page
    And approvals manager - search securities data approval.search.security
    And approvals manager - approve all securities data approval.search.result.security.cancel
    And navigate to security search page
    And search security search.security
    Then security search result should be check.security.amended
    When navigate to approvals manager page
    And approvals manager - search securities data approval.search.security
    And approvals manager - approve all securities data approval.search.result.security.ok
    And navigate to security search page
    And search security search.security
    Then security search result should be check.security.approved

  @KentGu @AM-SecuritiesData @T26797 @13.0.0 @Reviewed @SmokeTest
  Scenario: Approval Manager-AA-03-04 Verify Security ID mapping in  Approve Manager should display the primary key Instrument Id
    When navigate to approvals manager page
    And approvals manager - search securities data search.approval.manager.security.bond.cusip
    Then approvals manager - the search result search.result.approval.manager.security.bond is existed
    When approvals manager - search securities data search.approval.manager.security.bond.isin
    Then approvals manager - the search result search.result.approval.manager.security.bond is existed
    When approvals manager - search securities data search.approval.manager.security.bond.other
    Then approvals manager - the search result search.result.approval.manager.security.bond is existed
    When approvals manager - search securities data search.approval.manager.security.equity.cusip
    Then approvals manager - the search result search.result.approval.manager.security.equity is existed
    When approvals manager - search securities data search.approval.manager.security.equity.isin
    Then approvals manager - the search result search.result.approval.manager.security.equity is existed
    When approvals manager - search securities data search.approval.manager.security.equity.other
    Then approvals manager - the search result search.result.approval.manager.security.equity is existed

  @KentGu @AM-EligibilityRulesTemplate @T24839 @2012.3 @Reviewed @SmokeTest
  Scenario: Approve Manage ER-01-15 Check the batch Approved and Rejected Function
    When navigate to approvals manager page
    And approvals manager - search eligibility rules template search.blank.eligibility.rules.template
    Then approvals manager - the search result search.result.amended.eligibility.rules.template.1,search.result.amended.eligibility.rules.template.2,search.result.amended.eligibility.rules.template.3,search.result.deleted.eligibility.rules.template.1,search.result.deleted.eligibility.rules.template.2,search.result.deleted.eligibility.rules.template.3 is existed
    And approvals manager - the search result search.result.rejected.eligibility.rules.template.1,search.result.rejected.eligibility.rules.template.2 is not existed
    When approvals manager - search eligibility rules template search.amended.eligibility.rules.template
    Then approvals manager - the search result search.result.amended.eligibility.rules.template.1,search.result.amended.eligibility.rules.template.2,search.result.amended.eligibility.rules.template.3 is existed
    And approvals manager - the search result search.result.rejected.eligibility.rules.template.1,search.result.rejected.eligibility.rules.template.2,search.result.deleted.eligibility.rules.template.1,search.result.deleted.eligibility.rules.template.2,search.result.deleted.eligibility.rules.template.3 is not existed
    When approvals manager - reject ticked eligibility rules template reject.search.amended.eligibility.rules.template.without.reason
    And approvals manager - search eligibility rules template search.deleted.eligibility.rules.template
    Then approvals manager - the search result search.result.deleted.eligibility.rules.template.1,search.result.deleted.eligibility.rules.template.2,search.result.deleted.eligibility.rules.template.3 is existed
    And approvals manager - the search result search.result.rejected.eligibility.rules.template.1,search.result.rejected.eligibility.rules.template.2 is not existed
    When approvals manager - reject ticked eligibility rules template reject.search.deleted.eligibility.rules.template.with.reason
    And approvals manager - search eligibility rules template search.blank.eligibility.rules.template
    Then approvals manager - the search result search.result.amended.eligibility.rules.template.1,search.result.amended.eligibility.rules.template.2,search.result.amended.eligibility.rules.template.3,search.result.deleted.eligibility.rules.template.2,search.result.deleted.eligibility.rules.template.3 is existed
    And approvals manager - the search result search.result.rejected.eligibility.rules.template.1,search.result.rejected.eligibility.rules.template.2 is not existed
    When approvals manager - search eligibility rules template search.amended.eligibility.rules.template
    And approvals manager - approve all eligibility rules template approval.all.search.amended.eligibility.rules.template
    Then approvals manager - the search result search.result.amended.eligibility.rules.template.1,search.result.amended.eligibility.rules.template.2,search.result.amended.eligibility.rules.template.3 is not existed

  @KentGu @AM-Statements @T14409 @2011.1 @2011.2 @Reviewed @SmokeTest
  Scenario Outline: Approval Manager S-01-04 Verify that Approve All workflow for statements
    When navigate to approvals manager page
    And approvals manager - search statement <approval.manager.search.statement>
    Then approvals manager - the search result <approval.manager.search.statement.result> <can.or.cannot> be checked
    When approvals manager - approve all statements <all.approval.manager.statement>
    And search agreement <search.agreement>
    And view completed agreement statement <search.agreement.result>
    Then agreement statement should be <check.agreement.statement.status>
    Examples:
      | approval.manager.search.statement                | approval.manager.search.statement.result                     | can.or.cannot | all.approval.manager.statement         | search.agreement                | search.agreement.result                | check.agreement.statement.status          |
      | approval.manager.statement.pending.search        | tick.search.result.approval.manager.statement.pending        | can           | approval.manager.statement.approve.all | search.pending.agreement        | search.result.pending.agreement        | agreement.statement.status.is.approved    |
      | approval.manager.statement.amended.search        | tick.search.result.approval.manager.statement.amended        | cannot        | approval.manager.statement.approve.all | search.amended.agreement        | search.result.amended.agreement        | agreement.statement.status.is.amended     |
      | approval.manager.statement.draft.search          | tick.search.result.approval.manager.statement.draft          | cannot        | approval.manager.statement.approve.all | search.draft.agreement          | search.result.draft.agreement          | agreement.statement.status.is.draft       |
      | approval.manager.statement.stale.approval.search | tick.search.result.approval.manager.statement.stale.approval | can           | approval.manager.statement.approve.all | search.stale.approval.agreement | search.result.stale.approval.agreement | agreement.statement.status.is.approved    |
      | approval.manager.statement.in.progress.search    | tick.search.result.approval.manager.statement.in.progress    | cannot        | approval.manager.statement.approve.all | search.in.progress.agreement    | search.result.in.progress.agreement    | agreement.statement.status.is.in.progress |

  @KentGu @AM-Trades @T27902 @13.0.0 @Reviewed @SmokeTest
  Scenario: Approval Manager-AT-01-13 Verify that [Approve Ticked]&[Approve All] button  works correctly
    Given switch user to login.credential.user2
    And have collateral preferences collateral.preference.trade.four.eye
    When search agreement agreement.search
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type prodcut.qtp on exposure summary page
    And edit trade original.trade1 to new.trade1
    And edit trade original.trade2 to new.trade2
    Given switch user to login.credential.test1
    When navigate to approvals manager page
    And approvals manager - search trade approval.manager.search.trade.1
    And approvals manager - tick trade approval.manager.search.trade.1.result
    And approvals manager - approve ticked trades
    Then approvals manager - the search result approval.manager.search.trade.1.result is not existed
    When approvals manager - search trade approval.manager.search.trade.2
    And approvals manager - approve all trades approval.all.cancel
    Then approvals manager - the search result approval.manager.search.trade.2.result is existed
    When approvals manager - approve all trades approval.all.ok
    Then approvals manager - the search result approval.manager.search.trade.2.result is not existed

  @KentGu @AM-Workflow @T28965 @13.1.0 @Reviewed
  Scenario: Approval AMW-01-16 Verify VM, Cash, Fee events by Model added to Approval Manager workflow
    Given have collateral preferences collateral.preference.workflow.approvals
    When navigate to Exposure Management
    And Exposure Management - margin filters - search margin dynamic filter exposure.manager.event.search
    And Exposure Management - set event exposure.vm.call.event value to exposure.event.call.status.full.dispute
    And Exposure Management - set event exposure.cash.call.event value to exposure.event.call.status.full.dispute
    And Exposure Management - set event exposure.fee.call.event value to exposure.event.call.status.waived
    And Exposure Management - tick events exposure.vm.call.event,exposure.cash.call.event,exposure.fee.call.event
    And Exposure Management - save changes
    And navigate to approvals manager page
    And approvals manager - search workflow approval.manager.workflow.search.amended.dispute
    Then approvals manager - the search result approval.manager.workflow.vm.call.3m,approval.manager.workflow.vm.call.9m,approval.manager.workflow.vm.call.6m,approval.manager.workflow.cash.call.1m,approval.manager.workflow.cash.call.5m,approval.manager.workflow.cash.call.4m is existed
    And approvals manager - the search result approval.manager.workflow.fee.call.2m,approval.manager.workflow.fee.call.7m,approval.manager.workflow.fee.call.5m is not existed
    When approvals manager - search workflow approval.manager.workflow.search.all.status
    Then approvals manager - the search result approval.manager.workflow.vm.call.3m,approval.manager.workflow.vm.call.9m,approval.manager.workflow.vm.call.6m,approval.manager.workflow.cash.call.1m,approval.manager.workflow.cash.call.5m,approval.manager.workflow.cash.call.4m,approval.manager.workflow.fee.call.2m,approval.manager.workflow.fee.call.7m,approval.manager.workflow.fee.call.5m is existed
    And approvals manager - the search result approval.manager.workflow.vm.call.9m,approval.manager.workflow.cash.call.5m,approval.manager.workflow.fee.call.7m cannot be checked
    When approvals manager - tick workflow approval.manager.workflow.vm.call.3m,approval.manager.workflow.cash.call.1m,approval.manager.workflow.vm.call.6m,approval.manager.workflow.cash.call.4m,approval.manager.workflow.fee.call.2m,approval.manager.workflow.fee.call.5m
    And approvals manager - approve ticked workflows
    Then approvals manager - the search result approval.manager.workflow.vm.call.3m,approval.manager.workflow.cash.call.1m,approval.manager.workflow.vm.call.6m,approval.manager.workflow.cash.call.4m,approval.manager.workflow.fee.call.2m,approval.manager.workflow.fee.call.5m is not existed
    And approvals manager - the search result approval.manager.workflow.vm.call.9m,approval.manager.workflow.cash.call.5m,approval.manager.workflow.fee.call.7m can be checked
    When approvals manager - approve all workflow approve.all.ok
    Then approvals manager - the search result approval.manager.workflow.vm.call.9m,approval.manager.workflow.cash.call.5m,approval.manager.workflow.fee.call.7m is not existed
    #Add steps that checking the event in the EM page

  @KentGu @AM-SettlementInstructions @T24571 @2012.3 @Reviewed @SmokeTest
  Scenario: Approval Manager SI-01-07 Verify Settlement Instructions Tab in Approvals Manager for Repo umbrella and fund agreements works correct
    When navigate to approvals manager page
    And approvals manager - search settlement instructions search.approval.manager.settlement.instructions
    Then approvals manager - the search result search.result.approval.manager.settlement.instruction.fund1,search.result.approval.manager.settlement.instruction.fund2,search.result.approval.manager.settlement.instruction.fund3,search.result.approval.manager.settlement.instruction.umberlla is existed
    When approvals manager - tick settlement instructions search.result.approval.manager.settlement.instruction.fund2,search.result.approval.manager.settlement.instruction.fund3
    And approvals manager - approve ticked settlement instructions
    Then approvals manager - the search result search.result.approval.manager.settlement.instruction.fund2,search.result.approval.manager.settlement.instruction.fund3 is not existed
    And approvals manager - the search result search.result.approval.manager.settlement.instruction.fund1,search.result.approval.manager.settlement.instruction.umberlla is existed
    When approvals manager - approve all settlement instructions approval.all.ok
    Then approvals manager - the search result search.result.approval.manager.settlement.instruction.fund1,search.result.approval.manager.settlement.instruction.fund2,search.result.approval.manager.settlement.instruction.fund3,search.result.approval.manager.settlement.instruction.umberlla is not existed
    When navigate to collateral static data settlement data page
    And search collateral static data settlement data search.settlement.data
    Then collateral static data settlement data search result should be settlement.data.fund1,settlement.data.fund2,settlement.data.fund3,settlement.data.umberlla