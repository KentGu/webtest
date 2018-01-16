@ExposureManagement @regression
Feature: ExposureManagement

  Background:
    Given have login with credential login.credential.test1
    And have collateral preferences collateral.preference.default
    And have default stp configuration
    And have system preferences system.preferences.default

  @v13.0.0 @JaneZhang @F7895 @T28189 @General @Proxied @Reviewed
  Scenario: Verify IM event always look up the VM call frequency to determin shown in EM or IR
  """
  1.IM Call/IM Delivery/IM Return/IM Recall look up the IM call frequency to determine event should be shown in EM or IR
  2.LRFP, LRFD, Notify By, Resolve By are shown IM info, and same value in Daily Exposure Report and Internal Review Report
  """
    When Go to agreement agr28189.otc.not.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.1m.checkLRFP.LRFD.NotifyBy.ResolveBy
    When navigate to internal review page
    And internal review - search internal review agr28189.search.deliveryEvent
    Then internal review list summary should be internalReview.delivery.noRecord
    When navigate to Daily Exposure Report
    And search Daily Exposure Report report.searchbyAgr28189Description
    And run report to dailyExposureReportPath as Excel Worksheet
    Then assert Excel Worksheet report dailyExposureReportPath should follow the rule dailyExposureReport.rule.sameAsIMCallScheduleSetting.daily

    When Go to agreement agr28189.otc.not.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr28189.vmLegalReviewFrequencyMonthly.imLegalReviewFrequencyDaily to agr28189.vmLegalReviewFrequencyDaily.imLegalReviewFrequencyMonthly in callSchedule tab
    And view statement
    And approve agreement agr28189
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.event.notDisplay
    When navigate to internal review page
    And internal review - search internal review agr28189.search.deliveryEvent
    Then internal review list summary should be internalReview.delivery.checkLRFP.LRFD.NotifyBy.ResolveBy.sameAsIMCallScheduleSetting
    When navigate to Daily Exposure Report
    And search Daily Exposure Report report.searchbyAgr28189Description
    And run report to dailyExposureReportPath2 as Excel Worksheet
    Then assert Excel Worksheet report dailyExposureReportPath2 should follow the rule dailyExposureReport.rule.sameAsIMCallScheduleSetting.monthly

  @v13.1.0 @JaneZhang @F6248 @T28947 @forETFAgreement @Reviewd
  Scenario: Verify ETF event would change to Completed when models are completed and no call event margin request issued status aggregated by Event Source
    When Go to agreement agr28947.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.Call.agreementRequirement.1m
    Then Exposure Management - event should be vm.Call.agreementRequirement.1m,vm.Call.o3.agreementRequirement.3m,vm.Delivery.o2.agreementRequirement.-2m,vm.noCall.o1.agreementRequirement.0m
    When Exposure Management - set event vm.Call.o3.pendingReview value to vm.Call.o3.waived
    And Exposure Management - save changes
    And Exposure Management - expand events vm.Call.agreementRequirement.1m
    And Exposure Management - set event vm.noCall.o1.pendingReview value to vm.noCall.o1.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events vm.Call.agreementRequirement.1m
    Then Exposure Management - event should be vm.Call.agreementRequirement.1m,vm.Call.o3.waived,vm.Delivery.o2.agreementRequirement.-2m,vm.noCall.o1.MarginRequestIssued
    When Exposure Management - set event vm.Delivery.o2.pendingReview value to vm.Delivery.o2.completed
    And Exposure Management - save changes
    And Exposure Management - expand events vm.Call.completed
    Then Exposure Management - event should be vm.Call.completed,vm.Call.o3.waived,vm.Delivery.o2.completed,vm.noCall.o1.MarginRequestIssued

  @v13.1.0 @JaneZhang @F6248 @T29013 @forETFAgreement @Reviewd
  Scenario: Verify Delivery Excess event workflow for ETF agreement from pending review to Partial Dispute aggregated by Model
    When Go to agreement agr29013.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events delivery.top.o2,delivery.top.o1
    Then Exposure Management - event should be delivery.top.o2.agreementRequirement.-3m,delivery.top.o1.agreementRequirement.-3m,vm.delivery.o2.agreementRequirement.-1m,cash.delivery.o2.agreementRequirement.-2m,vm.delivery.o1.agreementRequirement.-2m,fee.delivery.o1.agreementRequirement.-1m

    When Exposure Management - set event vm.delivery.o1.pendingReview value to vm.delivery.o1.MarginRequestIssued.counterpartyAmount.-3m
    And Exposure Management - save changes
    And Exposure Management - expand events delivery.top.o2,delivery.top.o1
    And Exposure Management - tick events vm.delivery.o1.MarginRequestIssued.counterpartyAmount.-3m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking delivery.applyAgreementAmount
    And quick link - save
    And Exposure Management - expand events delivery.top.o2,delivery.top.o1
    Then Exposure Management - event should be delivery.top.o1.disbuteValue.0.pendingPreview,vm.delivery.o1.disbuteValue.-1m.partialDispute,fee.delivery.o1.disbuteValue.1m.pendingPreview
    Then Exposure Management - event should be delivery.top.o2.agreementRequirement.-3m,vm.delivery.o2.agreementRequirement.-1m,cash.delivery.o2.agreementRequirement.-2m

    When Exposure Management - set event fee.delivery.o1.pendingReview value to vm.delivery.o1.MarginRequestIssued.counterpartyAmount.-2m
    And Exposure Management - save changes
    And Exposure Management - expand events delivery.top.o2,delivery.top.o1
    And Exposure Management - tick events vm.delivery.o1.MarginRequestIssued.counterpartyAmount.-2m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking pay.applyAgreementAmount
    And quick link - save
    And Exposure Management - expand events delivery.top.o2,delivery.top.o1
    Then Exposure Management - event should be delivery.top.o1.disbuteValue.-2m.partialDispute,vm.delivery.o1.disbuteValue.-1m.partialDispute,fee.delivery.o1.disbuteValue.-1m.partialDispute
    Then Exposure Management - event should be delivery.top.o2.agreementRequirement.-3m,vm.delivery.o2.agreementRequirement.-1m,cash.delivery.o2.agreementRequirement.-2m

  @v13.1.0 @JaneZhang @F3641 @T20371 @forModelAgreement @Reviewd
  Scenario: Verify Call event workflow is correct for Net and multi model agreement from pending review to Margin Request Issued by sending letter on net level
    When Go to agreement agr20371.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model,notificationStatus
    And Exposure Management - expand events call.net
    Then Exposure Management - event should be call.net.agreementRequirement.724015.31,call.m1.agreementRequirement.3034.77,call.m2.agreementRequirement.406416.73

    When Exposure Management - tick events call.m1.agreementRequirement.3034.77.event
    Then Exposure Management - createLetter,AutoEmail,AutoEmailAll action should be displayed
    When Exposure Management - tick events call.net.agreementRequirement.724015.31.event
    Then Exposure Management - createLetter,AutoEmail,AutoEmailAll action should be displayed
    When Exposure Management - create letter
    Then letter detail should be letterProcessing.statementNotification.fullDispute.display
    When quick link - agreement exposure management
    And Exposure Management - tick events call.net.agreementRequirement.724015.31.event
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter
    And Exposure Management - expand events call.net
    Then Exposure Management - event should be call.net.MarginRequestIssued.notificationStatusSent,call.m1.MarginRequestIssued.notificationStatusSent,call.m2.MarginRequestIssued.notificationStatusSent
    When Exposure Management - margin filters - search margin issued agr20371.issued
    And Exposure Management - tick events call.net
    Then Exposure Management - event should be call.net.MarginRequestIssued.notificationStatusSent,call.m1.MarginRequestIssued.notificationStatusSent,call.m2.MarginRequestIssued.notificationStatusSent

  @v13.1.0 @JaneZhang @F3641 @T20377 @forModelAgreement @Reviewd
  Scenario: Verify VM Call event workflow is correct for FCM and multi model agreement from pending review to Margin Request Issued
    When Go to agreement agr20377.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events im.call.top,vm.call.top
    Then Exposure Management - event should be im.call.top.agreementRequirement.428710.23.pendingReview,im.recall.m2.agreementRequirement.0.8m.pendingReview,im.call.m1.agreementRequirement.421773.61.pendingReview
    Then Exposure Management - event should be vm.call.top.agreementRequirement.1484967.94.pendingReview,vm.call.m2.agreementRequirement.122756785.52.pendingReview,vm.call.m1.agreementRequirement.420571.98.pendingReview
    When Exposure Management - set event vm.call.m2.agreementRequirement.122756785.52.pendingReview value to vm.call.m2.agreementRequirement.122756785.52.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events im.call.top,vm.call.top
    Then Exposure Management - event should be im.call.top.agreementRequirement.428710.23.pendingReview,im.recall.m2.agreementRequirement.0.8m.pendingReview,im.call.m1.agreementRequirement.421773.61.pendingReview
    Then Exposure Management - event should be vm.call.top.agreementRequirement.1484967.94.pendingReview,vm.call.m2.agreementRequirement.122756785.52.MarginRequestIssued,vm.call.m1.agreementRequirement.420571.98.pendingReview
    When Exposure Management - set event vm.call.m1.agreementRequirement.420571.98.pendingReview value to vm.call.m1.agreementRequirement.420571.98.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events im.call.top,vm.call.top
    Then Exposure Management - event should be im.call.top.agreementRequirement.428710.23.pendingReview,im.recall.m2.agreementRequirement.0.8m.pendingReview,im.call.m1.agreementRequirement.421773.61.pendingReview
    Then Exposure Management - event should be vm.call.top.agreementRequirement.1484967.94.MarginRequestIssued,vm.call.m2.agreementRequirement.122756785.52.MarginRequestIssued,vm.call.m1.agreementRequirement.420571.98.MarginRequestIssued


  @v2012.2 @JaneZhang @F3641 @T20633 @forModelAgreement @Reviewed
  Scenario: Verify Auto Populate Counterparty Amount shown and work fine for Net and Multi model agreement
    When Go to agreement agr20633.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events call.net.agreementRequirement.1450631.84
    Then Exposure Management - event should be call.net.agreementRequirement.1450631.84,recall.m2.agreementRequirement.1.2m,call.m1.agreementRequirement.1m
    When Exposure Management - tick events call.m1.agreementRequirement.1m
    Then Exposure Management - AutoPopulateCounterpartyAmount action should be displayed
    When Exposure Management - untick events call.m1.agreementRequirement.1m
    When Exposure Management - tick events recall.m2.agreementRequirement.1.2m
    Then Exposure Management - AutoPopulateCounterpartyAmount action should be displayed
    When Exposure Management - untick events recall.m2.agreementRequirement.1.2m
    And Exposure Management - tick events call.net.agreementRequirement.1450631.84
    And Exposure Management - auto populate counterparty amount
    Then Exposure Management - event should be call.net.couterpartyAmount.1450631.84,recall.m2.couterpartyAmount.1.2m,call.m1.couterpartyAmount.1m

  @v2012.2 @JaneZhang @F3641 @T20634 @forModelAgreement @Reviewed
  Scenario: Verify Auto Populate Counterparty Amount shown and work fine for Not Net and Multi model agreement
    When Go to agreement agr20634.multiModel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.call.top.agreementRequirement.1450631.84
    Then Exposure Management - event should be vm.call.top.agreementRequirement.1450631.84,vm.recall.m2.agreementRequirement.1.2m,vm.call.m1.agreementRequirement.1m
    When Exposure Management - tick events vm.call.top.agreementRequirement.1450631.84
    Then Exposure Management - AutoPopulateCounterpartyAmount action should be displayed
    When Exposure Management - untick events vm.call.top.agreementRequirement.1450631.84
    When Exposure Management - tick events vm.call.m1.agreementRequirement.1m
    Then Exposure Management - AutoPopulateCounterpartyAmount action should be displayed
    When Exposure Management - untick events vm.call.m1.agreementRequirement.1m
    And Exposure Management - tick events vm.recall.m2.agreementRequirement.1.2m
    Then Exposure Management - AutoPopulateCounterpartyAmount action should be displayed
    And Exposure Management - auto populate counterparty amount
    Then Exposure Management - event should be vm.call.top.couterpartyAmount.1441961.07,vm.recall.m2.couterpartyAmount.1.2m,vm.call.m1.agreementRequirement.1m
    When Exposure Management - untick events vm.recall.m2.agreementRequirement.1.2m
    And Exposure Management - tick events vm.call.m1.agreementRequirement.1m
    And Exposure Management - auto populate counterparty amount
    Then Exposure Management - event should be vm.call.top.couterpartyAmount.1450631.84,vm.recall.m2.couterpartyAmount.1.2m,vm.call.m1.couterpartyAmount.1m

  @v2012.2 @JaneZhang @F3641 @T20406 @forModelAgreement @Reviewed
  Scenario: Verify VM Call Deficit event workflow is correct for FCM agreement from pending review to Partial Dispute
    When Go to agreement agr20406.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events im.call.top.agreementRequirement.167500,vm.call.top.agreementRequirement.169000
    Then Exposure Management - event should be im.call.top.agreementRequirement.167500,im.call.m1.agreementRequirement.175500,im.return.m2.agreementRequirement.800000
    Then Exposure Management - event should be vm.call.top.agreementRequirement.169000,vm.delivery.m2.agreementRequirement.600000,vm.call.m1.agreementRequirement.175000

    When Exposure Management - tick events vm.delivery.m2.agreementRequirement.600000
    And Exposure Management - set event vm.delivery.m2.agreementRequirement.600000 value to vm.delivery.m2.counterpartyAmount.700000
    And Exposure Management - save changes
    And Exposure Management - expand events im.call.top,vm.call.top
    And Exposure Management - tick events vm.delivery.m2.agreementRequirement.600000
    And Exposure Management - make booking
    And view asset type asset.type.Cash.JPY agreement asset CASH Page
    And add delivery booking - statement booking deliveryBooking.vmAmount.0.6m
    And Exposure Management - expand events im.call.top,vm.call.top
    Then Exposure Management - event should be vm.call.top.disputeValue.-176000,vm.delivery.m2.disputeValue.-100000.PartialDispute,vm.call.m1.disputeValue.175000

    When Exposure Management - tick events vm.call.m1.agreementRequirement.175000
    And Exposure Management - set event vm.call.m1.agreementRequirement.175000 value to vm.call.m1.counterpartyAmount.174000
    And Exposure Management - save changes
    And Exposure Management - expand events im.call.top,vm.call.top
    And Exposure Management - tick events vm.call.m1.agreementRequirement.175000
    And Exposure Management - make booking
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And add call booking - statement booking callBooking.vmAmount.175000
    And Exposure Management - expand events im.call.top,vm.call.top
    Then Exposure Management - event should be vm.call.top.disputeValue.-2000.PartialDispute,vm.delivery.m2.disputeValue.-100000.PartialDispute,vm.call.m1.disputeValue.-1000.PartialDispute

  @v2012.2 @JaneZhang @F3641 @T20442 @forModelAgreement @Reviewed
  Scenario: Verify VM Delivery Excess event workflow is correct for FCM and multi model agreement from pending review to Completed
    When Go to agreement agr20442.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.1375000.pendingReview,vm.delivery.m2.agreementRequirement.120m.pendingReview,vm.delivery.m1.agreementRequirement.175000.pendingReview
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.181600.pendingReview,im.delivery.m2.agreementRequirement.610000.pendingReview,im.delivery.m1.agreementRequirement.175500.pendingReview
    When Exposure Management - set event vm.delivery.m2.agreementRequirement.120m.pendingReview value to vm.delivery.m2.agreementRequirement.120m.completed
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.1375000.pendingReview,vm.delivery.m2.agreementRequirement.120m.completed,vm.delivery.m1.agreementRequirement.175000.pendingReview
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.181600.pendingReview,im.delivery.m2.agreementRequirement.610000.pendingReview,im.delivery.m1.agreementRequirement.175500.pendingReview
    When Exposure Management - set event vm.delivery.m1.agreementRequirement.175000.pendingReview value to vm.delivery.m1.agreementRequirement.175000.completed
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.1375000.completed,vm.delivery.m2.agreementRequirement.120m.completed,vm.delivery.m1.agreementRequirement.175000.completed
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.181600.pendingReview,im.delivery.m2.agreementRequirement.610000.pendingReview,im.delivery.m1.agreementRequirement.175500.pendingReview

  @v2012.2 @JaneZhang @F3641 @T20446 @forModelAgreement @Reviewed
  Scenario: Verify VM Delivery event workflow is correct for Non Net agreement from pending review to Partial Dispute
    When Go to agreement agr20446.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.1825000,vm.delivery.m1.agreementRequirement.175000,vm.delivery.m2.agreementRequirement.800000,vm.delivery.m3.agreementRequirement.100000
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.1850500,im.delivery.m1.agreementRequirement.175500,im.delivery.m2.agreementRequirement.810000,im.delivery.m3.agreementRequirement.110000

    And Exposure Management - set event vm.delivery.m3.agreementRequirement.100000 value to vm.delivery.m3.agreementRequirement.100000.waived
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    And Exposure Management - set event im.delivery.m3.agreementRequirement.110000 value to im.delivery.m3.agreementRequirement.110000.waived
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.1825000,vm.delivery.m1.agreementRequirement.175000,vm.delivery.m2.agreementRequirement.800000,vm.delivery.m3.agreementRequirement.100000.waived
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.1850500,im.delivery.m1.agreementRequirement.175500,im.delivery.m2.agreementRequirement.810000,im.delivery.m3.agreementRequirement.110000.waived

    And Exposure Management - set event vm.delivery.m2.agreementRequirement.800000 value to vm.delivery.m2.counterpartyAmount.900000
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    And Exposure Management - tick events vm.delivery.m2.agreementRequirement.800000
    And Exposure Management - make booking
    And view asset type asset.type.Cash.GBP agreement asset CASH Page
    And add delivery booking - statement booking deliveryBooking.vmAmount.800000
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.disputeValue.1825000.pendingReview,vm.delivery.m1.disputeValue.175000.pendingReview,vm.delivery.m2.disputeValue.800000.partialDispute,vm.delivery.m3.disputeValue.100000.waived
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.1850500,im.delivery.m1.agreementRequirement.175500,im.delivery.m2.agreementRequirement.810000,im.delivery.m3.agreementRequirement.110000.waived

    When Exposure Management - tick events vm.delivery.m1.agreementRequirement.175000
    And Exposure Management - set event vm.delivery.m1.agreementRequirement.175000 value to vm.delivery.m1.counterpartyAmount.176000
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    And Exposure Management - tick events vm.delivery.m1.agreementRequirement.175000
    And Exposure Management - make booking
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And add call booking - statement booking returnBooking.vmAmount.175000
    And Exposure Management - expand events vm.delivery.top,im.delivery.top
    Then Exposure Management - event should be vm.delivery.top.disputeValue.151000.partialDispute,vm.delivery.m1.disputeValue.1000.partialDispute,vm.delivery.m2.disputeValue.800000.partialDispute,vm.delivery.m3.disputeValue.100000.waived
    Then Exposure Management - event should be im.delivery.top.agreementRequirement.1850500,im.delivery.m1.agreementRequirement.175500,im.delivery.m2.agreementRequirement.810000,im.delivery.m3.agreementRequirement.110000.waived

  @2012.2 @JaneZhang @F3641 @T20541 @forModelAgreement @Reviewed
  Scenario: Verify Create Letter shown and works fine for Non Net and Multi model agreement
    When Go to agreement agr20541.multiModel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model,notificationStatus
    And Exposure Management - expand events vm.delivery.top,im.noCall.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.250000,vm.delivery.m2.agreementRequirement.300000,vm.call.m1.agreementRequirement.350000
    Then Exposure Management - event should be im.noCall.top.agreementRequirement.0,im.recall.m2.agreementRequirement.175500,im.Delivery.m1.agreementRequirement.351000

    When Exposure Management - tick events vm.delivery.top,im.noCall.top
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter
    And Exposure Management - expand events vm.delivery.top,im.noCall.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.250000.pendingReview.statementNotificationSent,vm.delivery.m2.agreementRequirement.300000.pendingReview.statementNotificationSent,vm.call.m1.agreementRequirement.350000.MarginRequestIssued.statementNotificationSent
    Then Exposure Management - event should be im.noCall.top.agreementRequirement.0.NoCallRequired.statementNotificationSent,im.recall.m2.agreementRequirement.175500.MarginRequestIssued.statementNotificationSent,im.Delivery.m1.agreementRequirement.351000.pendingReview.statementNotificationSent

  @2012.3 @JaneZhang @F3855 @T24084 @forModelAgreement @Reviewed
  Scenario: Verify TSA Call event workflow is correct for  Multi Model agreement from pending review to Completed
  """
    1.manual change
    2.Net event will change to Completed automatically when all models are Completed.
    3.Shown in Completed tab
    4.EM
  """
    When Go to agreement agr24084.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events TSA.Call.top
    Then Exposure Management - event should be TSA.Call.top.agreementRequirement.2891.15.pendingReview,TSA.Call.m2.agreementRequirement.1927.43.pendingReview,TSA.Call.m1.agreementRequirement.694.10.pendingReview
    And Exposure Management - set event TSA.Call.m1.agreementRequirement.694.10.pendingReview value to TSA.Call.m1.agreementRequirement.694.10.completed
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Call.top
    Then Exposure Management - event should be TSA.Call.top.agreementRequirement.2891.15.pendingReview,TSA.Call.m2.agreementRequirement.1927.43.pendingReview,TSA.Call.m1.agreementRequirement.694.10.completed
    When Exposure Management - set event TSA.Call.m2.agreementRequirement.1927.43.pendingReview value to TSA.Call.m2.agreementRequirement.1927.43.completed
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Call.top
    Then Exposure Management - event should be TSA.Call.top.agreementRequirement.2891.15.completed.automaticallyChanged,TSA.Call.m2.agreementRequirement.1927.43.completed,TSA.Call.m1.agreementRequirement.694.10.completed
    When Go to agreement agr24084.multiModel.fcm statement page by URL
    And recalculate agreement statement
    And quick link - agreement exposure management
    And Exposure Management - expand events TSA.Call.top.agreementRequirement.2891.15.pendingReview.newlyCreated
    Then Exposure Management - event should be TSA.Call.top.agreementRequirement.2891.15.pendingReview.newlyCreated,TSA.Call.m2.agreementRequirement.1927.43.pendingReview.newlyCreated,TSA.Call.m1.agreementRequirement.694.10.pendingReview.newlyCreated
    When Exposure Management - set event TSA.Call.top.agreementRequirement.2891.15.pendingReview.newlyCreated value to TSA.Call.top.agreementRequirement.2891.15.completed.withComment
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Call.top.agreementRequirement.2891.15.completed.withComment
    Then Exposure Management - event should be TSA.Call.top.agreementRequirement.2891.15.completed.withComment,TSA.Call.m2.agreementRequirement.1927.43.completed.automaticallyChanged,TSA.Call.m1.agreementRequirement.694.10.completed.automaticallyChanged

  @2012.3 @JaneZhang @F3855 @T24092 @forModelAgreement @Reviewed
  Scenario: Verify net event would change to Completed when models are combination of completed No Call waived
  """
    -model1: Completed, model2: No Call,  model3: waived
    Result:
    -Net event status is changed to Completed automatically
    -EM
  """
    When Go to agreement agr24092.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.pendingReview,TSA.Delivery.m4.agreementRequirement.4000.pendingReview,TSA.Call.m3.agreementRequirement.3000.pendingReview,TSA.Delivery.m2.agreementRequirement.2000.pendingReview,TSA.Call.m1.agreementRequirement.1000.pendingReview
    And Exposure Management - set event TSA.Call.m1.agreementRequirement.1000.pendingReview value to TSA.Call.m1.agreementRequirement.1000.waived
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    And Exposure Management - set event TSA.Delivery.m2.agreementRequirement.2000.pendingReview value to TSA.Delivery.m2.agreementRequirement.2000.waived
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.pendingReview,TSA.Delivery.m4.agreementRequirement.4000.pendingReview,TSA.Call.m3.agreementRequirement.3000.pendingReview,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived
    When Exposure Management - margin filters - search margin completed agr24092.completed
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed.notdisplay

    When Exposure Management - all filters - search dynamic filter agr24092.allFilter
    And Exposure Management - expand events TSA.Delivery.top
    And Exposure Management - set event TSA.Call.m3.agreementRequirement.3000.pendingReview value to TSA.Call.m3.agreementRequirement.3000.completed
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.pendingReview,TSA.Delivery.m4.agreementRequirement.4000.pendingReview,TSA.Call.m3.agreementRequirement.3000.completed,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived
    When Exposure Management - margin filters - search margin completed agr24092.completed
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed.notdisplay

    When Exposure Management - all filters - search dynamic filter agr24092.allFilter
    And Exposure Management - expand events TSA.Delivery.top
    And Exposure Management - set event TSA.Delivery.m4.agreementRequirement.4000.pendingReview value to TSA.Delivery.m4.agreementRequirement.4000.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.pendingReview,TSA.Delivery.m4.agreementRequirement.4000.MarginRequestIssued,TSA.Call.m3.agreementRequirement.3000.completed,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived
    When Exposure Management - margin filters - search margin completed agr24092.completed
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed.notdisplay

    When Exposure Management - all filters - search dynamic filter agr24092.allFilter
    And Exposure Management - expand events TSA.Delivery.top
    And Exposure Management - set event TSA.Delivery.m4.agreementRequirement.4000.MarginRequestIssued value to TSA.Delivery.m4.agreementRequirement.4000.MarginRequestConfirmed
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.pendingReview,TSA.Delivery.m4.agreementRequirement.4000.MarginRequestConfirmed,TSA.Call.m3.agreementRequirement.3000.completed,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived
    When Exposure Management - margin filters - search margin completed agr24092.completed
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed.notdisplay

    When Exposure Management - all filters - search dynamic filter agr24092.allFilter
    And Exposure Management - expand events TSA.Delivery.top
    And Exposure Management - set event TSA.Delivery.m4.agreementRequirement.4000.MarginRequestConfirmed value to TSA.Delivery.m4.agreementRequirement.4000.noCall
    And Exposure Management - save changes
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed,TSA.Delivery.m4.agreementRequirement.4000.noCall,TSA.Call.m3.agreementRequirement.3000.completed,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived
    When Exposure Management - margin filters - search margin completed agr24092.completed
    And Exposure Management - expand events TSA.Delivery.top
    Then Exposure Management - event should be TSA.Delivery.top.agreementRequirement.2388.45.completed,TSA.Delivery.m4.agreementRequirement.4000.noCall,TSA.Call.m3.agreementRequirement.3000.completed,TSA.Delivery.m2.agreementRequirement.2000.waived,TSA.Call.m1.agreementRequirement.1000.waived

  @2012.2 @JaneZhang @F3641 @T20548 @forModelAgreement  @Reviewed
  Scenario: Verify Auto Email All shown and works fine for Non-Net and Multi model agreement
    When Go to agreement agr20548.multiModel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model,notificationStatus
    And Exposure Management - expand events vm.delivery.top,im.noCall.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.250000,vm.delivery.m2.agreementRequirement.300000,vm.call.m1.agreementRequirement.350000
    Then Exposure Management - event should be im.noCall.top.agreementRequirement.0,im.recall.m2.agreementRequirement.175500,im.Delivery.m1.agreementRequirement.351000
    And Exposure Management - auto send all letter
    And Exposure Management - expand events vm.delivery.top,im.noCall.top
    Then Exposure Management - event should be vm.delivery.top.agreementRequirement.250000.pendingReview.statementNotificationSent,vm.delivery.m2.agreementRequirement.300000.pendingReview.statementNotificationSent,vm.call.m1.agreementRequirement.350000.MarginRequestIssued.statementNotificationSent
    Then Exposure Management - event should be im.noCall.top.agreementRequirement.0.NoCallRequired.statementNotificationSent,im.recall.m2.agreementRequirement.175500.MarginRequestIssued.statementNotificationSent,im.Delivery.m1.agreementRequirement.351000.pendingReview.statementNotificationSent

  @2012.3 @JaneZhang @F5850 @T24070 @AutoBookCash @Reviewed
  Scenario: Verify Physical TSA Auto booking can create succeed via Auto book cash button
  """
    Need to check:
    - auto booking should succeed when set valid auto booking rule for  TSA Delivery(Excess) event
    - The Auto booked TSA booking movement should be set to Pay
    - The Status of the booking is as defined in the auto-booking setting
    - Booking source: Auto-book

    Need to check:
    - Creation date today (server time)
    -  Settlement Date should be set to today (settlement date based on server side adjusted by agreement timezone - same behaviour as capitalise TSA)
  """

    When Go to agreement agr24070.fcm statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.delivery.agreementRequirement.1001000,tsa.delivery.agreementRequirement.2000,im.noCall
    When Exposure Management - set event tsa.delivery.agreementRequirement.2000 value to tsa.delivery.agreementRequirement.2000.MarginRequestConfirmed
    And Exposure Management - save changes
    And Exposure Management - tick events tsa.delivery.agreementRequirement.2000.MarginRequestConfirmed
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be tsa.autoBookingFailDueToCounterpartyAmountNotSet
    When Exposure Management - set event tsa.delivery.agreementRequirement.2000.MarginRequestConfirmed value to tsa.delivery.agreementRequirement.2000.MarginRequestConfirmed.counterPartyAmout.-3k
    And Exposure Management - tick events tsa.delivery.agreementRequirement.2000.MarginRequestConfirmed
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be tsa.autoBookingSuccess

    When Go to agreement agr24070.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset Cash Movements Page
    Then cash movement summary should be tsa.pay.pending.autobook.2000
    When open cash movement editor page tsa.pay.pending.autobook.2000
    Then cash movement detail should be tsa.pay.pending.autobook.2000.checkAssetNoteAndlinkedEvent
    When asset booking edit - click cancel button
    And quick link - view statement
    And approve agreement agr24070
    And quick link - agreement exposure management
    And Exposure Management - set event vm.delivery.agreementRequirement.1001000 value to vm.delivery.agreementRequirement.1001000.MarginRequestConfirmed.counterPartyAmout.-1001000
    And Exposure Management - save changes
    And Exposure Management - tick events vm.delivery.agreementRequirement.1001000.MarginRequestConfirmed.counterPartyAmout.-1001000
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be vm.delivery.autoBookingSuccess

    When Go to agreement agr24070.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.GBP agreement asset Cash Movements Page
    Then cash movement summary should be CapitaliseTsa.pay.pending.autobook.563.70
    When open cash movement editor page CapitaliseTsa.pay.pending.autobook.563.70
    Then cash movement detail should be CapitaliseTsa.pay.pending.autobook.563.70.checklinkedEventNull

  @2012.3 @JaneZhang @F10624 @T31367 @AutoBookCash @Reviewed
  Scenario: Verify that auto book for IM Call and IM Recall when both IM Call and IM Recall selected FCMGrossIM
  """
     R6 Auto Book Buttons and Task
     Regarding R2, R3, Auto book collateral booking is depending on what kind letter issued if send letter to trigger auto-booking. Now if we click auto book button or execute Schedule Task - Auto Book Cash:
     Both Call and Recall applicable or selected: auto book collateral for Call and Recall
     Above logic should be applied to all Auto-Book button on EM and Schedule Task
  """
    When Go to agreement agr31367.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.noCall,im.call.agreementRequirement.1000000.pendingReview,im.recall.agreementRequirement.2000000.pendingReview
    When Exposure Management - tick events im.call.agreementRequirement.1000000.pendingReview
    And Exposure Management - manual send margin call letter createImCallLetter
    And Exposure Management - tick events im.recall.agreementRequirement.2000000.pendingReview
    And Exposure Management - manual send margin recall letter createImRecallLetter
    Then Exposure Management - event should be vm.noCall,im.call.agreementRequirement.1000000.MarginRequestIssued,im.recall.agreementRequirement.2000000.MarginRequestIssued

    When Exposure Management - set event im.call.agreementRequirement.1000000.MarginRequestIssued value to im.call.agreementRequirement.1000000.MarginRequestIssued.counterpartyAmount.1000000
    And Exposure Management - set event im.recall.agreementRequirement.2000000.MarginRequestIssued value to im.recall.agreementRequirement.2000000.MarginRequestIssued.counterpartyAmount.2000000
    And Exposure Management - save changes
    And Exposure Management - tick events im.call.agreementRequirement.1000000.MarginRequestIssued,im.recall.agreementRequirement.2000000.MarginRequestIssued
    And Exposure Management - auto book all
    Then Exposure Management - EM message should be im.call.im.recall.autoBookingSuccess
    Then Exposure Management - event should be vm.noCall,im.call.agreementRequirement.1000000.completed,im.recall.agreementRequirement.2000000.completed

    When Go to agreement agr31367.notnet statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be im.call.pending.autobook.1m
    And asset holding detail should be im.recall.pending.autobook.2m

  @2012.3 @JaneZhang @F3641 @T20347 @AutoBookCash @Reviewed
  Scenario: Verify search function by Model filter
    When Go to agreement agr20347.multimodel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events delivery.top
    Then Exposure Management - event should be delivery.top,return.model.testCGBP,recall.model.AbCUSD,return.model.abcGBP
    When Exposure Management - all filters - search dynamic filter model.abcGBP
    And Exposure Management - expand events delivery.top
    Then Exposure Management - event should be delivery.top,return.model.testCGBP,recall.model.AbCUSD,return.model.abcGBP
    When Exposure Management - all filters - search dynamic filter model.abc
    And Exposure Management - expand events delivery.top
    Then Exposure Management - event should be delivery.top,return.model.testCGBP,recall.model.AbCUSD,return.model.abcGBP
    When Exposure Management - all filters - search dynamic filter model.cgbp
    Then Exposure Management - event should be delivery.top.notdisplay

  @2012.2 @JaneZhang @F4424 @T22474 @CallStatusRevert @Reviewed
  Scenario: Verify Cancel Event related booking would set back Call Status for non messaging agreement in Net agreement
    When Go to agreement agr22474.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.requirementAmount.4800000.pendingReview
    When Exposure Management - tick events delivery.requirementAmount.4800000.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.0.1m,cash.usd.0.2m,cash.usd.4.5m
    And quick link - save
    Then Exposure Management - event should be delivery.requirementAmount.4800000.completed

    When Go to agreement agr22474.otc.net statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit call booking booking.delivery.0.1m to booking.delivery.0.1m.ChangeToCancelled
    Then asset holding detail should be booking.delivery.0.1m.cancelled
    When quick link - view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.requirementAmount.4800000.completed.AdjustedCollateralValue.4700000

    When Go to agreement agr22474.otc.net statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit call booking booking.delivery.0.2m to booking.delivery.0.2m.ChangeToCancelled
    Then asset holding detail should be booking.delivery.0.2m.cancelled
    When quick link - view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.requirementAmount.4800000.MarginRequestConfirmed.AdjustedCollateralValue.4500000

  @2012.2 @JaneZhang @F4424 @T22502 @CallStatusRevert @Reviewed
  Scenario: Verify Cancel Event related booking would set back Call Status for non messaging agreement in MultiModel agreement
    When Go to agreement agr22502.otc.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events call.top
    Then Exposure Management - event should be call.top.requirementAmount.10800000.pendingReview,call.m1.requirementAmount.3m.pendingReview,call.m2.requirementAmount.4.8m.pendingReview
    When Exposure Management - tick events call.m1.requirementAmount.3m.pendingReview,call.m2.requirementAmount.4.8m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.eur.0.1m,cash.eur.0.4m,cash.eur.2.5m,cash.usd.0.1m,cash.usd.0.4m,cash.usd.4.3m
    And quick link - save
    And Exposure Management - expand events call.top
    Then Exposure Management - event should be call.top.requirementAmount.10800000.completed,call.m1.requirementAmount.3m.completed,call.m2.requirementAmount.4.8m.completed

    When Go to agreement agr22502.otc.multiModel statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit call booking booking.call.usd.0.1m to booking.call.usd.0.1m.ChangeToCancelled
    And click back button to previous page
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    And edit call booking booking.call.eur.0.1m to booking.call.eur.0.1m.ChangeToCancelled
    When quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - expand events call.top
    Then Exposure Management - event should be call.top.requirementAmount.10800000.completed,call.m1.requirementAmount.3m.completed,call.m2.requirementAmount.4.8m.completed

    When Go to agreement agr22502.otc.multiModel statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    And edit call booking booking.call.eur.0.4m to booking.call.eur.0.4m.ChangeToCancelled
    When quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - expand events call.top
    Then Exposure Management - event should be call.top.requirementAmount.10800000.MaginRequestIssued,call.m1.requirementAmount.3m.MaginRequestIssued,call.m2.requirementAmount.4.8m.completed

    When Go to agreement agr22502.otc.multiModel statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit call booking booking.call.usd.0.4m to booking.call.usd.0.4m.ChangeToCancelled
    When quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - expand events call.top
    Then Exposure Management - event should be call.top.requirementAmount.10800000.MaginRequestIssued,call.m1.requirementAmount.3m.MaginRequestIssued,call.m2.requirementAmount.4.8m.MaginRequestIssued

  @2012.3 @JaneZhang @F5898 @T24732 @MarginEvent @Reviewed
  Scenario: Verify Multi model agreement TSA event amended waive waive from model
  """
      Need to check:
      - Waive model event, only model event can be find in approve manager
      - If all model event waived, then Agreement event waived automatic
  """

    When navigate to collateral preferences page
    And set collateral preferences setWorkflowWaive
    When Go to agreement agr24732.fcm.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events tsa.call.top
    Then Exposure Management - event should be tsa.call.top,tsa.call.m1,tsa.Delivery.m2
    When Exposure Management - set event tsa.call.m1 value to tsa.call.m1.waived
    And Exposure Management - set event tsa.Delivery.m2 value to tsa.Delivery.m2.waived
    And Exposure Management - save changes
    And Exposure Management - expand events tsa.call.top
    Then Exposure Management - event should be tsa.call.top,tsa.call.m1.amendedWaived,tsa.Delivery.m2.amendedWaived

    When navigate to approvals manager page
    And approvals manager - search workflow agr24732.amendedWaived
    Then approvals manager - the search result tsa.call.m1.amendedWaived.searchResult,tsa.Delivery.m2.amendedWaived.searchResult is existed
    When approvals manager - tick workflow tsa.call.m1.amendedWaived.searchResult
    And approvals manager - approve ticked workflows

    When Go to agreement agr24732.fcm.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events tsa.call.top
    Then Exposure Management - event should be tsa.call.top,tsa.call.m1.waivedApproved,tsa.Delivery.m2.amendedWaived

    When navigate to approvals manager page
    And approvals manager - search workflow agr24732.amendedWaived
    Then approvals manager - the search result tsa.Delivery.m2.amendedWaived.searchResult is existed
    When approvals manager - tick workflow tsa.Delivery.m2.amendedWaived.searchResult
    And approvals manager - approve ticked workflows

    When Go to agreement agr24732.fcm.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events tsa.call.top
    Then Exposure Management - event should be tsa.call.top.waivedApproved,tsa.call.m1.waivedApproved,tsa.Delivery.m2.waivedApproved

  @13.0.0 @JaneZhang @F6561 @T26522 @SubstitutionEvent @Reviewed
  Scenario: Verify make bulk booking to change Sub Request event workflow Net agreement Complete
  """
      Test data:
      Event link to Two or more instrument

      Need to check:
      - Bulk booking page: First leg Auto pop linked instument and not editable
      - Movement is Recall and delivery, not editable
      - Add icon available for last recall line and available for Delivery line
      - Delete icon  avalible for each lines
      - Abs(Leg1) >= Abs(Leg2) Event Call Status change to Complete
  """
    When Go to agreement agr26522.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column Source,bookedAmountFirstLeg,bookedAmountSecondLeg
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26522_recall
    And Exposure Management - create adhoc substitution events bond_usd_delivery_4000000
    Then Exposure Management - event should be substitutionRequest.bond.usd.created

    When Exposure Management - tick events substitutionRequest.bond.usd.created
    And Exposure Management - manual send substitution request letter createSubstitutionRequestLetter
    Then Exposure Management - event should be substitutionRequest.bond.usd.RequestIssued
    When Exposure Management - set event substitutionRequest.element.withoutCashGBP value to substitutionRequest.element.addCashGBP
    And Exposure Management - tick events substitutionRequest.bond.usd.tick
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking Recall.bond.usd.notional.2m,Recall.cash.GBP.notional.2m,Delivery.bond.gbp.notional.1m,Delivery.cash.usd.notional.4m
    And quick link - save
    Then Exposure Management - event should be substitutionRequest.bond.usd.completed.leg1.6m.leg2.6m

    When Go to agreement agr26522.net statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.delivery.4m
    Then delivery booking in booking editor page should be booking.checkEventId
    When asset booking edit - click cancel button
    When click back button to previous page
    And view asset type asset.type.CASH.GBP agreement asset CASH Page
    And open asset booking editor page CASH.GBP.Recall.2m
    Then recall booking in booking editor page should be booking.checkEventId
    When asset booking edit - click cancel button
    When click back button to previous page
    And view asset type asset.type.bond.USD agreement asset Bond Page
    And open asset booking editor page bond.usd.recall.2m
    Then delivery booking in booking editor page should be booking.checkEventId
    When asset booking edit - click cancel button
    And open asset booking editor page bond.gbp.delivery.1m
    Then delivery booking in booking editor page should be booking.checkEventId

      @13.0.0 @JaneZhang @F6561 @T26527 @SubstitutionEvent @Reviewed
  Scenario: Verify make bulk booking to change Sub Confirmation event workflow Net agreement Partially booked
  """
    Test data:
    Event link to Two or more instrument

    Need to check:
    - Bulk booking page: First leg Auto pop linked instument and not editable
    - Movement is Return and Call, not editable
    - Add icon available for last recall line and available for Delivery line
    - Delete icon  avalible for each lines
    - Abs(Leg1) > Abs(Leg2) Event Call Status change to Partially booked
  """

    When navigate to security search page
    And search security 26527.bond.usd.1
    And edit security 26527.bond.usd.approve to 26527.bond.usd.changeMaturityDateAndRecordDate.tPlus5
    And approve security 26527.bond.usd.approve
    And edit security 26527.bond.usd.2.approve to 26527.bond.usd.2.changeMaturityDateAndRecordDate.tPlus5
    And search security 26527.bond.usd.2
    And approve security 26527.bond.usd.2.approve

    When Go to agreement agr26527.net statement page by URL
    And approve agreement agr26527
    And quick link - agreement exposure management
    And Exposure Management - add column Source
    Then Exposure Management - event should be substitutionConfirmed.bond.usd.1.sourceSystem,substitutionConfirmed.bond.usd.2.sourceSystem
    When Exposure Management - tick events substitutionConfirmed.bond.usd.1.sourceSystem,substitutionConfirmed.bond.usd.2.sourceSystem
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be 26527.bond.usd.return,26527.bond.usd.call,26527.bond.usd.2.return,26527.bond.usd.2.call
    When Edit booking - bulk booking 26527.bond.usd.return.3m,26527.bond.usd.call.1m,26527.bond.usd.2.call.2m
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.bond2.leg1Andleg2Difference.1m
    Then Exposure Managerment - EM booking message should be warning.message.bond1.leg1Andleg2Difference.-2m
    Then Exposure Managerment - EM booking message should be warning.message.notional.0
    When Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be substitutionConfirmed.bond.usd.1.PartiallyBooked,substitutionConfirmed.bond.usd.2.PartiallyBooked

  @13.0.0 @JaneZhang @F6561 @T26531 @SubstitutionEvent @Reviewed
  Scenario: Verify make bulk booking to change Sub Request event workflow Not Net agreement Partially booked
  """
      Test data:
      Event Requirement amount come from collateral booking and movement booking, and have VM amount and IM amount
      Need to check:
      - Bulk booking page: First leg Auto pop linked instument and not editable
      - Movement is Recall and delivery, not editable
      - Add icon available for last recall line and available for Delivery line
      - Delete icon only avalible for user manual added lines
      - Only Linked intrument can ajax search out in first leg for new date input line
      - Only Cpty eligble asset type can ajax search out in second leg
      - Delete icon works for user manually create line
      - IM Abs(Leg1) >= Abs(Leg2) and VM Abs(Leg1) < Abs(Leg2) Event Call Status change to partial book
  """
    When Go to agreement agr26531.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column Source,bookedAmountFirstLeg,bookedAmountSecondLeg
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26531_recall
    And Exposure Management - create adhoc substitution events bond.usd.1.recall
    Then Exposure Management - event should be substitutionRequest.bond.usd.created

    When Exposure Management - tick events substitutionRequest.bond.usd.created
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be firstLeg.Recall.bond.usd.vm.0.im.0,SecondLeg.Delivery
    And Exposure Management - SecondLeg.Delivery should match ajax search for bond.usd.AjaxSearchCheck,cash.AjaxSearchCheck
    When Edit booking - bulk booking Recall.bond.usd.vmNotional.2m,Recall.bond.usd.imNotional.2m,Delivery.cash.usd.vmNotional.4m
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.leg1Andleg2Difference.-2m
    When Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be substitutionRequest.bond.usd.PartiallyBooked.firstLeg.4m.secondLeg.-4m

    When Exposure Management - tick events substitutionRequest.bond.usd.PartiallyBooked.firstLeg.4m.secondLeg.-4m
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be firstLeg.Recall.bond.usd.vm.0.im.0,SecondLeg.Delivery
    And EM Bulk booking should be Recall.bond.usd.booking1.readonly,Recall.bond.usd.booking2.readonly,Delivery.cash.usd.booking3.readonly
    And Exposure Management - SecondLeg.Delivery should match ajax search for bond.usd.AjaxSearchCheck,cash.AjaxSearchCheck
    When Edit booking - bulk booking Recall.bond.usd.vmNotional.1m,Recall.bond.usd.imNotional.2m.newlyAdd,Delivery.cash.usd.vmNotional.1m,Delivery.cash.usd.imNotional.2m
    And quick link - save
    Then Exposure Managerment - EM booking message should be warning.message.leg1Andleg2Difference.-2m
    When Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be substitutionRequest.bond.usd.PartiallyBooked.firstLeg.7m.secondLeg.-7m

  @2012.2 @JaneZhang @F3641 @T20602 @MakeBooking @Reviewed
  Scenario: Verify Bulk Booking shown and works fine for Non Net and Multi model agreement
    When Go to agreement agr20602.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vmCall.top.2410000
    Then Exposure Management - event should be vmCall.top.2410000,vmCall.m1.1000000,vmCall.m2.1200000

    When Exposure Management - tick events vmCall.top.2410000
    Then Exposure Management - bulkBooking.notdisplay action should be not displayed
    When Exposure Management - untick events vmCall.top.2410000
    And Exposure Management - tick events vmCall.m1.1000000
    Then Exposure Management - bulkBooking action should be displayed
    When Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.jpy.requirementAmount.apply
    And quick link - save
    And Exposure Management - expand events vmCall.top.2410000
    Then Exposure Management - event should be vmCall.top.2410000,vmCall.m1.1000000.completed,vmCall.m2.1200000
    When Exposure Management - tick events vmCall.m2.1200000
    Then Exposure Management - bulkBooking action should be displayed
    When Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.eur.requirementAmount.apply
    And quick link - save
    And Exposure Management - expand events vmCall.top.2410000.completed
    Then Exposure Management - event should be vmCall.top.2410000.completed,vmCall.m1.1000000.completed,vmCall.m2.1200000.completed

    When Go to agreement agr20602.multiModel statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    Then asset holding detail should be CASH.EUR.vm.call.pending.1.2m
    When click back button to previous page
    And view asset type asset.type.bond.jpy agreement asset Bond Page
    Then asset holding detail should be bond.jpy.vm.recall.pending.1m


  @13.0.0;14.0.0 @JaneZhang @F6978#F8949 @T26513 @SubEventAdHoc @Reviewed
  Scenario: Verify Ad hoc sub events display correct in EM for Multi Model agreement
    When navigate to security search page
    And search security 26513.bond.usd
    And edit security 26513.bond.usd.approve to 26513.bond.usd.changeRecordDate.tPlus8
    And approve security 26513.bond.usd.approve
    And search security 26513.equity.usd
    And edit security 26513.equity.usd.approve to 26513.equity.usd.changeRecordDate.tMinus8
    And search security 26513.equity.usd
    And approve security 26513.equity.usd.approve

    When Go to agreement agr26513.multiModel.notnet statement page by URL
    And approve agreement agr26513
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26513.return
    And Exposure Management - create substitution event - expand events agr26513.expand
    Then Exposure Management - substitution event should be m1.bond.usd.vm.1m,m1.equity.usd.vm.2m,m1.cash.usd.vm.3m
    And Exposure Management - substitution event should be m2.bond.usd.im.1m,m2.equity.usd.im.2m,m2.cash.usd.im.3m
    When Exposure Management - create adhoc substitution events m1.bond.usd.vm.1m,m2.equity.usd.im.2m,m2.cash.usd.im.3m
    And Exposure Management - expand events subConfirmation.top.requirementAmount.0
    Then Exposure Management - event should be subConfirmation.top.requirementAmount.0,subConfirmation.m1.bond.usd.requirementAmount.0,subConfirmation.o2.cash.usd.requirementAmount.0,subConfirmation.o2.equity.usd.requirementAmount.0

  @13.0.0;14.0.0 @JaneZhang @F6978#F8949 @T29481 @SubEventAdHoc @Reviewed
  Scenario: Verify Adhoc sub events display correct in EM for ETF agreement
    When navigate to security search page
    And search security 29481.bond.usd
    And edit security 29481.bond.usd.approve to 29481.bond.usd.changeRecordDate.tPlus8
    And approve security 29481.bond.usd.approve
    And search security 29481.equity.usd
    And edit security 29481.equity.usd.approve to 29481.equity.usd.changeRecordDate.tMinus8
    And search security 29481.equity.usd
    And approve security 29481.equity.usd.approve

    When Go to agreement agr29481.etf statement page by URL
    And approve agreement agr29481
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr29481.return
    And Exposure Management - create substitution event - expand events agr29481.expand
    Then Exposure Management - substitution event should be o1.bond.usd.vm.1m,o1.equity.usd.vm.2m,o1.cash.usd.vm.3m
    And Exposure Management - substitution event should be o2.bond.usd.vm.1m,o2.equity.usd.vm.2m,o2.cash.usd.vm.3m
    When Exposure Management - create adhoc substitution events o1.bond.usd.vm.1m,o2.equity.usd.vm.2m,o2.cash.usd.vm.3m
    And Exposure Management - expand events subConfirmation.top.requirementAmount.0
    Then Exposure Management - event should be subConfirmation.top.requirementAmount.0,subConfirmation.o1.bond.usd.requirementAmount.0,subConfirmation.m2.cash.usd.requirementAmount.0,subConfirmation.m2.equity.usd.requirementAmount.0

  @13.1.0;14.0.0 @JaneZhang @F6978#F8949 @T29482 @SubEventCorpAction @Proxied @changetoReportcompare
  Scenario: Verify corp action sub events display correct in EM for ETF agreement
    When navigate to security search page
    And search security 29482.bond.usd
    And edit security 29482.bond.usd.approve to 29482.bond.usd.changeRecordDate.tPlus8
    And approve security 29482.bond.usd.approve
    And search security 29482.equity.usd
    And edit security 29482.equity.usd.approve to 29482.equity.usd.changeRecordDate.tMinus8
    And search security 29482.equity.usd
    And approve security 29482.equity.usd.approve

    When Go to agreement agr29482.etf statement page by URL
    And approve agreement agr29482
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub,model
    And Exposure Management - expand events subConfirm.top.agreementReqiuement.-2m,subConfirm.top.agreementReqiuement.-4m
    Then Exposure Management - event should be subConfirm.top.agreementReqiuement.-2m,subConfirm.top.agreementReqiuement.-4m,subConfirm.o1.agreementReqiuement.-2m.reasonForSub.RecordDatedue,subConfirm.o2.agreementReqiuement.-2m.reasonForSub.RecordDatedue,subConfirm.o1.agreementReqiuement.-1m.reasonForSub.InstrumentEligibilityRuleNotMet,subConfirm.o2.agreementReqiuement.-1m.reasonForSub.InstrumentEligibilityRuleNotMet

    When navigate to Repo Daily Exposure Report
    And search Repo Daily Exposure Report agr29482.RepoDailyExposureReport.search
    And run report to repoEtfsblDailyExporesureReportPath as Excel Worksheet
    Then assert Excel Worksheet report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as CSV
