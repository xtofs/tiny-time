/*
 * Copyright (c) 2015.
 * CenturyLink Cloud. All rights reserved.
 */

package net.xtof.time

trait TimeSpan {
  def addTo(instant: Instant) : Instant
}

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

trait DurationWriter {
  def write(duration: Duration) : String
}

object DurationWriter {

  val default = new DurationWriter {
    override def write(duration: Duration): String = {
      val sb = new StringBuilder

      def appendIfNotNull(value: Int, suffix: String) =
        if (value != 0) {
          sb.append(value);
          sb.append(suffix)
        }

      sb.append("P")
      appendIfNotNull(duration.days, "D")
      sb.append("T")
      appendIfNotNull(duration.hours, "H")
      appendIfNotNull(duration.minutes, "M")

      val s = duration.seconds
      val ms = duration.milliseconds
      if (s > 0 || ms > 0) {
        sb.append(s)
        if (ms > 0) {
          sb.append(".")
          sb.append("%03d".format(ms))
        }
        sb.append("S")
      }

      sb.mkString
    }
  }
}
