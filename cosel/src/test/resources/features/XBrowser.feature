@XBrowser
Feature: XBrowser
  This feature is to validate all the core functions is working across ie 9 10 and 11

  In order to validate all core functions across browsers
  As a colline user
  I want test following functions

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference1
    And have default stp configuration

  @MengliHuang
  Scenario Outline: setup agreements
    When add <businessline> agreements <agrinfo>
    And view statement
    And edit summary exposure info
    And view product type <productType> on exposure summary page
    And add <tradeline> trades <tradeinfo>
    And quick link - view statement
    And edit asset summary info
    And view asset type cashbookingtype1 agreement asset CASH Page
    And add call booking - statement booking <cashbooking>
    And click back button to previous page
    And view asset type bondbookingtype1 agreement asset Bond Page
    And add call booking - statement booking <bondbooking>
    And click back button to previous page
    And view asset type equitybookingtype1 agreement asset Equity Page
    And add call booking - statement booking <equitybooking>
    And click back button to previous page
    And view asset type cashbookingtype1 agreement asset Cash Movements Page
    And add <interesttype> booking - statement booking <interestbooking>
    And quick link - view statement
#    And approve agreement <agrinfo>

    Examples:
      | businessline | agrinfo   |productType| agmtid       | tradeline | tradeinfo   | cashbooking       | cashtype         | bondbooking       | bondtype         | equitybooking       | equitytype         | interesttype | interestbooking |
      | OTC          | otc.agr1  |product1| otc.agr1.id  | OTC       | otc.trade1  | otc.cashbooking1  | cashbookingtype1 | otc.bondbooking1  | bondbookingtype1 | otc.equitybooking1  | equitybookingtype1 | net interest | otc.interest1   |
      | FCM          | fcm.agr1  |product2| fcm.agr1.id  | FCM       | fcm.trade1  | fcm.cashbooking1  | cashbookingtype1 | fcm.bondbooking1  | bondbookingtype1 | fcm.equitybooking1  | equitybookingtype1 | TSA          | fcm.interest1   |
      | Repo         | repo.agr1 |product3| repo.agr1.id | Repo      | repo.trade1 | repo.cashbooking1 | cashbookingtype1 | repo.bondbooking1 | bondbookingtype1 | repo.equitybooking1 | equitybookingtype1 | net interest | repo.interest1  |

  @MengliHuang @wip
  Scenario: setup umbrella agreements
    When add Umbrella agreements fund1,umAgr
#    fund2
    And view statement
    #And approve agreement umAgr
    And select agreement fund1 in umbrella list
    And edit summary exposure info
    And add OTC trades fund1.trade1
    And quick link - view statement
    And select agreement umAgr in umbrella list
#    And add call bookings - statement booking um.cashbooking1,um.bondbooking1,um.equitybooking1
#    And quick link - view statement
#    And edit asset summary info
#    And add net interest booking - statement booking um.interest1
#    And quick link - view statement
    And approve agreement umAgr

  @MengliHuang @wip
  Scenario Outline: setup etf&multimodel agreements
    When add <businessline> agreements <agrinfo>
    And view statement
    And edit summary exposure info
    And view product type <productType> on exposure summary page
    And add <businessline> trades <tradeinfo>
    And quick link - view statement
    And select <statement> <statementname>
#    And edit asset summary info
#    And add call booking - statement booking <cashbooking>,<bondbooking>,<equitybooking>
#    And quick link - view statement
#    And select <statement> <statementname>
#    And edit asset summary info
#    And add <interesttype> booking - statement booking <interestbooking>
#    And quick link - view statement
#    And approve agreement <agrinfo>
    Examples:
      | businessline | agrinfo         |productType| agmtid             | tradeinfo         | statement | statementname     | cashbooking             | cashtype         | bondbooking             | bondtype         | equitybooking             | equitytype         | interesttype | interestbooking      |
      | OTC          | multimodel.agr1 |multimodel.product| multimodel.agr1.id | multimodel.trade1 | model     | selectModel1 | multimodel.cashbooking1 | cashbookingtype1 | multimodel.bondbooking1 | bondbookingtype1 | multimodel.equitybooking1 | equitybookingtype1 | net interest | multimodel.interest1 |
      | ETF          | etf.agr1        |etf.product| etf.agr1.id        | etf.trade1        | order     | selectOrder1   | etf.cashbooking1        | cashbookingtype1 | etf.bondbooking1        | bondbookingtype1 | etf.equitybooking1        | equitybookingtype1 | Fee          | etf.interest1        |

  @MengliHuang
  Scenario: agreement search
  Verify browser compatibility for all Colline pages
    When add OTC agreements otc.agr3
    And view statement
    And search agreement agmtsearchCondition2
#    When agreement search result long view
    And view completed agreement statement agmtshortview1
