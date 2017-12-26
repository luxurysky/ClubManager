package luxurysky.clubmanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.realm.Realm
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.model.Player
import luxurysky.clubmanager.util.Log

/**
 * Created by HWANGJIN on 2017-12-18.
 */
class CMApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        private val TAG = CMApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        registerActivityLifecycleCallbacks(this)

        Log.init(this)
        Realm.init(this)

        createDummyClubs()
        createDummyPlayers()
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        Log.i(TAG, "[onActivityCreated] $activity")
    }

    override fun onActivityStarted(activity: Activity?) {
        Log.i(TAG, "[onActivityStarted] $activity")
    }

    override fun onActivityResumed(activity: Activity?) {
        Log.i(TAG, "[onActivityResumed] $activity")
    }

    override fun onActivityPaused(activity: Activity?) {
        Log.i(TAG, "[onActivityPaused] $activity")
    }

    override fun onActivityStopped(activity: Activity?) {
        Log.i(TAG, "[onActivityStopped] $activity")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, oustState: Bundle?) {
        Log.i(TAG, "[onActivitySaveInstanceState] $activity")
    }

    override fun onActivityDestroyed(activity: Activity?) {
        Log.i(TAG, "[onActivityDestroyed] $activity")
    }

    private fun createDummyClubs() {
        val realm = Realm.getDefaultInstance()
        val clubs = realm.where(Club::class.java).findAll()
        Log.i(TAG, "club count : " + clubs.count())

        realm.executeTransaction {
            clubs.deleteAllFromRealm()
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "FC 바르셀로나"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F09%2F146_100308409_team_image_url_1435388480471.jpg"
            club.playersUrl = "https://media-public.fcbarcelona.com/20157/0/document_thumbnail/20197/220/138/127/58690268/1.0-11/58690268.jpg"
            realm.insert(club)
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "레알 마드리드"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F21%2F146_100308421_team_image_url_1435302794719.jpg"
            club.playersUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fpost.phinf.naver.net%2F20150112_80%2Fyaspeed_1421042563655sC1go_PNG%2Fmug_obj_142104257864290575.jpg"
            realm.insert(club)
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "토트넘"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F33%2F05%2F146_100303305_team_image_url_1435202894494.jpg"
            club.playersUrl = "http://post.phinf.naver.net/MjAxNjEwMTlfODgg/MDAxNDc2ODYzMzYzOTQ4.6O9fJitxvYQ12rAGdnuyCIDC_wGrEjlwMJpnSnUMEhgg.TWQsQbImvknbEQP_P3ecFBh_tLYb9MeQPL0wQCvgtaEg.PNG/mug_obj_201610191649241635.jpg"
            realm.insert(club)
        }

        Log.i(TAG, "club count : " + clubs.count())
    }

    private fun createDummyPlayers() {
        val realm = Realm.getDefaultInstance()
        val players = realm.where(Player::class.java).findAll()

        realm.executeTransaction {
            players.deleteAllFromRealm()
        }

        Log.i(TAG, "player count : " + players.count())

        realm.executeTransaction {
            val player = Player()
            player.name = "Heung-Min Son"
            player.position = "Forward"
            player.squadNumber = 7
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/hm_son.jpg"
            realm.insert(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Christian Eriksenabcdefghijk"
            player.position = "Midfielder"
            player.squadNumber = 23
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/c_eriksen.jpg"
            realm.insert(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Hugo Lloris"
            player.position = "Goalkeeper"
            player.squadNumber = 1
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/h_lloris.jpg"
            realm.insert(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Jan Vertonghen"
            player.position = "Defender"
            player.squadNumber = 5
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/j_vertonghen.jpg"
            realm.insert(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Dele Alli"
            player.position = "Midfielder"
            player.squadNumber = 20
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/d_alli.jpg"
            realm.insert(player)
        }

        Log.i(TAG, "player count : " + players.count())
    }

}