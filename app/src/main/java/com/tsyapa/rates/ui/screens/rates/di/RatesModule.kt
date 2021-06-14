package com.tsyapa.rates.ui.screens.rates.di

import com.tsyapa.rates.ui.screens.rates.RatesContract
import com.tsyapa.rates.ui.screens.rates.data.repository.RatesRepository
import com.tsyapa.rates.ui.screens.rates.data.service.RatesService
import com.tsyapa.rates.utils.ViewModelProvider
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class RatesModule {

    @Provides
    fun provideRatesService(retrofit: Retrofit): RatesService = retrofit.create(RatesService::class.java)

    @Provides
    fun provideRatesRepository(ratesService: RatesService): RatesContract.Model = RatesRepository(ratesService)

    @Provides
    fun provideRatesViewModelFactory(ratesRepository: RatesContract.Model):
            ViewModelProvider<RatesContract.ViewModel> = RatesViewModelFactory(ratesRepository)
}