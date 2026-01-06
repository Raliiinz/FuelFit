package com.example.fuelfit.utils.datetime

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

private val WGER_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")

fun String.toOffsetDateTime(): OffsetDateTime? = runCatching {
    OffsetDateTime.parse(this, WGER_FORMATTER)
}.getOrNull()

fun LocalDate.toDate(): Date =
    Date.from(atStartOfDay(ZoneId.systemDefault()).toInstant())

fun Date.toLocalDate(): LocalDate =
    toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
