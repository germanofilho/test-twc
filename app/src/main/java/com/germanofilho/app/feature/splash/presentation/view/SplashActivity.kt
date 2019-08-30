package com.germanofilho.app.feature.splash.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.germanofilho.app.feature.home.presentation.view.HomeActivity
import com.germanofilho.twc_test.R
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Completable.complete()
            .delay(2, TimeUnit.SECONDS)
            .doOnComplete{
                startActivity(Intent(this, HomeActivity::class.java))
                finishAffinity()
            }
            .subscribe()
    }
}

