package luxurysky.clubmanager

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import io.realm.Realm
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.model.Player
import luxurysky.clubmanager.util.Log
import luxurysky.clubmanager.util.compatibility.APICompatibility
import java.util.*


class CMApplication : Application(), Application.ActivityLifecycleCallbacks {
    companion object {
        private val TAG = CMApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        Log.init(this)
        Realm.init(this)

        Log.d(TAG, "[onCreate] System Information model : ${Build.MODEL}, sdk : ${Build.VERSION.SDK_INT}, release : ${Build.VERSION.RELEASE}, abis : ${Arrays.toString(APICompatibility.instance.supportedAbis())}")

        registerActivityLifecycleCallbacks(this)

        createDummyClubs()
        createDummyPlayers()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW))
        }

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

        realm.executeTransaction {
            clubs.deleteAllFromRealm()
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "FC 바르셀로나"
            club.mainStadium = "캄푸 누"
            club.subStadium = "강서 개화 축구장"
            club.dues = "분기당 3만원"
            club.matchTime = "토요일 오후 18시"
            club.ageGroup = "20대 ~ 30대"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F09%2F146_100308409_team_image_url_1435388480471.jpg"
            club.playersUrl = "https://media-public.fcbarcelona.com/20157/0/document_thumbnail/20197/220/138/127/58690268/1.0-11/58690268.jpg"
            realm.insert(club)
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "레알 마드리드"
            club.mainStadium = "산티아고 베르나베우"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F21%2F146_100308421_team_image_url_1435302794719.jpg"
            club.playersUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fpost.phinf.naver.net%2F20150112_80%2Fyaspeed_1421042563655sC1go_PNG%2Fmug_obj_142104257864290575.jpg"
            realm.insert(club)
        }

        realm.executeTransaction {
            val club = Club()
            club.name = "토트넘"
            club.mainStadium = "웸블리 스타디움"
            club.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F33%2F05%2F146_100303305_team_image_url_1435202894494.jpg"
            club.playersUrl = "http://post.phinf.naver.net/MjAxNjEwMTlfODgg/MDAxNDc2ODYzMzYzOTQ4.6O9fJitxvYQ12rAGdnuyCIDC_wGrEjlwMJpnSnUMEhgg.TWQsQbImvknbEQP_P3ecFBh_tLYb9MeQPL0wQCvgtaEg.PNG/mug_obj_201610191649241635.jpg"
            realm.insert(club)
        }

        for (c in clubs) {
            Log.d(TAG, "1club insert :  $c")
        }
    }

    private fun createDummyPlayers() {
        val realm = Realm.getDefaultInstance()
        val tottenhamClub = realm.where(Club::class.java).equalTo(Club.FIELD_NAME, "토트넘").findFirst()
        val realClub = realm.where(Club::class.java).equalTo(Club.FIELD_NAME, "레알 마드리드").findFirst()
        val fc = realm.where(Club::class.java).equalTo(Club.FIELD_NAME, "FC 바르셀로나").findFirst()

        val players = realm.where(Player::class.java).findAll()

        realm.executeTransaction {
            players.deleteAllFromRealm()
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Heung-Min Son"
            player.position = "Forward"
            player.squadNumber = 7
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/hm_son.jpg"
            tottenhamClub?.players?.add(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Christian Eriksen"
            player.position = "Midfielder"
            player.squadNumber = 23
            player.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/c_eriksen.jpg"
            tottenhamClub?.players?.add(player)
        }

        realm.executeTransaction {
            val player = Player()
            player.name = "Cristiano Ronaldo"
            player.position = "Forward"
            player.squadNumber = 7
            player.photoUrl = "https://search.pstatic.net/common?type=a&size=120x150&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fpeople%2F12%2F201006081604205021.jpg"
            realClub?.players?.add(player)
        }
        realm.executeTransaction {
            val player = Player()
            player.name = "Lionel Messi"
            player.position = "Forward"
            player.squadNumber = 10
            player.photoUrl = "https://media-public.fcbarcelona.com/20157/0/document_thumbnail/20197/174/102/254/50226862/1.0-1/50226862.jpg"
            fc?.players?.add(player)
        }

        val clubs = realm.where(Club::class.java).findAll()
        for (c in clubs) Log.d(TAG, "2club insert :  $c")

        for (p in players) {
            Log.d(TAG, "2player insert :  $p")
        }
    }

}