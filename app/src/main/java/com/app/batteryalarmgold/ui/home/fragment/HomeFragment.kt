package com.app.batteryalarmgold.ui.home.fragment

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.app.batteryalarmgold.R
import com.app.batteryalarmgold.databinding.FragmentHomeBinding
import com.app.batteryalarmgold.ui.viewmodel.MainViewModel
import com.app.core.fragment.BaseFragment

/**
 * This [HomeFragment] extends the [BaseFragment] and display the
 * battery information like level, temperature etc.
 *
 * @author Lokik Soni
 * Created on 28-02-2021
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>(), View.OnClickListener {

    private val _viewModel by activityViewModels<MainViewModel>()

    override fun setLayout() = R.layout.fragment_home

    override fun onCreateView(binding: FragmentHomeBinding) {

        binding.apply {
            viewModel = _viewModel
            lifecycleOwner = viewLifecycleOwner

            // TODO get the temperature and voltage from sharedPref.
            _viewModel.voltageUnit = MainViewModel.VoltageUnit.VOLTS
            _viewModel.temperatureUnit = MainViewModel.TemperatureUnit.FAHRENHEIT

            btnHomeAlarmOff.setOnClickListener(this@HomeFragment)
            btnHomeAlarmOn.setOnClickListener(this@HomeFragment)
        }
    }

    override fun onClick(v: View) {

        when (v.id) {

            R.id.btn_home_alarm_off -> {
                Toast.makeText(context, "Off", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_home_alarm_on -> {
                Toast.makeText(context, "On", Toast.LENGTH_SHORT).show()
            }
        }
    }
}