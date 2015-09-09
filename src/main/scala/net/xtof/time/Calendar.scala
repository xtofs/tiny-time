package net.xtof.time

// https://en.wikipedia.org/wiki/Julian_day#Calculation
// http://aa.usno.navy.mil/cgi-bin/aa_jdconv.pl?form=1&year=1970&month=1&day=1&era=1&hr=0&min=0&sec=0.0
// https://tools.ietf.org/html/rfc3339

object Calendar {

  final val Epoch = Instant(0)

  val MillisecondsPerSecond = 1000L
  val MillisecondsPerMinute = 60L * MillisecondsPerSecond
  val MillisecondsPerHour = 60L * MillisecondsPerMinute
  val MillisecondsPerDay = 24L * MillisecondsPerHour

  private val julianDayNumberJan1st0001 = 1721424
  //  private val julianDayNumberJan1st1970 = 2440588
  //  private val julianDayNumberJan1st2000 = 2451545

  val UnixEpoch: Instant = Instant(1970, 1, 1) // indirectly depends on MillisecondsPerDay and julianDayNumberJan1st0001

  def millisecondsSinceEpoch(year: Int, month: Int, day: Int): Long = {

    val a = (14 - month) / 12 // 'a' will be 1 for January and February, and 0 for other months.
    val y = year + 4800 - a
    val m = month + 12 * a - 3 // 'm' will be 0 for March and 11 for February.

    val d = (153 * m + 2) / 5 // gives the number of days since March 1 and comes from the repetition of days in the month from March in groups of five
    val j = day + d + (365 * y) + (y / 4) - (y / 100) + (y / 400) - 32045

    val ms = (j - julianDayNumberJan1st0001).toLong * MillisecondsPerDay
    ms
  }

  def dateParts(millisecondsSinceEpoch: Long): (Int, Int, Int) = {
    if (millisecondsSinceEpoch < 0)
      throw new IllegalArgumentException

    val jd = (millisecondsSinceEpoch / MillisecondsPerDay).toInt + julianDayNumberJan1st0001

    val f = jd + 1401 + (((4 * jd + 274277) / 146097) * 3) / 4 - 38
    val e = 4 * f + 3
    val g = (e % 1461) / 4
    val h = 5 * g + 2

    val d = ((h % 153) / 5) + 1
    val m = ((h / 153 + 2) % 12) + 1
    val y = (e / 1461) - 4716 + (12 + 2 - m) / 12

    (y, m, d)
  }

  private[time] def dayOfWeek(millisecondsSinceEpoch: Long): Int = {
    ((millisecondsSinceEpoch / MillisecondsPerDay + 5).toInt % 7) + 1
  }
}
