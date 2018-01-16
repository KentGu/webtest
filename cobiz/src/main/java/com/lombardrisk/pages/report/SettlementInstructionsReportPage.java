package com.lombardrisk.pages.report;

import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SettlementInstructionsReportFilter;




public final class SettlementInstructionsReportPage extends AbstractReportPage {

    public SettlementInstructionsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setSettlementInstructionsReport(SettlementInstructionsReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
			setBusinessLine(reportFilter.getBusinessLine());
    	}
    	
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getType() != null){
    		element("rp.type").selectByVisibleText(reportFilter.getType().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(SettlementInstructionsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
