package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.ConcentrationLimitBreached;
import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;


/**
 * @author Kenny Wang
 */
public final class ConcentrationLimitBreachedPage extends PageBase {

    public ConcentrationLimitBreachedPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void continueMakeBookingIgnoreBreached() throws Exception {
        element("clb.rule.continue").click();
    }


    /**
     * assert concentration limits breached on such agreement
     */
    public void assertAgreementConcentrationLimitsBreached(ConcentrationLimitBreached concentrationLimitBreached) throws Exception {
            StringBuffer xpath = new StringBuffer();
            xpath.append("//tr");
            Method[] methods = concentrationLimitBreached.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().startsWith("get") && method.invoke(concentrationLimitBreached) != null
                        && !method.getName().equals("getName")
                        && method.getReturnType().equals(StringType.class)) {
                    StringType stringType = (StringType) method.invoke(concentrationLimitBreached);
                    if (method.getName().equals("getElement")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getRule")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getMethod")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getRuleValue")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getActualValue")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                    if (method.getName().equals("getAgreement")){
//                        xpath.append("[td[contains(text(),'" + stringType.getRealValue() + "')]]");
                        xpath.append("[td[contains(text(),'").append(stringType.getRealValue()).append("')]]");
                    }
                }
            }
            if (concentrationLimitBreached.isIsDisplayed() == null || (concentrationLimitBreached.isIsDisplayed() != null && concentrationLimitBreached.isIsDisplayed().equals(true))){
                    assertThat("ag.breached.rule", xpath.toString()).displayed().isEqualTo(true);
                }else {
                    assertThat("ag.breached.rule", xpath.toString()).displayed().isEqualTo(false);
                }
        }

//
//    public void assertSecurityMovementSummary(SecurityMovementSummary securityMovementSummary) throws Exception{
//        if (securityMovementSummary.isCorpActionDue() != null) {
//            if (securityMovementSummary.isCorpActionDue()) {
//                assertThat("smsmmy.corpActionDue.record", getSecurityMovementSummaryRow(securityMovementSummary)).displayed().isTrue();
//            } else {
//                assertThat("smsmmy.corpActionDue.record", getSecurityMovementSummaryRow(securityMovementSummary)).displayed().isFalse();
//            }
//        }
//    }

//    private String getSecurityMovementSummaryRow(SecurityMovementSummary securityMovementSummary) throws Exception{
//        if(securityMovementSummary.getAssetType() != null)
//            return "[td/a='" + securityMovementSummary.getAssetType().getRealValue() + "']";
//        else
//            return null;
//    }

    public void assertConcentrationLimitBreachedRule(ConcentrationLimitBreached concentrationLimitBreached) throws Exception {
        if (concentrationLimitBreached.getTicketId() != null){
            String tickIdRealValue = concentrationLimitBreached.getTicketId().getRealValue();
            if (concentrationLimitBreached.getRule() != null){
                assertThat("clb.rule",tickIdRealValue).innerText().isEqualToIgnoringWhitespace(concentrationLimitBreached.getRule().getRealValue());
            }
            if (concentrationLimitBreached.getMethod() != null){
                assertThat("clb.method",tickIdRealValue).innerText().isEqualToIgnoringWhitespace(concentrationLimitBreached.getMethod().getRealValue());
            }
            if (concentrationLimitBreached.getRuleValue() != null){
                assertThat("clb.rule.value",tickIdRealValue).innerText().isEqualToIgnoringWhitespace(concentrationLimitBreached.getRuleValue().getRealValue());
            }
            if (concentrationLimitBreached.getSource() != null){
                assertThat("clb.rule.source",tickIdRealValue).innerText().isEqualToIgnoringWhitespace(concentrationLimitBreached.getSource().getRealValue());
            }
        }
    }

//    private String getConcentrationLimitBreachedRuleRow(ConcentrationLimitBreached concentrationLimitBreached){
//        if (concentrationLimitBreached.getTicketId() != null){
//            return "[td/a='" + concentrationLimitBreached.getTicketId().getRealValue() + "']";
//        }else
//            return  null;
//    }



}