package com.example.fuelfit.routine.impl.dayDetail.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class SlotEntryTypeDto {
    @SerialName("normal") NORMAL,
    @SerialName("dropset") DROPSET,
    @SerialName("myo") MYO,
    @SerialName("partial") PARTIAL,
    @SerialName("forced") FORCED,
    @SerialName("tut") TUT,
    @SerialName("iso") ISO,
    @SerialName("jump") JUMP
}
