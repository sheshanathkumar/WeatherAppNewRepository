package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by SK on 1/6/2018.
 */
public class CityPreferences {

    SharedPreferences prefs;

    public CityPreferences(Activity activity) {
        prefs = activity.getPreferences(activity.MODE_PRIVATE);
    }

    public void setCity (String city) {
        prefs.edit().putString("city", city).commit();
    }

    public String getCity () {
        return prefs.getString("city", "Patna, BH");
    }
}
