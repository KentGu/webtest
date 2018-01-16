package com.lombardrisk.pages.collateral;

import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

public class AgreementFamilySetupPage extends PageBase {

    public AgreementFamilySetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void saveAndExit() throws Exception {
        element("ap.save.and.exit").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }

    public void enterNextStage() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("ap.next").clickByJavaScript();
        PageHelper.d33640Workaround();
    }
}
