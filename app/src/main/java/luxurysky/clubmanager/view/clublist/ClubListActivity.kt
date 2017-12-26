package luxurysky.clubmanager.view.clublist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.ClubActivity
import luxurysky.clubmanager.view.ClubCreateActivity

class ClubListActivity : AppCompatActivity(), ClubListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(Intent(this, ClubCreateActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == R.id.action_search) {
            return true
        } else if (id == R.id.action_more) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onListFragmentInteraction(item: Club) {
        val intent = Intent(this@ClubListActivity, ClubActivity::class.java)
        intent.putExtra(ClubActivity.EXTRA_CLUB_ID, item.id)
        startActivity(intent)
    }
}
