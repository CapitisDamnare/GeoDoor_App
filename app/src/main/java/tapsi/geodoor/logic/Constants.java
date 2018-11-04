package tapsi.geodoor.logic;

public class Constants {
    public interface ACTION {
        public static String SOCKET_MAIN = "tapsi.geodoor.socket.main";
        public static String SOCKET_START = "tapsi.geodoor.socket.start";
        public static String SOCKET_STOP = "tapsi.geodoor.socket.stop";
        public static String GPS_MAIN = "tapsi.geodoor.gps.main";
        public static String GPS_START = "tapsi.geodoor.gps.start";
        public static String GPS_STOP = "tapsi.geodoor.gps.stop";
    }

    public interface NOTIFICATION_ID {
        public static int SOCKET_SERVICE_FOREGROUND = 101;
        public static int SOCKET_SERVICE_TEMP = 102;
    }

    public interface BROADCAST {
        public static String EVENT_TOMAIN = "tapsi.geodoor.toMain";
        public static String EVENT_TOSOCKET = "tapsi.geodoor.toSocket";
        public static String EVENT_TOGPS = "tapsi.geodoor.toGPS";

        public static String NAME_VALUEUPDATE = "tapsi.geodoor.valueUpdate";
        public static String NAME_TIMEUPDATE = "tapsi.geodoor.timeUpdate";
        public static String NAME_LOCATIONUPDATE = "tapsi.geodoor.locationUpdate";
        public static String NAME_OPENGATE = "tapsi.geodoor.openGate";
        public static String NAME_NOTYETALLOWED = "tapsi.geodoor.notYetAllowed";
        public static String NAME_ALLOWED = "tapsi.geodoor.allowed";
        public static String NAME_REGISTERED = "tapsi.geodoor.registered";
        public static String NAME_PING = "tapsi.geodoor.ping";
        public static String NAME_DOOR1OPEN = "tapsi.geodoor.door1Open";
        public static String NAME_DOOR1CLOSE = "tapsi.geodoor.door1Close";
        public static String NAME_SOCKETCONNECTED = "tapsi.geodoor.socketConnected";
        public static String NAME_SOCKETDISONNECTED = "tapsi.geodoor.docketDisconnected";
        public static String NAME_GPSCONNECTED = "tapsi.geodoor.gpsConnected";
        public static String NAME_GPSDISCONNECTED = "tapsi.geodoor.gpsDisconnected";
        public static String NAME_POSITIONLOCK = "tapsi.geodoor.positionLock";
    }

    public interface ACRA {
        public static String SHARED_PREFERENCES = "tapsi.geodoor.SHARED_PREFERENCES";
    }
}
