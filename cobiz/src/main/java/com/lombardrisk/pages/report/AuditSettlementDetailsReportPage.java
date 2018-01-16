package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditSettlementDetailsReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;



public final class AuditSettlementDetailsReportPage extends AbstractReportPage {

    public AuditSettlementDetailsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditSettlementDetailsReport(AuditSettlementDetailsReportFilter reportFilter) throws Exception{
		if (reportFilter.getUser() != null){
			element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
		}
		if (reportFilter.getOperation() != null){
			element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
		}
    	if (reportFilter.getOrganisation() != null && reportFilter.getOrganisation().size() > 0){
    		for (OrganisationSearch organisationSearch : reportFilter.getOrganisation()){
				setOrganisation(organisationSearch);
    		}
    	}
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getBucket() != null){
    		element("rp.bucket").selectByVisibleText(reportFilter.getBucket().getRealValue());
    	}
		if (reportFilter.getStartDate() != null){
			element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
		}
		if (reportFilter.getEndDate() != null){
			element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
		}
    }
    
    public void saveAsTemplate(AuditSettlementDetailsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
    
   
}
