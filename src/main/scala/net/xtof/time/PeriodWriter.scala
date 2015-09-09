/*
 * Copyright (c) 2015.
 * CenturyLink Cloud. All rights reserved.
 */

package net.xtof.time




trait PeriodWriter {
  def write(p: Period): String
}

object PeriodWriter {

  val basic = new PeriodWriter {
    override def write(p: Period): String = s"P${p.years}Y${p.months}MT${p.precise * 1000.0}S"
  }

  val normalized = new PeriodWriter {
    
    override def write(p: Period): String = {
      val sb = new StringBuilder

      sb.append("P")
      // @formatter:off
      val y = p.years; if (y > 0) { sb.append(y); sb.append("Y") }
      val o = p.months; if (o > 0) { sb.append(o); sb.append("M") }
      if (p.precise > 0) {
        val w = p.weeks; if (w > 0) { sb.append(w); sb.append("W") }
        val d = p.days; if (d > 0) { sb.append(d); sb.append("D") }
        sb.append("T")
        val h = p.hours; if (h > 0) { sb.append(h); sb.append("H") }
        val m = p.minutes; if (m > 0) { sb.append(m); sb.append("M")}

        val s = p.seconds
        val z = p.precise % 1000
        if (s > 0 || z > 0) {
          if (z == 0) {
            sb.append(s)
          } else {
            sb.append(f"${s + z / 1000.0}%.3f")
          }
          sb.append("S")
        }
      }
      // @formatter:on

      sb.mkString
    }
  }
}