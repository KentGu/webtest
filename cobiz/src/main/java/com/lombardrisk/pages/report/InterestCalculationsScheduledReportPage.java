package com.lombardrisk.pages.report;



import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.InterestCalculationsScheduleReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;


public final class InterestCalculationsScheduledReportPage extends AbstractReportPage {

    public InterestCalculationsScheduledReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setInterestCalculationsScheduledReport(InterestCalculationsScheduleReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getAgreementDescription() != null) {
			setAgreementDescription(reportFilter.getAgreementDescription());
        }
    	if (reportFilter.getBusinessLine() != null && reportFilter.getBusinessLine().size() > 0){
			setBusinessLine(reportFilter.getBusinessLine());
    	}
    	if (reportFilter.getCashAssetType() != null){
    		element("rp.cash.asset.type").selectByVisibleText(reportFilter.getCashAssetType().getRealValue());
    	}
    	if (reportFilter.getInterestSource() != null){
    		element("rp.interest.source").selectByVisibleText(reportFilter.getInterestSource().getRealValue());
    	}
    	
    	if (reportFilter.getCalculationRule() != null){
    		element("rp.calculation.rule").selectByVisibleText(reportFilter.getCalculationRule().getRealValue());
    	}
    	if (reportFilter.getMethod() != null){
    		element("rp.method").selectByVisibleText(reportFilter.getMethod().getRealValue());
    	}
    	if (reportFilter.getMonth() != null){
    		element("rp.month").selectByVisibleText(reportFilter.getMonth().getRealValue());
    	}
    	if (reportFilter.getStartDate() != null){
    		element("rp.start.date").input(reportFilter.getStartDate().getRealValue());
    	}
    	if (reportFilter.getEndDate() != null){
    		element("rp.end.date").input(reportFilter.getEndDate().getRealValue());
    	}
    	if (reportFilter.isIncludeZeroInterest() != null){
    		element("rp.include.zero.interest").check(reportFilter.isIncludeZeroInterest());
    	}
    	if (reportFilter.isIncludeWht() != null){
    		element("rp.include.wht").check(reportFilter.isIncludeWht());
    	}
    }
    
    public void saveAsTemplate(InterestCalculationsScheduleReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
