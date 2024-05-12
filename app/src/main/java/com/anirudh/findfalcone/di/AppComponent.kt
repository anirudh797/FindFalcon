package com.anirudh.findfalcone.di

import android.app.Application
import com.anirudh.findfalcone.FalconApplication
import com.anirudh.findfalcone.di.module.ActivityModule
import com.anirudh.findfalcone.di.module.ApiModule
import com.anirudh.findfalcone.di.module.FragmentModule
import com.anirudh.findfalcone.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ApiModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class,
        AndroidInjectionModule::class
    ]
)

interface AppComponent : AndroidInjector<FalconApplication> {

    /*
     * We will call this builder interface from our custom Application class.
     * This will set our application object to the AppComponent.
     * So inside the AppComponent the application instance is available.
     * So this application instance can be accessed by our modules
     * such as ApiModule when needed
     *
     * */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    /*
     * This is our custom Application class
     * */
    override fun inject(instance: FalconApplication?)
}