package com.lombardrisk.pages.collateral.staticdata;

import com.lombardrisk.pojo.StatementCalcRule;
import com.lombardrisk.pojo.StatementCalcUdf;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;
import java.io.File;
import java.util.List;

public class CalculationRuleMaintenancePage extends PageBase{

    public CalculationRuleMaintenancePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void clickAddNew() throws Exception {
        element("sc.rule.add").click();
    }

    public void clickEdit() throws Exception{
        element("sc.rule.result-table-row.edit-button").click();
    }

    public void clickSave() throws Exception {
        element("sc.rule.save-button").click();
    }

    public void clickDelete() throws Exception{
        element("sc.rule.result-table-row.delete-button").click();
        element("sc.rule.result-table-row.delete-button-confirm").click();
    }

    public void populateRuleSetup(StatementCalcRule statementCalcRule) throws Exception{
        element("sc.business.line").selectByVisibleText(statementCalcRule.getBusinessLine().getValue());
        if(statementCalcRule.getJurisdiction() != null){
        element("sc.jurisdiction").selectByVisibleText(statementCalcRule.getJurisdiction().getRealValue());
        }
        element("sc.rule.name").input(statementCalcRule.getRuleName().getRealValue());
        element("sc.rule.type").selectByVisibleText(statementCalcRule.getRuleType().getValue());

        String filePath = Biz.getWorkspace() + "/cosel" + PropHelper.getProperty("data.folder.file.path") + "statementRule/droolsUploadFiles/" + statementCalcRule.getRuleFileName().getRealValue();
        element("sc.rule.file").type(filePath.replace("/", File.separator));
    }

    public void populateRuleUdf(List<StatementCalcUdf> statementCalcUdf) throws Exception {
        element("sc.rule.select").click();
        for (StatementCalcUdf udf : statementCalcUdf) {
            element("sc.rule.select.udf", udf.getFieldName().getRealValue()).click();
        }
        element("sc.rule.select.save").click();
        for (StatementCalcUdf udf : statementCalcUdf) {
            element("sc.rule.udf.is-fed", udf.getFieldName().getRealValue()).check(udf.isValueFed());
            element("sc.rule.udf.section", udf.getFieldName().getRealValue()).selectByVisibleText(udf.getStatementSection().getRealValue());
        }
    }

    public void assertSavedRulePresentOnFirstLine(StatementCalcRule statementCalcRule) throws Exception {
        assertThat("sc.rule.results-table-row.rule.name").innerText().isEqualToIgnoringWhitespace(statementCalcRule.getRuleName().getRealValue());
        assertThat("sc.rule.results-table-row.rule.type").innerText().isEqualToIgnoringWhitespace(statementCalcRule.getRuleType().getRealValue());
    }

    public void assertSavedRuleNotPresentOnFirstLine(StatementCalcRule statementCalcRule) throws Exception {
        assertThat(element("sc.rule.results-table-row", statementCalcRule.getRuleName().getRealValue()))
                .displayed()
                .isFalse();
    }

    public void updateRuleName(String ruleName) throws Exception {
        element("sc.rule.name").input(ruleName);
    }

    public void doSearch() throws Exception {
        element("sc.rule.search").click();
    }
}
