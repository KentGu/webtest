package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.OrganisationGlobalPreference;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class GlobalPreferencesPage extends PageBase {

    public GlobalPreferencesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * set global preference on global setting preferences page
     *
     * @param gp
     */
    public void editOrganisationGlobalSettingPreferences(OrganisationGlobalPreference gp) throws Exception {
        if (gp.getDebtClassification() != null)
            element("og.debtClassification").selectByVisibleText(gp.getDebtClassification().getRealValue());
        if (gp.getRatingAgency() != null)
            element("og.ratingAgency").selectByVisibleText(gp.getRatingAgency().getRealValue());
        if (gp.getDefaultExternalSystem() != null)
            element("og.ext.system").selectByVisibleText(gp.getDefaultExternalSystem().getRealValue());
        if (gp.getDefaultInventoryManagerSourceSystem() != null)
            element("og.src.system").selectByVisibleText(gp.getDefaultInventoryManagerSourceSystem().getRealValue());
        if (gp.isParentOrganisationIdentifier() != null){
            element("og.parent.org.id").check(gp.isParentOrganisationIdentifier());
        }
        element("og.gp.save").click();
    }


    public void assertOrganisationGlobalPreferences(OrganisationGlobalPreference organisationGlobalPreference) throws Exception {
        if (organisationGlobalPreference.getDebtClassification() != null) {
            assertThat("og.debtClassification").allSelectedTexts().containsOnly(organisationGlobalPreference.getDebtClassification().getRealValue());
        }
        if (organisationGlobalPreference.getRatingAgency() != null) {
            assertThat("og.ratingAgency").allSelectedTexts().containsOnly(organisationGlobalPreference.getRatingAgency().getRealValue());
        }
        if (organisationGlobalPreference.getDefaultExternalSystem() != null) {
            assertThat("og.ext.system").allSelectedTexts().containsOnly(organisationGlobalPreference.getDefaultExternalSystem().getRealValue());
        }
        if (organisationGlobalPreference.getDefaultInventoryManagerSourceSystem() != null) {
            assertThat("og.src.system").allSelectedTexts().containsOnly(organisationGlobalPreference.getDefaultInventoryManagerSourceSystem().getRealValue());
        }
        if (organisationGlobalPreference.isParentOrganisationIdentifier() != null) {
            assertThat("og.parent.org.id").selected().isEqualTo(organisationGlobalPreference.isParentOrganisationIdentifier());
        }
    }
}
