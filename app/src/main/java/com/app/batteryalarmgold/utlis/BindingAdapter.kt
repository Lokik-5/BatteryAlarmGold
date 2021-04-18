package com.app.batteryalarmgold.utlis

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.databinding.BindingAdapter
import com.app.batteryalarmgold.R
import com.google.android.material.appbar.MaterialToolbar
import me.itangqi.waveloadingview.WaveLoadingView

/**
 * Use this file to declare all binding related methods.
 *
 * @author Lokik Soni
 * Created on 04-11-2020
 * Modify on 23-01-2021
 */

private const val WAVE_DURATION = 4000L

/**
 * A [BindingAdapter] to configure the [WaveLoadingView].
 *
 * @author Lokik Soni
 */
@BindingAdapter("wlvProgress")
fun WaveLoadingView.configureWaveLoadingView(batteryProgress: String?) {

    batteryProgress?.toInt()?.let { progress ->

        val wlvColor = context.themeColor(
            if (progress > 20) R.attr.wlvBatteryOkColor else R.attr.wlvBatteryLowColor
        )

        progressValue = progress
        centerTitle = "$progress%"
        waveColor = wlvColor
        borderColor = wlvColor
        centerTitleColor = if (progress > 50) context.getColor(R.color.black_800)
        else context.themeColor(R.attr.wlvCenterTitleColor)

    } ?: run { centerTitle = context.resources.getString(R.string.msg_unknown) }

    borderWidth = context.resources.getDimension(R.dimen.wlv_border_width)
    setAnimDuration(WAVE_DURATION)
}

/**
 * A [BindingAdapter] to customize the [MaterialToolbar] title.
 * Set the [title], [titleColor], [titleSize] to the [MaterialToolbar]
 * based on [startIndex] and [endIndex].
 *
 * @author Lokik Soni
 */
@BindingAdapter(
    "app:title",
    "app:titleTextColor",
    "titleSize",
    "titleColorStartIndex",
    "titleColorEndIndex"
)
fun MaterialToolbar.fancyToolbarTitle(
    title: String,
    titleColor: Int,
    titleSize: Float,
    startIndex: Int,
    endIndex: Int
) {

    SpannableStringBuilder(title).apply {
        setSpan(
            ForegroundColorSpan(titleColor),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(
            RelativeSizeSpan(titleSize),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        this@fancyToolbarTitle.title = this
    }
}

