/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Kyle Sun
 */
public class Team2GetWeather {
    JSONObject jsonObj;
    
    public Team2GetWeather(String wKey , double lat, double lon) {
        try {
            setWeather(wKey , lat, lon);
        } catch (IOException | JSONException e) {
            System.out.println(e);
            System.exit(1);
        }
    }
    
    public void setWeather(String wKey , double lat, double lon) throws IOException, JSONException{
        String weaUrl = "https://api.darksky.net/forecast/" + wKey + "/" + lat + "," + lon;
        URL url = new URL(weaUrl);
        URLConnection wc = url.openConnection();
        BufferedReader in = new BufferedReader(
                            new InputStreamReader(wc.getInputStream()));
        String inputLine;
        String json = "";
        // Read response and add to string
        while ((inputLine = in.readLine()) != null) {
            json = json + inputLine;
        }
        in.close();
        jsonObj = new JSONObject(json);        
    }
    
    public String getCondition() {
        return jsonObj.getJSONObject("currently").getString("icon");
    }
    
    public double getDoubleInfo(String info) {
        return jsonObj.getJSONObject("currently").getDouble(info);       
    }
    
    public int getIntInfo(String info) {
        return jsonObj.getJSONObject("currently").getInt(info);       
    }
}
