package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.HistoricalFxRatesReportFilter;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class HistoricalFxRatesReportPage extends AbstractReportPage {

    public HistoricalFxRatesReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setHistoricalFxRatesReport(HistoricalFxRatesReportFilter reportFilter) throws Exception{
    	if (reportFilter.getCcy() != null){
    		element("rp.ccy").selectByVisibleText(reportFilter.getCcy().getRealValue());
    	}
    	if (reportFilter.getFxRateSet() != null){
    		element("rp.fx.rate.set").selectByVisibleText(reportFilter.getFxRateSet().getRealValue());
    	}
    	if (reportFilter.getDateFrom()!= null){
    		element("rp.date.from").input(reportFilter.getDateFrom().getRealValue());
    	}
    	if (reportFilter.getDateTo()!= null){
    		element("rp.date.to").input(reportFilter.getDateTo().getRealValue());
    	}
    }
    
    public void saveAsTemplate(HistoricalFxRatesReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}


    public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
