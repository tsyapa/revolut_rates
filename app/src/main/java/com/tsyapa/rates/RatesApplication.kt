package com.tsyapa.rates

import com.tsyapa.rates.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class RatesApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val build = DaggerAppComponent.builder().application(this).build()
        build.inject(this)
        return build
    }
}