/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package luxurysky.clubmanager.view

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface.OnClickListener
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import luxurysky.clubmanager.R

/**
 * Displays the options for changing the contact photo.
 */
class PhotoSourceDialogFragment : DialogFragment() {
    /**
     * Callbacks for the host of the [PhotoSourceDialogFragment].
     */
    interface Listener {
        fun onRemovePictureChosen()
        fun onTakePhotoChosen()
        fun onPickFromGalleryChosen()
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        // Get the available options for changing the photo
        val photoMode = arguments.getInt(ARG_PHOTO_MODE)
        val choices = PhotoActionPopup.getChoices(activity, photoMode)
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
        val title = View.inflate(activity, R.layout.dialog_title, /* listener =*/ null) as TextView
        title.setText(R.string.menu_change_photo)
        val builder = AlertDialog.Builder(activity)
        builder.setCustomTitle(title)
        builder.setItems(items, clickListener)
        builder.setNegativeButton(android.R.string.cancel, null)
        return builder.create()
    }

    companion object {
        private val ARG_PHOTO_MODE = "photoMode"
        fun show(activity: Activity, photoMode: Int) {
            if (activity !is Listener) {
                throw IllegalArgumentException("Activity must implement " + Listener::class.java.name)
            }
            val args = Bundle()
            args.putInt(ARG_PHOTO_MODE, photoMode)
            val dialog = PhotoSourceDialogFragment()
            dialog.arguments = args
            dialog.show(activity.fragmentManager, "photoSource")
        }
    }
}