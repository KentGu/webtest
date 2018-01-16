package com.lombardrisk.pages;

import com.lombardrisk.pages.assets.SecurityEditorPage;
import com.lombardrisk.pages.assets.SecuritySearchPage;
import com.lombardrisk.pages.collateral.*;
import com.lombardrisk.pages.collateral.staticdata.CalculationRuleMaintenancePage;
import com.lombardrisk.pages.home.HomePage;
import com.lombardrisk.pages.marketdata.AddNewRateSetPage;
import com.lombardrisk.pages.marketdata.FXRatesPage;
import com.lombardrisk.pages.optimisation.OptimisationPage;
import com.lombardrisk.pages.organisations.*;
import com.lombardrisk.pages.report.*;
import com.lombardrisk.pages.systemadmin.*;
import com.lombardrisk.pages.tools.AddToMyFavouritesPage;
import com.lombardrisk.pages.tools.ChangeMyPasswordPage;
import com.lombardrisk.pages.tools.EditMyFavouritesPage;
import com.lombardrisk.pages.tools.EditMyPreferencesPage;
import com.lombardrisk.pages.welcome.WelcomePage;
import com.lombardrisk.pojo.ExternalExposureList;
import org.yiwan.webcore.database.DatabaseWrapper;
import org.yiwan.webcore.web.IPageManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageFactory;

//import com.lombardrisk.pojo.BookingInformationDetail;

/**
 * Created by Kenny Wang on 2/4/2016.
 */
public class PageManager implements IPageManager {
    public AbstractCollinePage abstractCollinePage;

    public HomePage homePage;
    public WelcomePage welcomePage;

    //Under Market Data Menu
    public FXRatesPage fxRatesPage;
    public AddNewRateSetPage addNewRateSetPage;

    //Under Assets Menu
    public SecurityEditorPage securityEditorPage;
    public SecuritySearchPage securitySearchPage;

    //Under Organisations Menu
    public AddOrganisationPage addOrganisationPage;
    public ViewOrganisationsPage viewOrganisationsPage;
    public ViewOurOrganisationsPage viewOurOrganisationsPage;
    public com.lombardrisk.pages.organisations.StaticDataPage organisationStaticDataPage;
    public DeleteOrganisationPage deleteOrganisationPage;
    public RatingLevelPage ratingLevelPage;
    public BooksPage booksPage;
    public IndustryPage industryPage;
    public FeedOrganisationsPage feedOrganisationsPage;

    //Under Collateral Menu
    public AgreementSearchPage agreementSearchPage;
    public AgreementPartyCounterpartySetupPage agreementPartyCounterpartySetupPage;
    public AgreementDocumentationSetupPage agreementDocumentationSetupPage;
    public AgreementCallScheduleSetupPage agreementCallScheduleSetupPage;
    public AgreementProductsSetupPage agreementProductsSetupPage;
    public AgreementModelSetupPage agreementModelSetupPage;
    public AgreementFamilySetupPage agreementFamilySetupPage;
    public AgreementCollateralSetupPage agreementCollateralSetupPage;
    public AgreementFixedTriggerSetupPage agreementFixedTriggerSetupPage;
    public AgreementRuleTriggerSetupPage agreementRuleTriggerSetupPage;
    public AgreementAdditionalFieldsSetupPage agreementAdditionalFieldsSetupPage;
    public AgreementUmbrellaNettingSetupPage agreementUmbrellaNettingSetupPage;
    public AgreementSettlementSetupPage agreementSettlementSetupPage;
    public AgreementReportingControlSetupPage agreementReportingControlSetupPage;
    public AgreementSummaryPage agreementSummaryPage;
    public AgreementStatementPage agreementStatementPage;
    public AgreementExposuresSummaryPage agreementExposuresSummaryPage;
    public AgreementAssetHoldingsSummaryPage agreementAssetHoldingsSummaryPage;
    public AgreementAssetsSummaryPage agreementAssetsSummaryPage;
    public AgreementAssetsEditorPage agreementAssetsEditorPage;
    public ConcentrationLimitBreachedPage concentrationLimitBreachedPage;
    public CashMovementSummaryPage cashMovementSummaryPage;
    public CashMovementEditorPage cashMovementEditorPage;
    public com.lombardrisk.pages.collateral.StaticDataPage collateralStaticDataPage;
    public UdfValuesPage udfValuesPage;
    public SettlementStatusSearchPage settlementStatusSearchPage;
    public SettlementStatusSummaryPage settlementStatusSummaryPage;
    public SettlementStatusPage settlementStatusPage;
    public EligibilityRulesTemplatePage eligibilityRulesTemplatePage;
    public TradeSearchPage tradeSearchPage;
    public StatementArchiveSearchPage statementArchiveSearchPage;

