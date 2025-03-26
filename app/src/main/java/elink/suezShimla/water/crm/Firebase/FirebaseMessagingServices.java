package elink.suezShimla.water.crm.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


import elink.suezShimla.water.crm.R;

import static com.google.firebase.messaging.RemoteMessage.PRIORITY_HIGH;

public class FirebaseMessagingServices extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        String token = s;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        try {
            sendNotification(remoteMessage);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("fcmmmmmmmmm", e.getMessage());
        }
        // createNotificationChannel();
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        try {
            final String channelID = "my_channel_01";
            Map<String, String> dataMap = remoteMessage.getData();
            int a = dataMap.size();

            String title1 = dataMap.get("title");
            String message2 = dataMap.get("body");
            String message = remoteMessage.getNotification().getBody();
            String title = remoteMessage.getNotification().getTitle();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                channel = new NotificationChannel(channelID, "SOUND", NotificationManager.IMPORTANCE_DEFAULT);
                mNotificationManager.createNotificationChannel(channel);
            }

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channelID)
                    .setSmallIcon(R.drawable.mjp) // notification icon
                    .setContentTitle(title1) // title for notification
                    .setContentText(message2) // message for notification
                    .setAutoCancel(true)
                    .setPriority(PRIORITY_HIGH);// clear notification after click

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mBuilder.setSmallIcon(R.drawable.mjp);
                    mBuilder.setColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    mBuilder.setSmallIcon(R.drawable.mjp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

          /*  Intent intent = new Intent(this, ActivityNotification.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
            mBuilder.setContentIntent(pi);*/

            mNotificationManager.notify(0, mBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
