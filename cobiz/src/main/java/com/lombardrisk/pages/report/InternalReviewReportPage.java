package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.InternalReviewReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;


public final class InternalReviewReportPage extends AbstractReportPage {

    public InternalReviewReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setInternalReviewReport(InternalReviewReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getExposureType() != null){
    		element("rp.exposure.type").selectByVisibleText(reportFilter.getExposureType().getRealValue());
    	}
		if (reportFilter.getAgreementStatus() != null){
			element("rp.agreement.status").selectByVisibleText(reportFilter.getAgreementStatus().getRealValue());
		}
    	if (reportFilter.isIncludeSubAgreement()){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(InternalReviewReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
