package geodoor.tapsi.logic.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import geodoor.tapsi.MainActivity;
import geodoor.tapsi.geodoor_app.R;
import geodoor.tapsi.logic.Constants;

import static geodoor.tapsi.App.CHANNEL_ID;

public class MyService extends Service {

    private final IBinder binder = new MyBinder();
    private String TAG = "tapsi.service";

    @Override
    public void onCreate() {
        super.onCreate();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Constants.BROADCAST.EVENT_TOSOCKET));
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Service x");
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service 2");
        if (intent.getAction().equals(Constants.ACTION.SOCKET_START)) {

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

            //buildNotification();
            Log.d(TAG, "Service 2");
        }  else if (intent.getAction().equals(Constants.ACTION.SOCKET_STOP))
                stopForegroundService();
        return Service.START_NOT_STICKY;
    }

        public void stopForegroundService() {
            stopForeground(true);
            stopSelf();
        }

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

//    private void buildNotification() {
//        // Setup a notification
//        builder = new NotificationCompat.Builder(getApplication().getBaseContext(), "M_CH_ID");
//        builder.setAutoCancel(true);
//
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setWhen(System.currentTimeMillis());
//        builder.setContentTitle("Opened door automatically");
//        builder.setContentText("Click to launch App");
//        builder.setShowWhen(true);
//
//        Intent intent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        nm = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
//    }
}
