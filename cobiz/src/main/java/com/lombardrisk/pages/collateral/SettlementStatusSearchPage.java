package com.lombardrisk.pages.collateral;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SettlementStatusSearch;



/**
 * @author Kenny Wang
 */
public final class SettlementStatusSearchPage extends PageBase {

    public SettlementStatusSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void clearSearchCondition() throws Exception{
    	element("sss.clear").click();
    }
    
    public void searchSettlementStatus() throws Exception{
    	element("sss.search").click();
    }
    
    
    public void setSettlementStatusSearch(SettlementStatusSearch search) throws Exception{
    	if (search.getPrincipal() != null){
    		element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(search.getPrincipal());
            switchTo().defaultContent();
    	}
    	if (search.getCounterparty() != null){
   		 	element("sss.cpty.search").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(search.getCounterparty());
            switchTo().defaultContent();
    	}
    	if (search.getDocumentation() != null){
    		element("sss.documentation").selectByVisibleText(search.getDocumentation().getRealValue());
    	}
    	if (search.getDescription() != null) {
          	if (search.getDescription().getType() != null){
          		element("sss.description.searchType").selectByVisibleText(search.getDescription().getType().value());
          	}
            if (search.getDescription().getFilter() != null) {
                element("sss.description.searchFilter").input(search.getDescription().getFilter().getRealValue());
            }
        }
    	if (search.getSettlementStatus() != null){
    		element("sss.settlementStatus").selectByVisibleText(search.getSettlementStatus().getRealValue());
    	}
    	if (search.getAssetClass() != null){
    		element("sss.assetClass").selectByVisibleText(search.getAssetClass().getRealValue());
    	}
    	if (search.getAssetType() != null && search.getAssetType().size() >0){
    		for (int i = 0; i < search.getAssetType().size(); i++) {
				element("sss.assetType").selectByVisibleText(search.getAssetType().get(i).getRealValue());
			}
    	}
    	if (search.getBookingType() != null && search.getBookingType().size() >0){
    		for (int i = 0; i < search.getBookingType().size(); i++) {
				element("sss.bookingType").selectByVisibleText(search.getBookingType().get(i).getRealValue());
			}
    	}
    	if (search.getCreator() != null && search.getCreator().size() >0){
    		for (int i = 0; i < search.getCreator().size(); i++) {
				element("sss.creator").selectByVisibleText(search.getCreator().get(i).getRealValue());
			}
    	}
    	if (search.getGroup() != null && search.getGroup().size() >0){
    		for (int i = 0; i < search.getGroup().size(); i++) {
				element("sss.group").selectByVisibleText(search.getGroup().get(i).getRealValue());
			}
    	}
    	if (search.getRegion() != null && search.getRegion().size() >0){
    		for (int i = 0; i < search.getRegion().size(); i++) {
				element("sss.region").selectByVisibleText(search.getRegion().get(i).getRealValue());
			}
    	}
    	if (search.getMovement() != null){
    		element("sss.movement").selectByVisibleText(search.getMovement().getRealValue());
    	}
    	if (search.getBookingSource() != null && search.getBookingSource().size() >0){
    		for (int i = 0; i < search.getBookingSource().size(); i++) {
				element("sss.bookingSource").selectByVisibleText(search.getBookingSource().get(i).getRealValue());
			}
    	}
    	if (search.getClBreached() != null){
    		element("sss.clBreached").selectByVisibleText(search.getClBreached().getRealValue());
    	}
    	if (search.getFailedStatus() != null){
    		element("sss.failedStatus").selectByVisibleText(search.getFailedStatus().getRealValue());
    	}
    	if (search.getSettlementDateFrom() != null){
    		element("sss.settlementDateFrom").input(search.getSettlementDateFrom().getRealValue());
    	}
    	if (search.getSettlementDateTo() != null){
    		element("sss.settlementDateTo").input(search.getSettlementDateTo().getRealValue());
    	}
    	 if (search.getAdvancedSearch() != null){
         	element("sss.advancedSearchArrow").click();
         	setAdvancedSearch(search);
         }
    }
    		
            
    public void setAdvancedSearch(SettlementStatusSearch search) throws Exception{
    	if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
				if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
					element("sss.ccp.filter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
					element("sss.ccp.linktext",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
				if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
					element("sss.linkageSet.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
					element("sss.linkageSet.linktext",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
				if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
					element("sss.statementSet.filter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
					element("sss.statementSet.linktext",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
				element("sss.businessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
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
