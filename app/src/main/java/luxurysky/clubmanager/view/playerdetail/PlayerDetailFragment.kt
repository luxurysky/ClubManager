package luxurysky.clubmanager.view.playerdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import kotlinx.android.synthetic.main.fragment_player_detail.*
import luxurysky.clubmanager.GlideApp
import luxurysky.clubmanager.R

class PlayerDetailFragment : Fragment(), PlayerDetailContract.View {

    override lateinit var mPresenter: PlayerDetailContract.Presenter
    override var isActive: Boolean = false
        get() = isAdded

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_player_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val deletePressed = item.itemId == R.id.action_delete
        if (deletePressed) {
            mPresenter.deletePlayer()
        }
        return deletePressed
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.GONE
        }
    }

    override fun showMissingPlayer() {
    }

    override fun showPlayerPhoto(photo: String) {
        GlideApp.with(playerPhoto.context)
                .load(photo)
                .placeholder(R.drawable.ic_account_box_light_blue_700_24dp)
                .into(playerPhoto)
    }

    override fun showPlayerName(name: String) {
        playerName.setText(name)
    }

    override fun showPlayerSquadNumber(squadNumber: Int) {
        playerSquadNumber.setText(squadNumber.toString())
    }

    override fun showPlayerPosition(position: String) {
//        playerPosition.setText(position)
    }

    override fun showTaskDeleted() {
        activity?.finish()
    }
}