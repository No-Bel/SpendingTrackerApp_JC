package com.example.spendingtrackerapp.spending_overview.presentation.util

import java.time.ZonedDateTime

fun ZonedDateTime.dateFormater(): String {
    return "$dayOfMonth-$monthValue-$year"
}