package com.lombardrisk.test;

import com.google.common.base.CaseFormat;
import com.lombardrisk.glue.steps.GivenStepDefinition;
import com.lombardrisk.glue.steps.ThenStepDefinition;
import com.lombardrisk.glue.steps.WhenStepDefinition;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 3/28/2016.
 */
public class TestNGCase extends TestNGTemplate {
    protected GivenStepDefinition given;
    protected WhenStepDefinition when;
    protected ThenStepDefinition then;

    @BeforeMethod
    protected void setUpMethod(ITestContext testContext, Method method) throws Exception {
        setFeatureId(this.getClass().getSimpleName().toLowerCase());
        setScenarioId(getFeatureId() + ";" + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, method.getName()));
        setUpTest();
        given = new GivenStepDefinition();
        when = new WhenStepDefinition();
        then = new ThenStepDefinition();
    }

    @AfterMethod
    protected void tearDownMethod(ITestContext testContext, Method method, ITestResult testResult) throws Exception {
        if (testResult.getThrowable() != null) {
            logger.error(method.getName(), testResult.getThrowable());
        }
        tearDownTest();
    }

}
