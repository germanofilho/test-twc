package com.germanofilho.app.core.view

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.germanofilho.twc_test.BuildConfig

abstract class BaseActivity : AppCompatActivity(){
    fun getImage(icon : String) : String = BuildConfig.BASE_IMAGE_URL.plus(icon).plus("@2x.png")

    fun Activity.hideKeyboard() {
        hideKeyboard(if (currentFocus == null) View(this) else currentFocus)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}