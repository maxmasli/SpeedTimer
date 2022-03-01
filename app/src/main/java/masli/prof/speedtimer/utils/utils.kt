package masli.prof.speedtimer.utils


// don't look at it
fun mapToTime(millis: Long): String {
    var result = ""
    val hours = millis / 3600000
    var timestamp = millis % 3600000
    val minutes = timestamp / 60000
    timestamp %= 60000
    val seconds = timestamp / 1000
    val m = timestamp % 1000
    if (hours != 0L) {
        result += "$hours:"
    }
    if (minutes != 0L && hours != 0L) {
        result += if(minutes < 10L) "0$minutes:"
         else "$minutes:"
    }
    if(minutes != 0L && hours == 0L) {
        result += "$minutes:"
    }
    if (minutes == 0L && hours != 0L){
        result += "00:"
    }
    if(seconds != 0L && minutes != 0L) {
        result += if (seconds < 10L) "0$seconds."
        else "$seconds."
    }
    if (seconds != 0L && minutes == 0L) {
        result += "$seconds."
    }
    if(seconds == 0L && minutes != 0L) {
        result += "00."
    }
    if(seconds == 0L && minutes == 0L && hours != 0L) {
        result += "00:00."
    }
    if(seconds == 0L && minutes == 0L && hours == 0L) {
        result += "0."
    }
    when {
        m == 0L -> result += "000"
        m < 10L -> result += "00$m"
        m < 100L -> result += "0$m"
        m < 1000L -> result += "$m"
    }
    return result
}