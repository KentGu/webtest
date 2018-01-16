package com.lombardrisk.pages.report;


import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pages.AbstractReportPage;
import com.lombardrisk.pojo.UserProfileReportFilter;


public final class UserProfileReportPage extends AbstractReportPage {

    public UserProfileReportPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setUserProfileRepor(UserProfileReportFilter reportFilter) throws Exception{
    	if (reportFilter.getUser() != null){
    		setUser(reportFilter.getUser());
    	}
    	if (reportFilter.getRole() != null){
    		element("rp.role").selectByVisibleText(reportFilter.getRole().getRealValue());
    	}
    	if (reportFilter.getUserEnabled() != null){
    		element("rp.user.enabled").selectByVisibleText(reportFilter.getUserEnabled().getRealValue());
    	}
    }
    public void saveAsTemplate(UserProfileReportFilter reportFilter) throws Exception{
  		if (reportFilter.getSaveAsTemplate() != null) {
  			savaAsTemplate(reportFilter.getSaveAsTemplate());
  		}
  	}
  	
  	public void enterOutputFormatPage() throws Exception {
          editOutputFormat();
    }
    	
   
}
