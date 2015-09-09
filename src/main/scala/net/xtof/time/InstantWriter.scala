package net.xtof.time


trait InstantWriter {
  def write(dateTime: Instant): String
}

object InstantWriter {

  private val fmt = "%04d-%02d-%02d"

  val date: InstantWriter = BaseInstantWriter(dt => {
    val (y, m, d) = Calendar.dateParts(dt.millisSinceEpoch)
    fmt.format(y, m, d)
  })

  val time: InstantWriter = Sequence(
    part(InstantField.HourOfDay),
    const(":"),
    part(InstantField.MinuteOfHour),
    ifNotNull(InstantField.SecondOfMinute,
      const(":"),
      part(InstantField.SecondOfMinute)),
    // TODO: fix: if Second is 0 then the output is HH:MM.zzz 
    ifNotNull(InstantField.MillisecondsOfSecond,
      const("."),
      part(InstantField.MillisecondsOfSecond, 3))
  )

  val readable: InstantWriter = Sequence(date, const(" "), time)


  private final case class BaseInstantWriter(f: Instant => String) extends InstantWriter {
    def write(dateTime: Instant): String = f(dateTime)
  }

  private final case class Sequence(fmts: InstantWriter*) extends InstantWriter {
    def write(dateTime: Instant): String =
      fmts.map(fmt => fmt.write(dateTime)).mkString
  }

  def part(dtp: InstantField, n: Int = 2): InstantWriter =
    format(dtp, "%0" + n.toString + "d")

  def format(dtp: InstantField, format: String): InstantWriter =
    BaseInstantWriter(dt => format.format(dtp.extract(dt)))

  def const(str: String): InstantWriter =
    BaseInstantWriter(_ => str)

  def ifNotNull(dateTimePart: InstantField, fmts: InstantWriter*): InstantWriter =
    cond(dt => dateTimePart.extract(dt) != 0, fmts: _*)

  def cond(predicate: Instant => Boolean, fmts: InstantWriter*): InstantWriter =
    BaseInstantWriter(dt => if (predicate(dt)) fmts.map(f => f.write(dt)).mkString else "")
}