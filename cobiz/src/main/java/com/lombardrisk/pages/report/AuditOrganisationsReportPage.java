package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.AuditOrganisationsReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;



public final class AuditOrganisationsReportPage extends AbstractReportPage {

    public AuditOrganisationsReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setAuditOrganisationsReport(AuditOrganisationsReportFilter reportFilter) throws Exception{
		if (reportFilter.getUser() != null){
			element("rp.user").selectByVisibleText(reportFilter.getUser().getRealValue());
		}
		if (reportFilter.getOperation() != null){
			element("rp.operation").selectByVisibleText(reportFilter.getOperation().getRealValue());
		}
    	if (reportFilter.getOrganisation() != null && reportFilter.getOrganisation().size() > 0){
    		for (OrganisationSearch organisationSearch : reportFilter.getOrganisation()){
				setOrganisation(organisationSearch);
    		}
    	}
    	if (reportFilter.getOrgRegion() != null){
    		element("rp.org.region").selectByVisibleText(reportFilter.getOrgRegion().getRealValue());
    	}
		if (reportFilter.getStartDate() != null){
			element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
		}
		if (reportFilter.getEndDate() != null){
			element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
		}
    }
    
    public void saveAsTemplate(AuditOrganisationsReportFilter reportFilter) throws Exception{
		if (reportFilter.getSaveAsTemplate() != null) {
			savaAsTemplate(reportFilter.getSaveAsTemplate());
		}
	}
	
	public void enterOutputFormatPage() throws Exception {
        editOutputFormat();
    }
    
   
}
