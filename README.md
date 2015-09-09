# tiny-time
A very simple scala date and time library

Time And Date Libraries can be complicated and hard to use. This is an attempt to write a simple and focused Library.
It is scoped to scenarios related to time series and the date and time calculations that are required in that case.
Therefore we don't have the intention to support many different calendars or sophisticated support for
parsing and printing dates in Human readable form. It mostly tries to cover what ISO 8601 and RFC 3339 describe.

[rfc 3339](http://tools.ietf.org/html/rfc3339)
[Wikipedia on ISO 8601](https://en.wikipedia.org/wiki/ISO_8601)

## Data types
THe Library has three main data types
* Instant describes a single point int time in the physical sense. It is not directly what a clock shows since
this depends on what the current conventions are the current location on the globe.
It is very close to the RFC 3339 concept of a coordinated universal time.
* Duration describes an exact amount of elapsed time between two points in time
* Period describes an amount of time between two points in time in a more conventional way.
An hour is always exactly 60 minutes, but month and year vary in their exact length. Only when one is referring to
for example the year starting with January 1st 1990 is the exact duration (measured in Duration) known.
Period's allow to specify years and month whereas Duration's don't.
* Interval is the intervening time between two time points described by start and end or start and duration




## Operations on Instants, Duration, and Intervals

the relationship between Instant and Duration can be loosley described as a [Affine space](https://en.wikipedia.org/wiki/Affine_space)
If one thinks about Instant as a point in a 2 dimensional space without a fixed origin and Duration as a vector in this
space, it might explain why certain operations make sense and others not.

 * adding a Duration to a Point leads to another point
 * adding two Durations leads to another Duration

 * comparing two Instants and deciding if one is before, at, or after


 * testing if two Durations are overlapping at any point
 * testing if two Durations are abuting at any point (i.e. have a common boundary with each other)






## parsing and printing

we stick to [rfc 3339] for Instants and ISO 8601 for Period, Duration amd Interval
