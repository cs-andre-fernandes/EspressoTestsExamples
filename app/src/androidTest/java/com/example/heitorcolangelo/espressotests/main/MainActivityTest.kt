package com.example.heitorcolangelo.espressotests.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule
import br.com.concretesolutions.requestmatcher.model.HttpMethod
import com.example.heitorcolangelo.espressotests.common.ApplicationTest
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test
import org.hamcrest.CoreMatchers.not

/**
 * Created by andre on 15/02/18.
 */

@RunWith(JUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTest = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    var server = InstrumentedTestRequestMatcherRule()

    @Before
    fun setup()  {
        val app = InstrumentationRegistry.getTargetContext().applicationContext as ApplicationTest
        app.baseUrl = server.url("/").toString()
    }

    //region Tests

    @Test
    fun shouldShowRecyclerIfRequestIsOkay() {
        createSuccessResponse()

        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
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
        createSuccessResponse()

        onView(allOf(withId(R.id.user_view_image), hasSibling(withText("William Wilson"))))
                .check(matches(isDisplayed()))
        onView(allOf(withId(R.id.user_view_name), withText("William Wilson")))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldOpenDetailActivityWithExtras() {
        createSuccessResponse()

        Intents.init()

        val matcher = allOf(
                hasComponent(UserDetailsActivity::class.java.name),
                hasExtraWithKey(UserDetailsActivity.CLICKED_USER)
        )

        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, null)

        intending(matcher).respondWith(activityResult)

        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        intended(matcher)
        Intents.release()

    }

    //region Private functions

    private fun createSuccessResponse() {
        server.addFixture(200, "response.json")
                .ifRequestMatches()
                .queriesContain("page", "0")
                .queriesContain("results", "20")
                .methodIs(HttpMethod.GET)

        activityTest.launchActivity(Intent())
    }

    private fun testUsualErrors(codeError: Int) {
        server.addFixture(codeError, "responseError.json")
                .ifRequestMatches()
                .methodIs(HttpMethod.GET)

        activityTest.launchActivity(Intent())
        onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
    }


    //endregion

}