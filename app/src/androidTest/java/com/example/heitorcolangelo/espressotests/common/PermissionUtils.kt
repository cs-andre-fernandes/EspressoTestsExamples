package com.example.heitorcolangelo.espressotests.common

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import android.support.test.uiautomator.UiObjectNotFoundException
import android.support.test.uiautomator.UiSelector
import android.support.test.InstrumentationRegistry
import android.support.v4.content.ContextCompat

/**
 * Created by andre on 16/02/18.
 */
class PermissionUtils {

    private val PERMISSIONS_DIALOG_DELAY = 3000L
    private val GRANT_BUTTON_INDEX = 1

    fun allowPermissionsIfNeeded(permissionNeeded: String) {
        try {
            val context = InstrumentationRegistry.getTargetContext()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !hasNeededPermission(context, permissionNeeded)) {
                sleep()
                val device = UiDevice.getInstance(getInstrumentation())
                val allowPermissions = device.findObject(UiSelector()
                        .clickable(true)
                        .checkable(false)
                        .index(GRANT_BUTTON_INDEX))
                if (allowPermissions.exists()) {
                    allowPermissions.click()
                }
            }
        } catch (e: UiObjectNotFoundException) {
            println("There is no permissions dialog to interact with")
        }

    }

    private fun hasNeededPermission(context: Context, permissionNeeded: String): Boolean {
        val permissionStatus = ContextCompat.checkSelfPermission(context, permissionNeeded)
        return permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    private fun sleep() {
        try {
            Thread.sleep(PERMISSIONS_DIALOG_DELAY)
        } catch (e: InterruptedException) {
            throw RuntimeException("Cannot execute Thread.sleep()")
        }

    }
}