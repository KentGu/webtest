package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.ConcentrationLimitsReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class ConcentrationLimitsReportPage extends AbstractReportPage {

    public ConcentrationLimitsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setConcentrationLimitsReport(ConcentrationLimitsReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getOrganisation() != null && reportFilter.getOrganisation().size() > 0){
    		for (OrganisationSearch organisationSearch : reportFilter.getOrganisation()){
    			setOrganisationGroup(organisationSearch);
    		}
    	}
    	if (reportFilter.getRuleLevel() != null){
    		element("rp.rule.level").selectByVisibleText(reportFilter.getRuleLevel().getRealValue());
    	}
    	if (reportFilter.getParty() != null){
    		element("rp.party").selectByVisibleText(reportFilter.getParty().getRealValue());
    	}
    	if (reportFilter.getBreached() != null){
    		element("rp.breached").selectByVisibleText(reportFilter.getBreached().getRealValue());
    	}
    	if (reportFilter.getDataType() != null && reportFilter.getDataType().size() > 0){
    		setDataType(reportFilter.getDataType());
    	}
    }
    
    public void saveAsTemplate(ConcentrationLimitsReportFilter reportFilter) throws Exception{
    	if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
   
}
