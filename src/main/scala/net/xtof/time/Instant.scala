package net.xtof.time


case class Instant(millisSinceEpoch: Long) {

  def get(dateTimePart: InstantField): Int = dateTimePart.extract(this)

  def milliseconds = get(InstantField.MillisecondsOfSecond)

  def second = get(InstantField.SecondOfMinute)

  def minute = get(InstantField.MinuteOfHour)

  def hour = get(InstantField.HourOfDay)

  def day = get(InstantField.DayOfMonth)

  def month = get(InstantField.MonthOfYear)

  def year = get(InstantField.Year)

  def weekday = get(InstantField.DayOfWeek)


  override def toString = InstantWriter.readable.write(this)

  def +(timeSpan: TimeSpan): Instant = timeSpan.addTo(this)

  def -(other: Instant): Duration = new Duration(this.millisSinceEpoch - other.millisSinceEpoch)

  def <(dateTime: Instant): Boolean = this.millisSinceEpoch < dateTime.millisSinceEpoch

  def to(end: Instant): Interval =
    Interval(this, end)
}


object Instant {
  def now: Instant = Instant(System.currentTimeMillis + Calendar.UnixEpoch.millisSinceEpoch)

  def apply(year: Int, month: Int, day: Int): Instant =
    Instant(Calendar.millisecondsSinceEpoch(year, month, day))

  def apply(year: Int, month: Int, day: Int, hour: Int, minute: Int, seconds: Int, milliseconds: Int): Instant =
    Instant(Calendar.millisecondsSinceEpoch(year, month, day) +
      timeInMilliseconds(hour, minute, seconds, milliseconds))

  private def timeInMilliseconds(hour: Int, minute: Int, seconds: Int, milliseconds: Int) =
    milliseconds +
      seconds * Calendar.MillisecondsPerSecond +
      minute * Calendar.MillisecondsPerMinute +
      hour * Calendar.MillisecondsPerHour

}
