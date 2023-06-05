package com.vicyrfahreza.githubuserapi.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vicyrfahreza.githubuserapi.service.SettingPreferences

class VMFSwitchTheme (private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SwitchThemeViewModel::class.java)) {
            return SwitchThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class : " + modelClass.name)
    }

}