package luxurysky.clubmanager.view.clubedit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.view.PhotoSourceDialogFragment


class ClubEditActivity : AppCompatActivity(), PhotoSourceDialogFragment.Listener {

    private var mFragment: Fragment? = null
    private lateinit var mRealm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_edit)
        setSupportActionBar(toolbar)

        val clubId = intent.getStringExtra(EXTRA_CLUB_ID)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setTitle(if (clubId == null) R.string.title_create_club else R.string.title_edit_club)
        }

        if (savedInstanceState == null) {
            mFragment = ClubEditFragment.newInstance(clubId)
            supportFragmentManager.beginTransaction().add(R.id.fragment, mFragment, "TAG1").commit()
        } else {
            mFragment = supportFragmentManager.findFragmentByTag("TAG1")
            supportFragmentManager.beginTransaction().show(mFragment).commit()
        }

        mRealm = Realm.getDefaultInstance()

        ClubEditPresenter(clubId, mRealm, mFragment as ClubEditFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_club_edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        } else if (id == R.id.action_save) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun changePhoto(photoMode: Int) {
        PhotoSourceDialogFragment.show(this, photoMode)
    }

    override fun onRemovePictureChosen() {
    }

    override fun onTakePhotoChosen() {
    }

    override fun onPickFromGalleryChosen() {
        val intent = Intent(Intent.ACTION_PICK, null)
        intent.type = "image/*"
//        addPhotoPickerExtras(intent, outputUri)
//        startPhotoActivity(intent, REQUEST_CODE_PHOTO_PICKED_WITH_DATA, photoUri);
        startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKED_WITH_DATA)
    }

    fun addPhotoPickerExtras(intent: Intent, photoUri: Uri) {
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
//        intent.clipData = ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, photoUri)
    }


    companion object {
        const val EXTRA_CLUB_ID = "CLUB_ID"
        private val REQUEST_CODE_PHOTO_PICKED_WITH_DATA = 1002
    }
}