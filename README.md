# Tides API for Washington DC

## Endpoints
```/waterlevel``` all data from August 1, 2020 to August 30, 2020

```/waterlevel/outliers``` the high and low data levels defined by the boundary of ```1.8 * standard_deviation  + or -  mean```

```/waterlevel/stats``` stats for mean, median, stdev and percentiles for 10th, 25th, 50th, 75th, 90th, as well as the list of outliers

```/waterlevel/{year}/{month}/{day}/{hh}/{mm}/{width}``` readings for the specified date, through the ```width``` number of readings.


