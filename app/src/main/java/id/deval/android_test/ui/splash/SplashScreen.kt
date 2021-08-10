package id.deval.android_test.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.deval.android_test.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
    }
}