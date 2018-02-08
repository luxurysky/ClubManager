package luxurysky.clubmanager.view.clubschedule

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException
import com.google.api.services.calendar.model.Event
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_club_schedule.*
import kotlinx.android.synthetic.main.fragment_club_schedule.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.club.ClubActivity
import java.util.*

class ClubScheduleFragment : Fragment(), ClubScheduleContract.View {

    override lateinit var mPresenter: ClubScheduleContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private var mAdapter: ClubScheduleRecyclerViewAdapter? = null

    private var mClubId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mClubId = arguments!!.getString(ARG_CLUB_ID)
        }

        val realm = Realm.getDefaultInstance()
        val club = realm.where(Club::class.java).equalTo(Club.FIELD_ID, mClubId).findFirst()

        ClubSchedulePresenter(club!!.googleCalendarId, getString(R.string.google_api_key), this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_club_schedule, container, false)

        mAdapter = ClubScheduleRecyclerViewAdapter(null)
        root.clubScheduleRecyclerView.layoutManager = LinearLayoutManager(context)
        root.clubScheduleRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        root.clubScheduleRecyclerView.adapter = mAdapter

        root.clubScheduleCalendarView.selectionMode = MaterialCalendarView.SELECTION_MODE_MULTIPLE

        return root
    }

    override fun onStart() {
        super.onStart()
        getResultsFromApi()
    }

    private fun getResultsFromApi() {
        if (!isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices()
        } else if (isDeviceOnline()) {
            mPresenter.start()
        }
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.GONE
        }
    }

    override fun showScheduleList(output: List<Event>?) {
        mAdapter?.updateData(output)

        if (output != null) {
            for (event in output) {
                var dateTime = event.start.date
                if (dateTime == null) {
                    dateTime = event.start.dateTime
                }
                if (dateTime != null) {
                    clubScheduleCalendarView.setDateSelected(Date(dateTime.value), true)
                }
            }
        }
    }

    private fun isGooglePlayServicesAvailable(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        return connectionStatusCode == ConnectionResult.SUCCESS
    }

    private fun acquireGooglePlayServices() {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val connectionStatusCode = apiAvailability.isGooglePlayServicesAvailable(context)
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode)
        }
    }

    override fun showScheduleListError(exception: Exception?) {
        if (exception != null) {
            if (exception is GooglePlayServicesAvailabilityIOException) {
                showGooglePlayServicesAvailabilityErrorDialog(exception.connectionStatusCode)
            } else if (exception is UserRecoverableAuthIOException) {
                startGoogleAuthRequestActivity(exception.intent)
            } else {
                // show error dialog
            }
        } else {
            // empty. cancelled.
        }
    }

    private fun showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode: Int) {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val dialog = apiAvailability.getErrorDialog(
                getClubActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES)
        dialog.show()
    }

    private fun startGoogleAuthRequestActivity(intent: Intent) {
        startActivityForResult(intent, REQUEST_AUTHORIZATION)
    }

    private fun isDeviceOnline(): Boolean {
        val connMgr = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkInfo = connMgr!!.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun getClubActivity(): ClubActivity {
        return activity as ClubActivity
    }

    companion object {
        private val TAG = ClubScheduleFragment::class.java.simpleName

        private const val ARG_CLUB_ID = "club_id"

        private const val REQUEST_AUTHORIZATION = 1001
        private const val REQUEST_GOOGLE_PLAY_SERVICES = 1002

        fun newInstance(clubId: String): ClubScheduleFragment {
            val fragment = ClubScheduleFragment()
            val args = Bundle()
            args.putString(ARG_CLUB_ID, clubId)
            fragment.arguments = args
            return fragment
        }
    }
}