package luxurysky.clubmanager.view

import android.content.Context
import android.net.Uri
import android.support.v4.content.FileProvider
import luxurysky.clubmanager.R
import luxurysky.clubmanager.util.Log
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object PhotoUtils {

    private val TAG = PhotoUtils::class.java.simpleName
    private const val PHOTO_DATE_FORMAT = "'IMG'_yyyyMMdd_HHmmss"

    fun generateTempImageUri(context: Context): Uri {
        val fileProviderAuthority = context.resources.getString(R.string.photo_file_provider_authority)
        return FileProvider.getUriForFile(context, fileProviderAuthority, File(pathForTempPhoto(context, generateTempPhotoFileName())))
    }

    private fun pathForTempPhoto(context: Context, fileName: String): String {
        val dir = context.cacheDir
        dir.mkdirs()
        val f = File(dir, fileName)
        return f.absolutePath
    }

    private fun generateTempPhotoFileName(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(PHOTO_DATE_FORMAT, Locale.US)
        return "ContactPhoto-" + dateFormat.format(date) + ".jpg"
    }

    fun savePhotoFromUriToUri(context: Context, inputUri: Uri?, outputUri: Uri?, deleteAfterSave: Boolean): Boolean {
        if (inputUri == null || outputUri == null) {
            return false
        }
        try {
            context.contentResolver.openAssetFileDescriptor(outputUri, "rw")!!.createOutputStream().use { outputStream ->
                context.contentResolver.openInputStream(inputUri)!!.use { inputStream ->
                    val buffer = ByteArray(16 * 1024)
                    var length: Int
                    var totalLength = 0
                    while (true) {
                        length = inputStream.read(buffer)
                        if (length <= 0) {
                            break
                        }
                        outputStream.write(buffer, 0, length)
                        totalLength += length
                    }

                    Log.v(TAG, "Wrote " + totalLength + " bytes for photo " + inputUri.toString())
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Failed to write photo: " + inputUri.toString() + " because: " + e)
            return false
        } catch (e: NullPointerException) {
            Log.e(TAG, "Failed to write photo: " + inputUri.toString() + " because: " + e)
            return false
        } finally {
            if (deleteAfterSave) {
                context.contentResolver.delete(inputUri, null, null)
            }
        }
        return true
    }
}