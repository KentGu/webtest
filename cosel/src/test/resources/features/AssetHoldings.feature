@AssetHoldings @regression
Feature: AssetHoldings

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference.default
#    And have default stp configuration

  @v13.0.5 @YanLu @T13167 @AutoBookCash
  Scenario: Verify auto booking can be made by auto booking button for Normal Net agreement Call & Recall event - Use Agreement Amount
    When add OTC agreement agr
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades tradeExposureAmount1m
    And quick link - view statement
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking deliveryBookingWith2mAmount
    And quick link - view statement
    And approve agreement agr
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events call.event.with.agreement.requirement.1000000
    And Exposure Management - tick events recall.event.with.agreement.requirement.2000000
 #    And Exposure Management - auto send all letter
    And Exposure Management - auto send letter
    Then Exposure Management callEvent - event call status should be marginRequestIssuedCallEvent
    And Exposure Management recallEvent - event call status should be marginRequestIssuedRecallEvent

    When Exposure Management - set event eventCall value to eventCallCpty1.9m
    And Exposure Management - set event eventReCall value to eventReCallCpty0.4m
    And Exposure Management - auto book cash
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    Then asset holding detail should be assetHoldingDetailForCallAmount0.3m
    And asset holding detail should be assetHoldingDetailForReCallAmount2m

    When quick link - agreement exposure management
    Then Exposure Management callEventCheck - event call status should be partialDisputeCallEvent
    And Exposure Management recallEventCheck - event call status should be partialDisputeRecallEvent

  @v13.0.5 @AutoBookCash @F469 @T13167 @YanLu
  Scenario: Verify auto booking can be made by task for Normal Net agreement Call & Recall event - Use Agreement Amount
     #Do the same testing for Auto Book by Auto Book Cash task
    When add OTC agreement agr
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades tradeExposureAmount1m
    And quick link - view statement
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking deliveryBookingWith2mAmount
    And quick link - view statement
    And approve agreement agr
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events call.event.with.agreement.requirement.1000000
    And Exposure Management - tick events recall.event.with.agreement.requirement.2000000
    And Exposure Management - auto send letter
    Then Exposure Management callEvent - event call status should be marginRequestIssuedCallEvent
    And Exposure Management recallEvent - event call status should be marginRequestIssuedRecallEvent

    When Exposure Management - set event eventCall value to eventCallCpty1.9m
    And Exposure Management - set event eventReCall value to eventReCallCpty0.4m
    And Exposure Management - save changes
    And navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoCashBookTask
    And run task scheduler runAutoCashBookTask
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    Then asset holding detail should be assetHoldingDetailForCallAmount0.3m
    And asset holding detail should be assetHoldingDetailForReCallAmount2m

    When quick link - agreement exposure management
    Then Exposure Management callEventCheck - event call status should be partialDisputeCallEvent
    And Exposure Management recallEventCheck - event call status should be partialDisputeRecallEvent

  @v13.0.0 @AutoBookCash @F6260 @T27316 @YanLu
  Scenario: Verify Auto booking can be made seperately by auto booking button for Normal Not-Net agreement Margin (IM + VM) Call event - Use Agreement Amount (Cpty)
    When add OTC agreements agr
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade
    And quick link - view statement
    And approve agreement agr
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events im.call.event.with.agreement.requirement.1000000
    And Exposure Management - tick events vm.call.event.with.agreement.requirement.2000000
    And Exposure Management - auto send letter
#    And Exposure Management - auto send all letter
    Then Exposure Management - EM message should be emMessageForAutoSend

#    When navigate to agreement search page
#    And search agreement agrSearch
#    And view completed agreement statement agrSearchResult
#    And recalculate agreement statement
#    And quick link - agreement exposure management

    When Exposure Management - set event eventIMCall value to eventIMCallCpty1m
    And Exposure Management - save changes
    And Exposure Management - set event eventVMCall value to eventVMCallCpty2.5m
    And Exposure Management - save changes
    And Exposure Management - tick events eventIMCall
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be emMessageForAutoCashSendIMCall

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_cash_usd agreement asset Bond Page
    Then asset holding detail should be assetHoldingDetailForIMCall

    When quick link - agreement exposure management
    And Exposure Management - tick events eventVMCall
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be emMessageForAutoCashSendVMCall

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_cash_usd agreement asset Bond Page
    Then asset holding detail should be assetHoldingDetailForIMCall

  @v13.0.0 @AutoBookCash @F6260 @T27322 @YanLu
  Scenario: Verify VM and IM auto booking can be made by sending joint Margin Delivery & Return letter for Normal Not-Net agreement - Create Letter
    When add OTC agreements agr
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade
    And quick link - view statement
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking booking1
    And quick link - view statement
    And approve agreement agr
    And quick link - agreement exposure management
    Then Exposure Management vmDeliveryEvent - event call status should be vmDeliveryEventCheck
    And Exposure Management imReturnEvent - event call status should be imReturnEventCheck

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And Agreement Admin - manual send margin delivery&return letter marginDeliveyAndReturnLetter
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    Then asset holding detail should be assetHodingDetailForVMDelivery
    And asset holding detail should be assetHodingDetailForIMReturn

  @v14.0.0 @BulkBooking @F9376 @T30989 @YanLu @ignore
  Scenario: Verify Pending Cancelled booking is included as linked Exceeding booking-Call
    Given have collateral preferences preferenceSetPendingCancelledTrue
    When add OTC agreements agr
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade
    And quick link - view statement
    And approve agreement agr
    And quick link - agreement exposure management
    And Exposure Management - set event eventChangeCpty value to eventChangeCpty7m
    And Exposure Management - save changes
    And Exposure Management - tick events eventChangeCpty
#    And add delivery booking - bulk booking bulkbooking1Delivery3m,bulkbooking2Delivery4m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulkbooking1Delivery3m,bulkbooking2Delivery4m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be no.warning.message.shown
    When quick link - save
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
#change booking status to pending cancelled
#    And edit delivery booking <delivery> to <deliveryChange>
    And edit delivery booking booking1Pending to booking1ChangeToAuthorised
    And edit delivery booking booking1ChangedAuthorised to booking1ChangeToPendingSettlement
    And edit delivery booking booking1ChangedPendingSettlement to booking1ChangeToPendingCancelled
    And edit delivery booking booking2Pending to booking2ChangeToAuthorised
    And edit delivery booking booking2ChangedAuthorised to booking2ChangeToPendingSettlement
    And edit delivery booking booking2ChangedPendingSettlement to booking2ChangeToPendingCancelled
    And quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - tick events eventChangeCpty
    And add delivery bookings - group booking bookingThatWillPopUpWarningMessage
    And navigate to settlement status page
    And settlement status - search settlement status settlementStatusSearch
    And settlement status - show asset settlementStatusSearchResult details
    Then settlement status settlementStatusDetail should be settlementStatusDetailCheck

  @v14.1.0 @ApiBooking @F11281 @T32486 @YanLu
  Scenario: Verify Close Link Correlation apply to  the Counterparty compare the issuer by API
    When add organisations org1Branch
    And add organisations org4Branch
    And add organisations org2
    And add organisations org1
    And add organisations org4
#after add organisations put org1 into mainorg subsidiary
    And navigate to view organisation
    And search organisations mainOrgSearch
    And edit organisation to addOrg1IntoMainOrgSubsidiary
#search org1 and approve
    And search organisations org1Search
    And edit organisation to approveOrg1Again
    And add OTC agreements agr1
    And feed flush trades feed1 by excel
    Then feed log should be feed1Log

  @14.1.0 @GroupBookingInstrumentBooking @T32471 @YanLu
  Scenario: Verify dynamic tolerance is calculated basing on the sequence of selected asset-Delivery
    Given have asset class bondAssetClass,equityAssetClass,commodityAssetClass,cashAssetClass
    And have asset type bondAssetType,equityAssetType,commodityAssetType,cashAssetType
    When navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved
    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved
    And add securities commodityinstrument
    And search security commoditySecuritySearchAmended
    And approve security commoditySecuritySearchWillBeApproved
    And add OTC agreements clearingAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades -2mtrade
    And quick link - view statement
    And approve agreement clearingAgreement
    And quick link - agreement exposure management
    And Exposure Management - tick events tickDeliveryEvent
#    And add delivery booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorsOrWarningsFound
    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page

#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity0.52m
    And Edit booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity0.52m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorsOrWarningsFound

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity0.520001m
    And Edit booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity0.520001m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2020000.00Actual-2020000.01

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity
    And Edit booking - bulk booking bulkbookingForBond,bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity0.520001m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2020000.00Actual-2020000.01

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity1.4m
    And Edit booking - bulk booking bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity1.4m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2040000.00Actual2400000.00

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity1.41m
    And Edit booking - bulk booking bulkbookingForCash,bulkbookingForEquity,bulkbookingForCommodity1.41m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2040000.00Actual-2410000

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.5m
    And Edit booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.5m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2060000.00Actual-2500000

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.6m
    And Edit booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.6m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2060000.00Actual-2600000

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.7m
    And Edit booking - bulk booking bulkbookingForCash1m,bulkbookingForCommodity1.7m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2060000.00Actual-2700000

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash1m
    And Edit booking - bulk booking bulkbookingForCash1m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-2000000.00Actual-1000000

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash2m
    And Edit booking - bulk booking bulkbookingForCash2m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorsOrWarningsFound

    When cancel bulk booking
    And Exposure Management - tick events tickDeliveryEvent
    And Exposure Management - go to bulk booking page
#    When quick link - change bookings
#    And add delivery booking - bulk booking bulkbookingForCash2.1m
    And Edit booking - bulk booking bulkbookingForCash2.1m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warningExpected-200000.00Actual-2100000

  @14.1.0 @BulkBooking @T32568 @YanLu
  Scenario: Verify that Exceeding booking check when both tolerance amount and tolerance percentage are set if user has collateral.assets.excessbooking
    Given have asset class bondAssetClass,equityAssetClass
    And have asset type bondAssetType,equityAssetType
    When navigate to asset definition page
    And navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved
    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved
    And add OTC agreements clearingAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades -2mtrade
    And quick link - view statement
    And approve agreement clearingAgreement
    And quick link - agreement exposure management
    And Exposure Management - tick events tickDeliveryEvent
    And add delivery booking - bulk booking bulkbookingForBond,bulkbookingForEquity
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected2000000actual2000000.01
#    When quick link - cancel bulk bookings
#    And Exposure Management - tick events tickDeliveryEvent
    And add delivery booking - bulk booking bulkbookingForBond1000309,bulkbookingForEquity
    And quick link - save
    Then Exposure Managerment - EM booking message should be expected2000000actual2000309
    When Exposure Management - override warning overrideWarning
    And quick link - save
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type bondAssetType agreement asset Bond Page
    And edit delivery booking booking1Pending to booking1ChangeToQuery
    Then asset holding detail should be bondInstrument1000509Delivery

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type equityAssetType agreement asset Bond Page
    And edit delivery booking booking2Pending to booking2ChangeToQuery
    Then asset holding detail should be equityInstrument1100000Delivery

  @v14.1.0 @BulkBooking @YanLu @F11375 @T32433 @ignore
  Scenario: Verify that Exceeding booking check if tolerance percentage is set for multipal assets-Return
    Given add asset class bondAssetClass,equityAssetClass
    And add asset type bondAssetType,equityAssetType
    When navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved

    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved

    When add OTC agreements clearingAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades amount50000trade
    And quick link - view statement
    And edit asset summary info
    And view asset type bondAssetType agreement asset Bond Page
    And add call booking - statement booking 3.5mBondCall
    And quick link - view statement
    And edit asset summary info
    And view asset type equityAssetType agreement asset Bond Page
    And add call booking - statement booking 3.5mEquityCall
    And quick link - view statement
    And approve agreement clearingAgreement
    And quick link - agreement exposure management
    And Exposure Management - tick events tickDeliveryEvent
    And add delivery booking - bulk booking bulkbookingForBond3.4m,bulkbookingForEquity3.4m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected6630000actual6800000

    When quick link - change bookings
    And add delivery booking - bulk booking bulkbookingForBond3.4m,bulkbookingForEquity3230000.01
    And quick link - save
    Then Exposure Managerment - EM booking message should be expected6630000actual6630000.01

    When Exposure Management - override warning overrideWarning
    And quick link - save
# step7 cancel booking1 and booking2
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking1Pending to booking1ChangeToCancelled
#    And edit delivery booking booking1Pending to booking1ChangeToAuthorised
#    And edit delivery booking booking1ChangedAuthorised to booking1ChangeToPendingSettlement
#    And edit delivery booking booking1ChangedPendingSettlement to booking1ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking2Pending to booking2ChangeToCancelled
#    And edit delivery booking booking2Pending to booking2ChangeToAuthorised
#    And edit delivery booking booking2ChangedAuthorised to booking2ChangeToPendingSettlement
#    And edit delivery booking booking2ChangedPendingSettlement to booking2ChangeToCancelled
#step7 send margin return letter
    And quick link - view statement
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And approve agreement clearingAgreementForApprove
    And Agreement Admin - manual send margin delivery&return letter marginReturnLetter
#assert booking1 3.4m return
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type bondAssetType agreement asset Bond Page
    Then asset holding detail should be assetHoldingDetailForBooking1Is3.4mReturn

#assert booing2  3.4m return
    When quick link - view statement
    And edit asset summary info
    And view asset type equityAssetType agreement asset Bond Page
    Then asset holding detail should be assetHoldingDetailForBooking2Is3.4mReturn

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32470
  Scenario: Verify that Exceeding booking check if tolerance amount is set for multipal assets-delivery
    Given have asset class bondAssetClass,equityAssetClass
    And have asset type bondAssetType,equityAssetType
    When navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved
    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved
    And add OTC agreements clearingAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades amount-2000000trade
    And quick link - view statement
    And approve agreement clearingAgreement
    And quick link - agreement exposure management
    And Exposure Management - tick events tickDelivery2000000Event
    Then Exposure Management deliveryEventCheck - event call status should be delivery2000000Event

