package com.reemsib.shadowreader.fcm

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var broadcaster: LocalBroadcastManager? = null

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
      //  sendNotification(remoteMessage)
    }
    private fun sendNotification(remoteMessage: RemoteMessage) {
        //1
        val handler = Handler(Looper.getMainLooper())

        //2
        handler.post(Runnable {
            remoteMessage.notification?.let {
                val intent = Intent("MyData")
                intent.putExtra("message", remoteMessage.data["text"]);
                //   intent.putExtra("message", it.body.);
                //     remoteMessage.data["text"]
                //  intent.putExtra("message", remoteMessage.data["Negatives"]);
                broadcaster?.sendBroadcast(intent);
            }
            Toast.makeText(
                baseContext,
                "getString(R.string.handle_notification_now)",
                Toast.LENGTH_LONG
            ).show()


        }
        )


    }
}