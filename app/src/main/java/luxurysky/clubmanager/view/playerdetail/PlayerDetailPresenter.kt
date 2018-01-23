package luxurysky.clubmanager.view.playerdetail

import android.os.Handler
import android.os.Looper
import android.util.Log
import io.realm.Realm
import luxurysky.clubmanager.model.DataHelper

class PlayerDetailPresenter(
        private val playerId: String,
        private val realm: Realm,
        private val playerDetailView: PlayerDetailContract.View
) : PlayerDetailContract.Presenter {

    companion object {
        private val TAG = PlayerDetailPresenter::class.java.simpleName
    }

    init {
        playerDetailView.mPresenter = this
    }

    override fun start() {
        findPlayer()
    }

    private fun findPlayer() {
        if (playerId.isEmpty()) {
            playerDetailView.showMissingPlayer()
            return
        }

        playerDetailView.setLoadingIndicator(true)
        val player = DataHelper.findPlayer(realm, playerId)
        Thread(Runnable {
            try {
                Thread.sleep(2000)
            } catch (e: Exception) {

            }
            Handler(Looper.getMainLooper()).post({
                with(playerDetailView)
                {
                    Log.d(TAG, "with isActive : $isActive")
                    if (!isActive) {
                        return@post
                    }
                    setLoadingIndicator(false)
                    showPlayerPhoto(player?.photoUrl ?: "")
                    showPlayerName(player?.name ?: "")
                    showPlayerSquadNumber(player?.squadNumber ?: 0)
                    showPlayerPosition(player?.position ?: "")
                }
            })
        }).start()
    }

    override fun deletePlayer() {
        if (playerId.isEmpty()) {
            playerDetailView.showMissingPlayer()
            return
        }

        DataHelper.deletePlayer(realm, playerId)
        playerDetailView.showTaskDeleted()


    }
}