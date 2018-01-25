package luxurysky.clubmanager.view.playerdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_player_detail.*
import luxurysky.clubmanager.R

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        val playerId = intent.getStringExtra(EXTRA_PLAYER_ID)
        mRealm = Realm.getDefaultInstance()

        val playerDetailFragment = supportFragmentManager.findFragmentById(R.id.fragment) as PlayerDetailFragment
        PlayerDetailPresenter(playerId, mRealm, playerDetailFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    companion object {
        const val EXTRA_PLAYER_ID = "PLAYER_ID"
    }
}
