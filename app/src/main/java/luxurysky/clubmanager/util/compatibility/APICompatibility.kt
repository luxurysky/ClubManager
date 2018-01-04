package luxurysky.clubmanager.util.compatibility

import android.os.Build

open class APICompatibility {
    companion object {
        val instance: APICompatibility by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) API21Compatibility() else APICompatibility()
    }

    open fun supportedAbis(): Array<String> = arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
}