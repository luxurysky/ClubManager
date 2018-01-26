package luxurysky.clubmanager.view.clublist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import io.realm.Case
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

    private lateinit var mRealm: Realm
    private var mListener: OnListFragmentInteractionListener? = null
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mDecoration: GridSpacingItemDecoration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
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


        val clubs = mRealm.where(Club::class.java).findAll()
        mRecyclerView.adapter = ClubRecyclerViewAdapter(clubs, mListener)

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mRealm = Realm.getDefaultInstance()

        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu.findItem(R.id.action_search)
        (menuItem.actionView as SearchView).setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val clubs = mRealm.where(Club::class.java).contains(Club.FIELD_NAME, newText, Case.INSENSITIVE).findAll()
                (mRecyclerView.adapter as ClubRecyclerViewAdapter).updateData(clubs)
                return false
            }
        })
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

    override fun onDetach() {
        super.onDetach()
        mListener = null
        mRealm.close()
    }

    interface OnListFragmentInteractionListener {
        fun onClickClub(item: Club, view: View)
    }
}
