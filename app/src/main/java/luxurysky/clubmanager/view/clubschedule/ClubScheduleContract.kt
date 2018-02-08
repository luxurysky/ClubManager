package luxurysky.clubmanager.view.clubschedule

import com.google.api.services.calendar.model.Event
import luxurysky.clubmanager.BasePresenter
import luxurysky.clubmanager.BaseView

interface ClubScheduleContract {

    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun showScheduleList(output: List<Event>?)

        fun setLoadingIndicator(active: Boolean)

        fun showScheduleListError(exception: Exception?)


//        //
//        fun showMissingPlayer()
//        //
////        fun hideTitle()
////
//        fun showPlayerPhoto(photo : String)
//
//        fun showPlayerName(name: String)
//
//        fun showPlayerSquadNumber(squadNumber : Int)
//
//        fun showPlayerPosition(position : String)
//        //
////        fun hideDescription()
////
////        fun showDescription(description: String)
////
////        fun showCompletionStatus(complete: Boolean)
////
////        fun showEditTask(taskId: String)
////
//        fun showTaskDeleted()
////
////        fun showTaskMarkedComplete()
////
////        fun showTaskMarkedActive()
    }

    interface Presenter : BasePresenter {
//        fun deletePlayer()
    }
}