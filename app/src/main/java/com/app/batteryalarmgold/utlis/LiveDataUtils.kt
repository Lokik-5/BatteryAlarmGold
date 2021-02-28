package com.app.batteryalarmgold.utlis

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Use this file to declare all the helper
 * or extension function related to the LiveData.
 *
 * @author Lokik Soni
 * Created on 28-02-2021
 */

/**
 * This is an extension function to the [MutableLiveData].
 *
 * @author Lokik Soni
 */
fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>