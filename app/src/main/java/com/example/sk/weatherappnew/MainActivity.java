package com.example.sk.weatherappnew;

import android.content.DialogInterface;
import android.icu.text.DateFormat;
import android.icu.text.DecimalFormat;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import data.CityPreferences;
import data.JsonWeatherParser;
import data.WeatherHttpClient;
import model.Weather;

public class MainActivity extends AppCompatActivity {

    private TextView city, tempp, sunset, sunrise, humidity, pressure,
                    country, wind, condition, lastupdate;
    Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = (TextView) findViewById(R.id.cityText);
        tempp = (TextView) findViewById(R.id.tempText);
        sunset = (TextView) findViewById(R.id.sunsetText);
        sunrise =  (TextView) findViewById(R.id.sunriseText);
        humidity = (TextView) findViewById(R.id.humidityText);
        pressure = (TextView) findViewById(R.id.pressureText);
        wind = (TextView) findViewById(R.id.windText);
        condition = (TextView) findViewById(R.id.conditionText);
        lastupdate = (TextView) findViewById(R.id.lastUpdate);
        country = (TextView) findViewById(R.id.countryText);

        //randerWeather("Saharsa, IN");

        //CityPreferences cityPreferences = new CityPreferences(MainActivity.this);
        //randerWeather(cityPreferences.getCity().toString());


    }

    public void randerWeather (String city) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&appid=44775afd0ae7e8619045a07683abf631"});

    }


    public class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();
            String sunSet = df.format(weather.place.getSunset());
            String sunRise = df.format(weather.place.getSunrise());
            String lastUpdate = df.format(weather.place.getLastUpdate());

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            String temp = decimalFormat.format(weather.currentCondition.getTemprature());

            city.setText(weather.place.getCity());
            sunset.setText("Sunset : "+ sunSet);
            sunrise.setText("Sunset : "+ sunRise);
            humidity.setText("Humidity : "+ weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure : "+ weather.currentCondition.getPressure() + "hPa");
            country.setText("Country : "+ weather.place.getCountry());
            tempp.setText(temp+ "F");
            wind.setText("Wind : "+ weather.wind.getSpeed());
            condition.setText("Condition : "+ weather.currentCondition.getCondition());
            lastupdate.setText("Last Update : "+ lastUpdate);


        }

        @Override
        protected Weather doInBackground(String... strings) {

            String data = ((new WeatherHttpClient().getWeatherReport(strings[0])));
            weather = JsonWeatherParser.getWeather(data);

            Log.v("City", weather.place.getCity());

            return weather;

        }
    }

    private void showInputDialog (){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInpput = new EditText(MainActivity.this);
        cityInpput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInpput.setHint("Delhi, IN");
        builder.setView(cityInpput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                CityPreferences cityPreferences = new CityPreferences(MainActivity.this);
                cityPreferences.setCity(cityInpput.getText().toString());
                randerWeather(cityPreferences.getCity().toString());
            }
        });

        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_city) {
            showInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }
}
