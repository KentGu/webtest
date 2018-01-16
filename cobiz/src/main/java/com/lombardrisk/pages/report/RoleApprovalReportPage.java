package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.RoleApprovalReportFilter;



public final class RoleApprovalReportPage extends AbstractReportPage {

    public RoleApprovalReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAssetHoldingsAndValuationReport(RoleApprovalReportFilter reportFilter) throws Exception{
    	if (reportFilter.getRole() != null && reportFilter.getRole().size() > 0){
    		setRole(reportFilter.getRole());
    	}
    	if (reportFilter.getPrivilege() != null){
    		setPrivilege(reportFilter.getPrivilege());
    	}
    	if (reportFilter.getOperation() != null){
    		element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
    	}
    	if (reportFilter.getStatus() != null && reportFilter.getStatus().size() > 0){
    		setStatus(reportFilter.getRole());
    	}
    	if (reportFilter.getChangedStartDate() != null){
    		element("rp.changed.start.date").input(reportFilter.getChangedStartDate().getRealValue());
    	}
    	if (reportFilter.getChangedEndDate() != null){
    		element("rp.changed.end.date").input(reportFilter.getChangedEndDate().getRealValue());
    	}
    	if (reportFilter.getReviewedStartDate() != null){
    		element("rp.reviewed.start.date").input(reportFilter.getReviewedStartDate().getRealValue());
    	}
    	if (reportFilter.getReviewedEndDate() != null){
    		element("rp.reviewed.end.date").input(reportFilter.getReviewedEndDate().getRealValue());
    	}
    }
    public void saveAsTemplate(RoleApprovalReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
