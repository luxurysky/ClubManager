package luxurysky.clubmanager.view.clubschedule

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.api.services.calendar.model.Event
import kotlinx.android.synthetic.main.item_club_schedule.view.*
import luxurysky.clubmanager.R

class ClubScheduleRecyclerViewAdapter(private var mValues: List<Event>?) : RecyclerView.Adapter<ClubScheduleRecyclerViewAdapter.ViewHolder>() {

    fun updateData(values: List<Event>?) {
        mValues = values
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (mValues == null) {
            0
        } else {
            mValues!!.count()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_club_schedule, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var dateTime = mValues!![position].start.date
        if (dateTime == null) {
            dateTime = mValues!![position].start.dateTime
        }

        holder.mDateView.text = dateTime.toString()
        holder.mSummaryView.text = mValues!![position].summary
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mDateView: TextView = mView.dateText
        val mSummaryView: TextView = mView.summaryText
    }
}