package luxurysky.clubmanager.view.clubschedule

import android.os.AsyncTask
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.model.Event
import java.io.IOException

class GoogleCalendarTask(private val googleCalendarId: String, private val googleApiKey: String, private val callback: GoogleCalendarTaskCallback) : AsyncTask<Void, Void, List<Event>>() {
    private var mService: com.google.api.services.calendar.Calendar? = null
    private var mLastError: Exception? = null

    init {
        val transport = AndroidHttp.newCompatibleTransport()
        val jsonFactory = JacksonFactory.getDefaultInstance()
        mService = com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, null)
                .setApplicationName("ClubManager")
                .build()
    }

    @Throws(IOException::class)
    private fun getDataFromApi(): List<Event>? {
        val events = mService!!.events().list(googleCalendarId)
                .setKey(googleApiKey)
                .setMaxResults(10)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute()
        return events.items
    }

    override fun doInBackground(vararg params: Void): List<Event>? {
        return try {
            getDataFromApi()
        } catch (e: Exception) {
            mLastError = e
            cancel(true)
            null
        }
    }

    override fun onPreExecute() {
        callback.onPreExecute()
    }

    override fun onPostExecute(output: List<Event>?) {
        callback.onPostExecute(output)
    }

    override fun onCancelled() {
        callback.onCancelled(mLastError)
    }

    interface GoogleCalendarTaskCallback {
        fun onPreExecute()
        fun onPostExecute(output: List<Event>?)
        fun onCancelled(exception: Exception?)
    }
}
