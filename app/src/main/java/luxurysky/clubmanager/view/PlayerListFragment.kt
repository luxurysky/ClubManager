package luxurysky.clubmanager.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.RealmList
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.model.Player
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
            view.layoutManager = GridLayoutManager(context, 2)

            view.addItemDecoration(GridSpacingItemDecoration(2, 20))


            val realm = Realm.getDefaultInstance()
            val club = realm.where(Club::class.java).equalTo(Club.FIELD_ID, mClubId).findFirst()
            val players = club?.players
            view.adapter = PlayerRecyclerViewAdapter(players ?: RealmList(), mListener)
        }
        return view
    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnListFragmentInteractionListener) {
//            mListener = context
//        } else {
////            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
//        }
//    }


//    override fun onDetach() {
//        super.onDetach()
////        mListener = null
//    }

    private val mListener = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Player) {
//            Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
            val intent = Intent(context, PlayerDetailActivity::class.java)
            intent.putExtra(PlayerDetailActivity.EXTRA_PLAYER_ID, item.id)
            startActivity(intent)

        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Player)
    }

    companion object {

        private val ARG_CLUB_ID = "club_id"

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