    public TaskManagerPage taskManagerPage;

    public TradeSummaryPage tradeSummaryPage;
    public TradeEditorPage tradeEditorPage;

    public ApprovalsManagerPage approvalsManagerPage;

    public SecurityMovementSearchPage securityMovementSearchPage;
    public SecurityMovementPage securityMovementPage;
    public SecurityMovementSummaryPage securityMovementSummaryPage;

    public ExposureManagementPage exposureManagementPage;

    public InternalReviewSearchPage internalReviewSearchPage;
    public InternalReviewSummaryPage internalReviewSummaryPage;

    public AssetDefinitionPage assetDefinitionPage;
    public AddEditAssetPage addEditAssetPage;
    public InterestRatesPage interestRatesPage;
    public TSARulesPage tsaRulesPage;
    public InterestRateMaintenancePage interestRateMaintenancePage;
    public ModifyExistingReferenceRatePage modifyExistingReferenceRatePage;
    public SettlementDataPage settlementDataPage;
    public UmbrellaRulesPage umbrellaRulesPage;

    public GlobalPreferencesPage globalPreferencesPage;
    public CollateralPreferencesPage collateralPreferencesPage;

    public LetterConfigurationPage letterConfigurationPage;
    public LetterTemplateEditorPage letterTemplateEditorPage;
    public UserDefinedLetterPage userDefinedLetterPage;
    public LetterAdminPage letterAdminPage;
    public LetterSummaryPage letterSummaryPage;
    public LetterDetailsEditPage letterDetailsEditPage;
    public LetterInstrumentSearchPage letterInstrumentSearchPage;
    public LetterSetupPage letterSetupPage;
    public RequestTypeAndAssetSelectionPage requestTypeAndAssetSelectionPage;
    public FinalLetterReviewAndSubmissionMethodPage finalLetterReviewAndSubmissionMethodPage;
    public PortfolioDetailsPage portfolioDetailsPage;

    public InterestManagerSearchPage interestManagerSearchPage;
    public InterestManagerSearchResultPage interestManagerSearchResultPage;
    public InterestViewDetailsPage interestViewDetailsPage;
    public InterestManagerArchiveSearchPage interestManagerArchiveSearchPage;

    public CalculationRuleMaintenancePage calculationRuleMaintenancePage;

    //STP
    public StpConfigurationsPage stpConfigurationsPage;

