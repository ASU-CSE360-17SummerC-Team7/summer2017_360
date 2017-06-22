package CSE360;

public class Team8CityDetail {
	private String cityName;
	private double latitude;
	private double longitude;

	public Team8CityDetail(String cityName, double latitude, double longitude) {
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCityName() {
		return cityName;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public String toString() {
		return cityName;
	}

}
