package com.example.heitorcolangelo.espressotests.user_detail

import android.content.Intent

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.heitorcolangelo.espressotests.network.model.UserVO
import com.example.heitorcolangelo.espressotests.ui.activity.UserDetailsActivity
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
        load(activity) {
            userComplete()
        } send {
            initialStateSuccess()
        }
    }

    @Test
    fun matchesNoEmailInfoProfile() {
        load(activity) {
            userIncomplete()
        } send {
            withoutInfoMessage(activity)
        }
    }

    @Test
    fun clickPhoneAndAcceptedPermissionSuccessfully() {
        load(activity) {
            userComplete()
        } send {
            permissionAccepted()
        }
    }


    @Test
    fun clickEmailAndOpenSuccessfully() {
        load(activity) {
            userComplete()
        } send {
            openEmailSuccessFully()
        }
    }

    @Test
    fun clickAddressAndOpenMaps() {
        load(activity) {
            userComplete()
        } send {
            openMapsSuccessfully()
        }
    }
}