    //report
    public ReportListPage reportListPage;
    public ReportGuiPage reportGuiPage;
    public SaveReportsPage saveReportsPage;
    public AssetHoldingsAndValuationReportPage assetHoldingsAndValuationReportPage;
    public AssetManagementReportPage assetManagementReportPage;
    public AssetSettlementsReportPage assetSettlementsReportPage;
    public CollateralAvailabilityReportPage collateralAvailabilityReportPage;
    public ConcentrationLimitsReportPage concentrationLimitsReportPage;
    public CorporateActionsReportPage corporateActionsReportPage;
    public IneligibleAssetReportPage ineligibleAssetReportPage;
    public InventoryManagerReportPage inventoryManagerReportPage;
    public RehypothecationEligibilityReportPage rehypothecationEligibilityReportPage;
    public SecurityInstrumentsReportPage securityInstrumentsReportPage;
    public AuditAgreementsReportPage auditAgreementsReportPage;
    public AuditAssetDefinitionReportPage auditAssetDefinitionReportPage;
    public AuditAssetsReportPage auditAssetsReportPage;
    public AuditEligibilityRuleTemplateReportPage auditEligibilityRuleTemplateReportPage;
    public AuditHolidayCentresReportPage auditHolidayCentresReportPage;
    public AuditInterestRatesReportPage auditInterestRatesReportPage;
    public AuditOptimisationRuleReportPage auditOptimisationRuleReportPage;
    public AuditOrganisationsReportPage auditOrganisationsReportPage;
    public AuditRolesAdministrationReportPage auditRolesAdministrationReportPage;
    public AuditSettlementDetailsReportPage auditSettlementDetailsReportPage;
    public AuditStatementsReportPage auditStatementsReportPage;
    public AuditTradesReportPage auditTradesReportPage;
    public AuditUserAdministrationReportPage auditUserAdministrationReportPage;
    public FrequencyDistributionLogReportPage frequencyDistributionLogReportPage;
    public AgreementsReportPage agreementsReportPage;
    public EligibilityRulesTemplateReportPage eligibilityRulesTemplateReportPage;
    public HistoricalFxRatesReportPage historicalFxRatesReportPage;
    public HistoricalInterestRateReportPage historicalInterestRateReportPage;
    public LicensingReportPage licensingReportPage;
    public OrganisationAgreementsReportPage organisationAgreementsReportPage;
    public OrganisationThresholdReportPage organisationThresholdReportPage;
    public SettlementInstructionsReportPage settlementInstructionsReportPage;
    public BadStagingFeedReportPage badStagingFeedReportPage;
    public CashAndAccrualReportPage cashAndAccrualReportPage;
    public InterestAppliedReportPage interestAppliedReportPage;
    public InterestCalculationsScheduledReportPage interestCalculationsScheduledReportPage;
    public InterestChangesReportPage interestChangesReportPage;
    public MonthEndReportPage monthEndReportPage;
    public RejectedTradesReportPage rejectedTradesReportPage;
    public RepoEtfsblRejectedTradesReportPage repoEtfsblRejectedTradesReportPage;
    public RepoEtfsblTradesOutputReportPage repoEtfsblTradesOutputReportPage;
    public TradesOutputReportPage tradesOutputReportPage;
    public DailyExposureReportPage dailyExposureReportPage;
    public DisputeReportPage disputeReportPage;
    public DisputeHistoryReportPage disputeHistoryReportPage;
    public HistoricalExposureReportPage historicalExposureReportPage;
    public InternalReviewReportPage internalReviewReportPage;
    public MisReportPage misReportPage;
    public MtmComparisonReportPage mtmComparisonReportPage;
    public RepoEtfsblDailyExposureReportPage repoEtfsblDailyExposureReportPage;
    public RepoEtfsblHistoricalExposureReportPage repoEtfsblHistoricalExposureReportPage;
    public TsaReportPage tsaReportPage;
    public WhatIfScenarioReportPage whatIfScenarioReportPage;
    public OptimisationResultReportPage optimisationResultReportPage;
    public OptimisationRuleReportPage optimisationRuleReportPage;
    public ManuallyOverriddenAssetPriceReportPage manuallyOverriddenAssetPriceReportPage;
    public PriceAgeExceptionsReportPage priceAgeExceptionsReportPage;
    public PriceExceptionsReportPage priceExceptionsReportPage;
    public PriceVarianceExceptionsReportPage priceVarianceExceptionsReportPage;
    public ReconciliationOutputReportPage reconciliationOutputReportPage;
    public ReconciliationTradesOutputReportPage reconciliationTradesOutputReportPage;
    public StpDashboardReportPage stpDashboardReportPage;
    public RoleApprovalReportPage roleApprovalReportPage;
    public UserProfileReportPage userProfileReportPage;
    public ReportPage reportPage;
    public OptimisationPage optimisationPage;

