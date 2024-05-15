package com.anirudh.findfalcone.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anirudh.findfalcone.di.ViewModelKey
import com.anirudh.findfalcone.view.viewModel.FindFalconViewModel
import com.anirudh.findfalcone.view.viewModel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
@Module
public abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
    @Binds
    @IntoMap
    @ViewModelKey(FindFalconViewModel::class)
    protected abstract fun findFalconViewModel(findFalconViewModel: FindFalconViewModel): ViewModel

}