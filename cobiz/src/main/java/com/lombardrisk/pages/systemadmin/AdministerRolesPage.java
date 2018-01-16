package com.lombardrisk.pages.systemadmin;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.AdministerRole;


public final class AdministerRolesPage extends PageBase {

    public AdministerRolesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setRoleName(AdministerRole ar) throws Exception{
    	if (ar.getNewRoleName() != null){
    		element("ar.roleName").input(ar.getNewRoleName().getRealValue());
    	}
    }

    public void changeRoleStatus(AdministerRole administerRole) throws Exception {
        if (administerRole.getNewRoleName() == null) {
            element("ar.roleEnableOrDisable",administerRole.getNewRoleName().getRealValue()).click();
        }
    }
    public void submit() throws Exception{
    	element("ar.submit").click();
    }

}
