package luxurysky.clubmanager.view.playerlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmList
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.model.Player
import luxurysky.clubmanager.view.AutoFitGridLayoutManager
import luxurysky.clubmanager.view.club.ClubActivity
import luxurysky.clubmanager.view.playerdetail.PlayerDetailActivity

class PlayerListFragment : Fragment() {
    // TODO: Customize parameters
    private var mClubId = ""
//    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mClubId = arguments!!.getString(ARG_CLUB_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = AutoFitGridLayoutManager(context, resources.getDimensionPixelSize(R.dimen.default_auto_fit_grid_width))

            val realm = Realm.getDefaultInstance()
            val club = realm.where(Club::class.java).equalTo(Club.FIELD_ID, mClubId).findFirst()
            val players = club?.players
            view.adapter = PlayerRecyclerViewAdapter(players ?: RealmList(), mListener)

        }
        return view
    }

    private val mListener = object : OnListFragmentInteractionListener {
        override fun onClickPlayer(item: Player, photoView: View) {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity as ClubActivity, photoView, ViewCompat.getTransitionName(photoView))
            val intent = Intent(context, PlayerDetailActivity::class.java)
            intent.putExtra(PlayerDetailActivity.EXTRA_PLAYER_ID, item.id)
            startActivity(intent, options.toBundle())
        }
    }

    interface OnListFragmentInteractionListener {
        fun onClickPlayer(item: Player, photoView: View)
    }

    companion object {

        private const val ARG_CLUB_ID = "club_id"

        // TODO: Customize parameter initialization
        fun newInstance(clubId: String): PlayerListFragment {
            val fragment = PlayerListFragment()
            val args = Bundle()
            args.putString(ARG_CLUB_ID, clubId)
            fragment.arguments = args
            return fragment
        }
    }
}
