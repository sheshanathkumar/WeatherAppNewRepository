package data;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;

import model.Weather;
import utill.Utill;

/**
 * Created by SK on 1/6/2018.
 */
public class WeatherHttpClient {

    public String getWeatherReport (String place) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        //Connect to outer world....

        try {
            connection = (HttpURLConnection) (new URL(Utill.BASE_URL + place)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.getContent();

            Log.v("Connection", "connection successful");

            //......Get data from outer world...

            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ( (line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }

            Log.v("DataFetch", "Data Fetch Successfully");
            inputStream.close();
            connection.disconnect();


            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
