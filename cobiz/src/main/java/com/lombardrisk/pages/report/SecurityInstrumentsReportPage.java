package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;
import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.SecurityInstrumentsReportFilter;



public final class SecurityInstrumentsReportPage extends AbstractReportPage {

    public SecurityInstrumentsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setSecurityInstrumentsReport(SecurityInstrumentsReportFilter reportFilter) throws Exception{
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getAssetStatus() != null){
    		element("rp.asset.status").selectByVisibleText(reportFilter.getAssetStatus().getRealValue());
    	}
    	if (reportFilter.getReportingCurrency() != null){
    		element("rp.reporting.currency").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
    	}
    	if (reportFilter.getAdditionalInfo1() != null){
    		setAdditionalInfo1(reportFilter.getAdditionalInfo1());
    	}
    	if (reportFilter.getAdditionalInfo2() != null){
			setAdditionalInfo2(reportFilter.getAdditionalInfo2());
    	}
    	if (reportFilter.getMaturityDateFrom() != null){
    		element("rp.maturity.date.from").input(reportFilter.getMaturityDateFrom().getRealValue());
    	}
    	if (reportFilter.getMaturityDateTo() != null){
    		element("rp.maturity.date.to").input(reportFilter.getMaturityDateTo().getRealValue());
    	}
    	if (reportFilter.getPriceDateFrom() != null){
    		element("rp.price.date.from").input(reportFilter.getPriceDateFrom().getRealValue());
    	}
    	if (reportFilter.getPriceDateTo() != null){
    		element("rp.price.date.to").input(reportFilter.getPriceDateTo().getRealValue());
    	}
    }
    public void saveAsTemplate(SecurityInstrumentsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
