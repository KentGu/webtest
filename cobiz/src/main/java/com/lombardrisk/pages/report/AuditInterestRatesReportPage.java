package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditInterestRatesReportFilter;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class AuditInterestRatesReportPage extends AbstractReportPage {

    public AuditInterestRatesReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditInterestRatesReport(AuditInterestRatesReportFilter reportFilter) throws Exception{
		if (reportFilter.getUser() != null){
			element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
		}
		if (reportFilter.getOperation() != null){
			element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
		}
    	if (reportFilter.getRateName() != null){
    		element("rp.rate.name").selectByVisibleText(reportFilter.getRateName().getRealValue());
    	}
		if (reportFilter.getStartDate() != null){
			element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
		}
		if (reportFilter.getEndDate() != null){
			element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
		}
    }
    public void saveAsTemplate(AuditInterestRatesReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
