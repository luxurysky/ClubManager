package luxurysky.clubmanager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import io.realm.Realm
import luxurysky.clubmanager.util.Log
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.model.Player

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

        realm.beginTransaction()
        clubs.deleteAllFromRealm()
        realm.commitTransaction()

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

            realm.beginTransaction()
            val club3 = realm.createObject(Club::class.java)
            club3.name = "토트넘"
            club3.emblemUrl = "https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F33%2F05%2F146_100303305_team_image_url_1435202894494.jpg"
            realm.commitTransaction()
        }

        Log.i(TAG, "club count : " + clubs.count())
    }

    private fun createDummyPlayers() {
        val realm = Realm.getDefaultInstance()
        val players = realm.where(Player::class.java).findAll()

        realm.beginTransaction()
        players.deleteAllFromRealm()
        realm.commitTransaction()

        Log.i(TAG, "player count : " + players.count())

        if (players.size == 0) {
            realm.beginTransaction()
            val player1 = realm.createObject(Player::class.java)
            player1.name = "Heung-Min Son"
            player1.position = "Forward"
            player1.squadNumber = 7
            player1.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/hm_son.jpg"
            realm.commitTransaction()

            realm.beginTransaction()
            val player2 = realm.createObject(Player::class.java)
            player2.name = "Christian Eriksen"
            player2.position = "Midfielder"
            player2.squadNumber = 23
            player2.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/c_eriksen.jpg"
            realm.commitTransaction()

            realm.beginTransaction()
            val player3 = realm.createObject(Player::class.java)
            player3.name = "Hugo Lloris"
            player3.position = "Goalkeeper"
            player3.squadNumber = 1
            player3.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/h_lloris.jpg"
            realm.commitTransaction()

            realm.beginTransaction()
            val player4 = realm.createObject(Player::class.java)
            player4.name = "Jan Vertonghen"
            player4.position = "Defender"
            player4.squadNumber = 5
            player4.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/j_vertonghen.jpg"
            realm.commitTransaction()

            realm.beginTransaction()
            val player5 = realm.createObject(Player::class.java)
            player5.name = "Dele Alli"
            player5.position = "Midfielder"
            player5.squadNumber = 20
            player5.photoUrl = "http://www.tottenhamhotspur.com/uploadedImages/Shared_Assets/Images/Player_Profiles/2017-18_First_Team/new_getty/d_alli.jpg"
            realm.commitTransaction()
        }

        Log.i(TAG, "player count : " + players.count())
    }

}