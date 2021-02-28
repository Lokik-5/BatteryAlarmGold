package com.app.batteryalarmgold.batterymanager

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager.*
import androidx.lifecycle.*
import com.app.batteryalarmgold.R
import com.app.batteryalarmgold.utlis.BatteryAlarmGoldException
import com.app.batteryalarmgold.utlis.TimberUtils
import com.app.batteryalarmgold.utlis.asLiveData

/**
 * A lifecycle observable BroadcastReceiver [BatteryManager]
 * responsible for receiving the broadcast intents.
 *
 * Observe the [batteryDetails] to get the battery-details
 * like level, temperature etc.
 *
 * @author Lokik Soni
 * Created on 03-10-2020
 * Modify on 28-02-2021
 */
object BatteryManager : LifecycleObserver {

    // Private fields
    private lateinit var ctx: Application
    private val batteryDetailMap = HashMap<String, String?>()
    private val batteryDetailsLiveData = MutableLiveData<HashMap<String, String?>>()

    // Public fields
    val batteryDetails = batteryDetailsLiveData.asLiveData()

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            context?.let { ctx ->

                intent?.let { intent ->

                    intent.apply {

                        batteryDetailMap[EXTRA_LEVEL] = getBatteryIntExtra(EXTRA_LEVEL)?.toString()
                        batteryDetailMap[EXTRA_TEMPERATURE] =
                            getBatteryIntExtra(EXTRA_TEMPERATURE)?.toString()
                        batteryDetailMap[EXTRA_VOLTAGE] = getBatteryIntExtra(EXTRA_VOLTAGE)?.toString()
                        batteryDetailMap[EXTRA_TECHNOLOGY] =
                            getBatteryStringExtra(ctx, EXTRA_TECHNOLOGY)
                        batteryDetailMap[EXTRA_PLUGGED] = getBatteryPlugged(EXTRA_PLUGGED)
                        batteryDetailMap[EXTRA_HEALTH] = getBatteryHealth(EXTRA_HEALTH)
                        batteryDetailMap[EXTRA_STATUS] = getBatteryStatus(EXTRA_STATUS)

                        batteryDetailsLiveData.value = batteryDetailMap
                    }

                } ?: TimberUtils.error("BroadcastReceiver onReceive Intent is NULL")

            } ?: TimberUtils.error("BroadcastReceiver onReceive Context is NULL")
        }
    }

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     *
     * @return the value for the desired item [name] or
     * the null value if none was found.
     *
     * @author Lokik Soni
     */
    private fun Intent.getBatteryIntExtra(name: String) =
        getIntExtra(name, DEF_VAL).takeIf { it != DEF_VAL }

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     *
     * @return the value for the desired item [name] or
     * the default string if none was found.
     *
     * @author Lokik Soni
     */
    private fun Intent.getBatteryStringExtra(context: Context, name: String) =
        getStringExtra(name) ?: context.getString(R.string.msg_unknown)

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     *
     * @return the value for the desired item [name] or
     * the else value if none was found.
     *
     * @author Lokik Soni
     */
    private fun Intent.getBatteryPlugged(name: String) = ctx.run {
        when (getIntExtra(name, DEF_VAL)) {

            BATTERY_PLUGGED_AC -> getString(R.string.plugged_ac)
            BATTERY_PLUGGED_USB -> getString(R.string.plugged_usb)
            BATTERY_PLUGGED_WIRELESS -> getString(R.string.plugged_wireless)
            else -> getString(R.string.plugged_not_plugged)
        }
    }

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     *
     * @return the value for the desired item [name] or
     * the else value if none was found.
     *
     * @author Lokik Soni
     */
    private fun Intent.getBatteryHealth(name: String) = ctx.run {
        when (getIntExtra(name, DEF_VAL)) {

            BATTERY_HEALTH_COLD -> getString(R.string.health_cold)
            BATTERY_HEALTH_DEAD -> getString(R.string.health_dead)
            BATTERY_HEALTH_GOOD -> getString(R.string.health_good)
            BATTERY_HEALTH_OVERHEAT -> getString(R.string.health_overheat)
            BATTERY_HEALTH_OVER_VOLTAGE -> getString(R.string.health_over_voltage)
            BATTERY_HEALTH_UNSPECIFIED_FAILURE -> getString(R.string.health_failure)
            else -> getString(R.string.msg_unknown)
        }
    }

    /**
     * Retrieve extended data from the intent.
     *
     * @param name The name of the desired item.
     *
     * @return the value for the desired item [name] or
     * the else value if none was found.
     *
     * @author Lokik Soni
     */
    private fun Intent.getBatteryStatus(name: String) = ctx.run {
        when (getIntExtra(name, DEF_VAL)) {

            BATTERY_STATUS_CHARGING -> getString(R.string.charging)
            BATTERY_STATUS_DISCHARGING -> getString(R.string.discharging)
            BATTERY_STATUS_FULL -> getString(R.string.full)
            BATTERY_STATUS_NOT_CHARGING -> getString(R.string.not_charging)
            else -> getString(R.string.msg_unknown)
        }
    }

    /**
     * This function must be called before any other
     * functions.
     */
    fun init(context: Application) {
        ctx = context
    }

    /**
     * This function will get call automatically with the help of
     * [LifecycleObserver] when the application/service run by user.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun registerReceiver() {
        TimberUtils.debug("registerReceiver called.")

        // Throw the BatteryAlarmException i.e INIT_ERROR when init method
        // of BatteryBroadcastManager is not get called.
        if (!(::ctx.isInitialized))
            throw BatteryAlarmGoldException(BatteryAlarmGoldException.Error.INIT_ERROR)

        // Register the broadcastReceiver
        ctx.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    /**
     * This function will get call automatically with the help of
     * [LifecycleObserver] when the application/service killed by user
     * or application goes into background.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregisterReceiver() {
        TimberUtils.debug("unregisterReceiver called.")

        // Unregister the broadcastReceiver
        ctx.unregisterReceiver(receiver)
    }

    // Constant fields
    private const val DEF_VAL = -1
}