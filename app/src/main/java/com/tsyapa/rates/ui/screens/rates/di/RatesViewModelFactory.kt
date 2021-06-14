package com.tsyapa.rates.ui.screens.rates.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import com.tsyapa.rates.ui.screens.rates.RatesContract
import com.tsyapa.rates.ui.screens.rates.RatesViewModel
import com.tsyapa.rates.utils.ViewModelProvider
import javax.inject.Inject

class RatesViewModelFactory @Inject constructor(
    private val repository: RatesContract.Model
) : ViewModelProvider<RatesContract.ViewModel> {

    override fun provideViewModel(owner: ViewModelStoreOwner,
                                  savedStateRegistryOwner: SavedStateRegistryOwner): RatesContract.ViewModel {
        return androidx.lifecycle.ViewModelProvider(
            owner,
            object : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {

                override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
                    return RatesViewModel(repository, handle) as T
                }

            }).get(RatesViewModel::class.java)
    }
}