package com.app.batteryalarmgold.utlis

import android.graphics.Typeface
import androidx.databinding.BindingAdapter
import com.google.android.material.appbar.MaterialToolbar

/**
 * Use this file to declare all binding related methods.
 *
 * @author Lokik Soni
 * Created on 04-11-2020
 * Modify on 23-01-2021
 */

/**
 * A [BindingAdapter] to customize the [MaterialToolbar] title.
 * Set the [title], [titleColor], [titleSize] and [titleStyle]
 * (ex. [Typeface.BOLD])
 * to the [MaterialToolbar] based on [startIndex] and [endIndex]
 * with the help of [fancyStringBuilder].
 *
 * @author Lokik Soni
 */
@BindingAdapter(
    "app:title",
    "app:titleTextColor",
    "titleSize",
    "titleStyle",
    "titleColorStartIndex",
    "titleColorEndIndex"
)
fun MaterialToolbar.fancyToolbarTitle(
    title: String,
    titleColor: Int,
    titleSize: Float,
    titleStyle: Int,
    startIndex: Int,
    endIndex: Int
) {
    this@fancyToolbarTitle.title =
        fancyStringBuilder(title, titleColor, titleSize, titleStyle, startIndex, endIndex)
}

