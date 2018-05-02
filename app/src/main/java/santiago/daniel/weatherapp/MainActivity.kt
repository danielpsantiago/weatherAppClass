package santiago.daniel.weatherapp

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import santiago.daniel.weatherapp.conn.*
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }
        
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCurrentWeather()
        getForecast()
    }

    private fun getForecast() {
        val call = ApiClient.instance.weatherService().getForecast("bfe04d8304296be17d369387b6e8a963")
        call.enqueue(object: Callback<ForecastResponse?> {
            override fun onFailure(call: Call<ForecastResponse?>?, t: Throwable?) {
                showGenericError()
            }

            override fun onResponse(call: Call<ForecastResponse?>?, response: Response<ForecastResponse?>?) {
                if (response?.isSuccessful == true && response.code() == 200
                        && response.body()?.list != null) {
                    val forecasts = response.body()!!.list
                    val forecastsIte = forecasts.listIterator()
                    forecastsIte.forEach { forecastItem ->
                        val found = forecasts.find {
                            forecastItem.date.get(Calendar.DAY_OF_YEAR) == it.date.get(Calendar.DAY_OF_YEAR)
                                    && forecastItem.date.get(Calendar.HOUR) != it.date.get(Calendar.HOUR)
                        }

                        if (found != null) forecastsIte.remove()
                    }

                    forecastRV.layoutManager = LinearLayoutManager(this@MainActivity)
                    forecastRV.adapter = ForecastAdapter(ArrayList(forecasts), this@MainActivity)
                } else {
                    showGenericError()
                }
            }
        })
    }

    private fun getCurrentWeather() {
        val call = ApiClient.instance.weatherService().getCurrentWeather("bfe04d8304296be17d369387b6e8a963")
        call.enqueue(object: Callback<CurrentWeatherResponse?> {
            override fun onFailure(call: Call<CurrentWeatherResponse?>?, t: Throwable?) {
                showGenericError()
            }

            override fun onResponse(call: Call<CurrentWeatherResponse?>?, response: Response<CurrentWeatherResponse?>?) {
                progressBar.visibility = View.GONE
                if (response?.isSuccessful == true && response.code() == 200) {
                    response.body()?.let {
                        currentTempTX.text = "${it.main.temp}°C"
                        val weatherDescription =
                                when (WeatherConditions.normalize(it.weather[0].id)) {
                                    WeatherConditions.CLEAR -> getString(R.string.clear)
                                    WeatherConditions.ATMOSPHERE -> getString(R.string.cloudy)
                                    WeatherConditions.RAIN -> getString(R.string.rain)
                                    WeatherConditions.DRIZZLE -> getString(R.string.drizzle)
                                    WeatherConditions.THUNDERSTORM -> getString(R.string.storm)
                                    WeatherConditions.SNOW -> getString(R.string.snow)
                                    else -> getString(R.string.clear)
                                }
                        currentWeatherDescTX.text = "$weatherDescription ${it.main.minTemp.roundToInt()}/${it.main.maxTemp.roundToInt()}°C"

                        root.background = when (WeatherConditions.normalize(it.weather[0].id)) {
                            WeatherConditions.ATMOSPHERE, WeatherConditions.DRIZZLE -> ContextCompat.getDrawable(this@MainActivity, R.drawable.rainy_bg)
                            WeatherConditions.RAIN, WeatherConditions.THUNDERSTORM, WeatherConditions.SNOW -> ContextCompat.getDrawable(this@MainActivity, R.drawable.stormy_bg)
                            else -> ContextCompat.getDrawable(this@MainActivity, R.drawable.sunny_bg)
                        }
                    }
                } else {
                    showGenericError()
                }
            }
        })
    }

    private fun showGenericError() {
        AlertDialog.Builder(this@MainActivity)
                .setTitle("Erro!")
                .setMessage("Não conseguimos pegar os dados do tempo! Tente novamente!")
                .setPositiveButton(getString(R.string.ok), { dialog, _ ->
                    dialog.dismiss()
                })
                .show()
    }
}
