package luxurysky.clubmanager.view.clublist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_club.view.*
import luxurysky.clubmanager.GlideApp
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.clublist.ClubListFragment.OnListFragmentInteractionListener

class ClubListRecyclerViewAdapter(mValues: OrderedRealmCollection<Club>, private val mListener: OnListFragmentInteractionListener?)
    : RealmRecyclerViewAdapter<Club, ClubListRecyclerViewAdapter.ViewHolder>(mValues, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_club, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (data == null) return

        holder.mNameTextView.text = data!![position].name

        GlideApp.with(holder.mEmblemImageView.context)
                .load(data!![position].emblemUrl)
                .into(holder.mEmblemImageView)

        holder.mView.setOnClickListener {
            mListener?.onClickClub(data!![position], holder.mNameTextView)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mEmblemImageView: ImageView = mView.clubEmblem
        val mNameTextView: TextView = mView.clubName
    }
}
