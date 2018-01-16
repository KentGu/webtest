package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.User;
import com.lombardrisk.util.Biz;
import org.openqa.selenium.Keys;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.io.File;


public final class EditUserPreferencePage extends UserAndRoleAdministrationNavigationPannel {

    public EditUserPreferencePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void editUserPreference(User user) throws Exception{
    	if (user.getPreferences() != null){
    		if (user.getPreferences().getCurrency() != null){
    			element("").selectByVisibleText(user.getPreferences().getCurrency().getRealValue());
    		}
    		if (user.getPreferences().getParty() != null){
    			element("").selectByVisibleText(user.getPreferences().getParty().getRealValue());
    		}
    		if (user.getPreferences().getCollateralRegion() != null && user.getPreferences().getCollateralRegion().size() > 0){
				element("eups.collateralRegion").deselectAll();
    			for (int i = 0; i < user.getPreferences().getCollateralRegion().size(); i++) {
					element("eups.collateralRegion").selectByVisibleText(user.getPreferences().getCollateralRegion().get(i).getRealValue());
				}
    		}
    		if (user.getPreferences().getCollateralGroup() != null && user.getPreferences().getCollateralGroup().size() > 0){
				element("eups.collateralGroup").deselectAll();
    			for (int i = 0; i < user.getPreferences().getCollateralGroup().size(); i++) {
					element("eups.collateralGroup").selectByVisibleText(user.getPreferences().getCollateralGroup().get(i).getRealValue());
				}
    		}
    		if (user.getPreferences().getCollateralCategory() != null && user.getPreferences().getCollateralCategory().size() > 0){
				element("eups.collateralCategory").deselectAll();
				for (int i = 0; i < user.getPreferences().getCollateralCategory().size(); i++) {
					element("eups.collateralCategory").selectByVisibleText(user.getPreferences().getCollateralCategory().get(i).getRealValue());
				}
    		}
    		if (user.getPreferences().getBusinessLine() != null && user.getPreferences().getBusinessLine().size() > 0){
				element("eups.businessLine").deselectAll();
				for (int i = 0; i < user.getPreferences().getBusinessLine().size(); i++) {
					element("eups.businessLine").selectByVisibleText(user.getPreferences().getBusinessLine().get(i).getRealValue());
				}
    		}
    		if (user.getPreferences().getPrincipalFilter() != null && user.getPreferences().getPrincipalFilter().size() > 0){
				for (int i = 0; i < user.getPreferences().getPrincipalFilter().size(); i++) {
					if (user.getPreferences().getPrincipalFilter().get(i).isRemove()!=null && user.getPreferences().getPrincipalFilter().get(i).isRemove())
						if (user.getPreferences().getPrincipalFilter().get(i).getLinkText()!=null) {
							element("ap.prin.filter").selectByVisibleText(user.getPreferences().getPrincipalFilter().get(i).getLinkText().getRealValue());
							element("ap.prin.filter").type(Keys.DELETE);
						}
				}
				element("ap.search.prin").click();
	            element("ap.frame.prin").switchTo();
    			for (int i = 0; i < user.getPreferences().getPrincipalFilter().size(); i++) {
					if (user.getPreferences().getPrincipalFilter().get(i).isRemove()==null || !user.getPreferences().getPrincipalFilter().get(i).isRemove()) {
						searchOrganisation(user.getPreferences().getPrincipalFilter().get(i));
					}
				}
				switchTo().defaultContent();
    		}
    		if (user.getPreferences().getCounterpartyFilter() != null && user.getPreferences().getCounterpartyFilter().size() > 0){
				for (int i = 0; i < user.getPreferences().getCounterpartyFilter().size(); i++) {
					if (user.getPreferences().getCounterpartyFilter().get(i).isRemove()!=null && user.getPreferences().getCounterpartyFilter().get(i).isRemove())
						if (user.getPreferences().getCounterpartyFilter().get(i).getLinkText()!=null) {
							element("ap.cpty.filter").selectByVisibleText(user.getPreferences().getCounterpartyFilter().get(i).getLinkText().getRealValue());
							element("ap.cpty.filter").type(Keys.DELETE);
						}
				}
				element("ap.search.cpty.new").click();
    			element("ap.frame.cpty").switchTo();
    			for (int i = 0; i < user.getPreferences().getCounterpartyFilter().size(); i++) {
					if (user.getPreferences().getCounterpartyFilter().get(i).isRemove()==null || !user.getPreferences().getCounterpartyFilter().get(i).isRemove()) {
						searchOrganisation(user.getPreferences().getCounterpartyFilter().get(i));
					}
				}
				switchTo().defaultContent();
    		}
    		if (user.getPreferences().getEmail() != null){
    			element("eups.email").input(user.getPreferences().getEmail().getRealValue());
    		}
    		if (user.getPreferences().getTelNumber() != null){
    			element("eups.telNumber").input(user.getPreferences().getTelNumber().getRealValue());
    		}
    		if (user.getPreferences().getFullName() != null){
    			element("eups.fullName").input(user.getPreferences().getFullName().getRealValue());
    		}
    		if (user.getPreferences().isNotifyFeedStatus() != null && user.getPreferences().isNotifyFeedStatus()){
    			element("eups.notifyFeedStatus").check(user.getPreferences().isNotifyFeedStatus());
    		}
    		if (user.getPreferences().getSignature() != null){
					String filePath = Biz.getWorkspace()+ PropHelper.getProperty("signature.path")+user.getPreferences().getSignature().getRealValue();
    			element("eups.signature").type(filePath.replace("/", File.separator));
    		}
    	}
    }
    
    
    public void submit() throws Exception{
    	element("eups.submit").click();
    }

    public void cancel() throws Exception{
    	element("eups.cancel").click();
    }
    
    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getCriteria() != null)
            element("ap.frame.criteria").selectByVisibleText(search.getCriteria().value());
        if (search.getType() != null)
            element("ap.frame.type").selectByVisibleText(search.getType().value());
        if (search.getFilter() != null)
            element("ap.frame.filter").input(search.getFilter().getRealValue());
        element("ap.frame.search").click();

        if (search.getLinkText() != null)
            element("ap.frame.link", search.getLinkText().getRealValue()).clickByJavaScript();
    }
}
