package com.workflowassignment.dz.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DummyData {

    public static JSONArray getDummyData() {

        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("Operation", "sendmessage");
        jsonObject1.put("Employee", "Employee-1");
        jsonObject1.put("Customer", "John Doe");
        jsonObject1.put("Time", "1");
        jsonObject1.put("Type", "email");
        jsonObject1.put("Template", "Template1");

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("Operation", "sendmessage");
        jsonObject2.put("Employee", "Employee-1");
        jsonObject2.put("Customer", "John Doe");
        jsonObject2.put("Time", "3");
        jsonObject2.put("Type", "email");
        jsonObject2.put("Template", "Template2");

        JSONObject jsonObject3 = new JSONObject();
        jsonObject3.put("Operation", "sendmessage");
        jsonObject3.put("Employee", "Employee-1");
        jsonObject3.put("Customer", "John Doe");
        jsonObject3.put("Time", "5");
        jsonObject3.put("Type", "email");
        jsonObject3.put("Template", "Template3");

        JSONObject jsonObject4 = new JSONObject();
        jsonObject4.put("Operation", "sendmessage");
        jsonObject4.put("Employee", "Employee-1");
        jsonObject4.put("Customer", "John Doe");
        jsonObject4.put("Time", "7");
        jsonObject4.put("Type", "email");
        jsonObject4.put("Template", "Template4");

        JSONObject jsonObject5 = new JSONObject();
        jsonObject5.put("Operation", "sendmessage");
        jsonObject5.put("Employee", "Employee-1");
        jsonObject5.put("Customer", "John Doe");
        jsonObject5.put("Time", "9");
        jsonObject5.put("Type", "email");
        jsonObject5.put("Template", "Template5");

        jsonArray.add(jsonObject1);
        jsonArray.add(jsonObject2);
        jsonArray.add(jsonObject3);
        jsonArray.add(jsonObject4);
        jsonArray.add(jsonObject5);

        return jsonArray;

    }
}
