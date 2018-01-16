package com.lombardrisk.pages.systemadmin;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.PasswordConstraints;


public final class PasswordConstraintsPage extends PageBase {

    public PasswordConstraintsPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setPasswordConstraints(PasswordConstraints pc) throws Exception{
    	if (pc.getPasswordConstraint() != null){
    		if (pc.getPasswordConstraint().getMinimumPasswordLength() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getMinimumPasswordLength().getRealValue());
    		}
    		if (pc.getPasswordConstraint().getMustContainNumber() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getMustContainNumber().getRealValue());
    		}
    		if (pc.getPasswordConstraint().getMustContainUpperCaseLetter() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getMustContainUpperCaseLetter().getRealValue());
    		}
    		if (pc.getPasswordConstraint().getMustContainLowerCaseLetter() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getMustContainLowerCaseLetter().getRealValue());
    		}
    		if (pc.getPasswordConstraint().getMustContainSysmbol() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getMustContainSysmbol().getRealValue());
    		}
    		if (pc.getPasswordConstraint().getOnlyAllowAlphanumericCharacters() != null){
    			element("").selectByVisibleText(pc.getPasswordConstraint().getOnlyAllowAlphanumericCharacters().getRealValue());
    		}
    	}
    	
       if (pc.getNewUserPassword() != null){
    	   if (pc.getNewUserPassword().getAllowReuseOfPasswords() != null){
    		   element("").selectByVisibleText(pc.getNewUserPassword().getAllowReuseOfPasswords().value());
    	   }
    	   if (pc.getNewUserPassword().getAfterWeeks() != null){
    		   element("").input(pc.getNewUserPassword().getAfterWeeks().getRealValue());
    	   }
    	   if (pc.getNewUserPassword().getMaxFailedPasswordAttempts() != null){
    		   element("").selectByVisibleText(pc.getNewUserPassword().getMaxFailedPasswordAttempts().getRealValue());
    	   }
    	   if (pc.getNewUserPassword().getMaxPasswordAge() != null){
    		   element("").selectByVisibleText(pc.getNewUserPassword().getMaxPasswordAge().value());
    	   }
       }
     
    }
}
