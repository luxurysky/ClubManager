package luxurysky.clubmanager.util.compatibility

import android.annotation.TargetApi
import android.os.Build

@TargetApi(8)
class API8Compatibility : APICompatibility() {
    override fun supportedAbis(): Array<String> = arrayOf(Build.CPU_ABI, Build.CPU_ABI2)
}