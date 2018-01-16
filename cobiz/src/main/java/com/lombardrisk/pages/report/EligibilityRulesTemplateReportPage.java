package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.EligibilityRulesTemplateReportFilter;


public final class EligibilityRulesTemplateReportPage extends AbstractReportPage {

    public EligibilityRulesTemplateReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setEligibilityRulesTemplateReport(EligibilityRulesTemplateReportFilter reportFilter) throws Exception{
    	if (reportFilter.getTemplateName() != null && reportFilter.getTemplateName().size() > 0){
    		for (int i = 0; i < reportFilter.getTemplateName().size(); i++) {
				element("rp.template.name").selectByVisibleText(reportFilter.getTemplateName().get(i).getRealValue());
			}
    	}
    }
    
    public void saveAsTemplate(EligibilityRulesTemplateReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}


    public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
