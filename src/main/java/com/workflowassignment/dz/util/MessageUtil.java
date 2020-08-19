package com.workflowassignment.dz.util;

import com.workflowassignment.dz.config.TemplateConfig;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static com.workflowassignment.dz.util.Constants.DUMMY_DATA;


public class MessageUtil {

    public static JSONObject getData(JSONArray dummyData, String template) {
        if (!dummyData.isEmpty()) {
            DUMMY_DATA = dummyData;
            JSONArray jsonArray = dummyData;
            for (int j = 0; j < dummyData.size(); j++) {
                return  (JSONObject) jsonArray.get(j);
            }
        }
        return null;
    }

    public static String getMessage(JSONObject jsonObject) {
        if (TemplateConfig.getTemplate() != null && TemplateConfig.getTemplate().size() > 0 && jsonObject != null) {
            String message = String.format(TemplateConfig.getTemplate().get(jsonObject.get("Template")), jsonObject.get("Customer"), jsonObject.get("Employee"));
            TemplateConfig.getTemplate().remove(jsonObject.get("Template"));
            DUMMY_DATA.remove(jsonObject);
            return message;
        } else {
            return "";
        }
    }

    public static JSONArray getData() {
        return DUMMY_DATA;
    }

    public static Long getTimeUsingKeyInMillis(String key, JSONObject jsonObject) {
        if (jsonObject.get(key) != null) {
            return Long.valueOf(jsonObject.get(key).toString()) * 60000;
        }
        return 0L;
    }
}
