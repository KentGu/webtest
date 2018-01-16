package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.ReconciliationOutputReportFilter;


public final class ReconciliationOutputReportPage extends AbstractReportPage {

    public ReconciliationOutputReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setReconciliationOutputReport(ReconciliationOutputReportFilter reportFilter) throws Exception{
    	if (reportFilter.getReconciliationId() != null){
    		element("rp.reconciliation.id").selectByVisibleText(reportFilter.getReconciliationId().getRealValue());
    	}
    	if (reportFilter.getStatus() != null){
    		element("rp.status").selectByVisibleText(reportFilter.getStatus().getRealValue());
    	}
    	if (reportFilter.getMatchType() != null){
    		element("rp.match.type").selectByVisibleText(reportFilter.getMatchType().getRealValue());
    	}
    }
    
    public void saveAsTemplate(ReconciliationOutputReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
