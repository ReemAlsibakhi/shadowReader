package com.reemsib.shadowreader.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.reemsib.shadowreader.R;
import com.reemsib.shadowreader.activity.MainActivity;
import com.reemsib.shadowreader.setting.PreferencesManager;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class MyFirebaseMessagingService0 extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    Intent pushNotification;
    private  PreferencesManager manager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        manager=new PreferencesManager(getApplicationContext());

       // Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "Msg received From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
         if (remoteMessage.getData().size() > 0) {
                 Log.e("Message_payload", remoteMessage.getData().toString());
             }
         //   showNotification("ShadowReader",remoteMessage.getData().get("moreData"), remoteMessage);

           //  PushNotification pushObj = gson.fromJson(remoteMessage.getData().get("moreData"),PushNotification.class);
//             JsonParser parser = new JsonParser();
//             JsonElement mJson = parser.parse(remoteMessage.getData().get("moreData").toString());
//             Gson gson = new Gson();
//             PushNotification pushObj = gson.fromJson(mJson, PushNotification.class);
//
              pushNotification = new Intent("notify");
//             pushNotification.putExtra("video_id",pushObj.getVideo_id());
//             pushNotification.putExtra("isShow","isShow");
              pushNotification.putExtra("flag","1");

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            //   String title=remoteMessage.getNotification().getTitle();
        //    String body=remoteMessage.getNotification().getBody();
              Log.e(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
              showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),remoteMessage);

        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
        Log.d("pushed", "ontify");

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void showNotification(String Title, String body , RemoteMessage remoteMessage) {

        Intent  intent = new Intent(this, MainActivity.class);
        intent.putExtra("flag","1");

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(body)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId("com.myApp");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "com.myApp",
                    "My App",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        assert notificationManager != null;
        notificationManager.notify(2, builder.build());
    }


    @Override
    public void onNewToken(@NotNull String token) {
        Log.e(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }

    private void sendRegistrationToServer(String token) {
       new PreferencesManager(getApplicationContext()).setFcmToken(token);
    }



}
