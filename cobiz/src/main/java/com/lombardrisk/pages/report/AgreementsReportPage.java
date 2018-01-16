package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AgreementsReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;


public final class AgreementsReportPage extends AbstractReportPage {

    public AgreementsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAgreementsReport(AgreementsReportFilter reportFilter) throws Exception{
    	if (reportFilter.getPrincipal() != null && reportFilter.getPrincipal().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getPrincipal()){
				setPrincipal(organisationSearch);
			}
        }
    	if (reportFilter.getPrincipalExclusion() != null && reportFilter.getPrincipalExclusion().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getPrincipalExclusion()){
				setPrincipalExclusion(organisationSearch);
			}
        }
    	if (reportFilter.getCounterparty() != null && reportFilter.getCounterparty().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getCounterparty()){
				setCounterParty(organisationSearch);
			}
		}
    	if (reportFilter.getCounterpartyExclusion() != null && reportFilter.getCounterpartyExclusion().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getCounterpartyExclusion()){
				setCounterPartyExclusion(organisationSearch);
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
    	if (reportFilter.getDocumentType() != null){
    		element("rp.document.type").selectByVisibleText(reportFilter.getDocumentType().getRealValue());
    	}
    	if (reportFilter.getAgreementStatus() != null){
    		element("rp.agreement.status").selectByVisibleText(reportFilter.getAgreementStatus().getRealValue());
    	}
    	if (reportFilter.getMonth() != null){
    		element("rp.month").selectByVisibleText(reportFilter.getMonth().getRealValue());
    	}
    	if (reportFilter.getStartCreationDate() != null){
    		element("rp.start.creation.date").input(reportFilter.getStartCreationDate().getRealValue());
    	}
    	if (reportFilter.getEndCreationDate() != null){
    		element("rp.end.creation.date").input(reportFilter.getEndCreationDate().getRealValue());
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
    	if (reportFilter.getDistributionMedium() != null && reportFilter.getDistributionMedium().size() > 0){
    		setDistributionMedium(reportFilter.getDistributionMedium());
    	}
    	if (reportFilter.getStatementSet() != null && reportFilter.getStatementSet().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getStatementSet()){
				setStatementSet(simpleSearch);
			}
    	}
    	if (reportFilter.getMarginType() != null){
    		element("rp.margin.type").selectByVisibleText(reportFilter.getMarginType().value());
    	}
    	if (reportFilter.getAssetClass() != null && reportFilter.getAssetClass().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetClass()){
				setAssetClass(simpleSearch);
			}
		}
    	
    	if (reportFilter.getAssetType() != null && reportFilter.getAssetType().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getAssetType()){
				setAssetType(simpleSearch);
			}
		}
    	if (reportFilter.getCustodian() != null && reportFilter.getCustodian().size() > 0){
			for (OrganisationSearch organisationSearch : reportFilter.getCustodian()){
				setCustodian(organisationSearch);
			}
    	}
    	if (reportFilter.getWht() != null){
    		element("rp.wht").selectByVisibleText(reportFilter.getWht().getRealValue());
    	}
    }
    
    public void saveAsTemplate(AgreementsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
