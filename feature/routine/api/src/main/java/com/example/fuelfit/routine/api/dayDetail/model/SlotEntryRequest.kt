package com.example.fuelfit.routine.api.dayDetail.model

data class SlotEntryRequest(
    val slotId: Int,
    val exerciseId: Int,
    val type: SlotEntryType = SlotEntryType.NORMAL,

    val repetitionUnit: Int? = null,
    val repetitionRounding: String? = null,
    val weightUnit: Int? = null,
    val weightRounding: String? = null,

    val order: Int = 0,
    val comment: String = "",
    val config: String? = null
)

