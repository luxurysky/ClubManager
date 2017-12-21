package luxurysky.clubmanager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.realm.RealmRecyclerViewAdapter
import io.realm.RealmResults
import luxurysky.clubmanager.MyClubListFragment.OnListFragmentInteractionListener
import luxurysky.clubmanager.dummy.DummyContent.DummyItem
import luxurysky.clubmanager.model.Club

class MyClubRecyclerViewAdapter(private val mValues: RealmResults<Club>, private val mListener: OnListFragmentInteractionListener?)
    : RealmRecyclerViewAdapter<Club, MyClubRecyclerViewAdapter.ViewHolder>(mValues, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_myclub, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mItem = mValues[position]
//        holder.mEmblemImageView.sets
//        holder.mNameTextView.text = mValues[position].content
        holder.mNameTextView.text = mValues[position]!!.name

        Glide.with(holder.mEmblemImageView.context)
                .load(mValues[position]!!.emblemUrl)
                .into(holder.mEmblemImageView)


        holder.mView.setOnClickListener {
            //            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mEmblemImageView: ImageView = mView.findViewById<View>(R.id.clubEmblem) as ImageView
        val mNameTextView: TextView = mView.findViewById<View>(R.id.clubName) as TextView
        var mItem: DummyItem? = null

        override fun toString(): String {
            return super.toString() + " '" + mNameTextView.text + "'"
        }
    }
}
