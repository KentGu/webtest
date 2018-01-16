package com.lombardrisk.util;

import com.lombardrisk.pojo.Message;
import org.openqa.selenium.UnhandledAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;

public class PageHelper {
    private static final Logger logger = LoggerFactory.getLogger(Biz.class);

    @Deprecated
    public static void d31489Workaround(IWebDriverWrapper.IWebElementWrapper returnButton, PageBase pageBase) throws Exception {
        if (returnButton.isDisplayed()) {
            pageBase.navigate().backward();
            logger.warn("Defect D31489 occurred - JspException: Cannot find bean: \"menu\" in any scope");
        }
    }

    @Deprecated
    public static void d31489Workaround(IWebDriverWrapper.IWebElementWrapper returnButton, PageBase pageBase, IWebDriverWrapper.IWebElementWrapper actionElement) throws Exception {
        for (int i = 0; i < 3; i++) {
            if (returnButton.isDisplayed()) {
                pageBase.navigate().backward();
                logger.warn("Defect D31489 occurred - JspException: Cannot find bean: \"menu\" in any scope");
                actionElement.clickByJavaScript();
            } else {
                break;
            }
        }
    }

    public static void d33640Workaround() throws Exception{
        IWebDriverWrapper webDriverWrapper = TestCaseManager.getTestCase().getWebDriverWrapper();
        try {
            webDriverWrapper.waitThat().jQuery().toBeInactive();
            webDriverWrapper.waitThat().document().toBeReady();
            for (int i = 0; i < 3; i++) {
                if (webDriverWrapper.getCurrentUrl().contains(
                        TestCaseManager.getTestCase().getTestMap().get(Biz.J_WINNAME))
                        || webDriverWrapper.getPageSource().contains(
                        "<input name=\"j_winname\" value=\"" + TestCaseManager.getTestCase().getTestMap().get(
                                Biz.J_WINNAME) + "\" type=\"hidden\"/>")) {
                    break;
                }
                logger.info("Waiting for J_WINNAME to be loaded");
                webDriverWrapper.waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
            }
        } catch (UnhandledAlertException uae) {
            logger.error("UnhandleAlertException thrown", uae);
            if (webDriverWrapper.alert().isPresent()) {
                webDriverWrapper.alert().accept();
                d33640Workaround();
            }
        }
    }

    public static void handleAlters(List<Message> handles) {
        IWebDriverWrapper webDriverWrapper = TestCaseManager.getTestCase().getWebDriverWrapper();
        webDriverWrapper.waitThat().document().toBeReady();
        webDriverWrapper.waitThat().jQuery().toBeInactive();
        if (handles != null && !handles.isEmpty()) {
            for (Message handle : handles) {
                handleAlter(handle);
            }
        } else {
            if (webDriverWrapper.alert().isPresent()) {
//                webDriverWrapper.validateThat().alert().present().isEqualTo(false);
                logger.warn("unexpected alert present - " + webDriverWrapper.alert().getText());
                webDriverWrapper.alert().accept();
            }
        }
    }

    public static void handleAlter(Message handle) {
        IWebDriverWrapper webDriverWrapper = TestCaseManager.getTestCase().getWebDriverWrapper();
        webDriverWrapper.waitThat().document().toBeReady();
        webDriverWrapper.waitThat().jQuery().toBeInactive();
        if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
            if (webDriverWrapper.alert().getText().matches(handle.getContent().getRealValue())) {
                if (handle.isAccept())
                    webDriverWrapper.alert().accept();
                else
                    webDriverWrapper.alert().dismiss();
            } else {
                webDriverWrapper.validateThat().alert().text().matches(handle.getContent().getRealValue());
                logger.warn("Unexpected alert present - " + webDriverWrapper.alert().getText());
                webDriverWrapper.alert().accept();
            }
        } else if (webDriverWrapper.alert().isPresent()) {
            logger.warn("Alert present - " + webDriverWrapper.alert().getText());
            if (handle.isAccept()) {
                webDriverWrapper.alert().accept();
            } else {
                webDriverWrapper.alert().dismiss();
            }
        } else {
//            webDriverWrapper.validateThat().alert().present().isTrue();
            logger.warn("Alert expected but alert not present = " + webDriverWrapper.alert().isPresent());
        }
    }
}
