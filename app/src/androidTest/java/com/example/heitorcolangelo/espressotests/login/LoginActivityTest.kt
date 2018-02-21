package com.example.heitorcolangelo.espressotests.login

import android.app.Activity
import android.app.Instrumentation
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
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.ui.activity.LoginActivity
import com.example.heitorcolangelo.espressotests.ui.activity.MainActivity
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
        login { send { initialStateSuccess() } }
    }

    @Test
    fun whenOnlyHasUserNameShouldDisplayErrorMessage() {
        login {
            username("username")
        } send {
            isEmptyStateFailure()
        }
    }

    @Test
    fun whenOnlyHasPasswordShouldDisplayErrorMessage() {
        login {
            password("password")
        } send {
            isEmptyStateFailure()
        }
    }

    @Test
    fun whenBothFieldsAreFilledAndClickAtButtonShouldOpenMainActivity() {
        login {
            username("whatever")
            password("whatever")
        } send {
            isSuccess()
        }
    }

    //endregion


}