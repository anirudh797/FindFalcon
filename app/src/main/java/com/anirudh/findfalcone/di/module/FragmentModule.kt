package com.anirudh.findfalcone.di.module

import com.anirudh.findfalcone.view.fragments.FindFalconFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeFindFalconFragment(): FindFalconFragment
}