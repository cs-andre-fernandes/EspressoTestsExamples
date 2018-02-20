package com.example.heitorcolangelo.espressotests.login

import android.app.Activity
import android.app.Instrumentation
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.closeSoftKeyboard
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.withId
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity

/**
 * Created by andre on 20/02/18.
 */

fun login(func: LoginRobot.() -> Unit) = LoginRobot().apply { func() }

class LoginRobot {

    fun username(userName: String): LoginRobot {
        fillInput(R.id.login_username, userName)
        return this
    }

    fun password(password:String) : LoginRobot {
        fillInput(R.id.login_password, password)
        return this
    }

    infix fun send(func: ResultRobot.() -> Unit) : ResultRobot {
        return ResultRobot().apply {  func() }
    }

    //region Private Methods
    private fun fillInput(field: Int, text: String) {
        onView(withId(field)).perform(typeText(text))
        closeSoftKeyboard()
    }
    //endregion
}

class ResultRobot {
    fun isSuccess() {
        Intents.init()

        val matcher = hasComponent(MainActivity::class.qualifiedName)

        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, null)
        intending(matcher).respondWith(activityResult)

        onView(withId(R.id.login_button)).perform(click())

        intended(matcher)
        Intents.release()
    }

    fun isEmptyStateFailure() {
        onView(withId(R.id.login_button)).perform(click())
        onView(ViewMatchers.withText(R.string.important)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withText(R.string.ok)).perform(click())
    }

    fun initialStateSuccess() {
        onView(withId(R.id.login_image)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.login_username)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.login_password)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.login_button)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}