#feed booking amount<requirement+tolerance,
#feed booking delivery 1000000
    When feed asset bookings feedBooking1000000Delivery by excel
#check feed result and status
    Then feed log should be booking1000000DeliveryFeedResult
    And feed status should be booking1000000DeliveryFeedStatus

#feed booking delivery 1000200
    When feed asset bookings feedBooking1000200Delivery by excel
     #check feed result and status
    Then feed log should be booking1000200DeliveryFeedResult
    And feed status should be booking1000200DeliveryFeedStatus

#cancel booking1 and booking2
    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking1Pending to booking1ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking2Pending to booking2ChangeToCancelled
#continue to feed two bookings amount=requirement+tolerance,
#feed booking amount 1000000
    And feed asset bookings feedBooking3amount1000000Delivery by excel
    Then feed log should be booking3amount1000000DeliveryFeedResult
    And feed status should be booking3amount1000000DeliveryFeedStatus

#feed booking amount 1000300
    When feed asset bookings feedBooking4amount1000300Delivery by excel
#check feed result and status
    Then feed log should be booking4amount1000300DeliveryFeedResult
    And feed status should be booking4amount1000300DeliveryFeedStatus

#cancel booking3 and booking4
    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking3Pending to booking3ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking4Pending to booking4ChangeToCancelled
#continue to feed two bookings amount > requirement+tolerance
#feed booking amount 1000000
    And feed asset bookings feedBooking5amount1000000Delivery by excel
    Then feed log should be booking5amount1000000DeliveryFeedResult
    And feed status should be booking5amount1000000DeliveryFeedStatus

    When feed asset bookings feedBooking6amount1000400Delivery by excel
    Then feed log should be booking6amount1000400DeliveryFeedResult
    And feed status should be booking6amount1000400DeliveryFeedStatus

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    Then asset holding detail should be feedBooking5amount1000000DeliveryDetail

#    And edit delivery booking booking5Pending to booking5ChangeToCancelled
    When quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    Then asset holding detail should be feedBooking6amount1000400Delivery

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32470
  Scenario: Verify that Exceeding booking check if tolerance amount is set for multipal assets return
    Given have asset class bondAssetClass,equityAssetClass
    And have asset type bondAssetType,equityAssetType
    When navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved
    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved

    When add OTC agreements otcAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades amount500trade
    And quick link - view statement
    And approve agreement otcAgreement
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And add call booking - statement booking callBooking1With2000500Amount
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And add call booking - statement booking callBooking2With2000500Amount
    And quick link - view statement
    And approve agreement otcAgreementToApprove
    And quick link - agreement exposure management
    And Exposure Management - tick events tickReturn4500000Event
    Then Exposure Management return4500000EventCheck - event call status should be return4500000Event

#feed booking amount<requirement+tolerance,
#feed booking1 return 1900000
    When feed asset bookings feedBooking1Amount1900000Return by excel
    Then feed log should be feedBooking1Amount1900000ReturnFeedResult
    And feed status should be feedBooking1Amount1900000ReturnFeedStatus

#feed booking2 return 1900000
    When feed asset bookings feedBooking2Amount1900000Return by excel
#check feed result and status
    Then feed log should be feedBooking2Amount1900000ReturnFeedResult
    And feed status should be feedBooking2Amount1900000ReturnFeedStatus

#cancel booking1 and booking2
    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking1Pending to booking1ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking2Pending to booking2ChangeToCancelled
#Step feed booking amount = requirement+tolerance,
    And feed asset bookings feedBooking3Amount2000300Return by excel
    Then feed log should be feedBooking3Amount2000300ReturnFeedResult
    And feed status should be feedBooking3Amount2000300ReturnFeedStatus

    When feed asset bookings feedBooking4Amount2000400Return by excel
    Then feed log should be feedBooking4Amount2000400ReturnFeedResult
    And feed status should be feedBooking4Amount2000400ReturnFeedStatus

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking3Pending to booking3ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking4Pending to booking4ChangeToCancelled
    And feed asset bookings feedBooking5Amount2000400Return by excel
    Then feed log should be feedBooking5Amount2000400ReturnFeedResult
    And feed status should be feedBooking5Amount2000400ReturnStatus

    When feed asset bookings feedBooking6Amount2000500Return by excel
    Then feed log should be feedBooking6Amount2000500ReturnFeedResult
    And feed status should be booking6amount1000300DeliveryFeedStatus

    When search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    Then asset holding detail should be feedBooking5Amount2000400ReturnDetail

    When quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    Then asset holding detail should be feedBooking6Amount2000500ReturnDetail

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32467
  Scenario:Verify that Exceeding booking check if tolerance percentage and tolerance amount are set for multipal assets-delivery
    When navigate to asset definition page
    And add asset class bondAssetClass,equityAssetClass
    And add asset class bondAssetType,equityAssetType
    And navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved
    And add securities equityinstrument
    And search security equitySecuritySearchAmended
    And approve security equitySecuritySearchWillBeApproved
    And add OTC agreements clearingAgreement
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades amount-2000000trade
    And quick link - view statement
    And approve agreement clearingAgreement
    And quick link - agreement exposure management
    Then Exposure Management deliveryEventCheck - event call status should be delivery2000000Event

    When Exposure Management - tick events tickDelivery2000000Event
    And add delivery booking - bulk booking bulkbookingForBond,bulkbookingForEquity
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected-2000300.00actual-2000300.01

    When quick link - change bookings
    And add delivery booking - bulk booking bulkbookingForBond1000309,bulkbookingForEquity
    And quick link - save
    Then Exposure Managerment - EM booking message should be expected-2000300.00actual-2000309.00

    When Exposure Management - override warning overrideWarning
    And quick link - save
#step7 cancel booking1 and booking2
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking1Pending to booking1ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking2Pending to booking2ChangeToCancelled
    And quick link - view statement
    And approve agreement clearingAgreementToApprove
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tickDelivery2000000Event
    And add delivery booking - bulk booking bulkbookingForBond1000300,bulkbookingForEquity
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorsOrWarningsFound

    When quick link - save
#asset two booking add successful
    And search agreement agrSearch
    And view completed agreement statement agrSearchResult
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    Then asset holding detail should be bulkbookingForBond1000300Detail

    When quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    Then asset holding detail should be bulkbookingForEquityDetail

#cancel the two booking
    When quick link - view statement
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit delivery booking booking3Pending to booking3ChangeToCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type qtp_equity_usd agreement asset Bond Page
    And edit delivery booking booking4Pending to booking4ChangeToCancelled

    When feed asset bookings feedBookingAmount2030000Delivery by excel
    Then feed log should be feedBookingAmount2030000DeliveryFeedResult
    And feed status should be feedBookingAmount2030000DeliveryFeedStatus

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32468
  Scenario: Verify that Exceeding booking check if tolerance percentage is set for multipal assets-delivery
    Given have asset class bond.class.with.tolerance.percentage.0.00015.amount.blank.usd,equity.class.with.tolerance.percentage.2%.amount.blank.usd
    And have asset types bond.type.under.new.created.bond.class,equity.type.under.new.created.equity.class
    And have securities bond.instrument.with.price.100,equity.instrument.with.price.1
    And have OTC agreement otc.agreement.with.base.currency.usd.tick.all.bonds
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type qtp.product on exposure summary page
    And add OTC trades otc.trade.with.amount.2m
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced
    Then Exposure Management event.previous.agreement.produced - event call status should be event.with.action.delivery.amount.2m

    When add delivery booking - bulk booking bulk.booking.on.bond.class.with.amount.1000300.01,bulk.booking.on.equity.class.with.amount.1000000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000300.actual.2000300.01

    When quick link - change bookings
    And add delivery booking - bulk booking bulk.booking.on.bond.class.with.amount.1000309,bulk.booking.on.equity.class.with.amount.1000000
    And quick link - save
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000300.actual.2000309

    When Exposure Management - override warning override.warning.message.shown.by.previous.bulk.booking
    And quick link - save
#step7 asset booking1 and booking2 is exsit
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be delivery.booking.on.bond.class.with.amount.1000309

    When click back button to previous page
    And view asset type equity.type agreement asset Bond Page
    Then asset holding detail should be delivery.booking.on.equity.class.with.amount.1000000

#cancel booking1
    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And edit delivery booking delivery.booking.on.bond.class.with.amount.1000309.pending to delivery.booking.on.bond.class.with.amount.1000309.cancelled
#cancel booking2
    And quick link - view statement
    And edit asset summary info
    And view asset type equity.type agreement asset Bond Page
    And edit delivery booking delivery.booking.on.equity.class.with.amount.1000000.pending to delivery.booking.on.equity.class.with.amount.1000000.cancelled
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And Agreement Admin - manual send margin delivery&return letter margin.delivery.letter.with.bondinstrument.amount.required1000301.and.equityinstrument.amount.required1000000
    And quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be delivery.booking.on.bond.class.with.amount.1000301

    When quick link - view statement
    And edit asset summary info
    And view asset type equity.type agreement asset Bond Page
    Then asset holding detail should be delivery.booking.on.equity.class.with.amount.1000000

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32442
  Scenario: Verify that Exceeding booking check when both tolerance amount and tolerance percentage are set-return
    Given have asset class bond.class.with.tolerance.percentage.40.amount.100.gbp
    And have asset types bond.type.under.new.created.bond.class
    And have securities bond.instrument.with.price.100
    And have FX rate sets fx.rate.with.base.usd.risk.gbp.bid.2.offer.2
#    And have collateral preferences collateral.preference.with.new.created.fx.rate
    And have OTC agreement otc.agreement.with.base.currency.usd.tick.all.bonds
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type qtp.product on exposure summary page
    And add OTC trades otc.trade.with.amount.500
    And quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.bond.instrument.amount.2000500.call
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.return.amount.2000000
    Then Exposure Management event.previous.agreement.produced - event call status should be event.with.action.return.amount.2000000

#    When add delivery booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000500
    When Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000500
    And quick link - validate
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000200.actual.-20000500

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000400
    And Edit booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000400
    And quick link - save
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000200.actual.-2000400

    When Exposure Management - override warning override.warning.message.shown.expected.-2000200.actual.-2000400
    And quick link - save
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000000

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000400

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And edit return booking return.booking.on.bond.class.with.amount.1000000 to return.booking.on.bond.class.with.amount.1000100.will.pop.up.warning.message
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000100

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And edit return booking return.booking.on.bond.class.with.amount.1000400 to return.booking.on.bond.class.with.amount.1000300.willnot.pop.up.warning.message
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000300

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And edit return booking return.booking.on.bond.class.with.amount.1000100 to return.booking.on.bond.class.with.amount.1000100.cancelled
    And edit return booking return.booking.on.bond.class.with.amount.1000300 to return.booking.on.bond.class.with.amount.1000300.cancelled
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.return.amount.2000000
#    And add delivery booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000100
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.on.bond.class.with.amount.1000000,bulk.booking.on.bond.class.with.amount.1000100
    And quick link - save
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000000.status.not.cancelled

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    Then asset holding detail should be return.booking.on.bond.class.with.amount.1000100.status.not.cancelled

  @v14.1.0 @GroupBooking @YanLu @F11375 @T32469
  Scenario: 32469 verify the tolerance will be applied to joint event independently for non-net agreement-joint event
    Given have asset class bond.class.with.tolerance.percentage.5.amount.6m.usd
    And have asset types bond.type.under.new.created.bond.class
    And have securities bond.instrument.with.price.100
    And have OTC agreement otc.not.net.agreement.with.base.currency.usd.tick.all.bonds
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type qtp.product on exposure summary page
    And add OTC trades otc.trade.with.principal.amount.1m.deal.level.amount.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.bond.instrument.vmamount.-3m.imamount.-4m
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.vmreturn.amount.2000000
    And Exposure Management - tick events tick.event.previous.agreement.produced.imreturn.amount.3000000
#    And add delivery booking - bulk booking bulk.booking.on.vm.amount.2m,bulk.booking.on.im.amount.1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.on.vm.amount.2m,bulk.booking.on.im.amount.1m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be Expected:-3000000Actual:-1000000

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.vm.amount.2100000,bulk.booking.on.im.amount.3150000
    And Edit booking - bulk booking bulk.booking.on.vm.amount.2100000,bulk.booking.on.im.amount.3150000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be no.warning.message.shown

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.vm.amount.2100000,bulk.booking.on.im.amount.3200000
    And Edit booking - bulk booking bulk.booking.on.vm.amount.2100000,bulk.booking.on.im.amount.3200000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected.amount.3150000.actual.amount.3200000

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.vm.amount.2250000,bulk.booking.on.im.amount.3150000
    And Edit booking - bulk booking bulk.booking.on.vm.amount.2250000,bulk.booking.on.im.amount.3150000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected.amount.2100000.actual.amount.2250000

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.vm.amount.2600000,bulk.booking.on.im.amount.3300000
    And Edit booking - bulk booking bulk.booking.on.vm.amount.2600000,bulk.booking.on.im.amount.3300000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be expected.amount.2100000.actual.amount.2600000
    And Exposure Managerment - EM booking message should be expected.amount.3150000.actual.amount.3300000

  @v14.1.0 @BulkBooking @YanLu @F492 @F6823 @F11455 @T8992
  Scenario:8992
    Given have organisations org.with.role.issuer.rating.sen.sandpAAA.moodysAaa.fitchAAA
    And have securities instrument.with.asset.type.qtp.equity.usd.price.100.issuer.rating.new.created.org.above
    And have OTC agreement otc.not.net.agreement.with.base.currency.usd.tick.qtp.equity.usd
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type qtp.product on exposure summary page
    And add OTC trades otc.trade.with.amount.-2m
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.delivery.amount.2000000
    Then Exposure Management event.previous.agreement.produced - event call status should be event.with.action.delivery.amount.2000000

    When add delivery booking - bulk booking bulk.booking.on.equity.class.with.amount.200000,bulk.booking.on.equity.class.with.amount.300000,bulk.booking.on.equity.class.with.amount.500000
    Then quick link - validate
    And Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000000.actual.-1000000

    When quick link - change bookings
    And quick link - save
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-2000000.actual.-1000000

    When Exposure Management - override warning override.warning.message.shown.by.previous.bulk.booking.expected.-2000000.actual.-1000000
    And quick link - save
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.delivery.amount.2000000
    And add delivery booking - bulk booking bulk.booking.on.equity.class.with.amount.500000,bulk.booking.on.equity.class.with.amount.300000,bulk.booking.on.equity.class.with.amount.500000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-1000000.actual.-1300000

    When quick link - change bookings
    And quick link - save
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.-1000000.actual.-1300000
    And Exposure Management - override warning override.warning.message.shown.by.previous.bulk.booking.expected.-1000000.actual.-130000

  @v14.1.0 @BulkBooking @YanLu @F1215 @F6535 @F11455 @T17204
  Scenario:17204 Verify Sufficiency Check for Bulk Booking Normal Net Agreement for Recall event with below conditions
    Given have OTC agreement otc.not.net.agreement.with.base.currency.usd.tick.qtp.equity.usd.fixed.value.prin.200k.cpty.100k
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type qtp.product on exposure summary page
    And add OTC trades otc.trade.with.amount.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type cash.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.cash.amount.3m.delivery
    And quick link - view statement
    And approve agreement agreement.new.created.above.will.be.approved.from.amended.status
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events tick.event.previous.agreement.produced.recall.amount.3000000
    Then Exposure Management event.previous.agreement.produced - event call status should be event.with.action.recall.amount.3000000

