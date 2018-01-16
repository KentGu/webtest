package com.lombardrisk.pages.systemadmin;

import com.lombardrisk.pojo.ActiveMessagingResult;
import com.lombardrisk.pojo.DeliveryPriorityType;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.ActiveMessaging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("ALL")
public final class ActiveMessagingPage extends PageBase {

    public ActiveMessagingPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void editActiveMessaging(ActiveMessaging am) throws Exception{
    	if (am.getTitle() != null){
    		element("acme.title").input(am.getTitle().getRealValue());
    	}
    	if (am.getMessage() != null){
    		element("acme.message").input(am.getMessage().getRealValue());
    	}
    	if (am.getPriotity() != null){
    		element("acme.priorit",am.getPriotity().value()).click();
    	}
    	if (am.getSendAs() != null){
    		element("acme.sendAs",am.getSendAs().value()).click();
    	}
    	if (am.getRoles() != null && am.getRoles().size() > 0){
    		for (int i = 0; i < am.getRoles().size(); i++) {
				element("acme.role").selectByVisibleText(am.getRoles().get(i).getRealValue());
			}
    	}
    	if (am.getUser() != null && am.getUser().size() > 0){
    		for (int i = 0; i< am.getUser().size(); i++) {
				element("acme.user").selectByVisibleText(am.getUser().get(i).getRealValue());
			}
    	}
    	if (am.getValidFor() != null){
    		element("acme.validFor").selectByVisibleText(am.getValidFor().getRealValue());
    	}
    }
    
    public void sendActiveMessaging() throws Exception{
    	element("acme.send").click();
    }

	public void deleteActiveMessagingResult(ActiveMessagingResult activeMessagingResult) throws Exception {
		element("acme.result.delete",getXpath(activeMessagingResult)).click();
	}
	private String getXpath(ActiveMessagingResult activeMessagingResult) throws InvocationTargetException, IllegalAccessException {

		StringBuffer xpath = new StringBuffer("//tr");
		Method[] methods = activeMessagingResult.getClass().getDeclaredMethods();

		for (Method method : methods){
			if (method.getReturnType() == StringType.class && method.invoke(activeMessagingResult) != null){
				StringType v = (StringType) method.invoke(activeMessagingResult);
				String value = v.getRealValue();
				//    messageTitle                               message                   priority           sendTo     valid
				//tr[td[contains(text(),'Admin Reminder')]][td/a='Message to Send'][td/img[@title='High']][td/a='ALL'][td='1']//img[@title='Remove']
				if (v.getName().equals("getMessageTitle")){
					xpath.append("[td[contains(text(),'"+value+"')]]");
				}else if (v.getName().equals("getPriority")){
					xpath.append("[td/img[@title='"+value+"']]");
				}else if (v.getName().equals("getMessage") || v.getName().equals("getSendTo")){
					xpath.append("[td/a='"+value+"']");
				}else if (v.getName().equals("getValidFor")){
					xpath.append("[td='"+value+"']");
				}
			}
		}
		return xpath.toString();
	}




	@SuppressWarnings("EqualsBetweenInconvertibleTypes")
	private String getPriorityColor(ActiveMessaging activeMessaging){
		String priorityColor = null;
		if (activeMessaging.getPriotity() != null){
			//noinspection EqualsBetweenInconvertibleTypes
			if (activeMessaging.getPriotity().value().equals(DeliveryPriorityType.HIGH.value())){
				priorityColor = "red";
			}else  //noinspection EqualsBetweenInconvertibleTypes
				if (activeMessaging.getPriotity().value().equals(DeliveryPriorityType.MEDIUM.value())){
				priorityColor = "yellow";
			}else {
				priorityColor = "green";
			}
		}
		return priorityColor;
	}


}
