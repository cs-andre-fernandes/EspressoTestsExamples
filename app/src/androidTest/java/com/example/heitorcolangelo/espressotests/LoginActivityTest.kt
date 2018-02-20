package com.example.heitorcolangelo.espressotests

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.example.heitorcolangelo.espressotests.ui.activity.LoginActivity
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by andre on 14/02/18.
 */
@RunWith(JUnit4::class)
class LoginActivityTest {

    @get:Rule
    val activityTest = ActivityTestRule(LoginActivity::class.java, false, true)


    //region Publics
    @Test
    fun whenActivityIsLaunchedShouldDisplayInitialState() {
        onView(withId(R.id.login_image)).check(matches(isDisplayed()))
        onView(withId(R.id.login_username)).check(matches(isDisplayed()))
        onView(withId(R.id.login_password)).check(matches(isDisplayed()))
        onView(withId(R.id.login_button)).check(matches(isDisplayed()))
    }

    @Test
    fun whenOnlyHasUserNameShouldDisplayErrorMessage() {
        testEmptyStateField(R.id.login_username)
    }

    @Test
    fun whenOnlyHasPasswordShouldDisplayErrorMessage() {
        testEmptyStateField(R.id.login_password)
    }

    @Test
    fun whenBothFieldsAreFilledAndClickAtButtonShouldOpenMainActivity() {
        Intents.init()

        onView(withId(R.id.login_username)).perform(typeText("default"))
        closeSoftKeyboard()
        onView(withId(R.id.login_password)).perform(typeText("default"))
        closeSoftKeyboard()

        val matcher = hasComponent(MainActivity::class.qualifiedName)

        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(activityResult)

        onView(withId(R.id.login_button)).perform(click())

        intended(matcher)
        Intents.release()
    }

    //endregion


    //region Privates
    private fun testEmptyStateField(field: Int) {
        onView(withId(field)).perform(typeText("someText"))
        closeSoftKeyboard()
        onView(withId(R.id.login_button)).perform(click())
        onView(withText(R.string.important)).check(matches(isDisplayed()))
        onView(withText(R.string.ok)).perform(click())
    }

    //endregion

}