#    Then assert CSV report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as XML
#    Then assert XML report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule

    When navigate to Repo Historical Exposure Report
    And search Repo Historical Exposure Report agr29482.RepoHistoricalExposure.search
    And run report to repoEtfsblHistoricalExporesureReportPath as Excel Worksheet
    Then assert Excel Worksheet report repoEtfsblHistoricalExporesureReportPath should follow the rule repoEtfsblHistoricalExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as CSV
#    Then asset CSV report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as XML
#    Then asset XML report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule

    When navigate to Ineligible Asset Report
    And search Ineligible Asset Report agr29482.IneligibleAssetReport.search
    And run report to IneligibleAssetReportPath as Excel Worksheet
    Then assert Excel Worksheet report IneligibleAssetReportPath should follow the rule IneligibleAssetReport.rule
#    And run report to IneligibleAssetReport as CSV
#    Then asset CSV report IneligibleAssetReport should follow the rule IneligibleAssetReport.rule
#    And run report to IneligibleAssetReport as XML
#    Then asset XML report IneligibleAssetReport should follow the rule IneligibleAssetReport.rule


  @13.1.0;14.0.0 @JaneZhang @F6978#F8949 @T26495 @SubEventCorpAction @Reviewed
  Scenario: Verify succeed to create substitution request event due to corporate action via update security
    When Go to agreement agr26495.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub,source
    Then Exposure Management - event should be recall.agreementReqiuement.4m

    When navigate to security search page
    And search security 26495.bond.usd.1
    And edit security 26495.bond.usd.1.approve to 26495.bond.usd.1.changeRecordDate.tMinus5.MaturityDate.tPlus1000
    And approve security 26495.bond.usd.1.approve
    And search security 26495.equity.usd.1
    And edit security 26495.equity.usd.1.approve to 26495.equity.usd.1.changeRecordDate.tPlus1000.MaturityDate.tPlus5
    And approve security 26495.equity.usd.1.approve
    And search security 26495.equity.usd.2
    And edit security 26495.equity.usd.2.approve to 26495.equity.usd.2.changeRecordDate.tPlus1000.MaturityDate.tMinus5
    And approve security 26495.equity.usd.2.approve

    When Go to agreement agr26495.net statement page by URL
    And approve agreement agr26495
    And quick link - agreement exposure management
    And Exposure Management - substitution filters - search sub request agr26495.search
    And Exposure Management - add column reasonForSub,source
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue,equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue
    When Exposure Management - tick events bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue
    And Exposure Management - manual send substitution request letter createSubstitutionRequestLetter
    And Exposure Management - tick events equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue
    And Exposure Management - manual send substitution request letter createSubstitutionRequestLetter
    And Exposure Management - substitution filters - search issued agr26495.search
    And Exposure Management - add column reasonForSub,source
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.issued,equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue.issued

    When Exposure Management - tick events bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.issued,equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue.issued
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.1.recall.1m,bond.usd.2.delivery.1m,equity.usd.1.recall.1.2m,bond.usd.2.delivery.1.2m
    And quick link - save
    And Exposure Management - substitution filters - search completed agr26495.search
    And Exposure Management - add column reasonForSub,source
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.completed,equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue.completed
    When Exposure Management - set event bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.completed value to bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.waived
    And Exposure Management - save changes
    And Exposure Management - substitution filters - search waived agr26495.search
    And Exposure Management - add column reasonForSub,source
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.waived

    When Exposure Management - all filters - search dynamic filter agr26495.search
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.waived,equity.usd.1.subRequest.requirementAmount.2m.reasonForSub.MaturityDue.completed

    When Exposure Management - User Filters - add user filter subFilter.waived
    And Exposure Management - add column reasonForSub,source
    And Exposure Management - user filter - search agr26495.addedUserFilter filter agr26495.search
    Then Exposure Management - event should be bond.usd.1.subRequest.requirementAmount.1m.reasonForSub.RecordDatedue.waived

    When Exposure Management - all filters - search dynamic filter agr26495.search
    Then Exposure Management - column reasonForSub,source should be added in left panel in select columns dialog


  @13.0.0;14.0.0;14.1.3.4.3;14.1.7 @JaneZhang @F6978#F8949#D35701#D36374 @T26493 @SubEventAdHoc @Proxied @reportCompare
  Scenario: Verify succeed to create substitution request event via Create Substitution icon
    When navigate to security search page
    And search security 26493.bond.usd
    And edit security 26493.bond.usd.approve to 26493.bond.usd.changeRecordDate.tPlus8
    And approve security 26493.bond.usd.approve
    And search security 26493.equity.usd
    And edit security 26493.equity.usd.approve to 26493.equity.usd.changeRecordDate.tPlus7
    And approve security 26493.equity.usd.approve

    When Go to agreement agr26493.fcm statement page by URL
    And approve agreement agr26493
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub,source
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26493.recall
    Then Exposure Management - substitution event should be cash.usd.notional.3.5m.vmNotional.2m.imNotional.1.5m,bond.usd.notional.1.5m.vmNotional.0.7m.imNotional.0.8m,equity.usd.notional.2m.vmNotional.1m.imNotional.1m
    When Exposure Management - create adhoc substitution events cash.usd.notional.3.5m.vmNotional.2m.imNotional.1.5m,bond.usd.notional.1.5m.vmNotional.0.7m.imNotional.0.8m,equity.usd.notional.2m.vmNotional.1m.imNotional.1m
    Then Exposure Management - event should be subRequest.reasonForSub.AssetTypeNotEligibleagreement.requirement.3.5m.element.cash,subRequest.reasonForSub.blank.requirement.0m.element.equity,subRequest.reasonForSub.blank.requirement.0m.element.cash,subRequest.reasonForSub.blank.requirement.0m.element.bond,subRequest.reasonForSub.RecordDateDue.requirement.2m.element.equity

    When navigate to Daily Exposure Report
    And search Daily Exposure Report agr26493.DailyExposureReport.search
    And run report to repoDailyExporesureReportPath as Excel Worksheet
    Then assert Excel Worksheet report repoDailyExporesureReportPath should follow the rule repoDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as CSV
