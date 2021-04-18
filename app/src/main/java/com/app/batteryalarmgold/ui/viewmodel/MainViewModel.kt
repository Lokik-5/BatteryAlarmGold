package com.app.batteryalarmgold.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.app.batteryalarmgold.batteryhelper.BatteryProfileHelper
import com.app.batteryalarmgold.batteryhelper.BatteryProfileHelper.BatteryProfile
import kotlin.math.round

/**
 * Use this [MainViewModel] to access the battery detail.
 *
 * @author Lokik Soni
 * Created on 18-10-2020
 * Modify on 17-04-2021
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {

    // Private fields
    private val _batteryDetailMap = HashMap<BatteryProfile, String?>()
    private val _batteryDetailLiveData = MutableLiveData<HashMap<BatteryProfile, String?>>()
    private var _temperature: Float? = null
    private var _voltage: Float? = null

    // Public fields
    var batteryDetail = BatteryProfileHelper.batteryDetail.switchMap {
        _batteryDetailMap.putAll(it)
        _temperature = it[BatteryProfile.TEMPERATURE]?.toFloat()
        _voltage = it[BatteryProfile.VOLTAGE]?.toFloat()
        computeBatteryTemperature()
        computeBatteryVoltage()
        _batteryDetailLiveData
    }

    // To set the battery temperature
    var temperatureUnit = TemperatureUnit.CELSIUS
        set(value) {
            field = value
            computeBatteryTemperature()
        }

    // To set the battery voltage
    var voltageUnit = VoltageUnit.VOLTS
        set(value) {
            field = value
            computeBatteryVoltage()
        }

    /**
     * To compute the battery [BatteryProfile.TEMPERATURE] like in
     * [TemperatureUnit.CELSIUS], [TemperatureUnit.FAHRENHEIT] and
     * [TemperatureUnit.KELVIN].
     */
    private fun computeBatteryTemperature() {

        // To compute the battery temperature
        _temperature?.let {

            _batteryDetailMap[BatteryProfile.TEMPERATURE] = when (temperatureUnit) {

                TemperatureUnit.CELSIUS -> round(it.div(10))
                TemperatureUnit.FAHRENHEIT -> round((it.div(10)) * 9 / 5 + 32)
                TemperatureUnit.KELVIN -> it

            }.toString()
        }

        _batteryDetailLiveData.value = _batteryDetailMap
    }


    /**
     * To compute the battery [BatteryProfile.VOLTAGE] like in [VoltageUnit.VOLTS]
     * and [VoltageUnit.MILLI_VOLTS].
     */
    private fun computeBatteryVoltage() {

        // To compute the battery voltage
        _voltage?.let {

            _batteryDetailMap[BatteryProfile.VOLTAGE] = when (voltageUnit) {

                VoltageUnit.VOLTS -> it.div(1000)
                VoltageUnit.MILLI_VOLTS -> it.toLong()

            }.toString()
        }

        _batteryDetailLiveData.value = _batteryDetailMap
    }

    /**
     * Enum class for temperature unit.
     * @param unit to get the string for the enum
     */
    enum class TemperatureUnit(val unit: String) {
        CELSIUS("°C"),
        FAHRENHEIT("°F"),
        KELVIN("°K")
    }

    /**
     * Enum class for voltage unit.
     * @param unit to get the string for the enum.
     */
    enum class VoltageUnit(val unit: String) {
        MILLI_VOLTS("mV"),
        VOLTS("V")
    }

}