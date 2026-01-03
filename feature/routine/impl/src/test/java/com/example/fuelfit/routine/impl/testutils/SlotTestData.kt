package com.example.fuelfit.routine.impl.testutils

import com.example.fuelfit.routine.api.dayDetail.model.Slot
import com.example.fuelfit.routine.api.dayDetail.model.SlotRequest

object SlotTestData {

    fun makeSlotRequest(
        dayId: Int = 1,
        order: Int = 0,
        comment: String = "",
        config: String? = null
    ) = SlotRequest(
        dayId = dayId,
        order = order,
        comment = comment,
        config = config
    )

    fun makeSlot(
        id: Int = 1,
        dayId: Int = 1,
        order: Int = 0,
        comment: String = "",
        config: String? = null
    ) = Slot(
        id = id,
        dayId = dayId,
        order = order,
        comment = comment,
        config = config
    )
}
