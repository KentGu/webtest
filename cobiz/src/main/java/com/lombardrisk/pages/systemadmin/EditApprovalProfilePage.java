package com.lombardrisk.pages.systemadmin;

import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pojo.User;



public final class EditApprovalProfilePage extends UserAndRoleAdministrationNavigationPannel {

    public EditApprovalProfilePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void editApprovalProfile(User user) throws Exception {
        if (user.getApprovalProfile().getPay() != null) {
            element("eap.pay").selectByVisibleText(user.getApprovalProfile().getPay().getRealValue());
        }
        if (user.getApprovalProfile().getReceive() != null) {
            element("eap.receive").selectByVisibleText(user.getApprovalProfile().getReceive().getRealValue());
        }
    }
    
    public void save() throws Exception{
    	element("eap.save").click();
    }

    public void cancel() throws Exception{
    	element("eap.cancel").click();
    }
}
