package com.lombardrisk.pages.tools;

import com.lombardrisk.pojo.StringType;
import com.lombardrisk.pojo.UserPreference;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

public final class EditMyPreferencesPage extends PageBase {

    public EditMyPreferencesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void editMyPreference(UserPreference up) throws Exception {
        if (up.getTableSize() != null){
        	element("to.tablesize").input(up.getTableSize().getRealValue());
        }
            
        if (up.getTimeZone() != null){
        	element("to.timezone").selectByVisibleText(up.getTimeZone().getRealValue());
        }
        if (up.getCollateralRegion() != null && up.getCollateralRegion().size() > 0){
        	for (StringType collateralRegion : up.getCollateralRegion()) {
        		element("to.region").selectByVisibleText(collateralRegion.getRealValue());
			}
        }
        if (up.getCollateralGroup() != null  && up.getCollateralGroup().size() > 0){
        	for (StringType collateralGroup : up.getCollateralGroup()) {
        		element("to.group").selectByVisibleText(collateralGroup.getRealValue());
			}
        }
            
        if (up.getLinkageSet() != null && up.getLinkageSet().size() > 0){
        	for (StringType linkageSet : up.getLinkageSet() ) {
        		element("to.linkageset").selectByVisibleText(linkageSet.getRealValue());
			}
        } 
            
        if (up.getBusinessLine() != null && up.getBusinessLine().size() > 0){
        	for (StringType businessLine : up.getBusinessLine() ) {
        		element("to.businessline").selectByVisibleText(businessLine.getRealValue());
        	}
        }
        if (up.getDashboardLink() != null)
            element("to.dashbordlink", up.getDashboardLink().value()).click();
    }

    public void submitMyPreferencr() throws Exception{
    	element("to.submit").click();
    }
    public void resetMyPreference() throws Exception {
        element("to.reset").click();
    }

}
