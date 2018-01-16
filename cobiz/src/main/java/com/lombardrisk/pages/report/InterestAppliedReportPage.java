package com.lombardrisk.pages.report;


import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.InterestAppliedReportFilter;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.SimpleSearch;

import org.yiwan.webcore.web.IWebDriverWrapper;


public final class InterestAppliedReportPage extends AbstractReportPage {

    public InterestAppliedReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setInterestAppliedReport(InterestAppliedReportFilter reportFilter) throws Exception{
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
    	if (reportFilter.getCashAssetType() != null){
    		element("rp.cash.asset.type").selectByVisibleText(reportFilter.getCashAssetType().getRealValue());
    	}
    	if (reportFilter.getInterestSource() != null){
    		element("rp.interest.source").selectByVisibleText(reportFilter.getInterestSource().getRealValue());
    	}
    	if (reportFilter.getPaymentMethod() != null){
    		element("rp.payment.method").selectByVisibleText(reportFilter.getPaymentMethod().getRealValue());
    	}
    	if (reportFilter.getDirection() != null){
    		element("rp.direction").selectByVisibleText(reportFilter.getDirection().getRealValue());
    	}
    	if (reportFilter.getRegion() != null){
    		element("rp.region").selectByVisibleText(reportFilter.getRegion().getRealValue());
    	}
    	if (reportFilter.getGroup() != null && reportFilter.getGroup().size() > 0){
    		for (SimpleSearch simpleSearch : reportFilter.getGroup()){
				setGroup(simpleSearch);
			}
    	}
    	if (reportFilter.getStartSettlementDate() != null){
    		element("rp.start.settlement.date").input(reportFilter.getStartSettlementDate().getRealValue());
    	}
    	if (reportFilter.getEndSettlementDate() != null){
    		element("rp.end.settlement.date").input(reportFilter.getEndSettlementDate().getRealValue());
    	}
    }
    
    
    public void saveAsTemplate(InterestAppliedReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
      }
    
   
}
