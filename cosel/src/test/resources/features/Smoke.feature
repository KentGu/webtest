@Smoke @SmokeTest
Feature: Smoke
  This feature is to validate all the core functions is working after new build

  In order to validate core functions works well
  As a colline user
  I want test following functions

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration
    And have default organisation global preferences

  @BinHu @wip
  Scenario: OTC Smoke Test
#    Add OTC Principal Organisation with branch organisation and related to mainorg
    When add organisation SmokeBranchPrincipal
    And add organisation SmokePrincipal
    Then organisation should be SmokePrincipalDetail
#    Add OTC Counterparty Organisation with branch organisation
    When add organisation SmokeBranchCpty
    And add organisation SmokeCpty
    Then organisation should be SmokeCptyDetail
#    Add Interest Rate and Setup OTC Agreement
    When navigate to interest rates page
    And have interest rates SmokeInterestRate
    And add OTC agreement SmokeOTCAgreement001
#    And change tab to ...
#    Then agreement details should be \S+

    When view statement
    And approve agreement SmokeOTCAgreement001
#    Then agreement details should be SmokeAgreement001DetailsAfterApprove
#    Search agreement with Principal and Counterparty
    And search agreement SmokeAgreementSearch001
#   Then agreement short search result should be SmokeAgreementSearchResult001

    When view completed agreement statement SmokeAgreementSearchResult001

  @BinHu @ignore
  Scenario: Feed Trade
#    Flush feed and Delta feed for Trade
    When navigate to organisation static data page
    And add organisation static data AddSmokeFeedSystem
#    Then organisation static data should be SmokeFeedSystemDetail

    When navigate to collateral static data page
    And add collateral static data AddSmokeProduct
#    Then collateral static data should be SmokeProductDetail

    When add collateral static data AddCODES_SmokeFeedSystem
#    Then collateral static data should be CODES_SmokeFeedSystemDetail

    When add collateral static data AddSmokeProductToCODES_SmokeFeedSystem
#    Then collateral static data should be SmokeProductToCODES_SmokeFeedSystemDetail

    When navigate to organisation global preferences page
    And set organisation global preferences SetSmokeFeedSystem
#    Then organisation global preferences should be SetSmokeFeedSystemDetail

    When add organisation SmokeBranchPrincipalExtCode
#    Then organisation should be SmokeBranchPrincipalExtCodeDetail

    When add organisation SmokePrincipalExtCode
    When add organisation SmokeBranchCptyExtCode
#    Then organisation should be SmokeBranchCptyExtCodeDetail

    When add organisation SmokeCptyExtCode
    And have OTC agreement SmokeOTCAgreement002
    And feed flush trades SmokeTrade001 by xml
#    Then feed status should be Trade001status

    When feed delta trades SmokeTrade002 by xml
#    Then feed status should be Trade002status

  @BinHu
  Scenario: Event workflow Completed Call
#     Event workflow - Completed - Call
#    When navigate to edit my preference
#    When edit my preference SmokeDashboardLink
    When navigate to security search page
    And add security SmokeSecurityBond-USD
    And search security SmokeSecurityBond-USDSearch
    Then security SmokeSecurityBond-USDSearchResult should be SmokeSecurityBond-USD
    When cancel instrument

    And approve security SmokeSecurityBond-USDSearchResult
    And add OTC agreement SmokeAgreement003
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trade SmokeTrade003
    And quick link - view statement
    And approve agreement SmokeAgreement003
#     And navigate to workflow dashboard page
#     And workflow dashboard - show calls and recalls
    And quick link - agreement exposure management
#     And Exposure Management - all filters - search dynamic filter SmokeEMPrinCptySearchCR
    Then Exposure Management - event should be EMResult001


    When Exposure Management - tick event EventCall001
    And Exposure Management - auto send letter
    Then Exposure Management - event should be EMResult002

    When Exposure Management - set event EventCall001 value to EMCounterpartyAmount001
    And Exposure Management - save changes
    And Exposure Management - tick event EventCall001
    And add call booking - group booking EMgroupbooking001
    Then Exposure Management - event should be EventCall001Completed
#     Generate Delivery Event
    And search agreement SmokeAgreementSearch003
