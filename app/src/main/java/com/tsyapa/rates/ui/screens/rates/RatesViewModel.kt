package com.tsyapa.rates.ui.screens.rates

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.tsyapa.rates.ui.screens.base.BaseViewModelImpl
import com.tsyapa.rates.ui.screens.rates.data.model.Rate
import com.tsyapa.rates.utils.SingleLiveEvent
import io.reactivex.schedulers.Schedulers
import java.util.*

const val DEFAULT_BASE_CURRENCY = "EUR"
const val DEFAULT_BASE_AMOUNT = 100.0

class RatesViewModel(private val repository: RatesContract.Model, savedStateHandle: SavedStateHandle)
    : BaseViewModelImpl(savedStateHandle), RatesContract.ViewModel {

    override val liveDataUpdateRates: MutableLiveData<MutableList<Rate>> = SingleLiveEvent()
    private var baseRate = Rate(DEFAULT_BASE_CURRENCY, 1.0, DEFAULT_BASE_AMOUNT)
    private var rates: MutableList<Rate>? = null

    override fun getRates() {
        compositeDisposable.add(
            repository
                .getRates(baseRate.currency)
                .map {
                    if(rates == null){
                        rates = ratesMapToList(it)
                    } else {
                        updateRates(rates!!, it)
                    }
                    rates
                }
                .subscribeOn(Schedulers.io())
                .subscribe({
                    liveDataUpdateRates.postValue(it)
                }, {})
        )
    }

    override fun onBaseAmountChanged(newAmount: Double) {
        baseRate.amount = newAmount
        for(rate in rates!!){
            rate.amount = newAmount * rate.rate
        }
    }

    override fun onBaseCurrencyChanged(newCurrencyPosition: Int) {
        rates?.let {
            baseRate = it[newCurrencyPosition]
            baseRate.rate = 1.0
            it.removeAt(newCurrencyPosition)
            it.add(0, baseRate)
            compositeDisposable.clear()
            getRates()
        }
    }

    private fun ratesMapToList(mapRates: Map<String, Double>): MutableList<Rate> {
        val rates: MutableList<Rate> = ArrayList()
        rates.add(0, baseRate)
        for ((key, value) in mapRates) {
            rates.add(Rate(key, value, baseRate.amount * value))
        }
        return rates
    }

    private fun updateRates(rates: MutableList<Rate>, mapNewRates: Map<String, Double>){
        for(rate in rates){
            val newRate: Double? = mapNewRates[rate.currency]
            if (newRate != null && rate.rate != newRate) {
                rate.rate = newRate
                rate.amount = baseRate.amount * rate.rate
            }
        }
    }
}