@Optimisation @regression
Feature: Optimisation

  Background:
    Given have login with credential login.credential.test1
    And enable all enable.all.security.udf collateral static data

  @KentGu @T33193 @F11280 @14.1.0
  Scenario: Optimisation D-01-05 To check Grace period works properly in OPT for net bookings
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset filter.asset.rule
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Asset filter.agreement.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    When execute SQL query sql.optagreementpool
    Then database query return result should be sql.optagreementpool.result
    When execute SQL query sql.optbooking
    Then database query return result should be sql.optbooking.result
    When execute SQL query sql.opteligibility
    Then database query return result should be sql.opteligibility.result

  @KentGu @T33231 @F11825 @F7311 @14.1.0
  Scenario: Optimisation OPRR-01-02 Verify optConcentrationLimt pool display correct data after trigger and Group is added for CL rule
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset filter.asset.rule
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement filter.agreement.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.concentration.limit.table
    Then database query return result should be before.eligibility.rule.template.linked
    When Go to agreement fcm.agreement summary page by URL
    And edit FCM agreement to link.eligibility.rule.template.to.agreement in collateral tab
    And navigate to optimisation rule builder page
    And Optimisation - select optimisation - Rules
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.concentration.limit.table
    Then database query return result should be after.eligibility.rule.template.linked

  @KentGu @T27396 @F6271 @F7311 @D24916 @F10170 @13.0.0 @13.0.0.SP6.1 @14.1.0
  Scenario: Optimisation RR-AGFR-01-05 Verify data display correctly in OptFX table
  """
    Verify data display correctly in OptFX table
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule.before.delete.fx.rate.set
    And Optimisation - run rule run.optimisation.rule.before.delete.fx.rate.set.name
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.fx.table
    Then database query return result should be fx.set.record.in.opt.fx.table.before.delete.fx.rate.set
    # Check ack xml after trigger optimisation rule from task scheduler
    When navigate to FX rate page
    And delete FX rate sets fx.rate.set
    And navigate to optimisation rule builder page
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule.after.delete.fx.rate.set
    And Optimisation - run rule run.optimisation.rule.after.delete.fx.rate.set
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.fx.table
    Then database query return result should be fx.set.record.in.opt.fx.table.after.delete.fx.rate.set

  @KentGu @T27486 @F6271 @F7311 @13.0.0
  Scenario: Optimisation RR-AGFR-01-22 Verify data display correctly in OptAgreementPool table when filtered and ranked by Agreement rank number
  """
    Verify data display correctly in OptAgreementPool table when filtered and ranked by Agreement rank number
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.filter.rule
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset opt.asset.filter.rule
    And Optimisation - select rankings - Agreement
    And Optimisation - add rankings - Agreement opt.agreement.ranking.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.agreement.pool
    Then database query return result should be opt.agreement.pool.result
    # Check ack xml after trigger optimisation rule from task scheduler

  @KentGu @T27536 @F6271 @F7311 @D19030 @13.0.0 @13.0.0.SP1
  Scenario: Optimisation RR-AGFR-01-37 Verify the Call deficit bookings can display correctly in OptBooking(FCM Agreement)
  """
    Verify the Call deficit bookings can display correctly in OptBooking(FCM Agreement)
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset opt.asset.filter.rule
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.filter.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    # Check request JMS
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.agreement.pool
    Then database query return result should be opt.agreement.pool.result
    When execute SQL query check.opt.booking.pool
    Then database query return result should be opt.booking.pool.result

  @KentGu @T27416 @F6271 @F7311 @D19857 @D20007 @13.0.0
  Scenario: Optimisation RR-AFAR-01-07 Verify data display correctly in optassetdefinition table when filtered by Issue rating
  """
    Verify data display correctly in optassetdefinition table when filtered by Issue rating
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset opt.asset.filter
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.filter
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    # Check request JMS
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.asset.definition
    Then database query return result should be opt.asset.definition.result

  @KentGu @T31583 @F10590 @14.1.0 @COL-3885
  Scenario: Optimisation RR-AFAR-01-100 Verify the Security UDFs and Additional Info available to the Asset Definition Pool
  """
    Optimisation RR-AFAR-01-100 Verify the Security UDFs and Additional Info available to the Asset Definition Pool
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset opt.asset.class.filter.rule
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.filter.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule.before.disable.udf
    And Optimisation - run rule run.optimisation.rule.before.disable.udf
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.asset.definition
    Then database query return result should be opt.asset.definition.result.before.disable.static.data
    When navigate to collateral static data page
    And disabled collateral static data udf.2,udf.3,udf.4,udf.5,udf.6,udf.7,udf.8,udf.9,udf.11,udf.12,udf.13,udf.14,udf.17,udf.18,udf.19,udf.20,udf.21,udf.22,udf.23,udf.24,udf.25,udf.26,udf.27,udf.28,udf.29
    And navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset opt.asset.udf.filter.rule
    And Optimisation - select rankings - Asset
    And Optimisation - add rankings - Asset opt.asset.ranking.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule.after.disable.udf
    And Optimisation - add optimisation - Rules optimisation.rule.with.ranking.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - run rule run.optimisation.rule.after.disable.udf
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.asset.definition
    Then database query return result should be opt.asset.definition.result.after.disable.static.data
    When Optimisation - select optimisation - Rules
    And Optimisation - run rule run.optimisation.rule.with.ranking.rule
    And Optimisation - select optimisation - Results
    And execute SQL query check.opt.asset.definition
    Then database query return result should be opt.asset.definition.result.with.ranking

  @KentGu @T33201 @COL-5044 @F11529
  Scenario: Optimisation OPTR-01-29 Verify the output in OptConcentrationLimit pool correct after implemented Template vs Agreement Eligibility Rules
  """
    Objective
    Verify the output in OptConcentrationLimit pool correct after implemented Template vs Agreement Eligibility Rules
    Expected:
    1. Use Agreement Definition
    2. Use template Definition if not defined in agreement
    Precondition
    N/A
    Test Calls
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Asset
    And Optimisation - add filters - Asset filter.asset.rule
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement filter.agreement.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    And Database - execute SQL query check.opt.concentration.limit.table
    Then Database - database query return result should be opt.concentration.limit.table.result

  @KentGu @T27610 @COL-4285 @F6271 @F7311 @wip
  Scenario: Optimisation RR-AGFR-01-52 Verify the correct data display in OptAgreementPool for Multimodel agreeemnt_Net
  """
    Objective
    Verify the correct data display in OptAgreementPool for Multimodel agreeemnt_Net
    Precondition
    Install colline system with Optimisation license
    Test Calls
    Exclude Top model event of Agreement Pool
  """
    When navigate to optimisation rule builder page
    And Optimisation - select filters - Agreement
    And Optimisation - add filters - Agreement opt.agreement.filter.rule
    And Optimisation - select optimisation - Rules
    And Optimisation - add optimisation - Rules optimisation.rule
    And Optimisation - run rule run.optimisation.rule
    And Optimisation - select optimisation - Results
    And Database - execute SQL query check.opt.agreement.pool
    Then Database - database query return result should be opt.agreement.pool.result
    When Database - execute SQL query check.opt.booking.pool
    Then Database - database query return result should be opt.booking.pool.result