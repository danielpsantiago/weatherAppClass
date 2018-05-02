package santiago.daniel.weatherapp.conn

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    fun getCurrentWeather(
            @Query("APPID") apikey: String,
            @Query("q") city: String = "Salvador,br",
            @Query("units") units: String = "metric"
    ): Call<CurrentWeatherResponse>

    @GET("forecast")
    fun getForecast(
            @Query("APPID") apikey: String,
            @Query("q") city: String = "Salvador,br",
            @Query("units") units: String = "metric"
    ): Call<ForecastResponse>

}