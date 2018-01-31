package luxurysky.clubmanager.view

import android.app.Dialog
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AlertDialog
import luxurysky.clubmanager.R

class PhotoSourceDialogFragment : DialogFragment() {

    interface Listener {
        fun onRemovePictureChosen()
        fun onTakePhotoChosen()
        fun onPickFromGalleryChosen()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Get the available options for changing the photo
        val photoMode = arguments!!.getInt(ARG_PHOTO_MODE)
        val choices = PhotoActionPopup.getChoices(context!!, photoMode)
        // Prepare the AlertDialog items and click listener
        val items = arrayOfNulls<CharSequence>(choices.size)
        for (i in items.indices) {
            items[i] = choices[i].toString()
        }
        val clickListener = OnClickListener { _, which ->
            val listener = activity as Listener
            val choice = choices[which]
            when (choice.id) {
                PhotoActionPopup.ChoiceListItem.ID_REMOVE -> listener.onRemovePictureChosen()
                PhotoActionPopup.ChoiceListItem.ID_TAKE_PHOTO -> listener.onTakePhotoChosen()
                PhotoActionPopup.ChoiceListItem.ID_PICK_PHOTO -> listener.onPickFromGalleryChosen()
            }
            dismiss()
        }
        // Build the AlertDialog
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(R.string.menu_change_photo)
        builder.setItems(items, clickListener)
        builder.setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

    companion object {
        private const val ARG_PHOTO_MODE = "photoMode"
        fun show(activity: FragmentActivity, photoMode: Int) {
            if (activity !is Listener) {
                throw IllegalArgumentException("Activity must implement " + Listener::class.java.name)
            }
            val args = Bundle()
            args.putInt(ARG_PHOTO_MODE, photoMode)
            val dialog = PhotoSourceDialogFragment()
            dialog.arguments = args
            dialog.show(activity.supportFragmentManager, "photoSource")
        }
    }
}