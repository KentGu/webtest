package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OptimisationResultReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;


public final class OptimisationResultReportPage extends AbstractReportPage {

    public OptimisationResultReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setOptimisationResultReport(OptimisationResultReportFilter reportFilter) throws Exception{
    	if (reportFilter.getStatus() != null){
    		element("rp.status").selectByVisibleText(reportFilter.getStatus().getRealValue());
    	}
    	if (reportFilter.getInvocationRunId() != null){
    		element("rp.invocation.run.id").selectByVisibleText(reportFilter.getInvocationRunId().getRealValue());
    	}
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
    	if (reportFilter.getEndDateFrom() != null){
    		element("rp.end.date.from").input(reportFilter.getEndDateFrom().getRealValue());
    	}
    	if (reportFilter.getEndDateTo() != null){
    		element("rp.end.date.to").input(reportFilter.getEndDateTo().getRealValue());
    	}
    }
    
    public void saveAsTemplate(OptimisationResultReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
