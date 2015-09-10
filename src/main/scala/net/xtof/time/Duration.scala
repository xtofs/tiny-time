

package net.xtof.time



object Duration {
  def apply(days: Int = 0, hours: Int = 0, minutes: Int = 0, seconds: Int = 0, milliseconds: Int = 0): Duration =
    Duration(milliseconds +
      seconds * Constants.MillisecondsPerSecond +
      minutes * Constants.MillisecondsPerMinute +
      hours * Constants.MillisecondsPerHour +
      days * Constants.MillisecondsPerDay)
}

object Constants {
  val DaysPerWeek = 7L

  val HoursPerDay = 24L
  val HoursPerWeek = HoursPerDay * DaysPerWeek

  val MinutesPerHour = 60L
  val MinutesPerDay = MinutesPerHour * HoursPerDay
  val MinutesPerWeek = MinutesPerDay * DaysPerWeek

  val SecondsPerMinute = 60L
  val SecondsPerHour = SecondsPerMinute * MinutesPerHour
  val SecondsPerDay = SecondsPerHour * HoursPerDay
  val SecondsPerWeek = SecondsPerDay * DaysPerWeek

  val MillisecondsPerSecond = 1000L
  val MillisecondsPerMinute = MillisecondsPerSecond * SecondsPerMinute
  val MillisecondsPerHour = MillisecondsPerMinute * MinutesPerHour
  val MillisecondsPerDay = MillisecondsPerHour * HoursPerDay
  val MillisecondsPerWeek = MillisecondsPerDay * DaysPerWeek

}

case class Duration(totalMilliseconds: Long) extends TimeSpan {
  if (totalMilliseconds <= 0)
    throw new IllegalArgumentException("Duration must be strictly positive")

  override def addTo(instant: Instant): Instant = Instant(instant.millisSinceEpoch + this.totalMilliseconds)

  // TODO: move the property logic into DurationFields
  import Constants._

  def milliseconds = (totalMilliseconds % MillisecondsPerSecond).toInt

  def seconds = (totalMilliseconds / MillisecondsPerSecond % SecondsPerMinute).toInt

  def minutes = (totalMilliseconds / MillisecondsPerMinute % MinutesPerHour).toInt

  def hours = (totalMilliseconds / MillisecondsPerHour % HoursPerDay).toInt

  // TODO: this property is different from the above since it is not in the range [0-23], why?
  def days = (totalMilliseconds / MillisecondsPerDay).toInt

  override def toString = DurationWriter.default.write(this)

}




