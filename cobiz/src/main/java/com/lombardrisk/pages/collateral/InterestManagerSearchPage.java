package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.InterestEventSearch;
import com.lombardrisk.pojo.OrganisationSearch;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class InterestManagerSearchPage extends PageBase {

    public InterestManagerSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    public void clearSearchCondition() throws Exception{
    	element("ims.clear").click();
    }

    public void search() throws Exception{
    	element("ims.search").click();
    }

    /**
     * search Interest manager on the Collateral - Workflow - Interest Manager
     * Search page
     *
     * @param search
     */
    public void searchInterestEvent(InterestEventSearch search) throws Exception {
    	if (search.getPrincipal() != null){
            element("ap.search.prin").click();
            element("ap.frame.prin").switchTo();
            searchOrganisation(search.getPrincipal());
            switchTo().defaultContent();
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
    	}
    	if (search.getCounterparty() != null){
            element("ap.search.cpty").click();
            element("ap.frame.cpty").switchTo();
            searchOrganisation(search.getCounterparty());
            switchTo().defaultContent();
			waitThat().jQuery().toBeInactive();
			waitThat().document().toBeReady();
    	}
        if (search.getDescription() != null) {
        	if (search.getDescription().getType() != null){
                element("ims.description.searchType").selectByVisibleText(search.getDescription().getType().value());
        	}
            if (search.getDescription().getFilter() != null) {
                element("ims.description.searchFilter").input(search.getDescription().getFilter().getRealValue());
            }
        }
        if (search.getCollateralManager() != null){
        	element("ims.collateralManager").selectByVisibleText(search.getCollateralManager().getRealValue());
        }
        if (search.getPaymentMethod() != null){
        	element("ims.paymentMethod").selectByVisibleText(search.getPaymentMethod().getRealValue());
        }
        if (search.getDirection() != null){
        	element("ims.direction").selectByVisibleText(search.getDirection().getRealValue());
        }
        if (search.getCashAssetType() != null){
        	element("ims.cashAssetType").selectByVisibleText(search.getCashAssetType().getRealValue());
        }
        if (search.getInterestSource() != null){
        	element("ims.interestSource").selectByVisibleText(search.getInterestSource().getRealValue());
        }
        if (search.getInterestRule() != null){
        	element("ims.interestRule").selectByVisibleText(search.getInterestRule().getRealValue());
        }
        if (search.getGroup() != null && search.getGroup().size() >0){
        	for (int i = 0; i < search.getGroup().size(); i++) {
				element("ims.group").selectByVisibleText(search.getGroup().get(i).getRealValue());
			}
        }
        if (search.getRegion() != null && search.getRegion().size() >0){
        	for (int i = 0; i < search.getRegion().size(); i++) {
				element("ims.region").selectByVisibleText(search.getRegion().get(i).getRealValue());
			}
        }
        if (search.getDateRange() != null){
        	element("ims.dateRangeOr").click();
        	if (search.getDateRange().getStartDate() != null){
        		element("ims.dateRangeStart").input(search.getDateRange().getStartDate().getRealValue());
        	}
        	if (search.getDateRange().getEndDate() != null){
        		element("ims.dateRangeEnd").input(search.getDateRange().getEndDate().getRealValue());
        	}
        }
		if (search.getMonthRange() != null){
			element("ims.monthRangeOr").click();
			if (search.getMonthRange().getStartMonth() != null){
				element("ims.monthRangeStart").input(search.getMonthRange().getStartMonth().getRealValue());
			}
			if (search.getMonthRange().getEndMonth() != null){
				element("ims.monthRangeEnd").input(search.getMonthRange().getEndMonth().getRealValue());
			}
		}
		if (search.getMonth() != null) {
			element("ims.monthOr").click();
			element("ims.month").selectByVisibleText(search.getMonth().getRealValue());
		}
        if (search.getLastReset() != null){
        	element("ims.lastResetOr").click();
        	element("ims.endDate").input(search.getLastReset().getRealValue());
        }
        if (search.isIncludingZeroInterestMovement() != null){
        	if (search.isIncludingZeroInterestMovement()){
        		element("ims.includingZeroInterestMovement").check(search.isIncludingZeroInterestMovement());
        	}
        }
        if (search.isIncludingApplied() != null){
        	if (search.isIncludingApplied()){
        		element("ims.includingApplied").check(search.isIncludingApplied());
        	}
        }
        if (search.isIncludingCompounding() != null){
        	if (search.isIncludingCompounding()){
        		element("ims.includingCompouding").check(search.isIncludingCompounding());
        	}
        }
        if (search.isIndividualReset() != null){
        	if (search.isIndividualReset()){
        		element("ims.individualReset").check(search.isIndividualReset());
        	}
        }
        if (search.getAdvancedSearch() != null){
        	element("ims.advancedSearchArrowIcon").click();
        	setAdvancedSearch(search);
        }

    }

    public void setAdvancedSearch(InterestEventSearch search) throws Exception{
    	if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
				if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
					element("ims.ccp.filter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
					element("ims.ccp.linktext",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
				if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
					element("ims.linkageset.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
					element("ims.linkageset.linktext",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
				if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
					element("ims.statement.filter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
				}
				if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
					element("ims.statement.linktext",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
				}
			}
    	}
    	if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
    		for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
				element("ims.advancedSearchBusinessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
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
