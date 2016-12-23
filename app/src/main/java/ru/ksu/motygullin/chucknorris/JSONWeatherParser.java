package ru.ksu.motygullin.chucknorris;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static String getJoke(String data) throws JSONException {

        // Creating JSONObject from the data
        JSONObject jObj = new JSONObject(data);

        // Extracting the info
        JSONObject value = getObject("value", jObj);

        // Returning joke
        return value.getString("joke");
    }


    private static JSONObject getObject(String tagName, JSONObject jObj)  throws JSONException {
        return jObj.getJSONObject(tagName);

    }

}