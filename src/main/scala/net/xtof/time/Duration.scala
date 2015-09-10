

package net.xtof.time



object Duration {
  def apply(days: Int = 0, hours: Int = 0, minutes: Int = 0, seconds: Int = 0, milliseconds: Int = 0): Duration =
    Duration(milliseconds +
      seconds * Calendar.MillisecondsPerSecond +
      minutes * Calendar.MillisecondsPerMinute +
      hours * Calendar.MillisecondsPerHour +
      days * Calendar.MillisecondsPerDay)
}

case class Duration(totalMilliseconds: Long) extends TimeSpan {
  if (totalMilliseconds <= 0)
    throw new IllegalArgumentException("Duration must be strictly positive")

  override def addTo(instant: Instant): Instant = Instant(instant.millisSinceEpoch + this.totalMilliseconds)

  def milliseconds = (totalMilliseconds % 1000).toInt

  def seconds = (totalMilliseconds / 1000 % 60).toInt

  def minutes = (totalMilliseconds / 1000 / 60 % 60).toInt

  def hours = (totalMilliseconds / 1000 / 60 / 60 % 24).toInt

  def days = (totalMilliseconds / 1000 / 60 / 60 / 24).toInt


  override def toString = DurationWriter.default.write(this)

}




