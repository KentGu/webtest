package com.lombardrisk.pages.home;

import com.lombardrisk.data.Menu;
import com.lombardrisk.pojo.LoginCredential;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class HomePage extends PageBase {

    public HomePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void logon(LoginCredential loginCredential) throws Exception {
        if (loginCredential.getUsername() != null)
            element("hm.username").input(loginCredential.getUsername().getRealValue());
        if (loginCredential.getPassword() != null)
            element("hm.password").input(loginCredential.getPassword().getRealValue());

        TestCaseManager.getTestCase().startTransaction("login_request");
        element("hm.enter").click();
        TestCaseManager.getTestCase().stopTransaction();
    }

    public void login(LoginCredential loginCredential) throws Exception {
        logon(loginCredential);
        if (element("hm.oldPassword").isDisplayed()) {
            changePassword(loginCredential);
            element("hm.return").click();
            logon(loginCredential);
        } else if (element("hm.continue").isDisplayed()) {
            element("hm.continue").click();
        }
        TestCaseManager.getTestCase().getTestMap().put(Biz.J_WINNAME, getCurrentUrl().replace((TestCaseManager.getTestCase().getTestEnvironment().getApplicationServer(0).getUrl() + Menu.HOME.getValue()).replace("//colline", "/colline"), "").replace("?", "").replaceAll("&.*", ""));
    }

    public void changePassword(LoginCredential loginCredential) throws Exception {
        if (loginCredential.getPasswordChange() != null) {
            if (loginCredential.getPasswordChange().getOldPassword() != null)
                element("hm.oldPassword").input(loginCredential.getPasswordChange().getOldPassword().getRealValue());
            if (loginCredential.getPasswordChange().getNewPassword() != null)
                element("hm.newPassword").input(loginCredential.getPasswordChange().getNewPassword().getRealValue());
            if (loginCredential.getPasswordChange().getConfirmPassword() != null)
                element("hm.confirmPassword").input(loginCredential.getPasswordChange().getConfirmPassword().getRealValue());
            element("hm.submit").click();
        }

    }

    public void returnToApplication() throws Exception {
        element("hm.return").click();
    }
}
