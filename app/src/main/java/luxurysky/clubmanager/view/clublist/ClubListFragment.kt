package luxurysky.clubmanager.view.clublist

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_club_list.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.ClubRecyclerViewAdapter

class ClubListFragment : Fragment() {
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        if (newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            ((view as RecyclerView).layoutManager as GridLayoutManager).spanCount = 3

        } else if (newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ((view as RecyclerView).layoutManager as GridLayoutManager).spanCount = 2
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club_list, container, false)

        val context = view.context
        view.list.layoutManager = GridLayoutManager(context, 2)

        val realm = Realm.getDefaultInstance()
        val clubs = realm.where(Club::class.java).findAll()
        view.list.adapter = ClubRecyclerViewAdapter(clubs, mListener)

        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Club)
    }

    companion object {
        fun newInstance(): ClubListFragment {
            return ClubListFragment()
        }
    }
}
