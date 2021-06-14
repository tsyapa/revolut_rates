package com.tsyapa.rates.ui.screens.base

import android.animation.AnimatorInflater
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.tsyapa.rates.R
import com.tsyapa.rates.utils.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity<VM : BaseViewModel> : DaggerAppCompatActivity(), ViewBinding {

    lateinit var viewModel: VM
    var toolbar: View? = null
    var recyclerView: RecyclerView? = null

    abstract fun provideViewModelProvider(): ViewModelProvider<VM>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeBinder()
        setContentView(root)
        requestedOrientation = provideOrientation()

        provideViewModelProvider()?.let {
            viewModel = it.provideViewModel(this, this)
        }

        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        setToolbarElevationOnScroll(toolbar, recyclerView)

        viewModel.errorLiveData.observe(this, { showError(it) })
    }

    open fun showError(error: Throwable) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
    }

    abstract fun initializeBinder()

    open fun provideOrientation() = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    private fun setToolbarElevationOnScroll(toolbar: View?, scrollableView: View?) {
        if (toolbar != null && scrollableView != null) {
            toolbar.stateListAnimator = AnimatorInflater.loadStateListAnimator(this,
                R.animator.sla_toolbar)
            scrollableView.viewTreeObserver.addOnScrollChangedListener {
                toolbar.isSelected = scrollableView.canScrollVertically(-1)
            }
        }
    }
}