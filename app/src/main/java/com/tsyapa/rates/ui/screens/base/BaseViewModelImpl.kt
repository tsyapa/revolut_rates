package com.tsyapa.rates.ui.screens.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tsyapa.rates.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModelImpl(protected val savedStateHandle: SavedStateHandle) : ViewModel(), BaseViewModel {

    override val loadingLiveData = MutableLiveData<Boolean>()
    override val errorLiveData = SingleLiveEvent<Throwable>()
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun handleError(throwable: Throwable){ errorLiveData.postValue(throwable) }

    override fun onCleared() { compositeDisposable.dispose() }
}