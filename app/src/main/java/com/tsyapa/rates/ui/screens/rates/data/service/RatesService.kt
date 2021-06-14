package com.tsyapa.rates.ui.screens.rates.data.service

import com.tsyapa.rates.ui.screens.rates.data.model.RatesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

const val LATEST = "latest"
const val BASE = "base"

interface RatesService {

    @GET(LATEST)
    fun getRates(@Query(BASE) base: String?): Observable<RatesResponse>
}