package com.lombardrisk.test;

import com.lombardrisk.pages.PageManager;
import org.yiwan.webcore.test.TestBase;
import org.yiwan.webcore.util.Helper;
import org.yiwan.webcore.util.PropHelper;

/**
 * Created by Kenny Wang on 3/21/2016.
 */
public abstract class TestNGTemplate extends TestBase {
    private boolean unmarshalSuccess;

    @Override
    public void setUpTest(boolean proxied) throws Exception {
        unmarshalSuccess = false;
        super.setUpTest(proxied);
        setTestDataManager(new TestDataManager(getTestEnvironment().getDatabaseServer(0).getDump(), getFeatureId(), getScenarioId(), PropHelper.TARGET_SCENARIO_DATA_FOLDER + getSuiteTestSeparator()));
        ((TestDataManager) getTestDataManager()).unmarshalTestData();// create test data
        unmarshalSuccess = true;
        if (getTestEnvironment().getDatabaseServer(0).getDriver() != null)
            getDatabaseWrapper().initDatabaseWrapper(getTestEnvironment().getDatabaseServer(0));
        setPageManager(new PageManager(getWebDriverWrapper(), getDatabaseWrapper()));
        getWebDriverWrapper().navigate().to(getTestEnvironment().getApplicationServer(0).getUrl());
        report(Helper.getTestReportStyle(getTestEnvironment().getApplicationServer(0).getUrl(), "open test server url"));
    }

    @Override
    public void tearDownTest() throws Exception {
        try {
            if (unmarshalSuccess) {
                ((TestDataManager) getTestDataManager()).marshalTestData();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            super.tearDownTest();
        }
    }

}
