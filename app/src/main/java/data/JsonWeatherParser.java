package data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.CurrentCondition;
import model.Place;
import model.Weather;
import model.Wind;
import utill.Utill;

/**
 * Created by SK on 1/6/2018.
 */
public class JsonWeatherParser {

    public static Weather getWeather (String data) {
        Weather weather = new Weather();

        try {
            JSONObject jsonObject =new JSONObject(data);

            //Taking Co-ordinate from API to Place
            Place place = new Place();
            JSONObject coordObj = Utill.getObject("coord", jsonObject);
            place.setLat(Utill.getFloat("lat", coordObj));
            place.setLon(Utill.getFloat("lon", coordObj));

            Log.v("Place", "Place Data Fetch");

            //Taking sys from API to Place class
            JSONObject sysObj = Utill.getObject("sys", jsonObject);
            place.setCountry(Utill.getString("country", sysObj));
            place.setSunrise(Utill.getLong("sunrise", sysObj));
            place.setSunset(Utill.getLong("sunset", sysObj));
            place.setLastUpdate(Utill.getLong("dt", jsonObject));
            place.setCity(Utill.getString("name", jsonObject));

            weather.place = place;

            Log.v("Place1", "Place Data Fetch");

            //Taking main from API to CurrentCondition class
            JSONObject mainObj = Utill.getObject("main", jsonObject);
            CurrentCondition currentCondition = new CurrentCondition();
            currentCondition.setTemprature(Utill.getFloat("temp", mainObj));
            currentCondition.setHumidity(Utill.getInt("humidity", mainObj));
            currentCondition.setPressure(Utill.getInt("pressure", mainObj));
            weather.currentCondition = currentCondition;

            //Taking Wind from API to Wind class
            JSONObject windObj = Utill.getObject("wind", jsonObject);
            Wind wind = new Wind();
            wind.setSpeed(Utill.getFloat("speed", windObj));
            wind.setDeg(Utill.getFloat("deg", windObj));
            weather.wind = wind;

            Log.v("WindPlace", "Data Incomming ");
            //Taking weather from API (It is an array in API )

            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            weather.currentCondition.setCondition(Utill.getString("description", jsonWeather));

            Log.v("WeatherReport1", "Weather Data Fetch");

            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }
}
