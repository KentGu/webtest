package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.*;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.util.List;


public final class BadStagingFeedReportPage extends AbstractReportPage {

    public BadStagingFeedReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

//    @Override
//	public void setBusinessLine(List<ReportBusinessLineType> reportBusinessLineTypeList) throws Exception {
//		element("rp.tradebusinessLine").selectByVisibleText(reportBusinessLineTypeList.get(0).value());
//	}

//	public void setBusinessLine(StringType businessLine) throws Exception {
//		element("rp.tradeBusinessLine").selectByVisibleText(businessLine.getRealValue());
//	}
    
    public void setBadStagingFeedReport(BadStagingFeedReportFilter reportFilter) throws Exception{
    	if (reportFilter.getFeedTask() != null){
    		element("rp.feed.task").selectByVisibleText(reportFilter.getFeedTask().getRealValue());
		}
		if (reportFilter.getFeedExternalSystem() != null){
			element("rp.feed.external.system").selectByVisibleText(reportFilter.getFeedExternalSystem().getRealValue());
		}
    }
    
    public void saveAsTemplate(BadStagingFeedReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
