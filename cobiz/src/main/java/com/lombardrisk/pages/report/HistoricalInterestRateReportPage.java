package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.HistoricalInterestRateReportFilter;


public final class HistoricalInterestRateReportPage extends AbstractReportPage {

    public HistoricalInterestRateReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setHistoricalInterestRateReport(HistoricalInterestRateReportFilter reportFilter) throws Exception{
    	if (reportFilter.getReferenceRate() != null){
    		element("rp.reference.rate").selectByVisibleText(reportFilter.getReferenceRate().getRealValue());
    	}
		if (reportFilter.getDateFrom()!= null){
			element("rp.date.from").input(reportFilter.getDateFrom().getRealValue());
		}
		if (reportFilter.getDateTo()!= null){
			element("rp.date.to").input(reportFilter.getDateTo().getRealValue());
		}
    }
    
    public void saveAsTemplate(HistoricalInterestRateReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}


      public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
