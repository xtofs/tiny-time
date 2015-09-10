package net.xtof.time

trait TimeSpan {
  def addTo(instant: Instant) : Instant
}