#    When add delivery booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000
    When Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.3000000.actual.500000

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000,bulk.booking.on.cash.class.with.amount.2400000
    And Edit booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000,bulk.booking.on.cash.class.with.amount.2400000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be booking.warning.message.shown.expected.3000000.actual.2900000

    When quick link - change bookings
#    And add delivery booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000,bulk.booking.on.cash.class.with.amount.2450000
    And Edit booking - bulk booking bulk.booking.on.cash.usd.with.amount.500000,bulk.booking.on.cash.class.with.amount.2450000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorsOrWarningsFound

    When quick link - save
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type cash.type agreement asset Bond Page
    Then asset holding detail should be recall.booking.on.cash.class.with.amount.2450000
    And asset holding detail should be recall.booking.on.cash.class.with.amount.500000

  @v14.1.0 @StatementBooking @YanLu @F11529 @T32932
  Scenario: 32932 Verify agreement level fields works correctly when set on both agreement and template-Concentration Limit Trigger
    Given have eligibility rule templates eligibility.rule.template.with.cl.trigger.5000.bond.cl.rule.instrument.holding.max.value.100.cash.no.cl.rule
    And have securities bond.instrument.with.bond.usd.issuerance.10.dirty.price.100,equity.instrument.with.equity.usd.price.1
    And have OTC agreements otc.net.agreement.with.cl.trigger.2000.rule.template.above.bond.usd.cl.rule.issurance.holding.min.issuance.100.equity.usd.cl.rule.type.holding.max.value.1000
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.bond.intrument.amount.2000.call
    And continue booking
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be breached.rule.with.rule.issuance.holding.method.min.issuance.rule.value.100.actual.10.source.agreement

    When quick link - view statement
    And edit asset summary info
    And view asset type equity.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.equity.intrument.amount.2000.call
    And continue booking
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be breached.rule.with.rule.issuance.holding.method.min.issuance.rule.value.1000.actual.2000.source.agreement

    When quick link - view statement
    And edit asset summary info
    And view asset type cash.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.cash.usd.amount.1000.call
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be no.breached.rule

    When quick link - view statement
    And edit asset summary info
    And view asset type equity.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.equity.intrument.amount.2000.call
    And continue booking
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be breached.rule.with.rule.issuance.holding.method.min.issuance.rule.value.1000.actual.4000.source.agreement

    When quick link - view statement
    And edit asset summary info
    And view asset type bond.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.bond.intrument.amount.4000.call
    And continue booking
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be breached.rule.with.rule.instrument.holding.method.max.value.rule.value.100.actual.6000.source.t1
    And asset concentration limit rule breached should be breached.rule.with.rule.issuance.holding.method.min.issuance.rule.value.100.actual.0.source.agreement
    And asset concentration limit rule breached should be breached.rule.with.rule.type.holding.method.max.value.rule.value.1000.actual.4000.source.agreement

  @v14.1.0 @StatementBooking @YanLu @F11529 @T32992 @D33758
  Scenario: 32992 Verify CL rule works correctly after implemented Template vs Agreement Eligibility Rules
#    Given have eligibility rule templates eligibility.rule.template.with.bond.usd1.pc.bond.usd2.pc.equity.usd1.pc.equity.usd2.pc.cash.usd.pc
#    And have securities bond.instrument1.with.bond.usd1.dirty.price.100,bond.instrument2.with.bond.usd2.dirty.price.100,equity.instrument3.with.equity.usd1.price.1,equity.instrument4.with.equity.usd2.price.1
#    And have OTC agreements otc.net.agreement.with.cl.trigger.2000.rule.template.above.bond.usd.cl.rule.issurance.holding.min.issuance.100.equity.usd.cl.rule.type.holding.max.value.1000
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
#    And edit asset summary info
#    And view asset type bondusd1.type agreement asset Bond Page
#    And add call bookings - statement booking booking.with.bond.instrument1.amount.1000.call
#    And quick link - view statement
#    And edit asset summary info
#    And view asset type bondusd2.type agreement asset Bond Page
#    And add call bookings - statement booking booking.with.bond.instrument2.amount.1000.delivery
#    And quick link - view statement
#    And edit asset summary info
#    And view asset type equityusd1.type agreement asset Bond Page
#    And add call bookings - statement booking booking.with.equity.instrument3.amount.1000.call
#    And quick link - view statement
#    And edit asset summary info
#    And view asset type equityusd2.type agreement asset Bond Page
#    And add call bookings - statement booking booking.with.equity.instrument4.amount.1000.delivery
#    And quick link - view statement
#    And edit asset summary info
#    And view asset type cashusd.type agreement asset Bond Page
#    And add call bookings - statement booking booking.with.cash.usd.amount.1000.call
#    And quick link - view statement
#    And edit agreement edit.agreement.above.to.change.cl.rule in agreement search page
    And quick link - agreement review
    And edit OTC agreement original.agreement to edit.agreement.above.to.change.cl.rule
    And view statement
    And view rules breached
    Then asset concentration limit rule breached should be breached.rule.with.rule.bond.instrument2.breach.agreement
    And asset concentration limit rule breached should be breached.rule.with.rule.equity.instrument3.instrument4.breach.agreement
    And asset concentration limit rule breached should be breached.rule.with.rule.equity.usd2.breach.agreement
    And asset concentration limit rule breached should be breached.rule.with.rule.cash.usd.breach.agreement

  @YanLu @T31921 @F10575 @14.1.0 @StatementBooking
  Scenario Outline:31921 make booking with instrument2 and instrument3 will breach rule with warning shown
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And edit asset summary info
    And view asset type <bondusd.type> agreement asset Bond Page
    And add call bookings - statement booking <booking.detail>
    Then asset eligibility rule breached should be <breached.rule.details>
    Examples:
      | agreement.search.by.description           | agreement.search.result | bondusd.type | booking.detail                   | breached.rule.details                                  |
      | search.agreement.by.agreement.description | agreement.search.result | bondusd.type | booking.with.instrument2.call.1m | beached.rule.with.instrument2.eligibility.rule.not.met |
      | search.agreement.by.agreement.description | agreement.search.result | bondusd.type | booking.with.instrument3.call.1m | beached.rule.with.instrument3.eligibility.rule.not.met |
      | search.agreement.by.agreement.description | agreement.search.result | bondusd.type | booking.with.instrument4.call.1m | beached.rule.with.instrument4.asset.type.not.eligible  |

  @YanLu @T31921 @F10575 @14.1.0 @StatementBooking
  Scenario: 31921 successful make booking without breach rule
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bondusd.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.bond.instrument1.amount.1m.call
    Then asset holding detail should be asset.booking.summary.instrument.id.31921bondinstrument1.norminal.amount.1m.call

  @YanLu @T3048 @BZ_33945 @F11455 @2011.1 @2011.2 @14.1.0 @BulkBooking
  Scenario Outline: 3048
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument1>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument1>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument1>

    When quick link - change bookings
    And add call booking - bulk booking <bulk.booking.with.instrument2>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument2>

    Examples:
      | agreement.search.by.description                  | agreement.search.result        | tick.event          | bulk.booking.with.instrument1              | warning.message.with.instrument1                        | bulk.booking.with.instrument2              | warning.message.with.instrument2                          |
      | search.agreement.by.agreement.description        | agreement.search.result        | even.with.call.3m   | bulk.booking.with.3048.instrument1.1m.call | warning.message.with.error.instrument1                  | bulk.booking.with.3048.instrument2.1m.call | warning.message.with.error.instrument2                    |
      | search.autonetagreement.by.agreement.description | autonetagreement.search.result | even.with.call.3m   | bulk.booking.with.3048.instrument1.1m.call | warning.message.with.error.instrument1.autonetagreement | bulk.booking.with.3048.instrument2.1m.call | warning.message.with.error.instrument2.autonetagreement   |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.imcall.1m | bulk.booking.with.3048.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3048.instrument2.1m.call | warning.message.with.no.error.instrument2.notnetagreement |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.vmcall.2m | bulk.booking.with.3048.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3048.instrument2.1m.call | warning.message.with.error.instrument2.notnetagreement    |

  @YanLu @T3047 @BZ_33945 @F11455 @2011.1 @2011.2 @14.1.0 @BulkBooking
  Scenario Outline: 3047
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument1>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument1>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument1>

    When quick link - change bookings
#    And add call booking - bulk booking <bulk.booking.with.instrument2>
    And Edit booking - bulk booking <bulk.booking.with.instrument2>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument2>

    Examples:
      | agreement.search.by.description                  | agreement.search.result        | tick.event          | bulk.booking.with.instrument1              | warning.message.with.instrument1                        | bulk.booking.with.instrument2              | warning.message.with.instrument2                          |
      | search.agreement.by.agreement.description        | agreement.search.result        | even.with.call.3m   | bulk.booking.with.3047.instrument1.1m.call | warning.message.with.error.instrument1                  | bulk.booking.with.3047.instrument2.1m.call | warning.message.with.error.instrument2                    |
      | search.autonetagreement.by.agreement.description | autonetagreement.search.result | even.with.call.3m   | bulk.booking.with.3047.instrument1.1m.call | warning.message.with.error.instrument1.autonetagreement | bulk.booking.with.3047.instrument2.1m.call | warning.message.with.error.instrument2.autonetagreement   |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.imcall.1m | bulk.booking.with.3047.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3047.instrument2.1m.call | warning.message.with.no.error.instrument2.notnetagreement |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.vmcall.2m | bulk.booking.with.3047.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3047.instrument2.1m.call | warning.message.with.error.instrument2.notnetagreement    |

  @YanLu @T3049 @BZ_33945 @F11455 @2011.1 @2011.2 @14.1.0 @BulkBooking
  Scenario Outline: 3049
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument1>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument1>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument1>

    When quick link - change bookings
#    And add call booking - bulk booking <bulk.booking.with.instrument2>
    And Edit booking - bulk booking <bulk.booking.with.instrument2>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument2>

    Examples:
      | agreement.search.by.description                  | agreement.search.result        | tick.event          | bulk.booking.with.instrument1              | warning.message.with.instrument1                        | bulk.booking.with.instrument2              | warning.message.with.instrument2                          |
      | search.agreement.by.agreement.description        | agreement.search.result        | even.with.call.3m   | bulk.booking.with.3049.instrument1.1m.call | warning.message.with.error.instrument1                  | bulk.booking.with.3049.instrument2.1m.call | warning.message.with.error.instrument2                    |
      | search.autonetagreement.by.agreement.description | autonetagreement.search.result | even.with.call.3m   | bulk.booking.with.3049.instrument1.1m.call | warning.message.with.error.instrument1.autonetagreement | bulk.booking.with.3049.instrument2.1m.call | warning.message.with.error.instrument2.autonetagreement   |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.imcall.1m | bulk.booking.with.3049.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3049.instrument2.1m.call | warning.message.with.no.error.instrument2.notnetagreement |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.vmcall.2m | bulk.booking.with.3049.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3049.instrument2.1m.call | warning.message.with.error.instrument2.notnetagreement    |

  @YanLu @T3045 @BZ_33945 @F11455 @2011.1 @2011.2 @14.1.0 @BulkBooking
  Scenario Outline: 3045
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument1>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument1>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument1>

    When quick link - change bookings
#    And add call booking - bulk booking <bulk.booking.with.instrument2>
    And Edit booking - bulk booking <bulk.booking.with.instrument2>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument2>

    Examples:
      | agreement.search.by.description                  | agreement.search.result        | tick.event          | bulk.booking.with.instrument1              | warning.message.with.instrument1                        | bulk.booking.with.instrument2              | warning.message.with.instrument2                          |
      | search.agreement.by.agreement.description        | agreement.search.result        | even.with.call.3m   | bulk.booking.with.3045.instrument1.1m.call | warning.message.with.error.instrument1                  | bulk.booking.with.3045.instrument2.1m.call | warning.message.with.error.instrument2                    |
      | search.autonetagreement.by.agreement.description | autonetagreement.search.result | even.with.call.3m   | bulk.booking.with.3045.instrument1.1m.call | warning.message.with.error.instrument1.autonetagreement | bulk.booking.with.3045.instrument2.1m.call | warning.message.with.error.instrument2.autonetagreement   |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.imcall.1m | bulk.booking.with.3045.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3045.instrument2.1m.call | warning.message.with.no.error.instrument2.notnetagreement |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.vmcall.2m | bulk.booking.with.3045.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3045.instrument2.1m.call | warning.message.with.error.instrument2.notnetagreement    |

  @YanLu @T3044 @BZ_33945 @F11455 @2011.1 @2011.2 @14.1.0 @BulkBooking
  Scenario Outline: 3044
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument1>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument1>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument1>

    When quick link - change bookings
