package com.example.heitorcolangelo.espressotests.user_detail

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.annotation.IntegerRes
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.v4.content.ContextCompat
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.common.PermissionUtils
import com.example.heitorcolangelo.espressotests.matcher.withTextColor
import com.example.heitorcolangelo.espressotests.network.model.UserVO
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity
import com.google.gson.Gson
import fixtures.SingleUserMock
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf

/**
 * Created by andre on 20/02/18.
 */

fun load(activityTestRule: ActivityTestRule<UserDetailsActivity>,
         func: UserDetailRobot.() -> Unit) = UserDetailRobot(activityTestRule).apply { func() }


class UserDetailRobot(private val activityTestRule: ActivityTestRule<UserDetailsActivity>) {

    lateinit var user: UserVO

    fun userComplete() : UserDetailRobot {
        user = createUser(SingleUserMock.singleUserMock())
        intent()
        return this
    }

    fun userIncomplete() : UserDetailRobot {
        user = createUser(SingleUserMock.userWithoutInfos())
        intent()
        return this
    }

    private fun intent() {
        val intent = Intent()
        intent.putExtra(UserDetailsActivity.CLICKED_USER, user)
        activityTestRule.launchActivity(intent)
    }

    infix fun send(func: UserDetailRobotResult.() -> Unit) : UserDetailRobotResult {
        return UserDetailRobotResult().apply { func() }
    }

    //region Private functions

    fun  createUser(userString: String) : UserVO {
        return Gson().fromJson(userString, UserVO::class.java)
    }
}

class UserDetailRobotResult {

    fun initialStateSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.user_details_image)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.user_details_address)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.user_details_email)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.user_details_name)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.user_details_phone)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun withoutInfoMessage(activityTestRule: ActivityTestRule<UserDetailsActivity>) {
        onView(ViewMatchers.withId(R.id.user_details_name)).check(matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.user_details_image)).check(matches(ViewMatchers.isDisplayed()))

        onView(CoreMatchers.allOf(
                ViewMatchers.withId(R.id.image_and_text_image),
                ViewMatchers.hasSibling(withText(R.string.user_details_no_info))
        )).check(ViewAssertions.matches(isDisplayed()))

        onView(allOf(
                withText(R.string.user_details_no_info),
                withTextColor(ContextCompat.getColor(activityTestRule.activity, android.R.color.holo_red_dark))
        )).check(ViewAssertions.matches(isDisplayed()))
    }

    fun permissionAccepted() {
        openAction(Intent.ACTION_CALL, R.id.user_details_phone, {
            PermissionUtils().allowPermissionsIfNeeded(android.Manifest.permission.CALL_PHONE)
        })
    }

    fun openEmailSuccessFully() {
        openAction(Intent.ACTION_SEND, R.id.user_details_email)
    }

    fun openMapsSuccessfully() {
        openAction(Intent.ACTION_VIEW, R.id.user_details_address)
    }

    private fun openAction(action: String, @IntegerRes id: Int,
                           permissionFunction: UserDetailRobotResult.() -> Unit = {}) {
        Intents.init()
        intending(hasAction(action)).respondWith(
                Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))
        onView(withId(id)).perform(scrollTo(), click())
        permissionFunction()
        intended(hasAction(action))
        Intents.release()
    }
}