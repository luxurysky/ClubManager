package luxurysky.clubmanager.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_club.*
import luxurysky.clubmanager.R

class ClubActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CLUB_ID = "CLUB_ID"
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fab.hide()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val playerId = intent.getStringExtra(ClubActivity.EXTRA_CLUB_ID)
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
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, ClubScheduleFragment.newInstance("1", "2"))
                fragmentTransaction.commitAllowingStateLoss()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_players -> {
                fab.show()
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.content, PlayerListFragment.newInstance(2))
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
        navigation.selectedItemId = R.id.navigation_home
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_club, menu)
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
}