#    And add call booking - bulk booking <bulk.booking.with.instrument2>
    And Edit booking - bulk booking <bulk.booking.with.instrument2>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument2>

    Examples:
      | agreement.search.by.description                  | agreement.search.result        | tick.event          | bulk.booking.with.instrument1              | warning.message.with.instrument1                        | bulk.booking.with.instrument2              | warning.message.with.instrument2                          |
      | search.agreement.by.agreement.description        | agreement.search.result        | even.with.call.3m   | bulk.booking.with.3044.instrument1.1m.call | warning.message.with.error.instrument1                  | bulk.booking.with.3044.instrument2.1m.call | warning.message.with.error.instrument2                    |
      | search.autonetagreement.by.agreement.description | autonetagreement.search.result | even.with.call.3m   | bulk.booking.with.3044.instrument1.1m.call | warning.message.with.error.instrument1.autonetagreement | bulk.booking.with.3044.instrument2.1m.call | warning.message.with.error.instrument2.autonetagreement   |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.imcall.1m | bulk.booking.with.3044.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3044.instrument2.1m.call | warning.message.with.no.error.instrument2.notnetagreement |
      | search.notnetagreement.by.agreement.description  | notnetagreement.search.result  | even.with.vmcall.2m | bulk.booking.with.3044.instrument1.1m.call | warning.message.with.error.instrument1.notnetagreement  | bulk.booking.with.3044.instrument2.1m.call | warning.message.with.error.instrument2.notnetagreement    |

  @YanLu @BulkBooking @T24960 @F5762 @F6823 @F11455 @2012.3 @14.1.0
  Scenario Outline: 24960
    When search agreement <agreement.search.by.description>
    And view completed agreement statement <agreement.search.result>
    And quick link - agreement exposure management
    And Exposure Management - tick events <tick.event>
#    And add call booking - bulk booking <bulk.booking.with.instrument>
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking <bulk.booking.with.instrument>
    And quick link - validate
    Then Exposure Managerment - EM booking message should be <warning.message.with.instrument>

    Examples:
      | agreement.search.by.description           | agreement.search.result | tick.event         | bulk.booking.with.instrument                | warning.message.with.instrument        |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument1.1m.call | warning.message.with.error.instrument1 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument2.1m.call | warning.message.with.error.instrument2 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument3.1m.call | warning.message.with.error.instrument3 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument4.1m.call | warning.message.with.error.instrument4 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument5.1m.call | warning.message.with.error.instrument5 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument6.1m.call | warning.message.with.error.instrument6 |
      | search.agreement.by.agreement.description | agreement.search.result | even.with.call.10m | bulk.booking.with.24960.instrument7.1m.call | warning.message.with.error.instrument7 |

  @YanLu @T32477 @F11281 @F11455 @14.1.0 @BulkBooking
  Scenario: 32477
    When search agreement agreement.search.by.description
    And view completed agreement statement agreement.search.result
    And quick link - agreement exposure management
    And Exposure Management - tick events even.with.delivery.1m
#    And add call booking - bulk booking bulk.booking.with.32477.instrument
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bulk.booking.with.32477.instrument
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warning.message.with.instrument

  @YanLu @T33243 @F11314 @14.1.0 @StatementBooking
  Scenario: 33243
    When search agreement agreement.search.by.description
    And edit agreement agreement.search.result in agreement search page
    And edit OTC agreement original.agreement to verify.agreement.eligible.currency.usd
    And view statement
    And view eligibilty rules breached
    Then asset eligibility rule breached should be breached.eligibility.rule.with.instrument2.rule.security.currency.not.eligible.source.agreement.currency.cad

    When search agreement agreement.search.by.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type bondusd.type agreement asset Bond Page
    And add call bookings - statement booking booking.with.instrument1.call.4m
    And continue booking
    And quick link - view statement
    And view rules breached
    Then asset concentration limit rule breached should be beached.rule.with.instrument1.method.max%.rule.value.75.actual.value.100.source.agreement


  @JaneZhang @AssetHoldings @2012.2 @F4047 @T21713
  Scenario:im would follow gross logic and vm would follow net logic when gross calc set to gross im
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
#    And edit summary exposure info
#    And view product type product.qtp on exposure summary page
#    And add OTC trades trade1.cptyAmount.2k,trade2.cptyAmount.5k
#    And quick link - view statement
#    And edit asset summary info
#    And view asset type asset.type.CASH.USD agreement asset CASH Page
#    And add call bookings - statement booking booking1.call.imAmount.2k,booking2.call.vmAmount.3k
#    And quick link - view statement
#    And approve agreement agr21713
    And recalculate agreement statement

    And quick link - agreement exposure management
    Then Exposure Management - event should be IMDelivery.-5k,IMReturn.-1k,VMDelivery.-7k,VMReturn.-3k

    And Exposure Management - tick events VMDelivery.-7k
    And add delivery booking - bulk booking bulk.booking.with.VM.Cash.delivery.-7k
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warning.message.with.Cash.vmAmountCheck
    And cancel bulk booking

    And Exposure Management - tick events VMReturn.-3k
    And add return booking - bulk booking bulk.booking.with.VM.Cash.return.-3k
    And quick link - save
    And Exposure Management - tick events IMDelivery.-5k
    And add return booking - bulk booking bulk.booking.with.IM.Cash.delivery.-5k
    And quick link - save
    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be asset.booking.summary.cash.vm.amount.3k.return
    Then asset holding detail should be asset.booking.summary.cash.im.amount.5k.delivery


  @JaneZhang @StatementBooking @13.0.0.SP5 @F10626 @T31297
  Scenario:Verify multiple tradable amount related warning message works well when add booking
    When navigate to security search page
    And add securities bondinstrument.MultipleTradableAmount.10.23
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved

    And search agreement filter.agmt.description
    And view completed agreement statement agreement.description

    When edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And add call booking - statement booking callBooking1With1000000Amount
 #  Check the warning message "Cannot proceed with booking as the booking does not comply with the Multiple Trading Amount."
    And edit call booking booking1Pending to booking1ChangeToCancelled
    And add call booking - statement booking callBooking2With2000Amount


    And quick link - view statement
    And approve agreement agr31297
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events Return
    And add return booking - bulk booking bulk.booking.with.bond.return.2000
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.with.bond
    When Exposure Management - override warning override.warning.message.shown.by.previous.bulk.booking
    And quick link - save

    And search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    When edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And edit call booking booking3Pending to booking3ChangeToCancelled

    And quick link - view statement
    And approve agreement agr31297_2
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - tick events Return
    And add delivery bookings - group booking group.booking.with.bond.return.100
#  Check the warning message "Cannot proceed with booking as the booking does not comply with the Multiple Trading Amount." when click cancel
    And edit return booking group.booking.with.bond.return.2000
#  Check the warning message "Cannot proceed with booking as the booking does not comply with the Multiple Trading Amount." when click OK

  @JaneZhang @BulkBooking @14.1.0 @F10796 @T32140 @Proxied
  Scenario:Verify the half even rounding is avaliable for Asset valution and summary
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page

#    And add call booking - statement booking callBooking1.vmAmount.1000.1150.imAmount.1000.1251

    Then CASH asset booking summary should be asset.booking1.summary.cash.call.nominalAmount.2000.24
    When click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking payBooking2.Pay.VMInterest.2000.1250
    And add Cash Component booking - statement booking cappayBooking3.CapPay.IMInterest.3000.1251
    Then cash movement summary should be payBooking2ExpectedValue.VM.2000.12
    Then cash movement summary should be cappayBooking3ExpectedValue.IM.3000.13
    When click back button to previous page
    When click back button to previous page
    Then asset holding summary should be AllBookingExpectedValue.marketValue.5000.37
    When click back button to previous page
    Then agreement statement should be agrStatement32140.CheckHalfEvenRounding

    When navigate to Asset Settlements Report
    And search Asset Settlements Report agr32140.DataWithHalfEvenRounding
    And run report to assetSettlementReportPath as Excel Worksheet
    Then assert Excel Worksheet report assetSettlementReportPath should follow the rule reportRuleApplyHalfEvenRounding

  @JaneZhang @v2011.2.SP6;2012.3.SP9 @D11515#D16750 @StatementBooking @T26988
  Scenario:Verify bookings can not be changed to authorised status when tick do not include authorised call bookings
    When navigate to collateral preferences page
    When set collateral preferences CollateralPreferences26988.DoNotIncludeAuthorised
    And search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call booking - statement booking callBooking1Cash.nominalAmount.1m,callBooking2Cash.nominalAmount.2m
    When open asset booking editor page callBooking1Edit
    Then call booking in booking editor page should be callBooking1EditExpected.settlementStatusNotIncludeAuthorised
    When asset booking edit - click cancel button
    And edit call booking callBooking1Pending to callBooking1PendingSettlement
    When open asset booking editor page callBooking1Edit
    Then call booking in booking editor page should be callBooking1EditExpected2.AuditInfoNotIncludeAuthorised
    When asset booking edit - click cancel button
    When click back button to previous page

    And view asset type qtp_bond_usd agreement asset Bond Page
    And add call booking - statement booking callBooking3Bond.nominalAmount.1m
    When open asset booking editor page callBooking3Edit
    Then call booking in booking editor page should be callBooking3EditExpected.settlementStatusNotIncludeAuthorised
    When asset booking edit - click cancel button
    And edit call booking callBooking3Pending to callBooking3PendingSettlement
    When open asset booking editor page callBooking3Edit
    Then call booking in booking editor page should be callBooking3EditExpected2.AuditInfoNotIncludeAuthorised
    When asset booking edit - click cancel button
    When click back button to previous page

    And view asset type qtp_equity_usd agreement asset Equity Page
    And add call booking - statement booking callBooking4Equlity.nominalAmount.1m
    When open asset booking editor page callBooking4Edit
    Then call booking in booking editor page should be callBooking4EditExpected.settlementStatusNotIncludeAuthorised
    When asset booking edit - click cancel button
    And edit call booking callBooking4Pending to callBooking4PendingSettlement
    When open asset booking editor page callBooking4Edit
    Then call booking in booking editor page should be callBooking4EditExpected2.AuditInfoNotIncludeAuthorised
    When asset booking edit - click cancel button
    When click back button to previous page

    When navigate to workflow dashboard page
    Then workflow dashboard should be dashboard1
    When workflow dashboard - expand for details of outstanding entries up to today
    When workflow dashboard - click asset asset.type.cash under Collateral Settlement Summary
    Then settlement status - check all approveButton Approve All Authorised should not exist in settlement status search result

    When settlement status summary - click return to filter button
    And settlement status - search settlement status settlementStatusSearch1
    And settlement status - show asset cash.usd1 details
    Then settlement status - check all approveButton Approve All Authorised should not exist in settlement status page
    When settlement status - cash approve buttons approveAllTicked

    And search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    When open asset booking editor page callBooking2Edit
    Then call booking in booking editor page should be callBooking2EditExpected1.AuditInfoNotIncludeAuthorised


  @JaneZhang @13.1.0 @F7883 @BulkBooking @T28819
  Scenario: Verify succeed to make bulk booking for ETF agreement
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add ETF trades trade1Order1,trade2Order2
    And quick link - view statement
    And approve agreement agr28819
    And recalculate agreement statement
    And quick link - agreement exposure management
    When Exposure Management - expand events allEventForETF
    Then Exposure Management - event should be call.Amount.3352165.30,feeCall.Amount.1m,cashCall.Amount.963716.09,vmCall.Amount.1388449.21,Delivery.Amount.6000000.02,vmDelivery.Amount.2000000.01,feeDelivery.Amount.2000000.01,CashDelivery.Amount.2000000.00
    When Exposure Management - tick events feeCall.Amount.1m,feeDelivery.Amount.2000000.01
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be feeCall.Receive.amount.1m
    Then EM Bulk booking should be feeDelivery.Pay.amount.-2m
    When cancel bulk booking

    When Exposure Management - expand events allEventForETF
    And Exposure Management - tick events feeCall.Amount.1m
    And add call booking - bulk booking bulk.booking.with.feeCall.Cash.GBP.1037650
    And quick link - save
    When Exposure Management - expand events allEventForETF
    And Exposure Management - tick events feeDelivery.Amount.2000000.01
    And add delivery booking - bulk booking bulk.booking.with.feeDelivery.Cash.GBP.2075300.01
    And quick link - save
    Then Exposure Management - event should be feeCallCompleted,feeDeliveryCompleted

    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
    And edit asset summary info
    And view asset type asset.type.CASH.GBP agreement asset Cash Movements Page
    Then cash movement summary should be FeeReceive.Amount.1037650.00
    Then cash movement summary should be FeePay.Amount.2075300.01


  @JaneZhang @2011.2 @F1277 @FeedAssetBookings @T16020_1
  Scenario Outline: Verify warning message when Feed Asset booking for Full Dispute event
    When search agreement filter.agmt.description
    And view completed agreement statement agreement.description
#    And edit summary exposure info
#    And view product type ProductQTP on exposure summary page
#    And add OTC trades trade1.PrincipalValuationAmount.-2m

#    And quick link - view statement
#    And edit asset summary info
#    And view asset type asset.type.CASH.USD agreement asset CASH Page
#    And add call booking - statement booking Booking1.Cash.Call.1m
#    And approve agreement agr28819
#
    And quick link - agreement exposure management
    Then Exposure Management - event should be Delivery.2m,Return.1m
