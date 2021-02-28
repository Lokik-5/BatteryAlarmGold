package com.app.batteryalarmgold.utlis

import android.util.Log
import timber.log.Timber

/**
 * A [ReleaseTree] to get the crash report
 * in production level.
 *
 * @author Lokik Soni
 * Created on 28-02-2021
 **/
class ReleaseTree: Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

        if (priority == Log.ERROR || priority == Log.WARN) {

            // TODO log your crash to your favourite

            // Sending crash report to Firebase CrashAnalytics
            // FirebaseCrash.report(message);
            // FirebaseCrash.report(new Exception(message));
        }
    }
}