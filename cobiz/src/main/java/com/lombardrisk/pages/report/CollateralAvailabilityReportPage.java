package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.CollateralAvailabilityReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class CollateralAvailabilityReportPage extends AbstractReportPage {

    public CollateralAvailabilityReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setCollateralAvailabilityReport(CollateralAvailabilityReportFilter reportFilter) throws Exception{
    	if (reportFilter.getOrganisationGroup() != null && reportFilter.getOrganisationGroup().size() > 0 ){
    		for(OrganisationSearch organisationSearch :reportFilter.getOrganisationGroup()){
    			setOrganisationGroup(organisationSearch);
    		}
    	}
    	if (reportFilter.getPrincipalDeliveryGroup() != null && reportFilter.getPrincipalDeliveryGroup().size() > 0){
    		for(OrganisationSearch organisationSearch :reportFilter.getOrganisationGroup()){
    			setPrincipalDeliveryGroup(organisationSearch);
    		}
    	}
    	if (reportFilter.getPartyA() != null && reportFilter.getPartyA().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getPartyA()) {
				setPartyA(simpleSearch);
			}
    	}
		if (reportFilter.getBook() != null){
			element("rp.book").selectByVisibleText(reportFilter.getBook().getRealValue());
		}
    	if (reportFilter.isIncludeInventoryManager() != null){
    		element("rp.include.inventory.manager").check(reportFilter.isIncludeInventoryManager());
    	}
    	if (reportFilter.getRehypothecationPermitted() != null){
    		element("rp.rehypothecation.permitted").selectByVisibleText(reportFilter.getRehypothecationPermitted().getRealValue());
    	}
    	if (reportFilter.getPosition() != null){
    		element("rp.position").selectByVisibleText(reportFilter.getPosition().getRealValue());
    	}
    	if (reportFilter.getPrincipal() != null && reportFilter.getPrincipal().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getPrincipal()){
				setPrincipal(organisationSearch);
			}
        }
    	if (reportFilter.getCounterparty() != null && reportFilter.getCounterparty().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getCounterparty()){
				setCounterParty(organisationSearch);
			}
		}
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getLinkageSet() != null){
    		element("rp.linkage.set").selectByVisibleText(reportFilter.getLinkageSet().getRealValue());
    	}
    	if (reportFilter.getCcp() != null && reportFilter.getCcp().size() > 0){
			for (SimpleSearch simpleSearch : reportFilter.getCcp()){
				setCcp(simpleSearch);
			}
    	}
    }
    
    public void saveAsTemplate(CollateralAvailabilityReportFilter reportFilter) throws Exception{
    	if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