#    Can't set the CpyAmount by notReverse
#    When Exposure Management - set event Delivery.CpyAmount value to Delivery.CpyAmount.1m.NotReverse
#    And Exposure Management - set event Return.CpyAmount value to Return.CpyAmount.0.5m.NotReverse
#    And Exposure Management - save changes
#    Then Exposure Management - event should be Return.CpyAmount.1.5m


    When feed asset bookings <feed.asset.booking> by <file.format>
    Then feed log should be <feed.asset.booking.result>
    And feed status should be <feed.asset.booking.status>
    And search agreement <search.feed.asset.booking.agreement>
    And view completed agreement statement <search.result.feed.asset.booking.agreement>
    And edit asset summary info
    And view asset type <feed.asset.booking.holding.summary> agreement asset <asset.type> Page
    Then asset holding detail should be <feed.asset.booking.holding.summary>
    When open asset booking editor page <feed.asset.booking.holding.summary>
    Then booking details should be <check.feed.asset.booking.detail>

    When navigate to task scheduler page
    And edit task scheduler <task.feed.asset.booking>
    And copy Asset Booking feed <task.file.format> file <feed.asset.booking> for <task.feed.asset.booking> to <file>
    And run task scheduler <task.feed.asset.booking>
    Then task scheduler messaging should be <task.feed.asset.booking.message>
    And feed status should be <task.feed.asset.booking.status>

    Examples:
      | feed.asset.booking                   | file.format | feed.asset.booking.result                                          | feed.asset.booking.status                                                       | search.feed.asset.booking.agreement | search.result.feed.asset.booking.agreement | feed.asset.booking.holding.summary                         | asset.type | check.feed.asset.booking.detail                         | task.feed.asset.booking                            | file | task.feed.asset.booking.message                            | task.file.format | task.feed.asset.booking.status                                                       |
      | feed.cash.Return.2k.withEventId      | excel       | feed.cash.Return.2k.withEventId.passWithFullDisputeWarning         | feed.cash.Return.2k.withEventId.statusResult.passWithFullDisputeWarning         | filter.agmt.description             | agreement.description                      | feed.asset.booking.holding.summary.Return.withEventId      | CASH       | check.feed.asset.booking.detail.Return.withEventId      | task.feed.asset.booking.return.withEventId.2k      | file | task.feed.asset.booking.message.return.withEventId.2k      | xlsx             | task.feed.cash.Return.2k.withEventId.statusResult.passWithFullDisputeWarning         |
      | feed.cash.Delivery.2k.withEventId    | excel       | feed.cash.Delivery.2k.withEventId.passWithoutFullDisputeWarning    | feed.cash.Delivery.2k.withEventId.statusResult.passWithoutFullDisputeWarning    | filter.agmt.description             | agreement.description                      | feed.asset.booking.holding.summary.Delivery.withEventId    | CASH       | check.feed.asset.booking.detail.Delivery.withEventId    | task.feed.asset.booking.delivery.withEventId.2k    | file | task.feed.asset.booking.message.delivery.withEventId.2k    | xlsx             | task.feed.cash.Delivery.2k.withEventId.statusResult.passWithoutFullDisputeWarning    |
      | feed.cash.Return.2k.withoutEventId   | excel       | feed.cash.Return.2k.withoutEventId.passWithoutFullDisputeWarning   | feed.cash.Return.2k.withoutEventId.statusResult.passWithoutFullDisputeWarning   | filter.agmt.description             | agreement.description                      | feed.asset.booking.holding.summary.Return.withoutEventId   | CASH       | check.feed.asset.booking.detail.Return.withoutEventId   | task.feed.asset.booking.return.withoutEventId.2k   | file | task.feed.asset.booking.message.return.withoutEventId.2k   | xlsx             | task.feed.cash.Return.2k.withoutEventId.statusResult.passWithoutFullDisputeWarning   |
      | feed.cash.Delivery.2k.withoutEventId | excel       | feed.cash.Delivery.2k.withoutEventId.passWithoutFullDisputeWarning | feed.cash.Delivery.2k.withoutEventId.statusResult.passWithoutFullDisputeWarning | filter.agmt.description             | agreement.description                      | feed.asset.booking.holding.summary.Delivery.withoutEventId | CASH       | check.feed.asset.booking.detail.Delivery.withoutEventId | task.feed.asset.booking.delivery.withoutEventId.2k | file | task.feed.asset.booking.message.delivery.withoutEventId.2k | xlsx             | task.feed.cash.Delivery.2k.withoutEventId.statusResult.passWithoutFullDisputeWarning |


  @JaneZhang @2012.3 @FF3855#F5843 @AssetHoldings @T24141
  Scenario: verify bulk booking ui and logic for fcm multi model agreement when tsa level is component
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type product.qtp on exposure summary page
    And add FCM trades trade1.m1.tsaCapAmount.1m,trade2.m1.tsaPhyAmount.-1.1m,trade3.m2.tsaCapAmount.-0.8m,trade4.m2.tsaPhyAmount.1.25m
    And quick link - view statement
    And approve agreement agr24141
    And quick link - agreement exposure management

#    When Exposure Management - expand events allEventForFCM
#    Then Exposure Management - event should be TSADelivery.Net.Amount.277294.13,TSACall.o2.Amount.900285,TSADelivery.o1.Amount.1527294.13,VMDelivery.Net.Amount.110759.37,VMDelivery.o2.Amount.800000,VMCall.o1.Amount.1000000
#    When Exposure Management - tick events TSADelivery.Net.Amount.277294.13,TSACall.o2.Amount.900285,TSADelivery.o1.Amount.1527294.13
#    When Exposure Management - go to bulk booking page
#    Then EM Bulk booking should be TSABulkBooking
#    When cancel bulk booking

    When Exposure Management - expand events allEventForFCM
    And Exposure Management - tick events TSADelivery.Net.Amount.277294.13,TSACall.o2.Amount.900285,TSADelivery.o1.Amount.1527294.13,VMDelivery.Net.Amount.110759.37,VMDelivery.o2.Amount.800000,VMCall.o1.Amount.1000000
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be vmAndTSABulkBooking
    When Edit booking - bulk booking vmCall.Cash.USD.apply,vmDelivery.Cash.EUR.apply
    And quick link - save

    Then Exposure Managerment - EM booking message should be error.message.TSACallBookingNotCorrect
    Then Exposure Managerment - EM booking message should be error.message.TSADeliveryBookingNotCorrect
    When cancel bulk booking

    And search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And select model m1
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking payBooking1.BankedCoupon.capReceive.720228
    And quick link - view statement

    And select model m2
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking payBooking2.imInterest.capPay.1110759.37

    And quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - expand events allEventForFCM
    And Exposure Management - tick events TSADelivery.Net.Amount.277294.13,TSACall.o2.Amount.900285,TSADelivery.o1.Amount.1527294.13,VMDelivery.Net.Amount.110759.37,VMDelivery.o2.Amount.800000,VMCall.o1.Amount.1000000

    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking TSADelivery.tranferValue.Cash.USD.pay.notionalValue.1527294.14,TSACall.totalResetToPar.Cash.EUR.Receive.notionalValue.720228,vmCall.Cash.EUR.Recall.notionalValue.720228,vmDelivery.Cash.USD.Return.notionalValue.1110759.37
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events allEventForFCM
    Then Exposure Management - event should be TSADelivery.m1.completed,VMDelivery.m2.completed,VMCall.m1.completed,VMDelivery.net.completed

    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    When open asset booking editor page Cash.USD.Return.1110759.37
    Then call booking in booking editor page should be Cash.USD.Return.1110759.37.detailInfo
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    And open cash movement editor page TSA.pay.m1
    Then cash movement detail should be TSA.pay.m1.1527294.14
    When asset booking edit - click cancel button
    And open cash movement editor page TSA.capPay.m2
    Then cash movement detail should be TSA.capPay.m2.1110759.37.ExcludedInStatementCalc
    When asset booking edit - click cancel button
    And click back button to previous page
    And click back button to previous page

    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    When open asset booking editor page Cash.EUR.Recall.720228
    Then call booking in booking editor page should be Cash.EUR.Recall.720228.detailInfo
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.CASH.EUR agreement asset Cash Movements Page
    And open cash movement editor page TSA.capReceive.m1
    Then cash movement detail should be TSA.capReceive.m1.720228.ExcludedInStatementCalc
    When asset booking edit - click cancel button
    And open cash movement editor page TSA.receive.m2
    Then cash movement detail should be TSA.receive.m2.720228


  @JaneZhang @13.0.0 @F6561CR#D15875 @AssetHoldings @T28053
  Scenario: verify sub request event bulk booking page leg2 proposed value net agreement corp
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr28053_recall
    And Exposure Management - create adhoc substitution events bond_usd_delivery_4000000
    Then Exposure Management - event should be substitutionRequest.bond.usd.created

    When Exposure Management - tick events substitutionRequest.bond.usd.created
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be leg2.delivery.value.0
    When cancel bulk booking

    And Exposure Management - set event substitutionRequest.element.withoutCashGBP value to substitutionRequest.element.addCashGBP
    When Exposure Management - tick events substitutionRequest.bond.usd.created
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be leg2.delivery.value.0
    When Edit booking - bulk booking leg1.Recall.bond.usd.notional.1m
    Then EM Bulk booking should be leg2.delivery.value.0.8m
    And Edit booking - bulk booking leg1.Recall.cash.usd.value.1m.apply
    Then EM Bulk booking should be leg2.delivery.value.1.8m
    When Edit booking - bulk booking leg2.delivery.cash.usd.notional.1m
    Then EM Bulk booking should be leg2.delivery.value.SecondBooking.0.8m
    When Edit booking - bulk booking leg2.recall.bond.usd.nominalAmount.2m.update
    Then EM Bulk booking should be leg2.delivery.value.SecondBooking.1.6m
    When Edit booking - bulk booking leg2.delivery.value.SecondBooking.bond.gbp.update
    Then EM Bulk booking should be leg2.delivery.SecondBooking.value.0m.FirstBooking.value.1.8m
    When Edit booking - bulk booking leg1.recall.bond.usd.delete.And.leg2.delivery.addThirdBooking
    Then EM Bulk booking should be leg2.delivery.ThirdBooking.value.1.6m.SecondBooking.value.0m
    When Edit booking - bulk booking leg2.delivery.cash.usd.nominalValue.2m.update
    Then EM Bulk booking should be leg2.delivery.ThirdBooking.value.2.6m
    When Edit booking - bulk booking leg2.delivery.cash.usd.delete
    Then EM Bulk booking should be leg2.delivery.ThirdBooking.value.0.6m
    When quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.leg1Andleg2NotEqual.Difference.0.6m
    When Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be subsitutionRequest.PartiallyBooked.leg1.1m.leg2.1.6m
    When Exposure Management - tick events subsitutionRequest.PartiallyBooked.leg1.1m.leg2.1.6m
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be leg2.delivery.firstBooking.value.0m
    When Edit booking - bulk booking leg1.recall.bond.usd.nominalAmount.2m,leg2.delivery.cash.usd.nominalAmount.2m
##      v14.1 has bug when edit the intrusmentid
    When quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.leg1Andleg2NotEqual.Difference.1m
    When Edit booking - bulk booking leg1.recall.bond.usd.nominalAmount.2m,leg2.delivery.cash.usd.nominalAmount.2m
