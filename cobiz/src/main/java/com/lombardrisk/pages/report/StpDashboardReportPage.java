package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;
import com.lombardrisk.pojo.StpDashboardReportFilter;


public final class StpDashboardReportPage extends AbstractReportPage {

    public StpDashboardReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setStpDashboardReport(StpDashboardReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getStatementStatus() != null){
    		element("rp.statement.status").selectByVisibleText(reportFilter.getStatementStatus().getRealValue());
    	}
    	if (reportFilter.getAction() != null && reportFilter.getAction().size() > 0){
    		setAction(reportFilter.getAction());
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
    	if (reportFilter.getStatementSet() != null && reportFilter.getStatementSet().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getStatementSet()){
				setStatementSet(simpleSearch);
			}
    	}
    	if (reportFilter.getAgreementBaseCurrency() != null){
    		element("rp.agreement.base.currency").selectByVisibleText(reportFilter.getAgreementBaseCurrency().getRealValue());
    	}
    }
    
    public void saveAsTemplate(StpDashboardReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
