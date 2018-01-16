package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.Message;
import com.lombardrisk.pojo.TsaField;
import com.lombardrisk.pojo.TsaFieldNameType;
import com.lombardrisk.pojo.TsaRule;
import org.openqa.selenium.WebDriverException;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;

/**
 * @author Kenny Wang
 */
public final class TSARulesPage extends PageBase {

    public TSARulesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    public void addTsaRule() throws Exception {
        element("tr.add").click();
    }

    public void inputTsaRule(TsaRule rule) throws Exception {
        if (rule.getTsaRuleName() != null)
            element("tr.name").input(rule.getTsaRuleName().getRealValue());
        for (TsaField field : rule.getTsaField()) {
            if (field.getTsaFieldName() != null) {
                TsaFieldNameType fieldName = field.getTsaFieldName();
                if (field.getTsaFieldName().equals(TsaFieldNameType.TSA_MISC_1))
                    fieldName = TsaFieldNameType.CASHFLOW_MISC_1;
                else if (field.getTsaFieldName().equals(TsaFieldNameType.TSA_MISC_2))
                    fieldName = TsaFieldNameType.CASHFLOW_MISC_2;
                else if (field.getTsaFieldName().equals(TsaFieldNameType.TSA_MISC_3))
                    fieldName = TsaFieldNameType.CASHFLOW_MISC_3;
                element("tr.field.enable", fieldName.value()).check(field.isEnabled());
                if (field.getPaymentMethod() != null)
                    element("tr.field.method", fieldName.value()).selectByVisibleText(
                            field.getPaymentMethod().value());
            }
        }
        if (rule.getCashflowRuleName() != null)
            element("tr.name").input(rule.getCashflowRuleName().getRealValue());
        for (TsaField field : rule.getCashflowField()) {
            if (field.getCashflowFieldName() != null) {
                element("tr.field.enable", field.getCashflowFieldName().value()).check(field.isEnabled());
                if (field.getPaymentMethod() != null)
                    element("tr.field.method", field.getCashflowFieldName().value()).selectByVisibleText(
                            field.getPaymentMethod().value());
            }
        }
    }

    public void editTsaRule(TsaRule tsaRule) throws Exception {
        if (tsaRule.getTsaRuleName() != null){
            element("tr.edit",tsaRule.getTsaRuleName().getRealValue()).click();
        }
        else if (tsaRule.getCashflowRuleName() != null){
            element("tr.edit",tsaRule.getCashflowRuleName().getRealValue()).click();
        }
    }

    private void handleAlerts(List<Message> handles) {
        for (Message handle : handles) {
            if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
                if (alert().getText().matches(handle.getContent().getRealValue())) {
                    if (handle.isAccept())
                        alert().accept();
                    else
                        alert().dismiss();
                } else {
                    throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
                }
            } else if (alert().isPresent()) {
                if (handle.isAccept()) {
                    alert().accept();
                } else {
                    alert().dismiss();
                }
            }
        }

    }

    public void saveTsaRule(TsaRule tsaRule) throws Exception {
        clickSaveButton();
        handleAlerts(tsaRule.getAlertHandling());
    }

    public void clickSaveButton() throws Exception {
        element("tr.save").click();
    }

    public void cancelTsaRule() throws Exception {
        element("tr.cancel").click();
    }

    public void deleteTsaRule(TsaRule tsaRule) throws Exception {
        if (tsaRule.getTsaRuleName() != null){
            element("tr.delete",tsaRule.getTsaRuleName().getRealValue()).clickByJavaScript();
        }
        else if (tsaRule.getCashflowRuleName() != null){
            element("tr.delete",tsaRule.getCashflowRuleName().getRealValue()).clickByJavaScript();
        }
    }
    public  void setTsaRuleSearchCondition(TsaRule tsaRule) throws Exception{
        if (tsaRule.getTsaRuleName()!=null){
            element("tr.tsaNameSearch").selectByVisibleText(tsaRule.getTsaRuleName().getRealValue());
        }
        else if (tsaRule.getCashflowRuleName()!=null){
            element("tr.tsaNameSearch").selectByVisibleText(tsaRule.getCashflowRuleName().getRealValue());
        }
    }
    public void searchTsaRule()throws Exception{
        element("tr.search").click();
    }

}
