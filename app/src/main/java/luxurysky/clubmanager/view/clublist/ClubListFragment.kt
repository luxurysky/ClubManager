package luxurysky.clubmanager.view.clublist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_club_list.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.AutoFitGridLayoutManager
import luxurysky.clubmanager.view.GridSpacingItemDecoration

class ClubListFragment : Fragment() {

    companion object {
        private val TAG = ClubListFragment::class.java.simpleName
    }

    private var mListener: OnListFragmentInteractionListener? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDecoration: GridSpacingItemDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club_list, container, false)

        mRecyclerView = view.list
        val layoutManager = AutoFitGridLayoutManager(view.context, resources.getDimensionPixelSize(R.dimen.default_auto_fit_grid_width))
        mDecoration = GridSpacingItemDecoration(1, resources.getDimensionPixelSize(R.dimen.default_grid_spacing))
        mRecyclerView.addItemDecoration(mDecoration)

        layoutManager.setOnSpanCountUpdateListener(object : AutoFitGridLayoutManager.OnSpanCountUpdateListener {
            override fun onSpanCountUpdate(spanCount: Int) {
                Log.d(TAG, "onSpanCountUpdate: $spanCount")
                mDecoration.spanCount = spanCount
                mRecyclerView.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                    override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                        mRecyclerView.removeOnLayoutChangeListener(this)
                        mRecyclerView.removeItemDecoration(mDecoration)
                        mRecyclerView.addItemDecoration(mDecoration)
                    }
                })
            }
        })
        mRecyclerView.layoutManager = layoutManager

        val realm = Realm.getDefaultInstance()
        val clubs = realm.where(Club::class.java).findAll()
        mRecyclerView.adapter = ClubRecyclerViewAdapter(clubs, mListener)

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
        fun onClickClub(item: Club, view: View)
    }
}
