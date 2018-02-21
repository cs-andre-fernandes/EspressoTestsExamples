package com.example.heitorcolangelo.espressotests.main

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule
import com.example.heitorcolangelo.espressotests.common.ApplicationTest
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.junit.Rule

/**
 * Created by andre on 15/02/18.
 */

@RunWith(JUnit4::class)
class MainActivityTest {

    @get:Rule
    val server = InstrumentedTestRequestMatcherRule()

    @get:Rule
    val activityTest = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Before
    fun setup()  {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as ApplicationTest
        app.baseUrl = server.url("/").toString()
    }

    //region Tests

    @Test
    fun shouldShowRecyclerIfRequestIsOkay() {
        load(server, activityTest) {
            serverSuccess()
        } send {
            recyclerDisplayed()
        }
    }

    @Test
    fun shouldShowErrorWithBadRequest() {
        testUsualErrors(400)
    }

    @Test
    fun shouldShowErrorWithInternalErrorServer() {
        testUsualErrors(500)
    }

    //endregion

    @Test
    fun imageAndNameIsDisplayedWithSuccessResponse() {
        load(server, activityTest) {
            serverSuccess()
        } send {
            imageAndNameItemDisplayed()
        }
    }

    @Test
    fun shouldOpenDetailActivityWithExtras() {
        load(server, activityTest) {
            serverSuccess()
        } send {
            startUseDetail()
        }
    }

    //region Private functions

    private fun testUsualErrors(codeError: Int) {
        load(server, activityTest) {
            serverFailure(codeError)
        } send {
            failure()
        }
    }
    //endregion

}