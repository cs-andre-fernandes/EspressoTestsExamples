package com.example.heitorcolangelo.espressotests.common

import com.example.heitorcolangelo.espressotests.App

/**
 * [Testing Fundamentals](http://d.android.com/tools/testing/testing_android.html)
 */
class ApplicationTest : App() {

    lateinit var baseUrl: String

    override fun getUrl() = baseUrl

}