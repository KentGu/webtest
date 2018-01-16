package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.RehypothecationEligibilityReportFilter;



public final class RehypothecationEligibilityReportPage extends AbstractReportPage {

    public RehypothecationEligibilityReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setRehypothecationEligibilityReport(RehypothecationEligibilityReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAgreementRehypothecationEligibility() != null){
    		element("rp.agreement.rehypothecation.eligibility").selectByVisibleText(reportFilter.getAgreementRehypothecationEligibility().getRealValue());
    	}
    	if (reportFilter.getAssetRehypothecationEligibility() != null){
    		element("rp.asset.rehypothecation.eligibility").selectByVisibleText(reportFilter.getAssetRehypothecationEligibility().getRealValue());
    	}
    	if (reportFilter.getPositionType() != null){
    		element("rp.positionType").selectByVisibleText(reportFilter.getPositionType().getRealValue());
    	}
    	if (reportFilter.getCallStatus() != null){
    		element("rp.callStatus").selectByVisibleText(reportFilter.getCallStatus().getRealValue());
    	}
    	if (reportFilter.getReportingCurrency() != null){
    		element("rp.reporting.currenc").selectByVisibleText(reportFilter.getReportingCurrency().getRealValue());
    	}
    }
    
    public void saveAsTemplate(RehypothecationEligibilityReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
