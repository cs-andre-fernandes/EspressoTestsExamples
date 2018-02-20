package com.example.heitorcolangelo.espressotests

import android.app.Application
import android.test.ApplicationTestCase

/**
 * [Testing Fundamentals](http://d.android.com/tools/testing/testing_android.html)
 */
class ApplicationTest : App() {

    lateinit var baseUrl: String

    override fun getUrl() = baseUrl

}