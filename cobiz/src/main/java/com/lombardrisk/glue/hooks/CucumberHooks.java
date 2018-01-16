package com.lombardrisk.glue.hooks;

import com.lombardrisk.test.TestNGCucumber;
import com.lombardrisk.test.TestNGFeature;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.ScenarioImpl;
import gherkin.formatter.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yiwan.webcore.test.ITestBase;
import org.yiwan.webcore.test.TestCaseManager;
import org.yiwan.webcore.util.PropHelper;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Kenny Wang on 3/28/2016.
 */
public class CucumberHooks {
    private final static Logger logger = LoggerFactory.getLogger(CucumberHooks.class);
    private ITestBase testCase;

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {
        if (TestCaseManager.getTestCase() == null) {
            //may return null if run cucumber test from intellij idea or CLI
            TestCaseManager.setTestCase(new TestNGFeature());
        }
        testCase = TestCaseManager.getTestCase();
        ((TestNGCucumber) testCase).setScenario(scenario);
        testCase.setFeatureId(scenario.getId().substring(0, scenario.getId().indexOf(";")));
        testCase.setScenarioId(scenario.getId());
        if (scenario.getSourceTagNames().contains(PropHelper.getProperty("download.enable.by.scenario"))) {
            testCase.setUpTest(true);
        } else {
            testCase.setUpTest(false);
        }
    }

    @After
    public void afterScenario(Scenario scenario) throws Exception {
        if (scenario.isFailed()) {
            Result failedStepResult = getFailedStepResult(getStepResults(scenario));
            if (failedStepResult != null) {
                logger.error(scenario.getId(), failedStepResult.getError());
            }
            try {
                testCase.embedScreenshot();
            } catch (Throwable e) {
                logger.error(e.getMessage(), e);
            }
        }
        try {
            testCase.tearDownTest();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            testCase.embedTestLog();
        }
    }

    @SuppressWarnings("unchecked")
    private List<Result> getStepResults(Scenario scenario) throws Exception {
        Field field = ScenarioImpl.class.getDeclaredField("stepResults");
        field.setAccessible(true);
        return (List<Result>) field.get(scenario);
    }

    private Result getFailedStepResult(List<Result> stepResults) {
        for (Result stepResult : stepResults) {
            if ("failed".equals(stepResult.getStatus())) {
                return stepResult;
            }
        }
        return null;
    }
}
