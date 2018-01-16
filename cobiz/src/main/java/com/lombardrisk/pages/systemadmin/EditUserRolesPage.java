package com.lombardrisk.pages.systemadmin;

import org.yiwan.webcore.web.IWebDriverWrapper;

import com.lombardrisk.pojo.User;



public final class EditUserRolesPage extends UserAndRoleAdministrationNavigationPannel {

    public EditUserRolesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void editUserRoles(User user) throws Exception{
        boolean flag = true;
    	if (user.getRole() != null && user.getRole().size() > 0){
    		for (int i = 0; i < user.getRole().size(); i++) {
                if (user.getRole().get(i).isRemove() != null && user.getRole().get(i).isRemove()){
                    flag = false;
                }
                element("eurs.role",user.getRole().get(i).getRealValue()).check(flag);
                flag = true;
			}
    	}
    }
    
    public void save() throws Exception{
    	element("eurs.saveRoles").click();
    }

    public void cancel() throws Exception{
    	element("eurs.cancelRoles").click();
    }
}
