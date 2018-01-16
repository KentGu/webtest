package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.WhatIfScenarioReportFilter;


public final class WhatIfScenarioReportPage extends AbstractReportPage {

    public WhatIfScenarioReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setWhatIfScenarioReport(WhatIfScenarioReportFilter reportFilter) throws Exception{
    	if (reportFilter.getOrganisation() != null && reportFilter.getOrganisation().size() > 0) {
			for (OrganisationSearch organisationSearch : reportFilter.getOrganisation()){
				setOrganisation(organisationSearch);
			}
		}
    	if (reportFilter.getRatingLevelChange() != null){
    		element("rp.rating.level.change").selectByVisibleText(reportFilter.getRatingLevelChange().getRealValue());
    	}
    	if (reportFilter.getDirection() != null){
    		element("rp.direction").selectByVisibleText(reportFilter.getDirection().getRealValue());
    	}
    	if (reportFilter.getCountryOfRisk() != null){
    		element("rp.country.of.risk").selectByVisibleText(reportFilter.getCountryOfRisk().getRealValue());
    	}
    	if (reportFilter.getFlagCountry() != null){
    		element("rp.flag.country").selectByVisibleText(reportFilter.getFlagCountry().getRealValue());
    	}
    	if (reportFilter.getIndustry() != null){
    		element("rp.industry").selectByVisibleText(reportFilter.getIndustry().getRealValue());
    	}
    	if (reportFilter.getClassification() != null){
    		element("rp.classification").selectByVisibleText(reportFilter.getClassification().getRealValue());
    	}
    	if (reportFilter.getRatingAgency() != null && reportFilter.getRatingAgency().size() > 0){
    		setRatingAgency(reportFilter.getRatingAgency());
    	}
    }
    
    public void saveAsTemplate(WhatIfScenarioReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
    }
    
   
}
