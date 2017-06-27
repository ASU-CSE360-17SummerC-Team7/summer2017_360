/**
 * Created by User on 6/19/2017.
 */

package CSE360;
public class Team3City {
    private String name;
    private String latitude;
    private String longitude;
    public Team3City(String name, String latitude, String longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getLatitude() {
            return latitude;
        }
    public String getLongitude() {
            return longitude;
        }
    public String getName() {
            return name;
        }
}
