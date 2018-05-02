package santiago.daniel.weatherapp.conn

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Retrofit.weatherService(): WeatherService = this.create(WeatherService::class.java)

object ApiClient {
    val instance: Retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}