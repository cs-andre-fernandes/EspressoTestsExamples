package com.example.heitorcolangelo.espressotests.user_detail

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.scrollTo
import android.support.test.espresso.assertion.ViewAssertions.matches

import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v4.content.ContextCompat
import com.example.heitorcolangelo.espressotests.common.PermissionUtils
import com.example.heitorcolangelo.espressotests.R
import com.example.heitorcolangelo.espressotests.matcher.withTextColor
import com.example.heitorcolangelo.espressotests.network.model.UserVO
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity
import com.google.gson.Gson
import fixtures.SingleUserMock
import org.hamcrest.CoreMatchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by andre on 15/02/18.
 */
@RunWith(AndroidJUnit4::class)
class UserDetailsActivityTest {

    @get:Rule
    private var activity = ActivityTestRule(UserDetailsActivity::class.java, false, false)

    private lateinit var user: UserVO
    private lateinit var intent: Intent

    @Before
    fun createUser() {
        intent = Intent()
    }

    @Test
    fun matchesAllViewsDisplayed() {
        initWithFullUser()

        onView(withId(R.id.user_details_image)).check(matches(isDisplayed()))
        onView(withId(R.id.user_details_address)).check(matches(isDisplayed()))
        onView(withId(R.id.user_details_email)).check(matches(isDisplayed()))
        onView(withId(R.id.user_details_name)).check(matches(isDisplayed()))
        onView(withId(R.id.user_details_phone)).check(matches(isDisplayed()))
    }

    @Test
    fun matchesNoEmailInfoProfile() {
        initWithIncompleteUser()

        onView(withId(R.id.user_details_name)).check(matches(isDisplayed()))
        onView(withId(R.id.user_details_image)).check(matches(isDisplayed()))

        onView(allOf(
                withId(R.id.image_and_text_image),
                hasSibling(withText(R.string.user_details_no_info))
                )).check(matches(isDisplayed()))

        onView(allOf(
                withText(R.string.user_details_no_info),
                withTextColor(ContextCompat.getColor(activity.activity, android.R.color.holo_red_dark))
        )).check(matches(isDisplayed()))

    }

    @Test
    fun clickPhoneAndAcceptedPermissionSuccessfully() {
        initWithFullUser()

        Intents.init()
        intending(hasAction(Intent.ACTION_CALL))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))

        onView(withId(R.id.user_details_phone)).perform(scrollTo(), click())
        PermissionUtils().allowPermissionsIfNeeded(android.Manifest.permission.CALL_PHONE)
        intended(hasAction(Intent.ACTION_CALL))
        Intents.release()
    }


    @Test
    fun clickEmailAndOpenSuccessfully() {
        initWithFullUser()

        Intents.init()
        intending(hasAction(Intent.ACTION_SEND)).respondWith(
                Instrumentation.ActivityResult(Activity.RESULT_OK, Intent()))

        onView(withId(R.id.user_details_email)).perform(scrollTo(), click())

        intended(hasAction(Intent.ACTION_SEND))
        Intents.release()

    }

    @Test
    fun clickAddressAndOpenMaps() {
        initWithFullUser()

        Intents.init()
        intending(hasAction(Intent.ACTION_VIEW)).respondWith(
                Instrumentation.ActivityResult(Activity.RESULT_OK, Intent())
        )

        onView(withId(R.id.user_details_address)).perform(scrollTo(), click())

        intended(hasAction(Intent.ACTION_VIEW))
        Intents.release()

    }


    //region Privates
    private fun createUser(isMissingInfo: Boolean) : UserVO {
        val userMock = if(isMissingInfo) SingleUserMock.userWithoutInfos() else SingleUserMock.singleUserMock()
        return Gson().fromJson(userMock, UserVO::class.java)
    }


    private fun createIntent() {
        intent.putExtra(UserDetailsActivity.CLICKED_USER, user)
        activity.launchActivity(intent)
    }

    private fun initWithFullUser() {
        user = createUser(false)
        createIntent()
    }

    private fun initWithIncompleteUser() {
        user = createUser(true)
        createIntent()
    }
    //region
}