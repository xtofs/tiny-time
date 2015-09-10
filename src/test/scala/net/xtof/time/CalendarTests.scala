package net.xtof.time

import org.scalacheck.Gen
import org.scalacheck.Prop._
import org.scalatest._
import org.scalatest.prop.Checkers


class CalendarPropertyTests extends FunSuite with ShouldMatchers {

  import org.scalatest.prop.TableDrivenPropertyChecks._

  val samples =
    Table(
      ("i", "y", "m", "d", "h", "m", "s", "z"), // First tuple defines column names

      (Instant(1, 1, 1), 1, 1, 1, 0, 0, 0, 0),
      (Instant(9999, 1, 1), 9999, 1, 1, 0, 0, 0, 0),

      (Instant(1967, 5, 6), 1967, 5, 6, 0, 0, 0, 0),
      (Instant(1967, 5, 6, 3, 10, 15, 27), 1967, 5, 6, 3, 10, 15, 27),

      (Instant(2015, 5, 6), 2015, 5, 6, 0, 0, 0, 0),
      (Instant(2015, 5, 6, 3, 10, 15, 27), 2015, 5, 6, 3, 10, 15, 27),

      (Instant(1970, 1, 1), 1970, 1, 1, 0, 0, 0, 0),
      (Instant(1970, 1, 2), 1970, 1, 2, 0, 0, 0, 0),
      (Instant(1970, 1, 25), 1970, 1, 25, 0, 0, 0, 0),
      (Instant(1970, 1, 30), 1970, 1, 30, 0, 0, 0, 0),
      (Instant(1970, 1, 31), 1970, 1, 31, 0, 0, 0, 0),

      (Instant(1970, 2, 1), 1970, 2, 1, 0, 0, 0, 0),
      (Instant(2000, 1, 1), 2000, 1, 1, 0, 0, 0, 0),
      (Instant(2016, 1, 1), 2016, 1, 1, 0, 0, 0, 0))

  test("samples") {
    forAll(samples) { (i: Instant, y: Int, m: Int, d: Int, h: Int, n: Int, s: Int, z: Int) => {
      println(i, y, m, d, h, n, s, z)

      i.year should be === y
      i.month should be === m
      i.day should be === d

      i.hour should be === h
      i.minute should be === n
      i.second should be === s
      i.milliseconds should be === z
    }}
  }
}

class CalendarTests extends FunSuite with Checkers {

  test("weekday is consecutive") {

    val start = Instant(1970, 1, 1)
    var expected = start.get(InstantField.DayOfWeek)

    for (i <- start to Instant(2000, 1, 1) by Duration(days = 1)) {

      val dow = i.get(InstantField.DayOfWeek)

      assert(dow == expected)

      expected = expected % 7 + 1
    }
  }

  val max = Array(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
  val nums = org.scalacheck.Gen.choose(0, 40000)

  test("day never exceeds max number of days per month") {
    check(
      forAll(nums)((i: Int) => {
        val (_, m, d) = Calendar.dateParts(i)

        d <= max(m - 1)
      })
    )
  }

  implicit val oneDayAfter_2_28 =
    Gen.choose(1970, 4000).map(y => Calendar.millisecondsSinceEpoch(y, 2, 28) + Constants.MillisecondsPerDay)

  test("february has 29 days in leap year") {
    check(
      forAll(oneDayAfter_2_28)((j: Long) => {

        val (y, m, d) = Calendar.dateParts(j)

        val isLeap = (y % 4 == 0) && !( (y % 100 == 0) && (y % 400 != 0) )
        println(y, m, d, isLeap)

        // if it is a leap year the day following 2/28 is 2/29 otherwise it is 3/1
        isLeap && m == 2 && d == 29 || (! isLeap) && m == 3 && d == 1
      })
    )
  }
}