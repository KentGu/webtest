package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.SecurityMovementDetail;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;




/**
 * @author Kenny Wang
 */
public final class SecurityMovementPage extends PageBase {

    public SecurityMovementPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void expandAllSecurityMovementDetail() throws Exception {
        while (element("smd.expand", "").isDisplayed()){
            element("smd.expand", "").click();
        }
    }

    public void collapseAllSecurityMovementDetail() throws Exception {
        while (element("smd.collpase", "").isDisplayed()){
            element("smd.collpase", "").click();
        }
    }

    public void expandSecurityMovementDetail(SecurityMovementDetail securityMovementDetail) throws Exception{
        element("smd.expand", getSecurityMovementDetailRow(securityMovementDetail)).click();
    }

    public void collapseSecurityMovementDetail(SecurityMovementDetail securityMovementDetail) throws Exception{
        element("smd.collpase", getSecurityMovementDetailRow(securityMovementDetail)).click();
    }

    private String getSecurityMovementDetailRow(SecurityMovementDetail securityMovementDetail) {
        StringBuffer xpath = new StringBuffer();
        if(securityMovementDetail.getInstrumentId() != null) {
//            xpath.append("[td[contains(.,'"
//                    + securityMovementDetail.getInstrumentId().getRealValue() + "') and string-length(.)=string-length('"
//                    + securityMovementDetail.getInstrumentId().getRealValue() + "')+9]]");
            xpath.append("[td[contains(.,'").append(securityMovementDetail.getInstrumentId().getRealValue())
                    .append("') and string-length(.)=string-length('")
                    .append(securityMovementDetail.getInstrumentId().getRealValue()).append("')+9]]");
        }
        if(securityMovementDetail.getDescription() != null) {
//            xpath.append("[td='" + securityMovementDetail.getDescription().getRealValue() + "']");
            xpath.append("[td='").append(securityMovementDetail.getDescription().getRealValue()).append("']");
        }
        return xpath.toString();
    }

//    public void assertSecurityMovementDetail(SecurityMovementDetail securityMovementDetail) throws Exception{
//        assertThat("//tr[@id = //tr[contains(@name,'a')][preceding-sibling::tr[1][@class='lbg' and td='ml051811']]/@id]").displayed().isTrue();
//    }
}
