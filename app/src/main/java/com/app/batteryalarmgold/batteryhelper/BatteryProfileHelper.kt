package com.app.batteryalarmgold.batteryhelper

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
 * A lifecycle observable [BatteryProfileHelper].
 *
 * Observe the [batteryDetail] to get the battery-profile
 * like level, temperature etc.
 *
 * @author Lokik Soni
 * Created on 03-10-2020
 * Modify on 18-04-2021
 */
object BatteryProfileHelper : LifecycleObserver {

    // Private fields
    private val batteryDetailMap = HashMap<BatteryProfile, String?>()
    private val batteryDetailLiveData = MutableLiveData<HashMap<BatteryProfile, String?>>()
    private lateinit var ctx: Application

    // Public fields
    val batteryDetail = batteryDetailLiveData.asLiveData()

    /**
     * This function must be called before any other functions.
     */
    fun init(context: Application, lifecycle: Lifecycle) {
        ctx = context
        lifecycle.addObserver(this)
    }

    /**
     * BroadcastReceiver to listen battery changes.
     */
    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            context?.let { ctx ->

                intent?.apply {

                    batteryDetailMap[BatteryProfile.LEVEL] = getBatteryIntExtra(EXTRA_LEVEL)?.toString()
                    batteryDetailMap[BatteryProfile.TEMPERATURE] = getBatteryIntExtra(EXTRA_TEMPERATURE)?.toString()
                    batteryDetailMap[BatteryProfile.VOLTAGE] = getBatteryIntExtra(EXTRA_VOLTAGE)?.toString()
                    batteryDetailMap[BatteryProfile.TECHNOLOGY] = getBatteryStringExtra(ctx, EXTRA_TECHNOLOGY)
                    batteryDetailMap[BatteryProfile.PLUGGED] = getBatteryPlugged(EXTRA_PLUGGED)
                    batteryDetailMap[BatteryProfile.HEALTH] = getBatteryHealth(EXTRA_HEALTH)
                    batteryDetailMap[BatteryProfile.STATUS] = getBatteryStatus(EXTRA_STATUS)

                    batteryDetailLiveData.value = batteryDetailMap

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
     * This function will get call automatically with the help of
     * [LifecycleObserver] when the the application/service run by user.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun registerReceiver() {

        // Throw the BatteryAlarmException i.e INIT_ERROR when init method
        // of BatteryProfileHelper is not get called.
        if (!(::ctx.isInitialized))
            throw BatteryAlarmGoldException(BatteryAlarmGoldException.Error.INIT_ERROR)

        // Register the broadcastReceiver
        ctx.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        TimberUtils.debug("registerReceiver called.")
    }

    /**
     * This function will get call automatically with the help of
     * [LifecycleObserver] when the application/service killed by user
     * or the application goes into background.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregisterReceiver() {
        TimberUtils.debug("unregisterReceiver called.")

        // Unregister the broadcastReceiver
        ctx.unregisterReceiver(receiver)
    }

    /**
     * Enum class [BatteryProfile] to extract the battery details from
     * [batteryDetailMap] using these keys like [LEVEL], [TEMPERATURE].
     */
    enum class BatteryProfile {
        LEVEL,
        TEMPERATURE,
        VOLTAGE,
        TECHNOLOGY,
        PLUGGED,
        HEALTH,
        STATUS
    }

    // Constant fields
    private const val DEF_VAL = -1
}