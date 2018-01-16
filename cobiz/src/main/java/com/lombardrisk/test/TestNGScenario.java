package com.lombardrisk.test;

import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.RuntimeOptions;
import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;
import org.testng.annotations.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kenny Wang on 3/24/2016.
 */

public class TestNGScenario extends TestNGCucumber {
    private TestNGCucumberRunner testNGCucumberRunner;
    private RuntimeOptions runtimeOptions;
    private List<String> pluginFormatterNames;
    private List<CucumberFeatureWrapper> cucumberFeatures = new ArrayList<>();

    @BeforeClass(alwaysRun = true, dependsOnMethods = {"beforeClass"})
    public void setUpClass() throws Exception {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        runtimeOptions = getRuntimeOptions(testNGCucumberRunner);
        pluginFormatterNames = getPluginFormatterNames(runtimeOptions);
        rewritePluginFormatter(pluginFormatterNames);
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        cucumberFeatures.add(cucumberFeature);
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true, dependsOnMethods = {"afterClass"})
    public synchronized void tearDownClass() {
        testNGCucumberRunner.finish();
    }

    @AfterSuite
    public synchronized void suiteTearDown() {
        StringBuffer scenarioJsonPath = new StringBuffer("");
        for (String path : pluginFormatterNames) {
            if (path.startsWith("json:"))
                scenarioJsonPath.append(path.substring("json:".length()));
        }
        if (!scenarioJsonPath.toString().equals("")) {
            logger.debug("Generate result.json file");
            List<Map<String, Object>> resultJsonList = new ArrayList<>();
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File scenarioJsonFile = new File(scenarioJsonPath.toString());
            File resultFolder = new File(scenarioJsonFile.getParent());
            for (File scenarioFile : resultFolder.listFiles()) {
                if (scenarioFile.getName().endsWith(".json")) {
                    logger.debug("Opening " + scenarioFile.getName());
                    try {
                        FileReader scenarioFileReader = new FileReader(scenarioFile);
                        BufferedReader scenarioBufferedReader = new BufferedReader(scenarioFileReader);

                        Map<String, Object> scenarioJsonMap = ((List<Map<String, Object>>) gson.fromJson(
                                scenarioBufferedReader, List.class)).get(0);
                        Map<String, Object> matchedJsonObject = getFeatureJsonObject(resultJsonList, scenarioJsonMap);
                        if (matchedJsonObject == null) {
                            resultJsonList.add(scenarioJsonMap);
                        } else {
                            List<Map<String, Object>> scenarioElements = (List) scenarioJsonMap.get("elements");
                            List<Map<String, Object>> featureElements = (List) matchedJsonObject.get("elements");
                            featureElements.addAll(scenarioElements);
                        }
                        try {
                            scenarioFileReader.close();
                            scenarioBufferedReader.close();
                            logger.debug("Deleting " + scenarioFile.getName());
                            scenarioFile.delete();
                        } catch (IOException ioe) {
                            logger.error("IOException thrown:\n", ioe);
                        }
                    } catch (FileNotFoundException fnfe) {
                        logger.error("FileNotFoundException thrown:\n", fnfe);
                    }
                }
            }
            try {
                File resultFile = new File(resultFolder.getAbsoluteFile() + File.separator + "result.json");
                logger.debug("Create result.json file");
                if (resultFile.createNewFile()) {
                    BufferedWriter out = new BufferedWriter(new FileWriter(resultFile));
                    out.write(gson.toJson(resultJsonList));
                    out.close();
                } else {
                    logger.error("Generating result.json file failed");
                }
            } catch (IOException ioe) {
                logger.error("IOException thrown:\n", ioe);
            }
        }
    }

    private Map<String, Object> getFeatureJsonObject(List<Map<String, Object>> featureList, Map<String, Object> scenarioMap) {
        String featureName = (String) scenarioMap.get("name");
        for (Map<String, Object> feature : featureList) {
            if (feature.get("name").equals(featureName))
                return feature;
        }
        return null;
    }
}
