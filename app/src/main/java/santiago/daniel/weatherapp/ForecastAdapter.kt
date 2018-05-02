package santiago.daniel.weatherapp

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_prevision.view.*
import santiago.daniel.weatherapp.conn.CurrentWeatherResponse
import santiago.daniel.weatherapp.conn.WeatherConditions
import java.util.*
import kotlin.math.roundToInt

class ForecastAdapter(var list: ArrayList<CurrentWeatherResponse>, var context: Context): RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_prevision, parent, false))


    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastItem = list[position]

        holder.itemView.weatherImg.setImageDrawable(ContextCompat.getDrawable(context, getCorrectIconId(forecastItem.weather[0].id)))

        holder.itemView.dayTX.text = getDayStr(forecastItem.date.get(Calendar.DAY_OF_WEEK), context)

        holder.itemView.minTX.text = "${forecastItem.main.minTemp.roundToInt()}°C"
        holder.itemView.maxTX.text = "${forecastItem.main.maxTemp.roundToInt()}°C"
    }

    private fun getCorrectIconId(weatherId: Int): Int {
        return when (WeatherConditions.normalize(weatherId)) {
            WeatherConditions.ATMOSPHERE, WeatherConditions.DRIZZLE -> R.drawable.ic_cloud
            WeatherConditions.RAIN, WeatherConditions.THUNDERSTORM -> R.drawable.ic_rain
            else -> R.drawable.ic_sun
        }
    }

    private fun getDayStr(dayOfWeek: Int, context: Context): String {
        return when(dayOfWeek) {
            Calendar.SUNDAY -> context.getString(R.string.sunday)
            Calendar.MONDAY -> context.getString(R.string.monday)
            Calendar.TUESDAY -> context.getString(R.string.tuesday)
            Calendar.WEDNESDAY -> context.getString(R.string.wednesday)
            Calendar.THURSDAY -> context.getString(R.string.thursday)
            Calendar.FRIDAY -> context.getString(R.string.friday)
            else -> context.getString(R.string.saturday)
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}