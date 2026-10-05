package com.spit91.maskani.presentation.auth.home

enum class PriceRange( val label: String, val min: Long, val max: Long) {

    UNDER_10K("Under 10,000", 0L, 10_000L),
    FROM_10K("10,000 - 20,000", 10_000L, 20_000L),
    FROM_20K("20,000 - 40,000", 20_000L, 40_000L),
    FROM_40K("40,000 - 80,000", 40_000L, 80_000L),
    OVER_80K("Over 80,000", 80_000L, Long.MAX_VALUE)
}
