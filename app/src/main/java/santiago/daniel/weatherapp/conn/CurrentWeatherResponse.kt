package santiago.daniel.weatherapp.conn

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class CurrentWeatherResponse(
        @SerializedName("name") var cityName: String,
        @SerializedName("dt") var dateInMillis: Long,
        @SerializedName("dt_txt") private var dateStr: String,
        var weather: ArrayList<Weather>,
        var main: WeatherMainData
) {

    var date: Calendar = Calendar.getInstance()
        get() {
            val time = Calendar.getInstance()
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")//"2014-07-23 09:00:00"
            time.timeInMillis = sdf.parse(dateStr).time
            return time
        }

}