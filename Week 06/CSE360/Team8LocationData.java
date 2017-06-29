package CSE360;

/*
   Stores information about a particular city.
*/

public final class Team8LocationData {

    //Weather Data
    private String summary = "";
    private double windSpeed = 0.0;
    private double temperature = 0.0;
    private double humidity = 0.0;
    private double visibility = 0.0;
    private double cloudCover = 0.0;

    //Location Data
    private String cityName = "";
    private double latitude = 0.0;
    private double longitude = 0.0;

    public Team8LocationData() {}

    //Weather Accessors and Modifiers
    //--------------------
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public double getWindSpeed() { return windSpeed; }
    public void setWindSpeed(double windSpeed) { this.windSpeed = windSpeed; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public double getHumidity() { return humidity; }
    public void setHumidity(double humidity) { this.humidity = humidity; }

    public double getVisibility() { return visibility; }
    public void setVisibility(double visibility) { this.visibility = visibility; }

    public double getCloudCover() { return cloudCover; }
    public void setCloudCover(double cloudCover) { this.cloudCover = cloudCover; }
    //--------------------

    //Location Accessors and Modifiers
    //--------------------
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude;}
    //--------------------
}
