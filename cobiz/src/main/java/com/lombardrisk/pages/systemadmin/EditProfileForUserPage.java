package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.AccessStatusType;
import com.lombardrisk.pojo.User;
import org.yiwan.webcore.web.IWebDriverWrapper;


@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public final class EditProfileForUserPage extends UserAndRoleAdministrationNavigationPannel {

    public EditProfileForUserPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
	public void setAccessStatus(User user) throws Exception{
    	if (user.getAccessStatus() != null){
			if (user.getAccessStatus().value().equals(AccessStatusType.GRANTED.value()) && element("epfu.granted").isDisplayed()){
				element("epfu.granted").click();
			}else if (user.getAccessStatus().value().equals(AccessStatusType.LOCKED.value()) && element("epfu.locked").isDisplayed()) {
				element("epfu.locked").click();
			}
    	}
    }
    
    public void setChangePasswordConstraints(User user) throws Exception{
		if(user.getChangePasswordConstraints() != null) {
			if (user.getChangePasswordConstraints().getAllowReuseOfPasswords() != null) {
				element("epfu.allowReuseOfPasswords").selectByVisibleText(user.getChangePasswordConstraints().getAllowReuseOfPasswords().value().toLowerCase());
			}
			if (user.getChangePasswordConstraints().getAfterWeeks() != null) {
				element("epfu.afterWeeks").input(user.getChangePasswordConstraints().getAfterWeeks().getRealValue());
			}
			if (user.getChangePasswordConstraints().getMaxFailedPasswordAttempts() != null) {
				element("epfu.maxFailedPassword").selectByVisibleText(user.getChangePasswordConstraints().getMaxFailedPasswordAttempts().getRealValue());
			}
			if (user.getChangePasswordConstraints().getMaxPasswordAge() != null) {
				element("epfu.maxPasswordAge").selectByVisibleText(user.getChangePasswordConstraints().getMaxPasswordAge().value());
			}
		}
    }
    
    public void setChangePassword(User user) throws Exception{
       if (user.getChangePassword().getNewPassword() != null){
   		   element("epfu.newPassword").input(user.getChangePassword().getNewPassword().getRealValue());
   	   }
   	   if (user.getChangePassword().getConfirmPassword() != null){
   		   element("epfu.confirmNewPassword").input(user.getChangePassword().getConfirmPassword().getRealValue());
   	   }
    }
    
    public void savePassword() throws Exception{
    	element("epfu.savePassword").click();
    }
    
    
    public void enterEditUserRolesPage() throws Exception{
    	element("epfu.editRoleList").clickByJavaScript();
    }
    
    public void enterEditApprovalProfilePage() throws Exception{
    	element("epfu.editApprovalProfile").clickByJavaScript();
    }
    
    public void enterEditUserPreferencrPage() throws Exception{
    	element("epfu.editPreference").clickByJavaScript();
    }
    
}
