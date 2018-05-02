package santiago.daniel.weatherapp.conn

data class ForecastResponse(
    var list: ArrayList<CurrentWeatherResponse>
)