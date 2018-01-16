package com.lombardrisk.glue.steps;

import com.lombardrisk.colline.agreement.dto.ColAgreementHeaderData;
import com.lombardrisk.colline.agreement.service.ColAgreementServiceRemote;
import com.lombardrisk.data.Menu;
import com.lombardrisk.pages.PageManager;
import com.lombardrisk.pojo.*;
import com.lombardrisk.test.TestDataManager;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import com.lombardrisk.util.RemoteEJBUtil;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kenny Wang on 2/4/2016.
 */
public class GivenStepDefinition {
    private final static Logger logger = LoggerFactory.getLogger(GivenStepDefinition.class);
    private ITestBase testCase = TestCaseManager.getTestCase();
    private TestDataManager testDataManager = (TestDataManager) testCase.getTestDataManager();
    private PageManager pageManager = (PageManager) testCase.getPageManager();

    /**
     * Description: set organisation global preferences<br/>
     * samples: have organisation global preferences <b>my.global.prefernces</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link OrganisationGlobalPreference}</li>
     * @throws Exception
     */
    @Given("^have organisation global preferences (\\S+)$")
    public void have_organisation_global_preferences(String id) throws Exception {
        pageManager.welcomePage.navigate(Menu.GLOBAL_PREFERENCES);
        OrganisationGlobalPreference organisationGlobalPreference = testDataManager.getOrganisationGlobalPreference(id);
        pageManager.globalPreferencesPage.editOrganisationGlobalSettingPreferences(organisationGlobalPreference);
        pageManager.welcomePage.navigate(Menu.HOME);
        testCase.embedTestData(organisationGlobalPreference);
    }

    /**
     * Description: set organisation global preferences<br/>
     * samples: have organisation global preferences <b>my.global.prefernces</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link OrganisationGlobalPreference}</li>
     * @throws Exception
     */
//    @Given("^have letter configuration (\\S+)$")
//    public void have_letter_configuration(String id) throws Exception {
//        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
//        LetterConfiguration letterConfiguration = testDataManager.getLetterConfiguration(id);
//
//        if(letterConfiguration.getMarginLetter() != null && !letterConfiguration.getMarginLetter().isEmpty()) {
//            List<MarginLetterConfiguration> marginLetterConfigurations = letterConfiguration.getMarginLetter();
//            pageManager.letterConfigurationPage.switchToMarginLetterTab();
//            for (MarginLetterConfiguration marginLetterConfiguration : marginLetterConfigurations) {
//                pageManager.letterConfigurationPage.switchMarginLetterTypeTab(marginLetterConfiguration.getLetterType());
//                pageManager.letterConfigurationPage.setInclusioninMarginLetterTab(marginLetterConfiguration);
//                for(LetterTemplate letterTemplate : marginLetterConfiguration.getLetterTemplate()){
//                    pageManager.letterConfigurationPage.addLetterTemplate();
//                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
//                    if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
//                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
//                }
//            }
//        }
//
//        if(letterConfiguration.getInterestLetter() != null && !letterConfiguration.getInterestLetter().isEmpty()) {
//            List<InterestLetterConfiguration> interestLetterConfigurations = letterConfiguration.getInterestLetter();
//            pageManager.letterConfigurationPage.switchToInterestLetterTab();
//            for (InterestLetterConfiguration interestLetterConfiguration : interestLetterConfigurations) {
//                pageManager.letterConfigurationPage.switchInterestLetterTypeTab(interestLetterConfiguration.getLetterType());
//                for(LetterTemplate letterTemplate : interestLetterConfiguration.getLetterTemplate()){
//                    pageManager.letterConfigurationPage.addLetterTemplate();
//                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
//                    if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
//                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
//                }
//            }
//        }
//
//        if(letterConfiguration.getUserDefinedLetter() != null){
//            pageManager.letterConfigurationPage.switchToUDLTab();
//            pageManager.letterConfigurationPage.setInclusionInUDLTab(letterConfiguration.getUserDefinedLetter());
//            for(LetterTemplate letterTemplate : letterConfiguration.getUserDefinedLetter().getLetterTemplate()){
//                pageManager.letterConfigurationPage.addLetterTemplate();
//                pageManager.userDefinedLetterPage.inputLetterTemplate(letterTemplate);
//                if(!pageManager.userDefinedLetterPage.saveLetterTemplate())
//                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
//            }
//        }
//
//        if(letterConfiguration.getExposureStatements() != null){
//            pageManager.letterConfigurationPage.switchToExposureStatementsTab();
//            for(LetterTemplate letterTemplate : letterConfiguration.getExposureStatements().getLetterTemplate()){
//                pageManager.letterConfigurationPage.addLetterTemplate();
//                pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
//                if(!pageManager.letterTemplateEditorPage.saveLetterTemplate())
//                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
//            }
//        }
//        pageManager.welcomePage.navigate(Menu.HOME);
//        testCase.embedTestData(letterConfiguration);
//    }

    /**
     * Description: set organisation global preferences as default value<br/>
     * samples: have default organisation global preferences<br/>
     *
     * @throws Exception
     */
    @Given("^have default organisation global preferences$")
    public void have_default_organisation_global_preferences() throws Exception {
        have_organisation_global_preferences("organisation.global.preference.default");
    }

