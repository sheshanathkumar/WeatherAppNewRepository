package utill;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SK on 1/6/2018.
 */
public class Utill {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL = "http://openweathermap.org/img/w/";

    public static JSONObject getObject (String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jobj = jsonObject.getJSONObject(tagName);
        return jobj;
    }

    public static int getInt (String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getInt(tagName);
    }

    public static long getLong (String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getLong(tagName);
    }

    public static String getString (String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }

    public static float getFloat (String tagName, JSONObject jsonObject) throws JSONException {
        return (float) jsonObject.getDouble(tagName);
    }

}
