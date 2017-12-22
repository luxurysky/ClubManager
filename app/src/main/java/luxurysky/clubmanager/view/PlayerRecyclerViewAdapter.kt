package luxurysky.clubmanager.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_player.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Player
import luxurysky.clubmanager.view.PlayerListFragment.OnListFragmentInteractionListener


class PlayerRecyclerViewAdapter(private val mValues: OrderedRealmCollection<Player>, private val mListener: OnListFragmentInteractionListener?)
    : RealmRecyclerViewAdapter<Player, PlayerRecyclerViewAdapter.ViewHolder>(mValues, true) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.mPhotoView.context)
                .load(mValues[position].photoUrl)
                .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(50, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(holder.mPhotoView)

        holder.mNameView.text = mValues[position].name

        holder.mView.setOnClickListener {
            // mListener?.onListFragmentInteraction(holder.mItem)
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
