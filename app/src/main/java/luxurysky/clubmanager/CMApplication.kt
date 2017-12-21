package luxurysky.clubmanager

import android.app.Application
import io.realm.Realm
import luxurysky.clubmanager.common.Log
import luxurysky.clubmanager.model.Club

/**
 * Created by HWANGJIN on 2017-12-18.
 */
class CMApplication : Application() {
    companion object {
        private val TAG = CMApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        Log.init(this)
        Realm.init(this)

        createDummy()
    }

    private fun createDummy() {
        val realm = Realm.getDefaultInstance()
        val clubs = realm.where(Club::class.java).findAll()
        Log.i(TAG, "club count : " + clubs.count())

        if (clubs.size == 0) {

            realm.beginTransaction()
            val club1 = realm.createObject(Club::class.java)
            club1.name = "FC 바르셀로나"
            club1.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F09%2F146_100308409_team_image_url_1435388480471.jpg"
            realm.commitTransaction()

            realm.beginTransaction()
            val club2 = realm.createObject(Club::class.java)
            club2.name = "레알 마드리드"
            club2.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F21%2F146_100308421_team_image_url_1435302794719.jpg"
            realm.commitTransaction()
        }

        if (clubs.size == 2) {
            realm.beginTransaction()
            val club3 = realm.createObject(Club::class.java)
            club3.name = "토트넘"
            club3.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F33%2F05%2F146_100303305_team_image_url_1435202894494.jpg"
            realm.commitTransaction()
        }

        Log.i(TAG, "club count : " + clubs.count())
    }

}