package luxurysky.clubmanager.view.clubschedule

import com.google.api.services.calendar.model.Event

class ClubSchedulePresenter(
        private val googleCalendarId: String,
        private val googleApiKey: String,
        private val clubScheduleView: ClubScheduleContract.View
) : ClubScheduleContract.Presenter {

    init {
        clubScheduleView.mPresenter = this
    }

    private val callback = object : GoogleCalendarTask.GoogleCalendarTaskCallback {
        override fun onPreExecute() {
            clubScheduleView.setLoadingIndicator(true)
        }

        override fun onPostExecute(output: List<Event>?) {
            if (!clubScheduleView.isActive) {
                return
            }
            clubScheduleView.setLoadingIndicator(false)
            clubScheduleView.showScheduleList(output)
        }

        override fun onCancelled(exception: Exception?) {
            if (!clubScheduleView.isActive) {
                return
            }
            clubScheduleView.setLoadingIndicator(false)
            clubScheduleView.showScheduleListError(exception)
        }
    }

    override fun start() {
        GoogleCalendarTask(googleCalendarId, googleApiKey, callback).execute()
    }
}