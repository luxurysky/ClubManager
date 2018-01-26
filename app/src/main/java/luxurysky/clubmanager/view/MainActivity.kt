package luxurysky.clubmanager.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.club.ClubActivity
import luxurysky.clubmanager.view.clubedit.ClubEditActivity
import luxurysky.clubmanager.view.clublist.ClubListFragment

class MainActivity : AppCompatActivity(), ClubListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)

        fab.setOnClickListener {
            startActivity(Intent(this, ClubEditActivity::class.java))
        }
    }

    override fun onClickClub(item: Club, view: View) {
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, view, ViewCompat.getTransitionName(view))
        val intent = Intent(this@MainActivity, ClubActivity::class.java)
        intent.putExtra(ClubActivity.EXTRA_CLUB_ID, item.id)
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }
}
