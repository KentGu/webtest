package com.lombardrisk.test.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lombardrisk.test.pojo.Module;
import com.lombardrisk.test.pojo.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonTool {
    private final Logger logger = LoggerFactory.getLogger(JsonTool.class);
    private Modules modules;

    public JsonTool(String filePath) {
        setModules(filePath);
    }

    public void structureFolder() {
        for (Module module : modules.getModule()) {
            String compare_dir = PropTool.COMPARE_DIR + File.separator;
            String compare_module_folder = compare_dir + File.separator + module.getName();
            File file = new File(compare_module_folder);
            if (!file.exists())
                file.mkdir();
            for (String reportFile : module.getReports()) {
                File moveFile = new File(compare_dir + reportFile);
                if (moveFile.exists())
                    moveFile.renameTo(new File(compare_module_folder + File.separator + reportFile));
                else
                    logger.warn(reportFile + " in the module " + module.getName() + " doesn's exists.");
            }
        }
    }

    public Modules getModules() {
        return this.modules;
    }

    private void setModules(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.modules = mapper.readValue(new File(filePath),
                    Modules.class);
        } catch (JsonParseException jpe) {
            logger.error(filePath, jpe);
        } catch (JsonMappingException jme) {
            logger.error(filePath, jme);
        } catch (IOException ioe) {
            logger.error(filePath, ioe);
        }
    }
}
