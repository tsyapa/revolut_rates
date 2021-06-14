package com.tsyapa.rates.ui.screens.rates

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.SimpleItemAnimator
import com.tsyapa.rates.databinding.ActivityRatesBinding
import com.tsyapa.rates.ui.screens.base.BaseActivity
import com.tsyapa.rates.utils.ViewModelProvider
import javax.inject.Inject

class RatesActivity: BaseActivity<RatesContract.ViewModel>() {

    private lateinit var binding: ActivityRatesBinding

    private val rateAdapter = RateAdapter(
        { viewModel.onBaseAmountChanged(it) },
        { viewModel.onBaseCurrencyChanged(it) }
    )

    @Inject
    lateinit var provider: ViewModelProvider<RatesContract.ViewModel>

    override fun provideViewModelProvider(): ViewModelProvider<RatesContract.ViewModel> = provider

    override fun getRoot() = binding.root

    override fun initializeBinder() { binding = ActivityRatesBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding.recyclerView){
            adapter = rateAdapter
            if(itemAnimator is SimpleItemAnimator){
                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }

        viewModel.liveDataUpdateRates.observe(this, Observer {
            rateAdapter.setRates(it)
        })

        viewModel.getRates()
    }
}