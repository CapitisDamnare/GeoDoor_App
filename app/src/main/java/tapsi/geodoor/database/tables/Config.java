package tapsi.geodoor.database.tables;

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

    private float accuracy;

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

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    public Config(String name, String ipAddress, String latitude, String longitude, String altitude, int radius, float accuracy) {
        this.name = name;
        this.ipAddress = ipAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.radius = radius;
        this.accuracy = accuracy;
    }

    public Config()
    {

    }

    @Override
    public String toString() {
        return "Config{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", radius=" + radius +
                ", accuracy=" + accuracy +
                '}';
    }
}