    public ExposureStatementPage exposureStatementPage;




    public StartupDashboardPage startupDashboardPage;
    public DashboardPage dashboardPage;

    //Under System Admin Menu
    //In process management sub menu
    public TaskSchedulerPage taskSchedulerPage;
    public EventLogSearchPage eventLogSearchPage;
    public ActiveMessagingPage activeMessagingPage;
    public MessagePage messagePage;
    //In user data sub menu
    public PrivilegesPage privilegesPage;
    public UserAndRoleAdministrationNavigationPannel userAndRoleAdministrationNavigationPannel;
    public AdministerRolesPage administerRolesPage;
    public AdministerApprovalProfilesPage administerApprovalProfilesPage;
    public PasswordConstraintsPage passwordConstraintsPage;
    public CreateNewUserPage createNewUserPage;
    public EditApprovalProfilePage editApprovalProfilePage;
    public EditProfileForUserPage editProfileForUserPage;
    public EditUserRolesPage editUserRolesPage;
    public EditUserPreferencePage editUserPreferencePage;
    //In system settings sub menu
    public SystemPreferencesPage systemPreferencesPage;
    public AuditTrailStatusPage auditTrailStatusPage;
    public HelpAttachmentsPage helpAttachmentsPage;
    //In system data sub menu
    public SchemesPage schemesPage;
    public HolidayCentrePage holidayCentrePage;
    public RegionPage regionPage;
    public BusinessFrequencyPage businessFrequencyPage;

    public FeedHolidayCentrePage feedHolidayCentrePage;
    public FeedStaticDataPage feedStaticDataPage;

    //Under Tools Menu
    public ChangeMyPasswordPage changeMyPasswordPage;
    public EditMyFavouritesPage editMyFavouritesPage;
    public AddToMyFavouritesPage addToMyFavouritesPage;
    public EditMyPreferencesPage editMyPreferencesPage;
    //report

    //self service
    public ExternalExposureListPage externalExposureListPage;

    public DatabaseOperationsPage databaseOperationsPage;


