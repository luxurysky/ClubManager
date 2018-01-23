package luxurysky.clubmanager.util.compatibility

import android.annotation.TargetApi
import android.os.Build

@TargetApi(21)
class API21Compatibility : APICompatibility() {
    override fun supportedAbis(): Array<String> = Build.SUPPORTED_ABIS
}