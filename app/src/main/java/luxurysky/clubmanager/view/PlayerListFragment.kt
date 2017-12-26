package luxurysky.clubmanager.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Player
import luxurysky.clubmanager.view.playerdetail.PlayerDetailActivity

class PlayerListFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 2
//    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments!!.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            if (mColumnCount <= 1) {
                view.layoutManager = LinearLayoutManager(context)
            } else {
                view.layoutManager = GridLayoutManager(context, mColumnCount)
            }

            view.addItemDecoration(GridSpacingItemDecoration(2, 20))


            val realm = Realm.getDefaultInstance()
            val players = realm.where(Player::class.java).findAll()
            view.adapter = PlayerRecyclerViewAdapter(players, mListener)
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

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): PlayerListFragment {
            val fragment = PlayerListFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
