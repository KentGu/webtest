package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OptimisationRuleReportFilter;



public final class OptimisationRuleReportPage extends AbstractReportPage {

    public OptimisationRuleReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setOptimisationRuleReport(OptimisationRuleReportFilter reportFilter) throws Exception{
    	if (reportFilter.getUser() != null){
    		element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
    	}
    	if (reportFilter.getInvocationRunId() != null){
    		element("rp.invocation.run.id").selectByVisibleText(reportFilter.getInvocationRunId().getRealValue());
    	}
    	if (reportFilter.getRuleName() != null){
    		setRuleName(reportFilter.getRuleName());
    	}
    	if (reportFilter.getRuleType() != null){
    		element("rp.rule.type").selectByVisibleText(reportFilter.getRuleType().getRealValue());
    	}
    	if (reportFilter.getStartDateFrom() != null){
    		element("rp.start.date.from").input(reportFilter.getStartDateFrom().getRealValue());
    	}
    	if (reportFilter.getStartDateTo() != null){
    		element("rp.start.date.to").input(reportFilter.getStartDateTo().getRealValue());
    	}
    }
    public void saveAsTemplate(OptimisationRuleReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
}
