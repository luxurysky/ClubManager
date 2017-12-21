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
import kotlinx.android.synthetic.main.fragment_player.view.*

import luxurysky.clubmanager.PlayerListFragment.OnListFragmentInteractionListener
import luxurysky.clubmanager.model.Player


class MyPlayerRecyclerViewAdapter(private val mValues: RealmResults<Player>, private val mListener: OnListFragmentInteractionListener?)
    : RealmRecyclerViewAdapter<Player, MyPlayerRecyclerViewAdapter.ViewHolder>(mValues, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.mIdView.text = mValues[position]!!.name
//        holder.mContentView.text = mValues[position].content

        Glide.with(holder.mPhotoView.context)
                .load(mValues[position]!!.photoUrl)
                .into(holder.mPhotoView)

        holder.mNameView.text = mValues[position]!!.name

        holder.mView.setOnClickListener {
            //            mListener?.onListFragmentInteraction(holder.mItem)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mPhotoView: ImageView = mView.playerPhoto
        val mNameView: TextView = mView.playerName
    }
}
