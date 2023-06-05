package com.vicyrfahreza.githubuserapi.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.vicyrfahreza.githubuserapi.service.SettingPreferences
import kotlinx.coroutines.launch

class SwitchThemeViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSettings(isDarkMode: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkMode)
        }
    }
}