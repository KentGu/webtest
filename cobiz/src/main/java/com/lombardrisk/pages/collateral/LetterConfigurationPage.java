package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * @author Kenny Wang
 */
public final class LetterConfigurationPage extends PageBase {

    public LetterConfigurationPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void setInclusioninMarginLetterTab(MarginLetterConfiguration marginLetterConfiguration) throws Exception{
        if(marginLetterConfiguration.isAssetsPartialRecall() != null)
            element("lc.assets.partial.recall").check(marginLetterConfiguration.isAssetsPartialRecall());

        if(marginLetterConfiguration.isCreateSystemDraft() != null)
            element("lc.create.system.draft.cb").check(marginLetterConfiguration.isCreateSystemDraft());

        if(marginLetterConfiguration.isIncludeAssetHoldingsAndValuationReport() != null)
            element("lc.include.ahv.report").check(marginLetterConfiguration.isIncludeAssetHoldingsAndValuationReport());

        if(marginLetterConfiguration.isIncludeDailyExposureReport() != null)
            element("lc.include.daily.exp.report").check(marginLetterConfiguration.isIncludeDailyExposureReport());

        if(marginLetterConfiguration.isIncludeExposureReport() != null)
            element("lc.include.exp.report").check(marginLetterConfiguration.isIncludeExposureReport());

        if(marginLetterConfiguration.isIncludeTradesReport() != null)
            element("lc.include.trades.report").check(marginLetterConfiguration.isIncludeTradesReport());

        apply();
    }

    public void setInclusionInUDLTab(LetterIncludedReport letterIncludedReport) throws Exception{
        if(letterIncludedReport.isIncludeAssetHoldingsAndValuationReport() != null)
            element("lc.include.ahv.report").check(letterIncludedReport.isIncludeAssetHoldingsAndValuationReport());

        if(letterIncludedReport.isIncludeDailyExposureReport() != null)
            element("lc.include.daily.exp.report").check(letterIncludedReport.isIncludeDailyExposureReport());

        if(letterIncludedReport.isIncludeExposureReport() != null)
            element("lc.include.exp.report").check(letterIncludedReport.isIncludeExposureReport());

        if(letterIncludedReport.isIncludeTradesReport() != null)
            element("lc.include.trades.report").check(letterIncludedReport.isIncludeTradesReport());

        apply();
    }

    public void addLetterTemplate() throws Exception {
        logger.debug("add Letter Template By LetterType And LetterDirection");
//        if (letterTemplate.getLetterType() != null)
//            element("lc.menu", letterTemplate.getLetterType().getRealValue()).click();
//        if (letterTemplate.getLetterDirection() != null)
//            element("lc.menu", letterTemplate.getLetterDirection().getRealValue()).click();
        element("lc.add").click();
    }

    public void switchToMarginLetterTab() throws Exception{
        element("lc.margin.letter.tab").click();
    }

    public void switchToInterestLetterTab() throws Exception{
        element("lc.interest.letter.tab").click();
    }

    public void switchToUDLTab() throws Exception{
        element("lc.udl.tab").click();
    }

    public void switchToExposureStatementsTab() throws Exception{
        element("lc.exposure.statements.tab").click();
    }

    public void switchMarginLetterTypeTab(MarginLetterConfigurationType marginLetterConfigurationType) throws Exception{
        element("lc.menu", marginLetterConfigurationType.value()).click();
    }

    public void switchInterestLetterTypeTab(CashMovementType cashMovementType) throws Exception{
        if(cashMovementType == CashMovementType.CAPITALISE_PAY){
            element("lc.menu", "Capitalise Pay").click();
        }else if(cashMovementType == CashMovementType.CAPITALISE_RECEIVE){
            element("lc.menu", "Capitalise Receive").click();
        }else if(cashMovementType == CashMovementType.PAY){
            element("lc.menu", "Pay").click();
        }else if(cashMovementType == CashMovementType.RECEIVE){
            element("lc.menu", "Receive").click();
        }
    }

    public void editLetterTemplate(LetterTemplate letterTemplate) throws Exception{
        String xpath = getXpath(letterTemplate);
        element("lc.letter.template.edit", xpath).click();

    }

    public void deleteLetterTemplateRecord(LetterTemplate letterTemplate) throws Exception{
        String xpath = getXpath(letterTemplate);
        element("lc.letter.template.delete", xpath).clickByJavaScript();
    }

    private String getXpath(LetterTemplate letterTemplate) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = letterTemplate.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if(method.getReturnType() == StringType.class
                    && method.invoke(letterTemplate) != null
                    && !method.getName().equalsIgnoreCase("getTemplateFile")
                    && !method.getName().equals("getLetterMessage")){
                StringType value = (StringType) method.invoke(letterTemplate);
//                xpath.append("[td[contains(text(),'" + value.getRealValue() + "')]]");
                xpath.append("[td[contains(text(),'").append(value.getRealValue()).append("')]]");
            }
        }
        return xpath.toString();
    }

    private void apply() throws Exception{
        element("lc.apply").click();
        PageHelper.d31489Workaround(element("hm.return.col"), this);
    }

    public void setUpdate(LetterTemplate letterTemplate) throws Exception{
        String xpath = getXpath(letterTemplate);
        String updateBy = element("lc.update.by", xpath).getInnerText().trim();
        String updateTime = element("lc.updated.time", xpath).getInnerText().trim();
        //TODO
    }
}
