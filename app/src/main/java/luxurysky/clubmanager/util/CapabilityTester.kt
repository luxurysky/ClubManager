package luxurysky.clubmanager.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore

object CapabilityTester {

    private fun isIntentRegistered(context: Context, intent: Intent): Boolean {
        val packageManager = context.packageManager
        val receiverList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        return receiverList.isNotEmpty()
    }

    fun isCameraIntentRegistered(context: Context): Boolean {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        return isIntentRegistered(context, intent)
    }
}