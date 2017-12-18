package luxurysky.clubmanager.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Environment
import android.support.v4.content.ContextCompat
import luxurysky.clubmanager.BuildConfig
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.*
import java.util.logging.Formatter

/**
 * Created by HWANGJIN on 2017-12-18.
 */
object Log {
    private val TAG = Log::class.java.simpleName

    private val DEBUG = BuildConfig.DEBUG
    private val LOG_FILE_NAME = "FileLog%g.txt"
    private val LOG_FILE_SIZE_LIMIT = 10 * 1024 * 1024
    private val LOG_FILE_MAX_COUNT = 5

    private val dateFormat = SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault())

    private var hasPermission = false
    private var fileHandler: FileHandler? = null
    private var logger: Logger? = null

    fun init(context: Context) {
        hasPermission = (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)

        if (hasPermission) {
            fileHandler = FileHandler(Environment.getExternalStorageDirectory().toString() + File.separator + Log.LOG_FILE_NAME, Log.LOG_FILE_SIZE_LIMIT, Log.LOG_FILE_MAX_COUNT, true).apply {
                formatter = object : Formatter() {
                    override fun format(record: LogRecord?): String {
                        val builder = StringBuilder(80)
                        builder.append(Log.dateFormat.format(Date()))
                        builder.append(" ")
                        builder.append(record?.message)
                        return builder.toString()
                    }
                }
            }

            logger = Logger.getLogger(TAG).apply {
                addHandler(fileHandler)
                level = Level.ALL
                useParentHandlers = false
            }

        }
    }

    fun v(tag: String, msg: String) {
        if (DEBUG) {
            logger?.log(Level.FINEST, String.format("V/%s(%d): %s\n", tag, Binder.getCallingPid(), msg))
            android.util.Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            logger?.log(Level.FINE, String.format("D/%s(%d): %s\n", tag, Binder.getCallingPid(), msg))
            android.util.Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            logger?.log(Level.INFO, String.format("I/%s(%d): %s\n", tag, Binder.getCallingPid(), msg))
            android.util.Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (DEBUG) {
            logger?.log(Level.WARNING, String.format("W/%s(%d): %s\n", tag, Binder.getCallingPid(), msg))
            android.util.Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (DEBUG && hasPermission) {
            logger?.log(Level.SEVERE, String.format("E/%s(%d): %s\n", tag, Binder.getCallingPid(), msg))
            android.util.Log.e(tag, msg)
        }
    }
}