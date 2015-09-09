package net.xtof.time

case class Interval(start: Instant, end: Instant) {
  if (end < start)
    throw new IllegalArgumentException("Interval can not have an end Instant before start Instant")

  def by(step: Duration): Stream[Instant] =
    Stream.iterate(start)(_ + step).takeWhile(_ < end)
}
