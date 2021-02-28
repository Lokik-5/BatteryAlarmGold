package com.app.batteryalarmgold.utlis

import android.util.Log
import timber.log.Timber

/**
 * Use this object class to declare all the
 * logs related method.
 *
 * @author Lokik Soni
 * Created on 28-02-2021
 **/
object TimberUtils {

    /**
     * Use this function and ditch traditional [Timber.e] & [Log.e].
     * @param message the message to be shown in Logcat.
     * @param tag use this to filter the Logcat.
     *
     * @author Lokik Soni
     */
    fun error(message: String, tag: String = "BAGold") {
        Timber.tag(tag).e(message)
    }

    /**
     * Use this function and ditch traditional [Timber.d] & [Log.d].
     * @param message the message to be shown in Logcat.
     * @param tag use this to filter the Logcat.
     *
     * @author Lokik Soni
     */
    fun debug(message: String, tag: String = "BAGold") {
        Timber.tag(tag).d(message)
    }
}