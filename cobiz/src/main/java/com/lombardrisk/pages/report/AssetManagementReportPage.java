package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AssetManagementReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class AssetManagementReportPage extends AbstractReportPage {

    public AssetManagementReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAssetManagementReport(AssetManagementReportFilter reportFilter) throws Exception{
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
		if (reportFilter.getAssetClass() != null) {
			element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
		}
		if (reportFilter.getAssetType() != null) {
			element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
		}
		if (reportFilter.getBookingType() != null && reportFilter.getBookingType().size() > 0){
			setBookingType(reportFilter.getBookingType());
		}
		if (reportFilter.getDeliverPriority() != null){
			element("rp.deliver.priority").selectByVisibleText(reportFilter.getDeliverPriority().getRealValue());
		}
		if (reportFilter.getRecallPriority() != null){
			element("rp.recall.priorit").selectByVisibleText(reportFilter.getRecallPriority().getRealValue());
		}
		if (reportFilter.getNetPosition() != null){
			element("rp.net.position").selectByVisibleText(reportFilter.getNetPosition().getRealValue());
		}
		if (reportFilter.getAssetReusePermitted() != null){
			element("rp.asset.reuser.permitted").selectByVisibleText(reportFilter.getAssetReusePermitted().getRealValue());
		}
		if (reportFilter.getIncludeAmendedAgreements() != null){
			element("rp.include.amended.agreements").selectByVisibleText(reportFilter.getIncludeAmendedAgreements().getRealValue());
		}
		if (reportFilter.getNetFor() != null){
			element("rp.net.for").selectByVisibleText(reportFilter.getNetFor().getRealValue());
		}
    }
    
    public void saveAsTemplate(AssetManagementReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}


    public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }




    


   
}
