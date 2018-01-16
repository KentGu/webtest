package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.DisputeHistoryReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;


public final class DisputeHistoryReportPage extends AbstractReportPage {

    public DisputeHistoryReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setDisputeHistoryReport(DisputeHistoryReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
    		setBusinessLine(reportFilter.getBusinessLine());
    	}
    	if (reportFilter.getCcp() != null && reportFilter.getCcp().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getCcp()){
    			setCcp(simpleSearch);
    		}
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getRecordFrom() != null){
    		element("rp.record.from").input(reportFilter.getRecordFrom().getRealValue());
    	}
    	if (reportFilter.getRecordTo() != null){
    		element("rp.record.to").input(reportFilter.getRecordTo().getRealValue());
    	}
    	if (reportFilter.getTimeFrom() != null){
    		element("rp.time.from").selectByVisibleText(reportFilter.getTimeFrom().getRealValue());
    	}
    	if (reportFilter.getTimeTo() != null){
    		element("rp.time.to").selectByVisibleText(reportFilter.getTimeTo().getRealValue());
    	}
    }
    
    public void saveAsTemplate(DisputeHistoryReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
