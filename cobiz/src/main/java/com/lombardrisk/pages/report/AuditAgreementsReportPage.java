package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditAgreementsReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;



public final class AuditAgreementsReportPage extends AbstractReportPage {

    public AuditAgreementsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditAgreementsReport(AuditAgreementsReportFilter reportFilter) throws Exception{
    	if (reportFilter.getUser() != null){
    		element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
    	}
    	if (reportFilter.getOperation() != null){
    		element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
    	}
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
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getLinkageSet() != null){
    		element("rp.linkage.set").selectByVisibleText(reportFilter.getLinkageSet().getRealValue());
    	}
    	if (reportFilter.getStartDate() != null){
    		element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
    	}
    	if (reportFilter.getEndDate() != null){
    		element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
    	}
    	if (reportFilter.isIncludeSubAgreement() != null){
    		element("rp.include.subAgreements").check(reportFilter.isIncludeSubAgreement());
    	}
    }
    
    public void saveAsTemplate(AuditAgreementsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
    
   
}
