package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.PriceAgeExceptionsReportFilter;
import com.lombardrisk.pojo.SimpleSearch;


public final class PriceAgeExceptionsReportPage extends AbstractReportPage {

    public PriceAgeExceptionsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setPriceAgeExceptionsReport(PriceAgeExceptionsReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getPriceAgeGreaterThan() != null){
    		element("rp.price.age.greater.tha").input(reportFilter.getPriceAgeGreaterThan().getRealValue());
    	}
    	if (reportFilter.getPriceUnchangedDaysGreaterThan() != null){
    		element("rp.price.unchanged.days.greater.than").input(reportFilter.getPriceUnchangedDaysGreaterThan().getRealValue());
    	}
    	if (reportFilter.getReferenceBy() != null && reportFilter.getReferenceBy().size() >0){
    		setReferenceBy(reportFilter.getReferenceBy());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.amended.agreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void savaAsTemplate(PriceAgeExceptionsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
