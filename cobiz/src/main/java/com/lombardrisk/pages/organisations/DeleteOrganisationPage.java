package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.OrganisationSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class DeleteOrganisationPage extends PageBase {

    public DeleteOrganisationPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * search an organisation
     *
     * @param search
     */
    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getRole() != null)
            element("og.search.role").selectByVisibleText(search.getRole().getRealValue());
        if (search.getStatus() != null)
            element("og.search.status").selectByVisibleText(search.getStatus().value());
        if (search.getFilter() != null)
            element("og.search.filter").input(search.getFilter().getRealValue());
        element("og.search.submit").click();
        waitThat("og.search.table").toBeVisible();
        element("og.search.result", search.getLinkText().getRealValue()).click();
    }

    /**
     * @param search
     */
    public void deleteOrganisation(OrganisationSearch search) throws Exception {
        searchOrganisation(search);
        alert().accept();
    }
    public void enterFirstSearchResultPage(){}
    
    public void enterLastSearchResultPage(){}
    
    public void enterPrevSearchResultPage(){}
    
    public void enterNextSearchResultPage(){}
    
    public void selectSearchResultPage(String pageNumber) throws Exception{
    	element("",pageNumber).click();
    }
}
