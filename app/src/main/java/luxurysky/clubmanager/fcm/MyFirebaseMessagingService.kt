package luxurysky.clubmanager.fcm

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import luxurysky.clubmanager.R
import luxurysky.clubmanager.util.Log
import luxurysky.clubmanager.view.clublist.ClubListActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "[onMessageReceived] notification title : ${remoteMessage.notification?.title}, body : ${remoteMessage.notification?.body}, data : ${remoteMessage.data}")
        sendNotification(remoteMessage.notification?.title ?: getString(R.string.app_name), remoteMessage.notification?.body ?: "")
    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, ClubListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_wc_black_24dp)
                .setColor(getColor(R.color.colorPrimaryDark))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}