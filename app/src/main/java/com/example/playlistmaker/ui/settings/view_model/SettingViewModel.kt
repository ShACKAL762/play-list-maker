package com.example.playlistmaker.ui.settings.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.domain.settings.interactor.SettingsInteractor
import com.example.playlistmaker.domain.settings.interactor.SharingInteractor

class SettingViewModel(
    private val sharingInteractor: SharingInteractor,
    private val settingsInteractor: SettingsInteractor,
) : ViewModel() {

       private val switchState = MutableLiveData<Boolean>()
        var liveSwitchState : LiveData<Boolean>  = switchState

    fun share (){
        sharingInteractor.shareApp()
    }
    fun openLink(){
        sharingInteractor.openTerms()
    }
    fun sendSupport(){
        sharingInteractor.openSupport()
    }
    fun changeTheme(checked : Boolean){
        settingsInteractor.updateThemeSetting(checked)
    }
   fun getTheme(){
        switchState.value = settingsInteractor.getThemeSettings()
    }
}