#    Then asset CSV report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as XML
#    Then asset XML report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule

    When navigate to Historical Exposure Report
    And search Historical Exposure Report agr26493.HistoricalExposure.search
    And run report to HistoricalExporesureReportPath as Excel Worksheet
    Then assert Excel Worksheet report HistoricalExporesureReportPath should follow the rule HistoricalExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as CSV
#    Then asset CSV report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule
#    And run report to repoEtfsblDailyExporesureReportPath as XML
#    Then asset XML report repoEtfsblDailyExporesureReportPath should follow the rule repoEtfsblDailyExporesureReport.rule

    When navigate to Ineligible Asset Report
    And search Ineligible Asset Report agr26493.IneligibleAssetReport.search
    And run report to IneligibleAssetReportPath as Excel Worksheet
    Then assert Excel Worksheet report IneligibleAssetReportPath should follow the rule IneligibleAssetReport.rule
#    And run report to IneligibleAssetReport as CSV
#    Then asset CSV report IneligibleAssetReport should follow the rule IneligibleAssetReport.rule
#    And run report to IneligibleAssetReport as XML
#    Then asset XML report IneligibleAssetReport should follow the rule IneligibleAssetReport.rule


  @2011.1;2011.2 @JaneZhang @T10879 @SystemCalculatedAmount&UserAgreedAmount @Reviewed
  Scenario: Verify that Sys Calculated Amount is correct for Call Action
    When Go to agreement agr10879.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.requirementAmount.1236000
    When Exposure Management - set event call.requirementAmount.1236000 value to call.requirementAmount.1236000.counterpartyAmount.3m
    And Exposure Management - save changes
    Then Exposure Management - event should be call.SysCalculatedAmount.3m
    When Exposure Management - set event call.requirementAmount.1236000 value to call.requirementAmount.1236000.counterpartyAmount.0.5m
    And Exposure Management - save changes
    Then Exposure Management - event should be call.SysCalculatedAmount.1236000
    When Exposure Management - set event call.requirementAmount.1236000 value to call.requirementAmount.1236000.counterpartyAmount.1236500
    And Exposure Management - save changes
    Then Exposure Management - event should be call.SysCalculatedAmount.1236500
    When Exposure Management - set event call.requirementAmount.1236000 value to call.requirementAmount.1236000.counterpartyAmount.1235500
    And Exposure Management - save changes
    Then Exposure Management - event should be call.SysCalculatedAmount.1236000

  @2012.1 @JaneZhang @T19400 @F1217 @SystemCalculatedAmount&UserAgreedAmount @bulkbookingChangetoMakeBooking @wip @Reviewed
  Scenario: Verify that succeed to overwrite Abs recall user agreed amt less than or equal to MaxRecall amt for call recall event
    When Go to agreement agr19400.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.requirementAmount.10m,recall.requirementAmount.17m

    When Exposure Management - set event call.requirementAmount.10m value to call.requirementAmount.10m.userAgreedAmount.6m
    And Exposure Management - set event recall.requirementAmount.17m value to recall.requirementAmount.17m.userAgreedAmount.14m
    And Exposure Management - save changes
    And Exposure Management - tick events call.requirementAmount.10m,recall.requirementAmount.17m
    And Exposure Management - create letter
    Then letter set up detail should be call.agreementRequirementAmt.10m.agreedAmt.6m
    When Letter setup - click Agreement Admin
    Then letter set up detail should be call.agreementRequirementAmt.17m.agreedAmt.14m
    When Letter setup - click Agreement Admin
    Then letter set up detail should be callAndRecall.agreementRequirementAmtCall.10m.agreementRequirementAmtRecall.17m.agreedAmtCall.6m.agreedAmtRecall.14m.agreedAmtNet.20m
    When Letter setup - click Agreement Statement
    And quick link - agreement exposure management
    When Exposure Management - set event call.requirementAmount.10m value to call.requirementAmount.10m.userAgreedAmount.2m
    And Exposure Management - set event recall.requirementAmount.17m value to recall.requirementAmount.17m.userAgreedAmount.17m
    And Exposure Management - save changes
    And Exposure Management - tick events call.requirementAmount.10m,recall.requirementAmount.17m
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be call.value.2m.requiredAmount.2m,recall.value.17m.requiredAmount.17m
    When Exposure Managerment - cancel bulk booking
    And Exposure Management - set event call.requirementAmount.10m value to call.requirementAmount.10m.userAgreedAmount.-6m
    And Exposure Management - set event recall.requirementAmount.17m value to recall.requirementAmount.17m.userAgreedAmount.-13m
    And Exposure Management - save changes
    And Exposure Management - tick events call.requirementAmount.10m,recall.requirementAmount.17m
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be call.value.6m.requiredAmount.6m,recall.value.13m.requiredAmount.13m

  @13.0.0 @JaneZhang @T27785 @F7717 @EMWorkflow @COL-4596 @wip @Reviewed
  Scenario: Verify event workflow of Auto Partial Dispute Dispute Approval on Apply 4 eye on Multi Model
  """
      1.Workflow Approvals-Dispute is on, Apply 4-eye is on
      2.Multi-Model, VM Delivery&Return event.
      3.User1: Cpty amount > 0 => make booking1 linked to event by ExtAssetManager
          -Call Status = Pending Review, Wrk Status = Amend-Dispute
          -API User can update call status ,but can not update wrk status or approve wrk status in Approval Manager
          -User1 cannot update call status ,but can approve wrk status
      4.User2:  make booking2 linked to event
          -cannot update call status ,but can approve wrk status
  """

    When navigate to collateral preferences page
    And set collateral preferences setWorkflowDisbute.WorkflowApplyFourEyes

    When Go to agreement agr27785.multimodel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.agreementRequirement.3110759.37,vm.return.m2.agreementRequirement.0.5m,vm.return.m1.agreementRequirement.1m,vm.delivery.m2.agreementRequirement.0.3m,vm.delivery.m1.agreementRequirement.1m
    When Exposure Management - set event vm.return.m2.agreementRequirement.0.5m value to vm.return.m2.agreementRequirement.0.5m.counterpartyAmount.-0.55m
    And Exposure Management - set event vm.return.m1.agreementRequirement.1m value to vm.return.m1.agreementRequirement.1m.counterpartyAmount.-1m
    And Exposure Management - set event vm.delivery.m2.agreementRequirement.0.3m value to vm.delivery.m2.agreementRequirement.0.3m.counterpartyAmount.-0.3m
    And Exposure Management - set event vm.delivery.m1.agreementRequirement.1m value to vm.delivery.m1.agreementRequirement.1m.counterpartyAmount.-1.1m
    And Exposure Management - save changes
    And Exposure Management - expand events vm.delivery.top
    And Exposure Management - tick events vm.return.m2.agreementRequirement.0.5m.afterChangeCounterpartyAmount,vm.return.m1.agreementRequirement.1m.afterChangeCounterpartyAmount
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking vm.return.m2.0.5m,vm.return.m1.0.5m
    And quick link - save
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.disputeValue.169422.46,vm.return.m2.disputeValue.50k,vm.return.m1.disputeValue.100k,vm.delivery.m2.disputeValue.50k,vm.delivery.m1.disputeValue.100k
#  vm.delivery.m2.disputeValue.50k and vm.return.m1.disputeValue.100k should be 0, not sure if it is a bug
    When navigate to approvals manager page
    And approvals manager - search workflow agr27785
    Then approvals manager - the search result searchResult.delivery.0.3m,searchResult.return.0.5m,searchResult.delivery.1m,searchResult.return.1m cannot be checked

    When switch user to loginWithAnotherUser
    And Go to agreement agr27785.multimodel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.disputeValue.169422.46,vm.return.m2.disputeValue.50k,vm.return.m1.disputeValue.100k,vm.delivery.m2.disputeValue.50k,vm.delivery.m1.disputeValue.100k
    And Exposure Management - tick events vm.delivery.m2.agreementRequirement.0.3m.afterChangeCounterpartyAmount,vm.delivery.m1.agreementRequirement.1m.afterChangeCounterpartyAmount
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking vm.delivery.m2.1m,vm.delivery.m1.0.3m
    And quick link - save
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.disputeValue.169422.46,vm.return.m2.disputeValue.50k,vm.return.m1.disputeValue.100k,vm.delivery.m2.disputeValue.50k,vm.delivery.m1.disputeValue.100k

    When navigate to approvals manager page
    And approvals manager - search workflow agr27785
    And approvals manager - tick workflow searchResult.delivery.0.3m,searchResult.return.0.5m
    And approvals manager - approve ticked workflows
    And Go to agreement agr27785.multimodel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.disputeValue.169422.46,vm.return.m2.disputeValue.50k.approved.callStatus.PartialDispute,vm.return.m1.disputeValue.100k,vm.delivery.m2.disputeValue.50k.approved.callStatus.PartialDispute,vm.delivery.m1.disputeValue.100k

    When navigate to approvals manager page
    And approvals manager - search workflow agr27785
    And approvals manager - tick workflow searchResult.delivery.1m,searchResult.return.1m
    And approvals manager - approve ticked workflows
    And Go to agreement agr27785.multimodel.notnet statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events vm.delivery.top
    Then Exposure Management - event should be vm.delivery.net.disputeValue.169422.46.amendedDispute,vm.return.m2.disputeValue.50k.approved.callStatus.PartialDispute,vm.return.m1.disputeValue.100k.approved.callStatus.PartialDispute,vm.delivery.m2.disputeValue.50k.approved.callStatus.PartialDispute,vm.delivery.m1.disputeValue.100k.approved.callStatus.PartialDispute

  @2012.1 @JaneZhang @T24368 @COL-4495 @F5843 @Reviewed
  Scenario: Check TSA Delivery Excess event workflow in EM TSA Level is Component
    When Go to agreement agr24368.fcm statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.nocall,vm.call.agreementRequirement.4k,tsa.delivery.agreementRequirement.4k
    When Exposure Management - tick events tsa.delivery.agreementRequirement.4k
    Then Exposure Management - makeBooking,makeInstrumentBooking action should be not displayed
    When Exposure Management - all filters - search dynamic filter delivery.search
    Then Exposure Management - event should be tsa.delivery.agreementRequirement.4k
    When Exposure Management - all filters - search dynamic filter clearAction.search
    And Exposure Management - tick events vm.call.agreementRequirement.4k,tsa.delivery.agreementRequirement.4k
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be vm.call.value.4k,tsa.delivery.OtherCashAdjustments.pay.value.1m,tsa.delivery.BankedCoupon.pay.value.1m
    Then EM Bulk booking should be tsa.delivery.IMInterest.pay.value.1m,tsa.delivery.UpfrontFee.receive.value.1m
    And Edit booking - bulk booking tsa.delivery.OtherCashAdjustments.pay.notional.900,tsa.delivery.BankedCoupon.pay.notional.1000,tsa.delivery.IMInterest.pay.notional.3100,tsa.delivery.UpfrontFee.receive.notional.1000
    And quick link - save
    Then Exposure Management - event should be tsa.delivery.agreementRequirement.4k.completed

  @2011.1;2011.2 @JaneZhang @T10924 @COL-3840 @Reviewed
  Scenario: Verify that if four eyes of workflow status is on any amended or amended waived events need approved by other users
    When navigate to collateral preferences page
    And set collateral preferences setWorkflowDisbute.WorkflowApplyFourEyes
    When Go to agreement agr10924.amended statement page by URL
    And quick link - agreement exposure management
    When Exposure Management - all filters - search dynamic filter agr10924.amended.amendedWaived.approved.search
    Then Exposure Management - event should be agr10924.amended.wrkStatusDisabled,agr10924.amendedWaived.wrkStatusDisabled,agr10924.approved.wrkStatusDisabled

    When switch user to loginWithUserB
    And navigate to collateral preferences page
    Then collateral preferences should be setWorkflowDisbute.WorkflowApplyFourEyes
    When Go to agreement agr10924.amended statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - all filters - search dynamic filter agr10924.amended.amendedWaived.approved.search
    Then Exposure Management - event should be agr10924.amended.wrkStatusEnabled,agr10924.amendedWaived.wrkStatusEnabled,agr10924.approved.wrkStatusEnabled
    When Exposure Management - set event agr10924.amended.wrkStatusEnabled value to agr10924.amended.wrkStatusEnabled.changeToApproved
    And Exposure Management - set event agr10924.amendedWaived.wrkStatusEnabled value to agr10924.amendedWaived.wrkStatusEnabled.changeToApproved

    When navigate to collateral preferences page
    And set collateral preferences setWorkflowDisbute.NotTickWorkflowApplyFourEyes
    When switch user to loginWithUserA
    And Go to agreement agr10924.amended statement page by URL
    When navigate to collateral preferences page
    Then collateral preferences should be setWorkflowDisbute.NotTickWorkflowApplyFourEyes

    When Go to agreement agr10924.amended statement page by URL
    And quick link - agreement exposure management
    When Exposure Management - all filters - search dynamic filter agr10924.amended.amendedWaived.approved.search
    Then Exposure Management - event should be agr10924.amended.wrkStatusEnabled,agr10924.amendedWaived.wrkStatusEnabled,agr10924.approved.wrkStatusEnabled
    When Exposure Management - set event agr10924.amended.wrkStatusEnabled value to agr10924.amended.wrkStatusEnabled.changeToApproved
    And Exposure Management - set event agr10924.amendedWaived.wrkStatusEnabled value to agr10924.amendedWaived.wrkStatusEnabled.changeToApproved
    And Exposure Management - save changes
    Then Exposure Management - event should be agr10924.amended.wrkStatusEnabled.changeToApproved,agr10924.amended.wrkStatusEnabled.changeToApproved

  @14.0 @JaneZhang @T30763 @F7836 @COL-5061 @Reviewed
  Scenario: Verify flush all booking linked to IM Delivery event
  """
      Precondition
      1)
       Try to explain how flush booking will impact Linked Margin Event
       flush all bookings linked margin eventapprove agreementrecalc agreementmargin delivery event become historical and new margin event is generated
       note
       Currently only Cancel or Cancel & Rebooking event linked booking will need to revert call status of linked booking.

      For other ways leads collateral value of event linked booking to zero will not impacted linked event at all. Like:
       Price change
       Asset matured
       Asset becomes ineligible
       Haircut change
       Booking Flushed
       Test Calls
  """

    When Go to agreement agr30763.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.1m,noCallRequired
    When Exposure Management - tick events im.delivery.1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking im.delivery.imNotional.0.5m,im.delivery.imNotional.0.5m.2
    And quick link - save
    Then Exposure Management - event should be im.delivery.1m.completed

    When navigate to task scheduler page
    And task scheduler - switch to Workflow tab
    And edit task scheduler flushBookingforAgr30763
    And run task scheduler flushBookingforAgr30763
    Then task scheduler messaging should be flushBookingforAgr30763.success

    When Go to agreement agr30763.otc.notnet statement page by URL
    Then agreement statement should be agr30763.amended.collateralHolding.0
    When quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.1m.completed

    When Go to agreement agr30763.otc.notnet statement page by URL
    And approve agreement agr30763
    And recalculate agreement statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.1m.pendingReview,im.delivery.1m.completed.greyOut

  @13.0.0,14.0.0,14.1.7,14.1.3.4.3 @JaneZhang @T26511 @F6978#F8949#D35701#D36374 @COL-5091 @AdhocSub @Reviewed
  Scenario: Verify Ad hoc sub events display correct in EM for Umbrella agreement collateral at F
    When navigate to security search page
    And search security 26511.bond.usd
    And edit security 26511.bond.usd.approve to 26511.bond.usd.changeRecordDate.tPlus8
    And approve security 26511.bond.usd.approve
    And search security 26511.equity.usd
    And edit security 26511.equity.usd.approve to 26511.equity.usd.changeRecordDate.tPlus7
    And approve security 26511.equity.usd.approve

    When Go to agreement agr26511.umb.collateralAtFund statement page by URL
    And approve agreement agr26511
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26511.recall
    And Exposure Management - create substitution event - expand events 26511.umb.subEvent
    Then Exposure Management - substitution event should be equity.usd.notional.-2m.vmNotional.-2m.imNotional.0m,bond.usd.notional.-1m.vmNotional.0m.imNotional.-1m,cash.usd.notional.-3m.vmNotional.0m.imNotional.-3m
    When Exposure Management - create adhoc substitution events equity.usd.notional.-2m.vmNotional.-2m.imNotional.0m,bond.usd.notional.-1m.vmNotional.0m.imNotional.-1m
    And Exposure Management - expand events agr26511.subevent.top
    And Exposure Management - set event subRequest.f1.callStatus.pendingReview value to subRequest.f1.callStatus.RequestIssued
    And Exposure Management - set event subRequest.f1.callStatus.RequestIssued value to subRequest.f1.callStatus.pendingReview
    And Exposure Management - save changes
    And Exposure Management - expand events agr26511.subevent.top
    And Exposure Management - tick events subRequest.f1.callStatus.pendingReview
    And Exposure Management - manual send substitution request letter subRequest.createSubstitutionRequestLetter
    And Exposure Management - expand events agr26511.subevent.top
    Then Exposure Management - event should be subRequest.f1.callStatus.RequestIssued

    When Exposure Management - set event subRequest.f2.callStatus.pendingReview value to subRequest.f2.callStatus.RequestIssued
    And Exposure Management - set event subRequest.f2.callStatus.RequestIssued value to subRequest.f2.callStatus.pendingReview
    And Exposure Management - save changes
    And Exposure Management - expand events agr26511.subevent.top
    And Exposure Management - tick events subRequest.f2.callStatus.pendingReview
    And Exposure Management - manual send substitution request letter subRequest.createSubstitutionRequestLetter
    And Exposure Management - expand events agr26511.subevent.top
    Then Exposure Management - event should be subRequest.f2.callStatus.RequestIssued,agr26511.subevent.top.RequestIssued

    When Exposure Management - tick events subRequest.f1.callStatus.RequestIssued
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking subReq.firstLeg.vmNotional.2m,subReq.secondLeg.vmNotional.2m
    And quick link - save
    And Exposure Management - expand events agr26511.subevent.top
    Then Exposure Management - event should be subRequest.f1.callStatus.completed

    When Exposure Management - tick events subRequest.f2.callStatus.RequestIssued
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking subReq.firstLeg.imNotional.1m,subReq.secondLeg.imNotional.1m
    And quick link - save
    And Exposure Management - expand events agr26511.subevent.top
    Then Exposure Management - event should be subRequest.f2.callStatus.completed

    When Go to agreement agr26511.umb.collateralAtFund statement page by URL
    And recalculate agreement statement
    Then agreement statement should be noErrorDisplayed

  @13.0.0.SP @JaneZhang @T31404 @F10624 @COL-5033 @Reviewed
  Scenario: Verify auto email for margin call or recall NOT NET Gross
    When Go to agreement agr31404.otc.notnoet.gross statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be imCall.agreementRequirement.1m.pendingReview,vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be vmRecall.agreementRequirement.2m.pendingReview,imRecall.agreementRequirement.3m.pendingReview

    When Exposure Management - tick events imCall.agreementRequirement.1m.pendingReview
    And Exposure Management - auto send letter
    Then Exposure Management - event should be imCall.agreementRequirement.1m.marginRequestIssued,vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be vmRecall.agreementRequirement.2m.pendingReview,imRecall.agreementRequirement.3m.pendingReview

    When Exposure Management - tick events imRecall.agreementRequirement.3m.pendingReview
    And Exposure Management - auto send letter
    Then Exposure Management - event should be imCall.agreementRequirement.1m.marginRequestIssued,vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be vmRecall.agreementRequirement.2m.pendingReview,imRecall.agreementRequirement.3m.marginRequestIssued

    When Exposure Management - tick events vmCall.agreementRequirement.1m.pendingReview,vmRecall.agreementRequirement.2m.pendingReview
    And Exposure Management - auto send letter
    Then Exposure Management - event should be imCall.agreementRequirement.1m.marginRequestIssued,vmCall.agreementRequirement.1m.marginRequestIssued
    And Exposure Management - event should be vmRecall.agreementRequirement.2m.marginRequestIssued,imRecall.agreementRequirement.3m.marginRequestIssued

  @13.0.0 @JaneZhang @T27951 @F7665 @COL-5027 @Reviewed
  Scenario: Verify new columns for Regulatory Margin Update in EM page should be added successfully for OTC FCM Repo agreement
    When navigate to security search page
    And search security 27951.bond.usd
    And edit security 27951.bond.usd.approve to 27951.bond.usd.changeMaturityDate.tPlus3
    And approve security 27951.bond.usd.approve
    And search security 27951.equity.usd
    And edit security 27951.equity.usd.approve to 27951.equity.usd.changeMaturityDate.tPlus3
    And approve security 27951.equity.usd.approve

    When navigate to Exposure Management
    And Exposure Management - all filters - search dynamic filter agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be removed in left panel in select columns dialog
    When Exposure Management - add column grossCalc,AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM
    Then Exposure Management - event should be agr27951_3.call.AdjustedCollateralValueForPrcAndCpty.0
    And Exposure Management - event should be agr27951_1.return.AdjustedCollateralValueForPrcAndCpty.0
    And Exposure Management - event should be agr27951_2.vmReturn.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m,agr27951_2.imReturn.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m
    And Exposure Management - event should be agr27951_3.subReq.grossCalc.null.AdjustedCollateralValueForPrcAndCpty.0,agr27951_2.subReq.grossCalc.gross.AdjustedCollateralValueForPrcAndCpty.0
    And Exposure Management - event should be agr27951_1.subCon.grossCalc.net.AdjustedCollateralValueForPrcAndCpty.0

    And Exposure Management - margin filters - search margin call agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be removed in left panel in select columns dialog
    When Exposure Management - add column grossCalc,AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM
    Then Exposure Management - event should be agr27951_3.call.AdjustedCollateralValueForPrcAndCpty.0

    And Exposure Management - margin filters - search margin recall agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be removed in left panel in select columns dialog
    When Exposure Management - add column grossCalc,AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM
    Then Exposure Management - event should be agr27951_1.recall.AdjustedCollateralValueForPrcAndCpty.0
    And Exposure Management - event should be agr27951_2.vmRecall.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m,agr27951_2.imRecall.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m

    And Exposure Management - margin filters - search margin return agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be removed in left panel in select columns dialog
    When Exposure Management - add column grossCalc,AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM
    Then Exposure Management - event should be agr27951_1.return.AdjustedCollateralValueForPrcAndCpty.0
    And Exposure Management - event should be agr27951_2.vmReturn.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m,agr27951_2.imReturn.AdjustedCollateralValueForPrcVm.1m.im.3m.AdjustedCollateralValueForCpty.3m.im.2m

    And Exposure Management - substitution filters - search sub request agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be not displayed in left and right panel in select columns dialog
    When Exposure Management - add column grossCalc
    Then Exposure Management - event should be agr27951_3.subReq.grossCalc.null,agr27951_2.subReq.grossCalc.gross

    And Exposure Management - substitution filters - search sub confirmation agr27951_1.otc.net.agr27951_3.repo.agr27951_2.octNotnet
    Then Exposure Management - column AdjustedCollateralValuePrcVM,AdjustedCollateralValuePrcIM,AdjustedCollateralValueCptyVM,AdjustedCollateralValueCptyIM should be not displayed in left and right panel in select columns dialog
    When Exposure Management - add column grossCalc
    Then Exposure Management - event should be agr27951_1.subCon.grossCalc.net

      @13.0.0.SP5 @JaneZhang @T31320 @F10624 @COL-5021 @Reviewed
  Scenario: Verify call status will go to issued itself when send letter for call or recall event net agreement gross
    """Precondition
    R2
    Send email will process workflow to issued and now for Gross calculation agreement we should not process both events to go to margin issued when choose to send margin letter for each one event individually. If user sends joint letter then still both events should go to Issued/Confirmed:
    Manual Send Margin Letter
    · For Net Agreement

    1. Choose Margin Call:
    o Only Call event goes to issued
    o If Auto-Book rule Set only Call booking will be booked automatically

    2. Choose Margin Recall:
    o Only Recall event goes to issued
    o If Auto-Book rule set only Recall booking will be booked automatically

    3. Choose Margin Call and Recall:
    o Both Call and Recall events will go to issued per current.
    o If Auto-Book rule set both Call and Recall bookings will be booked automatically per current."""

    Given have letter configuration Recall.tickCreateSystemDraft.callandRecall.tickCreateSystemDraft
    When Go to agreement agr31320.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m.pendingReview,recall.agreementRequirement.2m.pendingReview
    When Exposure Management - tick events call.agreementRequirement.1m.pendingReview
    And Exposure Management - manual send margin call letter createCallLetter
    Then Exposure Management - event should be call.agreementRequirement.1m.MarginRequestIssued,recall.agreementRequirement.2m.pendingReview
    When Exposure Management - tick events recall.agreementRequirement.2m.pendingReview
    And Exposure Management - manual send margin recall letter createRecallLetter.norminalAmountRequired.apply.1k
    Then Exposure Management - event should be call.agreementRequirement.1m.MarginRequestIssued,recall.agreementRequirement.2m.MarginRequestIssued
    When Exposure Management - set event call.agreementRequirement.1m.MarginRequestIssued value to call.agreementRequirement.1m.pendingReview
    And Exposure Management - set event recall.agreementRequirement.2m.MarginRequestIssued value to recall.agreementRequirement.2m.pendingReview
    And Exposure Management - save changes

    When Go to agreement agr31320.otc.net statement page by URL
    And approve agreement agr31320
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m.pendingReview,recall.agreementRequirement.2m.pendingReview
    When Exposure Management - tick events call.agreementRequirement.1m.pendingReview,recall.agreementRequirement.2m.pendingReview
    And Exposure Management - manual send margin call&recall letter createCallAndRecallLetter.norminalAmountRequired.apply.2k
    Then Exposure Management - event should be call.agreementRequirement.1m.MarginRequestIssued,recall.agreementRequirement.2m.MarginRequestIssued

    When Go to agreement agr31320.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    Then asset holding detail should be bond.usd.recall.systemDraft.1k
    When Asset holding detail - collapse asset type asset.type.bond.usd
    And asset holding detail should be bond.usd.recall.systemDraft.2k

  @13.0.0 @JaneZhang @T27917 @F6978 @COL-5008 @Reviewed
  Scenario: verify call status for top level is the earliest call status of all funds umbrella agreement
    When navigate to security search page
    And search security 27917.bond.usd
    And edit security 27917.bond.usd.approve to 27917.bond.usd.changeMaturityDate.tPlus5.changeRecordDate.tMinus5
    And approve security 27917.bond.usd.approve

    When Go to agreement agr27917.repo statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events subConfirmation.top
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.pendingReview
    And Exposure Management - event should be subConfirmation.fund1.agreementRequirement.-1m.pendingReview
    And Exposure Management - event should be subConfirmation.fund2.agreementRequirement.-2m.pendingReview
    And Exposure Management - event should be subConfirmation.fund3.agreementRequirement.-3m.pendingReview
    When Exposure Management - set event subConfirmation.fund1.agreementRequirement.-1m.pendingReview value to subConfirmation.fund1.agreementRequirement.-1m.RequestConfirmed
    And Exposure Management - set event subConfirmation.fund2.agreementRequirement.-2m.pendingReview value to subConfirmation.fund2.agreementRequirement.-2m.PartiallyBooked
    And Exposure Management - save changes
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.pendingReview

    And Exposure Management - expand events subConfirmation.top
    When Exposure Management - set event subConfirmation.fund1.agreementRequirement.-1m.RequestConfirmed value to subConfirmation.fund1.agreementRequirement.-1m.PartiallyBooked
    And Exposure Management - set event subConfirmation.fund2.agreementRequirement.-2m.PartiallyBooked value to subConfirmation.fund2.agreementRequirement.-2m.Completed
    And Exposure Management - set event subConfirmation.fund3.agreementRequirement.-3m.pendingReview value to subConfirmation.fund3.agreementRequirement.-3m.RequestConfirmed
    And Exposure Management - save changes
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.RequestConfirmed

    And Exposure Management - expand events subConfirmation.top
    When Exposure Management - set event subConfirmation.fund1.agreementRequirement.-1m.PartiallyBooked value to subConfirmation.fund1.agreementRequirement.-1m.Completed
    And Exposure Management - set event subConfirmation.fund2.agreementRequirement.-2m.Completed value to subConfirmation.fund2.agreementRequirement.-2m.Waived
    And Exposure Management - set event subConfirmation.fund3.agreementRequirement.-3m.RequestConfirmed value to subConfirmation.fund3.agreementRequirement.-3m.PartiallyBooked
    And Exposure Management - save changes
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.PartiallyBooked

    And Exposure Management - expand events subConfirmation.top
    When Exposure Management - set event subConfirmation.fund3.agreementRequirement.-3m.PartiallyBooked value to subConfirmation.fund3.agreementRequirement.-3m.Completed
    And Exposure Management - save changes
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.Completed

    And Exposure Management - expand events subConfirmation.top
    When Exposure Management - set event subConfirmation.fund1.agreementRequirement.-1m.Completed value to subConfirmation.fund1.agreementRequirement.-1m.Waived
    And Exposure Management - set event subConfirmation.fund3.agreementRequirement.-3m.Completed value to subConfirmation.fund3.agreementRequirement.-3m.Waived
    And Exposure Management - save changes
    Then Exposure Management - event should be subConfirmation.top.agreementRequirement.-6m.Waived


  @13.0.0 @JaneZhang @T26535 @F6561 @COL-5007 @Reviewed
  Scenario: Verify make bulk booking to change Sub Confirmation event workflow Not Net agreement Partially booked
  """
Precondition

o Choose Margin Delivery:
 Ø Only VM and IM Delivery events will go to issued
 Ø If Auto-Book rule Set only VM and IM Delivery bookings will be booked automatically
 o Choose Margin Return:
 Ø Only VM and IM Return events will go to issues
 Ø If Auto-Book rule Set only VM and IM Return bookings will be booked automatically
 o Choose Margin Delivery and Return:
 Ø Both VM and IM Delivery and Return events will go to issues
 Ø If Auto-Book rule Set both VM and IM Delivery and Return bookings will be booked automatically

"""
    When Go to agreement agr26535.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be VM.Return.agreementRequirement.8m,IM.Return.agreementRequirement.10m

    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26535.return
    When Exposure Management - create adhoc substitution events bond.usd.1,bond.usd.2
    Then Exposure Management - event should be subConfirmation.bond.usd.1,subConfirmation.bond.usd.2
    And Exposure Management - tick events subConfirmation.bond.usd.1
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be subCon.firstLeg.bond.usd.1.return.vmAssetAvailable.4m.imAssetAvailable.5m
    And EM Bulk booking should be subCon.firstLeg.bond.usd.2.return.vmAssetAvailable.4m.imAssetAvailable.5m
    And EM Bulk booking should be subCon.secondLeg.call
    When Edit booking - bulk booking subCon.firstLeg.bond.usd.1.return.vmNotional.2m,subCon.firstLeg.bond.usd.2.return.vmNotional.2m,subCon.secondLeg.cash.usd.vmNotional.3m
    And quick link - save
    Then Exposure Managerment - EM booking message should be netValueOfItemsBookedForDeliveryNotEqualToReceipt
    When Exposure Management - override warning netValueOfItemsBookedForDeliveryNotEqualToReceipt
    And quick link - save
    Then Exposure Management - event should be subConfirmation.firstLeg.4m.secondLeg.3m.PartiallyBooked

    And Exposure Management - tick events subConfirmation.firstLeg.4m.secondLeg.3m.PartiallyBooked
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be subCon.firstLeg.bond.usd.1.historyBooking.return.vmNotional.2m
    And EM Bulk booking should be subCon.firstLeg.bond.usd.2.historyBooking.return.vmNotional.2m
    And EM Bulk booking should be subCon.secLeg.cash.historyBooking.call.vmNotional.3m
    When Edit booking - bulk booking subCon.firstLeg.bond.usd.1.return.vmNotional.1m,subCon.firstLeg.bond.usd.2.return.vmNotional.1m,subCon.secondLeg.cash.usd.vmNotional.1m
    When quick link - save
    Then Exposure Managerment - EM booking message should be netValueOfItemsBookedForDeliveryNotEqualToReceipt.vmDifference.2m
    And Exposure Management - override warning netValueOfItemsBookedForDeliveryNotEqualToReceipt.vmDifference.2m
    And quick link - save
    Then Exposure Management - event should be subConfirmation.firstLeg.6m.secondLeg.4m.PartiallyBooked

  @13.0.0.SP5 @JaneZhang @T31331 @F10624 @COL-4999 @meedchangeName
  Scenario: Verify the counterparty amount should not be auto adjusted to satisfy Recall event firstly save cpty amount manually
    Given have letter configuration return.tickCreateSystemDraft.delivery.tickCreateSystemDraft
    When Go to agreement agr31331.otc.notnet.grossIM statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.delivery.agreementRequirement.1m.pendingReview,im.delivery.agreementRequirement.1.5m.pendingReview,noCall
    When Exposure Management - tick events vm.delivery.agreementRequirement.1m.pendingReview,im.delivery.agreementRequirement.1.5m.pendingReview
    And Exposure Management - manual send margin delivery letter createDeliveryLetter
    Then Exposure Management - event should be vm.delivery.agreementRequirement.1m.MarginRequestConfirmed,im.delivery.agreementRequirement.1.5m.MarginRequestConfirmed

    When Go to agreement agr31331.otc.notnet.gross statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.return.agreementRequirement.3m.pendingReview,im.return.agreementRequirement.2m.pendingReview
    When Exposure Management - tick events vm.return.agreementRequirement.3m.pendingReview,im.return.agreementRequirement.2m.pendingReview
    And Exposure Management - manual send margin delivery letter createReturnLetter.applyIMReturn.1k.applyVMReturn.2k
    Then Exposure Management - event should be vm.return.agreementRequirement.3m.MarginRequestConfirmed,im.return.agreementRequirement.2m.MarginRequestConfirmed
    When Go to agreement agr31331.otc.notnet.gross statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    Then asset holding detail should be bond.usd.return.systemDraft.vm.2k.im.1k

    When Go to agreement agr31331.otc.notnet.grossIM.2 statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.return.agreementRequirement.3m.pendingReview.2,im.return.agreementRequirement.2m.pendingReview.2
    And Exposure Management - event should be vm.delivery.agreementRequirement.1m.pendingReview.2,im.delivery.agreementRequirement.1.5m.pendingReview.2
    When Exposure Management - tick events vm.return.agreementRequirement.3m.pendingReview.2,im.return.agreementRequirement.2m.pendingReview.2,vm.delivery.agreementRequirement.1m.pendingReview.2,im.delivery.agreementRequirement.1.5m.pendingReview.2
    And Exposure Management - manual send margin delivery&return letter createDeliveryAndReturnLetter.VMReturn.4k.IMReturn.3k.VMDelivery.2k.IMDelivery.1k
    Then Exposure Management - event should be vm.delivery.agreementRequirement.1m.MarginRequestConfirmed.2,im.delivery.agreementRequirement.1.5m.MarginRequestConfirmed.2
    Then Exposure Management - event should be vm.return.agreementRequirement.3m.MarginRequestConfirmed.2,im.return.agreementRequirement.2m.MarginRequestConfirmed.2
    When Go to agreement agr31331.otc.notnet.grossIM.2 statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    Then asset holding detail should be bond.usd.return.systemDraft.vm.4k.im.3k
    When Asset holding detail - collapse asset type asset.type.bond.usd
    Then asset holding detail should be bond.usd.delivery.systemDraft.vm.2k.im.1k

  @13.0.0 @JaneZhang @T27959 @F7665 @COL-4990 @Reviewed
  Scenario: Verify that retrieve Segregation Segregation type and Asset Custodian successfully via Make Booking to add collateral booking
    When Go to agreement agr27959.otc.net.NotTickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m
    When Exposure Management - tick events call.agreementRequirement.1m
    And add call bookings - group booking cash.call.0.5m,bond.call.0.5m
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And open group asset booking editor page bond.call.0.5m.open
    Then call booking in booking editor page should be bond.call.0.5m.Segregation.no.SegregationType.blank
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.cash.usd agreement asset CASH Page
    And open group asset booking editor page cash.call.0.5m.open
    Then call booking in booking editor page should be cash.call.0.5m.Segregation.no.SegregationType.blank

    When Go to agreement agr27959.otc.net.TickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m.2
    When Exposure Management - tick events call.agreementRequirement.1m.2
    And add call bookings - group booking cash.call.0.5m,equity.call.0.5m
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And open group asset booking editor page equity.call.0.5m.open
    Then call booking in booking editor page should be equity.call.0.5m.Segregation.no.SegregationType.blank
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.cash.usd agreement asset CASH Page
    And open group asset booking editor page cash.call.0.5m.open.2
    Then call booking in booking editor page should be cash.call.0.5m.Segregation.no.SegregationType.blank

    When Go to agreement agr27959.fcm.TickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.agreementRequirement.-1m,vm.call.agreementRequirement.1m
    When Exposure Management - tick events vm.call.agreementRequirement.1m
    And add call bookings - group booking cash.call.1m
    And view asset type asset.type.cash.usd agreement asset CASH Page
    And open group asset booking editor page cash.call.1m.open
    Then call booking in booking editor page should be cash.call.1m.Segregation.yes.SegregationType.SegregatedDirectDealerHolding
    When asset booking edit - click cancel button
    And Group booking - click cancel next booking button
    And Exposure Management - all filters - search dynamic filter agr27959.allFilter
    And Exposure Management - tick events im.delivery.agreementRequirement.-1m
    And add call bookings - group booking bond.delivery.1m
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And open group asset booking editor page bond.delivery.1m.open
    Then call booking in booking editor page should be bond.delivery.1m.Segregation.yes.SegregationType.TriPartyCollateralAgentHolding

  @13.0.0 @JaneZhang @T26757 @F6978 @COL-4966 @reviewed
  Scenario: Verify sub events with call status pending review will be removed from EM when update security from ineligible to eligible
    When navigate to security search page
    And search security 26757.bond.usd
    And edit security 26757.bond.usd.approve to 26757.bond.usd.changeMaturityDate.tPlus10.changeRecordDate.tMinus10
    And approve security 26757.bond.usd.approve
    And search security 26757.equity.usd
    And edit security 26757.equity.usd.approve to 26757.equity.usd.changeRecordDate.tMinus10
    And approve security 26757.equity.usd.approve

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.26757
    And edit eligibility rules template ERT.cash.usd.selectPC to ERT.cash.usd.selectP
    And search eligibility rules template ERT.26757
    And eligibility rules template - approve ERT.26757.approve
    When Go to agreement agr26757.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.noEligibilityRule to bond.usd.withEligibilityRule.excludeInstrumentId
    And edit OTC agreement equity.usd.noEligibilityRule to equity.usd.withEligibilityRule.EligibleCurrencies.GBP
    And view statement
    And approve agreement agr26757
    And quick link - agreement exposure management
    Then Exposure Management - event should be subReq.agreementRequirement.4m,subReq.agreementRequirement.2m,subCon.agreementRequirement.1m

    When navigate to security search page
    And search security instrument.equity.usd
    And edit security instrument.equity.usd.searchResult to instrument.equity.usd.currency.GBP
    And Go to agreement agr26757.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.withEligibilityRule.excludeInstrumentId1 to bond.usd.withEligibilityRule.NotExcludeInstrumentId
    And edit OTC agreement cash.usd.notSelectedInAgreement to cash.usd.SelectedForPCInAgreement
    And view statement
    And approve agreement agr26757
    And quick link - agreement exposure management
    Then Exposure Management - event should be subReq.agreementRequirement.4m.notDisplay,subReq.agreementRequirement.2m.notDisplay,subCon.agreementRequirement.1m.notDisplay

  @13.1.0 @JaneZhang @T28956 @F6248 @COL-4950 @Reviewed
  Scenario: Verify VM cash Fee Delivery Excess per Event Source with MTA applied
  """o MTA is only applicable to VM for Cash Component Event and Fee Event should take MTA 0
  Exposure < MTA & Fee/Cash < MTA
  """
    When Go to agreement agr28956.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events cash.delivery.agreementRequirement.-1m.top,fee.delivery.agreementRequirement.-2m.top,VMOTMNoCalls.agreementRequirement.0m.top
    Then Exposure Management - event should be cash.delivery.agreementRequirement.-1m.o1
    And Exposure Management - event should be fee.delivery.agreementRequirement.-2m.o1
    And Exposure Management - event should be VMOTMNoCalls.agreementRequirement.0m.o1

  @13.1.0 @JaneZhang @T26494 @F6978#F8949#F10169 @COL-4925 @Reviewed
  Scenario: Verify succeed to create substitution confirmation event via Create Substitution icon Adhoc confirmation
    When navigate to security search page
    And search security 26494.bond.usd
    And edit security 26494.bond.usd.approve to 26494.bond.usd.changeRecordDate.tPlus8
    And approve security 26494.bond.usd.approve
    And search security 26494.equity.usd
    And edit security 26494.equity.usd.approve to 26494.equity.usd.changeRecordDate.tPlus7
    And approve security 26494.equity.usd.approve

    When navigate to eligibility rules template page
    And search eligibility rules template ERT.26494
    And edit eligibility rules template ERT.cash.usd.Select to ERT.cash.usd.notSelect
    And search eligibility rules template ERT.26494
    And eligibility rules template - approve ERT.26494.approve
    And Go to agreement agr26494.otc.net statement page by URL
    And approve agreement agr26494

    And Go to agreement agr26494.otc.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26494.return
    Then Exposure Management - substitution event should be equity.usd.notional.2m,bond.usd.notional.1.5m,cash.usd.notional.3.5m
    When Exposure Management - create adhoc substitution events equity.usd.notional.2m,bond.usd.notional.1.5m,cash.usd.notional.3.5m
    And Exposure Management - add column source,ruleDetail,reasonForSub,level
    Then Exposure Management - event should be subCon.agreementRequirement.3.5m.cash.usd.reasonForSub.AssetTypeNotEligible
    And Exposure Management - event should be subCon.agreementRequirement.3.5m.equity.usd.reasonForSub.RecordDatedue
    And Exposure Management - event should be subCon.agreementRequirement.0m.equity.usd.reasonForSub.null
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.usd.reasonForSub.null
    And Exposure Management - event should be subCon.agreementRequirement.0m.bond.usd.reasonForSub.null


  @13.0.0 @JaneZhang @T27964 @F7665 @COL-4911 @Reviewed
  Scenario: Verify that retrieve Segregation, Segregation type and Asset Custodian successfully via Auto Booking through Letter to add collateral booking
  """Precondition
  1.Prepare 2 agreements:
  >Net (Auto Booking rule-Net)
  >Non-Net (Auto Booking rule-VM IM)
  """

    When Go to agreement agr27964.otc.net.NotTickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m
    And Exposure Management - set event call.agreementRequirement.1m value to counterpartyAmount.1m
    And Exposure Management - save changes
    When Exposure Management - tick events call.agreementRequirement.1m
    And Exposure Management - manual send margin call letter createCallLetter
    When Go to agreement agr27964.otc.net.NotTickSegregation statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.call.1m
    Then call booking in booking editor page should be cash.call.1m.Segregation.no.SegregationType.blank

    When Go to agreement agr27964.otc.net.TickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.agreementRequirement.1m
    And Exposure Management - set event call.agreementRequirement.1m value to counterpartyAmount.1m
    And Exposure Management - save changes
    When Exposure Management - tick events call.agreementRequirement.1m
    And Exposure Management - manual send margin call letter createCallLetter
    When Go to agreement agr27964.otc.net.TickSegregation statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.call.1m
    Then call booking in booking editor page should be cash.call.1m.Segregation.no.SegregationType.blank

    When Go to agreement agr27964.fcm.TickSegregation statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be im.delivery.agreementRequirement.-1m,vm.call.agreementRequirement.1m
    And Exposure Management - set event vm.call.agreementRequirement.1m value to counterpartyAmount.1m
    And Exposure Management - set event im.delivery.agreementRequirement.-1m value to counterpartyAmount.-1m
    And Exposure Management - save changes
    When Exposure Management - tick events vm.call.agreementRequirement.1m
    And Exposure Management - manual send margin call letter createCallLetter
    And Go to agreement agr27964.fcm.TickSegregation statement page by URL
    And approve agreement agr27964
    And quick link - agreement exposure management
    And Exposure Management - tick events im.delivery.agreementRequirement.-1m
    And Exposure Management - manual send margin delivery letter createDeliveryLetter
    When Go to agreement agr27964.fcm.TickSegregation statement page by URL
    When edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.vm.call.1m
    Then call booking in booking editor page should be cash.vm.call.1m.Segregation.yes.SegregationType.SegregatedDirectDealerHolding
    When asset booking edit - click cancel button
    And open asset booking editor page CASH.USD.im.delivery.1m
    Then call booking in booking editor page should be cash.delivery.im.1m.Segregation.yes.SegregationType.TriPartyCollateralAgentHolding


  @13.1.0 @JaneZhang @T29033 @F6248 @COL-4909 @Reviewed
  Scenario: Verify Auto Email works for ETF agreement aggregated by Model
    When Go to agreement agr29033.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    And Exposure Management - add column model,notificationStatus
    When Exposure Management - tick events o1.vmCall.agreementRequirement.1m.pendingReview
    Then Exposure Management - event should be o1.top.call.agreementRequirement.1m.pendingReview,o1.vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be o1.cashCall.agreementRequirement.1m.pendingReview,o1.feeDelivery.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be o2.top.delivery.agreementRequirement.2m.pendingReview,o2.vmDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be o2.feeCall.agreementRequirement.2m.pendingReview,o2.cashDelivery.agreementRequirement.2m.pendingReview
    Then Exposure Management - autoSend action should be not displayed

    When Go to agreement agr29033.etf statement page by URL
    And approve agreement agr29033
    And quick link - agreement exposure management
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    When Exposure Management - tick events o1.vmCall.agreementRequirement.1m.pendingReview
    Then Exposure Management - autoSend action should be displayed
    When Exposure Management - tick events o1.top.call,o2.top.delivery,o1.vmCall.agreementRequirement.1m.pendingReview
    Then Exposure Management - autoSend action should be displayed
    And Exposure Management - tick events o1.cashCall.agreementRequirement.1m.pendingReview,o1.feeDelivery.agreementRequirement.1m.pendingReview
    And Exposure Management - tick events o2.top.delivery.agreementRequirement.2m.pendingReview,o2.vmDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - tick events o2.feeCall.agreementRequirement.2m.pendingReview,o2.cashDelivery.agreementRequirement.2m.pendingReview
    Then Exposure Management - autoSend action should be displayed
    When Exposure Management - untick events o1.cashCall.agreementRequirement.1m.pendingReview,o1.feeDelivery.agreementRequirement.1m.pendingReview
    And Exposure Management - untick events o2.top.delivery.agreementRequirement.2m.pendingReview,o2.vmDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - untick events o2.cashDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - untick events o1.top.call,o2.top.delivery
    And Exposure Management - auto send letter
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    Then Exposure Management - event should be o1.top.call.agreementRequirement.1m.MarginRequestIssued.sent,o1.vmCall.agreementRequirement.1m.MarginRequestIssued.sent
    And Exposure Management - event should be o1.cashCall.agreementRequirement.1m.MarginRequestIssued.sent,o1.feeDelivery.agreementRequirement.1m.pendingReview.sent
    And Exposure Management - event should be o2.top.delivery.agreementRequirement.2m.pendingReview.sent,o2.vmDelivery.agreementRequirement.2m.pendingReview.sent
    And Exposure Management - event should be o2.feeCall.agreementRequirement.2m.MarginRequestIssued.sent,o2.cashDelivery.agreementRequirement.2m.pendingReview.sent

  @14.1.0 @JaneZhang @T31626 @F10482 @COL-4903 @Reviewed
  Scenario: Verify No call events for not net Gross agreement
    When Go to agreement agr31626.notnet.gross statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls
    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade.ExposureAmount.-2m
    And quick link - agreement exposure management
    Then Exposure Management - event should be VM.delivery.2m,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31626.vmThreshold.0 to agr31626.vmThreshold.2m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade.ExposureAmount.4m.independentAmount.3m
    And quick link - view statement
    And approve agreement agr31626
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VM.Call.4m,IMOtmNoCalls,IM.Call.3m

    When Go to agreement agr31626.notnet.gross statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31626.MTA.0 to agr31626.MTA.4.1m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IM.Call.3m

    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking callBooking.IM.1.5m
    And quick link - view statement
    And approve agreement agr31626
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IM.Call.1.5m

    When Go to agreement agr31626.notnet.gross statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31626.IM.counterparty.Threshold.0 to agr31626.IM.counterparty.Threshold.1.4m.MTA.0.2m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade.independentAmount.-5m
    And quick link - view statement
    And approve agreement agr31626
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IM.delivery.5m,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31626.IM.principal.Threshold.0 to agr31626.IM.principal.Threshold.5.1m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking deliveryBooking.IM.2m
    And quick link - view statement
    And approve agreement agr31626
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IM.recall.2m,IMItmNoCalls

    When Go to agreement agr31626.notnet.gross statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add recall bookings - statement booking recallBooking.IM.2m
    And quick link - view statement
    And approve agreement agr31626
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls


  @14.1.0 @JaneZhang @T31628 @F10482 @COL-4899 @Reviewed
  Scenario: Verify No call events for not net Gross IM agreement
    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMItmNoCalls,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade.ExposureAmount.-2m
    And quick link - agreement exposure management
    Then Exposure Management - event should be VM.delivery.2m,IMOtmNoCalls,IMItmNoCalls

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking deliveryBooking.IM.3m
    And quick link - view statement
    And approve agreement agr31628
    And quick link - agreement exposure management
    Then Exposure Management - event should be IMItmNoCalls,IM.recall.3m,VM.delivery.2m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades trade.independentAmount.4m
    And quick link - agreement exposure management
    Then Exposure Management - event should be VM.delivery.2m,IM.recall.3m,IM.call.4m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking callBooking.IM.4m
    And quick link - view statement
    And approve agreement agr31628
    And quick link - agreement exposure management
    Then Exposure Management - event should be IMItmNoCalls,IM.recall.3m,VM.delivery.2m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31628.vmPrincipalThreshold.0 to agr31628.vmPrincipalThreshold.2m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,IMItmNoCalls,IM.recall.3m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31628.vmPrincipalThreshold to agr31628.vmPrincipalThreshold.1.9m.MTA.0.2m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,IMItmNoCalls,IM.recall.3m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking recallBooking.IM.1.5m
    And quick link - view statement
    And approve agreement agr31628
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,IMItmNoCalls,IM.recall.1.5m

    When Go to agreement agr31628.notnet.grossIM statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr31628.imCounterparty to agr31628.imCounterpartyMTA.1.6m in fixTrigger tab
    And view statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be VMOtmNoCalls,IMItmNoCalls,IM.recall.1.5m
