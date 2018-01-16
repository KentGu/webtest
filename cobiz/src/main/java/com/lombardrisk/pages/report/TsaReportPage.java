package com.lombardrisk.pages.report;

import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;
import com.lombardrisk.pojo.TsaReportFilter;




public final class TsaReportPage extends AbstractReportPage {


	public TsaReportPage(IWebDriverWrapper webDriverWrapper) {
		super(webDriverWrapper);
	}

	public void setTsaReport(TsaReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getCcp() != null && reportFilter.getCcp().size() > 0){
			for (SimpleSearch simpleSearch : reportFilter.getCcp()){
				setCcp(simpleSearch);
			}
    	}
    	if (reportFilter.getAgreementStatus() != null){
    		element("rp.agreement.status").selectByVisibleText(reportFilter.getAgreementStatus().getRealValue());
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
    	
    	if (reportFilter.getTsaRule() != null && reportFilter.getTsaRule().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getTsaRule()){
				setCashflowRule(simpleSearch);
			}
    	}else if (reportFilter.getCashflowRule()!=null && reportFilter.getCashflowRule().size()>0){
			for (SimpleSearch simpleSearch : reportFilter.getCashflowRule())
				setCashflowRule(simpleSearch);
		}
    	
    	if (reportFilter.getTsaBookingDifference() != null){
    		element("rp.tsa.booking.difference").selectByVisibleText(reportFilter.getTsaBookingDifference().getRealValue());
    	}else if (reportFilter.getCashflowBookingDifference()!=null)
			element("rp.cashflow.booking.difference").selectByVisibleText(reportFilter.getCashflowBookingDifference().getRealValue());
    	if (reportFilter.getType() != null){
    		element("rp.type").selectByVisibleText(reportFilter.getType().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
	}
	
	public void savaAsTemplate(TsaReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
}
