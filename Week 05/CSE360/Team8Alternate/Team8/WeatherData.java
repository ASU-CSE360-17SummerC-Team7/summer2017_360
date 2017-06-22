package CSE360;

public final class WeatherData {

    private String summary;
    private double windSpeed;
    private double temperature;
    private double humidity;
    private double visibility;
    private double cloudCover;

    WeatherData(String summary, double windSpeed, double temperature, double humidity, double visibility, double cloudCover) {
        this.setSummary(summary);
        this.setWindSpeed(windSpeed);
        this.setTemperature(temperature);
        this.setHumidity(humidity);
        this.setVisibility(visibility);
        this.setCloudCover(cloudCover);
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(double cloudCover) {
        this.cloudCover = cloudCover;
    }
}
