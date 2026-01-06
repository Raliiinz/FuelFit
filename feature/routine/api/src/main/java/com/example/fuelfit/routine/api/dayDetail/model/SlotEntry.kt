package com.example.fuelfit.routine.api.dayDetail.model

data class SlotEntry(
    val id: Int,
    val slotId: Int,
    val exerciseId: Int,
    val type: SlotEntryType,
    val repetitionUnit: Int?,
    val repetitionRounding: String?,
    val weightUnit: Int?,
    val weightRounding: String?,
    val order: Int,
    val comment: String?,
    val config: String?,
)
