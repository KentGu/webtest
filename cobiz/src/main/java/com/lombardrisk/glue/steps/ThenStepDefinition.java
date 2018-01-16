package com.lombardrisk.glue.steps;

import com.lombardrisk.data.Menu;
import com.lombardrisk.pages.PageManager;
import com.lombardrisk.pojo.*;
import com.lombardrisk.test.TestDataManager;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.collateral.AgreementUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;

import java.util.ArrayList;
import java.util.List;


public class ThenStepDefinition {
    private final static Logger logger = LoggerFactory.getLogger(ThenStepDefinition.class);
    private ITestBase testCase = TestCaseManager.getTestCase();
    private TestDataManager testDataManager = (TestDataManager) testCase.getTestDataManager();
    private PageManager pageManager = (PageManager) testCase.getPageManager();

    @Then("^organisation should be (\\S+)$")
    public void organisation_should_be(String id) throws Exception {
        Organisation organisation = testDataManager.getOrganisation(id);
        pageManager.viewOrganisationsPage.assertOrganisation(organisation);
        testCase.embedTestData(organisation);
    }

    @Then("^organisation search result should be (\\S+)$")
    public void organisation_search_result_should_be(String id) throws Exception {
        OrganisationSearch organisationSearch = testDataManager.getOrganisationSearch(id);
        pageManager.viewOrganisationsPage.assertOrganisationSearchResult(organisationSearch);

        testCase.embedTestData(organisationSearch);

    }

    /**
     * Description: Check organisation rating level<br/>
     * Start page: organisation rating level page <br/>
     * End page: organisation rating level page <br/>
     * Samples: organisation rating level should be <b>my.agencyRating</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link AgencyRating}</li>
     * @throws Exception
     */
    @Then("^organisation rating level should be (\\S+)$")
    public void organisation_rating_level_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            AgencyRating agencyRating = testDataManager.getAgencyRating(id);
            pageManager.ratingLevelPage.assertRatingLevel(agencyRating);
            testCase.embedTestData(agencyRating);
        }
    }

    /**
     * Description: Check organisation book<br/>
     * Start page: Edit Book Hierarchy page <br/>
     * End page: Edit Book Hierarchy page <br/>
     * Samples: organisation book should be <b>my.organisationBook</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link OrganisationBook}</li>
     * @throws Exception
     */
    @Then("^organisation book should be (\\S+)$")
    public void organisation_book_should_be(String id) throws Exception {
        OrganisationBook organisationBook = testDataManager.getOrganisationBook(id);
        pageManager.booksPage.assertOrganisationBook(organisationBook);
        testCase.embedTestData(organisationBook);
    }

    /**
     * Description: Check organisation industry<br/>
     * Start page: Edit industry Hierarchy page <br/>
     * End page: Edit industry Hierarchy page <br/>
     * Samples: organisation industry should be <b>my.organisationIndustry</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link OrganisationIndustry}</li>
     * @throws Exception
     */
    @Then("^organisation industry should be (\\S+)$")
    public void organisation_industry_should_be(String id) throws Exception {
        OrganisationIndustry organisationIndustry = testDataManager.getOrganisationIndustry(id);
        pageManager.industryPage.assertOrganisationIndustry(organisationIndustry);
        testCase.embedTestData(organisationIndustry);
    }

    /**
     * Description: Check organisation global preferences<br/>
     * Start page: organisation global preferences page <br/>
     * End page: organisation global preferences page <br/>
     * Samples: organisation global preferences should be <b>my.organisationGlobalPreference</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link OrganisationGlobalPreference}</li>
     * @throws Exception
     */
    @Then("^organisation global preferences should be (\\S+)$")
    public void organisation_global_preferences_should_be(String id) throws Exception {
        OrganisationGlobalPreference organisationGlobalPreference = testDataManager.getOrganisationGlobalPreference(id);
        pageManager.globalPreferencesPage.assertOrganisationGlobalPreferences(organisationGlobalPreference);

        testCase.embedTestData(organisationGlobalPreference);
    }

    /**
     * Description: Check organisation static data<br/>
     * Start page: organisation static data page <br/>
     * End page: organisation static data page <br/>
     * Samples: organisation static data should be <b>my.staticdata</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Then("^organisation static data should be (\\S+)$")
    public void organisation_static_data_should_be(String id) throws Exception {
        StaticData staticData = testDataManager.getOrganisationStaticData(id);
        pageManager.organisationStaticDataPage.openStaticDataType(staticData);
        pageManager.organisationStaticDataPage.assertStaticData(staticData);
        testCase.embedTestData(staticData);
    }

    /**
     * Description: Check FX rate set<br/>
     * Start page: FX Rate Page <br/>
     * End page: FX Rate Page <br/>
     * Samples: fx rate set should be <b>my.fxrateset</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @throws Exception
     */
    @Then("^fx rate set should be (\\S+)$")
    public void fx_rate_set_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        FxRateSet fxRateSet = testDataManager.getFxRateSet(id);
        pageManager.fxRatesPage.assertFxRateSet(fxRateSet);
        testCase.embedTestData(fxRateSet);
    }

    /**
     * Description: Check securities data security editor <br/>
     * Start page: assets securities data security search page<br/>
     * End page: assets securities data security editor page <br/>
     * Samples: security <b>my.SecuritySearchResult</b><br/> should be <b>my.Instrument</b><br/>
     *
     * @param resultId   can be one of following type:<br /><li>{@link SecuritySearchResult}</li>
     * @param securityId can be one of following type:<br /><li>{@link Instrument}</li>
     * @throws Exception
     */
    @Then("^security (\\S+) should be (\\S+)$")
    public void security_should_be(String resultId, String securityId) throws Exception {
        SecuritySearchResult securitySearchResult = testDataManager.getSecuritySearchResult(resultId);
        Instrument instrument = testDataManager.getInstrument(securityId);
        if (securitySearchResult == null) {
            pageManager.securityEditorPage.assertInstrument(instrument);
            testCase.embedTestData(instrument);
        } else {
            pageManager.securitySearchPage.editInstrument(securitySearchResult);
            testCase.embedTestData(securitySearchResult);
            pageManager.securityEditorPage.assertInstrument(instrument);
            testCase.embedTestData(instrument);
        }
    }

    /**
     * Description: Check Validation Error in securities data security editor page<br/>
     * Start page: assets securities data security editor page page<br/>
     * End page: assets securities data security editor page page <br/>
     * Samples: security <b>my.SecuritySearchResult</b><br/> should be <b>my.Instrument</b><br/>
     *
     * @param securityId can be one of following type:<br /><li>{@link Instrument}</li>
     * @throws Exception
     */