#    When quick link - search
    And search agreement agmtsearchCondition2
    And edit agreement agmtshortview1 in agreement search page

  @MengliHuang @wip
  Scenario: prepare EM data
    When add OTC agreements otc.agr3
    And view statement
    And edit summary exposure info
    And view product type product1 on exposure summary page
    And add OTC trades otc.trade3
    And quick link - view statement
    And approve agreement otc.agr3
    And navigate to Exposure Management
   # And Exposure Management - add user filter \S+
   # And Exposure Management - edit user filter \S+
   # And Exposure Management - remove user filter \S+
    And Exposure Management - margin filters - search margin call agr.description
    And Exposure Management - tick events tickedcallevent1
    And Exposure Management - auto send letter
    And Exposure Management - margin filters - search margin issued agr.description
    And Exposure Management - tick events tickedcallevent1
    And add call bookings - group booking groupbooking1
#    And Exposure Management - group booking
#    And view asset type cashbookingtype1 agreement asset Cash Movements Page
#    And add net interest booking - statement booking otc.interest1
#    And quick link - agreement exposure management

    And Exposure Management - margin filters - search margin issued agr.description
    And Exposure Management - tick events tickedcallevent1
#    And Exposure Management - auto populate counterparty amount
#    And Exposure Management - save changes
    And add call booking - bulk booking bulkbooking1
    And Exposure Management - margin filters - search margin completed agr.description
    And Exposure Management - set event tickedcallevent1 value to tickedcallevent4
    And Exposure Management - save changes
    And Exposure Management - margin filters - search margin call agr.description

  @MengliHuang @wip
  Scenario Outline: prepare EM data - recall return event
    When add OTC agreements <agrinfo2>
    And view statement
    And add <movement2> booking - statement booking <bookinginfo2>
    And quick link - view statement
    #And add <movement1> booking - statement booking <bookinginfo1>
    And approve agreement <agrinfo2>
    Examples:
      | agrinfo2 | tradeinfo2 | movement2 | bookinginfo2 | agreementid2 |
      | otc.agr6 | otc.trade2 | delivery  | otc.booking6 | otc.agr6.id  |
      | otc.agr7 | otc.trade3 | call      | otc.booking7 | otc.agr7.id  |

  @MengliHuang @wip
  Scenario Outline: prepare EM data - call&recall delivery&return event
    When add OTC agreements <agrinfo3>
    And add OTC trades <tradeinfo3>
    And quick link - view statement
    And add <movement3> booking - statement booking <bookinginfo3>
    And quick link - view statement
    And approve agreement <agrinfo3>
    Examples:
      | agrinfo3 | tradeinfo3 | movement3 | bookinginfo3 | agreementid3 |
      | otc.agr8 | otc.trade8 | delivery  | otc.booking8 | otc.agr8.id  |
      | otc.agr9 | otc.trade9 | call      | otc.booking9 | otc.agr9.id  |

  @MengliHuang
  Scenario: IM
    When add OTC agreements IM.agr1
    And view statement
    And edit asset summary info
    And view asset type assetType1 agreement asset CASH Page
    And add call booking - statement booking IM.cashbooking1
    And edit call booking ImBookingSummary1 to IM.cashbooking2
    And quick link - view statement
    And approve agreement IM.agr1
    And navigate to interest manager search page
    And Interest Manager - search interest event by IMsearch
    And Interest Manager - switch to Receive tab
    And Interest Manager - switch to Pay tab
    And Interest Manager - email for event IMevent
    And Interest Manager - view letter for event IMevent
    And quick link - Interest Manager
    And Interest Manager - view details for event IMevent
    And Interest Manager - click Interest Manager button in interest detail page
    And Interest Manager - switch to Pay tab
    And Interest Manager - change interest events to IMevent1
    And Interest Manager - save change all events
    And Interest Manager - tick interest events IMevent
    And Interest Manager - Apply interest movement
    And Interest Manager - view interest movements for event IMevent
    And edit cashmovement booking cashMovementBookingSummary1 to cashMovementBooking1
    And quick link - Interest Manager
    And click back button to previous page
    And Interest Manager - search interest event by IMsearch
    And Interest Manager - reset events IMevent

  @MengliHuang
  Scenario: Security
    When navigate to security search page
    And add security bond_ins1
    And search security securitysearch1
    And edit security securitySearchResult1 to bond_ins2
    And search security securitysearch1
    And approve security securitySearchResult1
    And add security equity_ins1
    And search security securitysearch2
    And edit security securitySearchResult2 to equity_ins2
    And search security securitysearch2
    And approve security securitySearchResult2
    And search security securitysearch3
    And edit security securitySearchResult3 to cash_ins1
    And search security securitysearch3
    And approve security securitySearchResult3
