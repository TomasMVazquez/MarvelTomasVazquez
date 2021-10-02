package com.toms.applications.marveltomasvazquez

import android.app.Application
import com.toms.applications.marveltomasvazquez.di.initDI

class MarvelApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

}