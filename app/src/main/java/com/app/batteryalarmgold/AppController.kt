package com.app.batteryalarmgold

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.app.batteryalarmgold.batteryhelper.BatteryProfileHelper
import com.app.batteryalarmgold.utlis.ReleaseTree
import timber.log.Timber

/**
 * An application class [AppController] is
 * the base class of Android Application.
 *
 * @author Lokik Soni
 * Created on 28/02/2021
 **/
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the BatteryManager and attach to the application lifeCycle.
        BatteryProfileHelper.init(this, ProcessLifecycleOwner.get().lifecycle)

        // Initializes the Timber library
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(ReleaseTree())
    }
}