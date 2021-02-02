package com.example.weatherappprojectschiff;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView weatherListView;
    ArrayList<WeatherDataPoint> weatherData;
    LinearLayout layout;
    TextView textLat;
    TextView textLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setBackgroundColor(Color.BLUE);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        button.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

        final EditText lat = findViewById(R.id.idLat);
        lat.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));
        final EditText lon = findViewById(R.id.idLon);
        lon.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));
        weatherListView = findViewById(R.id.weatherList);
        layout = findViewById(R.id.parentLayout);
        layout.setBackgroundColor(Color.BLACK);
        weatherData = new ArrayList();
        textLat = findViewById(R.id.textLat);
        textLat.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));
        textLon = findViewById(R.id.textLong);
        textLon.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));


        //weatherData.add(new WeatherDataPoint("Town Time: N/A", "Temperature: N/A", "Weather: N/A", "Please Input Latitude and Longitude Coordinates!\nTown Name: N/A"));
        weatherData.add(new WeatherDataPoint("", "", "","", ""));
        ListAdapter listAdapter = new ListAdapter(this, R.layout.data_xml, weatherData);
        weatherListView.setAdapter(listAdapter);

        //fix images and get it to work for real weather
        //fill weather list based on connection to api
        //make api call based on lat and long
        //add colors to layout and make it look cool

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double latNum = Double.parseDouble(lat.getText().toString());
                    double lonNum = Double.parseDouble(lon.getText().toString());
                    Log.d("Test", "Lat: "+latNum+", Long: "+lonNum);
                    try {
                        String URLlink = "http://api.openweathermap.org/data/2.5/find?lat="+latNum+"&lon="+lonNum+"&cnt=3&appid=dc33dfe7f050efd6bcc8940773c6e8f3&units=imperial";
                        Log.d("URL", URLlink);

                        //String URLlink = "http://api.openweathermap.org/data/2.5/find?lat=40.37425732388781&lon=-74.56398991571521&cnt=3&appid=dc33dfe7f050efd6bcc8940773c6e8f3&units=imperial";
                        DownloadFilesTask task = new DownloadFilesTask();
                        task.execute(new URL(URLlink));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }catch(NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Please enter a Latitude and Longitude that is ONLY numbers!", Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    public String timeConversion(String time)
    {
        Date d = new Date(Long.parseLong(time)*1000);
        String newTime = d.toString();
        return newTime;

       /* //03:00:00 (input)
        //3 AM (output)
        int index = time.indexOf(":")-2;
        String temp = time.substring(index, time.length());
        String timeNew = "";
        int tempNum = Integer.parseInt(temp.substring(0, 2));
        if(temp.charAt(0)=='0' || time.substring(0, 2).equals("11")) {
            if (tempNum == 0)
                tempNum = 12;
            timeNew = tempNum+"AM";
        }
        else{
            int tempNum2 = Integer.parseInt(temp.substring(0, 2));
            tempNum2%=12;
            timeNew = tempNum2+"PM";
        }
        return timeNew;*/

    }


    private class DownloadFilesTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp"));
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main"));
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(0).getInt("dt")+"");
              Log.d("Test", "ASync");
                String firstTownTime = jsonObject.getJSONArray("list").getJSONObject(0).getInt("dt")+"";
                String firstTownTemp = jsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("main").getString("temp");
                String firstTownWeather = jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");
                String firstTownWeatherDescription = jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description");
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("description"));
                String firstTownName = jsonObject.getJSONArray("list").getJSONObject(0).getString("name");

                String secondTownTime = jsonObject.getJSONArray("list").getJSONObject(1).getInt("dt")+"";
                String secondTownTemp = jsonObject.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp");
                String secondTownWeather = jsonObject.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main");
                String secondTownWeatherDescription = jsonObject.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description");
                String secondTownName = jsonObject.getJSONArray("list").getJSONObject(1).getString("name");

                String thirdTownTime = jsonObject.getJSONArray("list").getJSONObject(2).getInt("dt")+"";
                String thirdTownTemp = jsonObject.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp");
                String thirdTownWeather = jsonObject.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main");
                String thirdTownWeatherDescription = jsonObject.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("description");
                String thirdTownName = jsonObject.getJSONArray("list").getJSONObject(2).getString("name");

                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(1).getJSONObject("main").getString("temp"));
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("main"));
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(1).getInt("dt")+"");

                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(2).getJSONObject("main").getString("temp"));
                Log.d("Test", jsonObject.getJSONArray("list").getJSONObject(2).getJSONArray("weather").getJSONObject(0).getString("main"));
                Log.d("Test", "Time: "+timeConversion(firstTownTime));

                weatherData = new ArrayList<>();
                weatherData.add(new WeatherDataPoint(timeConversion(firstTownTime), firstTownTemp, firstTownWeather, firstTownWeatherDescription, firstTownName));
                weatherData.add(new WeatherDataPoint(timeConversion(secondTownTime), secondTownTemp, secondTownWeather, secondTownWeatherDescription, secondTownName));
                weatherData.add(new WeatherDataPoint(timeConversion(thirdTownTime), thirdTownTemp, thirdTownWeather, thirdTownWeatherDescription, thirdTownName));

                ListAdapter listAdapter = new ListAdapter(MainActivity.this, R.layout.data_xml, weatherData);
                weatherListView.setAdapter(listAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //firstWeatherList.add(new WeatherDataPoint())

        }

        @Override
        protected JSONObject doInBackground(URL... urls) {
            Log.d("Test", "DoInBackground");

            try {
                //URL link = new URL(urls[0]);
                URLConnection connection = urls[0].openConnection();
                InputStream stream = connection.getInputStream();
                //Log.d("Test", "Words");
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line, JSONString = "";
                while((line=reader.readLine())!=null)
                {
                    JSONString+=line;

                }
                JSONObject object = new JSONObject(JSONString);
                Log.d("Test", JSONString);

                return object;
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                Log.d("Test", e.toString());

            }
            return null;
        }

    }

    public class ListAdapter extends ArrayAdapter<WeatherDataPoint>{
        private final Context c;
        private final int xml;
        private final List<WeatherDataPoint> list;

        public ListAdapter(@NonNull Context context, int resource, @NonNull List<WeatherDataPoint> objects) {
            super(context, resource, objects);
            c = context;
            xml = resource;
            list = objects;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) c.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View adapterView = layoutInflater.inflate(xml, null);
            //Color PURPLE = new Color();

            TextView temperature = adapterView.findViewById(R.id.temperatureData);
            temperature.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            temperature.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

            TextView weather = adapterView.findViewById(R.id.weatherData);
            weather.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            weather.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

            TextView weatherDescription = adapterView.findViewById(R.id.weatherDesriptionData);
            weatherDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            weatherDescription.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

            TextView time = adapterView.findViewById(R.id.timeData);
            time.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            time.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

            TextView name = adapterView.findViewById(R.id.townName);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            name.setTypeface(ResourcesCompat.getFont(MainActivity.this, R.font.chopsic));

            ImageView condition = adapterView.findViewById(R.id.weatherPic);

            LinearLayout layout = adapterView.findViewById(R.id.horLayout);
            layout.setBackgroundColor(Color.DKGRAY);
            temperature.setText("Temperature: "+list.get(position).getTemperature()+" °F");
            weather.setText("Weather: "+list.get(position).getWeather());
            weatherDescription.setText("Description: "+list.get(position).getDescription());
            time.setText("Time: "+list.get(position).getTime());
            name.setText("Town Name: "+list.get(position).getTownName());

            switch(list.get(position).getWeather())
            {
                case "Thunderstorm": condition.setImageResource(R.drawable.thunderstorm);
                    Log.d("Test", "thunderstorm");
                break;
                case "Clouds": condition.setImageResource(R.drawable.clouds);
                    Log.d("Test", "clouds");
                    break;
                case "Rain": condition.setImageResource(R.drawable.rain);
                    Log.d("Test", "rain");
                    break;
                case "Snow": condition.setImageResource(R.drawable.snowy);
                    Log.d("Test", "snow");
                    break;
                case "Clear": condition.setImageResource(R.drawable.clearsky);
                    Log.d("Test", "clear");
                    break;
                case "Drizzle": condition.setImageResource(R.drawable.rain);
                    Log.d("Test", "drizzle");
                    break;
                case "Mist": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "mist");
                    break;
                case "Smoke": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "smoke");
                    break;
                case "Fog": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Fog");
                    break;
                case "Haze": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Haze");
                    break;
                case "Dust": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Dust");
                    break;
                case "Sand": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Sand");
                    break;
                case "Ash": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Ash");
                    break;
                case "Squall": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Squall");
                    break;
                case "Tornado": condition.setImageResource(R.drawable.mist);
                    Log.d("Test", "Tornado");
                    break;
            }

//            if(weather.getText().toString().equals("clouds"))
//                condition.setImageResource(R.drawable.cloudy);
//            else if(weather.getText().toString().equals("clear"))
//                condition.setImageResource(R.drawable.clearsky);
//            else if(weather.getText().toString().equals("rain"))
//                condition.setImageResource(R.drawable.rainy);
//            else if(weather.getText().toString().equals("snow"))
//                condition.setImageResource(R.drawable.snowy);

            return adapterView;

        }
    }
}
