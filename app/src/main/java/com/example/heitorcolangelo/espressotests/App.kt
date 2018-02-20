package com.example.heitorcolangelo.espressotests

import android.app.Application

/**
 * Created by andre on 15/02/18.
 */
open class App : Application() {

    open fun getUrl() = BuildConfig.BASE_URL

}