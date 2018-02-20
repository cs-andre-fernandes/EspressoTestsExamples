package com.example.heitorcolangelo.espressotests.matcher

import android.support.annotation.ColorInt
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.TextView
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created by andre on 16/02/18.
 */
inline fun withTextColor(@ColorInt colorExpected: Int) : Matcher<View> = object : BoundedMatcher<View, TextView>(TextView::class.java) {

    var currentColor = 0

    override fun describeTo(description: Description?) {
        description?.appendText("Expected color: #${Integer.toHexString(colorExpected)}\n")
        description?.appendText("Current color: #${Integer.toHexString(currentColor)}")
    }

    override fun matchesSafely(item: TextView?): Boolean {
        if(currentColor == 0) {
            currentColor = item?.currentTextColor ?: 0
        }

        return currentColor == colorExpected
    }
}