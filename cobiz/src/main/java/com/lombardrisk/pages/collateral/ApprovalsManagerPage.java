package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import org.openqa.selenium.WebDriverException;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author Yan Lu
 */
public class ApprovalsManagerPage extends PageBase {

    public ApprovalsManagerPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void search() throws Exception {
        element("am.search").clickByJavaScript();
    }

    public void clearFilter() throws Exception {
        element("am.clear").clickByJavaScript();
    }

    /**
     * approve all ticked search results
     *
     * @throws Exception
     */
    public void approveTickedSearchResults() throws Exception {
        element("am.approve.ticked").click();
    }

    public void approveAllSearchResults(List<Message> handles) throws Exception {
        approveAll();
        for (Message handle : handles) {
            if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
                if (alert().getText().matches(handle.getContent().getRealValue())) {
                    if (handle.isAccept())
                        alert().accept();
                    else
                        alert().dismiss();
                } else {
                    throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
                }
            } else if (alert().isPresent()) {
                if (handle.isAccept()) {
                    alert().accept();
                } else {
                    alert().dismiss();
                }
            }
        }
    }

    public void approveAllSearchResults(Message handle) throws Exception {
        approveAll();
        if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
            if (alert().getText().matches(handle.getContent().getRealValue())) {
                if (handle.isAccept())
                    alert().accept();
                else
                    alert().dismiss();
            } else {
                throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
            }
        } else if (alert().isPresent()) {
            if (handle.isAccept()) {
                alert().accept();
            } else {
                alert().dismiss();
            }
        }
    }

    private void approveAll() throws Exception {
        element("am.approve.all").click();
    }

    private void rejectTickedSearchResults() throws Exception {
        element("am.reject.ticked").click();
    }

    public void rejectTickedSearchResults(List<Message> handles) throws Exception {
        rejectTickedSearchResults();
        for (Message handle : handles) {
            if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
                if (alert().getText().matches(handle.getContent().getRealValue())) {
                    if (handle.isAccept())
                        alert().accept();
                    else
                        alert().dismiss();
                } else {
                    throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
                }
            } else if (alert().isPresent()) {
                if (handle.isAccept()) {
                    alert().accept();
                } else {
                    alert().dismiss();
                }
            }
        }
    }

    public void rejectTickedSearchResults(Message handle) throws Exception {
        rejectTickedSearchResults();
        if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
            if (alert().getText().matches(handle.getContent().getRealValue())) {
                if (handle.isAccept())
                    alert().accept();
                else
                    alert().dismiss();
            } else {
                throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
            }
        } else if (alert().isPresent()) {
            if (handle.isAccept()) {
                alert().accept();
            } else {
                alert().dismiss();
            }
        }
    }


    /**
     * search Organisation in approvals manager page
     *
     * @param search
     * @author Yan Lu
     */
    public void searchOrganisation(OrganisationSimpleSearch search) throws Exception {
        if (search.getLongName() != null) {
            if (search.getLongName().getType() != null)
                element("am.ln.type").selectByVisibleText(search.getLongName().getType().value());
            if (search.getLongName().getFilter() != null)
                element("am.ln.filter").input(search.getLongName().getFilter().getRealValue());
        }
        if (search.getShortName() != null) {
            if (search.getShortName().getType() != null)
                element("am.sn.type").selectByVisibleText(search.getShortName().getType().value());
            if (search.getShortName().getFilter() != null)
                element("am.sn.filter").input(search.getShortName().getFilter().getRealValue());
        }
        if (search.getCode() != null) {
            if (search.getCode().getType() != null)
                element("am.cd.type").selectByVisibleText(search.getCode().getType().value());
            if (search.getCode().getFilter() != null)
                element("am.cd.filter").input(search.getCode().getFilter().getRealValue());
        }
        if (search.getStatus() != null)
            element("am.status").selectByVisibleText(search.getStatus().value());

    }

    /**
     * search agreement in approvals manager page
     *
     * @throws Exception
     * @author yan lu
     */
    public void searchAgreement(AgreementSearch as) throws Exception {
        clearFilter();
        //set principal
        if (as.getPrincipal() != null) {
            element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(as.getPrincipal());
            switchTo().defaultContent();
        }
        //set counterparty
        if (as.getCounterparty() != null) {
            element("ap.search.cpty.new").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(as.getCounterparty());
            switchTo().defaultContent();
        }
        //set description
        if (as.getDescription() != null) {
            if (as.getDescription().getType() != null) {
                element("am.agreementSearch.descType").selectByVisibleText(as.getDescription().getType().value());
            }
            if (as.getDescription().getFilter() != null) {
                element("am.agreementSearch.descFilter").input(as.getDescription().getFilter().getRealValue());
            }
        }
        //set umbrella rule name
        if (as.getUmbrellaRuleName() != null) {
            for (StringType umbrellaRuleName : as.getUmbrellaRuleName()) {
                element("am.agreementSearch.umbrellaRuleName").selectByVisibleText(umbrellaRuleName.getRealValue());
            }
        }
        //set status
        if (as.getStatus() != null) {
            if (as.getStatus().size() > 0) {
                element("am.agreementSearch.status").deselectAll();
                for (int i = 0; i < as.getStatus().size(); i++) {
                    element("am.agreementSearch.status").selectByVisibleText(as.getStatus().get(i).value());
                }
            }
        }
        //tick include sub agreement
        if (as.isIncludeSubAgreement() != null) {
            if (as.isIncludeSubAgreement()) {
                element("am.agreementSearch.includeSubAgr").check(as.isIncludeSubAgreement());
            }
        }
        //set system id
        if (as.getSystemId() != null) {
            element("am.agreementSearch.systemId").input(as.getSystemId().getRealValue());
        }
        //set external id
        if (as.getExternalId() != null) {
            if (as.getExternalId().getType() != null) {
                element("am.agreementSearch.external.id.type").selectByVisibleText(as.getExternalId().getType().value());
            }
            if (as.getExternalId().getFilter() != null) {
                element("am.workflowSearch.externalIdFilter").input(as.getExternalId().getFilter().getRealValue());
            }
        }
        if (as.getAdvancedSearch() != null) {
            element("am.agreementSearch.arrowIcon").click();
            setAdvancedSearch(as);
        }

    }

    /**
     * search statement in approvals manager page
     *
     * @param as
     * @throws Exception
     * @author yan lu
     */
    public void searchStatement(AgreementSearch as) throws Exception {
        searchAgreement(as);
    }

    /**
     * search workflow in approvals manager page
     *
     * @param as
     * @throws Exception
     * @author yan lu
     */
    public void searchWorkflow(AgreementSearch as) throws Exception {
        searchAgreement(as);
    }

    /**
     * search settlement instructions in approvals manager page
     *
     * @param sis
     * @throws Exception
     * @author yan lu
     */
    public void searchSettlementInstructions(SettlementInstructionsSearch sis) throws Exception {
//		clearFilter();
        if (sis.getBusinessLine() != null) {
            element("am.settlementInstructions.businessLine").selectByVisibleText(sis.getBusinessLine().value());
        }
    }

    public void searchTrade(ApprovalManagerTradesSearch approvalManagerTradesSearch) throws Exception {
        clearFilter();
        if (approvalManagerTradesSearch.getPrincipal() != null) {
            element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(approvalManagerTradesSearch.getPrincipal());
            switchTo().defaultContent();
        }
        if (approvalManagerTradesSearch.getCounterparty() != null) {
            element("ap.search.cpty.new").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(approvalManagerTradesSearch.getCounterparty());
            switchTo().defaultContent();
        }
        if (approvalManagerTradesSearch.getSystemId() != null)
            element("am.agreementSearch.systemId").input(approvalManagerTradesSearch.getSystemId().getRealValue());
        if (approvalManagerTradesSearch.getProductCategory() != null && !approvalManagerTradesSearch.getProductCategory().isEmpty()) {
            element("am.trade.search.product.category").deselectAll();
            for (ProductCategoryType productCategory : approvalManagerTradesSearch.getProductCategory()) {
                element("am.trade.search.product.category").selectByVisibleText(productCategory.value());
            }
        }
        if (approvalManagerTradesSearch.getExternalId() != null) {
            SimpleSearch simpleSearch = approvalManagerTradesSearch.getExternalId();
            if (simpleSearch.getType() != null) {
                element("am.agreementSearch.external.id.type").selectByVisibleText(simpleSearch.getType().value());
            }
            if (simpleSearch.getFilter() != null) {
                element("am.workflowSearch.externalIdFilter").input(simpleSearch.getFilter().getRealValue());
            }
        }
        if (approvalManagerTradesSearch.getDescription() != null) {
            SimpleSearch simpleSearch = approvalManagerTradesSearch.getDescription();
            if (simpleSearch.getType() != null) {
                element("am.agreementSearch.descType").selectByVisibleText(simpleSearch.getType().value());
            }
            if (simpleSearch.getFilter() != null) {
                element("am.agreementSearch.descFilter").input(simpleSearch.getFilter().getRealValue());
            }
        }
        if (approvalManagerTradesSearch.getProductType() != null)
            element("am.trade.search.product.type").selectByVisibleText(approvalManagerTradesSearch.getProductType().getRealValue());
        if (approvalManagerTradesSearch.getTradeIdentifierPartyA() != null) {
            SimpleSearch simpleSearch = approvalManagerTradesSearch.getTradeIdentifierPartyA();
            if (simpleSearch.getType() != null)
                element("am.trade.search.trade.indentifier.party.a.type").selectByVisibleText(simpleSearch.getType().value());
            if (simpleSearch.getFilter() != null)
                element("am.trade.search.trade.indentifier.party.a.value").input(simpleSearch.getFilter().getRealValue());
        }
        if (approvalManagerTradesSearch.getAdvancedSearch() != null) {
            element("am.agreementSearch.arrowIcon").click();
            setAdvancedSearch(approvalManagerTradesSearch.getAdvancedSearch());
        }
    }

    public void searchSecuritiesData(SecuritySearch ss) throws Exception {
        clearFilter();
        //set asset class
        if (ss.getAssetClass() != null) {
            element("am.securitiesData.assetClass").selectByVisibleText(ss.getAssetClass().getRealValue());
        }
        //set asset type
        if (ss.getAssetType() != null) {
            element("am.securitiesData.assetType").selectByVisibleText(ss.getAssetType().getRealValue());
        }
        //set market
        if (ss.getMarket() != null) {
            if (ss.getMarket().getType() != null) {
                element("am.securitiesData.marketType").selectByVisibleText(ss.getMarket().getType().value());
            }
            if (ss.getMarket().getFilter() != null) {
                element("am.securitiesData.marketFilter").input(ss.getMarket().getFilter().getRealValue());
            }
        }
        //set description
        if (ss.getDescription() != null) {
            if (ss.getDescription().getType() != null) {
                element("am.securitiesData.descType").selectByVisibleText(ss.getDescription().getType().value());
            }
            if (ss.getDescription().getFilter() != null) {
                element("am.securitiesData.descFilter").input(ss.getDescription().getFilter().getRealValue());
            }
        }
        //set instrument
        if (ss.getInstrument() != null) {
            if (ss.getInstrument().getType() != null) {
                element("am.securitiesData.instrumentType").selectByVisibleText(ss.getInstrument().getType().value());
            }
            if (ss.getInstrument().getFilter() != null) {
                element("am.securitiesData.instrumentFilter").input(ss.getInstrument().getFilter().getRealValue());
            }
        }
        if (ss.getInstrumentIdType() != null) {
            element("am.securitiesData.instrumentIdType").selectByVisibleText(ss.getInstrumentIdType().value());
        }

    }

    /**
     * set eligibility rule template
     *
     * @throws Exception
     * @author yan lu
     */
    public void searchEligibilityRuleTemplate(EligibilityRulesTemplateSimpleSearch erss) throws Exception {
        //set eligibility rules template name
        clearFilter();
        if (erss.getEligibilityRulesTemplateName() != null) {
            if (erss.getEligibilityRulesTemplateName().getFilter() != null) {
                element("am.eligibilityRuleTemp.nameFilter").input(erss.getEligibilityRulesTemplateName().getFilter().getRealValue());
            }
            if (erss.getEligibilityRulesTemplateName().getLinkText() != null) {
                element("am.eligibilityRuleTemp.nameLinktext", erss.getEligibilityRulesTemplateName().getLinkText().getRealValue()).click();
            }
        }
        //set status
        if (erss.getStatus() != null) {
            if (erss.getStatus().size() > 0) {
                element("am.eligibilityRuleTemp.status").deselectAll();
                for (int i = 0; i < erss.getStatus().size(); i++) {
                    element("am.eligibilityRuleTemp.status").selectByVisibleText(erss.getStatus().get(i).getRealValue());
                }
            }
        }
    }


    /**
     * set advanced search conditions
     *
     * @throws Exception
     * @author yan lu
     */
    public void setAdvancedSearch(AgreementSearch search) throws Exception {
        if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0) {
            for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
                if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null) {
                    element("am.agreementSearch.ccpFilter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null) {
                    element("am.agreementSearch.ccpLinktext", search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0) {
            for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
                if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null) {
                    element("am.agreementSearch.linkageSet.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null) {
                    element("am.agreementSearch.linkageSet.linktext", search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0) {
            for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
                if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null) {
                    element("am.agreementSearch.statementSetFilter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null) {
                    element("am.agreementSearch.statementSet.linktext", search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0) {
            for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
                element("am.agreementSearch.businessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
            }
        }
    }


    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getCriteria() != null)
            element("ap.frame.criteria").selectByVisibleText(search.getCriteria().value());
        if (search.getType() != null)
            element("ap.frame.type").selectByVisibleText(search.getType().value());
        if (search.getFilter() != null)
            element("ap.frame.filter").input(search.getFilter().getRealValue());
        element("ap.frame.search").click();

        if (search.getLinkText() != null)
            element("ap.frame.link", search.getLinkText().getRealValue()).click();
    }


    public void enterAgreementReview(AgreementSearchResult result) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (result.getStatus() != null) {
            xpath.append("[td='").append(result.getStatus().getRealValue()).append("']");
        }
        if (result.getDescription() != null) {
            xpath.append("[td='").append(result.getDescription().getRealValue()).append("']");
        }
        if (result.getBusinessLine() != null) {
            xpath.append("[td='").append(result.getBusinessLine().getRealValue()).append("']");
        }
        if (result.getAgreementId() != null) {
            xpath.append("[td='").append(result.getAgreementId().getRealValue()).append("']");
        }
        if (result.getExternalId() != null) {
            xpath.append("[td='").append(result.getExternalId().getRealValue()).append("']");
        }
        element("am.agreement.review", xpath.toString()).click();
    }


    public void switchToOrganisationTab() throws Exception {
        logger.debug("click Agreements Tab To Enter Agreements Page");
        element("am.organisation.tab").clickByJavaScript();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    /**
     * switch To Agreements Page when you in approvals Manager page
     *
     * @author Yan Lu
     */
    public void switchToAgreementsTab() throws Exception {
        logger.debug("click Agreements Tab To Enter Agreements Page");
        element("am.agreements.tab").clickByJavaScript();
    }

    /**
     * switch To Statements Page when you in approvals Manager page
     *
     * @author Yan Lu
     */
    public void switchToStatementsTab() throws Exception {
        logger.debug("click Statements Tab To Enter Statements Page");
        element("am.statements.tab").clickByJavaScript();
    }

    public void switchToWorkfolwTab() throws Exception {
        element("am.workflow.tab").clickByJavaScript();
    }

    public void switchToSettlementInstructionsTab() throws Exception {
        element("am.settlementInstructions.tab").clickByJavaScript();
    }


    public void switchToSecuritiesDataTab() throws Exception {
        logger.debug("click Securities Data Tab To Enter Securities Data Page");
        element("am.securitiesdata.tab").clickByJavaScript();
    }

    public void switchSecuritiesDataTab() throws Exception {
        element("am.securitiesdata.tab").clickByJavaScript();
    }

    public void switchToEligibilityRulesTemplateTab() throws Exception {
        element("am.eligibilityRulesTemplate.tab").clickByJavaScript();
    }

    public void switchToTradeTab() throws Exception {
        element("am.tarde.tab").clickByJavaScript();
    }


    /**
     * tick the organisation in approvals Manager page
     *
     * @param result
     * @author Yan Lu
     */
    public void tickOrganisationSearchResult(OrganisationSearchResult result,Boolean selectTick) throws Exception {
        logger.debug("approve the organisation in approvals manager page");
        StringBuffer xpath = new StringBuffer("//tr");
        if (result.getLongName() != null) {
            xpath.append("[td/a='").append(result.getLongName().getRealValue()).append("']");
        }
        if (result.getShortName() != null) {
            xpath.append("[td='").append(result.getShortName().getRealValue()).append("']");
        }
        if (result.getCode() != null) {
            xpath.append("[td='").append(result.getCode().getRealValue()).append("']");
        }
        if (result.getStatus() != null) {
            xpath.append("[td='").append(result.getStatus().value()).append("']");
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (result.isTickAll() != null) {
            if (result.isTickAll()) {
                element("am.approve.tickAllOrgResults").check(result.isTickAll());
            }
        }
    }

    public void tickOrganisationSearchResults(List<OrganisationSearchResult> list, Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (OrganisationSearchResult result : list) {
                tickOrganisationSearchResult(result,selectTick);
            }
        }
    }


    /**
     * tick the agreement in approvals manager page
     * by description or agreement id or external id
     *
     * @param result
     * @author Yan Lu
     */
    public void tickAgreementSearchResult(AgreementSearchResult result, Boolean selectTick) throws Exception {
        logger.debug("approve the agreement in approvals manager page");
        StringBuffer xpath = new StringBuffer("//tr");
        if (result.getDescription() != null)
            xpath.append("[td='").append(result.getDescription().getRealValue()).append("']");
        if (result.getAgreementId() != null)
            xpath.append("[td='").append(result.getAgreementId().getRealValue()).append("']");
        if (result.getExternalId() != null)
            xpath.append("[td='").append(result.getExternalId().getRealValue()).append("']");
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (result.isTickAll() != null) {
            if (result.isTickAll()) {
                element("am.approve.tickAllAgreementResults").check(result.isTickAll());
            }
        }
    }

    public void tickAgreementSearchResults(List<AgreementSearchResult> list,Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (AgreementSearchResult result : list) {
                tickAgreementSearchResult(result, selectTick);
            }
        }
    }

    /**
     * tick the statement in approvals manager page by
     * action or description or business line  or agreement id
     *
     * @param result
     * @author Yan Lu
     */
    public void tickStatementsSearchResult(AgreementSearchResult result, Boolean selectTick) throws Exception {
        logger.debug("approve the statement in approvals manager page");
        StringBuffer xpath = new StringBuffer("//tr");
        if (result.getAction() != null) {
            xpath.append("[td='").append(result.getAction().getRealValue()).append("']");
        }
        if (result.getDescription() != null) {
            xpath.append("[td='").append(result.getDescription().getRealValue()).append("']");
        }
        if (result.getBusinessLine() != null) {
            xpath.append("[td='").append(result.getBusinessLine().getRealValue()).append("']");
        }
        if (result.getAgreementId() != null) {
            xpath.append("[td/a='").append(result.getAgreementId().getRealValue()).append("']");
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (result.isTickAll() != null) {
            if (result.isTickAll()) {
                element("am.approve.tickAllStatementResults").check(result.isTickAll());
            }
        }
    }

    public void tickStatementsSearchResults(List<AgreementSearchResult> list,Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (AgreementSearchResult result : list) {
                tickStatementsSearchResult(result, selectTick);
            }
        }
    }

    /**
     * tick workflow in approval manager page
     *
     * @param wsr
     * @throws Exception
     * @author yan lu
     */
    public void tickWorkflowSearchResult(WorkflowSearchResult wsr, Boolean selectTick) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (wsr.getCounterparty() != null) {
            xpath.append("[td/a='").append(wsr.getCounterparty().getRealValue()).append("']");
        }
        if (wsr.getDescription() != null) {
            xpath.append("[td='").append(wsr.getDescription().getRealValue()).append("']");
        }
        if (wsr.getBusinessLine() != null) {
            xpath.append("[td='").append(wsr.getBusinessLine().getRealValue()).append("']");
        }
        if (wsr.getWorkflowStatus() != null) {
            xpath.append("[td='").append(wsr.getWorkflowStatus().getRealValue()).append("']");
        }
        if (wsr.getAction() != null) {
            xpath.append("[td='").append(wsr.getAction().getRealValue()).append("']");
        }
        if (wsr.getCallStatus() != null) {
            xpath.append("[td='").append(wsr.getCallStatus().getRealValue()).append("']");
        }
        if (wsr.getUserAgreedAmount() != null)
            xpath.append("[td/font='").append(wsr.getUserAgreedAmount().getRealValue()).append("']");
        if (wsr.getAgreementId() != null) {
            xpath.append("[td='").append(wsr.getAgreementId().getRealValue()).append("']");
        }
        if (wsr.getExternalId() != null) {
            xpath.append("[td='").append(wsr.getExternalId().getRealValue()).append("']");
        }
        if (wsr.getComment() != null) {
            xpath.append("[td='").append(wsr.getComment().getRealValue()).append("']");
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (wsr.isTickAll() != null) {
            if (wsr.isTickAll()) {
                element("am.approve.tickAllWorkflowResults").check(wsr.isTickAll());
            }
        }
    }

    public void tickWorkflowSearchResults(List<WorkflowSearchResult> list, Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (WorkflowSearchResult result : list) {
                tickWorkflowSearchResult(result,selectTick);
            }
        }
    }


    /**
     * tick settlement instructions in approvals page by
     * agreement id or asset class or asset type or bucked or last updated
     *
     * @param sisr
     * @throws Exception
     * @author yan lu
     */
    public void tickSettlementInstructionsSearchResult(SettlementInstructionsSearchResult sisr, Boolean selectTick) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (sisr.getAgreementId() != null) {
            xpath.append("[td='").append(sisr.getAgreementId().getRealValue()).append("']");
        }
        if (sisr.getAssetClass() != null) {
            xpath.append("[td='").append(sisr.getAssetClass().getRealValue()).append("']");
        }
        if (sisr.getAssetType() != null) {
            xpath.append("[td='").append(sisr.getAssetType().getRealValue()).append("']");
        }
        if (sisr.getBucket() != null) {
            xpath.append("[td='").append(sisr.getBucket().getRealValue()).append("']");
        }
        if (sisr.getLastUpdated() != null) {
            xpath.append("[td='").append(sisr.getLastUpdated().getRealValue()).append("']");
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (sisr.isTickAll() != null) {
            if (sisr.isTickAll()) {
                element("am.approve.tickAllSettlementInstructionsSearchResults").check(sisr.isTickAll());
            }
        }
    }

    public void tickSettlementInstructionsSearchResults(List<SettlementInstructionsSearchResult> list, Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (SettlementInstructionsSearchResult result : list) {
                tickSettlementInstructionsSearchResult(result, selectTick);
            }
        }
    }

    /**
     * tick the ticked security data in approval manager page by
     * assetClass or assetType or description or last updated
     *
     * @param ssr
     * @throws Exception
     * @author yan lu
     */
    public void tickSecuritiesDataSearchResult(SecuritySearchResult ssr, Boolean selectTick) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (ssr.getAssetClass() != null) {
            xpath.append("[td='").append(ssr.getAssetClass().getRealValue()).append("']");
        }
        if (ssr.getAssetType() != null) {
            xpath.append("[td='").append(ssr.getAssetType().getRealValue()).append("']");
        }
        if (ssr.getDescription() != null) {
            xpath.append("[td='").append(ssr.getDescription().getRealValue()).append("']");
        }
        if (ssr.getLastUpdated() != null) {
            xpath.append("[td='").append(ssr.getLastUpdated().getRealValue()).append("']");
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (ssr.isTickAll() != null) {
            if (ssr.isTickAll()) {
                element("am.approve.tickAllSecuritiesDataSearchResults").check(ssr.isTickAll());
            }
        }
    }

    public void tickSecuritiesDataSearchResults(List<SecuritySearchResult> list, Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (SecuritySearchResult result : list) {
                tickSecuritiesDataSearchResult(result, selectTick);
            }
        }
    }

    /**
     * tick eligibility rules template in approvals page or set reject reason
     * by eligibility rule template name or status
     *
     * @param ertsr
     * @throws Exception
     * @author yan lu
     */
    public void tickEligibilityRulesTemplateSearchRusult(EligibilityRulesTemplateSearchResult ertsr, Boolean selectTick) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (ertsr.getEligibilityRulesTemplateName() != null) {
//    		xpath.append("[td/a='" + ertsr.getEligibilityRulesTemplateName().getRealValue() + "']");
            xpath.append("[(td[normalize-space(.)='").append(ertsr.getEligibilityRulesTemplateName().getRealValue()).append("']) or (td/a[normalize-space(.)='").append(ertsr.getEligibilityRulesTemplateName().getRealValue()).append("'])]");
            //tr[td/a='luyan20160113-1']//input[@id='ticked']
        }
        if (ertsr.getStatus() != null) {
            xpath.append("[td='").append(ertsr.getStatus().getRealValue()).append("']");
        }
        if (ertsr.getRejectReason() != null) {
            element("am.result.rejectReason", xpath.toString()).input(ertsr.getRejectReason().getRealValue());
        }
        element("am.result.cb", xpath.toString()).check(selectTick);
        if (ertsr.isTickAll() != null) {
            if (ertsr.isTickAll()) {
                element("am.approve.tickAllEligibilityRulesTemplateSearchResults").check(ertsr.isTickAll());
            }
        }
    }

    public void tickTradeResult(ApprovalManagerTradesSearchResult approvalManagerTradesSearchResult, Boolean selectTick) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = approvalManagerTradesSearchResult.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(StringType.class)
                    && method.getParameterTypes().length == 0
                    && method.invoke(approvalManagerTradesSearchResult) != null) {
                StringType value = (StringType) method.invoke(approvalManagerTradesSearchResult);
                xpath.append("[(td[normalize-space(.)='").append(value.getRealValue()).append("']) or (td/a[normalize-space(.)='").append(value.getRealValue()).append("']) or (td/font[normalize-space(.)='").append(value.getRealValue()).append("'])]");
            }
        }
        if (xpath.length() != 0) {
            xpath.append("//input[@id='ticked']");
        }
        element("am.search.result.row", xpath.toString()).check(selectTick);
    }

    public void tickEligibilityRulesTemplateSearchRusults(List<EligibilityRulesTemplateSearchResult> list, Boolean selectTick) throws Exception {
        if (list != null && list.size() > 0) {
            for (EligibilityRulesTemplateSearchResult result : list) {
                tickEligibilityRulesTemplateSearchRusult(result, selectTick);
            }
        }
    }

    /**
     * view changes
     *
     * @param ertsr
     * @throws Exception
     */
    public void viewChangesEligibilityRulesTemplate(EligibilityRulesTemplateSearchResult ertsr) throws Exception {
        StringBuffer xpath = new StringBuffer("//tr");
        if (ertsr.getEligibilityRulesTemplateName() != null) {
            xpath.append("[td/a='").append(ertsr.getEligibilityRulesTemplateName().getRealValue()).append("']");
            //tr[td/a='luyan20160113-1']//input[@id='ticked']
        }
        if (ertsr.getStatus() != null) {
            xpath.append("[td='").append(ertsr.getStatus().getRealValue()).append("']");
        }
        if (ertsr.getRejectReason() != null) {
            element("am.result.rejectReason", xpath.toString()).input(ertsr.getRejectReason().getRealValue());
        }
        element("am.result.viewChanges", xpath.toString()).click();
    }


    /**
     * approve all ticked OrganisationSearchResult
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedOrganisationSearchResults(OrganisationSearchResult result, Boolean selectTick) throws Exception {
        tickOrganisationSearchResult(result, selectTick);
        approveTickedSearchResults();
    }


    /**
     * approve all ticked statement search results
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedStatementSearchResults(AgreementSearchResult result, Boolean selectTick) throws Exception {
        tickStatementsSearchResult(result, selectTick);
        approveTickedSearchResults();
    }

    /**
     * approve all ticked WorkflowSearchResults
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedWorkflowSearchResults(WorkflowSearchResult result, Boolean selectTick) throws Exception {
        tickWorkflowSearchResult(result, selectTick);
        approveTickedSearchResults();
    }

    /**
     * approve all ticked SettlementInstructionsSearchResults
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedSettlementInsructionsSearchResults(SettlementInstructionsSearchResult result, Boolean selectTick) throws Exception {
        tickSettlementInstructionsSearchResult(result, selectTick);
        approveTickedSearchResults();
    }

    /**
     * approve all ticked SecuritySearchResults
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedSecuritiesDataSearchResults(SecuritySearchResult result, Boolean selectTick) throws Exception {
        tickSecuritiesDataSearchResult(result, selectTick);
        approveTickedSearchResults();
    }


    /**
     * approve all ticked EligibilityRulesTemplateSearchResults
     *
     * @param result
     * @throws Exception
     */
    public void approveTickedEligibilityRulesTemplateSearchResults(EligibilityRulesTemplateSearchResult result, Boolean selectTick) throws Exception {
        tickEligibilityRulesTemplateSearchRusult(result, selectTick);
        approveTickedSearchResults();
    }

    /**
     * reject all ticked EligibilityRulesTemplateSearchResults
     *
     * @param result
     * @throws Exception
     */
    public void rejectTickedEligibilityRulesTemplateSearchResults(EligibilityRulesTemplateSearchResult result, boolean selectTick) throws Exception {
        tickEligibilityRulesTemplateSearchRusult(result,selectTick);
        if (result.getAlertHandling() != null)
            rejectTickedSearchResults(result.getAlertHandling());
        else
            rejectTickedSearchResults();
    }

    public void tickAll() throws Exception {
        element("").click();
    }

    public void approveAllIfExist() throws Exception {
        if (element("am.approve.all").isDisplayed()) {
            approveAll();
            if (alert().isPresent())
                alert().accept();
        }
    }

    public void isApprovalSearchResultExist(Object o, boolean flag) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(StringType.class)
                    && method.getParameterTypes().length == 0
                    && method.invoke(o) != null) {
                StringType value = (StringType) method.invoke(o);
                xpath.append("[(td[normalize-space(.)='").append(value.getRealValue()).append("']) ").append("or (td/a[normalize-space(.)='").append(value.getRealValue()).append("']) ").append("or (td/font[normalize-space(.)='").append(value.getRealValue()).append("'])").append("or (td/a/font[normalize-space(.)='").append(value.getRealValue()).append("'])").append("]");
            }
        }
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        assertThat("am.search.result.row", xpath.toString()).displayed().isEqualTo(flag);
    }

    public void isApprovalSearchResultCanBeChecked(Object o, boolean flag) throws Exception {
        StringBuffer xpath = new StringBuffer();
        Method[] methods = o.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getReturnType().equals(StringType.class)
                    && method.getParameterTypes().length == 0
                    && method.invoke(o) != null) {
                StringType value = (StringType) method.invoke(o);
                xpath.append("[(td[normalize-space(.)='").append(value.getRealValue()).append("']) or (td/a[normalize-space(.)='").append(value.getRealValue()).append("']) or (td/font[normalize-space(.)='").append(value.getRealValue()).append("'])]");
            }
        }
        if (xpath.length() != 0) {
            xpath.append("//input[@id='ticked']");
        }
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        assertThat("am.search.result.row", xpath.toString()).enabled().isEqualTo(flag);
    }

}
