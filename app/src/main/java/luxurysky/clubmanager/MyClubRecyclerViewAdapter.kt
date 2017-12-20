package luxurysky.clubmanager

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import luxurysky.clubmanager.MyClubListFragment.OnListFragmentInteractionListener
import luxurysky.clubmanager.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyClubRecyclerViewAdapter(private val mValues: List<DummyItem>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyClubRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_myclub, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
//        holder.mEmblemImageView.sets
        holder.mNameTextView.text = mValues[position].content

        Glide.with(holder.mEmblemImageView.context)
                .load("https://search.pstatic.net/common?type=o&size=152x114&quality=95&direct=true&src=http%3A%2F%2Fsstatic.naver.net%2Fkeypage%2Fimage%2Fdss%2F146%2F30%2F84%2F09%2F146_100308409_team_image_url_1435388480471.jpg")
                .into(holder.mEmblemImageView)


        holder.mView.setOnClickListener {
            //            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mEmblemImageView: ImageView
        val mNameTextView: TextView
        var mItem: DummyItem? = null

        init {
            mEmblemImageView = mView.findViewById<View>(R.id.clubEmblem) as ImageView
            mNameTextView = mView.findViewById<View>(R.id.clubName) as TextView
        }

        override fun toString(): String {
            return super.toString() + " '" + mNameTextView.text + "'"
        }
    }
}
