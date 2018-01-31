package luxurysky.clubmanager.view.clubedit

import io.realm.Realm
import luxurysky.clubmanager.model.Club
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
            if (!isActive) {
                return
            }
            club?.run {
                //                setLoadingIndicator(false)
                showClubPlayersPhoto(playersUrl)
                showClubName(name)
                showClubMainStadium(mainStadium)
                showClubSubStadium(subStadium)
                showClubDues(dues)
                showClubMatchTime(matchTime)
                showClubAgeGroup(ageGroup)
            }
        }

    }

    override fun saveClub(name: String?, mainStadium: String?, subStadium: String?, dues: String?, matchTime: String?, ageGroup: String?) {
        val club = if (clubId == null || clubId.isEmpty()) {
            Club()
        } else {
            DataHelper.findClub(realm, clubId)
        }

        if (club != null) {
            realm.executeTransaction {
                if (name != null) {
                    club.name = name
                }
                if (mainStadium != null) {
                    club.mainStadium = mainStadium
                }
                if (subStadium != null) {
                    club.subStadium = subStadium
                }
                if (dues != null) {
                    club.dues = dues
                }
                if (matchTime != null) {
                    club.matchTime = matchTime
                }
                if (ageGroup != null) {
                    club.ageGroup = ageGroup
                }
                realm.insert(club)
            }
        }

        clubEditView.showSaveCompleted()
    }
}