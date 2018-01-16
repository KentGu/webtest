package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.LicensingReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class LicensingReportPage extends AbstractReportPage {

    public LicensingReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setLicensingReport(LicensingReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAgreementStatus() != null){
    		element("rp.agreement.status").selectByVisibleText(reportFilter.getAgreementStatus().getRealValue());
    	}
    	if (reportFilter.getMonth() != null){
    		element("rp.month").selectByVisibleText(reportFilter.getMonth().getRealValue());
    	}
    	if (reportFilter.getStartCreationDate() != null){
    		element("rp.start.creation.date").input(reportFilter.getStartCreationDate().getRealValue());
    	}
    	if (reportFilter.getEndCreationDate() != null){
    		element("rp.end.creation.date").input(reportFilter.getEndCreationDate().getRealValue());
    	}
    }
    
    public void saveAsTemplate(LicensingReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}


      public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