#    Then agreement short search result should be SmokeAgreementSearchResult003

    When view completed agreement statement SmokeAgreementSearchResult003
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trade SmokeTrade004
    And quick link - view statement
    And approve agreement SmokeAgreement003StatementStatus
    And quick link - agreement exposure management
    Then Exposure Management - event should be EventDelivery001

    When Exposure Management - set event EventDelivery001 value to EMCounterpartyAmount002
    And Exposure Management - tick event EventDelivery001
    And add delivery booking - bulk booking EMbulkbooking001
    And quick link - save
    Then Exposure Management - event should be EventDelivery001Partial

  @BinHu
  Scenario: Settlement Status
    When navigate to security search page
    And add security SmokeBondInstr001
    And search security SmokeBondInstr001Search
    And approve security SmokeBondInstr001SearchResult
    And add security SmokeEquityInstr001
    And search security SmokeEquityInstr001Search
    And approve security SmokeEquityInstr001SearchResult
    And add OTC agreement SmokeOTCAgreement003
    And view statement
    And edit asset summary info
#    And approve agreement SmokeAgreement001
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking Smokebooking1
    And click back button to previous page
    And view asset type BondUSDType agreement asset Bond Page
    And add delivery bookings - statement booking Smokebooking2
    And add recall bookings - statement booking Smokebooking3
    And click back button to previous page
    And view asset type EquityUSDType agreement asset Equity Page
    And add call bookings - statement booking Smokebooking4
    And add return bookings - statement booking Smokebooking5
#    And click back button to previous page
#    And view asset type BondUSDType agreement asset Bond Page
#    And edit delivery booking EditSmokebooking2Pending to Smokebooking2Authorised
#    And edit delivery booking EditSmokebooking2Authorised to Smokebooking2PendingSettlement
#    And edit delivery booking EditSmokebooking2PendingSettlement to Smokebooking2Outstanding
#    And edit delivery booking EditSmokebooking2Outstanding to Smokebooking2Settled
    And navigate to settlement status page
    And settlement status - search settlement status SettlementSearch001
    Then settlement status - search result should be SettlementSearchResultBond.pending,SettlementSearchResultCash.pending,SettlementSearchResultEquity.pending

    When settlement status - approve all pending in settlement status summary
    Then settlement status - search result should be SettlementSearchResultBond.authorised,SettlementSearchResultCash.authorised,SettlementSearchResultEquity.authorised

    When settlement status - approve all authorised in settlement status summary
    Then settlement status - search result should be SettlementSearchResultBond.pending.settlement,SettlementSearchResultCash.pending.settlement,SettlementSearchResultEquity.pending.settlement

  @BinHu
  Scenario: SmokeInterestRate
    When navigate to interest rates page
    And add Interest Rates SmokeInterestRate
    And add OTC agreements SmokeOTCAgreement004
    And view statement
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking Smokebooking6
    And edit call booking EditSmokebooking6P to Smokebooking6Authorised
    And edit call booking EditSmokebooking6A to Smokebooking6PendingSettlement
    And edit call booking EditSmokebooking6PS to Smokebooking6Settled
    And quick link - view statement
    And approve agreement SmokeAgreement005
    And navigate to interest manager search page
    And Interest Manager - search interest event by InterestSearch001
    And Interest Manager - switch to Pay tab
    Then Interest Manager InterestEvent001 - event should be InterestResult001
    When Interest Manager - view details for event InterestEvent001
    Then interest rates should be InterestDetail001
    When Interest Manager - click Interest Manager button in interest detail page
    And Interest Manager - create letters smoke.interest.letter for event InterestEvent001
    Then Interest Manager InterestEvent001 - event should be InterestResult002
    When Interest Manager - tick interest events InterestEvent001
    And Interest Manager - Apply interest movement InterestEvent002
    Then Interest Manager InterestEvent001 - event should be InterestResult003
    When Interest Manager - view interest movements for event InterestEvent001
    Then cash movement summary should be CashMovementSummary001

  @BinHu @Proxied
  Scenario: Run AHV Report
    When add OTC agreements smoke.agreement.for.ahv
    And view statement
    And edit asset summary info
    And view asset type CashUSDType agreement asset CASH Page
    And add call bookings - statement booking booking.ahv
    And navigate to Asset Holdings and Valuation Report
    And search Asset Holdings and Valuation Report SearchAHVReportFilter
    And run report to ahvReportPath1 as Excel Worksheet
    And run report to ahvReportPath1 as CSV
    And run report to ahvReportPath1 as PDF
    And run report to ahvReportPath1 as XML
    And run report to ahvReportPath1 as GUI
