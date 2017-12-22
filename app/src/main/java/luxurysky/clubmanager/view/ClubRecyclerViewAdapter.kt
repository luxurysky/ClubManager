package luxurysky.clubmanager.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_club.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.ClubListFragment.OnListFragmentInteractionListener

class ClubRecyclerViewAdapter(private val mValues: OrderedRealmCollection<Club>, private val mListener: OnListFragmentInteractionListener?)
    : RealmRecyclerViewAdapter<Club, ClubRecyclerViewAdapter.ViewHolder>(mValues, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_club, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mNameTextView.text = mValues[position].name

        Glide.with(holder.mEmblemImageView.context)
                .load(mValues[position].emblemUrl)
                .into(holder.mEmblemImageView)


        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(mValues[position])
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mEmblemImageView: ImageView = mView.clubEmblem
        val mNameTextView: TextView = mView.clubName
    }
}