//    @Then("^validation error in security editor page should be (\\S+)$")
//    public void validation_error_should_be(String securityId) throws Exception {
//        Instrument instrument = testDataManager.getInstrument(securityId);
//        if (instrument.getValidationError() != null && instrument.getValidationError().size() > 0) {
//            List<ValidationErrorType> validationErrors = instrument.getValidationError();
//            for (ValidationErrorType validationError : validationErrors) {
//                if (validationError.getContext() != null) {
//                    assertThat("ag.bookingInfo.validationError").innerText().contains(validationError.getContext().getRealValue());
//                }
//            }
//            testCase.embedTestData(instrument);
//        }
//    }

    /**
     * Description: Check securities data security search result tab <br/>
     * Start page: assets securities data security search page<br/>
     * End page: assets securities data security search page <br/>
     * Samples: security search result should be <b>my.SecuritySearchResult</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link SecuritySearchResult}</li>
     * @throws Exception
     */
    @Then("^security search result should be (\\S+)$")
    public void security_search_result_should_be(String id) throws Exception {
        SecuritySearchResult securitySearchResult = testDataManager.getSecuritySearchResult(id);
        pageManager.securitySearchPage.assertSecuritySearchResult(securitySearchResult);
        testCase.embedTestData(securitySearchResult);
    }

    /**
     * Description: Check security instrument search result tab <br/>
     * Start page: security instrument search page<br/>
     * End page: security instrument search page <br/>
     * Samples: instrument search result should be <b>my.securitySearch</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@linksecuritySearch}</li>
     * @throws Exception
     */
    @Then("^security Instrument search result should be (\\S+)$")
    public void security_Instrument_search_result_should_be(String id) throws Exception {
        SecuritySearch securitySearch = testDataManager.getSecuritySearch(id);
        pageManager.securitySearchPage.assertSecuritySearch(securitySearch);
        testCase.embedTestData(securitySearch);
    }

    /**
     * Description: Check TSA rule<br/>
     * Start page: TSA Rule search page <br/>
     * End page: TSA Rule search page <br/>
     * Samples: TSA rule should be <b>my.tsa.rule</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @Then("^TSA rule should be (\\S+)$")
    public void tsa_rule_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TsaRule tsaRule = testDataManager.getTsaRule(id);
        testCase.embedTestData(tsaRule);
        pageManager.tsaRulesPage.editTsaRule(tsaRule);

        pageManager.tsaRulesPage.cancelTsaRule();
        throw new PendingException();
    }

    @Then("^(?:asset|inventory|dynamic) manager (\\S+) should be (\\S+)$")
    public void asset_inventory_dynamic_manager_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^(?:asset|inventory|dynamic) manager position (\\S+) result should be (\\S+)$")
    public void asset_inventory_dynamic_manager_position_result_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^(?:asset|inventory|dynamic) manager position result in (csv|excel) format should be (\\S+)$")
    public void asset_inventory_dynamic_manager_position_result_in_format_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^(?:asset|inventory|dynamic) manager booking result in (csv|excel) format should be (\\S+)$")
    public void asset_inventory_dynamic_manager_booking_result_in_format_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^inventory manager position user defined filter should be (\\S+)$")
    public void inventory_manager_position_user_defined_filter_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^inventory manager booking user defined filter should be (\\S+)$")
    public void inventory_manager_booking_user_defined_filter_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check agreement party and counterparty tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: party and counterparty tab page <br/>
     * Samples: agreement party/counterparty should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – party/counterparty should be (\\S+)$")
    public void agreement_summary_party_counterparty_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement documentation tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: documentation tab page <br/>
     * Samples: agreement documentation should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – documentation should be (\\S+)$")
    public void agreement_summary_documentation_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement call schedule tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: call schedule tab page <br/>
     * Samples: agreement call schedule should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – call schedule should be (\\S+)$")
    public void agreement_summary_call_schedule_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }


    /**
     * Description: Check agreement products tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: products tab page <br/>
     * Samples: agreement products should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – products should be (\\S+)$")
    public void agreement_summary_products_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement collateral tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: collateral tab page <br/>
     * Samples: agreement collateral should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – collateral should be (\\S+)$")
    public void agreement_summary_collateral_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement fixed trigger tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: fixed trigger tab page <br/>
     * Samples: agreement fixed trigger should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – fixed trigger should be (\\S+)$")
    public void agreement_summary_fixed_trigger_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement rule trigger tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: rule trigger tab page <br/>
     * Samples: agreement rule trigger should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – rule trigger should be (\\S+)$")
    public void agreement_summary_rule_trigger_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement additional fields tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: additional fields tab page <br/>
     * Samples: agreement additional fields should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – additional fields should be (\\S+)$")
    public void agreement_summary_additional_fields_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement settlement tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: settlement tab page <br/>
     * Samples: agreement settlement should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – settlement should be (\\S+)$")
    public void agreement_summary_settlement_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement reporting control tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: reporting control tab page <br/>
     * Samples: agreement reporting control should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – reporting control should be (\\S+)$")
    public void agreement_summary_reporting_control_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    /**
     * Description: Check agreement agreement summary tab <br/>
     * Start page: any tab of agreement summary <br/>
     * End page: agreement summary tab page <br/>
     * Samples: agreement agreement summary should be <b>my.AgreementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @Then("^agreement summary – agreement summary should be (\\S+)$")
    public void agreement_summary_agreement_summaryl_should_be(String id) throws Exception {
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        pageManager.agreementSummaryPage.switchToAgreementSummaryTab();
        pageManager.agreementSummaryPage.agreementSummaryAssertion(agreementSummary);
        testCase.embedTestData(agreementSummary);
    }

    @Then("^agreement (\\S+) summary should be (\\S+)$")
    public void agreement_summary_should_be(String resultId, String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        throw new PendingException();
    }

    /**
     * Description: Check agreement exposure trade summary page <br/>
     * Start page: trade summary page <br/>
     * End page: trade summary page <br/>
     * Samples: trade summary should be <b>my.tradeSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TradeSummary}</li>
     * @throws Exception
     */
    @Then("^trade summary should be (\\S+)$")
    public void trade_summary_should_be(String id) throws Exception {
        TradeSummary tradeSummary = testDataManager.getTradeSummary(id);
        pageManager.tradeSummaryPage.assertTradeSummary(tradeSummary);
        testCase.embedTestData(tradeSummary);
    }

    /**
     * Description: Check agreement exposure summary page <br/>
     * Start page: agreement exposure summary page <br/>
     * End page: agreement exposure summary page <br/>
     * Samples: exposure summary should be <b>my.agreementExposureSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementExposureSummary}</li>
     * @throws Exception
     */
    @Then("^exposure summary should be (\\S+)$")
    public void exposure_summary_should_be(String id) throws Exception {
        AgreementExposureSummary summary = testDataManager.getAgreementExposureSummary(id);
        pageManager.agreementExposuresSummaryPage.assertAgreementExposureSummary(summary);
        testCase.embedTestData(summary);
    }

    /**
     * Description: Check portfolioIa in EM page <br/>
     * Start page: EM page <br/>
     * End page: EM page <br/>
     * Samples: Exposure Management - portfolioIa dialog should be <b>my.agreementExposureSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AgreementExposureSummary}</li>
     * @throws Exception
     */
    @Then("^Exposure Management - portfolioIa dialog should be (\\S+)$")
    public void portfolioIa_dialog_in_EM_should_be(String id) throws Exception {
        AgreementExposureSummary summary = testDataManager.getAgreementExposureSummary(id);
        pageManager.exposureManagementPage.assertPortfolioIaDialog(summary);
        testCase.embedTestData(summary);
    }

    /**
     * Description: Check trade detail in trade editor page<br/>
     * Start page: trade editor page <br/>
     * End page: trade editor page <br/>
     * Samples: trade detail should be <b>my.tradeDetail</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @throws Exception
     */
    @Then("^trade detail should be (\\S+)$")
    public void trade_details_should_be(String id) throws Exception {
        TradeDetail tradeDetail = testDataManager.getTradeDetail(id);
        pageManager.tradeEditorPage.assertTradeDetailByInvoke(tradeDetail);
        testCase.embedTestData(tradeDetail);
    }

    /**
     * Description: Check asset holdings detail in collateral agreement asset holdings summary page<br/>
     * Start page: agreement asset holdings detail page <br/>
     * End page: agreement asset holdings detail page <br/>
     * Samples: asset holding summary should be <b>my.assetBookingSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @Then("^asset holding detail should be (\\S+)$")
    public void asset_holding_detail_should_be(String id) throws Exception {
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(id);
        pageManager.agreementAssetsSummaryPage.assertAssetHoldingDetail(assetBookingSummary);
        testCase.embedTestData(assetBookingSummary);

    }

    /**
     * Description: Check asset holdings summary detail in collateral agreement asset holdings summary page<br/>
     * Start page: agreement asset holdings summary page <br/>
     * End page: agreement asset holdings summary page <br/>
     * Samples: asset holding summary should be <b>my.collateralAssetHoldingSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CollateralAssetHoldingSummary}</li>
     * @throws Exception
     */
    @Then("^asset holding summary should be (\\S+)$")
    public void asset_holding_summary_should_be(String id) throws Exception {
        CollateralAssetHoldingSummary summary = testDataManager.getCollateralAssetHoldingSummary(id);
        pageManager.agreementAssetHoldingsSummaryPage.assertAssetHoldingSummary(summary);
        testCase.embedTestData(summary);
    }

    /**
     * Description: Check asset booking summary detail in collateral agreement asset security/cash/equity/other summary page<br/>
     * Start page: Collateral - Agreement Assets - Security Summary  <br/>
     * End page: Collateral - Agreement Assets - Security Summary  <br/>
     * Samples: Security asset booking summary should be <b>my.assetBookingSummary</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @Then("^(CASH|Equity|Security|Other)? asset booking summary should be (\\S+)$")
    public void asset_type_asset_holding_detail_should_be(String type, List<String> ids) throws Exception {
        for (String id : ids) {
            AssetBookingSummary summary = testDataManager.getAssetBookingSummary(id);
            pageManager.agreementAssetsSummaryPage.assertAssetBookingSummary(summary);
            testCase.embedTestData(summary);
        }
    }

    /**
     * Description: Check booking detail in trade editor page<br/>
     * Start page: booking editor page <br/>
     * End page: booking editor page <br/>
     * Samples: booking details should be <b>my.bookingDetail</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @Then("^booking details should be (\\S+)$")
    public void booking_details_should_be(String id) throws Exception {
        BookingDetail bookingDetail = testDataManager.getBookingDetail(id);
        pageManager.agreementAssetsEditorPage.assertBooking(bookingDetail);
        testCase.embedTestData(bookingDetail);
    }
    /**
     * Description: Check cash movement summary in cash movement editor page<br/>
     * Start page: cash movement summary page <br/>
     * End page: cash movement summary page <br/>
     * Samples: cash movement summary should be <b>my.cashMovementSummary</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CashMovementSummary}</li>
     * @throws Exception
     */
    @Then("^cash movement summary should be (\\S+)$")
    public void cash_movement_summary_should_be(String id) throws Exception {
        CashMovementSummary cashMovementSummary = testDataManager.getCashMovementSummary(id);
        pageManager.cashMovementSummaryPage.assertCashMovementSummary(cashMovementSummary);
        testCase.embedTestData(cashMovementSummary);
    }

    /**
     * Description: Check cash movement detail in cash movement editor page<br/>
     * Start page: cash movement editor page <br/>
     * End page: cash movement editor page <br/>
     * Samples: cash movement detail should be <b>my.cashmovement</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CashMovementDetail}</li>
     * @throws Exception
     */
    @Then("^cash movement detail should be (\\S+)$")
    public void cash_movement_detail_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CashMovementDetail cashMovementDetail = testDataManager.getCashMovementBooking(id);
        pageManager.cashMovementEditorPage.assertCashMovementDetail(cashMovementDetail);
        testCase.embedTestData(cashMovementDetail);
    }

    /**
     * Description: Check agreement statement in agreement statement page<br/>
     * Start page: cash movement editor page <br/>
     * End page: cash movement editor page <br/>
     * Samples: cash movement detail should be <b>my.cashmovement</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CashMovementDetail}</li>
     * @throws Exception
     */
    @Then("^agreement statement should be (\\S+)$")
    public void agreement_statement_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementStatement agreementStatement = testDataManager.getAgreementStatement(id);
        pageManager.agreementStatementPage.assertAgreementStatement(agreementStatement);
        testCase.embedTestData(agreementStatement);
    }

    @Then("^multi model agreement statement summary should be (\\S+)$")
    public void multi_model_agreement_statement_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        MultiModelAgreementStatement agreementStatement = testDataManager.getMultiModelAgreementStatement(id);
        pageManager.agreementStatementPage.assertMultiModelAgreementStatementSummary(agreementStatement);
        testCase.embedTestData(agreementStatement);
    }

    /**
     * Description: Check ETD agreement top level statement in agreement statement page<br/>
     * Start page: ETD top level statement page <br/>
     * End page: ETD top level statement page <br/>
     * @param id can be one of the following type: <br /><li>{@link EtdAgreementStatement}</li>
     * Samples: etd agreement top level statement summary should be <b>my.etd.top.level.agreement.statement</b><br/>
     * @throws Exception
     */
    @Then("^(?:Agreement Statement - )?etd agreement top level statement summary should be (\\S+)$")
    public void etd_agreement_top_level_statement_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EtdAgreementStatement agreementStatement = testDataManager.getEtdAgreementStatement(id);
        testCase.embedTestData(agreementStatement);
        pageManager.agreementStatementPage.assertEtdAgreementStatementSummary(agreementStatement);
    }

    /**
     * Description: Check ETD agreement model level statement in agreement statement page<br/>
     * Start page: ETD model level statement page <br/>
     * End page: ETD model level statement page <br/>
     * @param id can be one of the following type: <br /><li>{@link AgreementStatement}</li>
     * Samples: ^etd agreement model level statement summary should be <b>my.etd.model.level.agreement.statement</b><br/>
     * @throws Exception
     */
    @Then("^(?:Agreement Statement - )?etd agreement model level statement summary should be (\\S+)$")
    public void etd_agreement_model_level_statement_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementStatement agreementStatement = testDataManager.getAgreementStatement(id);
        testCase.embedTestData(agreementStatement);
        pageManager.agreementStatementPage.assertAgreementStatement(agreementStatement);
    }

    @Then("^exposure statement should be (\\S+)$")
    public void exposure_statement_should_be(String id) throws Exception {
        ExposureStatement statement = testDataManager.getExposureStatement(id);
        pageManager.exposureStatementPage.assetExposureStatementInfo(statement);
        testCase.embedTestData(statement);
    }

    @Then("^start-up dashboard should be (\\S+)$")
    public void start_up_dashboard_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions

        throw new PendingException();
    }

    @Then("^workflow dashboard should be (\\S+)$")
    public void workflowDashboard_should_be(String id) throws Exception {
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.assetDashboardInfo(dashboard);
    }

    /**
     * Description: check all approveButton's value in settlement Status Search Result Page<br/>
     * Start page: settlementStatusSearchResultPage <br/>
     * End page: settlementStatusSearchResultPage<br/>
     * samples: settlement status - check approveButton's value in settlement status search result<br/>
     *
     * @param status can be one of following type:<br /><li>Approve All System Draft</li><li>Approve All Pending</li><li>Approve All Authorised</li>
     * @throws Exception
     */
    @Then("^settlement status - check all approveButton (Approve All System Draft|Approve All Pending|Approve All Authorised) should (exist|not exist) in settlement status search result$")
    public void settlement_status_check_all_status_in_settlement_status_search_result(String status, String existOrNot) throws Exception {
        boolean isExisted = false;
        if (existOrNot.equals("exist"))
            isExisted = true;
        pageManager.settlementStatusSummaryPage.assertApproveButton(status, isExisted);
    }

    @Then("^settlement status - check all approveButton (Approve All System Draft|Approve All Pending|Approve All Authorised) should (exist|not exist) in settlement status page$")
    public void settlement_status_check_all_status_in_settlement_status_page(String status, String existOrNot) throws Exception {
        boolean isExisted = false;
        if (existOrNot.equals("exist"))
            isExisted = true;
        pageManager.settlementStatusPage.assertApproveButton(status, isExisted);
    }

    @Then("^organisation result in approvals manager should be (\\S+)$")
    public void organisation_result_in_approvals_manager_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check search result in approvals manager page existed<br/>
     * Start page: approvals manager page <br/>
     * End page: approvals manager page <br/>
     * Samples: approvals manager - the search result <b>my.search.result</b> is existed<br/>
     *
     * @param ids can be one of following type:<br /><li>{@link OrganisationSearchResult}</li><li>{@link AgreementSearchResult}</li><li>{@link }</li>
     * @throws Exception
     */
    @Then("^approvals manager - the search result (\\S+) (is|is not) existed$")
    public void approvals_manager_the_search_result_existed(List<String> ids, String flag) throws Exception {
        for (String id : ids) {
            Object o;
            o = testDataManager.getApprovalManagerOrganisationSearchResult(id);
            if (o == null) {
                o = testDataManager.getApprovalManagerAgreementSearchResult(id);
                if (o == null) {
                    o = testDataManager.getApprovalManagerStatementSearchResult(id);
                    if (o == null) {
                        o = testDataManager.getApprovalManagerWorkflowSearchResult(id);
                        if (o == null) {
                            o = testDataManager.getApprovalManagerSettlementInstructionsSearchResult(id);
                            if (o == null) {
                                o = testDataManager.getApprovalManagerSecuritySearchResult(id);
                                if (o == null) {
                                    o = testDataManager.getApprovalManagerEligibilityRulesTemplateSearchResult(id);
                                    if (o == null) {
                                        o = testDataManager.getApprovalManagerTradesSearchResult(id);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            testCase.embedTestData(o);
            switch (flag.toLowerCase()) {
                case "is":
                    pageManager.approvalsManagerPage.isApprovalSearchResultExist(o, true);
                    break;
                case "is not":
                    pageManager.approvalsManagerPage.isApprovalSearchResultExist(o, false);
                    break;
            }
        }
    }

    /**
     * Description: Check search result can be checked manager page existed<br/>
     * Start page: approvals manager page <br/>
     * End page: approvals manager page <br/>
     * Samples: approvals manager - the search result <b>my.search.result</b> can be checked<br/>
     *
     * @param ids can be one of following type:<br /><li>{@link OrganisationSearchResult}</li><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @Then("^approvals manager - the search result (\\S+) (can|cannot) be checked$")
    public void approvals_manager_the_search_result_can_be_checked(List<String> ids, String flag) throws Exception {
        for (String id : ids) {
            Object o = testDataManager.getApprovalManagerOrganisationSearchResult(id);
            if (o == null) {
                o = testDataManager.getApprovalManagerAgreementSearchResult(id);
                if (o == null) {
                    o = testDataManager.getApprovalManagerStatementSearchResult(id);
                    if (o == null) {
                        o = testDataManager.getApprovalManagerWorkflowSearchResult(id);
                        if (o == null) {
                            o = testDataManager.getApprovalManagerSettlementInstructionsSearchResult(id);
                            if (o == null) {
                                o = testDataManager.getApprovalManagerSecuritySearchResult(id);
                                if (o == null) {
                                    o = testDataManager.getApprovalManagerEligibilityRulesTemplateSearchResult(id);
                                    if (o == null) {
                                        o = testDataManager.getApprovalManagerTradesSearchResult(id);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            testCase.embedTestData(o);
            if (flag.equalsIgnoreCase("can")) {
                pageManager.approvalsManagerPage.isApprovalSearchResultCanBeChecked(o, true);
            } else if (flag.equalsIgnoreCase("cannot")) {
                pageManager.approvalsManagerPage.isApprovalSearchResultCanBeChecked(o, false);
            }
        }
    }

    /**
     * Description: Check settlement status search result in settlement status summary page<br/>
     * Start page: settlement status summary page <br/>
     * End page: settlement status summary page <br/>
     * Samples: settlement status search result should be <b>my.settlementStatusSummary</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link SettlementStatusSummary}</li>
     * @throws Exception
     */
    @Then("^settlement status - search result should be (.*)$")
    public void settlement_status_search_result_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            SettlementStatusSummary settlementStatusSummary = testDataManager.getSettlementStatusSummary(id);
            pageManager.settlementStatusSummaryPage.assertSettlementStatusSummary(settlementStatusSummary);
            testCase.embedTestData(settlementStatusSummary);
        }
    }

    /**
     * Description: Check settlement status Netted or PreNetted table should not be displayed<br/>
     * Start page: settlement status detail page <br/>
     * End page: settlement status detail page <br/>
     * Samples: Netted or PreNetted table should be not be displayed in settlement status detail page<br/>
     *
     * @param table can be one of following type:<br /><li>Netted</li><li>PreNetted</li>
     * @throws Exception
     */
    @Then("^settlement status - (Netted|PreNetted) table should not be displayed$")
    public void settlement_status_NetOrPreNet_table_should_not_displayed(String table) throws Exception {
//        SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(id);
//        testCase.embedTestData(settlementStatusDetail);
        switch (table.toLowerCase()) {
            case "netted":
                pageManager.settlementStatusPage.assertNetTableInSettlementStatusDetail();
                break;
            case "prenetted":
                pageManager.settlementStatusPage.assertPreNetTableInSettlementStatusDetail();
                break;

        }
    }


    @Then("^settlement status (\\S+) should be (\\S+)$")
    public void settlement_status_should_be(List<String> oriIds, List<String> checkIds) throws Exception {
        List<SettlementStatusDetail> checklist = new ArrayList<SettlementStatusDetail>();
        for (String id : checkIds) {
            checklist.add(testDataManager.getSettlementStatusDetail(id));
        }
        for (String id : oriIds) {
            SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(id);
            SettlementStatusDetail checkSettlementStatusDetail = (SettlementStatusDetail) Biz.matchedObjectWithName(settlementStatusDetail, checklist);
            pageManager.settlementStatusPage.assertSettlementStatusDetail(settlementStatusDetail, checkSettlementStatusDetail);
            testCase.embedTestData(settlementStatusDetail);
            testCase.embedTestData(checkSettlementStatusDetail);
        }

    }


    @Then("^security movement (\\S+) should be (\\S+)$")
    public void security_movement_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check security movement summary in security movement summary page<br/>
     * Start page: security movement summary page <br/>
     * End page: security movement summary page <br/>
     * Samples: security movement summary should be <b>my.security.movement</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link SecurityMovementSummary}</li>
     * @throws Exception
     */
    @Then("^security movement summary should be (\\S+)$")
    public void security_movement_summary_should_be(String id) throws Exception {
        SecurityMovementSummary securityMovementSummary = testDataManager.getSecurityMovementSummary(id);
        pageManager.securityMovementSummaryPage.assertSecurityMovementSummary(securityMovementSummary);
        testCase.embedTestData(securityMovementSummary);
    }

    /**
     * Description: Check collateral preferences page<br/>
     * Start page: collateral preferences page <br/>
     * End page: collateral preferences page <br/>
     * Samples: collateral preferences should be <b>my.collateralPreference</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CollateralPreference}</li>
     * @throws Exception
     */
    @Then("^collateral preferences should be (\\S+)$")
    public void collateral_preferences_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CollateralPreference collateralPreference = testDataManager.getCollateralPreference(id);
        testCase.embedTestData(collateralPreference);
        pageManager.collateralPreferencesPage.assertCollateralPreference(collateralPreference);
    }

    @Then("^stp filter (\\S+) should be (\\S+)$")
    public void stp_filter_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^stp - (?:quality|cutoff|margin|cutoff) quality rule (\\S+) should be (\\S+)$")
    public void stp_quality_cutoff_margin_cutoff_quality_rule_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^stp - business rules (\\S+) should be (\\S+)$")
    public void stp_business_rules_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^stp - stp switch should be (\\S+)$")
    public void stp_stp_switch_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^stp - status dashboard should be (\\S+)$")
    public void stp_status_dashboard_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^stp (\\S+) should be (\\S+)$")
    public void stp_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check feed result in task manager page<br/>
     * Start page: task manager page <br/>
     * End page: task manager page <br/>
     * Samples: feed log should be <b>my.check.feedResult</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link FeedResult}</li>
     * @throws Exception
     */
    @Then("^feed log should be (\\S+)$")
    public void feed_log_should_be(String id) throws Exception {
        FeedResult feedResult = testDataManager.getFeedResult(id);
        pageManager.taskManagerPage.assertFeedResult(feedResult);
        testCase.embedTestData(feedResult);
    }

    /**
     * Description: Check feed status in task manager page<br/>
     * Start page: any page <br/>
     * End page: task manager page <br/>
     * Samples: feed status should be <b>my.check.feedStatus</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link FeedStatus}</li>
     * @throws Exception
     */
    @Then("^feed status should be (\\S+)$")
    public void feed_status_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_STATUS);
        FeedStatus feedStatus = testDataManager.getFeedStatus(id);
        pageManager.taskManagerPage.assertFeedStatus(feedStatus);
        testCase.embedTestData(feedStatus);
    }

    /**
     * Description: Check exposure event detail in EM page, event and check event should have same value in its own name attribute<br/>
     * Start page: EM page <br/>
     * End page: EM page <br/>
     * Samples: Exposure Management <b>my.event1</b> - event call status should be <b>my.check.event1</b><br/>
     *
     * @param eventIds can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @param checkIds can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */
    @Then("^Exposure Management (\\S+) - event (?:call status |action )?should be (\\S+)$")
    public void exposure_management_event_should_be(List<String> eventIds, List<String> checkIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<ExposureEventDetail> checklist = new ArrayList<ExposureEventDetail>();
        for (String id : checkIds) {
            checklist.add(testDataManager.getExposureEventDetail(id));
        }
        for (String id : eventIds) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            ExposureEventDetail checkExposureEventDetail = (ExposureEventDetail) Biz.matchedObjectWithName(exposureEventDetail, checklist);
            testCase.embedTestData(exposureEventDetail);
            testCase.embedTestData(checkExposureEventDetail);
            pageManager.exposureManagementPage.assertExposureEventDetail(exposureEventDetail, checkExposureEventDetail);
        }
    }

    /**
     * Description: Check exposure event detail in EM page<br/>
     * Start page: EM page <br/>
     * End page: EM page <br/>
     * Samples: Exposure Management  - event should be <b>my.check.events</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */

    @Then("^Exposure Management - (\\S+) action should be (?:displayed|not displayed)$")
    public void exposure_management_action_should_be_displayOrNotdisplay(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            pageManager.exposureManagementPage.assertAction(exposureEventDetail);

        }
    }

    /**
     * Description: Check exposure event detail in EM page<br/>
     * Start page: EM page <br/>
     * End page: EM page <br/>
     * Samples: Exposure Management  - event should be <b>my.check.events</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */

    @Then("^Exposure Management - event should be (\\S+)$")
    public void exposure_management_event_should_be(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            pageManager.exposureManagementPage.assertEmEventDetail(exposureEventDetail);
        }
    }

    /**
     * Description: Check exposure management substitution event detail in EM page<br/>
     * Start page: EM page <br/>
     * End page: EM page <br/>
     * Samples: Exposure Management  -substitution event should be <b>my.check.subevents</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link SubstitutionEventDetail}</li>
     * @throws Exception
     */

    @Then("^Exposure Management - substitution event should be (\\S+)$")
    public void exposure_management_substitution_event_should_be(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            SubstitutionEventDetail substitutionEventDetail = testDataManager.getSubstitutionEventDetail(id);
            testCase.embedTestData(substitutionEventDetail);
            pageManager.exposureManagementPage.assertSubEventDetail(substitutionEventDetail);
        }
    }


    @Then("^Exposure Management (\\S+) - search result should follow rule (\\S+)$")
    public void exposure_management_search_result_should_follow_rule(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Assert all bulk booking info in bulk booking page<br/>
     * Start page: bulk booking page <br/>
     * End page: bulk booking page<br/>
     * samples: bulk booking should be <b>bulk booking info</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link BookingInformationDetail}</li>
     * @throws Exception
     */