    public PageManager(IWebDriverWrapper webDriverWrapper, DatabaseWrapper databaseWrapper) throws Exception {
        PageFactory pageFactory = new PageFactory(webDriverWrapper, databaseWrapper);

        abstractCollinePage = pageFactory.createByWebDriverWrapper(AbstractCollinePage.class);

        homePage = pageFactory.createByWebDriverWrapper(HomePage.class);
        welcomePage = pageFactory.createByWebDriverWrapper(WelcomePage.class);

        fxRatesPage = pageFactory.createByWebDriverWrapper(FXRatesPage.class);
        addNewRateSetPage = pageFactory.createByWebDriverWrapper(AddNewRateSetPage.class);

        securityEditorPage = pageFactory.createByWebDriverWrapper(SecurityEditorPage.class);
        securitySearchPage = pageFactory.createByWebDriverWrapper(SecuritySearchPage.class);

        addOrganisationPage = pageFactory.createByWebDriverWrapper(AddOrganisationPage.class);
        viewOrganisationsPage = pageFactory.createByWebDriverWrapper(ViewOrganisationsPage.class);
        viewOurOrganisationsPage = pageFactory.createByWebDriverWrapper(ViewOurOrganisationsPage.class);
        organisationStaticDataPage = pageFactory.createByWebDriverWrapper(com.lombardrisk.pages.organisations.StaticDataPage.class);
        deleteOrganisationPage = pageFactory.createByWebDriverWrapper(DeleteOrganisationPage.class);
        ratingLevelPage = pageFactory.createByWebDriverWrapper(RatingLevelPage.class);
        booksPage = pageFactory.createByWebDriverWrapper(BooksPage.class);
        industryPage = pageFactory.createByWebDriverWrapper(IndustryPage.class);
        feedOrganisationsPage = pageFactory.createByWebDriverWrapper(FeedOrganisationsPage.class);

        agreementSearchPage = pageFactory.createByWebDriverWrapper(AgreementSearchPage.class);
        agreementPartyCounterpartySetupPage = pageFactory.createByWebDriverWrapper(AgreementPartyCounterpartySetupPage.class);
        agreementDocumentationSetupPage = pageFactory.createByWebDriverWrapper(AgreementDocumentationSetupPage.class);
        agreementCallScheduleSetupPage = pageFactory.createByWebDriverWrapper(AgreementCallScheduleSetupPage.class);
        agreementFamilySetupPage = pageFactory.createByWebDriverWrapper(AgreementFamilySetupPage.class);
        agreementProductsSetupPage = pageFactory.createByWebDriverWrapper(AgreementProductsSetupPage.class);
        agreementModelSetupPage = pageFactory.createByWebDriverWrapper(AgreementModelSetupPage.class);
        agreementCollateralSetupPage = pageFactory.createByWebDriverWrapper(AgreementCollateralSetupPage.class);
        agreementFixedTriggerSetupPage = pageFactory.createByWebDriverWrapper(AgreementFixedTriggerSetupPage.class);
        agreementRuleTriggerSetupPage = pageFactory.createByWebDriverWrapper(AgreementRuleTriggerSetupPage.class);
        agreementAdditionalFieldsSetupPage = pageFactory.createByWebDriverWrapper(AgreementAdditionalFieldsSetupPage.class);
        agreementUmbrellaNettingSetupPage = pageFactory.createByWebDriverWrapper(AgreementUmbrellaNettingSetupPage.class);
        agreementSettlementSetupPage = pageFactory.createByWebDriverWrapper(AgreementSettlementSetupPage.class);
        agreementReportingControlSetupPage = pageFactory.createByWebDriverWrapper(AgreementReportingControlSetupPage.class);
        agreementSummaryPage = pageFactory.createByWebDriverWrapper(AgreementSummaryPage.class);
        agreementStatementPage = pageFactory.createByWebDriverWrapper(AgreementStatementPage.class);
        agreementExposuresSummaryPage = pageFactory.createByWebDriverWrapper(AgreementExposuresSummaryPage.class);
        agreementAssetHoldingsSummaryPage = pageFactory.createByWebDriverWrapper(AgreementAssetHoldingsSummaryPage.class);
        agreementAssetsSummaryPage = pageFactory.createByWebDriverWrapper(AgreementAssetsSummaryPage.class);
        agreementAssetsEditorPage = pageFactory.createByWebDriverWrapper(AgreementAssetsEditorPage.class);
        concentrationLimitBreachedPage = pageFactory.createByWebDriverWrapper(ConcentrationLimitBreachedPage.class);
        cashMovementSummaryPage =  pageFactory.createByWebDriverWrapper(CashMovementSummaryPage.class);
        cashMovementEditorPage = pageFactory.createByWebDriverWrapper(CashMovementEditorPage.class);
        collateralStaticDataPage = pageFactory.createByWebDriverWrapper(com.lombardrisk.pages.collateral.StaticDataPage.class);
        udfValuesPage = pageFactory.createByWebDriverWrapper(UdfValuesPage.class);
        settlementStatusSearchPage = pageFactory.createByWebDriverWrapper(SettlementStatusSearchPage.class);
        settlementStatusSummaryPage = pageFactory.createByWebDriverWrapper(SettlementStatusSummaryPage.class);
        settlementStatusPage = pageFactory.createByWebDriverWrapper(SettlementStatusPage.class);
        eligibilityRulesTemplatePage = pageFactory.createByWebDriverWrapper(EligibilityRulesTemplatePage.class);
        tradeSearchPage = pageFactory.createByWebDriverWrapper(TradeSearchPage.class);
        statementArchiveSearchPage = pageFactory.createByWebDriverWrapper(StatementArchiveSearchPage.class);

        taskManagerPage = pageFactory.createByWebDriverWrapper(TaskManagerPage.class);

        tradeSummaryPage = pageFactory.createByWebDriverWrapper(TradeSummaryPage.class);
        tradeEditorPage = pageFactory.createByWebDriverWrapper(TradeEditorPage.class);

        approvalsManagerPage = pageFactory.createByWebDriverWrapper(ApprovalsManagerPage.class);

        securityMovementSearchPage = pageFactory.createByWebDriverWrapper(SecurityMovementSearchPage.class);
        securityMovementPage = pageFactory.createByWebDriverWrapper(SecurityMovementPage.class);
        securityMovementSummaryPage = pageFactory.createByWebDriverWrapper(SecurityMovementSummaryPage.class);

        exposureManagementPage = pageFactory.createByWebDriverWrapper(ExposureManagementPage.class);

        internalReviewSearchPage = pageFactory.createByWebDriverWrapper(InternalReviewSearchPage.class);
        internalReviewSummaryPage = pageFactory.createByWebDriverWrapper(InternalReviewSummaryPage.class);

        assetDefinitionPage = pageFactory.createByWebDriverWrapper(AssetDefinitionPage.class);
        addEditAssetPage = pageFactory.createByWebDriverWrapper(AddEditAssetPage.class);
        interestRatesPage = pageFactory.createByWebDriverWrapper(InterestRatesPage.class);
        tsaRulesPage = pageFactory.createByWebDriverWrapper(TSARulesPage.class);
        interestRateMaintenancePage = pageFactory.createByWebDriverWrapper(InterestRateMaintenancePage.class);
        modifyExistingReferenceRatePage = pageFactory.createByWebDriverWrapper(ModifyExistingReferenceRatePage.class);
        settlementDataPage = pageFactory.createByWebDriverWrapper(SettlementDataPage.class);
        umbrellaRulesPage = pageFactory.createByWebDriverWrapper(UmbrellaRulesPage.class);

        globalPreferencesPage = pageFactory.createByWebDriverWrapper(GlobalPreferencesPage.class);
        collateralPreferencesPage = pageFactory.createByWebDriverWrapper(CollateralPreferencesPage.class);

        letterConfigurationPage = pageFactory.createByWebDriverWrapper(LetterConfigurationPage.class);
        letterTemplateEditorPage = pageFactory.createByWebDriverWrapper(LetterTemplateEditorPage.class);
        userDefinedLetterPage = pageFactory.createByWebDriverWrapper(UserDefinedLetterPage.class);
        letterAdminPage = pageFactory.createByWebDriverWrapper(LetterAdminPage.class);
        letterSummaryPage = pageFactory.createByWebDriverWrapper(LetterSummaryPage.class);
        letterDetailsEditPage = pageFactory.createByWebDriverWrapper(LetterDetailsEditPage.class);
        letterInstrumentSearchPage = pageFactory.createByWebDriverWrapper(LetterInstrumentSearchPage.class);
        letterSetupPage = pageFactory.createByWebDriverWrapper(LetterSetupPage.class);
        requestTypeAndAssetSelectionPage = pageFactory.createByWebDriverWrapper(RequestTypeAndAssetSelectionPage.class);
        finalLetterReviewAndSubmissionMethodPage = pageFactory.createByWebDriverWrapper(FinalLetterReviewAndSubmissionMethodPage.class);
        portfolioDetailsPage = pageFactory.createByWebDriverWrapper(PortfolioDetailsPage.class);

        interestManagerSearchPage = pageFactory.createByWebDriverWrapper(InterestManagerSearchPage.class);
        interestManagerSearchResultPage = pageFactory.createByWebDriverWrapper(InterestManagerSearchResultPage.class);
        interestViewDetailsPage = pageFactory.createByWebDriverWrapper(InterestViewDetailsPage.class);
        interestManagerArchiveSearchPage = pageFactory.createByWebDriverWrapper(InterestManagerArchiveSearchPage.class);

        stpConfigurationsPage = pageFactory.createByWebDriverWrapper(StpConfigurationsPage.class);

        reportListPage = pageFactory.createByWebDriverWrapper(ReportListPage.class);
        reportGuiPage = pageFactory.createByWebDriverWrapper(ReportGuiPage.class);
        saveReportsPage = pageFactory.createByWebDriverWrapper(SaveReportsPage.class);
        assetHoldingsAndValuationReportPage = pageFactory.createByWebDriverWrapper(AssetHoldingsAndValuationReportPage.class);
        priceExceptionsReportPage = pageFactory.createByWebDriverWrapper(PriceExceptionsReportPage.class);
        priceVarianceExceptionsReportPage = pageFactory.createByWebDriverWrapper(PriceVarianceExceptionsReportPage.class);
        manuallyOverriddenAssetPriceReportPage = pageFactory.createByWebDriverWrapper(ManuallyOverriddenAssetPriceReportPage.class);
        priceAgeExceptionsReportPage = pageFactory.createByWebDriverWrapper(PriceAgeExceptionsReportPage.class);
        tradesOutputReportPage = pageFactory.createByWebDriverWrapper(TradesOutputReportPage.class);
        dailyExposureReportPage = pageFactory.createByWebDriverWrapper(DailyExposureReportPage.class);
        tsaReportPage = pageFactory.createByWebDriverWrapper(TsaReportPage.class);

        startupDashboardPage = pageFactory.createByWebDriverWrapper(StartupDashboardPage.class);
        dashboardPage = pageFactory.createByWebDriverWrapper(DashboardPage.class);

        taskSchedulerPage = pageFactory.createByWebDriverWrapper(TaskSchedulerPage.class);
        eventLogSearchPage = pageFactory.createByWebDriverWrapper(EventLogSearchPage.class);
        activeMessagingPage = pageFactory.createByWebDriverWrapper(ActiveMessagingPage.class);
        messagePage = pageFactory.createByWebDriverWrapper(MessagePage.class);
        privilegesPage = pageFactory.createByWebDriverWrapper(PrivilegesPage.class);
        userAndRoleAdministrationNavigationPannel = pageFactory.createByWebDriverWrapper(EditUserRolesPage.class);
        administerRolesPage = pageFactory.createByWebDriverWrapper(AdministerRolesPage.class);
        administerApprovalProfilesPage = pageFactory.createByWebDriverWrapper(AdministerApprovalProfilesPage.class);
        createNewUserPage =  pageFactory.createByWebDriverWrapper(CreateNewUserPage.class);
        editApprovalProfilePage = pageFactory.createByWebDriverWrapper(EditApprovalProfilePage.class);
        editProfileForUserPage = pageFactory.createByWebDriverWrapper(EditProfileForUserPage.class);
        editUserRolesPage = pageFactory.createByWebDriverWrapper(EditUserRolesPage.class);
        editUserPreferencePage = pageFactory.createByWebDriverWrapper(EditUserPreferencePage.class);
        passwordConstraintsPage = pageFactory.createByWebDriverWrapper(PasswordConstraintsPage.class);
        systemPreferencesPage = pageFactory.createByWebDriverWrapper(SystemPreferencesPage.class);
        auditTrailStatusPage = pageFactory.createByWebDriverWrapper(AuditTrailStatusPage.class);
        helpAttachmentsPage = pageFactory.createByWebDriverWrapper(HelpAttachmentsPage.class);
        schemesPage = pageFactory.createByWebDriverWrapper(SchemesPage.class);
        holidayCentrePage = pageFactory.createByWebDriverWrapper(HolidayCentrePage.class);
        regionPage = pageFactory.createByWebDriverWrapper(RegionPage.class);
        businessFrequencyPage = pageFactory.createByWebDriverWrapper(BusinessFrequencyPage.class);

        feedHolidayCentrePage = pageFactory.createByWebDriverWrapper(FeedHolidayCentrePage.class);
        feedStaticDataPage = pageFactory.createByWebDriverWrapper(FeedStaticDataPage.class);

        changeMyPasswordPage = pageFactory.createByWebDriverWrapper(ChangeMyPasswordPage.class);
        editMyFavouritesPage = pageFactory.createByWebDriverWrapper(EditMyFavouritesPage.class);
        addToMyFavouritesPage = pageFactory.createByWebDriverWrapper(AddToMyFavouritesPage.class);
        editMyPreferencesPage = pageFactory.createByWebDriverWrapper(EditMyPreferencesPage.class);
        auditAssetsReportPage = pageFactory.createByWebDriverWrapper(AuditAssetsReportPage.class);
        auditAssetDefinitionReportPage = pageFactory.createByWebDriverWrapper(AuditAssetDefinitionReportPage.class);
        auditHolidayCentresReportPage = pageFactory.createByWebDriverWrapper(AuditHolidayCentresReportPage.class);
        auditInterestRatesReportPage = pageFactory.createByWebDriverWrapper(AuditInterestRatesReportPage.class);
        auditRolesAdministrationReportPage =pageFactory.createByWebDriverWrapper(AuditRolesAdministrationReportPage.class);
        auditUserAdministrationReportPage = pageFactory.createByWebDriverWrapper(AuditUserAdministrationReportPage.class);
        auditOrganisationsReportPage = pageFactory.createByWebDriverWrapper(AuditOrganisationsReportPage.class);
        auditEligibilityRuleTemplateReportPage = pageFactory.createByWebDriverWrapper(AuditEligibilityRuleTemplateReportPage.class);
        auditAgreementsReportPage = pageFactory.createByWebDriverWrapper(AuditAgreementsReportPage.class);
        auditTradesReportPage = pageFactory.createByWebDriverWrapper(AuditTradesReportPage.class);
        auditOptimisationRuleReportPage = pageFactory.createByWebDriverWrapper(AuditOptimisationRuleReportPage.class);
        badStagingFeedReportPage = pageFactory.createByWebDriverWrapper(BadStagingFeedReportPage.class);
        reportPage = pageFactory.createByWebDriverWrapper(ReportPage.class);
        optimisationPage = pageFactory.createByWebDriverWrapper(OptimisationPage.class);
        auditSettlementDetailsReportPage = pageFactory.createByWebDriverWrapper(AuditSettlementDetailsReportPage.class);
        auditStatementsReportPage =pageFactory.createByWebDriverWrapper(AuditStatementsReportPage.class);
        exposureStatementPage = pageFactory.createByWebDriverWrapper(ExposureStatementPage.class);
        reportPage = pageFactory.createByWebDriverWrapper(ReportPage.class);
        optimisationPage = pageFactory.createByWebDriverWrapper(OptimisationPage.class);
        auditSettlementDetailsReportPage = pageFactory.createByWebDriverWrapper(AuditSettlementDetailsReportPage.class);
        auditStatementsReportPage =pageFactory.createByWebDriverWrapper(AuditStatementsReportPage.class);
        exposureStatementPage = pageFactory.createByWebDriverWrapper(ExposureStatementPage.class);
        assetSettlementsReportPage = pageFactory.createByWebDriverWrapper(AssetSettlementsReportPage.class);
        repoEtfsblDailyExposureReportPage = pageFactory.createByWebDriverWrapper(RepoEtfsblDailyExposureReportPage.class);
        repoEtfsblHistoricalExposureReportPage = pageFactory.createByWebDriverWrapper(RepoEtfsblHistoricalExposureReportPage.class);
        ineligibleAssetReportPage = pageFactory.createByWebDriverWrapper(IneligibleAssetReportPage.class);
        historicalExposureReportPage = pageFactory.createByWebDriverWrapper(HistoricalExposureReportPage.class);

        calculationRuleMaintenancePage = pageFactory.createByWebDriverWrapper(CalculationRuleMaintenancePage.class);

        databaseOperationsPage = pageFactory.createByDatabaseWrapper(DatabaseOperationsPage.class);

        //self service
        externalExposureListPage  = pageFactory.createByWebDriverWrapper(ExternalExposureListPage.class);

    }
}
