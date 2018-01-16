package com.lombardrisk.pages.collateral;

import org.yiwan.webcore.web.IWebDriverWrapper;

import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SecurityMovementSearch;



/**
 * @author Kenny Wang
 */
public final class SecurityMovementSearchPage extends PageBase {

    public SecurityMovementSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void clearSearchCondition() throws Exception{
    	element("sms.clear").click();
    }
    
    public void searchSecurityMovement() throws Exception{
    	element("sms.search").click();
    }
    
    
    public void setSecurityMovementSearch(SecurityMovementSearch search) throws Exception{
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
    	if (search.getDocumentation() != null){
    		element("sms.documentation").selectByVisibleText(search.getDocumentation().getRealValue());
    	}
    	if (search.getDescription() != null) {
          	if (search.getDescription().getType() != null){
          		element("sms.description.searchType").selectByVisibleText(search.getDescription().getType().value());
          	}
            if (search.getDescription().getFilter() != null) {
                element("sms.description.searchFilter").input(search.getDescription().getFilter().getRealValue());
            }
        }
    	if (search.getSettlementStatus() != null){
    		element("sms.settlementStatus").selectByVisibleText(search.getSettlementStatus().getRealValue());
    	}
    	if (search.getAssetClass() != null){
    		element("sms.assetClass").selectByVisibleText(search.getAssetClass().getRealValue());
    	}
    	if (search.getMovement() != null){
    		element("sms.movement").selectByVisibleText(search.getMovement().getRealValue());
    	}
    	if (search.getSettlementDateFrom() != null){
    		element("sms.settlementDateFrom").input(search.getSettlementDateFrom().getRealValue());
    	}
    	if (search.getSettlementDateTo() != null){
    		element("sms.settlementDateTo").input(search.getSettlementDateTo().getRealValue());
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
