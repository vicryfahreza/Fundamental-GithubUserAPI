package com.vicyrfahreza.githubuserapi.service

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.vicyrfahreza.githubuserapi.R
import com.vicyrfahreza.githubuserapi.model.SwitchThemeViewModel
import com.vicyrfahreza.githubuserapi.model.VMFSwitchTheme

class SwitchThemeActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_theme)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val viewModel = ViewModelProvider(this, VMFSwitchTheme(pref)).get(
            SwitchThemeViewModel::class.java
        )

        viewModel.getThemeSettings().observe(this) {isDarkMode: Boolean ->
            if(isDarkMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            viewModel.saveThemeSettings(isChecked)
        }
    }
}