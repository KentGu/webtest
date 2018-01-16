package com.lombardrisk.pages.systemadmin;

import org.yiwan.webcore.web.IWebDriverWrapper;

import org.yiwan.webcore.web.PageBase;


public final class MessagePage extends PageBase {

    public MessagePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void editMessage(){
    	
    }
    
    public void sendMessage() throws Exception{
    	element("").click();
    }

}
