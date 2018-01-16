package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;
import com.lombardrisk.pojo.TradesOutputReportFilter;


public final class TradesOutputReportPage extends AbstractReportPage {

    public TradesOutputReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setTradesOutputReport(TradesOutputReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getAgreementBaseCurrency() != null){
    		element("rp.agreement.base.currency").selectByVisibleText(reportFilter.getAgreementBaseCurrency().getRealValue());
    	}
    	if (reportFilter.getProductType() != null){
    		element("rp.productType").selectByVisibleText(reportFilter.getProductType().getRealValue());
    	}
    	if (reportFilter.getValuationInformation() != null){
    		element("rp.valuation.information").selectByVisibleText(reportFilter.getValuationInformation().getRealValue());
    	}
    	if (reportFilter.getSourceSystem() != null){
    		element("rp.source.system").selectByVisibleText(reportFilter.getSourceSystem().getRealValue());
    	}
    	if (reportFilter.getMtmSet() != null){
    		element("rp.mtm.set").selectByVisibleText(reportFilter.getMtmSet().getRealValue());
    	}
    	if (reportFilter.getExcludeStatus() != null){
    		element("rp.exclude.status").selectByVisibleText(reportFilter.getExcludeStatus().getRealValue());
    	}
    	if (reportFilter.getTradeDateFrom() != null){
    		element("rp.trade.date.from").input(reportFilter.getTradeDateFrom().getRealValue());
    	}
    	if (reportFilter.getTradeDateTo() != null){
    		element("rp.trade.date.to").input(reportFilter.getTradeDateTo().getRealValue());
    	}
    	if (reportFilter.getEffectiveDateFrom() != null){
    		element("effective_date_from").input(reportFilter.getEffectiveDateFrom().getRealValue());
    	}
    	if (reportFilter.getEffectiveDateTo() != null){
    		element("effective_date_to").input(reportFilter.getEffectiveDateTo().getRealValue());
    	}
    	if (reportFilter.getMaturityDateFrom() != null){
    		element("rp.maturity.date.from").input(reportFilter.getMaturityDateFrom().getRealValue());
    	}
    	if (reportFilter.getMaturityDateTo() != null){
    		element("rp.maturity.date.to").input(reportFilter.getMaturityDateTo().getRealValue());
    	}
    }
    public void saveAsTemplate(TradesOutputReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
