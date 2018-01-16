package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.RepoEtfsblTradesOutputReportFilter;
import com.lombardrisk.pojo.SimpleSearch;


public final class RepoEtfsblTradesOutputReportPage extends AbstractReportPage {

    public RepoEtfsblTradesOutputReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setRepoEtfsblTradesOutputReport(RepoEtfsblTradesOutputReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
			setBusinessLine(reportFilter.getBusinessLine());
    	}
    	if (reportFilter.getOrderType() != null && reportFilter.getOrderType() != null){
    		setOrderType(reportFilter.getOrderType());
    	}
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getAgreementBaseCurrency() != null){
    		element("rp.agreement.base.currency").selectByVisibleText(reportFilter.getAgreementBaseCurrency().getRealValue());
    	}
    	if (reportFilter.getProductCategory() != null && reportFilter.getProductCategory().size() > 0){
    		setProductCategory(reportFilter.getProductCategory());
    	}
    	if (reportFilter.getProductType() != null && reportFilter.getProductType().size() > 0){
    		setProductType(reportFilter.getProductType());
    	}
    	if (reportFilter.getValuationInformation() != null){
    		element("rp.valuation.information").selectByVisibleText(reportFilter.getValuationInformation().getRealValue());
    	}
    	if (reportFilter.getSourceSystem() != null){
    		element("rp.source.system").selectByVisibleText(reportFilter.getSourceSystem().getRealValue());
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
    	
    	if (reportFilter.getStartDateFrom() != null){
    		element("rp.start.date.from").input(reportFilter.getStartDateFrom().getRealValue());
    	}
    	if (reportFilter.getStartDateTo() != null){
    		element("rp.start.date.to").input(reportFilter.getStartDateTo().getRealValue());
    	}
    	if (reportFilter.getEndDateFrom() != null){
    		element("rp.end.date.from").input(reportFilter.getEndDateFrom().getRealValue());
    	}
    	if (reportFilter.getEndDateTo() != null){
    		element("rp.end.date.to").input(reportFilter.getEndDateTo().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    	if (reportFilter.isIncludeOpenRepoTrade() != null){
    		element("rp.include.open.repo.trades").check(reportFilter.isIncludeOpenRepoTrade());
    	}
    }
    
    public void saveAsTemplate(RepoEtfsblTradesOutputReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
