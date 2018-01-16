package com.lombardrisk.pages.report;

import com.lombardrisk.data.ReportMenu;

import com.lombardrisk.pojo.OrganisationSearch;
import com.lombardrisk.pojo.OutputFormat;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.Biz;

import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;

public final class ReportListPage extends PageBase {

    public ReportListPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    /**
     * navigate to such report by menu on the left panel of report list
     *
     * @param reportMenu
     */
    public void navigate(ReportMenu reportMenu) throws Exception {
        navigate(reportMenu.getName());
    }

    /**
     * navigate to such report template by menu on the left panel of report list
     *
     * @param reportMenu
     * @param template
     */
    public void navigate(ReportMenu reportMenu, String template) throws Exception {
        navigate(reportMenu.getName(), template);
    }

    /**
     * navigate to such report by menu on the left panel of report list
     *
     * @param reportMenu
     * @param template
     */
    private void navigate(String reportMenu, String... template) throws Exception {
        String[] layer = reportMenu.split(Biz.SEPARATOR);
        element("rp.expand", layer[0]).click();
        if (template.length > 0) {
            element("rp.expand.sub", layer[1]).clickByJavaScript();
            element("rp.report", template[0]).clickByJavaScript();
        } else {
            element("rp.report", layer[1]).clickByJavaScript();
        }
    }

    /**
     * set up ConcentrationLimitsReport
     *
     * @param search
     */
    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getCriteria() != null)
            element("rp.frame.search.criteria").selectByVisibleText(search.getCriteria().value());
        if (search.getType() != null)
            element("rp.frame.search.type").selectByVisibleText(search.getType().value());
        if (search.getFilter() != null)
            element("rp.frame.search.filter").input(search.getFilter().getRealValue());
        element("rp.frame.search").click();
        if (search.getLinkText() != null)
            element("rp.frame.search.result", search.getLinkText().getRealValue()).click();
    }

    /**
     * @param searches
     */
    public void searchOrganisation(List<OrganisationSearch> searches) throws Exception {
        for (OrganisationSearch search : searches) {
            searchOrganisation(search);
        }
    }


    public void saveAsTemplate() throws Exception {
        element("rp.save.as.template").click();
    }

    public void editOutputFormat(List<OutputFormat> outputFormat) throws Exception {
        element("rp.format.edit").click();
        for (OutputFormat format : outputFormat) {
            editOutputFormat(format);
        }
        element("rp.changes.apply").click();
    }

    public void editOutputFormat(OutputFormat outputFormat) throws Exception {
        if (outputFormat.getDisplayValue() != null)
            element("rp.display.value", outputFormat.getInternalValue().getRealValue()).input(
                    outputFormat.getDisplayValue().getRealValue());
        if (outputFormat.getDisplayOrder() != null) {
            element("rp.display.order", outputFormat.getInternalValue().getRealValue()).click();
            element("rp.display.order", outputFormat.getInternalValue().getRealValue()).selectByVisibleText(outputFormat.getDisplayOrder().getRealValue());
        }
        if (outputFormat.getGroupLevel() != null) {
            element("rp.group.level", outputFormat.getInternalValue().getRealValue()).click();
            element("rp.group.level", outputFormat.getInternalValue().getRealValue()).selectByVisibleText(outputFormat.getGroupLevel().getRealValue());
        }
    }

    
    public void runReport(long waitTime) throws Exception{
    	element("rp.run.report").click();
        waitThat("rpgui.load.image").toBeDisappearedIn(waitTime);
    }
    
    public void runToExcelWorksheet(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("rp.run.to.excel").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }
    public void runToCsv(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("rp.run.to.csv").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }
    public void runToXml(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("rp.run.to.xml").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }
    public void runToPdf(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("rp.run.to.pdf").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }
}
