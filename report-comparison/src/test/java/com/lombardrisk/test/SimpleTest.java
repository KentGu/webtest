package com.lombardrisk.test;

import com.lombardrisk.test.utils.ExcelTool;
import com.lombardrisk.test.utils.FileTool;
import com.lombardrisk.test.utils.JsonTool;
import com.lombardrisk.test.utils.PropTool;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;


public class SimpleTest {

    public String goldenFiles[];
    public String resultFiles[];
    public FileTool tool;

    @BeforeSuite
    public void beforeSuite() {
        //准备统计数据的Excel文件
        JsonTool jsonTool = new JsonTool(PropTool.RESOURCE_DIR + File.separator + PropTool.REPORT_LIST_JSON);
        jsonTool.structureFolder();
        ExcelTool.prepareStatisticsExcel();
    }

    @Parameters({"goldenDir", "resultDir", "folderName", "excludeColumns", "keyColumns"})
    @Test
    public void test(String goldenDir, String resultDir, String folderName, String excludeColumns, String KeyColumns) throws Exception {
        if (goldenDir == null)
            goldenDir = PropTool.GOLDEN_DIR;
        if (resultDir == null)
            resultDir = PropTool.COMPARE_DIR;

        if (!goldenDir.endsWith("/")) {
            goldenDir = goldenDir + "/";
        }
        if (!resultDir.endsWith("/")) {
            resultDir = resultDir + "/";
        }
        if (!folderName.endsWith("/")) {
            folderName = folderName + "/";
        }
        //System.out.println(" 111111111 : ---->" + resultDir);
        //System.out.println(" folderName ===> " + folderName);
        goldenDir = goldenDir + folderName;
        resultDir = resultDir + folderName;
        File golden = new File(goldenDir);
        goldenFiles = golden.list();
        File result = new File(resultDir);
        resultFiles = result.list();


        //System.out.println(" 2222222222 : ---->" + resultDir);
        boolean flag = true;
        for (String goldenFileName : goldenFiles) {
            tool = new FileTool(goldenDir, resultDir, excludeColumns, KeyColumns);
            if (tool.readFile(goldenFileName, resultFiles)) {
                String compareResult = tool.compareFile();
                if ("NotSame".equals(compareResult)) {
                    tool.generateResult();
                    flag = false;
                }
            }else
                flag = false;
        }
        Assert.assertTrue(flag, "not same");
    }

    @AfterSuite
    public void afterSuite() {
        //完成统计数据的Excel文件的读写
        ExcelTool.close();
    }

}