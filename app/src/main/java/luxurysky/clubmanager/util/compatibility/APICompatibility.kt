package luxurysky.clubmanager.util.compatibility

import android.os.Build

abstract class APICompatibility {
    companion object {
        val instance: APICompatibility by lazy { Holder.INSTANCE }
    }

    private object Holder {
        val INSTANCE = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            API21Compatibility()
        } else {
            API8Compatibility()
        }
    }

    abstract fun supportedAbis(): Array<String>
}