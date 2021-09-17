package kz.example.coingeckoapp.clean.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.example.coingeckoapp.R
import kz.example.coingeckoapp.clean.presentation.ui.coin_list.CoinsListFragment

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