package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.InventoryManagerReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;



public final class InventoryManagerReportPage extends AbstractReportPage {

    public InventoryManagerReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setInventoryManagerReport(InventoryManagerReportFilter reportFilter) throws Exception{
    	if (reportFilter.getPartyA() != null){
    		element("rp.party.a").input(reportFilter.getPartyA().getRealValue());
    	}
    	if (reportFilter.getPartyB() != null){
    		element("rp.party.b").input(reportFilter.getPartyB().getRealValue());
    	}
    	if (reportFilter.getBook() != null){
    		element("rp.book").selectByVisibleText(reportFilter.getBook().getRealValue());
    	}
    	if (reportFilter.getSourceSystem() != null && reportFilter.getSourceSystem().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getSourceSystem()){
    			setSourceSystem(simpleSearch);
    		}
    	}
    	if (reportFilter.getAssetClass() != null) {
			element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
		}
		if (reportFilter.getAssetType() != null) {
			element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
		}
		if (reportFilter.getDeliverPriority() != null){
			element("rp.deliver.priority").selectByVisibleText(reportFilter.getDeliverPriority().getRealValue());
		}
		if (reportFilter.getRecallPriority() != null){
			element("rp.recall.priority").selectByVisibleText(reportFilter.getRecallPriority().getRealValue());
		}
		if (reportFilter.getNetPosition() != null){
			element("rp.net.position").selectByVisibleText(reportFilter.getNetPosition().getRealValue());
		}
		if (reportFilter.getRehypothecationPermitted() != null){
			element("rp.rehypothecation.permitted").selectByVisibleText(reportFilter.getRehypothecationPermitted().getRealValue());
		}
		if (reportFilter.getRehypothecated() != null){
			element("rp.rehypothecated").selectByVisibleText(reportFilter.getRehypothecated().getRealValue());
		}
		if (reportFilter.getAssetOwner() != null && reportFilter.getAssetOwner().size() > 0){
			for (OrganisationSearch organisationSearch : reportFilter.getAssetOwner()) {
				setAssetOwner(organisationSearch);
			}
		}
		if (reportFilter.getIncludeCollineData() != null){
			element("rp.include.colline.data").selectByVisibleText(reportFilter.getIncludeCollineData().getRealValue());
		}
		if (reportFilter.isIncludeAmendedAgreement() != null){
			element("rp.include.amended.agreements").check(reportFilter.isIncludeAmendedAgreement());
		}
    }
    
    public void saveAsTemplate(InventoryManagerReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
   
}