#      v14.1 has bug when edit the intrusmentid
    When quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.leg1Andleg2NotEqual.Difference.1m

  @JaneZhang @2012.3 @F3855#F5865 @AssetHoldings @T24135
  Scenario: Verify both collateral and model eligiblity check for capitalise and physical tsa of bulk booking when settlement level is model
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit summary exposure info
    And view product type product.qtp on exposure summary page
    And add FCM trades trade1.USD.imInterest.1m.PAI.-0.6m,trade2.EUR.imInterest.-0.58m.PAI.0.8m
    And quick link - view statement
    And approve agreement agr24135
    And quick link - agreement exposure management

    When Exposure Management - expand events allEventForFCM
    Then Exposure Management - event should be vmCall.net.requirementAmount.194699.46,vmDelivery.m2.requirementAmount.580000,vmCall.m1.requirementAmount.1000000,TSACall.Net.Amount.510759.37,TSADelivery.m1.Amount.600000,TSACall.m2.Amount.80000
    When Exposure Management - tick events vmCall.net.requirementAmount.194699.46,vmDelivery.m2.requirementAmount.580000,vmCall.m1.requirementAmount.1000000,TSACall.Net.Amount.510759.37,TSADelivery.m1.Amount.600000,TSACall.m2.Amount.80000
    When Exposure Management - go to bulk booking page
    When Edit booking - bulk booking TSA.delivery.cash.eur
    Then alert message should be assetTypeNotEligible in bulk booking page
    When Edit booking - bulk booking closeAlterMessage
    When Edit booking - bulk booking TSA.call.cash.usd
    Then alert message should be assetTypeNotEligible in bulk booking page
    When Edit booking - bulk booking closeAlterMessage
    When cancel bulk booking

    When Exposure Management - expand events allEventForFCM
    And Exposure Management - tick events vmCall.net.requirementAmount.194699.46,vmDelivery.m2.requirementAmount.580000,vmCall.m1.requirementAmount.1000000,TSACall.Net.Amount.510759.37,TSADelivery.m1.Amount.600000,TSACall.m2.Amount.80000
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking TSA.delivery.cash.usd.apply.And.vm.call.cash.usd.apply
    And Edit booking - bulk booking TSA.call.cash.eur.apply.And.vm.delivery.cash.eur.apply
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.TSACallBookingNotCorrect
    Then Exposure Managerment - EM booking message should be error.message.TSADeliveryBookingNotCorrect
    When cancel bulk booking
    When Exposure Management - expand events allEventForFCM
    Then Exposure Management - event should be TSACall.net.event.completed,TSADelivery.m1.event.completed,TSACall.m2.event.completed

    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    And open cash movement editor page TSA.pai.pay
    Then cash movement detail should be TSA.pai.pay.-600000
    When asset booking edit - click cancel button
    And click back button to previous page
    And click back button to previous page

    And view asset type asset.type.CASH.EUR agreement asset Cash Movements Page
    And open cash movement editor page TSA.pai.receive
    Then cash movement detail should be TSA.pai.receive.800000

      @JaneZhang @F5762 @AssetHoldings @T24981
  Scenario: Verify eligibility check for haicut rules with eligibility rule template set in agreement when bulk booking in em for umbrella agreement which is collateral at um
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And quick link - agreement exposure management
    When Exposure Management - tick events agr24981.umbrella
    When Exposure Management - go to bulk booking page
    When Edit booking - bulk booking umbrella.event.bond.usd
    Then alert message should be assetTypeNotEligible in bulk booking page

      @JaneZhang @14.1.0 @F10796 @AssetHoldings @T32123
  Scenario: Verify the half even rounding for max recall or return validation check for subsitution confirmation event when make bulk booking
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type qtp_bond_usd agreement asset Bond Page
    And add call booking - statement booking callBooking1Bond.usd.nominalAmount.1000.1149.settlementDate.dayBeforeT,callBooking2Bond.usd.nominalAmount.2000.1250.settlementDate.t,callBooking3Bond.usd.nominalAmount.3000.1251.settlementDate.dayAfterT
    And quick link - agreement review
    And edit OTC agreement bond.usd.noEligibilityRule to bond.usd.withEligibilityRule.excludeInstrumentId
    And view statement
    And approve agreement agr32123
    And quick link - agreement exposure management
    Then Exposure Management - event should be substitutionConfirmation.agreementRequirement.-6000.36
    When Exposure Management - tick events substitutionConfirmation.agreementRequirement.-6000.36
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be subConfirmation.return.assetAvailable.3000.24.returnHolding.6000.36
    When Edit booking - bulk booking subConfirmation.return.notional.3000.2451
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.nominalAmountExceedRange.3000.24
    When quick link - change bookings
    And Edit booking - bulk booking subConfirmation.return.settlementDate.oneDayBeforeToday
    Then EM Bulk booking should be subConfirmation.return.assetAvailable.1000.11.returnHolding.6000.36
    When Edit booking - bulk booking subConfirmation.return.notional.1000.1251
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.nominalAmountExceedRange.1000.115
    When quick link - change bookings
    And Edit booking - bulk booking subConfirmation.return.settlementDate.oneDayAfterToday
    Then EM Bulk booking should be subConfirmation.return.assetAvailable.6000.36.returnHolding.6000.36
    When Edit booking - bulk booking subConfirmation.return.notional.6000.3651
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.nominalAmountExceedRange.6000.365
    When quick link - change bookings
    When Edit booking - bulk booking subConfirmation.return.notional.6000.36
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.bookedForDeliveryNotEqualToReceipt
    When Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be substitutionConfirmation.callStatusBecomePartiallyBooked



      @JaneZhang @14.1.0 @F10796 @AssetHoldings @T32121
  Scenario: Verify the half even rounding for max recall or return validation check for subsitution confirmation event when make bulk booking for notnet agreement
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add delivery booking - statement booking booking1.cash.usd.delivery.vm.1000.1150.im.1000.1251.settlementDate.dayBeforeT,booking2.cash.usd.delivery.vm.2000.1250.im.2000.1150.settlementDate.t,booking3.cash.usd.delivery.vm.3000.1250.im.3000.1250.settlementDate.dayAfterT
    When click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    And add Cash Component booking - statement booking Booking1.CapRec.VMInterest.500.1150.dayBeforeT
    And add Cash Component booking - statement booking Booking2.CapRec.VMInterest.500.1250.dayAfterT
    And add Cash Component booking - statement booking Booking3.CapRec.IMInterest.500.1251.T
    And add Cash Component booking - statement booking Booking4.CapRec.TSA.500.1209.dayBeforeT
    And quick link - view statement
    And approve agreement agr32121
    And quick link - agreement exposure management
    Then Exposure Management - event should be IMRecall.agreementRequirement.6500.49,VMCall.agreementRequirement.7500.73
    When Exposure Management - tick events IMRecall.agreementRequirement.6500.49,VMCall.agreementRequirement.7500.73
    When Exposure Management - go to bulk booking page
    When Edit booking - bulk booking imRecallEvent.Recall.imNotional.3500.375.vmCallEvent.Recall.vmNotional.4000.4851
    Then EM Bulk booking should be imRecallEvent.Recall.imAssetAvailable.3500.37.recallHolding.6500.49
    Then EM Bulk booking should be imRecallEvent.Recall.AssetAvailable.7500.84.recallHolding.14001.22
    Then EM Bulk booking should be vmCallEvent.Recall.vmAssetAvailable.4000.48.recallHolding.7500.73
##    Have a bug in v14.1 for below step
    Then EM Bulk booking should be vmCallEvent.Recall.AssetAvailable.7500.84.recallHolding.14001.22
    When quick link - save
    Then Exposure Managerment - EM booking message should be error.message.VMAmountExceedRange.4000.476
    Then Exposure Managerment - EM booking message should be error.message.IMAmountExceedRange.3500.365
    When quick link - change bookings
    And Edit booking - bulk booking IMRecallEventAndVMCallEvent.settlementDate.oneDayBeforeToday
    Then EM Bulk booking should be IMRecallEvent.imAssetAvailable.1000.13.VMCallEvent.vmAssetAvailable.2000.35
    When Edit booking - bulk booking imRecallEvent.Recall.imNotional.1000.1251.vmCallEvent.Recall.vmNotional.4000.4851
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.vmNominalAmountExceedRange.2000.351
    Then Exposure Managerment - EM booking message should be warning.message.bookingNotMeetRequiredAmount.Expected.6500.49.Actual.1000.13
    When quick link - change bookings
    And Edit booking - bulk booking IMRecallEventAndVMCallEvent.settlementDate.oneDayAfterToday
    Then EM Bulk booking should be IMRecallEvent.imAssetAvailable.6500.49.VMCallEvent.vmAssetAvailable.7500.73
    When Edit booking - bulk booking imRecallEvent.Recall.imNotional.6500.4951.vmCallEvent.Recall.vmNotional.7500.735
    And quick link - save
    Then Exposure Managerment - EM booking message should be error.message.vmNominalAmountExceedRange.7500.726
    Then Exposure Managerment - EM booking message should be error.message.imNominalAmountExceedRange.6500.49
    When quick link - change bookings
    And Edit booking - bulk booking imRecallEvent.Recall.imNotional.6500.49.vmCallEvent.Recall.vmNotional.7500.73
    And quick link - save
    Then Exposure Management - event should be IMRecall.agreementRequirement.6500.49.CallStatusBecomeCompleted,VMCall.agreementRequirement.7500.73.CallStatusBecomeCompleted

      @JaneZhang @2012.2 @F4031#F6823 @AssetHoldings @T21734
  Scenario: Verify partial bookings logic correct in bulk booking for settlement level Transport in FCM multi model agreement TSA Check
    When search agreement search.agreement.by.agreement.description
    And view completed agreement statement agreement.search.result
    And quick link - agreement exposure management
    When Exposure Management - expand events allEventForFCM
    Then Exposure Management - event should be VMCall.net.agreementRequirement.1167180.77,VMDelivery.m2.agreementRequirement.222468.44,VMCall.m1.agreementRequirement.1000864.27
    Then Exposure Management - event should be IMDelivery.net.agreementRequirement.375197.85,IMRecall.m2.agreementRequirement.624802.15,IMReturn.m2.agreementRequirement.1000000,IMNoCall.m1.agreementRequirement.0.00
