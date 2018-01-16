package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;


import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.PriceExceptionsReportFilter;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class PriceExceptionsReportPage extends AbstractReportPage {

    public PriceExceptionsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setPriceExceptionsReport(PriceExceptionsReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null && reportFilter.getAssetType().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetType()){
				setAssetType(simpleSearch);
			}
		}
    	if (reportFilter.getInstrumentId() != null){
    		setInstrumentId(reportFilter.getInstrumentId());
    	}
    	if (reportFilter.getCleanPriceRule() != null){
    		element("rp.clean.price.rule").input(reportFilter.getCleanPriceRule().getRealValue());
    	}
    	if (reportFilter.getPriceDateFrom() != null){
    		element("rp.price.date.from").input(reportFilter.getPriceDateFrom().getRealValue());
    	}
    	if (reportFilter.getPriceDateTo() != null){
    		element("rp.price.date.to").input(reportFilter.getPriceDateTo().getRealValue());
    	}
    	if (reportFilter.getPriceSource() != null){
    		element("rp.price.source").input(reportFilter.getPriceSource().getRealValue());
    	}
    	if (reportFilter.getReferenceBy() != null && reportFilter.getReferenceBy().size() >0){
    		setReferenceBy(reportFilter.getReferenceBy());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(PriceExceptionsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
