package luxurysky.clubmanager.util

import android.os.Build
import android.text.TextUtils

object BuildCompat {
    fun supportedAbis(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.SUPPORTED_ABIS.isNotEmpty()) {
            Build.SUPPORTED_ABIS
        } else if (!TextUtils.isEmpty(Build.CPU_ABI2)) {
            arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
        } else {
            arrayOf(Build.CPU_ABI)
        }
    }
}