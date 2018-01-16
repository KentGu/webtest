package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.ApprovalProfile;
import com.lombardrisk.pojo.ProfileLimitLevel;
import org.yiwan.webcore.web.IWebDriverWrapper;


public final class AdministerApprovalProfilesPage extends UserAndRoleAdministrationNavigationPannel {

    public AdministerApprovalProfilesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    
    public void setApprovalProfile(ApprovalProfile ap) throws Exception{
    	if (ap.getLimitCurrency() != null){
    		element("aap.limitCurrency").input(ap.getLimitCurrency().getRealValue());
    	}

    	if (ap.getProfileLimit() != null && ap.getProfileLimit().size() > 0) {
			for (ProfileLimitLevel profileLimitLevel : ap.getProfileLimit()) {
				if (profileLimitLevel.isRemove() != null && profileLimitLevel.isRemove()) {
					element("aap.profile.delete", profileLimitLevel.getProfileName().getRealValue()).click();
				} else {
					element("aap.add").click();
					if (profileLimitLevel.getProfileName() != null) {
						element("aap.profileName").input(profileLimitLevel.getProfileName().getRealValue());
					}
					if (profileLimitLevel.getProfileLimit() != null) {
						element("aap.profileLimit").input(profileLimitLevel.getProfileLimit().getRealValue());
					}
				}
			}
		}
    }
    
    public void submit() throws Exception{
    	element("aap.submit").click();
    }

}
