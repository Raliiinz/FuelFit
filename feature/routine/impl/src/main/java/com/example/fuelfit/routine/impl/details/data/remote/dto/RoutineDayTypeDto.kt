package com.example.fuelfit.routine.impl.details.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class RoutineDayTypeDto {
    @SerialName("custom")
    CUSTOM,

    @SerialName("enom")
    ENOM,

    @SerialName("amrap")
    AMRAP,

    @SerialName("hiit")
    HIIT,

    @SerialName("tabata")
    TABATA,

    @SerialName("edt")
    EDT,

    @SerialName("rft")
    RFT,

    @SerialName("afap")
    AFAP,
}
