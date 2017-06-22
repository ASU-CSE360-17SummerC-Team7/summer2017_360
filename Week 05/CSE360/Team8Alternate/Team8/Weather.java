/*
   The Weather class is responsible for getting the weather information
   from DarkSky api.
*/
package CSE360;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

public class Weather {

    public WeatherData weatherData;

    public Weather(CityDetail city) {
        this._run(city);
    }

    private void _run(CityDetail city) {
        try {
            String yourKey = "fdd8a0537b1af5efefd850be6d1e3488";
            JSONObject json = readJsonFromUrl("https://api.darksky.net/forecast/"
                    + yourKey + "/" + city.getLatitude() + "," + city.getLongitude());
            String summary = json.getJSONObject("currently").getString("summary");
            double windSpeed = json.getJSONObject("currently").getDouble("windSpeed");
            double temperature = json.getJSONObject("currently").getDouble("temperature");
            double humidity = json.getJSONObject("currently").getDouble("humidity");
            double visibility = json.getJSONObject("currently").getDouble("visibility");
            double cloudCover = json.getJSONObject("currently").getDouble("cloudCover");
            weatherData = new WeatherData(summary, windSpeed, temperature, humidity, visibility, cloudCover);
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
