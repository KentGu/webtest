package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditEligibilityRuleTemplateReportFilter;



public final class AuditEligibilityRuleTemplateReportPage extends AbstractReportPage {

    public AuditEligibilityRuleTemplateReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditEligibilityRuleTemplateReport(AuditEligibilityRuleTemplateReportFilter reportFilter) throws Exception{
		if (reportFilter.getUser() != null){
			element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
		}
		if (reportFilter.getOperation() != null){
			element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
		}
    	if (reportFilter.getTemplate() != null){
    		element("rp.template").selectByVisibleText(reportFilter.getTemplate().getRealValue());
    	}
		if (reportFilter.getStartDate() != null){
			element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
		}
		if (reportFilter.getEndDate() != null){
			element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
		}
    }
    
    public void saveAsTemplate(AuditEligibilityRuleTemplateReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
