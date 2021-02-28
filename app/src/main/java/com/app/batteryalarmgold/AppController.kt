package com.app.batteryalarmgold

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.app.batteryalarmgold.batterymanager.BatteryManager
import com.app.batteryalarmgold.utlis.ReleaseTree
import timber.log.Timber

/**
 * An Application class [AppController] is
 * the base class of Android Application.
 *
 * @author Lokik Soni
 * Created on 28/02/2021
 **/
class AppController : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the BatteryBroadcastManager.
        BatteryManager.init(this)

        // Attach the BatteryBroadcastManager with application lifeCycle.
        ProcessLifecycleOwner.get().lifecycle.addObserver(BatteryManager)

        // Initializes the Timber library
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(ReleaseTree())
    }
}