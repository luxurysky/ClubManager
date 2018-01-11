package luxurysky.clubmanager.view.clubedit

import io.realm.Realm
import luxurysky.clubmanager.model.DataHelper

class ClubEditPresenter(
        private val clubId: String?,
        private val realm: Realm,
        private val clubEditView: ClubEditContract.View)
    : ClubEditContract.Presenter {

    init {
        clubEditView.mPresenter = this
    }

    override fun start() {
        findClub()
    }

    private fun findClub() {
        if (clubId == null || clubId.isEmpty()) {
//            clubEditView.showMissingPlayer()
            return
        }

//        clubEditView.setLoadingIndicator(true)
        val club = DataHelper.findClub(realm, clubId)
        with(clubEditView)
        {
            //                    android.util.Log.d(luxurysky.clubmanager.view.playerdetail.PlayerDetailPresenter.Companion.TAG, "with isActive : $isActive")
            if (!isActive) {
                return
            }
            club?.run {
                showClubPlayersPhoto(playersUrl)
                showClubName(name)
                showClubMainStadium(mainStadium)
                showClubSubStadium(subStadium)
                showClubDues(dues)
                showClubMatchTime(matchTime)
                showClubAgeGroup(ageGroup)
            }


//                    setLoadingIndicator(false)
//                    showPlayerPhoto(player?.photoUrl ?: "")
//                    showPlayerName(player?.name ?: "")
//                    showPlayerSquadNumber(player?.squadNumber ?: 0)
//                    showPlayerPosition(player?.position ?: "")
        }

    }


}