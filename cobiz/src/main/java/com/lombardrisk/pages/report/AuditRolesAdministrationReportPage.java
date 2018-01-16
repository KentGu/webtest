package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditRolesAdministrationReportFilter;



public final class AuditRolesAdministrationReportPage extends AbstractReportPage {

    public AuditRolesAdministrationReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditRolesAdministrationReport(AuditRolesAdministrationReportFilter reportFilter) throws Exception{
    	if (reportFilter.getAmendedBy() != null){
    		element("rp.amended.by").selectByVisibleText(reportFilter.getAmendedBy().getRealValue());
    	}
    	if (reportFilter.getRole() != null){
    		element("rp.role").selectByVisibleText(reportFilter.getRole().getRealValue());
    	}
    	if (reportFilter.getOperation() != null){
    		element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
    	}
		if (reportFilter.getStartDate() != null){
			element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
		}
		if (reportFilter.getEndDate() != null){
			element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
		}
    }
    
    public void saveAsTemplate(AuditRolesAdministrationReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