#    MTA not apply on full recall

  @13.0.0 @JaneZhang @T27782 @F7717 @COL-4899 @Reviewed
  Scenario: Verify event workflow of Auto Partial Dispute Dispute Approval on Apply 4 eye on
  """
Precondition
1.Workflow Approvals-Dispute is on Apply 4-eye is on
2.Normal agreement VM Call event.
 3.User1: Cpty amount< required amount => task feed(by system user) booking1 linked to event
 -Call Status = Pending Review Wrk Status = Amend-Dispute
 -System user can update call status but can not update wrk status or approve wrk status in Approval Manager
 -User 1 cannot update call status but can approve wrk status
 4.User2: make booking2 linked to event
 -cannot update call status but can approve wrk status
  """

    When navigate to collateral preferences page
    And set collateral preferences setWorkflowDisbute.WorkflowApplyFourEyes
    When Go to agreement agr27782.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.call.agreementRequirement.1m.wrkStatus.Approved.disputeValue.0,noCall
    When Exposure Management - set event vm.call.agreementRequirement.1m.wrkStatus.Approved.disputeValue.0 value to counterpartyAmount.0.9m
    And Exposure Management - save changes
    Then Exposure Management - event should be vm.call.agreementRequirement.1m.wrkStatus.Approved.disputeValue.-0.1m
    And Exposure Management - tick events vm.call.agreementRequirement.1m.wrkStatus.Approved.disputeValue.-0.1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.call.vm.0.4m
    And quick link - save
    And Exposure Management - override warning warning.notMeetRequiredAmount.override
    And quick link - save
    Then Exposure Management - event should be vm.call.wrkStatus.disabled

    When navigate to approvals manager page
    And approvals manager - search workflow agr27782
    Then approvals manager - the search result searchResult.vm.call cannot be checked

    When switch user to loginWithUserB
    And Go to agreement agr27782.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.call.agreementRequirement.1m.wrkStatus.ApprovedDisplayInOption.AmendedDispute.disputeValue.-0.1m
    And Exposure Management - tick events vm.call.agreementRequirement.1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.call.vm.0.5m
    And quick link - save
    Then Exposure Management - event should be vm.call.agreementRequirement.1m.wrkStatus.ApprovedDisplayInOption.AmendedDispute.disputeValue.-0.1m

    When navigate to approvals manager page
    And approvals manager - search workflow agr27782
    And approvals manager - tick workflow searchResult.vm.call
    And approvals manager - approve ticked workflows
    And Go to agreement agr27782.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be vm.call.agreementRequirement.1m.wrkStatus.Approved.disputeValue.-0.1m.callStatus.partialDispute

  @13.0.0.SP5 @JaneZhang @T31319 @F10624 @COL-4878 @Reviewed
  Scenario: Verify The dispute amount of Call or Recall should be calculated according set CPTY amount of itself manual NET
  """
Precondition
 R1
 1.
 · The dispute amount of Call or Recall Delivery or return should be calculated according set CPTY amount of itself.
 2.for these agreement
 · Gross Calc(ITM vs OTM) is Gross
 o Net/Auto-Net Agreement: Margin Call and Recall Margin Delivery and Return should be changed
 o Non-Net: VM or IM Margin Call and Recall VM or IM Margin Delivery and Return should be changed
 · Gross Calc(ITM vs OTM) is Gross IM:
 § IM Margin Call and Recall IM Delivery and Return should be changed
  """
    When Go to agreement agr31319.otc.net.gross statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be Call.agreementRequirement.1m,Recall.agreementRequirement.2m
    When Exposure Management - set event Call.agreementRequirement.1m value to counterpartyAmount.0.3m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.-0.7m,Recall.disputeValue.-2m
    When Exposure Management - set event Call.agreementRequirement.1m value to counterpartyAmount.2.5m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m,Recall.disputeValue.-2m
    When Exposure Management - set event Recall.agreementRequirement.2m value to counterpartyAmount.1.2m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m,Recall.disputeValue.-0.8m
    When Exposure Management - set event Recall.agreementRequirement.2m value to counterpartyAmount.2.3m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m,Recall.disputeValue.0.3m

    When Go to agreement agr31319.otc.autonet.gross statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be Call.agreementRequirement.1m.2,Recall.agreementRequirement.2m.2
    When Exposure Management - set event Call.agreementRequirement.1m.2 value to counterpartyAmount.0.3m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.-0.7m.2,Recall.disputeValue.-2m.2
    When Exposure Management - set event Call.agreementRequirement.1m.2 value to counterpartyAmount.2.5m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m.2,Recall.disputeValue.-2m.2
    When Exposure Management - set event Recall.agreementRequirement.2m.2 value to counterpartyAmount.1.2m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m.2,Recall.disputeValue.-0.8m.2
    When Exposure Management - set event Recall.agreementRequirement.2m.2 value to counterpartyAmount.2.3m
    And Exposure Management - save changes
    Then Exposure Management - event should be Call.disputeValue.1.5m.2,Recall.disputeValue.0.3m.2

  @13.1.0 @JaneZhang @T28928 @F6248 @COL-4876 @Reviewed
  Scenario: Verify Call Deficit event workflow for ETF agreement from pending review to Margin Request Issued by sending letter on Model level
    When Go to agreement agr28928.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    And Exposure Management - add column model,notificationStatus
    Then Exposure Management - event should be o1.top.call.agreementRequirement.1m.pendingReview,o1.vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be o1.cashCall.agreementRequirement.1m.pendingReview,o1.feeDelivery.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be o2.top.delivery.agreementRequirement.2m.pendingReview,o2.vmDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be o2.feeCall.agreementRequirement.2m.pendingReview,o2.cashDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - tick events o1.top.call.agreementRequirement.1m.pendingReview
    Then Exposure Management - createLetter,autoSend,autoSendAll action should be displayed
    And Exposure Management - untick events o1.top.call.agreementRequirement.1m.pendingReview
    When Exposure Management - tick events o1.vmCall.agreementRequirement.1m.pendingReview
    Then Exposure Management - createLetter,autoSend,autoSendAll action should be displayed
    And Exposure Management - create letter
    Then letter detail should be letterProcessing.statementNotification.fullDispute.display
    When quick link - agreement exposure management
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    And Exposure Management - tick events o1.vmCall.agreementRequirement.1m.pendingReview
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    Then Exposure Management - event should be o1.top.call.agreementRequirement.1m.MarginRequestIssued.sent,o1.vmCall.agreementRequirement.1m.MarginRequestIssued.sent
    And Exposure Management - event should be o1.cashCall.agreementRequirement.1m.MarginRequestIssued.sent,o1.feeDelivery.agreementRequirement.1m.pendingReview.sent
    And Exposure Management - event should be o2.top.delivery.agreementRequirement.2m.pendingReview,o2.vmDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be o2.feeCall.agreementRequirement.2m.pendingReview,o2.cashDelivery.agreementRequirement.2m.pendingReview
    And Exposure Management - tick events o2.feeCall.agreementRequirement.2m.pendingReview
    And Exposure Management - create letter
    Then letter detail should be letterProcessing.statementNotification.fullDispute.display
    When quick link - agreement exposure management
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    And Exposure Management - tick events o2.feeCall.agreementRequirement.2m.pendingReview
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter
    And Exposure Management - expand events o1.top.call,o2.top.delivery
    Then Exposure Management - event should be o1.top.call.agreementRequirement.1m.MarginRequestIssued.sent,o1.vmCall.agreementRequirement.1m.MarginRequestIssued.sent
    And Exposure Management - event should be o1.cashCall.agreementRequirement.1m.MarginRequestIssued.sent,o1.feeDelivery.agreementRequirement.1m.pendingReview.sent
    And Exposure Management - event should be o2.top.delivery.agreementRequirement.2m.pendingReview.sent,o2.vmDelivery.agreementRequirement.2m.pendingReview.sent
    And Exposure Management - event should be o2.feeCall.agreementRequirement.2m.MarginRequestIssued.sent,o2.cashDelivery.agreementRequirement.2m.pendingReview.sent


  @13.0.0 @JaneZhang @T26302 @F6561 @COL-4864 @Reviewed
  Scenario: Verify make bulk booking for Sub Request event click Apply have instrument ID price haircut and FX converting net agreement
    When navigate to security search page
    And search security 26302.bond.usd.1
    And edit security 26302.bond.usd.1.approve to 26302.bond.usd.1.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 26302.bond.usd.1.approve

    When Go to agreement agr26302.otc.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column reasonForSub
    Then Exposure Management - event should be sub.request.reasonForSub.MaturityDue.element.bond.usd.1
    When Exposure Management - tick events sub.request.reasonForSub.MaturityDue.element.bond.usd.1
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be bulkBooking.bond.usd.1.recall.value.7.2m
    When Edit booking - bulk booking bond.usd.1.recall.8m,bond.usd.2.delivery.1m,cash.usd.delivery.4m,cash.gbp.delivery.2m
    And quick link - save
    And Exposure Management - override warning overWriteWarning
    And quick link - save
    Then Exposure Management - event should be subReq.bookedAmountFirstLeg.6.4m.bookedAmountSecondLeg.-5.6m


  @13.1.0 @JaneZhang @T29014 @F6248 @COL-4860 @Reviewed
  Scenario: Verify Delivery Excess event workflow for ETF agreement from pending review to Partial Dispute aggregated by Event Source
    When Go to agreement agr29014.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events top.vm.delivery,top.fee.delivery
    And Exposure Management - add column model
    Then Exposure Management - event should be top.vm.delivery.pendingReview,vm.delivery.o2.agreementRequirement.-1m.pendingReview,vm.delivery.o1.agreementRequirement.-2m.pendingReview
    And Exposure Management - event should be top.fee.delivery.pendingReview,fee.delivery.o1.agreementRequirement.-1m.pendingReview
    When Exposure Management - set event vm.delivery.o1.agreementRequirement.-2m.pendingReview value to counterpartyAmount.-3m
    And Exposure Management - set event vm.delivery.o1.agreementRequirement.-2m.pendingReview value to callStatus.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events top.vm.delivery,top.fee.delivery
    And Exposure Management - tick events vm.delivery.o1.agreementRequirement.-2m.MarginRequestIssued.disputeValue.-1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.applyRequirementAmount
    And quick link - save
    And Exposure Management - expand events top.vm.delivery,top.fee.delivery
    Then Exposure Management - event should be top.vm.delivery.pendingReview.disputeValue.0.counterpartyAmount.-3m,vm.delivery.o2.agreementRequirement.-1m.pendingReview.disputeValue.1m,vm.delivery.o1.agreementRequirement.-2m.PartialDispute.disputeValue.-1m
    And Exposure Management - event should be top.fee.delivery.pendingReview,fee.delivery.o1.agreementRequirement.-1m.pendingReview

    When Exposure Management - set event vm.delivery.o2.agreementRequirement.-1m.pendingReview.disputeValue.1m value to counterpartyAmount.-2m
    And Exposure Management - set event vm.delivery.o2.agreementRequirement.-1m.pendingReview.disputeValue.1m value to callStatus.MarginRequestIssued
    And Exposure Management - save changes
    And Exposure Management - tick events vm.delivery.o2.agreementRequirement.-1m.MarginRequestIssued.disputeValue.-1m
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.applyRequirementAmount
    And quick link - save
    And Exposure Management - expand events top.vm.delivery,top.fee.delivery
    Then Exposure Management - event should be top.vm.delivery.PartialDispute.disputeValue.-2m.counterpartyAmount.-5m,vm.delivery.o2.agreementRequirement.-1m.PartialDispute.disputeValue.-1m,vm.delivery.o1.agreementRequirement.-2m.PartialDispute.disputeValue.-1m
    And Exposure Management - event should be top.fee.delivery.pendingReview,fee.delivery.o1.agreementRequirement.-1m.pendingReview


  @13.1.0 @JaneZhang @T28939 @F6248 @COL-4850 @Reviewed
  Scenario: Verify Call Deficit event workflow for ETF agreement from pending review to Completed on Event Source level
    When Go to agreement agr28939.etf statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events top.fee.call,top.vm.call
    And Exposure Management - add column model
    Then Exposure Management - event should be top.vm.call.pendingReview,vm.delivery.o2.agreementRequirement.-1m.pendingReview,vm.call.o1.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be top.fee.call.pendingReview,fee.call.o1.agreementRequirement.1m.pendingReview
    And Exposure Management - tick events vm.call.o1.agreementRequirement.2m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.applyRequirementAmount.o1
    And quick link - save
    And Exposure Management - expand events top.fee.call,top.vm.call
    Then Exposure Management - event should be top.vm.call.pendingReview,vm.delivery.o2.agreementRequirement.-1m.pendingReview,vm.call.o1.agreementRequirement.2m.completed
    And Exposure Management - event should be top.fee.call.pendingReview,fee.call.o1.agreementRequirement.1m.pendingReview

    When Exposure Management - tick events vm.delivery.o2.agreementRequirement.-1m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.applyRequirementAmount.o2
    And quick link - save
    And Exposure Management - expand events top.fee.call,top.vm.call
    Then Exposure Management - event should be top.vm.call.completed,vm.delivery.o2.agreementRequirement.-1m.completed,vm.call.o1.agreementRequirement.2m.completed
    And Exposure Management - event should be top.fee.call.pendingReview,fee.call.o1.agreementRequirement.1m.pendingReview

    When Exposure Management - tick events fee.call.o1.agreementRequirement.1m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.applyRequirementAmount.o1.fee
    And quick link - save
    And Exposure Management - expand events top.fee.call,top.vm.call
    Then Exposure Management - event should be top.vm.call.completed,vm.delivery.o2.agreementRequirement.-1m.completed,vm.call.o1.agreementRequirement.2m.completed
    And Exposure Management - event should be top.fee.call.completed,fee.call.o1.agreementRequirement.1m.completed

  @13.3.0 @JaneZhang @T30205 @F9292 @COL-4853 @Reviewed
  Scenario: Verify that the amount abbreviations work on page EM page and Bulk Booking Page for not net agreement
  """
  1. When global configuration for abbreviation is disabled user cannot enter abbreviations (but can enter comma and decimal) for amount on page 'EM page' and 'Bulk Booking Page' and is presented with an appropriate error message indicating that abbreviations are not accepted
  2. When it is enabled user can enter abbreviations for amount on page 'EM page' and 'Bulk Booking Page' without any error.
  3. No matter it is enabled or disabled all nonnumeric charactors except 'k' 'm' and 'b' cannot be recognised.
  """

    When navigate to system preferences page
    And set system preferences enableAmountAbbreviations.false
    When Go to agreement agr30205.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - set event im.recall.agreementRequirement.2m value to im.recall.agreementRequirement.2m.userAgreedAmount.1m
    And Exposure Management - set event vm.delivery.agreementRequirement.2b value to vm.delivery.agreementRequirement.2b.userAgreedAmount.1b
    And Exposure Management - save changes
    Then Exposure Management - EM message should be UserAgreedAmountNotValid

    And Exposure Management - set event im.recall.agreementRequirement.2m value to im.recall.agreementRequirement.2m.userAgreedAmount.1000000
    And Exposure Management - set event vm.delivery.agreementRequirement.2b value to vm.delivery.agreementRequirement.2b.userAgreedAmount.1000000000
    And Exposure Management - save changes
    Then Exposure Management - event should be im.recall.agreementRequirement.2m.userAgreedAmount.1000000
    And Exposure Management - event should be vm.delivery.agreementRequirement.2b.userAgreedAmount.1000000000
    When Exposure Management - tick events im.recall.agreementRequirement.2m,vm.delivery.agreementRequirement.2b
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.im.recall.1m,cash.usd.vm.delivery.1b
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid
    And Exposure Managerment - EM booking message should be VMNotionalNotValid
    When quick link - change bookings
    And Edit booking - bulk booking cash.usd.im.recall.1k,cash.usd.vm.delivery.1a
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid
    And Exposure Managerment - EM booking message should be VMNotionalNotValid
    When quick link - change bookings
    And Edit booking - bulk booking cash.usd.im.recall.aaa,cash.usd.vm.delivery.k2
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid
    And Exposure Managerment - EM booking message should be VMNotionalNotValid
    When quick link - change bookings
    And Edit booking - bulk booking cash.usd.im.recall.a200.00,cash.usd.vm.delivery.1m
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid
    And Exposure Managerment - EM booking message should be VMNotionalNotValid

    When navigate to system preferences page
    And set system preferences enableAmountAbbreviations.true
    When Go to agreement agr30205.fcm.2 statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - set event im.recall.agreementRequirement.2m.2 value to im.recall.agreementRequirement.2m.userAgreedAmount.1a
    And Exposure Management - set event vm.delivery.agreementRequirement.2b.2 value to vm.delivery.agreementRequirement.2b.userAgreedAmount.a100
    And Exposure Management - save changes
    Then Exposure Management - EM message should be UserAgreedAmountNotValid.enableAmountAbbreviations
    When Refresh the page
    And Exposure Management - set event im.recall.agreementRequirement.2m.2 value to im.recall.agreementRequirement.2m.userAgreedAmount.2km
    And Exposure Management - set event vm.delivery.agreementRequirement.2b.2 value to vm.delivery.agreementRequirement.2b.userAgreedAmount.k2m
    And Exposure Management - save changes
    Then Exposure Management - EM message should be UserAgreedAmountNotValid.enableAmountAbbreviations
    When Refresh the page
    And Exposure Management - set event im.recall.agreementRequirement.2m.2 value to im.recall.agreementRequirement.2m.userAgreedAmount.k200
    And Exposure Management - set event vm.delivery.agreementRequirement.2b.2 value to vm.delivery.agreementRequirement.2b.userAgreedAmount.aaa
    And Exposure Management - save changes
    Then Exposure Management - EM message should be UserAgreedAmountNotValid.enableAmountAbbreviations
    When Refresh the page
    And Exposure Management - set event im.recall.agreementRequirement.2m.2 value to im.recall.agreementRequirement.2m.userAgreedAmount.1k
    And Exposure Management - set event vm.delivery.agreementRequirement.2b.2 value to vm.delivery.agreementRequirement.2b.userAgreedAmount.1a
    And Exposure Management - save changes
    Then Exposure Management - EM message should be UserAgreedAmountNotValidVMEvent.enableAmountAbbreviations
    When Exposure Management - set event im.recall.agreementRequirement.2m.2 value to im.recall.agreementRequirement.2m.userAgreedAmount.1M
    And Exposure Management - set event vm.delivery.agreementRequirement.2b.2 value to vm.delivery.agreementRequirement.2b.userAgreedAmount.1b
    And Exposure Management - save changes
    Then Exposure Management - event should be im.recall.agreementRequirement.2m.2.userAgreedAmount.1000000
    And Exposure Management - event should be vm.delivery.agreementRequirement.2b.2.userAgreedAmount.1000000000

    When Exposure Management - tick events im.recall.agreementRequirement.2m.2,vm.delivery.agreementRequirement.2b.2
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking cash.usd.im.recall.1a,cash.usd.vm.delivery.k200
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid.enableAmountAbbreviations
    And Exposure Managerment - EM booking message should be VMNotionalNotValid.enableAmountAbbreviations
    When quick link - change bookings
    And Edit booking - bulk booking cash.usd.im.recall.200a,cash.usd.vm.delivery.200k
    And quick link - validate
    Then Exposure Managerment - EM booking message should be IMNotionalNotValid.enableAmountAbbreviations
    When quick link - change bookings
    And Edit booking - bulk booking cash.usd.im.recall.1m.enableAmountAbbreviations,cash.usd.vm.delivery.1b.enableAmountAbbreviations
    And quick link - validate
    Then Exposure Managerment - EM booking message should be NoErrorOrWarning
    And EM Bulk booking should be notional.1000000,notional.1000000000

      @13.0.0 @JaneZhang @T27754 @F7717 @EMWorkflow @COL-4846 @Reviewed
  Scenario: Verify event workflow of Manual Full Dispute Dispute Approval on Apply 4 eye on
  """
 Precondition
 1.Workflow Approvals-Dispute is on Apply 4-eye is on
 2.Normal agreement Recall event.
 3.User1: manual change to Full Dispute
 -Call Status = Pending Review Wrk Status = Amend-Dispute
 -User1 can update call status but can not update wrk status or approve wrk status in Approval Manager
 4.User2:
 -cannot update call status but can approve wrk status
  """
    When navigate to collateral preferences page
    And set collateral preferences setWorkflowDisbute.WorkflowApplyFourEyes
    When Go to agreement agr27754.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.Approved.disputeValue.0
    When Exposure Management - set event recall.agreementRequirement.1m.wrkStatus.Approved.disputeValue.0 value to callStatus.fullDispute
    And Exposure Management - save changes
    Then Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.AmendedDispute.disputeValue.0

    When switch user to anotherUser
    And Go to agreement agr27754.otc.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.AmendedDispute.disputeValue.0.anotherUser
    Then Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.includeApproved.disputeValue.0
    And Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.AmendedDispute.disputeValue.0.anotherUser
    When navigate to approvals manager page
    And approvals manager - search workflow agr27754
    Then approvals manager - the search result searchResult.recall is existed
    When approvals manager - tick workflow searchResult.recall
    And approvals manager - approve ticked workflows
    When Go to agreement agr27754.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be recall.agreementRequirement.1m.wrkStatus.approved.callStatus.fullDispute
