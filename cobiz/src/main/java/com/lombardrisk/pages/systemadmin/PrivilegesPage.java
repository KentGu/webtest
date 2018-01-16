package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.Privilege;
import com.lombardrisk.pojo.Privileges;
import com.lombardrisk.pojo.RolePrivilege;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


public final class PrivilegesPage extends PageBase {

    public PrivilegesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void editPrivileges(Privileges privileges) throws Exception{
        if (privileges.getPrivilegeType()!=null && privileges.getRolePrivilege()!=null && privileges.getRolePrivilege().size()>0){
            String privilegeType = privileges.getPrivilegeType().toString();
            if (privilegeType.equalsIgnoreCase("optimisation")) {
                privilegeType = "optimization";
            }
            privilegeType = privilegeType.replace("SYSTEM","SYS").replace("_","").toLowerCase();
            element("sa.privilege.tab",privilegeType).click();
            for(RolePrivilege rolePrivilege : privileges.getRolePrivilege()){
                if (rolePrivilege.getRoleName()!=null){
                    if(rolePrivilege.isSelectAll()!=null) {
                        element("sa.privilege.select.all", rolePrivilege.getRoleName().getRealValue()).check(true);
                        element("sa.privilege.select.all", rolePrivilege.getRoleName().getRealValue()).check(rolePrivilege.isSelectAll());
                    }
                    if(rolePrivilege.getPrivilege()!=null && rolePrivilege.getPrivilege().size()>0){
                        for (Privilege pri : rolePrivilege.getPrivilege()){
                            if (pri.getPrivilegeName()!=null && pri.isSelected()!=null)
                            element("sa.privilege.tickbox",pri.getPrivilegeName().getRealValue(),rolePrivilege.getRoleName().getRealValue()).check(pri.isSelected());
                        }
                    }
                }
            }
        }
    }

    public void save() throws Exception{
        element("sa.privilege.save").click();
    }

}
