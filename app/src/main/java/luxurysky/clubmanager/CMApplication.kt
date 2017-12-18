package luxurysky.clubmanager

import android.app.Application
import io.realm.Realm
import luxurysky.clubmanager.common.Log

/**
 * Created by HWANGJIN on 2017-12-18.
 */
class CMApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
        Log.init(this)
    }
}