package luxurysky.clubmanager.view.playerdetail

import luxurysky.clubmanager.BasePresenter
import luxurysky.clubmanager.BaseView

interface PlayerDetailContract {

    interface View : BaseView<Presenter> {

        val isActive: Boolean

        fun setLoadingIndicator(active: Boolean)
//
        fun showMissingPlayer()
//
//        fun hideTitle()
//
        fun showPlayerPhoto(photo : String)

        fun showPlayerName(name: String)

        fun showPlayerSquadNumber(squadNumber : Int)

        fun showPlayerPosition(position : String)
//
//        fun hideDescription()
//
//        fun showDescription(description: String)
//
//        fun showCompletionStatus(complete: Boolean)
//
//        fun showEditTask(taskId: String)
//
        fun showTaskDeleted()
//
//        fun showTaskMarkedComplete()
//
//        fun showTaskMarkedActive()
    }

    interface Presenter : BasePresenter {
        fun deletePlayer()
    }
}