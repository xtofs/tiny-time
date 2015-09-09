package net.xtof.time



case class Period(precise: Long, months: Int = 0, years: Int = 0) {

  def +(that: Period): Period =
    Period(this.precise + that.precise, this.months + that.months, this.years + that.years)

  // @formatter:off
  def millis  : Int = (precise % 1000 ).toInt
  def seconds : Int = (precise / 1000 % 60 ).toInt
  def minutes : Int = (precise / 1000 / 60 % 60 ).toInt
  def hours   : Int = (precise / 1000 / 60 / 60 % 24 ).toInt
  def days    : Int = (precise / 1000 / 60 / 60 / 24 % 7 ).toInt
  def weeks   : Int = (precise / 1000 / 60 / 60 / 24 / 7 ).toInt
  // @formatter:on

  def format(implicit writer: PeriodWriter) = writer.write(this)

  override def toString = s"P${years}Y${months}MT${precise * 1000.0}S"
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

  def Month(n: Int): Period = Period(0, n)

  def Years(n: Int): Period = Period(0, 0, n)
}
