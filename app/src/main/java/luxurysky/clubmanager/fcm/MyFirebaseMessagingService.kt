package luxurysky.clubmanager.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import luxurysky.clubmanager.util.Log

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "payload : ${remoteMessage.data}")
        }
    }
}