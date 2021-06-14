package com.tsyapa.rates.di.modules

import com.tsyapa.rates.ui.screens.rates.RatesActivity
import com.tsyapa.rates.ui.screens.rates.di.RatesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {

    @ContributesAndroidInjector(modules = [ RatesModule::class ])
    abstract fun contributeRatesActivityV2(): RatesActivity
}