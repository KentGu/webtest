package com.lombardrisk.pages.report;



import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.InterestChangesReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class InterestChangesReportPage extends AbstractReportPage {

    public InterestChangesReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setInterestChangesReport(InterestChangesReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getAssetType() != null){
    		element("rp.asset.type").selectByVisibleText(reportFilter.getAssetType().getRealValue());
    	}
    	if (reportFilter.getInterestSource() != null){
    		element("rp.interest.source").selectByVisibleText(reportFilter.getInterestSource().getRealValue());
    	}
    	if (reportFilter.getStartPeriod() != null){
    		element("rp.start.period").input(reportFilter.getStartPeriod().getRealValue());
    	}
    	if (reportFilter.getEndPeriod() != null){
    		element("rp.end.period").input(reportFilter.getEndPeriod().getRealValue());
    	}
    	if (reportFilter.getStartChangePeriod() != null){
    		element("rp.start.change.period").input(reportFilter.getStartChangePeriod().getRealValue());
    	}
    	if (reportFilter.getEndChangePeriod() != null){
    		element("rp.end.change.period").input(reportFilter.getEndChangePeriod().getRealValue());
    	}
    }
    public void saveAsTemplate(InterestChangesReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
