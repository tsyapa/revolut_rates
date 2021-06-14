package com.tsyapa.rates.di

import android.app.Application
import com.tsyapa.rates.RatesApplication
import com.tsyapa.rates.di.modules.ActivityBuildersModule
import com.tsyapa.rates.di.modules.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApiModule::class,
    ActivityBuildersModule::class
])
interface AppComponent: AndroidInjector<RatesApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }
}