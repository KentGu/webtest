package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.RepoEtfsblDailyExporesureReportFilter;
import com.lombardrisk.pojo.SimpleSearch;



public final class RepoEtfsblDailyExposureReportPage extends AbstractReportPage {

    public RepoEtfsblDailyExposureReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setRepoEtfsblDailyExposureReport(RepoEtfsblDailyExporesureReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getExposureProfile() != null){
    		element("rp.exposure.profile").selectByVisibleText(reportFilter.getExposureProfile().getRealValue());
    	}
    	if (reportFilter.getAgreementStatus() != null && reportFilter.getAgreementStatus().size() > 0){
    		setAgreementStatus(reportFilter.getAgreementStatus());
    	}
    	if (reportFilter.getExposureType() != null){
    		element("rp.exposure.type").selectByVisibleText(reportFilter.getExposureType().getRealValue());
    	}
    	if (reportFilter.getCollateralManager() != null){
    		element("rp.collateral.manager").selectByVisibleText(reportFilter.getCollateralManager().getRealValue());
    	}
    	if (reportFilter.getStatementStatus() != null){
    		element("rp.statement.status").selectByVisibleText(reportFilter.getStatementStatus().getRealValue());
    	}
    	if (reportFilter.getEventCategory() != null){
    		element("rp.event.category").selectByVisibleText(reportFilter.getEventCategory().getRealValue());
    	}
    	if (reportFilter.getCallStatus() != null && reportFilter.getCallStatus().size() > 0){
    		setCallStatus(reportFilter.getCallStatus());
    	}
    	if (reportFilter.getReportingCurrency() != null){
    		element("rp.reporting.currency").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
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
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.amended.agreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(RepoEtfsblDailyExporesureReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
   
}
