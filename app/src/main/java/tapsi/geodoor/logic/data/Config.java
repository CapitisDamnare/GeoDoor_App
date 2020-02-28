package tapsi.geodoor.logic.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "config_table")
public class Config {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String ipAddress;

    private String latitude;

    private String longitude;

    private String altitude;

    private int radius;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public int getRadius() {
        return radius;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Config(String name, String ipAddress, String latitude, String longitude, String altitude, int radius) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.radius = radius;
    }

    public Config()
    {

    }
}
