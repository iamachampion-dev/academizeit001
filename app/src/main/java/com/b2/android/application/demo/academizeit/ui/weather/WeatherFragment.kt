package com.b2.android.application.demo.academizeit.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.b2.android.application.demo.academizeit.R
import com.b2.android.application.demo.academizeit.databinding.WeatherFragmentBinding

class WeatherFragment: Fragment() {

    private lateinit var viewDataBinding: WeatherFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.weather_fragment, container, false)
        viewDataBinding = WeatherFragmentBinding.bind(root).apply {
            this.viewmodel = WeatherFragmentViewModel()
        }

        return viewDataBinding.root//super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewDataBinding.viewmodel?.apply {
            getJSONDataFromHttp()
            temperature?.observe(viewLifecycleOwner, {
                viewDataBinding.temperatureValue.text = it
            })
            weatherInfo?.observe(viewLifecycleOwner, {
                viewDataBinding.weatherInfo.text = it
            })
            jsonFromHttp?.observe(viewLifecycleOwner, {
                getDataFromJSON(it)
            })
            weatherInfoImage.observe(viewLifecycleOwner, {
                viewDataBinding.weatherImage.setImageBitmap(it)
            })
        }

    }
}