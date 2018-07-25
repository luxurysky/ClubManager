/*
 * Copyright (C) 2010 The Android Open Source Project
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

import android.content.Context
import luxurysky.clubmanager.R
import luxurysky.clubmanager.util.CapabilityTester
import java.util.*

/**
 * Shows a popup asking the user what to do for a photo. The result is passed back to the Listener
 */
object PhotoActionPopup {

    /**
     * Bitmask flags to specify which actions should be presented to the user.
     */
    object Flags {
        /** If set, show choice to remove photo.  */
        const val REMOVE_PHOTO = 2
        /** If set, show choices to take a picture with the camera, or pick one from the gallery.  */
        const val TAKE_OR_PICK_PHOTO = 4
        /**
         * If set, modifies the wording in the choices for TAKE_OR_PICK_PHOTO
         * to emphasize that the existing photo will be replaced.
         */
        const val TAKE_OR_PICK_PHOTO_REPLACE_WORDING = 8
    }

    /**
     * Convenient combinations of commonly-used flags (see [Flags]).
     */
    object Modes {
        const val NO_PHOTO = Flags.TAKE_OR_PICK_PHOTO
        const val READ_ONLY_PHOTO = 0
        val WRITE_ABLE_PHOTO = Flags.REMOVE_PHOTO or
                Flags.TAKE_OR_PICK_PHOTO or
                Flags.TAKE_OR_PICK_PHOTO_REPLACE_WORDING
        // When the popup represents multiple photos, the REMOVE_PHOTO option doesn't make sense.
        // The REMOVE_PHOTO option would have to remove all photos. And sometimes some of the
        // photos are readonly.
        val MULTIPLE_WRITE_ABLE_PHOTOS = Flags.TAKE_OR_PICK_PHOTO or Flags.TAKE_OR_PICK_PHOTO_REPLACE_WORDING
    }

    fun getChoices(context: Context, mode: Int): ArrayList<ChoiceListItem> {
        // Build choices, depending on the current mode. We assume this Dialog is never called
        // if there are NO choices (e.g. a read-only picture is already super-primary)
        val choices = ArrayList<ChoiceListItem>(4)
        // Remove
        if (mode and Flags.REMOVE_PHOTO > 0) {
            choices.add(ChoiceListItem(ChoiceListItem.ID_REMOVE, context.getString(R.string.removePhoto)))
        }
        // Take photo or pick one from the gallery.  Wording differs if there is already a photo.
        if (mode and Flags.TAKE_OR_PICK_PHOTO > 0) {
            val replace = mode and Flags.TAKE_OR_PICK_PHOTO_REPLACE_WORDING > 0
            val takePhotoResId = if (replace) R.string.take_new_photo else R.string.take_photo
            val takePhotoString = context.getString(takePhotoResId)
            val pickPhotoResId = if (replace) R.string.pick_new_photo else R.string.pick_photo
            val pickPhotoString = context.getString(pickPhotoResId)
            if (CapabilityTester.isCameraIntentRegistered(context)) {
                choices.add(ChoiceListItem(ChoiceListItem.ID_TAKE_PHOTO, takePhotoString))
            }
            choices.add(ChoiceListItem(ChoiceListItem.ID_PICK_PHOTO, pickPhotoString))
        }
        return choices
    }

    class ChoiceListItem(val id: Int, private val mCaption: String) {
        override fun toString(): String {
            return mCaption
        }

        companion object {
            const val ID_TAKE_PHOTO = 1
            const val ID_PICK_PHOTO = 2
            const val ID_REMOVE = 3
        }
    }
}