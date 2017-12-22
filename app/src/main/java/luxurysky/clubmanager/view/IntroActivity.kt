package luxurysky.clubmanager.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import luxurysky.clubmanager.R

class IntroActivity : AppCompatActivity() {
    private val mHandler = Handler()
    private val mRunnable = Runnable {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        mHandler.postDelayed(mRunnable, 1000)
    }
}