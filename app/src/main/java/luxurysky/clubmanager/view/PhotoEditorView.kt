package luxurysky.clubmanager.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.view_photo_editor.view.*
import luxurysky.clubmanager.GlideApp
import luxurysky.clubmanager.R
import luxurysky.clubmanager.util.SchedulingUtils


/**
 * Created by HWANGJIN on 2018-01-12.
 */
class PhotoEditorView : RelativeLayout {

    companion object {
        private val TAG = PhotoEditorView::class.java.simpleName
    }

    private val mLandscapePhotoRatio = getTypedFloat(R.dimen.landscape_photo_ratio)
    private val mPortraitPhotoRatio = getTypedFloat(R.dimen.portrait_photo_ratio)
    private val mIsTwoPanel = resources.getBoolean(R.bool.club_edit_two_panel)

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private fun getTypedFloat(resourceId: Int): Float {
        val typedValue = TypedValue()
        resources.getValue(resourceId, typedValue, /* resolveRefs =*/ true)
        return typedValue.float
    }

    fun setPhoto(photoUrl: String) {
        GlideApp.with(context)
                .load(photoUrl)
                .placeholder(R.drawable.ic_account_box_light_blue_700_24dp)
                .into(photo)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        adjustDimensions()
    }

    private fun adjustDimensions() {
        SchedulingUtils.doOnPreDraw(this, false, Runnable {
            val photoHeight: Int
            val photoWidth: Int
            if (mIsTwoPanel) {
                photoHeight = height
                photoWidth = (photoHeight * mLandscapePhotoRatio).toInt()
            } else {
                photoWidth = width
                photoHeight = (photoWidth / mPortraitPhotoRatio).toInt()
            }

            layoutParams.apply {
                height = photoHeight
                width = photoWidth
                layoutParams = this
            }
        })
    }
}