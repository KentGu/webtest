package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.DisputeReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;


public final class DisputeReportPage extends AbstractReportPage {

    public DisputeReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setDisputeReport(DisputeReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAgreementStatus() != null && reportFilter.getAgreementStatus().size() > 0){
    		setAgreementStatus(reportFilter.getAgreementStatus());
    	}
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
    		setBusinessLine(reportFilter.getBusinessLine());
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getLinkageSet() != null && reportFilter.getLinkageSet().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getLinkageSet()){
				setLinkageSet(simpleSearch);
			}
    	}
    	if (reportFilter.getTotalDisputeAgeGreaterEqualThan() != null){
    		element("rp.total.dispute.age.greater.equal.than").input(reportFilter.getTotalDisputeAgeGreaterEqualThan().getRealValue());
    	}
    	if (reportFilter.getDisputeValueGreaterThan() != null){
    		element("rp.dispute.greater.than").input(reportFilter.getDisputeValueGreaterThan().getRealValue());
    	}
    	if (reportFilter.getCallStatus() != null && reportFilter.getCallStatus().size() > 0){
    		setCallStatus(reportFilter.getCallStatus());
    	}
    	if (reportFilter.getStartDate() != null){
    		element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
    	}
    	if (reportFilter.getEndDate() != null){
    		element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
    	}
    	if (reportFilter.getTimeFrom() != null){
    		element("rp.time.from").selectByVisibleText(reportFilter.getTimeFrom().getRealValue());
    	}
    	if (reportFilter.getTimeTo() != null){
    		element("rp.time.to").selectByVisibleText(reportFilter.getTimeTo().getRealValue());
    	}
    	if (reportFilter.getReportingCurrency() != null){
    		element("rp.reporting.currency").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(DisputeReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
