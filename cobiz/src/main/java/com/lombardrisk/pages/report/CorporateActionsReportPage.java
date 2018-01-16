package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.CorporateActionsReportFilter;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class CorporateActionsReportPage extends AbstractReportPage {

    public CorporateActionsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setCorporateActionsReport(CorporateActionsReportFilter reportFilter) throws Exception{
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getNetPosition() != null){
    		element("rp.net.position").selectByVisibleText(reportFilter.getNetPosition().getRealValue());
    	}
    	if (reportFilter.getAdditionalInfo2() != null){
    		setAdditionalInfo2(reportFilter.getAdditionalInfo2());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(CorporateActionsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}


    public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
    
   
}