#    And save report result to Colline ahvSaveReport1
#    And export saved report ahvSaveReport1 result to ahvReportPath1 as Excel
#    And export saved report ahvSaveReport1 result to ahvReportPath1 as Excel
#    And delete marked reports ahvSaveReport1

  @BinHu
  Scenario: Umbrella Smoke Test
  Add Umbrella Principal organisation and two Fund Principal organisations under it
    When add organisations SmokeFund1PrincipalOrg
    Then organisation should be SmokeFund1PrincipalOrg

    When add organisations SmokeFund2PrincipalOrg
    Then organisation should be SmokeFund2PrincipalOrg

    When add organisations SmokeUmbrellaPrincipalOrg
    Then organisation should be SmokeUmbrellaPrincipalOrg

#    Add Umbrella Counterparty organisation and two Fund Counterparty organisations under it
    When add organisations SmokeFund1CptyOrg
    Then organisation should be SmokeFund1CptyOrg

    When add organisations SmokeFund2CptyOrg
    Then organisation should be SmokeFund2CptyOrg

    When add organisations SmokeUmbrellaCptyOrg
    Then organisation should be SmokeUmbrellaCptyOrg

#    Create Collateral at Fund agreement
    When add Umbrella agreement caf_Fund1Agreement,caf_Fund2Agreement,caf_UmAgreement

    And view statement
    And select agreement caf_Fund1Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades caf_Fund1Trade001
    And quick link - view statement
    And select agreement caf_Fund2Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades caf_Fund2Trade001
    And quick link - view statement
    And select agreement caf_Fund1Agreement in umbrella list
    And edit asset summary info
    And view asset type BondUSDType agreement asset Bond Page
    And add call booking - statement booking caf_Fund1Booking001
    And quick link - view statement
    And select agreement caf_Fund2Agreement in umbrella list
    And edit asset summary info
    And view asset type BondUSDType agreement asset Bond Page
    And add delivery booking - statement booking caf_Fund2Booking001
    And quick link - view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And edit trade caf_Fund2Trade001Old to Editcaf_Fund2Trade001
    And quick link - view statement
    And select agreement caf_Fund1Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And edit trade caf_Fund1Trade001Old to Editcaf_Fund1Trade001
    And quick link - view statement
    And select agreement caf_UmAgreement in umbrella list
    Then agreement statement should be caf_UmAgreementCallRequirement

    When approve agreement caf_UmAgreementAgreementStatusToPending
    And select agreement caf_Fund1Agreement in umbrella list
    And approve agreement caf_Fund1AgreementStatusApproved
    And select agreement caf_Fund2Agreement in umbrella list
    And approve agreement caf_Fund2AgreementStatusApproved
    And select agreement caf_UmAgreement in umbrella list
    And approve agreement caf_UmAgreementApproved
    And quick link - agreement exposure management
    Then Exposure Management - event should be caf_UmAgreement_EM_RequirementAmount_call

    When Exposure Management - expand events caf_UmAgreement_EM_RequirementAmount_call
    And Exposure Management - tick event caf_Fund1CallEvent
    And Exposure Management - manual send margin call letter caf_Fund1CallEvent.letter

    And Exposure Management - expand events caf_UmAgreement_EM_RequirementAmount_call

    And Exposure Management - tick event caf_Fund2RecallEvent
    And Exposure Management - manual send margin recall letter caf_Fund2RecallEvent.letter

    And Exposure Management - expand events caf_UmAgreement_EM_RequirementAmount_call

    Then Exposure Management - event should be caf_Fund1CallEvent.margin.request.issued
    And Exposure Management - event should be caf_Fund2RecallEvent.margin.request.issued

    When Exposure Management - set event caf_Fund1CallEvent.margin.request.issued value to EventPendingReviewStatus
    And Exposure Management - set event caf_Fund2RecallEvent.margin.request.issued value to EventPendingReviewStatus

    And Exposure Management - tick event caf_UmAgreement_EM_RequirementAmount_call
    And Exposure Management - auto send letter
    Then Exposure Management - event should be caf_UmAgreement_EM_RequirementAmount_call.margin.request.issued
    And Exposure Management - event should be caf_Fund1CallEvent.margin.request.issued
    And Exposure Management - event should be caf_Fund2RecallEvent.margin.request.issued

