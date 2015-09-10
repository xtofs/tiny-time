package net.xtof.time


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