package com.lombardrisk.pages.systemadmin;


import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.EventLogSearch;

public final class EventLogSearchPage extends PageBase {

    public EventLogSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setSearchEventLog(EventLogSearch els) throws Exception{
    	if (els.getSource() != null){
    		element("els.source").selectByVisibleText(els.getSource().getRealValue());
    	}
    	if (els.getLevel() != null){
    		element("els.level").selectByVisibleText(els.getLevel().getRealValue());
    	}
    	if (els.isLastTwentyFourHours() != null && els.isLastTwentyFourHours()){
    		element("els.lastDay").check(els.isLastTwentyFourHours());
    	}
    	if (els.getDateRange() != null){
    		element("els.dateRange").click();
    		if (els.getDateRange().getBeginDate() != null){
    			element("els.beginDate").input(els.getDateRange().getBeginDate().getRealValue());
    		}
    		if (els.getDateRange().getEndDate() != null){
    			element("els.endDate").input(els.getDateRange().getEndDate().getRealValue());
    		}
    	}
    }

    public void searchEventLog() throws Exception{
    	element("els.search").click();
    }

	public void goTo(StringType v) throws Exception {
		element("els.pageNumber").input(v.getRealValue());
		element("els.pageGo").click();
	}
    
    
}
