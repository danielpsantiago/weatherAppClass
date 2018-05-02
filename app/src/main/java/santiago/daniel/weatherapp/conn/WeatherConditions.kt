package santiago.daniel.weatherapp.conn

object WeatherConditions {

    const val THUNDERSTORM = -2
    const val DRIZZLE = -3
    const val RAIN = -5
    const val SNOW = -6
    const val ATMOSPHERE = -7
    const val CLEAR = -800

    fun normalize(code: Int): Int =
            when (code) {
                in 200..299 -> THUNDERSTORM
                in 300..399 -> DRIZZLE
                in 500..599 -> RAIN
                in 600..699 -> SNOW
                in 700..799 -> ATMOSPHERE
                in 800..899 -> CLEAR
                else -> CLEAR
            }

}