#    Collateral at Umbrella
    When add Umbrella agreement cau_Fund1Agreement,cau_Fund2Agreement,cau_UmAgreement
    And view statement
    And select agreement cau_Fund1Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades cau_Fund1Trade001
    And quick link - view statement
    And select agreement cau_Fund2Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades cau_Fund2Trade001
    And quick link - view statement
    And select agreement cau_UmAgreement in umbrella list
    And edit asset summary info
#    And click back button to previous page
    And view asset type BondUSDType agreement asset Bond Page
    And add call booking - statement booking cau_Fund1andFund2Booking001
    And quick link - view statement
    And select agreement cau_Fund1Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And edit trade cau_Fund1Trade001Old to Editcau_Fund1Trade001
#    Change Amount trade1 from 0.5m to 1m
    And quick link - view statement
    And select agreement cau_Fund2Agreement in umbrella list
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And edit trade cau_Fund2Trade001 to Editcau_Fund2Trade001
#    Change Amount trade1 from 0.5m to 1m
    And quick link - view statement
    And select agreement cau_UmAgreement in umbrella list
    Then agreement statement should be cau_UmAgreementCallRequirement

    When approve agreement cau_UmAgreementAgreementStatusToPending
    And select agreement cau_Fund1Agreement in umbrella list
    And approve agreement cau_Fund1AgreementStatusApproved
    And select agreement cau_Fund2Agreement in umbrella list
    And approve agreement cau_Fund2AgreementStatusApproved
    And select agreement cau_UmAgreement in umbrella list
    And approve agreement cau_UmAgreementApproved
    And quick link - agreement exposure management
    Then Exposure Management - event should be cau_UmAgreement_EM_RequirementAmount_call_deficit

    When Exposure Management - expand events cau_UmAgreement_EM_RequirementAmount_call_deficit
    And Exposure Management - tick event cau_Fund1CallEvent
    And Exposure Management - manual send margin call letter cau_Fund1CallEvent.letter
    And Exposure Management - tick event cau_Fund2RecallEvent
    And Exposure Management - manual send margin recall letter cau_Fund2RecallEvent.letter
    Then Exposure Management - event should be cau_Fund1CallEvent.margin.request.issued
    And Exposure Management - event should be cau_Fund2RecallEvent.margin.request.issued
    When Exposure Management - set event cau_Fund2RecallEvent value to EventPendingReviewStatus
    And Exposure Management - set event cau_Fund1CallEvent value to EventPendingReviewStatus
    And Exposure Management - tick event cau_UmAgreement_EM_RequirementAmount_call_deficit
    And Exposure Management - auto send letter
    Then Exposure Management - event should be cau_UmAgreement_EM_RequirementAmount_call.margin.request.issued
    And Exposure Management - event should be cau_Fund1CallEvent.margin.request.issued
    And Exposure Management - event should be cau_Fund2RecallEvent.margin.request.issued


  @BinHu
  Scenario: FCM Smoke Test
    When add organisations SmokePrincipal
    And add organisations SmokeCpty
#    And set system preferences EnableGP2
#    And navigate to TSA rule page
#    And add TSA rule SmokeTSARule
#    And navigate to collateral static data page
#    And add SmokeStatementSet to collateral static data scheme StatementSetScheme
    And add FCM agreement SmokeAgreementFCM001
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add FCM trades FCMTrade002
#    And approve agreement SmokeAgreementFCM001
#    And feed delta trades FCMTrade001 by excel
#    And add FCM trades FCMTrade002
#    And feed portfolio tsa FCMPortfolioTSAfeed001
    And search agreement SearchFCMagr001
    And view completed agreement statement SearchFCMagr001Result
#    Then agreement statement should be FCMagr001Statement

    When approve agreement SmokeAgreementFCM001
    And quick link - agreement exposure management
    And Exposure Management - tick events FCMagr001CallEvent
    And Exposure Management - auto send letter
