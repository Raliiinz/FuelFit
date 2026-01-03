package com.example.fuelfit.routine.impl.testutils

import com.example.fuelfit.routine.api.dayDetail.model.SlotEntry
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryRequest
import com.example.fuelfit.routine.api.dayDetail.model.SlotEntryType

object SlotEntryTestData {

    fun makeSlotEntryRequest(
        slotId: Int = 1,
        exerciseId: Int = 101,
        type: SlotEntryType = SlotEntryType.NORMAL,
        repetitionUnit: Int? = 10,
        repetitionRounding: String? = null,
        weightUnit: Int? = 50,
        weightRounding: String? = null,
        order: Int = 0,
        comment: String = "Test comment",
        config: String? = null
    ) = SlotEntryRequest(
        slotId = slotId,
        exerciseId = exerciseId,
        type = type,
        repetitionUnit = repetitionUnit,
        repetitionRounding = repetitionRounding,
        weightUnit = weightUnit,
        weightRounding = weightRounding,
        order = order,
        comment = comment,
        config = config
    )

    fun makeSlotEntry(
        id: Int = 1,
        slotId: Int = 1,
        exerciseId: Int = 101,
        type: SlotEntryType = SlotEntryType.NORMAL,
        repetitionUnit: Int? = 10,
        repetitionRounding: String? = null,
        weightUnit: Int? = 50,
        weightRounding: String? = null,
        order: Int = 0,
        comment: String = "Test comment",
        config: String? = null
    ) = SlotEntry(
        id = id,
        slotId = slotId,
        exerciseId = exerciseId,
        type = type,
        repetitionUnit = repetitionUnit,
        repetitionRounding = repetitionRounding,
        weightUnit = weightUnit,
        weightRounding = weightRounding,
        order = order,
        comment = comment,
        config = config
    )
}