//    @Then("^Bulk booking should be (\\S+)$")
//    public void bulk_booking_should_be(String id) throws Exception {
//        // Write code here that turns the phrase above into concrete actions
//        BookingDetail bookingDetail = testDataManager.getBookingDetail(id);
//        testCase.embedTestData(bookingDetail);
//        pageManager.exposureManagementPage.assertBulkBookingInformation(bookingDetail);;
//    }

    /**
     * Description: Assert ajax search in bulk booking page<br/>
     * Start page: bulk booking page <br/>
     * End page: bulk booking page<br/>
     * samples: Exposure Management - <b>event</b> should match ajax search for <b>instrumentID</b><br/>
     *
     * @param oriId can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @param newIds can be one of following type:<br /><li>{@link BookingDetail}</li>

     * @throws Exception
     */
    @Then("^Exposure Management - (\\S+) should match ajax search for (\\S+)$")
    public void em_bulkBooking_ajax_search_should_be(String oriId, List<String> newIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        BookingDetail oriBookingDetail = testDataManager.getBookingDetail(oriId);
        for (String newId : newIds) {
            BookingDetail newBookingDetail = testDataManager.getBookingDetail(newId);
            testCase.embedTestData(newId);
            pageManager.exposureManagementPage.assertBulkBookingAjaxSearch(oriBookingDetail,newBookingDetail,true);
        }



    }

    /**
     * Description: Assert all bulk booking info in bulk booking page<br/>
     * Start page: bulk booking page <br/>
     * End page: bulk booking page<br/>
     * samples: bulk booking should be <b>bulk booking info</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link BookingInformationDetail}</li>
     * @throws Exception
     */
    @Then("^EM Bulk booking should be (\\S+)$")
    public void em_bulk_booking_should_be(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            BookingDetail bookingDetail = testDataManager.getBookingDetail(id);
            testCase.embedTestData(bookingDetail);
            pageManager.exposureManagementPage.assertBulkBookingInfo(bookingDetail);
        }
    }

    /**
     * Description: check alert message<br/>
     * Start page: bulk booking page <br/>
     * End page: bulk booking page<br/>
     * samples: alert message should be <b>bulk booking info</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link BookingInformationDetail}</li>
     * @throws Exception
     */
    @Then("^alert message should be (\\S+) in bulk booking page$")
    public void alert_message_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        BookingDetail bookingDetail = testDataManager.getBookingDetail(id);
        testCase.embedTestData(bookingDetail);
        pageManager.exposureManagementPage.checkAlertMessage(bookingDetail);
        ;
    }


    @Then("^letter summary should be (\\S+)$")
    public void letter_summary_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        MarginLetterSummary marginLetterSummary = testDataManager.getMarginLetterSummary(id);
        pageManager.letterSummaryPage.assertLetterSummaryPage(marginLetterSummary);
        testCase.embedTestData(marginLetterSummary);
    }

    /**
     * Description: Assert all letter detail<br/>
     * Start page: Portfolio Details Page <br/>
     * End page: Letter Admin Page<br/>
     * samples: letter detail should be <b>my.letter</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @throws Exception
     */
    @Then("^letter detail should be (\\S+)$")
    public void letter_detail_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        MarginLetter marginLetter = testDataManager.getLetter(id);
        pageManager.portfolioDetailsPage.assertPortfolioDetailsPage(marginLetter.getPortfolioDetails());
        if (marginLetter.getLetterType() != null) {
            pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
            pageManager.letterSetupPage.assertLetterSetupPage(marginLetter.getLetterSetupDetails());
            pageManager.letterSetupPage.enterNext();

            if (marginLetter.getRequestTypeAndAssetSelection() != null) {
                if (marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null && !marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection().isEmpty()) {
                    for (EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()) {
                        pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                        pageManager.requestTypeAndAssetSelectionPage.assertRequestTypeAndAssetSelectionPage(marginLetter.getRequestTypeAndAssetSelection());
                        if (eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()) {
                            for (MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()) {
                                pageManager.requestTypeAndAssetSelectionPage.assertEligibleAsset(movementAsset);
                                if (movementAsset.getEligibleAsset() != null && movementAsset.getEligibleAssetSelectionDetail() != null) {
                                    pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
                                    pageManager.letterDetailsEditPage.assertAssetSelectionDetail(movementAsset.getEligibleAssetSelectionDetail());
                                    pageManager.letterDetailsEditPage.cancelLetterDetails();
                                }
                            }
                        }
                        pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                    }
                }
            }

            pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

            pageManager.letterSummaryPage.assertLetterSummaryPage(marginLetter);
            pageManager.letterSummaryPage.switchToLetterAdminPage();
        }
        testCase.embedTestData(marginLetter);
    }

    @Then("^letter set up detail should be (\\S+)$")
    public void letter_setup_detail_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        MarginLetter marginLetter = testDataManager.getLetter(id);
