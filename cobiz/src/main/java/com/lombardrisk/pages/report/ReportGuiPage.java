package com.lombardrisk.pages.report;


import com.lombardrisk.pojo.StringType;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;



public final class ReportGuiPage extends PageBase {

    public ReportGuiPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }



    public void runToPdf(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("rpgui.runToPdf").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    public void saveResult() throws  Exception{
        //element("rpgui.saveResults").scrollIntoView().click();
        element("rpgui.saveResults").click();
    }
}
