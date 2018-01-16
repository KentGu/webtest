package com.lombardrisk.pages.welcome;

import com.lombardrisk.data.Menu;
import com.lombardrisk.test.TestDataManager;
import com.lombardrisk.util.Biz;
import org.openqa.selenium.WebDriverException;
import org.yiwan.webcore.locator.Locator;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class WelcomePage extends PageBase {

    public WelcomePage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void navigate(Menu menu) throws Exception {
        if (PropHelper.getProperty("navigation.method").equals("panel")) {
            navigateByPane(menu);
        } else {
            navigateByUrl(menu);
        }
    }

    public void gotoAgreementSummaryPageByUrl(String agrID) throws Exception {
        logger.debug("navigating to agreeement (" + agrID + ") statement page by URL");
        try {
            waitThat("wc.menu.layer1", Menu.LOGOUT.getName()).toBeVisible();
        }catch (WebDriverException wde){
            logger.warn(wde.getMessage(),wde);
        }
        String url = "/colline/collateral/viewAgreementDetails.do?agreementId=" + agrID + "&";
        String j_winname = TestCaseManager.getTestCase().getTestMap().get(Biz.J_WINNAME);
        url = TestCaseManager.getTestCase().getTestEnvironment().getApplicationServer(0).getUrl() + url;
//        url = url + (url.endsWith("do") ? "?" : "&");
        url = url + j_winname;
        url = url.replace("//colline", "/colline");
        navigate().to(url);
    }

    public void gotoAgreementStatementPageByUrl(String agrID) throws Exception {
        logger.debug("navigating to agreeement (" + agrID + ") statement page by URL");
        try {
            waitThat("wc.menu.layer1", Menu.LOGOUT.getName()).toBeVisible();
        }catch (WebDriverException wde){
            logger.warn(wde.getMessage(),wde);
        }
        String url = "/colline/collateral/showAgreementStatement.do?agreementId=" + agrID + "&";
        String j_winname = TestCaseManager.getTestCase().getTestMap().get(Biz.J_WINNAME);
        url = TestCaseManager.getTestCase().getTestEnvironment().getApplicationServer(0).getUrl() + url;
//        url = url + (url.endsWith("do") ? "?" : "&");
        url = url + j_winname;
        url = url.replace("//colline", "/colline");
        navigate().to(url);
    }

    public void RefreshPage() throws Exception{
        this.navigate().refresh();
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    /**
     * navigate by url navigation
     *
     * @param menu menu navigation menu on top panel
     */
    private void navigateByUrl(Menu menu) throws Exception {
        logger.debug("navigating to menu " + menu.getName());
        try {
            waitThat("wc.menu.layer1", Menu.LOGOUT.getName()).toBeVisible();
        }catch (WebDriverException wde){
            logger.warn(wde.getMessage(),wde);
        }
        String url = menu.getValue();
        if (menu.equals(Menu.VIEW_ORGANISATIONS)) {
            url = url.concat(((TestDataManager) TestCaseManager.getTestCase().getTestDataManager()).getTestData().getSystemData().getOrganisation(Biz.MAIN_ORG).getOrganisationId().getRealValue());
        }
        String j_winname = TestCaseManager.getTestCase().getTestMap().get(Biz.J_WINNAME);
        url = TestCaseManager.getTestCase().getTestEnvironment().getApplicationServer(0).getUrl() + url;
        url = url + (url.endsWith("do") ? "?" : "&");
        url = url + j_winname;
        url = url.replace("//colline", "/colline");
        navigate().to(url);
    }

    /**
     * navigate by menu on top pane
     *
     * @param menu navigation menu on top panel
     */
    private void navigateByPane(Menu menu) throws Exception {
        logger.debug("navigating to menu " + menu.getName());
        String[] layer = menu.getName().split(Biz.SEPARATOR);
        for (int i = 0; i <= layer.length - 2; i++) {
            Locator locator1 = locator("wc.menu.layer" + (i + 1), layer[i]);
//            Locator locator2 = locator("wc.menu.layer" + (i + 2) + ".parent", layer[i + 1]);
            element(locator1).moveTo();
            element(locator1).fireEvent(Biz.TRIGGER_EVENT_MOUSEOVER);
//            ask the parent panel always on the top
//            element(locator2).setAttribute("style", "visibility: visible");
        }
        Locator locator = locator("wc.menu.layer" + layer.length, layer[layer.length - 1]);
        element(locator).fireEvent(Biz.TRIGGER_EVENT_CLICK);
    }

    public void logout() throws Exception {
        element("wc.menu.layer1", Menu.LOGOUT.getName()).scrollIntoView().click();
        waitThat("hm.username").toBeVisible();
    }

}
