package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.InterestEventSearch;
import com.lombardrisk.pojo.OrganisationSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class InterestManagerArchiveSearchPage extends PageBase {

    public InterestManagerArchiveSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    
    public void clearSearchCondition() throws Exception{
    	element("imas.clear").click();
    }
    
    public void search() throws Exception{
    	element("imas.search").click();
    }
    
    /**
     * search Interest manager on the Collateral - Workflow - Interest Manager
     * Search page
     *
     * @param search
     */
    public void setSearchInterestEvent(InterestEventSearch search) throws Exception {
    	if (search.getDateChangedBegin() != null){
    		element("imas.dateChangedBegin").input(search.getDateChangedBegin().getRealValue());
    	}
    	if (search.getDateChangedEnd() != null){
    		element("imas.dateChangedEnd").input(search.getDateChangedEnd().getRealValue());
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
        		element("imas.description.searchType").selectByVisibleText(search.getDescription().getType().value());
        	}
            if (search.getDescription().getFilter() != null) {
                element("imas.description.searchFilter").input(search.getDescription().getFilter().getRealValue());
            }
        }
        if (search.getPaymentMethod() != null){
        	element("imas.paymentMethod").selectByVisibleText(search.getPaymentMethod().getRealValue());
        }
        if (search.getDirection() != null){
        	element("imas.direction").selectByVisibleText(search.getDirection().getRealValue());
        }
        if (search.getCashAssetType() != null){
        	element("imas.cashAssetType").selectByVisibleText(search.getCashAssetType().getRealValue());
        }
        if (search.getInterestSource() != null){
        	element("imas.interestSource").selectByVisibleText(search.getInterestSource().getRealValue());
        }
        if (search.getInterestRule() != null){
        	element("imas.interestRule").selectByVisibleText(search.getInterestRule().getRealValue());
        }
        if (search.getMonth() != null) {
        	element("imas.monthOr").click();
            element("im.month").selectByVisibleText(search.getMonth().getRealValue());
        }
        if (search.getMonthRange() != null){
        	element("imas.monthRangeOr").click();
        	if (search.getMonthRange().getStartMonth() != null){
        		element("imas.monthRangeStart").input(search.getMonthRange().getStartMonth().getRealValue());
        	}
        	if (search.getMonthRange().getEndMonth() != null){
        		element("imas.monthRangeEnd").input(search.getMonthRange().getEndMonth().getRealValue());
        	}
        }
        if (search.getDateRange() != null){
        	element("imas.dateRangeOr").click();
        	if (search.getDateRange().getStartDate() != null){
        		element("imas.dateRangeStart").input(search.getDateRange().getStartDate().getRealValue());
        	}
        	if (search.getDateRange().getEndDate() != null){
        		element("imas.dateRangeEnd").input(search.getDateRange().getEndDate().getRealValue());
        	}
        }
      
        if (search.isIncludingZeroInterestMovement() != null){
        	if (search.isIncludingZeroInterestMovement()){
        		element("imas.includingZeroInterestMovement").check(search.isIncludingZeroInterestMovement());
        	}
        }
        if (search.isIncludingApplied() != null){
        	if (search.isIncludingApplied()){
        		element("imas.includingApplied").check(search.isIncludingApplied());
        	}
        }
        if (search.isIncludingCompounding() != null){
        	if (search.isIncludingCompounding()){
        		element("imas.includingCompouding").check(search.isIncludingCompounding());
        	}
        }
        if (search.getAdvancedSearch() != null){
        	element("imas.advancedSearchArrowIcon").click();
        	setAdvancedSearch(search);
        }
    }
    
    public void setAdvancedSearch(InterestEventSearch search) throws Exception{
    	if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
				if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
					element("imas.ccp.filter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
					element("imas.ccp.linktext",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
				if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
					element("imas.linkageset.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
					element("imas.linkageset.linktext",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
				if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
					element("imas.statement.filter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
					element("imas.statement.linktext",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
				element("imas.advancedSearchBusinessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
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

	public void searchInterestManagerHistory(InterestEventSearch search) throws Exception{
		setSearchInterestEvent(search);
		search();
	}

}
