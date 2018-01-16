package com.lombardrisk.test;

import cucumber.api.Scenario;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.RuntimeOptions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.slf4j.MDC;
import org.yiwan.webcore.util.JaxbHelper;
import org.yiwan.webcore.util.PropHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kenny Wang on 10/11/2016.
 */
public class TestNGCucumber extends TestNGTemplate {
    private Scenario scenario;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    protected RuntimeOptions getRuntimeOptions(TestNGCucumberRunner testNGCucumberRunner) throws NoSuchFieldException, IllegalAccessException {
        Field field = TestNGCucumberRunner.class.getDeclaredField("runtimeOptions");
        field.setAccessible(true);
        return (RuntimeOptions) field.get(testNGCucumberRunner);
    }

    @SuppressWarnings("unchecked")
    protected List<String> getPluginFormatterNames(RuntimeOptions runtimeOptions) throws NoSuchFieldException, IllegalAccessException {
        Field field = RuntimeOptions.class.getDeclaredField("pluginFormatterNames");
        field.setAccessible(true);
        return (List<String>) field.get(runtimeOptions);
    }

    protected void rewritePluginFormatter(List<String> pluginFormatterNames) {
        String[] formats = {"json", "html", "rerun"};
        String pluginFormatterName;
        for (int i = 0; i < pluginFormatterNames.size(); i++) {
            for (String format : formats) {
                if ((pluginFormatterName = pluginFormatterNames.get(i).trim()).startsWith(format)) {
                    String path = pluginFormatterName.substring((format + ":").length());
                    File file = new File(path);
                    String newPath = file.getParent() + "/" + getSuiteTestSeparator() + file.getName();
                    pluginFormatterNames.set(i, format + ":" + newPath);
                }
            }
        }
    }

    @Override
    public void embedScreenshot() throws IOException {
        if (getWebDriverWrapper() != null) {
            super.embedScreenshot();
            scenario.embed(getWebDriverWrapper().getScreenshotAs(OutputType.BYTES), "image/png");
        }
    }

    @Override
    public void embedTestLog() throws IOException {
        super.embedTestLog();
        embedXml(FileUtils.readFileToString(new File(PropHelper.RESULT_FOLDER + PropHelper.LOG_FOLDER + MDC.get(PropHelper.DISCRIMINATOR_KEY))));
    }

    @Override
    public void embedTestData(Object object) throws Exception {
        super.embedTestData(object);
        embedXml(JaxbHelper.marshalWithoutXmlRootElement(object));
    }

    public void embedHtml(String html) {
        scenario.embed(html.getBytes(Charset.defaultCharset()), "text/html");
    }

    public void embedPlainText(String text) {
        scenario.write("\n<p>\n" + text + "</p>");
    }

    public void embedXml(String xml) {
        scenario.write("\n<xmp>\n" + xml + "</xmp>");
    }

    @Override
    public void setUpTest(boolean proxied) throws Exception {
        super.setUpTest(proxied);
        logger.info("scenario tag names : " + Arrays.toString(scenario.getSourceTagNames().toArray()));
    }
}
