package luxurysky.clubmanager.view.club

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_club.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.view.clubinfo.ClubInfoFragment
import luxurysky.clubmanager.view.clubschedule.ClubScheduleFragment
import luxurysky.clubmanager.view.playerlist.PlayerListFragment

class ClubActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CLUB_ID = "CLUB_ID"
        const val KEY_SELECTED_ITEM_ID = "selected_item_id"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fab.hide()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val playerId = intent.getStringExtra(EXTRA_CLUB_ID)
                fragmentTransaction.replace(R.id.content, ClubInfoFragment.newInstance(playerId))
                fragmentTransaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fab.hide()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fab.hide()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_schedule -> {
                fab.hide()
                val clubId = intent.getStringExtra(EXTRA_CLUB_ID)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, ClubScheduleFragment.newInstance(clubId))
                fragmentTransaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_players -> {
                fab.show()
                val clubId = intent.getStringExtra(EXTRA_CLUB_ID)
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, PlayerListFragment.newInstance(clubId))
                fragmentTransaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true

            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = savedInstanceState?.getInt(KEY_SELECTED_ITEM_ID) ?: R.id.navigation_home
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt(KEY_SELECTED_ITEM_ID, navigation.selectedItemId)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}