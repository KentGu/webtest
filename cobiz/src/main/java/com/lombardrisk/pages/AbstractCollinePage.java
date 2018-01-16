package com.lombardrisk.pages;

import com.lombardrisk.pojo.Message;
import com.lombardrisk.pojo.MessageHandler;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;


public class AbstractCollinePage extends PageBase {

    public AbstractCollinePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
	
	public void viewStatement() throws Exception{
			waitThat().jQuery().toBeInactive();
			waitThat().document().toBeReady();
			PageHelper.d33640Workaround();
			element("ag.viewStatement").click();
			waitThat("at.agmt.id").toBePresent();
			PageHelper.d33640Workaround();
	}

    public void enterPage(String pageNumber) throws Exception {
        element("", pageNumber).click();
    }

    public void showExposureStatement() throws Exception {
        element("ag.show.exposure.statement").clickByJavaScript();
    }

    public void backToPreviousPage() throws Exception {
        element("ag.back").clickByJavaScript();
    }

    public void clickCancelButton() throws Exception {
        element("ag.cancel").click();
    }

    public void cancelNextBooking() throws Exception {
        element("ag.cancelNextBooking").click();
    }

    public void longView() throws Exception {
        element("").click();
    }

    public void enterExposureManagement() throws Exception {
        element("la.exposure.management").clickByJavaScript();
    }

    public void enterAgreementExposureManagement() throws Exception {
        element("la.agreement.exposure.management").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        waitThat("em.columns").toBePresent();

    }

    public void clickAndCheckMessage(MessageHandler messageHandler) throws Exception{
        if (messageHandler.getAlertHandling() !=null&& !messageHandler.getAlertHandling().isEmpty())
            PageHelper.handleAlters(messageHandler.getAlertHandling());

    }

    public void enterInterestManagement() throws Exception {
        element("la.interest.management").clickByJavaScript();
    }


    public void getResultsInMSExcel(StringType v) throws Exception {
        TestCaseManager.getTestCase().startTransaction("");
        TestCaseManager.getTestCase().setPrepareToDownload(true);
        element("ivd.getResultInExcel").click();
        TestCaseManager.getTestCase().stopTransaction();
        v.setValue(TestCaseManager.getTestCase().getDownloadFile());
    }

    public void enterAgreementReview() throws Exception {
        element("at.agmt.review").click();
    }

    public void switch_tab(String tabName) throws Exception {
        element("ap.tab.name", tabName).click();
        logger.debug("clicking " + tabName + " tab");
    }





}
