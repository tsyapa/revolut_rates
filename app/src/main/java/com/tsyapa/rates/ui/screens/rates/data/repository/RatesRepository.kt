package com.tsyapa.rates.ui.screens.rates.data.repository

import com.tsyapa.rates.ui.screens.rates.RatesContract
import com.tsyapa.rates.ui.screens.rates.data.service.RatesService
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val SECONDS_TO_UPDATE = 1L

class RatesRepository @Inject constructor(private val service: RatesService): RatesContract.Model {

    override fun getRates(baseCurrency: String): Observable<Map<String, Double>> {
        return Observable
            .interval(0, SECONDS_TO_UPDATE, TimeUnit.SECONDS)
            .flatMap { service.getRates(baseCurrency) }
            .map { it.rates }
    }
}