    /**
     * Description: set collateral preferences<br/>
     * samples: have collateral preferences <b>my.collateral.preferences</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link CollateralPreference}</li>
     * @throws Exception
     */
    @Given("^have collateral preferences (\\S+)$")
    public void have_collateral_preferences(String id) throws Exception {
        pageManager.welcomePage.navigate(Menu.COLLATERAL_CONFIGURATION_PREFERENCES);
        CollateralPreference collateralPreference = testDataManager.getCollateralPreference(id);
        pageManager.collateralPreferencesPage.setCollateralPreference(collateralPreference);
        pageManager.welcomePage.navigate(Menu.HOME);
        testCase.embedTestData(collateralPreference);
    }

    /**
     * Description: set collateral preferences as default value<br/>
     * samples: have default collateral preferences<br/>
     *
     * @throws Exception
     */
    @Given("^have default collateral preferences$")
    public void have_default_collateral_preferences() throws Exception {
        have_collateral_preferences("collateral.preference.default");
    }

    /**
     * Description: set system preferences<br/>
     * samples: have system preferences <b>my.system.preferences</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link SystemPreference}</li>
     * @throws Exception
     */
    @Given("^have system preferences (\\S+)$")
    public void have_system_preferences(String id) throws Exception {
        SystemPreference sp = testDataManager.getSystemPreference(id);
        pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        if (sp.getMessaging() != null) {
            pageManager.systemPreferencesPage.switchToMessagingTab();
            pageManager.systemPreferencesPage.editMessaging(sp);
            pageManager.systemPreferencesPage.save();
            pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if (sp.getApplicationPreferences() != null) {
            pageManager.systemPreferencesPage.switchToApplicationPreferenceTab();
            pageManager.systemPreferencesPage.setApplicationPreferences(sp);
            pageManager.systemPreferencesPage.save();
            pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if (sp.getCommunication() != null) {
            pageManager.systemPreferencesPage.switchToCommunicationTab();
            pageManager.systemPreferencesPage.setCommunication(sp);
            pageManager.systemPreferencesPage.save();
            pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if (sp.getLabels() != null) {
            pageManager.systemPreferencesPage.switchToLabelsTab();
            pageManager.systemPreferencesPage.setLabels(sp);
            pageManager.systemPreferencesPage.save();
            pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        if (sp.getEmailInformation() != null) {
            pageManager.systemPreferencesPage.switchToEmailInformationTab();
            pageManager.systemPreferencesPage.setEmailInformation(sp);
            pageManager.systemPreferencesPage.save();
            pageManager.welcomePage.navigate(Menu.SYSTEM_PREFERENCES);
        }
        testCase.embedTestData(sp);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set system preferences with defalut value<br/>
     * samples: have default system preferences<br/>
     *
     * @throws Exception
     */
    @Given("^have default system preferences$")
    public void have_default_system_preferences() throws Exception {
        have_system_preferences("system.preference.default");
    }

    /**
     * Description: prepare letter configurations<br/>
     * Start page: letter configuration page<br/>
     * End page: letter configuration page<br/>
     * samples: have letter configurations <b>my.letter.configurations</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link LetterConfiguration}</li>
     * @throws Exception
     */
    @Given("^have letter configurations? (\\S+)$")
    public void have_letter_configurations(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
        LetterConfiguration letterConfiguration = testDataManager.getLetterConfiguration(id);

        if (letterConfiguration.getMarginLetter() != null && !letterConfiguration.getMarginLetter().isEmpty()) {
            List<MarginLetterConfiguration> marginLetterConfigurations = letterConfiguration.getMarginLetter();
            pageManager.letterConfigurationPage.switchToMarginLetterTab();
            for (MarginLetterConfiguration marginLetterConfiguration : marginLetterConfigurations) {
                pageManager.letterConfigurationPage.switchMarginLetterTypeTab(marginLetterConfiguration.getLetterType());
                pageManager.letterConfigurationPage.setInclusioninMarginLetterTab(marginLetterConfiguration);
                for (LetterTemplate letterTemplate : marginLetterConfiguration.getLetterTemplate()) {
                    pageManager.letterConfigurationPage.addLetterTemplate();
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    if (!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
                }
            }
        }

        if (letterConfiguration.getInterestLetter() != null && !letterConfiguration.getInterestLetter().isEmpty()) {
            List<InterestLetterConfiguration> interestLetterConfigurations = letterConfiguration.getInterestLetter();
            pageManager.letterConfigurationPage.switchToInterestLetterTab();
            for (InterestLetterConfiguration interestLetterConfiguration : interestLetterConfigurations) {
                pageManager.letterConfigurationPage.switchInterestLetterTypeTab(interestLetterConfiguration.getLetterType());
                for (LetterTemplate letterTemplate : interestLetterConfiguration.getLetterTemplate()) {
                    pageManager.letterConfigurationPage.addLetterTemplate();
                    pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                    if (!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                        pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
                }
            }
        }

        if (letterConfiguration.getUserDefinedLetter() != null) {
            pageManager.letterConfigurationPage.switchToUDLTab();
            pageManager.letterConfigurationPage.setInclusionInUDLTab(letterConfiguration.getUserDefinedLetter());
            for (LetterTemplate letterTemplate : letterConfiguration.getUserDefinedLetter().getLetterTemplate()) {
                pageManager.letterConfigurationPage.addLetterTemplate();
                pageManager.userDefinedLetterPage.inputLetterTemplate(letterTemplate);
                if (!pageManager.userDefinedLetterPage.saveLetterTemplate())
                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
            }
        }

        if (letterConfiguration.getExposureStatements() != null) {
            pageManager.letterConfigurationPage.switchToExposureStatementsTab();
            for (LetterTemplate letterTemplate : letterConfiguration.getExposureStatements().getLetterTemplate()) {
                pageManager.letterConfigurationPage.addLetterTemplate();
                pageManager.letterTemplateEditorPage.inputLetterTemplate(letterTemplate);
                if (!pageManager.letterTemplateEditorPage.saveLetterTemplate())
                    pageManager.welcomePage.navigate(Menu.LETTER_CONFIGURATION);
            }
        }

        testCase.embedTestData(letterConfiguration);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set letter configurations with default value<br/>
     * Start page: letter configuration page<br/>
     * End page: letter configuration page<br/>
     * samples: ^have default letter configurations<br />
     *
     * @throws Exception
     */
    @Given("^have default letter configurations$")
    public void have_default_letter_configurations() throws Exception {
        have_letter_configurations("margin.call.letter.configuration.default");
    }

    /**
     * Description: login system<br/>
     * samples: have login with credential <b>my.login.user</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link LoginCredential}</li>
     * @throws Exception
     */
    @Given("^have login with credential (\\S+)$")
    public void have_login_with_credential(String id) throws Exception {
        LoginCredential lc = testDataManager.getLoginCredential(id);
        pageManager.homePage.login(lc);
        testCase.embedTestData(lc);
    }

    /**
     * Description: switch user<br/>
     * samples: hswitch user to <b>my.login.user</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link LoginCredential}</li>
     * @throws Exception
     */
    @Given("^switch user to (\\S+)$")
    public void switch_user_to(String id) throws Exception {
        pageManager.welcomePage.logout();
        have_login_with_credential(id);
    }

    /**
     * Description: prepare organisations<br/>
     * samples: have organisation <b>my.organisation</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link Organisation}</li>
     * @throws Exception
     */
    @Given("^have organisations? (\\S+)$")
    public void have_organisations(List<String> ids) throws Exception {
        for(String id : ids){
            List<FeedOrganisation> list = new ArrayList<>();
            List<OrganisationSearch> listApproveOrg = new ArrayList<>();
            Organisation organisation = testDataManager.getOrganisation(id);
            testCase.embedTestData(organisation);
            if(organisation.getStatus() != null && organisation.getStatus() == SimpleStatusType.APPROVED) {
                if (organisation.getRelationship() != null && !organisation.getRelationship().isEmpty()){
                    for (Relationship relationship : organisation.getRelationship()) {
                        if (relationship.getOrganisationSearch() != null)
                            listApproveOrg.add(relationship.getOrganisationSearch());
                    }
                }
                listApproveOrg.add(organisation.toOrganisationSearch());
            }
            FeedOrganisation feedOrganisation = organisation.toFeedOrganisation();
            list.add(feedOrganisation);
            if(organisation.getRelationship() != null && !organisation.getRelationship().isEmpty()){
                for(Relationship relationship : organisation.getRelationship()){
                    if(relationship.isParent() != null && relationship.isParent()){
                        FeedOrganisation feedParentOrg = new FeedOrganisation();
                        FeedOrganisation.Relationships feedParentOrgRelationships = new FeedOrganisation.Relationships();
                        FeedOrganisation.Relationships.Relationship feedParentOrgRelationship = new FeedOrganisation.Relationships.Relationship();
                        FeedOrganisation.OrgHeader feedParentOrgHeader = new FeedOrganisation.OrgHeader();
                        if(relationship.getOrganisationSearch() != null){
                            OrganisationSearch organisationSearch = relationship.getOrganisationSearch();
                            if(organisationSearch.getType() != null && organisationSearch.getLinkText() != null){
                                if(organisationSearch.getType() == SearchType.CODE){
                                    feedParentOrgHeader.setOrgCode(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setShortName(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setLongName(organisationSearch.getLinkText());
                                }else if(organisationSearch.getType() == SearchType.SHORT_NAME){
                                    feedParentOrgHeader.setOrgCode(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setShortName(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setLongName(organisationSearch.getLinkText());
                                }else if(organisationSearch.getType() == SearchType.LONG_NAME){
                                    feedParentOrgHeader.setOrgCode(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setShortName(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setLongName(organisationSearch.getLinkText());
                                }else if(organisationSearch.getType() == SearchType.LEI){
                                    feedParentOrgHeader.setLEI(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setOrgCode(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setShortName(organisationSearch.getLinkText());
                                    feedParentOrgHeader.setLongName(organisationSearch.getLinkText());
                                }
                            }
                            feedParentOrg.setOrgHeader(feedParentOrgHeader);
                        }
                        if(organisation.getLongName() != null)
                            feedParentOrgRelationship.setSubOrg(organisation.getLongName());
                        if(relationship.getType() != null)
                            feedParentOrgRelationship.setRelationshipType(relationship.getType());
                        feedParentOrgRelationship.setRelationshipOperation(new StringType("a"));
                        feedParentOrgRelationships.getRelationship().add(feedParentOrgRelationship);
                        feedParentOrg.setRelationships(feedParentOrgRelationships);
                        list.add(feedParentOrg);
                    }
                }
            }
            pageManager.welcomePage.navigate(Menu.FEED_ORGANISATIONS);
            Feed feed = new Feed();
            pageManager.feedOrganisationsPage.feedXmlFile(feed, list);
//            if(!listApproveOrg.isEmpty()){
//                pageManager.welcomePage.navigate(Menu.APPROVALS_MANAGER);
//                pageManager.approvalsManagerPage.switchToOrganisationTab();
//                for(Organisation organisation1 : listApproveOrg){
//                    OrganisationSimpleSearch organisationSimpleSearch = new OrganisationSimpleSearch();
//                    SimpleSearch simpleSearch = new SimpleSearch();
//                    simpleSearch.setType(SearchCriteriaType.EXACTLY_MATCHING);
//                    if(organisation.getCode() != null) {
//                        simpleSearch.setFilter(organisation1.getCode());
//                        simpleSearch.setLinkText(organisation1.getCode());
//                    }
//                    organisationSimpleSearch.setCode(simpleSearch);
//                    pageManager.approvalsManagerPage.searchOrganisation(organisationSimpleSearch);
//                    pageManager.approvalsManagerPage.search();
//                    pageManager.approvalsManagerPage.approveAllIfExist();
//                }
//                OrganisationSimpleSearch organisationSimpleSearch = new OrganisationSimpleSearch();
//                SimpleSearch simpleSearch = new SimpleSearch();
//                Organisation mainOrg = testDataManager.getOrganisation("org.main");
//                simpleSearch.setType(SearchCriteriaType.EXACTLY_MATCHING);
//                simpleSearch.setFilter(mainOrg.getCode());
//                simpleSearch.setLinkText(mainOrg.getCode());
//                organisationSimpleSearch.setCode(simpleSearch);
//                pageManager.approvalsManagerPage.searchOrganisation(organisationSimpleSearch);
//                pageManager.approvalsManagerPage.search();
//                pageManager.approvalsManagerPage.approveAllIfExist();
//            }
            pageManager.welcomePage.navigate(Menu.APPROVALS_MANAGER);
            if (!listApproveOrg.isEmpty()) {
                for (OrganisationSearch orgObj : listApproveOrg) {
                    OrganisationSimpleSearch organisationSimpleSearch = orgObj.toOrganisationSimpleSearch();
                    if (organisationSimpleSearch.getShortName() != null
                            || organisationSimpleSearch.getLongName() != null
                            || organisationSimpleSearch.getCode() != null) {
                        pageManager.approvalsManagerPage.switchToOrganisationTab();
                        pageManager.approvalsManagerPage.searchOrganisation(organisationSimpleSearch);
                        pageManager.approvalsManagerPage.search();
                        pageManager.approvalsManagerPage.approveAllIfExist();
                    }
                }
            }
        }


//        for (String id : ids) {
//            Organisation organisation = testDataManager.getOrganisation(id);
//
//            FeedOrganisation feedOrganisation = organisation.toFeedOrganisation();
//
//            pageManager.welcomePage.navigate(Menu.VIEW_ORGANISATIONS);
//            OrganisationSearch organisationSearch = organisation.toOrganisationSearch();
//            if (!pageManager.viewOrganisationsPage.isOrganisationExist(organisationSearch)) {
//                pageManager.welcomePage.navigate(Menu.ADD_ORGANISATION);
//                pageManager.addOrganisationPage.addOrganisation(organisation);
//                pageManager.viewOrganisationsPage.editOrganisation(organisation);
//            }
//            testCase.embedTestData(organisation);
//        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /*
    @Given("^g - delete organisations? (\\S+)$")
    public void delete_organisation(List<String> ids) throws Exception {
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.DELETE_ORGANISATION);
            OrganisationSearch organisationSearch = testDataManager.getOrganisationSearch(id);
            pageManager.deleteOrganisationPage.deleteOrganisation(organisationSearch);
            testCase.embedTestData(organisationSearch);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }
    */

    /**
     * Description: prepare agreements<br/>
     * samples: have <b>OTC</b> agreement <b>my.agreement</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link Agreement}</li>
     * @throws Exception
     */
    @Given("^have (?:OTC|FCM|ETF|Repo|Umbrella) agreements? (\\S+)$")
    public void have_agreements(List<String> ids) throws Exception {
        ColAgreementServiceRemote agreementServiceRemote= RemoteEJBUtil.getColAgreementServiceRemote();
        for (String id : ids) {
            Agreement agreement = testDataManager.getAgreement(id);
            testCase.embedTestData(agreement);
            String externalId = agreement.getExternalId().getRealValue();
            ColAgreementHeaderData agreementHeaderData = agreementServiceRemote.getAgreementByExtId(externalId);
            if (agreementHeaderData == null) {
                pageManager.welcomePage.navigate(Menu.FEED_AGREEMENTS);
                pageManager.taskManagerPage.feedXmlFile(new Feed(), Arrays.asList(agreement.toFeedAgreement()));
                agreementHeaderData = agreementServiceRemote.getAgreementByExtId(externalId);
                if (Boolean.TRUE.equals(agreement.isUmbrellaAgreement()) && agreement.getSubAgreement() != null && !agreement.getSubAgreement().isEmpty()) {
                    long umbrellaRule = agreementHeaderData.getUmbrellaRule();
                    for (SubAgreementSearch subAgreementSearch : agreement.getSubAgreement()) {
                        ColAgreementHeaderData subAgreementHeadData;
                        if (SubAgreementSearchType.AGREEMENT_ID.equals(subAgreementSearch.getType())) {
                            subAgreementHeadData = agreementServiceRemote.getAgreement(Long.parseLong(subAgreementSearch.getFilter().getRealValue()));
                            subAgreementHeadData.setUmbrellaRule(umbrellaRule);
                            subAgreementHeadData.setUmbrellaType(1);
                            agreementServiceRemote.setAgreementValuesOnly(subAgreementHeadData);
                        }
                    }
                }
            }
            agreement.setAgreementId(new StringType(String.valueOf(agreementHeaderData.getId())));
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare rating levels<br/>
     * samples: have rating levels <b>my.rating.level</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link AgencyRating}</li>
     * @throws Exception
     */
    @Given("^have rating levels? (\\S+)$")
    public void have_rating_level(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.RATINGLEVEL);
            AgencyRating agencyRating = testDataManager.getAgencyRating(id);
            pageManager.ratingLevelPage.addRatingLevel(agencyRating);
            testCase.embedTestData(agencyRating);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare book<br/>
     * samples: have book <b>my.book</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link OrganisationBook}</li>
     * @throws Exception
     */
    @Given("^have books? (\\S+)$")
    public void have_books(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.BOOKS);
            OrganisationBook organisationBook = testDataManager.getOrganisationBook(id);
            pageManager.booksPage.addBooks(organisationBook);
            testCase.embedTestData(organisationBook);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare industry<br/>
     * samples: have industry <b>my.industry</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link OrganisationIndustry}</li>
     * @throws Exception
     */
    @Given("^have industry|ies (\\S+)$")
    public void have_industries(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.INDUSTRY);
            OrganisationIndustry organisationIndustry = testDataManager.getOrganisationIndustry(id);
            pageManager.industryPage.addIndustry(organisationIndustry);
            testCase.embedTestData(organisationIndustry);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare organisation static data<br/>
     * samples: have organisation static data <b>my.static.data</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Given("^have organisation static data (\\S+)$")
    public void have_organisation_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.ORGANISATIONS_STATIC_DATA);
            StaticData staticData = testDataManager.getOrganisationStaticData(id);
            if (!pageManager.organisationStaticDataPage.staticDataExist(staticData))
                pageManager.organisationStaticDataPage.addStaticData(staticData);
            testCase.embedTestData(staticData);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare FX rate<br/>
     * samples: have FX rate <b>my.fx.rate</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link FxRateSet}</li>
     * @throws Exception
     */
    @Given("^have FX rate(?: set)?s? (\\S+)$")
    public void have_fx_rate_sets(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.FX_RATES);
            FxRateSet fxRateSet = testDataManager.getFxRateSet(id);
            pageManager.fxRatesPage.addNewRateSet();
            pageManager.addNewRateSetPage.editNewRateSet(fxRateSet);
            pageManager.addNewRateSetPage.saveNewRateSet();
            pageManager.fxRatesPage.setNewFxRateSet(fxRateSet);
            pageManager.fxRatesPage.save();
            testCase.embedTestData(fxRateSet);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare security<br/>
     * samples: have security <b>my.security</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link Instrument}</li>
     * @throws Exception
     */
    @Given("^have securit(?:y|ies) (\\S+)$")
    public void have_securities(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.SECURITIES_DATA);
            Instrument instrument = testDataManager.getInstrument(id);

            SecuritySearch securitySearch = instrument.toSecuritySearch();
            securitySearch.setLock(LockType.ALL);
            securitySearch.setMatured(MatureType.ALL);
            pageManager.securitySearchPage.searchInstrument(securitySearch);

            SecuritySearchResult securitySearchResult = new SecuritySearchResult();
            securitySearchResult.setInstrument(new StringType(instrument.getInstrumentId().get(0).getValue().getRealValue()));

            if (!pageManager.securitySearchPage.isInstrumentExist(securitySearchResult)) {
                pageManager.securitySearchPage.newInstrument(instrument);
                pageManager.securityEditorPage.inputInstrument(instrument);
                pageManager.securityEditorPage.saveInstrument();
                if (instrument.getSecurityStatus() != null && instrument.getSecurityStatus().getRealValue().equalsIgnoreCase("Approved")) {
                    securitySearchResult = instrument.toSecuritySearchResult();
                    pageManager.securitySearchPage.editInstrument(securitySearchResult);
                    pageManager.securityEditorPage.approveInstrunment();
                }
            }
            testCase.embedTestData(instrument);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare trades for agreement<br/>
     * samples: have <b>OTC</b> trades <b>my.trade</b> to search <b>my.agreement.search</b> agreement <b>my.agreement.search.result</b><br/>
     *
     * @param tradeIds    can be one of following type:<br /><li>{@link TradeDetail}</li>
     * @param product     can be one of following type:<br /><li>{@link AgreementExposureSummary}</li>
     * @param searchAgrId can be one of following type:<br /><li>{@link AgreementSearch}</li>
     * @param agrId       can be one of following type:<br /><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @Given("^have (OTC|FCM|ETF|Repo) trades? (\\S+) under product type (\\S+) to search (\\S+) agreement (\\S+)$")
    public void have_trades(List<String> tradeIds, String product, String searchAgrId, String agrId) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        //Go to Search agreement page
        pageManager.welcomePage.navigate(Menu.AGREEMENT_SEARCH);

        //Search agreement
        AgreementSearch agreementSearch = testDataManager.getAgreementSearch(searchAgrId);
        pageManager.agreementSearchPage.searchAgreement(agreementSearch);
        testCase.embedTestData(agreementSearch);

        //Enter agreement statement
        AgreementSearchResult agreementSearchResult = testDataManager.getAgreementSearchResult(agrId);
        pageManager.agreementSearchPage.viewCompletedAgreementStatement(agreementSearchResult);

        //enter agreement exposure summary page
        pageManager.agreementStatementPage.editSummaryExposureInfo();
        //enter trade summary page
        testCase.embedTestData(agreementSearchResult);
        AgreementExposureSummary agreementExposureSummary = testDataManager.getAgreementExposureSummary(product);
        pageManager.agreementExposuresSummaryPage.enterTradeSummary(agreementExposureSummary);
        //Add trade
        for (String id : tradeIds) {
            TradeDetail trade = testDataManager.getTradeDetail(id);
            pageManager.tradeSummaryPage.addTrade();
            pageManager.tradeEditorPage.addTrade(trade);
            testCase.embedTestData(trade);
//            pageManager.tradeSummaryPage.backToExporuresSummaryPage();
        }

        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set start-up dashboard<br/>
     * samples: have start-up dashboard <b>my.start-up.dashboard</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link StartupDashboard}</li>
     * @throws Exception
     */
    @Given("^have start-up dashboard (\\S+)$")
    public void have_start_up_dashboard(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.STARTUP_DASHBOARD);
        StartupDashboard startupDashboard = testDataManager.getStartupDashboard(id);
        pageManager.startupDashboardPage.setStartupDashboard(startupDashboard);
        testCase.embedTestData(startupDashboard);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set stp filter definition<br/>
     * samples: have stp filter definition <b>my.stp.filter.definition</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have stp filter definitions? (\\S+)$")
    public void have_stp_filter_definitions(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.STP_FILTER_DEFINITION);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
        throw new PendingException();
    }

    /**
     * Description: set stp rule definition<br/>
     * samples: have stp rule definition <b>my.stp.definition</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have stp rule definitions? (\\S+)$")
    public void have_stp_rule_definitions(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.STP_RULE_DEFINITION);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
        throw new PendingException();
    }

    /**
     * Description: prepare business rule<br/>
     * samples: have business rule <b>my.business.rule</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have business rules? (\\S+)$")
    public void have_business_rules(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.STP_BUSINESS_RULES);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
        throw new PendingException();
    }

    /**
     * Description: set stp switch<br/>
     * samples: have stp switch <b>my.stp.Configurations</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link StpConfiguration}</li>
     * @throws Exception
     */
    @Given("^have stp switch (\\S+)$")
    public void have_stp_switch(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.STP_SWITCH);
        StpConfiguration stpConfiguration = testDataManager.getStpConfiguration(id);
        pageManager.stpConfigurationsPage.editStpConfiguation(stpConfiguration);
        pageManager.stpConfigurationsPage.save();
        testCase.embedTestData(stpConfiguration);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set stp configuration with default value<br/>
     * samples: have default stp configuration<br/>
     *
     * @throws Exception
     */
    @Given("^have default stp configuration$")
    public void have_default_stp_configuration() throws Exception {
        have_stp_switch("default.stp.configuration");
    }

    /**
     * Description: prepare asset class|category|type<br/>
     * samples: have asset <b>class</b> <b>my.asset.class</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link AssetDefinition}</li>
     * @throws Exception
     */
    @Given("^have asset (?:class(?:es)?|categor(?:y|ies)|types?) (\\S+)$")
    public void have_asset_class_category_type(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.ASSET_DEFINITION);
            PageHelper.d33640Workaround();
            AssetDefinition assetDefinition = testDataManager.getAssetDefinition(id);
            pageManager.assetDefinitionPage.addAsset(assetDefinition);
            pageManager.addEditAssetPage.editAsset(assetDefinition);
            pageManager.addEditAssetPage.save(assetDefinition.getAlertHandling());
            testCase.embedTestData(assetDefinition);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare interest rate<br/>
     * samples: have interest rate <b>my.interest.rate</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link InterestRate}</li>
     * @throws Exception
     */
    @Given("^have interest rates? (\\S+)$")
    public void have_interest_rates(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.INTEREST_RATES);
            InterestRate interestRate = testDataManager.getInterestRate(id);
            pageManager.interestRatesPage.inputInterestRate(interestRate);
            pageManager.interestRatesPage.addMember();
            if (interestRate.getDailyInterestRate() != null
                    && !interestRate.getDailyInterestRate().isEmpty()) {
                for (DailyInterestRate dailyInterestRate : interestRate.getDailyInterestRate()) {
                    pageManager.interestRatesPage.updateInterestRate(interestRate);
                    pageManager.interestRateMaintenancePage.addInterestRateMaintenance(dailyInterestRate);
                }
            }
            testCase.embedTestData(interestRate);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare collateral static data settlement data<br/>
     * samples: have collateral static data settlement data <b>my.settlement.data</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link SettlementData}</li>
     * @throws Exception
     */
    @Given("^have collateral static data settlement data (\\S+)$")
    public void have_collateral_static_data_settlement_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.SETTLEMENT_DATA);
        for (String id : ids) {
            SettlementData settlementData = testDataManager.getSettlementData(id);
            pageManager.settlementDataPage.addSettelmentData(settlementData);
            testCase.embedTestData(settlementData);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare umbrella rule<br/>
     * samples: have collateral static data umbrella rule <b>my.umbrella.rule</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link UmbrellaRule}</li>
     * @throws Exception
     */
    @Given("^have collateral static data umbrella rules? (\\S+)$")
    public void have_collateral_static_data_umbrella_rules(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.UMBRELLA_RULES);
        for (String id : ids) {
            UmbrellaRule umbrellaRule = testDataManager.getUmbrellaRule(id);
            pageManager.umbrellaRulesPage.addUmbrellaRule();
            pageManager.umbrellaRulesPage.inputUmbrellaRule(umbrellaRule);
            pageManager.umbrellaRulesPage.saveUmbrellaRule();
            testCase.embedTestData(umbrellaRule);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare TSA|Cashflow rule<br/>
     * samples: have tsa rule <b>my.tsa.rule</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link TsaRule}</li>
     * @throws Exception
     */
    @Given("^have (?:tsa|Cashflow) rules? (\\S+)$")
    public void have_tsa_rules(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.CASHFLOW_RULES);
        }

        pageManager.welcomePage.navigate(Menu.HOME);
        throw new PendingException();
    }

    /**
     * Description: prepare eligibility rule template <br/>
     * samples: have eligibility rule template <b>my.eliginility.rule.template</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link EligibilityRulesTemplate}</li>
     * @throws Exception
     */
    @Given("^have eligibility rule templates? (\\S+)$")
    public void have_eligibility_rule_templates(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.ELIGIBILITY_RULES_TEMPLATE);
            EligibilityRulesTemplate eligibilityRulesTemplate = testDataManager.getEligibilityRulesTemplate(id);
            pageManager.eligibilityRulesTemplatePage.searchEligibilityRulesTemplate(eligibilityRulesTemplate);
            boolean flag = pageManager.eligibilityRulesTemplatePage.isEligibilityRulesTemplateExists();
            if (flag) {
                pageManager.eligibilityRulesTemplatePage.setupEligibilityRulesTemplate(eligibilityRulesTemplate);
            }
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare collateral static data<br/>
     * samples: <br/>
     *
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Given("^have collateral static data (\\S+)$")
    public void have_collateral_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.COLLATERAL_STATIC_DATA);
        for (String id : ids) {
            StaticData staticData = testDataManager.getCollateralStaticData(id);
            if (!pageManager.collateralStaticDataPage.staticDataExist(staticData))
                pageManager.collateralStaticDataPage.addStaticData(staticData);
            testCase.embedTestData(staticData);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: enable all collateral static data for the specific category<br/>
     * samples: enable all udf.static collateral static data <br/>
     * @param id can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Given("^enable all (\\S+) collateral static data$")
    public void enable_all_collateral_static_data(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.COLLATERAL_STATIC_DATA);
        StaticData staticData = testDataManager.getCollateralStaticData(id);
        testCase.embedTestData(staticData);
        pageManager.collateralStaticDataPage.enableAllStaticDataInCategory(staticData);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare collateral static data agreement UDF value<br/>
     * samples: have collateral static data agreement udf value <b>my.udf.value</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have collateral static data agreement udf values (\\S+)$")
    public void have_collateral_static_data_agreement_udf_values(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: prepare users<br/>
     * samples: have user <b>my.user</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link User}</li>
     * @throws Exception
     */
    @Given("^have users? (\\S+)$")
    public void have_user(List<String> ids) throws Exception {
        pageManager.welcomePage.navigate(Menu.USER_ROLE_ADMINISTRATION);
        for (String id : ids) {
            User user = testDataManager.getUser(id);
            pageManager.userAndRoleAdministrationNavigationPannel.enterCreateNewUserPage();
            pageManager.createNewUserPage.inputNewUser(user);
            pageManager.createNewUserPage.submit();
            pageManager.userAndRoleAdministrationNavigationPannel.enterEditProfileForUserPage(user);
            pageManager.editProfileForUserPage.enterEditUserRolesPage();
            pageManager.editUserRolesPage.editUserRoles(user);
            pageManager.editUserRolesPage.save();
            pageManager.editProfileForUserPage.enterEditApprovalProfilePage();
            pageManager.editApprovalProfilePage.editApprovalProfile(user);
            pageManager.editApprovalProfilePage.save();
            pageManager.editProfileForUserPage.enterEditUserPreferencrPage();
            pageManager.editUserPreferencePage.editUserPreference(user);
            pageManager.editUserPreferencePage.submit();
            pageManager.editProfileForUserPage.setAccessStatus(user);
            pageManager.editProfileForUserPage.setChangePasswordConstraints(user);
            if (user.getChangePassword() != null) {
                pageManager.editProfileForUserPage.setChangePassword(user);
                pageManager.editProfileForUserPage.savePassword();
            }
            testCase.embedTestData(user);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare administer role<br/>
     * samples: have administer role <b>my.administer.role</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link AdministerRole}</li>
     * @throws Exception
     */
    @Given("^have administer roles? (\\S+)$")
    public void have_administer_roles(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.USER_ROLE_ADMINISTRATION);
        pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerRolesPage();
        for (String id : ids) {
            AdministerRole administerRole = testDataManager.getAdministerRole(id);
            pageManager.administerRolesPage.setRoleName(administerRole);
            pageManager.administerRolesPage.submit();
            testCase.embedTestData(administerRole);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare administer approval profile<br/>
     * samples: have administer approval profiles <b>my.approval.profile</b><br/>
     *
     * @param id can be one of following type:<br /><li>{@link ApprovalProfile}</li>
     * @throws Exception
     */
    @Given("^have administer approval profiles (\\S+)$")
    public void have_administer_approval_profiles(String id) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.USER_ROLE_ADMINISTRATION);
        pageManager.userAndRoleAdministrationNavigationPannel.enterAdministerApprovalProfilesPage();
        ApprovalProfile approvalProfile = testDataManager.getApprovalProfile(id);
        pageManager.administerApprovalProfilesPage.setApprovalProfile(approvalProfile);
        pageManager.administerApprovalProfilesPage.submit();
        testCase.embedTestData(approvalProfile);
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: set privilege<br/>
     * samples: have privilege <b>my.privilege</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have privileges? (\\S+)$")
    public void have_privileges(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: prepare system static data<br/>
     * samples: have system static data <b>my.static.data</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link StaticData}</li>
     * @throws Exception
     */
    @Given("^have system static data (\\S+)$")
    public void have_system_static_data(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        for (String id : ids) {
            pageManager.welcomePage.navigate(Menu.SCHEMES);
            StaticData staticData = testDataManager.getSystemStaticData(id);
            pageManager.schemesPage.addStaticData(staticData);
            testCase.embedTestData(staticData);
        }
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: prepare holiday centre<br/>
     * samples: have holiday centre <b>my.holiday.centre</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have holiday centres? (\\S+)$")
    public void have_holiday_centres(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: prepare region<br/>
     * samples: have region <b>my.region</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have regions? (\\S+)$")
    public void have_regions(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: prepare business frequency<br/>
     * samples: have business frequency <b>my.business.frequency</b><br/>
     *
     * @param ids can be one of following type:<br /><li>{@link }</li>
     * @throws Exception
     */
    @Given("^have business frequenc(?:y|ies)? (\\S+)$")
    public void have_business_frequency(List<String> ids) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    /**
     * Description: prepare booking for agreement<br/>
     * samples: have <b>call</b> booking - statement booking <b>my.booking</b> for agreement <b>my.agreement</b><br/>
     *
     * @param bookingIds  can be one of following type:<br /><li>{@link BookingDetail}</li>
     * @param agreementId can be one of following type:<br /><li>{@link Agreement}</li><li>{@link AgreementSearch}</li><li>{@link AgreementSearchResult}</li>
     * @throws Exception
     */
    @Given("^have (?:call|return|delivery|recall) booking - statement bookings? (\\S+) for agreement (\\S+)$")
    public void have_bookings_statement_booking_for_agreement(List<String> bookingIds, String agreementId) throws Exception {
        Agreement agreement = testDataManager.getAgreement(agreementId);
        AgreementSearch agreementSearch = new AgreementSearch();
        AgreementSearchResult agreementSearchResult = new AgreementSearchResult();

        if (agreement.getPrincipal() != null && !agreement.getPrincipal().getLinkText().getRealValue().isEmpty()) {
            agreementSearch.setPrincipal(agreement.getPrincipal());
            agreementSearchResult.setPrincipal(agreement.getPrincipal().getLinkText());
        }
        if (agreement.getCounterparty() != null && !agreement.getCounterparty().getLinkText().getRealValue().isEmpty()) {
            agreementSearch.setCounterparty(agreement.getCounterparty());
            agreementSearchResult.setCounterparty(agreement.getCounterparty().getLinkText());
        }
        if (agreement.getAgreementId() != null && !agreement.getAgreementId().getRealValue().isEmpty()) {
            agreementSearch.setSystemId(agreement.getAgreementId());
            agreementSearchResult.setAgreementId(agreement.getAgreementId());
        }
        if (agreement.getAgreementDescription() != null && !agreement.getAgreementDescription().getRealValue().isEmpty()) {
            SimpleSearch simpleSearch = new SimpleSearch();
            simpleSearch.setFilter(agreement.getAgreementDescription());
            simpleSearch.setLinkText(agreement.getAgreementDescription());
            agreementSearch.setDescription(simpleSearch);
            agreementSearchResult.setDescription(agreement.getAgreementDescription());
        }
        if (agreement.getExternalId() != null && !agreement.getExternalId().getRealValue().isEmpty()) {
            SimpleSearch simpleSearch = new SimpleSearch();
            simpleSearch.setFilter(agreement.getExternalId());
            simpleSearch.setLinkText(agreement.getExternalId());
            agreementSearch.setExternalId(simpleSearch);
        }

        pageManager.welcomePage.navigate(Menu.AGREEMENT_SEARCH);
        pageManager.agreementSearchPage.searchAgreement(agreementSearch);
        pageManager.agreementSearchPage.viewCompletedAgreementStatement(agreementSearchResult);
        for (String bookingId : bookingIds) {
            BookingDetail bookingDetail = testDataManager.getBookingDetail(bookingId);
            pageManager.agreementStatementPage.editAssetSummaryInfo();
            pageManager.agreementAssetHoldingsSummaryPage.enterAgreementAssetsSummary(bookingDetail);
            pageManager.agreementAssetsSummaryPage.enterBookingEditor(bookingDetail);
            pageManager.agreementAssetsEditorPage.makeBooking(bookingDetail);
            pageManager.agreementAssetsSummaryPage.viewStatement();
            testCase.embedTestData(bookingDetail);
        }
        testCase.embedTestData(agreement);
        testCase.embedTestData(agreementSearch);
        testCase.embedTestData(agreementSearchResult);

        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: Approval all non-approved statements if there are<br/>
     * samples: approvals manager - approval all statements</b><br/>
     *
     * @throws Exception
     */
    @Given("^approve all statements in approvals manager$")
    public void approvals_manager_approve_all_statements() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        pageManager.welcomePage.navigate(Menu.APPROVALS_MANAGER);
        pageManager.approvalsManagerPage.switchToStatementsTab();
        pageManager.approvalsManagerPage.search();
        pageManager.approvalsManagerPage.approveAllIfExist();
        pageManager.welcomePage.navigate(Menu.HOME);
    }

    /**
     * Description: Prepare a server folder location<br/>
     * samples: have server folder location <b>server.folder</b><br/>
     * @param id  can be one of following type:<br /><li>{@link StringType}</li>
     * @throws Exception
     */
    @Given("^have server folder location (\\S+)$")
    public void have_server_folder_location(String id) throws Exception {
        StringType stringType = testDataManager.getVariable(id);
        testCase.embedTestData(stringType);
        Biz.createFolderBySSH(testCase, stringType.getRealValue(), testCase.getTestEnvironment().getApplicationServers().get(0).getHost());
    }

}
