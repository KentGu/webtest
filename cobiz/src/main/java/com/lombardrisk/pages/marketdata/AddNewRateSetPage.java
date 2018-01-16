package com.lombardrisk.pages.marketdata;


import com.lombardrisk.pojo.FxRateSet;
import com.lombardrisk.pojo.StringType;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


public final class AddNewRateSetPage extends PageBase {

    public AddNewRateSetPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void editNewRateSet(FxRateSet frs) throws Exception{
    	if (frs.getFxRateSetName() != null){
    		 element("md.rateset.name").input(frs.getFxRateSetName().getRealValue());
    	}
    	if (frs.getFxUpdatePermission() != null && !frs.getFxUpdatePermission().isEmpty() ){
            element("md.rateset.fx.update.permission").deselectAll();
    		for (StringType permission : frs.getFxUpdatePermission()) {
                element("md.rateset.fx.update.permission").selectByVisibleText(permission.getRealValue());
            }
    	}
    }
    
    public void saveNewRateSet() throws Exception{
    	element("md.rateset.save").click();
    }
    
    public void cancelNewRateSet() throws Exception{
    	element("md.rateset.cancel").click();
    }

}
