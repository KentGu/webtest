package com.lombardrisk.test;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * Created by Kenny Wang on 3/22/2016.
 */
public class F00001 extends TestNGCase {

    @BeforeMethod(dependsOnMethods = {"setUpMethod"})
    public void background() throws Exception {
        given.have_login_with_credential("login.credential.test1");
        given.have_default_organisation_global_preferences();
        given.have_default_collateral_preferences();
        given.have_default_system_preferences();
        given.have_default_letter_configurations();
    }

    @Test
    public void setupAgreementWithNewlyCreatedCounterparty() throws Exception {
        given.have_organisations(Collections.singletonList("org"));
        given.have_agreements(Collections.singletonList("agr"));
    }

    @Test(enabled = false)
    public void setupAgreementWithNewlyCreatedPrincipal() throws Exception {

    }

    @Test(enabled = false)
    public void setupAgreementWithSpecifiedOrganisations() throws Exception {

    }

    @Test(enabled = false)
    public void setupAgreementWith() throws Exception {

    }
}
