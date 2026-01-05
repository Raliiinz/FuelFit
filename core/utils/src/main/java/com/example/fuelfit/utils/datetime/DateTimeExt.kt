package com.example.fuelfit.utils.datetime

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

private val WGER_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSXXX")

fun String.toOffsetDateTime(): OffsetDateTime? = runCatching {
    OffsetDateTime.parse(this, WGER_FORMATTER)
}.getOrNull()