##    When Exposure Management - set event VMCall.net.agreementRequirement.1167180.77 value to VMCall.net.CallStatusChangeToMarginRequestIssued.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - set event VMDelivery.m2.agreementRequirement.222468.44 value to VMDelivery.m2.CallStatusChangeToMarginRequestConfirmed.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - set event VMCall.m1.agreementRequirement.1000864.27 value to VMCall.m1.CallStatusChangeToMarginRequestIssued.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - set event IMDelivery.net.agreementRequirement.375197.85 value to IMDelivery.net.CallStatusChangeToMarginRequestConfirmed.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - set event IMRecall.m2.agreementRequirement.624802.15 value to IMRecall.m2.CallStatusChangeToMarginRequestIssued.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - set event IMReturn.m2.agreementRequirement.1000000 value to IMReturn.m2.CallStatusChangeToMarginRequestConfirmed.counterpartyAmountChangetoSameAsReqirementAmount
    And Exposure Management - save changes
    When Exposure Management - expand events allEventForFCM
    And Exposure Management - tick events VMCall.net.agreementRequirement.1167180.77,VMDelivery.m2.agreementRequirement.222468.44,VMCall.m1.agreementRequirement.1000864.27,IMDelivery.net.agreementRequirement.375197.85,IMRecall.m2.agreementRequirement.624802.15,IMReturn.m2.agreementRequirement.1000000,IMNoCall.m1.agreementRequirement.0.00
    When Exposure Management - go to bulk booking page
    And Edit booking - bulk booking m1.vmCall.cash.usd.apply.m2.imReturn.cash.usd.apply
    And Edit booking - bulk booking m2.imRecall.cash.eur.apply
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warning.message.SomeOfTheMultiModelBookingCannotBeMade
    Then Exposure Managerment - EM booking message should be error.message.CapTSANotCorrect
    When quick link - change bookings
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.SomeOfTheMultiModelBookingCannotBeMade
    Then Exposure Managerment - EM booking message should be error.message.CapTSANotCorrect


      @JaneZhang @2012.2 @F492 @AssetHoldings @T10958
  Scenario: Verify that fail to make delivery bookings that exceeds the required amount without privileges for an umbrella agreement
    When switch user to userWithoutAssetExcessbookingPrivilege
    And Go to agreement agr10958.unmbrella.MultiFund statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events allEventForFCM
    Then Exposure Management - event should be DeliveryExcess.agreementRequirement.2m,Delivery.agreementRequirement.2m
    When Exposure Management - tick events DeliveryExcess.agreementRequirement.2m,Delivery.agreementRequirement.2m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking deliveryBooking1.umAgr.bond.usd
    And Edit booking - bulk booking deliveryBooking1.fundAgr.notional.700000
    And Edit booking - bulk booking deliveryBooking1.umAgr.bond.usd.clickPlusButton
    And Edit booking - bulk booking deliveryBooking2.umAgr.bond.usd
    And Edit booking - bulk booking deliveryBooking2.fundAgr.notional.300000
    And quick link - validate
    Then Exposure Managerment - EM booking message should be warning.message.bookingDoesNotMeetRequiredAmount
    When quick link - change bookings
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.bookingDoesNotMeetRequiredAmount
    When Exposure Management - override warning overrideWarning
    And quick link - save
    When Go to agreement agr10958.unmbrella.MultiFund statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    Then asset holding detail should be asset.booking.summary.bond.usd.nominal.0.7m.delivery
    And click back button to previous page
    And view asset type asset.type.bond.USD agreement asset Bond Page
    Then asset holding detail should be asset.booking.summary.bond.usd.nominal.0.3m.delivery

    When Go to agreement agr10958.unmbrella.MultiFund statement page by URL
    And quick link - agreement exposure management
    When Exposure Management - expand events allEventForFCM
    And Exposure Management - tick events DeliveryExcess.agreementRequirement.2m,Delivery.agreementRequirement.2m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking deliveryBooking3.umAgr.bond.usd
    And Edit booking - bulk booking deliveryBooking3.fundAgr.notional.500000
    And Edit booking - bulk booking deliveryBooking3.umAgr.bond.usd.clickPlusButton
    And Edit booking - bulk booking deliveryBooking4.umAgr.bond.usd
    And Edit booking - bulk booking deliveryBooking4.fundAgr.notional.800000
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.bookingExceedRequiredAmount

      @JaneZhang @2011.2 @F1257 @AssetHoldings @T16065
  Scenario: Verify SSI info can be displayed correctly in new booking after change counterparty Cash Statement for Call event
    When navigate to collateral static data settlement data page
    And search collateral static data settlement data agr16065.searchAgrID
    And add collateral static data settlement data agr16065.addSettlementData
    And approve collateral static data settlement data agr16065.approveSettlementData
    When Go to agreement agr16065 statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr16065.couterparty1 to agr16065.couterparty2
    And navigate to collateral static data settlement data page
    And search collateral static data settlement data agr16065.searchAgrID
    And approve collateral static data settlement data agr16065.approveSettlementData
    When Go to agreement agr16065 statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call booking - statement booking cash.usd.selectNewAddedPaymentInstructions
    Then call booking in booking editor page should be cash.usd.selectNewAddedPaymentInstructionsInfo

      @JaneZhang @2012.2 @F4031 @AssetHoldings @T21583
  Scenario: Verify Eligibility Check and booking correct by TSA Booking for multi model fcm agreement
    When Go to agreement agr21583.multiModel.fcm statement page by URL
    And select model m1
    And edit asset summary info
    Then asset holding summary should be addedAssetTypeAllPresent
    And view asset type asset.type.CASH.EUR agreement asset Cash Movements Page
    And add Cash Component booking - statement booking Booking1.CapPay.TSA.1m
    And open cash movement editor page Booking1.CapPay.TSA.1m.open
    Then cash movement detail should be Booking1.CapPay.TSA.groupSettlementIdIsEmpty
    When asset booking edit - click cancel button
    And add Cash Component booking - statement booking Booking2.CapPay.TSA.2m
    And open cash movement editor page Booking2.CapPay.TSA.2m.open
    Then cash movement detail should be Booking2.CapPay.TSA.groupSettlementIdIsEmpty
    When asset booking edit - click cancel button
    And click back button to previous page
    And click back button to previous page
    And view asset type asset.type.CASH.GBP agreement asset Cash Movements Page
    And add Cash Component booking - statement booking Booking3.CapPay.TSA.3m
    And open cash movement editor page Booking3.CapPay.TSA.3m.open
    Then cash movement detail should be Booking3.CapPay.TSA.groupSettlementIdIsEmpty




  @JaneZhang @2011.2 @F491 @AssetHoldings @T14591
  Scenario Outline: Verify that Booking Type can automatically set to VM when only VM Notional is set in booking page of Not Net agreement
    When Go to agreement agr14591.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events VMDelivery.event
    And add return bookings - group booking <AddBookingButNotSave>
    Then return booking in booking editor page should be <CheckBookingType.net>
    When edit return booking <AddBooking.vm.100>
    And view asset type <asset.type> agreement asset <asset> Page
    And open group asset booking editor page <booking.vm.100.open>
    Then return booking in booking editor page should be <CheckBookingType.vm>
    When edit return booking <changeBookingToAuthorised>
    And view asset type <asset.type> agreement asset <asset> Page
    And open group asset booking editor page <booking.vm.100.open>
    Then call booking in booking editor page should be <CheckBookingType.vm>

    Examples:
      | AddBookingButNotSave | CheckBookingType.net                | AddBooking.vm.100          |  booking.vm.100.open             | CheckBookingType.vm                | changeBookingToAuthorised             | asset.type | asset |
      | cash.usd.booking1    | cash.usd.booking1.bookingType.net   | cash.usd.booking1.vm.100   | cash.usd.booking1.vm.100.open   | cash.usd.booking1.bookingType.vm   | cash.usd.booking1.vm.100.Authorised   | asset.type.CASH.USD | CASH |
      | bond.usd.booking1    | bond.usd.booking1.bookingType.net   | bond.usd.booking1.vm.100   |  bond.usd.booking1.vm.100.open   | bond.usd.booking1.bookingType.vm   | bond.usd.booking1.vm.100.Authorised   |  asset.type.bond.USD | Bond |
      | equity.usd.booking1  | equity.usd.booking1.bookingType.net | equity.usd.booking1.vm.100 |  equity.usd.booking1.vm.100.open | equity.usd.booking1.bookingType.vm | equity.usd.booking1.vm.100.Authorised |  asset.type.equity.USD | Equity |

     @JaneZhang @2011.1 @AssetHoldings @T13974
  Scenario Outline: Verify Haircut Rules basing on Type when Adding Call Security booking Applicable to Both Parties on net agreement
    When navigate to security search page
    And add securities <bondinstrument>
    And search security <bondSecuritySearchAmended>
    And approve security <bondSecuritySearchWillBeApproved>
    When Go to agreement agr13974.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And add call booking - statement booking <callBooking.nominalAmount.1m>
    Then asset holding detail should be <callBooking.Bond.usd.checkHaircut>

       Examples:
         | callBooking.nominalAmount.1m                                                                 | callBooking.Bond.usd.checkHaircut | bondinstrument                                                                       | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking2.Bond.usd.maturityDate.moreThanTplus5Years.SandPRating.A-.MOODYSRating.Baa1      | callBooking2.Bond.usd.haircut.40  | bondinstrument.maturityDate.moreThanTplus5Years.SandPRating.A-.MOODYSRating.Baa1     | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking3.Bond.usd.maturityDate.lessThanTplus5Years.SandPRating.BBBplus.MOODYSRating.Caa1 | callBooking3.Bond.usd.haircut.20  | bondinstrument.lessThanTplus5Years.SandPRating.BBBplus.MOODYSRating.Caa1             | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking4.Bond.usd.maturityDate.moreThanTplus5Years.SandPRating.B-.MOODYSRating.Caa1      | callBooking4.Bond.usd.haircut.10  | bondinstrument.maturityDate.moreThanTplus5Years.SandPRating.B-.MOODYSRating.Caa1     | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking5.Bond.usd.maturityDate.lessThanTplus5Years.SandPRating.CCCplus.MOODYSRating.Aaa  | callBooking5.Bond.usd.haircut.20  | bondinstrument.maturityDate.lessThanTplus5Years.SandPRating.CCCplus.MOODYSRating.Aaa | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking6.Bond.usd.maturityDate.moreThanTplus5Years.SandPRating.NR.MOODYSRating.Aaa       | callBooking6.Bond.usd.haircut.80  | bondinstrument.maturityDate.moreThanTplus5Years.SandPRating.NR.MOODYSRating.Aaa      | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |
         | callBooking1.Bond.usd.maturityDate.lessThanTplus5Years.SandPRating.AAA.MOODYSRating.Baa1     | callBooking1.Bond.usd.haircut.50  | bondinstrument.maturityDate.lessThanTplus5Years.SandPRating.AAA.MOODYSRating.Baa1    | bondSecuritySearchAmended | bondSecuritySearchWillBeApproved |

     @JaneZhang @14.1.0 @F10796 @AssetHoldings @T32119
  Scenario: Verify the rounding rule for max RecallReturn check for return booking when make instrument booking
    When Go to agreement agr32119.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call booking - statement booking booking1.cash.usd.call.vm.1000.1150.im.1000.1251.settlementDate.dayBeforeT,booking2.cash.usd.call.vm.2000.1250.im.2000.1150.settlementDate.t,booking3.cash.usd.call.vm.3000.1250.im.3000.1250.settlementDate.dayAfterT
    And quick link - view statement
    And approve agreement agr32119
    And quick link - agreement exposure management
    Then Exposure Management - event should be return.agreementRequirement.12000.73
    When Exposure Management - tick events return.agreementRequirement.12000.73
    And add return booking - instrument booking goToBookingEditorPageWithoutSave
    Then return booking in booking editor page should be MaxVMReturnHolding.6000.36.MaxVMReturnAvailable.3000.24.MaxIMReturnHolding.6000.37.MaxIMReturnAvailable.3000.24
    When edit return booking AddBooking1.vm.3000.2451.im.3000.2451
    Then return booking in booking editor page should be NominalAmountExceedRange.6000.48.VmExceedRange.3000.24.ImExceedRange.3000.24
    When edit return booking EditBooking1.settlementDate.t-1
    Then return booking in booking editor page should be MaxVMReturnHolding.6000.36.MaxVMReturnAvailable.1000.12.MaxIMReturnHolding.6000.37.MaxIMReturnAvailable.1000.13
    When edit return booking EditBooking1.vm.1000.1251.im.1000.1350
    Then return booking in booking editor page should be NominalAmountExceedRange.2000.25.VmExceedRange.1000.12.ImExceedRange.1000.13
    When edit return booking EditBooking1.settlementDate.tPlus1
    Then return booking in booking editor page should be MaxVMReturnHolding.6000.36.MaxVMReturnAvailable.6000.36.MaxIMReturnHolding.6000.37.MaxIMReturnAvailable.6000.37
    When edit return booking EditBooking1.vm.6000.3651.im.6000.3750
    Then return booking in booking editor page should be NominalAmountExceedRange.12000.73.VmExceedRange.6000.36.ImExceedRange.6000.37
    When edit return booking EditBooking1.vm.6000.3650.im.6000.3651
    Then Exposure Management - event should be event.return.callStatus.completed


     @JaneZhang @14.1.0 @F10575 @InstrumentBooking @T32124 @Reviewd
  Scenario: Verify that Dependency Check logic on Change the Referenced Asset Category in Security works Net agreement
    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    And approve security instrument.bond.usd.searchResult
    And Go to agreement agr32124.net.templateNotApply.onlyTickPricipleForInstrument statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking1.bond.USD.call.1m
    Then call booking in booking editor page should be booking1.bond.USD.call.category.B
    When Go to agreement agr32124.net.templateApply.onlyTickCounterpartyForInstrument statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking2.bond.USD.delivery.1m
    Then call booking in booking editor page should be booking2.bond.USD.delivery.category.B

    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.A
    And approve security instrument.bond.usd.searchResult
    When Go to agreement agr32124.3 statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr32124.templateNotApply.3 to agr32124.templateApply.3
    When Go to agreement agr32124.4 statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr32124.templateNotApply.4 to agr32124.templateApply.4

    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    Then security instrument.bond.usd.category.B.edit should be errorMsg.agreementNotEligibleForAssetCategoryB
    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32124
    And edit eligibility rules template ERT.categoryB.bond.usd.selectNullParty to ERT.categoryB.bond.usd.selectPriciple
    And search eligibility rules template ERT.32124
    And eligibility rules template - approve ERT.32124.approve
    And navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    Then security instrument.bond.usd.category.B.edit should be errorMsg.agreementNotEligibleForAssetCategoryB

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32124
    And edit eligibility rules template ERT.categoryB.bond.usd.selectPriciple.original to ERT.categoryB.bond.usd.selectCouterparty
    And search eligibility rules template ERT.32124
    And eligibility rules template - approve ERT.32124.approve
    And navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    And approve security instrument.bond.usd.searchResult
    And Go to agreement agr32124.3 statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking3.bond.USD.delivery.1m.open
    Then call booking in booking editor page should be booking3.bond.USD.delivery.category.B
    When Go to agreement agr32124.4 statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking4.bond.USD.delivery.1m.open
    Then call booking in booking editor page should be booking4.bond.USD.delivery.category.B

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32124
    And edit eligibility rules template ERT.categoryA.bond.usd.selectC to ERT.categoryA.bond.usd.selectPandC
    And search eligibility rules template ERT.32124
    And eligibility rules template - approve ERT.32124.approve
    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.A
    Then security search result should be instrument.bond.usd.amended

  @JaneZhang @14.1.0 @F10575 @InstrumentBooking @T32126 @Reviewd
  Scenario: Verify that Dependency Check logic on Change the Referenced Asset Category in Security works notNet agreement
    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    And approve security instrument.bond.usd.searchResult
    And Go to agreement agr32126.notnet.templateNotApply.onlyTickPVForInstrument statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking1.bond.USD.call.1m
    Then call booking in booking editor page should be booking1.bond.USD.call.category.B
    When Go to agreement agr32126.notnet.templateApply.onlyTickCVIForInstrument statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking2.bond.USD.delivery.1m
    Then call booking in booking editor page should be booking2.bond.USD.delivery.category.B

    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.A
    And approve security instrument.bond.usd.searchResult
    When Go to agreement agr32126.3 statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr32126.templateNotApply.3 to agr32126.templateApply.3
    When Go to agreement agr32126.4 statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr32126.templateNotApply.4 to agr32126.templateApply.4 in collateral tab

    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    Then security instrument.bond.usd.category.B.edit should be errorMsg.agreementNotEligibleForAssetCategoryB
    And navigate to eligibility rules template page
    And search eligibility rules template ERT.32126
    And edit eligibility rules template ERT.categoryB.bond.usd.selectNullParty to ERT.categoryB.bond.usd.selectPV
    And search eligibility rules template ERT.32126
    And eligibility rules template - approve ERT.32126.approve
    And navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    Then security instrument.bond.usd.category.B.edit should be errorMsg.agreementNotEligibleForAssetCategoryB

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32126
    And edit eligibility rules template ERT.categoryB.bond.usd.selectPV.original to ERT.categoryB.bond.usd.selectCI
    And search eligibility rules template ERT.32126
    And eligibility rules template - approve ERT.32126.approve
    And navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    Then security instrument.bond.usd.category.B.edit should be errorMsg.agreementNotEligibleForAssetCategoryB

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32126
    And edit eligibility rules template ERT.categoryB.bond.usd.selectCI.original to ERT.categoryB.bond.usd.selectCV
    And search eligibility rules template ERT.32126
    And eligibility rules template - approve ERT.32126.approve
    And navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.B
    And approve security instrument.bond.usd.searchResult
    And Go to agreement agr32126.3 statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking3.bond.USD.delivery.1m.open
    Then call booking in booking editor page should be booking3.bond.USD.delivery.category.B
    When Go to agreement agr32126.4 statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page booking4.bond.USD.delivery.1m.open
    Then call booking in booking editor page should be booking4.bond.USD.delivery.category.B

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.32126
    And edit eligibility rules template ERT.categoryA.bond.usd.selectCV to ERT.categoryA.bond.usd.selectPandC
    And search eligibility rules template ERT.32126
    And eligibility rules template - approve ERT.32126.approve
    When navigate to security search page
    And search security instrument.bond.usd
    And edit security instrument.bond.usd.searchResult to instrument.bond.usd.category.A
    Then security search result should be instrument.bond.usd.amended

  @JaneZhang @2011.2 @F469 @AutoBooking @T12186_1 @Reviewd
  Scenario: Verify auto booking can be made by auto booking button and task for Normal NotNet agreement Margin IM VM Recall event Use Cpty Amount
    When Go to agreement agr12186.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.recall.50000,im.recall.50000
    When Exposure Management - tick events vm.recall.50000,im.recall.50000
    And Exposure Management - auto send letter
    Then Exposure Management - EM message should be im.vm.Recall.autoEmailSuccess
    When Exposure Management - margin filters - search margin issued agr12186.issued
    And Exposure Management - set event vmRecall.CpyAmount value to vmRecall.CpyAmount.49900
    And Exposure Management - set event imRecall.CpyAmount value to imRecall.CpyAmount.48800
    And Exposure Management - save changes
    And Exposure Management - tick events vm.recall.50000,im.recall.50000
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be vm.im.Recall.autoBookingSuccess

    When Go to agreement agr12186.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.recall.autoBook.systemDraft.49900
    And asset holding detail should be im.recall.autoBook.systemDraft.48800

  @JaneZhang @2011.2 @F469 @AutoBooking @T13171_1 @Reviewd
  Scenario: Verify auto booking cannot be made by auto booking button and task for Normal Not-Net agreement IM Recall IM Call event when no rule set for IM
    When Go to agreement agr13171.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.call.500000,vm.recall.2000000
    When Exposure Management - tick events vm.call.500000,vm.recall.2000000
    And Exposure Management - auto send letter
    Then Exposure Management - EM message should be vm.Recall.vm.call.autoEmailSuccess
    When Exposure Management - margin filters - search margin issued agr13171.issued
    And Exposure Management - tick events vm.call.500000,vm.recall.2000000
    And Exposure Management - set event vmCall.CpyAmount value to vmCall.CpyAmount.400000
    And Exposure Management - set event vmRecall.CpyAmount value to vmRecall.CpyAmount.1900000
    And Exposure Management - save changes
    And Exposure Management - tick events vm.call.500000,vm.recall.2000000
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be vm.Recall.vm.call.autoBookingUnsuccess

    When Go to agreement agr13171.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.call.autoBook.pending.400000.notadded
    And asset holding detail should be vm.recall.autoBook.pending.1900000.notadded

    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoBookCashforAgr13171
    And run task scheduler autoBookCashforAgr13171
    Then task scheduler messaging should be autoBookCashforAgr13171.success
    When Go to agreement agr13171.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.call.autoBook.pending.400000.notadded
    And asset holding detail should be vm.recall.autoBook.pending.1900000.notadded

  @JaneZhang @2011.2 @F469 @AutoBooking @T15394_1 @Reviewd
  Scenario: Verify VM and IM auto booking using own Tolerance Amount for Normal Not Net agreement Margin IM and VM Call event
    When Go to agreement agr15394.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.3000000
    When Exposure Management - tick events call.3000000
    And Exposure Management - auto send letter
    Then Exposure Management - EM message should be call.autoEmailSuccess
    When Exposure Management - margin filters - search margin issued agr15394.issued
    And Exposure Management - tick events call.3000000
    And Exposure Management - set event Call.CpyAmount value to Call.CpyAmount.3500000
    And Exposure Management - save changes
    And Exposure Management - tick events call.3000000
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be vm.call.autoBookingUnsuccess.im.call.autoBookingSuccess
    When Go to agreement agr15394.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be vm.call.autoBook.pending.notadded
    And asset holding detail should be im.recall.autoBook.pending.1000000.added

  @JaneZhang @2011.2 @F469 @AutoBooking @T12183 @Reviewd @wip
  Scenario: Verify auto booking can be made by auto booking button and task for CCP Net agreement Delivery event Use Agreement Amount
    When Go to agreement agr12183.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.1000000
    When Exposure Management - tick events delivery.1000000
    And Exposure Management - manual send margin delivery letter createDeliveryLetter
    When Exposure Management - margin filters - search margin confirmed agr12183.confirmed
    And Exposure Management - tick events delivery.1000000
    And Exposure Management - set event delivery.CpyAmount value to delivery.CpyAmount.1100000
    And Exposure Management - save changes
    And Exposure Management - tick events delivery.1000000
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be delivery.autoBookingSuccess
    When Go to agreement agr12183.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be delivery.autoBook.pendingSettlement.1000000.added
    When open asset booking editor page delivery.autoBook.pendingSettlement.1000000.open
    Then delivery booking in booking editor page should be booking.delivery.1m.autoAdd
    When edit delivery booking booking.delivery.1m.cancelled

    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler resetEEforAgr12183
    And run task scheduler resetEEforAgr12183
    Then task scheduler messaging should be resetEEforAgr12183.success

    When Go to agreement agr12183.notnet statement page by URL
    And approve agreement agr12183
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.1000000.pendingReview
    When Exposure Management - tick events delivery.1000000.pendingReview
    And Exposure Management - manual send margin delivery letter createDeliveryLetter
    When Exposure Management - margin filters - search margin confirmed agr12183.confirmed.2
    And Exposure Management - set event delivery.CpyAmount.confirmed value to delivery.CpyAmount.1100000.confirmed
    And Exposure Management - save changes

    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler autoBookCashforAgr12183
    And run task scheduler autoBookCashforAgr12183
    Then task scheduler messaging should be autoBookCashforAgr12183.success
    When Go to agreement agr12183.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    And open asset booking editor page delivery.autoBook.pendingSettlement.1000000.open
    Then delivery booking in booking editor page should be booking.delivery.1m.autoAddByTask

  @JaneZhang @2012.2 @2012.3 @F4031#F1260 @AutoBooking @T21664 @Reviewd
  Scenario: Verify net functionality logic with Mutli Model Model agreement Auto Booking
    When Go to agreement agr21664.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events net.noCall
    Then Exposure Management - event should be net.noCall,m2.Recall.1m,m1.Return.1m
    When Exposure Management - set event Recall.pendingReview value to Recall.marginRequestIssued.CpyAmount.1000000
    And Exposure Management - set event Return.pendingReview value to Return.marginRequestConfirmed.CpyAmount.1000000
    And Exposure Management - save changes
    And Exposure Management - expand events net.noCall
    And Exposure Management - tick events net.noCall
    Then Exposure Management - AutoBookCash action should be not displayed
    And Exposure Management - tick events m2.Recall.1m,m1.Return.1m
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be Recall.autoBookingUnsuccess.Return.autoBookingSuccess

    When Go to agreement agr21664.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.cash.USD agreement asset CASH Page
    Then asset holding detail should be recall.autoBook.pending.1000000.added
    And asset holding detail should be return.autoBook.pending.1000000.added
    When open asset booking editor page recall.autoBook.pending.1000000.added
    Then delivery booking in booking editor page should be recall.autoBook.pending.1000000.added.NoGroupSettlementID
    When asset booking edit - click cancel button
    When open asset booking editor page return.autoBook.pending.1000000.added
    Then delivery booking in booking editor page should be return.autoBook.pending.1000000.added.NoGroupSettlementID
    When asset booking edit - click cancel button

    When navigate to settlement status page
    And settlement status - search settlement status settlementStatusSearch.agr21664
    And settlement status - show asset settlementStatusSearchResult.cash.eur details
    And settlement status - net view selectNet
    Then settlement status - Netted table should not be displayed

  @JaneZhang @13.1.0 @F7883 @AutoBooking @T28824 @Reviewd
  Scenario: Verify succeed to make Auto booking of Fee Event for ETF agreement by sending Statment notification Letter
    When Go to agreement agr28824.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events agr28824.event
    Then Exposure Management - event should be VMDelivery.agreementRequirement.611550.80,VMCall.agreementRequirement.1388449.21,VMDelivery.agreementRequirement.2000000.01
    Then Exposure Management - event should be cashDelivery.agreementRequirement.1000000.01,cashDelivery.agreementRequirement.2000000.01,cashCall.agreementRequirement.1000000.00
    Then Exposure Management - event should be FeeDelivery.agreementRequirement.1036283.91,FeeDelivery.agreementRequirement.2000000.00,FeeCall.agreementRequirement.963716.09
    When Exposure Management - tick events FeeCall.agreementRequirement.963716.09
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter

    When Go to agreement agr28824.etf statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset Cash Movements Page
    Then cash movement summary should be cash.eur.pay.fee.autobook.1440456
    Then cash movement summary should be cash.eur.receive.fee.autobook.694095.31

  @JaneZhang @13.1.0 @F7883 @AutoBooking @28825 @Reviewd
  Scenario: Verifiy succeed to make Auto booking of Cash Component Event for ETF agreement by sending Statment notification Letter
    When Go to agreement agr28825.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events agr28825.event
    Then Exposure Management - event should be VMDelivery.agreementRequirement.611550.80,VMCall.agreementRequirement.1388449.21,VMDelivery.agreementRequirement.2000000.01
    Then Exposure Management - event should be cashDelivery.agreementRequirement.1036283.91,cashDelivery.agreementRequirement.2000000.00,cashCall.agreementRequirement.963716.09
    Then Exposure Management - event should be FeeDelivery.agreementRequirement.1000000.01,FeeDelivery.agreementRequirement.2000000.01,FeeCall.agreementRequirement.1000000.00
    When Exposure Management - tick events cashDelivery.agreementRequirement.2000000.00,cashCall.agreementRequirement.963716.09
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter

    When Go to agreement agr28825.etf statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    Then cash movement summary should be cash.usd.pay.cash.autobook.2000000
    Then cash movement summary should be cash.usd.receive.cash.autobook.963716.09

  @JaneZhang @13.1.0 @F7883 @AutoBooking @28826 @Reviewd
  Scenario: Verifiy succeed to make Auto booking of VM Event for ETF agreement by sending Statment notification Letter
    When Go to agreement agr28826.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events agr28826.event
    Then Exposure Management - event should be VMDelivery.agreementRequirement.1036283.91,VMCall.agreementRequirement.963716.09,VMDelivery.agreementRequirement.2000000.00
    Then Exposure Management - event should be cashDelivery.agreementRequirement.611550.80,cashDelivery.agreementRequirement.2000000.01,cashCall.agreementRequirement.1388449.21
    Then Exposure Management - event should be FeeDelivery.agreementRequirement.1000000.01,FeeDelivery.agreementRequirement.2000000.01,FeeCall.agreementRequirement.1000000.00
    When Exposure Management - tick events VMCall.agreementRequirement.963716.09,VMDelivery.agreementRequirement.2000000.00
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter

    When Go to agreement agr28826.etf statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    Then asset holding detail should be cash.eur.delivery.autobook.1440456
    And open asset booking editor page cash.eur.delivery.autobook.1440456
    Then booking details should be cash.eur.delivery.autobook.1440456.net
    And edit delivery booking changeStatusToCancelled

    And quick link - agreement review
    And edit ETF agreement autorule.vm.delivery to autorule.vm.call in collateral tab
    And view statement
    And approve agreement agr28826
    And quick link - agreement exposure management
    And Exposure Management - expand events agr28826.event
    And Exposure Management - tick events VMCall.agreementRequirement.963716.09
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter

    When Go to agreement agr28826.etf statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    Then asset holding detail should be cash.eur.call.autobook.694095.31
    And open asset booking editor page cash.eur.call.autobook.694095.31
    Then booking details should be cash.eur.call.autobook.694095.31.net

  @JaneZhang @2011.2 @F469#499 @AutoBooking @T15037
  Scenario: Verify that TSA Credit auto booking can be made by email Statement Notification letter for FCM Statement
    When Go to agreement agr15037.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events VMCall.agreementRequirement.999000
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter

    When Go to agreement agr15037.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    Then cash movement summary should be cash.usd.capitalisePay.tsa.autobook.1000
    When open cash movement editor page cash.usd.capitalisePay.tsa.autobook.1000
    Then cash movement detail should be cash.usd.capitalisePay.tsa.autobook.1000.tickExcludeFromStatementCalculation

  @JaneZhang @2011.2 @F469#499 @AutoBooking @T12414
  Scenario: Verify VM IM TSA auto booking made by auto email margin letter in EM for CCP NotNet TSA agreement
    When Go to agreement agr12414.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - tick events imRecall.50000,vmCall.51000
    And Exposure Management - auto send letter
    Then Exposure Management - EM message should be vm.im.autoBookingSuccess

    When Go to agreement agr12414.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be recall.vm.autoBook.systemDraft.51000.added
    Then asset holding detail should be recall.im.autoBook.pending.50000.added
    When click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    Then cash movement summary should be cash.usd.capitaliseReceive.tsa.autobook.pending.1000

  @JaneZhang @2011.2 @F469 @AutoBooking @T13993
  Scenario: Verify Cpty Amount is set after letter sent and auto booked for Normal NotNet agreement for IM Recall event Use Agreement Amount
    When Go to agreement agr13993.fcm statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.delivery.1m
    When Exposure Management - tick events vm.delivery.1m
    And Exposure Management - manual send margin delivery letter createDeliveryLetter

    When Go to agreement agr13993.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be delivery.vm.autoBook.pending.1m.added
    When quick link - agreement exposure management
    Then Exposure Management - event should be vm.delivery.1m.completed.counterpartyAmount.-1m

  @TalatRani @2012.2 @F3641 @CheckAmount @T21274
  Scenario: Verify Amount in [AGREEMENT OVERVIEW] section are correct for multi model agreement Net Call
    And Go to agreement agr21274-id statement page by URL
    And select model m1
    And edit asset summary info
    And view asset type cash.USD agreement asset CASH Page
    And Click on Add button on cash  summary
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be AgreementOverviewValues
    When edit call booking CallBooking1Cash.requiredAmount1.2M
    Then delivery booking in booking editor page should be CallBooking1Cash.nominalAmount1.2M

    When Go to agreement agr21274-id statement page by URL
    And select model m1
    And edit asset summary info
    And view asset type qtp_USD agreement asset Equity Page
    And add call booking - statement booking qtp-Equity-Usd.search
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be EquityAgreementOverviewValues
    When edit call booking CallBooking1Cash.EquityRequiredAmount1.2M
    Then delivery booking in booking editor page should be CallBooking1Cash.EquityNominalAmount1.2M

    When Go to agreement agr21274-id statement page by URL
    And select model m1
    And edit asset summary info
    And view asset type qtp_bond agreement asset Bond Page
    And add call booking - statement booking qtp_bond_Instrument
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be BondAgreementOverviewValues
    When edit call booking CallBooking1Cash.BondRequiredAmount1.2M
    Then delivery booking in booking editor page should be CallBooking1Cash.BondNominalAmount1.2M

    When Go to agreement agr21274-id statement page by URL
    And select model m2
    And edit asset summary info
    And view asset type qtp_cash agreement asset CASH Page
    And Click on Add button on cash  summary
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be AgreementOverviewValues.m2
    When edit call booking CallBooking1Cash.requiredAmount1.2M.m2
    Then delivery booking in booking editor page should be CallBooking1Cash.nominalAmount1.2M.m2

    When Go to agreement agr21274-id statement page by URL
    And select model m2
    And edit asset summary info
    And view asset type qtp_equity agreement asset Equity Page
    And add call booking - statement booking qtp-Equity-Eur.search
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be EquityAgreementOverviewValues.m2
    When edit call booking CallBooking1Cash.EquityRequiredAmount1.2M.m2
    Then delivery booking in booking editor page should be CallBooking1Cash.EquityNominalAmount1.2M.m2


    When Go to agreement agr21274-id statement page by URL
    And select model m2
    And edit asset summary info
    And view asset type qtp-bond-Eur2.search agreement asset Bond Page
    And add call booking - statement booking qtp_bond_Eur2
    And recalculate agreement overview on Cash Editor page
    Then delivery booking in booking editor page should be BondAgreementOverviewValues.Eur2
    When edit call booking CallBooking1Cash.BondRequiredAmount1.2M.Eur2
    Then delivery booking in booking editor page should be CallBooking1Cash.BondNominalAmount1.2M.Eur2

