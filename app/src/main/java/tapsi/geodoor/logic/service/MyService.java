package tapsi.geodoor.logic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import tapsi.geodoor.MainActivity;
import tapsi.geodoor.geodoor_app.R;
import tapsi.geodoor.logic.Constants;

import static tapsi.geodoor.App.CHANNEL_ID;
import static tapsi.geodoor.App.NOTIFY_ID;

//TODO: Clean Code
public class MyService extends Service {

    private final IBinder binder = new MyBinder();
    private String TAG = "tapsi.service";
    private NotificationManager nm;
    NotificationCompat.Builder builder;

    //GPSService GPSService;
    //boolean isGPSServiceBound = false;

    @Override
    public void onCreate() {
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constants.BROADCAST.EVENT_TOSOCKET));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.d(TAG, "Open Gate");
            //nm.notify(Constants.NOTIFICATION_ID.SOCKET_SERVICE_TEMP, builder.build());
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.SOCKET_START)) {

//            Intent startGPSIntent = new Intent(this, GPSService.class);
//            startGPSIntent.setAction(Constants.ACTION.GPS_START);
//            startService(startGPSIntent);
//            bindService(startGPSIntent, myServiceConnection, Context.BIND_AUTO_CREATE);

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.SOCKET_MAIN);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            Intent stopIntent = new Intent(this, MyService.class);
            stopIntent.setAction(Constants.ACTION.SOCKET_STOP);
            PendingIntent sstopIntent = PendingIntent.getService(this, 0,
                    stopIntent, 0);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            Notification notification = notificationBuilder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("GeoDoor GPS & Socket Service activated")
                    .setContentText("Click to launch App")
                    .setTicker("GeoDoor")
                    .addAction(android.R.drawable.ic_media_next, "Stop Service", sstopIntent)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setShowWhen(false)
                    .setOngoing(true).build();

            startForeground(Constants.NOTIFICATION_ID.SOCKET_SERVICE_FOREGROUND, notification);

            buildNotification();
        } else if (intent.getAction().equals(Constants.ACTION.SOCKET_STOP))
            stopForegroundService();
        return Service.START_NOT_STICKY;
    }

    public void stopForegroundService() {
        Intent stopGPSIntent = new Intent(MyService.this, GPSService.class);
        stopGPSIntent.setAction(Constants.ACTION.GPS_STOP);
        startService(stopGPSIntent);
//        if (isGPSServiceBound)
//            unbindService(myServiceConnection);
//        else
//            Log.i(TAG, "GPSService wasn't bound");

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        stopForeground(true);
        stopSelf();
    }

    //GPS Service
//    private ServiceConnection myServiceConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            GPSService.MyLocalBinder binder = (GPSService.MyLocalBinder) service;
//            GPSService = binder.getService();
//            isGPSServiceBound = true;
//            //sendOutBroadcast(Constants.BROADCAST.EVENT_TOMAIN, Constants.BROADCAST.NAME_GPSCONNECTED, "true");
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            //sendOutBroadcast(Constants.BROADCAST.EVENT_TOMAIN, Constants.BROADCAST.NAME_GPSDISCONNECTED, "true");
//            isGPSServiceBound = false;
//            GPSService = null;
//        }
//    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    private void buildNotification() {
        // Setup a notification
        builder = new NotificationCompat.Builder(this, NOTIFY_ID);
        builder = new NotificationCompat.Builder(getApplication().getBaseContext(), "M_CH_ID");
        builder.setAutoCancel(true);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("Opened door automatically");
        builder.setContentText("Click to launch App");
        builder.setShowWhen(true);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        nm = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
    }
}