#   v14.1 has bug that dispute value isn't changed to -1000000.00

  @13.0.0 @JaneZhang @T26619 @F6978 @COL-4748 @Reviewed
  Scenario: Verify agreement requirement will be updated for sub events if non event linked booking notional decreased
    When navigate to security search page
    And search security 26619.bond.usd
    And edit security 26619.bond.usd.approve to 26619.bond.usd.changeRecordDate.tMinus5.changeMaturityDate.tPlus5
    And approve security 26619.bond.usd.approve
    And search security 26619.equity.usd
    And edit security 26619.equity.usd.approve to 26619.equity.usd.changeRecordDate.tMinus10
    And approve security 26619.equity.usd.approve

    And Go to agreement agr26619.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement equity.usd.EligibilityRule to equity.usd.EligibilityRule.EligibleCurrencies.GBP in collateral tab
    And view statement
    And approve agreement agr26619
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26619.recall
    And Exposure Management - create adhoc substitution events equity.usd,bond.usd,cash.usd
    And Exposure Management - add column source,sub
    Then Exposure Management - event should be subReq.agreementRequirement.2m.source.System,subCon.agreementRequirement.-2m.source.System
    And Exposure Management - event should be subCon.agreementRequirement.-1.5m.source.System,subReq.agreementRequirement.1.5m.source.System
    And Exposure Management - event should be subReq.agreementRequirement.0.source.manual.element.bond,subReq.agreementRequirement.0.source.manual.element.equity,subReq.agreementRequirement.0.source.manual.element.cash

    When Go to agreement agr26619.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit return booking equity.return.1m.Pending to equity.return.1m.ChangeToCancelled
    And click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.delivery.3m
    And edit delivery booking CASH.USD.delivery.4m
    And Go to agreement agr26619.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be subReq.agreementRequirement.2m.source.System,subCon.agreementRequirement.-3m.source.System
    And Exposure Management - event should be subCon.agreementRequirement.-1.5m.source.System,subReq.agreementRequirement.1.5m.source.System
    And Exposure Management - event should be subReq.agreementRequirement.0.source.manual.element.bond,subReq.agreementRequirement.0.source.manual.element.equity,subReq.agreementRequirement.0.source.manual.element.cash

  @v2012.2 @JaneZhang @T20782 @F3641 @COL-4732 @Reviewed
  Scenario: Verify Auto Book All shown and works fine for FCM and Multi model agreement
    When Go to agreement agr20782.multiModel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events vm.call.top,im.call.top
    Then Exposure Management - event should be vm.call.top.agreementRequirement.2030399.14.pendingReview,vm.call.m1.agreementRequirement.1m.pendingReview
    And Exposure Management - event should be im.call.top.agreementRequirement.587125.18.pendingReview,im.call.m2.agreementRequirement.0.7m.pendingReview
    And Exposure Management - tick events vm.call.top,im.call.top
    And Exposure Management - auto send letter
    And Exposure Management - expand events vm.call.top,im.call.top
    Then Exposure Management - event should be vm.call.top.agreementRequirement.2030399.14.MarginRequestIssued,vm.call.m1.agreementRequirement.1m.MarginRequestIssued
    And Exposure Management - event should be im.call.top.agreementRequirement.587125.18.MarginRequestIssued,im.call.m2.agreementRequirement.0.7m.MarginRequestIssued

    And Exposure Management - tick events vm.call.top,im.call.top
    And Exposure Management - autoBookAll.notDisplay action should be not displayed
    And Exposure Management - untick events vm.call.top,im.call.top
    And Exposure Management - tick events im.call.m2.agreementRequirement.0.7m.MarginRequestIssued
    And Exposure Management - autoBookAll.display action should be displayed
    And Exposure Management - untick events im.call.m2.agreementRequirement.0.7m.MarginRequestIssued
    And Exposure Management - tick events vm.call.m1.agreementRequirement.1m.MarginRequestIssued
    And Exposure Management - autoBookAll.display action should be displayed
    And Exposure Management - set event vm.call.m1.agreementRequirement.1m.MarginRequestIssued value to counterpartyAmount.1.1m
    And Exposure Management - set event im.call.m2.agreementRequirement.0.7m.MarginRequestIssued value to counterpartyAmount.0.6m
    And Exposure Management - save changes
    And Exposure Management - expand events vm.call.top,im.call.top
    And Exposure Management - tick events vm.call.m1.agreementRequirement.1m.MarginRequestIssued
    And Exposure Management - auto book all
    And Exposure Management - EM message should be im.call.vm.call.autoBookingSuccess
    And Exposure Management - expand events vm.call.top,im.call.top
    Then Exposure Management - event should be vm.call.top.agreementRequirement.2030399.14.Completed,vm.call.m1.agreementRequirement.1m.Completed
    And Exposure Management - event should be im.call.top.agreementRequirement.587125.18.MarginRequestIssued,im.call.m2.agreementRequirement.0.7m.MarginRequestIssued

    When Go to agreement agr20782.multiModel.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.GBP agreement asset CASH Page
    Then asset holding detail should be CASH.GBP.call.vm.1.1m.pending.autoBook
    And click back button to previous page
    And view asset type asset.type.CASH.AUD agreement asset CASH Page
    Then asset holding detail should be CASH.AUD.call.vm.0.7m.systemDraft.autoBook

  @v13.0.0.SP5 @JaneZhang @T31374 @F10624 @COL-4723 @Reviewed
  Scenario: Verify that  partial dispute move back separately when cancel booking for call recall Gross
  """
Precondition
 R9 Cancelling Bookings
 · When Gross
 o If (VM/IM/Net) Dual Call & Recall or Delivery & Return the two events
 § If completed status then move back separately. (Keep Current)
 § If Partial Dispute then move back separately also (Need to change)
  """
    When Go to agreement agr31374.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be recall.agreementRequirement.2m,call.agreementRequirement.1m
    When Exposure Management - tick events recall.agreementRequirement.2m
    And Exposure Management - manual send margin recall letter createRecallLetter
    And Exposure Management - tick events call.agreementRequirement.1m
    And Exposure Management - manual send margin call letter createCallLetter
    Then Exposure Management - event should be recall.agreementRequirement.2m.MarginRequestIssued,call.agreementRequirement.1m.MarginRequestIssued

    When Exposure Management - set event recall.agreementRequirement.2m value to counterpartyAmount.1m
    And Exposure Management - save changes
    And Exposure Management - tick events recall.agreementRequirement.2m
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be recall.booking.autoCreated

    When Exposure Management - set event call.agreementRequirement.1m value to counterpartyAmount.0.5m
    And Exposure Management - save changes
    And Exposure Management - tick events call.agreementRequirement.1m
    And Exposure Management - auto book cash
    Then Exposure Management - EM message should be call.booking.autoCreated
    And Exposure Management - event should be recall.agreementRequirement.2m.PartialDispute,call.agreementRequirement.1m.PartialDispute

    When Go to agreement agr31374.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    Then asset holding detail should be CASH.USD.call.0.5m.pending.autoBook
    And asset holding detail should be CASH.USD.recall.1m.pending.autoBook

    When edit recall booking CASH.USD.recall.1m.pending.autoBook to CASH.USD.recall.1m.ChangeToCancelled
    And quick link - view statement
    And approve agreement agr31374
    And recalculate agreement statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be recall.agreementRequirement.2m.MarginRequestIssued.grey,call.agreementRequirement.1m.PartialDispute.grey
    And Exposure Management - event should be recall.agreementRequirement.2m.pendingReview.orange,call.agreementRequirement.0.5m.pendingReview.orange

    When Go to agreement agr31374.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    When edit recall booking CASH.USD.call.0.5m.pending.autoBook to CASH.USD.call.0.5m.ChangeToCancelled
    And quick link - view statement
    And approve agreement agr31374
    And recalculate agreement statement
    And quick link - agreement exposure management
    Then Exposure Management - event should be recall.agreementRequirement.2m.MarginRequestIssued.grey,call.agreementRequirement.1m.MarginRequestIssued.grey
    And Exposure Management - event should be recall.agreementRequirement.2m.pendingReview.orange,call.agreementRequirement.1m.pendingReview.orange

  @v13.0.0 @JaneZhang @T26612 @F6978 @COL-4708 @Reviewed
  Scenario: Verify Call Status and legs will be updated for sub events with call status partially booked if event linked booking changed
    When navigate to security search page
    And search security 26612.bond.usd
    And edit security 26612.bond.usd.approve to 26612.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26612.bond.usd.approve
    And search security 26612.equity.usd
    And edit security 26612.equity.usd.approve to 26612.equity.usd.changeRecordDate.tMinus5
    And approve security 26612.equity.usd.approve
    And search security 26612.bond.usd.2
    And edit security 26612.bond.usd.approve.2 to 26612.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26612.bond.usd.approve.2
    And search security 26612.equity.usd.2
    And edit security 26612.equity.usd.approve.2 to 26612.equity.usd.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26612.equity.usd.approve.2

    When Go to agreement agr26612.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.ExcludeInstruments.bond.1 in collateral tab
    And view statement
    And approve agreement agr26612
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26612.return
    And Exposure Management - create adhoc substitution events cash.usd
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.pendingReview
    And Exposure Management - event should be subReq.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be subCon.agreementRequirement.0m.pendingReview

    And Exposure Management - tick events subCon.agreementRequirement.-1m.pendingReview,subReq.agreementRequirement.2m.pendingReview,subCon.agreementRequirement.0m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.return.0.6m,equity.usd.call.0.5m,equity.usd.recall.0.9m,bond.usd.delivery.1m,cash.usd.return.0.8m,cash.usd.call.0.9m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.6m.secLeg.0.5m
    And Exposure Management - event should be subReq.agreementRequirement.2m.PartiallyBooked.firstLeg.0.9m.secLeg.1m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.firstLeg.0.8m.secLeg.0.9m

    When Go to agreement agr26612.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking cash.return.0.3m.linkToEvent3
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking bond.return.0.4m.linkToEvent1
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And add recall booking - statement booking equity.recall.0.3m.linkToEvent2

    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent1.booking13 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent1.booking13.successToFeed
    When feed asset bookings feed.bond.delivery.0.5m.pending.linkToEvent2.booking14 by xml
    Then feed log should be feed.bond.delivery.0.5m.pending.linkToEvent2.booking14.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent3.booking15 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent3.booking15.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent1.booking16 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent1.booking16.successToFeed
    When feed asset bookings feed.bond.delivery.0.1m.pending.linkToEvent2.booking17 by xml
    Then feed log should be feed.bond.delivery.0.1m.pending.linkToEvent2.booking17.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent3.booking18 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent3.booking18.successToFeed

    When Go to agreement agr26612.otc.net statement page by URL
    And approve agreement agr26612
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.PartiallyBooked.firstLeg.1m.secLeg.0.6m
    And Exposure Management - event should be subReq.agreementRequirement.2m.PartiallyBooked.firstLeg.1.2m.secLeg.1.5m
    And Exposure Management - event should be subCon.agreementRequirement.0m.PartiallyBooked.firstLeg.1.1m.secLeg.1m

    When Go to agreement agr26612.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.return.0.4m.linkToEvent1.booking to nominalAmount.0.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking equity.recall.0.3m.linkToEvent2.booking to nominalAmount.0.8m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit recall booking cash.return.0.3m.linkToEvent3.booking to nominalAmount.0.2m

    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Bond Page
    And edit recall booking booking16 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking booking18 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking booking17 to statusChangeToPending

    When Go to agreement agr26612.otc.net statement page by URL
    And approve agreement agr26612
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.Completed.firstLeg.0.7m.secLeg.0.7m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Completed.firstLeg.1.7m.secLeg.1.6m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.firstLeg.1m.secLeg.1.1m

  @v13.0.0 @JaneZhang @T27141 @F6260 @COL-3867 @wip @Reviewed
  Scenario: Verify that Auto Email send out individual VM and IM letters for NotNet agreement
    When Go to agreement agr27141.otc.notnet statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be imCall.agreementRequirement.2m.pendingReview,vmCall.agreementRequirement.5m.pendingReview
    And Exposure Management - event should be vmRecall.agreementRequirement.1m.pendingReview,imRecall.agreementRequirement.5k.pendingReview

    When Exposure Management - tick events imCall.agreementRequirement.2m.pendingReview
    And Exposure Management - auto send letter
    Then Exposure Management - event should be imCall.agreementRequirement.2m.MarginRequestIssued,imRecall.agreementRequirement.5k.MarginRequestIssued
    And Exposure Management - event should be vmRecall.agreementRequirement.1m.pendingReview,vmCall.agreementRequirement.5m.pendingReview

    When Go to agreement agr27141.otc.notnet statement page by URL
    And approve agreement agr27141
    And quick link - agreement exposure management
    And Exposure Management - tick events vmCall.agreementRequirement.5m.pendingReview
    And Exposure Management - auto send letter
    Then Exposure Management - event should be imCall.agreementRequirement.2m.MarginRequestIssued,imRecall.agreementRequirement.5k.MarginRequestIssued
    And Exposure Management - event should be vmRecall.agreementRequirement.1m.MarginRequestIssued,vmCall.agreementRequirement.5m.MarginRequestIssued
