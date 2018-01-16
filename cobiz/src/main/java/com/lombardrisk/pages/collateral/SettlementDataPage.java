package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;

/**
 * @author Kenny Wang
 */
public final class SettlementDataPage extends PageBase {

    public SettlementDataPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    public void clickAddImage() throws Exception{
        element("sd.new").click();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }
    @SuppressWarnings("UnnecessaryUnboxing")
    public void addSettelmentData(SettlementData data) throws Exception {
        if (data.getOrganisation()!=null) {
            element("sd.search.org").click();
            element("sd.org.frame").switchTo();
            if (data.getOrganisation().getFilter() != null)
                element("sd.frame.filter").input(data.getOrganisation().getFilter().getRealValue());
            element("sd.frame.search").click();
            waitThat("sd.frame.result").toBeVisible();
            if (data.getOrganisation().getLinkText() != null)
                element("sd.frame.link", data.getOrganisation().getLinkText().getRealValue()).click();
            switchTo().defaultContent();
        }
        if (data.getAssetClass() != null)
            element("sd.asset.class").selectByVisibleText(data.getAssetClass().getRealValue());
        if (data.getAssetType() != null) {
            element("sd.asset.type").click();
            element("sd.asset.type").selectByVisibleText(data.getAssetType().getRealValue());
        }
        if (data.getBucket() != null)
            element("sd.bucket").selectByVisibleText(data.getBucket().getRealValue());
        if (data.getCopyFromBucket() != null)
            element("sd.copy.from.bucket").selectByVisibleText(data.getCopyFromBucket().getRealValue());
        if (data.getCopyFromAssetType() != null)
            element("sd.copy.from.asset").selectByVisibleText(data.getCopyFromAssetType().getRealValue());
        if (data.getAgreement() != null)
            element("sd.agreement").selectByVisibleText(data.getAgreement().getRealValue());
        if (data.getCounterParty() != null) {
            List<Account> account = data.getCounterParty().getAccount();
            int accountSize = account.size();
            for (Account anAccount : account) {
                if (anAccount.getAccountName() != null) {
                    if (anAccount.getValue() != null)
                        element("sd.cpty.account", anAccount.getAccountName().getRealValue()).input(
                                anAccount.getValue().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    if (anAccount.getSelectValue() != null) {
                        if (anAccount.getSelectValue().isRemove() != null && anAccount.getSelectValue().isRemove())
                            element("sd.cpty.account.delete", anAccount.getAccountName().getRealValue()).click();
                        else {
                            element("sd.cpty.account.search", anAccount.getAccountName().getRealValue()).click();
                            element("sd.frame.cpty.cus").switchTo();
                            searchOrganisation(anAccount.getSelectValue());
                            switchTo().defaultContent();
                        }
                    }
                    if (anAccount.isOverwrite() != null) {
                        element("sd.cpty.account.overwrite", anAccount.getAccountName().getRealValue()).check(anAccount.isOverwrite()).fireEvent(
                                Biz.FIRE_EVENT_ONCHANGE);
                    }
                }
            }
        }
        if (data.getPrincipal() != null) {
            List<Account> account = data.getPrincipal().getAccount();
            int accountSize = account.size();
            for (Account anAccount : account) {
                if (anAccount.getAccountName() != null) {
                    if (anAccount.getValue() != null)
                        element("sd.prin.account2", anAccount.getAccountName().getRealValue()).input(anAccount.getValue().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
//                        inputSmartly(anAccount.getValue().getRealValue()
//                                , locator("sd.prin.account2", anAccount.getAccountName().getRealValue())
//                                , locator("sd.prin.account2", anAccount.getAccountName().getRealValue()));
                    if (anAccount.getSelectValue() != null) {
                        if (anAccount.getSelectValue().isRemove() != null && anAccount.getSelectValue().isRemove())
                            element("sd.prin.account.delete", anAccount.getAccountName().getRealValue()).click();
                        else {
                            element("sd.prin.account.search", anAccount.getAccountName().getRealValue()).click();
                            if (element("sd.frame.prin.cus").isDisplayed())
                                element("sd.frame.prin.cus").switchTo();
                            else if (element("sd.frame.cpty.cus").isDisplayed())
                                element("sd.frame.cpty.cus").switchTo();
                            searchOrganisation(anAccount.getSelectValue());
                            switchTo().defaultContent();
                        }
                    }
                    if (anAccount.isOverwrite() != null) {
                        element("sd.prin.account2.overwrite", anAccount.getAccountName().getRealValue()).check(anAccount.isOverwrite()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    }
                }
            }
        }
        element("sd.save").click();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
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

    public void searchSettlementData(SettlementDataSearch search) throws Exception {
        if (search.getOrganisation() != null && search.getOrganisation().size() != 0) {
            element("sd.search.org").click();
            element("sd.org.frame").switchTo();
            for (OrganisationSearch org : search.getOrganisation()) {
                setOrganisation(org);
                switchTo().defaultContent();
            }
        }
        if (search.getAgreementId()!=null)
//            element("sd.agreementId").input(search.getAgreementId().getRealValue());
        element("sd.agreementId").clear().type(search.getAgreementId().getRealValue());

        if (search.getPrincipal()!=null && search.getPrincipal().size()>0){
            element("sd.search.prin").click();
            element("sd.prin.frame").switchTo();
            for (OrganisationSearch prin : search.getPrincipal()){
                setOrganisation(prin);
                switchTo().defaultContent();
            }
        }
        if (search.getCounterparty()!=null && search.getCounterparty().size()>0){
            element("sd.search.cpty").click();
            element("sd.cpty.frame").switchTo();
            for (OrganisationSearch cpty : search.getCounterparty()){
                setOrganisation(cpty);
                switchTo().defaultContent();
            }
        }
        if (search.getDescription()!=null){
            if (search.getDescription().getType()!=null)
                element("sd.description.type").selectByVisibleText(search.getDescription().getType().value());
            if (search.getDescription().getFilter()!=null)
                element("sd.description").input(search.getDescription().getFilter().getRealValue());
        }
        if (search.getExternalId()!=null){
            if (search.getExternalId().getType()!=null)
                element("sd.externalId.type").selectByVisibleText(search.getExternalId().getType().value());
            if (search.getExternalId().getFilter()!=null)
                element("sd.externalId").input(search.getExternalId().getFilter().getRealValue());
        }
        if (search.getRegion()!=null && search.getRegion().size()>0){
            for (StringType region:search.getRegion())
                element("sd.region").selectByVisibleText(region.getRealValue());
        }
        if (search.getGroup()!=null && search.getGroup().size()>0){
            for (StringType group : search.getGroup())
                element("sd.group").selectByVisibleText(group.getRealValue());
        }
        if (search.getPaymentBucket()!=null)
            element("sd.bucket").selectByVisibleText(search.getPaymentBucket().getRealValue());
        if (search.getAssetClass()!=null)
            element("sd.asset.class").selectByVisibleText(search.getAssetClass().getRealValue());
        if (search.getAsserType()!=null)
            element("sd.asset.type").selectByVisibleText(search.getAsserType().getRealValue());
        if (search.getAdvancedSearch() != null){
            element("ah.advance").click();
            setAdvancedSearch(search);
        }
        element("sd.search").click();
    }

    public void setOrganisation(OrganisationSearch org) throws Exception{
        if (org.getCriteria() != null)
            element("ap.frame.criteria").selectByVisibleText(org.getCriteria().value());
        if (org.getType() != null)
            element("ap.frame.type").selectByVisibleText(org.getType().value());
        if (org.getFilter() != null)
            element("sd.frame.filter").input(org.getFilter().getRealValue());
        element("sd.frame.search").click();
        waitThat("sd.frame.result").toBeVisible();
        if (org.getLinkText() != null)
            element("sd.frame.link", org.getLinkText().getRealValue()).clickByJavaScript();
    }

    public void setAdvancedSearch(SettlementDataSearch search) throws Exception{
        if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
                if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
                    element("am.agreementSearch.ccpFilter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
                    element("am.agreementSearch.ccpFilter",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
                if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
                    element("am.agreementSearch.linkageSet.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
                    element("am.agreementSearch.linkageSet.filter",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
                if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
                    element("am.agreementSearch.statementSetFilter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
                    element("am.agreementSearch.statementSetFilter",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
                element("am.agreementSearch.businessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
            }
        }
    }

    public void approveSettlementData() throws Exception{
        element("sd.approve").click();
    }

    public void enterSettlementData(SettlementDataSearchResult result) throws Exception{
        if (result!=null){
            element("sd.settlement.data.result.edit",getSettlementDataResult(result)).clickByJavaScript();
        }
    }

    private String getSettlementDataResult(SettlementDataSearchResult result) {
        StringBuilder xpath = new StringBuilder();
        xpath.append("//table[contains(@id,'settlementDataIndexItem')]//tr");
        if (result.getOrganisation() != null) {
//            xpath.append("[td[contains(text(),'" + result.getOrganisation().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getOrganisation().getRealValue()).append("')]]");
        }
        if (result.getPrincipal() != null) {
//            xpath.append("[td[contains(text(),'" + result.getPrincipal().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getPrincipal().getRealValue()).append("')]]");
        }
        if (result.getCounterparty() != null) {
//            xpath.append("[td[contains(text(),'" + result.getCounterparty().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getCounterparty().getRealValue()).append("')]]");
        }
        if (result.getPaymentBucket() != null) {
//            xpath.append("[td[contains(text(),'" + result.getPaymentBucket().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getPaymentBucket().getRealValue()).append("')]]");
        }
        if (result.getAssetClass() != null) {
//            xpath.append("[td[contains(text(),'" + result.getAssetClass().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getAssetClass().getRealValue()).append("')]]");
        }
        if (result.getAssetType() != null) {
//            xpath.append("[td[contains(text(),'" + result.getAssetType().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getAssetType().getRealValue()).append("')]]");
        }
        if (result.getDescription() != null) {
//            xpath.append("[td[contains(text(),'" + result.getDescription().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getDescription().getRealValue()).append("')]]");
        }
        if (result.getBusinessLine() != null) {
//            xpath.append("[td[contains(text(),'" + result.getBusinessLine().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getBusinessLine().getRealValue()).append("')]]");
        }
        if (result.getStatus() != null) {
//            xpath.append("[td[contains(text(),'" + result.getStatus().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getStatus().getRealValue()).append("')]]");
        }
        if (result.getAgreementId() != null) {
//            xpath.append("[td[contains(text(),'" + result.getAgreementId().getRealValue() + "')]]");
            xpath.append("[td[contains(text(),'").append(result.getAgreementId().getRealValue()).append("')]]");
        }
        return xpath.toString();
    }

    public void assetSettlementData(SettlementDataSearchResult settlementDataSearchResult) throws Exception {
        validateThat("sd.settlement.data.result.edit", getSettlementDataResult(settlementDataSearchResult)).displayed().isTrue();
    }

}
