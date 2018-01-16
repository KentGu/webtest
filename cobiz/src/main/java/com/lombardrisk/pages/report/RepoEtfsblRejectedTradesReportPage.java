package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.RejectedTradesReportFilter;


public final class RepoEtfsblRejectedTradesReportPage extends AbstractReportPage {

    public RepoEtfsblRejectedTradesReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setRepoEtfsblRejectedTradesReport(RejectedTradesReportFilter reportFilter) throws Exception{
    	if (reportFilter.getFeedSystem() != null){
    		element("rp.feed.system").selectByVisibleText(reportFilter.getFeedSystem().getRealValue());
    	}
    }
    
    public void saveAsTemplate(RejectedTradesReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
