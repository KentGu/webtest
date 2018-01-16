package com.lombardrisk.test.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;

public class PropTool {
    private final static Logger logger = LoggerFactory.getLogger(PropTool.class);
    private final static Properties props = new Properties();
    private static boolean hasLoaded = false;

    public static final String RESULT_HTML_DIR = System.getProperty("user.dir") + File.separator + getProperty("result_html_dir") + File.separator;
    public static final String STATISTICS_FILE = getProperty("statistics_file");
    public static final String RESOURCE_DIR = getProperty("resource_dir");
    public static final String REPORT_LIST_JSON = getProperty("report_list_json");
    public static final String COMPARE_DIR = getProperty("compare_dir");
    public static final String GOLDEN_DIR = getProperty("golden_dir");

    private static void load(String file) {
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(file);
            if (is != null) {
                props.load(is);
                is.close();
            } else {
                logger.warn(format("%s was not provided", file));
            }
        }catch (IOException e) {
            logger.error(file, e);
        }
    }

    private static boolean load() {
        load("test.properties");
        return true;
    }

    public static String getProperty(String key) {
        if (!hasLoaded)
            hasLoaded = load();
        return  System.getProperty(key, props.getProperty(key));
    }
}
