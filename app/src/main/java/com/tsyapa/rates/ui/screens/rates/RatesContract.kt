package com.tsyapa.rates.ui.screens.rates

import androidx.lifecycle.MutableLiveData
import com.tsyapa.rates.ui.screens.base.BaseViewModel
import com.tsyapa.rates.ui.screens.rates.data.model.Rate
import io.reactivex.Observable

interface RatesContract {

    interface ViewModel: BaseViewModel {
        val liveDataUpdateRates: MutableLiveData<MutableList<Rate>>

        fun getRates()
        fun onBaseAmountChanged(newAmount: Double)
        fun onBaseCurrencyChanged(newCurrencyPosition: Int)
    }

    interface Model {
        fun getRates(baseCurrency: String): Observable<Map<String, Double>>
    }
}