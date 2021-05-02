package kz.example.coingeckoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = CoinsListFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.flContainer, fragment)
                .commit()

    }
}