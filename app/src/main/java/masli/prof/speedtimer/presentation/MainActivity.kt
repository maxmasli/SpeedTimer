package masli.prof.speedtimer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import masli.prof.speedtimer.R
import masli.prof.speedtimer.presentation.screens.timerscreen.TimerFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // turn off night theme
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}