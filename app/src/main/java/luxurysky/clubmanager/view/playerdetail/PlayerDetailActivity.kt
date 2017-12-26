package luxurysky.clubmanager.view.playerdetail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_player_detail.*
import luxurysky.clubmanager.R

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailContract.View {

    override lateinit var mPresenter: PlayerDetailContract.Presenter
    private lateinit var mRealm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
        }

        val playerId = intent.getStringExtra(EXTRA_PLAYER_ID)
        mRealm = Realm.getDefaultInstance()
        PlayerDetailPresenter(playerId, mRealm, this)
    }

    override fun onResume() {
        super.onResume()

        mPresenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.GONE
        }
    }

    override fun showMissingPlayer() {
    }

    override fun showTitle(title: String) {
        playerName.text = title
    }

    override fun showTaskDeleted() {
    }

    companion object {
        const val EXTRA_PLAYER_ID = "PLAYER_ID"
    }
}
