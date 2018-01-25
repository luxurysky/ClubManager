package luxurysky.clubmanager.view.clubedit

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_club_edit.*
import luxurysky.clubmanager.R
import luxurysky.clubmanager.view.PhotoSourceDialogFragment
import luxurysky.clubmanager.view.PhotoUtils


class ClubEditActivity : AppCompatActivity(), PhotoSourceDialogFragment.Listener {

    private var mFragment: Fragment? = null
    private lateinit var mRealm: Realm
    private var mTempPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_edit)
        setSupportActionBar(toolbar)

        mTempPhotoUri = PhotoUtils.generateTempImageUri(this)

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
        addPhotoPickerExtras(intent, mTempPhotoUri!!)
        startActivityForResult(intent, REQUEST_CODE_PHOTO_PICKED_WITH_DATA)
    }

    private fun addPhotoPickerExtras(intent: Intent, photoUri: Uri) {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.clipData = ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, photoUri)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [ $requestCode ], resultCode = [ $resultCode ], data = [ $data ]")
        if (requestCode == REQUEST_CODE_PHOTO_PICKED_WITH_DATA) {
            val uri = data?.data
            PhotoUtils.savePhotoFromUriToUri(this, uri, mTempPhotoUri, false)
            Log.d(TAG, "onActivityResult: data : " + data?.data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        private val TAG = ClubEditActivity::class.java.simpleName
        const val EXTRA_CLUB_ID = "CLUB_ID"
        private val REQUEST_CODE_PHOTO_PICKED_WITH_DATA = 1002
    }
}