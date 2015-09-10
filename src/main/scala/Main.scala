package main


import net.xtof.time._


object Main {


  def main(args: Array[String]): Unit = {

    val x = Instant(1967, 5, 6)
    val y = Period.Months(1) - Period.Days(1)

    val z: Instant = x + y
    println(z)
  }

  def test()
  {
    val x = Instant.now

    val y = Instant(1967, 5, 6)
    val g = x - y
    println(g)

    val a = Period.Hours(1)
    val b = Period.Minutes(65)
    val c = a + b
    val d = Period(7994213870L)
    val e = Period(7994213000L)
    val f = Period(61000L, 1, 1)

    implicit val fmt = PeriodWriter.normalized

    for (i <- Seq(a, b, c, d, e, f)) {
      println(i.format)
    }
  }
}

