package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.IneligibleAssetReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class IneligibleAssetReportPage extends AbstractReportPage {

    public IneligibleAssetReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setIneligibleAssetReport(IneligibleAssetReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getIssuer() != null && reportFilter.getIssuer().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getIssuer()){
				setIssuer(organisationSearch);
			}
		}
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null && reportFilter.getAssetType().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetType()){
				setAssetType(simpleSearch);
			}
		}
    	if (reportFilter.getRegion() != null){
    		element("re.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(IneligibleAssetReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
