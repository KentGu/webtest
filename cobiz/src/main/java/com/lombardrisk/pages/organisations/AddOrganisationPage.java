package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.Organisation;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class AddOrganisationPage extends PageBase {

    public AddOrganisationPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void addOrganisation(Organisation org) throws Exception {
        if (org.getShortName() != null)
            element("og.sname").input(org.getShortName().getRealValue());
        if (org.getLongName() != null)
            element("og.lname").input(org.getLongName().getRealValue());
        if (org.getCode() != null)
            element("og.code").input(org.getCode().getRealValue());
        element("og.submit").click();
    }

    public void switchToSearchFrame() throws Exception {
        element("og.search.frame").switchTo();
    }

    public void switchToDefaultFrame() {
        switchTo().defaultContent();
    }

}