#can't check email by step7,step8

  @v13.0.0 @JaneZhang @T26617 @F6978 @COL-3864 @wip @Reviewed
  Scenario: Verify no change for sub event with call statius waived if non event linked booking notional changed
    When navigate to security search page
    And search security 26617.bond.usd
    And edit security 26617.bond.usd.approve to 26617.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26617.bond.usd.approve
    And search security 26617.equity.gbp
    And edit security 26617.equity.gbp.approve to 26617.equity.gbp.changeRecordDate.tMinus5
    And approve security 26617.equity.gbp.approve
    And search security 26617.bond.usd.2
    And edit security 26617.bond.usd.approve.2 to 26617.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26617.bond.usd.approve.2
    And search security 26617.equity.gbp.2
    And edit security 26617.equity.gbp.approve.2 to 26617.equity.gbp.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26617.equity.gbp.approve.2

    When Go to agreement agr26617.otc.net.multiModel statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.ExcludeInstruments.bond.1 in collateral tab
    And view statement
    And approve agreement agr26617
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26617.return
    And Exposure Management - create substitution event - expand events agr26617.subEvent
    And Exposure Management - create adhoc substitution events cash.eur
    And Exposure Management - add column model
    And Exposure Management - expand events subCon.agreementRequirement.-1m.top,subReq.agreementRequirement.2m.top,subCon.agreementRequirement.0m.top
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.PendingReview,subReq.agreementRequirement.4m.equity.gbp.PendingReview
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.eur.PendingReview
    And Exposure Management - tick events subCon.agreementRequirement.-1m.bond.usd.PendingReview,subReq.agreementRequirement.4m.equity.gbp.PendingReview,subCon.agreementRequirement.0m.cash.eur.PendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.return.0.5m,equity.gbp.call.0.2m,equity.gbp.recall.1m,bond.usd.delivery.0.9m,cash.eur.return.0.55m,cash.eur.call.0.5m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events subCon.agreementRequirement.-1m.top,subReq.agreementRequirement.2m.top,subCon.agreementRequirement.0m.top
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.PartiallyBooked.firLeg.0.5m.secLeg.0.1m
    And Exposure Management - event should be subReq.agreementRequirement.4m.equity.gbp.PartiallyBooked.firLeg.1m.secLeg.1.8m
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.eur.PartiallyBooked.firLeg.0.55m.secLeg.0.125m

    And Exposure Management - set event subCon.agreementRequirement.-1m.bond.usd.PartiallyBooked.firLeg.0.5m.secLeg.0.1m value to callStatus.waived
    And Exposure Management - set event subReq.agreementRequirement.4m.equity.gbp.PartiallyBooked.firLeg.1m.secLeg.1.8m value to callStatus.waived
    And Exposure Management - set event subCon.agreementRequirement.0m.cash.eur.PartiallyBooked.firLeg.0.55m.secLeg.0.125m value to callStatus.waived
    And Exposure Management - save changes
    And Exposure Management - expand events subCon.agreementRequirement.-1m.top,subReq.agreementRequirement.2m.top,subCon.agreementRequirement.0m.top
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.waived.firLeg.0.5m.secLeg.0.1m
    And Exposure Management - event should be subReq.agreementRequirement.4m.equity.gbp.waived.firLeg.1m.secLeg.1.8m
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.eur.waived.firLeg.0.55m.secLeg.0.125m

    When Go to agreement agr26617.otc.net.multiModel statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.return.1m.booking to statusCancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And edit recall booking equity.delivery.4m.booking to nominalAmount.6m
    And quick link - view statement
    And approve agreement agr26617

    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.agreementRequirement.-1m.top,subReq.agreementRequirement.2m.top,subCon.agreementRequirement.0m.top
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.waived.firLeg.0.5m.secLeg.0.1m
    And Exposure Management - event should be subReq.agreementRequirement.4m.equity.gbp.waived.firLeg.1m.secLeg.1.8m
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.eur.waived.firLeg.0.55m.secLeg.0.125m

