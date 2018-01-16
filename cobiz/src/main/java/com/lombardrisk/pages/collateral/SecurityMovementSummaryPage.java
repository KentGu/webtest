package com.lombardrisk.pages.collateral;

import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import com.lombardrisk.pojo.SecurityMovementSummary;




/**
 * @author Kenny Wang
 */
public final class SecurityMovementSummaryPage extends PageBase {

    public SecurityMovementSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void returnToFilter() throws Exception{
    	element("smsmmy.returnToFliter").click();
    }
    
    public void enterExternalExposuresPage() throws Exception{
    	element("smsmmy.returnToEE").click();
    }
    
    public void enterExposureManagermentPage() throws Exception{
    	element("smsmmy.returnToEM").click();
    }
    
    public void enterInternalReviewsPage() throws Exception{
    	element("smsmmy.returnToIR").click();
    }
    
    	
    public void enterSecurityMovementPage(SecurityMovementSummary sms) throws Exception{
    	if (sms.getAssetClass() != null){
    		if (sms.getAssetType() != null){
    			element("smsmmy.assetClassName").click();
    		}else{
    			element("smsmmy.assetTypeName").click();
    		}
    	}
    }
    
    public void enterSecurityMovementPageByCorpActionDue() throws Exception{
    	element("smsmmy.corpActionDue").click();
    }

    public void enterSpecificSecurityMovementPageByCorpActionDue(SecurityMovementSummary securityMovementSummary) throws Exception{
        element("smsmmy.corpActionDue.record", getSecurityMovementSummaryRow(securityMovementSummary)).click();
    }

    public void back() throws Exception{
    	element("smsmmy.back").click();
    }

    public void assertSecurityMovementSummary(SecurityMovementSummary securityMovementSummary) throws Exception{
        if (securityMovementSummary.isCorpActionDue() != null) {
            if (securityMovementSummary.isCorpActionDue()) {
                assertThat("smsmmy.corpActionDue.record", getSecurityMovementSummaryRow(securityMovementSummary)).displayed().isTrue();
            } else {
                assertThat("smsmmy.corpActionDue.record", getSecurityMovementSummaryRow(securityMovementSummary)).displayed().isFalse();
            }
        }
    }

    private String getSecurityMovementSummaryRow(SecurityMovementSummary securityMovementSummary) {
        if(securityMovementSummary.getAssetType() != null)
            return "[td/a='" + securityMovementSummary.getAssetType().getRealValue() + "']";
        else
            return null;
    }
}