#    Then Exposure Management FCMagr001CallEvent - event call status|action should be \S+

    When Exposure Management - set event FCMagr001CallEvent value to FCMagr001CallEventCounterpartyAmount001
    And Exposure Management - save changes
    And Exposure Management - tick event FCMagr001CallEvent
    And Exposure Management - group booking
    And view asset type CashUSDType agreement asset Cash Movements Page
#    And add call bookings - statement booking FCMagr001CallEventGroupbooking
    And add TSA booking - statement booking FCMagr001CallEventGroupbooking
#    And add call booking - group booking FCMagr001CallEventGroupbooking
#    Then Exposure Management event should be FCMagr001CallEventCompleted

    And search agreement SearchFCMagr001
    And view completed agreement statement SearchFCMagr001Result
    And edit asset summary info
#    And click back button to previous page
    And view asset type CashUSDType agreement asset Cash Movements Page
    And add TSA booking - statement booking TSAbooking001
    And navigate to task scheduler page
#    And edit task scheduler EditTaskResetTotalSettlementAmount
#    And run task scheduler TaskResetTotalSettlementAmount
    And search agreement SearchFCMagr001
    And view completed agreement statement SearchFCMagr001Result
#    Then Then agreement statement should be FCMagr001StatementZero

    When add FCM agreement SmokeAgreementFCM002
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add FCM trades FCMTrade004
#    And feed delta trades FCMTrade003 by excel
#    And feed portfolio tsa FCMPortfolioTSAfeed002
    And search agreement SearchFCMagr002
    And view completed agreement statement SearchFCMagr002Result
#    Then agreement statement should be FCMagr002Statement

    When approve agreement SmokeAgreementFCM002
    And quick link - agreement exposure management
    And Exposure Management - tick events FCMagr002DeliveryEvent
#    And Exposure Management - manual send statement notification letter \S+ letter
#    Then Exposure Management FCMagr001CallEvent - event call status|action should be \S+

    And search agreement SearchFCMagr002
    And view completed agreement statement SearchFCMagr002Result
    And edit asset summary info
#    And click back button to previous page
    And view asset type CashUSDType agreement asset Cash Movements Page
#    Then booking summary should be TSAbooking002

    When quick link - agreement exposure management
    And Exposure Management - tick event FCMagr002DeliveryEvent
    And add call booking - bulk booking FCMagr002DeliveryEventBulkbooking
#    Then Exposure Management event should be FCMagr002DeliveryEventDispute

    When add FCM agreement SmokeAgreementFCM003
    And view statement
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add FCM trades FCMTrade006
#    And approve agreement SmokeAgreementFCM003
#    And feed delta trades FCMTrade005 by excel
#    And add FCM trades FCMTrade006
#    And feed portfolio tsa FCMPortfolioTSAfeed003
    And search agreement SearchFCMagr003
    And view completed agreement statement SearchFCMagr003Result
#    Then agreement statement should be FCMagr003Statement

    When approve agreement SmokeAgreementFCM003
    And quick link - agreement exposure management
    And Exposure Management - tick events FCMagr003CallEvent
    And Exposure Management - auto send letter
#    Then Exposure Management event should be FCMagr003CallEventIssued

    When Exposure Management - set event FCMagr003CallEvent value to FCMagr003CallEventCounterpartyAmount001
    And Exposure Management - save changes
    And Exposure Management - tick event FCMagr003CallEvent
    And Exposure Management - group booking
    And view asset type CashUSDType agreement asset CASH Page
