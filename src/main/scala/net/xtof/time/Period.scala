package net.xtof.time



case class Period(preciseMilliseconds: Long, months: Int = 0, years: Int = 0) extends TimeSpan {

  override def addTo(instant: Instant): Instant = {

    val (y, m, d) = Calendar.dateParts(instant.millisSinceEpoch)
    val ms = instant.millisSinceEpoch % Calendar.MillisecondsPerDay
    // the sum of y, m, d, and ms are conceptually the instant but are measured in their respective "units of measure"

    // add y, m, d, and ms to the respective parts of the this period
    Instant(Calendar.millisecondsSinceEpoch(y+years, m+months, d) + ms +  this.preciseMilliseconds)
  }

  def plus (that: Period): Period =
    Period(this.preciseMilliseconds + that.preciseMilliseconds, this.months + that.months, this.years + that.years)
  def minus (that: Period): Period =
    Period(this.preciseMilliseconds - that.preciseMilliseconds, this.months - that.months, this.years - that.years)


  def + (instant: Instant): Instant = this.addTo(instant)

  def + (that: Period): Period = this.plus(that)
  def - (that: Period): Period = this.minus(that)

  // @formatter:off
  def millis  : Int = (preciseMilliseconds % 1000 ).toInt
  def seconds : Int = (preciseMilliseconds / 1000 % 60 ).toInt
  def minutes : Int = (preciseMilliseconds / 1000 / 60 % 60 ).toInt
  def hours   : Int = (preciseMilliseconds / 1000 / 60 / 60 % 24 ).toInt
  def days    : Int = (preciseMilliseconds / 1000 / 60 / 60 / 24 % 7 ).toInt
  def weeks   : Int = (preciseMilliseconds / 1000 / 60 / 60 / 24 / 7 ).toInt
  // @formatter:on

  def format(implicit writer: PeriodWriter) = writer.write(this)

  override def toString = s"P${years}Y${months}MT${preciseMilliseconds * 1000.0}S"
}


object Period {
  //  private case class Precise(milliseconds: Long) extends Period
  //  // private case class Weeks(n: Int) extends Period
  //  private case class Months(n: Int) extends Period
  //  private case class Years(n: Int) extends Period
  //  private case class Combined(p: Precise, months: Months, years: Years) extends Period

  def Minutes(n: Int): Period = Period(n * Calendar.MillisecondsPerMinute)

  def Hours(n: Int): Period = Period(n * Calendar.MillisecondsPerHour)

  def Days(n: Int): Period = Period(n * Calendar.MillisecondsPerDay)

  def Weeks(n: Int): Period = Period(n * Calendar.MillisecondsPerDay * 7)

  def Months(n: Int): Period = Period(0, n)

  def Years(n: Int): Period = Period(0, 0, n)

}
