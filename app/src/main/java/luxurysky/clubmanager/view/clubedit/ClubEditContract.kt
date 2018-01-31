package luxurysky.clubmanager.view.clubedit

import luxurysky.clubmanager.BasePresenter
import luxurysky.clubmanager.BaseView

interface ClubEditContract {

    interface View : BaseView<Presenter> {

        // presenter - > view

        val isActive: Boolean

        fun showClubPlayersPhoto(photo: String)

        fun showClubName(name: String)

        fun showClubMainStadium(mainStadium: String)

        fun showClubSubStadium(subStadium: String)

        fun showClubDues(dues: String)

        fun showClubMatchTime(matchTime: String)

        fun showClubAgeGroup(ageGroup: String)

        fun showSaveCompleted()
//
//        fun setLoadingIndicator(active: Boolean)
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
        // view -> presenter
        fun saveClub(name: String?, mainStadium: String?, subStadium: String?, dues: String?, matchTime: String?, ageGroup: String?)
    }
}