#    When add call bookings - group booking FCMagr003CallEventGroupbooking

  @BinHu @wip
  Scenario: Smoke UDF testing
    When navigate to system preferences page
    And set system preferences TickEnableDateBasedValuation
    And navigate to organisation static data page
    And edit organisation static data OrgUDF1 to EnableOrgUDF1
    And edit organisation static data OrgUDF16 to EnableOrgUDF16
    And navigate to collateral static data page
    And edit collateral static data CollateralSecurityUDF1 to EnableCollateralSecurityUDF1
    And edit collateral static data CollateralSecurityUDF16 to EnableCollateralSecurityUDF16
    And edit collateral static data CollateralAgreementUDF1 to EnableCollateralAgreementUDF1
    And navigate to collateral static data UDF Values page
    And edit collateral static data UDF CollateralUDFValuesORGUDF_UDF1 to EnableCollateralUDFValuesORGUDF_UDF1
    And edit collateral static data UDF CollateralUDFValuesORGUDF_UDF16 to EnableCollateralUDFValuesORGUDF_UDF16
    And edit collateral static data UDF CollateralUDFValuesSECUDF_UDF1 to EnableCollateralUDFValuesSECUDF_UDF1
    And edit collateral static data UDF CollateralUDFValuesSECUDF_UDF16 to EnableCollateralUDFValuesSECUDF_UDF16
    And edit collateral static data UDF CollateralUDFValuesAGRUDF_UDF1 to EnableCollateralUDFValuesAGRUDF_UDF1
    And add collateral static data UDF AddValue1ToORGUDF_UDF1
    And add collateral static data UDF AddValue2ToORGUDF_UDF1
    And add collateral static data UDF AddValue3ToORGUDF_UDF1
    And add collateral static data UDF AddValue1ToORGUDF_UDF16
    And add collateral static data UDF AddValue2ToORGUDF_UDF16
    And add collateral static data UDF AddValue3ToORGUDF_UDF16
    And add collateral static data UDF AddValue1ToSECUDF_UDF1
    And add collateral static data UDF AddValue2ToSECUDF_UDF1
    And add collateral static data UDF AddValue3ToSECUDF_UDF1
    And add collateral static data UDF AddValue1ToSECUDF_UDF16
    And add collateral static data UDF AddValue2ToSECUDF_UDF16
    And add collateral static data UDF AddValue3ToSECUDF_UDF16
    And add collateral static data UDF AddValue1ToAGRUDF_UDF1
    And add collateral static data UDF AddValue2ToAGRUDF_UDF1
    And navigate to security search page
    And add security SmokeUDFInstr001
    And search security SmokeUDFInstr001Search
#    Then securities SmokeSecurityBond-USD should be SmokeUDFInstr001

    When approve security SmokeUDFInstr001SearchResult
    And add security SmokeUDFInstr002
    And search security SmokeUDFInstr002Search
#    Then securities SmokeSecurityBond-USD should be SmokeUDFInstr002

    When approve security SmokeUDFInstr002SearchResult
    And feed asset pricings UDFfeedassetpricings001,UDFfeedassetpricings002,UDFfeedassetpricings003 by xml
    And feed FX rates UDFfeedfxrate001,UDFfeedfxrate002,UDFfeedfxrate003 by xml
    And navigate to interest rates page
    And add Interest Rates UDFaddinterestrate001
    And navigate to holiday centre page
    And add holiday centres UDFsmokeholidaycentres001
    And add organisation SmokeBranchPrincipalForUDF
    And add organisation SmokePrincipalForUDF
    And navigate to view organisation
    And search organisations SmokePrincipalForUDFSearch
#    Then organisation should be SmokePrincipalForUDFColumn

    When edit organisation to SmokePrincipalForUDFWithValues
    And add organisation SmokeBranchCptyForUDF
    And add organisation SmokeCptyForUDF
    And navigate to view organisation
    And search organisations SmokeCptyForUDFSearch
#    Then organisation should be SmokeCptyForUDFColumn

    When edit organisation to SmokeCptyForUDFWithValues
    And add OTC agreements SmokeAgreementUDF001
    And feed flush trades FeedUDFAgrTrade001 by xml
#    And navigate to agreement search page
#    And search agreement SearchSmokeAgreementUDF001ByUDF
#    And view completed agreement statement SearchResultForSmokeAgreementUDF001ByUDF
#    Then agreement statement should be CheckStatementMTMandIA001

    When feed flush trades FeedUDFAgrTrade001Update1 by xml
#    Then agreement statement should be CheckStatementMTMandIA002

    When feed flush trades FeedUDFAgrTrade001Update2 by xml
#    Then agreement statement should be CheckStatementMTMandIA002

    And search agreement SearchSmokeAgreementUDF001ByUDF
    And view completed agreement statement SearchResultForSmokeAgreementUDF001ByUDF
    And approve agreement SmokeAgreementUDF001
#    Then agreement statement should be CheckStatementVMIMCallreq

