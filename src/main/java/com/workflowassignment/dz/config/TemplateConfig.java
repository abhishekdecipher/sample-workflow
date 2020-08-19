package com.workflowassignment.dz.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;


public class TemplateConfig {
    private static final Logger log = LogManager.getLogger(TemplateConfig.class);
    public static String YAML_FILE_PATH;
    public static String CONFIG_YML_FILE_PATH;

    static Map<String, String> template = new HashMap<>();

    public static void applyArgs() {
        try {
            File configYaml = new File("template.yml");
            applyConfig(configYaml.getAbsolutePath());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void applyConfig(final String yamlFilePath) {
        try {
            final File configYaml = new File(yamlFilePath);
            final FileInputStream fileInputStream = new FileInputStream(configYaml);
            TemplateConfig.YAML_FILE_PATH = yamlFilePath;
            final DumperOptions printOptions = new DumperOptions();
            printOptions.setDefaultScalarStyle(DumperOptions.ScalarStyle.DOUBLE_QUOTED);
            TemplateConfig.applyYamlProperties((Map<String, Map<String, String>>) new Yaml().load(fileInputStream));
            TemplateConfig.CONFIG_YML_FILE_PATH = yamlFilePath;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void applyTemplateConfiguration(Map<String, Map<String, String>> configYaml) {
        try {
            if (configYaml.containsKey("Template")) {
                for (int i = 1; configYaml.get("Template").size() >= i; i++) {
                    template.put("Template" + i, configYaml.get("Template").get(i));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private static void applyYamlProperties(final Map<String, Map<String, String>> configYaml) throws Exception {
        applyTemplateConfiguration(configYaml);
    }

    public static Map<String, String> getTemplate() {
        return template;
    }
}
