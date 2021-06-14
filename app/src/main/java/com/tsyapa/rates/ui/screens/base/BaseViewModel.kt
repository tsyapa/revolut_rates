package com.tsyapa.rates.ui.screens.base

import androidx.lifecycle.LiveData

interface BaseViewModel {

    val loadingLiveData: LiveData<Boolean>
    val errorLiveData: LiveData<Throwable>

    fun handleError(throwable: Throwable)
}