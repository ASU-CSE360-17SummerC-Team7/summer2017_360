package CSE360;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;
//Devyn Hedin
//Melissa Day
//Jonathon Proctor
//Thunpisit Amnuaikiatloet
/**
 * Created by User on 6/21/2017.
 */
public class Team3Weather {

    private static String temperature;

    public Team3Weather() {
        temperature = new String("");
    }
    public static String getTemp(String coord1, String coord2) {
        try {
            JSONObject json = readJsonFromUrl("https://api.darksky.net/forecast/"
                    +"58402e9dc3a740efde7142594af11c6d/" + coord1 + "," + coord2);
            //Prints image to debugging console. Need to create a graphical representation.
            System.out.println(json.toString());
            System.out.println(json.getJSONObject("currently").getString("summary"));
            temperature = new String(Double.toString((json.getJSONObject("currently").getDouble("temperature"))));
        } catch(IOException ex) {
            System.exit(1);
        }
        return temperature;
    }
    //Taken from the ExampleWeather.java file
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
    //Taken from the ExampleWeather.java file
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
