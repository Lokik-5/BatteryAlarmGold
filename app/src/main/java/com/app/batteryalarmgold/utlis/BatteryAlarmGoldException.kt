package com.app.batteryalarmgold.utlis

import java.lang.Exception

/**
 * Exception thrown by entire Battery Alarm Gold.
 *
 * @author Lokik Soni
 * Created on 10/10/2020
 * Modify on 28/02/2021
 */
class BatteryAlarmGoldException(private val error: Error) : Exception(error.message) {

    fun print() {
        TimberUtils.error("Battery Alarm Gold Exception: ${error.message}")

        printStackTrace()
    }

    enum class Error(val message: String, val userMessage: String? = null) {

        INIT_ERROR("Call init function of BatteryManager.")
    }
}