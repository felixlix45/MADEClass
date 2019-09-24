package com.felix.madeclass

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.felix.madeclass.alarm.AlarmReceiver

class SettingsActivity : AppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val toolbar : Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        if(supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val alarmReceiver = AlarmReceiver()
            val changeLanguage: Preference? = findPreference("language")
            changeLanguage?.setOnPreferenceClickListener {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                true
            }

            val notifEveryDay: SwitchPreferenceCompat? = findPreference("notification7am")

            notifEveryDay?.setOnPreferenceChangeListener { preference, isChecked ->
                if(isChecked == true){
                    alarmReceiver.setRepeatingAlarm(requireActivity(), "Aku kangen kamu :(", "Balik yuk ke aplikasi aku :)")
                }else{
                    alarmReceiver.cancelAlarm(requireActivity())
                }
                true
            }

            val notifNewMovie: SwitchPreferenceCompat? = findPreference("notification8am")

            notifNewMovie?.setOnPreferenceChangeListener { preference, isChecked ->
                if(isChecked == true){
                    alarmReceiver.setMovieAlarm(requireActivity())
                }else{
                    alarmReceiver.cancelMovieAlarm(requireActivity())
                }
                true
            }
        }
    }
}