package com.lombardrisk.pages.systemadmin;


import com.lombardrisk.pojo.User;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


public final class CreateNewUserPage extends PageBase {

    public CreateNewUserPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void inputNewUser(User user) throws Exception{
    	if (user.getUsername() != null)
    		element("cnu.username").input(user.getUsername().getRealValue());
    	if (user.getPassword() != null)
			element("cnu.newPassword").input(user.getPassword().getRealValue());
		if (user.getConfirmPassword() != null)
			element("cnu.confirmNewPassword").input(user.getConfirmPassword().getRealValue());
    }
    
    public void submit() throws Exception{
    	element("cnu.submit").click();
    }
}
