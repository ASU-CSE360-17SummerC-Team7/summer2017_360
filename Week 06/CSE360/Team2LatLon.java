/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CSE360;

import java.net.*;
import java.io.*;
import org.json.*;

public class Team2LatLon {
    String city;
    JSONObject obj;
    double lat;
    double lon;
    public Team2LatLon(String city) {
        setCity(city);
    }
    
    public void setCity(String city) {
        this.city = city;
        if ((this.city != null) && (this.city.length() > 0)) {
           this.city = this.city.replace(' ', '%');
           try {
           	//find the lat and lon by chosen city
               String mapURL = "https://maps.googleapis.com/maps/api/geocode/json?address=" + this.city;
               URL url = new URL(mapURL);
               URLConnection wc = url.openConnection();
               BufferedReader in = new BufferedReader(
               new InputStreamReader(
               wc.getInputStream()));
               String inputLine;
               String json = "";
               // Read response and add to string
               while ((inputLine = in.readLine()) != null) {
                   json = json + inputLine;
               }
               // Parse JSON
               obj = new JSONObject(json);
               in.close();
               JSONArray resultArr = obj.getJSONArray("results");
               lat = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
               lon = resultArr.getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng"); 
           } catch (IOException| JSONException i) {
               System.out.println(i);
               System.exit(1);
           }
       }
    }
    
    public double getLat() {
        return lat;
    }
    
    public double getLon() {
        return lon;    }

}
