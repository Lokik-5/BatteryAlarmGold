package com.app.batteryalarmgold.utlis

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan

/**
 * @author Lokik Soni
 * Created on 10-06-2021
 **/

/**
 * Use this method to create a customizable string.
 * Sample data
 * @param text "title"
 * @param textColor @color/red
 * @param textSize 2.1f
 * @param textStyle TypeFace.Bold
 * @param startIndex 1
 * @param endIndex 5
 *
 * Created on 10-06-2021
 */
fun fancyStringBuilder(
    text: String,
    textColor: Int,
    textSize: Float,
    textStyle: Int,
    startIndex: Int,
    endIndex: Int
): SpannableStringBuilder {

    return SpannableStringBuilder(text).apply {
        setSpan(
            ForegroundColorSpan(textColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            RelativeSizeSpan(textSize),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            StyleSpan(textStyle),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}