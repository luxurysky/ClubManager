package luxurysky.clubmanager.fcm

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import luxurysky.clubmanager.util.Log


class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {
    companion object {
        private val TAG = MyFirebaseInstanceIDService::class.java.simpleName
    }

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken)
    }
}