package com.tsyapa.rates.utils

import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner

interface ViewModelProvider<VM> {

    fun provideViewModel(owner: ViewModelStoreOwner, savedStateRegistryOwner: SavedStateRegistryOwner): VM
}