#    When navigate to task scheduler page
#    And task scheduler - switch to Workflow tab
#    And edit task scheduler resetEEforAgr26617
#    And run task scheduler resetEEforAgr26617
#    Then task scheduler messaging should be resetEEforAgr26617.success

  @v13.0.0 @JaneZhang @T26542 @F6561 @COL-3879 @Reviewed
  Scenario: Verify NotNet Multi model Agreement Sub event Call status Workflow Bulk booking Complete
    When navigate to security search page
    And search security 26542.bond.usd
    And edit security 26542.bond.usd.approve to 26542.bond.usd.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 26542.bond.usd.approve
    And search security 26542.bond.usd.2
    And edit security 26542.bond.usd.approve.2 to 26542.bond.usd.2.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 26542.bond.usd.approve.2

    When Go to agreement agr26542.otc.notnet.multiModel statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column source,model
    And Exposure Management - expand events subReq.top
    Then Exposure Management - event should be subReq.sub.bond.1.m1,subReq.sub.bond.2.m1
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26542.return
    And Exposure Management - create substitution event - expand events agr26542.subEvent
    And Exposure Management - create adhoc substitution events bond.gbp.1
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26542.return
    And Exposure Management - create substitution event - expand events agr26542.subEvent
    And Exposure Management - create adhoc substitution events bond.gbp.2

    And Exposure Management - expand events subReq.top,subCon.top,subCon.vm.top
    Then Exposure Management - event should be subReq.sub.bond.1.m1,subReq.sub.bond.2.m1,subCon.sub.bond.4.m2,subCon.sub.bond.5.m2
    When Exposure Management - tick events subReq.sub.bond.1.m1,subReq.sub.bond.2.m1,subCon.sub.bond.4.m2,subCon.sub.bond.5.m2
    And Exposure Management - go to bulk booking page
    And EM Bulk booking should be subReq.m1.bond.usd.1.recall,subReq.m1.instrumentEmpty.delivery,subReq.m1.bond.usd.2.recall,subReq.m1.instrumentEmpty.delivery.2
    And EM Bulk booking should be subCon.m2.bond.gbp.1.return,subCon.m2.instrumentEmpty.call,subCon.m2.bond.gbp.2.return,subCon.m2.instrumentEmpty.call.2
    And Edit booking - bulk booking subReq.m1.bond.usd.1.recall.vm.3m.im.2m,subReq.m1.bond.usd.3.delivery.vm.2m.im.2m
    And Edit booking - bulk booking subReq.m1.bond.usd.2.recall.vm.2m,subReq.m1.cash.usd.delivery.vm.2m
    And Edit booking - bulk booking subCon.m2.bond.gbp.4.return.vm.2m.im.2m,subCon.m2.bond.gbp.6.call.vm.2m.im.3m
    And Edit booking - bulk booking subCon.m2.bond.gbp.5.return.im.2m,subCon.m2.cash.gbp.call.im.2m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events subReq.top,subCon.top,subCon.vm.top
    Then Exposure Management - event should be subReq.sub.bond.1.m1.completed,subReq.sub.bond.2.m1.completed,subCon.sub.bond.4.m2.completed,subCon.sub.bond.5.m2.completed

    When Go to agreement agr26542.otc.notnet.multiModel statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And open asset booking editor page bond.usd.1.recall.vm.3n.im.2m
    Then call booking in booking editor page should be bond.usd.1.recall.vm.3n.im.2m.event1
    When asset booking edit - click cancel button
    And open asset booking editor page bond.usd.3.delivery.vm.2m.im.2m
    Then call booking in booking editor page should be bond.usd.3.delivery.vm.2m.im.2m.event1
    When asset booking edit - click cancel button

    And open asset booking editor page bond.usd.2.recall.vm.2m
    Then call booking in booking editor page should be bond.usd.2.recall.vm.2m.event2
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And open asset booking editor page CASH.USD.delivery.vm.2m
    Then call booking in booking editor page should be CASH.USD.delivery.vm.2m.event2
    When asset booking edit - click cancel button
    And click back button to previous page

    And view asset type asset.type.bond.gbp agreement asset Bond Page
    And open asset booking editor page bond.gbp.4.return.vm.2m.im.2m
    Then call booking in booking editor page should be bond.gbp.4.return.vm.2m.im.2m.event3
    When asset booking edit - click cancel button
    And open asset booking editor page bond.gbp.6.call.vm.2m.im.3m
    Then call booking in booking editor page should be bond.gbp.6.call.vm.2m.im.3m.event3
    When asset booking edit - click cancel button

    And open asset booking editor page bond.gbp.5.return.im.2m
    Then call booking in booking editor page should be bond.gbp.5.return.im.2m.event4
    When asset booking edit - click cancel button
    And click back button to previous page
    And view asset type asset.type.CASH.GBP agreement asset CASH Page
    And open asset booking editor page CASH.GBP.call.im.2m
    Then call booking in booking editor page should be CASH.GBP.call.im.2m.event4

  @2012.3 @13.0.0 @JaneZhang @T24083 @F2716#F7895 @COL-3964
  Scenario: Verify TSA events should always populated in the EE or EM page and never set in IR
    When switch user to anotherUser
    And Go to agreement agr24083.fcm statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr24083.LegalReviewStartDate to agr24083.LegalReviewStartDate.tPlus5 in callSchedule tab
    And view statement
    And approve agreement agr24083.approved
    And agreement statement should be agr24083.statementCheck
    And quick link - agreement exposure management
    Then Exposure Management - event should be tsa.delivery.5k
    When navigate to internal review page
    And internal review - search internal review agr24083.searchIR
    Then internal review list summary should be vm.call.totalExposureAmount.1m,tsa.notDisplay
    When Internal review list summary - go to tabLink DELIVERIESANDRETURNS
    Then internal review list summary should be im.return.totalExposureAmount.1m,tsa.notDisplay
    When Self Service - navigate to exposures page
    Then Self Service - Client Data - External Exposure List should be vm.call.agreementRequirement.503000,tsa.delivery.agreementRequirement.5k,im.return.agreementRequirement.0.5m
    And Self Service - Client Data - External Exposure List should be internal.noRecord

    And Go to agreement agr24083.fcm statement page by URL
    And quick link - agreement review
    And edit OTC agreement agr24083.LegalReviewStartDate to agr24083.LegalReviewStartDate.t in callSchedule tab
    And view statement
    And approve agreement agr24083.DisabledPending
    And approve agreement agr24083.Internal
    And quick link - agreement exposure management
    Then Exposure Management - event should be tsa.delivery.5k.notDisplay

    When navigate to internal review page
    And internal review - search internal review agr24083.searchIR
    Then internal review list summary should be vm.call.totalExposureAmount.1m
    When Internal review list summary - go to tabLink DELIVERIESANDRETURNS
    Then internal review list summary should be im.return.totalExposureAmount.1m,tsa.delivery.totalExposureAmount.1m
    When Self Service - navigate to exposures page
    Then Self Service - Client Data - External Exposure List should be internal.vm.call.503000,internal.tsa.delivery.5000,internal.im.return.0.5m

    When Go to agreement agr24083.fcm.2 statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be tsa.delivery.5k.check.lrfd.lrfp.notifyBy.resolveBy

  @13.2.SP2 @JaneZhang @T30267 @F6561#D20782 @COL-3955
  Scenario: Verify events under Call Status should be changed from Pending Review to Request Issued or Confirmed by Manually
    When navigate to security search page
    And search security 30267.bond.usd
    And edit security 30267.bond.usd.approve to 30267.bond.usd.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 30267.bond.usd.approve
    And search security 30267.bond.usd.2
    And edit security 30267.bond.usd.approve.2 to 30267.bond.usd.2.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 30267.bond.usd.approve.2

    When Go to agreement agr30267.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.1m,recall.2m,subCon.2m.bond.usd.2,subReq.2m.bond.usd.1
    When Exposure Management - set event subReq.2m.bond.usd.1 value to callStatus.RequestIssued
    And Exposure Management - save changes
    Then Exposure Management - event should be subReq.2m.bond.usd.1.RequestIssued
    When Exposure Management - substitution filters - search sub confirmation agr30267.search
    And Exposure Management - set event subCon.2m.bond.usd.2 value to callStatus.RequestConfirmed
    And Exposure Management - save changes
    When Exposure Management - substitution filters - search confirmed agr30267.search
    Then Exposure Management - event should be subCon.2m.bond.usd.2.RequestConfirmed

  @13.0.0 @JaneZhang @T26548 @F6561 @COL-4023 @wip
  Scenario: Verify make bulk booking to change Sub Confirmation event workflow Repo agreement Partially booked
    """
    Test data:
    Event link to Two or more instrument

    Need to check:
    ◾Bulk booking page: First leg Auto pop linked instument and not editable
    ◾Movement is Return and Call not editable
    ◾Add icon available for last recall line and available for Delivery line
    ◾Delete icon only avalible for user manual added lines
    ◾Abs(Leg1) > Abs(Leg2) Event Call Status change to Partially booked
    """

    When navigate to security search page
    And search security 26548.bond.usd
    And edit security 26548.bond.usd.approve to 26548.bond.usd.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 26548.bond.usd.approve
    And search security 26548.bond.usd.2
    And edit security 26548.bond.usd.approve.2 to 26548.bond.usd.2.changeRecordDate.tPlus6.changeMaturityDate.tPlus6
    And approve security 26548.bond.usd.approve.2

    When Go to agreement agr26548.repo statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.4m.bond.usd.2,subCon.4m.bond.usd.1
    When Exposure Management - tick events subCon.4m.bond.usd.2,subCon.4m.bond.usd.1
    And Exposure Management - go to bulk booking page
    And EM Bulk booking should be subCon.bond.usd.1.return.value.4m,subCon.bond.usd.1.call.value.4m
    And EM Bulk booking should be subCon.bond.usd.2.return.value.4m,subCon.bond.usd.2.call.value.4m
    And Edit booking - bulk booking subCon.bond.usd.1.return.value.4m.nominalAmount.3m
    And Edit booking - bulk booking subCon.bond.usd.1.call.value.4m.nominalAmount.1m
    And Edit booking - bulk booking subCon.bond.usd.2.call.value.4m.nominalAmount.2m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be subCon.4m.bond.usd.2.Partiallybooked,subCon.4m.bond.usd.1.Partiallybooked


  @v13.0.0 @JaneZhang @T26615 @F6978 @COL-4003 @wip
  Scenario: Verify legs will be updated for sub events with call status completed if event linked booking changed
    When navigate to security search page
    And search security 26615.bond.usd
    And edit security 26615.bond.usd.approve to 26615.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26615.bond.usd.approve
    And search security 26615.equity.usd
    And edit security 26615.equity.usd.approve to 26615.equity.usd.changeRecordDate.tMinus5
    And approve security 26615.equity.usd.approve
    And search security 26615.bond.usd.2
    And edit security 26615.bond.usd.approve.2 to 26615.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26615.bond.usd.approve.2
    And search security 26615.equity.usd.2
    And edit security 26615.equity.usd.approve.2 to 26615.equity.usd.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26615.equity.usd.approve.2

    When Go to agreement agr26615.fcm statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.ExcludeInstruments.bond.1 in collateral tab
    And view statement
    And approve agreement agr26615
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26615.return
    And Exposure Management - create adhoc substitution events cash.usd
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.PendingReview
    And Exposure Management - event should be subReq.agreementRequirement.2m.equity.usd.PendingReview
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.usd.PendingReview
    When Exposure Management - tick events subCon.agreementRequirement.-1m.bond.usd.PendingReview,subReq.agreementRequirement.2m.equity.usd.PendingReview,subCon.agreementRequirement.0m.cash.usd.PendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.return.0.5m,equity.usd.call.0.6m
    And Edit booking - bulk booking equity.usd.recall.1m,bond.usd.delivery.0.9m
    And Edit booking - bulk booking cash.usd.return.1m,equity.usd.call.1m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.usd.Completed.firLeg.0.5m.secLeg.0.6m
    And Exposure Management - event should be subReq.agreementRequirement.2m.equity.usd.Completed.firLeg.1m.secLeg.0.9m
    And Exposure Management - event should be subCon.agreementRequirement.0m.cash.usd.Completed.firLeg.1m.secLeg.1m

    When Go to agreement agr26615.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking cash.return.0.3m.linkToEvent3
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking bond.return.0.4m.linkToEvent1
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And add recall booking - statement booking equity.recall.0.3m.linkToEvent2

    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent1.booking13 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent1.booking13.successToFeed
    When feed asset bookings feed.bond.delivery.0.5m.pending.linkToEvent2.booking14 by xml
    Then feed log should be feed.bond.delivery.0.5m.pending.linkToEvent2.booking14.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent3.booking15 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent3.booking15.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent1.booking16 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent1.booking16.successToFeed
    When feed asset bookings feed.bond.delivery.0.1m.pending.linkToEvent2.booking17 by xml
    Then feed log should be feed.bond.delivery.0.1m.pending.linkToEvent2.booking17.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent3.booking18 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent3.booking18.successToFeed

    When Go to agreement agr26615.fcm statement page by URL
    And approve agreement agr26615
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.9m.secLeg.0.7m
    And Exposure Management - event should be subReq.agreementRequirement.2m.PartiallyBooked.firstLeg.1.3m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.PartiallyBooked.firstLeg.1.3m.secLeg.1.1m

    When Go to agreement agr26615.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.return.0.4m.linkToEvent1.booking to nominalAmount.0.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking equity.recall.0.3m.linkToEvent2.booking to nominalAmount.0.8m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit recall booking cash.return.0.3m.linkToEvent3.booking to nominalAmount.0.2m

    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Bond Page
    And edit recall booking booking16 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking booking18 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking booking17 to statusChangeToPending

    When Go to agreement agr26615.fcm statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.Completed.firstLeg.0.6m.secLeg.0.8m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Completed.firstLeg.1.8m.secLeg.1.5m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.firstLeg.1.2m.secLeg.1.2m

  @v13.0.0 @JaneZhang @T28335 @F7851 @COL-3959 @wip
  Scenario: Verify Portfolio IA is shown subtotals per IM Category in EM FCM
    When feed external IA feed.external.ia.-1k.IAFS1 by xml
    Then feed log should be feed.external.ia.-1k.IAFS1.success

    When feed external IA feed.external.ia.1.1k.IAFS1 by xml
    Then feed log should be feed.external.ia.1.1k.IAFS1.success

    When feed external IA feed.external.ia.-2k.IAFS2 by xml
    Then feed log should be feed.external.ia.-2k.IAFS2.success

    When feed external IA feed.external.ia.2.2k.IAFS2 by xml
    Then feed log should be feed.external.ia.2.2k.IAFS2.success

    When feed external IA feed.external.ia.-3k.IAFS3 by xml
    Then feed log should be feed.external.ia.-3k.IAFS3.success

    When feed external IA feed.external.ia.3.3k.IAFS3 by xml
    Then feed log should be feed.external.ia.3.3k.IAFS3.success

    When feed external IA feed.external.ia.-4k.IAFS4 by xml
    Then feed log should be feed.external.ia.-4k.IAFS4.success

    When feed external IA feed.external.ia.4.4k.IAFS4 by xml
    Then feed log should be feed.external.ia.4.4k.IAFS4.success

    When feed external IA feed.external.ia.-5k.IAFS5 by xml
    Then feed log should be feed.external.ia.-5k.IAFS5.success

    When feed external IA feed.external.ia.5.5k.IAFS5 by xml
    Then feed log should be feed.external.ia.5.5k.IAFS5.success

    When Go to agreement agr28335.fcm.grossIM statement page by URL
    And approve agreement agr28335
    And edit summary exposure info
    Then exposure summary should be Check.PrincipalIA.CounterpartyIA.ByPortfolioIAType
    When quick link - agreement exposure management
    And Exposure Management - add column portfolioIa
    Then Exposure Management - event should be vm.noCall.withPortfolioIa
    When Exposure Management - set event vm.noCall value to editPortfolioIa
    Then Exposure Management - portfolioIa dialog should be Check.PrincipalIA.CounterpartyIA.ByPortfolioIAType.disabled

    When navigate to FX rate page
    When edit FX rate set oldFxrate to newFxrate
    When Go to agreement agr28335.fcm.grossIM statement page by URL
    And edit summary exposure info
    Then exposure summary should be Check.PrincipalIA.CounterpartyIA.ByPortfolioIAType.afterUpdateFxrate
    When quick link - agreement exposure management
    Then Exposure Management - event should be vm.noCall.withPortfolioIa.afterUpdateFxrate
    When Exposure Management - set event vm.noCall value to editPortfolioIa
    Then Exposure Management - portfolioIa dialog should be Check.PrincipalIA.CounterpartyIA.ByPortfolioIAType.disabled.afterUpdateFxrate

  @2011.2,2011.1,2012.2.SP2.3.3,2012.2.SP0.1 @JaneZhang @T11635 @F5366#D12330 @COL-4034 @wip
  Scenario: Verify that Intra day event can be created when Call Status is Margin Request Issued
    When Go to agreement agr11635.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.1m
    When Exposure Management - tick events call.1m
    And Exposure Management - manual send margin call letter createCallLetter
    Then Exposure Management - event should be call.1m.MarginRequestIssued

    When Go to agreement agr11635.otc.net statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And edit trade trade.1m to trade.2m
    And quick link - agreement exposure management
    Then Exposure Management - event should be call.1m.MarginRequestIssued,call.2m.pendingReview

  @v13.0.0 @JaneZhang @T26618 @F6978 @COL-4041 @wip
  Scenario: Verify legs will be updated for sub events with call status waived if event linked booking changed
    When navigate to security search page
    And search security 26618.bond.usd
    And edit security 26618.bond.usd.approve to 26618.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26618.bond.usd.approve
    And search security 26618.equity.usd
    And edit security 26618.equity.usd.approve to 26618.equity.usd.changeRecordDate.tMinus5
    And approve security 26618.equity.usd.approve
    And search security 26618.bond.usd.2
    And edit security 26618.bond.usd.approve.2 to 26618.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26618.bond.usd.approve.2
    And search security 26618.equity.usd.2
    And edit security 26618.equity.usd.approve.2 to 26618.equity.usd.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26618.equity.usd.approve.2

    When Go to agreement agr26618.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.ExcludeInstruments.bond.1 in collateral tab
    And view statement
    And approve agreement agr26618
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26618.return
    And Exposure Management - create adhoc substitution events cash.usd
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.pendingReview
    And Exposure Management - event should be subReq.agreementRequirement.2m.pendingReview
    And Exposure Management - event should be subCon.agreementRequirement.0m.pendingReview

    And Exposure Management - tick events subCon.agreementRequirement.-1m.pendingReview,subReq.agreementRequirement.2m.pendingReview,subCon.agreementRequirement.0m.pendingReview
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.return.0.5m,equity.usd.call.0.6m,equity.usd.recall.1m,bond.usd.delivery.0.9m,cash.usd.return.1m,equity.usd.call.1m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.Completed.firstLeg.0.5m.secLeg.0.6m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Completed.firstLeg.1m.secLeg.0.9m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.firstLeg.1m.secLeg.1m
    When Exposure Management - set event subCon.agreementRequirement.-1m.Completed.firstLeg.0.5m.secLeg.0.6m value to callStatus.waived
    And Exposure Management - set event subReq.agreementRequirement.2m.Completed.firstLeg.1m.secLeg.0.9m value to callStatus.waived
    And Exposure Management - set event subCon.agreementRequirement.0m.Completed.firstLeg.1m.secLeg.1m value to callStatus.waived
    And Exposure Management - save changes

    When Go to agreement agr26618.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking cash.return.0.3m.linkToEvent3
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking bond.return.0.4m.linkToEvent1
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And add recall booking - statement booking equity.recall.0.3m.linkToEvent2

    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent1.booking13 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent1.booking13.successToFeed
    When feed asset bookings feed.bond.delivery.0.5m.pending.linkToEvent2.booking14 by xml
    Then feed log should be feed.bond.delivery.0.5m.pending.linkToEvent2.booking14.successToFeed
    When feed asset bookings feed.equity.call.0.1m.pending.linkToEvent3.booking15 by xml
    Then feed log should be feed.equity.call.0.1m.pending.linkToEvent3.booking15.successToFeed

    When Go to agreement agr26618.otc.net statement page by URL
    And approve agreement agr26618
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.Waived.firstLeg.0.9m.secLeg.0.7m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Waived.firstLeg.1.3m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Waived.firstLeg.1.3m.secLeg.1.1m

    When Go to agreement agr26618.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.return.0.4m.linkToEvent1.booking to nominalAmount.0.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking equity.recall.0.3m.linkToEvent2.booking to nominalAmount.0.8m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit recall booking cash.return.0.3m.linkToEvent3.booking to nominalAmount.0.2m

    When Go to agreement agr26618.otc.net statement page by URL
    And approve agreement agr26618
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.Waived.firstLeg.0.6m.secLeg.0.7m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Waived.firstLeg.1.8m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Waived.firstLeg.1.2m.secLeg.1.1m


  @2012.2,2012.2.SP0.1 @JaneZhang @T20962 @F3641#F5366 @COL-4055 @wip
  Scenario: Verify that Historical Event for multi model event when new exposure after issued net event status issued
    When Go to agreement agr20962.multiModel.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column model
    And Exposure Management - expand events call.agreementRequirement.1.8m.top
    Then Exposure Management - event should be call.agreementRequirement.1m.m2,call.agreementRequirement.1m.m1
    When Exposure Management - tick events call.agreementRequirement.1.8m.top
    And Exposure Management - manual send statement notification letter createStatementNotificationLetter
    And Exposure Management - expand events call.agreementRequirement.1.8m.top.MarginRequestIssued
    Then Exposure Management - event should be call.agreementRequirement.1m.m2.MarginRequestIssued,call.agreementRequirement.1m.m1.MarginRequestIssued

    When Go to agreement agr20962.multiModel.net statement page by URL
    And edit summary exposure info
    And view product type ProductQTP on exposure summary page
    And add OTC trades tradeExposureAmount.0.6m.m2
    And quick link - view statement
    And approve agreement agr20962
    And quick link - agreement exposure management
    And Exposure Management - expand events call.agreementRequirement.1.8m.top.MarginRequestIssued,call.agreementRequirement.2.4m.top.PendingReview.orange
    Then Exposure Management - event should be call.agreementRequirement.1.8m.top.MarginRequestIssued.blue
    And Exposure Management - event should be call.agreementRequirement.1m.m2.MarginRequestIssued.blue
    And Exposure Management - event should be call.agreementRequirement.1m.m1.MarginRequestIssued.blue
    And Exposure Management - event should be call.agreementRequirement.2.4m.top.PendingReview.orange
    And Exposure Management - event should be call.agreementRequirement.1.75m.m2.PendingReview.orange
    And Exposure Management - event should be call.agreementRequirement.1m.m1.PendingReview.orange

  @13.0.0 @JaneZhang @T26609 @F6978 @COL-4057 @wip
  Scenario: Verify Call Status and legs will be updated for sub events with call status request confirmed if event linked booking changed
    When navigate to security search page
    And search security 26609.bond.usd
    And edit security 26609.bond.usd.approve to 26609.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26609.bond.usd.approve
    And search security 26609.equity.gbp
    And edit security 26609.equity.gbp.approve to 26609.equity.gbp.changeRecordDate.tMinus5
    And approve security 26609.equity.gbp.approve
    And search security 26609.bond.usd.2
    And edit security 26609.bond.usd.approve.2 to 26609.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26609.bond.usd.approve.2
    And search security 26609.equity.gbp.2
    And edit security 26609.equity.gbp.approve.2 to 26609.equity.gbp.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26609.equity.gbp.approve.2

    When Go to agreement agr26609.multimodel.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.ExcludeInstruments.bond.1 in collateral tab
    And view statement
    And approve agreement agr26609
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26609.return
    And Exposure Management - create substitution event - expand events agr26609.subEvent
    And Exposure Management - create adhoc substitution events cash.eur
    And Exposure Management - add column model
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.pendingReview
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.pendingReview
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.pendingReview
    When Exposure Management - tick events subCon.m1.agreementRequirement.-1m.pendingReview
    And Exposure Management - manual send substitution confirmation letter createSubConfirmationLetter
    And Exposure Management - all filters - search dynamic filter agr26609.event
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    And Exposure Management - tick events subCon.m2.agreementRequirement.-4m.pendingReview
    And Exposure Management - manual send substitution confirmation letter createSubConfirmationLetter
    And Exposure Management - all filters - search dynamic filter agr26609.event
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    And Exposure Management - tick events subCon.m3.agreementRequirement.0m.pendingReview
    And Exposure Management - manual send substitution confirmation letter createSubConfirmationLetter
    And Exposure Management - all filters - search dynamic filter agr26609.event
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.RequestConfirmed
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.RequestConfirmed
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.RequestConfirmed

    And Exposure Management - tick events subCon.m1.agreementRequirement.-1m.RequestConfirmed,subCon.m2.agreementRequirement.-4m.RequestConfirmed,subCon.m3.agreementRequirement.0m.RequestConfirmed
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.return.0.5m,equity.gbp.call.0.2m
    And Edit booking - bulk booking equity.gbp.return.1m,bond.usd.call.0.9m
    And Edit booking - bulk booking cash.eur.return.0.55m,equity.gbp.call.0.5m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.5m.secLeg.0.1m
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.Completed.firstLeg.1m.secLeg.1.8m
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.PartiallyBooked.firstLeg.0.55m.secLeg.0.125m
    When Exposure Management - set event subCon.m1.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.5m.secLeg.0.1m value to callStatus.waived
    And Exposure Management - set event subCon.m2.agreementRequirement.-4m.Completed.firstLeg.1m.secLeg.1.8m value to callStatus.waived
    And Exposure Management - set event subCon.m3.agreementRequirement.0m.PartiallyBooked.firstLeg.0.55m.secLeg.0.125m value to callStatus.waived
    And Exposure Management - save changes
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    When Exposure Management - set event subCon.m1.agreementRequirement.-1m.Waived.firstLeg.0.5m.secLeg.0.1m value to callStatus.requestConfirmed
    And Exposure Management - set event subCon.m2.agreementRequirement.-4m.Waived.firstLeg.1m.secLeg.1.8m value to callStatus.requestConfirmed
    And Exposure Management - set event subCon.m3.agreementRequirement.0m.Waived.firstLeg.0.55m.secLeg.0.125m value to callStatus.requestConfirmed

    And Exposure Management - save changes
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.requestConfirmed.firstLeg.0.5m.secLeg.0.1m
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.requestConfirmed.firstLeg.1m.secLeg.1.8m
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.requestConfirmed.firstLeg.0.55m.secLeg.0.125m

    When Go to agreement agr26609.multimodel.net statement page by URL
    And select model m3
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    And add call bookings - statement booking cash.return.0.1m.linkToEvent3
    And quick link - view statement
    And select model m1
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking bond.return.0.4m.linkToEvent1
    And quick link - view statement
    And select model m2
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And add recall booking - statement booking equity.recall.0.6m.linkToEvent2

    When feed asset bookings feed.equity.call.1.2m.pending.linkToEvent1.booking13 by xml
    Then feed log should be feed.equity.call.1.2m.pending.linkToEvent1.booking13.successToFeed
    When feed asset bookings feed.bond.call.0.5m.pending.linkToEvent2.booking14 by xml
    Then feed log should be feed.bond.call.0.5m.pending.linkToEvent2.booking14.successToFeed
    When feed asset bookings feed.equity.call.0.8m.pending.linkToEvent3.booking15 by xml
    Then feed log should be feed.equity.call.0.8m.pending.linkToEvent3.booking15.successToFeed
    When feed asset bookings feed.equity.call.0.2m.pending.linkToEvent1.booking16 by xml
    Then feed log should be feed.equity.call.0.2m.pending.linkToEvent1.booking16.successToFeed
    When feed asset bookings feed.bond.call.0.1m.pending.linkToEvent2.booking17 by xml
    Then feed log should be feed.bond.call.0.1m.pending.linkToEvent2.booking17.successToFeed
    When feed asset bookings feed.equity.call.0.2m.pending.linkToEvent3.booking18 by xml
    Then feed log should be feed.equity.call.0.2m.pending.linkToEvent3.booking18.successToFeed

    When Go to agreement agr26609.multimodel.net statement page by URL
    And approve agreement agr26609
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.9m.secLeg.0.7m
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.Completed.firstLeg.1.6m.secLeg.2.8m
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.PartiallyBooked.firstLeg.0.65m.secLeg.0.325m
    When Exposure Management - set event subCon.m1.agreementRequirement.-1m.PartiallyBooked.firstLeg.0.9m.secLeg.0.7m value to callStatus.waived
    And Exposure Management - set event subCon.m2.agreementRequirement.-4m.Completed.firstLeg.1.6m.secLeg.2.8m value to callStatus.waived
    And Exposure Management - set event subCon.m3.agreementRequirement.0m.PartiallyBooked.firstLeg.0.65m.secLeg.0.325m value to callStatus.waived
    And Exposure Management - save changes
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    When Exposure Management - set event subCon.m1.agreementRequirement.-1m.waived.firstLeg.0.9m.secLeg.0.7m value to callStatus.requestConfirmed
    And Exposure Management - set event subCon.m2.agreementRequirement.-4m.waived.firstLeg.1.6m.secLeg.2.8m value to callStatus.requestConfirmed
    And Exposure Management - set event subCon.m3.agreementRequirement.0m.waived.firstLeg.0.65m.secLeg.0.325m value to callStatus.requestConfirmed
    And Exposure Management - save changes
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.requestConfirmed.firstLeg.0.9m.secLeg.0.7m
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.requestConfirmed.firstLeg.1.6m.secLeg.2.8m
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.requestConfirmed.firstLeg.0.65m.secLeg.0.325m

    When Go to agreement agr26609.multimodel.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.return.0.4m.linkToEvent1.booking to nominalAmount.0.5m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And edit recall booking equity.return.0.6m.linkToEvent2.booking to nominalAmount.1m
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.EUR agreement asset CASH Page
    And edit recall booking cash.return.0.1m.linkToEvent3.booking to nominalAmount.0.25m

    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Bond Page
    And edit recall booking booking16 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And edit recall booking booking18 to statusChangeToPending
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking booking17 to statusChangeToPending

    When Go to agreement agr26609.multimodel.net statement page by URL
    And approve agreement agr26609
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.1m,subCon.top.agreementRequirement.2m,subCon.top.agreementRequirement.0m
    Then Exposure Management - event should be subCon.m1.agreementRequirement.-1m.PartiallyBooked.firstLeg.1m.secLeg.0.8m
    And Exposure Management - event should be subCon.m2.agreementRequirement.-4m.Completed.firstLeg.2m.secLeg.3m
    And Exposure Management - event should be subCon.m3.agreementRequirement.0m.PartiallyBooked.firstLeg.0.8m.secLeg.0.375m

  @2012.2 @JaneZhang @T19442 @F1206 @COL-4147 @wip
  Scenario: Verify that Bulk Booking should be made correctly for Repo Delivery Excess imply Delivery Return events
    When navigate to security search page
    And add securities bondinstrument
    And search security bondSecuritySearchAmended
    And approve security bondSecuritySearchWillBeApproved

    When Go to agreement agr19442.repo statement page by URL
    And edit summary exposure info
    And view product type ProductRepo on exposure summary page
    And add Repo trades trade.notional.-1k
    Then trade summary should be trade.principalValuationAmount.13566
    When quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking booking.cash.usd.call.10k
    And quick link - view statement
    And approve agreement agr19442
    And quick link - agreement exposure management
    Then Exposure Management - event should be delivery.agreementRequirement.23566
    When Exposure Management - tick events delivery.agreementRequirement.23566
    And Exposure Management - manual send margin delivery letter createDeliveryLetter
    Then Exposure Management - event should be delivery.agreementRequirement.23566.MarginRequestConfirmed
    When Exposure Management - tick events delivery.agreementRequirement.23566.MarginRequestConfirmed
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be bulkBooking.value.23566
    When Edit booking - bulk booking cash.usd.return.10000
    Then EM Bulk booking should be bulkBooking.value.13566
    When Edit booking - bulk booking cash.usd.delivery.13566
    And quick link - save
    Then Exposure Management - event should be delivery.agreementRequirement.23566.completed


  @v13.0.0 @JaneZhang @T26601 @F6978 @COL-4179 @wip
  Scenario: Verify Call Status and legs will be updated for sub events with call status request issued if event linked booking changed
    When navigate to security search page
    And search security 26601.bond.usd
    And edit security 26601.bond.usd.approve to 26601.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26601.bond.usd.approve
    And search security 26601.equity.usd
    And edit security 26601.equity.usd.approve to 26601.equity.usd.changeRecordDate.tMinus5
    And approve security 26601.equity.usd.approve
    And search security 26601.bond.usd.2
    And edit security 26601.bond.usd.approve.2 to 26601.bond.usd.2.changeRecordDate.tPlus10
    And approve security 26601.bond.usd.approve.2
    And search security 26601.equity.usd.2
    And edit security 26601.equity.usd.approve.2 to 26601.equity.usd.2.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26601.equity.usd.approve.2

    When Go to agreement agr26601.otc.umb statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26601.recall
    And Exposure Management - create substitution event - expand events agr26601.subEvent
    And Exposure Management - create adhoc substitution events cash.usd
    When Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.pendingReview.bond
    And Exposure Management - event should be subReq.agreementRequirement.2m.pendingReview.equity
    And Exposure Management - event should be subCon.agreementRequirement.0m.pendingReview.cash

    When Exposure Management - tick events subReq.agreementRequirement.1m.pendingReview.bond
    And Exposure Management - manual send substitution request letter createSubRequestLetter
    And Exposure Management - all filters - search dynamic filter agr26601.umb
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    And Exposure Management - tick events subReq.agreementRequirement.2m.pendingReview.equity
    And Exposure Management - manual send substitution request letter createSubRequestLetter
    And Exposure Management - all filters - search dynamic filter agr26601.umb
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    And Exposure Management - tick events subCon.agreementRequirement.0m.pendingReview.cash
    And Exposure Management - manual send substitution request letter createSubRequestLetter
    And Exposure Management - all filters - search dynamic filter agr26601.umb
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.RequestIssued.bond
    And Exposure Management - event should be subReq.agreementRequirement.2m.RequestIssued.equity
    And Exposure Management - event should be subCon.agreementRequirement.0m.RequestIssued.cash

    When Exposure Management - tick events subReq.agreementRequirement.1m.RequestIssued.bond,subReq.agreementRequirement.2m.RequestIssued.equity,subCon.agreementRequirement.0m.RequestIssued.cash
    And Exposure Management - go to bulk booking page
    And Edit booking - bulk booking bond.usd.recall.0.5m,equity.usd.delivery.0.4m,equity.usd.recall.1m,bond.usd.delivery.0.9m,cash.usd.recall.1m,equity.usd.delivery.1m
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.Completed.bond.firstLeg.0.5m.secLeg.0.4m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Completed.equity.firstLeg.1m.secLeg.0.9m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.cash.firstLeg.1m.secLeg.1m
    When Exposure Management - set event subReq.agreementRequirement.1m.Completed.bond.firstLeg.0.5m.secLeg.0.4m value to callStatus.waived
    And Exposure Management - set event subReq.agreementRequirement.2m.Completed.equity.firstLeg.1m.secLeg.0.9m value to callStatus.waived
    And Exposure Management - set event subCon.agreementRequirement.0m.Completed.cash.firstLeg.1m.secLeg.1m value to callStatus.waived
    And Exposure Management - save changes
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    When Exposure Management - set event subReq.agreementRequirement.1m.Waived.bond.firstLeg.0.5m.secLeg.0.4m value to callStatus.RequestIssued
    And Exposure Management - set event subReq.agreementRequirement.2m.Waived.equity.firstLeg.1m.secLeg.0.9m value to callStatus.RequestIssued
    And Exposure Management - set event subCon.agreementRequirement.0m.Waived.cash.firstLeg.1m.secLeg.1m value to callStatus.RequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.RequestIssued.bond.firstLeg.0.5m.secLeg.0.4m
    And Exposure Management - event should be subReq.agreementRequirement.2m.RequestIssued.equity.firstLeg.1m.secLeg.0.9m
    And Exposure Management - event should be subCon.agreementRequirement.0m.RequestIssued.cash.firstLeg.1m.secLeg.1m

    When Go to agreement agr26601.otc.umb statement page by URL
    And select agreement agr26601.fund3 in umbrella list
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking cash.return.0.3m.linkToEvent3
    And quick link - view statement
    And select agreement agr26601.fund1 in umbrella list
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking bond.return.0.4m.linkToEvent1
    And quick link - view statement
    And select agreement agr26601.fund2 in umbrella list
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And add recall booking - statement booking equity.recall.0.3m.linkToEvent2

    When feed asset bookings feed.equity.Delivery.0.6m.pending.linkToEvent1.booking13 by xml
    Then feed log should be feed.equity.Delivery.0.6m.pending.linkToEvent1.booking13.successToFeed
    When feed asset bookings feed.bond.delivery.0.5m.pending.linkToEvent2.booking14 by xml
    Then feed log should be feed.bond.delivery.0.5m.pending.linkToEvent2.booking14.successToFeed
    When feed asset bookings feed.equity.Delivery.0.4m.pending.linkToEvent3.booking15 by xml
    Then feed log should be feed.equity.Delivery.0.4m.pending.linkToEvent3.booking15.successToFeed

    When Go to agreement agr26601.otc.umb statement page by URL
    And select agreement agr26601.fund1 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund2 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund3 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund4 in umbrella list
    And approve agreement agr26601
    And quick link - agreement exposure management
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.PartiallyBooked.bond.firstLeg.0.9m.secLeg.1m
    And Exposure Management - event should be subReq.agreementRequirement.2m.PartiallyBooked.equity.firstLeg.1.3m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.PartiallyBooked.cash.firstLeg.1.3m.secLeg.1.4m

    When Exposure Management - set event subReq.agreementRequirement.1m.PartiallyBooked.bond.firstLeg.0.9m.secLeg.1m value to callStatus.waived
    And Exposure Management - set event subReq.agreementRequirement.2m.PartiallyBooked.equity.firstLeg.1.3m.secLeg.1.4m value to callStatus.waived
    And Exposure Management - set event subCon.agreementRequirement.0m.PartiallyBooked.cash.firstLeg.1.3m.secLeg.1.4m value to callStatus.waived
    And Exposure Management - save changes
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    When Exposure Management - set event subReq.agreementRequirement.1m.Waived.bond.firstLeg.0.9m.secLeg.1m value to callStatus.RequestIssued
    And Exposure Management - set event subReq.agreementRequirement.2m.Waived.equity.firstLeg.1.3m.secLeg.1.4m value to callStatus.RequestIssued
    And Exposure Management - set event subCon.agreementRequirement.0m.Waived.cash.firstLeg.1.3m.secLeg.1.4m value to callStatus.RequestIssued
    And Exposure Management - save changes
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.RequestIssued.bond.firstLeg.0.9m.secLeg.1m
    And Exposure Management - event should be subReq.agreementRequirement.2m.RequestIssued.equity.firstLeg.1.3m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.RequestIssued.cash.firstLeg.1.3m.secLeg.1.4m

    When Go to agreement agr26601.otc.umb statement page by URL
    And select agreement agr26601.fund1 in umbrella list
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And edit recall booking bond.recall.0.4m.linkToEvent1.booking to nominalAmount.0.5m
    And quick link - view statement
    And select agreement agr26601.fund2 in umbrella list
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking equity.recall.0.3m.linkToEvent2.booking to nominalAmount.0.5m
    And quick link - view statement
    And select agreement agr26601.fund3 in umbrella list
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit recall booking cash.recall.0.3m.linkToEvent3.booking to nominalAmount.0.5m

    When Go to agreement agr26601.otc.umb statement page by URL
    And select agreement agr26601.fund1 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund2 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund3 in umbrella list
    And approve agreement agr26601
    And select agreement agr26601.fund4 in umbrella list
    And approve agreement agr26601
    And quick link - agreement exposure management
    And Exposure Management - expand events subReq.agreementRequirement.1m.top,subReq.agreementRequirement.2m.top,subReq.agreementRequirement.0m.top
    Then Exposure Management - event should be subReq.agreementRequirement.1m.Completed.bond.firstLeg.1m.secLeg.1m
    And Exposure Management - event should be subReq.agreementRequirement.2m.Completed.equity.firstLeg.1.5m.secLeg.1.4m
    And Exposure Management - event should be subCon.agreementRequirement.0m.Completed.cash.firstLeg.1.5m.secLeg.1.4m

  @v13.0.0 @JaneZhang @T26540 @F6561 @COL-4231 @wip
  Scenario: Verify Net Multi model Agreement Sub event Call status Workflow Bulk booking Partially booked
    When navigate to security search page
    And search security 26540.bond.usd
    And edit security 26540.bond.usd.approve to 26540.bond.usd.changeRecordDate.tMinus6.changeMaturityDate.tPlus6
    And approve security 26540.bond.usd.approve
    And search security 26540.bond.usd.2
    And edit security 26540.bond.usd.approve.2 to 26540.bond.usd.2.changeRecordDate.tMinus6.changeMaturityDate.tPlus6
    And approve security 26540.bond.usd.approve.2

    When Go to agreement agr26540.otc.multiModel.net statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - add column source
    And Exposure Management - expand events subReq.4m.top
    Then Exposure Management - event should be subReq.m1.bond.usd.2.agreementRequirement.4m,subReq.m1.bond.usd.1.agreementRequirement.4m
    When Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26540.return
    And Exposure Management - create substitution event - expand events agr26540.subEvent
    And Exposure Management - create adhoc substitution events subReq.m2.bond.gbp.1
    When Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26540.return
    And Exposure Management - create substitution event - expand events agr26540.subEvent
    And Exposure Management - create adhoc substitution events subReq.m2.bond.gbp.2
    And Exposure Management - expand events subReq.4m.top,subCon.0m.top
    Then Exposure Management - event should be subCon.m2.bond.usd.2.agreementRequirement.0m,subCon.m2.bond.usd.1.agreementRequirement.0m

    When Exposure Management - tick events subReq.m1.bond.usd.2.agreementRequirement.4m,subReq.m1.bond.usd.1.agreementRequirement.4m
    And Exposure Management - tick events subCon.m2.bond.usd.2.agreementRequirement.0m,subCon.m2.bond.usd.1.agreementRequirement.0m
    And Exposure Management - go to bulk booking page
    Then EM Bulk booking should be subReq.recall.bond.usd.1.movementAndInstrumentNotEditable,subReq.delivery.event1.movementNotEditable
    And EM Bulk booking should be subReq.recall.bond.usd.2.movementAndInstrumentNotEditable,subReq.delivery.event2.movementNotEditable
    And EM Bulk booking should be subCon.return.bond.gbp.1.movementAndInstrumentNotEditable,subCon.call.event3.movementNotEditable
    And EM Bulk booking should be subCon.return.bond.gbp.2.movementAndInstrumentNotEditable,subCon.call.event4.movementNotEditable
    And Edit booking - bulk booking subReq.recall.bond.usd.1.notional.2m.event1,subReq.delivery.bond.usd.3.notional.3m.event1
    And Edit booking - bulk booking subReq.recall.bond.usd.2.notional.2m.event2
    And Edit booking - bulk booking subCon.return.bond.gbp.1.notional.3m.event3,subCon.call.bond.gbp.3.notional.2m.event3
    And Edit booking - bulk booking subCon.call.cash.gbp.notional.2m.event4
    And quick link - save
    And Exposure Management - override warning overrideWarning
    And quick link - save
    And Exposure Management - expand events subReq.4m.top,subCon.0m.top
    Then Exposure Management - event should be subReq.m1.bond.usd.1.agreementRequirement.4m.PartiallyBooked,subReq.m1.bond.usd.1.agreementRequirement.4m.PartiallyBooked
    And Exposure Management - event should be subCon.m2.bond.usd.2.agreementRequirement.0m.PartiallyBooked,subCon.m2.bond.usd.1.agreementRequirement.0m.PartiallyBooked

    When Go to agreement agr26540.otc.multiModel.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And open asset booking editor page bond.usd.1.recall.nominalAmount.2m
    Then call booking in booking editor page should be booking.event1
    When asset booking edit - click cancel button
    And open asset booking editor page bond.usd.3.delivery.nominalAmount.3m
    Then call booking in booking editor page should be booking.event1
    When asset booking edit - click cancel button
    And open asset booking editor page bond.usd.2.recall.nominalAmount.2m
    Then call booking in booking editor page should be booking.event2
    When asset booking edit - click cancel button
    And click back button to previous page

    And view asset type asset.type.CASH.GBP agreement asset CASH Page
    And open asset booking editor page CASH.GBP.call.nominalAmount.2m
    Then call booking in booking editor page should be booking.event4
    When asset booking edit - click cancel button
    And click back button to previous page

    And view asset type asset.type.bond.gbp agreement asset Bond Page
    And open asset booking editor page bond.gbp.1.return.nominalAmount.3m
    Then call booking in booking editor page should be booking.event3
    When asset booking edit - click cancel button
    And open asset booking editor page bond.gbp.3.call.nominalAmount.2m
    Then call booking in booking editor page should be booking.event3

  @v13.0.0 @JaneZhang @T26596 @F6978 @COL-4369 @wip
  Scenario: Verify Agreement Requirement will be updated for sub events with call status pending review if NonEvent linked booking changed
    When navigate to security search page
    And search security 26596.bond.usd
    And edit security 26596.bond.usd.approve to 26596.bond.usd.changeRecordDate.tMinus5.changeMaturityDate.tPlus5
    And approve security 26596.bond.usd.approve
    And search security 26596.equity.usd
    And edit security 26596.equity.usd.approve to 26596.equity.usd.changeRecordDate.tMinus10
    And approve security 26596.equity.usd.approve

    When Go to agreement agr26596.otc.net statement page by URL
    And quick link - agreement review
    And edit OTC agreement equity.usd.EligibilityRule to equity.usd.EligibilityRule.EligibleCurrencies.GBP in collateral tab
    And view statement
    And approve agreement agr26596
    And quick link - agreement exposure management
    And Exposure Management - add column source
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26596.recall
    And Exposure Management - create adhoc substitution events cash.usd,bond.usd.id,equity.usd.id
    Then Exposure Management - event should be subCon.agreementRequirement.-1m.bond.pendingReview,subReq.agreementRequirement.1m.bond.pendingReview
    And Exposure Management - event should be subCon.agreementRequirement.-2m.equity.pendingReview,subReq.agreementRequirement.2m.equity.pendingReview
    And Exposure Management - event should be subReq.agreementRequirement.0m.pendingReview.equity,subReq.agreementRequirement.0m.pendingReview.bond,subReq.agreementRequirement.0m.pendingReview.cash

    When feed asset bookings feed.bond.call.0.5m.pendingSettlement.booking11 by xml
    Then feed log should be feed.bond.call.0.5m.pendingSettlement.booking11.successToFeed
    When feed asset bookings feed.bond.delivery.0.5m.pending.booking12 by xml
    Then feed log should be feed.bond.delivery.0.5m.pending.booking12.successToFeed

    When Go to agreement agr26596.otc.net statement page by URL
    And edit asset summary info
    And view asset type asset.type.equity.usd agreement asset Equity Page
    And edit recall booking equity.return.1m.booking to bookingStatus.cancelled
    And quick link - view statement
    And edit asset summary info
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And edit recall booking cash.delivery.3m.booking to nominalAmount.4m

    When Go to agreement agr26596.otc.net statement page by URL
    And quick link - agreement exposure management
    Then Exposure Management - event should be subCon.agreementRequirement.-1.5m.bond.pendingReview.afterChangeBooking,subReq.agreementRequirement.1.5m.bond.pendingReview.afterChangeBooking
    And Exposure Management - event should be subCon.agreementRequirement.-3m.equity.pendingReview.afterChangeBooking,subReq.agreementRequirement.2m.equity.pendingReview.afterChangeBooking
    And Exposure Management - event should be subReq.agreementRequirement.0m.pendingReview.equity.afterChangeBooking,subReq.agreementRequirement.0m.pendingReview.bond.afterChangeBooking,subReq.agreementRequirement.0m.pendingReview.cash.afterChangeBooking

  @v13.0.0 @JaneZhang @T26610 @F6978 @COL-4343 @wip
  Scenario: Verify only legs will be updated for sub events with call status partially booked if the booking dependent items changed
    When navigate to security search page
    And search security 26610.bond.usd
    And edit security 26610.bond.usd.approve to 26610.bond.usd.changeRecordDate.tMinus10.changeMaturityDate.tPlus10
    And approve security 26610.bond.usd.approve
    And search security 26610.equity.gbp
    And edit security 26610.equity.gbp.approve to 26610.equity.gbp.changeRecordDate.tMinus5.changeMaturityDate.tPlus5
    And approve security 26610.equity.gbp.approve
    And search security 26610.equity.gbp.2
    And edit security 26610.equity.gbp.approve.2 to 26610.equity.gbp.2.changeRecordDate.tPlus10.changeMaturityDate.tPlus1000
    And approve security 26610.equity.gbp.approve.2

    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.EligibilityRule to bond.usd.EligibilityRule.MinimumEligibilityRating.AAA in collateral tab
    And view statement
    And approve agreement agr26610
    And quick link - agreement exposure management
    And Exposure Management - click create substitution button
    And Exposure Management - search adhoc substitution agr26610.recall
    And Exposure Management - create substitution event - expand events agr26610.subEvent
    And Exposure Management - create adhoc substitution events cash.gbp,equity.gbp.1
    And Exposure Management - add column model
    And Exposure Management - expand events subReq.top.agreementRequirement.0,subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.7680000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.pendingReview.equity.gbp.1,subReq.m2.agreementRequirement.0.pendingReview.cash.gbp
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.pendingReview,subReq.m2.agreementRequirement.3840000.pendingReview

    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And select model m1
    And edit asset summary info
    And view asset type asset.type.bond.usd agreement asset Bond Page
    And add return booking - statement booking m1.bond.usd.1.return.vm.0.5m.im.0.5m.linkToEvent1.Bond.usd
    And click back button to previous page
    And view asset type asset.type.CASH.USD agreement asset CASH Page
    And add call bookings - statement booking m1.cash.usd.call.vm.0.3m.im.0.2m.linkToEvent1.Bond.usd
    And quick link - view statement
    And select model m2
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And add recall booking - statement booking m2.equity.gbp.recall.vm.0.5m.im.0.5m.linkToEvent2.equity.gbp,m2.equity.gbp.2.delivery.vm.0.5m.im.0.5m.linkToEvent2.equity.gbp,m2.equity.gbp.2.delivery.vm.0.5m.im.0.5m.linkToEvent3.equity.gbp
    And click back button to previous page
    And view asset type asset.type.CASH.GBP agreement asset CASH Page
    And add call bookings - statement booking m2.cash.gbp.call.vm.0.5m.im.0.5m.linkToEvent3.equity.gbp
    And quick link - view statement
    And approve agreement agr26610
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.7680000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.PartiallyBooked.equity.gbp.1.firstLegAmount.0.35m.secLegAmount.0.4m,subReq.m2.agreementRequirement.0.PartiallyBooked.cash.gbp.firstLegAmount.0.35m.secLegAmount.0.4m
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.PartiallyBooked.firstLegAmount.1.62m.secLegAmount.0.24m
    And Exposure Management - event should be subReq.m2.agreementRequirement.3840000.PartiallyBooked.firstLegAmount.1.92m.secLegAmount.2m

    When navigate to FX rate page
    When edit FX rate set oldFxrate to newFxrate
    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.1920000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.PartiallyBooked.equity.gbp.1.firstLegAmount.0.35m.secLegAmount.0.4m,subReq.m2.agreementRequirement.0.PartiallyBooked.cash.gbp.firstLegAmount.0.35m.secLegAmount.0.4m
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.PartiallyBooked.firstLegAmount.1.62m.secLegAmount.0.24m
    And Exposure Management - event should be subReq.m2.agreementRequirement.3840000.PartiallyBooked.firstLegAmount.1.92m.secLegAmount.2m

    When navigate to security search page
    And search security 26610.bond.usd
    And edit security 26610.bond.usd.approve to 26610.bond.usd.changeDirtyPrice.400
    And approve security 26610.bond.usd.approve
    And search security 26610.equity.gbp
    And edit security 26610.equity.gbp.approve to 26610.equity.gbp.changeDirtyPrice.6
    And approve security 26610.equity.gbp.approve
    And search security 26610.equity.gbp.2
    And edit security 26610.equity.gbp.approve.2 to 26610.equity.gbp.2.changeDirtyPrice.10
    And approve security 26610.equity.gbp.approve.2
    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.1920000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.PartiallyBooked.equity.gbp.1.firstLegAmount.0.35m.secLegAmount.0.8m
    And Exposure Management - event should be subReq.m2.agreementRequirement.0.PartiallyBooked.cash.gbp.firstLegAmount.0.35m.secLegAmount.0.8m
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.PartiallyBooked.firstLegAmount.3.24m.secLegAmount.0.24m
    And Exposure Management - event should be subReq.m2.agreementRequirement.3840000.PartiallyBooked.firstLegAmount.3.84m.secLegAmount.4m

    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And quick link - agreement review
    And edit OTC agreement bond.usd.haircut to bond.usd.haircut.45 in collateral tab
    And edit OTC agreement equity.gbp.haircut to equity.gbp.haircut.40 in collateral tab
    And view statement
    And approve agreement agr26610
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.1920000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.PartiallyBooked.equity.gbp.1.firstLegAmount.0.35m.secLegAmount.0.4m
    And Exposure Management - event should be subReq.m2.agreementRequirement.0.PartiallyBooked.cash.gbp.firstLegAmount.0.35m.secLegAmount.0.4m
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.PartiallyBooked.firstLegAmount.1.62m.secLegAmount.0.24m
    And Exposure Management - event should be subReq.m2.agreementRequirement.3840000.PartiallyBooked.firstLegAmount.1.92m.secLegAmount.2m

    When Go to agreement agr26610.multimodel.fcm statement page by URL
    And edit asset summary info
    And view asset type asset.type.equity.gbp agreement asset Equity Page
    And edit recall booking equity.recall.valuationPercentage.80 to valuationPercentage.40
    And edit recall booking equity.delivery.valuationPercentage.10 to valuationPercentage.20
    And quick link - view statement
    And quick link - agreement exposure management
    And Exposure Management - expand events subCon.top.agreementRequirement.-3240000,subReq.top.agreementRequirement.1920000
    Then Exposure Management - event should be subReq.m2.agreementRequirement.0m.PartiallyBooked.equity.gbp.1.firstLegAmount.0.35m.secLegAmount.0.8m
    And Exposure Management - event should be subReq.m2.agreementRequirement.0.PartiallyBooked.cash.gbp.firstLegAmount.0.35m.secLegAmount.0.8m
    And Exposure Management - event should be subCon.m1.agreementRequirement.-3240000.PartiallyBooked.firstLegAmount.1.62m.secLegAmount.0.24m
    And Exposure Management - event should be subReq.m2.agreementRequirement.3840000.PartiallyBooked.firstLegAmount.0.96m.secLegAmount.2m
#