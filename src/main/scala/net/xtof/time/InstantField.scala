package net.xtof.time


trait InstantField {
  def extract(dateTime: Instant): Int
}

object InstantField {

  private case class ExactInstantField(unit: Long, range: Int) extends InstantField {
    def extract(dateTime: Instant): Int = ((dateTime.millisSinceEpoch / unit) % range).toInt
  }

  private case class CalendarInstantField(select: ((Int, Int, Int)) => Int) extends InstantField {
    def extract(dateTime: Instant): Int =
      select(Calendar.dateParts(dateTime.millisSinceEpoch))
  }

  private case class FuncInstantField(select: Long => Int) extends InstantField {
    def extract(dateTime: Instant): Int =
      select(dateTime.millisSinceEpoch)
  }

  val Year:                 InstantField = CalendarInstantField(t => t._1)
  val MonthOfYear:          InstantField = CalendarInstantField(t => t._2)
  val DayOfMonth:           InstantField = CalendarInstantField(t => t._3)

  // TODO: use Constants Object
  val HourOfDay:            InstantField = ExactInstantField(1000 * 60 * 60, 24)
  val MinuteOfHour:         InstantField = ExactInstantField(1000 * 60, 60)
  val SecondOfMinute:       InstantField = ExactInstantField(1000, 60)
  val MillisecondsOfSecond: InstantField = ExactInstantField(1, 1000)
  val DayOfWeek:            InstantField = FuncInstantField(millis => Calendar.dayOfWeek(millis))
}