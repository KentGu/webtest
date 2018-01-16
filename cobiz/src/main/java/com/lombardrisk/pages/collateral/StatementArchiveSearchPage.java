package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.StatementArchiveSearch;
import org.yiwan.webcore.web.PageBase;
import org.yiwan.webcore.web.IWebDriverWrapper;

/**
 * Created by mengli huang on 2/08/2016.
 */
public class StatementArchiveSearchPage extends PageBase{
    public StatementArchiveSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void setStatementArchiveSearchCondition(StatementArchiveSearch statementArchiveSearch) throws Exception{
        if (statementArchiveSearch.getAgreementId()!=null)
            element("stmtArch.agmt.id").input(statementArchiveSearch.getAgreementId().getRealValue());
        if (statementArchiveSearch.getPrincipal()!=null){
            element("stmtArch.search.prin").click();
            element("stmtArch.frame.prin").switchTo();
            searchOrg(statementArchiveSearch.getPrincipal());
        }
        if (statementArchiveSearch.getCouterparty()!=null){
            element("stmtArch.search.cpty").click();
            element("stmtArch.frame.cpty").switchTo();
            searchOrg(statementArchiveSearch.getCouterparty());
        }
        if (statementArchiveSearch.getDocumentation()!=null)
            element("stmtArch.documentation").selectByVisibleText(statementArchiveSearch.getDocumentation().getRealValue());
        if (statementArchiveSearch.getDescription()!=null){
            if (statementArchiveSearch.getDescription().getType()!=null)
                element("stmtArch.description.type").selectByVisibleText(statementArchiveSearch.getDescription().getType().value());
            if (statementArchiveSearch.getDescription().getFilter()!=null)
                element("stmtArch.description").input(statementArchiveSearch.getDescription().getFilter().getRealValue());
        }
        if (statementArchiveSearch.getDateRange()!=null){
            if (statementArchiveSearch.getDateRange().getStartDate()!=null)
                element("stmtArch.date.range.from").input(statementArchiveSearch.getDateRange().getStartDate().getRealValue());
            if (statementArchiveSearch.getDateRange().getEndDate()!=null)
                element("stmtArch.date.range.to").input(statementArchiveSearch.getDateRange().getEndDate().getRealValue());
        }
        if (statementArchiveSearch.getAdvancedSearch()!=null)
            setAdvancedSearch(statementArchiveSearch);
    }

    public void searchOrg(OrganisationSearch organisationSearch) throws Exception{
        if (organisationSearch.getCriteria()!=null)
            element("ap.frame.criteria").selectByVisibleText(organisationSearch.getCriteria().value());
        if (organisationSearch.getType()!=null)
            element("ap.frame.type").selectByVisibleText(organisationSearch.getType().value());
        if (organisationSearch.getFilter()!=null)
            element("ap.frame.filter").input(organisationSearch.getFilter().getRealValue());
        element("ap.frame.search").click();
        if (organisationSearch.getLinkText()!=null)
            element("ap.frame.link", organisationSearch.getLinkText().getRealValue()).click();
        switchTo().defaultContent();
    }

    public void setAdvancedSearch(StatementArchiveSearch search) throws Exception{
        if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
                if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null)
                    element("am.agreementSearch.ccpFilter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
                if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null)
                    element("am.agreementSearch.ccpFilter",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
            }
        }
        if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
                if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null)
                    element("am.agreementSearch.linkageSet.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
                if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null)
                    element("am.agreementSearch.linkageSet.filter",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
            }
        }
        if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
                if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null)
                    element("am.agreementSearch.statementSetFilter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
                if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null)
                    element("am.agreementSearch.statementSetFilter",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
            }
        }
        if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++)
                element("am.agreementSearch.businessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
        }
    }

    public void clickSearchButton() throws Exception{
        element("stmtArch.search").click();
    }

    public void searchStatementArchive(StatementArchiveSearch statementArchiveSearch) throws Exception{
        setStatementArchiveSearchCondition(statementArchiveSearch);
        clickSearchButton();
    }

    public void checkAllResult(StatementArchiveSearch search) throws Exception{
        if (search.getCouterparty()!=null && search.getCouterparty().getLinkText()!=null)
            assertThat("stmtArch.column.couterparty").allInnerTexts().containsOnly(search.getCouterparty().getLinkText().getRealValue());
        if (search.getAgreementId() != null)
            assertThat("stmtArch.column.agmtid").allInnerTexts().containsOnly(search.getAgreementId().getRealValue());
        if (search.getPrincipal()!=null && search.getPrincipal().getLinkText()!=null)
            assertThat("stmtArch.column.principal").allInnerTexts().containsOnly(search.getPrincipal().getLinkText().getRealValue());
        if (search.getDocumentation()!=null)
            assertThat("stmtArch.column.documentation").allInnerTexts().containsOnly(search.getDocumentation().getRealValue());
        if (search.getDescription()!=null && search.getDescription().getFilter()!=null){
            if (search.getDescription().getType()!=null && !search.getDescription().getType().value().equals("Starting With")){
                if (search.getDescription().getType().value().equals("Containing"))
                    assertThat("stmtArch.column.description.notcontain",search.getDescription().getFilter().getRealValue()).displayed().isFalse();
                if (search.getDescription().getType().value().equals("Exactly matching"))
                    assertThat("stmtArch.column.description").allInnerTexts().containsOnly(search.getDescription().getFilter().getRealValue());
            }else {
                assertThat("stmtArch.column.description.notstartwith",search.getDescription().getFilter().getRealValue()).displayed().isFalse();
            }
        }

    }
}
