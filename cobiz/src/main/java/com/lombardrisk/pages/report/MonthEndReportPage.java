package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.MonthEndReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class MonthEndReportPage extends AbstractReportPage {

    public MonthEndReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setMonthEndReport(MonthEndReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getExcludeStatus() != null){
    		element("rp.exclude.status").selectByVisibleText(reportFilter.getExcludeStatus().getRealValue());
    	}
    }
    
    public void saveAsTemplate(MonthEndReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
