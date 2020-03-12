package com.gousto.philip.arnold

import android.app.Application
import android.util.Log
import com.gousto.philip.arnold.di.appModules
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GuostoApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        startKoin {
            androidContext(this@GuostoApplication)
            modules(appModules)
        }
    }
}