#    When navigate to workflow dashboard page
#    And workflow dashboard - show calls and recalls
    When quick link - agreement exposure management
#    And Exposure Management - all filters - search dynamic filter EMSearchByPrincipalCounterparty
#    Then agreement statement should be CheckEMVMIMCallReqAmount

    When Exposure Management - tick events UDFTickVMCallEvent,UDFTickIMCallEvent
#    And Exposure Management - tick events UDFTickIMCallEvent
    And Exposure Management - auto send letter
#    And exposure management - search
#    Then Exposure Management UDFTickVMCallEvent - event call status|action should be MarginRequestIssuedStatus
#    And Exposure Management UDFTickIMCallEvent - event call status|action should be MarginRequestIssuedStatus

    When Exposure Management - tick events UDFTickVMCallEvent,UDFTickIMCallEvent
    And Exposure Management - auto populate counterparty amount
    And Exposure Management - tick events UDFTickIMCallEvent
    And Exposure Management - group booking
    And view asset type BondUSDType agreement asset Bond Page
    And add call booking - statement booking UDFIMCallGroupBooking001
#    Then Exposure Management UDFTickIMCallEvent - event call status|action should be CompletedStatus

    When Exposure Management - tick events UDFTickVMCallEvent
    And add call booking - bulk booking UDFVMCallBulkBooking001

  @BinHu
  Scenario: Smoke Repo testing
    When navigate to collateral static data page
    And add collateral static data AddSmokePriceSource001
    And navigate to asset definition page
    And add asset class AddAssetClassSmokeBOND
    And add asset types AddAssetTypeSmokeBOND_ABC
    And navigate to security search page
    And add security AddRepoSecurity001
    And search security RepoSecurity001Search
    And approve security RepoSecurity001SearchResult
    And navigate to collateral static data page
    And add collateral static data AddValue1toAnnexes,AddValue2toAnnexes
    And add Repo agreements SmokeRepoAgreement001
    And view statement
    And approve agreement SmokeRepoAgreement001
    And edit summary exposure info
    And view product type Productpro2 on exposure summary page
    And add Repo trades AddSmokeRepoTrade001
#    Then trade SmokeRepoTrade001 details should be SmokeRepoTrade001Detail

    When click back button to previous page
    And view product type Productpro4 on exposure summary page
    And add Repo trades AddSmokeRepoTrade002
#    Then trade SmokeRepoTrade002 details should be SmokeRepoTrade002Detail

    When add Repo agreements SmokeRepoAgreement002
    And view statement
    And approve agreement SmokeRepoAgreement002
    And edit summary exposure info
    And view product type Productpro2 on exposure summary page
    And add Repo trades AddSmokeRepoTrade003
#    Then trade SmokeRepoTrade003 details should be SmokeRepoTrade003Detail

    When click back button to previous page
#    Then trade summary should be SmokeRepoTrade003Summary

#    When edit agreement exposure adjustment to AddAgreementExposureAdjustment001
#    Then trade summary should be SmokeRepoTrade003SummaryAdj

    When add Repo agreements SmokeRepoAgreement003
    And view statement
    And edit summary exposure info
    And view product type Productpro2 on exposure summary page
    And add Repo trades AddSmokeRepoTrade004
#    Then trade SmokeRepoTrade004 details should be SmokeRepoTrade004Detail

    When quick link - view statement
    And approve agreement SmokeRepoAgreement003
#    Then agreement statement should be SmokeRepoAgreement003StatementDetail

    When quick link - agreement exposure management
#    Then Exposure Management \S+ - search result should follow rule \S+

    When Exposure Management - tick events RepoCallDeficitEvent001
    And Exposure Management - auto send letter
#   Then Exposure Management event RepoCallDeficitEvent001 should be EMResult002

    When Exposure Management - set event RepoCallDeficitEvent001 value to RepoCallDeficitEvent001CptyAmount
    And Exposure Management - save changes
    And Exposure Management - tick events RepoCallDeficitEvent001
    And add call booking - group booking RepoEMgroupbooking001
    And add Repo agreements SmokeRepoAgreement004
    And view statement
    And edit summary exposure info
    And view product type Productpro2 on exposure summary page
    And add Repo trades AddSmokeRepoTrade005
