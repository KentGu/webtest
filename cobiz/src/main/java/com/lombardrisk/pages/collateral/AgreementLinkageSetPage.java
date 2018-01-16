package com.lombardrisk.pages.collateral;


import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;



/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementLinkageSetPage extends PageBase {
    public AgreementLinkageSetPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void returnToAgreementSummary() throws Exception{
    	element("").click();
    }
   
}
