package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.OrganisationThresholdReportFilter;
import com.lombardrisk.pojo.SimpleSearch;



public final class OrganisationThresholdReportPage extends AbstractReportPage {

    public OrganisationThresholdReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setOrganisationThresholdReport(OrganisationThresholdReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getOrganisation() != null && reportFilter.getOrganisation().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getOrganisation()){
				setOrganisation(organisationSearch);
			}
		}
    	if (reportFilter.getBreached() != null){
    		element("rp.breached").selectByVisibleText(reportFilter.getBreached().getRealValue());
    	}
    	if (reportFilter.getRegion() != null && reportFilter.getRegion().size() > 0){
    		setRegion(reportFilter.getRegion());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    }
    
    public void saveAsTemplate(OrganisationThresholdReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
