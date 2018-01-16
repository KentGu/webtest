package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;


import com.lombardrisk.pojo.AssetClassification;
import com.lombardrisk.pojo.AuditAssetDefinitionReportFilter;

import org.yiwan.webcore.web.IWebDriverWrapper;



public final class AuditAssetDefinitionReportPage extends AbstractReportPage {

    public AuditAssetDefinitionReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditAssetDefinitionReport(AuditAssetDefinitionReportFilter reportFilter) throws Exception{
    	if (reportFilter.getUser() != null){
    		element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
    	}
    	if (reportFilter.getOperation() != null){
    		element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
    	}
    	if (reportFilter.getAssetClass() != null){
    		element("rp.asset.class").selectByVisibleText(reportFilter.getAssetClass().getRealValue());
    	}
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
		if (reportFilter.getAssetClassification()!=null && reportFilter.getAssetClassification().size()>0){
			for (int i=0; i<reportFilter.getAssetClassification().size(); i++){
				AssetClassification assetClassification = reportFilter.getAssetClassification().get(i);
				if (assetClassification.getAssetClassificationName()!=null&&assetClassification.getAssetcategory()!=null)
					element("rp.class.category",assetClassification.getAssetClassificationName().getRealValue().toLowerCase()).selectByVisibleText(assetClassification.getAssetcategory().getRealValue());
			}
		}
    	if (reportFilter.getStartDate() != null){
    		element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
    	}
    	if (reportFilter.getEndDate() != null){
    		element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
    	}
    }
    
    public void saveAsTemplate(AuditAssetDefinitionReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
   
}
