@Securities @regression
Feature: Securities

  Background:
    Given have login with credential login.credential.test1

    @TalatRani @2012.2 @F6246 @UniqueSecurity+MarketIds @T26783 @JIRA-COL-2584
    Scenario: Verify Security id plus market is unique for Securities at instrument level when multiple instrument id set
    When navigate to collateral static data page
    And add collateral static data Instrument-Id-Type-CUSP
    Then collateral static data should be Instrument-Id-Type-CUSP
    And navigate to collateral static data page
    And add collateral static data Instrument-Id-Type-RIC
    Then collateral static data should be Instrument-Id-Type-RIC
    And navigate to collateral static data page
    And add collateral static data Instrument-Id-Type-ISIN
    Then collateral static data should be Instrument-Id-Type-ISIN
    And navigate to collateral static data page
    And add collateral static data Instrument-Id-Type-ABC
    Then collateral static data should be Instrument-Id-Type-ABC

    When navigate to asset definition page
    Then add asset class add-Equaties-class
    And add asset types add-Equaties-type
    And navigate to asset definition page
    Then add asset types add-Commodity-type
    And navigate to asset definition page
    Then add asset types add-Properties-type

    When navigate to security search page
    And add security bondinstrument
    And search security bondSecuritySearchAmended
    Then approve security bondSecuritySearchWillBeApproved

    When navigate to security search page
    And add security bondinstrumentDuplicate
    And navigate to security search page
    Then add security bondinstrumentABC

    When navigate to security search page
    And add security equity_United_States
    And search security equitySecuritySearch
    And approve security equitySecuritySearchApproved
    Then feed security templates feed.template.security by xlsx

  @TalatRani @2012.2 @F6246 @EquityMultipleInstrumentIdSet @T26818 @JIRA-COL-2585
  Scenario:Verify Securityid plus market is unique for Equity at instrument level when multiple instrument id set
    When navigate to security search page
    And add security equityinstrument
    And search security equitySearch
    Then approve security equitySearchWillBeApproved
    Then search security equitySearchId2

    When navigate to security search page
    Then add security equityinstrumentDuplicate

    When navigate to security search page
    Then add security equityInstrumentISIN

    When navigate to security search page
    Then add security equityInstrumentABC

  @TalatRani @2012.2 @F6246 @EquityMultipleInstrumentIdSet @T26840 @JIRA-COL-2581
    Scenario:Verify each Price Source Section should trigger Coupon Accural Calculation in Security Editor
    When navigate to security search page
    And add security bondinstrument
    And search security SecuritySearch
    And approve security SearchWillBeApproved
    Then security SearchSecurity should be instrument.Check

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit
    Then security SearchSecurity should be Coupon.Accual.Check.0.002

    When navigate to security search page
    And add security bondinstrument2
    And search security SecuritySearchBond2
    And approve security SearchWillBeApprovedBond2
    Then security SearchSecurity2 should be instrument.Check.Bond2

    When navigate to security search page
    And search security SecuritySearchBond2
    And edit security SearchSecurity2 to AccrualBases.30E/Act
    Then security SearchSecurity2 should be AccrualBasis30E/Act.check

    When navigate to security search page
    And search security SecuritySearchBond2
    And edit security SearchSecurity2 to AccrualBases.Act/360
    Then security SearchSecurity2 should be AccrualBases.Act/360.check

    When navigate to security search page
    And search security SecuritySearchBond2
    And edit security SearchSecurity2 to AccrualBases.Act/365
    Then security SearchSecurity2 should be AccrualBases.Act/365.check

    When navigate to security search page
    And search security SecuritySearchBond2
    And edit security SearchSecurity2 to instrument.ChangeCouponRate
    Then security SearchSecurity2 should be Coupon.Rate.check

    When navigate to security search page
    And search security SecuritySearchBond2
    And edit security SearchSecurity2 to instrument.EmptyCouponRate
    Then security SearchSecurity2 should be Coupon.RateEmpty.check

  @TalatRani @F6246 @PriceSourceSection @T26842 @Jira-COL-2595
    Scenario: SE-01-92 [Price Source Section]Verify each Price Source Section should trigger [Dirty Price] Calculation in Security Editor
    When navigate to security search page
    And add security bondinstrument
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Check.Values

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit
    Then security SearchSecurity should be instrument.Check.dirtyPrice

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit.PriceFactor.2
    Then security SearchSecurity should be PriceFactor.check.2

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument1.cleanPrice.edit
    Then security SearchSecurity should be instrument1.dirtyPrice.check

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument2.CleanPrice.edit
    Then security SearchSecurity should be instrument2.dirtyPrice.check

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument3.edit.cleanPrice
    Then security SearchSecurity should be instrument3.check.dirtyPrice

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument4.edit.cleanPrice
    Then security SearchSecurity should be instrument4.dirtyPrice.check

  @TalatRani @F6246 @CouponDayCalculation @T26839 @Jira-COL-2588
    Scenario: SE-01-89 [Price Source Section]Verify each Price Source Section should trigger Coupon Day Calculation in security editor
    When navigate to security search page
    And add security bondinstrument
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Check.Values

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.AccrualBasis
    Then security SearchSecurity should be instrument.CouponDaysCount.Check.Values

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.AccrualBasis.12
    Then security SearchSecurity should be instrument.CouponDaysCount.Check.Values.12

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.AccrualBasis.13
    Then security SearchSecurity should be instrument.CouponDaysCount.Check.Values.13

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.CouponDayCount.12
    Then security SearchSecurity should be instrument.CouponDayCount.Check.Values.12

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.CouponDayCount.34
    Then security SearchSecurity should be instrument.CouponDayCount.Check.Values.34

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.CouponDayCount.83
    Then security SearchSecurity should be instrument.CouponDayCount.Check.Values.83

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.CouponDayCount.84
    Then security SearchSecurity should be instrument.CouponDayCount.Check.Values.84

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.CouponDayCount.empty
    Then security SearchSecurity should be instrument.CouponDayCount.Check.Values.empty

    @TalatRani @F6246 @TriggerNextCouponDay @T26841 @Jira-COL-2593
    Scenario: SE-01-91 [Price Source Section]Verify each Price Source Section should trigger Next Coupon Day and Last Coupon Day check in security editor
    When navigate to security search page
    And add security bondinstrument
    Then security bondinstrument1 should be bondinstrument.id

    When navigate to security search page
    And add security bondinstrument2
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Check.CouponDaysCount

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit
    Then security instrumentcheck should be bondinstrument.id

    @TalatRani @F6246 @TriggerCleanPrice @T26852 @Jira-COL-2594
    Scenario: SE-01-93 [Price Source Section]Verify each Price Source Section should trigger [Clean Price] Calculation in Security Editor
    When navigate to security search page
    And add security bondinstrument
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Check.CleanPrice

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit
    Then security SearchSecurity should be instrument.Check.VerifyCleanPrice

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit.CAFactorPrice
    Then security SearchSecurity should be instrument.Check.VerifyCleanPrice.CAFactorPrice

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.edit.EmptyDirtyPrice
    Then security SearchSecurity should be instrument.Check.Verify.EmptyCleanPrice

    @TalatRani @F6246 @FourEyesApprovalProcess @T27086 @Jira-COL-2596
    Scenario:SE-01-94 Verify Security status apply to Four eyes approval process
    And switch user to first-User
    When navigate to collateral preferences page
    And set collateral preferences applyFourEyes.Securities
    And navigate to security search page
    And add security bondinstrument
    And search security SecuritySearch
    Then security SearchSecurity should be instrument.Status.Unable.to.Approve

    When switch user to Second-User
    And navigate to security search page
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Status.Approved

    When switch user to first-User
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to instrument.change
    Then security SearchSecurity should be instrument.Status.amended

    When switch user to Second-User
    And navigate to security search page
    And search security SecuritySearch
    And approve security SearchWillBeApprovedBond
    Then security SearchSecurity should be instrument.Status.Approved

    And switch user to first-User
    When navigate to collateral preferences page
    And set collateral preferences applyFourEyes.Securities
    And navigate to security search page
    And add security addEquityInstrument
    And search security equitySearch
    Then security SearchEquity should be equityInstrument.Status.Save

    When switch user to Second-User
    And navigate to security search page
    And search security equitySearch
    And approve security EquityWillBeApproved
    Then security SearchEquity should be EquityInstrument.Status.Approved

    And switch user to first-User
    When navigate to security search page
    And search security equitySearch
    And edit security SearchEquity to EquityInstrument.change
    Then security SearchEquity should be EquityInstrument.Status.amended

    When switch user to Second-User
    And navigate to security search page
    And search security equitySearch
    And approve security EquityWillBeApproved
    Then security SearchEquity should be EquityInstrument.Status.Approved

    And switch user to first-User
    When navigate to collateral preferences page
    And set collateral preferences applyFourEyes.Securities
    And navigate to security search page
    And add security addCommodityInstrument
    And search security CommoditySearch
    Then security SearchCommodity should be CommodityInstrument.Status.Save

    When switch user to Second-User
    And navigate to security search page
    And search security CommoditySearch
    And approve security CommodityWillBeApproved
    Then security SearchCommodity should be CommodityInstrument.Status.Approved

    And switch user to first-User
    When navigate to security search page
    And search security CommoditySearch
    And edit security SearchCommodity to CommodityInstrument.change
    Then security SearchCommodity should be CommodityInstrument.Status.amended

    When switch user to Second-User
    And navigate to security search page
    And search security CommoditySearch
    And approve security CommodityWillBeApproved
    Then security SearchCommodity should be CommodityInstrument.Status.Approved

    When switch user to first-User
    And navigate to collateral preferences page
    And set collateral preferences applyFourEyes.Securities
    And navigate to security search page
    And add security addPropertyInstrument
    And search security propertiesSearch
    Then security Searchproperties should be propertiesInstrument.Status.Save

    When switch user to Second-User
    And navigate to security search page
    And search security propertiesSearch
    And approve security propertiesWillBeApproved
    Then security Searchproperties should be propertiesInstrument.Status.Approved

    When switch user to first-User
    And navigate to security search page
    And search security propertiesSearch
    And edit security Searchproperties to propertiesInstrument.change
    Then security Searchproperties should be propertiesInstrument.Status.amended

    When switch user to Second-User
    And navigate to security search page
    And search security propertiesSearch
    And approve security propertiesWillBeApproved
    Then security Searchproperties should be propertiesInstrument.Status.Approved

    When switch user to first-User
    And navigate to security search page
    And search security propertiesSearch
    And edit security Searchproperties to propertiesInstrument.change
    Then security Searchproperties should be propertiesInstrument.Status.amended

    When switch user to Second-User
    And navigate to security search page
    And search security propertiesSearch
    And approve security propertiesWillBeApproved
    Then security Searchproperties should be propertiesInstrument.Status.Approved

  @TalatRani @F6246 @used&approved&Non-lockedInstrument @T:27706 @Jira-COL-2591
  Scenario: SE-01-95 Verify that instrument which are in used&approved&Non-locked can only be searched to do booking
    When navigate to security search page
    And add security bondinstrument-USD.1
    And search security SecuritySearch
    Then security SearchSecurity should be instrument.lockEdit.Amended
    When navigate to security search page
    And search security SecuritySearch.without.lock
    Then security search result should be SearchResults

    When Go to agreement agrSearch statement page by URL
    And edit asset summary info
    And view asset type qtp_bond agreement asset Bond Page
    And search Existing instrument - statement booking qtp_bond_Instrument_Search
    Then security Instrument search result should be Instrument.Search.Result

    When navigate to security search page
    And add security bondSecurity-USD.2
    And search security bondSecuritySearch.1
    And approve security bond.Security.Approve
    Then security bondSearchSecurity.2 should be bond.Status.IsApproved

    When navigate to security search page
    And add security bondSecurity-USD.3
    And search security SecuritySearch.bond3
    Then security SearchSecurity.bond3 should be Status.Amended.bond3

    When Go to agreement agrSearch statement page by URL
    And edit asset summary info
    And view asset type qtp_bond agreement asset Bond Page
    And search Existing instrument - statement booking qtp_bond_Instrument_Search.bond3
    Then security Instrument search result should be Instrument.Search.Result.Bond3

    When navigate to security search page
    And add security equityInstrument-USD.1
    And search security equitySecuritySearch.1
    Then security equitySearchSecurity should be equityInstrument.lockEdit.Amended
    When navigate to security search page
    And search security EquitySecuritySearch.without.lock
    Then security search result should be EquitynoInstrument

    When Go to agreement agrSearch statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events delivery.agreementRequirement.2m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking delivery.applyAgreementAmount
    Then messageHandler - alert message Web-error-message.Values

    And navigate to security search page
    And add security equitySecurity-USD.2
    And search security equitySecuritySearch.2
    And approve security Equity.Security.Approve
    Then security equitySearchSecurity.2 should be equity.Status.IsApproved

    When navigate to security search page
    And add security equitySecurity-USD.3
    And search security SecuritySearch.equity3
    Then security SearchSecurity.equity3 should be equity.Status.Amended

    When Go to agreement agrSearch statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events delivery.agreementRequirement.2m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking equity3delivery.applyAgreementAmount
    Then messageHandler - alert message Web-error-message.Values.equity3

    When feed security templates feed.security.template.qtp.bond.usd by xlsx
    Then feed log should be feed.security.template.qtp.bond.usd.result
    And feed status should be feed.security.template.qtp.bond.usd.status
    And navigate to security search page
    And search security feed.security.template.qtp.bond.usd.search
    Then security feed.security.template.qtp.bond.usd.search.result should be check.feed.security.template.qtp.bond.usd

    When navigate to agreement search page
    And Go to agreement agrSearch statement page by URL
    And edit asset summary info
    And view asset type qtp_bond agreement asset Bond Page
    And search Existing instrument - statement booking bond_Template_Search
    Then security Instrument search result should be Template.Instrument.Search.Result
  @TalatRani @F6246 @CounterpartyaAndBookingsInDeliveryPosition @T:31099 @Jira-COL-2590
  Scenario:SE-04-10 Verify that Instrument will be Not Eligible when Asset type changed from A to B and B is not selected under Counterparty and Bookings in Delivery Position
    When navigate to security search page
    And add security securityinstrument
    And search security SecuritySearch
    And approve security SearchWillBeApproved
    Then security SearchSecurity should be instrument.Status.Approved

    When Go to agreement agrSearch statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to edit.Security
    Then security securityinstrument.id should be validationError

    When navigate to security search page
    And add security securitybondinstrument
    And search security SecuritySearchBond
    And approve security SearchWillBeApprovedBond
    Then security BondSearchSecurity should be instrument.Status.Approved

    When Go to agreement agrSearch statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond_gbp agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search_gbp
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security SecuritySearchBond
    And edit security BondSearchSecurity to Instrument.Edit.Amended.usd
    Then security BondSearchSecurity should be security.instrument.Status.Amended

    When navigate to security search page
    And search security SecuritySearchBond
    And edit security BondSearchSecurity to Instrument.Edit.Amended.gbp
    And search security SecuritySearchBond
    And approve security SearchWillBeApprovedBond
    Then security BondSearchSecurity should be instrument.Status.Approved

    When navigate to security search page
    And add security Equitysecurityinstrument
    And search security EquitySecuritySearch
    And approve security EquitySearchWillBeApproved
    Then security EquitySearchSecurity should be Equity.instrument.Status.Approved

    When Go to agreement agrSearch statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events Equity.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_equity agreement asset Equity Page
    And add call bookings - statement booking qtp_equity_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security EquitySecuritySearch
    And edit security EquitySearchSecurity to Equity.Instrument.Edit.Amended
    Then security equityinstrument.id should be equity.Instrument.validationError

  @TalatRani @F10168 @DependencyCheck @T:31103 @Jira-COL-2592
 Scenario: Verify that there should be dependency check when the bookings in cancelled or failed or cancel and rebook status
    When navigate to security search page
    And add security new-USD-Bond-instrument
    And search security SecuritySearch
    And approve security SearchWillBeApproved
    Then security SearchSecurity should be instrument.Status.Approved

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Pending.Authorised
    And edit call booking booking.status.Pending.to.Authorised
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Authorised.to.Settlement
    And edit call booking booking.status.Pending.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page Status_should_Be_Pending_Settlement
    And edit call booking booking.status.Outstanding.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Outstanding.to.Failed
    And edit call booking booking.status.Failed
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When navigate to security search page
    And add security 2-USD-Bond-instrument
    And search security SecuritySearch-2
    And approve security SearchWillBeApproved-2
    Then security SearchSecurity-2 should be instrument.Status.Approved-2

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking 2-qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security SecuritySearch-2
    And edit security SearchSecurity-2 to bond.edit.Security-2
    Then security 2bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Cancelled
    And edit call booking booking.status.Cancelled
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch-2
    And edit security SearchSecurity-2 to bond.edit.Security-2
    Then security 2bondinstrument.id should be dependencies.check.validationError

    When navigate to security search page
    And add security 3-USD-Bond-instrument
    And search security SecuritySearch-3
    And approve security SearchWillBeApproved-3
    Then security SearchSecurity-3 should be instrument.Status.Approved-3

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking 3-qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Pending.Authorised-3
    And edit call booking booking.status.Pending.to.Authorised
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch-3
    And edit security SearchSecurity-3 to bond.edit.Security
    Then security 3bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page Authorised-to-Pending-settlement
    And edit call booking booking.status.Pending.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch-3
    And edit security SearchSecurity-3 to bond.edit.Security
    Then security 3bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page Settled
    And edit call booking booking.status.Settled
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch-3
    And edit security SearchSecurity-3 to bond.edit.Security
    Then security 3bondinstrument.id should be dependencies.check.validationError

  @TalatRani @F10168 @DependencyCheckSettledStatus @T:31106 @Jira-COL-2597
  Scenario: Verify that there should be dependency check when the bookings in settled status and Netted Collateral Value is zero
    When navigate to security search page
    And add security new-USD-Bond-instrument
    And search security SecuritySearch
    And approve security SearchWillBeApproved
    Then security SearchSecurity should be instrument.Status.Approved

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Pending.To.Authorised
    And edit call booking booking.status.Pending.is.Authorised
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Authorised.To.Pending.Settlement
    And edit call booking booking.status.is.changed.to.Pending.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page select.Pending.Settlement.change.to.Settled
    And edit call booking booking.status.is.changed.to.Settled
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-1-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreementRequirement.1m.VM.Return
    And Exposure Management - make booking
    And view asset type qtp_bond_IMReturn agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search.IM.Return
    Then Exposure Management - collapse events call.completed.for.IM.Return

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError

    When Go to agreement agreement-2-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreement2.3m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And add call bookings - statement booking qtp_bond_Instrument_Search
    Then Exposure Management - collapse events call.completed.3m

    When navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError.agreement-2

    When Go to agreement agreement-2-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreement2.3m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Pending.To.Authorised.call.argeement2
    And edit call booking booking.status.Pending.is.Authorised
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError.agreement-2

    When Go to agreement agreement-2-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreement2.3m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Authorised.To.retun.Pending.Settlement.agreement2
    And edit call booking booking.status.is.changed.to.Pending.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError.agreement-2

    When Go to agreement agreement-2-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreement2.3m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Pending.Settlement.Return.call.Outstanding.Settlement.agreement2
    And edit call booking booking.status.is.changed.to.Outstanding.Settlement
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError.agreement-2

    When Go to agreement agreement-2-Search statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events bond.delivery.agreement2.3m
    And Exposure Management - make booking
    And view asset type qtp_bond agreement asset Bond Page
    And open asset booking editor page booking.Outstanding.Settlement.to.Settled.agreement2
    And edit call booking booking.status.is.changed.to.Settled
    And view asset type qtp_bond agreement asset Bond Page
    And navigate to security search page
    And search security SecuritySearch
    And edit security SearchSecurity to bond.edit.Security
    Then security bondinstrument.id should be dependencies.check.validationError.agreement-2

  @TalatRani @F10168 @SecuritySearch @T:31162 @Jira-COL-2580
    Scenario: Security Search SS-01-25 Verify that filter Asset Classification functionality works
    When navigate to collateral static data page
    And add collateral static data Asset-Classification1
    Then collateral static data should be Asset-Classification1
    When navigate to collateral static data page
    And add collateral static data Asset-Classification2
    Then collateral static data should be Asset-Classification2
    When navigate to collateral static data page
    And add collateral static data Asset-Classification3
    Then collateral static data should be Asset-Classification3

    When navigate to asset definition page
    Then add asset category C1
    When navigate to asset definition page
    Then add asset category C2
    When navigate to asset definition page
    Then add asset category C3
    When navigate to asset definition page
    Then add asset category C4
    When navigate to asset definition page
    Then add asset category C5
    When navigate to asset definition page
    Then add asset category C6

    When navigate to security search page
    And add security instrument-insxxx001
    And search security SecuritySearch
    And approve security SearchWillBeApproved
    Then security SearchSecurity should be instrument.Status.Approved

    When navigate to security search page
    And add security instrument-insxxx002
    And search security SecuritySearch-002
    And approve security SearchWillBeApproved-002
    Then security SearchSecurity-002 should be instrument.Status.Approved-002

    When navigate to security search page
    And add security instrument-insxxx003
    And search security SecuritySearch-003
    And approve security SearchWillBeApproved-003
    Then security SearchSecurity-003 should be instrument.Status.Approved-003

    When navigate to security search page
    And add security instrument-insxxx004
    And search security SecuritySearch-004
    And approve security SearchWillBeApproved-004
    Then security SearchSecurity-004 should be instrument.Status.Approved-004

    When navigate to security search page
    And search security SecuritySearch.All.Instrument.starting.with.insxxx00
    Then security search result should be Instrument-001.Instrument-002.Instrument-003.Instrument-004

    When navigate to security search page
    And search security SecuritySearch.for.assetClassification
    Then security search result should be SecuritySearch.for.assetClassification.results

    When navigate to security search page
    And search security SecuritySearch.with.assetCategory
    Then security search result should be SecuritySearch.with.assetCategory.results

    When navigate to security search page
    And search security SecuritySearch.with.assetType
    Then security search result should be SecuritySearch.with.assetType.results

    When navigate to security search page
    And search security SecuritySearch.with.assetType.Bond
    Then security search result should be SecuritySearch.with.assetCategory.results

    When navigate to security search page
    And search security SecuritySearch.with.assetType.USD
    Then security search result should be SecuritySearch.USD.results
