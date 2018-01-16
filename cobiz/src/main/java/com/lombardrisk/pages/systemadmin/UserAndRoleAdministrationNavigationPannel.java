package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.User;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

public abstract class UserAndRoleAdministrationNavigationPannel extends PageBase {


	public UserAndRoleAdministrationNavigationPannel(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void enterCreateNewUserPage() throws Exception{
    	element("uarnp.creatNewUser").click();
    }
    
    public void enterAdministerRolesPage() throws Exception{
    	element("uarnp.administerRoles").click();
    }
    
    public void enterAdministerApprovalProfilesPage() throws Exception{
    	element("uarnp.administerApprovalProfiles").click();
    }

    public void changeUserAccess(User user) throws Exception {
        if (user.getUsername() != null){
            element("uarnp.enableAccess","uarnp.disableAccess",user.getUsername().getRealValue()).clickSmartly();
        }
    }

//    public void disableAccess(User user) throws Exception {
//        if (user.getUsername() != null){
//            element("uarnp.disableAccess",user.getUsername().getRealValue()).click();
//        }
//    }

    public void viewUser(User user) throws Exception {
        element("uarnp.viewUser").selectByVisibleText(user.getViewUser().value());
    }

    public void enterEditProfileForUserPage(User user) throws Exception {
        if (user.getUsername() != null){
            element("uarnp.userName",user.getUsername().getRealValue()).click();
        }
    }

}
