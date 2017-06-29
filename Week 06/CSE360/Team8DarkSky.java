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

/*
   The Weather class is responsible for retrieving and storing weather information from a DarkSky api call.
*/

public class Team8DarkSky {

    private static String key = "fdd8a0537b1af5efefd850be6d1e3488";

    //Update the information contained in the Team8LocationData object.
    public static void updateWeatherInfo(Team8LocationData locationData) {
        try {
            JSONObject json = readJSONFromURL("https://api.darksky.net/forecast/"
                    + key + "/" + locationData.getLatitude() + "," + locationData.getLongitude());

            locationData.setSummary(json.getJSONObject("currently").getString("summary"));
            locationData.setWindSpeed(json.getJSONObject("currently").getDouble("windSpeed"));
            locationData.setTemperature(json.getJSONObject("currently").getDouble("temperature"));
            locationData.setHumidity(json.getJSONObject("currently").getDouble("humidity"));
            locationData.setVisibility(json.getJSONObject("currently").getDouble("visibility"));
            locationData.setCloudCover(json.getJSONObject("currently").getDouble("cloudCover"));
        } catch (IOException | JSONException ex) {
            Logger.getLogger(Team8DarkSky.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    //HELPER METHOD ()
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    //HELPER METHOD ()
    private static JSONObject readJSONFromURL(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}
