package luxurysky.clubmanager.view.clubinfo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_club_info.view.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.model.DataHelper

class ClubInfoFragment : Fragment() {

    private var mClubId: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mClubId = arguments!!.getString(ARG_PARAM1)
        }
        Log.d(TAG, "[onCreate] ${hashCode()}")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_club_info, container, false)

        val realm = Realm.getDefaultInstance()
        val club = DataHelper.findClub(realm, mClubId ?: "")

        view.myClubName.text = club?.name

        Glide.with(this)
                .load(club?.playersUrl)
                .into(view.myClubImage)
        Log.d(TAG, "[onCreateView]")
        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null

        Log.d(TAG, "[onDetach]")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "[onDestroy]")
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val TAG = ClubInfoFragment::class.java.simpleName

        private val ARG_PARAM1 = "club_id"

        fun newInstance(clubId: String): ClubInfoFragment {
            val fragment = ClubInfoFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, clubId)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
