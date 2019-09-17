package com.felix.madeclass

import android.content.Intent
import android.provider.Settings
import com.google.android.material.bottomnavigation.BottomNavigationView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.Fragment
import com.felix.madeclass.alarm.AlarmReceiver
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    private lateinit var coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout
    private var doubleBackToExitPressedOnce = false
    private lateinit var toolbar: Toolbar

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        lateinit var selectedFragment: Fragment

        when (menuItem.itemId) {
            R.id.title_movies -> {
                selectedFragment = MoviesFragment()
                supportActionBar?.title = resources.getString(R.string.title_movies)
            }
            R.id.title_tv_show -> {
                selectedFragment = TvFragment()
                supportActionBar?.title = resources.getString(R.string.title_tv_show)
            }
            R.id.title_favorites -> {
                selectedFragment = FavoritesFragment()
                supportActionBar?.title = resources.getString(R.string.favorites)
            }
        }
        supportFragmentManager.commit { replace(R.id.fragment_container, selectedFragment, selectedFragment.javaClass.simpleName) }
        true
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to leave", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            val intentSetting = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intentSetting)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val alarmReceiver = AlarmReceiver()

        Realm.init(this)

        coordinatorLayout = findViewById(R.id.coordinatorLayout)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val fragment: Fragment
        fragment = MoviesFragment()

        supportFragmentManager.commit { replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName) }
        supportActionBar?.title = "Movies"
    }


}
