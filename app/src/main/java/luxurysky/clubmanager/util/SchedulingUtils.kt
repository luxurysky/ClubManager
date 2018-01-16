package luxurysky.clubmanager.util

import android.view.View
import android.view.ViewTreeObserver

object SchedulingUtils {
    fun doAfterLayout(view: View, runnable: Runnable) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                runnable.run()
            }
        })
    }

    /** Runs a piece of code just before the next draw, after layout and measurement  */
    fun doOnPreDraw(view: View, drawNextFrame: Boolean, runnable: Runnable) {
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                runnable.run()
                return drawNextFrame
            }
        })
    }
}