package com.example.heitorcolangelo.espressotests.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import br.com.concretesolutions.requestmatcher.InstrumentedTestRequestMatcherRule
import br.com.concretesolutions.requestmatcher.model.HttpMethod
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity
import org.hamcrest.CoreMatchers

/**
 * Created by andre on 20/02/18.
 */

fun load(server: InstrumentedTestRequestMatcherRule,
         activityTest: ActivityTestRule<MainActivity>,
         func: MainRobot.() -> Unit) = MainRobot(server, activityTest).apply { func() }

class MainRobot(val server: InstrumentedTestRequestMatcherRule,
                val activityTest: ActivityTestRule<MainActivity>) {

    fun serverSuccess() : MainRobot {
        server.addFixture(200, "response.json")
                .ifRequestMatches()
                .queriesContain("page", "0")
                .queriesContain("results", "20")
                .methodIs(HttpMethod.GET)

        startIntent()
        return this
    }

    fun serverFailure(codeError: Int) : MainRobot {
        server.addFixture(codeError, "responseError.json")
                .ifRequestMatches()
                .methodIs(HttpMethod.GET)

        startIntent()
        return this
    }

    private fun startIntent() {
        activityTest.launchActivity(Intent())
    }

    infix fun send(func: ResultMainRobot.() -> Unit) : ResultMainRobot {
        return ResultMainRobot().apply { func() }
    }
}

class ResultMainRobot {

    fun recyclerDisplayed() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun imageAndNameItemDisplayed() {
        Espresso.onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.user_view_image), ViewMatchers.hasSibling(ViewMatchers.withText("William Wilson"))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.user_view_name), ViewMatchers.withText("William Wilson")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun startUseDetail() {
        Intents.init()

        val matcher = CoreMatchers.allOf(
                IntentMatchers.hasComponent(UserDetailsActivity::class.java.name),
                IntentMatchers.hasExtraWithKey(UserDetailsActivity.CLICKED_USER)
        )

        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, null)

        Intents.intending(matcher).respondWith(activityResult)

        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        Intents.intended(matcher)
        Intents.release()
    }

    fun failure() {
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view)).check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
        Espresso.onView(ViewMatchers.withId(R.id.error_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}