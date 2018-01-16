package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.ReconciliationTradesOutputReportFilter;


public final class ReconciliationTradesOutputReportPage extends AbstractReportPage {

    public ReconciliationTradesOutputReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setReconciliationTradesOutputReport(ReconciliationTradesOutputReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getProductType() != null){
    		element("rp.productType").selectByVisibleText(reportFilter.getProductType().getRealValue());
    	}
    	if (reportFilter.getTradeStatus() != null){
    		element("rp.trade.status").selectByVisibleText(reportFilter.getTradeStatus().getRealValue());
    	}
    	
    	if (reportFilter.getTradeDateFrom() != null){
    		element("rp.trade.date.from").input(reportFilter.getTradeDateFrom().getRealValue());
    	}
    	if (reportFilter.getTradeDateTo() != null){
    		element("rp.trade.date.to").input(reportFilter.getTradeDateTo().getRealValue());
    	}
    	
    	if (reportFilter.getEffectiveDateFrom() != null){
    		element("rp.effective.date.from").input(reportFilter.getEffectiveDateFrom().getRealValue());
    	}
    	if (reportFilter.getEffectiveDateTo() != null){
    		element("rp.effective.date.to").input(reportFilter.getEffectiveDateTo().getRealValue());
    	}
    	if (reportFilter.getMaturityDateFrom() != null){
    		element("rp.maturity.date.from").input(reportFilter.getMaturityDateFrom().getRealValue());
    	}
    	if (reportFilter.getMaturityDateTo() != null){
    		element("rp.maturity.date.to").input(reportFilter.getMaturityDateTo().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(ReconciliationTradesOutputReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