#    Then trade SmokeRepoTrade005 details should be SmokeRepoTrade005Detail

    When quick link - view statement
    And approve agreement SmokeRepoAgreement004
    And quick link - View Simulation
#    Then

    When navigate to security search page
    And search security RepoSecurity001Search
    And edit security RepoSecurity001SearchResult to RepoSecurity001UpdateCleanPrice
    And search security RepoSecurity001Search
    And approve security RepoSecurity001SearchResult
    And add security AddRepoSecurity002
    And search security RepoSecurity002Search
    And edit security RepoSecurity002SearchResult to RepoSecurity002UpdateCleanPrice
    And approve security RepoSecurity002SearchResult
    And add Repo agreements SmokeRepoAgreement005
    And view statement
    And approve agreement SmokeRepoAgreement005
    And edit summary exposure info
    And view product type Productpro2 on exposure summary page
    And add Repo trades AddSmokeRepoTrade006
    And edit trade EditTrade006 to RepoTrade006UpdateCleanPrice
    And quick link - view statement
    And edit asset summary info
    And view asset type RepoBOND_ABC agreement asset Bond Page
#    And add call bookings - statement booking RepoStatementBooking001
    And navigate to Price Exceptions Report
    And search Price Exceptions Report PriceExceptionsReportTemplate

  @BinHu @ignore
  Scenario: Physical CCP and Eligibility Rule Template
    When navigate to TSA rules page
    And add TSA rules SmokeTSARule
    And navigate to eligibility rules template page
#    And add eligibility rules templates ERT001
    And navigate to collateral static data page
    And add collateral static data SmokeLinkageSet001
    And add FCM agreements SmokeFCMAgreement006
    And view statement
    And approve agreement SmokeFCMAgreement006
    And edit asset summary info
#    Then asset holding summary should be CheckAllAssetTypes

    When feed delta trades FeedFCMTrade001 by xml
    And search agreement SearchFCMAgr006
    And view completed agreement statement SearchFCMAgr006Result
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add FCM trades AddFCMTrade001
    And feed portfolio TSA FeedTSAResettoPar by excel
    And feed portfolio TSA FeedTSAInitialCoupon by excel
    And feed portfolio TSA FeedTSABankedCoupon by excel
    And feed portfolio TSA FeedTSAPAI by excel
    And feed portfolio TSA FeedTSAIMInterest by excel
    And feed portfolio TSA FeedTSANDFCashflow by excel
    And feed portfolio TSA FeedTSAUpfrontFee by excel
    And feed portfolio TSA FeedTSATransferValue by excel
    And feed portfolio TSA FeedTSACreditEventCashAmount by excel
    And feed portfolio TSA FeedTSACreditSuccessionEventCashAmount by excel
    And feed portfolio TSA FeedTSATSAMisc1 by excel
    And feed portfolio TSA FeedTSATSAMisc2 by excel
    And feed portfolio TSA FeedTSATSAMisc3 by excel
    And search agreement SearchFCMAgr006
    And view completed agreement statement SearchFCMAgr006Result
    And approve agreement SmokeFCMAgreement006ToApproved
#    Then agreement statement should be SmokeFCMAgreement006StatementDetail

    When quick link - agreement exposure management
#    Then Exposure Management \S+ - event call status|action should be \S+

    When Exposure Management - tick events SmokeFCMAgreement006IMCallEvent
    And Exposure Management - auto send letter
    And search agreement SearchFCMAgr006
    And view completed agreement statement SearchFCMAgr006Result
    And edit asset summary info
    And view asset type AssetTypeCashUSD agreement asset Cash Movements Page
    And quick link - agreement exposure management
    And Exposure Management - set event SmokeFCMAgreement006VMCallDeficitEvent value to FCM006VMCallDeficitEventCptyAmount
    And Exposure Management - save changes
    And Exposure Management - tick events SmokeFCMAgreement006VMCallDeficitEvent
#    And add call booking - bulk booking SmokeFCMAgreement006BulkBooking001
#    And Exposure Management - tick events SmokeFCMAgreement006TSACallDeficitEvent
#    And add receive booking - bulk booking SmokeFCMAgreement006BulkBooking002
#    And Exposure Management - tick events SmokeFCMAgreement006IMCallEvent
#    And add call booking - bulk booking SmokeFCMAgreement006BulkBooking003
