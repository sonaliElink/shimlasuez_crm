package elink.suezShimla.water.crm.ConnectionManagement.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;


import elink.suezShimla.water.crm.ConnectionManagement.activity.SiteVisitListVerficationActivity;
import elink.suezShimla.water.crm.R;

public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context,intent);
    }

    private void sendNotification(Context context,Intent receiverIntent) {
        try {
            final String channelID = "my_channel_01";
           /* Uri soundUri = Uri.parse("android.resource://" + context.getApplicationContext().getPackageName() + "/" + R.raw.swiftly);
            MediaPlayer ring= MediaPlayer.create(context,soundUri);
            ring.setLooping(false);
            ring.start();*/

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID)
                    .setSmallIcon(R.drawable.ic_appointment) // notification icon
                    .setContentTitle("title1") // title for notification
                    .setContentText("message2") // message for notification
                    .setAutoCancel(true)
                    .setVibrate(new long[] { 1000, 1000})
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setPriority(1);// clear notification after click

            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Creating Channel
                    NotificationChannel notificationChannel = new NotificationChannel(channelID,"Testing_Audio",NotificationManager.IMPORTANCE_HIGH);
                    mNotificationManager.createNotificationChannel(notificationChannel);
            }

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setSmallIcon(R.drawable.ic_appointment);
                    mBuilder.setColor(context.getResources().getColor(R.color.colorPrimary));
                } else {
                    mBuilder.setSmallIcon(R.drawable.ic_appointment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(context, SiteVisitListVerficationActivity.class);
            receiverIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            mBuilder.setContentIntent(pi);

            mNotificationManager.notify(0, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}