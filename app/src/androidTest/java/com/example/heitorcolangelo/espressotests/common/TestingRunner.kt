package com.example.heitorcolangelo.espressotests.common

import android.app.Application
import android.content.Context
import android.support.test.runner.AndroidJUnitRunner

/**
 * Created by andre on 15/02/18.
 */
class TestingRunner : AndroidJUnitRunner() {

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, ApplicationTest::class.java.name, context)
    }

}