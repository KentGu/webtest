package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;

import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementSearchPage extends PageBase {
    public AgreementSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void clearSearchFilter() throws Exception {
        element("ah.clear").click();
    }

    public void longView() throws Exception {
        element("ah.long.view").click();
    }

    public void goToPage() {
    }

    public void summaryView() throws Exception {
        element("ah.smy.view").click();
    }

    public void sorting() {
    }


    /**
     * Search agreement on search agreement page
     *
     * @param search
     */
    public void searchAgreement(AgreementSearch search) throws Exception {
        if (search.getSystemId() != null)
            element("ah.sys.id").input(search.getSystemId().getRealValue());
        if (search.getDescription() != null) {
            if (search.getDescription().getFilter() != null)
                element("ah.desc.filter").input(search.getDescription().getFilter().getRealValue());
        }
        if (search.getStatus() != null) {
            element("ah.status").deselectAll();
            for (AgreementStatusType status : search.getStatus()) {
                element("ah.status").selectByVisibleText(status.value());
            }
        }
        if (search.getTsaRule() != null && !search.getTsaRule().isEmpty()) {
            element("ah.tsa.rule").deselectAll();
            for (StringType rule : search.getTsaRule()) {
                if (rule != null)
                    element("ah.tsa.rule").selectByVisibleText(rule.getRealValue());
            }
        }
        if (search.getClBreached() != null)
            element("ah.cl.breached").selectByVisibleText(search.getClBreached().value());
        if (search.getClLevel() != null)
            element("ah.cl.level").selectByVisibleText(search.getClLevel().value());
        if (search.getClOrganisation() != null && !search.getClOrganisation().isEmpty()) {
            element("ah.search.org").click();
            element("ah.search.org").switchTo();
            for (OrganisationSearch org : search.getClOrganisation()) {
                if (org != null)
                    searchOrganisation(org);
            }
            element("ap.frame.close").click();
            switchTo().defaultContent();
        }
        if (search.getAdditionalField() != null && !search.getAdditionalField().isEmpty()) {
            element("ah.additional.field").click();
            for (AdditionalFieldSimpleSearch additionalFieldSimpleSearch : search.getAdditionalField()) {
                if (additionalFieldSimpleSearch != null)
                    setAddtionalField(additionalFieldSimpleSearch);
            }
        }
        if (search.getAdvancedSearch() != null) {
            element("ah.advance").click();
            setAdvancedSearch(search);
        }

        if (search.isIncludeSubAgreement() != null) {
            element("ah.include.sub").check(search.isIncludeSubAgreement());
        }

        if(search.getFamilyAgreement() != null) {
            element("ah.familyAgreement").selectByVisibleText(search.getFamilyAgreement().value());
        }

        if(search.getConfigurableRule() != null) {
            element("ah.configurableRule").selectByVisibleText(search.getConfigurableRule().value());
        }

        element("ah.search").click();
    }

    public void setAddtionalField(AdditionalFieldSimpleSearch additionalFieldSimpleSearch) throws Exception {
        if (additionalFieldSimpleSearch.getUdfName() != null) {
            if (additionalFieldSimpleSearch.getType() != null) {
                element("ah.additional.field.type", additionalFieldSimpleSearch.getUdfName().getRealValue()).selectByVisibleText(additionalFieldSimpleSearch.getType().value());
            }
            if (additionalFieldSimpleSearch.getFilter() != null) {
                element("ah.additional.field.filter", additionalFieldSimpleSearch.getUdfName().getRealValue()).input(additionalFieldSimpleSearch.getFilter().getRealValue());
            }
        }
    }

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


    /**
     * view complemented agreement statement from search agreement page
     *
     * @param agmt
     */
    public void viewCompletedAgreementStatement(AgreementSearchResult agmt) throws Exception {
        String xpath = getAgreementResult(agmt);
        element("ah.view.completed.agreement.statement", xpath).clickByJavaScript();
    }

    /**
     * get agreement result row by specified condition
     *
     * @param agmt
     * @return
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private String getAgreementResult(AgreementSearchResult agmt) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        StringBuffer xpath = new StringBuffer("//tr");
        Method[] methods = agmt.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && method.getReturnType().equals(StringType.class)
                    && method.invoke(agmt) != null) {
                /*
                 * @author kent gu
                 * Modified the following method
                 */
                //xpath.append("[*='");
                //xpath.append(method.invoke(agmt));
                StringType stringType = (StringType) method.invoke(agmt);
                if(stringType.getRealValue() != null && !stringType.getRealValue().equals("")) {
                    if (method.getName().equalsIgnoreCase("getPrincipal") || method.getName().equalsIgnoreCase("getCounterparty")) {
//                        xpath.append("[td/a='" + stringType.getRealValue() + "']");
                        xpath.append("[td/a='").append(stringType.getRealValue()).append("']");
                    } else {
//                        xpath.append("[*='" + stringType.getRealValue() + "']");
                        xpath.append("[*='").append(stringType.getRealValue()).append("']");
                    }
                }
            }
        }
        return xpath.toString();
    }

    /**
     * view edit agreement statement from search agreement page
     *
     * @param agmt
     */
    public void editAgreement(AgreementSearchResult agmt) throws Exception {
        String xpath = getAgreementResult(agmt);
        if (agmt.getBusinessLine() != null
                || agmt.getOwner() != null
                || agmt.getLastModifiedBy() != null
                || agmt.getRegion() != null
                || agmt.getExternalId() != null
                || agmt.getSignoffDate() != null
                || agmt.getSignoffBy() != null)
            longView();
        element("ah.edit.agreement", xpath).click();
    }

    public boolean isAgreementExists(AgreementSearchResult asr) throws Exception{
        String xpath = getAgreementResult(asr);
        if(!element("ah.long.view").isDisplayed())
            return false;
        longView();
        boolean flag = element("ah.agreement.id", xpath).isDisplayed();
        summaryView();
        return flag;
    }

    public String getAgreementId(AgreementSearchResult asr) throws Exception {
        String xpath = getAgreementResult(asr);
        longView();
        String agreementId = element("ah.agreement.id", xpath).getInnerText().trim();
        summaryView();
        return agreementId;
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

    // export to csv
    public void exportCsv(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.csv").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to excel
    public void exportExcel(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.excel").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to xml
    public void exportXml(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.xml").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    // export to pdf
    public void exportPdf(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ah.export.pdf").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

}
