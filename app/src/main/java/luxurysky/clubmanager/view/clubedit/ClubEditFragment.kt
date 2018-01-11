package luxurysky.clubmanager.view.clubedit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_club_edit.view.*
import kotlinx.android.synthetic.main.item_club_section.view.*
import luxurysky.clubmanager.GlideApp
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.Club
import luxurysky.clubmanager.view.PhotoActionPopup

class ClubEditFragment : Fragment(), ClubEditContract.View {

    companion object {
        private val TAG = ClubEditFragment::class.java.simpleName
        private val KEY_NAME = "name"

        private val VIEW_TYPES = arrayOf(Club.FIELD_NAME, Club.FIELD_MAIN_STADIUM, Club.FIELD_SUB_STADIUM, Club.FIELD_DUES, Club.FIELD_MATCH_TIME, Club.FIELD_AGE_GROUP)

        private val ARG_CLUB_ID = "club_id"

        fun newInstance(clubId: String?): ClubEditFragment {
            val fragment = ClubEditFragment()
            val args = Bundle()
            args.putString(ARG_CLUB_ID, clubId)
            fragment.arguments = args
            return fragment
        }
    }

    override lateinit var mPresenter: ClubEditContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    private var mName = ""

    private var mClubId: String? = null

    private val mSectionViewMap = HashMap<String, View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        if (arguments != null) {
            mClubId = arguments!!.getString(ARG_CLUB_ID)
        }

        if (savedInstanceState != null) {
            mName = savedInstanceState.getString(KEY_NAME)
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_NAME, mName)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club_edit, container, false)
        restoreView(view)
        return view
    }

    private fun restoreView(view: View) {
        view.myClubImage.setOnClickListener { onPhotoEditorViewClicked() }

        for (viewType in VIEW_TYPES) {
            val field = LayoutInflater.from(context).inflate(R.layout.item_club_section, view.club_fields, false)

            field.title.text = Club.getFieldName(resources, viewType)

            view.club_fields.addView(field)

            mSectionViewMap[viewType] = field
        }
    }

    override fun showClubPlayersPhoto(photo: String) {
        GlideApp.with(context)
                .load(photo)
                .placeholder(R.drawable.ic_account_box_light_blue_700_24dp)
                .into(view?.myClubImage)
    }

    override fun showClubName(name: String) {
        mSectionViewMap[Club.FIELD_NAME]?.field?.setText(name)
    }

    override fun showClubMainStadium(mainStadium: String) {
        mSectionViewMap[Club.FIELD_MAIN_STADIUM]?.field?.setText(mainStadium)
    }

    override fun showClubSubStadium(subStadium: String) {
        mSectionViewMap[Club.FIELD_SUB_STADIUM]?.field?.setText(subStadium)
    }

    override fun showClubDues(dues: String) {
        mSectionViewMap[Club.FIELD_DUES]?.field?.setText(dues)
    }

    override fun showClubMatchTime(matchTime: String) {
        mSectionViewMap[Club.FIELD_MATCH_TIME]?.field?.setText(matchTime)
    }

    override fun showClubAgeGroup(ageGroup: String) {
        mSectionViewMap[Club.FIELD_AGE_GROUP]?.field?.setText(ageGroup)
    }

    private fun onPhotoEditorViewClicked() {
//        getClubEditActivity().changePhoto(PhotoActionPopup.Modes.NO_PHOTO)
    }

    private fun getClubEditActivity(): ClubEditActivity {
        return activity as ClubEditActivity
    }
}