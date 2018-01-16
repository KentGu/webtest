package com.lombardrisk.data;

public enum Menu {
    HOME("Home", "/colline/index.do"), VIEW_ORGANISATIONS("Organisations>Organisation Details>View Organisations",
            "/colline/organisation/showOrg.do?id="), ADD_ORGANISATION(
            "Organisations>Organisation Details>Add Organisation",
            "/colline/redir.do?uri=organisation.addOrg"), DELETE_ORGANISATION(
            "Organisations>Organisation Details>Delete Organisation",
            "/colline/organisation/showDeleteOrganisation.do"), BOOKS(
            "Organisations>Organisation Admin>Books",
            "/colline/showHierachy.do?bean=orgBook"), INDUSTRY(
            "Organisations>Organisation Admin>Industry",
            "/colline/showHierachy.do?bean=orgIndustry"), RATINGLEVEL(
            "Organisations>Organisation Admin>Rating Level",
            "/colline/organisation/showRatingLevel.do"), GLOBAL_PREFERENCES(
            "Organisations>Organisation Admin>Global Preferences",
            "/colline/organisation/preferences/editGlobalPreferences.do"), FEED_ORGANISATIONS(
            "Organisations>Organisation Admin>Feed Organisations",
            "/colline/organisation/showFeedOrgUpload.do"), ORGANISATIONS_STATIC_DATA(
            "Organisations>Static Data",
            "/colline/refdata/showScheme.do?source=organisation"), FX_RATES(
            "Market Data>FX Rates",
            "/colline/marketdata/entry/editEntry.do"), SECURITIES_DATA(
            "Assets>Securities Data",
            "/colline/collateral/securityTemplateSearchCriteria.do"), INVENTORY_MANAGER(
            "Assets>Inventory Manager",
            "/colline/collateral/im/showFlexibleInventoryManager.do?fromMenu=true"), AGREEMENT_SEARCH(
            "Collateral>Agreement Details>Agreement Search",
            "/colline/collateral/agreementSearchCriteria.do"), AGREEMENT_SETUP(
            "Collateral>Agreement Details>Agreement Setup",
            "/colline/collateral/editAgreementPrncCpty.do"), AGREEMENT_USAGE_REPORT(
            "Collateral>Agreement Details>Agreement Usage Report",
            "/colline/collateral/agreementCount.do"), STATEMENT_ARCHIVE_SEARCH(
            "Collateral>Agreement Details>Statement Archive Search",
            "/colline/collateral/archive/searchCriteria.do"), TRADE_SEARCH(
            "Collateral>Agreement Details>Trade Search",
            "/colline/collateral/tradeSearch.do"), STARTUP_DASHBOARD(
            "Collateral>Workflow>Start-Up Dashboard",
            "/colline/collateral/diaryManager/showStartUpDashboard.do"), DASHBOARD(
            "Collateral>Workflow>Dashboard",
            "/colline/collateral/diaryManager/showDashboard.do"), APPROVALS_MANAGER(
            "Collateral>Workflow>Approvals Manager",
            "/colline/collateral/diaryManager/showApprovalsManager.do"), SETTLEMENT_STATUS(
            "Collateral>Workflow>Settlement Status",
            "/colline/collateral/statusSearchCriteria.do?init=1"), INTERNAL_REVIEWS(
            "Collateral>Workflow>Internal Reviews",
            "/colline/collateral/diaryManager/showInternalReviewsSearch.do"), INTEREST_MANAGER(
            "Collateral>Workflow>Interest Manager",
            "/colline/collateral/diaryManager/showInterestManager.do?init=1"), INTEREST_MANAGER_HISTORY(
            "Collateral>Workflow>Interest Manager History",
            "/colline/collateral/diaryManager/showInterestManagerHistory.do?init=1"), EXTERNAL_EXPOSURES(
            "Collateral>Workflow>External Exposures",
            "/colline/collateral/diaryManager/showDiaryManagerSearch.do"), EXPOSURE_MANAGEMENT(
            "Collateral>Workflow>Exposure Management",
            "/colline/collateral/flexibleEE/viewPage.do"), FEED_AGREEMENTS(
            "Collateral>Task Manager>Feed Agreements",
            "/colline/collateral/feeds/showFeedAgreementUpload.do"), FEED_AGREEMENT_RATINGS(
            "Collateral>Task Manager>Feed Agreement Ratings",
            "/colline/collateral/feeds/showFeedAgreementRatingUpload.do"), FEED_AGREEMENT_UDF(
            "Collateral>Task Manager>Feed Agreement UDF",
            "/colline/collateral/feeds/showFeedAgreementUDFUpload.do"), FEED_ASSET_BOOKINGS(
            "Collateral>Task Manager>Feed Asset Bookings",
            "/colline/collateral/feeds/showFeedSecurityParAmountsUpload.do"), FEED_ASSET_PRICING(
            "Collateral>Task Manager>Feed Asset Pricing",
            "/colline/collateral/feeds/showFeedAssetPricingUpload.do"), FEED_ASSET_RATINGS(
            "Collateral>Task Manager>Feed Asset Ratings",
            "/colline/collateral/feeds/showFeedAssetRatingsUpload.do"), FEED_NAV(
            "Collateral>Task Manager>Feed NAV",
            "/colline/collateral/feeds/showFeedNAVUpload.do"), FEED_COUNTERPARTY_AMOUNT(
            "Collateral>Task Manager>Feed Counterparty Amount",
            "/colline/collateral/feeds/showFeedCounterpartyAmountUpload.do"), FEED_REPO_ETF_SBL_TRADES(
            "Collateral>Task Manager>Feed Repo/ETF/SBL Trades",
            "/colline/collateral/feeds/showFeedTradeRepoUpload.do"), FEED_ELIGIBILITY_RULES_TEMPLATE(
            "Collateral>Task Manager>Feed Eligibility Rules Template",
            "/colline/collateral/feeds/showFeedEligiRulesTemplateUpload.do"), FEED_TRADES(
            "Collateral>Task Manager>Feed Trades",
            "/colline/collateral/feeds/showFeedTradeUpload.do"), FEED_PORTFOLIOCASHFLOW(
            "Collateral>Task Manager>Feed Portfolio Cashflow",
            "/colline/collateral/feeds/showFeedPortfolioTSAUpload.do"), FEED_REPO_MTM(
            "Collateral>Task Manager>Feed Repo/ETF/SBL MtM",
            "/colline/collateral/feeds/showFeedRepomtmUpload.do"), FEED_REPO_SETTLEMENT(
            "Collateral>Task Manager>Feed Repo Settlement",
            "/colline/collateral/feeds/showFeedRepoSettlementUpload.do"), FEED_SETTLEMENT_INSTRUCTION(
            "Collateral>Task Manager>Feed Settlement Instruction",
            "/colline/collateral/feeds/showFeedSettlementInstructionUpload.do"), FEED_STATUS(
            "Collateral>Task Manager>Feed Status",
            "/colline/collateral/feeds/showFeedStatus.do"), FEED_MTM(
            "Collateral>Task Manager>Feed MtM",
            "/colline/collateral/feeds/showFeedmtmUpload.do"), FEED_SECURITY_TEMPLATES(
            "Collateral>Task Manager>Feed Security Templates",
            "/colline/collateral/feeds/showFeedSecurityTempUpload.do"), FEED_EXTERNAL_IA(
            "Collateral>Task Manager>Feed External IA",
            "/colline/collateral/feeds/showFeedExtIAload.do"), FEED_FX_RATES(
            "Collateral>Task Manager>Feed Fx Rates",
            "/colline/collateral/feeds/showFeedFxRateUpload.do"), FEED_INVENTORY_MANAGER(
            "Collateral>Task Manager>Feed Inventory Manager",
            "/colline/collateral/feeds/showFeedFirmPositionUpload.do"), FEED_INTEREST_AMOUNT(
            "Collateral>Task Manager>Feed Interest Amount",
            "/colline/collateral/feeds/showFeedInterestAmountUpload.do"), FEED_INTEREST_RATES(
            "Collateral>Task Manager>Feed Interest Rates",
            "/colline/collateral/feeds/showFeedInterestUpload.do"), COLLATERAL_STATIC_DATA(
            "Collateral>Static Data>Static Data",
            "/colline/refdata/showScheme.do?source=collateral"), UMBRELLA_RULES(
            "Collateral>Static Data>Umbrella Rules",
            "/colline/collateral/showUmbrellarules.do"), CASHFLOW_RULES(
            "Collateral>Static Data>Cashflow Rules",
            "/colline/collateral/showTSARules.do"), UDF_VALUES(
            "Collateral>Static Data>UDF Values",
            "/colline/refdata/showScheme.do?source=udfvalues"), ELIGIBILITY_RULES_TEMPLATE(
            "Collateral>Static Data>Eligibility Rules Template",
            "/colline/collateral/eligibilityrules/showEligibilityRulesTemplateResults.do"), SETTLEMENT_DATA(
            "Collateral>Static Data>Settlement Data",
            "/colline/collateral/settlementDataCriteria.do"), INTEREST_RATES(
            "Collateral>Static Data>Interest Rates",
            "/colline/collateral/showRefRates.do"), ASSET_DEFINITION(
            "Collateral>Static Data>Asset Definition",
            "/colline/showHierachy.do?bean=asset"), STP_SWITCH(
            "Collateral>STP>STP Switch",
            "/colline/collateral/showStpSwitch.do"), STP_FILTER_DEFINITION(
            "Collateral>STP>Filter Definition",
            "/colline/collateral/showRuleFilter.do"), STP_BUSINESS_RULES(
            "Collateral>STP>Business Rules",
            "/colline/collateral/showRuleRuleFilterInit.do"), STP_RULE_DEFINITION(
            "Collateral>STP>Rule Definition",
            "/colline/collateral/showRuleRule.do"), STP_STATUS_SEARCH(
            "Collateral>STP>STP Status Search",
            "/colline/collateral/stpStatusSearchCriteria.do"), STP_STATUS_DASHBOARD(
            "Collateral>STP>STP Status Dashboard",
            "/colline/collateral/stpStatusShow.do"), COLLATERAL_CONFIGURATION_PREFERENCES(
            "Collateral>Configuration>Preferences",
            "/colline/collateral/preferences/editPreferences.do"), LETTER_CONFIGURATION(
            "Collateral>Configuration>Letter Configuration",
            "/colline/collateral/letters/showLetterConfiguration.do"), SELFSERVICE_COUNTERPARTY_DATA_EXPOSURES(
            "Self Service>Counterparty Data>Exposures",
            "/colline/collateral/selfService/showCptyExposures.do"), SELFSERVICE_CLIENTDATA_INTERESTMANAGER(
            "Self Service>Client Data>Interest Manager",
            "/colline/collateral/selfService/showInterestManager.do"), SELFSERVICE_CLIENTDATA_EXPOSURES(
            "Self Service>Client Data>Exposures",
            "/colline/collateral/selfService/showClientExposures.do"), RECONCILIATION_COLLATERALTRADE_TEMPLATES(
            "Reconciliation>Collateral Trade>Templates",
            "/colline/reconciliation/template/showTemplate.do"), RECONCILIATION_SETUP(
            "Reconciliation>Collateral Trade>Reconciliation Setup",
            "/colline/reconciliation/feeds/setup.do"), RECONCILIATIO_COLLATERALTRADE_ARCHIVE(
            "Reconciliation>Collateral Trade>Archive",
            "/colline/reconciliation/rec/showRecArchive.do"), REPORT_LIST(
            "Reports>Report List",
            "/colline/redir.do?uri=reports.reports"), ADD_TO_MY_FAVOURITES(
            "AddToMyFavouritesPage>Add To My Favourites",
            "/colline/admin/addUserFavourite.do"), EDIT_MY_FAVOURITES(
            "AddToMyFavouritesPage>Edit My Favourites",
            "/colline/admin/showUserFavourite.do"), CHANGE_MY_PASSWORD(
            "AddToMyFavouritesPage>Change My Password",
            "/colline/admin/useradmin/showChangeMyPassword.do"), EDIT_MY_PREFERENCES(
            "AddToMyFavouritesPage>Edit My Preferences",
            "/colline/admin/useradmin/showUserPreferences.do"), TASK_SCHEDULER(
            "System Admin>Process Management>Task Scheduler",
            "/colline/admin/showTaskScheduler.do"), EVENT_LOG(
            "System Admin>Process Management>Event Log",
            "/colline/admin/searchEventLog.do"),SERVER_LOG(
            "System Admin>Process Management>Server Log",
            "/colline/showLog.do"),ACTIVE_MESSAGING(
            "System Admin>Process Management>Active Messaging",
            "/colline/admin/showActiveMessaging.do"), MESSAGES(
            "System Admin>Process Management>Messages",
            "/colline/admin/showMessages.do"),USER_AND_ROLE_APPROVAL(
            "System Admin>User Data>User & Role Approval",
            "/colline/admin/useradmin/showUserApproval.do"), USER_ROLE_ADMINISTRATION(
            "System Admin>User Data>User & Role Administration",
            "/colline/admin/useradmin/showAllUserRoles.do"),PRIVILEGES(
            "System Admin>User Data>Privileges",
            "/colline/admin/showPrivileges.do"), PASSWORD_CONSTRAINTS(
            "System Admin>User Data>Password Constraints",
            "/colline/admin/useradmin/passwordConstraints.do"),SYSTEM_PREFERENCES(
            "System Admin>System Settings>System Preferences",
            "/colline/admin/editSystemPreferences.do"),HELP_ATTACHMENTS(
            "System Admin>System Settings>Help Attachments",
            "/colline/admin/showHelpAttachments.do"), AUDIT_TRAIL_STATUS(
            "System Admin>System Settings>Audit Trail Status",
            "/colline/redir.do?uri=admin.audittrail"), HOLIDAY_CENTRE(
            "System Admin>System Data>Holiday Centre",
            "/colline/holiday/showCalendarForCity.do"), REGION(
            "System Admin>System Data>Region",
            "/colline/redir.do?uri=refdata.region.editRegion&amp;new=1"), SCHEMES(
            "System Admin>System Data>Schemes",
            "/colline/refdata/showScheme.do"), BUSINESS_FREQUENCY(
            "System Admin>System Data>Business Frequency",
            "/colline/admin/showBusinessFrequences.do"), FEED_HOLIDAY_CENTRE(
            "System Admin>System Data>Feed Holiday Centre",
            "/colline/holiday/showFeedHolidayUpload.do"), FEED_STATIC_DATA(
            "System Admin>System Data>Feed Static Data",
            "/colline/collateral/showFeedStaticDataUpload.do"),FEED_ETD_BALANCES(
            "Collateral>Task Manager>Feed ETD Balances",
            "/colline/collateral/feeds/showFeedBalanceUpload.do"), FEED_ETD_ADJUSTMENT(
            "Collateral>Task Manager>Feed ETD Adjustment",
            "/colline/collateral/feeds/showFeedETDAdjustmentUpload.do"), LOGOUT(
            "Logout",
            "/colline/index.do?exit=true"), CLEARING_DASHBOARD(
            "Collateral>Clearing>Dashboard",
            "/colline/collateral/ccp/showFlexibleCCPDashboard.do"), SECURITY_MOVEMENTS(
            "Collateral>Workflow>Security Movements",
            "/colline/collateral/securityMovementSearchCriteria.do"), ABOUT_SOFTWARE(
            "Help>About This Software",
            "/colline/admin/showModuleVersions.do"),RULE_BUILDER(
            "Optimisation>Rule Builder",
                    "/colline/optimisation/showOptimisation.do"),
            CALCULATION_RULE_MAINTENANCE("Collateral>Static Data>Calculation Rule",
            "/colline/collateral/showCalculationRule.do");

    private final String name;
    private final String value;

    Menu(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
