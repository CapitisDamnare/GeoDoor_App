package geodoor.tapsi.logic;

public class Constants {
    public interface ACTION {
        public static String SOCKET_MAIN = "geodoor.tapsi.socket.main";
        public static String SOCKET_START = "geodoor.tapsi.socket.start";
        public static String SOCKET_STOP = "geodoor.tapsi.socket.stop";
        public static String GPS_MAIN = "geodoor.tapsi.gps.main";
        public static String GPS_START = "geodoor.tapsi.gps.start";
        public static String GPS_STOP = "geodoor.tapsi.gps.stop";
    }

    public interface NOTIFICATION_ID {
        public static int SOCKET_SERVICE_FOREGROUND = 101;
        public static int SOCKET_SERVICE_TEMP = 102;
    }

    public interface BROADCAST {
        public static String EVENT_TOMAIN = "geodoor.tapsi.toMain";
        public static String EVENT_TOSOCKET = "geodoor.tapsi.toSocket";
        public static String EVENT_TOGPS = "geodoor.tapsi.toGPS";

        public static String NAME_VALUEUPDATE = "geodoor.tapsi.valueUpdate";
        public static String NAME_TIMEUPDATE = "geodoor.tapsi.timeUpdate";
        public static String NAME_LOCATIONUPDATE = "geodoor.tapsi.locationUpdate";
        public static String NAME_OPENGATE = "geodoor.tapsi.openGate";
        public static String NAME_NOTYETALLOWED = "geodoor.tapsi.notYetAllowed";
        public static String NAME_ALLOWED = "geodoor.tapsi.allowed";
        public static String NAME_REGISTERED = "geodoor.tapsi.registered";
        public static String NAME_PING = "geodoor.tapsi.ping";
        public static String NAME_DOOR1OPEN = "geodoor.tapsi.door1Open";
        public static String NAME_DOOR1CLOSE = "geodoor.tapsi.door1Close";
        public static String NAME_SOCKETCONNECTED = "geodoor.tapsi.socketConnected";
        public static String NAME_SOCKETDISONNECTED = "geodoor.tapsi.docketDisconnected";
        public static String NAME_GPSCONNECTED = "geodoor.tapsi.gpsConnected";
        public static String NAME_GPSDISCONNECTED = "geodoor.tapsi.gpsDisconnected";
        public static String NAME_POSITIONLOCK = "geodoor.tapsi.positionLock";
    }

    public interface ACRA {
        public static String SHARED_PREFERENCES = "geodoor.tapsi.SHARED_PREFERENCES";
    }
}
