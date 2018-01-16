package com.lombardrisk.test.config;

import com.lombardrisk.test.utils.PropTool;

import java.io.File;


public class ReportConfig {
    public static char csvSeparateSymbol = ',';

    public static String getResultDir() {
        return PropTool.RESULT_HTML_DIR;
    }

    public static String getStatisticsFileName() {
        return PropTool.RESULT_HTML_DIR + File.separator + PropTool.STATISTICS_FILE;
    }

    public static String customConflictWithExclude() {
        return "<b>error</b> ):Custom Keys conflict with Exclude Columns .";
    }

    public static String keyNotFound(String key, String fileName) {
        return "<b>error</b> ):Custom Key " + key + "do NOT exists in " + fileName;
    }

    public static String fileNotFount(String fileName) {
        return "<b>error</b> ): " + fileName + " do NOT exists!";
    }

    public static String keysNotUnique(String fileName) {
        return "<b>error</b> ): Custom Key find more than 1 items in file " + fileName + ",Please check custom keys.";
    }

    public static void main(String[] args) {


    }
}
