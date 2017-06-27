
package CSE360;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import org.json.JSONException;
import org.json.JSONObject;


public class WeatherData {
    
    
    //private String latlong = "33.5722,-112.0879";
    private double temp;
    private String tempString;
    

    public WeatherData(){
       
    }
    
    public String getWeatherData(String coordinates){
        
        try{
            String latlong = coordinates;
            String yourKey = "2ebb44a45e67c7f1";
            JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/" + yourKey + 
                "/conditions/q/" + latlong + ".json");        
            DecimalFormat oneDigit = new DecimalFormat("#,##0.0");  //format to 1 decimal place    

            Object curr_obs = json.get("current_observation");      // JSON object from which all our weather data comes from

            temp = json.getJSONObject("current_observation").getDouble("temp_f");
            tempString = String.valueOf(temp) + "°F";

        }
    catch(IOException e){
	    System.out.println("IOException !!");
		//return null;
	}
	catch(JSONException e){
	    System.out.println("JSONException !!");
		//return null;
	}
        return tempString;
    }
    
    



private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
}



  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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
