package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.CashAndAccrualReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;


public final class CashAndAccrualReportPage extends AbstractReportPage {

    public CashAndAccrualReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setCashAndAccrualReport(CashAndAccrualReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getExternalId() != null){
    		setExternalId(reportFilter.getExternalId());
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getCustodian() != null && reportFilter.getCustodian().size() > 0){
			for (OrganisationSearch organisationSearch : reportFilter.getCustodian()){
				setCustodian(organisationSearch);
			}
    	}
    	if (reportFilter.getApplied() != null){
    		element("rp.applied").selectByVisibleText(reportFilter.getApplied().getRealValue());
    	}
    	if (reportFilter.getWht() != null){
    		element("rp.wht").selectByVisibleText(reportFilter.getWht().getRealValue());
    	}
    	if (reportFilter.getIndividualReset() != null){
    		element("rp.individual.reset").selectByVisibleText(reportFilter.getIndividualReset().getRealValue());
    	}
    	if (reportFilter.getInterestSource() != null){
    		element("rp.interest.source").selectByVisibleText(reportFilter.getInterestSource().getRealValue());
    	}
    	if (reportFilter.getInterestPaymentType() != null){
    		element("rp.interest.payment.type").selectByVisibleText(reportFilter.getInterestPaymentType().getRealValue());
    	}
    	
    	if (reportFilter.getMethod() != null){
    		element("rp.method").selectByVisibleText(reportFilter.getMethod().getRealValue());
    	}
    	if (reportFilter.getMonth() != null){
    		element("rp.month").selectByVisibleText(reportFilter.getMonth().getRealValue());
    	}
    	if (reportFilter.getStartDate() != null){
    		element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
    	}
    	if (reportFilter.getEndDate() != null){
    		element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
    	}
    	if (reportFilter.getStartMonth() != null){
    		element("rp.start.month").input(reportFilter.getStartMonth().getRealValue());
    	}
    	if (reportFilter.getEndMonth() != null){
    		element("rp.end.month").input(reportFilter.getEndMonth().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(CashAndAccrualReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
