package santiago.daniel.weatherapp.conn

import com.google.gson.annotations.SerializedName

data class WeatherMainData(
        var temp: Float,
        @SerializedName("temp_min") var maxTemp: Float,
        @SerializedName("temp_max") var minTemp: Float
)