#    And navigate to Inventory Manager search page

  @MengliHuang
  Scenario: organisation
    When add organisations org1,org2,org3,prinOrg1
    And navigate to view organisation
    And search organisations orgSearch2
    And edit organisation to editOrg1
    And delete organisations orgSearch3
    And navigate to organisation books page
    And add organisation books book1,book2,book3
#    And add organisations org1
#    And navigate to view organisation
#    And view our organisation
#    And expand organisations orgSearchResult1 tree


  @MengliHuang
  Scenario: Approvals Manager
    Given have OTC agreements approval.agr1,approval.agr2,approval.agr3
    When navigate to approvals manager page
    And approvals manager - search agreement approvalManagerAgreementSearch1
    And approvals manager - tick agreements approvalManagerAgreementSearchResult1
    And approvals manager - approve ticked agreements
    And approvals manager - agreement approvalManagerAgreementSearchResult1 agreement review
    And view statement
    And navigate to approvals manager page
    And approvals manager - search agreement approvalManagerAgreementSearch1
    And approvals manager - approve all agreements approvalManagerAgreementSearchResult2

  @MengliHuang @todo
  Scenario: settlement status
    When navigate to settlement status page
    And settlement status - search settlement status settlementDataSearch1
    And settlement status - show asset settlementStatusSummary1 details
    And settlement status - tick bookings settlementStatusDetail1 in capital movements

  @MengliHuang
  Scenario: configuration
    When navigate to collateral static data page
    And add collateral static data letterTemplate1
    And navigate to letter configuration page
    And add letter template in letter configuration page letterConfiguration1
    And edit letter template in letter configuration page letterConfiguration1 to editLetterConfiguration1
    And add letter template in letter configuration page letterUDL1
    And edit letter template in letter configuration page letterUDL1 to letterUDL1
    And navigate to collateral preferences page
    And set collateral preferences collateralPreference1
#    Given have default collateral preferences
#    When navigate to collateral preferences page
#    And set collateral preferences collateral.preference.default

  @MengliHuang @Proxied
  Scenario: reports
    When navigate to Asset Holdings and Valuation Report
    And search Asset Holdings and Valuation Report ahvReport1
    And run report to ahvReportPath as Excel Worksheet
    And navigate to Asset Holdings and Valuation Report
    And navigate to Asset Holdings and Valuation Report template ahvtemplate1.id
    And run report to ahvReportPath as GUI
    And run GUI report to ahvReportPath as PDF
    And save report result to Colline ahvSaveReport1
    And export saved report ahvSaveReport1 result to ahvReportPath as Excel
    And export saved report ahvSaveReport1 result to ahvReportPath as Excel
    And delete marked reports ahvSaveReport1

  @MengliHuang
  Scenario: static data
    When navigate to interest rates page
    And add Interest Rates interestRate1
    And edit Interest Rate interestRate1 to editInterestRate1
    And navigate to asset definition page
    And add asset class assetClass1
    And add asset types assetType1
    And edit asset class assetClass1 to editAssetClass1
    And edit asset type assetType1 to editAssetType1
    And navigate to collateral static data page
    And add collateral static data sheme1
    And add collateral static data schemeData1,schemeData2
    And edit collateral static data sheme1 to editSheme1
    And edit collateral static data schemeData1 to editSchemeData1
    And navigate to TSA rules page
    And add TSA rules tsaRule1
    And search TSA rule tsaRule1
    And edit TSA rule tsaRule1 to tsaRule2
    And navigate to TSA rules page
    And search TSA rule tsaRule1
    And delete TSA rules tsaRule1

  @MengliHuang
  Scenario: market data
    When navigate to FX rate page
    And add FX rate sets fxRate1
    And edit FX rate set fxRate1 to editFxRate1
    And delete FX rate sets fxRate1

  @MengliHuang
  Scenario: tools
#    When change my password changePassword
    When add to my favourites userFavourite
    And edit my favourites userFavourite
    And edit my preferences userPreference
    And reset my preference

  @MengliHuang
  Scenario: help
    When navigate to about this software

  @MengliHuang
  Scenario: system admin
    When navigate to task scheduler page
    And task scheduler - switch to Task Manager tab
    And duplicate task scheduler importAgreementsInfo1
    And edit task scheduler importAgreements1Info1
    And run task scheduler importAgreements1Info1
    And remove task scheduler importAgreements1Info1
    And navigate to system preferences page
    And set system preferences systemPreference
    And navigate to user&role administration page
    And user&role administration - switch to administer approval profiles page
    And set administer approval profiles approveProfile1
    And user&role administration - switch to administer roles page
    And add administer roles role1
    And add users userId1
    And edit user userId1
    And navigate to system date schemes page
    And add system static data schemeId
    And add system static data schemeDateId
    And navigate to event log page
    And event log search eventLogSearch1









