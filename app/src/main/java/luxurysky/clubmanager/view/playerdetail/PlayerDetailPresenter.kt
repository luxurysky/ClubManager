package luxurysky.clubmanager.view.playerdetail

import android.os.Handler
import android.os.Looper
import io.realm.Realm
import luxurysky.clubmanager.model.DataHelper

class PlayerDetailPresenter(
        private val playerId: String,
        private val realm: Realm,
        private val playerDetailView: PlayerDetailContract.View
) : PlayerDetailContract.Presenter {

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
                playerDetailView.showTitle(player?.name ?: "")
                playerDetailView.setLoadingIndicator(false)
            })
        }).start()
    }

    override fun deleteTask() {
    }
}