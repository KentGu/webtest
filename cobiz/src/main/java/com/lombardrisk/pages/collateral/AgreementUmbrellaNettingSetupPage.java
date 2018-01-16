package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.SubAgreementSearch;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementUmbrellaNettingSetupPage extends PageBase {
    public AgreementUmbrellaNettingSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementUmbrellaNetting(Agreement agmt) throws Exception {
        if (agmt.getSubAgreement() != null && !agmt.getSubAgreement().isEmpty()) {
            for (SubAgreementSearch subAgreementSearch : agmt.getSubAgreement()) {
                waitThat().document().toBeReady();
                waitThat().jQuery().toBeInactive();
                if (subAgreementSearch.getType() != null)
                    element("ap.seach.type").selectByVisibleText(subAgreementSearch.getType().value());
                if (subAgreementSearch.getFilter() != null)
                    element("ap.seach.filter").input(subAgreementSearch.getFilter().getRealValue());
                if (subAgreementSearch.getLinkText() != null) {
                    waitThat("ap.seach.link", subAgreementSearch.getLinkText().getRealValue()).toBeVisible();
                    element("ap.seach.link", subAgreementSearch.getLinkText().getRealValue()).click();
//                    element("ap.seach.link", subAgreementSearch.getLinkText().getRealValue()).clickByJavaScript();
                }
            }
        }
        enterNextStage();
    }
    
    /**
     * enter next stage while setup agreement
     */
    public void enterNextStage() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("ap.next").clickByJavaScript();
        PageHelper.d33640Workaround();
    }
}