//        pageManager.portfolioDetailsPage.assertPortfolioDetailsPage(marginLetter.getPortfolioDetails());
        if (marginLetter.getLetterType() != null) {
            pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
            pageManager.letterSetupPage.assertLetterSetupPage(marginLetter.getLetterSetupDetails());
        }
        testCase.embedTestData(marginLetter);
    }

    @Then("^letter pdf (\\S+) should be (\\S+)$")
    public void letter_pdf_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^Exposure Management - EM message should be (\\S+)$")
    public void exposure_management_message_should_be(String id) throws Exception {
        ExposureManagementMessage exposureManagementMessage = testDataManager.getExposureManagementMessage(id);
        pageManager.exposureManagementPage.assertExposureManagermentMessage(exposureManagementMessage);
        testCase.embedTestData(exposureManagementMessage);
    }

    @Then("^Exposure Managerment - EM booking message should be (\\S+)$")
    public void exposure_management_booking_message_should_be(String id) throws Exception {
        ExposureManagementMessage exposureManagementMessage = testDataManager.getExposureManagementMessage(id);
        pageManager.exposureManagementPage.assertExposureManagementBookingMessage(exposureManagementMessage);
        testCase.embedTestData(exposureManagementMessage);
    }

    /**
     * Description: Check interest event detail in Interest Manager search result page, event and check event should have same value in its own name attribute<br/>
     * Start page: Interest Manager search result page <br/>
     * End page: Interest Manager search result page <br/>
     * Samples: Interest Manager <b>my.event</b> - event should be <b>check.event</b><br/>
     *
     * @param eventIds can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @param checkIds can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @Then("^Interest Manager (\\S+) - event should be (\\S+)$")
    public void interest_manager_event_should_be(List<String> eventIds, List<String> checkIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<InterestEventDetail> checklist = new ArrayList<InterestEventDetail>();
        for (String id : checkIds) {
            checklist.add(testDataManager.getInterestEventDetail(id));
        }
        for (String id : eventIds) {
            InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
            InterestEventDetail checkInterestEventDetail = (InterestEventDetail) Biz.matchedObjectWithName(interestEventDetail, checklist);
            testCase.embedTestData(interestEventDetail);
            testCase.embedTestData(checkInterestEventDetail);
            pageManager.interestManagerSearchResultPage.assertInterestEventDetail(interestEventDetail, checkInterestEventDetail);
        }
    }

    /**
     * Description: Check interest event detail in Interest Manager search is existed or not result page<br/>
     * Start page: Interest Manager search result page <br/>
     * End page: Interest Manager search result page <br/>
     * Samples: Interest Manager - event <b>interest.event</b> is existed<br/>
     *
     * @param eventIds can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @param flag     can be one of following type:<br /><li>is</li><li>is not</li><li>are</li><li>are not</li>
     * @throws Exception
     */
    @Then("^Interest Manager - events? (\\S+) (is|is not|are|are not) existed$")
    public void interest_manager_event_is_existed(List<String> eventIds, String flag) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        boolean existed = true;
        if (flag.equalsIgnoreCase("is not") || flag.equalsIgnoreCase("are not"))
            existed = false;
        for (String id : eventIds) {
            InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
            testCase.embedTestData(interestEventDetail);
            pageManager.interestManagerSearchResultPage.isInterestEventDetailExsited(interestEventDetail, existed);
        }
    }

    @Then("^asset (?:class|category|type) should be (\\S+)$")
    public void asset_class_category_type_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check Interest rate detail<br/>
     * Start page: Interest View Details Page <br/>
     * End page: Interest View Details Page <br/>
     * Samples: interest rates should be <b>interest.detail</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link InterestReportDetails}</li>
     * @throws Exception
     */
    @Then("^interest rates should be (\\S+)$")
    public void interest_rates_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestReportDetails interestReportDetails = testDataManager.getInterestReportDetails(id);
        pageManager.interestViewDetailsPage.assertInterestReportDetail(interestReportDetails);
        testCase.embedTestData(interestReportDetails);
    }

    @Then("^interest reference rate should be (\\S+)$")
    public void interest_reference_rate_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check collateral static data<br/>
     * Start page: collateral static data page <br/>
     * End page: collateral static data page <br/>
     * Samples: collateral static data should be <b>my.staticdata</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Then("^collateral static data should be (\\S+)$")
    public void collateral_static_data_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            StaticData staticData = testDataManager.getCollateralStaticData(id);
            pageManager.collateralStaticDataPage.openStaticDataType(staticData);
            pageManager.collateralStaticDataPage.assertStaticData(staticData);
            testCase.embedTestData(staticData);
        }
    }

    @Then("^collateral static data settlement data (\\S+) detail should be (\\S+)$")
    public void collateral_static_data_settlement_data_detail_should_be(String resultId, String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check collateral static data settlement data<br/>
     * Start page: collateral static data settlement data page<br/>
     * End page: collateral static data settlement data page<br/>
     * Samples: collateral static data settlement data should be <b>my.settlement.data</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link SettlementDataSearchResult}</li>
     * @throws Exception
     */
    @Then("^collateral static data settlement data search result should be (\\S+)$")
    public void collateral_static_data_settlement_data_search_result_should_be(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            SettlementDataSearchResult settlementDataSearchResult = testDataManager.getSettlementDataSearchResult(id);
            testCase.embedTestData(settlementDataSearchResult);
            pageManager.settlementDataPage.assetSettlementData(settlementDataSearchResult);
        }
    }

    @Then("^collateral static data umbrella rules should be (\\S+)$")
    public void collateral_static_data_umbrella_rules_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^eligibility rules template change should be (\\S+)$")
    public void eligibility_rules_template_change_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check eligibility rule template<br/>
     * Start page: Eligibility rule template page <br/>
     * End page: Eligibility rule template page page <br/>
     * Samples: eligibility rules template should be <b>eligibility.rule.template</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplate}</li>
     * @throws Exception
     */
    @Then("^eligibility rules template should be (\\S+)$")
    public void eligibility_rules_template_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EligibilityRulesTemplate eligibilityRulesTemplate = testDataManager.getEligibilityRulesTemplate(id);
        testCase.embedTestData(eligibilityRulesTemplate);
        pageManager.eligibilityRulesTemplatePage.assertEligibilityRulesTemplate(eligibilityRulesTemplate);
    }

    @Then("^collateral static data agreement UDF values should be (\\S+)$")
    public void collateral_static_data_agreement_UDF_values_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^task scheduler (\\S+) detail should be (\\S+)$")
    public void task_scheduler_detail_should_be(String resultId, String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check task scheduler message<br/>
     * Start page: Task Scheduler Page <br/>
     * End page: Task Scheduler Page <br/>
     * Samples: task scheduler messaging should be <b>task.scheduler.message</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerMessage}</li>
     * @throws Exception
     */
    @Then("^task scheduler messaging should be (\\S+)$")
    public void task_scheduler_messaging_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerMessage taskSchedulerMessage = testDataManager.getTaskSchedulerMessage(id);
        pageManager.taskSchedulerPage.assertSchedulerMessages(taskSchedulerMessage);
        testCase.embedTestData(taskSchedulerMessage);
    }

    @Then("^user should be (\\S+)$")
    public void user_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^administer role should be (\\S+)$")
    public void administer_role_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^administer approval profiles should be (\\S+)$")
    public void administer_approval_profiles_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^privilege should be (\\S+)$")
    public void privilege_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^password constraints should be (\\S+)$")
    public void password_constraints_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^system preferences should be (\\S+)$")
    public void system_preferences_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^system static data should be (\\S+)$")
    public void system_static_data_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check holiday centre<br/>
     * Start page: holiday centre Page <br/>
     * End page: holiday centre Page <br/>
     * Samples: holiday centre should be <b>holiday.centre</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link HolidayCentre}</li>
     * @throws Exception
     */
    @Then("^holiday centre should be (\\S+)$")
    public void holiday_centre_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        HolidayCentre holidayCentre = testDataManager.getHolidayCentre(id);
        testCase.embedTestData(holidayCentre);
        pageManager.holidayCentrePage.openHolidayCentre(holidayCentre);
        pageManager.holidayCentrePage.assertHolidayCentre(holidayCentre);
    }

    @Then("^region should be (\\S+)$")
    public void region_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^business frequency should be (\\S+)$")
    public void business_frequency_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^event log should be (\\S+)$")
    public void event_log_should_be(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^booking (\\S+) CL breach should be (\\S+)$")
    public void booking_cl_breach_should_be(String bookingId, List<String> clBreachedId) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: Check booking detail in booking editor page<br/>
     * Start page: Booking editor page <br/>
     * End page: Booking editor page <br/>
     * Samples: call booking in booking editor page should be <b>my.booking</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @Then("^(?:call|return|delivery|recall) booking in booking editor page should be (\\S+)$")
    public void booking_in_booking_editor_paeg_should_be(String id) throws Exception {
        BookingDetail bookingDetail = testDataManager.getBookingDetail(id);
        pageManager.agreementAssetsEditorPage.assertBooking(bookingDetail);
        testCase.embedTestData(bookingDetail);
    }

    @Then("^(?:error|warning) message in IM Search Result should be (\\S+)$")
    public void message_in_im_search_result_should_be(String id) throws Exception {
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        pageManager.interestManagerSearchResultPage.assertMessage(interestEventDetail);
        testCase.embedTestData(interestEventDetail);
    }

    /**
     * Description: Check whether trade search result meet conditions <br/>
     * Start page: trade search page <br/>
     * End page: trade search page <br/>
     * Samples: trade search - search result should be <b>my.tradesearch</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TradeSearch}</li>
     * @throws Exception
     */
    @Then("^trade search - search result should be (\\S+)$")
    public void trade_search_result_should_be(String id) throws Exception {
        TradeSearch tradeSearch = testDataManager.getTradeSearch(id);
        pageManager.tradeSearchPage.checkAllResult(tradeSearch);
        testCase.embedTestData(tradeSearch);
    }

    /**
     * Description: Check trade search result detail info <br/>
     * Start page: trade search page <br/>
     * End page: trade search page <br/>
     * Samples: trade search - search results detail should be <b>my.tradesearchResult</b><br/>
     *
     * @param resultDetail can be one of following type:<br /><li>{@link TradeSearchResult}</li>
     * @throws Exception
     */
    @Then("^trade search - search results detail should be (\\S+)$")
    public void trade_search_result_detail_should_be(List<String> resultDetail) throws Exception {
        for (String id : resultDetail) {
            TradeSearchResult tradeSearchResultDetail = testDataManager.getTradeSearchResult(id);
            pageManager.tradeSearchPage.assertTradeSearchResultDetailByInvoke(tradeSearchResultDetail);
            testCase.embedTestData(tradeSearchResultDetail);
        }
    }

    /**
     * Description: Check whether statement archive search result meet conditions <br/>
     * Start page: statement archive search page <br/>
     * End page: statement archive search page <br/>
     * Samples: statement archive search - search result should be <b>my.statementArchive</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link TradeSearch}</li>
     * @throws Exception
     */
    @Then("^statement archive search - search result should be (\\S+)$")
    public void statement_archive_search_result_should_be(String id) throws Exception {
        StatementArchiveSearch statementArchiveSearch = testDataManager.getStatementArchiveSearch(id);
        pageManager.statementArchiveSearchPage.checkAllResult(statementArchiveSearch);
        testCase.embedTestData(statementArchiveSearch);
    }

    /**
     * Description: Check asset rule in agreement edit page <br/>
     * Start page: agreement summary page <br/>
     * End page: agreement summary page <br/>
     * Samples: asset rule in agreement edit page should be <b>agreement</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @Then("^asset rule in agreement edit page should be (\\S+)$")
    public void asset_rule_inAgreement_edit_page_should_be(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        testCase.embedTestData(agreement);
        pageManager.agreementSummaryPage.enterAgreementCollateralSummary();
        pageManager.agreementSummaryPage.forceEditAgreementSummary();
        if (agreement.getCollateralAssetClass() != null && !agreement.getCollateralAssetClass().isEmpty()) {
            for (CollateralAssetClass collateralAssetClass : agreement.getCollateralAssetClass()) {
                String assetClassName = collateralAssetClass.getCollateralAssetClassName().getRealValue();
                if (collateralAssetClass.getCollateralAssetType() != null && !collateralAssetClass.getCollateralAssetType().isEmpty()) {
                    for (CollateralAssetType collateralAssetType : collateralAssetClass.getCollateralAssetType()) {
                        pageManager.agreementCollateralSetupPage.assertAgreementAssetTypeRule(assetClassName, collateralAssetType);
                    }
                }
            }
        }
        pageManager.agreementCollateralSetupPage.saveAndExit();
    }


    /**
     * Description: Check concentration limits breached page <br/>
     * Start page: concentration limits breached page <br/>
     * End page: concentration limits breached page <br/>
     * Samples: asset concentration limits breached page should be <b>concentrationLimitBreached</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link ConcentrationLimitBreached}</li>
     * @throws Exception
     */
    @Then("^asset concentration limit rule breached should be (\\S+)$")
    public void asset_concentration_limit_rule_breached_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            ConcentrationLimitBreached concentrationLimitBreached = testDataManager.getConcentrationLimitBreached(id);
            testCase.embedTestData(concentrationLimitBreached);
            pageManager.concentrationLimitBreachedPage.assertAgreementConcentrationLimitsBreached(concentrationLimitBreached);
        }
    }

    /**
     * Description: Check eligibility rule breach information <br/>
     * Start page: eligibility rule breach page <br/>
     * End page: eligibility rule breach page <br/>
     * Samples: asset eligibility rule breached should be <b>eligibilityRuleBreached</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link EligibilityRuleBreached}</li>
     * @throws Exception
     */
    @Then("^asset eligibility rule breached should be (\\S+)$")
    public void asset_eligibility_rule_breached_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            EligibilityRuleBreached eligibilityRuleBreached = testDataManager.getEligibilityRuleBreached(id);
            testCase.embedTestData(eligibilityRuleBreached);
            pageManager.agreementAssetsEditorPage.assertEligibilityRuleBreached(eligibilityRuleBreached);
        }
    }

    /**
     * Description: validate report result <br/>
     * Start page:  <br/>
     * End page:  <br/>
     * Samples: asset <b>Excel Worksheet</b> report <b>Variable</b> should follow the rule <b>ReportValidationRules</b><br/>
     *
     * @param format     can be one of following type:<br /><li>{@link StringType}</li>
     * @param reportFile can be one of following type:<br /><li>{@link StringType}</li>
     * @param reportRule can be one of following type:<br /><li>{@link ReportValidationRules}</li>
     * @throws Exception
     */
    @Then("^assert (Excel Worksheet|CSV|XML) report (\\S+) should follow the rule (\\S+)$")
    public void assert_report_should_follow_the_rule(String format, String reportFile, String reportRule) throws Exception {
        StringType file = testDataManager.getVariable(reportFile);
        ReportValidationRules reportValidationRules = testDataManager.getReportValidationRules(reportRule);
        testCase.embedTestData(file);
        testCase.embedTestData(reportValidationRules);
        Biz.validationReport(testCase.getSoftAssertions(), format, file, reportValidationRules);
    }

    /**
     * Description: validate extract agreement result <br/>
     * Start page:  <br/>
     * End page:  <br/>
     * Samples: asset extract agreement file <b>actualAgreementPath</b> should be <b>sourceAgreementPath</b><br/>
     *
     * @param actualAgreementPath can be one of following type:<br /><li>{@link StringType}</li>
     * @param sourceAgreementPath can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @Then("^assert extract agreement file (\\S+) should be (\\S+)$")
    public void assert_extract_agreement_file_should_be(String actualAgreementPath, String sourceAgreementPath) throws Exception {

        StringType sourceAgrPath = testDataManager.getVariable(sourceAgreementPath);
        String sourceFilePath = Biz.getWorkspace() + PropHelper.getProperty("extract.agreement.source.path") + sourceAgrPath.getRealValue();
        StringType actualAgrPath = testDataManager.getVariable(actualAgreementPath);
        String actualFilePath = Biz.getWorkspace() + "/" + PropHelper.getProperty("path.target.scenario.data.folder") + actualAgrPath.getRealValue() + ".xml";
        testCase.embedTestData(sourceAgrPath);
        testCase.embedTestData(actualAgrPath);
        sourceFilePath = sourceFilePath.replace('\\', '/');
        actualFilePath = actualFilePath.replace('\\', '/');
        Biz.compareXmlFile(testCase.getSoftAssertions(), sourceFilePath, actualFilePath);
    }

    /**
     * Description: Check database query result <br/>
     * Start page: any pages <br/>
     * End page: any pages <br/>
     * Samples: database query return result should be <b>databaseQueryResult</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link DatabaseQueryResult}</li>
     * @throws Exception
     */
    @Then("^(?:Database - )?database query return result should be (\\S+)$")
    public void database_query_return_result_should_be(String id) throws Exception {
        DatabaseQueryResult databaseQueryResult = testDataManager.getDatabaseQueryResult(id);
        testCase.embedTestData(databaseQueryResult);
        pageManager.databaseOperationsPage.validateDatabaseQueryResult(databaseQueryResult);
    }

    /**
     * Description: Check internal review list summary <br/>
     * Start page: internal review list summary page <br/>
     * End page: internal review list summary page <br/>
     * Samples: internal review list summary should be <b>internalReviewSummary</b><br/>
     * @param ids can be one of following type:<br /><li>{@link InternalReviewSummary}</li>
     * @throws Exception
     */
    @Then("^internal review list summary should be (\\S+)$")
    public void internal_review_list_summary_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            InternalReviewSummary internalReviewSummary = testDataManager.getInternalReviewSummary(id);
            testCase.embedTestData(internalReviewSummary);
            pageManager.internalReviewSummaryPage.longView();
            pageManager.internalReviewSummaryPage.assertInternalReviewSummary(internalReviewSummary);
        }
    }

    /**
     * Description: Internal review list summary - go to tabLink <br/>
     * Start page: internal review list summary page <br/>
     * End page: internal review list summary page <br/>
     * Samples: Internal review list summary - go to tabLink <b>tab</b><br/>
     * @param tab can be one of following type:<br /><li>DELIVERIESANDRETURNS</li><li>CALLSANDRECALLS</li><li>NOCALL</li>
     * @throws Exception
     */
    @Then("^Internal review list summary - go to tabLink (DELIVERIESANDRETURNS|CALLSANDRECALLS|NOCALL)$")
    public void internal_review_list_summary_gototabLink(String tab) throws Exception {
        switch (tab.toUpperCase()){
            case "DELIVERIESANDRETURNS":
                pageManager.internalReviewSummaryPage.switchToDeliveriesAndReturnTab();
                break;
            case "CALLSANDRECALLS":
                pageManager.internalReviewSummaryPage.switchToCallAndRecallTab();
                break;
            case "NOCALL":
                pageManager.internalReviewSummaryPage.switchToNoCallsTab();
                break;
        }
    }

    /**
     * Description: Check that agreement is editable according to privileges assigned and check the values in the agreement<br/>
     * Start page: Statement page <br/>
     * End page: Agreement summary page <br/>
     * Samples: Check that agreement is editable according to privileges assigned and check the values in the agreement should be <b>my.agreementSummary</b><br/>
     * <p>
     * //     * @param id1 can be one of following type:<br /><li>{@link BookingDetail}</li>
     * //     * @throws Exception
     */
    @Then("^check fields in (?:OTC|FCM|ETF|Repo|Umbrella) agreement (\\S+) in (partyCounterpaty|Documentation|callSchedule|Products|Collateral|fixedTrigger|ruleTrigger|additionalFields|Settlement|reportingControl|Messaging) tab$")
    public void check_agreement(String id1, String tab) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementSummary a = testDataManager.getAgreementSummary(id1);
        testCase.embedTestData(a);
        switch (tab.toLowerCase()) {
            case "partycounterparty":
                pageManager.agreementSummaryPage.enterAgreementPartySummary();
                if (a.getPartyAndCounterpartyDetails().isApply() == null || a.getPartyAndCounterpartyDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getPartyAndCounterpartyDetails().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "documentation":
                pageManager.agreementSummaryPage.enterAgreementDocumentationSummary();
                if (a.getDocumentationDetails().isApply() == null || a.getDocumentationDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.assertAgreementDocumentationDetails(a);
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getDocumentationDetails().isApply())
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                break;

            case "callschedule":
                pageManager.agreementSummaryPage.enterAgreementcallScheduleSummary();
                if (a.getValuationFrequencyAndCallScheduleDetails().isApply() == null || a.getValuationFrequencyAndCallScheduleDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getValuationFrequencyAndCallScheduleDetails().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);

                }
                break;

            case "products":
                pageManager.agreementSummaryPage.enterAgreementProductSummary();
                if (a.getCollateralisedPortfolioDetails().isApply() == null || a.getCollateralisedPortfolioDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getCollateralisedPortfolioDetails().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }

                break;

            case "collateral":
                pageManager.agreementSummaryPage.enterAgreementCollateralSummary();
                if (a.getCollateralRules().isApply() == null || a.getCollateralRules().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getCollateralRules().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "fixedtrigger":
                pageManager.agreementSummaryPage.enterAgreementFixedTriggerSummary();
                if (a.getCollateralThresholdAndMinimumTransferDetails().isApply() == null || a.getCollateralThresholdAndMinimumTransferDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getCollateralThresholdAndMinimumTransferDetails().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "ruletrigger":
                pageManager.agreementSummaryPage.enterAgreementRuleTriggerSummary();
                if (a.getCollateralThresholdAndMinimumTransferDetails().isApply() == null || a.getCollateralThresholdAndMinimumTransferDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getCollateralThresholdAndMinimumTransferDetails().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "additionalfields":
                pageManager.agreementSummaryPage.enterAgreementAdditionalFieldsSummary();
                if (a.getUserDefinedFieldsDetails().isApply() == null || a.getUserDefinedFieldsDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                }else if (!a.getUserDefinedFieldsDetails().isApply()){
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "settlement":
                pageManager.agreementSummaryPage.enterAgreementSettlementSummary();
                if (a.getSettlementInformationConfiguration().isApply() == null || a.getSettlementInformationConfiguration().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                } else if (!a.getSettlementInformationConfiguration().isApply()) {
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);
                }
                break;

            case "reportingcontrol":
                pageManager.agreementSummaryPage.enterAgreementReportingControlSummary();
                if (a.getReportingDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                }
                break;

            case "messaging":
                pageManager.agreementSummaryPage.enterAgreementMessagingSummary();
                // pageManager.agreementSummaryPage.assertReportingMessagingSummary();
                if (a.getMessagingDetails().isApply() == null || a.getMessagingDetails().isApply()) {
                    pageManager.agreementSummaryPage.editAgreementSummary();
                    pageManager.agreementSummaryPage.saveAndExit();
                }  if (!a.getMessagingDetails().isApply()){
                    pageManager.agreementSummaryPage.checkAgreementEditbuttonIsDisabled(a);

                }
                break;

        }

    }
    @Then("^Error message in Call Schedule page (\\S+) should be displayed$")
    public void error_message_in_Agreement_should_be(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        pageManager.agreementSummaryPage.assertcallScheduleErrorMessage(agreement);
        testCase.embedTestData(agreement);
    }

    /**
     * Description: Confirms that calculation rule is saved and displayed in search results
     * Start page: Calculation rules search results page
     * End page: Calculation rules search results page
     * @param id The id of the {@link StatementCalcRule} to lookup and confirm is displayed
     * @throws Exception
     */
    @Then("^calculation rule (\\S+) is displayed in list of rules$")
    public void calculationRuleIsDisplayedInListOfRules(String id) throws Exception {
        StatementCalcRule statementCalcRule = testDataManager.getStatementCalcRule(id);
        testCase.embedTestData(statementCalcRule);
        pageManager.calculationRuleMaintenancePage.assertSavedRulePresentOnFirstLine(statementCalcRule);
    }

    /**
     * Description: Confirms that calculation rule is not displayed in the search results
     * Start page: Calculation rules search results page
     * End page: Calculation rules search results page
     * @param id The id of the {@link StatementCalcRule} to lookup and confirm it is not displayed
     * @throws Exception
     */
    @Then("^calculation rule (\\S+) is not displayed in list of rules$")
    public void calculationRuleIsNotDisplayedInListOfRules(String id) throws Exception {
        StatementCalcRule statementCalcRule = testDataManager.getStatementCalcRule(id);
        testCase.embedTestData(statementCalcRule);
        pageManager.calculationRuleMaintenancePage.assertSavedRuleNotPresentOnFirstLine(statementCalcRule);
    }

    /**
     * Description: Asserting the drools calculation has not altered fx rate calculation on the agreement statement
     * Start page: Agreement Statement Page
     * End page: Agreement Statement Page
     * @param fxRates List of FX rates to be applied for each model
     * @param etdAgrmtStatementId Holds the pending excess deficit value for each model
     * @throws Exception
     */
    @Then("^fx rates (\\S+) should be applied correctly in agreement for each (\\S+)$")
    public void theFxRatesShouldBeAppliedCorrectlyForTheCategory(List<String> fxRates, String etdAgrmtStatementId) throws Exception{

        EtdAgreementStatement etdAgreementStatement = testDataManager.getEtdAgreementStatement(etdAgrmtStatementId);
        EtdAssetHoldingSummary etdAssetHoldingSummary = etdAgreementStatement.getEtdAssetHoldingSummary();
        testCase.embedTestData(etdAssetHoldingSummary);
        double totalEdValue = 0;
        double newEdValue;
        String modelCategoryName = null;

        for (String fxRateId : fxRates) {
            FeedFxRate feedFxRate = testDataManager.getFeedFxRate(fxRateId);
            testCase.embedTestData(feedFxRate);
            for (int i = 0; i < etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary().size(); i++) {
                StringType modelName = etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary().get(i).getModel();
                modelCategoryName = AgreementUtils.getDisplayedCategoryName(
                        etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary().get(i).getModelCategory(),
                        etdAssetHoldingSummary.getAgreementBaseCurrency());
                StringType edValue = etdAssetHoldingSummary.getEtdModelAndModelCategoryAssetHoldingSummary().get(i).getEtdAssetHoldingSummaryDetail().getPendingExcessOrDeficit();

                if (modelName.getRealValue().equals(feedFxRate.getCurrency().getValue())) {
                    newEdValue = Double.parseDouble(edValue.getRealValue()) * (1 / Double.parseDouble(feedFxRate.getFxRate().getValue()));
                    totalEdValue = totalEdValue + newEdValue;
                }
            }
        }
        pageManager.agreementStatementPage.assertCategoryEdValue(totalEdValue, modelCategoryName);
    }

    /**
     * Description: verify EM column<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - column <b>my.EM.column</b> should be <b>added</b> in select columns dialog<br/>
     * @param action can be one of following type:<br /><li>added</li><li>removed</li>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @Then("^Exposure Management - column (\\S+) should be (added in left panel|removed in left panel|not displayed in left and right panel) in select columns dialog$")
    public void exposure_Management_add_delete_order_column(List<String> ids, String action) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            switch (action.toLowerCase()) {
                case "added in left panel":
                    pageManager.exposureManagementPage.assertColumnInSelectColumnsDialog(exposureEventDetail, true,true);
                    break;
                case "removed in left panel":
                    pageManager.exposureManagementPage.assertColumnInSelectColumnsDialog(exposureEventDetail,false,false);
                    break;
                case "not displayed in left and right panel":
                    pageManager.exposureManagementPage.assertColumnInSelectColumnsDialog(exposureEventDetail,false,true);
                    break;
            }
        }
    }

    @Then("^party Counterparty tab should be populated like agreement (\\S+)$")
    public void assertPartyCounterpartyTab(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        pageManager.agreementPartyCounterpartySetupPage.assertPartyCounterparty(agreement);
    }

    /**
     * Description: Check External Exposure List <br/>
     * Start page: External Exposure List page <br/>
     * End page: External Exposure List page <br/>
     * Samples: External Exposure List should be <b>externalExposureList</b><br/>
     * @param ids can be one of following type:<br /><li>{@link ExternalExposureList}</li>
     * @throws Exception
     */
    @Then("^Self Service - Client Data - External Exposure List should be (\\S+)$")
    public void  SelfService_ClientData_ExternalExposureList_should_be(List<String> ids) throws Exception {
        for (String id : ids) {
            ExternalExposureList externalExposureList = testDataManager.getExternalExposureList(id);
            testCase.embedTestData(externalExposureList);
            pageManager.externalExposureListPage.gotoTablink(externalExposureList);
            pageManager.externalExposureListPage.assertExternalExposureList(externalExposureList);
        }
    }
}
