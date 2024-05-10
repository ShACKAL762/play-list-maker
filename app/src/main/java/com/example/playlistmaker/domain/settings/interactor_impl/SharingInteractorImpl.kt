package com.example.playlistmaker.domain.settings.interactor_impl

import com.example.playlistmaker.domain.settings.interactor.SharingInteractor
import com.example.playlistmaker.domain.settings.repository.SharingRepoitory

class SharingInteractorImpl (private val externalNavigator: SharingRepoitory
) : SharingInteractor {
    override fun shareApp() {
        externalNavigator.shareApp()
    }

    override fun openTerms() {
        externalNavigator.openLink()
    }

    override fun openSupport() {
        externalNavigator.openSupport()
    }

}