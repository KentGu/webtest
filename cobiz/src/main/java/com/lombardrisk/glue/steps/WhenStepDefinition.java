package com.lombardrisk.glue.steps;

import com.lombardrisk.data.ExposureManagementMenu;
import com.lombardrisk.data.Menu;
import com.lombardrisk.data.OptimisationMenu;
import com.lombardrisk.data.ReportMenu;
import com.lombardrisk.pages.PageManager;
import com.lombardrisk.pojo.*;
import com.lombardrisk.test.TestDataManager;
import com.lombardrisk.util.Biz;
import cucumber.api.PendingException;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kenny Wang on 2/4/2016.
 */

public class WhenStepDefinition {
    private final static Logger logger = LoggerFactory.getLogger(WhenStepDefinition.class);
    private ITestBase testCase = TestCaseManager.getTestCase();
    private TestDataManager testDataManager = (TestDataManager) testCase.getTestDataManager();
    private PageManager pageManager = (PageManager) testCase.getPageManager();


    /**
     * Description: go to organisations - organisation details - delete orgnisation<br/>
     * Start page: anyPage <br/>
     * End page:delete organisations <br/>
     * @param ids can be one of following type:<br /><li>{@link Organisation}</li><li>{@link OrganisationSearch}</li><li>{@link OrganisationSearchResult}</li>
     * @throws Exception
     */
    @When("^delete organisations? (\\S+)$")
    public void delete_organisation(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.DELETE_ORGANISATION);
            OrganisationSearch organisationSearch = testDataManager.getOrganisationSearch(id);
            testCase.embedTestData(organisationSearch);
            if(organisationSearch == null){
                Organisation organisation = testDataManager.getOrganisation(id);
                if(organisation != null) {
                    organisationSearch = organisation.toOrganisationSearch();
                }else{
                    organisationSearch = testDataManager.getOrganisationSearchResult(id).toOrganisationSearch();
                }
            }
            pageManager.deleteOrganisationPage.deleteOrganisation(organisationSearch);
        }
    }

    /**
     * Description: click view satement TAB when setup agreement <br/>
     * Start page: agreementSummaryPage  <br/>
     * End page: agreementStatementPage<br/>
     * @throws Exception
     */
    @When("^view statement$")
    public void view_statement() throws Exception {
        pageManager.agreementSummaryPage.viewStatement();
    }

    /**
     * Description: Go to agreement statement page by URL <br/>
     * Start page: any Page  <br/>
     * End page: agreemtStatementPage<br/>
     * @param id can be one of the following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^Go to agreement (\\S+) statement page by URL$")
    public void view_agreement_statement_by_url(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        testCase.embedTestData(agreement);
        if (agreement.getAgreementId() != null)
            pageManager.welcomePage.gotoAgreementStatementPageByUrl(agreement.getAgreementId().getRealValue());
    }

    /**
     * Description: Refresh the page <br/>
     * Start page: any Page  <br/>
     * End page: any Page<br/>
     */
    @When("^Refresh the page$")
    public void Refresh_the_page() throws Exception {
        pageManager.welcomePage.RefreshPage();
    }

    /**
     * Description: Go to agreement summary page by URL <br/>
     * Start page: any Page  <br/>
     * End page: agreementSummary<br/>
     * @param id can be one of the following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^Go to agreement (\\S+) summary page by URL$")
    public void view_agreement_summary_by_url(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        testCase.embedTestData(agreement);
        if (agreement.getAgreementId() != null) {
            pageManager.welcomePage.gotoAgreementSummaryPageByUrl(agreement.getAgreementId().getRealValue());
            pageManager.agreementSummaryPage.switchToAgreementSummaryTab();
        }
    }

    /**
     * Description: go to organisation - add organisation<br/>
     * Start page: anyPage <br/>
     * End page: addOrganisationPage<br/>
     * @param ids can be one of following type:<br /><li>{@link Organisation}</li>
     * @throws Exception
     */
    @When("^add organisations? (\\S+)$")
    public void add_organisations(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.ADD_ORGANISATION);
            pageManager.addOrganisationPage.switchToSearchFrame();
            pageManager.addOrganisationPage.switchToDefaultFrame();
            Organisation organisation = testDataManager.getOrganisation(id);
            testCase.embedTestData(organisation);
            pageManager.addOrganisationPage.addOrganisation(organisation);
            pageManager.viewOrganisationsPage.editOrganisation(organisation);
        }
    }

    /**
     * Description: select orgnisation and modify it<br/>
     * Start page: viewOrgnisationPage <br/>
     * End page: viewOrgnisationPage<br/>
     * @param id can be one of following type:<br /><li>{@link Organisation}</li>
     * @throws Exception
     */
    @When("^edit organisation to (\\S+)$")
    public void edit_organisation_to(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Organisation organisation = testDataManager.getOrganisation(id);
        testCase.embedTestData(organisation);
        pageManager.viewOrganisationsPage.editOrganisation(organisation);
    }

    /**
     * Description: search orgnisations<br/>
     * Start page: viewOrgnisationPage <br/>
     * End page: viewOrgnisationPage<br/>
     * @param ids can be one of following type:<br /><li>{@link OrganisationSearch}</li>
     * @throws Exception
     */
    @When("^search organisations? (\\S+)$")
    public void search_organisations(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            OrganisationSearch organisationSearch = testDataManager.getOrganisationSearch(id);
            testCase.embedTestData(organisationSearch);
            pageManager.viewOrganisationsPage.searchOrganisationAndView(organisationSearch);
        }
    }

    /*
    The feature included in the search organisation DSL
    @When("^view organisation (\\S+)$")
    public void view_organisation(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationSearchResult organisationSearchResult = testDataManager.getOrganisationSearchResult(id);
        testCase.embedTestData(organisationSearchResult);
        throw new PendingException();
    }
    */

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param ids can be one of following type:<br /><li>{@link AgencyRating}</li>
     * @throws Exception
     */
    @When("^add rating levels? (\\S+)$")
    public void add_rating_levels(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            AgencyRating agencyRating = testDataManager.getAgencyRating(id);
            testCase.embedTestData(agencyRating);
            pageManager.ratingLevelPage.addRatingLevel(agencyRating);
        }
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param ids can be one of following type:<br /><li>{@link AgencyRating}</li>
     * @throws Exception
     */
    @When("^delete organisation rating levels? (\\S+)$")
    public void delete_organisation_rating_level(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            AgencyRating agencyRating = testDataManager.getAgencyRating(id);
            //TODO need a function that delete the rating level in RatingLevelPage

            testCase.embedTestData(agencyRating);
        }
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param ids can be one of following type:<br /><li>{@link AgencyRating}</li>
     * @throws Exception
     */
    @When("^edit organisation rating levels? (\\S+)$")
    public void edit_organisation_rating_level(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            AgencyRating agencyRating = testDataManager.getAgencyRating(id);
            testCase.embedTestData(agencyRating);
            pageManager.ratingLevelPage.editRatingLevel(agencyRating);
        }
    }

    /**
     * Description: navigate to orgnisation - orgnisation admin - rating level page<br/>
     * Start page: anyPage <br/>
     * End page: ratingLevelPage<br/>
     * @throws Exception
     */
    @When("^navigate to rating level page$")
    public void navigate_to_rating_level_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.RATINGLEVEL);
    }

    /**
     * Description: add orgnisation books<br/>
     * Start page: edit book hierachy page <br/>
     * End page: edit book hierachy page<br/>
     * @param ids can be one of following type:<br /><li>{@link OrganisationBook}</li>
     * @throws Exception
     */
    @When("^add organisation books? (\\S+)$")
    public void add_organisation_books(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            OrganisationBook organisationBook = testDataManager.getOrganisationBook(id);
            testCase.embedTestData(organisationBook);
            pageManager.booksPage.addBooks(organisationBook);
        }
    }

    /**
     * Description: navigate to orgnisations - orgnisation admin - books <br/>
     * Start page: anyPage <br/>
     * End page: edit book hierachy page<br/>
     * @throws Exception
     */
    @When("^navigate to organisation books page$")
    public void navigate_to_organisation_books_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.BOOKS);
    }

    /**
     * Description: edit orgnisation books<br/>
     * Start page: edit book hierachy page <br/>
     * End page: edit book hierachy page<br/>
     * @param oriId can be one of following type:<br /><li>{@link OrganisationBook}</li>
     * @param newId can be one of following type:<br /><li>{@link OrganisationBook}</li>
     * @throws Exception
     */
    @When("^edit organisation book (\\S+) to (\\S+)$")
    public void edit_organisation_book(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationBook oriOrganisationBook = testDataManager.getOrganisationBook(oriId);
        OrganisationBook newOrganisationBook = testDataManager.getOrganisationBook(newId);
        //TODO need a function that open an organisation book record in BooksPage
        //TODO need a function that edit organisation book in BooksPage

        testCase.embedTestData(oriOrganisationBook);
        testCase.embedTestData(newOrganisationBook);
        throw new PendingException();
    }

    /**
     * Description: filter books by status<br/>
     * Start page: edit book hierachy page <br/>
     * End page: edit book hierachy page<br/>
     * @param view can be one of following value:<br /><li>Approved</li><li>Amended</li>
     * @throws Exception
     */
    @When("^filter organisation books by status (Approved|Amended)$")
    public void filter_organisation_books_by_status(String view) {
        // Write code here that turns the phrase above into concrete actions
        switch (view.toLowerCase()) {
            case "approved":
                //TODO need a function that change the filter by status in BooksPage
                break;
            case "amended":
                break;
        }
        throw new PendingException();
    }

    @When("^delete organisation book (\\S+)$")
    public void delete_organisation_book(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationBook organisationBook = testDataManager.getOrganisationBook(id);
        //TODO the deleteBook() function need a parameter which indicate the book that should be deleted in BooksPage
        testCase.embedTestData(organisationBook);
        throw new PendingException();
    }

    /**
     * Description: navigate to organisations - admin organisation - industry<br/>
     * Start page: anyPage <br/>
     * End page: edit industry hierachy<br/>
     * @throws Exception
     */
    @When("^navigate to organisation industry page$")
    public void navigate_to_organisation_industry_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.INDUSTRY);
    }

    /**
     * Description: add industry<br/>
     * Start page: edit industry hierachy <br/>
     * End page: edit industry hierachy<br/>
     * @param ids can be one of following type:<br /><li>{@link OrganisationIndustry}</li>
     * @throws Exception
     */
    @When("^add industr(?:y|ies) (\\S+)$")
    public void add_industries(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            OrganisationIndustry organisationIndustry = testDataManager.getOrganisationIndustry(id);
            testCase.embedTestData(organisationIndustry);
            pageManager.industryPage.addIndustry(organisationIndustry);
        }
    }

    /**
     * Description: edit industry<br/>
     * Start page: edit industry hierachy <br/>
     * End page: edit industry hierachy<br/>
     * @param oriIndustryId can be one of following type:<br /><li>{@link OrganisationIndustry}</li>
     * @param newIndustryId can be one of following type:<br /><li>{@link OrganisationIndustry}</li>
     * @throws Exception
     */
    @When("^edit industry (\\S+) to (\\S+)$")
    public void edit_industry(String oriIndustryId, String newIndustryId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationIndustry oriOrganisationIndustry = testDataManager.getOrganisationIndustry(oriIndustryId);
        OrganisationIndustry newOrganisationIndustry = testDataManager.getOrganisationIndustry(newIndustryId);

        //TODO need a function that open a industry record in IndustryPage
        //TODO need a function that edit a industry record in IndustryPage

        testCase.embedTestData(oriOrganisationIndustry);
        testCase.embedTestData(newOrganisationIndustry);
        throw new PendingException();
    }


    /**
     * Description: filter industry by status<br/>
     * Start page: edit industry hierachy <br/>
     * End page: edit industry hierachy<br/>
     * @param view can be one of following value:<br /><li>disabled</li><li>enabled</li>
     * @throws Exception
     */
    @When("^filter industry by status (disabled|enabled)$")
    public void filter_industry_by_status(String view) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that change the filter by status in IndustryPage
        switch(view.toLowerCase()){
            case "disabled" :
                break;
            case "enabled" :
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: set organisation global prefernces <br/>
     * Start page: anyPage <br/>
     * End page: global setting preferences<br/>
     * @param id can be one of following type:<br /><li>{@link OrganisationGlobalPreference}</li>
     * @throws Exception
     */
    @When("^set organisation global preferences (\\S+)$")
    public void set_organisation_global_preferences(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationGlobalPreference organisationGlobalPreference = testDataManager.getOrganisationGlobalPreference(id);
        testCase.embedTestData(organisationGlobalPreference);
        pageManager.globalPreferencesPage.editOrganisationGlobalSettingPreferences(organisationGlobalPreference);
    }

    /**
     * Description: feed organisations<br/>
     * Start page: anyPage <br/>
     * End page: feed organisation page<br/>
     * @param ids feed file location<br />
     * @throws Exception
     */
    @When("^feed organisations? (\\S+) by (xml|excel|xlsx)$")
    public void feed_organisation(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ORGANISATIONS);
        List<FeedOrganisation> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedOrganisation(id));
            testCase.embedTestData(testDataManager.getFeedOrganisation(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.feedOrganisationsPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.feedOrganisationsPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.feedOrganisationsPage.feedXlsxFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed organisation contact<br/>
     * Start page: anyPage <br/>
     * End page: feed organisation page<br/>
     * @param ids feed file location<br />
     * @throws Exception
     */
    @When("^feed organisation contact (\\S+) by (xml|excel|xlsx)$")
    public void feed_organisation_contact(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ORGANISATIONS);
        List<FeedOrganisationContact> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedOrganisationContact(id));
            testCase.embedTestData(testDataManager.getFeedOrganisationContact(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.feedOrganisationsPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.feedOrganisationsPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.feedOrganisationsPage.feedXlsxFile(feed, list);
                break;
        }
    }

    /**
     * Description: navigate to organisation static page<br/>
     * Start page: anyPage <br/>
     * End page: organisation static data page<br/>
     * @throws Exception
     */
    @When("^navigate to organisation static data page$")
    public void navigate_to_organisation_static_data_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.ORGANISATIONS_STATIC_DATA);
    }

    /**
     * Description: add organisation static data<br/>
     * Start page: organisation static data page <br/>
     * End page: organisation static data page<br/>
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^add organisation static data (\\S+)$")
    public void add_organisation_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            StaticData staticData = testDataManager.getOrganisationStaticData(id);
            testCase.embedTestData(staticData);
            pageManager.organisationStaticDataPage.addStaticData(staticData);
        }
    }

    /**
     * Description: edit organisation static data<br/>
     * Start page: organisation static data page <br/>
     * End page: organisation static data page<br/>
     * @param oriId can be one of following type:<br /><li>{@linkplain StaticData}</li>
     * @param newId can be one of following type:<br /><li>{@linkplain StaticData}</li>
     * @throws Exception
     */
    @When("^edit organisation static data (\\S+) to (\\S+)$")
    public void edit_organisation_static_data(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StaticData oriStaticData = testDataManager.getOrganisationStaticData(oriId);
        StaticData newStaticData = testDataManager.getOrganisationStaticData(newId);
        testCase.embedTestData(oriStaticData);
        testCase.embedTestData(newStaticData);
        //TODO need a function that open specific static data record in OrganisationStaticDataPage. (Same function might also need to be added to other static data page)
        pageManager.organisationStaticDataPage.openStaticDataType(oriStaticData);
        pageManager.organisationStaticDataPage.openStaticDataRecord(oriStaticData);
        pageManager.organisationStaticDataPage.editStaticData(newStaticData);
    }

    /**
     * Description: navigate to view organisation page<br/>
     * Start page: anyPage <br/>
     * End page: viewOrganisationPage<br/>
     * @throws Exception
     */
    @When("^navigate to view organisation$")
    public void navigate_to_view_organisation() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.VIEW_ORGANISATIONS);
    }

    /**
     * Description: select view our organisation link<br/>
     * Start page: viewOrganisationPage <br/>
     * End page: viewOrganisationPage<br/>
     * @throws Exception
     */
    @When("^view our organisation$")
    public void view_our_organisation() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.viewOrganisationsPage.enterViewOurOrganisationPage();
    }

    /**
     * Description: expand or collapse organisation tree<br/>
     * Start page: viewOrganisationPage <br/>
     * End page: viewOrganisationPage<br/>
     * @param action can be one of following value:<br /><li>expand</li><li>collapse</li>
     * @param ids can be one of following type:<br /><li>{@link Organisation}</li><li>{@link OrganisationSearchResult}</li><li>{@link OrganisationSearch}</li>
     * @throws Exception
     */
    @When("^(expand|collapse) organisations? (\\S+) tree$")
    public void expand_or_collapse_organisation_tree(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<StringType> orgs = new ArrayList<>();
        for (int i = 0; i < ids.size() ; i++) {
            OrganisationSearchResult organisationSearchResult = testDataManager.getOrganisationSearchResult(ids.get(i));
            if(organisationSearchResult == null){
                Organisation organisation = testDataManager.getOrganisation(ids.get(i));
                if(organisation != null) {
                    organisationSearchResult = organisation.toOrganisationSearchResult();
                }else{
                    organisationSearchResult = testDataManager.getOrganisationSearch(ids.get(i)).toOrganisationSearchResult();
                }
            }
            orgs.add(i, organisationSearchResult.getShortName());
            testCase.embedTestData(organisationSearchResult);
        }

        switch (action.toLowerCase()) {
            case "expand":
                pageManager.viewOurOrganisationsPage.expandOrgTree(orgs);
                break;
            case "collapse":
                pageManager.viewOurOrganisationsPage.collapseOrgTree(orgs);
                break;
        }
    }

    /**
     * Description: add FX rate sets<br/>
     * Start page: FX rate page <br/>
     * End page: FX rate page<br/>
     * @param ids can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @throws Exception
     */
    @When("^add FX rate sets? (\\S+)$")
    public void add_fx_rate_sets(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            FxRateSet fxRateSet = testDataManager.getFxRateSet(id);
            testCase.embedTestData(fxRateSet);
            pageManager.fxRatesPage.addNewRateSet();
            pageManager.addNewRateSetPage.editNewRateSet(fxRateSet);
            pageManager.addNewRateSetPage.saveNewRateSet();
            pageManager.fxRatesPage.setNewFxRateSet(fxRateSet);
            pageManager.fxRatesPage.save();
        }
    }

    /**
     * Description: edit FX rate set<br/>
     * Start page: FX rate page <br/>
     * End page: FX rate page<br/>
     * @param oriId can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @param newId can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @throws Exception
     */
    @When("^edit FX rate set (\\S+) to (\\S+)$")
    public void edit_fx_rate_set(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        FxRateSet oriFxRateSet = testDataManager.getFxRateSet(oriId);
        FxRateSet newFxRateSet = testDataManager.getFxRateSet(newId);
        testCase.embedTestData(oriFxRateSet);
        testCase.embedTestData(newFxRateSet);

        List<FxRate> oriFxRates = oriFxRateSet.getFxRate();

        pageManager.fxRatesPage.editFxRateSetInfo(newFxRateSet);

        for(FxRate fxRate: newFxRateSet.getFxRate()){
            if(fxRate.getName() != null){
                FxRate oriFxRate = (FxRate) Biz.matchedObjectWithName(fxRate, oriFxRates);
                pageManager.fxRatesPage.editFxRate(oriFxRate, fxRate);
            }else{
                pageManager.fxRatesPage.addFxRate(fxRate);
            }
        }
        pageManager.fxRatesPage.save();
    }

    /**
     * Description: delete FX rate sets<br/>
     * Start page: FX rate page <br/>
     * End page: FX rate page<br/>
     * @param ids can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @throws Exception
     */
    @When("^delete FX rate sets? (\\S+)$")
    public void delete_fx_rate_sets(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            FxRateSet fxRateSet = testDataManager.getFxRateSet(id);
            testCase.embedTestData(fxRateSet);
            pageManager.fxRatesPage.openFxRateSet(fxRateSet);
            pageManager.fxRatesPage.deleteRateSet(fxRateSet);
        }
    }

    /**
     * Description: navigate to FX rate page<br/>
     * Start page: anyPage <br/>
     * End page: FX rate page<br/>
     * @throws Exception
     */
    @When("^navigate to FX rate page$")
    public void navigate_to_fx_rate_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FX_RATES);
    }

    /*
    DSL removed
    @When("^(add|edit) fx rates? (\\S+)$")
    public void add_edit_fx_rates(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids) {
            FxRateSet fxRateSet = testDataManager.getFxRateSet(id);
            for (FxRate fxRate : fxRateSet.getFxRate()) {
                switch(action.toLowerCase()) {
                    case "edit" :
                        pageManager.fxRatesPage.editFxRate(fxRate);
                        break;
                    case "add" :
                        pageManager.fxRatesPage.addFxRates(Arrays.asList(fxRate));
                        break;
                }
                testCase.embedTestData(fxRate);
            }
            testCase.embedTestData(fxRateSet);
        }
    }
    */


    /**
     * Description: add security<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param ids can be one of following type:<br /><li>{@link Instrument}</li>
     * @throws Exception
     */
    @When("^add securit(?:y|ies) (\\S+)$")
    public void add_securities(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            Instrument instrument = testDataManager.getInstrument(id);
            testCase.embedTestData(instrument);
            pageManager.securitySearchPage.newInstrument(instrument);
            pageManager.securityEditorPage.inputInstrument(instrument);
            pageManager.securityEditorPage.saveInstrument();
        }
    }

    /**
     * Description: edit security<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param searchId can be one of following type:<br /><li>{@link SecuritySearchResult}</li><li>{@link Instrument}</li><li>{@link SecuritySearch}</li>
     * @param id can be one of following type:<br /><li>{@link Instrument}</li>
     * @throws Exception
     */
    @When("^edit security (\\S+) to (\\S+)$")
    public void edit_security(String searchId, String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        SecuritySearchResult securitySearchResult = testDataManager.getSecuritySearchResult(searchId);
        testCase.embedTestData(securitySearchResult);
        if(securitySearchResult == null){
            Instrument instrument = testDataManager.getInstrument(id);
            if(instrument != null) {
                securitySearchResult = instrument.toSecuritySearchResult();
            }else{
                securitySearchResult = testDataManager.getSecuritySearch(id).toSecuritySearchResult();
            }
        }
        Instrument instrument = testDataManager.getInstrument(id);
        testCase.embedTestData(instrument);
        pageManager.securitySearchPage.editInstrument(securitySearchResult);
        pageManager.securityEditorPage.inputInstrument(instrument);
        pageManager.securityEditorPage.saveInstrument();

        if(instrument.getSecurityStatus() != null && instrument.getSecurityStatus().getRealValue().equalsIgnoreCase("Approved")){
            pageManager.securitySearchPage.editInstrument(securitySearchResult);
            pageManager.securityEditorPage.approveInstrunment();
        }
    }

    /**
     * Description: approve security<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param ids can be one of following type:<br /><li>{@link SecuritySearchResult}</li><li>{@link Instrument}</li><li>{@link SecuritySearch}</li>
     * @throws Exception
     */
    @When("^approve securit(?:y|ies) (\\S+)$")
    public void approve_securities(List<String> ids) throws Exception{
        for(String id : ids) {
            SecuritySearchResult securitySearchResult = testDataManager.getSecuritySearchResult(id);
            if(securitySearchResult == null){
                Instrument instrument = testDataManager.getInstrument(id);
                if(instrument != null) {
                    securitySearchResult = instrument.toSecuritySearchResult();
                }else{
                    securitySearchResult = testDataManager.getSecuritySearch(id).toSecuritySearchResult();
                }
            }
            testCase.embedTestData(securitySearchResult);
            pageManager.securitySearchPage.editInstrument(securitySearchResult);
            pageManager.securityEditorPage.approveInstrunment();

        }
    }

    /**
     * Description: cancel security<br/>
     * Start page: secirty editor page <br/>
     * End page: security search page<br/>
     * @throws Exception
     */
    @When("^(?:Instrument Editor - )?cancel instrument$")
    public void cancel_instrument() throws Exception{
        pageManager.securityEditorPage.cancelInstrument();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param oriId can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("edit result (\\S+) to (\\S+)")
    public void edit_result(String oriId, String newId) {
        throw new PendingException();
    }

    /**
     * Description: click inuse button in security search result<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param ids can be one of following type:<br /><li>{@link SecuritySearchResult}</li><li>{@link Instrument}</li><li>{@link SecuritySearch}</li>
     * @throws Exception
     */
    @When("^inuse securit(?:y|ies) (\\S+)$")
    public void inuse_securities(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            SecuritySearchResult securitySearchResult = testDataManager.getSecuritySearchResult(id);
            if(securitySearchResult == null){
                Instrument instrument = testDataManager.getInstrument(id);
                if(instrument != null) {
                    securitySearchResult = instrument.toSecuritySearchResult();
                }else{
                    securitySearchResult = testDataManager.getSecuritySearch(id).toSecuritySearchResult();
                }
            }
            //TODO need a function that click the inuse button in the SecuritySearchPage
            testCase.embedTestData(securitySearchResult);
        }
        throw new PendingException();
    }

    /**
     * Description: search securities<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param id can be one of following type:<br /><li>{@link SecuritySearchResult}</li><li>{@link Instrument}</li><li>{@link SecuritySearch}</li>
     * @throws Exception
     */
    @When("^search security (\\S+)$")
    public void search_security(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        SecuritySearch securitySearch = testDataManager.getSecuritySearch(id);
        if(securitySearch == null){
            Instrument instrument = testDataManager.getInstrument(id);
            if(instrument != null) {
                securitySearch = instrument.toSecuritySearch();
            }else{
                securitySearch = testDataManager.getSecuritySearchResult(id).toSecuritySearch();
            }
        }
        testCase.embedTestData(securitySearch);
        pageManager.securitySearchPage.searchInstrument(securitySearch);

    }

    /**
     * Description: click long view|view summary button in security search result<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param view can be one of following value:<br /><li>long view</li><li>view summary</li>
     * @throws Exception
     */
    @When("^security search result (long view|view summary)$")
    public void view_security_search_result_in_view(String view) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        switch (view.toLowerCase()) {
            case "long view":
                pageManager.securitySearchPage.longView();
                break;
            case "view summary":
                //TODO need a function that click view summary button in SecuritySearchPage
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: sort security search result<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param columnName can be one of following value:<br /><li>asset class</li><li>asset type</li><li>market</li><li>instrument</li><li>instrument type</li><li>currency</li><li>price</li><li>price source</li><li>price level</li><li>price date</li><li>last price change</li>
     * @throws Exception
     */
    @When("^sort security search result by (asset class|asset type|market|instrument|instrument type|currency|price|price source|price level|price date|last price change)$")
    public void sort_security_search_result_by_column(String columnName) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that sorting the search result which has an argument that indicate the column Name in the SecuritySearchPage
        switch(columnName.toLowerCase()){
            default :
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: switch TAB in inventory manager page<br/>
     * Start page: inventoryManagerPage <br/>
     * End page: inventoryManagerPage<br/>
     * @param manager can be one of following value:<br /><li>asset</li><li>inventory</li><li>dynamic</li>
     * @param type can be one of following value:<br /> <li>positons</li><li>bookings</li>
     * @throws Exception
     */
    @When("^Inventory manager - navigate to (asset|inventory|dynamic) manager in (positons|bookings)$")
    public void inventory_manager_navigate_to_manager_in(String manager, String type) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param action can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Inventory manager - (add|delete|order) column (\\S+)$")
    public void inventory_manager_column(String action) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param action can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Inventory manager - (add|delete|order) T-column (\\S+)$")
    public void inventory_manager_t_column(String action) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param id1 can be one of following type:<br /><li></li>
     * @param id2 can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Inventory manager - show (\\S+) detail (\\S+)$")
    public void inventory_manager_show_detail(String id1, String id2) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param action can be one of following type:<br /><li></li>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Inventory manager - (expand|collapse) events (\\S+)$")
    public void inventory_manager_event(String action, String id) {
        throw new PendingException();
    }

    /**
     * Description: navigate to security search page<br/>
     * Start page: anyPage <br/>
     * End page: security search page<br/>
     * @throws Exception
     */
    @When("^navigate to security search page$")
    public void navigate_to_security_search_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.SECURITIES_DATA);
    }

    /**
     * Description: navigate to inventory manager search page<br/>
     * Start page: anyPage <br/>
     * End page: inventoryManagerPage<br/>
     * @throws Exception
     */
    @When("^navigate to Inventory Manager search page$")
    public void navigate_to_inventory_manager_search_page() throws Exception {
        // Write code here that turns the phrase above into concrete action
        pageManager.welcomePage.navigate(Menu.INVENTORY_MANAGER);
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^set (?:asset|inventory|dynamic) manager position filter (\\S+)$")
    public void set_asset_inventory_dynamic_manager_position_filter(String id) {
        // Write code here that turns the phrase above into concrete actions

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^set (?:asset|inventory|dynamic) manager booking filter (\\S+)$")
    public void set_manager_booking_filter(String id) {
        throw new PendingException();
    }

    /**
     * Description: export security search result<br/>
     * Start page: security search page <br/>
     * End page: security search page<br/>
     * @param format can be one of following value:<br /><li>CSV</li><li>Excel</li><li>XML</li><li>PDF</li>
     * @param id file location <br />
     * @throws Exception
     */
    @When("^export securities search result in (CSV|Excel|XML|PDF) format (\\S+)$")
    public void export_securities_search_result_in_format(String format, String id) throws Exception{
        StringType stringType = testDataManager.getVariable(id);
        testCase.embedTestData(stringType);
        switch(format.toLowerCase()) {
            case "csv" :
                pageManager.securitySearchPage.exportCsv(stringType);
                break;
            case "excel" :
                pageManager.securitySearchPage.exportExcel(stringType);
                break;
            case "xml" :
                pageManager.securitySearchPage.exportXml(stringType);
                break;
            case "pdf" :
                pageManager.securitySearchPage.exportPdf(stringType);
                break;
        }
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param type can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^export (?:asset|inventory|dynamic) manager (position|booking) result in (CSV|Excel) format$")
    public void export_asset_inventory_dynamic_manager_position_result_in_format(String type, String format) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add a InventoryManagerPage class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^add inventory manager position user defined filter (\\S+)$")
    public void add_inventory_manager_position_user_defined_filter(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^add inventory manager booking user defined filter (\\S+)$")
    public void add_inventory_manager_booking_user_defined_filter(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }



    /**
     * Description: create agreements<br/>
     * Start page: anyPage <br/>
     * End page: agreementSummaryPage<br/>
     * @param agreementType can be one of following type:<br /><li>OTC</li><li>FCM</li><li>ETF</li><li>Repo</li><li>Umbrella</li>
     * @param ids can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^add (OTC|FCM|ETF|Repo|ETD|Umbrella) agreements? (\\S+)$")
    public void add_agreements(String agreementType, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.AGREEMENT_SETUP);
            Agreement a = testDataManager.getAgreement(id);
            testCase.embedTestData(a);
            pageManager.agreementPartyCounterpartySetupPage.setupAgreementPartyCounterparty(a);
            pageManager.agreementDocumentationSetupPage.setupAgreementDocumentation(a);
            pageManager.agreementCallScheduleSetupPage.setupAgreementCallSchedule(a);
            if ((a.isUmbrellaAgreement() == null || !a.isUmbrellaAgreement()) &&
                    !Boolean.TRUE.equals(a.isFamilyAgreement())){
                if (a.getBusinessLine() != null && a.getBusinessLine().value().equals("ETD"))
                    pageManager.agreementModelSetupPage.setupAgreementModelCategory(a);
                else
                    pageManager.agreementProductsSetupPage.setupAgreementProducts(a);
            }

            if (a.isMultiModel() != null && a.isMultiModel()) {
                pageManager.agreementModelSetupPage.setupAgreementModel(a);
            }

            if(Boolean.TRUE.equals(a.isFamilyAgreement())) {
                pageManager.agreementFamilySetupPage.enterNextStage();
            } else {
                pageManager.agreementCollateralSetupPage.setupAgreementCollateral(a);
                pageManager.agreementFixedTriggerSetupPage.setupAgreementFixedTrigger(a);
                pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
            }



//            if (a.getPrincipalFixedTrigger() != null ||
//                    a.getCounterpartyFixedTrigger() != null) {
//                if (a.getPrincipalFixedTrigger().getFixedValues() != null ||
//                        a.getCounterpartyFixedTrigger().getFixedValues() != null) {
//                    pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
//                }
//            } else if (a.getVmPrincipalFixedTrigger() != null ||
//                    a.getVmCounterpartyFixedTrigger() != null ||
//                    a.getImPrincipalFixedTrigger() != null ||
//                    a.getImCounterpartyFixedTrigger() != null) {
//
//                if (a.getVmPrincipalFixedTrigger().getFixedValues() != null
//                        || a.getVmCounterpartyFixedTrigger().getFixedValues() != null
//                        || a.getImPrincipalFixedTrigger().getFixedValues() != null
//                        || a.getImCounterpartyFixedTrigger().getFixedValues() != null) {
//                    pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
//                }
//            }

            pageManager.agreementAdditionalFieldsSetupPage.setupAgreementAdditionalFields(a);

            if (a.isUmbrellaAgreement() != null && a.isUmbrellaAgreement()) {
                pageManager.agreementUmbrellaNettingSetupPage.setupAgreementUmbrellaNetting(a);
            }

            if(a.getSettlement() != null || a.getTsaLevel() != null || a.getSettlementBucket() != null){
                pageManager.agreementSettlementSetupPage.setupAgreementSettlement(a);
            }
            pageManager.agreementReportingControlSetupPage.setupAgreementReportingControl(a);
            pageManager.agreementSummaryPage.viewStatement();

            //sydney suggest on locked statement, retry to re-caculate statement in 3 times by 5 seconds for each cycle
            //pageManager.agreementStatementPage.recalculateIf("has already been locked for calculation", 3, 5000);

            String agreementId = pageManager.agreementStatementPage.getAgreementId();
            if (a.getAgreementId() != null)
                a.getAgreementId().setValue(agreementId);
            else
                a.setAgreementId(new StringType(agreementId));

            if(agreementType.equalsIgnoreCase("Umbrella")){
                if (a.isUmbrellaAgreement() == null ||
                        (a.isUmbrellaAgreement() != null && !a.isUmbrellaAgreement())){
                    approve_agreement(id);
                }
            }

            pageManager.agreementStatementPage.enterAgreementSummary();
            /*
            if (a.getAgreementStatus() != null && a.getAgreementStatus().size() > 0 )
                pageManager.agreementStatementPage.changeAgreementStatus(a.getAgreementStatus());
            if (a.getStatementStatus() != null && a.getStatementStatus().size() > 0)
                pageManager.agreementStatementPage.changeStatementStatus(a.getStatementStatus());
            */
        }
    }

    /**
     * Description: expand category<br/>
     * Start page: agreement statement page<br/>
     * End page: agreement statement page<br/>
     * samples: expand category <b>agreement.category</b>
     * @param ids can be one of following value:<br /><li>{@link ModelCategoryType}</li>
     * @throws Exception
     */
//    @When("^expand category (\\S+)$")
//    public void expand_category(List<String> ids) throws Exception{
//        for (String id : ids){
//            EtdAgreementStatement etdAgreementStatement = testDataManager.getEtdAgreementStatement(id);
//            testCase.embedTestData(etdAgreementStatement);
//            pageManager.agreementStatementPage.assertEtdTopAgreementStatement();
//            pageManager.agreementStatementPage.expandCategory(etdAgreementStatement);
//        }
//    }

    /**
     * Description: edit fix trigger tab in agreement<br/>
     * Start page: agreementSummaryPage <br/>
     * End page: agreementSummaryPage<br/>
     * Samples: edit fix trigger tab in agreement to <b>my.agreement</b><br/>
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^edit fix trigger tab in agreement to (\\S+)$")
    public void edit_fix_trigger_tab_in_agreement_to(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementSummaryPage.enterAgreementFixedTriggerSummary();
        pageManager.agreementSummaryPage.editAgreementSummary();

        Agreement a = testDataManager.getAgreement(id);

        testCase.embedTestData(a);

        pageManager.agreementFixedTriggerSetupPage.editAgreementFixedTrigger(a);
        pageManager.agreementFixedTriggerSetupPage.saveAndExit();
        pageManager.agreementSummaryPage.enterAgreementSummary();

            /*
            if (a.getAgreementStatus() != null && a.getAgreementStatus().size() > 0 )
                pageManager.agreementStatementPage.changeAgreementStatus(a.getAgreementStatus());
            if (a.getStatementStatus() != null && a.getStatementStatus().size() > 0)
                pageManager.agreementStatementPage.changeStatementStatus(a.getStatementStatus());
            */
    }

    /**
     * Description: edit agreement<br/>
     * Start page: agreementSummaryPage <br/>
     * End page: agreementSummaryPage<br/>
     * Samples: edit OTC agreement to <b>my.new.agreement</b><br/>
     * @param id2 can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^edit (?:OTC|FCM|ETF|Repo|Umbrella) agreement ?(\\S+)? to (\\S+)$")
    public void edit_agreement_to(String id1, String id2) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementSummaryPage.enterAgreementPartySummary();
        pageManager.agreementSummaryPage.editAgreementSummary();
        Agreement a = testDataManager.getAgreement(id2);
        testCase.embedTestData(a);
        Agreement b = null;
        if (id1 != null && !id1.isEmpty()){
            b = testDataManager.getAgreement(id1);
        }

        pageManager.agreementPartyCounterpartySetupPage.setupAgreementPartyCounterparty(a);
        pageManager.agreementDocumentationSetupPage.setupAgreementDocumentation(a);
        pageManager.agreementCallScheduleSetupPage.setupAgreementCallSchedule(a);
        if (a.isUmbrellaAgreement() == null) {
            pageManager.agreementProductsSetupPage.setupAgreementProducts(a);
        }
        if (a.isMultiModel() != null && a.isMultiModel()) {
            pageManager.agreementModelSetupPage.setupAgreementModel(a);
        }
        if (b!=null) {
            pageManager.agreementCollateralSetupPage.editAgreementCollateral(b, a);
            testCase.embedTestData(b);
        } else
            pageManager.agreementCollateralSetupPage.setupAgreementCollateral(a);

        pageManager.agreementFixedTriggerSetupPage.setupAgreementFixedTrigger(a);

        if (a.getPrincipalFixedTrigger() != null ||
                a.getCounterpartyFixedTrigger() != null) {
            if (a.getPrincipalFixedTrigger().getFixedValues() != null ||
                    a.getCounterpartyFixedTrigger().getFixedValues() != null) {
                pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
            }
        } else if (a.getVmPrincipalFixedTrigger() != null ||
                a.getVmCounterpartyFixedTrigger() != null ||
                a.getImPrincipalFixedTrigger() != null ||
                a.getImCounterpartyFixedTrigger() != null) {

            if (a.getVmPrincipalFixedTrigger().getFixedValues() != null
                    || a.getVmCounterpartyFixedTrigger().getFixedValues() != null
                    || a.getImPrincipalFixedTrigger().getFixedValues() != null
                    || a.getImCounterpartyFixedTrigger().getFixedValues() != null) {
                pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
            }
        }

        pageManager.agreementAdditionalFieldsSetupPage.setupAgreementAdditionalFields(a);

        if (a.isUmbrellaAgreement() != null && a.isUmbrellaAgreement()) {
            pageManager.agreementUmbrellaNettingSetupPage.setupAgreementUmbrellaNetting(a);
        }

        if (a.isUmbrellaAgreement() == null || !a.isUmbrellaAgreement())
            pageManager.agreementSettlementSetupPage.setupAgreementSettlement(a);
        pageManager.agreementReportingControlSetupPage.setupAgreementReportingControl(a);
        pageManager.agreementSummaryPage.viewStatement();

        //sydney suggest on locked statement, retry to re-caculate statement in 3 times by 5 seconds for each cycle
        //pageManager.agreementStatementPage.recalculateIf("has already been locked for calculation", 3, 5000);

        String agreementId = pageManager.agreementStatementPage.getAgreementId();
        if (a.getAgreementId() != null)
            a.getAgreementId().setValue(agreementId);
        else
            a.setAgreementId(new StringType(agreementId));

        pageManager.agreementStatementPage.enterAgreementSummary();

            /*
            if (a.getAgreementStatus() != null && a.getAgreementStatus().size() > 0 )
                pageManager.agreementStatementPage.changeAgreementStatus(a.getAgreementStatus());
            if (a.getStatementStatus() != null && a.getStatementStatus().size() > 0)
                pageManager.agreementStatementPage.changeStatementStatus(a.getStatementStatus());
            */
    }

    /**
     * Description: edit agreement<br/>
     * Start page: agreementSummaryPage <br/>
     * End page: agreementSummaryPage<br/>
     * Samples: edit OTC agreement to <b>my.new.agreement</b><br/>
     * @param id1 can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^edit (?:OTC|FCM|ETF|Repo|Umbrella|ETD) agreement ?(\\S+)? to (\\S+) in (document|collateral|ETD|callSchedule|fixTrigger|reportControl) tab$")
    public void edit_agreement(String id1, String id2, String tab) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Agreement a = testDataManager.getAgreement(id2);
        Agreement b = null;
        if (id1 != null && !id1.isEmpty()){
            b = testDataManager.getAgreement(id1);
        }

        testCase.embedTestData(a);

        if (b!=null) {
            testCase.embedTestData(b);
        }

        switch (tab.toLowerCase()) {
            case "document":
                pageManager.agreementSummaryPage.enterAgreementDocumentationSummary();
                pageManager.agreementSummaryPage.editAgreementSummary();
                pageManager.agreementDocumentationSetupPage.setupAgreementDocumentationAndSave(a);
                break;
            case "collateral":
                pageManager.agreementSummaryPage.enterAgreementCollateralSummary();
                pageManager.agreementSummaryPage.editAgreementSummary();
                if (b != null) {
                    pageManager.agreementCollateralSetupPage.editAgreementCollateral(b, a);
                    testCase.embedTestData(b);
                } else {
                    pageManager.agreementCollateralSetupPage.setupAgreementCollateral(a);
                }
                pageManager.agreementCollateralSetupPage.saveAndExit();
                break;
            case "callschedule":
                pageManager.agreementSummaryPage.enterAgreementcallScheduleSummary();
                pageManager.agreementSummaryPage.editAgreementSummary();
                pageManager.agreementCallScheduleSetupPage.setupAgreementCallSchedule(a);
                pageManager.agreementCallScheduleSetupPage.saveAndExit();
                break;
            case "fixtrigger":
                pageManager.agreementSummaryPage.enterAgreementFixedTriggerSummary();
                pageManager.agreementSummaryPage.editAgreementSummary();
                pageManager.agreementFixedTriggerSetupPage.setupAgreementFixedTrigger(a);
                if (a.getPrincipalFixedTrigger() != null ||
                        a.getCounterpartyFixedTrigger() != null) {
                    if (a.getPrincipalFixedTrigger().getFixedValues() != null ||
                            a.getCounterpartyFixedTrigger().getFixedValues() != null) {
                        pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
                    }
                } else if (a.getVmPrincipalFixedTrigger() != null ||
                        a.getVmCounterpartyFixedTrigger() != null ||
                        a.getImPrincipalFixedTrigger() != null ||
                        a.getImCounterpartyFixedTrigger() != null) {

                    if (a.getVmPrincipalFixedTrigger().getFixedValues() != null
                            || a.getVmCounterpartyFixedTrigger().getFixedValues() != null
                            || a.getImPrincipalFixedTrigger().getFixedValues() != null
                            || a.getImCounterpartyFixedTrigger().getFixedValues() != null) {
                        pageManager.agreementRuleTriggerSetupPage.setupAgreementRuleTrigger(a);
                    }
                }
                pageManager.agreementFixedTriggerSetupPage.saveAndExit();
                break;
            case "reportcontrol":
                pageManager.agreementSummaryPage.enterAgreementReportingControlSummary();
                pageManager.agreementSummaryPage.editAgreementSummary();
                pageManager.agreementReportingControlSetupPage.setupAgreementReportingControl(a);
                pageManager.agreementCallScheduleSetupPage.saveAndExit();
                break;
        }
        if ((a.getAgreementStatus() != null && a.getAgreementStatus().size() > 0)
                || (a.getStatementStatus() != null && a.getStatementStatus().size() > 0)){
            pageManager.agreementSummaryPage.viewStatement();
            if (a.getAgreementStatus() != null && !a.getAgreementStatus().isEmpty())
                pageManager.agreementStatementPage.changeAgreementStatus(a.getAgreementStatus());
            if (a.getStatementStatus() != null && !a.getStatementStatus().isEmpty())
                pageManager.agreementStatementPage.changeStatementStatus(a.getStatementStatus());
            pageManager.agreementStatementPage.enterAgreementSummary();
        }
    }

    /**
     * Description: linkage set copy agreement<br/>
     * Start page: agreementSummaryPage <br/>
     * End page: copyResultPage<br/>
     * samples: linkage set copy agreement <b>my.agreementsummary</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSummary}</li>
     * @throws Exception
     */
    @When("^linkage set copy agreement (\\S+)$")
    public void linkage_set_copy_agreement(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions'
        AgreementSummary agreementSummary = testDataManager.getAgreementSummary(id);
        testCase.embedTestData(agreementSummary);
        pageManager.agreementSummaryPage.savetoLinkageSet(agreementSummary);
    }

    /**
     * Description: click product type<br/>
     * Start page: agreement exposure summary page <br/>
     * End page: trade summary page<br/>
     * samples: view product type <b>my.agreementExposureSummary</b> on exposure summary page<br/>
     * @param id can be one of following type:<br /><li>{@link AgreementExposureSummary}</li>
     * @throws Exception
     */
    @When("^view product type (\\S+) on exposure summary page$")
    public void view_product_type_on_exposure_summary_page(String id) throws Exception{
        AgreementExposureSummary agreementExposureSummary = testDataManager.getAgreementExposureSummary(id);
        testCase.embedTestData(agreementExposureSummary);
        pageManager.agreementExposuresSummaryPage.enterTradeSummary(agreementExposureSummary);
    }

    /**
     * Description: add trades<br/>
     * Start page: trade summary page <br/>
     * End page: trade summary page<br/>
     * samples: add <b>OTC</b> trades <b>my.trade</b><br/>
     * @param ids can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @throws Exception
     */
    @When("^add (?:OTC|FCM|ETF|Repo) trades? (\\S+)$")
    public void add_trade(List<String> ids) throws Exception {
        for (String id : ids) {
            TradeDetail trade = testDataManager.getTradeDetail(id);
            testCase.embedTestData(trade);
            pageManager.tradeSummaryPage.addTrade();
            pageManager.tradeEditorPage.addTrade(trade);
        }
    }

    /**
     * Description: edit trade<br/>
     * Start page: trade summary page <br/>
     * End page: trade summary page<br/>
     * samples: edit trade <b>my.old.trade</b> to <b>my.new.trade</b><br/>
     * @param oriTradeID can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @param newTradeID can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @throws Exception
     */
    @When("^edit trade (\\S+) to (\\S+)$")
    public void edit_trade_to(String oriTradeID, String newTradeID) throws Exception {
        TradeDetail oriTrade = testDataManager.getTradeDetail(oriTradeID);
        TradeDetail newTrade = testDataManager.getTradeDetail(newTradeID);
        testCase.embedTestData(oriTrade);
        testCase.embedTestData(newTrade);
        pageManager.tradeSummaryPage.enterTrade(oriTrade);
        pageManager.tradeEditorPage.addTrade(newTrade);
    }

    /**
     * Description: delete trade<br/>
     * Start page: trade summary page <br/>
     * End page: trade summary page<br/>
     * samples: edit trade <b>my.old.trade</b> to <b>my.new.trade</b><br/>
     * @param oriTradeID can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @throws Exception
     */
    @When("^delete trade (\\S+)$")
    public void delete_trade(String oriTradeID) throws Exception {
        TradeDetail oriTrade = testDataManager.getTradeDetail(oriTradeID);
        testCase.embedTestData(oriTrade);
        pageManager.tradeSummaryPage.deleteTrade(oriTrade);
    }

    /**
     * Description: view trade detail<br/>
     * Start page: trade summary page <br/>
     * End page: trade editor page<br/>
     * samples: view trade <b>my.trade.detail</b> detail<br/>
     * @param id can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @throws Exception
     */
    @When("^view trade (\\S+) detail$")
    public void view_trade_detail(String id) throws Exception{
        TradeDetail tradeDetail = testDataManager.getTradeDetail(id);
        testCase.embedTestData(tradeDetail);
        pageManager.tradeSummaryPage.enterTrade(tradeDetail);
    }

    /**
     * Description: Resend confirmation letter for specific booking<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: resend confirmation letter for booking <b>my.booking</b><br/>
     * @param id can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @When("^resend confirmation letter for booking (\\S+)$")
    public void resend_conf_letter(String id) throws Exception{
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(id);
        testCase.embedTestData(assetBookingSummary);
        pageManager.agreementAssetsSummaryPage.enterBookingEditor(assetBookingSummary);
        pageManager.agreementAssetsEditorPage.resendConfLetter();
        pageManager.agreementAssetsEditorPage.returnToAssetHoldings();
    }

    /**
     * Description: Open booking<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: Open asset booking editor page <b>my.booking</b><br/>
     * @param id can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @When("^open asset booking editor page (\\S+)$")
    public void open_asset_booking_editor_page(String id) throws Exception{
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(id);
        testCase.embedTestData(assetBookingSummary);
        pageManager.agreementAssetsSummaryPage.enterBookingEditor(assetBookingSummary);
    }

    /**
     * Description: Open booking<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: Open asset booking editor page <b>my.booking</b><br/>
     * @param id can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @When("^open group asset booking editor page (\\S+)$")
    public void open_group_asset_booking_editor_page(String id) throws Exception{
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(id);
        testCase.embedTestData(assetBookingSummary);
        pageManager.agreementAssetsSummaryPage.enterGroupBookingEditor(assetBookingSummary);
    }

    /**
     * Description: edit booking<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: edit <b>call</b> booking <b>my.bookingSummary</b> to <b>my.new.bookingDetail</b><br/>
     * @param oriBookingId can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @param newBookingId can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^edit (?:call|return|delivery|recall|wire in|wire out|pledge in|pledge out)? booking (\\S+) to (\\S+)$")
    public void edit_booking_to(String oriBookingId, String newBookingId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(oriBookingId);
        BookingDetail newBookingDetail = testDataManager.getBookingDetail(newBookingId);
        testCase.embedTestData(assetBookingSummary);
        testCase.embedTestData(newBookingDetail);
        pageManager.agreementAssetsSummaryPage.enterBookingEditor(assetBookingSummary);
        pageManager.agreementAssetsEditorPage.changeBooking(newBookingDetail);
        if(newBookingDetail.getBookingInformation().getSettlementStatusInfo() != null
                && ! newBookingDetail.getBookingInformation().getSettlementStatusInfo().isEmpty()){
            StringType stringType = assetBookingSummary.getStatus();
            for(int i = 0; i< newBookingDetail.getBookingInformation().getSettlementStatusInfo().size(); i++){
                if(stringType == null){
                    assetBookingSummary.setStatus(
                            new StringType(
                                    newBookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value()
                            ));
                }else{
                    assetBookingSummary.getStatus().setValue(
                            newBookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value()
                    );
                }
                pageManager.agreementAssetsSummaryPage.enterBookingEditor(assetBookingSummary);
                pageManager.agreementAssetsEditorPage.changeStatementStatus(newBookingDetail.getBookingInformation().getSettlementStatusInfo().get(i));

                if (newBookingDetail.getAlertHandling() != null && !newBookingDetail.getAlertHandling().isEmpty())
                    pageManager.agreementAssetsEditorPage.handleAlters(newBookingDetail.getAlertHandling());
            }
            assetBookingSummary.setStatus(stringType);
        }
    }


    /**
     * Description: Edit booking information from Agreement Assets - Security Editor page <br/>
     * Start page: AssetsEditorPage <br/>
     * End page: AssetsEditorPage<br/>
     * samples: edit <b>call</b> booking<br/>
     */
    @When("^edit (?:call|return|delivery|recall)? booking (\\S+)$")
    public void edit_booking(String BookingId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        BookingDetail newBookingDetail = testDataManager.getBookingDetail(BookingId);
        testCase.embedTestData(newBookingDetail);
        pageManager.agreementAssetsEditorPage.changeBooking(newBookingDetail);
        }


    /**
     * Description: Open cash movement <br/>
     * Start page: cashMovementSummary <br/>
     * End page: cashMovementSummary<br/>
     * samples: Open cash movement editor page <b>my.cashMovement</b><br/>
     * @param id can be one of following type:<br /><li>{@link CashMovementSummary}</li>
     * @throws Exception
     */
    @When("^open cash movement editor page (\\S+)$")
    public void open_cash_movement_editor_page(String id) throws Exception{
        CashMovementSummary cashMovementSummary = testDataManager.getCashMovementSummary(id);
        testCase.embedTestData(cashMovementSummary);
        pageManager.cashMovementSummaryPage.enterCashMovementEdit(cashMovementSummary);
    }

    /**
     * Description: edit cashmovement booking<br/>
     * Start page: cashMovementSummaryPage <br/>
     * End page: cashMovementSummaryPage<br/>
     * samples: edit cashmovement booking <b>my.cashmovementSummary</b> to <b>my.new.cashMovementBooking</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link CashMovementSummary}</li>
     * @param newId can be one of following type:<br /><li>{@link CashMovementDetail}</li>
     * @throws Exception
     */
    @When("^edit cashmovement booking (\\S+) to (\\S+)$")
    public void edit_cashmovement_booking(String oriId, String newId) throws Exception{
        CashMovementSummary oriCashMovementSummary = testDataManager.getCashMovementSummary(oriId);
        CashMovementDetail newCashMovementDetail = testDataManager.getCashMovementBooking(newId);
        testCase.embedTestData(oriCashMovementSummary);
        testCase.embedTestData(newCashMovementDetail);
        pageManager.cashMovementSummaryPage.enterCashMovementEdit(oriCashMovementSummary);
        pageManager.cashMovementEditorPage.setCashEditor(newCashMovementDetail);
        pageManager.cashMovementEditorPage.save(newCashMovementDetail);
        if(newCashMovementDetail.getSettlementStatus() != null
                && ! newCashMovementDetail.getSettlementStatus().isEmpty()){
//            StringType stringType = assetBookingSummary.getStatus();
            for(int i = 0; i< newCashMovementDetail.getSettlementStatus().size(); i++){
//                if(stringType == null){
//                    assetBookingSummary.setStatus(
//                            new StringType(
//                                    newBookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value()
//                            ));
//                }else{
//                    assetBookingSummary.getStatus().setValue(
//                            newBookingDetail.getBookingInformation().getSettlementStatusInfo().get(i).getSettlementStatus().value()
//                    );
//                }
                pageManager.cashMovementSummaryPage.enterCashMovementEdit(oriCashMovementSummary);
                pageManager.cashMovementEditorPage.changeSettlementStatus(newCashMovementDetail.getSettlementStatus().get(i));
                pageManager.cashMovementEditorPage.save();
            }
//            assetBookingSummary.setStatus(stringType);
        }
    }

    /**
     * Description: click asset type, go to asset summary|cashmovement summary page<br/>
     * Start page: assetHoldingSummaryPage <br/>
     * End page: assetBookingSummaryPage|cashmovementSummaryPage<br/>
     * samples: view asset type <b>my.assetBookingSumary</b> agreement asset <b>Cash Movements</b> Page<br/>
     * @param assetBookingSummaryId can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @param type can be one of following value:<br /><li>CASH</li><li>Equity</li><li>Other</li><li>Cash Movements</li>
     * @throws Exception
     */
    @When("^view asset type (\\S+) agreement asset (CASH|Equity|Bond|Other|Cash Movements)? ?Page$")
    public void view_asset_type_in_agreement_asset_page(String assetBookingSummaryId, String type) throws Exception{
        AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(assetBookingSummaryId);
        testCase.embedTestData(assetBookingSummary);
        pageManager.agreementAssetHoldingsSummaryPage.enterAgreementAssetsSummary(assetBookingSummary);
        if(type != null && type.equalsIgnoreCase("Cash Movements")){
            pageManager.agreementAssetsSummaryPage.viewCashMovementsSummary();
        }
    }

    /**
     * Description: approve agreement<br/>
     * Start page: agreementStatementPage <br/>
     * End page: agreementStatementPage<br/>
     * samples: approve agreement <b>my.agreement</b><br/>
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^approve agreement (\\S+)$")
    public void approve_agreement(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        Agreement agreement = testDataManager.getAgreement(id);
        testCase.embedTestData(agreement);
        if (agreement.getAgreementStatus() != null && agreement.getAgreementStatus().size() > 0){
            if(agreement.isUmbrellaAgreement() != null && agreement.isUmbrellaAgreement()){
                pageManager.agreementStatementPage.switchAgreement(agreement);
                pageManager.agreementStatementPage.changeAgreementStatus(agreement.getAgreementStatus().get(0));
                for(SubAgreementSearch subAgreementSearch : agreement.getSubAgreement()){
                    Agreement a = new Agreement();
                    a.setAgreementName(subAgreementSearch.getLinkText());

                    pageManager.agreementStatementPage.switchAgreement(a);
                    pageManager.agreementStatementPage.changeAgreementStatus(agreement.getAgreementStatus());
                    testCase.embedTestData(a);
                }
                pageManager.agreementStatementPage.switchAgreement(agreement);
                pageManager.agreementStatementPage.changeAgreementStatus(agreement.getAgreementStatus());
            }else{
                pageManager.agreementStatementPage.changeAgreementStatus(agreement.getAgreementStatus());
            }
        }

        if (agreement.getStatementStatus() != null && agreement.getStatementStatus().size() > 0){
            if(agreement.isUmbrellaAgreement() != null && agreement.isUmbrellaAgreement()){
                pageManager.agreementStatementPage.switchAgreement(agreement);
                pageManager.agreementStatementPage.changeStatementStatus(agreement.getStatementStatus().get(0));
                for(SubAgreementSearch simpleSearch : agreement.getSubAgreement()){
                    Agreement a = new Agreement();
                    a.setAgreementName(simpleSearch.getLinkText());

                    pageManager.agreementStatementPage.switchAgreement(a);
                    pageManager.agreementStatementPage.changeStatementStatus(agreement.getStatementStatus());
                    testCase.embedTestData(a);
                }
                pageManager.agreementStatementPage.switchAgreement(agreement);
                pageManager.agreementStatementPage.changeStatementStatus(agreement.getAgreementStatus());
            }else{
                pageManager.agreementStatementPage.changeStatementStatus(agreement.getStatementStatus());
            }
        }

        /*
        if (agreement.getAgreementStatus() != null && agreement.getAgreementStatus().size() > 0)
            pageManager.agreementStatementPage.changeAgreementStatus(agreement.getAgreementStatus());
        if (agreement.getStatementStatus() != null && agreement.getStatementStatus().size() > 0)
            pageManager.agreementStatementPage.changeStatementStatus(agreement.getStatementStatus());
        */
    }

    /**
     * Description: click recalc button on agreement statement page<br/>
     * Start page: agreementStatementPage <br/>
     * End page: agreementStatementPage<br/>
     * samples: recalculate agreement statement<br/>
     * @throws Exception
     */
    @When("^recalculate agreement statement$")
    public void recalculate_agreement_statement() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementStatementPage.recalculate();
    }

    /**
     * Description: click add button on agreement Asset Holdings  page<br/>
     * Start page: agreementAssetHoldingPage <br/>
     * End page: agreementAssetHoldingPage<br/>
     * samples: click add to access the page<br/>
     * @throws Exception
     */
    @When("^Click on Add button on cash  summary$")
    public void Click_on_Add_button_on_cash_summary() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementAssetHoldingsSummaryPage.add_booking();
    }

    /**
     * Description: click apply button on agreement Asset Holdings  page<br/>
     * Start page: agreementAssetHoldingPage <br/>
     * End page: agreementAssetHoldingPage<br/>
     * samples: click apply button to implement the required amount in nominal amount<br/>
     *
     * @throws Exception
     */

    @When("^click Apply on Collateral Agreement Assets Cash Editor $")
    public void click_Apply_on_Collateral_Agreement_Assets_Cash_Editor() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementAssetsEditorPage.apply();

    }

    /**
     * Description: click recalculate button on agreement Asset Holdings  page<br/>
     * Start page: agreementAssetHoldingPage <br/>
     * End page: agreementAssetHoldingPage<br/>
     * samples: click recalculate inorder to recalculate the asset in agreement overview section of the page<br/>
     *
     * @throws Exception
     */
    @When("^recalculate agreement overview on Cash Editor page$")
    public void recalculate_agreement_overview_on_Cash_Editor_page() throws Exception {
        pageManager.agreementAssetsEditorPage.Recalculate();

    }
    /**
     * Description: click view simulation on agreement statement page<br/>
     * Start page: agreementStatementPage <br/>
     * End page: simulation statement<br/>
     * samples: view simulation<br/>
     * @throws Exception
     */
    @When("^quick link - View Simulation$")
    public void view_simulation() throws Exception {
        pageManager.agreementStatementPage.viewSimulation();
    }

    /**
     * Description: set start-up dashboard page<br/>
     * Start page: start-up dashboard page <br/>
     * End page: start-up dashboard page<br/>
     * samples: set start-up dashboard <b>my.startup.dashboard</b><br/>
     * @param id can be one of following type:<br /><li>{@link StartupDashboard}</li>
     * @throws Exception
     */
    @When("^set start-up dashboard (\\S+)$")
    public void set_start_up_dashboard(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StartupDashboard startupDashboard = testDataManager.getStartupDashboard(id);
        testCase.embedTestData(startupDashboard);
        pageManager.startupDashboardPage.setStartupDashboard(startupDashboard);
    }

    /**
     * Description: navigate to approvals manager page<br/>
     * Start page: anyPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: navigate to approvals manager page<br/>
     * @throws Exception
     */
    @When("^navigate to approvals manager page$")
    public void navigate_to_approvals_manager_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.APPROVALS_MANAGER);
    }

    /**
     * Description: tick approval manager result<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - tick <b>agreements</b> <b>my.agreements</b><br/>
     * @param tabName can be one of following value:<br /><li>organisation</li><li>agreements</li><li>statements</li><li>workflow</li><li>settlement instructions</li><li>securities data</li><li>eligibility rules template</li>
     * @param ids can be one of following type:<br /><li>{@link OrganisationSearchResult}</li><li>{@link AgreementSearchResult}</li><li>{@link AgreementSearchResult}</li><li>{@link WorkflowSearchResult}</li><li>{@link SettlementInstructionsSearchResult}</li><li>{@link SecuritySearchResult}</li><li>{@link EligibilityRulesTemplateSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - (untick|tick) (organisation|agreements|statements|workflow|settlement instructions|securities data|eligibility rules template|trade) (\\S+)$")
    public void approvals_manager_tick_event(String tickOrUntick, String tabName, List<String> ids) throws Exception{
        for(String id : ids) {
            Object object;
            Boolean selectTick = null;
            switch (tickOrUntick) {
                case "untick":
                    selectTick = false;
                    break;
                case "tick":
                    selectTick = true;
                    break;
            }
            switch (tabName.toLowerCase()) {
                case "organisation":
                    object = testDataManager.getApprovalManagerOrganisationSearchResult(id);
                    pageManager.approvalsManagerPage.tickOrganisationSearchResult((OrganisationSearchResult) object, selectTick);
                    break;
                case "agreements":
                    object = testDataManager.getApprovalManagerAgreementSearchResult(id);
                    pageManager.approvalsManagerPage.tickAgreementSearchResult((AgreementSearchResult) object, selectTick);
                    break;
                case "statements":
                    object = testDataManager.getApprovalManagerAgreementSearchResult(id);
                    pageManager.approvalsManagerPage.tickStatementsSearchResult((AgreementSearchResult) object, selectTick);
                    break;
                case "workflow":
                    object = testDataManager.getApprovalManagerWorkflowSearchResult(id);
                    pageManager.approvalsManagerPage.tickWorkflowSearchResult((WorkflowSearchResult) object, selectTick);
                    break;
                case "settlement instructions":
                    object = testDataManager.getApprovalManagerSettlementInstructionsSearchResult(id);
                    pageManager.approvalsManagerPage.tickSettlementInstructionsSearchResult((SettlementInstructionsSearchResult) object, selectTick);
                    break;
                case "securities data":
                    object = testDataManager.getApprovalManagerSecuritySearchResult(id);
                    pageManager.approvalsManagerPage.tickSecuritiesDataSearchResult((SecuritySearchResult) object, selectTick);
                    break;
                case "eligibility rules template":
                    object = testDataManager.getApprovalManagerEligibilityRulesTemplateSearchResult(id);
                    pageManager.approvalsManagerPage.tickEligibilityRulesTemplateSearchRusult((EligibilityRulesTemplateSearchResult) object, selectTick);
                    break;
                case "trade":
                    object = testDataManager.getApprovalManagerTradesSearchResult(id);
                    pageManager.approvalsManagerPage.tickTradeResult((ApprovalManagerTradesSearchResult) object, selectTick);
                    break;
            }
        }
    }

    /**
     * Description: tick all approval manager result<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - tick all<br/>
     * @throws Exception
     */
    @When("^approvals manager - tick all$")
    public void approvals_manager_tick_all() {
        throw new PendingException();
    }

    /**
     * Description: search organisation in approval manager organisation TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search organisation <b>my.OrganisationSimpleSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link OrganisationSimpleSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search organisation (\\S+)$")
    public void approvals_manager_search_organisation(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        OrganisationSimpleSearch organisationSimpleSearch = testDataManager.getApprovalManagerOrganisationSimpleSearch(id);
        testCase.embedTestData(organisationSimpleSearch);
        pageManager.approvalsManagerPage.switchToOrganisationTab();
        pageManager.approvalsManagerPage.searchOrganisation(organisationSimpleSearch);
        pageManager.approvalsManagerPage.search();
    }

//    @When("^approve ticked organisations? (\\S+) in approvals manager$")
//    public void approve_ticked_organisation_in_approvals_manager(List<String> ids) throws Exception {
//        // Write code here that turns the phrase above into concrete actions
//        for (String id : ids) {
//            OrganisationSearchResult organisationSearchResult = testDataManager.getOrganisationSearchResult(id);
//            pageManager.approvalsManagerPage.tickOrganisationSearchResult(organisationSearchResult);
//            testCase.embedTestData(organisationSearchResult);
//        }
//        pageManager.approvalsManagerPage.approveTickedSearchResults();
//    }

    /*
    The function implemented in approvals_manager_approve_all_organisations(String id)
    @When("^approve all organisations in approvals manager$")
    public void approve_all_organisation_in_approvals_manager() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        //pageManager.approvalsManagerPage.approveAllSearchResults();
        throw new PendingException();
    }
    */

    /**
     * Description: search agreement in approval manager agreement TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search agreement <b>my.AgreementSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search agreement (\\S+)$")
    public void approvals_manager_search_agreement(String id) throws Exception{
        AgreementSearch agreementSearch = testDataManager.getApprovalsManagerAgreementSearch(id);
        testCase.embedTestData(agreementSearch);
        pageManager.approvalsManagerPage.switchToAgreementsTab();
        pageManager.approvalsManagerPage.searchAgreement(agreementSearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: click approve ticked button in approval manager agreements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked agreements<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked agreements?$")
    public void approvals_manager_approve_ticked_agreements() throws Exception {
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager agreements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all agreements <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all agreements (\\S+)$")
    public void approvals_manager_approve_all_agreements(String id) throws Exception {
        AgreementSearchResult agreementSearchResult = testDataManager.getApprovalManagerAgreementSearchResult(id);
        testCase.embedTestData(agreementSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(agreementSearchResult.getAlertHandling());
    }

    /**
     * Description: click approve all button in approval manager agreements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all trades <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link ApprovalManagerTradesSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all trades (\\S+)$")
    public void approvals_manager_approve_all_trades(String id) throws Exception {
        ApprovalManagerTradesSearchResult approvalManagerTradesSearchResult = testDataManager.getApprovalManagerTradesSearchResult(id);
        testCase.embedTestData(approvalManagerTradesSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(approvalManagerTradesSearchResult.getAlertHandling());
    }

    /**
     * Description: click agreement review button in approval manager agreements search result<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - agreement <b>my.AgreementSearchResult</b> agreement review<br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - agreement (\\S+) agreement review$")
    public void approvals_manager_agreement_agreement_review(String id) throws Exception{
        AgreementSearchResult agreementSearchResult = testDataManager.getApprovalManagerAgreementSearchResult(id);
        testCase.embedTestData(agreementSearchResult);
        pageManager.approvalsManagerPage.enterAgreementReview(agreementSearchResult);
    }

    /**
     * Description: click approve ticked button in approval manager organisations TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked organisations<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked organisations?$")
    public void approvals_manager_approve_ticked_organisations() throws Exception {
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager organisations TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all organisations <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link OrganisationSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all organisations (\\S+)$")
    public void approvals_manager_approve_all_organisations(String id) throws Exception {
        OrganisationSearchResult organisationSearchResult = testDataManager.getApprovalManagerOrganisationSearchResult(id);
        testCase.embedTestData(organisationSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(organisationSearchResult.getAlertHandling());
    }

    /**
     * Description: search statement in approval manager statement TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search statement <b>my.agreementSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search statement (\\S+)$")
    public void approvals_manager_search_statement(String id) throws Exception{
        AgreementSearch agreementSearch = testDataManager.getApprovalsManagerStatementSearch(id);
        testCase.embedTestData(agreementSearch);
        pageManager.approvalsManagerPage.switchToStatementsTab();
        pageManager.approvalsManagerPage.searchStatement(agreementSearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: search trade in approval manager statement TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search trade <b>my.atrade</b><br/>
     * @param id can be one of following type:<br /><li>{@link ApprovalManagerTradesSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search trade (\\S+)$")
    public void approvals_manager_search_trade(String id) throws Exception{
        ApprovalManagerTradesSearch approvalManagerTradesSearch = testDataManager.getApprovalManagerTradesSearch(id);
        testCase.embedTestData(approvalManagerTradesSearch);
        pageManager.approvalsManagerPage.switchToTradeTab();
        pageManager.approvalsManagerPage.searchTrade(approvalManagerTradesSearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: click approve ticked button in approval manager statements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked statements<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked statements?$")
    public void approvals_manager_approve_ticked_statements() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve ticked button in approval manager statements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked trades<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked trades?$")
    public void approvals_manager_approve_ticked_trades() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager statements TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all statements <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all statements (\\S+)$")
    public void approvals_manager_approve_all_statements(String id) throws Exception{
        AgreementSearchResult agreementSearchResult = testDataManager.getApprovalManagerStatementSearchResult(id);
        testCase.embedTestData(agreementSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(agreementSearchResult.getAlertHandling());
    }

    /**
     * Description: click view statement button in approval manager statement search result<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^approvals manager - statement (\\S+) agreement view$")
    public void approvals_manager_statement_agreement_view(String id) {
        throw new PendingException();
    }

    /**
     * Description: search workflow in approval manager workflow TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search workflow <b>my.agreementSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search workflow (\\S+)$")
    public void approvals_manager_search_workflow(String id) throws Exception{
        AgreementSearch agreementSearch = testDataManager.getApprovalManagerWorkflowSearch(id);
        testCase.embedTestData(agreementSearch);
        pageManager.approvalsManagerPage.switchToWorkfolwTab();
        pageManager.approvalsManagerPage.searchWorkflow(agreementSearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: click approve ticked button in approval manager workflows TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked workflows<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked workflows?$")
    public void approvals_manager_approve_ticked_workflows() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: reject ticked eligibility rule template record in approval manager eligibility rules template TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - reject ticked eligibility rules template <b>my.eligibility.rules.template.search.result</b><br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplateSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - reject ticked eligibility rules template (\\S+)$")
    public void approvals_manager_reject_ticked_eligibility_rules_template(String id) throws Exception{
        Boolean selectTick = true;
        EligibilityRulesTemplateSearchResult eligibilityRulesTemplateSearchResult = testDataManager.getApprovalManagerEligibilityRulesTemplateSearchResult(id);
        testCase.embedTestData(eligibilityRulesTemplateSearchResult);
        pageManager.approvalsManagerPage.rejectTickedEligibilityRulesTemplateSearchResults(eligibilityRulesTemplateSearchResult, selectTick);
    }


    /**
     * Description: click approve ticked button in approval manager workflows TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all workflow <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link WorkflowSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all workflow (\\S+)$")
    public void approvals_manager_approve_all_workflow(String id) throws Exception{
        WorkflowSearchResult workflowSearchResult = testDataManager.getApprovalManagerWorkflowSearchResult(id);
        testCase.embedTestData(workflowSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(workflowSearchResult.getAlertHandling());
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^approvals manager - workflow (\\S+) agreement review$")
    public void approvals_manager_workflow_agreement_review(String id) {
        throw new PendingException();
    }

    /**
     * Description: search settlement instructions in approval manager settlement instructions TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search settlement instructions <b>my.settlementInstructionsSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link SettlementInstructionsSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search settlement instructions (\\S+)$")
    public void approvals_manager_search_settlement_instructions(String id) throws Exception{
        SettlementInstructionsSearch settlementInstructionsSearch = testDataManager.getApprovalManagerSettlementInstructionsSearch(id);
        testCase.embedTestData(settlementInstructionsSearch);
        pageManager.approvalsManagerPage.switchToSettlementInstructionsTab();
        pageManager.approvalsManagerPage.searchSettlementInstructions(settlementInstructionsSearch);
//        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description:  click approve ticked button in approval manager settlement instructions TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked settlement instructions<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked settlement instructions?$")
    public void approvals_manager_approve_ticked_settlement_instrunctions() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager settlement instructions TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all settlement instructions <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link SettlementInstructionsSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all settlement instructions (\\S+)$")
    public void approvals_manager_approve_all_settlement_instructions(String id) throws Exception{
        SettlementInstructionsSearchResult settlementInstructionsSearchResult = testDataManager.getApprovalManagerSettlementInstructionsSearchResult(id);
        testCase.embedTestData(settlementInstructionsSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(settlementInstructionsSearchResult.getAlertHandling());
    }

    /**
     * Description: search securities data in approval manager securities data TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search securities data <b>my.securitySearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link SecuritySearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search securities data (\\S+)$")
    public void approvals_manager_search_securities_data(String id) throws Exception{
        SecuritySearch securitySearch = testDataManager.getApprovalsManagerSecuritySearch(id);
        testCase.embedTestData(securitySearch);
        pageManager.approvalsManagerPage.switchToSecuritiesDataTab();
        pageManager.approvalsManagerPage.searchSecuritiesData(securitySearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: click approve ticed button in approval manager security data TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked security data<br/> approvals manager - approve ticked securities data<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked securit(?:ies|y) data$")
    public void approvals_manager_approve_ticked_securities_data() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager security data TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all securities data <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link SecuritySearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all securities data (\\S+)$")
    public void approvals_manager_approve_all_securities_data(String id) throws Exception{
        SecuritySearchResult securitySearchResult = testDataManager.getApprovalManagerSecuritySearchResult(id);
        testCase.embedTestData(securitySearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(securitySearchResult.getAlertHandling());
    }

    /**
     * Description: search eligibility rules template in approval manager eligibility rules template TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - search eligibility rules template <b>my.eligibilityRulesTemplateSimpleSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplateSimpleSearch}</li>
     * @throws Exception
     */
    @When("^approvals manager - search eligibility rules template (\\S+)$")
    public void approvals_manager_search_eligibility_rules_template(String id) throws Exception {
        EligibilityRulesTemplateSimpleSearch eligibilityRulesTemplateSimpleSearch = testDataManager.getApprovalManagerEligibilityRulesTemplateSimpleSearch(id);
        testCase.embedTestData(eligibilityRulesTemplateSimpleSearch);
        pageManager.approvalsManagerPage.switchToEligibilityRulesTemplateTab();
        pageManager.approvalsManagerPage.searchEligibilityRuleTemplate(eligibilityRulesTemplateSimpleSearch);
        pageManager.approvalsManagerPage.search();
    }

    /**
     * Description: click approve ticed button in approval manager eligibility rules templates TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve ticked eligibility rules templates<br/>
     * @throws Exception
     */
    @When("^approvals manager - approve ticked eligibility rules templates?$")
    public void approvals_manager_approve_ticked_eligibility_rules_tempaltes() throws Exception{
        pageManager.approvalsManagerPage.approveTickedSearchResults();
    }

    /**
     * Description: click approve all button in approval manager eligibility rules templates TAB<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: approvals manager - approve all eligibility rules template <b>my.approveall.alerthanding</b><br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplateSearchResult}</li>
     * @throws Exception
     */
    @When("^approvals manager - approve all eligibility rules template (\\S+)$")
    public void approvals_manager_approve_all_eligibility_rules_template(String id) throws Exception{
        EligibilityRulesTemplateSearchResult eligibilityRulesTemplateSearchResult = testDataManager.getApprovalManagerEligibilityRulesTemplateSearchResult(id);
        testCase.embedTestData(eligibilityRulesTemplateSearchResult);
        pageManager.approvalsManagerPage.approveAllSearchResults(eligibilityRulesTemplateSearchResult.getAlertHandling());
    }

    /**
     * Description: click view change button in approval manager eligibility rules templates search result<br/>
     * Start page: approvalManagerPage <br/>
     * End page: approvalManagerPage<br/>
     * samples: view change for eligibility rules template <b>my.eligibilityRulesTemplateSearchResult</b> in approvals manager<br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplateSearchResult}</li>
     * @throws Exception
     */
    @When("^view change for eligibility rules template (\\S+) in approvals manager$")
    public void view_change_for_eligibility_rules_template_in_approvals_manager(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EligibilityRulesTemplateSearchResult eligibilityRulesTemplateSearchResult = testDataManager.getEligibilityRulesTemplateSearchResult(id);
        testCase.embedTestData(eligibilityRulesTemplateSearchResult);
        pageManager.approvalsManagerPage.viewChangesEligibilityRulesTemplate(eligibilityRulesTemplateSearchResult);
    }

    /**
     * Description: navigate to settlement status page<br/>
     * Start page: anyPage <br/>
     * End page: settlementStatusSearchPage<br/>
     * samples: navigate to settlement status page<br/>
     * @throws Exception
     */
    @When("^navigate to settlement status page$")
    public void navigate_to_settlement_status_page() throws Exception {
        pageManager.welcomePage.navigate(Menu.SETTLEMENT_STATUS);
    }

    /**
     * Description: search settlement status<br/>
     * Start page: settlementStatusSearchPage <br/>
     * End page: settlementStatusSearchResultPage<br/>
     * samples: settlement status - search settlement status <b>my.settlementStatusSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link SettlementStatusSearch}</li>
     * @throws Exception
     */
    @When("^settlement status - search settlement status (\\S+)$")
    public void settlement_status_search_settlement_status(String id) throws Exception {
        SettlementStatusSearch settlementStatusSearch = testDataManager.getSettlementStatusSearch(id);
        testCase.embedTestData(settlementStatusSearch);
        pageManager.settlementStatusSearchPage.clearSearchCondition();
        pageManager.settlementStatusSearchPage.setSettlementStatusSearch(settlementStatusSearch);
        pageManager.settlementStatusSearchPage.searchSettlementStatus();
    }

    /**
     * Description: click approve button in settlement Status Search Result Page<br/>
     * Start page: settlementStatusSearchResultPage <br/>
     * End page: settlementStatusSearchResultPage<br/>
     * samples: settlement status - approve all <b>system draft</b> in settlement status search result<br/>
     * @param status can be one of following type:<br /><li>system draft</li><li>pending</li><li>authorised</li><li>pending release</li>
     * @throws Exception
     */
    @When("^settlement status - approve all (system draft|pending|authorised|pending release) in settlement status search result$")
    public void settlement_status_approve_all_status_in_settlement_status_search_result(String status) throws Exception{
        switch(status.toLowerCase()){
            case "system draft" :
                pageManager.settlementStatusSummaryPage.approveAllSystemDraft();
                break;
            case "pending" :
                pageManager.settlementStatusSummaryPage.approveAllPending();
                break;
            case "authorised" :
                pageManager.settlementStatusSummaryPage.approveAllAuthorised();
                break;
            case "pending release" :
                pageManager.settlementStatusSummaryPage.approveAllPendingRelease();
                break;
        }
    }

    /**
     * Description: click approve button in settlement Status Search Result Page<br/>
     * Start page: settlementStatusSearchResultPage <br/>
     * End page: settlementStatusSearchResultPage<br/>
     * samples: settlement status - approve all <b>system draft</b> in settlement status search result<br/>
     * @throws Exception
     */
    @When("^settlement status summary - click return to filter button$")
    public void settlement_status_summary_click_returnToFilter_button() throws Exception{
        pageManager.settlementStatusSummaryPage.returnToFilter();
    }


    /**
     * Description: select a asset type to view settlement status detail<br/>
     * Start page: settlementStatusSearchResultPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - show asset <b>my.settlementStatusSummary</b> details<br/>
     * @param id can be one of following type:<br /><li>{@link SettlementStatusSummary}</li>
     * @throws Exception
     */
    @When("^settlement status - show asset (\\S+) details$")
    public void settlement_status_show_asset_details(String id) throws Exception{
        SettlementStatusSummary settlementStatusSummary = testDataManager.getSettlementStatusSummary(id);
        testCase.embedTestData(settlementStatusSummary);
        pageManager.settlementStatusSummaryPage.enterAssetHoldingDetailPage(settlementStatusSummary);
    }

    /**
     * Description: tick events in settlement status detail page<br/>
     * Start page: settlementStatusDetailPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - tick bookings <b>my.settlementStatusDetail</b> in <b>cash movements</b> <br/>
     * @param ids can be one of following type:<br /><li>{@link SettlementStatusDetail}</li>
     * @throws Exception
     */
    @When("settlement status - tick bookings (\\S+) in (?:capital movements|cash movements|non cash)$")
    public void settlement_status_tick_bookings_in_type_movements(List<String> ids) throws Exception{
        for(String id : ids){
            SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(id);
            testCase.embedTestData(settlementStatusDetail);
            pageManager.settlementStatusPage.tickSettlementStatusSearchResult(settlementStatusDetail);
        }
    }

    @When("settlement status - tick all in (?:capital movements|cash movements|non cash)$")
    public void settlement_status_tick_all_in_type_movements() throws Exception{
        pageManager.settlementStatusPage.tickAll();
    }

    /**
     * Description: click approve button in settlement status detail page<br/>
     * Start page: settlementStatusDetailPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - approve all <b>system draft</b> in <b>capital movements</b> under item view<br/>
     * @param status can be one of following type:<br /><li>system draft</li><li>pending</li><li>authorised</li><li>pending release</li><li>ticked</li>
     * @param type can be one of following type:<br /><li>capital movements</li><li>cash movements</li><li>non cash</li>
     * @throws Exception
     */
    @When("^settlement status - approve all (system draft|pending|authorised|pending release|ticked) in (capital movements|cash movements|non cash) under item view$")
    public void settlement_status_approve_all_status_in_type_under_item_view(String status, String type) throws Exception{
        SettlementStatusDetail settlementStatusDetail = new SettlementStatusDetail();
        if(type.equalsIgnoreCase("non cash")){
            settlementStatusDetail.setInstrumentId(new StringType(""));
        }else{
            if(type.equalsIgnoreCase("capital movements"))
                settlementStatusDetail.setMovement(new StringType("Capitalise receive"));
            if(type.equalsIgnoreCase("pay"))
                settlementStatusDetail.setMovement(new StringType("pay"));
        }
        testCase.embedTestData(settlementStatusDetail);
        switch(status){
            case "system draft" :
                //pageManager.settlementStatusPage.approveAllSystemDraft();
                break;
            case "pending" :
                pageManager.settlementStatusPage.approveAllPending();
                break;
            case "authorised" :
                pageManager.settlementStatusPage.approveAllAuthorised();
                break;
            case "pending release" :
                pageManager.settlementStatusPage.approveAllPendingRelease();
                break;
            case "ticked" :
                pageManager.settlementStatusPage.approveAllTicked();
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: edit booking in settlement status detail page<br/>
     * Start page: settlementStatusDetailPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - edit booking <b>my.settlementStatusDetail</b> to <b>my.bookingDetail</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link SettlementStatusDetail}</li>
     * @param newId can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^settlement status - edit booking (\\S+) to (\\S+)$")
    public void settlement_status_edit_booking(String oriId, String newId) throws Exception{
        SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(oriId);
        BookingDetail bookingDetail = testDataManager.getBookingDetail(newId);
        testCase.embedTestData(settlementStatusDetail);
        testCase.embedTestData(bookingDetail);
        pageManager.settlementStatusPage.editSettlementStatusSearchResult(settlementStatusDetail);
        pageManager.agreementAssetsEditorPage.makeBooking(bookingDetail);
    }

    /**
     * Description: edit cash movement booking in settlement status detail page<br/>
     * Start page: settlementStatusDetailPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - edit cash movement booking <b>my.settlementStatusDetail</b> to <b>my.bookingDetail</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link SettlementStatusDetail}</li>
     * @param newId can be one of following type:<br /><li>{@link CashMovementDetail}</li>
     * @throws Exception
     */
    @When("^settlement status - edit cash movement booking (\\S+) to (\\S+)$")
    public void settlement_status_edit_cash_movement_booking(String oriId, String newId) throws Exception{
        SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(oriId);
        CashMovementDetail bookingDetail = testDataManager.getCashMovementBooking(newId);
        testCase.embedTestData(settlementStatusDetail);
        testCase.embedTestData(bookingDetail);
        pageManager.settlementStatusPage.editSettlementStatusSearchResult(settlementStatusDetail);
        pageManager.cashMovementEditorPage.setCashEditor(bookingDetail);
        pageManager.cashMovementEditorPage.save(bookingDetail);
        if (bookingDetail.getSettlementStatus() != null && !bookingDetail.getSettlementStatus().isEmpty()) {
            for (SettlementStatusType settlementStatusType : bookingDetail.getSettlementStatus()){
                pageManager.settlementStatusPage.editSettlementStatusSearchResult(settlementStatusDetail);
                pageManager.cashMovementEditorPage.changeSettlementStatus(settlementStatusType);
                pageManager.cashMovementEditorPage.save();
            }
        }
    }

    /**
     * Description: select net in settlement status detail page<br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^settlement status - net ticked (\\S+)$")
    public void settlement_status_net_ticked(String id) {
        throw new PendingException();
    }

    /**
     * Description: click net all button<br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^settlement status - net all$")
    public void settlement_status_net_all() {
        throw new PendingException();
    }

    /**
     * Description: click un-net button<br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @see
     * @throws Exception
     */
    @When("^settlement status - un-net$")
    public void settlement_status_un_net() {
        throw new PendingException();
    }

    /**
     * Description: click approve button under net view<br/>
     * Start page: settlementStatusNetViewPage <br/>
     * End page: settlementStatusNetViewPage<br/>
     * samples: settlement status - approve all <b>system draft</b> under net view<br/>
     * @param status can be one of following type:<br /><li>system draft</li><li>pending</li><li>authorised</li><li>pending release</li><li>ticked</li>
     * @throws Exception
     */
    @When("^settlement status - approve all (system draft|pending|authorised|pending release|ticked) under net view$")
    public void settlement_status_approve_all_status_under_net_view(String status) {
        throw new PendingException();
    }

    /**
     * Description: select net|item<br/>
     * Start page: settlementStatusDetailPage <br/>
     * End page: settlementStatusDetailPage<br/>
     * samples: settlement status - net view <b>my.settlementStatusDetail</b><br/>
     * @param id can be one of following type:<br /><li>{@link SettlementStatusDetail}</li>
     * @throws Exception
     */
    @When("^settlement status - (?:net|item) view (\\S+)$")
    public void settlement_status_type_view(String id) throws Exception{
        SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(id);
        pageManager.settlementStatusPage.setNetFormat(settlementStatusDetail);
        testCase.embedTestData(settlementStatusDetail);
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^settlement status - approve all pending in settlement status summary$")
    public void settlement_status_approve_all_pending_in_settlement_status_summary() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.settlementStatusSummaryPage.approveAllPending();
    }
    /**
     * Description: approve all system draft <br/>
     * Start page: settlement status summary page<br/>
     * End page: settlement status summary page<br/>
     * samples: settlement status - approve all system draft<br/>
     * @throws Exception
     */
    @When("^settlement status - approve all system draft in settlement status summary$")
    public void settlement_status_approve_all_system_draft_in_settlement_status_summary() throws Exception {
        pageManager.settlementStatusSummaryPage.approveAllSystemDraft();
    }

    /**
     * Description: approve all authorised <br/>
     * Start page: settlement status summary page <br/>
     * End page: settlement status summary page <br/>
     * samples: settlement status - approve all authorised <br/>
     * @throws Exception
     */
    @When("^settlement status - approve all authorised in settlement status summary$")
    public void settlement_status_approve_all_authorised_in_settlement_status_summary() throws Exception {
        pageManager.settlementStatusSummaryPage.approveAllAuthorised();
    }










    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link SettlementStatusDetail}</li>
     * @throws Exception
     */
    @When("^settlement status - (?:cash|cash movement) approve buttons (\\S+)$")
    public void settlement_status_approve_buttons(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            SettlementStatusDetail settlementStatusDetail = testDataManager.getSettlementStatusDetail(id);
            testCase.embedTestData(settlementStatusDetail);
            if (settlementStatusDetail.isTickAll() == null || !settlementStatusDetail.isTickAll() ) {
                pageManager.settlementStatusPage.tickSettlementStatusSearchResult(settlementStatusDetail);


            }
            else if (settlementStatusDetail.isTickAll()){
                pageManager.settlementStatusPage.tickAll();

            }

            pageManager.settlementStatusPage.approveAllTicked();
            if(settlementStatusDetail.getAlertHandling() != null && !settlementStatusDetail.getAlertHandling().isEmpty())
                pageManager.settlementStatusPage.handleAlters(settlementStatusDetail.getAlertHandling());
        }


    }

    /**
     * Description: set collateral preferences<br/>
     * Start page: collateralPreferencePage <br/>
     * End page: collateralPreferencePage<br/>
     * samples: set collateral preferences <b>my.collateral.preference</b><br/>
     * @param id can be one of following type:<br /><li>{@link CollateralPreference}</li>
     * @throws Exception
     */
    @When("^set collateral preferences (\\S+)$")
    public void set_collateral_preferences(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CollateralPreference collateralPreference = testDataManager.getCollateralPreference(id);
        testCase.embedTestData(collateralPreference);
        pageManager.collateralPreferencesPage.setCollateralPreference(collateralPreference);
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @throws Exception
     */
    @When("^stp - view In Progress Statements for All Agreements (\\S+)$")
    public void stp_view_in_progress_statements_for_all_agreements(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @throws Exception
     */
    @When("^stp - view Stale Approval Statements for All Agreements (\\S+)$")
    public void stp_view_stale_approval_statements_for_all_agreements(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @throws Exception
     */
    @When("^stp - view Pending Approval Statements for All Agreements (\\S+)$")
    public void stp_view_pending_approval_statements_for_all_agreements(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view Amended Statements for All Agreements (\\S+)$")
    public void stp_view_amended_statements_for_all_agreements(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view Approved Statements for All Agreements (\\S+)$")
    public void stp_view_approved_statements_for_all_agreements(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view In Progress Statements for Rules Failed (\\\\S+)$")
    public void stp_view_in_progress_statements_for_rules_failed(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view Stale Approval Statements for Rules Failed (\\S+)$")
    public void stp_view_stale_approval_statements_for_rules_failed(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view Pending Approval Statements for Rules Failed (\\S+)$")
    public void stp_view_pending_approval_statements_for_rules_failed(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("stp - view Amended Statements for Rules Failed (\\S+)$")
    public void stp_view_amended_statements_for_rules_failed(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("stp - view Approved Statements for Rules Failed (\\S+)$")
    public void stp_view_approved_statements_for_rules_failed(List<String> ids) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - add (?:quality|cutoff|margin|cutoff quality) rules? (\\S+)$")
    public void stp_add_quality_cutoff_margin_cutoff_quality_rules(List<String> ids) {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            //TODO need to add STP related page and class
        }
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - add business rules? (\\S+)$")
    public void stp_add_business_rules(List<String> ids) {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            //TODO need to add STP related page and class
        }
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - search business rules (\\S+)$")
    public void stp_search_business_rules(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - edit business rules (\\S+) to (\\S+)$")
    public void stp_edit_business_rules_to(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: set stp switch<br/>
     * Start page: stp configuration page <br/>
     * End page: stp configuration page<br/>
     * samples: stp - set stp switch <b>my.stpConfiguration</b><br/>
     * @param id can be one of following type:<br /><li>{@link StpConfiguration}</li>
     * @throws Exception
     */
    @When("^stp - set stp switch (\\S+)$")
    public void stp_set_stp_switch(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StpConfiguration stpConfiguration = testDataManager.getStpConfiguration(id);
        testCase.embedTestData(stpConfiguration);
        pageManager.stpConfigurationsPage.editStpConfiguation(stpConfiguration);
        pageManager.stpConfigurationsPage.save();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - search stp status search (\\S+)$")
    public void stp_search_stp_status_search(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view in progress statements for all agreements (\\S+)$")
    public void stp_view_in_progress_statements_for_all_agreements(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view stale approval statements for all agreements (\\S+)$")
    public void stp_view_stale_approval_statements_for_all_agreements(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view pending approval statements for all agreements (\\S+)$")
    public void stp_view_pending_approval_statements_for_all_agreements(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view amended statements for all agreements (\\S+)$")
    public void stp_view_amended_statements_for_all_agreements(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view approved statements for all agreements (\\S+)$")
    public void stp_view_approved_statements_for_all_agreements(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view in progress statements for rules failed (\\S+)$")
    public void stp_view_in_progress_statements_for_rules_failed(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view stale approval statements for rules failed (\\S+)$")
    public void stp_view_stale_approval_statements_for_rules_failed(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view pending approval statements for rules failed (\\S+)$")
    public void stp_view_pending_approval_statements_for_rules_failed(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view amended statements for rules failed (\\S+)$")
    public void stp_view_amended_statements_for_rules_failed(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^stp - view approved statements for rules failed (\\S+)$")
    public void stp_view_approved_statements_for_rules_failed(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add STP related page and class
        throw new PendingException();
    }

    /**
      * Description: feed agreement<br/>
      * Start page: anyPage<br/>
      * End page: feed agreement page<br/>
      * samples: feed agreements <b>my.agreement.feed.file</b> by <b>xml</b><br/>
      * @param ids can be one of following type:<br /><li>{@link FeedAgreement}</li>
      * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
      * @throws Exception
      */
    @When("^feed agreements? (\\S+) by (xml|excel|xlsx)$")
    public void feed_agreements(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_AGREEMENTS);
        List<FeedAgreement> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedAgreement(id));
            testCase.embedTestData(testDataManager.getFeedAgreement(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }

    /**
      * Description: feed agreement rating<br/>
      * Start page: anyPage<br/>
      * End page: feed agreement ratings page<br/>
      * samples: feed agreement rating <b>my.agreementrating.feed.file</b> by <b>xml</b><br/><br/>
      * @param ids can be one of following type:<br /><li>{@link FeedAgreementRating}</li>
      * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
      * @throws Exception
      */
    @When("^feed agreement ratings (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_agreement_ratings(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_AGREEMENT_RATINGS);
        List<FeedAgreementRating> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedAgreementRating(id));
            testCase.embedTestData(testDataManager.getFeedAgreementRating(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
      * Description: feed agreement UDF<br/>
      * Start page: anyPage<br/>
      * End page: feed agreement UDF page<br/>
      * samples: feed agreement UDF <b>my.agreementUDF.feed.file</b> by <b>xml</b><br/><br/>
      * @param ids can be one of following type:<br /><li>{@link FeedAgreementUDF}</li>
      * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
      * @throws Exception
      */
    @When("^feed agreement UDF (\\S+) by (xml|excel|xlsx)$")
    public void feed_agreement_UDF(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_AGREEMENT_UDF);
        List<FeedAgreementUDF> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedAgreementUDF(id));
            testCase.embedTestData(testDataManager.getFeedAgreementUDF(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed asset booking<br/>
     * Start page: anyPage<br/>
     * End page: feed asset booking page<br/>
     * samples: feed asset booking <b>my.asset.booking</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedAssetBooking}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @When("^feed asset bookings? (\\S+) by (xml|excel|csv)$")
    public void feed_asset_bookings(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ASSET_BOOKINGS);
        List<FeedAssetBooking> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedAssetBooking(id));
            testCase.embedTestData(testDataManager.getFeedAssetBooking(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed asset pricing<br/>
     * Start page: anyPage<br/>
     * End page: feed asset pricing page<br/>
     * samples: feed asset pricing <b>my.asset.pricing</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedAssetPricing}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed asset pricings? (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_asset_pricings(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ASSET_PRICING);
        List<FeedAssetPricing> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedAssetPricing(id));
            testCase.embedTestData(testDataManager.getFeedAssetPricing(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed asset rating<br/>
     * Start page: anyPage<br/>
     * End page: feed asset rating page<br/>
     * samples: feed asset rating <b>my.asset.rating</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedAssetRating}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed asset ratings? (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_asset_ratings(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ASSET_RATINGS);
        List<FeedAssetRating> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedAssetRating(id));
            testCase.embedTestData(testDataManager.getFeedAssetRating(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed counterparty amount<br/>
     * Start page: anyPage<br/>
     * End page: feed counterparty amount page<br/>
     * samples: feed counterparty amount <b>my.counterparty.amount</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedCounterpartyAmount}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed counterparty amount (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_counterparty_amount(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_COUNTERPARTY_AMOUNT);
        List<FeedCounterpartyAmount> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedCounterpartyAmount(id));
            testCase.embedTestData(testDataManager.getFeedCounterpartyAmount(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed eligibility rules template<br/>
     * Start page: anyPage<br/>
     * End page: feed eligibility rules template page<br/>
     * samples: feed eligibility rules template <b>my.eligibility.rule.template</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedEligibilityRulesTemplate}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed eligibility rules templates? (\\S+) by (xml|excel)$")
    public void feed_eligibility_rules_templates(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ELIGIBILITY_RULES_TEMPLATE);
        List<FeedEligibilityRulesTemplate> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedEligibilityRulesTemplate(id));
            testCase.embedTestData(testDataManager.getFeedEligibilityRulesTemplate(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed external IA<br/>
     * Start page: anyPage<br/>
     * End page: feed external IA page<br/>
     * samples: feed external IA <b>my.external.IA</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedExternalIA}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @When("^feed external IA (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_external_ia(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_EXTERNAL_IA);
        List<FeedExternalIA> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedExternalIA(id));
            testCase.embedTestData(testDataManager.getFeedExternalIA(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed FX rate<br/>
     * Start page: anyPage<br/>
     * End page: feed FX rate page<br/>
     * samples: feed FX rate <b>my.fx.rate</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedFxRate}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @throws Exception
     */
    @When("^feed FX rates (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_fx_rates(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_FX_RATES);
        List<FeedFxRate> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedFxRate(id));
            testCase.embedTestData(testDataManager.getFeedFxRate(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed interest rate<br/>
     * Start page: anyPage<br/>
     * End page: feed interest rate page<br/>
     * samples: feed interest rate <b>my.interest.rate</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedInterestRate}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed interest rates (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_interest_rates(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_INTEREST_RATES);
        List<FeedInterestRate> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedInterestRate(id));
            testCase.embedTestData(testDataManager.getFeedInterestRate(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed interest amount<br/>
     * Start page: anyPage<br/>
     * End page: feed interest amount page<br/>
     * samples: feed interest amount <b>my.interest.amount</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedInterestAmount}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @When("^feed interest amount (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_interest_amount(List<String> ids, String type) throws Exception{
        pageManager.welcomePage.navigate(Menu.FEED_INTEREST_AMOUNT);
        List<FeedInterestAmount> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedInterestAmount(id));
            testCase.embedTestData(testDataManager.getFeedInterestAmount(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
        }
    }
    /**
     * Description: feed inventory manager<br/>
     * Start page: anyPage<br/>
     * End page: feed inventory manager page<br/>
     * samples: feed inventory manager <b>my.inventory.manager</b> by <b>xml</b><br/>
     * @param mode can be one of following value:<br /><li>flush</li><li>delta</li>
     * @param ids can be one of following type:<br /><li>{@link FeedInventoryManager}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed (flush|delta) inventory manager (\\S+) by (xml|excel)$")
    public void feed_Inventory_Manager(String mode, List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_INVENTORY_MANAGER);
        List<FeedInventoryManager> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedInventoryManager(id));
            testCase.embedTestData(testDataManager.getFeedInventoryManager(id));
        }
        Feed feed = new Feed();
        if(mode.toLowerCase().equals("flush")){
            feed.setDeltaMode(DeltaModeType.FLUSH);
        }else if(mode.toLowerCase().equals("delta")){
            feed.setDeltaMode(DeltaModeType.DELTA);
        }
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed mtm<br/>
     * Start page: anyPage<br/>
     * End page: feed mtm page<br/>
     * samples: feed mtm <b>my.mtm</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedMtM}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed mtm (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_mtm(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_MTM);
        List<FeedMtM> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedMtM(id));
            testCase.embedTestData(testDataManager.getFeedMtM(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed NAV<br/>
     * Start page: anyPage<br/>
     * End page: feed NAV page<br/>
     * samples: feed NAV <b>my.NAV</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedNAV}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed NAV (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_nav(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_NAV);
        List<FeedNAV> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedNAV(id));
            testCase.embedTestData(testDataManager.getFeedNAV(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed portfolio TSA<br/>
     * Start page: anyPage<br/>
     * End page: feed portfolio TSA page<br/>
     * samples: feed portfolio TSA <b>my.portfolio.TSA</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedPortfolioTSA}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed portfolio (TSA|Cashflow) (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_portfolio_tsa(String category, List<String> ids, String type) throws Exception {
        pageManager.welcomePage.navigate(Menu.FEED_PORTFOLIOCASHFLOW);
        List<FeedPortfolioTSA> list = new ArrayList<>();
        for(String id : ids){
            switch (category.toLowerCase()){
                case "tsa":
                    list.add(testDataManager.getFeedPortfolioTSA(id));
                    testCase.embedTestData(testDataManager.getFeedPortfolioTSA(id));
                    break;
                case "cashflow":
                    list.add(testDataManager.getFeedPortfolioCashflow(id));
                    testCase.embedTestData(testDataManager.getFeedPortfolioCashflow(id));
                    break;
            }
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed repo mtm<br/>
     * Start page: anyPage<br/>
     * End page: feed repo mtm page<br/>
     * samples: feed repo mtm <b>my.repo.mtm</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedMtM}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed repo mtm (\\S+) by (xml|excel|csv)$")
    public void feed_repo_mtm(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_REPO_MTM);
        List<FeedMtM> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedMtM(id));
            testCase.embedTestData(testDataManager.getFeedMtM(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed repo settlement<br/>
     * Start page: anyPage<br/>
     * End page: feed repo settlement page<br/>
     * samples: feed repo settlement <b>my.repo.settlement</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedRepoSettlement}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @When("^feed repo settlement (\\S+) by (xml|excel|csv)$")
    public void feed_repo_settlement(List<String> ids, String type) throws Exception{
        pageManager.welcomePage.navigate(Menu.FEED_REPO_SETTLEMENT);
        List<FeedRepoSettlement> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedRepoSettlement(id));
            testCase.embedTestData(testDataManager.getFeedRepoSettlement(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed security templates<br/>
     * Start page: anyPage<br/>
     * End page: feed repo settlement page<br/>
     * samples: feed security templates <b>my.security.templates</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedSecurityTemplate}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed security templates (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_security_templates(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_SECURITY_TEMPLATES);
        List<FeedSecurityTemplate> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedSecurityTemplate(id));
            testCase.embedTestData(testDataManager.getFeedSecurityTemplate(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed settlement instruction<br/>
     * Start page: anyPage<br/>
     * End page: feed settlement instruction page<br/>
     * samples: feed settlement instruction <b>my.settlement.instruction</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedSettlementInstruction}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed settlement instruction (\\S+) by (xml|excel)$")
    public void feed_settlement_instruction(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_SETTLEMENT_INSTRUCTION);
        List<FeedSettlementInstruction> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedSettlementInstruction(id));
            testCase.embedTestData(testDataManager.getFeedSettlementInstruction(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed ETF trades<br/>
     * Start page: anyPage<br/>
     * End page: feed ETF trades page<br/>
     * samples: feed ETF trades <b>my.ETF.trads</b> by <b>xml</b><br/>
     * @param mode can be one of following value:<br /><li>flush</li><li>delta</li>
     * @param ids can be one of following type:<br /><li>{@link FeedETFTrade}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^feed (delta|flush) (ETF|Repo) trades (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_etf_repo_trades(String mode, String tradeType, List<String> ids, String type) throws Exception {
        pageManager.welcomePage.navigate(Menu.FEED_REPO_ETF_SBL_TRADES);
        List list = new ArrayList();
        switch(tradeType){
            case "ETF" :
                for(String id : ids){
                    list.add(testDataManager.getFeedETFTrade(id));
                    testCase.embedTestData(testDataManager.getFeedETFTrade(id));
                }
                break;
            case "Repo" :
                for(String id: ids){
                    list.add(testDataManager.getFeedRepoTrade(id));
                    testCase.embedTestData(testDataManager.getFeedRepoTrade(id));
                }
                break;
        }
        Feed feed = new Feed();
        if(mode.toLowerCase().equals("flush")){
            feed.setDeltaMode(DeltaModeType.FLUSH);
        }else if(mode.toLowerCase().equals("delta")){
            feed.setDeltaMode(DeltaModeType.DELTA);
        }
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }
//    /**
//     * Description: feed repo trades<br/>
//     * Start page: anyPage<br/>
//     * End page: feed repo trades page<br/>
//     * samples: feed repo trades <b>my.repo.trades</b> by <b>xml</b><br/>
//     * @param mode can be one of following value:<br /><li>flush</li><li>delta</li>
//     * @param ids can be one of following type:<br /><li>{@link FeedRepoTrade}</li>
//     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
//     * @throws Exception
//     */
//    @When("^feed (delta|flush) repo trades (\\S+) by (xml|excel|csv)$")
//    public void feed_repo_trades(String mode, List<String> ids, String type) throws Exception {
//        pageManager.welcomePage.navigate(Menu.FEED_REPO_ETF_SBL_TRADES);
//        List<FeedRepoTrade> list = new ArrayList<FeedRepoTrade>();
//        for(String id : ids){
//            list.add(testDataManager.getFeedRepoTrade(id));
//            testCase.embedTestData(testDataManager.getFeedRepoTrade(id));
//        }
//        Feed feed = new Feed();
//        if(mode.toLowerCase().equals("flush")){
//            feed.setDeltaMode(DeltaModeType.FLUSH);
//        }else if(mode.toLowerCase().equals("delta")){
//            feed.setDeltaMode(DeltaModeType.DELTA);
//        }
//        switch(type.toLowerCase()){
//            case "xml" :
//                pageManager.taskManagerPage.feedXmlFile(feed, list);
//                break;
//            case "excel" :
//                pageManager.taskManagerPage.feedXlsFile(feed, list);
//                break;
//            case "csv" :
//                pageManager.taskManagerPage.feedCsvFile(feed, list);
//                break;
//        }
//    }
    /**
     * Description: feed trades<br/>
     * Start page: anyPage<br/>
     * End page: feed trades page<br/>
     * samples: feed trades <b>my.trades</b> by <b>xml</b><br/>
     * @param mode can be one of following value:<br /><li>flush</li><li>delta</li>
     * @param ids can be one of following type:<br /><li>{@link FeedTrade}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li><li>csv</li>
     * @throws Exception
     */
    @When("^feed (delta|flush) trades (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_trades_by(String mode, List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_TRADES);
        List<FeedTrade> list = new ArrayList<>();
        for(String id : ids){
            list.add(testDataManager.getFeedTrade(id));
            testCase.embedTestData(testDataManager.getFeedTrade(id));
        }
        Feed feed = new Feed();
        if(mode.toLowerCase().equals("flush")){
            feed.setDeltaMode(DeltaModeType.FLUSH);
        }else if(mode.toLowerCase().equals("delta")){
            feed.setDeltaMode(DeltaModeType.DELTA);
        }
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: Exposure Management - search event by <li>my.exposureEventSearch</li><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
//    @When("^Exposure Management - search event by (\\S+)$")
    public void exposure_Management_search_event_by(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        ExposureEventSearch exposureEventSearch = testDataManager.getExposureEventSearch(id);
        testCase.embedTestData(exposureEventSearch);
        pageManager.exposureManagementPage.searchEvent(exposureEventSearch);
    }

    /**
     * Description: change EM event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - set event <b>my.old.event</b> value to <b>my.new.event</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @param newId can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - set event (\\S+) value to (\\S+)$")
    public void exposure_Management_set_event_value_to(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        ExposureEventDetail oriExposureEventDetail = testDataManager.getExposureEventDetail(oriId);
        ExposureEventDetail newExposureEventDetail1 = testDataManager.getExposureEventDetail(newId);
        testCase.embedTestData(oriExposureEventDetail);
        testCase.embedTestData(newExposureEventDetail1);
        pageManager.exposureManagementPage.changeExposureManagement(oriExposureEventDetail, newExposureEventDetail1);
    }

    /**
     * Description: add bulk booking<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage <br/>
     * samples: add <b>call</b> booking - bulk booking <b>my.bulkbooking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^add (?:call|return|delivery|recall|pay|receive)? ?booking - bulk booking (\\S+)$")
    public void add_bulk_booking(List<String> bookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<BookingDetail> bookingDetails = new ArrayList<>();
        for(String bookingId : bookingIds){
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            bookingDetails.add(bookingDetail);
            testCase.embedTestData(bookingDetail);
        }
        pageManager.exposureManagementPage.enterBulkBooking();
        if (bookingDetails.get(0) != null){
            if (bookingDetails.get(0).getBookingInformation() != null){
                if (bookingDetails.get(0).getBookingInformation().getBookingInformationDetail() != null &&
                        bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().size() > 0){
                    if(bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementDiscription() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getPrincipalShortName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getCounterpartyShortName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementId() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getBookingMovement() == null){
                        pageManager.exposureManagementPage.makeBulkBooking(bookingDetails);
                    }else{
                        pageManager.exposureManagementPage.makeBulkBookingForMultiEvent(bookingDetails);
                    }
                }
            }
        }
    }

    /**
     * Description: Edit bulk booking<br/>
     * Start page: exposureManagementPage<br/>
     * End page: exposureManagementPage <br/>
     * samples: Edit booking - bulk booking <b>my.bulkbooking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^Edit booking - bulk booking (\\S+) to (\\S+)$")
    public void Edit_bulk_booking_to(List<String> bookingIds, List<String> newbookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<BookingDetail> bookingDetails = new ArrayList<>();
        List<BookingDetail> newbookingDetails = new ArrayList<>();

        for (String bookingId : bookingIds) {
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            bookingDetails.add(bookingDetail);
            testCase.embedTestData(bookingDetail);
        }

        /**when different column need be used to locate event and also input the info*/

        for (String newbookingId : newbookingIds) {
            BookingDetail newbookingDetail = testDataManager.getBookingDetail(newbookingId);
            newbookingDetails.add(newbookingDetail);
            testCase.embedTestData(newbookingDetail);
        }

        /**when same column need be used to locate event and also input the info,
         * but movement can be set in both old bookingdetail and new bookingdetail in data file, since xpath will be changed after change the movement */
        pageManager.exposureManagementPage.enterBulkBookingForComplexMultiEvents(bookingDetails,newbookingDetails);

//       pageManager.exposureManagementPage.enterBulkBooking();
    }

    /**
     * Description: Edit bulk booking<br/>
     * Start page: exposureManagementPage<br/>
     * End page: exposureManagementPage <br/>
     * samples: Edit booking - bulk booking <b>my.bulkbooking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^Edit booking - bulk booking (\\S+)$")
    public void Edit_bulk_booking(List<String> bookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        List<BookingDetail> bookingDetails = new ArrayList<>();

        for (String bookingId : bookingIds) {
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            bookingDetails.add(bookingDetail);
            testCase.embedTestData(bookingDetail);
        }

        /**when different column need be used to locate event and also input the info*/
        if (bookingDetails.get(0) != null) {
            if (bookingDetails.get(0).getBookingInformation() != null) {
                if (bookingDetails.get(0).getBookingInformation().getBookingInformationDetail() != null &&
                        bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().size() > 0) {
                    if (bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementDiscription() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getPrincipalShortName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getCounterpartyShortName() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getAgreementId() == null &&
                            bookingDetails.get(0).getBookingInformation().getBookingInformationDetail().get(0).getBookingMovement() == null) {
                        pageManager.exposureManagementPage.enterBulkBookingInfo(bookingDetails);  //only for single booking
                    } else {
                        pageManager.exposureManagementPage.enterBulkBookingForMultiEvents(bookingDetails); //for mutiple bookings
                    }
                }
            }
        }




    }

    /**
     * Description: alert message in EM Bulk <br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - group booking-<br/>
     * @throws Exception
     */

    @When("^messageHandler - alert message (\\S+)$")
    public void click_ok_alert_message(String id) throws Exception {
        MessageHandler messageHandler = testDataManager.getMessageHandler(id);
        testCase.embedTestData(messageHandler);
        pageManager.abstractCollinePage.clickAndCheckMessage(messageHandler);
    }


    @When("^Exposure Management - override warning (\\S+)$")
    public void override_warning(String id) throws Exception {
        ExposureManagementMessage exposureManagementMessage = testDataManager.getExposureManagementMessage(id);
        testCase.embedTestData(exposureManagementMessage);
        pageManager.exposureManagementPage.overrideWarningMeassage(exposureManagementMessage);
    }


    /**
     * Description: add booking<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: add <b>call</b> booking - statement booking <b>my.booking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^add (?:call|return|delivery|recall|wire in|wire out|pledge in|pledge out)? bookings? - statement booking (\\S+)$")
    public void add_statement_booking(List<String> bookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String bookingId : bookingIds){
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            testCase.embedTestData(bookingDetail);
            pageManager.agreementAssetsSummaryPage.enterBookingEditor(bookingDetail);
            pageManager.agreementAssetsEditorPage.makeBooking(bookingDetail);
        }
    }

    /**
     * Description: search Existing Instrument<br/>
     * Start page: bookingSummaryPage <br/>
     * End page: bookingSummaryPage<br/>
     * samples: add <b>call</b> booking - statement booking <b>my.booking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */

    @When("^search Existing instrument - statement booking (\\S+)$")
    public void search_statement_booking(List<String> bookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String bookingId : bookingIds){
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            testCase.embedTestData(bookingDetail);
            pageManager.agreementAssetsSummaryPage.InstrumentSearch(bookingDetail);
        }
    }

    /**
     * Description: add cash movement booking<br/>
     * Start page: cashmovementSummaryPage <br/>
     * End page: cashmovementSummaryPage<br/>
     * samples: add <b>net interest</b> booking - statement booking <b>my.cashmovement.booking</b><br/>
     * @param id can be one of following type:<br /><li>{@link CashMovementDetail}</li>
     * @throws Exception
     */
    @When("^add (?:net interest|vm interest|im interest|TSA|Cashflow|Fee|Cash Component) booking - statement booking (\\S+)$")
    public void add_cash_movement_booking(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        CashMovementDetail cashMovementDetail = testDataManager.getCashMovementBooking(id);
        testCase.embedTestData(cashMovementDetail);
        pageManager.cashMovementSummaryPage.addInsterestBooking();
        pageManager.cashMovementEditorPage.setCashEditor(cashMovementDetail);
        pageManager.cashMovementEditorPage.save(cashMovementDetail);
    }

    /**
     * Description: make booking in EM page <br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: add <b>call</b> booking - group booking <b>my.groupbooking</b><br/>
     * @param bookingIds can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^add (?:call|return|delivery|recall)? bookings? - group booking (\\S+)$")
    public void add_group_booking(List<String> bookingIds) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterGroupBooking();
        for (String bookingId : bookingIds) {
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            testCase.embedTestData(bookingDetail);
            pageManager.agreementAssetHoldingsSummaryPage.enterAgreementAssetsSummary(bookingDetail);
            pageManager.agreementAssetsSummaryPage.enterBookingEditor(bookingDetail);
            pageManager.agreementAssetsEditorPage.makeBooking(bookingDetail);
        }
    }

    /**
     * Description: click make booking button in EM page <br/>
     * Start page: exposureManagementPage <br/>
     * End page: agreement asset holding summary page<br/>
     * samples: Exposure Management - group booking<br/>
     * @throws Exception
     */
    @When("^Exposure Management - group booking$")
    public void add_group_booking() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterGroupBooking();
    }
    /**
     * Description: make instrument booking in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: add <b>call</b> booking - instrument booking <b>my.instrumentBooking</b><br/>
     * @param bookingId can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @throws Exception
     */
    @When("^add (?:call|return|delivery|recall)? booking - instrument booking (\\S+)$")
    public void add_instrument_booking(String bookingId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
        testCase.embedTestData(bookingDetail);
        pageManager.exposureManagementPage.enterInstrumentBooking();
        pageManager.exposureManagementPage.makeInstrumentBooking(bookingDetail);
        pageManager.agreementAssetsEditorPage.makeBooking(bookingDetail);
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^add (?:call|return|delivery|recall)? booking - auto booking (\\S+) for events? (\\S+)$")
    public void add_auto_booking_for_events(List<String> ids) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that click the auto booking button in the ExposureManagementPage
        throw new PendingException();
    }

    /**
     * Description: Click Agreement Admin and download letter<br/>
     * Start page: Agreement Statement page <br/>
     * End page: Agreement Statement page <br/>
     * samples: Agreement Admin - manual download <b>margin call</b> letter <b>my.marginLetter</b> and save file to <b>my.file</b><br/>
     * @param letterId can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @param pathId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^Agreement Admin - manual download (?:margin call|margin return|margin delivery|margin recall|margin call&recall|margin delivery&return|full dispute|UDL|statement notification|substitution request|substitution confirmation)? ?letter (\\S+) and save file to (\\S+)$")
    public void agreement_admin_manual_save_letter(String letterId, String pathId) throws Exception{
        pageManager.agreementStatementPage.enterAgreementAdmin();
        MarginLetter marginLetter = testDataManager.getLetter(letterId);
        StringType filePath = testDataManager.getVariable(pathId);
        testCase.embedTestData(marginLetter);
        testCase.embedTestData(filePath);
        if(marginLetter.getPortfolioDetails() != null){
            PortfolioDetails portfolioDetails = marginLetter.getPortfolioDetails();
            pageManager.portfolioDetailsPage.setupDiaryEvents(portfolioDetails.getDiaryEvents());
            pageManager.portfolioDetailsPage.setupNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
        }
        pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
        pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
        pageManager.letterSetupPage.enterNext();

        if(marginLetter.getRequestTypeAndAssetSelection() != null){
            if(marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null){
                for(EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()){
                    pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                    if(eligibleAssetSelection.getMovementAssets() != null && ! eligibleAssetSelection.getMovementAssets().isEmpty()){
                        for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                            if(movementAsset.getInstrumentSelectionSearch() != null
                                    && movementAsset.getInstrumentSelectionSearchResult() != null){
                                if(!eligibleAssetSelection.getMovementType().getRealValue().contains("Sub")) {
                                    pageManager.requestTypeAndAssetSelectionPage.enterLetterInstrumentSearchPage();
                                    pageManager.letterInstrumentSearchPage.setInstrumentSearch(movementAsset.getInstrumentSelectionSearch());
                                    pageManager.letterInstrumentSearchPage.searchInstrument();
                                    pageManager.letterInstrumentSearchPage.selectInstrumentResult(movementAsset.getInstrumentSelectionSearchResult());
                                }else{
                                    pageManager.requestTypeAndAssetSelectionPage.searchInstrument(movementAsset);
                                    pageManager.requestTypeAndAssetSelectionPage.selectInstrument(movementAsset);
//                                    if(movementAsset.getEligibleAsset() != null) {
//                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
//                                        if(movementAsset.getEligibleAssetSelectionDetail() != null)
//                                            pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
//                                    }
                                }
                            }
                                    if(movementAsset.getEligibleAsset() != null) {
                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
                                    }
                                if(movementAsset.getEligibleAssetSelectionDetail() != null)
                                    pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
                            }
                        }
                    pageManager.requestTypeAndAssetSelectionPage.bookAll(eligibleAssetSelection);
                    pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                }
            }
        }
        pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

        pageManager.letterSummaryPage.accept();
        pageManager.finalLetterReviewAndSubmissionMethodPage.download(filePath);
        pageManager.finalLetterReviewAndSubmissionMethodPage.switchToLetterSummary();
        pageManager.letterSummaryPage.switchToAgreementStatementPage();
    }

    /**
     * Description: Click Agreement Admin and setup letter<br/>
     * Start page: Agreement Statement page <br/>
     * End page: Agreement Statement page <br/>
     * samples: Agreement Admin - manual send <b>margin call</b> letter <b>my.marginLetter</b><br/>
     * @param id can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @throws Exception
     */
    @When("^Agreement Admin - manual send (?:margin call|margin return|margin delivery|margin recall|margin call&recall|margin delivery&return|full dispute|UDL|statement notification|substitution request|substitution confirmation)? ?letter ?(\\S+)$")
    public void agreement_admin_manual_send_letter(String id) throws Exception{
        pageManager.agreementStatementPage.enterAgreementAdmin();
        MarginLetter marginLetter = testDataManager.getLetter(id);
        testCase.embedTestData(marginLetter);
        if(marginLetter.getPortfolioDetails() != null){
            PortfolioDetails portfolioDetails = marginLetter.getPortfolioDetails();
            pageManager.portfolioDetailsPage.setupDiaryEvents(portfolioDetails.getDiaryEvents());
            pageManager.portfolioDetailsPage.setupNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
        }
        pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
        pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
        pageManager.letterSetupPage.enterNext();

        if(marginLetter.getRequestTypeAndAssetSelection() != null){
            if(marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null){
                for(EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()){
                    pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                    if(eligibleAssetSelection.getMovementAssets() != null && ! eligibleAssetSelection.getMovementAssets().isEmpty()){
                        for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                            if(movementAsset.getInstrumentSelectionSearch() != null
                                    && movementAsset.getInstrumentSelectionSearchResult() != null){
                                if(!eligibleAssetSelection.getMovementType().getRealValue().contains("Sub")) {
                                    pageManager.requestTypeAndAssetSelectionPage.enterLetterInstrumentSearchPage();
                                    pageManager.letterInstrumentSearchPage.setInstrumentSearch(movementAsset.getInstrumentSelectionSearch());
                                    pageManager.letterInstrumentSearchPage.searchInstrument();
                                    pageManager.letterInstrumentSearchPage.selectInstrumentResult(movementAsset.getInstrumentSelectionSearchResult());
                                }else{
                                    pageManager.requestTypeAndAssetSelectionPage.searchInstrument(movementAsset);
                                    pageManager.requestTypeAndAssetSelectionPage.selectInstrument(movementAsset);
//                                    if(movementAsset.getEligibleAsset() != null) {
//                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
//                                        if(movementAsset.getEligibleAssetSelectionDetail() != null)
//                                            pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
//                                    }
                                }
                            }
                                    if(movementAsset.getEligibleAsset() != null) {
                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
                                    }
                                if(movementAsset.getEligibleAssetSelectionDetail() != null)
                                    pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
                            }
                        }
                    pageManager.requestTypeAndAssetSelectionPage.bookAll(eligibleAssetSelection);
                    pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                }
            }
        }
        pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

        pageManager.letterSummaryPage.accept();
        pageManager.finalLetterReviewAndSubmissionMethodPage.email();
        pageManager.letterAdminPage.switchToAgreementStatementPage();
    }

    /**
     * Description: Manual send letter from Agreement Admin page<br/>
     * Start page: Agreement Admin page<br/>
     * End page: one of the following pages<br /><li>portfolio details</li><li>letter setup</li><li>request type and asset selection</li><li>letter summary</li><li>letter review and submission method</li><li>letter admin</li><br/>
     * samples: Agreement Admin - manual send margin call letter <b>margin.letter</b> for event em.event to portfolio details page<br/>
     * @param id can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @param pageName can be one of following value:<br /><li>portfolio details</li><li>letter setup</li><li>request type and asset selection</li><li>letter summary</li><li>letter review and submission method</li><li>letter admin</li>
     * @throws Exception
     */
    @When("^Agreement Admin - manual send (Margin Call|Margin Return|Margin Delivery|Margin Recall|Margin Call&Recall|Margin Delivery&return|full dispute|UDL|statement notification|substitution request|substitution confirmation)? letter (\\S+) to (portfolio details|letter setup|request type and asset selection|letter summary|letter review and submission method|letter admin) page$")
    public void agreement_admin_manual_send_for_event_to_stage(String letterType,String id, String pageName) throws Exception {
        pageManager.agreementStatementPage.enterAgreementAdmin();
        pageManager.letterSetupPage.selectLetterType(letterType);
        MarginLetter marginLetter = testDataManager.getLetter(id);
        testCase.embedTestData(letterType);
        testCase.embedTestData(marginLetter);
        if(pageName != null) {
            if (marginLetter.getPortfolioDetails() != null) {
                PortfolioDetails portfolioDetails = marginLetter.getPortfolioDetails();
                pageManager.portfolioDetailsPage.setupDiaryEvents(portfolioDetails.getDiaryEvents());
                pageManager.portfolioDetailsPage.setupNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
            }
            if(!pageName.equalsIgnoreCase("portfolio details")) {
                pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
                pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
                if(!pageName.equalsIgnoreCase("letter setup")) {
                    pageManager.letterSetupPage.enterNext();

                    if(marginLetter.getRequestTypeAndAssetSelection() != null){
                        if(marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null){
                            for(EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()){
                                pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                                if(eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()){
                                    for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                                        if(movementAsset.getInstrumentSelectionSearch() != null
                                                && movementAsset.getInstrumentSelectionSearchResult() != null){
                                            if(!eligibleAssetSelection.getMovementType().getRealValue().contains("Sub")) {
                                                pageManager.requestTypeAndAssetSelectionPage.enterLetterInstrumentSearchPage();
                                                pageManager.letterInstrumentSearchPage.setInstrumentSearch(movementAsset.getInstrumentSelectionSearch());
                                                pageManager.letterInstrumentSearchPage.searchInstrument();
                                                pageManager.letterInstrumentSearchPage.selectInstrumentResult(movementAsset.getInstrumentSelectionSearchResult());
                                            }else{
                                                pageManager.requestTypeAndAssetSelectionPage.searchInstrument(movementAsset);
                                                pageManager.requestTypeAndAssetSelectionPage.selectInstrument(movementAsset);
//                                                if(movementAsset.getEligibleAsset() != null) {
//                                                    pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
//                                                    if(movementAsset.getEligibleAssetSelectionDetail() != null)
//                                                        pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
//                                                }
                                            }
                                        }
                                                if(movementAsset.getEligibleAsset() != null) {
                                                    pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
                                                }
                                            if(movementAsset.getEligibleAssetSelectionDetail() != null)
                                                pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
                                        }
                                    }
                                pageManager.requestTypeAndAssetSelectionPage.bookAll(eligibleAssetSelection);
                                pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                            }
                        }
                    }

                    pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

                    if(pageName.equalsIgnoreCase("request type and asset selection")) {
                        pageManager.letterSummaryPage.edit();
                        pageManager.letterSetupPage.enterNext();
                    }else if (!pageName.equalsIgnoreCase("letter summary")) {
                        pageManager.letterSummaryPage.accept();
                        if (!pageName.equalsIgnoreCase("letter review and submission method")) {
                            pageManager.finalLetterReviewAndSubmissionMethodPage.email();
                            if (!pageName.equalsIgnoreCase("letter admin"))
                                pageManager.letterAdminPage.switchToAgreementStatementPage();
                        }
                    }

                }
            }
        }
    }

    @When("^Letter Summary - click (Agreement Statement|Agreement Admin|Letter Admin)$")
    public void click_agreement_statement_or_agreement_admin_or_letter_admin(String id) throws Exception{
        switch(id.toLowerCase()){
            case "agreement statement":
                pageManager.letterSummaryPage.switchToAgreementStatementPage();
                break;
            case "agreement admin":
                pageManager.letterSummaryPage.switchToAgreementAdminPage();
                break;
            case "letter admin":
                pageManager.letterSummaryPage.switchToLetterAdminPage();
                break;
        }
    }

    /**
     * Description: click Agreement Admin or Agreement Statement button in EM page<br/>
     * Start page: letter Portfolio Details page <br/>
     * End page: letter setup page <br/>
     * samples: Letter setup - click <b>Agreement Admin</b><br/>
     * @throws Exception
     */
    @When("^Letter setup - click (Agreement Admin|Agreement Statement)$")
    public void click_agreement_statement_or_agreement_admin_or_letter_admin_in_Letter_setup_page(String id) throws Exception{
        switch(id.toLowerCase()){
            case "agreement admin":
                pageManager.letterSetupPage.goToAgreementAdminPage();
                break;
            case "agreement statement":
                pageManager.letterSetupPage.goToAgreementStatementPage();
                break;
        }
    }
    /**
     * Description: click create letter button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: letter Notes/Dispute History <br/>
     * samples: Exposure Management - manual send <b>margin call</b> letter <b>my.marginLetter</b><br/>
     * @param id can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @throws Exception
     */
    @When("^Exposure Management - manual send (?:margin call|margin return|margin delivery|margin recall|margin call&recall|margin delivery&return|full dispute|UDL|statement notification|substitution request|substitution confirmation)? letter ?(\\S+)$")
    public void exposure_Management_manual_send_letter(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterCreateLetter();
        if(id != null) {
            MarginLetter marginLetter = testDataManager.getLetter(id);
            testCase.embedTestData(marginLetter);
            if(marginLetter.getPortfolioDetails() != null){
                PortfolioDetails portfolioDetails = marginLetter.getPortfolioDetails();
                pageManager.portfolioDetailsPage.setupDiaryEvents(portfolioDetails.getDiaryEvents());
                pageManager.portfolioDetailsPage.setupNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
            }
            pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
            pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
            pageManager.letterSetupPage.enterNext();

            if(marginLetter.getRequestTypeAndAssetSelection() != null){
                if(marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null){
                    for(EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()){
                        pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                        if(eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()){
                            for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                                if(movementAsset.getInstrumentSelectionSearch() != null
                                        && movementAsset.getInstrumentSelectionSearchResult() != null){
                                    if(!eligibleAssetSelection.getMovementType().getRealValue().contains("Sub")) {
                                        pageManager.requestTypeAndAssetSelectionPage.enterLetterInstrumentSearchPage();
                                        pageManager.letterInstrumentSearchPage.setInstrumentSearch(movementAsset.getInstrumentSelectionSearch());
                                        pageManager.letterInstrumentSearchPage.searchInstrument();
                                        pageManager.letterInstrumentSearchPage.selectInstrumentResult(movementAsset.getInstrumentSelectionSearchResult());
                                    }else{
                                        pageManager.requestTypeAndAssetSelectionPage.searchInstrument(movementAsset);
                                        pageManager.requestTypeAndAssetSelectionPage.selectInstrument(movementAsset);
//                                        if(movementAsset.getEligibleAsset() != null) {
//                                            pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
//                                            if(movementAsset.getEligibleAssetSelectionDetail() != null)
//                                                pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
//                                        }
                                    }
                                }
                                        if(movementAsset.getEligibleAsset() != null) {
                                            pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAsset.getEligibleAsset());
                                        }
                                    if(movementAsset.getEligibleAssetSelectionDetail() != null)
                                        pageManager.letterDetailsEditPage.setLetterDetail(movementAsset.getEligibleAssetSelectionDetail());
                                }
                            }
                        pageManager.requestTypeAndAssetSelectionPage.bookAll(eligibleAssetSelection);
                        pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                    }
                }
            }

            pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

            pageManager.letterSummaryPage.accept();
            pageManager.finalLetterReviewAndSubmissionMethodPage.email();
            pageManager.letterAdminPage.switchToAgreementExposureManagementPage();

        }
    }

    /**
     * Description: click create letter button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - create letter<br/>
     * @throws Exception
     */
    @When("^Exposure Management - create letter$")
    public void exposure_Management_create_letter() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterCreateLetter();
    }

    /**
     * Description: click auto send button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - auto send letter<br/>
     * @throws Exception
     */
    @When("^Exposure Management - auto send letter$")
    public void exposure_Management_auto_send_letter() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterAutoSendLetter();
    }
    /**
     * Description: click auto book cash button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - auto book cash<br/>
     * @throws Exception
     */
    @When("^Exposure Management - auto book cash$")
    public void exposure_Management_auto_book_cash() throws Exception {
        pageManager.exposureManagementPage.autoBookCash();
    }

    /**
     * Description: click auto book all button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - auto book all<br/>
     * @throws Exception
     */
    @When("^Exposure Management - auto book all$")
    public void exposure_Management_auto_book_all() throws Exception {
        pageManager.exposureManagementPage.autoBookAll();
    }

    /**
     * Description: click make booking button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - make booking<br/>
     * @throws Exception
     */
    @When("^Exposure Management - make booking$")
    public void exposure_Management_make_booking() throws Exception {
        pageManager.exposureManagementPage.enterMakeBooking();
    }

    /**
     * Description: click auto sned all button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - auto send all letter<br/>
     * @throws Exception
     */
    @When("^Exposure Management - auto send all letter$")
    public void exposure_Management_auto_send_all_letter() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.autoSendAll();
    }

    /**
     * Description: Manual send letter from EM page<br/>
     * Start page: EM page<br/>
     * End page: one of the following pages<br /><li>portfolio details</li><li>letter setup</li><li>request type and asset selection</li><li>letter summary</li><li>letter review and submission method</li><li>letter admin</li><br/>
     * samples: Exposure Management - manual send margin call letter <b>margin.letter</b> for event em.event to portfolio details page<br/>
     * @param id can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @param pageName can be one of following value:<br /><li>portfolio details</li><li>letter setup</li><li>request type and asset selection</li><li>letter summary</li><li>letter review and submission method</li><li>letter admin</li>
     * @throws Exception
     */
    @When("^Exposure Management - manual send (?:margin call|margin return|margin delivery|margin recall|margin call&recall|margin delivery&return|full dispute|UDL|statement notification|substitution request|substitution confirmation)? letter (\\S+) for event (?:\\S+) to (portfolio details|letter setup|request type and asset selection|letter summary|letter review and submission method|letter admin) page$")
    public void exposure_Management_manual_send_for_event_to_stage(String id, String pageName) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterCreateLetter();
        if(id != null) {
            MarginLetter marginLetter = testDataManager.getLetter(id);
            testCase.embedTestData(marginLetter);
            if(pageName != null) {
                if (marginLetter.getPortfolioDetails() != null) {
                    PortfolioDetails portfolioDetails = marginLetter.getPortfolioDetails();
                    pageManager.portfolioDetailsPage.setupDiaryEvents(portfolioDetails.getDiaryEvents());
                    pageManager.portfolioDetailsPage.setupNotesOrDisputeHistory(portfolioDetails.getNotesOrDisputeHistory());
                }
                if(!pageName.equalsIgnoreCase("portfolio details")) {
                    pageManager.portfolioDetailsPage.letterProcessing(marginLetter.getLetterType());
                    pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
                    if(!pageName.equalsIgnoreCase("letter setup")) {
                        pageManager.letterSetupPage.enterNext();

                        if(marginLetter.getRequestTypeAndAssetSelection() != null){
                            if(marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection() != null){
                                for(EligibleAssetSelection eligibleAssetSelection : marginLetter.getRequestTypeAndAssetSelection().getEligibleAssetSelection()){
                                    pageManager.requestTypeAndAssetSelectionPage.goToCorrectPage(eligibleAssetSelection);
                                    if(eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()){
                                        for(MovementAsset movementAssets : eligibleAssetSelection.getMovementAssets()){
                                            if(movementAssets.getInstrumentSelectionSearch() != null
                                                    && movementAssets.getInstrumentSelectionSearchResult() != null){
                                                if(!eligibleAssetSelection.getMovementType().getRealValue().contains("Sub")) {
                                                    pageManager.requestTypeAndAssetSelectionPage.enterLetterInstrumentSearchPage();
                                                    pageManager.letterInstrumentSearchPage.setInstrumentSearch(movementAssets.getInstrumentSelectionSearch());
                                                    pageManager.letterInstrumentSearchPage.searchInstrument();
                                                    pageManager.letterInstrumentSearchPage.selectInstrumentResult(movementAssets.getInstrumentSelectionSearchResult());
                                                }else{
                                                    pageManager.requestTypeAndAssetSelectionPage.searchInstrument(movementAssets);
                                                    pageManager.requestTypeAndAssetSelectionPage.selectInstrument(movementAssets);
//                                                    if(movementAssets.getEligibleAsset() != null) {
//                                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAssets.getEligibleAsset());
//                                                        if(movementAssets.getEligibleAssetSelectionDetail() != null)
//                                                            pageManager.letterDetailsEditPage.setLetterDetail(movementAssets.getEligibleAssetSelectionDetail());
//                                                    }
                                                }
                                            }
                                                    if(movementAssets.getEligibleAsset() != null) {
                                                        pageManager.requestTypeAndAssetSelectionPage.editEligibleAsset(movementAssets.getEligibleAsset());
                                                    }
                                                if(movementAssets.getEligibleAssetSelectionDetail() != null)
                                                    pageManager.letterDetailsEditPage.setLetterDetail(movementAssets.getEligibleAssetSelectionDetail());
                                            }
                                        }
                                    pageManager.requestTypeAndAssetSelectionPage.bookAll(eligibleAssetSelection);
                                    pageManager.requestTypeAndAssetSelectionPage.enterNext(marginLetter.getRequestTypeAndAssetSelection().getAlertHandling());
                                }
                            }
                        }

                        pageManager.requestTypeAndAssetSelectionPage.enterNextStage();

                        if(pageName.equalsIgnoreCase("request type and asset selection")) {
                            pageManager.letterSummaryPage.edit();
                            pageManager.letterSetupPage.enterNext();
                        }else if (!pageName.equalsIgnoreCase("letter summary")) {
                            pageManager.letterSummaryPage.accept();
                            if (!pageName.equalsIgnoreCase("letter review and submission method")) {
                                pageManager.finalLetterReviewAndSubmissionMethodPage.email();
                                if (!pageName.equalsIgnoreCase("letter admin"))
                                    pageManager.letterAdminPage.switchToAgreementExposureManagementPage();
                            }
                        }

                    }
                }
            }
        }
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param action   can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^letter Summary- (email|download pdf|save as pending|print)$")
    public void letter_email_download_pdf_save_as_pending_print(String action) {
        // Write code here that turns the phrase above into concrete actions
        switch (action.toLowerCase()) {
            case "email":
                //TODO need a function that click the email button in LetterSummaryPage
                break;
            case "download pdf":
                //TODO need a function that click the download pdf button in LetterSummaryPage
                break;
            case "save as pending":
                //TODO need a function that click the save as pending button in LetterSummaryPage
                break;
            case "print":
                //TODO need a function that click the print button in LetterSummaryPage
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: add|delete|remove user filter<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - <b>add</b> user filter <b>my.filter</b><br/>
     * @param action can be one of following value:<br /><li>add</li><li>delete</li><li>remove</li>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Exposure Management - (add|delete|remove) user filter (\\S+)$")
    public void exposure_management_add_user_filter(String action, String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add the user filter object in the schema and corresponding functions in ExposureManagementPage
        throw new PendingException();
    }

    /**
     * Description: modify EM column<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - <b>add</b> column <b>my.EM.column</b><br/>
     * @param action can be one of following type:<br /><li>add</li><li>delete</li><li>order</li>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Exposure Management - (add|delete|order) column (\\S+)$")
    public void exposure_Management_add_delete_order_column(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            switch (action.toLowerCase()) {
                case "add":
                    pageManager.exposureManagementPage.addSomeColumns(exposureEventDetail);
                    break;
            }
        }
    }



    @When("^Exposure Management - (add|remove) all columns$")
    public void exposure_Management_add_remove_all_column(String action) throws Exception {
        switch (action.toLowerCase()){
            case "add":
                pageManager.exposureManagementPage.addAllColumns();
                break;
            case "remove":
                break;
        }
    }

    /**
     * Description: click coolapse|expand button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - <b>collapse</b> event <b>my.exposureEventDetail</b><br/>
     * @param action can be one of following value:<br /><li>collapse</li><li>expand</li>
     * @param ids can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - (collapse|expand) events? (\\S+)$")
    public void exposure_Management_collapse_expand_events(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            switch (action.toLowerCase()) {
                case "collapse":
                    pageManager.exposureManagementPage.collapseEvent(exposureEventDetail);
                    break;
                case "expand":
                    pageManager.exposureManagementPage.expandEvent(exposureEventDetail);
                    break;
            }
        }
    }



    /**
     * Description: click auto populate counterparty amount in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - auto populate counterparty amount<br/>
     * @throws Exception
     */
    @When("^Exposure Management - auto populate counterparty amount$")
    public void exposure_Management_auto_populate_counterparty_amount() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.autoPopulateCounterpartyAmount();
    }

    /**
     * Description: click settlement data button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: settlementStatusSummaryPage<br/>
     * samples: Exposure Management - go to settlement data page<br/>
     * @throws Exception
     */
    @When("^Exposure Management - go to settlement data page (\\S+)$")
    public void exposure_Management_go_to_settlement_data_page(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterSettlement();
    }

    /**
     * Description: click live statment button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: agreementStatementPage<br/>
     * samples: Exposure Management - view live statement page<br/>
     * @throws Exception
     */
    @When("^Exposure Management - view live statement page$")
    public void exposure_Management_view_live_statement_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterStatement();
    }

    /**
     * Description: click archive statment button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: archiveStatementPage<br/>
     * samples: Exposure Management - view archive statement page<br/>
     * @throws Exception
     */
    @When("^Exposure Management - view archive statement page$")
    public void exposure_management_view_archive_statement_page() throws Exception{
        pageManager.exposureManagementPage.enterArchiveStatement();
    }

    /**
     * Description: click agreement button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: agreementSummaryPage<br/>
     * samples: Exposure Management - view agreement summary page<br/>
     * @throws Exception
     */
    @When("^Exposure Management - view agreement summary page$")
    public void exposure_Management_view_agreement_summary_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterAgreement();
    }

    /**
     * Description: click shoe letter log button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: letterLogPage<br/>
     * samples: Exposure Management - show letter log<br/>
     * @throws Exception
     */
    @When("^Exposure Management - show letter log$")
    public void exposure_Management_show_letter_log() throws Exception {
        // Write code here that turns the phrase above into concrete actions
//        ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
        pageManager.exposureManagementPage.showLetterLog();
//        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @SuppressWarnings("DanglingJavadoc")
    @When("^Exposure Management - show error log (\\S+)$")
    public void exposure_Management_show_error_log(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: click show reform process summary button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: reform ProcessSummaryPage<br/>
     * samples: Exposure Management - show reform process summary<br/>
     * @throws Exception
     */
    @When("^Exposure Management - show reform process summary$")
    public void exposure_Management_show_reform_process_summary() throws Exception {
        // Write code here that turns the phrase above into concrete actions
//        ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
        pageManager.exposureManagementPage.showReformProcessSummary();
//        throw new PendingException();
    }

    /**
     * Description: click show reform exception button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: reformExceptionPage<br/>
     * samples: Exposure Management - show reform exception<br/>
     * @throws Exception
     */
    @When("^Exposure Management - show reform exception$")
    public void exposure_Management_show_reform_exception() throws Exception {
        // Write code here that turns the phrase above into concrete actions
//        ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
        pageManager.exposureManagementPage.showReformException();
//        throw new PendingException();
    }

    /**
     * Description: click save change button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - save changes<br/>
     * @throws Exception
     */
    @When("^Exposure Management - save changes$")
    public void exposure_Management_save_changes() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterSaveChanges();
    }

    /*
    @When("^Exposure Management - add user filter (\\S+)$")
    public void exposure_Management_add_user_filter(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    */

    /**
     * Description: click margin call TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin call <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin call (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_call(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_CALL);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin recall TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin recall <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin recall (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_recall(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_RECALL);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin call&recall TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin call&recall <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin call&recall (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_call_recall(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_CALL_AND_RECALL);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin issued TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin issued <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin issued (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_issued(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_ISSUED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin delivery TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin delivery <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin delivery (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_delivery(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_DELIVERY);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin return TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin return <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin return (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_return(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_RETURN);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin delivery&return TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin delivery&return <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin delivery&return (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_delivery_return(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_DELIVERY_AND_RETURN);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin confirmed TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin confirmed <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin confirmed (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_confirmed(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_CONFIRMED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin no call TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin no call <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search Margin no call (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_no_call(String id) throws Exception {
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_NO_CALLS);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin waived TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin waived <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin waived (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_waived(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_WAIVED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin completed TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin completed <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin completed (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_completed(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_COMPLETED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin disputes TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin disputes <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin disputes (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_disputes(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_DISPUTES);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin errors TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin errors <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin errors (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_errors(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_ERRORS);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click margin dynamic filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - margin filters - search margin dynamic filter <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - margin filters - search margin dynamic filter (\\S+)$")
    public void exposure_Management_margin_filters_search_margin_dynamic(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.MARGIN_DYNAMIC_FILTER);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution sub request filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search sub request <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search sub request (\\S+)$")
    public void exposure_Management_substitution_filters_search_sub_request(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_REQUEST);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution sub confirmation filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search sub confirmation <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search sub confirmation (\\S+)$")
    public void exposure_Management_substitution_filters_search_sub_confirmation(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_CONFIRMATION);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution issued filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search issued <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search issued (\\S+)$")
    public void exposure_Management_substitution_filters_search_issued(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_ISSUED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution confirmed filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search confirmed <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search confirmed (\\S+)$")
    public void exposure_Management_substitution_filters_search_confirmed(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_CONFIRMED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution waived filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search waived <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search waived (\\S+)$")
    public void exposure_Management_substitution_filters_search_waived(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_WAIVED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution partially booked filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search partially booked <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search partially booked (\\S+)$")
    public void exposure_Management_substitution_filters_search_partially_booked(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_PARTIALLY_BOOKED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click substitution completed filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - substitution filters - search completed <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - substitution filters - search completed (\\S+)$")
    public void exposure_Management_substitution_filters_search_completed(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.SUBSTITUTION_COMPLETED);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click all dynamic filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - all filters - search dynamic filter <b>my.exposureEventSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - all filters - search dynamic filter (\\S+)$")
    public void exposure_Management_all_filters_search_dynamic_filter(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.ALL_DYNAMIC_FILTER);
        exposure_Management_search_event_by(id);
    }

    /**
     * Description: click all user filter TAB and search event<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - all filters - search user filter <b>my.exposureEventSearch</b><br/>
     * @param addUserFilterId can be one of following type:<br /><li>{@link ExposureManagementFilter}</li>
     * @param searchId can be one of following type:<br /><li>{@link ExposureEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - user filter - search (\\S+) filter (\\S+)$")
    public void exposure_Management_user_filters_search_filter(String addUserFilterId, String searchId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        ExposureManagementFilter exposureManagementFilter = testDataManager.getExposureManagementFilter(addUserFilterId);
        pageManager.exposureManagementPage.navigate(ExposureManagementMenu.USER_FILTER,exposureManagementFilter);
        exposure_Management_search_event_by(searchId);
    }

    /**
     * Description: navigate to Exposure Management<br/>
     * Start page: anyPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: navigate to Exposure Management<br/>
     * @throws Exception
     */
    @When("^navigate to Exposure Management$")
    public void navigate_to_exposure_management() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.EXPOSURE_MANAGEMENT);
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Exposure Management - select page (\\S+)$")
    public void exposure_management_select_page(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a pagination related function in ExposureManagementPage
        throw new PendingException();
    }

    /**
     * Description: tick events in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - tick event <b>my.exposureEventDetail</b><br/>
     * @param ids can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - tick events? (\\S+)$")
    public void exposure_management_tick_event(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions

        for(String id : ids){
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            pageManager.exposureManagementPage.tickEvent(exposureEventDetail, true);
        }
    }

    /**
     * Description: untick events in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - untick event <b>my.exposureEventDetail</b><br/>
     * @param ids can be one of following type:<br /><li>{@link ExposureEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - untick events? (\\S+)$")
    public void exposure_management_untick_event(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions

        for(String id : ids){
            ExposureEventDetail exposureEventDetail = testDataManager.getExposureEventDetail(id);
            testCase.embedTestData(exposureEventDetail);
            pageManager.exposureManagementPage.tickEvent(exposureEventDetail, false);
        }
    }

    /**
     * Description: Go to bulk booking page from EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Go to bulk booking page from EM page <br/>
     * @throws Exception
     */
    @When("^Exposure Management - go to bulk booking page$")
    public void exposure_management_go_to_bulkBooking() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.enterBulkBooking();
    }
    /**
     * Description: click exposure manaement button in EM page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: agreementExposureManagementPage<br/>
     * samples: Exposure Management - show exposure management<br/>
     * @throws Exception
     */
    @When("^Exposure Management - show exposure management$")
    public void exposure_management_show_exposure_management(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.showExposureManagement();
    }

    /**
     * Description: select create letter in IM search result and download the letter<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page <br/>
     * samples: Interest Manager - download letters <b>my.letter</b> for event <b>my.event</b> and save file to <b>my.file</b><br/>
     * @param letterId can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @param eventId can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @param fileId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^Interest Manager - download letters (\\S+) for event (\\S+) and save file to (\\S+)$")
    public void interest_Manager_download_letter_for_event(String letterId, String eventId, String fileId) throws Exception{
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(eventId);
        testCase.embedTestData(interestEventDetail);
        StringType filePath = testDataManager.getVariable(fileId);
        testCase.embedTestData(filePath);
        interestEventDetail.setLinks(InterestEventlinkType.CREATE_LETTER);
        MarginLetter marginLetter = testDataManager.getLetter(letterId);
        testCase.embedTestData(marginLetter);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
        pageManager.letterSetupPage.enterNext();
        pageManager.letterSummaryPage.accept();
        pageManager.finalLetterReviewAndSubmissionMethodPage.download(filePath);
        pageManager.finalLetterReviewAndSubmissionMethodPage.switchToLetterSummary();
        pageManager.letterSummaryPage.switchToLetterAdminPage();
        pageManager.letterAdminPage.switchToInterestManagerPage();
    }

    /**
     * Description: select create letter in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page <br/>
     * samples: Interest Manager - create letters <b>my.letter</b> for event <b>my.event</b><br/>
     * @param letterId can be one of following type:<br /><li>{@link MarginLetter}</li>
     * @param eventId can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - create letters (\\S+) for event (\\S+)$")
    public void interest_Manager_create_letter_for_events(String letterId, String eventId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(eventId);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(InterestEventlinkType.CREATE_LETTER);
        MarginLetter marginLetter = testDataManager.getLetter(letterId);
        testCase.embedTestData(marginLetter);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        pageManager.letterSetupPage.setLetterSetupPage(marginLetter.getLetterSetupDetails());
        pageManager.letterSetupPage.enterNext();
        pageManager.letterSummaryPage.accept();
        pageManager.finalLetterReviewAndSubmissionMethodPage.email();
        pageManager.letterAdminPage.switchToInterestManagerPage();
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: select reset in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - reset event <b>my.interestevent</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - reset events? (\\S+)$")
    public void interest_Manager_reset_event(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(InterestEventlinkType.RESET);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: select view detail in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: interestReport<br/>
     * samples: Interest Manager - view details for event <b>my.interestevent</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - view details for event (\\S+)$")
    public void interest_manager_view_details_for_event(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(InterestEventlinkType.VIEW_DETAILS);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: select Email in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - email for event <b>my.interestevent</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - email for event (\\S+)$")
    public void interest_manager_email_for_event(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(InterestEventlinkType.EMAIL);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: select view interest movements in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: cashmovementSummaryPage<br/>
     * samples: Interest Manager - view interest movements for event <b>my.interestevent</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - view interest movements for event (\\S+)$")
    public void interest_manager_view_interest_movements_for_events(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(InterestEventlinkType.VIEW_INTEREST_MOVEMENT);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: select view letter in IM search result<br/>
     * Start page: IM search result page <br/>
     * End page: letter admin page<br/>
     * samples: Interest Manager - view letter for event <b>my.interestevent</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - view letter for event (\\S+)$")
    public void interest_manager_view_letter_for_event(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
        interestEventDetail.setLinks(InterestEventlinkType.VIEW_LETTER);
        pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
        testCase.embedTestData(interestEventDetail);
        interestEventDetail.setLinks(null);
    }

    /**
     * Description: search interest event<br/>
     * Start page: IM search page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - search interest event by <b>my.interestSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventSearch}</li>
     * @throws Exception
     */
    @When("^Interest Manager - search interest event by (\\S+)$")
    public void interest_Manager_search_interest_event_by(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestEventSearch interestEventSearch = testDataManager.getInterestEventSearch(id);
        pageManager.interestManagerSearchPage.searchInterestEvent(interestEventSearch);
        pageManager.interestManagerSearchPage.search();
        testCase.embedTestData(interestEventSearch);
    }

    /**
     * Description: click reset button to reset ticked interest event<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - reset ticked events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - reset ticked events?$")
    public void interest_Manager_reset_ticked_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.reset();
    }

    /**
     * Description: click reset all button to reset all interest event<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - reset all events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - reset all events$")
    public void interest_Manager_reset_all_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.resetAll();
    }

    /**
     * Description: click auto email button to email ticked interest event<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - auto email ticked events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - auto email ticked events?$")
    public void interest_Manager_auto_email_ticked_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.autoEmail();
    }

    /**
     * Description: click auto email all button to email all interest event<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - auto email all events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - auto email all events$")
    public void interest_Manager_auto_email_all_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.autoEmailAll();
    }

    /**
     * Description: click save change button to save ticked interest event change<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - save change ticked events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - save change ticked events?$")
    public void interest_Manager_save_change_ticked_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.saveChangeTicked();
    }

    /**
     * Description: click save change button to save all interest event change<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - save change all events<br/>
     * @throws Exception
     */
    @When("^Interest Manager - save change all events$")
    public void interest_Manager_save_change_all_events() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.saveChangeAll();
    }

    /**
     * Description: navigate to interest manager search page<br/>
     * Start page: anyPage <br/>
     * End page: IM search page<br/>
     * samples: navigate to interest manager search page<br/>
     * @throws Exception
     */
    @When("^navigate to interest manager search page$")
    public void navigate_to_interest_manager_search_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.INTEREST_MANAGER);
    }

    /**
     * Description: page turning<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: <br/>
     * @param pageNumber can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^Interest Manager - view page (\\S+)$")
    public void interest_Manager_view_page(String pageNumber) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.abstractCollinePage.enterPage(pageNumber);
        testCase.embedTestData(pageNumber);
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: <br/>
     * @param columnName can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^sort interest manager result by (eventid|principal|counterparty|description|businessline|model|cash asset type|interest rule)?$")
    public void sort_interest_manager_result(String columnName) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a sorting function that has an argument which indicate the column Name in the InterestManagerSearchResultPage
        throw new PendingException();
    }

    /**
     * Description: export interest event search result<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - export search result in <b>CSV</b> format <b>my.location.object</b><br/>
     * @param format can be one of following value:<br /><li>CSV</li><li>Excel</li><li>XML</li><li>PDF</li>
     * @param id file location<br />
     * @throws Exception
     */
    @When("^Interest Manager - export search result in (CSV|Excel|XML|PDF) format (\\S+)$")
    public void interest_Manager_export_search_result(String format, String id) throws Exception {
        StringType stringType = testDataManager.getVariable(id);
        switch (format.toLowerCase()) {
            case "csv":
                pageManager.interestManagerSearchResultPage.exportCsv(stringType);
                break;
            case "excel":
                pageManager.interestManagerSearchResultPage.exportExcel(stringType);
                break;
            case "xml":
                pageManager.interestManagerSearchResultPage.exportXml(stringType);
                break;
            case "pdf":
                pageManager.interestManagerSearchResultPage.exportPdf(stringType);
                break;
        }
        testCase.embedTestData(stringType);
    }

    /**
     * Description: click apply interest movement button<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - Apply interest movement<br/>
     * @throws Exception
     */
    @When("^Interest Manager - Apply interest movement$")
    public void interest_Manager_apply_interest_movement() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.applyInterestMovement();
    }

    /**
     * Description: click apply interest movement button and set the interest movement status back to {@link InterestEventDetail}<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - Apply interest movement <b>my.interest.event<b/><br/>
     * @param ids can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - Apply interest movement (\\S+)$")
    public void interest_Manager_apply_interest_movement(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.interestManagerSearchResultPage.applyInterestMovement();
        if(ids != null){
            for(String id : ids){
                String time = pageManager.interestManagerSearchResultPage.getTime();
                InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
                if(interestEventDetail.getInterestMovementStatus() == null) {
                    interestEventDetail.setInterestMovementStatus(new StringType(time));
                }else if(interestEventDetail.getInterestMovementStatus().getRealValue().isEmpty()){
                    interestEventDetail.getInterestMovementStatus().setValue(time);
                }
                testCase.embedTestData(interestEventDetail);
            }
        }
    }

    /**
     * Description: click get results in MS-Excel button<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - get results in MS-Excel <b>my.result.location</b> for all events<br/>
     * @param id can be one of following type:<br /><li>Variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^Interest Manager - get results in MS-Excel (\\S+) for all events$")
    public void interest_Manager_get_results_in_MS_Excel_for_all_events(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StringType stringType = testDataManager.getVariable(id);
        pageManager.abstractCollinePage.getResultsInMSExcel(stringType);
        testCase.embedTestData(stringType);
    }

    /**
     * Description: change interest events<br/>
     * Start page: IM search result page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - change interest evnet to <b>my.new.interestevent</b><br/>
     * @param ids can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - change interest events? to (\\S+)$")
    public void interest_manager_change_interest_events(List<String> ids) throws Exception{
        for(String id : ids){
            InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
            pageManager.interestManagerSearchResultPage.inputInterestManagerSearchResult(interestEventDetail);
            testCase.embedTestData(interestEventDetail);
        }
    }

    /**
     * Description: click Interest Manager button in interest detail page<br/>
     * Start page: interest detail page <br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - click Interest Manager button in interest detail page<br/>
     * @throws Exception
     */
    @When("^Interest Manager - click Interest Manager button in interest detail page$")
    public void interest_manager_click_interest_manager_button_in_interest_detail_page() throws Exception{
        pageManager.interestViewDetailsPage.enterInterestManagerPage();
    }

    /**
     * Description: click get result in MS-Excel button in interest detail page<br/>
     * Start page: interest detail page <br/>
     * End page: interest detail page <br/>
     * samples: Interest Manager - get results in MS-Excel <b>my.result.location</b> in interest detail page<br/>
     * @param id can be one of following type:<br /><li>Variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^Interest Manager - get results in MS-Excel (\\S+) in interest detail page$")
    public void interest_manager_get_result_in_MS_Excel_in_interest_detail_page(String id) throws Exception{
        StringType stringType = testDataManager.getVariable(id);
        pageManager.abstractCollinePage.getResultsInMSExcel(stringType);
        testCase.embedTestData(stringType);
    }

    /*
    @When("^Interest Manager quick link$")
    public void interest_Manager_quick_link() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    */

    /**
     * Description: add asset class|asset category|asset type<br/>
     * Start page: assetDefinitionPage <br/>
     * End page: assetDefinitionPage<br/>
     * samples: add asset class <b>my.assetDefinition</b><br/>
     * @param ids can be one of following type:<br /><li>{@link AssetDefinition}</li>
     * @throws Exception
     */
    @When("^add asset (?:class(?:es)?|categor(?:y|ies)|types?) (\\S+)$")
    public void add_asset_class_category_type(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            AssetDefinition assetDefinition = testDataManager.getAssetDefinition(id);
            pageManager.assetDefinitionPage.setAssetSearchFilter(assetDefinition);
            pageManager.assetDefinitionPage.addAsset(assetDefinition);
            pageManager.addEditAssetPage.editAssetInfo(assetDefinition);
//            pageManager.addEditAssetPage.editAsset(assetDefinition);
//            pageManager.addEditAssetPage.save();
            testCase.embedTestData(assetDefinition);
        }
    }

    /**
     * Description: edit asset class|asset category|asset type<br/>
     * Start page: assetDefinitionPage <br/>
     * End page: assetDefinitionPage<br/>
     * samples: edit asset class <b>my.old.assetDefinition</b> to <b>my.new.assetDefinition</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link AssetDefinition}</li>
     * @param newId can be one of following type:<br /><li>{@link AssetDefinition}</li>
     * @throws Exception
     */
    @When("^edit asset (?:class|category|type) (\\S+) to (\\S+)$")
    public void edit_asset_class_category_type(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AssetDefinition oriAssetDefinition = testDataManager.getAssetDefinition(oriId);
        AssetDefinition newAssetDefinition = testDataManager.getAssetDefinition(newId);
        pageManager.assetDefinitionPage.setAssetSearchFilter(oriAssetDefinition);
        pageManager.assetDefinitionPage.editAsset(oriAssetDefinition);
        pageManager.addEditAssetPage.editAssetInfo(newAssetDefinition);
//        pageManager.addEditAssetPage.editAsset(newAssetDefinition);
//        pageManager.addEditAssetPage.save();
        testCase.embedTestData(oriAssetDefinition);
        testCase.embedTestData(newAssetDefinition);
    }

    /**
     * Description: add Interest Rates<br/>
     * Start page: Cash Interest Reference Rate page <br/>
     * End page: Cash Interest Reference Rate page<br/>
     * samples: add Interest Rates <b>my.interestRate</b><br/>
     * @param ids can be one of following type:<br /><li>{@link InterestRate}</li>
     * @throws Exception
     */
    @When("^add Interest Rates? (\\S+)$")
    public void add_Interest_Rate(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids) {
            InterestRate interestRate = testDataManager.getInterestRate(id);
            pageManager.interestRatesPage.inputInterestRate(interestRate);
            pageManager.interestRatesPage.addMember();
            if(interestRate.getDailyInterestRate() != null
                    && !interestRate.getDailyInterestRate().isEmpty()){
                for(DailyInterestRate dailyInterestRate : interestRate.getDailyInterestRate()){
                    pageManager.interestRatesPage.updateInterestRate(interestRate);
                    pageManager.interestRateMaintenancePage.addInterestRateMaintenance(dailyInterestRate);
                }
            }
            testCase.embedTestData(interestRate);
        }
    }

    /**
     * Description: edit interest rate<br/>
     * Start page:  <br/>
     * End page: <br/>
     * samples: edit Interest Rate <b>my.old.interestRate</b> to <b>my.new.interestRate</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link InterestRate}</li>
     * @param newId can be one of following type:<br /><li>{@link InterestRate}</li>
     * @throws Exception
     */
    @When("^edit Interest Rate (\\S+) to (\\S+)$")
    public void edit_Interest_Rate(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        InterestRate oriInterestRate = testDataManager.getInterestRate(oriId);
        testCase.embedTestData(oriInterestRate);
        InterestRate newInterestRate = testDataManager.getInterestRate(newId);

        if(newInterestRate.getInterestRateName() != null
                || newInterestRate.getDescription() != null
                || newInterestRate.getRefixFrequency() != null
                || newInterestRate.getStatus() != null
                || newInterestRate.getType() != null){
            pageManager.interestRatesPage.changeInterestRates(oriInterestRate);
            pageManager.modifyExistingReferenceRatePage.inputInterestRate(newInterestRate);
            pageManager.modifyExistingReferenceRatePage.submitChanges();
            if(newInterestRate.getInterestRateName() != null){
                if(oriInterestRate.getInterestRateName() != null){
                    oriInterestRate.getInterestRateName().setValue(newInterestRate.getInterestRateName().getRealValue());
                }else{
                    oriInterestRate.setInterestRateName(newInterestRate.getInterestRateName());
                }
            }
            if(newInterestRate.getDescription() != null){
                if(oriInterestRate.getDescription() != null){
                    oriInterestRate.getDescription().setValue(newInterestRate.getDescription().getRealValue());
                }else{
                    oriInterestRate.setDescription(newInterestRate.getDescription());
                }
            }
            if(newInterestRate.getRefixFrequency() != null)
                oriInterestRate.setRefixFrequency(newInterestRate.getRefixFrequency());
            if(newInterestRate.getStatus() != null)
                oriInterestRate.setStatus(newInterestRate.getStatus());
            if(newInterestRate.getType() != null)
                oriInterestRate.setType(newInterestRate.getType());
        }

        if(newInterestRate.getDailyInterestRate() != null
                && !newInterestRate.getDailyInterestRate().isEmpty()){
            for(DailyInterestRate dailyInterestRate : newInterestRate.getDailyInterestRate()){
                pageManager.interestRatesPage.updateInterestRate(oriInterestRate);
                pageManager.interestRateMaintenancePage.editDailyInterestRate(dailyInterestRate);
            }
        }
        testCase.embedTestData(newInterestRate);
    }

    /**
     * Description: add collateral static data settlement data<br/>
     * Start page: SSI search page <br/>
     * End page: SSI search page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^add collateral static data settlement data (\\S+)$")
    public void add_collateral_static_data_settlement_data(List<String> ids) throws Exception {
        for (String id : ids){
            SettlementData settlementData = testDataManager.getSettlementData(id);
            pageManager.settlementDataPage.clickAddImage();
            pageManager.settlementDataPage.addSettelmentData(settlementData);
            testCase.embedTestData(settlementData);
        }
    }

    /**
     * Description: search collateral static data settlement data<br/>
     * Start page: SSI search page <br/>
     * End page: SSI search page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search collateral static data settlement data (\\S+)$")
    public void search_collateral_static_data_settlement_data(String id) throws Exception {
        SettlementDataSearch settlementDataSearch = testDataManager.getSettlementDataSearch(id);
        pageManager.settlementDataPage.searchSettlementData(settlementDataSearch);
        testCase.embedTestData(settlementDataSearch);
    }

    /**
     * Description: add umbrella rule<br/>
     * Start page: umbrella rules page <br/>
     * End page: umbrella rules page br/>
     * samples: add collateral static data umbrella rule <b>my.umbrella.rule</b><br/>
     * @param ids can be one of following type:<br /><li>{@link UmbrellaRule}</li>
     * @throws Exception
     */
    @When("^add collateral static data umbrella rules? (\\S+)$")
    public void add_collateral_static_data_umbrella_rules(List<String> ids) throws Exception {
        for(String id : ids){
            UmbrellaRule umbrellaRule = testDataManager.getUmbrellaRule(id);
            pageManager.umbrellaRulesPage.addUmbrellaRule();
            pageManager.umbrellaRulesPage.inputUmbrellaRule(umbrellaRule);
            pageManager.umbrellaRulesPage.saveUmbrellaRule();
            testCase.embedTestData(umbrellaRule);
        }
    }

    /**
     * Description: delete umbrella rule<br/>
     * Start page: umbrella rules page <br/>
     * End page: umbrella rules page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^delete collateral static data umbrella rules? (\\S+)$")
    public void delete_collateral_static_data_umbrella_rules(List<String> ids) throws Exception {
        for(String id : ids){
            UmbrellaRule umbrellaRule = testDataManager.getUmbrellaRule(id);
            pageManager.umbrellaRulesPage.deleteUmbrellaRule(umbrellaRule);
            testCase.embedTestData(umbrellaRule);
        }
    }

    /**
     * Description: edit umbrella rule<br/>
     * Start page: umbrella rules page <br/>
     * End page: umbrella rules page<br/>
     * samples: <br/>
     * @param oriUmbrellaId can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^edit collateral static data umbrella ruless? (\\S+)$")
    public void edit_collateral_static_data_umbrella_rules(String oriUmbrellaId) throws Exception {
        UmbrellaRule oriUmbrellaRule = testDataManager.getUmbrellaRule(oriUmbrellaId);
        pageManager.umbrellaRulesPage.editUmbrellaRule(oriUmbrellaRule);
        testCase.embedTestData(oriUmbrellaRule);
    }

    /**
     * Description: add TSA or cashflow rule<br/>
     * Start page: TSA|cashflow rule page <br/>
     * End page: TSA|cashflow rule page<br/>
     * samples: add <b>TSA</b> rule <b>my.tsaRule</b><br/>
     * @param type can be one of following value:<br /><li>TSA</li><li>cashflow</li>
     * @param id can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @When("^add (TSA|cashflow) rules? (\\S+)$")
    public void add_TSA_rule(String type, String id) throws Exception {
        TsaRule tsaRule = null;
        switch (type.toLowerCase()){
            case "tsa":
                tsaRule = testDataManager.getTsaRule(id);
                break;
            case "cashflow":
                tsaRule = testDataManager.getCashflowRule(id);
                break;
        }
        pageManager.tsaRulesPage.addTsaRule();
        pageManager.tsaRulesPage.inputTsaRule(tsaRule);
        pageManager.tsaRulesPage.saveTsaRule(tsaRule);
        testCase.embedTestData(tsaRule);
    }

    /**
     * Description: add eligibility rules templates<br/>
     * Start page: eliginilityRuleTemplateSearchPage <br/>
     * End page: eliginilityRuleTemplateSearchPage<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplate}</li>
     * @throws Exception
     */
    @When("^add eligibility rules templates? (\\S+)$")
    public void add_eligibility_rules_template(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.ELIGIBILITY_RULES_TEMPLATE);
        EligibilityRulesTemplate eligibilityRulesTemplate = testDataManager.getEligibilityRulesTemplate(id);
        pageManager.eligibilityRulesTemplatePage.addEligibilityRulesTemplate(eligibilityRulesTemplate);
        if(eligibilityRulesTemplate.getStatus() != null && eligibilityRulesTemplate.getStatus() == SimpleStatusType.APPROVED){
            pageManager.eligibilityRulesTemplatePage.approveEligibilityRulesTemplate(eligibilityRulesTemplate);
        }else if(eligibilityRulesTemplate.getRejectReason() != null){
            pageManager.eligibilityRulesTemplatePage.rejectEligibilityRulesTemplate(eligibilityRulesTemplate);
        }
        testCase.embedTestData(eligibilityRulesTemplate);
    }

    /**
     * Description: open eligibility rules templates<br/>
     * Start page: eligibilityRuleTemplateSearchPage <br/>
     * End page: eligibilityRuleTemplateSearchPage<br/>
     * samples: open eligibility rules template <b>eligibility.rule.template</b><br/>
     * @param id can be one of following type:<br /><li>{@link EligibilityRulesTemplate}</li>
     * @throws Exception
     */
    @When("^open eligibility rules template (\\S+)$")
    public void open_eligibility_rules_template(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EligibilityRulesTemplate eligibilityRulesTemplate = testDataManager.getEligibilityRulesTemplate(id);
        testCase.embedTestData(eligibilityRulesTemplate);
        pageManager.eligibilityRulesTemplatePage.openEligibilityRulesTemplate(eligibilityRulesTemplate);
    }

    /**
     * Description: clicl view change button in eligibility rules templates search page<br/>
     * Start page: eliginilityRuleTemplateSearchPage <br/>
     * End page: eliginilityRuleTemplateChangePage<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^eligibility rules template - view change (\\S+)$")
    public void eligibility_rules_template_view_change(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: click approve button in eligibility rules templates change page<br/>
     * Start page: eliginilityRuleTemplateChangePage <br/>
     * End page: eliginilityRuleTemplateSearchPage<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^eligibility rules template - approve (\\S+)$")
    public void eligibility_rules_template_approve(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EligibilityRulesTemplate eligibilityRulesTemplate = testDataManager.getEligibilityRulesTemplate(id);
        pageManager.eligibilityRulesTemplatePage.approveEligibilityRulesTemplate(eligibilityRulesTemplate);
        testCase.embedTestData(eligibilityRulesTemplate);
    }

    /**
     * Description: click reject button in eligibility rules templates change page<br/>
     * Start page: eliginilityRuleTemplateChangePage <br/>
     * End page: eliginilityRuleTemplateSearchPage<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^eligibility rules template - reject (\\S+)$")
    public void eligibility_rules_template_reject(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: add collateral static data<br/>
     * Start page: collateral static data page <br/>
     * End page: collateral static data page<br/>
     * samples: add collateral static data <b>my.staticdata</b><br/>
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^add collateral static data (\\S+)$")
    public void add_collateral_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            StaticData staticData = testDataManager.getCollateralStaticData(id);
            pageManager.collateralStaticDataPage.addStaticData(staticData);
            testCase.embedTestData(staticData);
        }
    }

    /**
     * Description: edit collateral static data<br/>
     * Start page: collateral static data page <br/>
     * End page: collateral static data page<br/>
     * samples: edit collateral static data <b>my.old.staticdate</b> to <b>my.new.staticdate</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link StaticData}</li>
     * @param newId can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^edit collateral static data (\\S+) to (\\S+)$")
    public void edit_collateral_static_data(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StaticData oriStaticData = testDataManager.getCollateralStaticData(oriId);
        StaticData newStaticData = testDataManager.getCollateralStaticData(newId);
        pageManager.collateralStaticDataPage.openStaticDataType(oriStaticData);
        pageManager.collateralStaticDataPage.openStaticDataRecord(oriStaticData);
        pageManager.collateralStaticDataPage.editStaticData(newStaticData);
        testCase.embedTestData(oriStaticData);
        testCase.embedTestData(newStaticData);
    }

    /**
     * Description: disable or enable collateral static data<br/>
     * Start page: collateral static data page <br/>
     * End page: collateral static data page<br/>
     * samples: disable collateral static data <b>my.static.data</b><br/>
     * @param action can be one of following type:<br /><li>disabled</li><li>enabled</li>
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^(disabled|enabled) collateral static data (\\S+)$")
    public void disable_collateral_static_data(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            StaticData data = testDataManager.getCollateralStaticData(id);
            testCase.embedTestData(data);
            pageManager.collateralStaticDataPage.openStaticDataType(data);
            pageManager.collateralStaticDataPage.enableStaticData(data, action);
        }
    }

    /**
     * Description: navigate to collateral static data page<br/>
     * Start page: anyPage <br/>
     * End page: collateral static data page<br/>
     * samples: navigate to collateral static data page<br/>
     * @throws Exception
     */
    @When("^navigate to collateral static data page$")
    public void navigate_to_collateral_static_data_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.COLLATERAL_STATIC_DATA);
    }

    /**
     * Description: add collateral static data UDF value<br/>
     * Start page: UDF page <br/>
     * End page: UDF page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li></li>
     * @see
     * @throws Exception
     */
    @When("^add collateral static data agreement UDF values (\\S+)$")
    public void add_collateral_static_data_agreement_udf_values(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids){
            StaticData udfValue = testDataManager.getUdfValue(id);
            pageManager.udfValuesPage.addStaticData(udfValue);
            testCase.embedTestData(udfValue);
        }
    }

    /**
     * Description: edit collateral static data UDF value<br/>
     * Start page: UDF page <br/>
     * End page: UDF page<br/>
     * samples: <br/>
     * @param oriAgreementUdfValueId can be one of following type:<br /><li>{@link}</li>
     * @param newAgreementUdfValueId can be one of following type:<br /><li>{@link}</li>
     * @throws Exception
     */
    @When("^edit collateral static data agreement UDF values (\\S+)$")
    public void edit_collateral_static_data_agreement_udf_values(String oriAgreementUdfValueId, String newAgreementUdfValueId) throws Exception {
        StaticData oriAgreementUdfValue = testDataManager.getUdfValue(oriAgreementUdfValueId);
        StaticData newAgreementUdfValue = testDataManager.getUdfValue(newAgreementUdfValueId);
        pageManager.udfValuesPage.openStaticDataType(oriAgreementUdfValue);
        pageManager.udfValuesPage.openStaticDataRecord(oriAgreementUdfValue);
        pageManager.udfValuesPage.editStaticData(newAgreementUdfValue);
        testCase.embedTestData(oriAgreementUdfValue);
        testCase.embedTestData(newAgreementUdfValue);
    }

    /**
     * Description: navigate to Asset Holdings and Valuation Report<br/>
     * Start page: anyPage <br/>
     * End page: AHV report page<br/>
     * samples: navigate to Asset Holdings and Valuation Report<br/>
     * @throws Exception
     */
    @When("^navigate to Asset Holdings and Valuation Report$")
    public void navigate_to_asset_holdings_and_valuation_report() throws Exception {
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ASSET_HOLDINGS_AND_VALUATION);
    }

    /**
     * Description: navigate to Asset Management Report<br/>
     * Start page: anyPage <br/>
     * End page: Asset Management Report page<br/>
     * samples: navigate to Asset Management Report<br/>
     * @throws Exception
     */
    @When("^navigate to Asset Management Report$")
    public void navigate_to_asset_management_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ASSET_MANAGEMENT);
    }

    /**
     * Description: navigate to Asset Settlements Report<br/>
     * Start page: anyPage <br/>
     * End page: Asset Settlements Report page<br/>
     * samples: navigate to Asset Settlements Report<br/>
     * @throws Exception
     */
    @When("^navigate to Asset Settlements Report$")
    public void navigate_to_asset_settlements_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ASSET_SETTLEMENTS);
    }

    /**
     * Description: navigate to Concentration Limits Report<br/>
     * Start page: anyPage <br/>
     * End page: Concentration Limits Report<br/>
     * samples: navigate to Concentration Limits Report<br/>
     * @throws Exception
     */
    @When("^navigate to Concentration Limits Report$")
    public void navigate_to_concentration_limits_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.CONCENTRATION_LIMITS);
    }

    /**
     * Description: navigate to Corporate Actions Report<br/>
     * Start page: anyPage <br/>
     * End page: Corporate Actions Report<br/>
     * samples: navigate to Corporate Actions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Corporate Actions Report$")
    public void navigate_to_corporate_actions_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.CORPORATE_ACTIONS);
    }

    /**
     * Description: Corporate Actions Report<br/>
     * Start page: anyPage <br/>
     * End page: Ineligible Asset Report<br/>
     * samples: Corporate Actions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Ineligible Asset Report$")
    public void navigate_to_ineligible_asset_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INELIGIBLE_ASSET);
    }

    /**
     * Description: navigate to Inventory Manager Report<br/>
     * Start page: anyPage <br/>
     * End page: Inventory Manager Report<br/>
     * samples: navigate to Inventory Manager Report<br/>
     * @throws Exception
     */
    @When("^navigate to Inventory Manager Report$")
    public void navigate_to_Inventory_Manager_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INVENTORY_MANAGER);
    }

    /**
     * Description: navigate to report lists page<br/>
     * Start page: anyPage <br/>
     * End page: saved reports page<br/>
     * samples: navigate to report lists page<br/>
     * @throws Exception
     */
    @When("^navigate to report lists page$")
    public void navigate_to_report_list_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
    }

    /**
     * Description: navigate to Rehypothecation Eligibility Report<br/>
     * Start page: anyPage <br/>
     * End page: Rehypothecation Eligibility Report<br/>
     * samples: navigate to Rehypothecation Eligibility Report<br/>
     * @throws Exception
     */
    @When("^navigate to Rehypothecation Eligibility Report$")
    public void navigate_to_Rehypothecation_Eligibility_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REHYPOTHECATION_ELIGIBILITY);
    }

    /**
     * Description: navigate to Security Instruments Report<br/>
     * Start page: anyPage <br/>
     * End page: Security Instruments Report<br/>
     * samples: navigate to Security Instruments Report<br/>
     * @throws Exception
     */
    @When("^navigate to Security Instruments Report$")
    public void navigate_to_security_instruments_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.SECURITY_INSTRUMENTS);
    }

    /**
     * Description: navigate to Audit Agreements Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Agreements Report<br/>
     * samples: navigate to Audit Agreements Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Agreements Report$")
    public void navigate_to_audit_agreements_report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_AUDIT_AGREEMENTS);
    }

    /**
     * Description: navigate to Audit Asset Definition Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Asset Definition Report<br/>
     * samples: navigate to Audit Asset Definition Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Asset Definition Report$")
    public void navigate_to_Audit_Asset_Definition_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ASSET_DEFINITION);
    }

    /**
     * Description: navigate to Audit Assets Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Assets Report<br/>
     * samples: navigate to Audit Assets Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Assets Report$")
    public void navigate_to_Audit_Assets_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ASSETS);
    }

    /**
     * Description: navigate to Audit Eligibility Rule Template Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Eligibility Rule Template Report<br/>
     * samples: navigate to Audit Eligibility Rule Template Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Eligibility Rule Template Report$")
    public void navigate_to_Audit_Eligibility_Rule_Template_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ELIGIBILITY_RULE_TEMPLATE);
    }

    /**
     * Description: navigate to Audit Holiday Centres Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Holiday Centres Report<br/>
     * samples: navigate to Audit Holiday Centres Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Holiday Centres Report$")
    public void navigate_to_Audit_Holiday_Centres_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_HOLIDAY_CENTRES);
    }

    /**
     * Description: navigate to Audit Interest Rates Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Interest Rates Report<br/>
     * samples: navigate to Audit Interest Rates Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Interest Rates Report$")
    public void navigate_to_Audit_Interest_Rates_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_INTEREST_RATES);
    }

    /**
     * Description: navigate to Audit Optimisation Rule Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Optimisation Rule Report<br/>
     * samples: navigate to Audit Optimisation Rule Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Optimisation Rule Report$")
    public void navigate_to_Audit_Optimisation_Rule_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_OPTIMISATION_RULE);
    }

    /**
     * Description: navigate to Audit Organisations Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Organisations Report<br/>
     * samples: navigate to Audit Organisations Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Organisations Report$")
    public void navigate_to_Audit_Organisations_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ORGANISATIONS);
    }

    /**
     * Description: navigate to Audit Roles Administration Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Roles Administration Report<br/>
     * samples: navigate to Audit Roles Administration Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Roles Administration Report$")
    public void navigate_to_Audit_Roles_Administration_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ROLES_ADMINISTRATION);
    }

    /**
     * Description: navigate to Audit Settlement Details Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Settlement Details Report<br/>
     * samples: navigate to Audit Settlement Details Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Settlement Details Report$")
    public void navigate_to_Audit_Settlement_Details_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_SETTLEMENT_DETAILS);
    }

    /**
     * Description: navigate to Audit statements Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit statements Report<br/>
     * samples: navigate to Audit statements Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit statements Report$")
    public void navigate_to_Audit_statements_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_STATEMENTS);
    }

    /**
     * Description: navigate to Audit Trades Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit Trades Report<br/>
     * samples: navigate to Audit Trades Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit Trades Report$")
    public void navigate_to_Audit_Trades_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_TRADES);
    }

    /**
     * Description: navigate to Audit User Administration Report<br/>
     * Start page: anyPage <br/>
     * End page: Audit User Administration Report<br/>
     * samples: navigate to Audit User Administration Report<br/>
     * @throws Exception
     */
    @When("^navigate to Audit User Administration Report$")
    public void navigate_to_Audit_User_Administration_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_USER_ADMINISTRATION);
    }

    /**
     * Description: navigate to Frequency Distribution Log Report<br/>
     * Start page: anyPage <br/>
     * End page: Frequency Distribution Log Report<br/>
     * samples: navigate to Frequency Distribution Log Report<br/>
     * @throws Exception
     */
    @When("^navigate to Frequency Distribution Log Report$")
    public void navigate_to_Frequency_Distribution_Log_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.FREQUENCY_DISTRIBUTION_LOG);
    }

    /**
     * Description: navigate to Agreements Report<br/>
     * Start page: anyPage <br/>
     * End page: Agreements Report<br/>
     * samples: navigate to Agreements Report<br/>
     * @throws Exception
     */
    @When("^navigate to Agreements Report$")
    public void navigate_to_Agreements_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.AGREEMENTS);
    }

    /**
     * Description: navigate to Eligibility Rules Template Report<br/>
     * Start page: anyPage <br/>
     * End page: Eligibility Rules Template Report<br/>
     * samples: navigate to Eligibility Rules Template Report<br/>
     * @throws Exception
     */
    @When("^navigate to Eligibility Rules Template Report$")
    public void navigate_to_Eligibility_rules_Template_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ELIGIBILITY_RULES_TEMPLATE);
    }

    /**
     * Description: navigate to Historical FX Rates Report<br/>
     * Start page: anyPage <br/>
     * End page: Historical FX Rates Report<br/>
     * samples: navigate to Historical FX Rates Report<br/>
     * @throws Exception
     */
    @When("^navigate to Historical FX Rates Report$")
    public void navigate_to_Historical_fx_Rates_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_FX_RATES);
    }

    /**
     * Description: navigate to Historical Interest Rate Report<br/>
     * Start page: anyPage <br/>
     * End page: Historical Interest Rate Report<br/>
     * samples: navigate to Historical Interest Rate Report<br/>
     * @throws Exception
     */
    @When("^navigate to Historical Interest Rate Report$")
    public void navigate_to_Historical_Interest_Rate_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_INTEREST_RATE);
    }

    /**
     * Description: navigate to Licensing Report<br/>
     * Start page: anyPage <br/>
     * End page: Licensing Report<br/>
     * samples: navigate to Licensing Report<br/>
     * @throws Exception
     */
    @When("^navigate to Licensing Report$")
    public void navigate_to_Licensing_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.LICENSING);
    }

    /**
     * Description: navigate to Organisation Agreements Report<br/>
     * Start page: anyPage <br/>
     * End page: Organisation Agreements Report<br/>
     * samples: navigate to Organisation Agreements Report<br/>
     * @throws Exception
     */
    @When("^navigate to Organisation Agreements Report$")
    public void navigate_to_Organisation_Agreements_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ORGANISATION_AGREEMENTS);
    }

    /**
     * Description: navigate to Settlement Instructions Report<br/>
     * Start page: anyPage <br/>
     * End page: Settlement Instructions Report<br/>
     * samples: navigate to Settlement Instructions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Settlement Instructions Report$")
    public void navigate_to_Settlement_Instructions_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.SETTLEMENT_INSTRUCTIONS);
    }

    /**
     * Description: navigate to Bad Staging Feed Report<br/>
     * Start page: anyPage <br/>
     * End page: Bad Staging Feed Report<br/>
     * samples: navigate to Bad Staging Feed Report<br/>
     * @throws Exception
     */
    @When("^navigate to Bad Staging Feed Report$")
    public void navigate_to_Bad_Staging_Feed_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.BAD_STAGING_FEED);
    }

    /**
     * Description: set Bad Staging Feed Report filter or save template<br/>
     * Start page: Bad Staging Feed Report<br/>
     * End page: Bad Staging Feed Report<br/>
     * samples: search Bad Staging Feed Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Bad Staging Feed Report (\\S+)$")
    public void search_bad_staging_feed_report(String id)throws Exception{
        BadStagingFeedReportFilter badStagingFeedReportFilter = testDataManager.getBadStagingFeedReportFilter(id);
        pageManager.badStagingFeedReportPage.setBadStagingFeedReport(badStagingFeedReportFilter);
        if(badStagingFeedReportFilter.getOutputFormat() != null && !badStagingFeedReportFilter.getOutputFormat().isEmpty()){
            pageManager.badStagingFeedReportPage.editOutputFormat();
            pageManager.badStagingFeedReportPage.setOutputParameters(badStagingFeedReportFilter.getOutputFormat());
            pageManager.badStagingFeedReportPage.applyChanges();
        }
        if(badStagingFeedReportFilter.getSaveAsTemplate() != null){
            pageManager.badStagingFeedReportPage.saveAsTemplate(badStagingFeedReportFilter);
        }
        testCase.embedTestData(badStagingFeedReportFilter);
    }

    /**
     * Description: navigate to Cash And Accrual Report<br/>
     * Start page: anyPage <br/>
     * End page: Cash And Accrual Report<br/>
     * samples: navigate to Cash And Accrual Report<br/>
     * @throws Exception
     */
    @When("^navigate to Cash And Accrual Report$")
    public void navigate_to_Cash_And_Accrual_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.CASH_AND_ACCRUAL);
    }

    /**
     * Description: navigate to Interest Applied Report<br/>
     * Start page: anyPage <br/>
     * End page: Interest Applied Report<br/>
     * samples: navigate to Interest Applied Report<br/>
     * @throws Exception
     */
    @When("^navigate to Interest Applied Report$")
    public void navigate_to_Interest_Applied_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_APPLIED);
    }

    /**
     * Description: navigate to Interest Calculations Scheduled Report<br/>
     * Start page: anyPage <br/>
     * End page: Interest Calculations Scheduled Report<br/>
     * samples: navigate to Interest Calculations Scheduled Report<br/>
     * @throws Exception
     */
    @When("^navigate to Interest Calculations Scheduled Report$")
    public void navigate_to_Interest_Calculations_Scheduled_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_CALCULATIONS_SCHEDULED);
    }

    /**
     * Description: navigate to Interest Changes Report<br/>
     * Start page: anyPage <br/>
     * End page: Interest Changes Report<br/>
     * samples: navigate to Interest Changes Report<br/>
     * @throws Exception
     */
    @When("^navigate to Interest Changes Report$")
    public void navigate_to_Interest_Changes_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_CHANGES);
    }

    /**
     * Description: navigate to Month End Report<br/>
     * Start page: anyPage <br/>
     * End page: Month End Report<br/>
     * samples: navigate to Month End Report<br/>
     * @throws Exception
     */
    @When("^navigate to Month End Report$")
    public void navigate_to_Month_End_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.MONTH_END);
    }

    /**
     * Description: navigate to Rejected Trades Report<br/>
     * Start page: anyPage <br/>
     * End page: Rejected Trades Report<br/>
     * samples: navigate to Rejected Trades Report<br/>
     * @throws Exception
     */
    @When("^navigate to Rejected Trades Report$")
    public void navigate_to_Rejected_Trades_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REJECTED_TRADES);
    }

    /**
     * Description: navigate to Repo Rejected Trades Report<br/>
     * Start page: anyPage <br/>
     * End page: Repo Rejected Trades Report<br/>
     * samples: navigate to Repo Rejected Trades Report<br/>
     * @throws Exception
     */
    @When("^navigate to Repo Rejected Trades Report$")
    public void navigate_to_Repo_Rejected_Trades_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_REJECTED_TRADES);
    }

    /**
     * Description: navigate to Repo Trades Output Report<br/>
     * Start page: anyPage <br/>
     * End page: Repo Trades Output Report<br/>
     * samples: navigate to Repo Trades Output Report<br/>
     * @throws Exception
     */
    @When("^navigate to Repo Trades Output Report$")
    public void navigate_to_Repo_Trades_Output_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_TRADES_OUTPUT);
    }

    /**
     * Description: navigate to Trades Output Report<br/>
     * Start page: anyPage <br/>
     * End page: Trades Output Report<br/>
     * samples: navigate to Trades Output Report<br/>
     * @throws Exception
     */
    @When("^navigate to Trades Output Report$")
    public void navigate_to_Trades_Output_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.TRADES_OUTPUT);
    }

    /**
     * Description: navigate to Daily Exposure Report<br/>
     * Start page: anyPage <br/>
     * End page: Daily Exposure Report<br/>
     * samples: navigate to Daily Exposure Report<br/>
     * @throws Exception
     */
    @When("^navigate to Daily Exposure Report$")
    public void navigate_to_Daily_Exposure_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.DAILY_EXPOSURE);
    }

    /**
     * Description: navigate to BRFIRV Report<br/>
     * Start page: anyPage <br/>
     * End page: BRFIRV Report<br/>
     * samples: navigate to BRFIRV Report<br/>
     * @throws Exception
     */
    @When("^navigate to BRFIRV Report$")
    public void navigate_to_BRFIRV_Report() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: navigate to BREMEA Report<br/>
     * Start page: anyPage <br/>
     * End page: BREMEA Report<br/>
     * samples: navigate to BREMEA Report<br/>
     * @throws Exception
     */
    @When("^navigate to BREMEA Report$")
    public void navigate_to_BREMEA_Report() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: navigate to Dispute History Report<br/>
     * Start page: anyPage <br/>
     * End page: Dispute History Report<br/>
     * samples: navigate to Dispute History Report<br/>
     * @throws Exception
     */
    @When("^navigate to Dispute History Report$")
    public void navigate_to_Dispute_History_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.DISPUTE_HISTORY);
    }

    /**
     * Description: navigate to Historical Exposure Report<br/>
     * Start page: anyPage <br/>
     * End page: Historical Exposure Report<br/>
     * samples: navigate to Historical Exposure Report<br/>
     * @throws Exception
     */
    @When("^navigate to Historical Exposure Report$")
    public void navigate_to_Historical_Exposure_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_EXPOSURE);
    }

    /**
     * Description: navigate to Internal Review Report<br/>
     * Start page: anyPage <br/>
     * End page: Internal Review Report<br/>
     * samples: navigate to Internal Review Report<br/>
     * @throws Exception
     */
    @When("^navigate to Internal Review Report$")
    public void navigate_to_Internal_Review_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.INTERNAL_REVIEW);
    }

    /**
     * Description: navigate to MIS Report<br/>
     * Start page: anyPage <br/>
     * End page: MIS Report<br/>
     * samples: navigate to MIS Report<br/>
     * @throws Exception
     */
    @When("^navigate to MIS Report$")
    public void navigate_to_MIS_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.MIS);
    }

    /**
     * Description: navigate to MtM Comparison Report<br/>
     * Start page: anyPage <br/>
     * End page: MtM Comparison Report<br/>
     * samples: navigate to MtM Comparison Report<br/>
     * @throws Exception
     */
    @When("^navigate to MtM Comparison Report$")
    public void navigate_to_MtM_Comparison_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.MTM_COMPARISON);
    }

    /**
     * Description: navigate to Repo Daily Exposure Report<br/>
     * Start page: anyPage <br/>
     * End page: Repo Daily Exposure Report<br/>
     * samples: navigate to Repo Daily Exposure Report<br/>
     * @throws Exception
     */
    @When("^navigate to Repo Daily Exposure Report$")
    public void navigate_to_Repo_Daily_Exposure_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_DAILY_EXPOSURE);
    }

    /**
     * Description: navigate to Repo Historical Exposure Report<br/>
     * Start page: anyPage <br/>
     * End page: Repo Historical Exposure Report<br/>
     * samples: navigate to Repo Historical Exposure Report<br/>
     * @throws Exception
     */
    @When("^navigate to Repo Historical Exposure Report$")
    public void navigate_to_Repo_Historical_Exposure_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_HISTORICAL_EXPOSURE);
    }

    /**
     * Description: navigate to TSA|cashflow Report<br/>
     * Start page: anyPage <br/>
     * End page: TSA|cashflow Report<br/>
     * samples: navigate to TSA|cashflow Report<br/>
     * @throws Exception
     */
    @When("^navigate to (?:TSA|Cashflow) Report$")
    public void navigate_to_TSA_Report() throws Exception {
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.CASHFLOW);
    }

    /**
     * Description: navigate to What If Scenario Report<br/>
     * Start page: anyPage <br/>
     * End page: What If Scenario Report<br/>
     * samples: navigate to What If Scenario Report<br/>
     * @throws Exception
     */
    @When("^navigate to What If Scenario Report$")
    public void navigate_to_What_If_Scenario_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.WHAT_IF_SCENARIO);
    }

    /**
     * Description: navigate to Manually Overridden Asset Price Report<br/>
     * Start page: anyPage <br/>
     * End page: Manually Overridden Asset Price Report<br/>
     * samples: navigate to Manually Overridden Asset Price Report<br/>
     * @throws Exception
     */
    @When("^navigate to Manually Overridden Asset Price Report$")
    public void navigate_to_Manually_Overridden_Asset_Price_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.MANUALLY_OVERRIDDEN_ASSET_PRICE);
    }

    /**
     * Description: navigate to Price Age Exceptions Report<br/>
     * Start page: anyPage <br/>
     * End page: Price Age Exceptions Report<br/>
     * samples: navigate to Price Age Exceptions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Price Age Exceptions Report$")
    public void navigate_to_Price_Age_Exceptions_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.PRICE_AGE_EXCEPTIONS);
    }

    /**
     * Description: navigate to Price Exceptions Report<br/>
     * Start page: anyPage <br/>
     * End page: Price Exceptions Report<br/>
     * samples: navigate to Price Exceptions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Price Exceptions Report$")
    public void navigate_to_Price_Exceptions_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.PRICE_EXCEPTIONS);
    }

    /**
     * Description: navigate to Price Variance Exceptions Report<br/>
     * Start page: anyPage <br/>
     * End page: Price Variance Exceptions Report<br/>
     * samples: navigate to Price Variance Exceptions Report<br/>
     * @throws Exception
     */
    @When("^navigate to Price Variance Exceptions Report$")
    public void navigate_to_Price_Variance_Exceptions_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.PRICE_VARIANCE_EXCEPTIONS);
    }

    /**
     * Description: navigate to Stp Dashboard Report<br/>
     * Start page: anyPage <br/>
     * End page: Stp Dashboard Report<br/>
     * samples: navigate to Stp Dashboard Report<br/>
     * @throws Exception
     */
    @When("^navigate to Stp Dashboard Report$")
    public void navigate_to_stp_Dashboard_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.STP_DASHBOARD);
    }

    /**
     * Description: navigate to Role Approval Report<br/>
     * Start page: anyPage <br/>
     * End page: Role Approval Report<br/>
     * samples: navigate to Role Approval Report<br/>
     * @throws Exception
     */
    @When("^navigate to Role Approval Report$")
    public void navigate_to_Role_Approval_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.ROLE_APPROVAL);
    }

    /**
     * Description: navigate to User Profile Report<br/>
     * Start page: anyPage <br/>
     * End page: User Profile Report<br/>
     * samples: navigate to User Profile Report<br/>
     * @throws Exception
     */
    @When("^navigate to User Profile Report$")
    public void navigate_to_User_Profile_Report() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.REPORT_LIST);
        pageManager.reportListPage.navigate(ReportMenu.USER_PROFILE);
    }

    /*@When("^set report filter (\\S+)$")
    public void set_report_filter(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^set report output parameters (\\S+)$")
    public void set_report_output_parameters(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report template (\\S+)$")
    public void save_report_template(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to excel raw$")
    public void run_report_to_excel_raw() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to excel preserve grouping$")
    public void run_report_to_excel_preserve_grouping() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to csv$")
    public void run_report_to_csv() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to pdf$")
    public void run_report_to_pdf() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to xml$")
    public void run_report_to_xml() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^run report to gui$")
    public void run_report_to_gui() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report result to excel raw$")
    public void save_report_result_to_excel_raw() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report result to excel preserve grouping$")
    public void save_report_result_to_excel_preserve_grouping() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report result to csv$")
    public void save_report_result_to_csv() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report result to pdf$")
    public void save_report_result_to_pdf() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^save report result to xml$")
    public void save_report_result_to_xml() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^delete report batch (\\S+)$")
    public void delete_report_batch(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }*/

    /**
     * Description: click run now button in task scheduler<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: run task scheduler <b>my.taskSchedulerDetail</b><br/>
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^run task scheduler (\\S+)$")
    public void run_task_scheduler(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(id);
        pageManager.taskSchedulerPage.runNow(taskSchedulerDetail);
        testCase.embedTestData(taskSchedulerDetail);
    }

    /**
     * Description: edit task scheduler<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: edit task scheduler <b>my.taskSchedulerDetail</b><br/>
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^edit task scheduler (\\S+)$")
    public void edit_task_scheduler(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(id);
        pageManager.taskSchedulerPage.editTaskSchedule(taskSchedulerDetail);
        pageManager.taskSchedulerPage.inputTaskSchedule(taskSchedulerDetail);
        pageManager.taskSchedulerPage.save();
        testCase.embedTestData(taskSchedulerDetail);
    }

    /**
     * Description: duplicate task scheduler<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: duplicate task scheduler <b>my.taskSchedulerDetail</b><br/>
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^duplicate task scheduler (\\S+)$")
    public void duplicate_task_scheduler(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(id);
        pageManager.taskSchedulerPage.duplicatedTaskSchedule(taskSchedulerDetail);
        testCase.embedTestData(taskSchedulerDetail);
    }

    /**
     * Description: delete task scheduler<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: remove task scheduler <b>my.taskSchedulerDetail</b><br/>
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^remove task scheduler (\\S+)$")
    public void remove_task_scheduler(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(id);
        pageManager.taskSchedulerPage.removeTaskSchedule(taskSchedulerDetail);
        testCase.embedTestData(taskSchedulerDetail);
    }

    /**
     * Description: wait task done<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: wait <b>my.taskSchedulerDetail</b> task scheduler to be done<br/>
     * @param id can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^wait (\\S+) task scheduler to be done$")
    public void wait_task_scheduler_to_be_done(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(id);
        pageManager.taskSchedulerPage.switchToTaskScheduleTab(taskSchedulerDetail);
        testCase.embedTestData(taskSchedulerDetail);
        throw new PendingException();
    }

    /**
     * Description: click save all button in task scheduler page<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: save all workflow tasks<br/>
     * @throws Exception
     */
    @When("^save all workflow tasks$")
    public void save_all_workflow_tasks() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.taskSchedulerPage.saveAllWorkflowTasks();
    }

    /**
     * Description: click save my tasks button in task scheduler page<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: save all my workflow tasks<br/>
     * @throws Exception
     */
    @When("^save all my workflow tasks$")
    public void save_all_my_workflow_tasks() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.taskSchedulerPage.saveAllMyWorkflowTasks();
    }

    /**
     * Description: search event log <br/>
     * Start page: event log search page<br/>
     * End page: event log search page<br/>
     * samples: event log search <b>my.eventlog.search</b><br/>
     * @param id can be one of following type:<br /><li>{@link EventLogSearch}</li>
     * @throws Exception
     */
    @When("^event log search (\\S+)$")
    public void event_log_search(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EventLogSearch eventLogSearch = testDataManager.getEventLogSearch(id);
        pageManager.eventLogSearchPage.setSearchEventLog(eventLogSearch);
        pageManager.eventLogSearchPage.searchEventLog();
    }

    /**
     * Description: navigate to event log page<br/>
     * Start page: anyPage <br/>
     * End page: event log search page<br/>
     * samples: navigate to event log page<br/>
     * @throws Exception
     */
    @When("^navigate to event log page$")
    public void navigate_to_event_log_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.EVENT_LOG);
    }

    /**
     * Description: add users<br/>
     * Start page: user&role administration page<br/>
     * End page: user&role administration page<br/>
     * samples: add user <b>my.user</b><br/>
     * @param ids can be one of following type:<br /><li>{@link User}</li>
     * @throws Exception
     */
    @When("^add users? (\\S+)$")
    public void add_user(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            User user = testDataManager.getUser(id);
            pageManager.userAndRoleAdministrationNavigationPannel.enterCreateNewUserPage();
            pageManager.createNewUserPage.inputNewUser(user);
            pageManager.createNewUserPage.submit();
            testCase.embedTestData(user);
        }
    }

    /**
     * Description: select and edit user<br/>
     * Start page: user edit profile page<br/>
     * End page: user edit profile page<br/>
     * samples: edit user <b>my.user</b><br/>
     * @param id can be one of following type:<br /><li>{@link User}</li>
     * @throws Exception
     */
    @When("^edit user (\\S+)$")
    public void edit_user(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        User user = testDataManager.getUser(id);
        pageManager.userAndRoleAdministrationNavigationPannel.enterEditProfileForUserPage(user);
        if (user.getRole()!=null) {
            pageManager.editProfileForUserPage.enterEditUserRolesPage();
            pageManager.editUserRolesPage.editUserRoles(user);
            pageManager.editUserRolesPage.save();
        }
        if(user.getApprovalProfile()!=null) {
            pageManager.editProfileForUserPage.enterEditApprovalProfilePage();
            pageManager.editApprovalProfilePage.editApprovalProfile(user);
            pageManager.editApprovalProfilePage.save();
        }
        if(user.getPreferences()!=null) {
            pageManager.editProfileForUserPage.enterEditUserPreferencrPage();
            pageManager.editUserPreferencePage.editUserPreference(user);
            pageManager.editUserPreferencePage.submit();
        }
        pageManager.editProfileForUserPage.setAccessStatus(user);
        pageManager.editProfileForUserPage.setChangePasswordConstraints(user);
        if(user.getChangePassword() != null) {
            pageManager.editProfileForUserPage.setChangePassword(user);
            pageManager.editProfileForUserPage.savePassword();
        }
        testCase.embedTestData(user);
    }

    /**
     * Description: add administer roles<br/>
     * Start page: administer roles page<br/>
     * End page: administer roles page<br/>
     * samples: add administer role <b>my.administerRole</b><br/>
     * @param ids can be one of following type:<br /><li>{@link AdministerRole}</li>
     * @throws Exception
     */
    @When("^add administer roles? (\\S+)$")
    public void add_administer_roles(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            AdministerRole administerRole = testDataManager.getAdministerRole(id);
            pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerRolesPage();
            pageManager.administerRolesPage.setRoleName(administerRole);
            pageManager.administerRolesPage.submit();
            testCase.embedTestData(administerRole);
        }
    }

    /**
     * Description: enable administer roles<br/>
     * Start page: administer roles page<br/>
     * End page: administer roles page<br/>
     * samples: enable administer role <b>my.administerRole</b><br/>
     * @param ids can be one of following type:<br /><li>{@link AdministerRole}</li>
     * @throws Exception
     */
    @When("^enable administer roles? (\\S+)$")
    public void enable_administer_roles(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            AdministerRole administerRole = testDataManager.getAdministerRole(id);
            pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerRolesPage();
            pageManager.administerRolesPage.changeRoleStatus(administerRole);
            testCase.embedTestData(administerRole);
        }
    }

    /**
     * Description: disable administer roles<br/>
     * Start page: administer roles page<br/>
     * End page: administer roles page<br/>
     * samples: disable administer role <b>my.administerRole</b><br/>
     * @param ids can be one of following type:<br /><li>{@link AdministerRole}</li>
     * @throws Exception
     */
    @When("^disable administer roles? (\\S+)$")
    public void disable_administer_roles(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids){
            AdministerRole administerRole = testDataManager.getAdministerRole(id);
            pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerRolesPage();
            pageManager.administerRolesPage.changeRoleStatus(administerRole);
            testCase.embedTestData(administerRole);
        }
    }

    /**
     * Description: edit privileges<br/>
     * Start page: privilege page<br/>
     * End page: privilege page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^edit privileges (\\S+)$")
    public void edit_privilege(List<String> ids) throws Exception {
        for (String id:ids){
            Privileges privileges = testDataManager.getPrivileges(id);
            this.pageManager.privilegesPage.editPrivileges(privileges);
            this.pageManager.privilegesPage.save();
            testCase.embedTestData(privileges);
        }
    }

    /**
     * Description: click approve ticked button in user TAB<br/>
     * Start page: user&role approval page<br/>
     * End page: user&role approval page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^approve user (\\S+)$")
    public void approve_user(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: search user for approve<br/>
     * Start page: user&role approval page<br/>
     * End page: user&role approval page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search user for approve (\\S+)$")
    public void search_user_for_approve(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: click approve ticked button in role TAB<br/>
     * Start page: user&role approval page<br/>
     * End page: user&role approval page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^approve ticked role privilege (\\S+)$")
    public void approve_ticked_role_privilege(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: click reject ticked button in Role TAB<br/>
     * Start page: user&role approval page<br/>
     * End page: user&role approval page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^reject ticked role privilege (\\S+)$")
    public void reject_ticked_role_privilege(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: set password constraints<br/>
     * Start page: Password Constraints<br/>
     * End page: Password Constraints<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^set password constraints (\\S+)$")
    public void set_password_constraints(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: set system preferences<br/>
     * Start page: system preferences page<br/>
     * End page: system preferences page<br/>
     * samples: set system preferences <b>my.system.preferences</b><br/>
     * @param id can be one of following type:<br /><li>{@link SystemPreference}</li>
     * @throws Exception
     */
    @When("^set system preferences (\\S+)$")
    public void set_system_preferences(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        SystemPreference systemPreference = testDataManager.getSystemPreference(id);
        if (systemPreference.getMessaging() != null) {
            pageManager.systemPreferencesPage.switchToMessagingTab();
            pageManager.systemPreferencesPage.editMessaging(systemPreference);
            pageManager.systemPreferencesPage.save();
            if(systemPreference.getApplicationPreferences() != null
                    || systemPreference.getCommunication() != null
                    || systemPreference.getLabels() != null
                    || systemPreference.getEmailInformation() != null)
                pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if (systemPreference.getApplicationPreferences() != null){
            pageManager.systemPreferencesPage.switchToApplicationPreferenceTab();
            pageManager.systemPreferencesPage.setApplicationPreferences(systemPreference);
            pageManager.systemPreferencesPage.save();
            if(systemPreference.getCommunication() != null
                    || systemPreference.getLabels() != null
                    || systemPreference.getEmailInformation() != null)
                pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if(systemPreference.getCommunication() != null){
            pageManager.systemPreferencesPage.switchToCommunicationTab();
            pageManager.systemPreferencesPage.setCommunication(systemPreference);
            pageManager.systemPreferencesPage.save();
            if(systemPreference.getLabels() != null
                    || systemPreference.getEmailInformation() != null)
                pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if(systemPreference.getLabels() != null) {
            pageManager.systemPreferencesPage.switchToLabelsTab();
            pageManager.systemPreferencesPage.setLabels(systemPreference);
            pageManager.systemPreferencesPage.save();
            if(systemPreference.getEmailInformation() != null)
                pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if(systemPreference.getEmailInformation() != null){
            pageManager.systemPreferencesPage.switchToEmailInformationTab();
            pageManager.systemPreferencesPage.setEmailInformation(systemPreference);
            pageManager.systemPreferencesPage.save();
        }
        testCase.embedTestData(systemPreference);
    }

    /**
     * Description: add static data in system data scheme<br/>
     * Start page: system data scheme page<br/>
     * End page: system data scheme page<br/>
     * samples: add system static data <b<my.statciData<br/>
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^add system static data (\\S+)$")
    public void add_system_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids) {
            StaticData staticData = testDataManager.getSystemStaticData(id);
            pageManager.schemesPage.addStaticData(staticData);
            testCase.embedTestData(staticData);
        }
    }

    /**
     * Description: navigate to system date schemes page<br/>
     * Start page: anyPage<br/>
     * End page: system data scheme page<br/>
     * samples: navigate to system date schemes page<br/>
     * @throws Exception
     */
    @When("^navigate to system date schemes page$")
    public void navigate_to_system_data_schemes_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.SCHEMES);
    }

    /**
     * Description: add holiday centres<br/>
     * Start page: holiday centre page<br/>
     * End page: holiday centre page<br/>
     * samples: add holiday centre <b>my.holidayCentre</b><br/>
     * @param ids can be one of following type:<br /><li>{@link HolidayCentre}</li>
     * @throws Exception
     */
    @When("^add holiday centres? (\\S+)$")
    public void add_holiday_centres(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for(String id : ids) {
            HolidayCentre holidayCentre = testDataManager.getHolidayCentre(id);
            this.pageManager.holidayCentrePage.editHolidatCentre(holidayCentre);
            testCase.embedTestData(holidayCentre);
        }
    }

    /**
     * Description: edit holiday centre<br/>
     * Start page: holiday centre page<br/>
     * End page: holiday centre page<br/>
     * samples: edit holiday centre <b>my.holidayCentre</b><br/>
     * @param id can be one of following type:<br /><li>{@link HolidayCentre}</li>
     * @throws Exception
     */
    @When("^edit holiday centre (\\S+)$")
    public void edit_holiday_centre(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        HolidayCentre holidayCentre = testDataManager.getHolidayCentre(id);
        this.pageManager.holidayCentrePage.openHolidayCentre(holidayCentre);
        this.pageManager.holidayCentrePage.editHolidatCentre(holidayCentre);
        testCase.embedTestData(holidayCentre);
    }
    /**
     * Description: feed holiday centre<br/>
     * Start page: anyPage<br/>
     * End page: feed holiday centre page<br/>
     * samples: feed holiday centre <b>my.holiday.centre</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedHolidayCentre}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed holiday centres? (\\S+) by (xml|excel|csv|xlsx)$")
    public void feed_holiday_centres(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_HOLIDAY_CENTRE);
        List<FeedHolidayCentre> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedHolidayCentre(id));
            testCase.embedTestData(testDataManager.getFeedHolidayCentre(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
        }
    }
    /**
     * Description: feed system static data<br/>
     * Start page: anyPage<br/>
     * End page: feed system static data page<br/>
     * samples: feed system static data <b>my.system.static.data</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedStaticData}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed system static data (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_system_static_data(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_STATIC_DATA);
        List<FeedStaticData> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedStaticData(id));
            testCase.embedTestData(testDataManager.getFeedStaticData(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed etd balances<br/>
     * Start page: anyPage<br/>
     * End page: feed etd balances page<br/>
     * samples: feed etd balances <b>my.etd.balances</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedEtdBalances}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed (delta|flush) etd balances (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_etd_balances(String mode, List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ETD_BALANCES);
        List<FeedEtdBalances> list = new ArrayList<FeedEtdBalances>();
        for (String id : ids) {
            list.add(testDataManager.getFeedEtdBalances(id));
            testCase.embedTestData(testDataManager.getFeedEtdBalances(id));
        }
        Feed feed = new Feed();
        if(mode.toLowerCase().equals("flush")){
            feed.setDeltaMode(DeltaModeType.FLUSH);
        }else if(mode.toLowerCase().equals("delta")){
            feed.setDeltaMode(DeltaModeType.DELTA);
        }
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
     * Description: feed etd adjustment<br/>
     * Start page: anyPage<br/>
     * End page: feed etd adjustment page<br/>
     * samples: feed etd adjustment <b>my.etd.adjustment</b> by <b>xml</b><br/>
     * @param ids can be one of following type:<br /><li>{@link FeedEtdAdjustment}</li>
     * @param type can be one of following value:<br /><li>xml</li><li>excel</li>
     * @throws Exception
     */
    @When("^feed etd adjustment (\\S+) by (xml|excel|xlsx|csv)$")
    public void feed_etd_adjustment(List<String> ids, String type) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.FEED_ETD_ADJUSTMENT);
        List<FeedEtdAdjustment> list = new ArrayList<>();
        for (String id : ids) {
            list.add(testDataManager.getFeedEtdAdjustment(id));
            testCase.embedTestData(testDataManager.getFeedEtdAdjustment(id));
        }
        Feed feed = new Feed();
        switch(type.toLowerCase()){
            case "xml" :
                pageManager.taskManagerPage.feedXmlFile(feed, list);
                break;
            case "excel" :
                pageManager.taskManagerPage.feedXlsFile(feed, list);
                break;
            case "xlsx" :
                pageManager.taskManagerPage.feedXlsxFile(feed, list);
                break;
            case "csv" :
                pageManager.taskManagerPage.feedCsvFile(feed, list);
                break;
        }
    }

    /**
     * Description: add regions<br/>
     * Start page: add/edit region page<br/>
     * End page: add/edit region page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^add regions? (\\S+)$")
    public void add_regions(List<String> ids) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: edit region<br/>
     * Start page: add/edit region page<br/>
     * End page: add/edit region page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^edit region (\\S+)$")
    public void edit_region(String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: add business frequency<br/>
     * Start page: search frequency page<br/>
     * End page: search frequency page<br/>
     * samples: <br/>
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^add business frequenc(?:y|ies) (\\S+)$")
    public void add_business_frequencies(List<String> ids) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: edit business frequency<br/>
     * Start page: search frequency page<br/>
     * End page: search frequency page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^edit business frequency (\\S+)$")
    public void edit_business_frequency(String resultId, String id) {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: change my password<br/>
     * Start page: anyPage<br/>
     * End page: login page<br/>
     * samples: change my password <b>my.password</b><br/>
     * @param id can be one of following type:<br /><li>{@link PasswordChange }</li>
     * @throws Exception
     */
    @When("^change my password (\\S+)$")
    public void change_my_password(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.CHANGE_MY_PASSWORD);
        PasswordChange passwordChange = testDataManager.getPasswordChange(id);
        pageManager.changeMyPasswordPage.setChangemMyPassword(passwordChange);
        pageManager.changeMyPasswordPage.submitChangeMyPassword();
        pageManager.changeMyPasswordPage.returnToApplication();
        testCase.embedTestData(passwordChange);
    }

    /**
     * Description: edit my favourite<br/>
     * Start page: anyPage<br/>
     * End page: edit facourite page<br/>
     * samples: edit my favourites <b>my.favourite</b><br/>
     * @param id can be one of following type:<br /><li>{@link UserFavourite}</li>
     * @throws Exception
     */
    @When("^edit my favourites (\\S+)$")
    public void edit_my_favourites(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.EDIT_MY_FAVOURITES);
        UserFavourite userFavourite = testDataManager.getUserFavourite(id);
        pageManager.editMyFavouritesPage.tickDeleteFavourite(userFavourite);
        pageManager.editMyFavouritesPage.delete();
        testCase.embedTestData(userFavourite);
    }

    /**
     * Description: add to my favourites<br/>
     * Start page: anyPage<br/>
     * End page: edit facourite page<br/>
     * samples: add to my favourites <b>my.favourite</b><br/>
     * @param id can be one of following type:<br /><li>{@link UserFavourite}</li>
     * @throws Exception
     */
    @When("^add to my favourites (\\S+)$")
    public void add_to_my_favourites(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.ADD_TO_MY_FAVOURITES);
        UserFavourite userFavourite = testDataManager.getUserFavourite(id);
        pageManager.addToMyFavouritesPage.editFavourites(userFavourite);
        pageManager.addToMyFavouritesPage.addFavourites();
        testCase.embedTestData(userFavourite);
    }

    /**
     * Description: edit my preferences<br/>
     * Start page: anyPage<br/>
     * End page: edit user preference page<br/>
     * samples: edit my preferences <b>my.preference</b><br/>
     * @param id can be one of following type:<br /><li>{@link UserPreference}</li>
     * @throws Exception
     */
    @When("^edit my preferences (\\S+)$")
    public void edit_my_preferences(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.EDIT_MY_PREFERENCES);
        UserPreference userPreference = testDataManager.getUserPreference(id);
        pageManager.editMyPreferencesPage.resetMyPreference();
        pageManager.editMyPreferencesPage.editMyPreference(userPreference);
        pageManager.editMyPreferencesPage.submitMyPreferencr();
        testCase.embedTestData(userPreference);
    }

    /**
     * Description: reset my preference<br/>
     * Start page: anyPage<br/>
     * End page: edit user preference page<br/>
     * samples: reset my preference<br/>
     * @throws Exception
     */
    @When("^reset my preference$")
    public void reset_my_preferences() throws Exception{
        pageManager.welcomePage.navigate(Menu.EDIT_MY_PREFERENCES);
        pageManager.editMyPreferencesPage.resetMyPreference();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^approve all role privilege$")
    public void approve_all_role_privilege() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: continue booking ignore breached in concontration limit breached page<br/>
     * Start page: concontration limit breached page<br/>
     * End page: agreement assets security summary page<br/>
     * @throws Exception
     */
    @When("^continue booking$")
    public void continue_booking_ignore_rule_breached() throws Exception {
        pageManager.concentrationLimitBreachedPage.continueMakeBookingIgnoreBreached();
    }

    /**
     * Description: view rules breach in agreement statement page<br/>
     * Start page: agreement statement page<br/>
     * End page: concontration limit breached page<br/>
     * @throws Exception
     */
    @When("^view rules breached$")
    public void view_rules_breached() throws Exception {
        pageManager.agreementStatementPage.viewRulesBreached();
    }

    /**
     * Description: view eligibility rules breach in agreement statement page<br/>
     * Start page: agreement statement page<br/>
     * End page: eligibility rule breached page<br/>
     * @throws Exception
     */
    @When("^view eligibilty rules breached$")
    public void view_eligibilty_rules_breached() throws Exception {
        pageManager.agreementStatementPage.viewEligibilityRuleBreached();
    }








    /**
     * Description: select model|orde in statement page<br/>
     * Start page: agreement statement page<br/>
     * End page: model|order|etd model statement page<br/>
     * samples: select <b>model</b> <b>my.AgreementStatement.model</b><br/>
     * @param view can be one of following type:<br /><li>model</li><li>order</li><li>etd model</li>
     * @param id if view is model,can be one of following type:<br /><li>{@link MultiModelAgreementStatement}</li>
     *           <li>{@link EtfAgreementStatement}</li>
     *           <li>{@link EtdModelAndModelCategoryAssetHoldingSummary}</li>
     * @throws Exception
     */
    @When("^select (model|order|etd model) (\\S+)$")
    public void select_model_or_order(String view, String id) throws Exception {
        switch (view.toLowerCase()) {
            case "model":
                MultiModelAgreementStatement multiModelAgreementStatement = testDataManager.getMultiModelAgreementStatement(id);
                testCase.embedTestData(multiModelAgreementStatement);
                pageManager.agreementStatementPage.switchModel(multiModelAgreementStatement);
                break;
            case "order":
                EtfAgreementStatement etfAgreementStatement=testDataManager.getEtfAgreementStatement(id);
                testCase.embedTestData(etfAgreementStatement);
                pageManager.agreementStatementPage.switchOrder(etfAgreementStatement);
                break;
            case "etd model":
                EtdAgreementStatement etdAgreementStatement = testDataManager.getEtdAgreementStatement(id);
                testCase.embedTestData(etdAgreementStatement);
                String currency;
                if (etdAgreementStatement.getEtdAssetHoldingSummary().getAgreementBaseCurrency() != null)
                    currency = etdAgreementStatement.getEtdAssetHoldingSummary().getAgreementBaseCurrency().getRealValue();
                else
                    currency = "";
                pageManager.agreementStatementPage.switchEtdModel(etdAgreementStatement.getEtdAssetHoldingSummary().getEtdModelAndModelCategoryAssetHoldingSummary().get(0)
                        , currency);
                break;
        }
    }

    /**
     * Description: Check ETD agreement top level statement in agreement statement page<br/>
     * Start page: ETD top level statement page <br/>
     * End page: ETD top level statement page <br/>
     * @param id can be one of the following type: <br /><li>{@link EtdAgreementStatement}</li>
     * Samples: etd agreement statement - expand category <b>my.etd.top.level.agreement.statement</b><br/>
     * @throws Exception
     */
    @Then("^(?:Agreement Statement - )?etd agreement statement - expand category (\\S+)$")
    public void etd_agreement_statement_expand_category(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        EtdAgreementStatement agreementStatement = testDataManager.getEtdAgreementStatement(id);
        testCase.embedTestData(agreementStatement);
        for (EtdModelAndModelCategoryAssetHoldingSummary etdModelAndModelCategoryAssetHoldingSummary
                : agreementStatement.getEtdAssetHoldingSummary().getEtdModelAndModelCategoryAssetHoldingSummary()) {
            String currency;
            if (agreementStatement.getEtdAssetHoldingSummary().getAgreementBaseCurrency() != null)
                currency = agreementStatement.getEtdAssetHoldingSummary().getAgreementBaseCurrency().getRealValue();
            else
                currency = "";
            pageManager.agreementStatementPage.expandETDModelCategory(etdModelAndModelCategoryAssetHoldingSummary, currency);
        }
    }

    /**
     * Description: click ajust ADJ button in ETD agreement model level statement page<br/>
     * Start page: ETD model level statement page<br/>
     * End page: ETD model level statement page<br/>
     * samples: etd agreement statement - click adjust adj button<br/>
     * @throws Exception
     */
    @When("^(?:Agreement Statement - )?etd agreement statement - click adjust adj button$")
    public void etd_agreement_statement_click_adjust_adj_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementStatementPage.clickEditMarginCallCalculationButton();
    }

    /**
     * Description: edit ADJ column values in ETD agreement model level statement page<br/>
     * Start page: ETD model level statement page<br/>
     * End page: ETD model level statement page<br/>
     * @param id can ben one of the following types<br/><li>{@link AgreementStatement}</li>
     * samples: etd agreement statement - edit model level ADJ column values <b>my.agreement.statement</b><br/>
     * @throws Exception
     */
    @When("^(?:Agreement Statement - )?etd agreement statement - edit model level ADJ column values (\\S+)$")
    public void etd_agreement_statement_edit_model_level_adj_column_values(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementStatement agreementStatement = testDataManager.getAgreementStatement(id);
        testCase.embedTestData(agreementStatement);
        pageManager.agreementStatementPage.inputAdjColumnValue(agreementStatement);
        pageManager.agreementStatementPage.saveADJAdjustmentChange(agreementStatement.getMarginCallCalculation().getEtdReq().getAdj());
    }

    /**
     * Description: click edit asset summary info button in statement page<br/>
     * Start page: agreement statement page<br/>
     * End page: agreement asset holding summary page<br/>
     * samples: edit asset summary info<br/>
     * @throws Exception
     */
    @When("^edit asset summary info$")
    public void edit_asset_summary_info() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementStatementPage.editAssetSummaryInfo();
    }

    /**
     * Description: expandOrCollapse asset type in Asset holding detail page<br/>
     * Start page: Asset holding detail page<br/>
     * End page: Asset holding detail page<br/>
     * samples: select <b>model</b> <b>my.AgreementStatement.model</b><br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @param ids if view is model,can be one of following type:<br /><li>{@link AssetBookingSummary}</li>
     * @throws Exception
     */
    @When("^Asset holding detail - (expand|collapse) asset type (\\S+)$")
    public void expandOrCollapse_asset_type(String action, List<String> ids) throws Exception {
        for (String id : ids) {
            AssetBookingSummary assetBookingSummary = testDataManager.getAssetBookingSummary(id);
            testCase.embedTestData(assetBookingSummary);
            switch (action.toLowerCase()) {
                case "collapse":
                    pageManager.agreementAssetsSummaryPage.expandOrCollapseAssetType("collapse", assetBookingSummary);
                    break;
                case "expand":
                    pageManager.agreementAssetsSummaryPage.expandOrCollapseAssetType("expand", assetBookingSummary);
                    break;
            }
        }
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @SuppressWarnings("DanglingJavadoc")
    @When("^navigate to agreement statement page$")
    public void navigate_to_agreement_statement_page() {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: search agreement<br/>
     * Start page: any page<br/>
     * End page: agreement search page<br/>
     * samples: search agreement <b>my.agreement.search</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearch}</li><li>{@link Agreement}</li><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @When("^search agreement (\\S+)$")
    public void search_agreement(String id) throws Exception {
        pageManager.welcomePage.navigate(Menu.AGREEMENT_SEARCH);
        AgreementSearch agreementSearch = testDataManager.getAgreementSearch(id);
        if(agreementSearch == null){
            Agreement agreement = testDataManager.getAgreement(id);
            if(agreement != null) {
                agreementSearch = testDataManager.getAgreement(id).toAgreementSearch();
            }else{
                agreementSearch = testDataManager.getAgreementSearchResult(id).toAgreementSearch();
            }
        }
        pageManager.agreementSearchPage.searchAgreement(agreementSearch);
        testCase.embedTestData(agreementSearch);
    }

    /**
     * Description: click clear button in agreement search page<br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     * samples: clear agreement search filter<br/>
     * @throws Exception
     */
    @When("^clear agreement search filter$")
    public void clear_agreement_search_filter() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementSearchPage.clearSearchFilter();
    }

    @When("^(?:Exposure Managerment - )?cancel bulk booking$")
    public void cancelBulkbooking() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.exposureManagementPage.cancelbooking();
    }

    /**
     * Description: click long view|view summary button in agreement search result <br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     * samples: agreement search result <b>long view</b><br/>
     * @param view can be one of following type:<br /><li>long view</li><li>view summary</li>
     * @throws Exception
     */
    @When("^agreement search result (long view|view summary)$")
    public void agreement_search_result_view(String view) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        switch (view.toLowerCase()) {
            case "long view":
                pageManager.agreementSearchPage.longView();
                break;
            case "view summary":
                pageManager.agreementSearchPage.summaryView();
                break;
        }
    }

    /**
     * Description: sort agreement by agreement search result head<br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     * samples: sort agreement by <b>agreementid</b><br/>
     * @param columnName can be one of following value:<br /><li>agreementid</li><li>principal</li><li>counterparty</li><li>cpty ID</li><li>credit support documentation</li><li>description</li><li>status</li><li>business line</li><li>owner</li>
     * @throws Exception
     */
    @When("^sort agreement by (agreementid|principal|counterparty|cpty ID|credit support documentation|description|status|business line|owner)$")
    public void sort_result_in_agreement_search_page(String columnName) {
        // Write code here that turns the phrase above into concrete actions
        //TODO the sorting() function should have an argument which is the columnName in AgreementSearchPage
        pageManager.agreementSearchPage.sorting();
        throw new PendingException();
    }

    /**
     * Description: page turning<br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     * samples: view page <b>my.page.num</b> of agreement search result<br/>
     * @param pageNumber
     * @throws Exception
     */
    @When("^view page (\\S+) of agreement search result$")
    public void view_page_number_in_agreement_search_result(String pageNumber) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.abstractCollinePage.enterPage(pageNumber);
        testCase.embedTestData(pageNumber);
    }

    /**
     * Description: navigate to agreement search page<br/>
     * Start page: anyPage<br/>
     * End page: agreement search page<br/>
     * samples: navigate to agreement search page<br/>
     * @throws Exception
     */
    @When("^navigate to agreement search page$")
    public void navigate_to_agreement_search_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.AGREEMENT_SEARCH);
    }

    /**
     * Description: export agreement search result<br/>
     * Start page: agreement search page<br/>
     * End page: agreement search page<br/>
     * samples: export agreement search result in <b>CSV</b> Format <b>my.result.location</b><br/>
     * @param format can be one of following value:<br /><li>CSV</li><li>Excel</li><li>XML</li><li>PDF</li>
     * @param id result location<br /><li>Variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^export agreement search result in (CSV|Excel|XML|PDF) Format (\\S+)$")
    public void export_agreement_search_result(String format, String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        StringType stringType = testDataManager.getVariable(id);
        switch (format.toLowerCase()) {
            case "csv":
                pageManager.agreementSearchPage.exportCsv(stringType);
                break;
            case "excel":
                pageManager.agreementSearchPage.exportExcel(stringType);
                break;
            case "xml":
                pageManager.agreementSearchPage.exportXml(stringType);
                break;
            case "pdf":
                pageManager.agreementSearchPage.exportPdf(stringType);
                break;
        }
        testCase.embedTestData(stringType);
    }

    /**
     * Description: click edit agreement button in agreement search page<br/>
     * Start page: agreement search page<br/>
     * End page: agreement summary page<br/>
     * samples: edit agreement <b>my.agreement</b> in agreement search page<br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearchResult}</li><li>{@link Agreement}</li><li>{@link AgreementSearch}</li>
     * @throws Exception
     */
    @When("^edit agreement (\\S+) in agreement search page$")
    public void edit_agreement_in_agreement_search_page(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementSearchResult agreementSearchResult = testDataManager.getAgreementSearchResult(id);
        if(agreementSearchResult == null){
            Agreement agreement = testDataManager.getAgreement(id);
            if(agreement != null) {
                agreementSearchResult = agreement.toAgreementSearchResult();
            }else{
                agreementSearchResult = testDataManager.getAgreementSearch(id).toAgreementSearchResult();
            }
        }
        if(agreementSearchResult.getAgreementId() == null || agreementSearchResult.getAgreementId().getRealValue().isEmpty()) {
            String agrId = pageManager.agreementSearchPage.getAgreementId(agreementSearchResult);
            if (agreementSearchResult.getAgreementId() == null) {
                agreementSearchResult.setAgreementId(new StringType(agrId));
            } else if (agreementSearchResult.getAgreementId().getRealValue().isEmpty()) {
                agreementSearchResult.getAgreementId().setValue(agrId);
            }
        }
        pageManager.agreementSearchPage.editAgreement(agreementSearchResult);
        testCase.embedTestData(agreementSearchResult);
    }

    /**
     * Description: click view completed agreement statement button in agreement search page<br/>
     * Start page: agreement search page<br/>
     * End page: agreement statement page<br/>
     * samples: view completed agreement statement <b>my.agreement</b><br/>
     * @param id can be one of following type:<br /><li>{@link AgreementSearchResult}</li><li>{@link Agreement}</li><li>{@link AgreementSearch}</li>
     * @throws Exception
     */
    @When("^view completed agreement statement (\\S+)$")
    public void view_completed_agreement_statement_from_agreement_search_page(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        AgreementSearchResult agreementSearchResult = testDataManager.getAgreementSearchResult(id);
        if(agreementSearchResult == null){
            Agreement agreement = testDataManager.getAgreement(id);
            if(agreement != null) {
                agreementSearchResult = agreement.toAgreementSearchResult();
            }else{
                agreementSearchResult = testDataManager.getAgreementSearch(id).toAgreementSearchResult();
            }
        }
        testCase.embedTestData(agreementSearchResult);
        pageManager.agreementSearchPage.viewCompletedAgreementStatement(agreementSearchResult);
    }

    /**
     * Description: click edit summary exposure info button in statement page<br/>
     * Start page: agreement statement page<br/>
     * End page: agreement exposure summary page<br/>
     * samples: edit summary exposure info<br/>
     * @throws Exception
     */
    @When("^edit summary exposure info$")
    public void edit_exposure_summary_info() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementStatementPage.editSummaryExposureInfo();
    }

    /*
    @When("^view statement quick link$")
    public void view_statement_quick_link() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.abstractCollinePage.viewStatement();
        //sydney suggest on locked statement, retry to re-caculate statement in 3 times by 5 seconds for each cycle
        pageManager.agreementStatementPage.recalculateIf("has already been locked for calculation", 3, 5000);
    }
    */

    /**
     * Description: click cancel Next Booking button to previous page<br/>
     * Start page: group booking - ASSET HOLDING DETAIL Page<br/>
     * End page: EM page<br/>
     * samples: click cancel Next Booking button to EM page<br/>
     * @throws Exception
     */
    @When("^Group booking - click cancel next booking button$")
    public void click_cancel_next_booking() throws Exception {
        pageManager.abstractCollinePage.cancelNextBooking();
    }

    /**
     * Description: click back (<<) button to previous page<br/>
     * Start page: current page<br/>
     * End page: previous page<br/>
     * samples: click back button to previous page<br/>
     * @throws Exception
     */
    @When("^click back button to previous page$")
    public void click_back_button_to_previous_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.abstractCollinePage.backToPreviousPage();
    }

    @When("^trade detail - click cancel button$")
    public void click_cancel_button() throws Exception{
        pageManager.abstractCollinePage.clickCancelButton();
    }

    @When("^click next stage button$")
    public void click_next_stage_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementCollateralSetupPage.enterNextStage();
    }

    @When("^click last stage button$")
    public void click_last_stage_button() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.agreementCollateralSetupPage.enterLastStage();
    }

    @When ("^agreementSummary - Edit Button$")
    public void edit_agreementsummary()throws Exception{
        pageManager.agreementSummaryPage.editAgreementSummary();
    }

    @When("^asset booking edit - click cancel button$")
    public void click_assetBookingEditcancel_button() throws Exception{
        pageManager.agreementAssetsEditorPage.clickCancelButton();
    }

    /**
     * Description: navigate to letter configuration page<br/>
     * Start page: anyPage<br/>
     * End page: letter configuration page<br/>
     * samples: navigate to letter configuration page<br/>
     * @throws Exception
     */
    @When("^navigate to letter configuration page$")
    public void navigate_to_letter_configuration_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
    }

    /**
     * Description: add letter template in letter configuration page<br/>
     * Start page: letter configuration page<br/>
     * End page: letter configuration page<br/>
     * samples: add letter template in letter configuration page <b>my.letter.template</b><br/>
     * @param id can be one of following type:<br /><li>{@link LetterConfiguration}</li>
     * @throws Exception
     */
    @When("^add letter template in letter configuration page (\\S+)$")
    public void add_letter_template_in_letter_configuration_page(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        LetterConfiguration letterConfiguration = testDataManager.getLetterConfiguration(id);

        if(letterConfiguration.getMarginLetter() != null && !letterConfiguration.getMarginLetter().isEmpty()) {
            List<MarginLetterConfiguration> marginLetterConfigurations = letterConfiguration.getMarginLetter();
            pageManager.letterConfigurationPage.switchToMarginLetterTab();
            for (MarginLetterConfiguration marginLetterConfiguration : marginLetterConfigurations) {
                pageManager.letterConfigurationPage.switchMarginLetterTypeTab(marginLetterConfiguration.getLetterType());
                pageManager.letterConfigurationPage.setInclusioninMarginLetterTab(marginLetterConfiguration);
                for(LetterTemplate letterTemplate : marginLetterConfiguration.getLetterTemplate()){
                    pageManager.letterConfigurationPage.addLetterTemplate();
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
                }
            }
        }

        if(letterConfiguration.getInterestLetter() != null && !letterConfiguration.getInterestLetter().isEmpty()) {
            List<InterestLetterConfiguration> interestLetterConfigurations = letterConfiguration.getInterestLetter();
            pageManager.letterConfigurationPage.switchToInterestLetterTab();
            for (InterestLetterConfiguration interestLetterConfiguration : interestLetterConfigurations) {
                pageManager.letterConfigurationPage.switchInterestLetterTypeTab(interestLetterConfiguration.getLetterType());
                for(LetterTemplate letterTemplate : interestLetterConfiguration.getLetterTemplate()){
                    pageManager.letterConfigurationPage.addLetterTemplate();
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
                }
            }
        }

        if(letterConfiguration.getUserDefinedLetter() != null){
            pageManager.letterConfigurationPage.switchToUDLTab();
            pageManager.letterConfigurationPage.setInclusionInUDLTab(letterConfiguration.getUserDefinedLetter());
            for(LetterTemplate letterTemplate : letterConfiguration.getUserDefinedLetter().getLetterTemplate()){
                pageManager.letterConfigurationPage.addLetterTemplate();
                pageManager.userDefinedLetterPage.inputLetterTemplate(letterTemplate);
                if(!pageManager.userDefinedLetterPage.saveLetterTemplate())
                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
            }
        }

        if(letterConfiguration.getExposureStatements() != null){
            pageManager.letterConfigurationPage.switchToExposureStatementsTab();
            for(LetterTemplate letterTemplate : letterConfiguration.getExposureStatements().getLetterTemplate()){
                pageManager.letterConfigurationPage.addLetterTemplate();
                pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
            }
        }

        testCase.embedTestData(letterConfiguration);
    }

    /**
     * Description: edit letter template in letter configuration page<br/>
     * Start page: letter configuration page<br/>
     * End page: letter configuration page<br/>
     * samples: edit letter template in letter configuration page <b>my.old.lettertemplate</b> to <b>my.new.lettertemplate</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link LetterConfiguration}</li>
     * @param newId can be one of following type:<br /><li>{@link LetterConfiguration}</li>
     * @throws Exception
     */
    @When("^edit letter template in letter configuration page (\\S+) to (\\S+)$")
    public void edit_letter_template_in_letter_configuration_page(String oriId, String newId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        LetterConfiguration newLetterConfiguration = testDataManager.getLetterConfiguration(newId);
        LetterConfiguration oriLetterConfiguration = testDataManager.getLetterConfiguration(oriId);

        if(newLetterConfiguration.getMarginLetter() != null && !newLetterConfiguration.getMarginLetter().isEmpty()) {
            List<MarginLetterConfiguration> newMarginLetterConfigurations = newLetterConfiguration.getMarginLetter();
            pageManager.letterConfigurationPage.switchToMarginLetterTab();
            for (MarginLetterConfiguration marginLetterConfiguration : newMarginLetterConfigurations) {
                MarginLetterConfiguration matchedMarginLetterConfiguration = (MarginLetterConfiguration) Biz.matchedLetterConfigurationWithLetterType(
                        marginLetterConfiguration, oriLetterConfiguration.getMarginLetter());
                pageManager.letterConfigurationPage.switchMarginLetterTypeTab(marginLetterConfiguration.getLetterType());
                pageManager.letterConfigurationPage.setInclusioninMarginLetterTab(marginLetterConfiguration);
                for(LetterTemplate letterTemplate : marginLetterConfiguration.getLetterTemplate()){
                    LetterTemplate matchedLetterTemplate = (LetterTemplate) Biz.matchedObjectWithName(
                            letterTemplate, matchedMarginLetterConfiguration.getLetterTemplate());
                    pageManager.letterConfigurationPage.editLetterTemplate(matchedLetterTemplate);
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    pageManager.letterTemplateEditorPage.saveLetterTemplate();
                }
            }
        }

        if(newLetterConfiguration.getInterestLetter() != null && !newLetterConfiguration.getInterestLetter().isEmpty()) {
            List<InterestLetterConfiguration> interestLetterConfigurations = newLetterConfiguration.getInterestLetter();
            pageManager.letterConfigurationPage.switchToInterestLetterTab();
            for (InterestLetterConfiguration interestLetterConfiguration : interestLetterConfigurations) {
                pageManager.letterConfigurationPage.switchInterestLetterTypeTab(interestLetterConfiguration.getLetterType());
                InterestLetterConfiguration matchedInterestLetterConfiguration = (InterestLetterConfiguration) Biz.matchedLetterConfigurationWithLetterType(
                        interestLetterConfiguration, oriLetterConfiguration.getInterestLetter());
                for(LetterTemplate letterTemplate : interestLetterConfiguration.getLetterTemplate()){
                    LetterTemplate matchedLetterTemplate = (LetterTemplate) Biz.matchedObjectWithName(
                            letterTemplate, matchedInterestLetterConfiguration.getLetterTemplate());
                    pageManager.letterConfigurationPage.editLetterTemplate(matchedLetterTemplate);
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    pageManager.letterTemplateEditorPage.saveLetterTemplate();
                }
            }
        }

        if(newLetterConfiguration.getUserDefinedLetter() != null){
            pageManager.letterConfigurationPage.switchToUDLTab();
            pageManager.letterConfigurationPage.setInclusionInUDLTab(newLetterConfiguration.getUserDefinedLetter());
            for(LetterTemplate letterTemplate : newLetterConfiguration.getUserDefinedLetter().getLetterTemplate()){
                if(letterTemplate.isRemove()){
                    pageManager.letterConfigurationPage.deleteLetterTemplateRecord(letterTemplate);
                }else{
                    LetterTemplate matchedLetterTemplate = (LetterTemplate) Biz.matchedObjectWithName(
                            letterTemplate, oriLetterConfiguration.getUserDefinedLetter().getLetterTemplate());
                    pageManager.letterConfigurationPage.editLetterTemplate(matchedLetterTemplate);
                    pageManager.userDefinedLetterPage.inputLetterTemplate(letterTemplate);
                    pageManager.userDefinedLetterPage.saveLetterTemplate();
                }
            }
        }

        if(newLetterConfiguration.getExposureStatements() != null){
            pageManager.letterConfigurationPage.switchToExposureStatementsTab();
            for(LetterTemplate letterTemplate : newLetterConfiguration.getExposureStatements().getLetterTemplate()){
                LetterTemplate matchedLetterTemplate = (LetterTemplate) Biz.matchedObjectWithName(
                        letterTemplate, oriLetterConfiguration.getExposureStatements().getLetterTemplate());
                pageManager.letterConfigurationPage.editLetterTemplate(matchedLetterTemplate);
                pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                pageManager.letterTemplateEditorPage.saveLetterTemplate();
            }
        }

        testCase.embedTestData(newLetterConfiguration);
        testCase.embedTestData(oriLetterConfiguration);
    }

    /**
     * Description: navigate to collateral preferences page<br/>
     * Start page: anyPage<br/>
     * End page: collateral preference page<br/>
     * samples: navigate to collateral preferences page<br/>
     * @throws Exception
     */
    @When("^navigate to collateral preferences page$")
    public void navigate_to_collateral_preferences_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.COLLATERAL_CONFIGURATION_PREFERENCES);
    }

    /**
     * Description: navigate to clearing dashboard page<br/>
     * Start page: anyPage<br/>
     * End page: clearing dashboard page<br/>
     * samples: navigate to clearing dashboard page<br/>
     * @throws Exception
     */
    @When("^navigate to clearing dashboard page$")
    public void navigate_to_clearing_dashboard_page() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.CLEARING_DASHBOARD);
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^clearing dashboard - add column in dashboard dynamic filter$")
    public void clearing_dashboard_add_column_in_dashboard_dynamic_filter() {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^clearing dashboard - add user filter in dashboard filter$")
    public void clearing_dashboard_add_user_filter_in_dashboard_filter() {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - navigate to (\\S+) in (?:dashboard|agreement|trade)$")
    public void clearing_dashborad_navigate_to(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add ClearingDashboard object in xsd
        //TODO need to add ClearingDashboardPage
        //TODO need a function that switch dashboard/agreement/trade category in ClearingDashboardPage
        //TODO need a function that open filters in dashboard/agreement/trade category in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param action can be one of following type:<br /><li>{@link }</li>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - (add|remove|order) column (\\S+) in (?:dashboard|agreement|trade) dynamic filter$")
    public void clearing_dashboard_modify_column(String action, String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need to add ClearingDashboard object in xsd
        //TODO need to add ClearingDashboardPage
        switch (action.toLowerCase()) {
            case "add":
                //TODO need a function that add columns in ClearingDashboardPage
                break;
            case "remove":
                //TODO need a function that remove column in ClearingDashboardPage
                break;
            case "order":
                //TODO need a function that re-order the column sequence in ClearingDashboardPage
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param action can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - (expand|collapse) in (?:dashboard|agreement|trade) dynamic filter result page$")
    public void clearing_dashboard_expand_collapse_dynamic_filter_result_page(String action) {
        // Write code here that turns the phrase above into concrete actions
        switch(action.toLowerCase()){
            case "expand" :
                //TODO need a function that click the expand result button in ClearingDashboardPage
                break;
            case "collapse" :
                //TODO need a function that click the collapse result button in ClearingDashboardPage
        }

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @param action can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - show (\\S+) details link to (agreement|trade)$")
    public void clearing_dashboard_show_details(String id, String action) {
        // Write code here that turns the phrase above into concrete actions
        switch(action.toLowerCase()){
            case "agreement" :
                //TODO need a function that view the agreement detail from a dashbaord result in ClearingDashboardPage
                break;
            case "trade" :
                //TODO need a function that view the trade detail from a dashboard result in ClearingDashboardPage
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param action can be one of following type:<br /><li>{@link }</li>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - (add|edit|delete) user filter (\\S+) in (dashboard|agreement|trade) filter$")
    public void clearing_dashboard_modify_user_filter(String action, String id) {
        // Write code here that turns the phrase above into concrete actions
        switch(action.toLowerCase()){
            case "add" :
                //TODO need a function that add filter in ClearingDashboardPage
                break;
            case "edit" :
                //TODO need a function that edit filter in ClearingDashboardPage
                break;
            case "delete" :
                //TODO need a function that remove filter in ClearingDashboardPage
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: Toggles the show/hide button on top ETD statement<br/>
     * Start page: Top ETD Statement<br/>
     * End page: Top ETD Statement<br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^show/hide zero balances$")
    public void show_hide_zero_balances() throws Exception {
        pageManager.agreementStatementPage.showHideZeroBalance();
    }
    
    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - search dashboard result (\\S+)$")
    public void clearing_dashboard_search_dashboard_result(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that search dashboard in ClearingDashboardPage
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - search agreement result (\\S+)$")
    public void clearing_dashboard_search_agreement_result(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that search agreement in ClearingDashboardPage
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - search trade result (\\S+)$")
    public void clearing_dashboard_search_trade_result(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that search trade in ClearingDashboardPage
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - view statement of ticked record (\\S+) in agreement filter$")
    public void clearing_dashboard_view_statement_of_ticked_record_in_agreement_filter(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that tick the record in the agreement category in ClearingDashboardPage
        //TODO need a function that click view statement in the agreement category in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - agreement review of ticked record (\\S+) in agreement filter$")
    public void clearing_dashboard_agreement_review_of_ticked_record_in_agreement_filter(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that tick the record in the agreement category in ClearingDashboardPage
        //TODO need a function that click agreement review in the agreement category in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - view statement of ticked record (\\S+) in trade filter$")
    public void clearing_dashboard_view_statement_of_ticked_record_in_trade_filter(String id) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that tick the record in the trade category in ClearingDashboardPage
        //TODO need a function that click view statement in the trade category in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param oriId can be one of following type:<br /><li>{@link }</li>
     * @param newId can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^clearing dashboard - edit trade of ticked record (\\S+) in trade filter to (\\S+)$")
    public void clearing_dashboard_edit_trade_of_ticked_record(String oriId, String newId) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that tick the record in the trade category in ClearingDashboardPage
        //TODO need a function that modify the record in the trade category in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param format can be dashboard id
     * @throws Exception
     */
    @When("^clearing dashboard - export result in (CSV|Excel) format of (?:dashboard|agreement|trade) filter$")
    public void clearing_dashboard_export_result(String format) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that export the result in dashbaord/agreement/trade (CSV/EXCEL) data in ClearingDashboardPage

        throw new PendingException();
    }

    /**
     * Description: navigate to workflow start-up dashboard page<br/>
     * Start page: anyPage<br/>
     * End page: start-up dashboard page<br/>
     * samples: navigate to workflow start-up dashboard page<br/>
     * @throws Exception
     */
    @When("^navigate to workflow start-up dashboard page$")
    public void navigate_to_workflow_start_up_dashboard_page() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.STARTUP_DASHBOARD);
    }

    /**
     * Description: edit workflow start-up dashboard settings<br/>
     * Start page: start-up dashboard page<br/>
     * End page: start-up dashboard page<br/>
     * samples: edit workflow start-up dashboard settings <b>my.startupDashboard</b><br/>
     * @param id can be one of following type:<br /><li>{@link StartupDashboard}</li>
     * @throws Exception
     */
    @When("^edit workflow start-up dashboard settings (\\S+)$")
    public void edit_workflow_start_up_dashboard_settings(String id) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        StartupDashboard startupDashboard = testDataManager.getStartupDashboard(id);
        pageManager.startupDashboardPage.setStartupDashboard(startupDashboard);
        pageManager.startupDashboardPage.saveStartupDashboard();
        testCase.embedTestData(startupDashboard);
    }

    /**
     * Description: navigate to workflow dashboard page<br/>
     * Start page: anyPage<br/>
     * End page: dashboard page<br/>
     * samples: navigate to workflow dashboard page<br/>
     * @throws Exception
     */
    @When("^navigate to workflow dashboard page$")
    public void navigate_to_workflow_dashboard_page() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.DASHBOARD);
    }

    /**
     * Description: edit dashboard<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - edit dashboard <b>my.dashboard</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^workflow dashboard - edit dashboard (\\S+)$")
    public void workflow_dashboard_edit_dashboard(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.editDashboard(dashboard);
        pageManager.dashboardPage.save(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click title link in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show <b>external exposures information</b><br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show (?:external exposures information|calls and recalls|deliveries and returns|no calls|substitutions) (\\S+)$")
    public void workflow_dashboard_show_type(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click asset link under Collateral Settlement Summary in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - click <b>asset under Collateral Settlement Summary</b><br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - click asset (\\S+) under Collateral Settlement Summary$")
    public void workflow_dashboard_click_asset(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        testCase.embedTestData(dashboard);
        pageManager.dashboardPage.enterCollateralWorkflowSettlementStatus(dashboard);
    }


    /**
     * Description: click link under calls and recalls in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show calls and recalls (awaiting action|calls in next hour|processing call|calls past notification time|pending review|margin request issued|margin request confirmed|partially disputed|call|fully disputed call|sub request made|sub request received) details$")
    public void workflow_dashboard_show_calls_and_recalls_details(String id) throws Exception {
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse for details of calls and recalls <br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of calls and recalls<br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of calls and recalls$")
    public void workflow_dashboard_expand_collapse_for_details_of_call_and_recalls(String action) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that expand/collapse details in calls and recalss section in DashboardPage
        switch(action.toLowerCase()){
            case "expand" :
                break;
            case "collapse" :
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: click link under deliveries and returns in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show deliveries and returns (awaiting action|calls in next hour|processing call|calls past notification time|pending review|margin request issued|margin request confirmed|partially disputed|call|fully disputed call|sub request made|sub request received) details$")
    public void workflow_dashboard_show_deliveries_and_returns_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse for details of deliveries and returns<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of deliveries and returns<br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of deliveries and returns$")
    public void workflow_dashboard_expand_collapse_for_details_of_deliveries_and_returns(String action) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that expand/collapse details in deliveries and returns section in DashboardPage
        switch(action.toLowerCase()){
            case "expand" :
                break;
            case "collapse" :
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: click link under deliveries and returns in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: workflow dashboard - show no calls <b>no call required</b> details<br/>
     * samples: <br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show no calls (no call required|collateral booked|waived|completed|sub request made|sub request received) details$")
    public void workflow_dashboard_show_no_calls_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse for details of no calls<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of no calls<br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of no calls$")
    public void workflow_dashboard_expand_collapse_for_details_of_no_calls(String action) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that expand/collapse details in no calls section in DashboardPage
        switch(action.toLowerCase()){
            case "expand" :
                break;
            case "collapse" :
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: click link under substitutions in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show substitutions (substitution requests|substitution confirmations|pending review|request issued|request confirmed|partially booked|waived|completed) details$")
    public void workflow_dashboard_show_substitutions_detail(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse for details of substitutions<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of substitutions<br/>
     * @param action can be one of following value:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of substitutions$")
    public void workflow_dashboard_expand_collapse_for_details_of_substitutions(String action) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that expand/collapse details in substitutions section in DashboardPage
        switch(action.toLowerCase()){
            case "expand" :
                break;
            case "collapse" :
                break;
        }

        throw new PendingException();
    }

    /**
     * Description: click link internal reviews to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show internal reviews details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show internal reviews details$")
    public void workflow_dashboard_show_internal_reviews_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link under internal reviews in dashboard page<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show internal reviews <b>calls and recalls</b> details<br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show internal reviews (calls and recalls|deliveries and returns|no calls) details$")
    public void workflow_dashboard_show_internal_reviews_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link collateral settlement summary to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show collateral settlement summary details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show collateral settlement summary details$")
    public void workflow_dashboard_show_collateral_settlement_summary_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link outstanding entries up to today to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show outstanding entries up to today details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show outstanding entries up to today details$")
    public void workflow_dashboard_show_outstanding_entries_up_to_today_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse for details of outstanding entries up to today<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of outstanding entries up to today<br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of outstanding entries up to today$")
    public void workflow_dashboard_expand_collapse_for_details_of_outstanding_entries_up_to_today(String action) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        switch(action.toLowerCase()){
            case "expand" :
                pageManager.dashboardPage.expandOutstandingEntriesUpToTodayForDetail();
                break;
            case "collapse" :
                pageManager.dashboardPage.collapseOutstandingEntriesUpToTodayForDetail();
                break;
        }
    }

    /**
     * Description: see different asset type status collateral settlement summary<br/>
     * Start page: dashboard page<br/>
     * End page: settlement status summary page<br/>
     * samples: <br/>
     * @param id can be dashboard id
     * @throws Exception
     */
    @When("^workflow dashboard - show asset (\\S+) of status (sys draft|pending|query|authorised|pend rel|pend sett|os sett|corp action due) details$")
    public void workflow_dashboard_show_asset_status_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: see summary different status collateral settlement summary<br/>
     * Start page: dashboard page<br/>
     * End page: settlement status summary page<br/>
     * samples: workflow dashboard - show outstanding entries up to today <b>all assets</b> details<br/>
     * @param id can be one of following type:<br /><li>sys draft</li><li>pending</li><li>query</li><li>authorised</li><li>pend rel</li><li>pend sett</li><li>corp action due</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show outstanding entries up to today (all assets|sys draft|pending|query|authorised|pend rel|pend sett|os sett|corp action due) details$")
    public void workflow_dashboard_show_outstanding_entries_up_to_today_detail(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link interest summary to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: IM search result page<br/>
     * samples: workflow dashboard - show interest summary details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show interest summary details$")
    public void workflow_dashboard_show_interest_summary_details() {
        // Write code here that turns the phrase above into concrete actions
        //TODO click interest summary link in DashboardPage
        throw new PendingException();
    }

    /**
     * Description: click link under interest summary to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show interest summary <b>interest statements for lsat month</b> details<br/>
     * @param id can be one of following type:<br /><li>interest statements for lsat month</li><li>outstanding movements up to today</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show interest summary (interest statements for lsat month|outstanding movements up to today) details$")
    public void workflow_dashboard_show_interest_summary_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link interest statements for last month to view detail<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show interest statements for last month <b>interest statement outstanding</b> details<br/>
     * @param id can be one of following type:<br /><li>interest statement outstanding</li><li>interest statement initiated</li><li>interest statement applied</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show interest statements for last month (interest statement outstanding|interest statement initiated|interest statement applied) details$")
    public void workflow_dashboard_show_interest_statement_for_last_month (String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link under outstanding movements up to today<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show outstanding movements up to today <b>physical receive</b> of status <b>pending</b> details<br/>
     * @param id can be one of following value:<br /><li>physical receive</li><li>capitalise receive</li><li>physical pay</li><li>capitalise pay</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show outstanding movements up to today (physical receive|capitalise receive|physical pay|capitalise pay) of status (pending|query|authorised|pending release|pending settlement) details$")
    public void workflow_dashboard_show_outstanding_movements_up_to_today_type_status_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click summary title link under outstanding movements up to today<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show outstanding movements up to today <b>all interest movements</b> details<br/>
     * @param id can be one of following value:<br /><li>all interest movements</li><li>pending</li><li>query</li><li>authorised</li><li>pending release</li><li>pending settlement</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show outstanding movements up to today (all interest movements|pending|query|authorised|pending release|pending settlement) details$")
    public void workflow_dashboard_show_outstanding_movements_up_to_today_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link approvals management<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show approvals management details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show approvals management details$")
    public void workflow_dashboard_show_approvals_management_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click expand|collapse under outstanding approvals<br/>
     * Start page: dashboard page<br/>
     * End page: dashboard page<br/>
     * samples: workflow dashboard - <b>expand</b> for details of outstanding approvals<br/>
     * @param action can be one of following type:<br /><li>expand</li><li>collapse</li>
     * @throws Exception
     */
    @When("^workflow dashboard - (expand|collapse) for details of outstanding approvals$")
    public void workflow_dashboard_expand_collapse_for_details_of_outstanding_approvals(String action) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that click expand and collapse button in outstanding approvals section in DashboardPage
        switch(action.toLowerCase()){
            case "expand" :
                break;
            case "collapse" :
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: click link under approvals management<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show approvals management <b>outstanding approvals</b> details<br/>
     * @param id can be one of following type:<br /><li>outstanding approvals</li><li>amended securities data</li><li>amended workflow</li><li>amended organisations</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show approvals management (outstanding approvals|amended securities data|amended workflow|amended organisations) details$")
    public void workflow_dashboard_show_approvals_management_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link under outstanding approvals<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show outstanding approvals <b>new agreements</b> details<br/>
     * @param id can be one of following type:<br /><li>new agreements</li><li>amended agreements</li><li>amended umbrella agreements</li><li>amended eligibility rules template</li><li>deleted eligibility rules template</li><li>new statements</li><li>amended statements</li><li>pending approval statements</li><li>in progress statements</li><li>stale approval statements</li><li>amended settlement instructions</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show outstanding approvals (new agreements|amended agreements|amended umbrella agreements|amended eligibility rules template|deleted eligibility rules template|new statements|amended statements|pending approval statements|in progress statements|stale approval statements|amended settlement instructions) details$")
    public void workflow_dashboard_show_outstanding_approvals_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link today's events<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show today's events details<br/>
     * @throws Exception
     */
    @When("^workflow dashboard - show today's events details$")
    public void workflow_dashboard_show_todays_events_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: click link under today's events<br/>
     * Start page: dashboard page<br/>
     * End page: <br/>
     * samples: workflow dashboard - show today's events <b>actioned events for today</b> details<br/>
     * @param type can be one of following type:<br /><li>actioned events for today</li><li>not actioned evenets for today</li>
     * @throws Exception
     */
    @When("^workflow dashboard - show today's events (actioned events for today|not actioned evenets for today) details$")
    public void workflow_dashboard_show_todays_events_type_details(String type) {
        // Write code here that turns the phrase above into concrete actions
        //TODO need a function that click actioned events for today|not actioned events for today in todays events section in DashboardPage
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^workflow dashboard - agreement actions (concenration limits breached|failed statements|ineligible assets) details$")
    public void workflow_dashboard_agreement_actions_type_details(String id) throws Exception{
        Dashboard dashboard = testDataManager.getDashboard(id);
        pageManager.dashboardPage.show(dashboard);
        testCase.embedTestData(dashboard);
    }

    /**
     * Description: navigate to security movement search page<br/>
     * Start page: anyPage<br/>
     * End page: Security Movement Search<br/>
     * samples: navigate to security movement search page<br/>
     * @throws Exception
     */
    @When("^navigate to security movement search page$")
    public void navigate_to_security_movement_search_page() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.SECURITY_MOVEMENTS);
    }

    /**
     * Description: search security movement<br/>
     * Start page: security movement search page<br/>
     * End page: security movement search result page<br/>
     * samples: security movement - search security movement <b>my.securityMovementSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link SecurityMovementSearch}</li>
     * @throws Exception
     */
    @When("^security movement - search security movement (\\S+)$")
    public void security_movement_search_security_movement(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        SecurityMovementSearch securityMovementSearch = testDataManager.getSecurityMovementSearch(id);
        pageManager.securityMovementSearchPage.setSecurityMovementSearch(securityMovementSearch);
        pageManager.securityMovementSearchPage.searchSecurityMovement();
        testCase.embedTestData(securityMovementSearch);
    }

    /**
     * Description: select asset type to view details<br/>
     * Start page: security movement search result page<br/>
     * End page: security movement detail page<br/>
     * samples: security movement - show asset <b>my.assetType</b> details<br/>
     * @param id can be one of following type:<br /><li>{@link SecurityMovementSummary}</li>
     * @throws Exception
     */
    @When("^security movement - show asset (\\S+) details$")
    public void security_movement_show_asset(String id) throws Exception{
        // Write code here that turns the phrase above into concrete actions
        SecurityMovementSummary securityMovementSummary = testDataManager.getSecurityMovementSummary(id);
        pageManager.securityMovementSummaryPage.enterSecurityMovementPage(securityMovementSummary);
        testCase.embedTestData(securityMovementSummary);
    }

    /**
     * Description: navigate to internal review page<br/>
     * Start page: anyPage<br/>
     * End page: internal review search page<br/>
     * samples: navigate to internal review page<br/>
     * @throws Exception
     */
    @When("^navigate to internal review page$")
    public void navigate_to_internal_review_page() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.INTERNAL_REVIEWS);
    }

    /**
     * Description: search internal review<br/>
     * Start page: internal review search page<br/>
     * End page: internal review search result page<br/>
     * samples: internal review - search internal review <b>my.internalReviewSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link InternalReviewSearch}</li>
     * @throws Exception
     */
    @When("^internal review - search internal review (\\S+)$")
    public void internal_review_search_internal_review(String id) throws Exception{
        InternalReviewSearch internalReviewSearch = testDataManager.getInternalReviewSearch(id);
        pageManager.internalReviewSearchPage.setInternalReviewSearch(internalReviewSearch);
        pageManager.internalReviewSearchPage.searchInternal();
        testCase.embedTestData(internalReviewSearch);
    }

    /**
     * Description: select agreement in links dropdown list under different tabs<br/>
     * Start page: internal review search result page<br/>
     * End page: agreement summary page<br/>
     * samples: internal review - link to <b>my.internalReviewSummary</b> agreement in <b>calls and recalls</b><br/>
     * @param id can be one of following type:<br /><li>{@link InternalReviewSummary}</li>
     * @throws Exception
     */
    @When("^internal review - link to (\\S+) agreement in (?:calls and recalls|deliveries and returns|no calls)$")
    public void internal_review_link_to_agreement_in_type(String id) throws Exception{
        InternalReviewSummary internalReviewSummary = testDataManager.getInternalReviewSummary(id);
        pageManager.internalReviewSummaryPage.setLinks(internalReviewSummary);
        testCase.embedTestData(internalReviewSummary);
    }

    /**
     * Description: select statement in links dropdown list under different tabs<br/>
     * Start page: internal review search result page<br/>
     * End page: agreement statement page<br/>
     * samples: internal review - link to <b>my.internalReviewSummary</b> statement in <b>calls and recalls</b><br/>
     * @param id can be one of following type:<br /><li>{@link InternalReviewSummary}</li>
     * @throws Exception
     */
    @When("^internal review - link to (\\S+) statement in (?:calls and recalls|deliveries and returns|no calls)$")
    public void internal_review_link_to_statement_in_type(String id) throws Exception{
        InternalReviewSummary internalReviewSummary = testDataManager.getInternalReviewSummary(id);
        pageManager.internalReviewSummaryPage.setLinks(internalReviewSummary);
        testCase.embedTestData(internalReviewSummary);
    }

    /**
     * Description: select settlement in links dropdown list under different tabs<br/>
     * Start page: internal review search result page<br/>
     * End page: settlement status summary page<br/>
     * samples: internal review - link to <b>my.internalReviewSummary</b> settlement in <b>calls and recalls</b><br/>
     * @param id can be one of following type:<br /><li>{@link InternalReviewSummary}</li>
     * @throws Exception
     */
    @When("^internal review - link to (\\S+) settlement in (?:calls and recalls|deliveries and returns|no calls)$")
    public void internal_review_link_to_settlement_in_type(String id) throws Exception{
        InternalReviewSummary internalReviewSummary = testDataManager.getInternalReviewSummary(id);
        pageManager.internalReviewSummaryPage.setLinks(internalReviewSummary);
        testCase.embedTestData(internalReviewSummary);
    }

    /**
     * Description: click long view|view summary button in internal review search result page<br/>
     * Start page: internal review search result page<br/>
     * End page: internal review search result page<br/>
     * samples: internal review - <b>long view</b><br/>
     * @param view can be one of following value:<br /><li>long view</li><li>view summary</li>
     * @throws Exception
     */
    @When("^internal review - (long view|view summary)$")
    public void internal_review_view(String view) throws Exception{
        switch(view.toLowerCase()){
            case "long view" :
                pageManager.internalReviewSummaryPage.longView();
                break;
            case "view summary" :
                //TODO need a function that click view summary button in InternalReviewSummaryPage
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: sort search result by header<br/>
     * Start page: internal review search result page<br/>
     * End page: internal review search result page<br/>
     * samples: internal review - sort search result by <b>pricipal</b><br/>
     * @param columnName can be one of following type:<br /><li>pricipal</li><li>counterparty</li><li>description</li><li>business line</li><li>action</li>
     * @throws Exception
     */
    @When("^internal review - sort search result by (pricipal|counterparty|description|business line|action)$")
    public void internal_review_sort_search_result_by_column(String columnName) {
        //TODO need a sorting function that sort the table result in InternalReviewSummaryPage
        switch(columnName.toLowerCase()){
            default:
                break;
        }
        throw new PendingException();
    }

    /**
     * Description: click agreement review button<br/>
     * Start page: <br/>
     * End page: agreement summary page<br/>
     * samples: quick link - agreement review<br/>
     * @throws Exception
     */
    @When("^quick link - agreement review$")
    public void quick_link_agreement_review() throws Exception{
        pageManager.abstractCollinePage.enterAgreementReview();
    }

    /**
     * Description: click view statement button<br/>
     * Start page: <br/>
     * End page: agreement statement page<br/>
     * samples: quick link - view statement<br/>
     * @throws Exception
     */
    @When("^quick link - view statement$")
    public void quick_link_view_statement() throws Exception{
        pageManager.abstractCollinePage.viewStatement();
        //sydney suggest on locked statement, retry to re-caculate statement in 3 times by 5 seconds for each cycle
        //pageManager.agreementStatementPage.recalculateIf("has already been locked for calculation", 3, 5000);
    }

    /**
     * Description: click search button<br/>
     * Start page: <br/>
     * End page: agreement search page<br/>
     * samples: quick link - search<br/>
     * @throws Exception
     */
    @When("^quick link - search$")
    public void quick_link_search() {
        throw new PendingException();
    }

    /**
     * Description: click view exposure data button<br/>
     * Start page: <br/>
     * End page: agreement exposures suammary page<br/>
     * samples: quick link - view exposure data<br/>
     * @throws Exception
     */
    @When("^quick link - view exposure data$")
    public void quick_link_view_exposure_data() {
        throw new PendingException();
    }

    /**
     * Description: click view asset data button<br/>
     * Start page: <br/>
     * End page: agreement asset holding summary page<br/>
     * samples: quick link - view asset data<br/>
     * @throws Exception
     */
    @When("^quick link - view asset data$")
    public void quick_link_view_asset_data() {
        throw new PendingException();
    }

    /**
     * Description: click show history button<br/>
     * Start page: <br/>
     * End page: Statement Archive Search page<br/>
     * samples: quick link - show history<br/>
     * @throws Exception
     */
    @When("^quick link - show history$")
    public void quick_link_show_history() {
        throw new PendingException();
    }

    /**
     * Description: click external exposures button<br/>
     * Start page: <br/>
     * End page: External Exposure Search page<br/>
     * samples: quick link - external exposures<br/>
     * @throws Exception
     */
    @When("^quick link - external exposures$")
    public void quick_link_external_exposures() {
        throw new PendingException();
    }

    /**
     * Description: click agreement external exposures button<br/>
     * Start page: <br/>
     * End page: External Exposure Search result page<br/>
     * samples: quick link - agreement external exposures<br/>
     * @throws Exception
     */
    @When("^quick link - agreement external exposures$")
    public void quick_link_agreement_external_exposures() {
        throw new PendingException();
    }

    /**
     * Description: click exposure management button<br/>
     * Start page: <br/>
     * End page: exposure management page<br/>
     * samples: quick link - exposure management<br/>
     * @throws Exception
     */
    @When("^quick link - exposure management$")
    public void quick_link_exposure_management() throws Exception{
        pageManager.abstractCollinePage.enterExposureManagement();
    }

    /**
     * Description: click agreement exposure management button<br/>
     * Start page: <br/>
     * End page: exposure management page<br/>
     * samples: quick link - agreement exposure management<br/>
     * @throws Exception
     */
    @When("^quick link - agreement exposure management$")
    public void quick_link_agreement_exposure_management() throws Exception{
        pageManager.abstractCollinePage.enterAgreementExposureManagement();
    }

    /**
     * Description: click validate button<br/>
     * Start page: exposure management <br/>
     * End page: exposure management page<br/>
     * samples: quick link - validate<br/>
     * @throws Exception
     */
    @When("^quick link - validate$")
    public void quick_link_validate() throws Exception {
        pageManager.exposureManagementPage.validate();
    }

    /**
     * Description: click change bookings button<br/>
     * Start page: exposure management <br/>
     * End page: exposure management page<br/>
     * samples: quick link - change bookings<br/>
     * @throws Exception
     */
    @When("^quick link - change bookings$")
    public void quick_link_change_bookings() throws Exception {
        pageManager.exposureManagementPage.changeBookings();
    }

    /**
     * Description: click save button <br/>
     * Start page: exposure management <br/>
     * End page: exposure management page<br/>
     * samples: quick link - save bookings<br/>
     * @throws Exception
     */
    @When("^quick link - save$")
    public void quick_link_save() throws Exception {
        pageManager.exposureManagementPage.saveBookings();
    }

    /**
     * Description: click cancel button <br/>
     * Start page: exposure management <br/>
     * End page: exposure management page<br/>
     * samples: quick link - cancel bulk bookings<br/>
     * @throws Exception
     */
    @When("^quick link - cancel bulk bookings$")
    public void quick_link_cancel_bulk_booking() throws Exception {
        pageManager.exposureManagementPage.cancelbooking();
    }



    /**
     * Description: click show exposure statement button<br/>
     * Start page: <br/>
     * End page: exposure statement page<br/>
     * samples: quick link - show exposure statement<br/>
     * @throws Exception
     */
    @When("^quick link - show exposure statement$")
    public void quick_link_show_exposure_statement() throws Exception{
        pageManager.abstractCollinePage.showExposureStatement();
    }

    /**
     * Description: click SSI search button<br/>
     * Start page: <br/>
     * End page: SSI search page<br/>
     * samples: quick link - SSI search<br/>
     * @throws Exception
     */
    @When("^quick link - SSI search$")
    public void quick_link_ssi_search() {
        throw new PendingException();
    }

    /**
     * Description: click STP status button<br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: quick link - STP status<br/>
     * @throws Exception
     */
    @When("^quick link - STP status$")
    public void quick_link_stp_status() {
        throw new PendingException();
    }

    /**
     * Description: click Interest Manager button<br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: quick link - Interest Manager<br/>
     * @throws Exception
     */
    @When("^quick link - Interest Manager$")
    public void quick_link_interest_manager() throws Exception{
        pageManager.abstractCollinePage.enterInterestManagement();
    }

    /**
     * Description: select agreement in umbrella list<br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: select agreement <b>my.agreement</b> in umbrella list<br/>
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^select agreement (\\S+) in umbrella list$")
    public void select_agreement_in_umbrella_list(String id) throws Exception{
        Agreement agreement = testDataManager.getAgreement(id);
        pageManager.agreementStatementPage.switchAgreement(agreement);
        testCase.embedTestData(agreement);
    }

    /*
    duplicated function as @When("^recalculate agreement statement$") already implemented
    @When("^recalculate statement$")
    public void recalculate_statement() throws Exception{
        pageManager.agreementStatementPage.recalculate();
    }
    */

    /**
     * Description: tick interest events<br/>
     * Start page: IM search result page<br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - tick interest events <b>my.interestEventDetail</b><br/>
     * @param eventIds can be one of following type:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - tick interest events (\\S+)$")
    public void interest_manager_tick_interest_events(List<String> eventIds) throws Exception{
        for(String id : eventIds){
            InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
            pageManager.interestManagerSearchResultPage.tickInterestManagerSearchResult(interestEventDetail);
            testCase.embedTestData(interestEventDetail);
        }
    }

    /**
     * Description: switch between different tab<br/>
     * Start page: IM search result page<br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - switch to <b>Pay</b> tab<br/>
     * @param tabName can be one of following value:<br /><li>Pay</li><li>Receive</li><li>Capitalise pay</li><li>Capitalise receive</li><li>Gross Interest</li>
     * @throws Exception
     */
    @When("^Interest Manager - switch to (Pay|Receive|Capitalise pay|Capitalise receive|Gross Interest) tab$")
    public void interest_manager_switch_tab(String tabName) throws Exception{
        switch(tabName.toLowerCase()){
            case "pay" :
                pageManager.interestManagerSearchResultPage.switchToPayTab();
                break;
            case "receive" :
                pageManager.interestManagerSearchResultPage.switchToReceiveTab();
                break;
            case "capitalise pay" :
                pageManager.interestManagerSearchResultPage.switchToCapitalisePayTab();
                break;
            case "capitalise receive" :
                pageManager.interestManagerSearchResultPage.switchToCapitaliseReceiveTab();
                break;
            case "gross interest" :
                pageManager.interestManagerSearchResultPage.switchToGrossInterestTab();
                break;
        }
    }

    /**
     * Description: expand top events<br/>
     * Start page: IM search result page<br/>
     * End page: IM search result page<br/>
     * samples: Interest Manager - expand top events <b>top.events</b>
     * @param ids can be one of following value:<br /><li>{@link InterestEventDetail}</li>
     * @throws Exception
     */
    @When("^Interest Manager - expand top events? (\\S+)$")
    public void interest_manager_expand_top_event(List<String> ids) throws Exception{
        for (String id : ids){
            InterestEventDetail interestEventDetail = testDataManager.getInterestEventDetail(id);
            testCase.embedTestData(interestEventDetail);
            pageManager.interestManagerSearchResultPage.expandEvent(interestEventDetail);
        }
    }

    /**
     * Description: set AHV report report filter or save template<br/>
     * Start page: AHV report page<br/>
     * End page: AHV report page<br/>
     * samples: search Asset Holdings and Valuation Report <b>my.assetHoldingsAndValuationReportFilter</b><br/>
     * @param id can be one of following type:<br /><li>{@link AssetHoldingsAndValuationReportFilter}</li>
     * @throws Exception
     */
    @When("^search Asset Holdings and Valuation Report (\\S+)$")
    public void search_asset_holdings_and_valuation_report(String id)throws Exception{
        AssetHoldingsAndValuationReportFilter assetHoldingsAndValuationReportFilter = testDataManager.getAssetHoldingsAndValuationReportFilter(id);
        pageManager.assetHoldingsAndValuationReportPage.setAssetHoldingsAndValuationReport(assetHoldingsAndValuationReportFilter);
        if(assetHoldingsAndValuationReportFilter.getOutputFormat() != null && !assetHoldingsAndValuationReportFilter.getOutputFormat().isEmpty()){
            pageManager.assetHoldingsAndValuationReportPage.editOutputFormat();
            pageManager.assetHoldingsAndValuationReportPage.setOutputParameters(assetHoldingsAndValuationReportFilter.getOutputFormat());
            pageManager.assetHoldingsAndValuationReportPage.applyChanges();
        }
        if(assetHoldingsAndValuationReportFilter.getSaveAsTemplate() != null){
            pageManager.assetHoldingsAndValuationReportPage.savaAsTemplate(assetHoldingsAndValuationReportFilter);
        }
        testCase.embedTestData(assetHoldingsAndValuationReportFilter);
    }

    /**
     * Description: navigate to Asset Holdings and Valuation Report template<br/>
     * Start page: anyPage<br/>
     * End page: AHV report page<br/>
     * samples: navigate to Asset Holdings and Valuation Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Asset Holdings and Valuation Report template (\\S+)$")
    public void navigate_to_asset_holdings_and_valuation_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AssetHoldingsAndValuationReportFilter reportFilter = testDataManager.getAssetHoldingsAndValuationReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ASSET_HOLDINGS_AND_VALUATION, templateName);
    }

    /**
     * Description: set Asset Management report report filter or save template<br/>
     * Start page: Asset Management report<br/>
     * End page: Asset Management report<br/>
     * samples: search Asset Management Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Asset Management Report (\\S+)$")
    public void search_asset_management_report(String id)throws Exception{
        AssetManagementReportFilter assetManagementReportFilter = testDataManager.getAssetManagementReportFilter(id);
        pageManager.assetManagementReportPage.setAssetManagementReport(assetManagementReportFilter);
        if (assetManagementReportFilter.getOutputFormat() != null && !assetManagementReportFilter.getOutputFormat().isEmpty()){
            pageManager.assetManagementReportPage.editOutputFormat();
            pageManager.assetManagementReportPage.setOutputParameters(assetManagementReportFilter.getOutputFormat());
            pageManager.assetManagementReportPage.applyChanges();
        }
        if (assetManagementReportFilter.getSaveAsTemplate() != null){
            pageManager.assetManagementReportPage.saveAsTemplate(assetManagementReportFilter);
        }
        testCase.embedTestData(assetManagementReportFilter);
    }

    /**
     * Description: navigate to Asset Management Report template<br/>
     * Start page: anyPage<br/>
     * End page: Asset Management Report<br/>
     * samples: navigate to Asset Management Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Asset Management Report template (\\S+)$")
    public void navigate_to_asset_management_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AssetManagementReportFilter reportFilter = testDataManager.getAssetManagementReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ASSET_MANAGEMENT, templateName);
    }

    /**
     * Description: set Asset Settlements report filter or save template<br/>
     * Start page: Asset Settlements Report<br/>
     * End page: Asset Settlements Report<br/>
     * samples: search Asset Settlements Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Asset Settlements Report (\\S+)$")
    public void search_asset_settlements_report(String id)throws Exception{
        AssetSettlementReportFilter assetSettlementReportFilter = testDataManager.getAssetSettlementReportFilter(id);
        pageManager.assetSettlementsReportPage.setAssetSettlementsReport(assetSettlementReportFilter);
        if (assetSettlementReportFilter.getOutputFormat() != null && !assetSettlementReportFilter.getOutputFormat().isEmpty()){
            pageManager.assetSettlementsReportPage.editOutputFormat();
            pageManager.assetSettlementsReportPage.setOutputParameters(assetSettlementReportFilter.getOutputFormat());
            pageManager.assetSettlementsReportPage.applyChanges();
        }
        if (assetSettlementReportFilter.getSaveAsTemplate() != null){
            pageManager.assetSettlementsReportPage.saveAsTemplate(assetSettlementReportFilter);
        }
        testCase.embedTestData(assetSettlementReportFilter);
    }

    /**
     * Description: navigate to Asset Settlements Report template<br/>
     * Start page: anyPage<br/>
     * End page: Asset Settlements Report<br/>
     * samples: navigate to Asset Settlements Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see  StringType
     * @throws Exception
     */
    @When("^navigate to Asset Settlements Report template (\\S+)$")
    public void navigate_to_asset_settlements_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AssetSettlementReportFilter reportFilter = testDataManager.getAssetSettlementReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ASSET_SETTLEMENTS, templateName);
    }

    /**
     * Description: set Collateral Availability report filter or save template<br/>
     * Start page: Collateral Availability Report<br/>
     * End page: Collateral Availability Report<br/>
     * samples: search Asset Collateral Availability <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Collateral Availability Report (\\S+)$")
    public void search_collateral_availability_report(String id)throws Exception{
        CollateralAvailabilityReportFilter collateralAvailabilityReportFilter = testDataManager.getCollateralAvailabilityReportFilter(id);
        pageManager.collateralAvailabilityReportPage.setCollateralAvailabilityReport(collateralAvailabilityReportFilter);
        if (collateralAvailabilityReportFilter.getOutputFormat() != null && !collateralAvailabilityReportFilter.getOutputFormat().isEmpty()){
            pageManager.collateralAvailabilityReportPage.editOutputFormat();
            pageManager.collateralAvailabilityReportPage.setOutputParameters(collateralAvailabilityReportFilter.getOutputFormat());
            pageManager.collateralAvailabilityReportPage.applyChanges();
        }
        if (collateralAvailabilityReportFilter.getSaveAsTemplate() != null){
            pageManager.collateralAvailabilityReportPage.saveAsTemplate(collateralAvailabilityReportFilter);
        }
        testCase.embedTestData(collateralAvailabilityReportFilter);
    }

    /**
     * Description: navigate to Collateral Availability Report template<br/>
     * Start page: anyPage<br/>
     * End page: Collateral Availability Report<br/>
     * samples: navigate to Collateral Availability Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see  StringType
     * @throws Exception
     */
    @When("^navigate to Collateral Availability Report template (\\S+)$")
    public void navigate_to_collateral_availability_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            CollateralAvailabilityReportFilter reportFilter = testDataManager.getCollateralAvailabilityReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.COLLATERAL_AVAILABILITY, templateName);
    }








    /**
     * Description: set Concentration Limits Report filter or save template<br/>
     * Start page: Concentration Limits Report<br/>
     * End page: Concentration Limits Report<br/>
     * samples: search Concentration Limits Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Concentration Limits Report (\\S+)$")
    public void search_concentration_limits_report(String id)throws Exception{
        ConcentrationLimitsReportFilter concentrationLimitsReportFilter = testDataManager.getConcentrationLimitsReportFilter(id);
        pageManager.concentrationLimitsReportPage.setConcentrationLimitsReport(concentrationLimitsReportFilter);
        if (concentrationLimitsReportFilter.getOutputFormat() != null && !concentrationLimitsReportFilter.getOutputFormat().isEmpty()){
            pageManager.concentrationLimitsReportPage.editOutputFormat();
            pageManager.concentrationLimitsReportPage.setOutputParameters(concentrationLimitsReportFilter.getOutputFormat());
            pageManager.concentrationLimitsReportPage.applyChanges();
        }
        if (concentrationLimitsReportFilter.getSaveAsTemplate() != null){
            pageManager.concentrationLimitsReportPage.saveAsTemplate(concentrationLimitsReportFilter);
        }
        testCase.embedTestData(concentrationLimitsReportFilter);
    }

    /**
     * Description: navigate to Concentration Limits Report template<br/>
     * Start page: anyPage<br/>
     * End page: Concentration Limits Report<br/>
     * samples: navigate to Concentration Limits Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Concentration Limits Report template (\\S+)$")
    public void navigate_to_concentration_limits_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            ConcentrationLimitsReportFilter reportFilter = testDataManager.getConcentrationLimitsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.CONCENTRATION_LIMITS, templateName);
    }

    /**
     * Description: set Corporate Actions Report filter or save template<br/>
     * Start page: Corporate Actions Report<br/>
     * End page: Corporate Actions Report<br/>
     * samples: search Corporate Actions Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Corporate Actions Report (\\S+)$")
    public void search_corporate_actions_report(String id)throws Exception{
        CorporateActionsReportFilter corporateActionsReportFilter = testDataManager.getCorporateActionsReportFilter(id);
        pageManager.corporateActionsReportPage.setCorporateActionsReport(corporateActionsReportFilter);
        if (corporateActionsReportFilter.getOutputFormat() != null && !corporateActionsReportFilter.getOutputFormat().isEmpty()){
            pageManager.corporateActionsReportPage.editOutputFormat();
            pageManager.corporateActionsReportPage.setOutputParameters(corporateActionsReportFilter.getOutputFormat());
            pageManager.corporateActionsReportPage.applyChanges();
        }
        if (corporateActionsReportFilter.getSaveAsTemplate() != null){
            pageManager.corporateActionsReportPage.saveAsTemplate(corporateActionsReportFilter);
        }
        testCase.embedTestData(corporateActionsReportFilter);
    }

    /**
     * Description: navigate to Corporate Actions Report template<br/>
     * Start page: anyPage<br/>
     * End page: Corporate Actions Report<br/>
     * samples: navigate to Corporate Actions Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Corporate Actions Report template (\\S+)$")
    public void navigate_to_corporate_actions_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            CorporateActionsReportFilter reportFilter = testDataManager.getCorporateActionsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.CORPORATE_ACTIONS, templateName);
    }

    /**
     * Description: set Ineligible Asset Report filter or save template<br/>
     * Start page: Ineligible Asset report<br/>
     * End page: Ineligible Asset report<br/>
     * samples: search Ineligible Asset Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Ineligible Asset Report (\\S+)$")
    public void search_ineligible_asset_report(String id)throws Exception{
        IneligibleAssetReportFilter ineligibleAssetReportFilter = testDataManager.getIneligibleAssetReportFilter(id);
        pageManager.ineligibleAssetReportPage.setIneligibleAssetReport(ineligibleAssetReportFilter);
        if (ineligibleAssetReportFilter.getOutputFormat() != null && !ineligibleAssetReportFilter.getOutputFormat().isEmpty()){
            pageManager.ineligibleAssetReportPage.editOutputFormat();
            pageManager.ineligibleAssetReportPage.setOutputParameters(ineligibleAssetReportFilter.getOutputFormat());
            pageManager.ineligibleAssetReportPage.applyChanges();
        }
        if (ineligibleAssetReportFilter.getSaveAsTemplate() != null){
            pageManager.ineligibleAssetReportPage.saveAsTemplate(ineligibleAssetReportFilter);
        }
        testCase.embedTestData(ineligibleAssetReportFilter);
    }

    /**
     * Description: navigate to Ineligible Asset Report template<br/>
     * Start page: anyPage<br/>
     * End page: Ineligible Asset Report<br/>
     * samples: navigate to Ineligible Asset Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^navigate to Ineligible Asset Report template (\\S+)$")
    public void navigate_to_ineligible_asset_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            IneligibleAssetReportFilter reportFilter = testDataManager.getIneligibleAssetReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INELIGIBLE_ASSET, templateName);
    }

    /**
     * Description: set Inventory Manager Report filter or save template<br/>
     * Start page: Inventory Manager Report<br/>
     * End page: Inventory Manager Report<br/>
     * samples: search Inventory Manager Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Inventory Manager Report (\\S+)$")
    public void search_inventory_manager_report(String id)throws Exception{
        InventoryManagerReportFilter inventoryManagerReportFilter = testDataManager.getInventoryManagerReportFilter(id);
        pageManager.inventoryManagerReportPage.setInventoryManagerReport(inventoryManagerReportFilter);
        if (inventoryManagerReportFilter.getOutputFormat() != null && !inventoryManagerReportFilter.getOutputFormat().isEmpty()){
            pageManager.inventoryManagerReportPage.editOutputFormat();
            pageManager.inventoryManagerReportPage.setOutputParameters(inventoryManagerReportFilter.getOutputFormat());
            pageManager.inventoryManagerReportPage.applyChanges();
        }
        if (inventoryManagerReportFilter.getSaveAsTemplate() != null){
            pageManager.inventoryManagerReportPage.saveAsTemplate(inventoryManagerReportFilter);
        }
        testCase.embedTestData(inventoryManagerReportFilter);

    }

    /**
     * Description: navigate to Inventory Manager Report template<br/>
     * Start page: anyPage<br/>
     * End page: Inventory Manager Report<br/>
     * samples: navigate to Inventory Manager Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Inventory Manager Report template (\\S+)$")
    public void navigate_to_inventory_manager_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            InventoryManagerReportFilter reportFilter = testDataManager.getInventoryManagerReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INVENTORY_MANAGER, templateName);
    }

    /**
     * Description: set Rehypothecation Eligibility Report filter or save template<br/>
     * Start page: Rehypothecation Eligibility Report<br/>
     * End page: Rehypothecation Eligibility Report<br/>
     * samples: search Rehypothecation Eligibility Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Rehypothecation Eligibility Report (\\S+)$")
    public void search_rehypothecation_eligibility_report(String id)throws Exception{
        RehypothecationEligibilityReportFilter rehypothecationEligibilityReportFilter = testDataManager.getRehypothecationEligibilityReportFilter(id);
        pageManager.rehypothecationEligibilityReportPage.setRehypothecationEligibilityReport(rehypothecationEligibilityReportFilter);
        if (rehypothecationEligibilityReportFilter.getOutputFormat() != null && !rehypothecationEligibilityReportFilter.getOutputFormat().isEmpty()){
            pageManager.rehypothecationEligibilityReportPage.editOutputFormat();
            pageManager.rehypothecationEligibilityReportPage.setOutputParameters(rehypothecationEligibilityReportFilter.getOutputFormat());
            pageManager.rehypothecationEligibilityReportPage.applyChanges();
        }
        if (rehypothecationEligibilityReportFilter.getSaveAsTemplate() != null){
            pageManager.rehypothecationEligibilityReportPage.saveAsTemplate(rehypothecationEligibilityReportFilter);
        }
        testCase.embedTestData(rehypothecationEligibilityReportFilter);
    }

    /**
     * Description: navigate to Rehypothecation Eligibility Report template<br/>
     * Start page: anyPage<br/>
     * End page: Rehypothecation Eligibility Report<br/>
     * samples: navigate to Rehypothecation Eligibility Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Rehypothecation Eligibility Report template (\\S+)$")
    public void navigate_to_rehypothecation_eligibility_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RehypothecationEligibilityReportFilter reportFilter = testDataManager.getRehypothecationEligibilityReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REHYPOTHECATION_ELIGIBILITY, templateName);
    }

    /**
     * Description: set Security Instruments Report filter or save template<br/>
     * Start page: Security Instruments Report<br/>
     * End page: Security Instruments Report<br/>
     * samples: search Security Instruments Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Security Instruments Report (\\S+)$")
    public void search_security_instruments_report(String id)throws Exception{
        SecurityInstrumentsReportFilter securityInstrumentsReportFilter = testDataManager.getSecurityInstrumentsReportFilter(id);
        pageManager.securityInstrumentsReportPage.setSecurityInstrumentsReport(securityInstrumentsReportFilter);
        if (securityInstrumentsReportFilter.getOutputFormat() != null && !securityInstrumentsReportFilter.getOutputFormat().isEmpty()){
            pageManager.securityInstrumentsReportPage.editOutputFormat();
            pageManager.securityInstrumentsReportPage.setOutputParameters(securityInstrumentsReportFilter.getOutputFormat());
            pageManager.securityInstrumentsReportPage.applyChanges();
        }
        if (securityInstrumentsReportFilter.getSaveAsTemplate() != null){
            pageManager.securityInstrumentsReportPage.saveAsTemplate(securityInstrumentsReportFilter);
        }
        testCase.embedTestData(securityInstrumentsReportFilter);

    }

    /**
     * Description: navigate to Security Instruments Report template<br/>
     * Start page: anyPage<br/>
     * End page: Security Instruments Report<br/>
     * samples: navigate to Security Instruments Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^navigate to Security Instruments Report template (\\S+)$")
    public void navigate_to_security_instruments_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            SecurityInstrumentsReportFilter reportFilter = testDataManager.getSecurityInstrumentsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.SECURITY_INSTRUMENTS, templateName);
    }

    /**
     * Description: set Audit Agreements Report filter or save template<br/>
     * Start page: Audit Agreements Report<br/>
     * End page: Audit Agreements Report<br/>
     * samples: search Audit Agreements Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Agreements Report (\\S+)$")
    public void search_audit_agreements_report(String id)throws Exception{
        AuditAgreementsReportFilter auditAgreementsReportFilter = testDataManager.getAuditAgreementsReportFilter(id);
        pageManager.auditAgreementsReportPage.setAuditAgreementsReport(auditAgreementsReportFilter);
        if (auditAgreementsReportFilter.getOutputFormat() != null && !auditAgreementsReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditAgreementsReportPage.editOutputFormat();
            pageManager.auditAgreementsReportPage.setOutputParameters(auditAgreementsReportFilter.getOutputFormat());
            pageManager.auditAgreementsReportPage.applyChanges();
        }
        if (auditAgreementsReportFilter.getSaveAsTemplate() != null){
            pageManager.auditAgreementsReportPage.saveAsTemplate(auditAgreementsReportFilter);
        }
        testCase.embedTestData(auditAgreementsReportFilter);
    }

    /**
     * Description: navigate to Audit Agreements Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Agreements Report<br/>
     * samples: navigate to Audit Agreements Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Agreements Report template (\\S+)$")
    public void navigate_to_audit_agreements_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditAgreementsReportFilter reportFilter = testDataManager.getAuditAgreementsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_AUDIT_AGREEMENTS, templateName);
    }

    /**
     * Description: set Audit Asset Definition Report filter or save template<br/>
     * Start page: Audit Asset Definition Report<br/>
     * End page: Audit Asset Definition Report<br/>
     * samples: search Audit Asset Definition Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Asset Definition Report (\\S+)$")
    public void search_audit_asset_definition_report(String id)throws Exception{
        AuditAssetDefinitionReportFilter auditAssetDefinitionReportFilter = testDataManager.getAuditAssetDefinitionReportFilter(id);
        pageManager.auditAssetDefinitionReportPage.setAuditAssetDefinitionReport(auditAssetDefinitionReportFilter);
        if (auditAssetDefinitionReportFilter.getOutputFormat() != null && !auditAssetDefinitionReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditAssetDefinitionReportPage.editOutputFormat();
            pageManager.auditAssetDefinitionReportPage.setOutputParameters(auditAssetDefinitionReportFilter.getOutputFormat());
            pageManager.auditAssetDefinitionReportPage.applyChanges();
        }
        if (auditAssetDefinitionReportFilter.getSaveAsTemplate() != null){
            pageManager.auditAssetDefinitionReportPage.saveAsTemplate(auditAssetDefinitionReportFilter);
        }
        testCase.embedTestData(auditAssetDefinitionReportFilter);

    }

    /**
     * Description: navigate to Audit Asset Definition Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Asset Definition Report<br/>
     * samples: navigate to Audit Asset Definition Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Asset Definition Report template (\\S+)$")
    public void navigate_to_audit_asset_definition_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditAssetDefinitionReportFilter reportFilter = testDataManager.getAuditAssetDefinitionReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ASSET_DEFINITION, templateName);
    }

    /**
     * Description: set Audit Asset Report filter or save template<br/>
     * Start page: Audit Assets Report<br/>
     * End page: Audit Assets Report<br/>
     * samples: search Audit Assets Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Assets Report (\\S+)$")
    public void search_audit_assets_report(String id)throws Exception{
        AuditAssetsReportFilter auditAssetsReportFilter = testDataManager.getAuditAssetsReportFilter(id);
        pageManager.auditAssetsReportPage.setAuditAssetsReport(auditAssetsReportFilter);
        if (auditAssetsReportFilter.getOutputFormat() != null && !auditAssetsReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditAssetsReportPage.editOutputFormat();
            pageManager.auditAssetsReportPage.setOutputParameters(auditAssetsReportFilter.getOutputFormat());
            pageManager.auditAssetsReportPage.applyChanges();
        }
        if (auditAssetsReportFilter.getSaveAsTemplate() != null){
            pageManager.auditAssetsReportPage.saveAsTemplate(auditAssetsReportFilter);
        }
        testCase.embedTestData(auditAssetsReportFilter);
    }

    /**
     * Description: navigate to Audit Assets Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Assets Report<br/>
     * samples: navigate to Audit Assets Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Assets Report template (\\S+)$")
    public void navigate_to_audit_assets_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditAssetsReportFilter reportFilter = testDataManager.getAuditAssetsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ASSETS, templateName);
    }

    /**
     * Description: set Audit Eligibility Rule Template Report filter or save template<br/>
     * Start page: Audit Eligibility Rule Template Report<br/>
     * End page: Audit Eligibility Rule Template Report<br/>
     * samples: search Audit Eligibility Rule Template Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Eligibility Rule Template Report (\\S+)$")
    public void search_audit_eligibility_rule_template_report(String id)throws Exception{
        AuditEligibilityRuleTemplateReportFilter auditEligibilityRuleTemplateReportFilter = testDataManager.getAuditEligibilityRuleTemplateReportFilter(id);
        pageManager.auditEligibilityRuleTemplateReportPage.setAuditEligibilityRuleTemplateReport(auditEligibilityRuleTemplateReportFilter);
        if (auditEligibilityRuleTemplateReportFilter.getOutputFormat() != null && !auditEligibilityRuleTemplateReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditEligibilityRuleTemplateReportPage.editOutputFormat();
            pageManager.auditEligibilityRuleTemplateReportPage.setOutputParameters(auditEligibilityRuleTemplateReportFilter.getOutputFormat());
            pageManager.auditEligibilityRuleTemplateReportPage.applyChanges();
        }
        if (auditEligibilityRuleTemplateReportFilter.getSaveAsTemplate() != null){
            pageManager.auditEligibilityRuleTemplateReportPage.saveAsTemplate(auditEligibilityRuleTemplateReportFilter);
        }
        testCase.embedTestData(auditEligibilityRuleTemplateReportFilter);
    }

    /**
     * Description: navigate to Audit Eligibility Rule Template Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Eligibility Rule Template Report<br/>
     * samples: navigate to Audit Eligibility Rule Template Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Eligibility Rule Template Report template (\\S+)$")
    public void navigate_to_audit_eligibility_rule_template_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditEligibilityRuleTemplateReportFilter reportFilter = testDataManager.getAuditEligibilityRuleTemplateReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ELIGIBILITY_RULE_TEMPLATE, templateName);
    }

    /**
     * Description: set Audit Holiday Centres Report filter or save template<br/>
     * Start page: Audit Holiday Centres Report<br/>
     * End page: Audit Holiday Centres Report<br/>
     * samples: search Audit Holiday Centres Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Holiday Centres Report (\\S+)$")
    public void search_audit_holiday_centres_report(String id)throws Exception{
        AuditHolidayCentresReportFilter auditHolidayCentresReportFilter = testDataManager.getAuditHolidayCentresReportFilter(id);
        pageManager.auditHolidayCentresReportPage.setAuditHolidayCentresReport(auditHolidayCentresReportFilter);
        if (auditHolidayCentresReportFilter.getOutputFormat() != null && !auditHolidayCentresReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditHolidayCentresReportPage.editOutputFormat();
            pageManager.auditHolidayCentresReportPage.setOutputParameters(auditHolidayCentresReportFilter.getOutputFormat());
            pageManager.auditHolidayCentresReportPage.applyChanges();
        }
        if (auditHolidayCentresReportFilter.getSaveAsTemplate() != null){
            pageManager.auditHolidayCentresReportPage.saveAsTemplate(auditHolidayCentresReportFilter);
        }
        testCase.embedTestData(auditHolidayCentresReportFilter);
    }

    /**
     * Description: navigate to Audit Holiday Centres Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Holiday Centres Report<br/>
     * samples: navigate to Audit Holiday Centres Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Holiday Centres Report template (\\S+)$")
    public void navigate_to_audit_holiday_centres_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditHolidayCentresReportFilter reportFilter = testDataManager.getAuditHolidayCentresReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_HOLIDAY_CENTRES, templateName);
    }

    /**
     * Description: set Audit Interest Rates Report filter or save template<br/>
     * Start page: Audit Interest Rates Report<br/>
     * End page: Audit Interest Rates Report<br/>
     * samples: search Audit Interest Rates Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Interest Rates Report (\\S+)$")
    public void search_audit_interest_rates_report(String id)throws Exception{
        AuditInterestRatesReportFilter auditInterestRatesReportFilter = testDataManager.getAuditInterestRatesReportFilter(id);
        pageManager.auditInterestRatesReportPage.setAuditInterestRatesReport(auditInterestRatesReportFilter);
        if (auditInterestRatesReportFilter.getOutputFormat() != null && !auditInterestRatesReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditInterestRatesReportPage.editOutputFormat();
            pageManager.auditInterestRatesReportPage.setOutputParameters(auditInterestRatesReportFilter.getOutputFormat());
            pageManager.auditInterestRatesReportPage.applyChanges();
        }
        if (auditInterestRatesReportFilter.getSaveAsTemplate() != null){
            pageManager.auditInterestRatesReportPage.saveAsTemplate(auditInterestRatesReportFilter);
        }
        testCase.embedTestData(auditInterestRatesReportFilter);
    }

    /**
     * Description: navigate to Audit Interest Rates Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Interest Rates Report<br/>
     * samples: navigate to Audit Interest Rates Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Interest Rates Report template (\\S+)$")
    public void navigate_to_audit_interest_rates_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditInterestRatesReportFilter reportFilter = testDataManager.getAuditInterestRatesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_INTEREST_RATES, templateName);
    }

    /**
     * Description: set Audit Optimisation Rule Report filter or save template<br/>
     * Start page: Audit Optimisation Rule Report<br/>
     * End page: Audit Optimisation Rule Report<br/>
     * samples: search Audit Optimisation Rule Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Optimisation Rule Report (\\S+)$")
    public void search_audit_optimisation_rule_report(String id)throws Exception{
        AuditOptimisationRuleReportFilter auditOptimisationRuleReportFilter = testDataManager.getAuditOptimisationRuleReportFilter(id);
        pageManager.auditOptimisationRuleReportPage.setAuditOptimisationRuleReport(auditOptimisationRuleReportFilter);
        if (auditOptimisationRuleReportFilter.getOutputFormat() != null && !auditOptimisationRuleReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditOptimisationRuleReportPage.editOutputFormat();
            pageManager.auditOptimisationRuleReportPage.setOutputParameters(auditOptimisationRuleReportFilter.getOutputFormat());
            pageManager.auditOptimisationRuleReportPage.applyChanges();
        }
        if (auditOptimisationRuleReportFilter.getSaveAsTemplate() != null){
            pageManager.auditOptimisationRuleReportPage.saveAsTemplate(auditOptimisationRuleReportFilter);
        }
        testCase.embedTestData(auditOptimisationRuleReportFilter);
    }

    /**
     * Description: navigate to Audit Optimisation Rule Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Optimisation Rule Report<br/>
     * samples: navigate to Audit Optimisation Rule Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Optimisation Rule Report template (\\S+)$")
    public void navigate_to_audit_optimisation_rule_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditOptimisationRuleReportFilter reportFilter = testDataManager.getAuditOptimisationRuleReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_OPTIMISATION_RULE, templateName);
    }

    /**
     * Description: set Audit Organisations Report filter or save template<br/>
     * Start page: Audit Organisations Report<br/>
     * End page: Audit Organisations Report<br/>
     * samples: search Audit Organisations Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Organisations Report (\\S+)$")
    public void search_audit_organisations_report(String id)throws Exception{
        AuditOrganisationsReportFilter auditOrganisationsReportFilter = testDataManager.getAuditOrganisationsReportFilter(id);
        pageManager.auditOrganisationsReportPage.setAuditOrganisationsReport(auditOrganisationsReportFilter);
        if (auditOrganisationsReportFilter.getOutputFormat() != null && !auditOrganisationsReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditOrganisationsReportPage.editOutputFormat();
            pageManager.auditOrganisationsReportPage.setOutputParameters(auditOrganisationsReportFilter.getOutputFormat());
            pageManager.auditOrganisationsReportPage.applyChanges();
        }
        if (auditOrganisationsReportFilter.getSaveAsTemplate() != null){
            pageManager.auditOrganisationsReportPage.saveAsTemplate(auditOrganisationsReportFilter);
        }
        testCase.embedTestData(auditOrganisationsReportFilter);
    }

    /**
     * Description: navigate to Audit Organisations Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Organisations Report<br/>
     * samples: navigate to Audit Organisations Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Organisations Report template (\\S+)$")
    public void navigate_to_audit_organisations_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditOrganisationsReportFilter reportFilter = testDataManager.getAuditOrganisationsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ORGANISATIONS, templateName);
    }

    /**
     * Description: set Audit Roles Administration Report filter or save template<br/>
     * Start page: Audit Roles Administration Report<br/>
     * End page: Audit Roles Administration Report<br/>
     * samples: search Audit Roles Administration Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Roles Administration Report (\\S+)$")
    public void search_audit_roles_administration_report(String id)throws Exception{
        AuditRolesAdministrationReportFilter auditRolesAdministrationReportFilter = testDataManager.getAuditRolesAdministrationReportFilter(id);
        pageManager.auditRolesAdministrationReportPage.setAuditRolesAdministrationReport(auditRolesAdministrationReportFilter);
        if (auditRolesAdministrationReportFilter.getOutputFormat() != null && !auditRolesAdministrationReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditRolesAdministrationReportPage.editOutputFormat();
            pageManager.auditRolesAdministrationReportPage.setOutputParameters(auditRolesAdministrationReportFilter.getOutputFormat());
            pageManager.auditRolesAdministrationReportPage.applyChanges();
        }
        if (auditRolesAdministrationReportFilter.getSaveAsTemplate() != null){
            pageManager.auditRolesAdministrationReportPage.saveAsTemplate(auditRolesAdministrationReportFilter);
        }
        testCase.embedTestData(auditRolesAdministrationReportFilter);
    }

    /**
     * Description: navigate to Audit Roles Administration Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Roles Administration Report<br/>
     * samples: navigate to Audit Roles Administration Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Roles Administration Report template (\\S+)$")
    public void navigate_to_audit_roles_administration_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditRolesAdministrationReportFilter reportFilter = testDataManager.getAuditRolesAdministrationReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_ROLES_ADMINISTRATION, templateName);
    }

    /**
     * Description: set Audit Settlement Details Report filter or save template<br/>
     * Start page: Audit Settlement Details Report<br/>
     * End page: Audit Settlement Details Report<br/>
     * samples: search Audit Settlement Details Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Settlement Details Report (\\S+)$")
    public void search_audit_settlement_details_report(String id)throws Exception{
        AuditSettlementDetailsReportFilter auditSettlementDetailsReportFilter = testDataManager.getAuditSettlementDetailsReportFilter(id);
        pageManager.auditSettlementDetailsReportPage.setAuditSettlementDetailsReport(auditSettlementDetailsReportFilter);
        if (auditSettlementDetailsReportFilter.getOutputFormat() != null && !auditSettlementDetailsReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditSettlementDetailsReportPage.editOutputFormat();
            pageManager.auditSettlementDetailsReportPage.setOutputParameters(auditSettlementDetailsReportFilter.getOutputFormat());
            pageManager.auditSettlementDetailsReportPage.applyChanges();
        }
        if (auditSettlementDetailsReportFilter.getSaveAsTemplate() != null){
            pageManager.auditSettlementDetailsReportPage.saveAsTemplate(auditSettlementDetailsReportFilter);
        }
        testCase.embedTestData(auditSettlementDetailsReportFilter);
    }

    /**
     * Description: navigate to Audit Settlement Details Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Settlement Details Report<br/>
     * samples: navigate to Audit Settlement Details Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Settlement Details Report template (\\S+)$")
    public void navigate_to_audit_settlement_details_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditSettlementDetailsReportFilter reportFilter = testDataManager.getAuditSettlementDetailsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_SETTLEMENT_DETAILS, templateName);
    }

    /**
     * Description: set Audit Statements Report filter or save template<br/>
     * Start page: Audit Statements Report<br/>
     * End page: Audit Statements Report<br/>
     * samples: search Audit Statements Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Statements Report (\\S+)$")
    public void search_audit_statements_report(String id)throws Exception{
        AuditStatementsReportFilter auditStatementsReportFilter = testDataManager.getAuditStatementsReportFilter(id);
        pageManager.auditStatementsReportPage.setAuditStatementsReport(auditStatementsReportFilter);
        if (auditStatementsReportFilter.getOutputFormat() != null && !auditStatementsReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditStatementsReportPage.editOutputFormat();
            pageManager.auditStatementsReportPage.setOutputParameters(auditStatementsReportFilter.getOutputFormat());
            pageManager.auditStatementsReportPage.applyChanges();
        }
        if (auditStatementsReportFilter.getSaveAsTemplate() != null){
            pageManager.auditStatementsReportPage.saveAsTemplate(auditStatementsReportFilter);
        }
        testCase.embedTestData(auditStatementsReportFilter);
    }

    /**
     * Description: navigate to Audit Statements Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Statements Report<br/>
     * samples: navigate to Audit Statements Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Statements Report template (\\S+)$")
    public void navigate_to_audit_statements_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditStatementsReportFilter reportFilter = testDataManager.getAuditStatementsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_STATEMENTS, templateName);
    }

    /**
     * Description: set Audit Trades Report filter or save template<br/>
     * Start page: Audit Trades Report<br/>
     * End page: Audit Trades Report<br/>
     * samples: search Audit Trades Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit Trades Report (\\S+)$")
    public void search_audit_trades_report(String id)throws Exception{
        AuditTradesReportFilter auditTradesReportFilter = testDataManager.getAuditTradesReportFilter(id);
        pageManager.auditTradesReportPage.setAuditTradesReport(auditTradesReportFilter);
        if (auditTradesReportFilter.getOutputFormat() != null && !auditTradesReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditTradesReportPage.editOutputFormat();
            pageManager.auditTradesReportPage.setOutputParameters(auditTradesReportFilter.getOutputFormat());
            pageManager.auditTradesReportPage.applyChanges();
        }
        if (auditTradesReportFilter.getSaveAsTemplate() != null){
            pageManager.auditTradesReportPage.saveAsTemplate(auditTradesReportFilter);
        }
        testCase.embedTestData(auditTradesReportFilter);
    }

    /**
     * Description: navigate to Audit Trades Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit Trades Report<br/>
     * samples: navigate to Audit Trades Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit Trades Report template (\\S+)$")
    public void navigate_to_audit_trades_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditTradesReportFilter reportFilter = testDataManager.getAuditTradesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_TRADES, templateName);
    }

    /**
     * Description: set Audit User Administration Report filter or save template<br/>
     * Start page: Audit User Administration Report<br/>
     * End page: Audit User Administration Report<br/>
     * samples: search Audit User Administration Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Audit User Administration Report (\\S+)$")
    public void search_audit_user_administration_report(String id)throws Exception{
        AuditUserAdministrationReportFilter auditUserAdministrationReportFilter = testDataManager.getAuditUserAdministrationReportFilter(id);
        pageManager.auditUserAdministrationReportPage.setAuditUserAdministrationReportFilter(auditUserAdministrationReportFilter);
        if (auditUserAdministrationReportFilter.getOutputFormat() != null && !auditUserAdministrationReportFilter.getOutputFormat().isEmpty()){
            pageManager.auditUserAdministrationReportPage.editOutputFormat();
            pageManager.auditUserAdministrationReportPage.setOutputParameters(auditUserAdministrationReportFilter.getOutputFormat());
            pageManager.auditUserAdministrationReportPage.applyChanges();
        }
        if (auditUserAdministrationReportFilter.getSaveAsTemplate() != null){
            pageManager.auditUserAdministrationReportPage.saveAsTemplate(auditUserAdministrationReportFilter);
        }
        testCase.embedTestData(auditUserAdministrationReportFilter);
    }

    /**
     * Description: navigate to Audit User Administration Report template<br/>
     * Start page: anyPage<br/>
     * End page: Audit User Administration Report<br/>
     * samples: navigate to Audit User Administration Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Audit User Administration Report template (\\S+)$")
    public void navigate_to_audit_user_administration_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AuditUserAdministrationReportFilter reportFilter = testDataManager.getAuditUserAdministrationReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AUDIT_USER_ADMINISTRATION, templateName);
    }

    /**
     * Description: set Frequency Distribution Log Report filter or save template<br/>
     * Start page: Frequency Distribution Log Report<br/>
     * End page: Frequency Distribution Log Report<br/>
     * samples: search Frequency Distribution Log Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Frequency Distribution Log Report (\\S+)$")
    public void search_frequency_distribution_log_report(String id)throws Exception{
        FrequencyDistributionLogReportFilter frequencyDistributionLogReportFilter = testDataManager.getFrequencyDistributionLogReportFilter(id);
        pageManager.frequencyDistributionLogReportPage.setFrequencyDistributionLogReport(frequencyDistributionLogReportFilter);
        if (frequencyDistributionLogReportFilter.getOutputFormat() != null && !frequencyDistributionLogReportFilter.getOutputFormat().isEmpty()){
            pageManager.frequencyDistributionLogReportPage.editOutputFormat();
            pageManager.frequencyDistributionLogReportPage.setOutputParameters(frequencyDistributionLogReportFilter.getOutputFormat());
            pageManager.frequencyDistributionLogReportPage.applyChanges();
        }
        if (frequencyDistributionLogReportFilter.getSaveAsTemplate() != null){
            pageManager.frequencyDistributionLogReportPage.saveAsTemplate(frequencyDistributionLogReportFilter);
        }
        testCase.embedTestData(frequencyDistributionLogReportFilter);
    }

    /**
     * Description: navigate to Frequency Distribution Log Report template<br/>
     * Start page: anyPage<br/>
     * End page: Frequency Distribution Log Report<br/>
     * samples: navigate to Frequency Distribution Log Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Frequency Distribution Log Report template (\\S+)$")
    public void navigate_to_frequency_distribution_log_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            FrequencyDistributionLogReportFilter reportFilter = testDataManager.getFrequencyDistributionLogReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.FREQUENCY_DISTRIBUTION_LOG, templateName);
    }

    /**
     * Description: set Agreements Report filter or save template<br/>
     * Start page: Agreements Report<br/>
     * End page: Agreements Report<br/>
     * samples: search Agreements Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Agreements Report (\\S+)$")
    public void search_agreements_report(String id)throws Exception{
        AgreementsReportFilter agreementsReportFilter = testDataManager.getAgreementsReportFilter(id);
        pageManager.agreementsReportPage.setAgreementsReport(agreementsReportFilter);
        if (agreementsReportFilter.getOutputFormat() != null && !agreementsReportFilter.getOutputFormat().isEmpty()){
            pageManager.agreementsReportPage.editOutputFormat();
            pageManager.agreementsReportPage.setOutputParameters(agreementsReportFilter.getOutputFormat());
            pageManager.agreementsReportPage.applyChanges();
        }
        if (agreementsReportFilter.getSaveAsTemplate() != null){
            pageManager.agreementsReportPage.saveAsTemplate(agreementsReportFilter);
        }
        testCase.embedTestData(agreementsReportFilter);
    }

    /**
     * Description: navigate to Agreements Report template<br/>
     * Start page: anyPage<br/>
     * End page: Agreements Report<br/>
     * samples: navigate to Agreements Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Agreements Report template (\\S+)$")
    public void navigate_to_agreements_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            AgreementsReportFilter reportFilter = testDataManager.getAgreementsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.AGREEMENTS, templateName);
    }

    /**
     * Description: set Eligibility Rules Template Report filter or save template<br/>
     * Start page: Eligibility Rules Template Report<br/>
     * End page: Eligibility Rules Template Report<br/>
     * samples: search Eligibility Rules Template Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Eligibility Rules Template Report (\\S+)$")
    public void search_eligibility_rules_template_report(String id)throws Exception{
        EligibilityRulesTemplateReportFilter eligibilityRulesTemplateReportFilter = testDataManager.getEligibilityRulesTemplateReportFilter(id);
        pageManager.eligibilityRulesTemplateReportPage.setEligibilityRulesTemplateReport(eligibilityRulesTemplateReportFilter);
        if (eligibilityRulesTemplateReportFilter.getOutputFormat() != null && !eligibilityRulesTemplateReportFilter.getOutputFormat().isEmpty()){
            pageManager.eligibilityRulesTemplateReportPage.editOutputFormat();
            pageManager.eligibilityRulesTemplateReportPage.setOutputParameters(eligibilityRulesTemplateReportFilter.getOutputFormat());
            pageManager.eligibilityRulesTemplateReportPage.applyChanges();
        }
        if (eligibilityRulesTemplateReportFilter.getSaveAsTemplate() != null){
            pageManager.eligibilityRulesTemplateReportPage.saveAsTemplate(eligibilityRulesTemplateReportFilter);
        }
        testCase.embedTestData(eligibilityRulesTemplateReportFilter);
    }

    /**
     * Description: navigate to Eligibility Rules Template Report template<br/>
     * Start page: anyPage<br/>
     * End page: Eligibility Rules Template Report<br/>
     * samples: navigate to Eligibility Rules Template Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Eligibility Rules Template Report template (\\S+)$")
    public void navigate_to_eligibility_rules_template_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            EligibilityRulesTemplateReportFilter reportFilter = testDataManager.getEligibilityRulesTemplateReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ELIGIBILITY_RULES_TEMPLATE, templateName);
    }

    /**
     * Description: set Historical FX Rates Report filter or save template<br/>
     * Start page: Historical FX Rates Report<br/>
     * End page: Historical FX Rates Report<br/>
     * samples: search Historical FX Rates Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Historical FX Rates Report (\\S+)$")
    public void search_historical_fx_rates_report(String id)throws Exception{
        HistoricalFxRatesReportFilter historicalFxRatesReportFilter = testDataManager.getHistoricalFxRatesReportFilter(id);
        pageManager.historicalFxRatesReportPage.setHistoricalFxRatesReport(historicalFxRatesReportFilter);
        if (historicalFxRatesReportFilter.getOutputFormat() != null && !historicalFxRatesReportFilter.getOutputFormat().isEmpty()){
            pageManager.historicalFxRatesReportPage.editOutputFormat();
            pageManager.historicalFxRatesReportPage.setOutputParameters(historicalFxRatesReportFilter.getOutputFormat());
            pageManager.historicalFxRatesReportPage.applyChanges();
        }
        if (historicalFxRatesReportFilter.getSaveAsTemplate() != null){
            pageManager.historicalFxRatesReportPage.saveAsTemplate(historicalFxRatesReportFilter);
        }
        testCase.embedTestData(historicalFxRatesReportFilter);
    }

    /**
     * Description: navigate to Historical FX Rates Report template<br/>
     * Start page: anyPage<br/>
     * End page: Historical FX Rates Report<br/>
     * samples: navigate to Historical FX Rates Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Historical FX Rates Report template (\\S+)$")
    public void navigate_to_historical_fx_rates_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            HistoricalFxRatesReportFilter reportFilter = testDataManager.getHistoricalFxRatesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_FX_RATES, templateName);
    }

    /**
     * Description: set Historical Interest Rate Report filter or save template<br/>
     * Start page: Historical Interest Rate Report<br/>
     * End page: Historical Interest Rate Report<br/>
     * samples: search Historical Interest Rate Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Historical Interest Rate Report (\\S+)$")
    public void search_historical_interest_rate_report(String id)throws Exception{
        HistoricalInterestRateReportFilter historicalInterestRateReportFilter = testDataManager.getHistoricalInterestRateReportFilter(id);
        pageManager.historicalInterestRateReportPage.setHistoricalInterestRateReport(historicalInterestRateReportFilter);
        if (historicalInterestRateReportFilter.getOutputFormat() != null && !historicalInterestRateReportFilter.getOutputFormat().isEmpty()){
            pageManager.historicalInterestRateReportPage.editOutputFormat();
            pageManager.historicalInterestRateReportPage.setOutputParameters(historicalInterestRateReportFilter.getOutputFormat());
            pageManager.historicalInterestRateReportPage.applyChanges();
        }
        if (historicalInterestRateReportFilter.getSaveAsTemplate() != null){
            pageManager.historicalInterestRateReportPage.saveAsTemplate(historicalInterestRateReportFilter);
        }
        testCase.embedTestData(historicalInterestRateReportFilter);
    }

    /**
     * Description: navigate to Historical Interest Rate Report template<br/>
     * Start page: anyPage<br/>
     * End page: Historical Interest Rate Report<br/>
     * samples: navigate to Historical Interest Rate Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Historical Interest Rate Report template (\\S+)$")
    public void navigate_to_historical_interest_rate_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            HistoricalInterestRateReportFilter reportFilter = testDataManager.getHistoricalInterestRateReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_INTEREST_RATE, templateName);
    }

    /**
     * Description: set Licensing Report filter or save template<br/>
     * Start page: Licensing Report<br/>
     * End page: Licensing Report<br/>
     * samples: search Licensing Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Licensing Report (\\S+)$")
    public void search_licensing_report(String id)throws Exception{
        LicensingReportFilter licensingReportFilter = testDataManager.getLicensingReportFilter(id);
        pageManager.licensingReportPage.setLicensingReport(licensingReportFilter);
        if (licensingReportFilter.getOutputFormat() != null && !licensingReportFilter.getOutputFormat().isEmpty()){
            pageManager.licensingReportPage.editOutputFormat();
            pageManager.licensingReportPage.setOutputParameters(licensingReportFilter.getOutputFormat());
            pageManager.licensingReportPage.applyChanges();
        }
        if (licensingReportFilter.getSaveAsTemplate() != null){
            pageManager.licensingReportPage.saveAsTemplate(licensingReportFilter);
        }
        testCase.embedTestData(licensingReportFilter);
    }

    /**
     * Description: navigate to Licensing Report template<br/>
     * Start page: anyPage<br/>
     * End page: Licensing Report<br/>
     * samples: navigate to Licensing Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Licensing Report template (\\S+)$")
    public void navigate_to_licensing_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            LicensingReportFilter reportFilter = testDataManager.getLicensingReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.LICENSING, templateName);
    }

    /**
     * Description: set Organisation Agreements Report filter or save template<br/>
     * Start page: Organisation Agreements Report<br/>
     * End page: Organisation Agreements Report<br/>
     * samples: search Organisation Agreements Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Organisation Agreements Report (\\S+)$")
    public void search_organisation_agreements_report(String id)throws Exception{
        OrganisationAgreementsReportFilter organisationAgreementsReportFilter = testDataManager.getOrganisationAgreementsReportFilter(id);
        pageManager.organisationAgreementsReportPage.setOrganisationAgreementsReport(organisationAgreementsReportFilter);
        if (organisationAgreementsReportFilter.getOutputFormat() != null && !organisationAgreementsReportFilter.getOutputFormat().isEmpty()){
            pageManager.organisationAgreementsReportPage.editOutputFormat();
            pageManager.organisationAgreementsReportPage.setOutputParameters(organisationAgreementsReportFilter.getOutputFormat());
            pageManager.organisationAgreementsReportPage.applyChanges();
        }
        if (organisationAgreementsReportFilter.getSaveAsTemplate() != null){
            pageManager.organisationAgreementsReportPage.saveAsTemplate(organisationAgreementsReportFilter);
        }
        testCase.embedTestData(organisationAgreementsReportFilter);

    }

    /**
     * Description: navigate to Organisation Agreements Report template<br/>
     * Start page: anyPage<br/>
     * End page: Organisation Agreements Report<br/>
     * samples: navigate to Organisation Agreements Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Organisation Agreements Report template (\\S+)$")
    public void navigate_to_organisation_agreements_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            OrganisationAgreementsReportFilter reportFilter = testDataManager.getOrganisationAgreementsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ORGANISATION_AGREEMENTS, templateName);
    }


    /**
     * Description: set Organisation Threshold Report filter or save template<br/>
     * Start page: Organisation Threshold Report<br/>
     * End page: Organisation Threshold Report<br/>
     * samples: search Organisation Threshold Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Organisation Threshold Report (\\S+)$")
    public void search_organisation_threshold_report(String id)throws Exception{
        OrganisationThresholdReportFilter organisationThresholdReportFilter = testDataManager.getOrganisationThresholdReportFilter(id);
        pageManager.organisationThresholdReportPage.setOrganisationThresholdReport(organisationThresholdReportFilter);
        if (organisationThresholdReportFilter.getOutputFormat() != null && !organisationThresholdReportFilter.getOutputFormat().isEmpty()){
            pageManager.organisationThresholdReportPage.editOutputFormat();
            pageManager.organisationThresholdReportPage.setOutputParameters(organisationThresholdReportFilter.getOutputFormat());
            pageManager.organisationThresholdReportPage.applyChanges();
        }
        if (organisationThresholdReportFilter.getSaveAsTemplate() != null){
            pageManager.organisationThresholdReportPage.saveAsTemplate(organisationThresholdReportFilter);
        }
        testCase.embedTestData(organisationThresholdReportFilter);
    }

    /**
     * Description: navigate to Organisation Threshold Report template<br/>
     * Start page: anyPage<br/>
     * End page: Organisation Threshold Report<br/>
     * samples: navigate to Organisation Threshold Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Organisation Threshold Report template (\\S+)$")
    public void navigate_to_organisation_threshold_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            OrganisationThresholdReportFilter reportFilter = testDataManager.getOrganisationThresholdReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ORGANISATION_THRESHOLD, templateName);
    }

    /**
     * Description: set Settlement Instructions Report filter or save template<br/>
     * Start page: Settlement Instructions Report<br/>
     * End page: Settlement Instructions Report<br/>
     * samples: search Settlement Instructions Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Settlement Instructions Report (\\S+)$")
    public void search_settlement_instructions_report(String id)throws Exception{
        SettlementInstructionsReportFilter settlementInstructionsReportFilter = testDataManager.getSettlementInstructionsReportFilter(id);
        pageManager.settlementInstructionsReportPage.setSettlementInstructionsReport(settlementInstructionsReportFilter);
        if (settlementInstructionsReportFilter.getOutputFormat() != null && !settlementInstructionsReportFilter.getOutputFormat().isEmpty()){
            pageManager.settlementInstructionsReportPage.editOutputFormat();
            pageManager.settlementInstructionsReportPage.setOutputParameters(settlementInstructionsReportFilter.getOutputFormat());
            pageManager.settlementInstructionsReportPage.applyChanges();
        }
        if (settlementInstructionsReportFilter.getSaveAsTemplate() != null){
            pageManager.settlementInstructionsReportPage.saveAsTemplate(settlementInstructionsReportFilter);
        }
        testCase.embedTestData(settlementInstructionsReportFilter);
    }

    /**
     * Description: navigate to Settlement Instructions Report template<br/>
     * Start page: anyPage<br/>
     * End page: Settlement Instructions Report<br/>
     * samples: navigate to Settlement Instructions Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Settlement Instructions Report template (\\S+)$")
    public void navigate_to_settlement_instructions_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            SettlementInstructionsReportFilter reportFilter = testDataManager.getSettlementInstructionsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.SETTLEMENT_INSTRUCTIONS, templateName);
    }

    /**
     * Description: set Cash And Accrual Report filter or save template<br/>
     * Start page: Cash And Accrual Report<br/>
     * End page: Cash And Accrual Report<br/>
     * samples: search Cash And Accrual Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Cash And Accrual Report (\\S+)$")
    public void search_cash_accrual_report(String id)throws Exception{
        CashAndAccrualReportFilter cashAndAccrualReportFilter = testDataManager.getCashAndAccrualReportFilter(id);
        pageManager.cashAndAccrualReportPage.setCashAndAccrualReport(cashAndAccrualReportFilter);
        if (cashAndAccrualReportFilter.getOutputFormat() != null && !cashAndAccrualReportFilter.getOutputFormat().isEmpty()){
            pageManager.cashAndAccrualReportPage.editOutputFormat();
            pageManager.cashAndAccrualReportPage.setOutputParameters(cashAndAccrualReportFilter.getOutputFormat());
            pageManager.cashAndAccrualReportPage.applyChanges();
        }
        if (cashAndAccrualReportFilter.getSaveAsTemplate() != null){
            pageManager.cashAndAccrualReportPage.saveAsTemplate(cashAndAccrualReportFilter);
        }
        testCase.embedTestData(cashAndAccrualReportFilter);
    }

    /**
     * Description: navigate to Cash And Accrual Report template<br/>
     * Start page: anyPage<br/>
     * End page: Cash And Accrual Report<br/>
     * samples: navigate to Cash And Accrual Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Cash And Accrual Report template (\\S+)$")
    public void navigate_to_cash_accrual_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            CashAndAccrualReportFilter reportFilter = testDataManager.getCashAndAccrualReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.CASH_AND_ACCRUAL, templateName);
    }

    /**
     * Description: set Interest Applied Report filter or save template<br/>
     * Start page: Interest Applied Report<br/>
     * End page: Interest Applied Report<br/>
     * samples: search Interest Applied Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Interest Applied Report (\\S+)$")
    public void search_interest_applied_report(String id)throws Exception{
        InterestAppliedReportFilter interestAppliedReportFilter = testDataManager.getInterestAppliedReportFilter(id);
        pageManager.interestAppliedReportPage.setInterestAppliedReport(interestAppliedReportFilter);
        if (interestAppliedReportFilter.getOutputFormat() != null && !interestAppliedReportFilter.getOutputFormat().isEmpty()){
            pageManager.interestAppliedReportPage.editOutputFormat();
            pageManager.interestAppliedReportPage.setOutputParameters(interestAppliedReportFilter.getOutputFormat());
            pageManager.interestAppliedReportPage.applyChanges();
        }
        if (interestAppliedReportFilter.getSaveAsTemplate() != null){
            pageManager.interestAppliedReportPage.saveAsTemplate(interestAppliedReportFilter);
        }
        testCase.embedTestData(interestAppliedReportFilter);
    }

    /**
     * Description: navigate to Interest Applied Report template<br/>
     * Start page: anyPage<br/>
     * End page: Interest Applied Report<br/>
     * samples: navigate to Interest Applied Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Interest Applied Report template (\\S+)$")
    public void navigate_to_interest_applied_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            InterestAppliedReportFilter reportFilter = testDataManager.getInterestAppliedReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_APPLIED, templateName);
    }

    /**
     * Description: set Interest Calculations Scheduled Report filter or save template<br/>
     * Start page: Interest Calculations Scheduled Report<br/>
     * End page: Interest Calculations Scheduled Report<br/>
     * samples: search Interest Calculations Scheduled Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Interest Calculations Scheduled Report (\\S+)$")
    public void search_interest_calculations_scheduled_report(String id)throws Exception{
        InterestCalculationsScheduleReportFilter interestCalculationsScheduleReportFilter = testDataManager.getInterestCalculationsScheduleReportFilter(id);
        pageManager.interestCalculationsScheduledReportPage.setInterestCalculationsScheduledReport(interestCalculationsScheduleReportFilter);
        if (interestCalculationsScheduleReportFilter.getOutputFormat() != null && !interestCalculationsScheduleReportFilter.getOutputFormat().isEmpty()){
            pageManager.interestCalculationsScheduledReportPage.editOutputFormat();
            pageManager.interestCalculationsScheduledReportPage.setOutputParameters(interestCalculationsScheduleReportFilter.getOutputFormat());
            pageManager.interestCalculationsScheduledReportPage.applyChanges();
        }
        if (interestCalculationsScheduleReportFilter.getSaveAsTemplate() != null){
            pageManager.interestCalculationsScheduledReportPage.saveAsTemplate(interestCalculationsScheduleReportFilter);
        }
        testCase.embedTestData(interestCalculationsScheduleReportFilter);
    }

    /**
     * Description: navigate to Interest Calculations Scheduled Report template<br/>
     * Start page: anyPage<br/>
     * End page: Interest Calculations Scheduled Report<br/>
     * samples: navigate to Interest Calculations Scheduled Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Interest Calculations Scheduled Report template (\\S+)$")
    public void navigate_to_interest_calculations_scheduled_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            InterestCalculationsScheduleReportFilter reportFilter = testDataManager.getInterestCalculationsScheduleReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_CALCULATIONS_SCHEDULED, templateName);
    }

    /**
     * Description: set Interest Changes Report filter or save template<br/>
     * Start page: Interest Changes Report<br/>
     * End page: Interest Changes Report<br/>
     * samples: search Interest Changes Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Interest Changes Report (\\S+)$")
    public void search_interest_changes_report(String id)throws Exception{
        InterestChangesReportFilter interestChangesReportFilter = testDataManager.getInterestChangesReportFilter(id);
        pageManager.interestChangesReportPage.setInterestChangesReport(interestChangesReportFilter);
        if (interestChangesReportFilter.getOutputFormat() != null && !interestChangesReportFilter.getOutputFormat().isEmpty()){
            pageManager.interestChangesReportPage.editOutputFormat();
            pageManager.interestChangesReportPage.setOutputParameters(interestChangesReportFilter.getOutputFormat());
            pageManager.interestChangesReportPage.applyChanges();
        }
        if (interestChangesReportFilter.getSaveAsTemplate() != null){
            pageManager.interestChangesReportPage.saveAsTemplate(interestChangesReportFilter);
        }
        testCase.embedTestData(interestChangesReportFilter);
    }

    /**
     * Description: navigate to Interest Changes Report template<br/>
     * Start page: anyPage<br/>
     * End page: Interest Changes Report<br/>
     * samples: navigate to Interest Changes Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Interest Changes Report template (\\S+)$")
    public void navigate_to_interest_changes_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            InterestChangesReportFilter reportFilter = testDataManager.getInterestChangesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INTEREST_CHANGES, templateName);
    }

    /**
     * Description: set Month End Report filter or save template<br/>
     * Start page: Month End Report<br/>
     * End page: Month End Report<br/>
     * samples: search Month End Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Month End Report (\\S+)$")
    public void search_month_end_report(String id)throws Exception{
        MonthEndReportFilter monthEndReportFilter = testDataManager.getMonthEndReportFilter(id);
        pageManager.monthEndReportPage.setMonthEndReport(monthEndReportFilter);
        if (monthEndReportFilter.getOutputFormat() != null && !monthEndReportFilter.getOutputFormat().isEmpty()){
            pageManager.monthEndReportPage.editOutputFormat();
            pageManager.monthEndReportPage.setOutputParameters(monthEndReportFilter.getOutputFormat());
            pageManager.monthEndReportPage.applyChanges();
        }
        if (monthEndReportFilter.getSaveAsTemplate() != null){
            pageManager.monthEndReportPage.saveAsTemplate(monthEndReportFilter);
        }
        testCase.embedTestData(monthEndReportFilter);
    }

    /**
     * Description: navigate to Month End Report template<br/>
     * Start page: anyPage<br/>
     * End page: Month End Report<br/>
     * samples: navigate to Month End Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Month End Report template (\\S+)$")
    public void navigate_to_month_end_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            MonthEndReportFilter reportFilter = testDataManager.getMonthEndReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.MONTH_END, templateName);
    }

    /**
     * Description: set Rejected Trades Report filter or save template<br/>
     * Start page: Rejected Trades Report<br/>
     * End page: Rejected Trades Report<br/>
     * samples: search Rejected Trades Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Rejected Trades Report (\\S+)$")
    public void search_rejected_trades_report(String id)throws Exception{
        RejectedTradesReportFilter rejectedTradesReportFilter = testDataManager.getRejectedTradesReportFilter(id);
        pageManager.rejectedTradesReportPage.setRejectedTradesReport(rejectedTradesReportFilter);
        if (rejectedTradesReportFilter.getOutputFormat() != null && !rejectedTradesReportFilter.getOutputFormat().isEmpty()){
            pageManager.rejectedTradesReportPage.editOutputFormat();
            pageManager.rejectedTradesReportPage.setOutputParameters(rejectedTradesReportFilter.getOutputFormat());
            pageManager.rejectedTradesReportPage.applyChanges();
        }
        if (rejectedTradesReportFilter.getSaveAsTemplate() != null){
            pageManager.rejectedTradesReportPage.saveAsTemplate(rejectedTradesReportFilter);
        }
        testCase.embedTestData(rejectedTradesReportFilter);
    }

    /**
     * Description: navigate to Rejected Trades Report template<br/>
     * Start page: anyPage<br/>
     * End page: Rejected Trades Report<br/>
     * samples: navigate to Rejected Trades Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Rejected Trades Report template (\\S+)$")
    public void navigate_to_rejected_trades_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RejectedTradesReportFilter reportFilter = testDataManager.getRejectedTradesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REJECTED_TRADES, templateName);
    }

    /**
     * Description: set Repo Rejected Trades Report filter or save template<br/>
     * Start page: Repo Rejected Trades Report<br/>
     * End page: Repo Rejected Trades Report<br/>
     * samples: search Repo Rejected Trades Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Repo Rejected Trades Report (\\S+)$")
    public void search_repo_rejected_trades_report(String id)throws Exception{
        RejectedTradesReportFilter rejectedTradesReportFilter = testDataManager.getRejectedTradesReportFilter(id);
        pageManager.repoEtfsblRejectedTradesReportPage.setRepoEtfsblRejectedTradesReport(rejectedTradesReportFilter);
        if (rejectedTradesReportFilter.getOutputFormat() != null && !rejectedTradesReportFilter.getOutputFormat().isEmpty()){
            pageManager.repoEtfsblRejectedTradesReportPage.editOutputFormat();
            pageManager.repoEtfsblRejectedTradesReportPage.setOutputParameters(rejectedTradesReportFilter.getOutputFormat());
            pageManager.repoEtfsblRejectedTradesReportPage.applyChanges();
        }
        if (rejectedTradesReportFilter.getSaveAsTemplate() != null){
            pageManager.repoEtfsblRejectedTradesReportPage.saveAsTemplate(rejectedTradesReportFilter);
        }
        testCase.embedTestData(rejectedTradesReportFilter);
    }

    /**
     * Description: navigate to Repo Rejected Trades Report template<br/>
     * Start page: anyPage<br/>
     * End page: Repo Rejected Trades Report<br/>
     * samples: navigate to Repo Rejected Trades Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Repo Rejected Trades Report template (\\S+)$")
    public void navigate_to_repo_rejected_trades_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RejectedTradesReportFilter reportFilter = testDataManager.getRepoEtfsblRejectedTradesReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_REJECTED_TRADES, templateName);
    }

    /**
     * Description: set Repo Trades Output Report filter or save template<br/>
     * Start page: Repo Trades Output Report<br/>
     * End page: Repo Trades Output Report<br/>
     * samples: search Repo Trades Output Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Repo Trades Output Report (\\S+)$")
    public void search_repo_trades_output_report(String id)throws Exception{
        RepoEtfsblTradesOutputReportFilter repoEtfsblTradesOutputReportFilter = testDataManager.getRepoEtfsblTradesOutputReportFilter(id);
        pageManager.repoEtfsblTradesOutputReportPage.setRepoEtfsblTradesOutputReport(repoEtfsblTradesOutputReportFilter);
        if (repoEtfsblTradesOutputReportFilter.getOutputFormat() != null && !repoEtfsblTradesOutputReportFilter.getOutputFormat().isEmpty()){
            pageManager.repoEtfsblTradesOutputReportPage.editOutputFormat();
            pageManager.repoEtfsblTradesOutputReportPage.setOutputParameters(repoEtfsblTradesOutputReportFilter.getOutputFormat());
            pageManager.repoEtfsblTradesOutputReportPage.applyChanges();
        }
        if (repoEtfsblTradesOutputReportFilter.getSaveAsTemplate() != null){
            pageManager.repoEtfsblTradesOutputReportPage.saveAsTemplate(repoEtfsblTradesOutputReportFilter);
        }
        testCase.embedTestData(repoEtfsblTradesOutputReportFilter);
    }

    /**
     * Description: navigate to Repo Trades Output Report template<br/>
     * Start page: anyPage<br/>
     * End page: Repo Trades Output Report<br/>
     * samples: navigate to Repo Trades Output Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Repo Trades Output Report template (\\S+)$")
    public void navigate_to_repo_trades_output_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RepoEtfsblTradesOutputReportFilter reportFilter = testDataManager.getRepoEtfsblTradesOutputReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_TRADES_OUTPUT, templateName);
    }

    /**
     * Description: set Trades Output Report filter or save template<br/>
     * Start page: Trades Output Report<br/>
     * End page: Trades Output Report<br/>
     * samples: search Trades Output Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link TradesOutputReportFilter}</li>
     * @throws Exception
     */
    @When("^search Trades Output Report (\\S+)$")
    public void search_trades_output_report(String id)throws Exception{
        TradesOutputReportFilter tradesOutputReportFilter = testDataManager.getTradesOutputReportFilter(id);
        pageManager.tradesOutputReportPage.setTradesOutputReport(tradesOutputReportFilter);
        if(tradesOutputReportFilter.getOutputFormat() != null && !tradesOutputReportFilter.getOutputFormat().isEmpty()){
            pageManager.tradesOutputReportPage.editOutputFormat();
            pageManager.tradesOutputReportPage.setOutputParameters(tradesOutputReportFilter.getOutputFormat());
            pageManager.tradesOutputReportPage.applyChanges();
        }
        if(tradesOutputReportFilter.getSaveAsTemplate() != null){
            pageManager.tradesOutputReportPage.saveAsTemplate(tradesOutputReportFilter);
        }
        testCase.embedTestData(tradesOutputReportFilter);
    }

    /**
     * Description: navigate to Trades Output Report template<br/>
     * Start page: anyPage<br/>
     * End page: Trades Output Report<br/>
     * samples: navigate to Trades Output Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Trades Output Report template (\\S+)$")
    public void navigate_to_trades_output_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            TradesOutputReportFilter reportFilter = testDataManager.getTradesOutputReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.TRADES_OUTPUT, templateName);
    }

    /**
     * Description: set Daily Exposure Report filter or save template<br/>
     * Start page: Daily Exposure Report<br/>
     * End page: Daily Exposure Report<br/>
     * samples: search Daily Exposure Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link DailyExposureReportFilter}</li>
     * @throws Exception
     */
    @When("^search Daily Exposure Report (\\S+)$")
    public void search_daily_exposure_report(String id)throws Exception{
        DailyExposureReportFilter dailyExposureReportFilter = testDataManager.getDailyExposureReportFilter(id);
        pageManager.dailyExposureReportPage.setDailyExposureReport(dailyExposureReportFilter);
        if(dailyExposureReportFilter.getOutputFormat() != null && !dailyExposureReportFilter.getOutputFormat().isEmpty()){
            pageManager.dailyExposureReportPage.editOutputFormat();
            pageManager.dailyExposureReportPage.setOutputParameters(dailyExposureReportFilter.getOutputFormat());
            pageManager.dailyExposureReportPage.applyChanges();
        }
        if(dailyExposureReportFilter.getSaveAsTemplate() != null){
            pageManager.dailyExposureReportPage.savaAsTemplate(dailyExposureReportFilter);
        }
        testCase.embedTestData(dailyExposureReportFilter);
    }

    /**
     * Description: navigate to Daily Exposure Report template<br/>
     * Start page: anyPage<br/>
     * End page: Daily Exposure Report<br/>
     * samples: navigate to Daily Exposure Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Daily Exposure Report template (\\S+)$")
    public void navigate_to_daily_exposure_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            DailyExposureReportFilter reportFilter = testDataManager.getDailyExposureReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.DAILY_EXPOSURE, templateName);
    }

    /**
     * Description: set Dispute Report filter or save template<br/>
     * Start page: Dispute Report<br/>
     * End page: Dispute Report<br/>
     * samples: search Dispute Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Dispute Report (\\S+)$")
    public void search_dispute_report(String id)throws Exception{
        DisputeReportFilter disputeReportFilter = testDataManager.getDisputeReportFilter(id);
        pageManager.disputeReportPage.setDisputeReport(disputeReportFilter);
        if(disputeReportFilter.getOutputFormat() != null && !disputeReportFilter.getOutputFormat().isEmpty()){
            pageManager.disputeReportPage.editOutputFormat();
            pageManager.disputeReportPage.setOutputParameters(disputeReportFilter.getOutputFormat());
            pageManager.disputeReportPage.applyChanges();
        }
        if(disputeReportFilter.getSaveAsTemplate() != null){
            pageManager.disputeReportPage.saveAsTemplate(disputeReportFilter);
        }
        testCase.embedTestData(disputeReportFilter);
    }

    /**
     * Description: navigate to BRFIRV Report template<br/>
     * Start page: anyPage<br/>
     * End page: BRFIRV Report<br/>
     * samples: navigate to BRFIRV Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Dispute Report template (\\S+)$")
    public void navigate_to_dispute_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            DisputeReportFilter reportFilter = testDataManager.getDisputeReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
    }
        pageManager.reportListPage.navigate(ReportMenu.DISPUTE, templateName);
    }

    /**
     * Description: set BREMEA Report filter or save template<br/>
     * Start page: BREMEA Report<br/>
     * End page: BREMEA Report<br/>
     * samples: search BREMEA Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search BREMEA Report (\\S+)$")
    public void search_bremea_report(String id) {
        throw new PendingException();
    }

    /**
     * Description: navigate to BREMEA Report template<br/>
     * Start page: anyPage<br/>
     * End page: BREMEA Report<br/>
     * samples: navigate to BREMEA Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to BREMEA Report template (\\S+)$")
    public void navigate_to_bremea_report_template(String id) {
        throw new PendingException();
    }

    /**
     * Description: set Dispute History Report filter or save template<br/>
     * Start page: Dispute History Report<br/>
     * End page: Dispute History Report<br/>
     * samples: search Dispute History Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Dispute History Report (\\S+)$")
    public void search_dispute_history_report(String id)throws Exception{
        DisputeHistoryReportFilter disputeHistoryReportFilter = testDataManager.getDisputeHistoryReportFilter(id);
        pageManager.disputeHistoryReportPage.setDisputeHistoryReport(disputeHistoryReportFilter);
        if(disputeHistoryReportFilter.getOutputFormat() != null && !disputeHistoryReportFilter.getOutputFormat().isEmpty()){
            pageManager.disputeHistoryReportPage.editOutputFormat();
            pageManager.disputeHistoryReportPage.setOutputParameters(disputeHistoryReportFilter.getOutputFormat());
            pageManager.disputeHistoryReportPage.applyChanges();
        }
        if(disputeHistoryReportFilter.getSaveAsTemplate() != null){
            pageManager.disputeHistoryReportPage.saveAsTemplate(disputeHistoryReportFilter);
        }
        testCase.embedTestData(disputeHistoryReportFilter);
    }

    /**
     * Description: navigate to Dispute History Report template<br/>
     * Start page: anyPage<br/>
     * End page: Dispute History Report<br/>
     * samples: navigate to Dispute History Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Dispute History Report template (\\S+)$")
    public void navigate_to_dispute_history_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            DisputeHistoryReportFilter reportFilter = testDataManager.getDisputeHistoryReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.DISPUTE_HISTORY, templateName);
    }

    /**
     * Description: set Historical Exposure Report filter or save template<br/>
     * Start page: Historical Exposure Report<br/>
     * End page: Historical Exposure Report<br/>
     * samples: search Historical Exposure Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Historical Exposure Report (\\S+)$")
    public void search_historical_exposure_report(String id)throws Exception{
        HistoricalExposureReportFilter historicalExposureReportFilter = testDataManager.getHistoricalExposureReportFilter(id);
        pageManager.historicalExposureReportPage.setHistoricalExposureReport(historicalExposureReportFilter);
        if(historicalExposureReportFilter.getOutputFormat() != null && !historicalExposureReportFilter.getOutputFormat().isEmpty()){
            pageManager.historicalExposureReportPage.editOutputFormat();
            pageManager.historicalExposureReportPage.setOutputParameters(historicalExposureReportFilter.getOutputFormat());
            pageManager.historicalExposureReportPage.applyChanges();
        }
        if(historicalExposureReportFilter.getSaveAsTemplate() != null){
            pageManager.historicalExposureReportPage.saveAsTemplate(historicalExposureReportFilter);
        }
        testCase.embedTestData(historicalExposureReportFilter);
    }

    /**
     * Description: navigate to Historical Exposure Report template<br/>
     * Start page: anyPage<br/>
     * End page: Historical Exposure Report<br/>
     * samples: navigate to Historical Exposure Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Historical Exposure Report template (\\S+)$")
    public void navigate_to_historical_exposure_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            HistoricalExposureReportFilter reportFilter = testDataManager.getHistoricalExposureReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.HISTORICAL_EXPOSURE, templateName);
    }

    /**
     * Description: set Internal Review Report filter or save template<br/>
     * Start page: Internal Review Report<br/>
     * End page: Internal Review Report<br/>
     * samples: search Internal Review Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Internal Review Report (\\S+)$")
    public void search_internal_review_report(String id)throws Exception{
        InternalReviewReportFilter internalReviewReportFilter = testDataManager.getInternalReviewReportFilter(id);
        pageManager.internalReviewReportPage.setInternalReviewReport(internalReviewReportFilter);
        if(internalReviewReportFilter.getOutputFormat() != null && !internalReviewReportFilter.getOutputFormat().isEmpty()){
            pageManager.internalReviewReportPage.editOutputFormat();
            pageManager.internalReviewReportPage.setOutputParameters(internalReviewReportFilter.getOutputFormat());
            pageManager.internalReviewReportPage.applyChanges();
        }
        if(internalReviewReportFilter.getSaveAsTemplate() != null){
            pageManager.internalReviewReportPage.saveAsTemplate(internalReviewReportFilter);
        }
        testCase.embedTestData(internalReviewReportFilter);
    }

    /**
     * Description: navigate to Internal Review Report template<br/>
     * Start page: anyPage<br/>
     * End page: Internal Review Report<br/>
     * samples: navigate to Internal Review Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Internal Review Report template (\\S+)$")
    public void navigate_to_internal_review_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            InternalReviewReportFilter reportFilter = testDataManager.getInternalReviewReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.INTERNAL_REVIEW, templateName);
    }

    /**
     * Description: set MIS Report filter or save template<br/>
     * Start page: MIS Report<br/>
     * End page: MIS Report<br/>
     * samples: search MIS Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search MIS Report (\\S+)$")
    public void search_mis_report(String id)throws Exception{
        MisReportFilter misReportFilter = testDataManager.getMisReportFilter(id);
        pageManager.misReportPage.setMisReport(misReportFilter);
        if(misReportFilter.getOutputFormat() != null && !misReportFilter.getOutputFormat().isEmpty()){
            pageManager.misReportPage.editOutputFormat();
            pageManager.misReportPage.setOutputParameters(misReportFilter.getOutputFormat());
            pageManager.misReportPage.applyChanges();
        }
        if(misReportFilter.getSaveAsTemplate() != null){
            pageManager.misReportPage.saveAsTemplate(misReportFilter);
        }
        testCase.embedTestData(misReportFilter);
    }

    /**
     * Description: navigate to MIS Report template<br/>
     * Start page: anyPage<br/>
     * End page: MIS Report<br/>
     * samples: navigate to MIS Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to MIS Report template (\\S+)$")
    public void navigate_to_mis_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            MisReportFilter reportFilter = testDataManager.getMisReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.MIS, templateName);
    }

    /**
     * Description: set MtM Comparison Report filter or save template<br/>
     * Start page: MtM Comparison Report<br/>
     * End page: MtM Comparison Report<br/>
     * samples: search MtM Comparison Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search MtM Comparison Report (\\S+)$")
    public void search_mtm_comparison_report(String id)throws Exception{
        MtmComparisonReportFilter mtmComparisonReportFilter = testDataManager.getMtmComparisonReportFilter(id);
        pageManager.mtmComparisonReportPage.setMtmComparisonReport(mtmComparisonReportFilter);
        if(mtmComparisonReportFilter.getOutputFormat() != null && !mtmComparisonReportFilter.getOutputFormat().isEmpty()){
            pageManager.mtmComparisonReportPage.editOutputFormat();
            pageManager.mtmComparisonReportPage.setOutputParameters(mtmComparisonReportFilter.getOutputFormat());
            pageManager.mtmComparisonReportPage.applyChanges();
        }
        if(mtmComparisonReportFilter.getSaveAsTemplate() != null){
            pageManager.mtmComparisonReportPage.saveAsTemplate(mtmComparisonReportFilter);
        }
        testCase.embedTestData(mtmComparisonReportFilter);
    }

    /**
     * Description: navigate to MtM Comparison Report template<br/>
     * Start page: anyPage<br/>
     * End page: MtM Comparison Report<br/>
     * samples: navigate to MtM Comparison Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to MtM Comparison Report template (\\S+)$")
    public void navigate_to_mtm_comparison_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            MtmComparisonReportFilter reportFilter = testDataManager.getMtmComparisonReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.MTM_COMPARISON, templateName);
    }

    /**
     * Description: set Daily Historical Exposure Report filter or save template<br/>
     * Start page: Daily Historical Exposure Report<br/>
     * End page: Daily Historical Exposure Report<br/>
     * samples: search Daily Historical Exposure Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Repo Daily Exposure Report (\\S+)$")
    public void search_repo_daily_exposure_report(String id)throws Exception{
        RepoEtfsblDailyExporesureReportFilter repoEtfsblDailyExporesureReportFilter = testDataManager.getRepoEtfsblDailyExporesureReportFilter(id);
        pageManager.repoEtfsblDailyExposureReportPage.setRepoEtfsblDailyExposureReport(repoEtfsblDailyExporesureReportFilter);
        if(repoEtfsblDailyExporesureReportFilter.getOutputFormat() != null && !repoEtfsblDailyExporesureReportFilter.getOutputFormat().isEmpty()){
            pageManager.repoEtfsblDailyExposureReportPage.editOutputFormat();
            pageManager.repoEtfsblDailyExposureReportPage.setOutputParameters(repoEtfsblDailyExporesureReportFilter.getOutputFormat());
            pageManager.repoEtfsblDailyExposureReportPage.applyChanges();
        }
        if(repoEtfsblDailyExporesureReportFilter.getSaveAsTemplate() != null){
            pageManager.repoEtfsblDailyExposureReportPage.saveAsTemplate(repoEtfsblDailyExporesureReportFilter);
        }
        testCase.embedTestData(repoEtfsblDailyExporesureReportFilter);
    }

    /**
     * Description: navigate to Repo Daily Exposure Report template<br/>
     * Start page: anyPage<br/>
     * End page: Repo Daily Exposure Report<br/>
     * samples: navigate to Repo Daily Exposure Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Repo Daily Exposure Report template (\\S+)$")
    public void navigate_to_repo_daily_exposure_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RepoEtfsblDailyExporesureReportFilter reportFilter = testDataManager.getRepoEtfsblDailyExporesureReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_DAILY_EXPOSURE, templateName);
    }






    /**
     * Description: set Repo Historical Exposure Report filter or save template<br/>
     * Start page: Repo Historical Exposure Report<br/>
     * End page: Repo Historical Exposure Report<br/>
     * samples: search Repo Historical Exposure Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Repo Historical Exposure Report (\\S+)$")
    public void search_repo_historical_exposure_report(String id)throws Exception{
        RepoEtfsblHistoricalExporesureReportFilter repoEtfsblHistoricalExporesureReportFilter = testDataManager.getRepoEtfsblHistoricalExporesureReportFilter(id);
        pageManager.repoEtfsblHistoricalExposureReportPage.setRepoEtfsblHistoricalExposureReport(repoEtfsblHistoricalExporesureReportFilter);
        if(repoEtfsblHistoricalExporesureReportFilter.getOutputFormat() != null && !repoEtfsblHistoricalExporesureReportFilter.getOutputFormat().isEmpty()){
            pageManager.repoEtfsblHistoricalExposureReportPage.editOutputFormat();
            pageManager.repoEtfsblHistoricalExposureReportPage.setOutputParameters(repoEtfsblHistoricalExporesureReportFilter.getOutputFormat());
            pageManager.repoEtfsblHistoricalExposureReportPage.applyChanges();
        }
        if(repoEtfsblHistoricalExporesureReportFilter.getSaveAsTemplate() != null){
            pageManager.repoEtfsblHistoricalExposureReportPage.saveAsTemplate(repoEtfsblHistoricalExporesureReportFilter);
        }
        testCase.embedTestData(repoEtfsblHistoricalExporesureReportFilter);
    }

    /**
     * Description: navigate to Repo Historical Exposure Report template<br/>
     * Start page: anyPage<br/>
     * End page: Repo Historical Exposure Report<br/>
     * samples: navigate to Repo Historical Exposure Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Repo Historical Exposure Report template (\\S+)$")
    public void navigate_to_repo_historical_exposure_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RepoEtfsblHistoricalExporesureReportFilter reportFilter = testDataManager.getRepoEtfsblHistoricalExporesureReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.REPOETFSBL_HISTORICAL_EXPOSURE, templateName);
    }

    /**
     * Description: set TSA Report filter or save template<br/>
     * Start page: TSA Report<br/>
     * End page: TSA Report<br/>
     * samples: search TSA Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search (TSA|Cashflow) Report (\\S+)$")
    public void search_tsa_report(String type, String id)throws Exception{
        TsaReportFilter tsaReportFilter = null;
        switch (type.toLowerCase()){
            case "tsa":
                tsaReportFilter = testDataManager.getTsaReportFilter(id);
                break;
            case "cashflow":
                tsaReportFilter = testDataManager.getCashflowReportFilter(id);
                break;
        }
        pageManager.tsaReportPage.setTsaReport(tsaReportFilter);
        if (tsaReportFilter.getOutputFormat()!=null && !tsaReportFilter.getOutputFormat().isEmpty()){
            pageManager.tsaReportPage.editOutputFormat();
            pageManager.tsaReportPage.setOutputParameters(tsaReportFilter.getOutputFormat());
            pageManager.tsaReportPage.applyChanges();
        }
        if (tsaReportFilter.getSaveAsTemplate()!=null)
            pageManager.tsaReportPage.savaAsTemplate(tsaReportFilter);
        testCase.embedTestData(tsaReportFilter);

    }

    /**
     * Description: navigate to TSA|cashflow Report template<br/>
     * Start page: anyPage<br/>
     * End page: TSA|cashflow Report<br/>
     * samples: navigate to <b>TSA</b> Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to (?:TSA|Cashflow) Report template (\\S+)$")
    public void navigate_to_tsa_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            TsaReportFilter reportFilter = testDataManager.getTsaReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.CASHFLOW, templateName);
    }

    /**
     * Description: set What If Scenario Report filter or save template<br/>
     * Start page: What If Scenario Report<br/>
     * End page: What If Scenario Report<br/>
     * samples: search What If Scenario Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search What If Scenario Report (\\S+)$")
    public void search_what_if_scenario_report(String id)throws Exception{
        WhatIfScenarioReportFilter whatIfScenarioReportFilter = testDataManager.getWhatIfScenarioReportFilter(id);
        pageManager.whatIfScenarioReportPage.setWhatIfScenarioReport(whatIfScenarioReportFilter);
        if(whatIfScenarioReportFilter.getOutputFormat() != null && !whatIfScenarioReportFilter.getOutputFormat().isEmpty()){
            pageManager.whatIfScenarioReportPage.editOutputFormat();
            pageManager.whatIfScenarioReportPage.setOutputParameters(whatIfScenarioReportFilter.getOutputFormat());
            pageManager.whatIfScenarioReportPage.applyChanges();
        }
        if(whatIfScenarioReportFilter.getSaveAsTemplate() != null){
            pageManager.whatIfScenarioReportPage.saveAsTemplate(whatIfScenarioReportFilter);
        }
        testCase.embedTestData(whatIfScenarioReportFilter);
    }

    /**
     * Description: navigate to What If Scenario Report template<br/>
     * Start page: anyPage<br/>
     * End page: What If Scenario Report<br/>
     * samples: navigate to What If Scenario Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to What If Scenario Report template (\\S+)$")
    public void navigate_to_what_if_scenario_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            WhatIfScenarioReportFilter reportFilter = testDataManager.getWhatIfScenarioReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.WHAT_IF_SCENARIO, templateName);
    }



    /**
     * Description: set Optimisation Result Report filter or save template<br/>
     * Start page: Optimisation Result Report<br/>
     * End page: Optimisation Result Report<br/>
     * samples: search Optimisation Result Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Optimisation Result Report (\\S+)$")
    public void search_oprimiasation_result_report(String id)throws Exception{
        OptimisationResultReportFilter optimisationResultReportFilter = testDataManager.getOptimisationResultReportFilter(id);
        pageManager.optimisationResultReportPage.setOptimisationResultReport(optimisationResultReportFilter);
        if(optimisationResultReportFilter.getOutputFormat() != null && !optimisationResultReportFilter.getOutputFormat().isEmpty()){
            pageManager.optimisationResultReportPage.editOutputFormat();
            pageManager.optimisationResultReportPage.setOutputParameters(optimisationResultReportFilter.getOutputFormat());
            pageManager.optimisationResultReportPage.applyChanges();
        }
        if(optimisationResultReportFilter.getSaveAsTemplate() != null){
            pageManager.optimisationResultReportPage.saveAsTemplate(optimisationResultReportFilter);
        }
        testCase.embedTestData(optimisationResultReportFilter);
    }

    /**
     * Description: navigate to Optimisation Result Report template<br/>
     * Start page: anyPage<br/>
     * End page: Optimisation Result Report<br/>
     * samples: navigate to Optimisation Result Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Optimisation Result Report template (\\S+)$")
    public void navigate_to_optimisation_result_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            OptimisationResultReportFilter reportFilter = testDataManager.getOptimisationResultReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.OPTIMISATION_RESULT, templateName);
    }



    /**
     * Description: set Optimisation Rule Report filter or save template<br/>
     * Start page: Optimisation Rule Report<br/>
     * End page: Optimisation Rule Report<br/>
     * samples: search Optimisation Rule Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Optimisation Rule Report (\\S+)$")
    public void search_oprimiasation_rule_report(String id)throws Exception{
        OptimisationRuleReportFilter optimisationRuleReportFilter = testDataManager.getOptimisationRuleReportFilter(id);
        pageManager.optimisationRuleReportPage.setOptimisationRuleReport(optimisationRuleReportFilter);
        if(optimisationRuleReportFilter.getOutputFormat() != null && !optimisationRuleReportFilter.getOutputFormat().isEmpty()){
            pageManager.optimisationRuleReportPage.editOutputFormat();
            pageManager.optimisationRuleReportPage.setOutputParameters(optimisationRuleReportFilter.getOutputFormat());
            pageManager.optimisationRuleReportPage.applyChanges();
        }
        if(optimisationRuleReportFilter.getSaveAsTemplate() != null){
            pageManager.optimisationRuleReportPage.saveAsTemplate(optimisationRuleReportFilter);
        }
        testCase.embedTestData(optimisationRuleReportFilter);
    }

    /**
     * Description: navigate to Optimisation Rule Report template<br/>
     * Start page: anyPage<br/>
     * End page: Optimisation Rule Report<br/>
     * samples: navigate to Optimisation Rule Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Optimisation Rule Report template (\\S+)$")
    public void navigate_to_optimisation_rule_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            OptimisationRuleReportFilter reportFilter = testDataManager.getOptimisationRuleReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.OPTIMISATION_RULE, templateName);
    }




    /**
     * Description: set Manually Overridden Asset Price Report filter or save template<br/>
     * Start page: Manually Overridden Asset Price Report<br/>
     * End page: Manually Overridden Asset Price Report<br/>
     * samples: search Manually Overridden Asset Price Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link ManuallyOverriddenAssetPriceReportFilter}</li>
     * @throws Exception
     */
    @When("^search Manually Overridden Asset Price Report (\\S+)$")
    public void search_manually_overridden_asset_price_report(String id)throws Exception{
        ManuallyOverriddenAssetPriceReportFilter manuallyOverriddenAssetPriceReportFilter = testDataManager.getManuallyOverriddenAssetPriceReportFilter(id);
        pageManager.manuallyOverriddenAssetPriceReportPage.setManuallyOverriddenAssetPriceReport(manuallyOverriddenAssetPriceReportFilter);
        if(manuallyOverriddenAssetPriceReportFilter.getOutputFormat() != null && !manuallyOverriddenAssetPriceReportFilter.getOutputFormat().isEmpty()){
            pageManager.manuallyOverriddenAssetPriceReportPage.editOutputFormat();
            pageManager.manuallyOverriddenAssetPriceReportPage.setOutputParameters(manuallyOverriddenAssetPriceReportFilter.getOutputFormat());
            pageManager.manuallyOverriddenAssetPriceReportPage.applyChanges();
        }
        if(manuallyOverriddenAssetPriceReportFilter.getSaveAsTemplate() != null){
            pageManager.manuallyOverriddenAssetPriceReportPage.savaAsTemplate(manuallyOverriddenAssetPriceReportFilter);
        }
        testCase.embedTestData(manuallyOverriddenAssetPriceReportFilter);
    }

    /**
     * Description: navigate to Manually Overridden Asset Price Report template<br/>
     * Start page: anyPage<br/>
     * End page: Manually Overridden Asset Price Report<br/>
     * samples: navigate to Manually Overridden Asset Price Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Manually Overridden Asset Price Report template (\\S+)$")
    public void navigate_to_manually_overridden_asset_price_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            ManuallyOverriddenAssetPriceReportFilter reportFilter = testDataManager.getManuallyOverriddenAssetPriceReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.MANUALLY_OVERRIDDEN_ASSET_PRICE, templateName);
    }

    /**
     * Description: set Price Age Exceptions Report filter or save template<br/>
     * Start page: Price Age Exceptions Report<br/>
     * End page: Price Age Exceptions Report<br/>
     * samples: search Price Age Exceptions Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link PriceAgeExceptionsReportFilter}</li>
     * @throws Exception
     */
    @When("^search Price Age Exceptions Report (\\S+)$")
    public void search_price_age_exceptions_report(String id)throws Exception{
        PriceAgeExceptionsReportFilter priceAgeExceptionsReportFilter = testDataManager.getPriceAgeExceptionsReportFilter(id);
        pageManager.priceAgeExceptionsReportPage.setPriceAgeExceptionsReport(priceAgeExceptionsReportFilter);
        if(priceAgeExceptionsReportFilter.getOutputFormat() != null && !priceAgeExceptionsReportFilter.getOutputFormat().isEmpty()){
            pageManager.priceAgeExceptionsReportPage.editOutputFormat();
            pageManager.priceAgeExceptionsReportPage.setOutputParameters(priceAgeExceptionsReportFilter.getOutputFormat());
            pageManager.priceAgeExceptionsReportPage.applyChanges();
        }
        if(priceAgeExceptionsReportFilter.getSaveAsTemplate() != null){
            pageManager.priceAgeExceptionsReportPage.savaAsTemplate(priceAgeExceptionsReportFilter);
        }
        testCase.embedTestData(priceAgeExceptionsReportFilter);
    }

    /**
     * Description: navigate to Price Age Exceptions Report template<br/>
     * Start page: anyPage<br/>
     * End page: Price Age Exceptions Report<br/>
     * samples: navigate to Price Age Exceptions Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Price Age Exceptions Report template (\\S+)$")
    public void navigate_to_price_age_exceptions_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            PriceAgeExceptionsReportFilter reportFilter = testDataManager.getPriceAgeExceptionsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.PRICE_AGE_EXCEPTIONS, templateName);
    }

    /**
     * Description: set Price Exceptions Report filter or save template<br/>
     * Start page: Price Exceptions Report<br/>
     * End page: Price Exceptions Report<br/>
     * samples: search Price Exceptions Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link PriceExceptionsReportFilter}</li>
     * @throws Exception
     */
    @When("^search Price Exceptions Report (\\S+)$")
    public void search_price_exceptions_report(String id)throws Exception{
        PriceExceptionsReportFilter priceExceptionsReportFilter = testDataManager.getPriceExceptionsReportFilter(id);
        pageManager.priceExceptionsReportPage.setPriceExceptionsReport(priceExceptionsReportFilter);
        if (priceExceptionsReportFilter.getOutputFormat() != null && !priceExceptionsReportFilter.getOutputFormat().isEmpty()){
            pageManager.priceExceptionsReportPage.editOutputFormat();
            pageManager.priceExceptionsReportPage.setOutputParameters(priceExceptionsReportFilter.getOutputFormat());
            pageManager.priceExceptionsReportPage.applyChanges();
        }
        if (priceExceptionsReportFilter.getSaveAsTemplate() != null){
            pageManager.priceExceptionsReportPage.saveAsTemplate(priceExceptionsReportFilter);
        }
        testCase.embedTestData(priceExceptionsReportFilter);
    }

    /**
     * Description: navigate to Price Exceptions Report template<br/>
     * Start page: anyPage<br/>
     * End page: Price Exceptions Report<br/>
     * samples: navigate to Price Exceptions Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Price Exceptions Report template (\\S+)$")
    public void navigate_to_price_exceptions_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            PriceExceptionsReportFilter reportFilter = testDataManager.getPriceExceptionsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.PRICE_EXCEPTIONS, templateName);
    }

    /**
     * Description: set Price Variance Exceptions Report filter or save template<br/>
     * Start page: Price Variance Exceptions Report<br/>
     * End page: Price Variance Exceptions Report<br/>
     * samples: search Price Variance Exceptions Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link PriceVarianceExceptionsReportFilter}</li>
     * @throws Exception
     */
    @When("^search Price Variance Exceptions Report (\\S+)$")
    public void search_price_variance_exceptions_report(String id)throws Exception{
        PriceVarianceExceptionsReportFilter priceVarianceExceptionsReportFilter = testDataManager.getPriceVarianceExceptionsReportFilter(id);
        pageManager.priceVarianceExceptionsReportPage.setPriceVarianceExceptionsReport(priceVarianceExceptionsReportFilter);
        if(priceVarianceExceptionsReportFilter.getOutputFormat() != null && !priceVarianceExceptionsReportFilter.getOutputFormat().isEmpty()){
            pageManager.priceVarianceExceptionsReportPage.editOutputFormat();
            pageManager.priceVarianceExceptionsReportPage.setOutputParameters(priceVarianceExceptionsReportFilter.getOutputFormat());
            pageManager.priceVarianceExceptionsReportPage.applyChanges();
        }
        if(priceVarianceExceptionsReportFilter.getSaveAsTemplate() != null){
            pageManager.priceVarianceExceptionsReportPage.savaAsTemplate(priceVarianceExceptionsReportFilter);
        }
        testCase.embedTestData(priceVarianceExceptionsReportFilter);
    }

    /**
     * Description: navigate to Price Variance Exceptions Report template<br/>
     * Start page: anyPage<br/>
     * End page: Price Variance Exceptions Report<br/>
     * samples: navigate to Price Variance Exceptions Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Price Variance Exceptions Report template (\\S+)$")
    public void navigate_to_price_variance_exceptions_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            PriceVarianceExceptionsReportFilter reportFilter = testDataManager.getPriceVarianceExceptionsReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.PRICE_VARIANCE_EXCEPTIONS, templateName);
    }

    /**
     * Description: set Reconciliation Output  Report filter or save template<br/>
     * Start page: Reconciliation Output  Report<br/>
     * End page: Reconciliation Output  Report<br/>
     * samples: search Reconciliation Output <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link ReconciliationOutputReportFilter}</li>
     * @throws Exception
     */
    @When("^search Reconciliation Output Report (\\S+)$")
    public void search_reconciliation_output_report(String id)throws Exception{
        ReconciliationOutputReportFilter reconciliationOutputReportFilter = testDataManager.getReconciliationOutputReportFilter(id);
        pageManager.reconciliationOutputReportPage.setReconciliationOutputReport(reconciliationOutputReportFilter);
        if(reconciliationOutputReportFilter.getOutputFormat() != null && !reconciliationOutputReportFilter.getOutputFormat().isEmpty()){
            pageManager.reconciliationOutputReportPage.editOutputFormat();
            pageManager.reconciliationOutputReportPage.setOutputParameters(reconciliationOutputReportFilter.getOutputFormat());
            pageManager.reconciliationOutputReportPage.applyChanges();
        }
        if(reconciliationOutputReportFilter.getSaveAsTemplate() != null){
            pageManager.reconciliationOutputReportPage.saveAsTemplate(reconciliationOutputReportFilter);
        }
        testCase.embedTestData(reconciliationOutputReportFilter);
    }

    /**
     * Description: navigate to Reconciliation Output Report template<br/>
     * Start page: anyPage<br/>
     * End page: Reconciliation Output Report<br/>
     * samples: navigate to Reconciliation Output Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Reconciliation Output Report template (\\S+)$")
    public void navigate_to_reconciliation_output_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            ReconciliationOutputReportFilter reportFilter = testDataManager.getReconciliationOutputReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.RECONCILIATION_OUTPUT, templateName);
    }

    /**
     * Description: set Reconciliation Trades Output  Report filter or save template<br/>
     * Start page: Reconciliation Trades Output  Report<br/>
     * End page: Reconciliation Trades Output  Report<br/>
     * samples: search Reconciliation Trades Output <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link ReconciliationTradesOutputReportFilter}</li>
     * @throws Exception
     */
    @When("^search Reconciliation Trades Output Report (\\S+)$")
    public void search_reconciliation_trades_output_report(String id)throws Exception{
        ReconciliationTradesOutputReportFilter reconciliationTradesOutputReportFilter = testDataManager.getReconciliationTradesOutputReportFilter(id);
        pageManager.reconciliationTradesOutputReportPage.setReconciliationTradesOutputReport(reconciliationTradesOutputReportFilter);
        if(reconciliationTradesOutputReportFilter.getOutputFormat() != null && !reconciliationTradesOutputReportFilter.getOutputFormat().isEmpty()){
            pageManager.reconciliationTradesOutputReportPage.editOutputFormat();
            pageManager.reconciliationTradesOutputReportPage.setOutputParameters(reconciliationTradesOutputReportFilter.getOutputFormat());
            pageManager.reconciliationTradesOutputReportPage.applyChanges();
        }
        if(reconciliationTradesOutputReportFilter.getSaveAsTemplate() != null){
            pageManager.reconciliationTradesOutputReportPage.saveAsTemplate(reconciliationTradesOutputReportFilter);
        }
        testCase.embedTestData(reconciliationTradesOutputReportFilter);
    }

    /**
     * Description: navigate to Reconciliation Trades Output Report template<br/>
     * Start page: anyPage<br/>
     * End page: Reconciliation Trades Output Report<br/>
     * samples: navigate to Reconciliation Trades Output Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Reconciliation Trades Output Report template (\\S+)$")
    public void navigate_to_reconciliation_trades_output_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            ReconciliationTradesOutputReportFilter reportFilter = testDataManager.getReconciliationTradesOutputReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.RECONCILIATION_TRADES_OUTPUT, templateName);
    }







    /**
     * Description: set Stp Dashboard Report filter or save template<br/>
     * Start page: Stp Dashboard Report<br/>
     * End page: Stp Dashboard Report<br/>
     * samples: search Stp Dashboard Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Stp Dashboard Report (\\S+)$")
    public void search_stp_dashboard_report(String id)throws Exception{
        StpDashboardReportFilter stpDashboardReportFilter = testDataManager.getStpDashboardReportFilter(id);
        pageManager.stpDashboardReportPage.setStpDashboardReport(stpDashboardReportFilter);
        if(stpDashboardReportFilter.getOutputFormat() != null && !stpDashboardReportFilter.getOutputFormat().isEmpty()){
            pageManager.stpDashboardReportPage.editOutputFormat();
            pageManager.stpDashboardReportPage.setOutputParameters(stpDashboardReportFilter.getOutputFormat());
            pageManager.stpDashboardReportPage.applyChanges();
        }
        if(stpDashboardReportFilter.getSaveAsTemplate() != null){
            pageManager.stpDashboardReportPage.saveAsTemplate(stpDashboardReportFilter);
        }
        testCase.embedTestData(stpDashboardReportFilter);
    }

    /**
     * Description: navigate to Stp Dashboard Report template<br/>
     * Start page: anyPage<br/>
     * End page: Stp Dashboard Report<br/>
     * samples: navigate to Stp Dashboard Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Stp Dashboard Report template (\\S+)$")
    public void navigate_to_stp_dashboard_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            StpDashboardReportFilter reportFilter = testDataManager.getStpDashboardReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.STP_DASHBOARD, templateName);
    }

    /**
     * Description: set Role Approval Report filter or save template<br/>
     * Start page: Role Approval Report<br/>
     * End page: Role Approval Report<br/>
     * samples: search Role Approval Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search Role Approval Report (\\S+)$")
    public void search_role_approval_report(String id)throws Exception{
        RoleApprovalReportFilter roleApprovalReportFilter = testDataManager.getRoleApprovalReportFilter(id);
        pageManager.roleApprovalReportPage.setAssetHoldingsAndValuationReport(roleApprovalReportFilter);
        if(roleApprovalReportFilter.getOutputFormat() != null && !roleApprovalReportFilter.getOutputFormat().isEmpty()){
            pageManager.roleApprovalReportPage.editOutputFormat();
            pageManager.roleApprovalReportPage.setOutputParameters(roleApprovalReportFilter.getOutputFormat());
            pageManager.roleApprovalReportPage.applyChanges();
        }
        if(roleApprovalReportFilter.getSaveAsTemplate() != null){
            pageManager.roleApprovalReportPage.saveAsTemplate(roleApprovalReportFilter);
        }
        testCase.embedTestData(roleApprovalReportFilter);
    }

    /**
     * Description: navigate to Role Approval Report template<br/>
     * Start page: anyPage<br/>
     * End page: Role Approval Report<br/>
     * samples: navigate to Role Approval Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to Role Approval Report template (\\S+)$")
    public void navigate_to_role_approval_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            RoleApprovalReportFilter reportFilter = testDataManager.getRoleApprovalReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.ROLE_APPROVAL, templateName);
    }

    /**
     * Description: set User Profile Report filter or save template<br/>
     * Start page: User Profile Report<br/>
     * End page: User Profile Report<br/>
     * samples: search User Profile Report <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search User Profile Report (\\S+)$")
    public void search_user_profile_report(String id)throws Exception{
        UserProfileReportFilter userProfileReportFilter = testDataManager.getUserProfileReportFilter(id);
        pageManager.userProfileReportPage.setUserProfileRepor(userProfileReportFilter);
        if(userProfileReportFilter.getOutputFormat() != null && !userProfileReportFilter.getOutputFormat().isEmpty()){
            pageManager.userProfileReportPage.editOutputFormat();
            pageManager.userProfileReportPage.setOutputParameters(userProfileReportFilter.getOutputFormat());
            pageManager.userProfileReportPage.applyChanges();
        }
        if(userProfileReportFilter.getSaveAsTemplate() != null){
            pageManager.userProfileReportPage.saveAsTemplate(userProfileReportFilter);
        }
        testCase.embedTestData(userProfileReportFilter);
    }

    /**
     * Description: navigate to User Profile Report template<br/>
     * Start page: anyPage<br/>
     * End page: User Profile Report<br/>
     * samples: navigate to User Profile Report template <b>my.template</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @see StringType
     * @throws Exception
     */
    @When("^navigate to User Profile Report template (\\S+)$")
    public void navigate_to_user_profile_report_template(String id)throws Exception{
        StringType variable = testDataManager.getVariable(id);
        String templateName;
        if(variable == null){
            UserProfileReportFilter reportFilter = testDataManager.getUserProfileReportFilter(id);
            templateName = reportFilter.getSaveAsTemplate().getRealValue();
            testCase.embedTestData(reportFilter);
        }else{
            templateName = variable.getRealValue();
            testCase.embedTestData(variable);
        }
        pageManager.reportListPage.navigate(ReportMenu.USER_PROFILE, templateName);
    }

    /**
     * Description: click run each report format button in report search page<br/>
     * Start page:current report page<br/>
     * End page: current report page<br/>
     * samples: run report to <b>my.file.location</b> as <b>Excel Worksheet</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @param format can be one of following type:<br /><li>Excel Worksheet</li><li>CSV</li><li>XML</li><li>GUI</li>
     * @see StringType
     * @throws Exception
     */
    @When("^run report to (\\S+)? ?as (Excel Worksheet|CSV|PDF|XML|GUI)$")
    public void run_report_to(String id, String format)throws Exception{
        if(!format.equalsIgnoreCase("gui")) {
            StringType path = testDataManager.getVariable(id);
            if(format.equalsIgnoreCase("excel worksheet")) {
                pageManager.reportListPage.runToExcelWorksheet(path);
            }else if(format.equalsIgnoreCase("csv")) {
                pageManager.reportListPage.runToCsv(path);
            }else if(format.equalsIgnoreCase("pdf")){
                pageManager.reportListPage.runToPdf(path);
            }else if(format.equalsIgnoreCase("xml")){
                pageManager.reportListPage.runToXml(path);
            }
            testCase.embedTestData(path);
        }else{
            long waitTime = PropHelper.TIMEOUT_INTERVAL * 1000;
            SystemPreference systemPreference = testDataManager.getSystemPreference("system.preference.default");
            testCase.embedTestData(systemPreference);
            if(systemPreference.getApplicationPreferences().getMaxWaitTimeForGui() != null
                    && !systemPreference.getApplicationPreferences().getMaxWaitTimeForGui().getRealValue().isEmpty())
                waitTime = Long.parseLong(systemPreference.getApplicationPreferences().getMaxWaitTimeForGui().getRealValue()) * 60 * 1000;
            pageManager.reportListPage.runReport(waitTime);
        }
    }

    /**
     * Description: click run each report format button in GUI report page<br/>
     * Start page: GUI report page<br/>
     * End page: GUI report page<br/>
     * samples: run GUI report to <b>my.file.location</b> as <b>Excel Raw</b><br/>
     * @param id can be one of following type:<br /><li>variable</li>
     * @param format can be one of following value:<br /><li>Excel Raw</li><li>CSV</li><li>PDF</li><li>XML</li>
     * @see StringType
     * @throws Exception
     */
    @When("^run GUI report to (\\S+) as (Excel Raw|CSV|PDF|XML)$")
    public void run_gui_report_to(String id, String format)throws Exception{
        StringType path = testDataManager.getVariable(id);
        switch (format.toLowerCase()){
            case "pdf" :
                pageManager.reportGuiPage.runToPdf(path);
                break;
            case "excel raw" :
                throw new PendingException();
            case "csv" :
                throw new PendingException();
            case "xml" :
                throw new PendingException();
        }
        testCase.embedTestData(path);
    }

    /**
     * Description: click save report button in GUI report page<br/>
     * Start page: GUI report page<br/>
     * End page: saved report page<br/>
     * samples: save report result to Colline <b>my.saved.report</b><br/>
     * @param id can be one of following type:<br /><li>{@link SavedReport}</li>
     * @throws Exception
     */
    @When("^save report result to Colline (\\S+)$")
    public void save_report_result_to_colline(String id)throws Exception{
        SavedReport savedReport = testDataManager.getSavedReport(id);
        pageManager.reportGuiPage.saveResult();
        pageManager.saveReportsPage.expandSavedReport(savedReport);
        pageManager.saveReportsPage.getSavedReportId(savedReport);
        testCase.embedTestData(savedReport);
    }

    /**
     * Description: export saved report<br/>
     * Start page: saved report page<br/>
     * End page: saved report page<br/>
     * samples: export saved report <b>my.saved.report</b> result to <b>my.file.location</b> as <b>Excel</b><br/>
     * @param id can be one of following type:<br /><li>{@link SavedReport}</li>
     * @param pathId can be one of following type:<br /><li>variable</li>
     * @param format can be one of following value:<br /><li>Excel</li><li>CSV</li><li>XML</li><li>PDF</li>
     * @see StringType
     * @throws Exception
     */
    @When("^export saved report (\\S+) result to (\\S+) as (Excel|CSV|XML|PDF)$")
    public void run_saved_report_result_to_as(String id, String pathId, String format)throws Exception{
        SavedReport savedReport = testDataManager.getSavedReport(id);
        StringType path = testDataManager.getVariable(pathId);
        pageManager.saveReportsPage.tickExportReport(savedReport);
        switch(format.toLowerCase()){
            case "excel" :
                pageManager.saveReportsPage.exportMarketReportsToExcel(path);
                break;
            case "csv" :
                pageManager.saveReportsPage.exportMarketReportsToCsv(path);
                break;
            case "xml" :
                pageManager.saveReportsPage.exportMarketReportsToXml(path);
                break;
            case "pdf" :
                pageManager.saveReportsPage.exportMarketReportsToPdf(path);
                break;
        }
        testCase.embedTestData(path);
        testCase.embedTestData(savedReport);
    }

    /**
     * Description: delete marked reports<br/>
     * Start page: saved report page<br/>
     * End page: saved report page<br/>
     * samples: delete marked reports <b>my.marked.report</b><br/>
     * @param id can be one of following type:<br /><li>{@link SavedReport}</li>
     * @throws Exception
     */
    @When("^delete marked reports (\\S+)$")
    public void delete_marked_reports(String id) throws Exception{
        SavedReport savedReport = testDataManager.getSavedReport(id);
        pageManager.saveReportsPage.tickDeleteReport(savedReport);
        pageManager.saveReportsPage.deleteMarketReports(savedReport);
        testCase.embedTestData(savedReport);
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^create report batch (\\S+)$")
    public void create_report_batch(String id) {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^edit report batch (\\S+)$")
    public void edit_report_batch(String id) {
        throw new PendingException();
    }

    /**
     * Description: navigate to asset definition page<br/>
     * Start page: anyPage<br/>
     * End page: asset definition page<br/>
     * samples: navigate to asset definition page<br/>
     * @throws Exception
     */
    @When("^navigate to asset definition page$")
    public void navigate_to_asset_definition_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.ASSET_DEFINITION);
    }

    /**
     * Description: navigate to holiday centre page<br/>
     * Start page: anyPage<br/>
     * End Page: holiday centre page<br/>
     * samples:navigate to holiday centre page<br/>
     * @throws Exception
     */
    @When("^navigate to holiday centre page$")
    public void navigate_to_holiday_centre_page() throws Exception {
        pageManager.welcomePage.navigate(Menu.HOLIDAY_CENTRE);
    }

    /**
     * Description: navigate to interest rates page<br/>
     * Start page: anyPage<br/>
     * End page: interest rates page<br/>
     * samples: navigate to interest rates page<br/>
     * @throws Exception
     */
    @When("^navigate to interest rates page$")
    public void navigate_to_interest_rates_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.INTEREST_RATES);
    }

    /**
     * Description: navigate to collateral static data settlement data page<br/>
     * Start page: anyPage<br/>
     * End page: collateral static data settlement data page<br/>
     * samples: navigate to collateral static data settlement data page<br/>
     * @throws Exception
     */
    @When("^navigate to collateral static data settlement data page$")
    public void navigate_to_collateral_static_data_settlement_data_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.SETTLEMENT_DATA);
    }

    /**
     * Description: edit collateral static data settlement data<br/>
     * Start page: SSI search result page<br/>
     * End page: SSI search result page<br/>
     * samples: edit collateral static data settlement data <b>my.old.settlement.data</b> to <b>my.new.settlememt.data</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link SettlementDataSearchResult}</li>
     * @param newId can be one of following type:<br /><li>{@link SettlementData}</li>
     * @throws Exception
     */
    @When("^edit collateral static data settlement data (\\S+) to (\\S+)$")
    public void edit_collateral_static_data_settlement_data_to(String oriId, String newId)throws Exception{
        SettlementDataSearchResult oriSettlementData = testDataManager.getSettlementDataSearchResult(oriId);
        SettlementData newSettlementData = testDataManager.getSettlementData(newId);
        pageManager.settlementDataPage.enterSettlementData(oriSettlementData);
        pageManager.settlementDataPage.addSettelmentData(newSettlementData);
        testCase.embedTestData(oriSettlementData);
        testCase.embedTestData(newSettlementData);

    }

    /**
     * Description: edit collateral static data settlement data<br/>
     * Start page: SSI search result page<br/>
     * End page: SSI search result page<br/>
     * samples: edit collateral static data settlement data <b>my.old.settlement.data</b> to <b>my.new.settlememt.data</b><br/>
     * @param id can be one of following type:<br /><li>{@link SettlementDataSearchResult}</li>
     * @throws Exception
     */
    @When("^approve collateral static data settlement data (\\S+)$")
    public void approve_collateral_static_data_settlement_data(String id)throws Exception{
        SettlementDataSearchResult oriSettlementData = testDataManager.getSettlementDataSearchResult(id);
        pageManager.settlementDataPage.enterSettlementData(oriSettlementData);
        pageManager.settlementDataPage.approveSettlementData();
        testCase.embedTestData(oriSettlementData);
    }

    /**
     * Description: click edit SSI button<br/>
     * Start page: SSI search result page<br/>
     * End page: SSI search result page<br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^click edit SSI button (\\S+)$")
    public void click_edit_SSI_button() {
        throw new PendingException();
    }

    /**
     * Description: export settlement data result<br/>
     * Start page: SSI search result page<br/>
     * End page: SSI search result page<br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^export settlement data result in (CSV|Excel|XML|PDF) format$")
    public void export_settlement_data_result_in_format() {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^view asset detail (\\S+)$")
    public void view_asset_detail() {
        throw new PendingException();
    }

    /**
     * Description: <br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @throws Exception
     */
    @When("^sort settlement data search result by (\\S+)$")
    public void sort_settlement_data_search_result_by() {
        throw new PendingException();
    }

    /**
     * Description: navigate to collateral static data umbrella rule page<br/>
     * Start page: anyPage<br/>
     * End page: umbrella rule page<br/>
     * samples: navigate to collateral static data umbrella rule page<br/>
     * @throws Exception
     */
    @When("^navigate to collateral static data umbrella rule page$")
    public void navigate_to_collateral_static_data_umbrella_rule_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.UMBRELLA_RULES);
    }

    /**
     * Description: edit umbrella rule<br/>
     * Start page: umbrella rule page<br/>
     * End page: umbrella rule page<br/>
     * samples: edit collateral static data umbrella rule <b>my.old.umbrella.rule</b> to <b>my.new.umbrella.rule</b><br/>
     * @param oriUmbrellaId  can be one of following type:<br /><li>{@link }</li>
     * @param newUmbrellaId  can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^edit collateral static data umbrella rules? (\\S+) to (\\S+)$")
    public void edit_collateral_static_data_umbrella_rules_to(String oriUmbrellaId,String newUmbrellaId)throws Exception{
        UmbrellaRule oriUmbrellaRule = testDataManager.getUmbrellaRule(oriUmbrellaId);
        UmbrellaRule newUmbrellaRule = testDataManager.getUmbrellaRule(newUmbrellaId);
        pageManager.umbrellaRulesPage.editUmbrellaRule(oriUmbrellaRule);
        pageManager.umbrellaRulesPage.inputUmbrellaRule(newUmbrellaRule);
        pageManager.umbrellaRulesPage.saveUmbrellaRule();
        testCase.embedTestData(oriUmbrellaRule);
        testCase.embedTestData(newUmbrellaRule);
    }

    /**
     * Description: search umbrella rule<br/>
     * Start page: umbrella rule page<br/>
     * End page: umbrella rule page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search collateral static data umbrella rule(\\S+)$")
    public void search_collateral_static_data_umbrella_rule(String id)throws Exception{
        UmbrellaRule umbrellaRule = testDataManager.getUmbrellaRule(id);
        pageManager.umbrellaRulesPage.searchUmbrellaRule(umbrellaRule);
        testCase.embedTestData(umbrellaRule);
    }

    /**
     * Description: navigate to TSA or cashflow rules page<br/>
     * Start page: anyPage<br/>
     * End page: TSA or cashflow rule page<br/>
     * samples: navigate to <b>cashflow</b> rules page<br/>
     * @throws Exception
     */
    @When("^navigate to (?:TSA|cashflow) rules page$")
    public void navigate_to_TSA_rules_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.CASHFLOW_RULES);
    }

    /**
     * Description: edit TSA|cashflow rule<br/>
     * Start page: TSA|cashflow rule page<br/>
     * End page: TSA|cashflow rule definition page<br/>
     * samples: edit <b>TSA</b> rule <b>my.old.tsaRule</b> to <b>my.new.tsaRule</b><br/>
     * @param type can be one of following value:<br /><li>TSA</li><li>cashflow</li>
     * @param oldId can be one of following type:<br /><li>{@link TsaRule}</li>
     * @param newId can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @When("^edit (TSA|cashflow) rule (\\S+) to (\\S+)$")
    public void edit_TSA_rule_to(String type, String oldId, String newId)throws Exception{
        TsaRule oldTsaRule = null;
        TsaRule newTsaRule = null;
        switch (type.toLowerCase()){
            case "tsa":
                oldTsaRule = testDataManager.getTsaRule(oldId);
                newTsaRule = testDataManager.getTsaRule(newId);
                break;
            case "cashflow":
                oldTsaRule = testDataManager.getCashflowRule(oldId);
                newTsaRule = testDataManager.getCashflowRule(newId);
                break;
        }
        testCase.embedTestData(oldTsaRule);
        testCase.embedTestData(newTsaRule);
        if (oldTsaRule != null && (oldTsaRule.getTsaRuleName() != null||oldTsaRule.getCashflowRuleName()!=null)) {
            pageManager.tsaRulesPage.editTsaRule(oldTsaRule);
            pageManager.tsaRulesPage.inputTsaRule(newTsaRule);
            pageManager.tsaRulesPage.saveTsaRule(newTsaRule);
        }

    }

    /**
     * Description: delete TSA|cashflow rule<br/>
     * Start page: TSA|cashflow rule page<br/>
     * End page: TSA|cashflow rule page<br/>
     * samples: delete <b>cashflow</b> rule <b>my.tsaRule</b><br/>
     * @param type can be one of following value:<br /><li>TSA</li><li>cashflow</li>
     * @param ids can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @When("^delete (TSA|cashflow) rules? (\\S+)$")
    public void delete_TSA_rule(String type, List<String> ids)throws Exception{
        TsaRule tsaRule = null;
        for (String id : ids) {
            switch (type.toLowerCase()){
                case "tsa":
                    tsaRule = testDataManager.getTsaRule(id);
                    break;
                case "cashflow":
                    tsaRule = testDataManager.getCashflowRule(id);
                    break;
            }
            testCase.embedTestData(tsaRule);
            if (tsaRule != null && (tsaRule.getTsaRuleName() != null||tsaRule.getCashflowRuleName()!=null)) {
                pageManager.tsaRulesPage.deleteTsaRule(tsaRule);
            }
        }
    }

    /**
     * Description: search TSA|cashflow rule<br/>
     * Start page: TSA|cashflow rule page<br/>
     * End page: TSA|cashflow rule page<br/>
     * samples: search <b>TSA</b> rule <b>my.tsaRule</b><br/>
     * @param id can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @When("^search (TSA|cashflow) rule (\\S+)$")
    public void search_TSA_rule(String type, String id)throws Exception{
        TsaRule tsaRule = null;
        switch (type.toLowerCase()){
            case "tsa":
                tsaRule = testDataManager.getTsaRule(id);
                break;
            case "cashflow":
                tsaRule = testDataManager.getCashflowRule(id);
                break;
        }
        testCase.embedTestData(tsaRule);
        pageManager.tsaRulesPage.setTsaRuleSearchCondition(tsaRule);
        pageManager.tsaRulesPage.searchTsaRule();
    }

    /**
     * Description: navigate to eligibility rules template page<br/>
     * Start page: eligibility rules template page<br/>
     * End page: eligibility rules template page<br/>
     * samples: navigate to eligibility rules template page<br/>
     * @throws Exception
     */
    @When("^navigate to eligibility rules template page$")
    public void navigate_to_eligibility_rules_template_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.ELIGIBILITY_RULES_TEMPLATE);
    }

    /**
     * Description: edit eligibility rules template<br/>
     * Start page: eligibility rules template page<br/>
     * End page: eligibility rules template page<br/>
     * samples: edit eligibility rules template <b>templateSearchResult</b> to <b>newTemplate</b><br/>
     * @param oriId can be one of following type:<br /><li>{@link EligibilityRulesTemplateSearchResult}</li>
     * @param newId can be one of following type:<br /><li>{@link EligibilityRulesTemplate}</li>
     * @throws Exception
     */
    @When("^edit eligibility rules template (\\S+) to (\\S+)$")
    public void edit_eligibility_rules_template_to(String oriId, String newId)throws Exception{
        EligibilityRulesTemplate oriTemplate = testDataManager.getEligibilityRulesTemplate(oriId);
        EligibilityRulesTemplate newTemplate = testDataManager.getEligibilityRulesTemplate(newId);
        pageManager.eligibilityRulesTemplatePage.changeEligibilityRulesTemplate(oriTemplate,newTemplate);
        testCase.embedTestData(oriTemplate);
        testCase.embedTestData(newTemplate);
    }

    /**
     * Description: delete eligibility rules template<br/>
     * Start page: eligibility rules template page<br/>
     * End page: eligibility rules template page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^delete eligibility rules template (\\S+)$")
    public void delete_eligibility_rules_template(String id) {
        throw new PendingException();
    }

    /**
     * Description: search eligibility rules template<br/>
     * Start page: eligibility rules template page<br/>
     * End page: eligibility rules template page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^search eligibility rules template (\\S+)$")
    public void search_eligibility_rules_template(String id)throws Exception{
        EligibilityRulesTemplateSearch eligibilityRulesTemplateSearch = testDataManager.getEligibilityRulesTemplateSearch(id);
        pageManager.eligibilityRulesTemplatePage.searchEligibilityRulesTemplateByTemplateName(eligibilityRulesTemplateSearch);
        testCase.embedTestData(eligibilityRulesTemplateSearch);
    }

    /**
     * Description: click approve|reject|exit button in view change page<br/>
     * Start page: change page<br/>
     * End page: eligibility rules template page<br/>
     * samples: <br/>
     * @param status can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^eligibility rules template - change (approve|reject|exit)$")
    public void eligibility_rules_template_change(String status) {
        throw new PendingException();
    }

    /**
     * Description: navigate to collateral static data UDF Values page<br/>
     * Start page: anyPage<br/>
     * End page: UDF Values page<br/>
     * samples: navigate to collateral static data UDF Values page<br/>
     * @throws Exception
     */
    @When("^navigate to collateral static data UDF Values page$")
    public void navigate_to_collateral_static_data_UDF_values_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.UDF_VALUES);
    }

    /**
     * Description: add UDF value<br/>
     * Start page: UDF Values page<br/>
     * End page: UDF Values page<br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^add collateral static data UDF (\\S+)$")
    public void add_collateral_static_data_UDF(String id)throws Exception{
        StaticData udfValue = testDataManager.getUdfValue(id);
        pageManager.udfValuesPage.addStaticData(udfValue);
        testCase.embedTestData(udfValue);
    }

    /**
     * Description: edit UDF value<br/>
     * Start page: UDF Values page<br/>
     * End page: UDF Values page<br/>
     * samples: <br/>
     * @param oriUdfId can be one of following type:<br /><li>{@link StaticData}</li>
     * @param newUdfId can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @When("^edit collateral static data UDF (\\S+) to (\\S+)$")
    public void edit_collateral_static_data_UDF_to(String oriUdfId, String newUdfId)throws Exception{
        StaticData oriUdfValue = testDataManager.getUdfValue(oriUdfId);
        StaticData newUdfValue = testDataManager.getUdfValue(newUdfId);
        pageManager.udfValuesPage.openStaticDataType(oriUdfValue);
        pageManager.udfValuesPage.openStaticDataRecord(oriUdfValue);
        pageManager.udfValuesPage.editStaticData(newUdfValue);
        testCase.embedTestData(oriUdfValue);
        testCase.embedTestData(newUdfValue);

    }

    /**
     * Description: download help file<br/>
     * Start page: <br/>
     * End page: <br/>
     * samples: <br/>
     * @param id can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @When("^download help file (\\S+)$")
    public void download_help_file(String id) {
        throw new PendingException();
    }

    /**
     * Description: navigate to about this software<br/>
     * Start page: anyPage<br/>
     * End page: about this software page<br/>
     * samples: navigate to about this software<br/>
     * @throws Exception
     */
    @When("^navigate to about this software$")
    public void navigate_to_about_this_software() throws Exception{
        pageManager.welcomePage.navigate(Menu.ABOUT_SOFTWARE);
    }

    /**
     * Description: navigate to user&role administration page<br/>
     * Start page: anyPage<br/>
     * End page: user&role administration page<br/>
     * samples: navigate to user&role administration page<br/>
     * @throws Exception
     */
    @When("^navigate to user&role administration page$")
    public void navigate_to_user_and_role_administration_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.USER_ROLE_ADMINISTRATION);
    }

    /**
     * Description: swicth to different page in list<br/>
     * Start page: user&role administration page<br/>
     * End page: user&role administration page<br/>
     * samples: user&role administration - switch to administer <b>roles</b> page<br/>
     * @param page can be one of following type:<br /><li>roles</li><li>approval profiles</li>
     * @throws Exception
     */
    @When("^user&role administration - switch to administer (roles|approval profiles) page$")
    public void user_and_role_administration_switch_page_in_user_role_administration_page(String page) throws Exception{
        switch (page.toLowerCase()){
            case "roles" :
                pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerRolesPage();
                break;
            case "approval profiles" :
                pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerApprovalProfilesPage();
                break;
        }
    }

    /**
     * Description: select view (active|all|inactive) users<br/>
     * Start page: user&role administration page<br/>
     * End page: user&role administration page<br/>
     * samples: view (active|all|inactive) users<br/>
     * @param type can be one of following value:<br /><li>active</li><li>all</li><li>inactive</li>
     * @throws Exception
     */
    @When("^view (active|all|inactive) users$")
    public void view_users(String type)throws Exception{
        User user = new User();
        switch (type.toLowerCase()){
            case "active" :
                user.setViewUser(UserViewType.VIEW_ACTIVE_USERS);
                break;
            case "all" :
                user.setViewUser(UserViewType.VIEW_ALL_USERS);
                break;
            case "inactive" :
                user.setViewUser(UserViewType.VIEW_INACTIVE_USERS);
        }
        pageManager.userAndRoleAdministrationNavigationPannel.viewUser(user);
    }

    /**
     * Description: click disable|enable users access button in user list<br/>
     * Start page: user&role administration page<br/>
     * End page: user&role administration page<br/>
     * samples: <b>enable</b> users access <b>my.user</b><br/>
     * @param id can be one of following type:<br /><li>{@link User}</li>
     * @throws Exception
     */
    @When("^(?:disable|enable) users access (\\S+)$")
    public void disable_or_enable_users_access(String id)throws Exception{
        User user = testDataManager.getUser(id);
        pageManager.userAndRoleAdministrationNavigationPannel.changeUserAccess(user);
        testCase.embedTestData(user);
    }

    /**
     * Description: set administer approval profiles<br/>
     * Start page: administer approval profiles page<br/>
     * End page: administer approval profiles page<br/>
     * samples: set administer approval profiles <b>my.profile</b><br/>
     * @param id can be one of following type:<br /><li>{@link ApprovalProfile}</li>
     * @throws Exception
     */
    @When("^set administer approval profiles (\\S+)$")
    public void set_administer_approval_profiles(String id) throws Exception{
        ApprovalProfile approvalProfile = testDataManager.getApprovalProfile(id);
        pageManager.administerApprovalProfilesPage.setApprovalProfile(approvalProfile);
        pageManager.administerApprovalProfilesPage.submit();
        testCase.embedTestData(approvalProfile);
    }

    /**
     * Description: navigate to system preferences page<br/>
     * Start page: anyPage<br/>
     * End page: system preferences page<br/>
     * samples: navigate to system preferences page<br/>
     * @throws Exception
     */
    @When("^navigate to system preferences page$")
    public void navigate_to_system_preferences_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
    }

    /**
     * Description: navigate to task scheduler page<br/>
     * Start page: anyPage<br/>
     * End page: task scheduler page<br/>
     * samples: navigate to task scheduler page<br/>
     * @throws Exception
     */
    @When("^navigate to task scheduler page$")
    public void navigate_to_task_scheduler_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.TASK_SCHEDULER);
    }

    /**
     * Description: Copy Agreement Rating feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy Agreement Rating feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAgreementRating}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy Agreement Rating feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_agreement_rating_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAgreementRating(id));
            testCase.embedTestData(testDataManager.getFeedAgreementRating(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy asset pricing feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy asset pricing feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAssetPricing}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy Asset Pricing feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_asset_pricing_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAssetPricing(id));
            testCase.embedTestData(testDataManager.getFeedAssetPricing(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy MTM feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy MTM feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedMtM}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy MTM feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_mtm_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedMtM(id));
            testCase.embedTestData(testDataManager.getFeedMtM(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy NAV feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy NAV feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedNAV}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy NAV feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_nav_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedNAV(id));
            testCase.embedTestData(testDataManager.getFeedNAV(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy Asset Rating feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy Assets Rating feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAssetRating}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy Asset Rating feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_asset_rating_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAssetRating(id));
            testCase.embedTestData(testDataManager.getFeedAssetRating(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy External IA feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy External IA feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedExternalIA}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy External IA feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_external_ia_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedExternalIA(id));
            testCase.embedTestData(testDataManager.getFeedExternalIA(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy FX Rate feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy FX rate feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedFxRate}</li>
     * @param taskSchedulerId can be following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy FX rate feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_fx_rate_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedFxRate(id));
            testCase.embedTestData(testDataManager.getFeedFxRate(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy portfolio TSA feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy portfolio TSA feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedPortfolioTSA}</li>
     * @param taskSchedulerId can be following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy portfolio TSA feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_portfolio_tsa_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            FeedPortfolioTSA feedPortfolioTSA = testDataManager.getFeedPortfolioTSA(id);
            list.add(feedPortfolioTSA);
            testCase.embedTestData(feedPortfolioTSA);
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy repo settlement feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy repo settlement feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedRepoSettlement}</li>
     * @param taskSchedulerId can be following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^copy repo settlement feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_repo_settlement_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedRepoSettlement(id));
            testCase.embedTestData(testDataManager.getFeedRepoSettlement(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy security template feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy security template feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedSecurityTemplate}</li>
     * @param taskSchedulerId can be following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy security template feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_security_template_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedSecurityTemplate(id));
            testCase.embedTestData(testDataManager.getFeedSecurityTemplate(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy agreement feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy agreement feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li><xlsx/li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAgreement}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy agreement feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_feed_agreement_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAgreement(id));
            testCase.embedTestData(testDataManager.getFeedAgreement(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy interest rate feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy interest rate feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li><li>csv</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedInterestRate}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy interest rate feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_interest_rate_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedInterestRate(id));
            testCase.embedTestData(testDataManager.getFeedInterestRate(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), fileName);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Copy agreement UDF feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy agreement UDF feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAgreementUDF}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy agreement UDF feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_agreement_udf_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAgreementUDF(id));
            testCase.embedTestData(testDataManager.getFeedAgreementUDF(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(serverFilePath);
    }

    /**
     * Description: Copy static data feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy static data feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedStaticData}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy static data feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_static_data_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedStaticData(id));
            testCase.embedTestData(testDataManager.getFeedStaticData(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(serverFilePath);
    }

    /**
     * Description: Copy organisation feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy organisation feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedOrganisation}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy organisation feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_organisation_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedOrganisation(id));
            testCase.embedTestData(testDataManager.getFeedOrganisation(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy counterpaty amount feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy counterpaty amount feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedCounterpartyAmount}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy counterparty amount feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_counterparty_amount_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedCounterpartyAmount(id));
            testCase.embedTestData(testDataManager.getFeedCounterpartyAmount(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy holiday centres feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy holiday centres feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedHolidayCentre}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^copy holiday centres feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_holiday_centres_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedHolidayCentre(id));
            testCase.embedTestData(testDataManager.getFeedHolidayCentre(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy interest amount feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy interest amount feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedInterestAmount}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy interest amount feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_interest_amount_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedInterestAmount(id));
            testCase.embedTestData(testDataManager.getFeedInterestAmount(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy Asset Booking feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy Asset Booking feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedAssetBooking}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy Asset Booking feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_asset_booking_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedAssetBooking(id));
            testCase.embedTestData(testDataManager.getFeedAssetBooking(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy trade feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy trade feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedTrade}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy trade feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_trade_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedTrade(id));
            testCase.embedTestData(testDataManager.getFeedTrade(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy repo trade feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy trade feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedRepoTrade}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^copy repo trade feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_repo_trade_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedRepoTrade(id));
            testCase.embedTestData(testDataManager.getFeedRepoTrade(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: Copy organisation contact feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy organisation contact feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedOrganisationContact}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    @When("^copy organisation contact feed (xml|excel|xlsx) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_organisation_contact_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedOrganisationContact(id));
            testCase.embedTestData(testDataManager.getFeedOrganisationContact(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(serverFilePath);
    }

    /**
     * Description: Copy etd balances feed file to server location<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: Copy etd balances feed <b>xml</b> file <b>feed.file</b> for <b>task.scheduler</b> to <b>server.location</b><br/>
     * @param format can be one of following value:<br /><li>xml</li><li>excel</li><li>xlsx</li>
     * @param feedIds can be one of following type:<br /><li>{@link FeedEtdBalances}</li>
     * @param taskSchedulerId can be one of following type:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^copy etd balances feed (xml|excel|xlsx|csv) file (\\S+) for (\\S+) to (\\S+)$")
    public void copy_etd_balances_feed_file_to(String format, List<String> feedIds, String taskSchedulerId, String variableId) throws Exception{
        StringType serverFilePath = testDataManager.getVariable(variableId);
        ArrayList list = new ArrayList();
        for(String id : feedIds){
            list.add(testDataManager.getFeedEtdBalances(id));
            testCase.embedTestData(testDataManager.getFeedEtdBalances(id));
        }
        Feed feed = new Feed();
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        testCase.embedTestData(serverFilePath);
        testCase.embedTestData(taskSchedulerDetail);
        switch (format.toLowerCase()) {
            case "xml" :
                pageManager.taskSchedulerPage.copyFeedXmlFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "excel" :
                pageManager.taskSchedulerPage.copyFeedXlsFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "xlsx" :
                pageManager.taskSchedulerPage.copyFeedXlsxFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
            case "csv" :
                pageManager.taskSchedulerPage.copyFeedCsvFile(testCase, feed, list, taskSchedulerDetail.getAdditionalInfo(), serverFilePath);
                break;
        }
    }

    /**
     * Description: switch TAB in task scheduler page<br/>
     * Start page: task scheduler page<br/>
     * End page: task scheduler page<br/>
     * samples: task scheduler - switch to <b>Task Manager</b> tab<br/>
     * @param tabName can be one of following value:<br /><li>Task Manager</li><li>Workflow</li><li>Market Data</li><li>Reports</li><li>System</li><li>Reconciliation</li><li>Admin</li><li>Optimisation</li>
     * @throws Exception
     */
    @When("^task scheduler - switch to (Task Manager|Workflow|Market Data|Reports|System|Reconciliation|Admin|Optimisation) tab$")
    public void task_scheduler_switch_tab(String tabName) throws Exception{
        TaskSchedulerDetail taskSchedulerDetail = new TaskSchedulerDetail();
        taskSchedulerDetail.setType(TaskSchedulerType.fromValue(tabName));
        pageManager.taskSchedulerPage.switchToTaskScheduleTab(taskSchedulerDetail);
    }

    /**
     * Description: navigate to stp switch page<br/>
     * Start page: anyPage<br/>
     * End page: STP Configurations<br/>
     * samples: navigate to stp switch page<br/>
     * @throws Exception
     */
    @When("^navigate to stp switch page$")
    public void navigate_to_stp_switch_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.STP_SWITCH);
    }

    /**
     * Description: navigate to server log page<br/>
     * Start page: anyPage<br/>
     * End page: server log page<br/>
     * samples: navigate to server log page<br/>
     * @throws Exception
     */
    @When("^navigate to server log page$")
    public void navigate_to_server_log() throws Exception{
        pageManager.welcomePage.navigate(Menu.SERVER_LOG);
    }

    /**
     * Description: navigate to active messaging page<br/>
     * Start page: anyPage<br/>
     * End page: active messaging page<br/>
     * samples: navigate to active messaging page<br/>
     * @throws Exception
     */
    @When("^navigate to active messaging page$")
    public void navigate_to_active_messaging() throws Exception{
        pageManager.welcomePage.navigate(Menu.ACTIVE_MESSAGING);
    }
    /**
     * Description: navigate to messages page<br/>
     * Start page: anyPage<br/>
     * End page: messages page<br/>
     * samples: navigate to messages page<br/>
     * @throws Exception
     */
    @When("^navigate to messages page$")
    public void navigate_to_messages() throws Exception{
        pageManager.welcomePage.navigate(Menu.MESSAGES);
    }
    /**
     * Description: navigate to user&role approval page<br/>
     * Start page: anyPage<br/>
     * End page: user&role approval page<br/>
     * samples: navigate to user&role approval page<br/>
     * @throws Exception
     */
    @When("^navigate to user&role approval page$")
    public void navigate_to_user_and_role_approval() throws Exception{
        pageManager.welcomePage.navigate(Menu.USER_AND_ROLE_APPROVAL);
    }
    /**
     * Description: navigate to privileges page<br/>
     * Start page: anyPage<br/>
     * End page: privileges page<br/>
     * samples: navigate to privileges page<br/>
     * @throws Exception
     */
    @When("^navigate to privileges page$")
    public void navigate_to_privileges() throws Exception{
        pageManager.welcomePage.navigate(Menu.PRIVILEGES);
    }
    /**
     * Description: navigate to password constraints page<br/>
     * Start page: anyPage<br/>
     * End page: password constraints page<br/>
     * samples: navigate to password constraints page<br/>
     * @throws Exception
     */
    @When("^navigate to password constraints page$")
    public void navigate_to_password_constraints() throws Exception{
        pageManager.welcomePage.navigate(Menu.PASSWORD_CONSTRAINTS);
    }
    /**
     * Description: navigate to audit trail status page<br/>
     * Start page: anyPage<br/>
     * End page: audit trail status page<br/>
     * samples: navigate to audit trail status page<br/>
     * @throws Exception
     */
    @When("^navigate to audit trail status page$")
    public void navigate_to_audit_trail_status() throws Exception{
        pageManager.welcomePage.navigate(Menu.AUDIT_TRAIL_STATUS);
    }
    /**
     * Description: navigate to help attachments page<br/>
     * Start page: anyPage<br/>
     * End page: help attachments page<br/>
     * samples: navigate to help attachments page<br/>
     * @throws Exception
     */
    @When("^navigate to help attachments page$")
    public void navigate_to_help_attachments() throws Exception{
        pageManager.welcomePage.navigate(Menu.HELP_ATTACHMENTS);
    }

    /**
     * Description: navigate to region page<br/>
     * Start page: anyPage<br/>
     * End page: region page<br/>
     * samples: navigate to region page<br/>
     * @throws Exception
     */
    @When("^navigate to region page$")
    public void navigate_to_region() throws Exception{
        pageManager.welcomePage.navigate(Menu.REGION);
    }
    /**
     * Description: navigate to business frequency page<br/>
     * Start page: anyPage<br/>
     * End page: business frequency page<br/>
     * samples: navigate to business frequency page<br/>
     * @throws Exception
     */
    @When("^navigate to business frequency page$")
    public void navigate_to_business_frequency() throws Exception{
        pageManager.welcomePage.navigate(Menu.BUSINESS_FREQUENCY);
    }

    /**
     * Description: Add concentration limit rules for asset class or type in agreement setup page<br/>
     * Start page: Collateral setup page in agreement setup<br/>
     * End page: Collateral setup page in agreement setup<br/>
     * samples: add concentration limit rule for asset class in agreement <b>my.agreement</b><br/>
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^add concentration limit rules? for asset (?:class|type) in agreement (\\S+)$")
    public void add_concentration_limit_rule_for_asset_in_agreement(String id) throws Exception{
        Agreement agreement = testDataManager.getAgreement(id);
        pageManager.agreementCollateralSetupPage.setupCollateralAssetClass(agreement);
        testCase.embedTestData(agreement);
    }

    /**
     * Description: edit concentration limit rule for asset class or asset type in agreement setup page<br/>
     * Start page: Collateral setup page in agreement setup<br/>
     * End page: Collateral setup page in agreement setup<br/>
     * samples: edit concentration limit rule <b>my.agreement1</b> to <b>my.agreement2</b> for asset class in agreement<br/>
     * @param oriId can be one of following type:<br /><li>{@link Agreement}</li>
     * @param newId can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^edit concentration limit rule (\\S+) to (\\S+) for asset (?:class|type) in agreement$")
    public void edit_concentration_limit_rule_to_for_asset_in_agreement(String oriId, String newId) throws Exception{
        Agreement oriAgreement = testDataManager.getAgreement(oriId);
        Agreement newAgreement = testDataManager.getAgreement(newId);

        pageManager.agreementCollateralSetupPage.editAgreementAssetClass(oriAgreement, newAgreement);

        testCase.embedTestData(oriAgreement);
        testCase.embedTestData(newAgreement);
    }

    /**
     * Description: click create substitution button in EM page<br/>
     * Start page: EM page<br/>
     * End page: adhoc substitution pop-up in EM page<br/>
     * samples: Exposure Management - click create substitution button<br/>
     * @throws Exception
     */
    @When("^Exposure Management - click create substitution button$")
    public void exposure_management_click_create_substitution() throws Exception{
        pageManager.exposureManagementPage.createSubstitution();
    }

    /**
     * Description: set the adhoc substitution search criteria and search in adhoc substitutition pop-up in EM page<br/>
     * Start page: adhoc substitution pop-up in EM page<br/>
     * End page: adhoc substitution pop-up in EM page<br/>
     * samples: Exposure Management - search adhoc substitution <b>my.subsearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link SubstitutionEventSearch}</li>
     * @throws Exception
     */
    @When("^Exposure Management - search adhoc substitution (\\S+)$")
    public void exposure_management_search_adhoc_substitution(String id) throws Exception{
        SubstitutionEventSearch substitutionEventSearch = testDataManager.getSubstitutionEventSearch(id);
        pageManager.exposureManagementPage.setSubstitutionSearchCriteria(substitutionEventSearch);
        pageManager.exposureManagementPage.searchSubstitutionEvent();
        testCase.embedTestData(substitutionEventSearch);
    }

    /**
     * Description: tick the substitution events and click save in adhoc substitutition pop-up in EM page<br/>
     * Start page: adhoc substitution pop-up in EM page<br/>
     * End page: EM page<br/>
     * samples: Exposure Management - create adhoc substitution event <b>my.sub</b><br/>
     * @param ids can be one of following type:<br /><li>{@link SubstitutionEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - create adhoc substitution events? (\\S+)$")
    public void exposure_management_create_adhoc_substitution_event(List<String> ids) throws Exception{
        for(String id : ids){
            SubstitutionEventDetail substitutionEventDetail = testDataManager.getSubstitutionEventDetail(id);
            pageManager.exposureManagementPage.tickSubstitutionEvent(substitutionEventDetail);
            testCase.embedTestData(substitutionEventDetail);
        }
        pageManager.exposureManagementPage.saveSubstitutionCreation();
    }

    /**
     * Description: click colapse|expand button in Create Substitution Event page<br/>
     * Start page: exposureManagementPage <br/>
     * End page: exposureManagementPage<br/>
     * samples: Exposure Management - <b>collapse</b> event <b>my.sub</b><br/>
     * @param action can be one of following value:<br /><li>collapse</li><li>expand</li>
     * @param ids can be one of following type:<br /><li>{@link SubstitutionEventDetail}</li>
     * @throws Exception
     */
    @When("^Exposure Management - create substitution event - (collapse|expand) events? (\\S+)$")
    public void exposure_Management_Create_Substitution_Event_collapse_expand_events(String action, List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            SubstitutionEventDetail substitutionEventDetail = testDataManager.getSubstitutionEventDetail(id);
            testCase.embedTestData(substitutionEventDetail);
            switch (action.toLowerCase()) {
                case "collapse":
                    pageManager.exposureManagementPage.collapseSubstitutionEvent(substitutionEventDetail);
                    break;
                case "expand":
                    pageManager.exposureManagementPage.expandSubstitutionEvent(substitutionEventDetail);
                    break;
            }
        }
    }

    /**
     * Description: edit agreement exposure adjustment and click save button<br/>
     * Start page: collateral agreement exposure summary page<br/>
     * End page: collateral agreement exposure summary page<br/>
     * @param id can be one of following type:<br /><li>{@link AgreementExposureAdjustment}</li>
     * @throws Exception
     */
    @When("^edit agreement exposure adjustment to (\\S+)$")
    public void edit_agreement_exposure_to(String id) throws Exception {
        AgreementExposureAdjustment agreementExposureAdjustment = testDataManager.getAgreementExposureAdjustment(id);
        pageManager.tradeSummaryPage.editAgreementExposureAdjustment(agreementExposureAdjustment);
        testCase.embedTestData(agreementExposureAdjustment);
    }

    /**
     * Description: edit agreement id value and click save button<br/>
     * Start page: collateral agreement exposure summary page<br/>
     * End page: collateral agreement exposure summary page<br/>
     * @param id can be one of following type:<br /><li>{@link AgreementIaValue}</li>
     * @throws Exception
     */
    @When("^edit ia value to (\\S+)$")
    public void edit_ia_value_to(String id) throws Exception {
        AgreementIaValue agreementIaValue = testDataManager.getAgreementIaValue(id);
        pageManager.tradeSummaryPage.editAgreementIaValue(agreementIaValue);
        testCase.embedTestData(agreementIaValue);
    }



    /**
     * Description: expand mulit-model or ETF or umbrella interest event in interest manager search result page<br/>
     * Start page: interest manager search result page<br/>
     * End page: interest manager search result page<br/>
     * samples: expand Umbrella interest event in IM<br/>
     * @throws Exception
     */
    @When("^expand (?:Multimodel|ETF|Umbrella) interest event in IM$")
    public void interest_manager_expand_interest_event() throws Exception{
        pageManager.interestManagerSearchResultPage.expandAllEvents();
    }

    /**
     * Description: Add new user filter in EM page<br/>
     * Start page: EM page<br/>
     * End page: EM page<br/>
     * samples: Exposure Management - User Filters - add user filter <b>my.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link ExposureManagementFilter}</li>
     * @throws Exception
     */
    @When("^Exposure Management - User Filters - add user filter (\\S+)$")
    public void exposure_management_user_filters_add_user_filter(String id) throws Exception{
        ExposureManagementFilter exposureManagementFilter = testDataManager.getExposureManagementFilter(id);
        pageManager.exposureManagementPage.addFilter(exposureManagementFilter);
        testCase.embedTestData(exposureManagementFilter);
    }

    /**
     * Description: Change agreement tab<br/>
     * Start page: Agreement Summary page<br/>
     * End page: Any agreement setup tab page<br/>
     * samples: agreement setup - change tab to <b>Agreement</b><br/>
     * @param id can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @When("^agreement setup - change tab to (\\S+)$")
    public void agreement_setup_change_tab_to(String id) throws Exception {
        Agreement agreement = testDataManager.getAgreement(id);
        pageManager.abstractCollinePage.switch_tab(agreement.getAgreementTabName().value());
        testCase.embedTestData(agreement);
    }

	/**
     * Description: navigate to trade search page<br/>
     * Start page: anyPage<br/>
     * End page: trade search page<br/>
     * samples: navigate to trade search page<br/>
     * @throws Exception
     */
    @When("^navigate to trade search page$")
    public void navigate_to_trade_search_page() throws  Exception{
        pageManager.welcomePage.navigate(Menu.TRADE_SEARCH);
    }

    /**
     * Description: search trade in trade search page<br/>
     * Start page: trade search page<br/>
     * End page: trade search page<br/>
     * samples: Trade search - search trade <b>my.tradeSearch.filter</b><br/>
     * @param id can be one of following type:<br /><li>{@link TradeSearch}</li>
     * @throws Exception
     */
    @When("^Trade search - search trade (\\S+)$")
    public void search_trade(String id) throws  Exception{
        TradeSearch tradeSearch = testDataManager.getTradeSearch(id);
        pageManager.tradeSearchPage.searchTrade(tradeSearch);
        testCase.embedTestData(tradeSearch);
    }

    /**
     * Description: navigate to organisation global preferences page<br/>
     * Start page: anyPage<br/>
     * End page: organisation global preferences page<br/>
     * samples: navigate to organisation global preferences page<br/>
     * @throws Exception
     */
    @When("^navigate to organisation global preferences page$")
    public void navigate_to_organisation_global_preferences_page()throws Exception{
        pageManager.welcomePage.navigate(Menu.GLOBAL_PREFERENCES);
    }

	/**
     * Description: navigate to statement archive search page<br/>
     * Start page: anyPage<br/>
     * End page: statement archive search page<br/>
     * samples: navigate to statement archive search page<br/>
     * @throws Exception
     */
    @When("^navigate to statement archive search page$")
    public void navigate_to_statement_archive_search_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.STATEMENT_ARCHIVE_SEARCH);
    }

    /**
     * Description: search statement archive in statement archive search page<br/>
     * Start page: statement archive search page<br/>
     * End page: statement archive search page<br/>
     * samples: statement archive - search statement archive <b>my.statementArchiveSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link StatementArchiveSearch}</li>
     * @throws Exception
     */
    @When("^statement archive - search statement archive (\\S+)$")
    public void search_statement_archive(String id)throws  Exception{
        StatementArchiveSearch statementArchiveSearch = testDataManager.getStatementArchiveSearch(id);
        pageManager.statementArchiveSearchPage.searchStatementArchive(statementArchiveSearch);
        testCase.embedTestData(statementArchiveSearch);
    }

    /**
     * Description: navigate to interest manager history page<br/>
     * Start page: anyPage<br/>
     * End page: interest manager history page<br/>
     * samples: navigate to interest manager history page<br/>
     * @throws Exception
     */
    @When("^navigate to interest manager history page$")
    public void navigate_to_interest_manager_history_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.INTEREST_MANAGER_HISTORY);
    }

    /**
     * Description: search interest manager history in interest manager history search page<br/>
     * Start page: interest manager history page<br/>
     * End page: interest manager history page<br/>
     * samples: interest manager history - search interest manager history <b>my.interestManagerArchiveSearch</b><br/>
     * @param id can be one of following type:<br /><li>{@link InterestEventSearch}</li>
     * @throws Exception
     */
    @When("^interest manager history - search interest manager history (\\S+)$")
    public void search_interest_manager_history(String id) throws  Exception{
        InterestEventSearch interestEventSearch = testDataManager.getInterestEventSearch(id);
        pageManager.interestManagerArchiveSearchPage.searchInterestManagerHistory(interestEventSearch);
        testCase.embedTestData(interestEventSearch);
    }

    /**
     * Description: Expand or Collapse security movement detail in security movement page<br/>
     * Start page: security movement page<br/>
     * End page: security movement page<br/>
     * samples: security movement - expand security <b>my.security.movement</b> details<br/>
     * @param action can be one of following value:<br /><li>expand</li><li>collpase</li>
     * @param ids can be one of following type:<br /><li>{@link SecurityMovementDetail}</li>
     * @throws Exception
     */
    @When("^security movement - (expand|collapse) security (\\S+) details$")
    public void security_movement_expand_or_collapse_security(String action, List<String> ids) throws Exception{
        for(String id : ids){
            SecurityMovementDetail securityMovementDetail = testDataManager.getSecurityMovementDetail(id);
            switch (action.toLowerCase()){
                case "expand" :
                    pageManager.securityMovementPage.expandSecurityMovementDetail(securityMovementDetail);
                    break;
                case "collapse" :
                    pageManager.securityMovementPage.collapseSecurityMovementDetail(securityMovementDetail);
                    break;
            }
            testCase.embedTestData(securityMovementDetail);
        }
    }

    /**
     * Description: navigate to optimisation rule builder page<br/>
     * Start page: anyPage<br/>
     * End page: optimisation rule builder page<br/>
     * samples: navigate to optimisation rule builder page<br/>
     * @throws Exception
     */
    @When("^navigate to optimisation rule builder page$")
    public void navigate_to_rule_builder_page() throws Exception{
        pageManager.welcomePage.navigate(Menu.RULE_BUILDER);
    }

    /**
     * Description: select optimisation module<br/>
     * Start page: optimisation rule builder page<br/>
     * End page: optimisation rule builder page<br/>
     * samples: Optimisation - select <b>filters</b> - <b>Asset</b><br/>
     * @param mainModule can be one of following value:<br /><li>filters</li><li>rankings</li><li>optimisation</li>
     * @param subModule can be one of following type:<br /><li>Asset</li><li>Agreement</li><li>Rules</li><li>Results</li>
     * @throws Exception
     */
    @When("^(?:Optimisation - )?select (filters|rankings|optimisation) - (Asset|Agreement|Rules|Results)$")
    public void optimisation_select_modual(String mainModule,String subModule) throws Exception{
        switch (mainModule.toLowerCase()) {
            case "filters":
                if (subModule.equals("Asset"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Filters_Asset);
                else if (subModule.equals("Agreement"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Filters_Agreement);
                break;
            case "rankings":
                if (subModule.equals("Asset"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Rankings_Asset);
                else if (subModule.equals("Agreement"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Rankings_Agreement);
                break;
            case "optimisation":
                if (subModule.equals("Rules"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Optimisation_Rules);
                else if (subModule.equals("Results"))
                    pageManager.optimisationPage.navigate(OptimisationMenu.Optimisation_Results);
                break;
            default:
                break;
        }
    }
    /**
     * Description: add optimisation rule<br/>
     * Start page: optimisation rule builder page<br/>
     * End page: optimisation rule builder page<br/>
     * samples: Optimisation - add <b>filters</b> - <b>Asset</b> <b>OptimisationRule</b><br/>
     * @param id can be one of following type:<br /><li>{@link OptimisationRule}</li>
     * @throws Exception
     */
    @When("^(?:Optimisation - )?add (?:filters|rankings|optimisation) - (?:Asset|Agreement|Rules) (\\S+)$")
    public void optimisation_add_rule(String id) throws Exception{
        OptimisationRule optimisationRule = testDataManager.getOptimisationRule(id);
        pageManager.optimisationPage.addOptRule(optimisationRule);
        testCase.embedTestData(optimisationRule);
    }

    /**
     * Description: edit optimisation rule<br/>
     * Start page: optimisation rule builder page<br/>
     * End page: optimisation rule builder page<br/>
     * samples: Optimisation - edit rule <b>OptimisationRule</b> to <b>OptimisationRule</b><br/>
     * @param oriRule can be one of following value:<br /><li>{@link OptimisationRule}</li>
     * @param newRule can be one of following type:<br /><li>{@link OptimisationRule}</li>
     * @throws Exception
     */
    @When("^Optimisation - edit rule (\\S+) to (\\S+)$")
    public void edit_opt_rule(String oriRule, String newRule) throws Exception{
        OptimisationRule oriOpt = testDataManager.getOptimisationRule(oriRule);
        OptimisationRule optimisationRule = testDataManager.getOptimisationRule(newRule);
        pageManager.optimisationPage.enterRule(oriOpt);
        pageManager.optimisationPage.modifyOptRule(oriOpt,optimisationRule);
        testCase.embedTestData(oriOpt);
        testCase.embedTestData(optimisationRule);
    }

    /**
     * Description: delete optimisation rule<br/>
     * Start page: optimisation rule builder page<br/>
     * End page: optimisation rule builder page<br/>
     * samples: Optimisation - delete rule <b>OptimisationRuleDetail</b><br/>
     * @param searchResult can be one of following value:<br /><li>{@link OptimisationRuleDetail}</li>
     * @throws Exception
     */
    @When("^Optimisation - delete rule (\\S+)$")
    public void delete_opt_rule(String searchResult) throws Exception{
        OptimisationRuleDetail result = testDataManager.getOptimisationRuleDetail(searchResult);
        pageManager.optimisationPage.deleteRule(result);
        testCase.embedTestData(result);
    }

    /**
     * Description: run optimisation rule<br/>
     * Start page: optimisation rule builder page<br/>
     * End page: optimisation rule builder page<br/>
     * samples: Optimisation - run rule <b>OptimisationRuleDetail</b><br/>
     * @param id can be one of following value:<br /><li>{@link OptimisationRuleDetail}</li>
     * @throws Exception
     */
    @When("^(?:Optimisation - )?run rule (\\S+)$")
    public void run_opt_rule(String id) throws Exception{
        OptimisationRuleDetail result = testDataManager.getOptimisationRuleDetail(id);
        pageManager.optimisationPage.runRule(result);
        testCase.embedTestData(result);
    }

    /**
     * Description: Create folder on Colline server<br/>
     * Start page: any pages<br/>
     * End page: any pages<br/>
     * samples: create new folder <b>folder</b> on server<br/>
     * @param taskSchedulerId can be one of following value:<br /><li>{@link TaskSchedulerDetail}</li>
     * @throws Exception
     */
    @When("^create new folder (\\S+) on server$")
    public void create_folder_on_server(String taskSchedulerId) throws Exception{
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
        pageManager.taskSchedulerPage.createResultFolderOnServer(testCase, taskSchedulerDetail.getAdditionalInfo());
        testCase.embedTestData(taskSchedulerDetail);
    }

    /**
     * Description: Get extract agreement file from Colline Server<br/>
     * Start page: any pages<br/>
     * End page: any pages<br/>
     * samples: get extract agreement result for <b>agreement</b> to local <b>colline.server<b/><br/>
     * @param taskSchedulerId can be one of following value:<br /><li>{@link TaskSchedulerDetail}</li>
     * @param variableId can be one of following value:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @When("^get extract agreement result for (\\S+) to local (\\S+)$")
    public void get_extract_agreement_to_local(String taskSchedulerId, String variableId) throws Exception{
        StringType fileName = testDataManager.getVariable(variableId);
        TaskSchedulerDetail taskSchedulerDetail = testDataManager.getTaskSchedulerDetail(taskSchedulerId);
//        pageManager.taskSchedulerPage.createResultFolderOnServer(testCase, taskSchedulerDetail.getAdditionalInfo());
        pageManager.taskSchedulerPage.getTaskScheduelResult(testCase, taskSchedulerDetail.getAdditionalInfo(), fileName);
        testCase.embedTestData(taskSchedulerDetail);
        testCase.embedTestData(fileName);
    }

    /**
     * Description: Execute SQL query<br/>
     * Start page: any pages<br/>
     * End page: any pages<br/>
     * samples: execute SQL query <b>sql</b><br/>
     * @param id can be one of following value:<br /><li>{@link DatabaseQuery}</li>
     * @throws Exception
     */
    @When("^(?:Database - )?execute SQL query (\\S+)$")
    public void execute_sql_query(String id) throws Exception{
        DatabaseQuery databaseQuery = testDataManager.getDatabaseQuery(id);
        testCase.embedTestData(databaseQuery);
        pageManager.databaseOperationsPage.executeQuery(databaseQuery);
    }

    /**
     * Description: Create a new calculation rule
     * Start page: Any page
     * End page: Calculation rule Search Page
     * @param id The id of the {@link StatementCalcRule} data to lookup and save
     * @throws Exception
     */
    @When("^I create a new calculation rule (\\S+)$")
    public void iCreateANewCalculationRule(List<String> ids) throws Exception {
        for(String id : ids) {
            pageManager.calculationRuleMaintenancePage.clickAddNew();
            StatementCalcRule statementCalcRule = testDataManager.getStatementCalcRule(id);
            testCase.embedTestData(statementCalcRule);
            pageManager.calculationRuleMaintenancePage.populateRuleSetup(statementCalcRule);
            if (statementCalcRule.getUserDefinedFields() != null && !statementCalcRule.getUserDefinedFields().isEmpty()) {
                pageManager.calculationRuleMaintenancePage.populateRuleUdf(statementCalcRule.getUserDefinedFields());
            }
            pageManager.calculationRuleMaintenancePage.clickSave();
        }
    }

    /**
     * Description: Create and edit a new calculation rule
     * Start page: Newly created Calculation Search Rule showing as single result
     * End page: Calculation rule Search Page
     * @param id The id of the {@link StatementCalcRule} to lookup and save
     * @throws Exception
     */
    @When("^I edit the calculation rule and change it to be (\\S+)$")
    public void iEditTheCalculationRuleAndChangeItToBeUpdatedCalcRule(String id) throws Exception {
        pageManager.calculationRuleMaintenancePage.clickEdit();
        StatementCalcRule updatedStatementCalcRule = testDataManager.getStatementCalcRule(id);
        testCase.embedTestData(updatedStatementCalcRule);
        pageManager.calculationRuleMaintenancePage.updateRuleName(updatedStatementCalcRule.getRuleName().getRealValue());
        pageManager.calculationRuleMaintenancePage.clickSave();
    }

    /**
     * Description: Delete a saved calculation rule
     * Start page: Newly created Calculation Search Rule showing as single result
     * End page: Calculation rule Search Page
     * @throws Exception
     */
    @When("^I delete this calculation rule in the rules table$")
    public void iDeleteThisCalculationRuleInTheRulesTable() throws Exception {
        pageManager.calculationRuleMaintenancePage.clickDelete();
    }

    /**
     * Description: Navigates to the saved calculation rule page
     * Start page: Any page
     * End page: Calculation rule Search Page
     * @throws Exception
     */
    @When("^I navigate to the rule set up page$")
    public void iNavigateToTheRuleSetUpPage() throws Exception {
        pageManager.welcomePage.navigate(Menu.CALCULATION_RULE_MAINTENANCE);
    }

    @When("^I search calculation rules filtering by name (\\S+)$")
    public void iSearchCalculationRulesFilteringByName(String id) throws Exception {
        pageManager.welcomePage.navigate(Menu.CALCULATION_RULE_MAINTENANCE);
        StatementCalcRule updatedStatementCalcRule = testDataManager.getStatementCalcRule(id);
        pageManager.calculationRuleMaintenancePage.updateRuleName(updatedStatementCalcRule.getRuleName().getRealValue());
        pageManager.calculationRuleMaintenancePage.doSearch();
    }

    /**
     * Description: creates linkage set static data
     * Start page: Any page
     * End page: Collateral static data
     * @throws Exception
     */
    @When("^I create the linkage set (\\S+)$")
    public void iCreateALinkageSet(String id) throws Exception {
        pageManager.welcomePage.navigate(Menu.COLLATERAL_STATIC_DATA);
        StaticData staticData = testDataManager.getCollateralStaticData(id);
        pageManager.collateralStaticDataPage.addStaticData(staticData);
        testCase.embedTestData(staticData);
    }

    /**
     * Description: copies agreement being edited
     * Start page: Agreement summary page
     * End page: Agreement summary page
     * @throws Exception
     */
    @When("^copy agreement (\\S+) times$")
    public void iCopyAgreement(String times) throws Exception {
        pageManager.agreementSummaryPage.enterAgreementPartySummary();
        pageManager.agreementSummaryPage.editAgreementSummary();
        pageManager.agreementPartyCounterpartySetupPage.copyAndSave(times);
    }

    /**
     * Description: approves the agreement
     * Start page: Agreement summary page
     * End page: Agreement summary page
     * @throws Exception
     */
    @When("^approve the ETD agreement$")
    public void iApproveTheEtdAgreement() throws Exception {
        pageManager.agreementSummaryPage.viewStatement();
        pageManager.agreementStatementPage.approveAgreementStatus();
    }

    /**
     * Description: navigate to exposures page<br/>
     * Start page: anyPage<br/>
     * End page: self service - client data - exposures page<br/>
     * samples: navigate to exposures page<br/>
     * @throws Exception
     */
    @When("^Self Service - navigate to exposures page$")
    public void navigate_to_exposures_page() throws Exception{
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.INTERNAL_REVIEWS);
    }
}