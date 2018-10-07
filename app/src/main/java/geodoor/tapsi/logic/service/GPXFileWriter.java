package geodoor.tapsi.logic.service;

import android.location.Location;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GPXFileWriter {

    private FileWriter fw;

    // Some constants
    private final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
    private final String TAG_GPX = "<gpx"
            + " xmlns=\"http://www.topografix.com/GPX/1/1\""
            + " version=\"1.1\""
            + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
            + " xsi:schemaLocation=\"http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd\">";
    private final SimpleDateFormat POINT_DATE_FORMATTER = new SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());

    public GPXFileWriter(File target) {
        try {
            fw = new FileWriter(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openGpxFile() {
        try {
            fw.write(XML_HEADER + "\n");
            fw.write(TAG_GPX + "\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeGPXFile() {
        try {
            fw.write("</gpx>");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTrackPoints(ActivityData activityData) {
        try {
            fw.write("\t" + "<trk>" + "\n");
            fw.write("\t\t" + "<name>" + POINT_DATE_FORMATTER.format(new Date(activityData.time)) + "</name>" + "\n");
            fw.write("\t\t" + "<trkseg>" + "\n");

            if (activityData.type == ActivityData.TYPE_TRACKPOINT) {
                StringBuffer out = new StringBuffer();
                out.append("\t\t\t" + "<trkpt lat=\""
                        + activityData.location.getLatitude() + "\" " + "lon=\""
                        + activityData.location.getLongitude() + "\">" + "\n");
                out.append("\t\t\t\t" + "<ele>" + activityData.location.getAltitude()
                        + "</ele>" + "\n");
                out.append("\t\t\t\t" + "<time>"
                        + POINT_DATE_FORMATTER.format(new Date(activityData.time))
                        + "</time>" + "\n");
                out.append("\t\t\t\t" + "<cmt>speed="
                        + activityData.location.getSpeed() + "</cmt>" + "\n");
                out.append("\t\t\t\t" + "<hdop>" + activityData.location.getAccuracy()
                        + "</hdop>" + "\n");
                out.append("\t\t\t" + "</trkpt>" + "\n");

                fw.write(out.toString());
            }

            fw.write("\t\t" + "</trkseg>" + "\n");
            fw.write("\t" + "</trk>" + "\n");
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ActivityData {
        public static final int TYPE_TRACKPOINT = 0;

        public long time;
        public int type = TYPE_TRACKPOINT;
        public Location location;

        public ActivityData(long time, Location location) {
            this.time = time;
            this.location = location;
        }
    }
}
