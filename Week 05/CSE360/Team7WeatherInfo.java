package CSE360;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pdreiter
 */
public class Team7WeatherInfo {
    private Timestamp curTime;
    private Date curDate;
    private JSONObject darksky;
    private double latitude;
    private double longitude;
    
    public Team7WeatherInfo(double latitude, double longitude){
        this.latitude = latitude; this.longitude = longitude;
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
    }
    public void UpdateGeoLocation(double latitude, double longitude){
        this.latitude = latitude; this.longitude = longitude;
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
    }
    public String getLongitude() { 
        return darksky.getString("longitude");
    }
    public String getLatitude() { 
        return darksky.getString("latitude");
    }
    public String getTimezone() { 
        return darksky.getString("timezone");
    }
    private boolean isValid_TimeType(String timeType){ 
        if (    timeType.matches("currently")||
                timeType.matches("minutely")||
                timeType.matches("hourly")||
                timeType.matches("daily")
            ) { return true; }
        else { return false; }
    }
    private boolean isValid_WeatherInfoKey(String wkey){ 
        if(     wkey.matches("time")|| 
                wkey.matches("summary")|| 
                wkey.matches("icon")|| 
                wkey.matches("nearestStormDistance")|| 
                wkey.matches("precipIntensity")|| 
                wkey.matches("precipIntensityError")|| 
                wkey.matches("precipProbability")||
                wkey.matches("precipType")||
                wkey.matches("temperature")||
                wkey.matches("apparentTemperature")||
                wkey.matches("dewPoint")||
                wkey.matches("humidity")||
                wkey.matches("windSpeed")||
                wkey.matches("windBearing")||
                wkey.matches("visibility")||
                wkey.matches("cloudCover")||
                wkey.matches("pressure")||
                wkey.matches("ozone")
            ) { return true; }
        else { return false; }
    }
    private boolean isWeatherInfoKey_String(String wkey){ 
        if(
                wkey.matches("summary")|| 
                wkey.matches("icon")
            ) { return true; }
        else { return false; }
    }
    public String getWeatherFieldString(String timeType, String wkey){
        if ( !isValid_TimeType(timeType) ) { return "ERROR: Invalid timeType (valid: currently, minutely, hourly, daily)"; }
        else if ( !isValid_WeatherInfoKey(wkey)) {  return "ERROR: Invalid weather key";  }
        else { 
            if(isWeatherInfoKey_String(wkey)) { 
              return darksky.getJSONObject(timeType).getString(wkey); 
            }
            else { 
                return String.valueOf(darksky.getJSONObject(timeType).getDouble(wkey));
            }
        }
    }
    
    private JSONObject getLatestWeatherJSON() {
        curDate = new Date();
        curTime = new Timestamp(curDate.getTime());
        updateDarkSKYJSONObject();
        return darksky;
    }
    private void updateDarkSKYJSONObject() { 
        String ourAPIKey = "fc32de3a545df155ae6e26a367e4259f";
        String darkSKYURL_ForecastRequest = "https://api.darksky.net/forecast/"+ourAPIKey+"/"+String.valueOf(this.latitude)+","+String.valueOf(this.longitude);
        //System.out.print(darkSKYURL_ForecastRequest);
        try { darksky = readJSONFromURL(darkSKYURL_ForecastRequest); }
        catch(IOException e) {} // ignoring exceptions for now
    }
    // reusing the provided StringBuilder method provided by Rao 
    //[originally in CSE360 L06 - recitation 02.pdf, Main.java example]
    private static String readAll(Reader rd) throws IOException{
        StringBuilder sb = new StringBuilder();
        int cp; 
        while((cp=rd.read())!=-1) { // while reader is not at end of buffer
            sb.append((char)cp); // append the current character to stringbuilder
           // System.out.print((char)cp);
        }
        return sb.toString(); //need actual string within buffer, returning that
    }
    //based on the readJsonFromURL method provided by Rao
    //[originally in CSE360 L06 - recitation 02.pdf, Main.java example]
    private static JSONObject readJSONFromURL(String url) throws IOException, JSONException { 
        try (InputStream is = new URL(url).openStream() // opens URL stream
        ) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is,Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            is.close();
            return json;
        } 
    } 


    private JSONObject getCurrently() {
        return darksky.getJSONObject("currently");
    }
    private JSONObject getMinutely() {
        return darksky.getJSONObject("minutely");
    }
    private JSONObject getHourly() {
        return darksky.getJSONObject("hourly");
    }
    private JSONObject getDaily() {
        return darksky.getJSONObject("daily");
    }
    private JSONObject getAlerts() {
        return darksky.getJSONObject("alerts");
    }
    private JSONObject getFlags() {
        return darksky.getJSONObject("flags");
    }

}