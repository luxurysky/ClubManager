package luxurysky.clubmanager.view.clublist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_club_list.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.AutoFitGridLayoutManager

class ClubListFragment : Fragment() {

    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club_list, container, false)

        view.list.layoutManager = AutoFitGridLayoutManager(view.context, 400)

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
        fun onClickClub(item: Club)
    }
}
