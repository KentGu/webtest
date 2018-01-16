package com.lombardrisk.pages.collateral;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.InternalReviewSearch;
import com.lombardrisk.pojo.OrganisationSearch;



/**
 * @author Kenny Wang
 */
public final class InternalReviewSearchPage extends PageBase {

    public InternalReviewSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void clearSearchCondition() throws Exception{
    	element("irs.clear").click();
    }
    
    public void searchInternal() throws Exception{
    	element("irs.search").click();
		waitThat().document().toBeReady();
		waitThat().jQuery().toBeInactive();
    }
    
    
    public void setInternalReviewSearch(InternalReviewSearch search) throws Exception{
    	if (search.getAgreementId() != null){
    		element("irs.agreemenId").input(search.getAgreementId().getRealValue());
    	}
    	
    	if (search.getExternalId() != null) {
          	if (search.getExternalId().getType() != null){
          		element("irs.externalId.searchType").selectByVisibleText(search.getExternalId().getType().value());
          	}
            if (search.getExternalId().getFilter() != null) {
                element("irs.externalId.filter").input(search.getExternalId().getFilter().getRealValue());
            }
        }
    	
    	if (search.getPrincipal() != null){
    		element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(search.getPrincipal());
            switchTo().defaultContent();
    	}
    	if (search.getCounterparty() != null){
   		 	element("ap.search.cpty").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(search.getCounterparty());
            switchTo().defaultContent();
    	}
    	if (search.getDescription() != null) {
          	if (search.getDescription().getType() != null){
          		element("irs.description.searchType").selectByVisibleText(search.getDescription().getType().value());
          	}
            if (search.getDescription().getFilter() != null) {
                element("irs.description.filter").input(search.getDescription().getFilter().getRealValue());
            }
        }
    	if (search.getCollateralManager() != null){
    		element("irs.collateralManager").selectByVisibleText(search.getCollateralManager().getRealValue());
    	}
    	if (search.getMarginType() != null){
    		element("irs.marginType").selectByVisibleText(search.getMarginType().getRealValue());
    	}
    	if (search.getTabPage() != null){
    		element("irs.tabPage").selectByVisibleText(search.getTabPage().getRealValue());
    	}
    	 if (search.getAdvancedSearch() != null){
         	element("irs.advancedSearch.arrowIcon").click();
         	setAdvancedSearch(search);
         }
    }
    
    public void setAdvancedSearch(InternalReviewSearch search) throws Exception{
    	if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
				if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
					element("irs.ccp.filter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
					element("irs.ccp.linktext",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
				if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
					element("irs.linkageset.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
					element("irs.linkageset.linktext",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
				if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
					element("irs.statement.filter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
					element("irs.statement.linktext",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
				element("irs.advancedSearchBusinessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
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
    	
    	
   

}
