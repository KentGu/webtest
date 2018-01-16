package com.lombardrisk.pages.tools;



import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.PasswordChange;

public final class ChangeMyPasswordPage extends PageBase {

    public ChangeMyPasswordPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void setChangemMyPassword(PasswordChange pc) throws  Exception{
        if (pc.getOldPassword() != null){
            element("to.oldPassword").input(pc.getOldPassword().getRealValue());
        }
        if (pc.getNewPassword() != null){
            element("to.newPassword").input(pc.getNewPassword().getRealValue());
        }
        if (pc.getConfirmPassword() != null){
            element("to.changeNewPassword").input(pc.getConfirmPassword().getRealValue());
        }
    }

    public void submitChangeMyPassword() throws  Exception{
        element("to.submit.changeMyPassword").click();
    }
    public void returnToApplication() throws Exception {
        element("hm.return").click();
    }
}
